package com.nexusteam.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.sdklib.devices.DeviceWriter;
import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BlockMakerLayout extends LinearLayout {

    private Activity activity;
    private RelativeLayout blockArea;
    private LinearLayout removeArea;
    private TextInputLayout tiName;
    private TextInputLayout tiLabel;
    private TextInputLayout tiVariableName;
    private EditText edName;
    private EditText edLabel;
    private EditText edVariableName;
    private LinearLayout varTypeSpinnerContainer;
    private VariableTypeSpinner variableTypeSpinner;

    private ArrayList<Pair<String, String>> parameters = new ArrayList<>();
    private lr nameValidator;
    private lr labelValidator;
    private lr variableNameValidator;
    private fm previewBlock;

    public BlockMakerLayout(Activity activity) {
        super(activity);
        init(activity);
    }

    public void setFuncNameValidator(ArrayList<String> existingNames) {
        this.nameValidator = new lr(this.activity, this.tiName, fc.b, fc.c(), existingNames);
    }

    private void init(Activity activity) {
        this.activity = activity;
        LayoutInflater.from(activity).inflate(R.layout.make_block_layout, this);

        this.varTypeSpinnerContainer = (LinearLayout) findViewById(R.id.var_type_spinner);
        this.blockArea = (RelativeLayout) findViewById(R.id.block_area);
        this.removeArea = (LinearLayout) findViewById(R.id.remove_area);

        this.variableTypeSpinner = new VariableTypeSpinner(activity);
        this.varTypeSpinnerContainer.addView(this.variableTypeSpinner);

        this.tiName = (TextInputLayout) findViewById(R.id.ti_name);
        this.tiLabel = (TextInputLayout) findViewById(R.id.ti_label);
        this.tiVariableName = (TextInputLayout) findViewById(R.id.ti_variable_name);

        ((TextView) findViewById(R.id.tv_title_blockname))
                .setText(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_title_name_of_block));
        ((TextView) findViewById(R.id.tv_title_add_variable))
                .setText(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_title_add_variable));

        this.nameValidator = new lr(activity, this.tiName, fc.b, fc.c(), new ArrayList<String>());
        this.labelValidator = new lr(activity, this.tiLabel, fc.b, fc.c(), new ArrayList<String>());
        this.variableNameValidator = new lr(activity, this.tiVariableName, fc.b, fc.c(), new ArrayList<String>());

        this.edName = (EditText) findViewById(R.id.ed_name);
        this.edLabel = (EditText) findViewById(R.id.ed_label);
        this.edVariableName = (EditText) findViewById(R.id.ed_variable_name);

        this.tiName.setHint(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_hint_enter_new_block_name));
        this.tiVariableName.setHint(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_hint_enter_variable_name));
        this.tiLabel.setHint(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_hint_enter_block_label));

        this.edName.setPrivateImeOptions("defaultInputmode=english;");
        this.edLabel.setPrivateImeOptions("defaultInputmode=english;");
        this.edVariableName.setPrivateImeOptions("defaultInputmode=english;");

        this.edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("") || BlockMakerLayout.this.nameValidator.a()) {
                    BlockMakerLayout.this.rebuildPreview(
                            BlockMakerLayout.this.blockArea,
                            BlockMakerLayout.this.removeArea,
                            BlockMakerLayout.this.previewBlock,
                            editable.toString(),
                            BlockMakerLayout.this.parameters);
                }
            }
        });

        // Add Variable button
        Button btnAddVariable = (Button) findViewById(R.id.add_variable);
        btnAddVariable.setText(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_button_add));
        btnAddVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ki.a()) return;
                if (!BlockMakerLayout.this.variableNameValidator.a() || !BlockMakerLayout.this.nameValidator.a()) {
                    return;
                }

                Pair<String, String> selected = BlockMakerLayout.this.variableTypeSpinner.getSelectedItem();
                String type = selected.first;
                if (selected.second.length() > 0) {
                    type = type + "." + selected.second;
                }

                BlockMakerLayout.this.parameters.add(
                        new Pair<String, String>(type, BlockMakerLayout.this.edVariableName.getText().toString()));

                BlockMakerLayout.this.rebuildPreview(
                        BlockMakerLayout.this.blockArea,
                        BlockMakerLayout.this.removeArea,
                        BlockMakerLayout.this.previewBlock,
                        BlockMakerLayout.this.edName.getText().toString(),
                        BlockMakerLayout.this.parameters);

                // update forbidden names for variable validator
                ArrayList<String> forbidden = new ArrayList<String>(Arrays.asList(fc.c()));
                for (Pair<String, String> p : BlockMakerLayout.this.parameters) {
                    if (!"t".equals(p.first)) {
                        forbidden.add(p.second);
                    }
                }
                BlockMakerLayout.this.variableNameValidator.a(forbidden.toArray(new String[0]));
                BlockMakerLayout.this.edVariableName.setText("");
            }
        });

        // Add Label button
        Button btnAddLabel = (Button) findViewById(R.id.add_label);
        btnAddLabel.setText(StringResourceManager.a().a((Context) activity, R.string.logic_editor_more_block_button_add));
        btnAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ki.a()) return;
                if (!BlockMakerLayout.this.labelValidator.a() || !BlockMakerLayout.this.nameValidator.a()) {
                    return;
                }

                BlockMakerLayout.this.parameters.add(
                        new Pair<String, String>("t", BlockMakerLayout.this.edLabel.getText().toString()));

                BlockMakerLayout.this.rebuildPreview(
                        BlockMakerLayout.this.blockArea,
                        BlockMakerLayout.this.removeArea,
                        BlockMakerLayout.this.previewBlock,
                        BlockMakerLayout.this.edName.getText().toString(),
                        BlockMakerLayout.this.parameters);

                BlockMakerLayout.this.edLabel.setText("");
            }
        });

        this.previewBlock = new fm(activity, 0, "", " ", "definedFunc");
        this.blockArea.addView(this.previewBlock);
    }

    public boolean isValid() {
        if (this.edName.getText().toString().isEmpty()) {
            ke.b(getContext(),
                    StringResourceManager.a().a(getContext(), R.string.logic_editor_message_name_requied), 0).show();
            return false;
        }
        if (!this.nameValidator.a()) {
            ke.b(getContext(),
                    StringResourceManager.a().a(getContext(), R.string.logic_editor_message_name_requied), 0).show();
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return this.edName.getText().toString().isEmpty() && this.parameters.size() == 0;
    }

    public Pair<String, String> getBlockInformation() {
        return new Pair<String, String>(
                this.edName.getText().toString().trim(),
                this.previewBlock.a);
    }

    /**
     * Rebuilds the visual preview of the block and the remove buttons.
     */
    public void rebuildPreview(final ViewGroup blockContainer,
                               final ViewGroup removeContainer,
                               final fm mainBlock,
                               final String blockName,
                               final ArrayList<Pair<String, String>> params) {

        blockContainer.removeAllViews();
        blockContainer.addView(mainBlock);

        // Build the spec string
        StringBuilder spec = new StringBuilder(blockName);
        for (Pair<String, String> p : params) {
            String type = p.first;
            String name = p.second;

            if ("b".equals(type)) {
                spec.append(" %b.").append(name);
            } else if (DeviceWriter.LOCAL_NS.equals(type)) {
                spec.append(" %d.").append(name);
            } else if ("s".equals(type)) {
                spec.append(" %s.").append(name);
            } else if (type.length() > 2 && type.indexOf('.') >= 0) {
                spec.append(" %").append(type).append(".").append(name);
            } else {
                // label
                spec.append(" ").append(name);
            }
        }
        mainBlock.setSpec(spec.toString());

        // Attach argument blocks (only real arguments consume a socket)
        int argIndex = 0;
        for (int i = 0; i < params.size(); i++) {
            Pair<String, String> pair = params.get(i);
            String type = pair.first;
            String name = pair.second;

            if ("b".equals(type)) {
                fm arg = new fm(this.activity, i + 1, name, "b", "getArg");
                blockContainer.addView(arg);
                mainBlock.a((fo) mainBlock.c.get(argIndex), arg);
                argIndex++;
            } else if (DeviceWriter.LOCAL_NS.equals(type)) {
                fm arg = new fm(this.activity, i + 1, name, DeviceWriter.LOCAL_NS, "getArg");
                blockContainer.addView(arg);
                mainBlock.a((fo) mainBlock.c.get(argIndex), arg);
                argIndex++;
            } else if ("s".equals(type)) {
                fm arg = new fm(this.activity, i + 1, name, "s", "getArg");
                blockContainer.addView(arg);
                mainBlock.a((fo) mainBlock.c.get(argIndex), arg);
                argIndex++;
            } else if (type.length() > 2 && type.indexOf('.') >= 0) {
                String subtype = type.substring(type.indexOf('.') + 1);
                fm arg = new fm(this.activity, i + 1, name, et.b(subtype), et.a(subtype), "getArg");
                blockContainer.addView(arg);
                mainBlock.a((fo) mainBlock.c.get(argIndex), arg);
                argIndex++;
            }
            // labels ("t") are ignored for socket attachment
        }

        mainBlock.b();

        // Rebuild remove buttons
        removeContainer.removeAllViews();
        int size = mainBlock.o.size();

        for (int i = 0; i < size; i++) {
            View child = mainBlock.o.get(i);
            int width = 0;

            if ("label".equals(mainBlock.p.get(i))) {
                width = measureTextWidth((TextView) child);
            } else if (child instanceof fm) {
                width = ((fm) child).getWidthSum();
            }

            ImageView removeBtn = new ImageView(this.activity);
            removeBtn.setImageResource(R.drawable.ic_remove_grey600_24dp);
            removeBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            removeBtn.setPadding(0,
                    (int) ViewHelper.a(this.activity, 4.0f),
                    0,
                    (int) ViewHelper.a(this.activity, 4.0f));
            removeBtn.setLayoutParams(new LinearLayout.LayoutParams(
                    (int) (width + ViewHelper.a(this.activity, 4.0f)),
                    ViewGroup.LayoutParams.MATCH_PARENT));

            removeContainer.addView(removeBtn);

            final int removeIndex = i;

            // First remove button is hidden when the block already has a name
            if (i == 0 && this.edName.getText().length() > 0) {
                removeBtn.setVisibility(View.INVISIBLE);
                removeBtn.setEnabled(false);
            } else {
                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int listIndex = BlockMakerLayout.this.edName.getText().length() > 0
                                ? removeIndex - 1
                                : removeIndex;

                        if (listIndex >= 0 && listIndex < params.size()) {
                            params.remove(listIndex);
                        }

                        // refresh forbidden names
                        ArrayList<String> forbidden = new ArrayList<String>(Arrays.asList(fc.c()));
                        for (Pair<String, String> p : params) {
                            if (!"t".equals(p.first)) {
                                forbidden.add(p.second);
                            }
                        }
                        BlockMakerLayout.this.variableNameValidator.a(forbidden.toArray(new String[0]));

                        // rebuild
                        BlockMakerLayout.this.rebuildPreview(
                                blockContainer, removeContainer, mainBlock, blockName, params);
                    }
                });
            }
        }
    }

    private int measureTextWidth(TextView textView) {
        Rect bounds = new Rect();
        textView.getPaint().getTextBounds(
                textView.getText().toString(), 0, textView.getText().length(), bounds);
        return bounds.width();
    }
}