
package com.nexusteam.blacklogics.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nexusteam.blacklogics.bean.PermissionItem;
import com.nexusteam.blacklogics.model.FileResConfig;
import com.nexusteam.blacklogics.model.PermissionFilterModel;
import com.nexusteam.blacklogics.model.PermissionManagerModel;
import com.nexusteam.blacklogics.utils.FilePathUtil;
import com.nexusteam.blacklogics.utils.FileUtil;
import com.nexusteam.blacklogics.utils.PermissionUtils;
import com.nexusteam.blacklogics.R;
import com.nexusteam.sdk.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class ManagePermissionActivity extends Activity {
    public ListAdapter adapter;
    public ListView lv;
    public SearchView sv;
    
    private PermissionManagerModel permissionModel;
    private PermissionFilterModel filterModel;
    private String numProj;
    private FilePathUtil filePathUtil;
    
    private void initializeModels() {
        filePathUtil = new FilePathUtil();
        permissionModel = new PermissionManagerModel(numProj);
        

        permissionModel.setAllPermissions(PermissionUtils.getAllSystemPermissions());
        permissionModel.setSelectedPermissions(permissionModel.getFileResConfig().getPermissions());
        
        filterModel = new PermissionFilterModel(permissionModel.getAllPermissions());
    }
    
    private void setItems() {
        Parcelable lvSavedState = lv.onSaveInstanceState();
        

        List<PermissionItem> permissionItems = PermissionUtils.convertToPermissionItems(
        PermissionUtils.getPermissionNames(permissionModel.getAllPermissions()),
        permissionModel.getSelectedPermissions()
        );
        

        filterModel.setAllPermissions(permissionItems);
        
        adapter = new ListAdapter(filterModel.getFilteredPermissions());
        lv.setAdapter(adapter);
        lv.onRestoreInstanceState(lvSavedState);
    }
    
    private void checkFile() {
        String pathPermission = filePathUtil.getPathPermission(numProj);
        if (!FileUtil.isExistFile(pathPermission)) {
            FileUtil.writeFile(pathPermission, "[]");
        }
    }
    
    private void setUpSearchView() {
        sv.setActivated(true);
        sv.setQueryHint("Search for a permission");
        sv.onActionViewExpanded();
        sv.setIconifiedByDefault(true);
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                adapter.setFilter(filterModel.filterPermissions(newText));
                return true;
            }
            
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });
    }
    
    public void initToolbar() {
        ((TextView) findViewById(R.id.tx_toolbar_title)).setText("Permission Manager");
        ImageView back = (ImageView) findViewById(R.id.ig_toolbar_back);
        Helper.applyRipple(this, back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        ImageView resetPermissions = findViewById(R.id.ig_toolbar_load_file);
        resetPermissions.setVisibility(View.VISIBLE);
        resetPermissions.setImageResource(R.drawable.ic_reset_color_32dp);
        resetPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetConfirmationDialog();
            }
        });
    }
    
    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManagePermissionActivity.this);
        builder.setTitle("Reset permissions");
        builder.setMessage("Are you sure you want to reset all permissions? This cannot be undone!");
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtil.writeFile(filePathUtil.getPathPermission(numProj), "[]");
                permissionModel.resetPermissions();
                permissionModel.setFileResConfig(new FileResConfig(numProj));
                setItems();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_permission);
        
        if (getIntent().hasExtra("sc_id")) {
            numProj = getIntent().getStringExtra("sc_id");
        }
        
        sv = (SearchView) findViewById(R.id.search_perm);
        lv = (ListView) findViewById(R.id.main_content);
        
        initializeModels();
        checkFile();
        setItems();
        setUpSearchView();
        initToolbar();
    }
    
    @Override
    public void onBackPressed() {

        permissionModel.syncToAllPermissions();
        

        FileUtil.writeFile(filePathUtil.getPathPermission(numProj), 
        new Gson().toJson(permissionModel.getSelectedPermissions()));
        

        permissionModel.getFileResConfig().savePermissions();
        
        super.onBackPressed();
    }
    
    public class ListAdapter extends BaseAdapter {
        private List<PermissionItem> permissionItems;
        
        public ListAdapter(List<PermissionItem> permissionItems) {
            this.permissionItems = permissionItems;
        }
        
        public int getCount() {
            return permissionItems.size();
        }
        
        public PermissionItem getItem(int i) {
            return permissionItems.get(i);
        }
        
        public long getItemId(int i) {
            return i;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_item_permission, null);
            }
            
            final PermissionItem item = permissionItems.get(position);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_content);
            checkBox.setText(item.getPermissionName());
            
            checkBox.setOnCheckedChangeListener(null); // Clear previous listener
            checkBox.setChecked(item.isSelected());
            
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton button, boolean checked) {
                    String permission = button.getText().toString();
                    item.setSelected(checked);
                    
                    if (checked) {
                        permissionModel.addPermission(permission);
                    } else {
                        permissionModel.removePermission(permission);
                    }
                }
            });
            
            return convertView;
        }
        
        public void setFilter(List<PermissionItem> filter) {
            this.permissionItems = filter;
            notifyDataSetChanged();
        }
    }
}
