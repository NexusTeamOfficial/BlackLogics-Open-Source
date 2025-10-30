package com.besome.blacklogics.image_manager;

import static mod.SketchwareUtil.dpToPx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.besome.blacklogics.R;
import com.besome.blacklogics.FileUtil;
import com.besome.blacklogics.common.ImportIconActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import mod.hey.studios.util.Helper;

public class ManageImageActivity extends AppCompatActivity {
    public static final int REQ_CD_PICKER = 101;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private LinearLayout layout_ads;
    private Intent picker = new Intent(Intent.ACTION_GET_CONTENT);
    private String sc_id = "601";
    private ArrayList<String> selectedImagePaths = new ArrayList<>();
    private String currentCollectionPath;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.manage_image);
        initialize(_savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            setupUI();
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            setupUI();
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        layout_ads = findViewById(R.id.layout_ads);
        picker.setType("image/*");
        picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        sc_id = getIntent().getStringExtra("sc_id");

        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) return new ImageFragment();
                return new ImageCollectionFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? "This project" : "My collection";
            }
        });

        tab_layout.setupWithViewPager(view_pager);
    }

    private void initializeLogic() {
        File collectionDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/image");
        if (!collectionDir.exists()) collectionDir.mkdirs();
        File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/images/" + sc_id);
        if (!resourceDir.exists()) resourceDir.mkdirs();
    }

    public void _shape(final double _top1, final double _top2, final double _bottom2, final double _bottom1,
                       final String _inside_color, final String _side_color, final double _side_size, final View _view) {
        Double tlr = _top1;
        Double trr = _top2;
        Double blr = _bottom2;
        Double brr = _bottom1;
        Double sw = _side_size;
        GradientDrawable s = new GradientDrawable();
        s.setShape(GradientDrawable.RECTANGLE);
        s.setCornerRadii(new float[]{tlr.floatValue(), tlr.floatValue(), trr.floatValue(), trr.floatValue(),
                blr.floatValue(), blr.floatValue(), brr.floatValue(), brr.floatValue()});
        s.setColor(Color.parseColor(_inside_color));
        s.setStroke(sw.intValue(), Color.parseColor(_side_color));
        _view.setBackground(s);
    }

    public void _ImagePickerDialog(final boolean _check, final String path) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View inflate = getLayoutInflater().inflate(R.layout.dialog5, null);
        dialog.setView(inflate);

        LinearLayout linear3 = inflate.findViewById(R.id.linear3);
        ImageView imageview1 = inflate.findViewById(R.id.imageview1);
        EditText edittext1 = inflate.findViewById(R.id.edittext1);
        TextView textview4 = inflate.findViewById(R.id.textview4);
        CheckBox checkbox1 = inflate.findViewById(R.id.checkbox1);
        TextView textview3 = inflate.findViewById(R.id.textview3);

        // Apply shadow background to ImageView
        imageview1.setBackgroundResource(R.drawable.test);
        linear3.setBackground(null); // Remove shape background to avoid overlap

        // Set focus to EditText
        edittext1.requestFocus();
        edittext1.setFocusable(true);
        edittext1.setFocusableInTouchMode(true);
        edittext1.setClickable(true);

        imageview1.setOnClickListener(v -> {
            startActivityForResult(picker, REQ_CD_PICKER);
        });

        textview4.setOnClickListener(v -> {
            String name = edittext1.getText().toString().trim();
            if (name.isEmpty()) {
                showMessage("Please enter a name");
                return;
            }

            // Validate name for Android resource naming (only lowercase letters and underscores, no numbers)
            if (!name.matches("^[a-z_]+$")) {
                showMessage("Name must contain only lowercase letters or underscores, no numbers");
                return;
            }

            File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/images/" + sc_id);
            File destFile = new File(resourceDir, name + ".png");
            if (destFile.exists()) {
                showMessage("File name already exists!");
                return;
            }

            if (checkbox1.isChecked()) {
                saveToCollection(name, currentCollectionPath);
            }
            importImages(Arrays.asList(currentCollectionPath));
            dialog.dismiss();
        });

        textview3.setOnClickListener(v -> {
            checkbox1.setChecked(!checkbox1.isChecked());
        });

        if (_check && path != null) {
            File imageFile = new File(path);
            if (imageFile.exists()) {
                Glide.with(this)
                        .load(imageFile)
                        .error(R.drawable.test)
                        .into(imageview1);
                edittext1.setText(imageFile.getName().replaceFirst("[.][^.]+$", ""));
            } else {
                showMessage("Image file not found");
                imageview1.setImageResource(R.drawable.test);
            }
        } else {
            imageview1.setImageResource(R.drawable.test);
        }

        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CD_PICKER && resultCode == RESULT_OK && data != null) {
            selectedImagePaths.clear();
            if (data.getClipData() != null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    String path = getRealPathFromURI(uri);
                    if (!path.isEmpty()) {
                        selectedImagePaths.add(path);
                    }
                }
            } else if (data.getData() != null) {
                String path = getRealPathFromURI(data.getData());
                if (!path.isEmpty()) {
                    selectedImagePaths.add(path);
                }
            }
            if (!selectedImagePaths.isEmpty()) {
                currentCollectionPath = selectedImagePaths.get(0);
                _ImagePickerDialog(true, currentCollectionPath);
            } else {
                showMessage("Failed to load selected image");
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String path = "";
        try {
            // Get original file name from URI
            String originalName = null;
            android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                int nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                if (nameIndex != -1) {
                    originalName = cursor.getString(nameIndex);
                }
                cursor.close();
            }
            
            if (originalName == null) {
                originalName = "image_" + System.currentTimeMillis() + ".png";
            }

            // Remove extension and numbers from the original name
            String cleanName = originalName.replaceFirst("[.][^.]+$", "").replaceAll("[0-9]", "");

            java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
            // Use cleaned name instead of timestamp-based name
            File file = new File(getCacheDir(), cleanName + ".png");
            java.io.FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            path = file.getAbsolutePath();
        } catch (Exception e) {
            showMessage("Error getting image path: " + e.getMessage());
        }
        return path;
    }

    private void saveToCollection(String name, String path) {
        File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/collection.json");
        List<HashMap<String, String>> collections = new ArrayList<>();
        Gson gson = new Gson();

        try {
            if (collectionFile.exists()) {
                collections = gson.fromJson(new FileReader(collectionFile),
                        new TypeToken<List<HashMap<String, String>>>() {}.getType());
                if (collections == null) {
                    collections = new ArrayList<>();
                }
            }

            HashMap<String, String> entry = new HashMap<>();
            entry.put("name", name);
            entry.put("path", path);
            collections.add(entry);

            FileWriter writer = new FileWriter(collectionFile);
            gson.toJson(collections, writer);
            writer.close();
            showMessage("Image added to collection");
        } catch (Exception e) {
            showMessage("Error saving collection: " + e.getMessage());
        }
    }

    public void importImages(List<String> imagePaths) {
        File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/images/" + sc_id);
        for (String path : imagePaths) {
            try {
                File sourceFile = new File(path);
                String name = sourceFile.getName();
                // Use the name from EditText if provided, else keep original
                if (currentCollectionPath != null && currentCollectionPath.equals(path)) {
                    String newName = new File(currentCollectionPath).getName();
                    if (!newName.isEmpty() && !newName.equals(name)) {
                        name = newName;
                    }
                }
                File destFile = new File(resourceDir, name);
                if (destFile.exists()) {
                    showMessage("Image " + name + " already exists");
                    continue;
                }
                FileUtil.copyFile(sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
                showMessage("Image imported successfully");
            } catch (Exception e) {
                showMessage("Error importing image: " + e.getMessage());
            }
        }
    }

    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    private void setupUI() {
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);

        loadFile.setImageResource(R.drawable.ic_delete_btn_white_96dp);

        ImageView importImage = new ImageView(this);
        importImage.setLayoutParams(new LinearLayout.LayoutParams(
                dpToPx(ManageImageActivity.this, 40),
                dpToPx(ManageImageActivity.this, 40)));
        importImage.setPadding(
                dpToPx(ManageImageActivity.this, 9),
                dpToPx(ManageImageActivity.this, 9),
                dpToPx(ManageImageActivity.this, 9),
                dpToPx(ManageImageActivity.this, 9)
        );
        importImage.setImageResource(R.drawable.ic_import_white_24dp);
        importImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ((ViewGroup) loadFile.getParent()).addView(importImage, ((ViewGroup) loadFile.getParent()).indexOfChild(loadFile));
        Helper.applyRippleToToolbarView(importImage);

        importImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImportIconActivity.class);
            intent.putExtra("sc_id", sc_id);
            startActivity(intent);
        });

        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        title.setText("Image manager");

        loadFile.setVisibility(View.VISIBLE);
        Helper.applyRippleToToolbarView(loadFile);
    }

    public static class ImageFragment extends Fragment {
        private RecyclerView recyclerView;
        private List<File> images = new ArrayList<>();
        private FloatingActionButton fabImport;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false);
            recyclerView = view.findViewById(R.id.recycler_view);
            fabImport = view.findViewById(R.id.fab_import);

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            loadImages();
            recyclerView.setAdapter(new ImageAdapter());

            fabImport.setOnClickListener(v -> {
                ((ManageImageActivity) getActivity())._ImagePickerDialog(false, null);
            });

            return view;
        }

        private void loadImages() {
            File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/images/" + ((ManageImageActivity) getActivity()).sc_id);
            if (resourceDir.exists()) {
                images = Arrays.asList(resourceDir.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg")));
            }
        }

        private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                File image = images.get(position);
                String name = image.getName().substring(0, image.getName().lastIndexOf('.'));
                Glide.with(holder.itemView).load(image).into(holder.imageView);
                holder.textView.setText(name);
            }

            @Override
            public int getItemCount() {
                return images.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                ImageView imageView;
                TextView textView;

                ViewHolder(View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.image_view);
                    textView = itemView.findViewById(R.id.text_view);
                }
            }
        }
    }

    public static class ImageCollectionFragment extends Fragment {
        private RecyclerView recyclerView;
        private LinearLayout buttonLayout;
        private Button importButton, cancelButton;
        private List<HashMap<String, String>> collections = new ArrayList<>();
        private List<Integer> selectedPositions = new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_collection, container, false);
            recyclerView = view.findViewById(R.id.recycler_view);
            buttonLayout = view.findViewById(R.id.button_layout);
            importButton = view.findViewById(R.id.import_button);
            cancelButton = view.findViewById(R.id.cancel_button);

            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            loadCollections();
            recyclerView.setAdapter(new CollectionAdapter());

            importButton.setOnClickListener(v -> {
                List<String> pathsToImport = new ArrayList<>();
                for (int pos : selectedPositions) {
                    pathsToImport.add(collections.get(pos).get("path"));
                }
                ((ManageImageActivity) getActivity()).importImages(pathsToImport);
                selectedPositions.clear();
                buttonLayout.setVisibility(View.GONE);
                recyclerView.getAdapter().notifyDataSetChanged();
            });

            cancelButton.setOnClickListener(v -> {
                selectedPositions.clear();
                buttonLayout.setVisibility(View.GONE);
                recyclerView.getAdapter().notifyDataSetChanged();
            });

            return view;
        }

        private void loadCollections() {
            File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/collection.json");
            if (collectionFile.exists()) {
                try {
                    collections = new Gson().fromJson(new FileReader(collectionFile),
                            new TypeToken<List<HashMap<String, String>>>() {}.getType());
                    if (collections == null) {
                        collections = new ArrayList<>();
                    }
                } catch (Exception e) {
                    ((ManageImageActivity) getActivity()).showMessage("Error loading collections");
                }
            }
        }

        private class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                HashMap<String, String> item = collections.get(position);
                Glide.with(holder.itemView).load(item.get("path")).into(holder.imageView);
                holder.textView.setText(item.get("name"));
                holder.checkBox.setChecked(selectedPositions.contains(position));

                holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedPositions.add(position);
                    } else {
                        selectedPositions.remove(Integer.valueOf(position));
                    }
                    buttonLayout.setVisibility(selectedPositions.isEmpty() ? View.GONE : View.VISIBLE);
                });
            }

            @Override
            public int getItemCount() {
                return collections.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                ImageView imageView;
                TextView textView;
                CheckBox checkBox;

                ViewHolder(View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.image_view);
                    textView = itemView.findViewById(R.id.text_view);
                    checkBox = itemView.findViewById(R.id.check_box);
                }
            }
        }
    }
}