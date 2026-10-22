package com.nexusteam.blacklogics.design;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.nexusteam.blacklogics.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AppCompat Injection manager.
 *
 * Edits the files stored at
 *   <base>/<sc_id>/Injection/appcompat/<layoutName>      (no extension)
 *
 * File format (JSON array, byte-compatible with the generator's Gson output):
 *   [{"type":"toolbar","value":"android:layout_width=\"0dp\""}, ...]
 *
 * Intent extras:
 *   "sc_id"          project id (builds the default folder)
 *   "injection_dir"  (optional) full folder path, overrides BASE_PATH + sc_id
 *   "layout_name"    (optional) open this layout straight in the editor
 *
 * Theme: @style/Theme.AppCompatManager (Material 3 DayNight, NoActionBar).
 */
public class AppCompatManagerActivity extends AppCompatActivity {

    /** Set false to always use the static palette from am_colors.xml. */
    private static final boolean USE_DYNAMIC_COLORS = true;

    /** Change this if your Injection folder lives somewhere else. */
    private static final String BASE_PATH = "/storage/emulated/0/.blacklogics/data/";

    private static final String[] DEFAULT_TYPES = {
            "toolbar", "appbarlayout", "coordinatorlayout", "drawerlayout", "navigationdrawer",
            "fab", "collapsingtoolbarlayout", "tablayout", "bottomnavigationview", "nestedscrollview"
    };

    private static final String[] COMMON = {
            "android:layout_width", "android:layout_height", "android:background",
            "android:visibility", "android:padding", "android:layout_margin",
            "android:elevation", "android:theme", "android:fitsSystemWindows"
    };

    private static final Map<String, String[]> SUGGESTIONS = new HashMap<String, String[]>();

    static {
        SUGGESTIONS.put("toolbar", new String[]{
                "android:layout_width", "android:layout_height", "android:background",
                "android:visibility", "android:paddingTop", "android:elevation", "android:theme",
                "app:popupTheme", "app:title", "app:subtitle", "app:titleTextColor",
                "app:navigationIcon", "app:layout_scrollFlags"});
        SUGGESTIONS.put("appbarlayout", new String[]{
                "android:layout_width", "android:layout_height", "android:theme",
                "android:visibility", "android:background", "android:elevation",
                "android:fitsSystemWindows", "app:liftOnScroll", "app:elevation"});
        SUGGESTIONS.put("coordinatorlayout", new String[]{
                "android:layout_width", "android:layout_height", "android:background",
                "android:fitsSystemWindows"});
        SUGGESTIONS.put("drawerlayout", new String[]{
                "android:layout_width", "android:layout_height", "android:background",
                "android:fitsSystemWindows", "tools:openDrawer"});
        SUGGESTIONS.put("navigationdrawer", new String[]{
                "android:layout_width", "android:layout_height", "android:layout_gravity",
                "android:background", "android:elevation", "android:fitsSystemWindows"});
        SUGGESTIONS.put("fab", new String[]{
                "android:layout_width", "android:layout_height", "android:layout_gravity",
                "android:layout_margin", "android:src", "app:backgroundTint", "app:tint",
                "app:elevation", "app:fabSize"});
        SUGGESTIONS.put("collapsingtoolbarlayout", new String[]{
                "android:layout_width", "android:layout_height", "app:contentScrim",
                "app:layout_scrollFlags", "app:title", "app:expandedTitleMarginStart"});
        SUGGESTIONS.put("tablayout", new String[]{
                "android:layout_width", "android:layout_height", "app:tabMode",
                "app:tabGravity", "app:tabIndicatorColor", "app:tabTextColor"});
        SUGGESTIONS.put("bottomnavigationview", new String[]{
                "android:layout_width", "android:layout_height", "android:background",
                "app:menu", "app:labelVisibilityMode", "app:itemIconTint", "app:itemTextColor"});
        SUGGESTIONS.put("nestedscrollview", new String[]{
                "android:layout_width", "android:layout_height", "android:fillViewport",
                "app:layout_behavior"});
    }

    /** {type, attribute name, value} */
    private static final String[][] DEFAULTS = {
            {"toolbar", "android:layout_width", "0dp"},
            {"toolbar", "android:layout_height", "0dp"},
            {"toolbar", "android:background", "?attr/colorPrimary"},
            {"toolbar", "app:popupTheme", "@style/AppTheme.PopupOverlay"},
            {"appbarlayout", "android:layout_width", "0dp"},
            {"appbarlayout", "android:layout_height", "0dp"},
            {"appbarlayout", "android:theme", "@style/AppTheme.AppBarOverlay"},
            {"coordinatorlayout", "android:layout_width", "match_parent"},
            {"coordinatorlayout", "android:layout_height", "match_parent"},
            {"drawerlayout", "android:layout_width", "match_parent"},
            {"drawerlayout", "android:layout_height", "match_parent"},
            {"drawerlayout", "tools:openDrawer", "start"},
            {"navigationdrawer", "android:layout_width", "320dp"},
            {"navigationdrawer", "android:layout_height", "match_parent"},
            {"navigationdrawer", "android:layout_gravity", "start"},
            {"navigationdrawer", "android:background", "?attr/colorSurface"},
            {"toolbar", "android:visibility", "gone"},
            {"appbarlayout", "android:visibility", "gone"},
            {"toolbar", "android:paddingTop", "0dp"}
    };

    // ------------------------------------------------------------------ models

    private static class Entry {
        String type;
        String name;
        String value;

        Entry(String type, String name, String value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }
    }

    private static class LayoutInfo {
        File file;
        String name;
        String raw = "";
        int attrCount;
        int typeCount;
        boolean broken;
    }

    // ------------------------------------------------------------------ state

    private final List<Entry> entries = new ArrayList<Entry>();
    private final List<Entry> visibleAttrs = new ArrayList<Entry>();
    private final List<LayoutInfo> layoutInfos = new ArrayList<LayoutInfo>();
    private final Set<String> extraTypes = new LinkedHashSet<String>();

    private File injectionDir;
    private String currentLayout;            // null => layout list screen
    private String selectedType = "toolbar";

    private View root;
    private AppBarLayout appBar;
    private MaterialToolbar toolbar;
    private View chipsScroll;
    private ChipGroup chipGroup;
    private RecyclerView recycler;
    private View emptyState;
    private android.widget.ImageView emptyIcon;
    private TextView emptyTitle;
    private TextView emptyMessage;
    private ExtendedFloatingActionButton fab;

    private LayoutAdapter layoutAdapter;
    private AttrAdapter attrAdapter;

    // ------------------------------------------------------------------ lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (USE_DYNAMIC_COLORS) DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);

        String custom = getIntent().getStringExtra("injection_dir");
        String scId = getIntent().getStringExtra("sc_id");
        injectionDir = new File(custom != null ? custom : BASE_PATH + scId + "/Injection/appcompat");

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        boolean night = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        WindowInsetsControllerCompat bars = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        bars.setAppearanceLightStatusBars(!night);
        bars.setAppearanceLightNavigationBars(!night);

        setContentView(R.layout.appcompat_manager);

        root = findViewById(R.id.root);
        appBar = findViewById(R.id.app_bar);
        toolbar = findViewById(R.id.toolbar);
        chipsScroll = findViewById(R.id.chips_scroll);
        chipGroup = findViewById(R.id.chip_group);
        recycler = findViewById(R.id.recycler);
        emptyState = findViewById(R.id.empty_state);
        emptyIcon = findViewById(R.id.empty_icon);
        emptyTitle = findViewById(R.id.empty_title);
        emptyMessage = findViewById(R.id.empty_message);
        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        layoutAdapter = new LayoutAdapter();
        attrAdapter = new AttrAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                if (dy > 4) fab.shrink();
                else if (dy < -4) fab.extend();
            }
        });

        // edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(root, new OnApplyWindowInsetsListener() {
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat insets) {
                Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                        | WindowInsetsCompat.Type.displayCutout());
                appBar.setPadding(sb.left, sb.top, sb.right, 0);
                recycler.setPadding(dp(16) + sb.left, dp(8), dp(16) + sb.right, dp(96) + sb.bottom);
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                lp.bottomMargin = dp(16) + sb.bottom;
                lp.rightMargin = dp(16) + sb.right;
                lp.leftMargin = dp(16) + sb.left;
                fab.setLayoutParams(lp);
                return insets;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLayout == null) showNewLayoutDialog();
                else showAttrDialog(null);
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBack();
            }
        });

        String direct = getIntent().getStringExtra("layout_name");
        if (direct != null && direct.length() > 0) {
            if (!new File(injectionDir, direct).exists()) writeDefaults(direct);
            openLayout(direct);
        } else {
            render();
        }
    }

    private void goBack() {
        if (currentLayout != null) {
            currentLayout = null;
            entries.clear();
            render();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appcompat_manager_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean editor = currentLayout != null;
        for (int i = 0; i < menu.size(); i++) menu.getItem(i).setVisible(editor);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_type) {
            showAddTypeDialog();
        } else if (id == R.id.action_raw_json) {
            showRawJsonDialog(currentLayout, serialize(true));
        } else if (id == R.id.action_reset) {
            confirmReset();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // ------------------------------------------------------------------ rendering

    private void render() {
        invalidateOptionsMenu();
        if (currentLayout == null) renderList();
        else renderEditor();
    }

    private void renderList() {
        getSupportActionBar().setTitle(R.string.am_title);
        getSupportActionBar().setSubtitle(null);
        chipsScroll.setVisibility(View.GONE);
        fab.setText(R.string.am_new_layout);
        fab.extend();

        loadLayoutInfos();
        recycler.setAdapter(layoutAdapter);
        layoutAdapter.notifyDataSetChanged();

        showEmpty(layoutInfos.isEmpty(), R.drawable.ic_appcompat_layout,
                getString(R.string.am_empty_layouts_title), getString(R.string.am_empty_layouts_msg));
    }

    private void renderEditor() {
        getSupportActionBar().setTitle(currentLayout);
        getSupportActionBar().setSubtitle(getString(R.string.am_subtitle_attrs, entries.size()));
        chipsScroll.setVisibility(View.VISIBLE);
        fab.setText(R.string.am_add_attribute);
        fab.extend();

        List<String> types = allTypes();
        if (!types.contains(selectedType)) selectedType = types.get(0);

        chipGroup.removeAllViews();
        for (final String t : types) {
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.appcompat_manager_chip, chipGroup, false);
            chip.setId(View.generateViewId());
            chip.setText(getString(R.string.am_chip_label, t, countOf(t)));
            chip.setChecked(t.equals(selectedType));
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedType = t;
                    refreshVisibleAttrs();
                }
            });
            chipGroup.addView(chip);
        }

        recycler.setAdapter(attrAdapter);
        refreshVisibleAttrs();
    }

    private void refreshVisibleAttrs() {
        visibleAttrs.clear();
        for (Entry e : entries) {
            if (e.type.equals(selectedType)) visibleAttrs.add(e);
        }
        attrAdapter.notifyDataSetChanged();
        showEmpty(visibleAttrs.isEmpty(), R.drawable.ic_appcompat_tune,
                getString(R.string.am_empty_attrs_title, selectedType), getString(R.string.am_empty_attrs_msg));
    }

    private void showEmpty(boolean empty, int iconRes, String title, String message) {
        emptyState.setVisibility(empty ? View.VISIBLE : View.GONE);
        if (empty) {
            emptyIcon.setImageResource(iconRes);
            emptyTitle.setText(title);
            emptyMessage.setText(message);
        }
    }

    // ------------------------------------------------------------------ adapters

    private class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.VH> {

        class VH extends RecyclerView.ViewHolder {
            final TextView title;
            final TextView subtitle;
            final View delete;

            VH(View v) {
                super(v);
                title = v.findViewById(R.id.tv_title);
                subtitle = v.findViewById(R.id.tv_subtitle);
                delete = v.findViewById(R.id.btn_delete);
            }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.appcompat_manager_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            final LayoutInfo info = layoutInfos.get(position);
            h.title.setText(info.name);
            if (info.broken) {
                h.subtitle.setText(R.string.am_invalid_json);
                h.subtitle.setTextColor(MaterialColors.getColor(h.subtitle,
                        com.google.android.material.R.attr.colorError));
            } else {
                h.subtitle.setText(getString(R.string.am_layout_summary, info.attrCount, info.typeCount));
                h.subtitle.setTextColor(MaterialColors.getColor(h.subtitle,
                        com.google.android.material.R.attr.colorOnSurfaceVariant));
            }
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (info.broken) showRawJsonDialog(info.name, info.raw);
                    else openLayout(info.name);
                }
            });
            h.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDeleteLayout(info.file);
                }
            });
        }

        @Override
        public int getItemCount() {
            return layoutInfos.size();
        }
    }

    private class AttrAdapter extends RecyclerView.Adapter<AttrAdapter.VH> {

        class VH extends RecyclerView.ViewHolder {
            final TextView name;
            final TextView value;
            final View delete;

            VH(View v) {
                super(v);
                name = v.findViewById(R.id.tv_name);
                value = v.findViewById(R.id.tv_value);
                delete = v.findViewById(R.id.btn_delete);
            }
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(getLayoutInflater().inflate(R.layout.appcompat_manager_item_attr, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            final Entry e = visibleAttrs.get(position);
            h.name.setText(e.name);
            h.value.setText(e.value.length() == 0 ? getString(R.string.am_empty_value) : e.value);
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAttrDialog(e);
                }
            });
            h.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteWithUndo(e);
                }
            });
        }

        @Override
        public int getItemCount() {
            return visibleAttrs.size();
        }
    }

    // ------------------------------------------------------------------ dialogs

    private void showNewLayoutDialog() {
        View v = getLayoutInflater().inflate(R.layout.appcompat_manager_dialog_input, null);
        final TextInputLayout til = v.findViewById(R.id.til);
        final TextInputEditText et = v.findViewById(R.id.et);
        til.setHint(R.string.am_hint_layout_name);
        til.setHelperText(getString(R.string.am_helper_layout_name));

        final AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.am_dialog_new_layout_title)
                .setView(v)
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_create, null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text(et);
                if (!name.matches("[A-Za-z0-9_]+")) {
                    til.setError(getString(R.string.am_error_layout_name));
                    return;
                }
                if (!new File(injectionDir, name).exists()) writeDefaults(name);
                dialog.dismiss();
                openLayout(name);
            }
        });
    }

    private void showAddTypeDialog() {
        View v = getLayoutInflater().inflate(R.layout.appcompat_manager_dialog_input, null);
        final TextInputLayout til = v.findViewById(R.id.til);
        final TextInputEditText et = v.findViewById(R.id.et);
        til.setHint(R.string.am_hint_type);
        til.setHelperText(getString(R.string.am_helper_type));

        final AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.am_dialog_add_type_title)
                .setView(v)
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_add, null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = text(et).toLowerCase();
                if (!t.matches("[a-z0-9_]+")) {
                    til.setError(getString(R.string.am_error_type));
                    return;
                }
                extraTypes.add(t);
                selectedType = t;
                dialog.dismiss();
                render();
            }
        });
    }

    private void showAttrDialog(final Entry existing) {
        String[] sug = SUGGESTIONS.get(selectedType);
        if (sug == null) sug = COMMON;

        View v = getLayoutInflater().inflate(R.layout.appcompat_manager_dialog_attr, null);
        final TextInputLayout nameTil = v.findViewById(R.id.til_name);
        final MaterialAutoCompleteTextView nameEt = v.findViewById(R.id.et_name);
        final TextInputEditText valEt = v.findViewById(R.id.et_value);
        final ChipGroup quick = v.findViewById(R.id.quick_values);

        nameEt.setSimpleItems(sug);

        if (existing != null) {
            nameEt.setText(existing.name, false);
            valEt.setText(existing.value);
            rebuildQuickValues(quick, existing.name, valEt);
        }
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {
                nameTil.setError(null);
                rebuildQuickValues(quick, s.toString(), valEt);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(this)
                .setTitle(existing == null
                        ? getString(R.string.am_dialog_add_attr_title, selectedType)
                        : getString(R.string.am_dialog_edit_attr_title))
                .setView(v)
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_save, null);
        if (existing != null) {
            b.setNeutralButton(R.string.am_delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface d, int w) {
                    deleteWithUndo(existing);
                }
            });
        }
        final AlertDialog dialog = b.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = text(nameEt);
                String val = text(valEt);
                if (name.length() == 0 || name.contains(" ")) {
                    nameTil.setError(getString(R.string.am_error_attr_name));
                    return;
                }
                if (existing != null) {
                    existing.name = name;
                    existing.value = val;
                } else {
                    Entry dup = find(selectedType, name);
                    if (dup != null) dup.value = val;
                    else entries.add(new Entry(selectedType, name, val));
                }
                dialog.dismiss();
                commit();
            }
        });
    }

    private void rebuildQuickValues(ChipGroup group, String attrName, final TextInputEditText target) {
        group.removeAllViews();
        String[] vals = quickValues(attrName);
        group.setVisibility(vals.length == 0 ? View.GONE : View.VISIBLE);
        for (final String val : vals) {
            Chip c = (Chip) getLayoutInflater().inflate(R.layout.appcompat_manager_chip_assist, group, false);
            c.setText(val);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    target.setText(val);
                    target.setSelection(val.length());
                }
            });
            group.addView(c);
        }
    }

    private static String[] quickValues(String name) {
        String n = name == null ? "" : name.trim();
        if (n.endsWith("layout_width") || n.endsWith("layout_height"))
            return new String[]{"match_parent", "wrap_content", "0dp"};
        if (n.endsWith("visibility")) return new String[]{"visible", "invisible", "gone"};
        if (n.endsWith("gravity")) return new String[]{"start", "end", "center", "top", "bottom"};
        if (n.endsWith("background") || n.endsWith("backgroundTint") || n.endsWith(":tint")
                || n.endsWith("contentScrim"))
            return new String[]{"?attr/colorPrimary", "?attr/colorSurface",
                    "?attr/colorSurfaceContainer", "?attr/colorSecondaryContainer"};
        if (n.endsWith("fitsSystemWindows") || n.endsWith("liftOnScroll") || n.endsWith("fillViewport"))
            return new String[]{"true", "false"};
        if (n.endsWith("openDrawer")) return new String[]{"start", "end"};
        if (n.endsWith("tabMode")) return new String[]{"fixed", "scrollable"};
        if (n.endsWith("labelVisibilityMode"))
            return new String[]{"auto", "labeled", "selected", "unlabeled"};
        return new String[0];
    }

    /**
     * Raw JSON editor. layoutName is where the result is saved; also used to repair a file that
     * failed to parse (then it is not the current layout yet).
     */
    private void showRawJsonDialog(final String layoutName, String initial) {
        View v = getLayoutInflater().inflate(R.layout.appcompat_manager_dialog_json, null);
        final TextInputLayout til = v.findViewById(R.id.til_json);
        final TextInputEditText et = v.findViewById(R.id.et_json);
        et.setText(initial);

        final AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.am_dialog_raw_title)
                .setView(v)
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_apply, null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Entry> parsed = new ArrayList<Entry>();
                try {
                    parse(et.getText() == null ? "" : et.getText().toString(), parsed);
                } catch (JSONException ex) {
                    til.setError(getString(R.string.am_error_json, ex.getMessage()));
                    return;
                }
                entries.clear();
                entries.addAll(parsed);
                currentLayout = layoutName;
                dialog.dismiss();
                commit();
            }
        });
    }

    private void confirmReset() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.am_reset_title)
                .setMessage(getString(R.string.am_reset_msg, currentLayout))
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        entries.clear();
                        addDefaults(entries);
                        commit();
                    }
                }).show();
    }

    private void confirmDeleteLayout(final File f) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.am_delete_layout_title, f.getName()))
                .setMessage(R.string.am_delete_layout_msg)
                .setNegativeButton(R.string.am_cancel, null)
                .setPositiveButton(R.string.am_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int w) {
                        //noinspection ResultOfMethodCallIgnored
                        f.delete();
                        render();
                    }
                }).show();
    }

    private void deleteWithUndo(final Entry e) {
        final int index = entries.indexOf(e);
        if (index < 0) return;
        entries.remove(index);
        commit();
        Snackbar.make(root, getString(R.string.am_attr_deleted, e.name), Snackbar.LENGTH_LONG)
                .setAnchorView(fab)
                .setAction(R.string.am_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entries.add(Math.min(index, entries.size()), e);
                        commit();
                    }
                }).show();
    }

    // ------------------------------------------------------------------ data

    private void loadLayoutInfos() {
        layoutInfos.clear();
        File[] files = injectionDir.listFiles();
        if (files == null) return;
        Arrays.sort(files);
        for (File f : files) {
            if (!f.isFile()) continue;
            LayoutInfo info = new LayoutInfo();
            info.file = f;
            info.name = f.getName();
            try {
                info.raw = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                List<Entry> tmp = new ArrayList<Entry>();
                parse(info.raw, tmp);
                Set<String> types = new HashSet<String>();
                for (Entry e : tmp) types.add(e.type);
                info.attrCount = tmp.size();
                info.typeCount = types.size();
            } catch (Exception ex) {
                info.broken = true;
            }
            layoutInfos.add(info);
        }
    }

    private void openLayout(String name) {
        File f = new File(injectionDir, name);
        List<Entry> parsed = new ArrayList<Entry>();
        String raw = "";
        try {
            raw = f.exists() ? new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8) : "";
            parse(raw, parsed);
        } catch (Exception ex) {
            showRawJsonDialog(name, raw);
            return;
        }
        entries.clear();
        entries.addAll(parsed);
        currentLayout = name;
        render();
    }

    private void writeDefaults(String name) {
        String saved = currentLayout;
        List<Entry> backup = new ArrayList<Entry>(entries);
        currentLayout = name;
        entries.clear();
        addDefaults(entries);
        save();
        entries.clear();
        entries.addAll(backup);
        currentLayout = saved;
    }

    private static void addDefaults(List<Entry> out) {
        for (String[] d : DEFAULTS) out.add(new Entry(d[0], d[1], d[2]));
    }

    /** Save to disk and refresh the screen. */
    private void commit() {
        save();
        render();
    }

    private void save() {
        if (currentLayout == null) return;
        try {
            //noinspection ResultOfMethodCallIgnored
            injectionDir.mkdirs();
            Files.write(new File(injectionDir, currentLayout).toPath(),
                    serialize(false).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Toast.makeText(this, getString(R.string.am_save_failed, ex.getMessage()), Toast.LENGTH_LONG).show();
        }
    }

    private static void parse(String json, List<Entry> out) throws JSONException {
        String s = json.trim();
        if (s.length() == 0) return;
        JSONArray arr = new JSONArray(s);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            String type = o.getString("type");
            String raw = o.getString("value");
            int eq = raw.indexOf('=');
            String name;
            String val;
            if (eq < 0) {
                name = raw.trim();
                val = "";
            } else {
                name = raw.substring(0, eq).trim();
                val = raw.substring(eq + 1).trim();
                if (val.length() >= 2 && val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }
                val = val.replace("&quot;", "\"");
            }
            out.add(new Entry(type, name, val));
        }
    }

    /** Same escaping style Gson uses (=, <, >, &, ' as \\uXXXX), so files stay byte-compatible. */
    private String serialize(boolean pretty) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            if (i > 0) sb.append(',');
            if (pretty) sb.append("\n  ");
            sb.append("{\"type\":\"").append(esc(e.type))
                    .append("\",\"value\":\"")
                    .append(esc(e.name + "=\"" + e.value.replace("\"", "&quot;") + "\""))
                    .append("\"}");
        }
        if (pretty && !entries.isEmpty()) sb.append('\n');
        return sb.append(']').toString();
    }

    private static String esc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                case '=': sb.append("\\u003d"); break;
                case '<': sb.append("\\u003c"); break;
                case '>': sb.append("\\u003e"); break;
                case '&': sb.append("\\u0026"); break;
                case '\'': sb.append("\\u0027"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    private Entry find(String type, String name) {
        for (Entry e : entries) {
            if (e.type.equals(type) && e.name.equals(name)) return e;
        }
        return null;
    }

    private int countOf(String type) {
        int n = 0;
        for (Entry e : entries) {
            if (e.type.equals(type)) n++;
        }
        return n;
    }

    private List<String> allTypes() {
        Set<String> s = new LinkedHashSet<String>(Arrays.asList(DEFAULT_TYPES));
        for (Entry e : entries) s.add(e.type);
        s.addAll(extraTypes);
        return new ArrayList<String>(s);
    }

    // ------------------------------------------------------------------ helpers

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }

    private static String text(TextView tv) {
        return tv.getText() == null ? "" : tv.getText().toString().trim();
    }
}
