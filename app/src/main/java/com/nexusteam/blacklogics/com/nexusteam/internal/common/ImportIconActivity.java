package com.nexusteam.internal.common;

import com.nexusteam.internal.fc;
import com.nexusteam.internal.fe;
import com.nexusteam.internal.hl;
import com.nexusteam.internal.jq;
import com.nexusteam.internal.kh;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.lc;
import com.nexusteam.internal.lo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.lib.base.BaseAppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Material 3 Import Icon Activity — Java 7 compatible (no lambdas).
 */
public class ImportIconActivity extends BaseAppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup styleToggle;
    private MaterialCardView colorCard;
    private TextView colorHex;
    private MaterialButton btnPickColor;
    private MaterialButton btnCancel;
    private MaterialButton btnImport;

    private ArrayList existingNames;
    private IconAdapter adapter;
    private ArrayList iconList = new ArrayList();
    private ArrayList filteredList = new ArrayList();

    private int selectedStyle = 0;
    private int selectedColor = 0xFF9E9E9E;
    private String currentQuery = "";

    private static final String[] STYLE_FOLDERS = {
            "outlined", "filled", "rounded", "sharp"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_icon);

        existingNames = getIntent().getStringArrayListExtra("imageNames");
        if (existingNames == null) {
            existingNames = new ArrayList();
        }

        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ki.a()) {
                    return;
                }
                onBackPressed();
            }
        });

        styleToggle = (MaterialButtonToggleGroup) findViewById(R.id.toggle_style);
        colorCard = (MaterialCardView) findViewById(R.id.card_color);
        colorHex = (TextView) findViewById(R.id.tv_color_hex);
        btnPickColor = (MaterialButton) findViewById(R.id.btn_pick_color);
        btnCancel = (MaterialButton) findViewById(R.id.btn_cancel);
        btnImport = (MaterialButton) findViewById(R.id.btn_import);
        recyclerView = (RecyclerView) findViewById(R.id.image_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, calculateSpanCount()));
        adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);

        styleToggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (!isChecked) {
                    return;
                }
                if (checkedId == R.id.btn_style_outlined) {
                    selectedStyle = 0;
                } else if (checkedId == R.id.btn_style_filled) {
                    selectedStyle = 1;
                } else if (checkedId == R.id.btn_style_rounded) {
                    selectedStyle = 2;
                } else if (checkedId == R.id.btn_style_sharp) {
                    selectedStyle = 3;
                }
                reloadIcons();
            }
        });

        btnPickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        colorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ki.a()) {
                    return;
                }
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ki.a()) {
                    return;
                }
                if (adapter.selectedIndex >= 0) {
                    showSaveDialog(adapter.selectedIndex);
                }
            }
        });

        updateColorUi();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                new ExtractIconsTask(ImportIconActivity.this).execute();
            }
        }, 200L);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.import_icon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            showSearchDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openColorPicker() {
        View pickerView = LayoutInflater.from(this).inflate(R.layout.color_picker_reuse, null);
        hl picker = new hl(pickerView, this, selectedColor, true, true);
        picker.a(new hl.b() {
            @Override
            public void a(int color) {
                selectedColor = color;
                updateColorUi();
                adapter.notifyDataSetChanged();
            }
        });
        picker.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private void updateColorUi() {
        colorCard.setCardBackgroundColor(selectedColor);
        colorHex.setText(String.format("#%06X", (0xFFFFFF & selectedColor)));
    }

    /**
     * Load SVG (preferred) or PNG into ImageView with tint.
     * Needs dependency: com.caverock:androidsvg:1.4
     */
    private void loadIconInto(ImageView imageView, String path, int tintColor) {
        if (imageView == null || path == null) {
            return;
        }
        imageView.setColorFilter(null);
        try {
            if (path.toLowerCase(Locale.US).endsWith(".svg")) {
                FileInputStream fis = new FileInputStream(path);
                SVG svg = SVG.getFromInputStream(fis);
                fis.close();
                if (svg == null) {
                    imageView.setImageDrawable(null);
                    return;
                }
                // Render at ~96px for crisp grid icons
                float docW = svg.getDocumentWidth();
                float docH = svg.getDocumentHeight();
                if (docW <= 0) docW = 24f;
                if (docH <= 0) docH = 24f;
                int size = 96;
                svg.setDocumentWidth(size);
                svg.setDocumentHeight(size);
                Picture picture = svg.renderToPicture();
                PictureDrawable drawable = new PictureDrawable(picture);
                imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                imageView.setImageDrawable(drawable);
                imageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } else {
                // PNG fallback (old pack)
                imageView.setImageBitmap(kh.a(path, 1));
                imageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            }
        } catch (Exception e) {
            try {
                // last resort PNG path
                imageView.setImageBitmap(kh.a(path, 1));
                imageView.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e2) {
                imageView.setImageDrawable(null);
            }
        }
    }


    /**
     * Convert SVG to PNG so parent (ManageImageProjectFragment) can use kh.a.
     */
    private String convertToPngForImport(String srcPath, String iconName, int tintColor) {
        if (srcPath == null) {
            return null;
        }
        String lower = srcPath.toLowerCase(Locale.US);
        if (lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg")
                || lower.endsWith(".webp")) {
            return srcPath;
        }
        try {
            File outDir = new File(getCacheDir(), "import_icons");
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            String safe = iconName != null ? iconName : "icon";
            safe = safe.replaceAll("[^a-zA-Z0-9_]", "_");
            File outFile = new File(outDir, safe + ".png");

            Bitmap bitmap = null;
            if (lower.endsWith(".svg")) {
                FileInputStream fis = new FileInputStream(srcPath);
                SVG svg = SVG.getFromInputStream(fis);
                fis.close();
                if (svg == null) {
                    return srcPath;
                }
                int size = 192;
                svg.setDocumentWidth(size);
                svg.setDocumentHeight(size);
                Picture picture = svg.renderToPicture(size, size);
                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawPicture(picture);
                if (tintColor != 0) {
                    android.graphics.Paint paint = new android.graphics.Paint();
                    paint.setColorFilter(new android.graphics.PorterDuffColorFilter(
                            tintColor, PorterDuff.Mode.SRC_IN));
                    Bitmap tinted = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                    Canvas c2 = new Canvas(tinted);
                    c2.drawBitmap(bitmap, 0, 0, paint);
                    bitmap.recycle();
                    bitmap = tinted;
                }
            } else {
                bitmap = kh.a(srcPath, 1);
            }

            if (bitmap == null) {
                return srcPath;
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return outFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return srcPath;
        }
    }

    private void showSearchDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.import_icon_search_dialog, null);
        final TextInputEditText edit = (TextInputEditText) view.findViewById(R.id.ed_search);
        edit.setText(currentQuery);

        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Search")
                .setView(view)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        if (edit.getText() != null) {
                            currentQuery = edit.getText().toString().trim().toLowerCase(Locale.US);
                        } else {
                            currentQuery = "";
                        }
                        applyFilter(currentQuery);
                    }
                })
                .setNegativeButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        currentQuery = "";
                        applyFilter("");
                    }
                })
                .setNeutralButton("Cancel", null)
                .create();
        dialog.show();
        edit.requestFocus();
    }

    private void showSaveDialog(int position) {
        if (position < 0 || position >= filteredList.size()) {
            Toast.makeText(this, "Select an icon first", Toast.LENGTH_SHORT).show();
            return;
        }
        final Pair item = (Pair) filteredList.get(position);
        final String iconPath = (String) item.second;

        // Safe default: no leading digit, only [a-z0-9_]
        String raw = String.valueOf(item.first).toLowerCase(Locale.US);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_') {
                sb.append(c);
            } else {
                sb.append('_');
            }
        }
        String defaultName = sb.toString();
        if (defaultName.length() == 0) {
            defaultName = "icon";
        }
        // Project names usually cannot start with digit
        char first = defaultName.charAt(0);
        if (first >= '0' && first <= '9') {
            defaultName = "icon_" + defaultName;
        }

        View view = LayoutInflater.from(this).inflate(R.layout.import_icon_save_dialog, null);
        ImageView preview = (ImageView) view.findViewById(R.id.img_preview);
        final TextInputEditText nameEdit = (TextInputEditText) view.findViewById(R.id.ed_name);
        final TextInputLayout nameLayout = (TextInputLayout) view.findViewById(R.id.ti_name);

        nameEdit.setText(defaultName);
        loadIconInto(preview, iconPath, selectedColor);

        // Keep lo for live error UI, but do not hard-block on it
        final lo validator = new lo(getApplicationContext(), nameLayout, fc.b, existingNames);

        final AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setView(view)
                .setPositiveButton("Import", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface di) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = "";
                        if (nameEdit.getText() != null) {
                            name = nameEdit.getText().toString().trim();
                        }
                        if (name.length() == 0) {
                            nameLayout.setError("Enter icon name");
                            Toast.makeText(ImportIconActivity.this, "Enter icon name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Basic format check
                        char c0 = name.charAt(0);
                        if (c0 >= '0' && c0 <= '9') {
                            nameLayout.setError("Name cannot start with number");
                            Toast.makeText(ImportIconActivity.this,
                                    "Name cannot start with number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (int i = 0; i < name.length(); i++) {
                            char c = name.charAt(i);
                            boolean okChar = (c >= 'a' && c <= 'z')
                                    || (c >= 'A' && c <= 'Z')
                                    || (c >= '0' && c <= '9')
                                    || c == '_';
                            if (!okChar) {
                                nameLayout.setError("Only letters, numbers, underscore");
                                Toast.makeText(ImportIconActivity.this,
                                        "Only letters, numbers, underscore", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        // Duplicate
                        if (existingNames != null) {
                            for (int i = 0; i < existingNames.size(); i++) {
                                Object n = existingNames.get(i);
                                if (n != null && name.equalsIgnoreCase(String.valueOf(n))) {
                                    nameLayout.setError("Name already exists");
                                    Toast.makeText(ImportIconActivity.this,
                                            "Name already exists", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                        // Soft project validator (show warning but still allow)
                        try {
                            if (!validator.a()) {
                                Toast.makeText(ImportIconActivity.this,
                                        "Validator warning, importing anyway", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ignored) {
                        }

                        // Parent expects PNG path (kh.a / Glide) — convert SVG first
                        String pngPath = convertToPngForImport(iconPath, name, selectedColor);
                        if (pngPath == null || pngPath.length() == 0) {
                            Toast.makeText(ImportIconActivity.this,
                                    "Failed to convert icon", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent result = new Intent();
                        result.putExtra("iconName", name);
                        result.putExtra("iconPath", pngPath);
                        result.putExtra("iconStyle", STYLE_FOLDERS[selectedStyle]);
                        result.putExtra("iconColor", selectedColor);
                        setResult(RESULT_OK, result);
                        dialog.dismiss();
                        finish();
                    }
                });
            }
        });

        dialog.show();
        nameEdit.requestFocus();
    }

    private void reloadIcons() {
        new LoadIconsTask(getApplicationContext()).execute();
    }

    private void refreshIconList() {
        iconList.clear();
        String styleFolder = STYLE_FOLDERS[selectedStyle];
        String dirPath = fe.p() + File.separator + "material3_icon_pack" + File.separator + styleFolder;
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            dirPath = fe.p() + File.separator + "icon_black";
            dir = new File(dirPath);
        }

        String[] files = dir.list();
        if (files == null) {
            return;
        }

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String lower = fileName.toLowerCase(Locale.US);
            if (!lower.endsWith(".svg") && !lower.endsWith(".png")) {
                continue;
            }
            String base = fileName;
            int dot = fileName.lastIndexOf('.');
            if (dot > 0) {
                base = fileName.substring(0, dot);
            }
            iconList.add(new Pair(base, dirPath + File.separator + fileName));
        }

        Collections.sort(iconList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Pair a = (Pair) o1;
                Pair b = (Pair) o2;
                return ((String) a.first).compareToIgnoreCase((String) b.first);
            }
        });
    }

    private void applyFilter(String query) {
        filteredList.clear();
        if (query == null || query.length() == 0) {
            filteredList.addAll(iconList);
        } else {
            for (int i = 0; i < iconList.size(); i++) {
                Pair p = (Pair) iconList.get(i);
                if (((String) p.first).toLowerCase(Locale.US).contains(query)) {
                    filteredList.add(p);
                }
            }
        }
        adapter.selectedIndex = -1;
        btnImport.setEnabled(false);
        adapter.notifyDataSetChanged();
    }

    private boolean isAlreadyImported() {
        File dir = new File(fe.p() + File.separator + "material3_icon_pack");
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }
        String[] list = dir.list();
        return list != null && list.length > 0;
    }

    private void extractIconPack() throws Throwable {
        lc.a(this, "icons" + File.separator + "material3_icon_pack.zip", fe.p());
    }

    private int calculateSpanCount() {
        int widthDp = (int) (getResources().getDisplayMetrics().widthPixels
                / getResources().getDisplayMetrics().density);
        int span = widthDp / 72;
        if (span < 4) {
            span = 4;
        }
        return span;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) recyclerView.getLayoutManager())
                    .setSpanCount(calculateSpanCount());
        }
    }

    class ExtractIconsTask extends jq {
        ExtractIconsTask(Context ctx) {
            super(ctx);
            ImportIconActivity.this.a(this);
        }

        @Override
        public void a() {
            publishProgress("Preparing...");
            if (!isAlreadyImported()) {
                try {
                    extractIconPack();
                } catch (Throwable t) {
                    String msg = t.getMessage() != null ? t.getMessage() : t.toString();
                    a(msg);
                }
            }
        }

        @Override
        public void b() {
            reloadIcons();
        }

        @Override
        public void a(String msg) {
        }
    }

    class LoadIconsTask extends jq {
        LoadIconsTask(Context ctx) {
            super(ctx);
            ImportIconActivity.this.a(this);
        }

        @Override
        public void a() {
            publishProgress("Loading...");
            refreshIconList();
        }

        @Override
        public void b() {
            applyFilter(currentQuery);
        }

        @Override
        public void a(String msg) {
        }
    }

    class IconAdapter extends RecyclerView.Adapter {
        int selectedIndex = -1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.import_icon_list_item, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
            VH h = (VH) holder;
            boolean sel = (pos == selectedIndex);
            Pair item = (Pair) filteredList.get(pos);

            int bg;
            if (sel) {
                bg = MaterialColors.getColor(ImportIconActivity.this,
                        com.google.android.material.R.attr.colorPrimaryContainer, 0xFFE8DEF8);
            } else {
                bg = MaterialColors.getColor(ImportIconActivity.this,
                        com.google.android.material.R.attr.colorSurfaceContainerHighest, 0xFFE6E0E9);
            }

            h.card.setCardBackgroundColor(bg);
            h.card.setStrokeWidth(sel ? 2 : 0);
            if (sel) {
                h.card.setStrokeColor(MaterialColors.getColor(ImportIconActivity.this,
                        com.google.android.material.R.attr.colorPrimary, Color.BLACK));
            }
            h.name.setText((String) item.first);
            if (sel) {
                h.name.setTextColor(MaterialColors.getColor(ImportIconActivity.this,
                        com.google.android.material.R.attr.colorOnPrimaryContainer, Color.BLACK));
            } else {
                h.name.setTextColor(MaterialColors.getColor(ImportIconActivity.this,
                        com.google.android.material.R.attr.colorOnSurfaceVariant, Color.GRAY));
            }

            loadIconInto(h.icon, (String) item.second, selectedColor);
        }

        @Override
        public int getItemCount() {
            return filteredList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            MaterialCardView card;
            ImageView icon;
            TextView name;

            VH(View v) {
                super(v);
                card = (MaterialCardView) v.findViewById(R.id.card_icon);
                icon = (ImageView) v.findViewById(R.id.img);
                name = (TextView) v.findViewById(R.id.tv_icon_name);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ki.a()) {
                            return;
                        }
                        int prev = selectedIndex;
                        selectedIndex = getLayoutPosition();
                        notifyItemChanged(selectedIndex);
                        if (prev >= 0) {
                            notifyItemChanged(prev);
                        }
                        btnImport.setEnabled(true);
                    }
                });
            }
        }
    }
}
