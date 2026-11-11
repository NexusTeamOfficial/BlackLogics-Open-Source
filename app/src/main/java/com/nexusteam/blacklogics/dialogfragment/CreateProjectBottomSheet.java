package com.nexusteam.blacklogics.dialogfragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.card.MaterialCardView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.adapter.ColorThemeAdapter;
import com.nexusteam.blacklogics.adapter.ProjectTemplateAdapter;
import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.dialogfragment.VersionDialog;
import com.nexusteam.blacklogics.generator.source.ResourceManager;
import com.nexusteam.blacklogics.generator.source.TemplateLayoutWriter;
import com.nexusteam.blacklogics.util.ProjectCreateAssistant;
import com.nexusteam.blacklogics.manager.ActivityManager;
import com.nexusteam.blacklogics.model.ColorTheme;
import com.nexusteam.blacklogics.model.DataModel;
import com.nexusteam.blacklogics.model.ProjectTemplate;
import com.nexusteam.blacklogics.project.CreateProjectManager;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;

import com.nexusteam.internal.hl;
import com.nexusteam.internal.ViewHelper;

import de.hdodenhof.circleimageview.CircleImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProjectBottomSheet extends BottomSheetDialogFragment {

    private static final int PICK_IMAGE_REQUEST = 101;

    public interface OnProjectCreatedListener {
        void onProjectCreated(String projectPath, boolean isUpdate);
        void onCancel();
    }

    private CircleImageView appIcon;
    private ImageView ivLivePreview;
    private TextView tvSelectedTemplateHint;
    private TextView tvIncludes, tvContrastWarn, tvProjectPath;
    private LinearLayout containerMinSdk, containerTargetSdk;
    private SwitchCompat switchAndroidX, switchRemember;
    private int selectedMinSdk = 26;
    private int selectedTargetSdk = 36;
    private boolean useAndroidXFlag = true;
    private EditText appName, packageName, projectName;
    private TextView versionCodeT, versionNameT;
    private RecyclerView rvTemplates, rvColorThemes;
    private View layoutCustomColors;
    private LinearLayout layoutAdvanced, containerColorCircles;
    private ImageView ivCustomExpand, ivAdvancedExpand;
    private MaterialButton btnCreate, btnCancel;
    private View btnToggleCustom, btnToggleAdvanced;
    private View btnVersionCode, btnVersionName;

    private boolean isUpdateMode = false;
    private File originalProjectDir;
    private String sc_id;
    private String selectedIconPath = "";
    private boolean isCustomColorsExpanded = false;
    private boolean isAdvancedExpanded = false;

    private int selectedColorAccent = Color.parseColor("#FFFF4081");
    private int selectedColorPrimary = Color.parseColor("#FF3F51B5");
    private int selectedColorPrimaryDark = Color.parseColor("#FF303F9F");
    private int selectedColorHighlight = Color.parseColor("#FFFF9800");
    private int selectedColorNormal = Color.parseColor("#FF2196F3");

    private ResourceManager resourceManager;
    private Complex complex;
    private ProjectTemplateAdapter templateAdapter;
    private ColorThemeAdapter colorThemeAdapter;
    private List<ProjectTemplate> templates;
    private List<ColorTheme> colorThemes;

    private View circleAccent, circlePrimary, circlePrimaryDark, circleHighlight, circleNormal;

    public static CreateProjectBottomSheet newInstance(boolean isUpdate, File projectDir) {
        CreateProjectBottomSheet sheet = new CreateProjectBottomSheet();
        Bundle args = new Bundle();
        args.putBoolean("update", isUpdate);
        if (isUpdate && projectDir != null) {
            args.putString("projectDir", projectDir.getAbsolutePath());
        }
        sheet.setArguments(args);
        return sheet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);

        sc_id = String.valueOf(DataModel.DEFAULT_PROJECT_ID + getNextProjectNumber());
        complex = new Complex();
        complex.setC(requireActivity());

        if (getArguments() != null) {
            isUpdateMode = getArguments().getBoolean("update", false);
            if (isUpdateMode) {
                originalProjectDir = new File(getArguments().getString("projectDir", ""));
                String dirName = originalProjectDir.getName();
                if (dirName.matches("\\d+")) {
                    sc_id = dirName;
                } else if (dirName.startsWith("PROJECT")) {
                    sc_id = dirName.substring(7);
                }
            }
        }
        complex.setId(sc_id);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
            @Override
            public void onShow(android.content.DialogInterface d) {
                BottomSheetDialog d2 = (BottomSheetDialog) d;
                View bottomSheet = d2.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setSkipCollapsed(true);
                    behavior.setDraggable(true);
                }
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_project_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
        initTemplates();
        initColorThemes();
        initializeResourceManager();

        int nameCounter = getNextNameCounter();
        projectName.setText(DataModel.PROJECT_TEMPLATE_NAME + nameCounter);
        packageName.setText(DataModel.PROJECT_TEMPLATE_PACKAGE_NAME + nameCounter);

        if (isUpdateMode && originalProjectDir != null) {
            loadExistingProjectData();
        }

        setupClickListeners();
        buildCustomColorCircles();
        updateColorCircles();
        setupSmartFeatures();
    }

    private void bindViews(View view) {
        appIcon = (CircleImageView) view.findViewById(R.id.app_icon);
        ivLivePreview = (ImageView) view.findViewById(R.id.iv_live_preview);
        tvSelectedTemplateHint = (TextView) view.findViewById(R.id.tv_selected_template_hint);
        tvIncludes = (TextView) view.findViewById(R.id.tv_includes);
        tvContrastWarn = (TextView) view.findViewById(R.id.tv_contrast_warn);
        tvProjectPath = (TextView) view.findViewById(R.id.tv_project_path);
        containerMinSdk = (LinearLayout) view.findViewById(R.id.container_min_sdk);
        containerTargetSdk = (LinearLayout) view.findViewById(R.id.container_target_sdk);
        switchAndroidX = (SwitchCompat) view.findViewById(R.id.switch_androidx);
        switchRemember = (SwitchCompat) view.findViewById(R.id.switch_remember);
        appName = (EditText) view.findViewById(R.id.app_name);
        packageName = (EditText) view.findViewById(R.id.package_name);
        projectName = (EditText) view.findViewById(R.id.project_name);
        versionCodeT = (TextView) view.findViewById(R.id.version_code);
        versionNameT = (TextView) view.findViewById(R.id.version_name);

        rvTemplates = (RecyclerView) view.findViewById(R.id.rv_templates);
        rvColorThemes = (RecyclerView) view.findViewById(R.id.rv_color_themes);

        layoutCustomColors = view.findViewById(R.id.layout_custom_colors);
        layoutAdvanced = (LinearLayout) view.findViewById(R.id.layout_advanced);
        containerColorCircles = (LinearLayout) view.findViewById(R.id.container_color_circles);

        ivCustomExpand = (ImageView) view.findViewById(R.id.iv_custom_expand);
        ivAdvancedExpand = (ImageView) view.findViewById(R.id.iv_advanced_expand);

        btnToggleCustom = view.findViewById(R.id.btn_toggle_custom_colors);
        btnToggleAdvanced = view.findViewById(R.id.btn_toggle_advanced);
        btnVersionCode = view.findViewById(R.id.btn_version_code);
        btnVersionName = view.findViewById(R.id.btn_version_name);

        btnCreate = (MaterialButton) view.findViewById(R.id.btn_create);
        btnCancel = (MaterialButton) view.findViewById(R.id.btn_cancel);
    }

    private void initTemplates() {
        templates = new ArrayList<ProjectTemplate>();
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_EMPTY,
                "Empty", "Status only",
                R.drawable.activity_0000,
                true, false, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_BASIC,
                "Basic", "Toolbar",
                R.drawable.activity_0001,
                true, true, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_DRAWER,
                "Drawer", "Nav drawer",
                R.drawable.activity_0101,
                true, true, true, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_FAB,
                "FAB", "Toolbar + FAB",
                R.drawable.activity_1001,
                true, true, false, true));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_BOTTOM_NAV,
                "Bottom Nav", "Bottom navigation",
                R.drawable.activity_preview_bottomnav,
                true, true, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_FULLSCREEN,
                "Fullscreen", "No bars",
                R.drawable.activity_0010,
                false, false, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_LOGIN,
                "Login", "Email + password",
                R.drawable.activity_preview_login,
                true, false, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_TABS,
                "Tabs", "TabLayout",
                R.drawable.activity_preview_tabs,
                true, true, false, false));
        templates.add(new ProjectTemplate(ProjectTemplate.TYPE_DRAWER,
                "Drawer+FAB", "Drawer & FAB",
                R.drawable.activity_1101,
                true, true, true, true));

        templateAdapter = new ProjectTemplateAdapter(requireContext(), templates);
        rvTemplates.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rvTemplates.setAdapter(templateAdapter);
        rvTemplates.setHasFixedSize(true);

        templateAdapter.setOnTemplateSelectedListener(new ProjectTemplateAdapter.OnTemplateSelectedListener() {
            @Override
            public void onTemplateSelected(ProjectTemplate template, int position) {
                updateLivePreview(template);
            }
        });

        templateAdapter.setSelectedPosition(1);
        if (templates.size() > 1) {
            updateLivePreview(templates.get(1));
        }
    }

    private void updateLivePreview(ProjectTemplate template) {
        if (template == null) return;
        if (ivLivePreview != null) {
            ivLivePreview.setImageResource(template.getPreviewDrawable());
        }
        if (tvSelectedTemplateHint != null) {
            String chrome = "";
            if (template.hasToolbar()) chrome += "Toolbar";
            if (template.hasDrawer()) chrome += (chrome.length() > 0 ? " · " : "") + "Drawer";
            if (template.hasFab()) chrome += (chrome.length() > 0 ? " · " : "") + "FAB";
            if (!template.hasStatusBar()) chrome += (chrome.length() > 0 ? " · " : "") + "Fullscreen";
            if (chrome.length() == 0) chrome = "Empty activity";
            tvSelectedTemplateHint.setText(template.getName() + " · " + chrome);
        }
        if (tvIncludes != null) {
            tvIncludes.setText(ProjectCreateAssistant.includesFor(template));
        }
    }

    private void initColorThemes() {
        colorThemes = new ArrayList<ColorTheme>();
        colorThemes.add(new ColorTheme("Indigo",
                Color.parseColor("#FF3F51B5"), Color.parseColor("#FF303F9F"),
                Color.parseColor("#FFFF4081"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF2196F3")));
        colorThemes.add(new ColorTheme("Teal",
                Color.parseColor("#FF009688"), Color.parseColor("#FF00796B"),
                Color.parseColor("#FFFF5722"), Color.parseColor("#FFFFC107"), Color.parseColor("#FF4CAF50")));
        colorThemes.add(new ColorTheme("Deep Purple",
                Color.parseColor("#FF673AB7"), Color.parseColor("#FF512DA8"),
                Color.parseColor("#FFE91E63"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF03A9F4")));
        colorThemes.add(new ColorTheme("Orange",
                Color.parseColor("#FFFF5722"), Color.parseColor("#FFE64A19"),
                Color.parseColor("#FFFFEB3B"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF2196F3")));
        colorThemes.add(new ColorTheme("Green",
                Color.parseColor("#FF4CAF50"), Color.parseColor("#FF388E3C"),
                Color.parseColor("#FFFFEB3B"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF03A9F4")));
        colorThemes.add(new ColorTheme("Pink",
                Color.parseColor("#FFE91E63"), Color.parseColor("#FFC2185B"),
                Color.parseColor("#FFFF4081"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF2196F3")));
        colorThemes.add(new ColorTheme("Blue Grey",
                Color.parseColor("#FF607D8B"), Color.parseColor("#FF455A64"),
                Color.parseColor("#FFFF5722"), Color.parseColor("#FFFFC107"), Color.parseColor("#FF03A9F4")));
        colorThemes.add(new ColorTheme("Cyan",
                Color.parseColor("#FF00BCD4"), Color.parseColor("#FF0097A7"),
                Color.parseColor("#FFFFEB3B"), Color.parseColor("#FFFF9800"), Color.parseColor("#FF4CAF50")));

        colorThemeAdapter = new ColorThemeAdapter(requireContext(), colorThemes);
        rvColorThemes.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rvColorThemes.setAdapter(colorThemeAdapter);
        rvColorThemes.setHasFixedSize(true);

        colorThemeAdapter.setOnThemeSelectedListener(new ColorThemeAdapter.OnThemeSelectedListener() {
            @Override
            public void onThemeSelected(ColorTheme theme, int position) {
                selectedColorPrimary = theme.getPrimary();
                selectedColorPrimaryDark = theme.getPrimaryDark();
                selectedColorAccent = theme.getAccent();
                selectedColorHighlight = theme.getHighlight();
                selectedColorNormal = theme.getNormal();
                updateColorCircles();
                updateResourceColors();
                updateContrastWarn();
            }
        });
    }

    private void buildCustomColorCircles() {
        containerColorCircles.removeAllViews();
        int size = dpToPx(48);
        int margin = dpToPx(10);

        circleAccent = makeColorCircle(size, "Accent");
        circlePrimary = makeColorCircle(size, "Primary");
        circlePrimaryDark = makeColorCircle(size, "Dark");
        circleHighlight = makeColorCircle(size, "Highlight");
        circleNormal = makeColorCircle(size, "Normal");

        addCircleWithLabel(circleAccent, "Accent", margin);
        addCircleWithLabel(circlePrimary, "Primary", margin);
        addCircleWithLabel(circlePrimaryDark, "Dark", margin);
        addCircleWithLabel(circleHighlight, "Highlight", margin);
        addCircleWithLabel(circleNormal, "Normal", margin);

        circleAccent.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { pickColor("Accent Color", selectedColorAccent, 0); }
        });
        circlePrimary.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { pickColor("Primary Color", selectedColorPrimary, 1); }
        });
        circlePrimaryDark.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { pickColor("Primary Dark", selectedColorPrimaryDark, 2); }
        });
        circleHighlight.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { pickColor("Highlight", selectedColorHighlight, 3); }
        });
        circleNormal.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { pickColor("Normal", selectedColorNormal, 4); }
        });
    }

    private View makeColorCircle(int size, String tag) {
        View v = new View(requireContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
        v.setLayoutParams(lp);
        v.setTag(tag);
        return v;
    }

    private void addCircleWithLabel(View circle, String label, int margin) {
        LinearLayout col = new LinearLayout(requireContext());
        col.setOrientation(LinearLayout.VERTICAL);
        col.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams colLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        colLp.setMargins(margin, 0, margin, 0);
        col.setLayoutParams(colLp);

        col.addView(circle);

        TextView tv = new TextView(requireContext());
        tv.setText(label);
        tv.setTextAppearance(requireContext(), com.google.android.material.R.style.TextAppearance_Material3_LabelSmall);
        tv.setGravity(android.view.Gravity.CENTER);
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tvLp.topMargin = dpToPx(6);
        tv.setLayoutParams(tvLp);
        col.addView(tv);

        containerColorCircles.addView(col);
    }

    private void updateColorCircles() {
        setCircleBg(circleAccent, selectedColorAccent);
        setCircleBg(circlePrimary, selectedColorPrimary);
        setCircleBg(circlePrimaryDark, selectedColorPrimaryDark);
        setCircleBg(circleHighlight, selectedColorHighlight);
        setCircleBg(circleNormal, selectedColorNormal);
    }

    private void setCircleBg(View v, int color) {
        if (v == null) return;
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        gd.setStroke(dpToPx(2), Color.WHITE);
        v.setBackground(gd);
    }

    private void pickColor(String title, int initial, final int which) {
        if (!isAdded() || getActivity() == null) return;

        try {
            View pickerRoot = ViewHelper.a(requireContext(), R.layout.color_picker_reuse);
            pickerRoot.setAnimation(
                    AnimationUtils.loadAnimation(requireContext(), R.anim.abc_fade_in));

            hl picker = new hl(pickerRoot, requireActivity(), initial, true, false);

            picker.a(new hl.b() {
                @Override
                public void a(int color) {
                    switch (which) {
                        case 0: selectedColorAccent = color; break;
                        case 1: selectedColorPrimary = color; break;
                        case 2: selectedColorPrimaryDark = color; break;
                        case 3: selectedColorHighlight = color; break;
                        case 4: selectedColorNormal = color; break;
                    }
                    updateColorCircles();
                    updateResourceColors();
                    updateContrastWarn();
                }
            });

            picker.setAnimationStyle(R.anim.abc_fade_in);
            picker.showAtLocation(pickerRoot, android.view.Gravity.CENTER, 0, 0);

        } catch (Throwable t) {
            t.printStackTrace();
            Toast.makeText(getContext(),
                    "Color picker unavailable: " + t.getClass().getSimpleName(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeResourceManager() {
        resourceManager = new ResourceManager();
        resourceManager.setAndroidX(false);
        resourceManager.setStyle("AppTheme", "@android:style/Theme.Material.Light.NoActionBar");
        resourceManager.setStyle("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
        resourceManager.setStyle("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
        resourceManager.setStyle("NoStatusBar", "AppTheme");
        resourceManager.setDimen("app_margin", "8dp");
        resourceManager.setDimen("button_corner", "4dp");
        resourceManager.setDimen("text_size", "16sp");
        updateResourceColors();
        resourceManager.save(requireContext(), sc_id);
    }

    private void updateResourceColors() {
        if (resourceManager == null) return;
        String primary = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimary));
        String primaryDark = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryDark));
        String accent = String.format("#FF%06X", (0xFFFFFF & selectedColorAccent));
        String highlight = String.format("#FF%06X", (0xFFFFFF & selectedColorHighlight));
        String normal = String.format("#FF%06X", (0xFFFFFF & selectedColorNormal));

        resourceManager.setColor("colorPrimary", primary);
        resourceManager.setColor("colorPrimaryDark", primaryDark);
        resourceManager.setColor("colorAccent", accent);
        resourceManager.setColor("colorControlHighlight", highlight);
        resourceManager.setColor("colorControlNormal", normal);
        resourceManager.setColor("colorBackground", "#FFEEEEEE");
        resourceManager.save(requireContext(), sc_id);
    }

    private void setupClickListeners() {
        btnToggleCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSection(layoutCustomColors, ivCustomExpand, isCustomColorsExpanded);
                isCustomColorsExpanded = !isCustomColorsExpanded;
            }
        });

        btnToggleAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSection(layoutAdvanced, ivAdvancedExpand, isAdvancedExpanded);
                isAdvancedExpanded = !isAdvancedExpanded;
            }
        });

        btnVersionCode.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { showVersionDialog(); }
        });
        btnVersionName.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { showVersionDialog(); }
        });

        appIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getListener() != null) getListener().onCancel();
                dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreateProject();
            }
        });
    }

    private void toggleSection(View section, ImageView arrow, boolean currentlyExpanded) {
        ViewGroup parent = (ViewGroup) section.getParent().getParent();
        TransitionManager.beginDelayedTransition(parent, new AutoTransition());
        if (currentlyExpanded) {
            section.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.ic_expand_more);
        } else {
            section.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.ic_expand_less);
        }
    }

    private void setupSmartFeatures() {
        selectedMinSdk = ProjectCreateAssistant.loadMinSdk(requireContext());
        selectedTargetSdk = ProjectCreateAssistant.loadTargetSdk(requireContext());
        useAndroidXFlag = ProjectCreateAssistant.loadAndroidX(requireContext());
        if (switchAndroidX != null) switchAndroidX.setChecked(useAndroidXFlag);

        int[] last = ProjectCreateAssistant.loadLastTheme(requireContext());
        if (last != null) {
            selectedColorPrimary = last[0];
            selectedColorPrimaryDark = last[1];
            selectedColorAccent = last[2];
            selectedColorHighlight = last[3];
            selectedColorNormal = last[4];
            updateColorCircles();
            updateResourceColors();
            updateContrastWarn();
        }

        int lastTpl = ProjectCreateAssistant.loadLastTemplateType(requireContext(), ProjectTemplate.TYPE_BASIC);
        if (templates != null && templateAdapter != null) {
            for (int i = 0; i < templates.size(); i++) {
                if (templates.get(i).getType() == lastTpl) {
                    templateAdapter.setSelectedPosition(i);
                    updateLivePreview(templates.get(i));
                    break;
                }
            }
        }

        buildSdkChips(containerMinSdk, ProjectCreateAssistant.minSdkOptions(), true);
        buildSdkChips(containerTargetSdk, ProjectCreateAssistant.targetSdkOptions(), false);

        if (tvProjectPath != null) {
            tvProjectPath.setText("Path: " + ProjectCreateAssistant.projectPathPreview(sc_id));
        }

        if (appName != null) {
            appName.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
                public void onTextChanged(CharSequence s, int a, int b, int c) {}
                public void afterTextChanged(Editable s) {
                    if (packageName == null) return;
                    String cur = packageName.getText().toString();
                    if (cur.startsWith("com.my.") || cur.length() == 0) {
                        packageName.setText(ProjectCreateAssistant.suggestPackage(s.toString()));
                    }
                }
            });
        }

        if (switchAndroidX != null) {
            switchAndroidX.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                    useAndroidXFlag = isChecked;
                    if (resourceManager != null) {
                        resourceManager.setAndroidX(isChecked);
                    }
                }
            });
        }

        updateContrastWarn();
    }

    private void buildSdkChips(final LinearLayout container, java.util.List options, final boolean isMin) {
        if (container == null || options == null) return;
        container.removeAllViews();
        int pad = dpToPx(10);
        int selected = isMin ? selectedMinSdk : selectedTargetSdk;
        for (int i = 0; i < options.size(); i++) {
            final int sdk = ((Integer) options.get(i)).intValue();
            final java.util.List opts = options;
            TextView chip = new TextView(requireContext());
            chip.setText(String.valueOf(sdk));
            chip.setPadding(pad, pad / 2, pad, pad / 2);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            chip.setLayoutParams(lp);
            boolean on = (sdk == selected);
            chip.setBackgroundResource(R.drawable.bg_option_chip);
            chip.setAlpha(on ? 1f : 0.55f);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMin) selectedMinSdk = sdk;
                    else selectedTargetSdk = sdk;
                    buildSdkChips(container, opts, isMin);
                }
            });
            container.addView(chip);
        }
    }

    private void updateContrastWarn() {
        if (tvContrastWarn == null) return;
        boolean bad = ProjectCreateAssistant.isLowContrastOnWhite(selectedColorPrimary);
        tvContrastWarn.setVisibility(bad ? View.VISIBLE : View.GONE);
    }

    private void applyTemplateToViewEditor(ProjectTemplate selectedTpl) {
        if (selectedTpl == null) return;
        try {
            complex.setC(requireContext());
            complex.setId(sc_id);
            complex.enableToolBar("MainActivity", useAndroidXFlag, selectedTpl.hasToolbar());
            complex.addActivity("MainActivity", "main",
                    selectedTpl.hasFab(), true, selectedTpl.hasDrawer());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        try {
            com.nexusteam.blacklogics.sdk.util.ActivityConfig ac =
                    new com.nexusteam.blacklogics.sdk.util.ActivityConfig();
            ac.init(requireContext(), sc_id);
            ac.setActivityConfig(
                    "MainActivity",
                    selectedTpl.hasStatusBar(),
                    selectedTpl.hasToolbar(),
                    selectedTpl.hasDrawer(),
                    selectedTpl.hasFab(),
                    "Activity",
                    "Both",
                    "main");
            ac.save(requireContext(), sc_id);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private void attemptCreateProject() {
        String appNameText = appName.getText().toString().trim();
        String pkgName = packageName.getText().toString().trim();
        String projectNameText = projectName.getText().toString().trim();
        String vCode = versionCodeT.getText().toString().trim();
        String vName = versionNameT.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(appNameText)) {
            appName.setError("Application name is required");
            focusView = appName;
            cancel = true;
        }
        if (TextUtils.isEmpty(pkgName)) {
            packageName.setError("Package name is required");
            focusView = packageName;
            cancel = true;
        } else if (!ProjectCreateAssistant.isValidPackage(pkgName)) {
            packageName.setError("Invalid package (e.g. com.company.app)");
            focusView = packageName;
            cancel = true;
        }
        if (TextUtils.isEmpty(projectNameText)) {
            projectName.setError("Project name is required");
            focusView = projectName;
            cancel = true;
        }
        if (TextUtils.isEmpty(vCode) || !vCode.matches("\\d+")) {
            Toast.makeText(getContext(), "Version code must be a number", Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if (TextUtils.isEmpty(vName) || !vName.matches("[0-9]+(\\.[0-9]+)*")) {
            Toast.makeText(getContext(), "Invalid version name (e.g. 1.0)", Toast.LENGTH_SHORT).show();
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) focusView.requestFocus();
            return;
        }
        createProject(projectNameText, pkgName, appNameText, vCode, vName);
    }

    private boolean isValidPackageName(String packageName) {
        return packageName.matches("^([a-zA-Z_][a-zA-Z0-9_]*\\.)+([a-zA-Z_][a-zA-Z0-9_]*)$");
    }

    private void createProject(String projectName, String packageName, String appName,
                               String versionCode, String versionName) {
        try {
            if (resourceManager == null) resourceManager = new ResourceManager();

            String accent = String.format("#FF%06X", (0xFFFFFF & selectedColorAccent));
            String primary = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimary));
            String primaryDark = String.format("#FF%06X", (0xFFFFFF & selectedColorPrimaryDark));
            String highlight = String.format("#FF%06X", (0xFFFFFF & selectedColorHighlight));
            String normal = String.format("#FF%06X", (0xFFFFFF & selectedColorNormal));

            resourceManager.setColor("colorPrimary", primary);
            resourceManager.setColor("colorPrimaryDark", primaryDark);
            resourceManager.setColor("colorAccent", accent);
            resourceManager.setColor("colorControlHighlight", highlight);
            resourceManager.setColor("colorControlNormal", normal);
            resourceManager.setColor("colorBackground", "#FFEEEEEE");
            resourceManager.setAppName(appName);
            resourceManager.setAndroidX(useAndroidXFlag);
            resourceManager.save(requireContext(), sc_id);

            ProjectTemplate selectedTpl = templateAdapter.getSelected();
            if (selectedTpl != null) {
                if (selectedTpl.usesCustomChrome()) {
                    resourceManager.setHasCustomToolbarChrome(true);
                }
                if (!selectedTpl.hasStatusBar() && !selectedTpl.hasToolbar()) {
                    resourceManager.setStyle("AppTheme", "FullScreen");
                }
                resourceManager.save(requireContext(), sc_id);
            }

            CreateProjectManager manager = new CreateProjectManager(
                    requireContext(), "blacklogics@2026".toCharArray());

            manager.setVariables(selectedColorAccent, selectedColorPrimary,
                    selectedColorPrimaryDark, selectedColorHighlight, selectedColorNormal);

            JSONObject config = new JSONObject();
            config.put("projectName", appName);
            config.put("packageName", packageName);
            config.put("appName", appName);
            config.put("sc_id", sc_id);
            config.put("versionCode", versionCode);
            config.put("versionName", versionName);
            config.put("colorAccent", accent);
            config.put("colorPrimary", primary);
            config.put("colorPrimaryDark", primaryDark);
            config.put("colorPrimaryHighlight", highlight);
            config.put("colorPrimaryNormal", normal);
            if (selectedTpl != null) {
                config.put("templateType", selectedTpl.getType());
                config.put("toolbar", selectedTpl.hasToolbar());
                config.put("drawer", selectedTpl.hasDrawer());
                config.put("fab", selectedTpl.hasFab());
                config.put("statusBar", selectedTpl.hasStatusBar());
            } else {
                config.put("toolbar", true);
                config.put("drawer", false);
                config.put("fab", false);
                config.put("statusBar", true);
            }
            config.put("minSdk", selectedMinSdk);
            config.put("targetSdk", selectedTargetSdk);
            config.put("androidX", useAndroidXFlag);

            if (!TextUtils.isEmpty(selectedIconPath)) {
                File iconDir = new File("/storage/emulated/0/.blacklogics/resources/icons/" + sc_id);
                if (!iconDir.exists()) iconDir.mkdirs();
                File iconFile = new File(iconDir, "icon.png");
                try {
                    Bitmap bmp = BitmapFactory.decodeFile(selectedIconPath);
                    if (bmp != null) {
                        FileOutputStream fos = new FileOutputStream(iconFile);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();
                    } else {
                        TheBlockLogicsUtil.copyFile(new File(selectedIconPath), iconFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                config.put("iconPath", iconFile.getAbsolutePath());
            } else if (isUpdateMode) {
                File existingIcon = new File("/storage/emulated/0/.blacklogics/resources/icons/" + sc_id, "icon.png");
                config.put("iconPath", existingIcon.exists() ? existingIcon.getAbsolutePath() : "default_icon.png");
            } else {
                config.put("iconPath", "default_icon.png");
            }

            ActivityManager activityManager = new ActivityManager()
                    .addActivity(DataModel.DEFAULT_ACTIVITY, DataModel.DEFAULT_LAYOUT_NAME);
            activityManager.save(requireContext(), sc_id);

            File projectDir = manager.createOrUpdateProject(
                    isUpdateMode, originalProjectDir, sc_id,
                    projectName, packageName, appName,
                    Integer.parseInt(versionCode), versionName,
                    createMainActivityAttributes(), config);

            String resourcesPath = projectDir.getAbsolutePath() + "/app/src/main/res";
            resourceManager.extractResources(sc_id, resourcesPath);

            if (selectedTpl != null) {
                TemplateLayoutWriter.apply(projectDir, selectedTpl);
            }

            applyTemplateToViewEditor(selectedTpl);

            if (switchRemember == null || switchRemember.isChecked()) {
                ProjectTemplate st = templateAdapter != null ? templateAdapter.getSelected() : null;
                if (st != null) {
                    ProjectCreateAssistant.saveLastTemplateType(requireContext(), st.getType());
                }
                ProjectCreateAssistant.saveLastTheme(requireContext(), "custom",
                        selectedColorPrimary, selectedColorPrimaryDark, selectedColorAccent,
                        selectedColorHighlight, selectedColorNormal);
                ProjectCreateAssistant.saveSdkPrefs(requireContext(), selectedMinSdk, selectedTargetSdk, useAndroidXFlag);
            }

            if (resourceManager != null) {
                resourceManager.setAndroidX(useAndroidXFlag);
                resourceManager.save(requireContext(), sc_id);
            }

            Toast.makeText(getContext(),
                    isUpdateMode ? "Project Updated" : "Project Created",
                    Toast.LENGTH_SHORT).show();

            if (getListener() != null) {
                getListener().onProjectCreated(projectDir.getAbsolutePath(), isUpdateMode);
            }
            dismiss();

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private Map<String, String> createMainActivityAttributes() {
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("exported", "true");
        attrs.put("launchMode", "singleTop");
        attrs.put("configChanges", "orientation|keyboardHidden|screenSize");
        attrs.put("windowSoftInputMode", "adjustResize");
        return attrs;
    }

    private void loadExistingProjectData() {
        try {
            CreateProjectManager manager = new CreateProjectManager(
                    requireContext(), "blacklogics@2026".toCharArray());
            JSONObject config = manager.loadEncryptedConfig(originalProjectDir);
            if (config != null) {
                appName.setText(config.optString("appName", ""));
                packageName.setText(config.optString("packageName", ""));
                projectName.setText(config.optString("projectName", ""));
                versionCodeT.setText(config.optString("versionCode", "1"));
                versionNameT.setText(config.optString("versionName", "1.0"));

                selectedColorAccent = Color.parseColor(config.optString("colorAccent", "#FFFF4081"));
                selectedColorPrimary = Color.parseColor(config.optString("colorPrimary", "#FF3F51B5"));
                selectedColorPrimaryDark = Color.parseColor(config.optString("colorPrimaryDark", "#FF303F9F"));
                selectedColorHighlight = Color.parseColor(config.optString("colorPrimaryHighlight", "#FFFF9800"));
                selectedColorNormal = Color.parseColor(config.optString("colorPrimaryNormal", "#FF2196F3"));

                File iconFile = new File(originalProjectDir, "icon.png");
                if (iconFile.exists()) {
                    selectedIconPath = iconFile.getAbsolutePath();
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedIconPath);
                    appIcon.setImageBitmap(bitmap);
                }
                updateColorCircles();
                updateResourceColors();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to load project config", Toast.LENGTH_SHORT).show();
        }
    }

    private void showVersionDialog() {
        int currentMajor = 1;
        int currentMinor = 1;
        int currentPatch = 0;
        try {
            if (versionCodeT.getText() != null && versionCodeT.getText().toString().length() > 0) {
                currentMajor = Integer.parseInt(versionCodeT.getText().toString());
            }
            if (versionNameT.getText() != null && versionNameT.getText().toString().length() > 0) {
                String[] parts = versionNameT.getText().toString().split("\\.");
                if (parts.length >= 2) {
                    currentMinor = Integer.parseInt(parts[0]);
                    currentPatch = Integer.parseInt(parts[1]);
                }
            }
        } catch (Exception ignored) {}

        VersionDialog dialog = new VersionDialog(getContext())
                .setInitialValues(currentMajor, currentMinor, currentPatch)
                .setVersionDialogListener(new VersionDialog.VersionDialogListener() {
                    @Override
                    public void onVersionSelect(String versionName, String versionCode, String minorNumber) {
                        versionCodeT.setText(versionCode);
                        versionNameT.setText(versionName);
                    }
                });
        dialog.show();
    }

    private int getMaxExistingProjectId() {
        File projectsDir = new File(TheBlockLogicsUtil.mysc);
        int max = -1;
        if (projectsDir.exists()) {
            File[] files = projectsDir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    File f = files[i];
                    if (!f.isDirectory()) continue;
                    try {
                        int id = Integer.parseInt(f.getName());
                        if (id > max) max = id;
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return max;
    }

    private int getNextProjectNumber() {
        int maxId = getMaxExistingProjectId();
        if (maxId < 0) return 0;
        return (maxId - DataModel.DEFAULT_PROJECT_ID) + 1;
    }

    private int getNextNameCounter() {
        int maxId = getMaxExistingProjectId();
        if (maxId < 0) return 1;
        return (maxId - DataModel.DEFAULT_PROJECT_ID) + 2;
    }

    private int dpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (inputStream != null) inputStream.close();
                if (bitmap == null) {
                    Toast.makeText(requireContext(), "Unsupported image format", Toast.LENGTH_SHORT).show();
                    return;
                }
                bitmap = cropToSquare(bitmap);
                File iconFile = new File(requireContext().getCacheDir(), "temp_icon.png");
                FileOutputStream fos = new FileOutputStream(iconFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                selectedIconPath = iconFile.getAbsolutePath();
                appIcon.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap cropToSquare(Bitmap src) {
        int size = Math.min(src.getWidth(), src.getHeight());
        int x = (src.getWidth() - size) / 2;
        int y = (src.getHeight() - size) / 2;
        return Bitmap.createBitmap(src, x, y, size, size);
    }

    private OnProjectCreatedListener getListener() {
        if (getActivity() instanceof OnProjectCreatedListener) {
            return (OnProjectCreatedListener) getActivity();
        }
        return null;
    }

    public static void show(FragmentManager fm, boolean isUpdate, File projectDir) {
        CreateProjectBottomSheet sheet = newInstance(isUpdate, projectDir);
        sheet.show(fm, "CreateProjectBottomSheet");
    }
}