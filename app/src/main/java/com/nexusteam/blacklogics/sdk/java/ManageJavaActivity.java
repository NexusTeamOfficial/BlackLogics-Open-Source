package com.nexusteam.sdk.java;

import static mod.SketchwareUtil.dpToPx;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ScrollView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.nexusteam.blacklogics.R;
import mod.SketchwareUtil;
import com.nexusteam.sdk.activities.tools.ConfigActivity;
import com.nexusteam.sdk.code.SrcCodeEditorLegacy;
import com.nexusteam.sdk.lib.FilePathUtil;
import com.nexusteam.sdk.lib.FileResConfig;
import com.nexusteam.sdk.lib.FileUtil;
import com.nexusteam.sdk.util.Helper;
import com.nexusteam.sdk.util.AdvancedRefactor;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageJavaActivity extends com.nexusteam.blacklogics.lib.base.LocaleBaseActivity {

    private static final String PACKAGE_DECL_REGEX = "package (.*?);?\\n";

    private static final String ACTIVITY_TEMPLATE =
            "package %s;\n" +
                    "\n" +
                    "import android.app.Activity;\n" +
                    "import android.os.Bundle;\n" +
                    "\n" +
                    "public class %s extends Activity {\n" +
                    "\n" +
                    "    @Override\n" +
                    "    protected void onCreate(Bundle savedInstanceState) {\n" +
                    "        super.onCreate(savedInstanceState);\n" +
                    "    }" +
                    "\n" +
                    "}\n";

    private static final String CLASS_TEMPLATE =
            "package %s;\n" +
                    "\n" +
                    "public class %s {\n" +
                    "    \n" +
                    "}\n";

    private static final String KT_ACTIVITY_TEMPLATE =
            "package %s\n" +
                    "\n" +
                    "import android.app.Activity\n" +
                    "import android.os.Bundle\n" +
                    "\n" +
                    "class %s : Activity() {\n" +
                    "\n" +
                    "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                    "        super.onCreate(savedInstanceState)\n" +
                    "    }" +
                    "\n" +
                    "}\n";

    private static final String KT_CLASS_TEMPLATE =
            "package %s\n" +
                    "\n" +
                    "class %s {\n" +
                    "    \n" +
                    "}\n";

    private final ArrayList<String> currentTree = new ArrayList<>();
    private String current_path;
    private FilePathUtil fpu;
    private FileResConfig frc;
    private GridView gridView;
    private MyAdapter adapter;
    private String sc_id;
    private TextView noteNoFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_file);

        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileprovider();
        setupUI();
        frc = new FileResConfig(sc_id);
        fpu = new FilePathUtil();
        current_path = Uri.parse(fpu.getPathJava(sc_id)).getPath();
        refresh();
    }

    @Override
    public void onBackPressed() {
        if (Objects.equals(
                Uri.parse(current_path).getPath(),
                Uri.parse(fpu.getPathJava(sc_id)).getPath())) {
            super.onBackPressed();
        } else {
            current_path = current_path.substring(0, current_path.lastIndexOf("/"));
            refresh();
        }
    }

    private void setupUI() {
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
        ImageView refactorIcon = findViewById(R.id.ig_toolbar_refactor);

        FloatingActionButton fab = findViewById(R.id.fab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateDialog();
            }
        });

        gridView = findViewById(R.id.list_file);
        gridView.setNumColumns(1);
        noteNoFiles = findViewById(R.id.text_info);
        noteNoFiles.setText(R.string.manage_java_no_files);

        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        title.setText(getString(R.string.manage_java_title,
                getString(R.string.text_title_menu_java)));

        loadFile.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(loadFile);
        loadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadDialog();
            }
        });

        if (refactorIcon != null) {
            refactorIcon.setVisibility(View.VISIBLE);
            Helper.applyRippleToToolbarView(refactorIcon);
            refactorIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRefactorDialog();
                }
            });
        }
    }

    private String getCurrentPkgName() {
        String pkgName = getIntent().getStringExtra("pkgName");

        try {
            String trimmedPath = Helper.trimPath(fpu.getPathJava(sc_id));
            String substring = current_path.substring(
                    current_path.indexOf(trimmedPath) + trimmedPath.length());

            if (substring.endsWith("/")) {
                substring = substring.substring(0, substring.length() - 1);
            }

            if (substring.startsWith("/")) {
                substring = substring.substring(1);
            }

            String replace = substring.replace("/", ".");
            return replace.isEmpty() ? pkgName : pkgName + "." + replace;
        } catch (Exception e) {
            return pkgName;
        }
    }

    private RadioButton createRadioButton(CharSequence text, int id) {
        RadioButton r = new RadioButton(this);
        r.setText(text);
        r.setId(id);

        return r;
    }

    private void showCreateDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View root = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);

        final EditText inputName = root.findViewById(R.id.dialog_edittext_name);
        final RadioGroup radio_fileType = root.findViewById(R.id.dialog_radio_filetype);

        ((TextView) root.findViewById(R.id.dialog_radio_filetype_class))
                .setText(R.string.manage_java_java_class);
        ((TextView) root.findViewById(R.id.dialog_radio_filetype_activity))
                .setText(R.string.manage_java_java_activity);

        inputName.setFocusable(true);
        inputName.setFocusableInTouchMode(true);

        final int dialog_radio_filetype_kt_class = 1001;
        final int dialog_radio_filetype_kt_activity = 1001 + 1;
        {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            radio_fileType.addView(
                    createRadioButton(getString(R.string.manage_java_kotlin_class),
                            dialog_radio_filetype_kt_class),
                    layoutParams);

            radio_fileType.addView(
                    createRadioButton(getString(R.string.manage_java_kotlin_activity),
                            dialog_radio_filetype_kt_activity),
                    layoutParams);
        }

        root.findViewById(R.id.dialog_text_cancel)
                .setOnClickListener(Helper.getDialogDismissListener(dialog));

        root.findViewById(R.id.dialog_text_save)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputName.getText().toString().isEmpty()) {
                            SketchwareUtil.toastError(
                                    getString(R.string.manage_java_invalid_file_name));
                            return;
                        }

                        String name = inputName.getText().toString();
                        String packageName = getCurrentPkgName();

                        String extension;
                        String newFileContent;
                        int checkedRadioButtonId = radio_fileType.getCheckedRadioButtonId();

                        if (checkedRadioButtonId == R.id.dialog_radio_filetype_class) {
                            newFileContent = String.format(CLASS_TEMPLATE, packageName, name);
                            extension = ".java";
                        } else if (checkedRadioButtonId == R.id.dialog_radio_filetype_activity) {
                            newFileContent = String.format(ACTIVITY_TEMPLATE, packageName, name);
                            extension = ".java";
                        } else if (checkedRadioButtonId == dialog_radio_filetype_kt_class) {
                            newFileContent = String.format(KT_CLASS_TEMPLATE, packageName, name);
                            extension = ".kt";
                        } else if (checkedRadioButtonId == dialog_radio_filetype_kt_activity) {
                            newFileContent = String.format(KT_ACTIVITY_TEMPLATE, packageName, name);
                            extension = ".kt";
                        } else if (checkedRadioButtonId == R.id.radio_button_folder) {
                            FileUtil.makeDir(new File(current_path, name).getAbsolutePath());
                            refresh();
                            SketchwareUtil.toast(
                                    getString(R.string.manage_java_folder_created));
                            dialog.dismiss();
                            return;
                        } else {
                            SketchwareUtil.toast(
                                    getString(R.string.manage_java_select_file_type));
                            return;
                        }

                        if (Build.VERSION.SDK_INT < 26 && ".kt".equals(extension)) {
                            SketchwareUtil.toast(
                                    getString(R.string.manage_java_kotlin_unsupported),
                                    Toast.LENGTH_LONG);
                            return;
                        }

                        FileUtil.writeFile(
                                new File(current_path, name + extension).getAbsolutePath(),
                                newFileContent);
                        refresh();
                        SketchwareUtil.toast(getString(R.string.manage_java_file_created));
                        dialog.dismiss();
                    }
                });

        dialog.setView(root);
        dialog.show();

        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        inputName.requestFocus();
    }

    private void showLoadDialog() {
        File externalStorageDir = new File(FileUtil.getExternalStorageDir());
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = externalStorageDir;
        properties.error_dir = externalStorageDir;
        properties.offset = externalStorageDir;
        properties.extensions = new String[]{"java", "kt"};

        FilePickerDialog pickerDialog = new FilePickerDialog(this, properties);

        pickerDialog.setTitle(getString(R.string.manage_java_select_files_title));
        pickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    String filename = Uri.parse(path).getLastPathSegment();
                    String fileContent = FileUtil.readFile(path);

                    if (Build.VERSION.SDK_INT < 26 && filename.endsWith(".kt")) {
                        SketchwareUtil.toast(
                                getString(R.string.manage_java_kotlin_unsupported_skip,
                                        filename));
                        continue;
                    }

                    if (fileContent.contains("package ")) {
                        fileContent = fileContent.replaceFirst(PACKAGE_DECL_REGEX,
                                "package " + getCurrentPkgName()
                                        + (filename.endsWith(".java") ? ";" : "")
                                        + "\n");
                    }

                    FileUtil.writeFile(
                            new File(current_path, filename).getAbsolutePath(),
                            fileContent);
                    refresh();
                }
            }
        });

        pickerDialog.show();
    }

    private void showRenameDialog(final int position) {
        boolean isFolder = adapter.isFolder(position);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        LinearLayout root = (LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog_input_layout, null);

        final EditText filename = root.findViewById(R.id.edittext_change_name);
        filename.setText(adapter.getFileName(position));

        final CheckBox renameOccurrences = null;
        if (!isFolder) {
            CheckBox renameOccurrencesTemp = new CheckBox(this);
            {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(dpToPx(ManageJavaActivity.this, 16), 0,
                        dpToPx(ManageJavaActivity.this, 16),
                        dpToPx(ManageJavaActivity.this, 10));
                renameOccurrencesTemp.setLayoutParams(params);
            }
            {
                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) filename.getLayoutParams();
                params.bottomMargin = 0;
                filename.setLayoutParams(params);
            }
            renameOccurrencesTemp.setText(
                    getString(R.string.manage_java_rename_occurrences,
                            adapter.getFileNameWoExt(position)));
            root.addView(renameOccurrencesTemp, 2);

            final CheckBox finalRenameOccurrences = renameOccurrencesTemp;
            root.findViewById(R.id.text_save)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!filename.getText().toString().isEmpty()) {
                                if (!adapter.isFolder(position)) {
                                    if (frc.getJavaManifestList()
                                            .contains(adapter.getFullName(position))) {
                                        frc.getJavaManifestList()
                                                .remove(adapter.getFullName(position));
                                        FileUtil.writeFile(
                                                fpu.getManifestJava(sc_id),
                                                new Gson().toJson(frc.listJavaManifest));
                                        SketchwareUtil.toast(
                                                getString(R.string.manage_java_removed_activity_manifest));
                                    }

                                    if (finalRenameOccurrences != null
                                            && finalRenameOccurrences.isChecked()) {
                                        String fileContent =
                                                FileUtil.readFile(adapter.getItem(position));
                                        FileUtil.writeFile(
                                                adapter.getItem(position),
                                                fileContent.replaceAll(
                                                        adapter.getFileNameWoExt(position),
                                                        FileUtil.getFileNameNoExtension(
                                                                filename.getText().toString()
                                                        )
                                                ));
                                    }
                                }

                                FileUtil.renameFile(
                                        adapter.getItem(position),
                                        new File(current_path,
                                                filename.getText().toString())
                                                .getAbsolutePath());
                                refresh();
                                SketchwareUtil.toast(
                                        getString(R.string.manage_java_renamed_successfully));
                            }

                            dialog.dismiss();
                        }
                    });
        } else {
            root.findViewById(R.id.text_save)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!filename.getText().toString().isEmpty()) {
                                FileUtil.renameFile(
                                        adapter.getItem(position),
                                        new File(current_path,
                                                filename.getText().toString())
                                                .getAbsolutePath());
                                refresh();
                                SketchwareUtil.toast(
                                        getString(R.string.manage_java_renamed_successfully));
                            }

                            dialog.dismiss();
                        }
                    });
        }

        root.findViewById(R.id.text_cancel)
                .setOnClickListener(Helper.getDialogDismissListener(dialog));

        dialog.setView(root);
        dialog.show();

        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        filename.requestFocus();
    }

    private void showDeleteDialog(final int position) {
        final boolean isInManifest = frc.getJavaManifestList()
                .contains(adapter.getFullName(position));
        final boolean isFolder = adapter.isFolder(position);

        String message;
        if (isFolder) {
            message = isInManifest
                    ? getString(R.string.manage_java_delete_message_folder_manifest)
                    : getString(R.string.manage_java_delete_message_folder);
        } else {
            message = isInManifest
                    ? getString(R.string.manage_java_delete_message_file_manifest)
                    : getString(R.string.manage_java_delete_message_file);
        }

        new AlertDialog.Builder(this)
                .setTitle(adapter.getFileName(position))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!isFolder && isInManifest) {
                                    frc.getJavaManifestList()
                                            .remove(adapter.getFullName(position));
                                    FileUtil.writeFile(
                                            fpu.getManifestJava(sc_id),
                                            new Gson().toJson(frc.listJavaManifest));
                                }

                                FileUtil.deleteFile(adapter.getItem(position));
                                refresh();
                                SketchwareUtil.toast(
                                        getString(R.string.manage_java_deleted_successfully));
                            }
                        })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void refresh() {
        if (!FileUtil.isExistFile(fpu.getPathJava(sc_id))) {
            FileUtil.makeDir(fpu.getPathJava(sc_id));
            refresh();
        }

        if (!FileUtil.isExistFile(fpu.getManifestJava(sc_id))) {
            FileUtil.writeFile(fpu.getManifestJava(sc_id), "");
            refresh();
        }

        currentTree.clear();
        FileUtil.listDir(current_path, currentTree);
        Helper.sortPaths(currentTree);

        adapter = new MyAdapter();

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (adapter.isFolder(position)) {
                    current_path = adapter.getItem(position);
                    refresh();
                    return;
                }
                adapter.goEditFile(position);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                adapter.itemContextMenu(view, position, Gravity.CENTER);
                return true;
            }
        });

        noteNoFiles.setVisibility(currentTree.size() == 0 ? View.VISIBLE : View.GONE);
    }


    private void showRefactorDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_refactor, null);

        final TextInputEditText etOldPackage = dialogView.findViewById(R.id.et_old_package);
        final TextInputEditText etNewPackage = dialogView.findViewById(R.id.et_new_package);

        final MaterialCheckBox cbDryRun = dialogView.findViewById(R.id.cb_dry_run);
        final MaterialCheckBox cbUpdateImports = dialogView.findViewById(R.id.cb_update_imports);
        final MaterialCheckBox cbMoveFiles = dialogView.findViewById(R.id.cb_move_files);

        final MaterialButton btnPreview = dialogView.findViewById(R.id.btn_preview);
        final MaterialButton btnExecute = dialogView.findViewById(R.id.btn_execute);
        final MaterialButton btnCancel = dialogView.findViewById(R.id.btn_cancel);

        final MaterialTextView tvPreview = dialogView.findViewById(R.id.tv_preview);
        final ScrollView previewScroll = dialogView.findViewById(R.id.preview_scroll);

        String currentPkg = getCurrentPkgName();
        etOldPackage.setText(currentPkg);
        etNewPackage.setText(currentPkg);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPkg = etOldPackage.getText().toString().trim();
                String newPkg = etNewPackage.getText().toString().trim();

                if (oldPkg.length() == 0 || newPkg.length() == 0) {
                    SketchwareUtil.toastError(
                            getString(R.string.refactor_enter_both_packages));
                    return;
                }

                previewScroll.setVisibility(View.VISIBLE);

                StringBuilder preview = new StringBuilder();
                preview.append(getString(R.string.refactor_preview_header)).append("\n\n");
                preview.append(getString(R.string.refactor_old_pkg_label, oldPkg)).append("\n");
                preview.append(getString(R.string.refactor_new_pkg_label, newPkg)).append("\n");
                preview.append(getString(R.string.refactor_separator)).append("\n\n");
                preview.append(getString(R.string.refactor_files_affected)).append("\n");

                File javaDir = new File(fpu.getPathJava(sc_id));
                listFilesForPreview(javaDir, preview, oldPkg);

                if (preview.toString()
                        .contains(getString(R.string.refactor_no_files_found))) {
                    preview.append("\n").append(
                            getString(R.string.refactor_no_files_with_pkg, oldPkg));
                }

                tvPreview.setText(preview.toString());
            }
        });

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPkg = etOldPackage.getText().toString().trim();
                String newPkg = etNewPackage.getText().toString().trim();

                if (oldPkg.length() == 0 || newPkg.length() == 0) {
                    SketchwareUtil.toastError(
                            getString(R.string.refactor_enter_both_packages));
                    return;
                }

                boolean dryRun = cbDryRun.isChecked();
                boolean updateImports = cbUpdateImports.isChecked();
                boolean moveFiles = cbMoveFiles.isChecked();

                executeRefactor(oldPkg, newPkg, dryRun, updateImports, moveFiles, dialog);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void listFilesForPreview(File dir, StringBuilder preview, String oldPkg) {
        File[] files = dir.listFiles();
        boolean found = false;

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFilesForPreview(file, preview, oldPkg);
                } else if (file.getName().endsWith(".java")
                        || file.getName().endsWith(".kt")) {
                    String content = FileUtil.readFile(file.getAbsolutePath());
                    if (content.contains("package " + oldPkg)) {
                        preview.append("  📄 ").append(file.getAbsolutePath()).append("\n");
                        found = true;
                    }
                }
            }
        }

        if (!found && dir == new File(fpu.getPathJava(sc_id))) {
            preview.append("  ").append(
                    getString(R.string.refactor_no_files_found)).append("\n");
        }
    }

    private void executeRefactor(String oldPkg, String newPkg, boolean dryRun,
                                 boolean updateImports, boolean moveFiles,
                                 androidx.appcompat.app.AlertDialog dialog) {
        try {
            AdvancedRefactor refactor = new AdvancedRefactor(dryRun);
            String projectRoot = fpu.getPathJava(sc_id);

            SketchwareUtil.toast(getString(dryRun
                    ? R.string.refactor_starting_dry_run
                    : R.string.refactor_starting));

            AdvancedRefactor.PackageRenameResult result =
                    refactor.renamePackage(projectRoot, oldPkg, newPkg);

            showRefactorResults(result, dryRun);

            if (!dryRun) {
                refresh();
                SketchwareUtil.toast(getString(R.string.refactor_completed_success));
            }

            dialog.dismiss();

        } catch (Exception e) {
            SketchwareUtil.toastError(
                    getString(R.string.refactor_failed, e.getMessage()));
            e.printStackTrace();
        }
    }

    private void showRefactorResults(AdvancedRefactor.PackageRenameResult result,
                                     boolean dryRun) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_refactor_results, null);

        TextView tvResults = view.findViewById(R.id.tv_results);
        Button btnOk = view.findViewById(R.id.btn_ok);

        StringBuilder results = new StringBuilder();
        results.append(getString(dryRun
                        ? R.string.refactor_dry_run_results
                        : R.string.refactor_completed_results))
                .append("\n\n");
        results.append(result.toString());

        tvResults.setText(results.toString());

        final AlertDialog resultDialog = builder.setView(view)
                .setCancelable(false)
                .create();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultDialog.dismiss();
            }
        });

        resultDialog.show();
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public String getItem(int position) {
            return currentTree.get(position);
        }

        @Override
        public int getCount() {
            return currentTree.size();
        }

        public String getFullName(int position) {
            String readFile = FileUtil.readFile(getItem(position));

            if (!readFile.contains("package ")) {
                return getFileNameWoExt(position);
            }

            Matcher m = Pattern.compile(PACKAGE_DECL_REGEX).matcher(readFile);
            if (m.find()) {
                return m.group(1) + "." + getFileNameWoExt(position);
            }

            return getFileNameWoExt(position);
        }

        public String getFileName(int position) {
            String item = getItem(position);
            return item.substring(item.lastIndexOf("/") + 1);
        }

        public String getFileNameWoExt(int position) {
            return FileUtil.getFileNameNoExtension(getItem(position));
        }

        public boolean isFolder(int position) {
            return FileUtil.isDirectory(getItem(position));
        }

        public void goEditFile(int position) {
            Intent intent = new Intent();

            if (ConfigActivity.isLegacyCeEnabled()) {
                intent.setClass(getApplicationContext(), SrcCodeEditorLegacy.class);
            } else {
                intent.setClass(getApplicationContext(),
                        com.nexusteam.sdk.code.SrcCodeEditor.class);
            }

            intent.putExtra("java", "");
            intent.putExtra("title", getFileName(position));
            intent.putExtra("content", getItem(position));

            startActivity(intent);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.manage_java_item_hs, null);
            }

            TextView name = convertView.findViewById(R.id.title);
            ImageView icon = convertView.findViewById(R.id.icon);
            final ImageView more = convertView.findViewById(R.id.more);

            name.setText(getFileName(position));

            if (isFolder(position)) {
                icon.setImageResource(R.drawable.ic_folder_48dp);
                icon.setPadding(0, 0, 0, 0);
            } else if (getFileName(position).endsWith(".java")) {
                icon.setImageResource(R.drawable.java_96);
                icon.setPadding(0, 0, 0, 0);
            } else if (getFileName(position).endsWith(".kt")) {
                icon.setImageResource(R.drawable.kotlin_full_color_mark);
                icon.setPadding(
                        dpToPx(ManageJavaActivity.this, 4),
                        dpToPx(ManageJavaActivity.this, 4),
                        dpToPx(ManageJavaActivity.this, 4),
                        dpToPx(ManageJavaActivity.this, 4));
            }

            Helper.applyRipple(ManageJavaActivity.this, more);

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemContextMenu(more, position, Gravity.RIGHT);
                }
            });

            return convertView;
        }

        private void itemContextMenu(View v, final int position, int gravity) {
            PopupMenu popupMenu = new PopupMenu(ManageJavaActivity.this, v, gravity);
            popupMenu.inflate(R.menu.popup_menu_double);

            Menu popupMenuMenu = popupMenu.getMenu();
            popupMenuMenu.clear();

            boolean isActivityInManifest = frc.getJavaManifestList()
                    .contains(getFullName(position));
            boolean isServiceInManifest = frc.getServiceManifestList()
                    .contains(getFullName(position));

            if (!isFolder(position)) {
                if (isActivityInManifest) {
                    popupMenuMenu.add(getString(
                            R.string.manage_java_remove_activity_manifest));
                } else if (!isServiceInManifest) {
                    popupMenuMenu.add(getString(
                            R.string.manage_java_add_activity_manifest));
                }

                if (isServiceInManifest) {
                    popupMenuMenu.add(getString(
                            R.string.manage_java_remove_service_manifest));
                } else if (!isActivityInManifest) {
                    popupMenuMenu.add(getString(
                            R.string.manage_java_add_service_manifest));
                }

                popupMenuMenu.add(getString(R.string.manage_java_edit));
                popupMenuMenu.add(getString(R.string.manage_java_edit_with));
            }

            popupMenuMenu.add(getString(R.string.manage_java_rename));
            popupMenuMenu.add(getString(R.string.manage_java_delete));

            popupMenu.setOnMenuItemClickListener(
                    new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String title = item.getTitle().toString();

                            if (title.equals(getString(
                                    R.string.manage_java_add_activity_manifest))) {
                                frc.getJavaManifestList().add(getFullName(position));
                                FileUtil.writeFile(
                                        fpu.getManifestJava(sc_id),
                                        new Gson().toJson(frc.listJavaManifest));
                                SketchwareUtil.toast(getString(
                                        R.string.manage_java_added_activity_manifest,
                                        getFileNameWoExt(position)));

                            } else if (title.equals(getString(
                                    R.string.manage_java_remove_activity_manifest))) {
                                if (frc.getJavaManifestList()
                                        .remove(getFullName(position))) {
                                    FileUtil.writeFile(
                                            fpu.getManifestJava(sc_id),
                                            new Gson().toJson(frc.listJavaManifest));
                                    SketchwareUtil.toast(getString(
                                            R.string.manage_java_removed_activity_manifest,
                                            getFileNameWoExt(position)));
                                } else {
                                    SketchwareUtil.toast(getString(
                                            R.string.manage_java_activity_not_in_manifest));
                                }

                            } else if (title.equals(getString(
                                    R.string.manage_java_add_service_manifest))) {
                                frc.getServiceManifestList().add(getFullName(position));
                                FileUtil.writeFile(
                                        fpu.getManifestService(sc_id),
                                        new Gson().toJson(frc.listServiceManifest));
                                SketchwareUtil.toast(getString(
                                        R.string.manage_java_added_service_manifest,
                                        getFileNameWoExt(position)));

                            } else if (title.equals(getString(
                                    R.string.manage_java_remove_service_manifest))) {
                                if (frc.getServiceManifestList()
                                        .remove(getFullName(position))) {
                                    FileUtil.writeFile(
                                            fpu.getManifestService(sc_id),
                                            new Gson().toJson(frc.listServiceManifest));
                                    SketchwareUtil.toast(getString(
                                            R.string.manage_java_removed_service_manifest,
                                            getFileNameWoExt(position)));
                                } else {
                                    SketchwareUtil.toast(getString(
                                            R.string.manage_java_service_not_in_manifest));
                                }

                            } else if (title.equals(getString(
                                    R.string.manage_java_edit))) {
                                goEditFile(position);

                            } else if (title.equals(getString(
                                    R.string.manage_java_edit_with))) {
                                Intent launchIntent = new Intent(Intent.ACTION_VIEW);
                                launchIntent.setDataAndType(
                                        Uri.fromFile(new File(getItem(position))),
                                        "text/plain");
                                startActivity(launchIntent);

                            } else if (title.equals(getString(
                                    R.string.manage_java_rename))) {
                                showRenameDialog(position);

                            } else if (title.equals(getString(
                                    R.string.manage_java_delete))) {
                                showDeleteDialog(position);

                            } else {
                                return false;
                            }

                            return true;
                        }
                    });

            popupMenu.show();
        }
    }
}