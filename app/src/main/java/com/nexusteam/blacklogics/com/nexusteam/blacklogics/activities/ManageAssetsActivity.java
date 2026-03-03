package com.nexusteam.blacklogics.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.AssetItem;
import com.nexusteam.blacklogics.models.AssetManagerModel;
import com.nexusteam.blacklogics.utils.AssetHelper;
import com.nexusteam.blacklogics.utils.AssetFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.FilePathUtil;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.activities.SrcCodeEditor;
import com.nexusteam.blacklogics.utils.Helper;

public class ManageAssetsActivity extends Activity {
    
    private GridView gridView;
    private TextView tv_noFileExist;
    private AssetManagerModel assetModel;
    private AssetAdapter adapter;
    private FilePathUtil fpu;
    private String sc_id;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_file);
        
        sc_id = getIntent().getStringExtra("sc_id");
        Helper.fixFileProvider();
        setupUI();
        
        fpu = new FilePathUtil();
        String initialPath = fpu.getPathAssets(sc_id);
        assetModel = new AssetManagerModel(sc_id, initialPath);
        
        refresh();
    }
    
    @SuppressLint("SetTextI18n")
    private void setupUI() {
        gridView = findViewById(R.id.list_file);
        gridView.setNumColumns(1);
        
        FloatingActionButton fab = findViewById(R.id.fab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateDialog();
            }
        });
        
        tv_noFileExist = findViewById(R.id.text_info);
        tv_noFileExist.setText("No files");
        
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Asset Manager");
        ImageView imageView = findViewById(R.id.ig_toolbar_back);
        Helper.applyRippleToToolbarView(imageView);
        imageView.setOnClickListener(Helper.getBackPressedClickListener(this));
        
        ImageView ig_load_file = findViewById(R.id.ig_toolbar_load_file);
        ig_load_file.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(ig_load_file);
        ig_load_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadDialog();
            }
        });
    }
    
    @Override
    public void onBackPressed() {
        if (assetModel.navigateUp()) {
            refresh();
        } else {
            super.onBackPressed();
        }
    }
    
    @SuppressLint("SetTextI18n")
    private void showCreateDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_create_new_file_layout, null);
        final RadioGroup folderOrFile = view.findViewById(R.id.dialog_radio_filetype);
        final RadioButton file = view.findViewById(R.id.dialog_radio_filetype_class);
        final RadioButton folder = view.findViewById(R.id.radio_button_folder);
        final EditText filename = view.findViewById(R.id.dialog_edittext_name);
        final TextView cancel = view.findViewById(R.id.dialog_text_cancel);
        final TextView save = view.findViewById(R.id.dialog_text_save);
        
        file.setText("File");
        file.setFocusable(true);
        file.setFocusableInTouchMode(true);
        
        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename.getText().toString().isEmpty()) {
                    BlackLogicsUtil.toastError("Invalid filename");
                    return;
                }
                
                String name = filename.getText().toString();
                String currentPath = assetModel.getCurrentPath();
                
                int checkedRadioButtonId = folderOrFile.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.dialog_radio_filetype_class) {
                    AssetHelper.createFile(currentPath, name);
                } else if (checkedRadioButtonId == R.id.radio_button_folder) {
                    AssetHelper.createFolder(currentPath, name);
                } else {
                    BlackLogicsUtil.toast("Select a file type");
                    return;
                }
                
                refresh();
                dialog.dismiss();
            }
        });
        
        
        dialog.setView(view);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        filename.requestFocus();
    }
    
    private void showLoadDialog() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = new File("/storage/emulated/0/");
        properties.error_dir = new File("/storage/emulated/0/");
        properties.offset = new File("/storage/emulated/0/");
        properties.extensions = null;
        
        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("Select an asset file");
        

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] selections) {
                for (String path : selections) {
                    File file = new File(path);
                    try {
                        FileUtil.copyDirectory(file, new File(assetModel.getCurrentPath(), file.getName()));
                        refresh();
                    } catch (IOException e) {
                        BlackLogicsUtil.toastError("Couldn't import file! [" + e.getMessage() + "]");
                    }
                }
            }
        });
        
        dialog.show();
        
    }
    
    private void showRenameDialog(final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_input_layout, null);
        final EditText newFileName = view.findViewById(R.id.edittext_change_name);
        final TextView cancel = view.findViewById(R.id.text_cancel);
        final TextView save = view.findViewById(R.id.text_save);
        
        final AssetItem item = assetModel.getItem(position);
        if (item != null) {
            newFileName.setText(item.getName());
        }
        
        cancel.setOnClickListener(Helper.getDialogDismissListener(dialog));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (!newFileName.getText().toString().isEmpty()) {
                    
                    String newName = newFileName.getText().toString();
                    String oldPath = item.getPath();
                    String newPath = new File(
                    assetModel.getCurrentPath(),
                    newName
                    ).getAbsolutePath();
                    
                    AssetHelper.renameFile(oldPath, newPath);
                    refresh();
                }
                
                dialog.dismiss();
            }
        });
        
        
        dialog.setView(view);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        newFileName.requestFocus();
    }
    
    private void showDeleteDialog(final int position) {
        final AssetItem item = assetModel.getItem(position);
        if (item != null) {
            AssetHelper.showDeleteDialog(this, item.getName(), item.isFolder(), new Runnable() {
                @Override
                public void run() {
                    FileUtil.deleteFile(item.getPath());
                    refresh();
                    BlackLogicsUtil.toast("Deleted successfully");
                }
            });
        }
        
    }
    
    private void refresh() {
        if (!FileUtil.isExistFile(fpu.getPathAssets(sc_id))) {
            FileUtil.makeDir(fpu.getPathAssets(sc_id));
        }
        
        assetModel.loadAssets();
        adapter = new AssetAdapter();
        
        adapter = new AssetAdapter();
        
        gridView.setAdapter(adapter);
        
        gridView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
            AdapterView<?> parent,
            View view,
            int position,
            long id
            ) {
                AssetItem item = assetModel.getItem(position);
                
                if (item != null) {
                    if (item.isFolder()) {
                        assetModel.navigateToFolder(item.getPath());
                        refresh();
                    } else {
                        AssetHelper.openFile(
                        ManageAssetsActivity.this,
                        item.getPath(),
                        item.getName()
                        );
                    }
                }
            }
        }
        );
        
        gridView.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
            AdapterView<?> parent,
            View view,
            int position,
            long id
            ) {
                view.findViewById(R.id.more).performClick();
                return true;
            }
        }
        );
        
        
        if (assetModel.getItemCount() == 0) {
            tv_noFileExist.setVisibility(View.VISIBLE);
        } else {
            tv_noFileExist.setVisibility(View.GONE);
        }
    }
    
    private class AssetAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return assetModel.getItemCount();
        }
        
        @Override
        public AssetItem getItem(int position) {
            return assetModel.getItem(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.manage_java_item_hs, null);
            }
            
            final TextView name = convertView.findViewById(R.id.title);
            final ImageView icon = convertView.findViewById(R.id.icon);
            final ImageView more = convertView.findViewById(R.id.more);
            
            final AssetItem item = getItem(position);
            if (item != null) {
                name.setText(item.getName());
                
                if (item.isFolder()) {
                    icon.setImageResource(R.drawable.ic_folder_48dp);
                } else if (item.isImage()) {
                    Glide.with(getApplicationContext())
                    .load(new File(item.getPath()))
                    .into(icon);
                } else {
                    icon.setImageResource(R.drawable.file_48_blue);
                }
                
                Helper.applyRipple(ManageAssetsActivity.this, more);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        PopupMenu popupMenu =
                        new PopupMenu(ManageAssetsActivity.this, v);
                        
                        if (!item.isFolder()) {
                            popupMenu.getMenu().add(0, 0, 0, "Edit");
                        }
                        
                        popupMenu.getMenu().add(0, 1, 0, "Rename");
                        popupMenu.getMenu().add(0, 2, 0, "Delete");
                        
                        popupMenu.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                
                                switch (menuItem.getItemId()) {
                                    case 0:
                                    AssetHelper.openFile(
                                    ManageAssetsActivity.this,
                                    item.getPath(),
                                    item.getName()
                                    );
                                    break;
                                    
                                    case 1:
                                    showRenameDialog(position);
                                    break;
                                    
                                    case 2:
                                    showDeleteDialog(position);
                                    break;
                                }
                                return true;
                            }
                        }
                        );
                        
                        popupMenu.show();
                    }
                });
                
            }
            
            return convertView;
        }
    }
}
