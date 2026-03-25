package com.nexusteam.blacklogics.logic.editor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.logic.editor.Block;
import com.besome.blacklogics.logic.editor.BlockBase;
import com.besome.blacklogics.logic.editor.DesignDataManager;
import com.besome.blacklogics.logic.editor.DefineSource;
import com.besome.blacklogics.logic.editor.LayoutUtil;
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import b.b.b.Qf;
import com.besome.blacklogics.logic.editor.VariableNameValidator;
import com.besome.blacklogics.project.ProjectDataHelper;
import com.besome.blacklogics.util.ProjectActivityManager;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddBlockPopup {
    
    private final Context context;
    private final String activityName;
    
    private VariableNameValidator booleanValidator;
    private VariableNameValidator numberValidator;
    private VariableNameValidator stringValidator;
    
    private AlertDialog dialog;
    
    public AddBlockPopup(Context c, String activityName) {
        this.context = c;
        this.activityName = activityName;
    }
    

    
    public void show() {
        final View view = LayoutUtil.inflate(context, R.layout.logic_popup_add_block1);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setTitle(context.getString(R.string.logic_popup_title_make_block));
        
        final ArrayList<Pair<String, String>> args = new ArrayList<>();
        
        final RelativeLayout blockArea = (RelativeLayout) view.findViewById(R.id.block_area);
        final LinearLayout removeArea = (LinearLayout) view.findViewById(R.id.remove_area);
        
        final Block previewBlock = new Block(
            context,
            0,
            "",
            " ",
            "definedFunc",
            new Object[]{-7711273}
        );
        blockArea.addView(previewBlock);
        
        setupValidators(view);
        setupInputs(view, blockArea, removeArea, previewBlock, args);
        
        builder.setNegativeButton(R.string.btn_cancel, null);
        builder.setPositiveButton(R.string.btn_accept, null);
        
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                setupAcceptButton(previewBlock, view);
            }
        });
        
        dialog.show();
    }
    
    private void setupValidators(View view) {




        

        TextInputLayout tiName = (TextInputLayout) view.findViewById(R.id.ti_name);
        TextInputLayout tiBoolean = (TextInputLayout) view.findViewById(R.id.ti_boolean);
        TextInputLayout tiNumber = (TextInputLayout) view.findViewById(R.id.ti_number);
        TextInputLayout tiString = (TextInputLayout) view.findViewById(R.id.ti_string);
        

        String[] reservedWords = DefineSource.RESERVED_WORD;
        String[] usedWords = DefineSource.getUsedWord(ProjectDataHelper.getScId(context));
        ArrayList<String> allNames = DesignDataManager.getAllNamesForValid(LogicEditorActivity.filename);
        

        @SuppressWarnings("unchecked")
        VariableNameValidator nameValidator = new VariableNameValidator(
            context,
            tiName,
            reservedWords,
            usedWords,
            allNames
        );
        
        @SuppressWarnings("unchecked")
        VariableNameValidator booleanValidator = new VariableNameValidator(
            context,
            tiBoolean,
            reservedWords,
            usedWords,
            new ArrayList<String>()
        );
        this.booleanValidator = booleanValidator;
        
        @SuppressWarnings("unchecked")
        VariableNameValidator numberValidator = new VariableNameValidator(
            context,
            tiNumber,
            reservedWords,
            usedWords,
            new ArrayList<String>()
        );
        this.numberValidator = numberValidator;
        
        @SuppressWarnings("unchecked")
        VariableNameValidator stringValidator = new VariableNameValidator(
            context,
            tiString,
            reservedWords,
            usedWords,
            new ArrayList<String>()
        );
        this.stringValidator = stringValidator;
    }
    
    private void setupInputs(
        final View view,
        final RelativeLayout blockArea,
        final LinearLayout removeArea,
        final Block block,
        final ArrayList<Pair<String, String>> args
    ) {
        final EditText edName = (EditText) view.findViewById(R.id.ed_name);
        final EditText edBool = (EditText) view.findViewById(R.id.ed_boolean);
        final EditText edNum = (EditText) view.findViewById(R.id.ed_number);
        final EditText edStr = (EditText) view.findViewById(R.id.ed_string);
        final EditText edLbl = (EditText) view.findViewById(R.id.ed_label);
        

        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                makeBlockWithSpec(blockArea, removeArea, block, s.toString(), args);
            }
        });
        
        view.findViewById(R.id.add_boolean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (booleanValidator != null && booleanValidator.isValid()) {
                    args.add(new Pair<String, String>("b", edBool.getText().toString()));
                    refresh(blockArea, removeArea, block, edName, args);
                    edBool.setText("");
                }
            }
        });
        
        view.findViewById(R.id.add_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberValidator != null && numberValidator.isValid()) {
                    args.add(new Pair<String, String>("d", edNum.getText().toString()));
                    refresh(blockArea, removeArea, block, edName, args);
                    edNum.setText("");
                }
            }
        });
        
        view.findViewById(R.id.add_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringValidator != null && stringValidator.isValid()) {
                    args.add(new Pair<String, String>("s", edStr.getText().toString()));
                    refresh(blockArea, removeArea, block, edName, args);
                    edStr.setText("");
                }
            }
        });
        
        view.findViewById(R.id.add_label).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args.add(new Pair<String, String>("t", edLbl.getText().toString()));
                refresh(blockArea, removeArea, block, edName, args);
                edLbl.setText("");
            }
        });
    }
    

    private void setupAcceptButton(final Block block, final View view) {
      /*  final EditText edName = (EditText) view.findViewById(R.id.ed_name);
        
        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fnName = edName.getText().toString().trim();
                    if (fnName.isEmpty()) return;
                    
                    Object tag = block.getTag();
                    if (!(tag instanceof ArrayList)) return;
                    
                    ArrayList<?> rawArgs = (ArrayList<?>) tag;
                    List<HashMap<String, String>> safeParams = new ArrayList<HashMap<String, String>>();
                    
                    for (Object o : rawArgs) {
                        if (!(o instanceof Pair)) continue;
                        
                        Pair<?, ?> p = (Pair<?, ?>) o;
                        
                        String typeKey = String.valueOf(p.first);
                        String name = String.valueOf(p.second);
                        
                        if ("t".equals(typeKey)) continue;
                        
                        HashMap<String, String> map = new HashMap<String, String>();
                        
                        if ("b".equals(typeKey)) {
                            map.put("type", "boolean");
                        } else if ("d".equals(typeKey)) {
                            map.put("type", "double");
                        } else if ("s".equals(typeKey)) {
                            map.put("type", "String");
                        } else {
                            continue;
                        }
                        
                        map.put("name", name);
                        safeParams.add(map);
                    }
                    
                    Qf.addFunction(activityName, fnName, "void", safeParams);
                    


                    try {

                        java.lang.reflect.Field field = Block.class.getDeclaredField("mSpec");
                        field.setAccessible(true);
                        String spec = (String) field.get(block);
                        DesignDataManager.addFunction(activityName, fnName, spec);
                    } catch (Exception e) {

                        StringBuilder specBuilder = new StringBuilder(fnName);
                        for (Pair<String, String> arg : args) {
                            String typeKey = arg.first;
                            String name = arg.second;
                            if ("b".equals(typeKey)) {
                                specBuilder.append(" %b.").append(name);
                            } else if ("d".equals(typeKey)) {
                                specBuilder.append(" %d.").append(name);
                            } else if ("s".equals(typeKey)) {
                                specBuilder.append(" %s.").append(name);
                            } else if ("t".equals(typeKey)) {
                                specBuilder.append(" ").append(name);
                            }
                        }
                        DesignDataManager.addFunction(activityName, fnName, specBuilder.toString());
                    }
                    
                    dialog.dismiss();
                }
            });*/
    }
    

    
    private void refresh(
        final ViewGroup blockArea,
        final ViewGroup removeArea,
        final Block block,
        final EditText name,
        final ArrayList<Pair<String, String>> args
    ) {
        makeBlockWithSpec(
            blockArea,
            removeArea,
            block,
            name.getText().toString(),
            args
        );
        updateValidators(args);
    }
    
    private void updateValidators(ArrayList<Pair<String, String>> args) {
        ArrayList<String> used = new ArrayList<String>(
            Arrays.asList(DefineSource.getUsedWord(ProjectDataHelper.getScId(context)))
        );
        
        for (Pair<String, String> p : args) {
            if (!"t".equals(p.first)) used.add(p.second);
        }
        
        String[] usedArray = used.toArray(new String[used.size()]);
        if (booleanValidator != null) booleanValidator.setUsedWords(usedArray);
        if (numberValidator != null) numberValidator.setUsedWords(usedArray);
        if (stringValidator != null) stringValidator.setUsedWords(usedArray);
    }
    
    private void makeBlockWithSpec(ViewGroup viewGroup, ViewGroup viewGroup2, Block block, String str, ArrayList<Pair<String, String>> arrayList) {
        int i;
        int i2;
        viewGroup.removeAllViews();
        viewGroup.addView(block);
        Iterator<Pair<String, String>> it = arrayList.iterator();
        String str2 = str;
        while (it.hasNext()) {
            Pair<String, String> pair = it.next();
            str2 = ("b".equals(pair.first)) ? str2 + " %b." + pair.second :
                   ("d".equals(pair.first)) ? str2 + " %d." + pair.second :
                   ("s".equals(pair.first)) ? str2 + " %s." + pair.second :
                   str2 + " " + pair.second;
        }
        block.setSpec(str2, null);
        int size = arrayList.size();
        int i3 = 0;
        for (i = 0; i < size; i++) {
            Pair<String, String> pair = arrayList.get(i);
            Block block2;
            int i4;
            if ("b".equals(pair.first)) {
                block2 = new Block(context, arrayList.indexOf(pair) + 1, pair.second, "b", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
                viewGroup.addView(block2);
                i4 = i3 + 1;
                block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
                i2 = i4;
            } else if ("d".equals(pair.first)) {
                block2 = new Block(context, arrayList.indexOf(pair) + 1, pair.second, "d", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
                viewGroup.addView(block2);
                i4 = i3 + 1;
                block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
                i2 = i4;
            } else if ("s".equals(pair.first)) {
                block2 = new Block(context, arrayList.indexOf(pair) + 1, pair.second, "s", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
                viewGroup.addView(block2);
                i4 = i3 + 1;
                block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
                i2 = i4;
            } else {
                i2 = i3;
            }
            i3 = i2;
        }
        block.fixLayout();
        viewGroup2.removeAllViews();
        i = block.labelsAndArgs.size();
        for (i3 = 0; i3 < i; i3++) {
            View view = (View) block.labelsAndArgs.get(i3);
            int i5 = 0;
            if ("label".equals(block.argTypes.get(i3))) {
                i5 = getLabelWidth((TextView) view);
            }
            if (view instanceof Block) {
                i5 = ((Block) view).getWidthSum();
            }
            i2 = (int) (((float) i5) + LayoutUtil.getDip(context, 4.0f));
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.ic_remove_grey600_24dp);
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setPadding(0, (int) LayoutUtil.getDip(context, 4.0f), 0, (int) LayoutUtil.getDip(context, 4.0f));
            imageView.setLayoutParams(new LayoutParams(i2, -1));
            viewGroup2.addView(imageView);
            if (i3 == 0) {
                imageView.setVisibility(View.INVISIBLE);
                imageView.setEnabled(false);
            } else {
                final int index = i3;
                final ArrayList<Pair<String, String>> finalArgs = arrayList;
                final ViewGroup finalViewGroup = viewGroup;
                final ViewGroup finalViewGroup2 = viewGroup2;
                final Block finalBlock = block;
                final String finalStr = str;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalArgs.remove(index - 1);
                        ArrayList<String> var3 = new ArrayList<String>(Arrays.asList(DefineSource.getUsedWord(ProjectDataHelper.getScId(context))));
                        Iterator<Pair<String, String>> var4 = finalArgs.iterator();
                        
                        while (var4.hasNext()) {
                            Pair<String, String> var5 = var4.next();
                            if (!"t".equals(var5.first)) {
                                var3.add(var5.second);
                            }
                        }
                        
                        String[] usedArray = var3.toArray(new String[var3.size()]);
                        if (booleanValidator != null) booleanValidator.setUsedWords(usedArray);
                        if (numberValidator != null) numberValidator.setUsedWords(usedArray);
                        if (stringValidator != null) stringValidator.setUsedWords(usedArray);
                        makeBlockWithSpec(finalViewGroup, finalViewGroup2, finalBlock, finalStr, finalArgs);
                    }
                });
            }
        }
    }
    
    private int getLabelWidth(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }
    
    private void addMoreBlockFunction(String type, String name) {
        List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        
        HashMap<String, String> p1 = new HashMap<String, String>();
        p1.put("type", "int");
        p1.put("name", "a");
        params.add(p1);
        
        HashMap<String, String> p2 = new HashMap<String, String>();
        p2.put("type", "int");
        p2.put("name", "b");
        params.add(p2);
        
        Qf.addFunction(
            activityName,
            "sum",
            "int",
            params
        );
    }
    
    private List<HashMap<String, String>> parseParams(String spec) {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        String[] parts = spec.split(" ");
        
        for (String p : parts) {
            HashMap<String, String> map = new HashMap<String, String>();
            if (p.startsWith("%b.")) {
                map.put("type", "boolean");
                map.put("name", p.substring(3));
            } else if (p.startsWith("%d.")) {
                map.put("type", "double");
                map.put("name", p.substring(3));
            } else if (p.startsWith("%s.")) {
                map.put("type", "String");
                map.put("name", p.substring(3));
            } else continue;
            
            list.add(map);
        }
        return list;
    }
}