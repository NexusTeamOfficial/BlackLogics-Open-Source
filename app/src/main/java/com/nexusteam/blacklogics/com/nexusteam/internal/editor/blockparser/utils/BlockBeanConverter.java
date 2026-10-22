package com.nexusteam.internal.editor.blockparser.utils;

import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.editor.blockparser.model.ParsedBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockBeanConverter {

    private static final Map OPCODE_MAP = new HashMap();
    static {
        OPCODE_MAP.put("setVisibility", "setVisible");
        OPCODE_MAP.put("setEnabled", "setEnable");
        OPCODE_MAP.put("setBackgroundColor", "setBgColor");
        OPCODE_MAP.put("toast", "doToast");
        OPCODE_MAP.put("showToast", "doToast");
        OPCODE_MAP.put("finish", "finishActivity");
        OPCODE_MAP.put("intentSetClass", "intentSetScreen");
        OPCODE_MAP.put("setVar", "setVarString");
        OPCODE_MAP.put("setVarString", "setVarString");
        OPCODE_MAP.put("setVarInt", "setVarInt");
        OPCODE_MAP.put("setVarBoolean", "setVarBoolean");
        OPCODE_MAP.put("if", "if");
        OPCODE_MAP.put("ifElse", "ifElse");
        OPCODE_MAP.put("doToast", "doToast");
        OPCODE_MAP.put("setText", "setText");
        OPCODE_MAP.put("setTextColor", "setTextColor");
        OPCODE_MAP.put("setVisible", "setVisible");
        OPCODE_MAP.put("setEnable", "setEnable");
        OPCODE_MAP.put("addSourceDirectly", "addSourceDirectly");
    }

    private int nextId = 1;

    public List convertToBlockBeans(List parsedBlocks) {
        return convert(parsedBlocks);
    }

    public List convert(List parsedBlocks) {
        nextId = 1;
        List result = new ArrayList();
        if (parsedBlocks == null || parsedBlocks.size() == 0) {
            return result;
        }

        Map idMap = new HashMap();
        Map beanById = new HashMap();
        Map parentOf = new HashMap();

        Map isChild = new HashMap();
        for (int i = 0; i < parsedBlocks.size(); i++) {
            ParsedBlock pb = (ParsedBlock) parsedBlocks.get(i);
            List ch = pb.getChildren();
            if (ch != null) {
                for (int c = 0; c < ch.size(); c++) {
                    ParsedBlock child = (ParsedBlock) ch.get(c);
                    isChild.put(child.getId(), Boolean.TRUE);
                    parentOf.put(child.getId(), pb);
                }
            }
        }

        List roots = new ArrayList();
        for (int i = 0; i < parsedBlocks.size(); i++) {
            ParsedBlock pb = (ParsedBlock) parsedBlocks.get(i);
            if (isChild.get(pb.getId()) == null) {
                roots.add(pb);
            }
        }

        for (int i = 0; i < roots.size(); i++) {
            convertTree((ParsedBlock) roots.get(i), result, idMap, beanById);
        }

        for (int i = 0; i < roots.size() - 1; i++) {
            ParsedBlock cur = (ParsedBlock) roots.get(i);
            ParsedBlock nxt = (ParsedBlock) roots.get(i + 1);
            BlockBean curBean = (BlockBean) beanById.get(idMap.get(cur.getId()));
            BlockBean nxtBean = (BlockBean) beanById.get(idMap.get(nxt.getId()));
            if (curBean != null && nxtBean != null) {
                curBean.nextBlock = Integer.parseInt(nxtBean.id);
            }
        }

        for (int i = 0; i < roots.size(); i++) {
            wireContainer((ParsedBlock) roots.get(i), idMap, beanById);
        }

        return result;
    }

    private void convertTree(ParsedBlock pb, List result, Map idMap, Map beanById) {
        if (pb == null) return;

        BlockBean condBean = null;
        if (pb.isContainer() && ("if".equals(pb.getOpcode()) || "ifElse".equals(pb.getOpcode()))) {
            String condition = pb.getParameter("condition");
            if (condition != null && condition.trim().length() > 0) {
                List extraVars = new ArrayList();
                condBean = buildConditionBlock(condition.trim(), extraVars);
                for (int ev = 0; ev < extraVars.size(); ev++) {
                    BlockBean gv = (BlockBean) extraVars.get(ev);
                    String gid = String.valueOf(nextId++);
                    gv.id = gid;
                    beanById.put(gid, gv);
                    result.add(gv);
                    if (condBean != null && condBean.parameters != null) {
                        for (int pi = 0; pi < condBean.parameters.size(); pi++) {
                            String pv = (String) condBean.parameters.get(pi);
                            if (pv != null && pv.startsWith("@TEMP_" + ev)) {
                                condBean.parameters.set(pi, "@" + gid);
                            }
                        }
                    }
                }
                if (condBean != null) {
                    if ("_bareVar".equals(condBean.opCode)) {
                        String ref = condBean.parameters.size() > 0 ? (String) condBean.parameters.get(0) : null;
                        if (ref != null && ref.startsWith("@")) {
                            condBean.id = ref.substring(1);
                            condBean.opCode = "getVar";
                        } else {
                            condBean = null;
                        }
                    } else {
                        String cid = String.valueOf(nextId++);
                        condBean.id = cid;
                        idMap.put("cond_" + pb.getId(), cid);
                        beanById.put(cid, condBean);
                        result.add(condBean);
                    }
                }
            }
        }

        String numId = String.valueOf(nextId++);
        idMap.put(pb.getId(), numId);
        BlockBean bean = convertOne(pb, numId);

        if (condBean != null && bean.parameters != null) {
            if (bean.parameters.size() == 0) {
                bean.parameters.add("@" + condBean.id);
            } else {
                bean.parameters.set(0, "@" + condBean.id);
            }
        }

        result.add(bean);
        beanById.put(numId, bean);

        List children = pb.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                convertTree((ParsedBlock) children.get(i), result, idMap, beanById);
            }
        }
    }

    private void wireContainer(ParsedBlock pb, Map idMap, Map beanById) {
        if (pb == null) return;
        String myNum = (String) idMap.get(pb.getId());
        BlockBean myBean = (BlockBean) beanById.get(myNum);
        if (myBean == null) return;

        List children = pb.getChildren();
        if (pb.isContainer() && children != null && children.size() > 0) {
            ParsedBlock firstIf = null;
            ParsedBlock firstElse = null;
            List ifBranch = new ArrayList();
            List elseBranch = new ArrayList();

            for (int c = 0; c < children.size(); c++) {
                ParsedBlock ch = (ParsedBlock) children.get(c);
                String branch = ch.getParameter("_branch");
                if ("else".equals(branch)) {
                    elseBranch.add(ch);
                    if (firstElse == null) firstElse = ch;
                } else {
                    ifBranch.add(ch);
                    if (firstIf == null) firstIf = ch;
                }
            }

            if (firstIf != null) {
                String childNum = (String) idMap.get(firstIf.getId());
                if (childNum != null) {
                    myBean.subStack1 = Integer.parseInt(childNum);
                }
            }
            if (firstElse != null) {
                myBean.opCode = "ifElse";
                myBean.type = "e";
                String childNum = (String) idMap.get(firstElse.getId());
                if (childNum != null) {
                    myBean.subStack2 = Integer.parseInt(childNum);
                }
            }

            chainList(ifBranch, idMap, beanById);
            chainList(elseBranch, idMap, beanById);
        }

        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                wireContainer((ParsedBlock) children.get(i), idMap, beanById);
            }
        }
    }

    private void chainList(List branch, Map idMap, Map beanById) {
        for (int i = 0; i < branch.size() - 1; i++) {
            ParsedBlock cur = (ParsedBlock) branch.get(i);
            ParsedBlock nxt = (ParsedBlock) branch.get(i + 1);
            BlockBean curBean = (BlockBean) beanById.get(idMap.get(cur.getId()));
            BlockBean nxtBean = (BlockBean) beanById.get(idMap.get(nxt.getId()));
            if (curBean != null && nxtBean != null) {
                curBean.nextBlock = Integer.parseInt(nxtBean.id);
            }
        }
        if (branch.size() > 0) {
            ParsedBlock last = (ParsedBlock) branch.get(branch.size() - 1);
            BlockBean lastBean = (BlockBean) beanById.get(idMap.get(last.getId()));
            if (lastBean != null) {
                lastBean.nextBlock = -1;
            }
        }
    }

    private BlockBean convertOne(ParsedBlock pb, String numId) {
        BlockBean bean = new BlockBean();
        bean.id = numId;
        String realOpcode = mapOpcode(pb.getOpcode());
        bean.opCode = realOpcode;
        bean.type = resolveType(realOpcode, pb);
        String kqLabel = KqOpcodeSource.label(realOpcode);
        bean.spec = (kqLabel != null && kqLabel.length() > 0) ? kqLabel : "";
        bean.parameters = new ArrayList();
        bean.subStack1 = -1;
        bean.subStack2 = -1;
        bean.nextBlock = -1;
        bean.isCustomBlock = false;

        if (pb.isSourceBlock() || "addSourceDirectly".equals(realOpcode)) {
            String code = pb.getParameter("code");
            if (code == null) code = pb.getOriginalCode();
            if (code == null) code = "";
            bean.parameters.add(code);
            return bean;
        }

        if ("if".equals(realOpcode) || "ifElse".equals(realOpcode)) {
            String cond = pb.getParameter("condition");
            if (cond != null) {
                bean.parameters.add(cond);
            }
        } else if ("doToast".equals(realOpcode)) {
            String msg = pb.getParameter("message");
            if (msg == null || msg.length() == 0) {
                msg = extractToastMessage(pb.getOriginalCode());
            }
            bean.parameters.add(cleanParam(msg));
        } else if ("setVar".equals(realOpcode) || "setVarString".equals(realOpcode) || "setVarInt".equals(realOpcode) || "setVarBoolean".equals(realOpcode)) {
            String var = pb.getParameter("variable");
            if (var == null) var = pb.getParameter("name");
            String val = cleanParam(pb.getParameter("value"));
            bean.parameters.add(cleanParam(var));
            bean.parameters.add(val);
            if ("setVar".equals(realOpcode)) {
                if ("true".equals(val) || "false".equals(val)) {
                    bean.opCode = "setVarBoolean";
                } else {
                    boolean isNum = false;
                    try {
                        Double.parseDouble(val);
                        isNum = true;
                    } catch (Exception e) {}
                    if (isNum) bean.opCode = "setVarInt";
                    else bean.opCode = "setVarString";
                }
            }
        } else if ("setText".equals(realOpcode) || "setTextColor".equals(realOpcode)
                || "setTextSize".equals(realOpcode) || "setEnable".equals(realOpcode)
                || "setEnabled".equals(realOpcode) || "setBgColor".equals(realOpcode)
                || "setBackgroundColor".equals(realOpcode) || "setChecked".equals(realOpcode)
                || "setHint".equals(realOpcode) || "setImageResource".equals(realOpcode)
                || "setImage".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("view")));
            String p2 = pb.getParameter("text");
            if (p2 == null) p2 = pb.getParameter("color");
            if (p2 == null) p2 = pb.getParameter("size");
            if (p2 == null) p2 = pb.getParameter("enabled");
            if (p2 == null) p2 = pb.getParameter("checked");
            if (p2 == null) p2 = pb.getParameter("hint");
            if (p2 == null) p2 = pb.getParameter("res");
            if (p2 == null) p2 = pb.getParameter("value");
            bean.parameters.add(cleanParam(p2));
            applyViewVariety(bean, pb.getParameter("view"));
        } else if ("setVisible".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("view")));
            String vis = pb.getParameter("visibility");
            if (vis == null) vis = "";
            vis = vis.trim();
            if (vis.contains("VISIBLE")) vis = "VISIBLE";
            else if (vis.contains("GONE")) vis = "GONE";
            else if (vis.contains("INVISIBLE")) vis = "INVISIBLE";
            bean.parameters.add(vis);
            applyViewVariety(bean, pb.getParameter("view"));
        } else if ("requestFocus".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("view")));
            applyViewVariety(bean, pb.getParameter("view"));
        } else if ("stringContains".equals(realOpcode) || "stringEquals".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("left")));
            bean.parameters.add(cleanParam(pb.getParameter("right")));
        } else if ("fileutilwrite".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("path")));
            bean.parameters.add(cleanParam(pb.getParameter("data")));
        } else if ("fileutilread".equals(realOpcode) || "fileutilisexist".equals(realOpcode)
                || "fileutildelete".equals(realOpcode) || "fileutilmakedir".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("path")));
        } else if ("fileutilcopy".equals(realOpcode) || "fileutilmove".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("src")));
            bean.parameters.add(cleanParam(pb.getParameter("dest")));
        } else if ("intentPutExtra".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("intent")));
            bean.parameters.add(cleanParam(pb.getParameter("key")));
            bean.parameters.add(cleanParam(pb.getParameter("value")));
        } else if ("intentSetClass".equals(realOpcode) || "intentSetScreen".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("intent")));
            bean.parameters.add(cleanParam(pb.getParameter("activity")));
        } else if ("intentSetAction".equals(realOpcode) || "intentSetData".equals(realOpcode)
                || "intentSetFlags".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("intent")));
            String p2 = pb.getParameter("action");
            if (p2 == null) p2 = pb.getParameter("data");
            if (p2 == null) p2 = pb.getParameter("flags");
            bean.parameters.add(cleanParam(p2));
        } else if ("startActivity".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("intent")));
        } else if ("webViewLoadUrl".equals(realOpcode)) {
            bean.parameters.add(cleanParam(pb.getParameter("webview")));
            bean.parameters.add(cleanParam(pb.getParameter("url")));
            applyViewVariety(bean, pb.getParameter("webview"));
        } else {
            Map all = pb.getParameters();
            if (all != null) {
                String[] keys = new String[]{"view", "text", "message", "name", "value",
                        "visibility", "left", "right", "condition", "code"};
                for (int i = 0; i < keys.length; i++) {
                    if (all.containsKey(keys[i]) && !"_branch".equals(keys[i])) {
                        bean.parameters.add(cleanParam((String) all.get(keys[i])));
                    }
                }
            }
        }
        return bean;
    }

    private BlockBean buildConditionBlock(String condition, List extraVars) {
        if (condition == null || condition.length() == 0) return null;
        BlockBean bean = new BlockBean();
        bean.spec = "";
        bean.parameters = new ArrayList();
        bean.subStack1 = -1;
        bean.subStack2 = -1;
        bean.nextBlock = -1;
        bean.type = "b";
        bean.isCustomBlock = false;

        if (condition.indexOf(".equals(") >= 0) {
            int dot = condition.indexOf(".equals(");
            String left = condition.substring(0, dot).trim();
            int open = condition.indexOf('(', dot);
            int close = condition.lastIndexOf(')');
            String right = open >= 0 && close > open ? condition.substring(open + 1, close).trim() : "";
            bean.opCode = "stringEquals";
            bean.parameters.add(varOrLiteral(left, "s", extraVars));
            bean.parameters.add(varOrLiteral(right, "s", extraVars));
            return bean;
        }
        if (condition.indexOf(".contains(") >= 0) {
            int dot = condition.indexOf(".contains(");
            String left = condition.substring(0, dot).trim();
            int open = condition.indexOf('(', dot);
            int close = condition.lastIndexOf(')');
            String right = open >= 0 && close > open ? condition.substring(open + 1, close).trim() : "";
            bean.opCode = "stringContains";
            bean.parameters.add(varOrLiteral(left, "s", extraVars));
            bean.parameters.add(varOrLiteral(right, "s", extraVars));
            return bean;
        }

        String[] ops = new String[]{">=", "<=", "!=", "==", ">", "<"};
        String[] opcodes = new String[]{">=", "<=", "!=", "=", ">", "<"};
        for (int i = 0; i < ops.length; i++) {
            int idx = condition.indexOf(ops[i]);
            if (idx > 0) {
                String left = condition.substring(0, idx).trim();
                String right = condition.substring(idx + ops[i].length()).trim();
                bean.opCode = opcodes[i];
                bean.parameters.add(varOrLiteral(left, "d", extraVars));
                bean.parameters.add(varOrLiteral(right, "d", extraVars));
                return bean;
            }
        }

        if ("true".equals(condition)) {
            bean.opCode = "true";
            return bean;
        }
        if ("false".equals(condition)) {
            bean.opCode = "false";
            return bean;
        }

        if (isIdentifier(condition)) {
            extraVars.add(makeGetVar(condition, "b"));
            bean.opCode = "_bareVar";
            bean.parameters.add("@TEMP_" + (extraVars.size() - 1));
            return bean;
        }
        return null;
    }

    private String varOrLiteral(String raw, String varType, List extraVars) {
        if (raw == null) return "";
        String v = raw.trim();
        if (v.length() >= 2 && ((v.startsWith("\"") && v.endsWith("\"")) || (v.startsWith("'") && v.endsWith("'")))) {
            return v.substring(1, v.length() - 1);
        }
        try {
            Double.parseDouble(v);
            return v;
        } catch (Exception e) {}
        if ("true".equals(v) || "false".equals(v)) return v;
        if (isIdentifier(v)) {
            int idx = extraVars.size();
            extraVars.add(makeGetVar(v, varType));
            return "@TEMP_" + idx;
        }
        return cleanParam(v);
    }

    private boolean isIdentifier(String s) {
        if (s == null || s.length() == 0) return false;
        char c0 = s.charAt(0);
        if (!(Character.isLetter(c0) || c0 == '_')) return false;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_')) return false;
        }
        return true;
    }

    private BlockBean makeGetVar(String varName, String type) {
        BlockBean gv = new BlockBean();
        gv.opCode = "getVar";
        gv.type = type == null ? "s" : type;
        gv.spec = "";
        gv.parameters = new ArrayList();
        gv.parameters.add(varName);
        gv.subStack1 = -1;
        gv.subStack2 = -1;
        gv.nextBlock = -1;
        gv.isCustomBlock = false;
        return gv;
    }

    private String cleanParam(String value) {
        if (value == null) return "";
        value = value.trim();
        if (value.length() >= 2) {
            if ((value.startsWith("\"") && value.endsWith("\""))
                    || (value.startsWith("'") && value.endsWith("'"))) {
                return value.substring(1, value.length() - 1);
            }
        }
        if (value.startsWith("View.")) {
            return value.substring(5);
        }
        return value;
    }

    private String mapOpcode(String internal) {
        if (internal == null) return "addSourceDirectly";
        Object mapped = OPCODE_MAP.get(internal);
        String candidate = mapped != null ? (String) mapped : internal;
        return KqOpcodeSource.normalize(candidate);
    }

    private String resolveType(String opcode, ParsedBlock pb) {
        if (pb.isSourceBlock() || "addSourceDirectly".equals(opcode)) return " ";
        if (pb.isContainer() || "if".equals(opcode) || "ifElse".equals(opcode)
                || "repeat".equals(opcode) || "forever".equals(opcode)
                || "while".equals(opcode) || "for".equals(opcode)) {
            if ("ifElse".equals(opcode)) return "e";
            return "c";
        }
        if ("true".equals(opcode) || "false".equals(opcode)
                || "stringEquals".equals(opcode) || "stringContains".equals(opcode)
                || "not".equals(opcode) || ">".equals(opcode) || "<".equals(opcode)
                || "=".equals(opcode) || "!=".equals(opcode)
                || ">=".equals(opcode) || "<=".equals(opcode)) {
            return "b";
        }
        return " ";
    }

    private String extractToastMessage(String code) {
        if (code == null) return "";
        int first = code.indexOf("makeText");
        if (first < 0) return code;
        int open = code.indexOf('(', first);
        if (open < 0) return code;
        int depth = 0;
        int comma1 = -1, comma2 = -1;
        for (int i = open; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') { depth--; if (depth == 0) break; }
            else if (c == ',' && depth == 1) {
                if (comma1 < 0) comma1 = i;
                else { comma2 = i; break; }
            }
        }
        if (comma1 > 0 && comma2 > comma1) {
            return code.substring(comma1 + 1, comma2).trim();
        }
        return code;
    }

    private void applyViewVariety(BlockBean bean, String viewId) {
        if (viewId == null || bean == null) return;
        String id = viewId.trim().toLowerCase();
        String typeName = null;
        if (id.startsWith("textview") || id.startsWith("tv")) typeName = "textview";
        else if (id.startsWith("edittext") || id.startsWith("et")) typeName = "edittext";
        else if (id.startsWith("button") || id.startsWith("btn")) typeName = "button";
        else if (id.startsWith("imageview") || id.startsWith("img")) typeName = "imageview";
        else if (id.startsWith("listview") || id.startsWith("lv")) typeName = "listview";
        else if (id.startsWith("spinner")) typeName = "spinner";
        else if (id.startsWith("checkbox") || id.startsWith("cb")) typeName = "checkbox";
        else if (id.startsWith("switch") || id.startsWith("sw")) typeName = "switch";
        else if (id.startsWith("seekbar") || id.startsWith("sb")) typeName = "seekbar";
        else if (id.startsWith("webview") || id.startsWith("wv")) typeName = "webview";
        else if (id.startsWith("progressbar") || id.startsWith("pb")) typeName = "progressbar";
        else if (id.startsWith("calendarview")) typeName = "calendarview";
        else if (id.startsWith("radiobutton") || id.startsWith("rb")) typeName = "radiobutton";
        else if (id.startsWith("linear") || id.startsWith("ll")) typeName = "linear";
        else if (id.startsWith("scrollview") || id.startsWith("sv")) typeName = "scrollview";
        else if (id.startsWith("recyclerview") || id.startsWith("rv")) typeName = "recyclerview";
        else if (id.startsWith("cardview")) typeName = "cardview";
        else if (id.startsWith("materialbutton")) typeName = "materialbutton";
        else if (id.startsWith("chip")) typeName = "chip";
        else if (id.startsWith("tab")) typeName = "tablayout";
        else if (id.startsWith("bottomnav")) typeName = "bottomnavigation";
        else if (id.startsWith("videoview")) typeName = "videoview";
        else if (id.startsWith("lottie")) typeName = "lottie";
        if (typeName != null) {
            bean.typeName = typeName;
        }
    }


}
