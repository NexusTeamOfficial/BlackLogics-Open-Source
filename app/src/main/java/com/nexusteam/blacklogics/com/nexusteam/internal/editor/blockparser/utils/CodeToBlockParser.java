package com.nexusteam.internal.editor.blockparser.utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.nexusteam.internal.editor.blockparser.model.BlockDefinition;
import com.nexusteam.internal.editor.blockparser.model.ParsedBlock;
import com.nexusteam.internal.editor.blockparser.result.BlockConversionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeToBlockParser {

    private BlockDefinitionRegistry registry;
    private final JavaParser javaParser;

    public CodeToBlockParser() {
        this.registry = BlockDefinitionRegistry.getInstance();
        this.javaParser = new JavaParser();
    }

    public CodeToBlockParser(BlockDefinitionRegistry registry) {
        this.registry = registry;
        this.javaParser = new JavaParser();
    }


    public BlockConversionResult parse(String javaCode) {
        long start = System.currentTimeMillis();
        if (javaCode == null || javaCode.trim().length() == 0) {
            return BlockConversionResult.empty();
        }
        try {
            List rootBlocks = parseWithAst(javaCode);
            if (rootBlocks == null || rootBlocks.size() == 0) {
                rootBlocks = parseWithRegexFallback(javaCode);
            }
            List flat = flattenForConversion(rootBlocks);
            BlockConversionResult result;
            if (flat.size() == 0) {
                result = BlockConversionResult.failed("No blocks generated from code");
            } else {
                boolean hasSource = false;
                for (int i = 0; i < flat.size(); i++) {
                    if (((ParsedBlock) flat.get(i)).isSourceBlock()) {
                        hasSource = true;
                        break;
                    }
                }
                if (hasSource) {
                    result = BlockConversionResult.partial(flat,
                            "Some code converted to blocks, unmatched parts became Source blocks");
                } else {
                    result = BlockConversionResult.success(flat);
                }
            }
            result.setOriginalCode(javaCode);
            result.setConversionTimeMs(System.currentTimeMillis() - start);
            return result;
        } catch (Exception e) {
            try {
                List rootBlocks = parseWithRegexFallback(javaCode);
                List flat = flattenForConversion(rootBlocks);
                BlockConversionResult result = flat.size() == 0
                        ? BlockConversionResult.failed("No blocks generated")
                        : BlockConversionResult.partial(flat, "AST failed, used regex fallback: " + e.getMessage());
                result.setOriginalCode(javaCode);
                result.setConversionTimeMs(System.currentTimeMillis() - start);
                return result;
            } catch (Exception e2) {
                BlockConversionResult fail = BlockConversionResult.failed(
                        "Parse error: " + (e.getMessage() != null ? e.getMessage() : e.toString()));
                fail.setOriginalCode(javaCode);
                fail.setConversionTimeMs(System.currentTimeMillis() - start);
                return fail;
            }
        }
    }


    private List parseWithAst(String javaCode) {
        String cleaned = removeComments(javaCode).trim();
        String wrapped = "public class _Tmp {\n  void _m() {\n" + cleaned + "\n  }\n}\n";

        ParseResult pr = javaParser.parse(wrapped);
        if (!pr.isSuccessful() || !pr.getResult().isPresent()) {
            return null;
        }
        CompilationUnit cu = (CompilationUnit) pr.getResult().get();
        final List roots = new ArrayList();

        cu.accept(new VoidVisitorAdapter() {
            @Override
            public void visit(MethodDeclaration md, Object arg) {
                if ("_m".equals(md.getNameAsString()) && md.getBody().isPresent()) {
                    BlockStmt body = md.getBody().get();
                    for (Statement st : body.getStatements()) {
                        ParsedBlock pb = convertStatement(st);
                        if (pb != null) roots.add(pb);
                    }
                }
            }
        }, null);

        return roots;
    }

    private ParsedBlock convertStatement(Statement st) {
        if (st == null) return null;

        if (st instanceof IfStmt) {
            return convertIf((IfStmt) st);
        }
        if (st instanceof WhileStmt) {
            return convertWhile((WhileStmt) st);
        }
        if (st instanceof DoStmt) {
            return convertDo((DoStmt) st);
        }
        if (st instanceof ForStmt) {
            return convertFor((ForStmt) st);
        }
        if (st instanceof ForEachStmt) {
            return convertForEach((ForEachStmt) st);
        }
        if (st instanceof BreakStmt) {
            return simpleBlock("break", st.toString());
        }
        if (st instanceof ContinueStmt) {
            return simpleBlock("continue", st.toString());
        }
        if (st instanceof ReturnStmt) {
            ReturnStmt rs = (ReturnStmt) st;
            if (rs.getExpression().isPresent()) {
                ParsedBlock b = simpleBlock("returnValue", st.toString());
                b.addParameter("value", rs.getExpression().get().toString());
                return b;
            }
            return simpleBlock("return", st.toString());
        }
        if (st instanceof BlockStmt) {
            ParsedBlock dummy = new ParsedBlock();
            dummy.setId(generateId());
            dummy.setOpcode("block");
            dummy.setType(ParsedBlock.TYPE_CONTAINER);
            dummy.setMatched(true);
            for (Statement inner : ((BlockStmt) st).getStatements()) {
                ParsedBlock child = convertStatement(inner);
                if (child != null) dummy.addChild(child);
            }
            return dummy;
        }
        if (st instanceof ExpressionStmt) {
            return convertExpression(((ExpressionStmt) st).getExpression(), st.toString());
        }
        if (st instanceof ExplicitConstructorInvocationStmt) {
            return matchByRegistry(st.toString());
        }

        return matchByRegistry(st.toString());
    }

    private ParsedBlock convertIf(IfStmt ifs) {
        boolean hasElse = ifs.getElseStmt().isPresent();
        String opcode = hasElse ? "ifElse" : "if";
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode(opcode);
        block.setDisplayName(opcode);
        block.setOriginalCode(ifs.toString());
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        block.addParameter("condition", ifs.getCondition().toString());

        addBodyChildren(block, ifs.getThenStmt(), false);

        if (hasElse) {
            Statement elseSt = ifs.getElseStmt().get();
            if (elseSt instanceof IfStmt) {
                ParsedBlock elseIf = convertIf((IfStmt) elseSt);
                if (elseIf != null) {
                    elseIf.addParameter("_branch", "else");
                    block.addChild(elseIf);
                }
            } else {
                addBodyChildren(block, elseSt, true);
            }
        }
        return block;
    }

    private ParsedBlock convertWhile(WhileStmt ws) {
        String cond = ws.getCondition().toString().trim();
        String opcode = "true".equals(cond) ? "forever" : "while";
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode(opcode);
        block.setDisplayName(opcode);
        block.setOriginalCode(ws.toString());
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        if (!"forever".equals(opcode)) {
            block.addParameter("condition", cond);
        }
        addBodyChildren(block, ws.getBody(), false);
        return block;
    }

    private ParsedBlock convertDo(DoStmt ds) {
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode("while");
        block.setDisplayName("do-while");
        block.setOriginalCode(ds.toString());
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        block.addParameter("condition", ds.getCondition().toString());
        addBodyChildren(block, ds.getBody(), false);
        return block;
    }

    private ParsedBlock convertFor(ForStmt fs) {
        String header = "";
        if (fs.getInitialization() != null && fs.getInitialization().size() > 0) {
            header += fs.getInitialization().toString();
        }
        if (fs.getCompare().isPresent()) {
            header += "; " + fs.getCompare().get().toString();
        }
        if (fs.getUpdate() != null && fs.getUpdate().size() > 0) {
            header += "; " + fs.getUpdate().toString();
        }
        String opcode = "for";
        if (fs.getInitialization() != null && fs.getInitialization().size() == 1
                && fs.getCompare().isPresent()
                && fs.getUpdate() != null && fs.getUpdate().size() == 1) {
            String init = fs.getInitialization().get(0).toString();
            if (init.matches(".*int\\s+\\w+\\s*=\\s*0.*")) {
                opcode = "repeat";
            }
        }
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode(opcode);
        block.setDisplayName(opcode);
        block.setOriginalCode(fs.toString());
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        if ("repeat".equals(opcode) && fs.getCompare().isPresent()) {
            String cmp = fs.getCompare().get().toString();
            Matcher m = Pattern.compile("<\\s*(.+)").matcher(cmp);
            if (m.find()) {
                block.addParameter("times", m.group(1).trim());
            } else {
                block.addParameter("condition", header);
            }
        } else {
            block.addParameter("header", header);
            block.addParameter("condition", header);
        }
        addBodyChildren(block, fs.getBody(), false);
        return block;
    }

    private ParsedBlock convertForEach(ForEachStmt fes) {
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode("for");
        block.setDisplayName("for-each");
        block.setOriginalCode(fes.toString());
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        block.addParameter("header", fes.getVariable().toString() + " : " + fes.getIterable().toString());
        block.addParameter("condition", fes.getVariable().toString() + " : " + fes.getIterable().toString());
        addBodyChildren(block, fes.getBody(), false);
        return block;
    }

    private void addBodyChildren(ParsedBlock parent, Statement body, boolean elseBranch) {
        if (body == null) return;
        if (body instanceof BlockStmt) {
            for (Statement inner : ((BlockStmt) body).getStatements()) {
                ParsedBlock child = convertStatement(inner);
                if (child != null) {
                    if (elseBranch) child.addParameter("_branch", "else");
                    parent.addChild(child);
                }
            }
        } else {
            ParsedBlock child = convertStatement(body);
            if (child != null) {
                if (elseBranch) child.addParameter("_branch", "else");
                parent.addChild(child);
            }
        }
    }

    private ParsedBlock convertExpression(Expression expr, String original) {
        if (expr == null) return matchByRegistry(original);

        if (expr instanceof VariableDeclarationExpr) {
            VariableDeclarationExpr vde = (VariableDeclarationExpr) expr;
            String typeStr = vde.getElementType().asString();
            if (isComponentType(typeStr)) {
                return matchByRegistry(original);
            }
            for (VariableDeclarator vd : vde.getVariables()) {
                String opcode = pickSetVarOpcode(typeStr, vd);
                ParsedBlock b = simpleBlock(opcode, original);
                b.addParameter("variable", vd.getNameAsString());
                if (vd.getInitializer().isPresent()) {
                    b.addParameter("value", stripQuotes(vd.getInitializer().get().toString()));
                }
                return b;
            }
        }
        if (expr instanceof AssignExpr) {
            AssignExpr ae = (AssignExpr) expr;
            String val = ae.getValue().toString();
            String opcode = "setVarString";
            if ("true".equals(val) || "false".equals(val)) opcode = "setVarBoolean";
            else {
                try { Double.parseDouble(val); opcode = "setVarInt"; } catch (Exception e) {}
            }
            if (val.startsWith("new ")) {
                return matchByRegistry(original);
            }
            ParsedBlock b = simpleBlock(opcode, original);
            b.addParameter("variable", ae.getTarget().toString());
            b.addParameter("value", stripQuotes(val));
            return b;
        }
        if (expr instanceof MethodCallExpr) {
            MethodCallExpr mc = (MethodCallExpr) expr;
            String name = mc.getNameAsString();
            String scope = "";
            if (mc.getScope().isPresent()) {
                scope = mc.getScope().get().toString();
            }

            if ("show".equals(name) && scope.indexOf("makeText") >= 0) {
                ParsedBlock b = simpleBlock("doToast", original);
                String msg = extractQuotedOrArg(original, 0);
                if (msg.length() == 0) msg = extractQuotedOrArg(original, 1);
                b.addParameter("message", msg);
                return b;
            }
            if ("makeText".equals(name)) {
                ParsedBlock b = simpleBlock("doToast", original);
                if (mc.getArguments().size() >= 2) {
                    b.addParameter("message", stripQuotes(mc.getArguments().get(1).toString()));
                } else {
                    b.addParameter("message", extractQuotedOrArg(original, 0));
                }
                return b;
            }

            if ("writeFile".equals(name)) {
                ParsedBlock b = simpleBlock("fileutilwrite", original);
                if (mc.getArguments().size() >= 2) {
                    b.addParameter("path", stripQuotes(mc.getArguments().get(0).toString()));
                    b.addParameter("data", stripQuotes(mc.getArguments().get(1).toString()));
                }
                return b;
            }
            if ("readFile".equals(name)) {
                ParsedBlock b = simpleBlock("fileutilread", original);
                if (mc.getArguments().size() >= 1) {
                    b.addParameter("path", stripQuotes(mc.getArguments().get(0).toString()));
                }
                return b;
            }
            if ("isExistFile".equals(name)) {
                ParsedBlock b = simpleBlock("fileutilisexist", original);
                if (mc.getArguments().size() >= 1) {
                    b.addParameter("path", stripQuotes(mc.getArguments().get(0).toString()));
                }
                return b;
            }

            if ("setText".equals(name) || "setTextColor".equals(name) || "setTextSize".equals(name)
                    || "setVisibility".equals(name) || "setEnabled".equals(name)
                    || "setBackgroundColor".equals(name) || "setChecked".equals(name)
                    || "requestFocus".equals(name) || "setHint".equals(name)
                    || "setImageResource".equals(name)) {
                String opcode = name;
                if ("setVisibility".equals(name)) opcode = "setVisible";
                if ("setEnabled".equals(name)) opcode = "setEnable";
                if ("setBackgroundColor".equals(name)) opcode = "setBgColor";
                ParsedBlock b = simpleBlock(opcode, original);
                b.addParameter("view", scope);
                if (mc.getArguments().size() >= 1) {
                    String arg = stripQuotes(mc.getArguments().get(0).toString());
                    if ("setText".equals(name)) b.addParameter("text", arg);
                    else if ("setTextColor".equals(name) || "setBackgroundColor".equals(name)) b.addParameter("color", arg);
                    else if ("setTextSize".equals(name)) b.addParameter("size", arg);
                    else if ("setVisibility".equals(name)) b.addParameter("visibility", arg);
                    else if ("setEnabled".equals(name) || "setChecked".equals(name)) b.addParameter("enabled", arg);
                    else if ("setHint".equals(name)) b.addParameter("hint", arg);
                    else if ("setImageResource".equals(name)) b.addParameter("res", arg);
                    else b.addParameter("value", arg);
                }
                return b;
            }

            if ("putExtra".equals(name)) {
                ParsedBlock b = simpleBlock("intentPutExtra", original);
                b.addParameter("intent", scope);
                if (mc.getArguments().size() >= 2) {
                    b.addParameter("key", stripQuotes(mc.getArguments().get(0).toString()));
                    b.addParameter("value", stripQuotes(mc.getArguments().get(1).toString()));
                }
                return b;
            }
            if ("setClass".equals(name)) {
                ParsedBlock b = simpleBlock("intentSetScreen", original);
                b.addParameter("intent", scope);
                if (mc.getArguments().size() >= 2) {
                    b.addParameter("activity", stripQuotes(mc.getArguments().get(1).toString()));
                }
                return b;
            }
            if ("setAction".equals(name) || "setData".equals(name) || "setFlags".equals(name)) {
                String op = "intentSetAction";
                if ("setData".equals(name)) op = "intentSetData";
                if ("setFlags".equals(name)) op = "intentSetFlags";
                ParsedBlock b = simpleBlock(op, original);
                b.addParameter("intent", scope);
                if (mc.getArguments().size() >= 1) {
                    b.addParameter("action", stripQuotes(mc.getArguments().get(0).toString()));
                }
                return b;
            }
            if ("startActivity".equals(name)) {
                ParsedBlock b = simpleBlock("startActivity", original);
                if (mc.getArguments().size() >= 1) {
                    b.addParameter("intent", stripQuotes(mc.getArguments().get(0).toString()));
                }
                return b;
            }
            if ("loadUrl".equals(name)) {
                ParsedBlock b = simpleBlock("webViewLoadUrl", original);
                b.addParameter("webview", scope);
                if (mc.getArguments().size() >= 1) {
                    b.addParameter("url", stripQuotes(mc.getArguments().get(0).toString()));
                }
                return b;
            }
        }
        return matchByRegistry(original);
    }



    private boolean isComponentType(String typeStr) {
        if (typeStr == null) return false;
        String t = typeStr.trim();
        int lt = t.indexOf('<');
        if (lt > 0) t = t.substring(0, lt);
        t = t.trim();
        return "Intent".equals(t) || "ArrayList".equals(t) || "HashMap".equals(t)
                || "Map".equals(t) || "List".equals(t) || "File".equals(t)
                || "StringBuilder".equals(t) || "Bundle".equals(t)
                || "Uri".equals(t) || "Bitmap".equals(t) || "JSONObject".equals(t)
                || "JSONArray".equals(t) || "SharedPreferences".equals(t)
                || "Calendar".equals(t) || "Date".equals(t)
                || "ObjectAnimator".equals(t) || "MediaPlayer".equals(t)
                || "SoundPool".equals(t) || "Vibrator".equals(t)
                || "RequestNetwork".equals(t) || "FirebaseDatabase".equals(t);
    }

    private String pickSetVarOpcode(String typeStr, VariableDeclarator vd) {
        if (typeStr == null) typeStr = "";
        String t = typeStr.trim().toLowerCase();
        if (t.equals("boolean") || t.equals("bool")) return "setVarBoolean";
        if (t.equals("int") || t.equals("integer") || t.equals("double")
                || t.equals("float") || t.equals("long") || t.equals("number")) {
            return "setVarInt";
        }
        if (t.equals("string")) return "setVarString";
        if (vd != null && vd.getInitializer().isPresent()) {
            String val = vd.getInitializer().get().toString().trim();
            if ("true".equals(val) || "false".equals(val)) return "setVarBoolean";
            try {
                Double.parseDouble(val);
                return "setVarInt";
            } catch (Exception e) {
            }
        }
        return "setVarString";
    }

    private String stripQuotes(String value) {
        if (value == null) return "";
        value = value.trim();
        if (value.length() >= 2) {
            char c0 = value.charAt(0);
            char c1 = value.charAt(value.length() - 1);
            if ((c0 == '"' && c1 == '"') || (c0 == '\'' && c1 == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        if (value.startsWith("View.")) return value.substring(5);
        return value;
    }

    private String extractQuotedOrArg(String code, int argIndex) {
        if (code == null) return "";
        int from = 0;
        int found = 0;
        while (from < code.length()) {
            int q = code.indexOf('"', from);
            if (q < 0) break;
            int q2 = code.indexOf('"', q + 1);
            while (q2 > 0 && code.charAt(q2 - 1) == '\\') {
                q2 = code.indexOf('"', q2 + 1);
            }
            if (q2 < 0) break;
            if (found == argIndex) {
                return code.substring(q + 1, q2);
            }
            found++;
            from = q2 + 1;
        }
        return "";
    }

    private ParsedBlock simpleBlock(String opcode, String original) {
        ParsedBlock b = new ParsedBlock();
        b.setId(generateId());
        String op = KqOpcodeSource.normalize(opcode);
        b.setOpcode(op);
        b.setDisplayName(op);
        b.setOriginalCode(original == null ? "" : original);
        b.setMatched(true);
        b.setType(ParsedBlock.TYPE_BUILT_IN);
        return b;
    }


    private ParsedBlock matchByRegistry(String statement) {
        if (statement == null || statement.trim().length() == 0) return null;
        String trimmed = statement.trim();

        List all = registry.getAllBlocks();
        ParsedBlock bestFull = null;
        int bestFullLen = 0;
        ParsedBlock bestPartial = null;
        int bestPartialLen = 0;

        for (int i = 0; i < all.size(); i++) {
            BlockDefinition def = (BlockDefinition) all.get(i);
            if (def.isContainer()) continue;
            List patterns = def.getCompiledPatterns();
            for (int j = 0; j < patterns.size(); j++) {
                Pattern pattern = (Pattern) patterns.get(j);
                Matcher matcher = pattern.matcher(trimmed);
                if (matcher.find()) {
                    int len = matcher.group(0).length();
                    String matched = matcher.group(0).trim();
                    String stmtNoSemi = trimmed;
                    if (stmtNoSemi.endsWith(";")) {
                        stmtNoSemi = stmtNoSemi.substring(0, stmtNoSemi.length() - 1).trim();
                    }
                    boolean isFull = matched.equals(stmtNoSemi)
                            || matched.equals(trimmed)
                            || (matcher.start() == 0 && matcher.end() >= trimmed.length() - 1);

                    if (isFull && len > bestFullLen) {
                        bestFullLen = len;
                        bestFull = createMatchedBlock(def, statement, matcher);
                    } else if (!isFull && len > bestPartialLen) {
                        bestPartialLen = len;
                        bestPartial = createMatchedBlock(def, statement, matcher);
                    }
                }
            }
        }
        if (bestFull != null) return bestFull;
        if (bestPartial != null) return bestPartial;
        return createSourceBlock(statement);
    }

    private ParsedBlock createMatchedBlock(BlockDefinition def, String originalCode, Matcher matcher) {
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode(def.getOpcode());
        block.setDisplayName(def.getName());
        block.setOriginalCode(originalCode);
        block.setMatched(true);
        if (def.isContainer()) {
            block.setType(ParsedBlock.TYPE_CONTAINER);
        } else if (def.isCustom()) {
            block.setType(ParsedBlock.TYPE_CUSTOM);
        } else {
            block.setType(ParsedBlock.TYPE_BUILT_IN);
        }
        List paramNames = def.getParameterNames();
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = (String) paramNames.get(i);
            try {
                int groupIndex = i + 1;
                if (groupIndex <= matcher.groupCount()) {
                    String value = matcher.group(groupIndex);
                    if (value != null) {
                        block.addParameter(paramName, value.trim());
                    }
                }
            } catch (Exception e) {
            }
        }
        return block;
    }

    private ParsedBlock createSourceBlock(String code) {
        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode("addSourceDirectly");
        block.setDisplayName("Add Source Directly");
        block.setType(ParsedBlock.TYPE_SOURCE);
        block.setOriginalCode(code);
        block.setMatched(false);
        block.addParameter("code", code);
        return block;
    }


    private List parseWithRegexFallback(String javaCode) {
        String cleaned = removeComments(javaCode);
        List statements = splitStatements(cleaned);
        List rootBlocks = new ArrayList();
        for (int i = 0; i < statements.size(); i++) {
            String stmt = ((String) statements.get(i)).trim();
            if (stmt.length() == 0) continue;
            ParsedBlock block = matchStatementRegex(stmt);
            if (block != null) rootBlocks.add(block);
        }
        return rootBlocks;
    }

    private ParsedBlock matchStatementRegex(String statement) {
        if (statement == null || statement.trim().length() == 0) return null;
        String trimmed = statement.trim();
        if (trimmed.indexOf('{') >= 0 || looksLikeControlKeyword(trimmed)) {
            ParsedBlock container = tryMatchContainerRegex(trimmed);
            if (container != null) return container;
        }
        return matchByRegistry(statement);
    }

    private boolean looksLikeControlKeyword(String s) {
        String t = s.trim();
        return t.startsWith("if") || t.startsWith("while") || t.startsWith("for")
                || t.startsWith("do") || t.startsWith("try") || t.startsWith("switch");
    }

    private ParsedBlock tryMatchContainerRegex(String statement) {
        String trimmed = statement.trim();
        String opcode = null;
        String condition = null;

        if (trimmed.startsWith("if") && looksLikeControl(trimmed, "if")) {
            boolean hasElse = hasElseBranch(trimmed);
            opcode = hasElse ? "ifElse" : "if";
            int p = indexOfKeywordParen(trimmed, "if");
            condition = extractParenContent(trimmed, p);
        } else if (trimmed.startsWith("while") && looksLikeControl(trimmed, "while")) {
            int p = indexOfKeywordParen(trimmed, "while");
            condition = extractParenContent(trimmed, p);
            if (condition != null && "true".equals(condition.trim())) {
                opcode = "forever";
                condition = null;
            } else {
                opcode = "while";
            }
        } else if (trimmed.startsWith("for") && looksLikeControl(trimmed, "for")) {
            int p = indexOfKeywordParen(trimmed, "for");
            condition = extractParenContent(trimmed, p);
            opcode = "for";
            if (condition != null && condition.matches("\\s*int\\s+\\w+\\s*=\\s*0\\s*;.*")) {
                opcode = "repeat";
            }
        } else if (trimmed.startsWith("do") && trimmed.indexOf("while") > 0) {
            opcode = "while";
            int w = trimmed.lastIndexOf("while");
            int p = trimmed.indexOf('(', w);
            if (p > 0) condition = extractParenContent(trimmed, p);
        }

        if (opcode == null) return null;

        ParsedBlock block = new ParsedBlock();
        block.setId(generateId());
        block.setOpcode(opcode);
        block.setDisplayName(opcode);
        block.setOriginalCode(statement);
        block.setMatched(true);
        block.setType(ParsedBlock.TYPE_CONTAINER);
        if (condition != null) block.addParameter("condition", condition.trim());

        String body = extractBraceBody(statement);
        if (body != null && body.trim().length() > 0) {
            List bodyStmts = splitStatements(body);
            for (int k = 0; k < bodyStmts.size(); k++) {
                String bs = ((String) bodyStmts.get(k)).trim();
                if (bs.length() == 0) continue;
                ParsedBlock child = matchStatementRegex(bs);
                if (child != null) block.addChild(child);
            }
        }

        if ("ifElse".equals(opcode) || "if".equals(opcode)) {
            List elseParts = extractAllElseBodies(statement);
            for (int e = 0; e < elseParts.size(); e++) {
                String elseBody = (String) elseParts.get(e);
                if (elseBody == null || elseBody.trim().length() == 0) continue;
                String eb = elseBody.trim();
                if (eb.startsWith("if") && looksLikeControl(eb, "if")) {
                    ParsedBlock elseIf = matchStatementRegex(eb);
                    if (elseIf != null) {
                        elseIf.addParameter("_branch", "else");
                        block.addChild(elseIf);
                        block.setOpcode("ifElse");
                    }
                } else {
                    List elseStmts = splitStatements(elseBody);
                    for (int k = 0; k < elseStmts.size(); k++) {
                        String bs = ((String) elseStmts.get(k)).trim();
                        if (bs.length() == 0) continue;
                        ParsedBlock child = matchStatementRegex(bs);
                        if (child != null) {
                            child.addParameter("_branch", "else");
                            block.addChild(child);
                        }
                    }
                    block.setOpcode("ifElse");
                }
            }
        }
        return block;
    }


    private boolean looksLikeControl(String s, String keyword) {
        int p = indexOfKeywordParen(s, keyword);
        if (p < 0) return false;
        int close = findMatchingParen(s, p);
        if (close < 0) return false;
        String after = s.substring(close + 1).trim();
        return after.startsWith("{") || after.startsWith("if") || after.length() == 0 || after.startsWith(";");
    }

    private boolean hasElseBranch(String s) {
        int idx = 0;
        while (true) {
            idx = s.indexOf("else", idx);
            if (idx < 0) return false;
            if (idx > 0 && Character.isJavaIdentifierPart(s.charAt(idx - 1))) {
                idx += 4; continue;
            }
            if (idx + 4 < s.length() && Character.isJavaIdentifierPart(s.charAt(idx + 4))) {
                idx += 4; continue;
            }
            return true;
        }
    }

    private List extractAllElseBodies(String code) {
        List bodies = new ArrayList();
        int searchFrom = 0;
        while (true) {
            int idx = code.indexOf("else", searchFrom);
            if (idx < 0) break;
            if (idx > 0 && Character.isJavaIdentifierPart(code.charAt(idx - 1))) {
                searchFrom = idx + 4; continue;
            }
            String after = code.substring(idx + 4).trim();
            if (after.startsWith("{")) {
                String body = extractBraceBody(after);
                if (body != null) bodies.add(body);
                int braceStart = code.indexOf('{', idx);
                if (braceStart < 0) break;
                int depth = 0;
                int end = braceStart;
                for (int i = braceStart; i < code.length(); i++) {
                    char c = code.charAt(i);
                    if (c == '{') depth++;
                    else if (c == '}') {
                        depth--;
                        if (depth == 0) { end = i; break; }
                    }
                }
                searchFrom = end + 1;
            } else if (after.startsWith("if")) {
                bodies.add(after);
                break;
            } else {
                int semi = after.indexOf(';');
                if (semi >= 0) {
                    bodies.add(after.substring(0, semi + 1));
                    searchFrom = idx + 4 + semi + 1;
                } else {
                    bodies.add(after);
                    break;
                }
            }
        }
        return bodies;
    }

    private int indexOfKeywordParen(String s, String keyword) {
        int k = s.indexOf(keyword);
        if (k < 0) return -1;
        int i = k + keyword.length();
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) i++;
        if (i < s.length() && s.charAt(i) == '(') return i;
        return -1;
    }

    private String extractParenContent(String code, int openIdx) {
        if (openIdx < 0 || openIdx >= code.length() || code.charAt(openIdx) != '(') return null;
        int close = findMatchingParen(code, openIdx);
        if (close < 0) return null;
        return code.substring(openIdx + 1, close);
    }

    private int findMatchingParen(String code, int openIdx) {
        int depth = 0;
        boolean inString = false;
        char stringChar = 0;
        for (int i = openIdx; i < code.length(); i++) {
            char c = code.charAt(i);
            if (!inString && (c == '"' || c == '\'')) {
                inString = true; stringChar = c; continue;
            }
            if (inString) {
                if (c == stringChar && (i == 0 || code.charAt(i - 1) != '\\')) inString = false;
                continue;
            }
            if (c == '(') depth++;
            else if (c == ')') {
                depth--;
                if (depth == 0) return i;
            }
        }
        return -1;
    }

    private String extractBraceBody(String code) {
        int start = code.indexOf('{');
        if (start < 0) return null;
        int depth = 0;
        boolean inString = false;
        char stringChar = 0;
        for (int i = start; i < code.length(); i++) {
            char c = code.charAt(i);
            if (!inString && (c == '"' || c == '\'')) {
                inString = true; stringChar = c; continue;
            }
            if (inString) {
                if (c == stringChar && (i == 0 || code.charAt(i - 1) != '\\')) inString = false;
                continue;
            }
            if (c == '{') depth++;
            else if (c == '}') {
                depth--;
                if (depth == 0) return code.substring(start + 1, i);
            }
        }
        return null;
    }

    private List splitStatements(String code) {
        List result = new ArrayList();
        StringBuffer current = new StringBuffer();
        int braceDepth = 0;
        int parenDepth = 0;
        boolean inString = false;
        char stringChar = 0;
        int len = code.length();
        for (int i = 0; i < len; i++) {
            char c = code.charAt(i);
            if (!inString && (c == '"' || c == '\'')) {
                inString = true; stringChar = c; current.append(c); continue;
            }
            if (inString) {
                current.append(c);
                if (c == stringChar && i > 0 && code.charAt(i - 1) != '\\') inString = false;
                continue;
            }
            if (c == '{') { braceDepth++; current.append(c); }
            else if (c == '}') {
                braceDepth--; current.append(c);
                if (braceDepth == 0 && parenDepth == 0) {
                    int j = i + 1;
                    while (j < len && Character.isWhitespace(code.charAt(j))) j++;
                    if (j < len && isContinuationKeyword(code, j)) continue;
                    String stmt = current.toString().trim();
                    if (stmt.length() > 0) result.add(stmt);
                    current = new StringBuffer();
                }
            } else if (c == '(') { parenDepth++; current.append(c); }
            else if (c == ')') { parenDepth--; current.append(c); }
            else if (c == ';' && braceDepth == 0 && parenDepth == 0) {
                current.append(c);
                String stmt = current.toString().trim();
                if (stmt.length() > 0) result.add(stmt);
                current = new StringBuffer();
            } else {
                current.append(c);
            }
        }
        String remaining = current.toString().trim();
        if (remaining.length() > 0) result.add(remaining);
        return result;
    }

    private boolean isContinuationKeyword(String code, int idx) {
        return startsWithKeyword(code, idx, "else")
                || startsWithKeyword(code, idx, "catch")
                || startsWithKeyword(code, idx, "finally")
                || startsWithKeyword(code, idx, "while");
    }

    private boolean startsWithKeyword(String code, int idx, String kw) {
        int n = kw.length();
        if (idx + n > code.length()) return false;
        for (int k = 0; k < n; k++) {
            if (code.charAt(idx + k) != kw.charAt(k)) return false;
        }
        if (idx + n < code.length() && Character.isJavaIdentifierPart(code.charAt(idx + n))) return false;
        return true;
    }

    private String removeComments(String code) {
        StringBuffer sb = new StringBuffer();
        int len = code.length();
        boolean inString = false;
        char stringChar = 0;
        int i = 0;
        while (i < len) {
            char c = code.charAt(i);
            if (!inString && (c == '"' || c == '\'')) {
                inString = true; stringChar = c; sb.append(c); i++; continue;
            }
            if (inString) {
                sb.append(c);
                if (c == stringChar && i > 0 && code.charAt(i - 1) != '\\') inString = false;
                i++; continue;
            }
            if (c == '/' && i + 1 < len && code.charAt(i + 1) == '/') {
                i += 2;
                while (i < len && code.charAt(i) != '\n') i++;
                continue;
            }
            if (c == '/' && i + 1 < len && code.charAt(i + 1) == '*') {
                i += 2;
                while (i + 1 < len && !(code.charAt(i) == '*' && code.charAt(i + 1) == '/')) i++;
                i += 2; continue;
            }
            sb.append(c); i++;
        }
        return sb.toString();
    }

    private List flattenForConversion(List roots) {
        List flat = new ArrayList();
        for (int i = 0; i < roots.size(); i++) {
            addFlattened((ParsedBlock) roots.get(i), flat);
        }
        return flat;
    }

    private void addFlattened(ParsedBlock block, List flat) {
        if (block == null) return;
        flat.add(block);
        List children = block.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                addFlattened((ParsedBlock) children.get(i), flat);
            }
        }
    }

    private String generateId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return "block_" + uuid.substring(0, 12);
    }

    public void registerCustomBlock(BlockDefinition definition) {
        registry.registerCustom(definition);
    }

    public BlockDefinitionRegistry getRegistry() {
        return registry;
    }
}
