package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.besome.blacklogics.R;
/*import com.besome.sketch.editor.logic.LogicEditorActivity;
import com.besome.sketch.editor.logic.block.Block;
import com.besome.sketch.manager.DesignDataManager;
*/
public class BlockCopyInterface extends RelativeLayout {
    public LogicEditorActivity activity;
    View dimBg;
    ViewLogicEditor editor;
    private boolean isCopyMode = false;
    public Block lastSelectedBlock = null;

    public BlockCopyInterface(Context context) {
        super(context);
        init(context);
    }

    public BlockCopyInterface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
    }

    public boolean getCopyMode() {
        return this.isCopyMode;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.editor = (ViewLogicEditor) findViewById(R.id.editor);
        this.dimBg = findViewById(R.id.dim_bg);
        this.dimBg.setVisibility(4);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.isCopyMode;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.isCopyMode) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 1:
                if (this.editor.hitTest(motionEvent.getX(), motionEvent.getRawY())) {
                    this.lastSelectedBlock = this.editor.getBlockPane().getHitBlock(motionEvent.getRawX(), motionEvent.getRawY());
                    if (this.lastSelectedBlock != null) {
                        LogicEditorActivity logicEditorActivity = this.activity;
                        DesignDataManager.copyBlocks(LogicEditorActivity.filename, this.lastSelectedBlock.getAllChildren());
                        Toast.makeText(getContext(), getContext().getString(R.string.complete_copy), 0).show();
                        setCopyMode(false);
                        this.activity.refreshPasteIcon();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public void setCopyMode(boolean z) {
        this.isCopyMode = z;
        if (this.isCopyMode) {
            this.dimBg.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432576));
            this.dimBg.setVisibility(0);
            Toast.makeText(getContext(), getContext().getString(R.string.desc_copy_block), 0).show();
            return;
        }
        this.dimBg.startAnimation(AnimationUtils.loadAnimation(getContext(), 17432577));
        this.dimBg.setVisibility(4);
    }
}
