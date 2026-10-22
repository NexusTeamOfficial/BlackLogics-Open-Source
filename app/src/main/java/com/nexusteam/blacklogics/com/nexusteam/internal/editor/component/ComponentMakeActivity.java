package com.nexusteam.internal.editor.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.manager.ComponentManager;
import com.nexusteam.internal.lib.base.BaseDialogActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentMakeActivity extends BaseDialogActivity {

    private ScrollView scrollContainer;
    private LinearLayout rootLayout;
    private MaterialCardView mainCard;

    private ImageView backButton;
    private ImageView closeButton;
    private TextView titleText;
    private TextView subtitleText;
    private ShapeableImageView iconPreview;

    private TextInputLayout nameInputLayout;
    private TextInputEditText nameInput;
    private TextInputLayout descriptionInputLayout;
    private TextInputEditText descriptionInput;
    private TextInputLayout iconInputLayout;
    private TextInputEditText iconInput;

    private TextInputLayout typeNameInputLayout;
    private TextInputEditText typeNameInput;
    private TextInputLayout classNameInputLayout;
    private TextInputEditText classNameInput;
    private TextInputLayout buildClassInputLayout;
    private TextInputEditText buildClassInput;
    private TextInputLayout importsInputLayout;
    private TextInputEditText importsInput;
    private TextInputLayout additionalVarInputLayout;
    private TextInputEditText additionalVarInput;
    private TextInputLayout defineVarInputLayout;
    private TextInputEditText defineVarInput;
    private TextInputLayout urlInputLayout;
    private TextInputEditText urlInput;

    private TextInputLayout propertiesInputLayout;
    private TextInputEditText propertiesInput;

    private MaterialButton saveButton;
    private MaterialButton cancelButton;
    private MaterialButton deleteButton;

    private ComponentManager componentManager;
    private ComponentBean editingComponent;
    private boolean isEditMode = false;

    private static final String COMPONENT_JSON_PATH = "/storage/emulated/0/.blacklogics/system/component.json";
    private Gson gson;

    private int nextComponentType = 100;

    public static final int REQUEST_MAKE_COMPONENT = 1001;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.logic_activity_component_make);

        gson = new GsonBuilder().setPrettyPrinting().create();
        componentManager = ComponentManager.getInstance();

        Intent intent = getIntent();
        
        String componentId = intent.getStringExtra("component_id");
        if (componentId != null && !componentId.isEmpty()) {
            isEditMode = true;
            editingComponent = componentManager.getComponentById(componentId);
        }

        initViews();

        setupListeners();

        loadData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (nameInput != null) {
                    nameInput.requestFocus();
                    showKeyboard(nameInput);
                }
            }
        }, 300);
    }

    private void initViews() {
        mainCard = (MaterialCardView) findViewById(R.id.main_card);
        scrollContainer = (ScrollView) findViewById(R.id.scroll_container);
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);

        backButton = (ImageView) findViewById(R.id.back_button);
        closeButton = (ImageView) findViewById(R.id.close_button);
        titleText = (TextView) findViewById(R.id.title_text);
        subtitleText = (TextView) findViewById(R.id.subtitle_text);
        iconPreview = (ShapeableImageView) findViewById(R.id.icon_preview);

        if (iconPreview != null) {
            ShapeAppearanceModel shapeModel = ShapeAppearanceModel.builder()
                .setAllCorners(com.google.android.material.shape.CornerFamily.ROUNDED, 16)
                .build();
            iconPreview.setShapeAppearanceModel(shapeModel);
        }

        nameInputLayout = (TextInputLayout) findViewById(R.id.name_input_layout);
        nameInput = (TextInputEditText) findViewById(R.id.name_input);
        descriptionInputLayout = (TextInputLayout) findViewById(R.id.description_input_layout);
        descriptionInput = (TextInputEditText) findViewById(R.id.description_input);
        iconInputLayout = (TextInputLayout) findViewById(R.id.icon_input_layout);
        iconInput = (TextInputEditText) findViewById(R.id.icon_input);

        typeNameInputLayout = (TextInputLayout) findViewById(R.id.type_name_input_layout);
        typeNameInput = (TextInputEditText) findViewById(R.id.type_name_input);
        classNameInputLayout = (TextInputLayout) findViewById(R.id.class_name_input_layout);
        classNameInput = (TextInputEditText) findViewById(R.id.class_name_input);
        buildClassInputLayout = (TextInputLayout) findViewById(R.id.build_class_input_layout);
        buildClassInput = (TextInputEditText) findViewById(R.id.build_class_input);
        importsInputLayout = (TextInputLayout) findViewById(R.id.imports_input_layout);
        importsInput = (TextInputEditText) findViewById(R.id.imports_input);
        additionalVarInputLayout = (TextInputLayout) findViewById(R.id.additional_var_input_layout);
        additionalVarInput = (TextInputEditText) findViewById(R.id.additional_var_input);
        defineVarInputLayout = (TextInputLayout) findViewById(R.id.define_var_input_layout);
        defineVarInput = (TextInputEditText) findViewById(R.id.define_var_input);
        urlInputLayout = (TextInputLayout) findViewById(R.id.url_input_layout);
        urlInput = (TextInputEditText) findViewById(R.id.url_input);

        propertiesInputLayout = (TextInputLayout) findViewById(R.id.properties_input_layout);
        propertiesInput = (TextInputEditText) findViewById(R.id.properties_input);

        saveButton = (MaterialButton) findViewById(R.id.save_button);
        cancelButton = (MaterialButton) findViewById(R.id.cancel_button);
        deleteButton = (MaterialButton) findViewById(R.id.delete_button);

        
        saveButton.setText("Save Component");
        cancelButton.setText("Cancel");
        deleteButton.setText("Delete");

        if (isEditMode) {
            titleText.setText("Edit Custom Component");
            subtitleText.setText("Update global custom component");
            saveButton.setText("Update Component");
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            titleText.setText("Create Custom Component");
            subtitleText.setText("Add new global custom component");
            saveButton.setText("Save Component");
            deleteButton.setVisibility(View.GONE);
        }

        if (iconPreview != null) {
            iconPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showIconPickerDialog();
                }
            });
        }

        if (iconInput != null) {
            iconInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    updateIconPreview(s.toString());
                }
            });
        }
    }

    private void setupListeners() {
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (closeButton != null) {
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishWithCancel();
                }
            });
        }

        if (saveButton != null) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ki.a()) {
                        saveComponent();
                    }
                }
            });
        }

        if (cancelButton != null) {
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishWithCancel();
                }
            });
        }

        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ki.a()) {
                        showDeleteConfirmationDialog();
                    }
                }
            });
        }
    }

    private void loadData() {
        if (isEditMode && editingComponent != null) {
            nameInput.setText(editingComponent.name != null ? editingComponent.name : "");
            descriptionInput.setText(editingComponent.description != null ? editingComponent.description : "");
            iconInput.setText(editingComponent.icon != null ? editingComponent.icon : "");
            typeNameInput.setText(editingComponent.typeName != null ? editingComponent.typeName : "");
            classNameInput.setText(editingComponent.className != null ? editingComponent.className : "");
            buildClassInput.setText(editingComponent.buildClass != null ? editingComponent.buildClass : "");
            importsInput.setText(editingComponent.imports != null ? editingComponent.imports : "");
            additionalVarInput.setText(editingComponent.additionalVar != null ? editingComponent.additionalVar : "");
            defineVarInput.setText(editingComponent.defineAdditionalVar != null ? editingComponent.defineAdditionalVar : "");
            urlInput.setText(editingComponent.url != null ? editingComponent.url : "");

            String propsJson = editingComponent.param3 != null ? editingComponent.param3 : "";
            if (propsJson != null && !propsJson.isEmpty() && propsJson.contains("{")) {
                try {
                    Type type = new TypeToken<Map<String, Object>>(){}.getType();
                    Map<String, Object> props = gson.fromJson(propsJson, type);
                    if (props != null && !props.isEmpty()) {
                        propertiesInput.setText(propsJson);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            updateIconPreview(editingComponent.icon);
            
            subtitleText.setText("Component Type: " + editingComponent.type + " (Global)");
        } else {
            iconInput.setText("widget_module");
            updateIconPreview("widget_module");
            
            calculateNextComponentType();
        }
    }

    private void calculateNextComponentType() {
        try {
            List<ComponentBean> allComponents = componentManager.getAllComponents();
            int maxType = 100;

            for (ComponentBean comp : allComponents) {
                if (comp.type >= 100 && comp.type > maxType) {
                    maxType = comp.type;
                }
            }

            nextComponentType = maxType + 1;

            if (!isEditMode) {
                subtitleText.setText("New component will have type: " + nextComponentType + " (Global)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            nextComponentType = 100;
        }
    }

    private void saveComponent() {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            nameInputLayout.setError("Component name is required");
            nameInput.requestFocus();
            return;
        }
        nameInputLayout.setError(null);

        String description = descriptionInput.getText().toString().trim();
        String icon = iconInput.getText().toString().trim();
        if (icon.isEmpty()) {
            icon = "widget_module";
        }

        String typeName = typeNameInput.getText().toString().trim();
        String className = classNameInput.getText().toString().trim();
        String buildClass = buildClassInput.getText().toString().trim();
        String imports = importsInput.getText().toString().trim();
        String additionalVar = additionalVarInput.getText().toString().trim();
        String defineVar = defineVarInput.getText().toString().trim();
        String url = urlInput.getText().toString().trim();

        String propertiesText = propertiesInput.getText().toString().trim();
        Map<String, Object> propertiesMap = new HashMap<String, Object>();

        if (!propertiesText.isEmpty()) {
            try {
                Type type = new TypeToken<Map<String, Object>>(){}.getType();
                propertiesMap = gson.fromJson(propertiesText, type);
                if (propertiesMap == null) {
                    propertiesMap = new HashMap<String, Object>();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid properties JSON format", Toast.LENGTH_SHORT).show();
                propertiesInput.requestFocus();
                return;
            }
        }

        try {
            File jsonFile = new File(COMPONENT_JSON_PATH);
            JsonArray jsonArray = new JsonArray();

            if (jsonFile.exists()) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF-8"));
                    JsonParser parser = new JsonParser();
                    jsonArray = parser.parse(reader).getAsJsonArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonArray = new JsonArray();
                } finally {
                    if (reader != null) {
                        try { reader.close(); } catch (Exception e) {}
                    }
                }
            }

            JsonObject componentObj = new JsonObject();
            componentObj.addProperty("id", isEditMode ? editingComponent.componentId : String.valueOf(nextComponentType));
            componentObj.addProperty("name", name);
            componentObj.addProperty("description", description);
            componentObj.addProperty("icon", icon);
            componentObj.addProperty("typeName", typeName);
            componentObj.addProperty("class", className);
            componentObj.addProperty("buildClass", buildClass);
            componentObj.addProperty("imports", imports);
            componentObj.addProperty("additionalVar", additionalVar);
            componentObj.addProperty("defineAdditionalVar", defineVar);
            componentObj.addProperty("url", url);

            if (propertiesMap != null && !propertiesMap.isEmpty()) {
                JsonObject propsObj = new JsonObject();
                for (Map.Entry<String, Object> entry : propertiesMap.entrySet()) {
                    try {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if (value instanceof String) {
                            propsObj.addProperty(key, (String) value);
                        } else if (value instanceof Number) {
                            propsObj.addProperty(key, (Number) value);
                        } else if (value instanceof Boolean) {
                            propsObj.addProperty(key, (Boolean) value);
                        } else {
                            propsObj.addProperty(key, String.valueOf(value));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                componentObj.add("properties", propsObj);
            }

            if (isEditMode) {
                boolean found = false;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject obj = jsonArray.get(i).getAsJsonObject();
                    String id = obj.has("id") ? obj.get("id").getAsString() : "";
                    if (id.equals(editingComponent.componentId)) {
                        jsonArray.set(i, componentObj);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    jsonArray.add(componentObj);
                }
            } else {
                jsonArray.add(componentObj);
            }

            File parentDir = jsonFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(jsonFile), "UTF-8");
                String jsonOutput = gson.toJson(jsonArray);
                writer.write(jsonOutput);
                writer.flush();

                componentManager.refreshCustomComponents();

                String successMsg = isEditMode ? "Component updated successfully" : "Component created successfully";
                Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("component_updated", true);
                setResult(RESULT_OK, resultIntent);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error saving component: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (writer != null) {
                    try { writer.close(); } catch (Exception e) {}
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void deleteComponent() {
        if (!isEditMode || editingComponent == null) {
            return;
        }

        try {
            File jsonFile = new File(COMPONENT_JSON_PATH);
            if (!jsonFile.exists()) {
                Toast.makeText(this, "Component file not found", Toast.LENGTH_SHORT).show();
                return;
            }

            JsonArray jsonArray = new JsonArray();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), "UTF-8"));
                JsonParser parser = new JsonParser();
                jsonArray = parser.parse(reader).getAsJsonArray();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading component file", Toast.LENGTH_SHORT).show();
                return;
            } finally {
                if (reader != null) {
                    try { reader.close(); } catch (Exception e) {}
                }
            }

            JsonArray newArray = new JsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject obj = jsonArray.get(i).getAsJsonObject();
                String id = obj.has("id") ? obj.get("id").getAsString() : "";
                if (!id.equals(editingComponent.componentId)) {
                    newArray.add(obj);
                }
            }

            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(jsonFile), "UTF-8");
                String jsonOutput = gson.toJson(newArray);
                writer.write(jsonOutput);
                writer.flush();

                componentManager.refreshCustomComponents();

                Toast.makeText(this, "Component deleted successfully", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("component_deleted", true);
                setResult(RESULT_OK, resultIntent);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error deleting component: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                if (writer != null) {
                    try { writer.close(); } catch (Exception e) {}
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showDeleteConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
            .setTitle("Delete Component")
            .setMessage("Are you sure you want to delete \"" + editingComponent.name + "\" component?\n\nThis will remove it from ALL projects.")
            .setPositiveButton("Delete", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(android.content.DialogInterface dialog, int which) {
                    deleteComponent();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showIconPickerDialog() {
        final String[] icons = {
            "widget_module",
            "widget_custom",
            "widget_intent",
            "widget_shared_preference",
            "widget_calendar",
            "widget_vibrator",
            "widget_timer",
            "widget_firebase",
            "widget_alertdialog",
            "widget_mediaplayer",
            "widget_soundpool",
            "widget_objectanimator",
            "widget_gyroscope",
            "widget_admob",
            "widget_camera",
            "widget_file",
            "widget_network_request",
            "widget_text_to_speech",
            "widget_speech_to_text",
            "widget_bluetooth",
            "widget_location"
        };

        new MaterialAlertDialogBuilder(this)
            .setTitle("Select Icon")
            .setItems(icons, new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(android.content.DialogInterface dialog, int which) {
                    String selectedIcon = icons[which];
                    iconInput.setText(selectedIcon);
                    updateIconPreview(selectedIcon);
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void updateIconPreview(String iconName) {
        if (iconPreview == null) return;

        try {
            int iconRes = 0;
            if (iconName != null && !iconName.isEmpty()) {
                iconRes = getResources().getIdentifier(iconName, "drawable", getPackageName());
            }
            if (iconRes == 0) {
                iconRes = R.drawable.widget_module;
            }
            iconPreview.setImageResource(iconRes);
        } catch (Exception e) {
            iconPreview.setImageResource(R.drawable.widget_module);
        }
    }

    private void finishWithCancel() {
        if (hasUnsavedChanges()) {
            new MaterialAlertDialogBuilder(this)
                .setTitle("Discard Changes?")
                .setMessage("You have unsaved changes. Do you want to discard them?")
                .setPositiveButton("Discard", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        hideKeyboard();
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .setNegativeButton("Stay", null)
                .show();
        } else {
            hideKeyboard();
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private boolean hasUnsavedChanges() {
        if (isEditMode && editingComponent != null) {
            String name = nameInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();
            String icon = iconInput.getText().toString().trim();

            if (!name.equals(editingComponent.name != null ? editingComponent.name : "") ||
                !description.equals(editingComponent.description != null ? editingComponent.description : "") ||
                !icon.equals(editingComponent.icon != null ? editingComponent.icon : "")) {
                return true;
            }
            return false;
        } else {
            if (!nameInput.getText().toString().trim().isEmpty() ||
                !descriptionInput.getText().toString().trim().isEmpty() ||
                !iconInput.getText().toString().trim().isEmpty() ||
                !typeNameInput.getText().toString().trim().isEmpty() ||
                !classNameInput.getText().toString().trim().isEmpty() ||
                !buildClassInput.getText().toString().trim().isEmpty() ||
                !importsInput.getText().toString().trim().isEmpty() ||
                !additionalVarInput.getText().toString().trim().isEmpty() ||
                !defineVarInput.getText().toString().trim().isEmpty() ||
                !urlInput.getText().toString().trim().isEmpty() ||
                !propertiesInput.getText().toString().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishWithCancel();
    }

    @Override
    public void onDestroy() {
        hideKeyboard();
        super.onDestroy();
    }
}