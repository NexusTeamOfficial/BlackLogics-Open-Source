package com.nexusteam.blacklogics.manage.view;

import com.nexusteam.internal.ez;
import com.nexusteam.internal.fc;
import com.nexusteam.internal.jy;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ActivityNameValidator;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.internal.lib.base.BaseDialogActivity;
import com.nexusteam.internal.lib.ui.SelectableButtonBar;
import com.nexusteam.blacklogics.generator.source.model.ActivityConfig;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import java.util.ArrayList;

public class AddViewActivity extends BaseDialogActivity {

    ArrayList<a> f1352a;
    RecyclerView b;
    b c;
    SelectableButtonBar d;
    SelectableButtonBar e;
    SelectableButtonBar typeBar;
    LinearLayout f;
    LinearLayout g;
    LinearLayout h;
    RelativeLayout i;
    ImageView j;
    ImageView k;
    TextInputLayout l;
    EditText m;
    ActivityNameValidator n;
    TextView o;
    ArrayList<String> p;
    boolean q;
    boolean r;
    boolean s;
    boolean t;
    int u;
    ProjectFileBean v;
    String w;
    String scId;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.manage_screen_activity_add_temp);
        a(kq.a().a(getApplicationContext(), R.string.design_manager_view_title_add_view));

        Intent intent = getIntent();

        // ---- STRICT sc_id: fail fast if missing ----
        String rawScId = intent.getStringExtra("sc_id");
      /*  if (rawScId == null || rawScId.trim().isEmpty()) {
            android.util.Log.e("AddViewActivity",
                    "missing sc_id — aborting to avoid stray registry files");
            finish();
            return;
        }*/
        this.scId = rawScId.trim();

        this.p = intent.getStringArrayListExtra("screen_names");
        if (this.p == null) this.p = new ArrayList<>();
        try {
            com.nexusteam.internal.ly ly = com.nexusteam.internal.ma.b(this.scId);
            if (ly != null && ly.a() != null) {
                for (com.nexusteam.internal.beans.ProjectFileBean bean : ly.a()) {
                    if (bean != null && bean.fileName != null && !this.p.contains(bean.fileName)) {
                        this.p.add(bean.fileName);
                    }
                }
            }
        } catch (Throwable ignore) {}

        this.u = intent.getIntExtra("request_code", 264);
        this.v = (ProjectFileBean) intent.getParcelableExtra("project_file");
        if (this.v != null) {
            a(kq.a().a(getApplicationContext(), R.string.design_manager_view_title_edit_view));
        }

        this.d = (SelectableButtonBar) findViewById(R.id.btnbar_orientation);
        this.e = (SelectableButtonBar) findViewById(R.id.btnbar_keyboard);
        this.typeBar = (SelectableButtonBar) findViewById(R.id.btnbar_activity_type);
        this.f = (LinearLayout) findViewById(R.id.preview_statusbar);
        this.g = (LinearLayout) findViewById(R.id.preview_toolbar);
        this.h = (LinearLayout) findViewById(R.id.preview_drawer);
        this.i = (RelativeLayout) findViewById(R.id.preview_keyboard);
        this.j = (ImageView) findViewById(R.id.preview_fab);
        this.k = (ImageView) findViewById(R.id.img_keyboard);
        this.l = (TextInputLayout) findViewById(R.id.ti_name);
        this.m = (EditText) findViewById(R.id.ed_name);
        this.o = (TextView) findViewById(R.id.tv_warning);
        this.o.setVisibility(8);
        this.o.setText(kq.a().a(getApplicationContext(), R.string.design_manager_view_message_slow_down));
        this.l.setHint(kq.a().a(this, R.string.design_manager_view_hint_enter_view_name));
        this.m.setPrivateImeOptions("defaultInputmode=english;");

        this.b = (RecyclerView) findViewById(R.id.feature_types);
        this.c = new b();
        this.b.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.b.setHasFixedSize(true);
        this.b.setAdapter(this.c);

        ((TextView) findViewById(R.id.tv_screen_orientation)).setText(kq.a().a(getApplicationContext(), R.string.design_manager_view_title_screen_orientation));
        ((TextView) findViewById(R.id.tv_keyboard)).setText(kq.a().a(getApplicationContext(), R.string.design_manager_view_title_keyboad_settings));

        TextView tvType = (TextView) findViewById(R.id.tv_activity_type);
        if (tvType != null) tvType.setText("Activity Type");

        this.d.a(0, "Portrait");
        this.d.a(1, "Landscape");
        this.d.a(2, "Both");
        this.d.a();

        this.e.a(0, "Unspecified");
        this.e.a(1, "Visible");
        this.e.a(2, "Hidden");
        this.e.a();
        this.e.setListener(new jy() {
            @Override
            public void a(int i) {
                switch (i) {
                    case 0:
                    case 1:
                        AddViewActivity.this.c(AddViewActivity.this.i);
                        break;
                    case 2:
                        AddViewActivity.this.i.animate()
                                .translationY(AddViewActivity.this.k.getMeasuredHeight()).start();
                        break;
                }
            }
        });

        this.typeBar.a(0, "Activity");
        this.typeBar.a(1, "Fragment");
        this.typeBar.a(2, "DialogFragment");
        this.typeBar.a(3, "BottomSheet");
        this.typeBar.a();
        this.typeBar.setSelectedItemByKey(0);

        b(kq.a().a(getApplicationContext(), R.string.common_word_add));
        c(kq.a().a(getApplicationContext(), R.string.common_word_cancel));

        View _ok = findViewById(R.id.common_dialog_ok_button);
        if (_ok != null) _ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ---------- EDIT path ----------
                if (AddViewActivity.this.u == 265) {
                    AddViewActivity.this.v.orientation     = AddViewActivity.this.d.getSelectedItemKey();
                    AddViewActivity.this.v.keyboardSetting = AddViewActivity.this.e.getSelectedItemKey();
                    int i = AddViewActivity.this.r ? 1 : 0;
                    if (!AddViewActivity.this.q) i |= 2;
                    if (AddViewActivity.this.s)  i |= 8;
                    if (AddViewActivity.this.t)  i |= 4;
                    AddViewActivity.this.v.options = i;

                    ActivityType chosenType = mapTypeIndex(typeBar.getSelectedItemKey());
                    applyTypeToRegistry(AddViewActivity.this.v.fileName, chosenType);

                    Intent intent2 = new Intent();
                    intent2.putExtra("project_file", AddViewActivity.this.v);
                    AddViewActivity.this.setResult(-1, intent2);
                    ke.a(AddViewActivity.this.getApplicationContext(),
                            kq.a().a(AddViewActivity.this.getApplicationContext(),
                                    R.string.design_manager_message_edit_complete), 0).show();
                    AddViewActivity.this.finish();
                    return;
                }

                // ---------- ADD path ----------
                if (AddViewActivity.this.a(AddViewActivity.this.n)) {
                    ProjectFileBean projectFileBean = new ProjectFileBean(
                            0,
                            AddViewActivity.this.m.getText().toString(),
                            AddViewActivity.this.d.getSelectedItemKey(),
                            AddViewActivity.this.e.getSelectedItemKey(),
                            AddViewActivity.this.r,
                            !AddViewActivity.this.q,
                            AddViewActivity.this.s,
                            AddViewActivity.this.t);

                    ActivityType chosenType = mapTypeIndex(typeBar.getSelectedItemKey());
                    applyTypeToRegistry(projectFileBean.fileName, chosenType);

                    Intent intent3 = new Intent();
                    intent3.putExtra("project_file", projectFileBean);
                    if (AddViewActivity.this.w != null) {
                        intent3.putExtra("preset_views", AddViewActivity.this.e(AddViewActivity.this.w));
                    }
                    AddViewActivity.this.setResult(-1, intent3);
                    ke.a(AddViewActivity.this.getApplicationContext(),
                            kq.a().a(AddViewActivity.this.getApplicationContext(),
                                    R.string.design_manager_message_add_complete), 0).show();
                    AddViewActivity.this.finish();
                }
            }
        });

        View _cancel = findViewById(R.id.common_dialog_cancel_button);
        if (_cancel != null) _cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddViewActivity.this.setResult(0);
                AddViewActivity.this.finish();
            }
        });

        if (this.u == 265) {
            this.n = new ActivityNameValidator(getApplicationContext(), this.l, fc.b,
                    new ArrayList(), this.v.fileName);
            this.m.setText(this.v.fileName);
            this.m.setEnabled(false);
            this.m.setBackgroundResource(R.color.transparent);
            b(this.v.options);
            this.d.setSelectedItemByKey(this.v.orientation);
            this.e.setSelectedItemByKey(this.v.keyboardSetting);

            ActivityConfig cfg = ActivityTypeRegistry.getInstance()
                    .getActivityConfig(this.v.fileName);
            ActivityType existingType = (cfg != null) ? cfg.getType() : ActivityType.ACTIVITY;
            this.typeBar.setSelectedItemByKey(reverseTypeIndex(existingType));

            TextView _saveLabel = findViewById(R.id.common_dialog_ok_button);
            if (_saveLabel != null) {
                _saveLabel.setText(kq.a().a(getApplicationContext(),
                        R.string.common_word_save).toUpperCase());
            }
        } else {
            this.r = true;
            this.q = true;
            this.n = new ActivityNameValidator(getApplicationContext(), this.l, fc.b,
                    this.p != null ? this.p : new ArrayList<String>());
        }
        c();
    }

    /** Register chosen type under scId, guaranteed non-empty. */
    private void applyTypeToRegistry(String fileName, ActivityType type) {
        if (this.scId == null || this.scId.trim().isEmpty()) {
            android.util.Log.e("AddViewActivity",
                    "applyTypeToRegistry: missing scId, skipping");
            return;
        }
        try {
            ActivityTypeRegistry reg = ActivityTypeRegistry.getInstance();
            ActivityConfig cfg = reg.getActivityConfig(fileName);
            if (cfg == null) {
                reg.registerActivity(fileName, type, fileName);
            } else {
                cfg.setType(type);
                reg.registerActivity(cfg);   // re-index aliases under same object
            }
            reg.save(this, this.scId);
            android.util.Log.i("AddViewActivity", "type saved file=" + fileName + " type=" + type);
        } catch (Throwable t) {
            android.util.Log.e("AddViewActivity", "applyTypeToRegistry failed", t);
        }
    }

    private static ActivityType mapTypeIndex(int idx) {
        switch (idx) {
            case 1:  return ActivityType.FRAGMENT_ACTIVITY;
            case 2:  return ActivityType.DIALOG_FRAGMENT;
            case 3:  return ActivityType.BOTTOM_SHEET_DIALOG_FRAGMENT;
            case 0:
            default: return ActivityType.ACTIVITY;
        }
    }

    private static int reverseTypeIndex(ActivityType t) {
        if (t == null) return 0;
        switch (t) {
            case FRAGMENT_ACTIVITY:            return 1;
            case DIALOG_FRAGMENT:              return 2;
            case BOTTOM_SHEET_DIALOG_FRAGMENT: return 3;
            default:                           return 0;
        }
    }

    @Override public void onResume() { super.onResume(); }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 276 && i2 == -1) {
            ProjectFileBean projectFileBean =
                    (ProjectFileBean) intent.getParcelableExtra("preset_data");
            this.w = projectFileBean.presetName;
            b(projectFileBean.options);
            c();
        }
    }

    private void b(int i) {
        this.r = (i & 1) == 1;
        this.q = (i & 2) != 2;
        this.s = (i & 8) == 8;
        this.t = (i & 4) == 4;
    }

    protected boolean a(ActivityNameValidator lqVar) { return lqVar.a(); }

    private void c() {
        this.f1352a = new ArrayList<>();
        this.f1352a.add(new a(0, R.drawable.ic_statusbar_color_48dp, "StatusBar", this.q));
        this.f1352a.add(new a(1, R.drawable.ic_toolbar_color_48dp,   "Toolbar",   this.r));
        this.f1352a.add(new a(2, R.drawable.ic_drawer_color_48dp,    "Drawer",    this.t));
        this.f1352a.add(new a(3, R.drawable.fab_color,               "FAB",       this.s));
        this.c.notifyDataSetChanged();
    }

    public void a(boolean z) {
        for (int i = 0; i < this.f1352a.size(); i++) {
            a aVar = this.f1352a.get(i);
            if (aVar.f1356a == 1) { aVar.d = z; this.c.notifyItemChanged(i); return; }
        }
    }

    public void b(boolean z) {
        for (int i = 0; i < this.f1352a.size(); i++) {
            a aVar = this.f1352a.get(i);
            if (aVar.f1356a == 2) { aVar.d = z; this.c.notifyItemChanged(i); return; }
        }
    }

    private void a(View view) { view.animate().translationY(-view.getMeasuredHeight()).start(); }
    private void b(View view) { view.animate().translationY( view.getMeasuredHeight()).start(); }
    public  void c(View view) { view.animate().translationY(0.0f).start(); }
    private void d(View view) { view.animate().translationX(-view.getMeasuredWidth()).start(); }
    private void e(View view) { view.animate().translationX(0.0f).start(); }

    private void d() {
        this.g.animate().translationY(
                -(this.f.getMeasuredHeight() + this.g.getMeasuredHeight())).start();
    }

    public void a(a aVar) {
        switch (aVar.f1356a) {
            case 0:
                if (aVar.d) {
                    c(this.f);
                    if (this.r) c(this.g);
                } else {
                    a(this.f);
                    if (this.r) this.g.animate().translationY(-this.f.getMeasuredHeight()).start();
                    else d();
                }
                break;
            case 1:
                if (aVar.d) {
                    if (!this.q) this.g.animate().translationY(-this.f.getMeasuredHeight()).start();
                    else c(this.g);
                } else if (!this.q) d();
                else a(this.g);
                break;
            case 2:
                if (aVar.d) e(this.h); else d(this.h);
                break;
            case 3:
                if (aVar.d) c(this.j); else b(this.j);
                break;
        }
    }

    public ArrayList<ViewBean> e(String str) { return ez.a(str); }

    class b extends RecyclerView.Adapter<b.FeatureVH> {

        int f1357a = -1;
        boolean b;

        b() {}

        @Override
        public FeatureVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewA = kp.a(viewGroup.getContext(),
                    R.layout.manage_screen_activity_add_feature_item);
            viewA.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new FeatureVH(viewA);
        }

        @Override
        public void onBindViewHolder(FeatureVH aVar, int i) {
            this.b = true;
            a aVar2 = AddViewActivity.this.f1352a.get(i);
            aVar.f1358a.setImageResource(aVar2.b);
            aVar.b.setText(aVar2.c);
            aVar.c.setChecked(aVar2.d);
            if (aVar2.f1356a == 0)      AddViewActivity.this.q = aVar2.d;
            else if (aVar2.f1356a == 1) AddViewActivity.this.r = aVar2.d;
            else if (aVar2.f1356a == 3) AddViewActivity.this.s = aVar2.d;
            else if (aVar2.f1356a == 2) AddViewActivity.this.t = aVar2.d;
            if (AddViewActivity.this.s || AddViewActivity.this.t) {
                AddViewActivity.this.o.setVisibility(0);
            }
            AddViewActivity.this.a(aVar2);
            this.b = false;
        }

        @Override
        public int getItemCount() { return AddViewActivity.this.f1352a.size(); }

        public class FeatureVH extends RecyclerView.ViewHolder {
            ImageView f1358a;
            TextView b;
            CheckBox c;

            public FeatureVH(View view) {
                super(view);
                this.f1358a = (ImageView) view.findViewById(R.id.img_icon);
                this.b = (TextView) view.findViewById(R.id.tv_name);
                this.c = (CheckBox) view.findViewById(R.id.checkbox);
                this.c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (b.this.b) return;
                        b.this.f1357a = FeatureVH.this.getLayoutPosition();
                        a aVar = AddViewActivity.this.f1352a.get(b.this.f1357a);
                        AddViewActivity.this.o.setVisibility(8);
                        aVar.d = z;
                        if (aVar.f1356a == 2 && aVar.d) AddViewActivity.this.a(true);
                        else if (aVar.f1356a == 1 && !aVar.d) AddViewActivity.this.b(false);
                        b.this.notifyItemChanged(b.this.f1357a);
                    }
                });
            }
        }
    }

    class a {
        int f1356a;
        int b;
        String c;
        boolean d;

        public a(int i, int i2, String str, boolean z) {
            this.f1356a = i;
            this.b = i2;
            this.c = str;
            this.d = z;
        }
    }
}