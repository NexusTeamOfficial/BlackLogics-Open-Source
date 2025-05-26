package com.besome.blacklogics.logic.editor;


import com.besome.blacklogics.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
/*import com.besome.sketch.lib.ui.CustomHorizontalScrollView;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.besome.sketch.lib.utils.LayoutUtil;
*/
public class PaletteBlock extends LinearLayout {
    LinearLayout blockBuilder;
    private float dip = 0.0f;
    CustomHorizontalScrollView hscv;
    private Context mContext;
    CustomScrollView scv;

    public PaletteBlock(Context context) {
        super(context);
        init(context);
    }

    public PaletteBlock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutUtil.inflate(context, this, R.layout.palette_block);
        this.scv = (CustomScrollView) findViewById(R.id.scv);
        this.hscv = (CustomHorizontalScrollView) findViewById(R.id.hscv);
        this.blockBuilder = (LinearLayout) findViewById(R.id.block_builder);
        this.dip = LayoutUtil.getDip(this.mContext, 1.0f); 
    }

    public BlockBase addBlock(String str, String str2, String str3, int i, Object... objArr) {
        View view = new View(this.mContext);
        view.setLayoutParams(new LayoutParams(-1, (int) (8.0f * this.dip)));
        this.blockBuilder.addView(view);
        Block block = new Block(this.mContext, -1, str, str2, str3, new Object[]{Integer.valueOf(i), objArr});
        block.setBlockType(1);
        this.blockBuilder.addView(block);
        return block;
    }

    public TextView addButton(String str) {
        TextView textView = new TextView(this.mContext);
        textView.setLayoutParams(new LayoutParams(-1, (int) (30.0f * this.dip)));
        textView.setBackgroundResource(R.drawable.bg_feed); 
        textView.setText(str);
        textView.setTextSize(10.0f);
        textView.setGravity(17);
        textView.setPadding((int) (this.dip * 8.0f), 0, (int) (this.dip * 8.0f), 0);
        this.blockBuilder.addView(textView);
        return textView;
    }

    public void removeAllBlocks() {
        this.blockBuilder.removeAllViews();
    }

    public void setDragEnabled(boolean z) {
        if (z) {
            this.scv.setScrollEnabled();
            this.hscv.setScrollEnabled();
            return;
        }
        this.scv.setScrollDisabled();
        this.hscv.setScrollDisabled();
    }

    public void setMinWidth(int i) {
        this.scv.setMinimumWidth(i - ((int) (this.dip * 5.0f)));
        this.hscv.setMinimumWidth(i - ((int) (this.dip * 5.0f)));
        getLayoutParams().width = i;
    }
}
