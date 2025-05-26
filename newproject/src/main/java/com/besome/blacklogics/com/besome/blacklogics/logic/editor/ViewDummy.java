package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.besome.blacklogics.R;
/*import com.besome.sketch.editor.logic.block.Block;
import com.besome.sketch.lib.utils.LayoutUtil;*/

public class ViewDummy extends RelativeLayout {
    private ImageView imgDummy;
    private ImageView imgNotAllowed;
    private LinearLayout layoutDummy;
    private boolean mAllow;
    private int[] pos;
    private int[] posAreaDummy;

    public ViewDummy(Context context) {
        super(context);
        this.pos = new int[2];
        this.posAreaDummy = new int[2];
        this.mAllow = false;
        init(context);
    }

    public ViewDummy(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.pos = new int[2];
        this.posAreaDummy = new int[2];
        this.mAllow = false;
        init(context);
    }

    private void init(Context context) {
        LayoutUtil.inflate(context, this, R.layout.dummy);
        this.imgNotAllowed = (ImageView) findViewById(R.id.img_notallowed);
        this.imgDummy = (ImageView) findViewById(R.id.img_dummy);
        this.layoutDummy = (LinearLayout) findViewById(R.id.layout_dummy);
    }

    private Bitmap loadBitmapFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public boolean getAllow() {
        return this.mAllow;
    }

    public void getDummyPosition(int[] iArr) {
        this.imgDummy.getLocationOnScreen(iArr);
    }

    public void makeDummy(View view) {
        this.imgDummy.setImageBitmap(loadBitmapFromView(view));
        this.imgDummy.setAlpha(0.5f);
    }

    public void makeDummyWithBlock(Block var1) {
        String var2 = var1.mType;
        byte var3 = -1;
        switch(var2.hashCode()) {
            case 98:
                if(var2.equals("b")) {
                    var3 = 0;
                }
                break;
            case 99:
                if(var2.equals("c")) {
                    var3 = 4;
                }
                break;
            case 100:
                if(var2.equals("d")) {
                    var3 = 1;
                }
                break;
            case 101:
                if(var2.equals("e")) {
                    var3 = 5;
                }
                break;
            case 102:
                if(var2.equals("f")) {
                    var3 = 6;
                }
                break;
            case 110:
                if(var2.equals("n")) {
                    var3 = 2;
                }
                break;
            case 115:
                if(var2.equals("s")) {
                    var3 = 3;
                }
        }

        switch(var3) {
            case 0:
                this.imgDummy.setImageResource(R.drawable.selected_block_boolean);
                break;
            case 1:
            case 2:
                this.imgDummy.setImageResource(R.drawable.selected_block_integer);
                break;
            case 3:
                this.imgDummy.setImageResource(R.drawable.selected_block_string);
                break;
            case 4:
                this.imgDummy.setImageResource(R.drawable.selected_block_loop);
                break;
            case 5:
                this.imgDummy.setImageResource(R.drawable.selected_block_ifelse);
                break;
            case 6:
                this.imgDummy.setImageResource(R.drawable.selected_block_final);
                break;
            default:
                this.imgDummy.setImageResource(R.drawable.selected_block_command);
        }

        this.imgDummy.setAlpha(0.5F);
    }
    
    public void moveDummy(View view, float f, float f2, float f3, float f4) {
        /*if (this.layoutDummy.getVisibility() != 0) {
            setDummyVisibility(0);
        }
        view.getLocationOnScreen(this.pos);
        getLocationOnScreen(this.posAreaDummy);
        this.layoutDummy.setX(((((float) (this.pos[0] - this.posAreaDummy[0])) + f) - f3) - ((float) this.imgNotAllowed.getWidth()));
        this.layoutDummy.setY((((float) (this.pos[1] - this.posAreaDummy[1])) + f2) - f4);*/
        moveDummy(view, f, f2, f3, f4, 0.0F, 0.0F);
    }
    
    //new
    public void moveDummy(View view, float f, float f2, float f3, float f4,   float f5, float f6) {
        if (this.layoutDummy.getVisibility() != 0) {
            setDummyVisibility(0);
        }
        view.getLocationOnScreen(this.pos);
        getLocationOnScreen(this.posAreaDummy);
        this.layoutDummy.setX(f5 + ((((float) (this.pos[0] - this.posAreaDummy[0])) + f) - f3) - ((float) this.imgNotAllowed.getWidth()));
        this.layoutDummy.setY(f6 + (((float) (this.pos[1] - this.posAreaDummy[1])) + f2) - f4);
    }
    
    
    
    
    
    

    public void setAllow(boolean z) {
        this.mAllow = z;
        if (z) {
            this.imgNotAllowed.setVisibility(4);
        } else {
            this.imgNotAllowed.setVisibility(0);
        }
    }

    public void setDummyVisibility(int i) {
        this.layoutDummy.setVisibility(i);
    }
}
