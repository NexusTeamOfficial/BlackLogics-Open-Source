package com.besome.blacklogics.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.airbnb.lottie.LottieAnimationView;
import com.nexusteam.blacklogics.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ImportIconActivity extends AppCompatActivity {
    
    private Toolbar toolbar;
    private GridView gridView;
    private Button searchButton;
    private LinearLayout searchContainer;
    private EditText searchEditText;
    private Button whiteButton, blackButton, greyButton;
    private Button importButton, cancelButton;
    private IconAdapter iconAdapter;
    private List<IconItem> icons = new ArrayList<>();
    private List<IconItem> filteredIcons = new ArrayList<>();
    private String currentFilter = "black";
    private int selectedPosition = -1;
    private String scId;
    private Dialog loadingDialog; // Added for loading dialog
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_icon);
        

        scId = getIntent().getStringExtra("sc_id");
        if (scId == null) scId = "601";
        

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Import Icon");
        
        searchButton = findViewById(R.id.search_button);
        searchContainer = findViewById(R.id.search_container);
        searchEditText = findViewById(R.id.search_edit_text);
        whiteButton = findViewById(R.id.white_button);
        blackButton = findViewById(R.id.black_button);
        greyButton = findViewById(R.id.grey_button);
        importButton = findViewById(R.id.import_button);
        cancelButton = findViewById(R.id.cancel_button);
        gridView = findViewById(R.id.grid_view);
        
        
        searchEditText.setFocusable(true);
        searchEditText.setFocusableInTouchMode(true);
        searchEditText.setClickable(true);
        

        setupLoadingDialog();
        

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchContainer.getVisibility() == View.VISIBLE) {
                    searchContainer.setVisibility(View.GONE);
                    searchEditText.setText("");
                } else {
                    searchContainer.setVisibility(View.VISIBLE);
                }
            }
        });
        
        

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                filterIcons(s.toString());
            }
        });
        

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("black");
            }
        });
        
        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("white");
            }
        });
        
        greyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("grey");
            }
        });
        

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition >= 0) {
                    showImportDialog(filteredIcons.get(selectedPosition));
                }
            }
        });
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        
        

        iconAdapter = new IconAdapter();
        gridView.setAdapter(iconAdapter);
        

        setFilter("black");
    }
    
    private void setupLoadingDialog() {
        loadingDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(this).inflate(R.layout.loading, null);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottie1);
        lottieAnimationView.setAnimation("sketchware_logo_modified.json");
        lottieAnimationView.playAnimation();
        loadingDialog.setContentView(view);
        
        if (loadingDialog.getWindow() != null) {

            loadingDialog.getWindow().setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
            );

            loadingDialog.getWindow().setGravity(Gravity.CENTER);

            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            loadingDialog.getWindow().setDimAmount(0.5f);
        }
        
        loadingDialog.setCancelable(false);
    }
    
    
    private void setFilter(String filter) {
        if (!filter.equals(currentFilter)) {
            currentFilter = filter;

            blackButton.setBackgroundColor(filter.equals("black") ? 0xFF2196F3 : 0xFF757575);
            whiteButton.setBackgroundColor(filter.equals("white") ? 0xFF2196F3 : 0xFF757575);
            greyButton.setBackgroundColor(filter.equals("grey") ? 0xFF2196F3 : 0xFF757575);

            new LoadIconsTask().execute();
        }
    }
    
    private void filterIcons(String query) {
        filteredIcons.clear();
        query = query.toLowerCase();
        for (IconItem icon : icons) {
            if (icon.name.toLowerCase().contains(query)) {
                filteredIcons.add(icon);
            }
        }
        iconAdapter.notifyDataSetChanged();
    }
    
    private void showImportDialog(final IconItem icon) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_import_icon);
        
        final ImageView previewImage = dialog.findViewById(R.id.preview_image);
        final EditText nameEditText = dialog.findViewById(R.id.name_edit_text);
        final Button saveButton = dialog.findViewById(R.id.save_button);
        final Button cancelDialogButton = dialog.findViewById(R.id.cancel_dialog_button);
        

        final Bitmap bitmap = BitmapFactory.decodeFile(icon.path);
        previewImage.setImageBitmap(bitmap);
        

        String nameWithoutExtension = icon.name.replace("_" + currentFilter, "");
        nameEditText.setText(nameWithoutExtension);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iconName = nameEditText.getText().toString().trim();
                if (!iconName.isEmpty()) {
                    String savedPath = saveIcon(icon, iconName);
                    if (savedPath != null) {
                        Intent result = new Intent();
                        result.putExtra("iconName", iconName);
                        result.putExtra("iconPath", savedPath);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                }
                dialog.dismiss();
            }
        });
        
        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        
        dialog.show();
    }
    
    private String saveIcon(IconItem icon, String iconName) {
        String outputPath = "/storage/emulated/0" +
        "/.blacklogics/resources/images/" + scId + "/" + iconName + ".png";
        File outputFile = new File(outputPath);
        File outputDir = outputFile.getParentFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            Bitmap bitmap = BitmapFactory.decodeFile(icon.path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            return outputPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private class LoadIconsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show(); // Show loading dialog
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            icons.clear();
            try {
                File cacheDir = new File(getCacheDir(), "icons/" + currentFilter);
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                    unzipIcons(currentFilter, cacheDir);
                }
                
                File[] files = cacheDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            String name = file.getName().replace(".png", "");
                            icons.add(new IconItem(name, file.getAbsolutePath()));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            filteredIcons.clear();
            filteredIcons.addAll(icons);
            return null;
        }
        
        @Override
        protected void onPostExecute(Void aVoid) {
            iconAdapter.notifyDataSetChanged();
            searchEditText.setText("");
            selectedPosition = -1;
            loadingDialog.dismiss(); // Dismiss loading dialog
        }
    }
    
    private void unzipIcons(String filter, File outputDir) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("icons.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry entry;
        byte[] buffer = new byte[1024];
        
        while ((entry = zipInputStream.getNextEntry()) != null) {
            String entryName = entry.getName().toLowerCase();

            if (entryName.contains(filter) && entryName.endsWith(".png")) {
                File outputFile = new File(outputDir, entry.getName());
                File parentDir = outputFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
        inputStream.close();
    }
    
    private class IconAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return filteredIcons.size();
        }
        
        @Override
        public Object getItem(int position) {
            return filteredIcons.get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ImportIconActivity.this)
                .inflate(R.layout.grid_item_icon, parent, false);
            }
            
            LinearLayout container = convertView.findViewById(R.id.icon_container);
            ImageView iconImage = convertView.findViewById(R.id.icon_image);
            TextView iconName = convertView.findViewById(R.id.icon_name);
            
            final IconItem item = filteredIcons.get(position);
            iconName.setText(item.name.replace("_" + currentFilter, ""));
            
            Bitmap bitmap = BitmapFactory.decodeFile(item.path);
            iconImage.setImageBitmap(bitmap);
            
            container.setBackgroundColor(position == selectedPosition ? 0xFFFFCDD2 :
            currentFilter.equals("white") ? 0xFFBCBCBC : 0xFFFFFFFF);
            
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    showImportDialog(item);
                    iconAdapter.notifyDataSetChanged();
                }
            });
            
            return convertView;
        }
    }
    
    private static class IconItem {
        String name;
        String path;
        
        IconItem(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}
