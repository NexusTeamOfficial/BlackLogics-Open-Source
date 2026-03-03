package com.nexusteam.internal.editor;
import com.nexusteam.blacklogics.R;

import com.nexusteam.internal.fp;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.lib.ui.CustomScrollView;

import java.util.ArrayList;

public class LogicEditorDrawer extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    LinearLayout f1061a;
    CustomScrollView b;
    private CardView c;

    public LogicEditorDrawer(Context context) {
        super(context);
        a(context);
    }

    public LogicEditorDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    private void a(Context context) {
        kp.a(context, this, R.layout.logic_editor_drawer);
        ((TextView) findViewById(R.id.tv_block_collection)).setText(kq.a().a(getContext(), R.string.logic_editor_title_block_collection));
        this.f1061a = (LinearLayout) findViewById(R.id.layout_favorite);
        this.b = (CustomScrollView) findViewById(R.id.scv);
        this.c = (CardView) findViewById(R.id.cv_shared_blocks);
        this.c.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LogicEditorDrawer.this.b();
            }
        });
        ((TextView) findViewById(R.id.tv_shared_blocks)).setText(kq.a().a(getContext(), R.string.shared_blocks_list_title_shared_blocks));
    }

    public void setDragEnabled(boolean z) {
        if (z) {
            this.b.a();
        } else {
            this.b.b();
        }
    }

    public void a() {
        this.f1061a.removeAllViews();
    }

    public View a(String str, ArrayList<BlockBean> arrayList) {
        if (arrayList.size() <= 0) {
            return null;
        }
        BlockBean blockBean = arrayList.get(0);
        fp fpVar = new fp(getContext(), blockBean.type, blockBean.typeName, blockBean.opCode, str, arrayList);
        this.f1061a.addView(fpVar);
        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(1, (int) kp.a(getContext(), 8.0f)));
        this.f1061a.addView(view);
        return fpVar;
    }

    public void a(String str) {
        for (int i = 0; i < this.f1061a.getChildCount(); i++) {
            View childAt = this.f1061a.getChildAt(i);
            if ((childAt instanceof fp) && ((fp) childAt).a.equals(str)) {
                this.f1061a.removeViewAt(i + 1);
                this.f1061a.removeViewAt(i);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
      /* Intent intent = new Intent(getContext(), SharedBlocksListActivity.class);
        intent.setFlags(536870912);
        ((LogicEditorActivity) getContext()).startActivityForResult(intent, 463);*/
    }
}
