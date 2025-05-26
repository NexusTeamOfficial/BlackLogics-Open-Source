package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
/*import com.besome.sketch.define.BlockBean;
import com.besome.sketch.define.DefineBlock;
import com.besome.sketch.lib.utils.StringUtil;*/
import java.util.ArrayList;
import java.util.Iterator;
import com.besome.blacklogics.R;

public class Block extends BlockBase {
    public static final int BLOCK_TYPE_HAT = 2;
    public static final int BLOCK_TYPE_INPALETTE = 1;
    public static final int BLOCK_TYPE_NORMAL = 0;
    public ArrayList<String> argTypes = new ArrayList();
    public ArrayList<View> args;
    private int blockType = 0;
    public int childDepth = 0;
    public Object[] defaultArgValues;
    private int defaultSpace = 4;
    private TextView elseLabel = null;
    public boolean forcedRequester = false;
    public boolean isHat = false;
    public boolean isReporter = false;
    public boolean isRequester = false;
    public boolean isTerminal = false;
    public ArrayList<View> labelsAndArgs = new ArrayList();
    private LayoutParams lp;
    private Object[] mDefaultArgs;
    public String mOpCode;
    public String mSpec;
    private int minCommandWidth = 50;
    private int minHatWidth = 90;
    private int minLoopWidth = 90;
    private int minReporterWidth = 30;
    public int nextBlock = -1;
    public BlockPane pane = null;
    public int subStack1 = -1;
    public int subStack2 = -1;

    public Block(Context context, int i, String str, String str2, String str3, Object... objArr) {
        super(context, str2, false);
        setTag(Integer.valueOf(i));
        this.mSpec = str;
        this.mOpCode = str3;
        this.mDefaultArgs = objArr;
        init(context);
    }

    private void addLabelsAndArgs(String str, int i) {
        ArrayList tokenize = StringUtil.tokenize(str);
        this.labelsAndArgs = new ArrayList();
        this.argTypes = new ArrayList();
        for (int i2 = 0; i2 < tokenize.size(); i2 += BLOCK_TYPE_INPALETTE) {
            View argOrLabelFor = argOrLabelFor((String) tokenize.get(i2), i);
            if (argOrLabelFor instanceof BlockBase) {
                ((BlockBase) argOrLabelFor).parentBlock = this;
            }
            this.labelsAndArgs.add(argOrLabelFor);
            String obj = "icon";
            if (argOrLabelFor instanceof BlockArg) {
                obj = (String) tokenize.get(i2);
            }
            if (argOrLabelFor instanceof TextView) {
                obj = "label";
            }
            this.argTypes.add(obj);
        }
    }

    private void appendBlock(Block block) {
        if (canHaveSubstack1() && -1 == this.subStack1) {
            insertBlockSub1(block);
            return;
        }
        Block bottomBlock = bottomBlock();
        bottomBlock.nextBlock = ((Integer) block.getTag()).intValue();
        block.parentBlock = bottomBlock;
    }

    private View argOrLabelFor(String str, int i) {
		if (str.length() >= BLOCK_TYPE_HAT && str.charAt(0) == '%') {
			char charAt = str.charAt(BLOCK_TYPE_INPALETTE);
			if (charAt == 'b') {
				return new BlockArg(this.mContext, "b", i, "");
			}
			if (charAt == 'd') {
				return new BlockArg(this.mContext, "d", i, "");
			}
			if (charAt == 'm') {
				return new BlockArg(this.mContext, "m", i, str.substring(3));
			}
			if (charAt == 's') {
				if (str.startsWith("%s.inputOnly")) {
					return new BlockArg(this.mContext, "s.inputOnly", i, str.length() > 12 ? str.substring(12) : "");
				}
				return new BlockArg(this.mContext, "s", i, str.length() > BLOCK_TYPE_HAT ? str.substring(3) : "");
			}
		}
		return makeLabel(StringUtil.unescape(str));
	}

    private void collectArgs() {
        this.args = new ArrayList();
        for (int i = 0; i < this.labelsAndArgs.size(); i += BLOCK_TYPE_INPALETTE) {
            View view = (View) this.labelsAndArgs.get(i);
            if ((view instanceof Block) || (view instanceof BlockArg)) {
                this.args.add(view);
            }
        }
    }

    private void fixElseLabel() {
        if (this.elseLabel != null) {
            this.elseLabel.bringToFront();
            this.elseLabel.setX((float) this.indentLeft);
            this.elseLabel.setY((float) (substack2y() - this.DividerH));
        }
    }

    private int getLabelWidth(TextView textView) {
        Rect rect = new Rect();
        textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
        return rect.width();
    }

    private void init(Context var1) {
        this.setDrawingCacheEnabled(false);
        this.setGravity(51);
        this.minReporterWidth = (int)((float)this.minReporterWidth * this.dip);
        this.minCommandWidth = (int)((float)this.minCommandWidth * this.dip);
        this.minHatWidth = (int)((float)this.minHatWidth * this.dip);
        this.minLoopWidth = (int)((float)this.minLoopWidth * this.dip);
        this.defaultSpace = (int)((float)this.defaultSpace * this.dip);
        String var2 = this.mType;
        byte var3 = -1;
        switch(var2.hashCode()) {
            case 32:
                if(var2.equals(" ")) {
                    var3 = 0;
                }
                break;
            case 98:
                if(var2.equals("b")) {
                    var3 = 1;
                }
                break;
            case 99:
                if(var2.equals("c")) {
                    var3 = 4;
                }
                break;
            case 100:
                if(var2.equals("d")) {
                    var3 = 3;
                }
                break;
            case 101:
                if(var2.equals("e")) {
                    var3 = 6;
                }
                break;
            case 102:
                if(var2.equals("f")) {
                    var3 = 7;
                }
                break;
            case 104:
                if(var2.equals("h")) {
                    var3 = 8;
                }
                break;
            case 115:
                if(var2.equals("s")) {
                    var3 = 2;
                }
                break;
            case 3171:
                if(var2.equals("cf")) {
                    var3 = 5;
                }
        }

        switch(var3) {
            case 0:
            case 4:
            case 6:
            default:
                break;
            case 1:
            case 2:
                this.isReporter = true;
                break;
            case 3:
                this.isReporter = true;
                this.isRequester = false;
                this.forcedRequester = false;
                break;
            case 5:
                this.isTerminal = true;
                break;
            case 7:
                this.isTerminal = true;
                break;
            case 8:
                this.isHat = true;
        }

        if(!this.isHat && !this.mOpCode.equals("definedFunc") && !this.mOpCode.equals("getVar") && !this.mOpCode.equals("getArg")) {
            String var4 = "block_" + DefineBlock.getSpecStringId(this.mOpCode, this.mType);
            int var5 = this.getResources().getIdentifier(var4, "string", this.getContext().getPackageName());
            if(var5 > 0) {
                this.mSpec = this.getResources().getString(var5);
            }
        }

        this.setSpec(this.mSpec, this.mDefaultArgs);
        this.mColor = DefineBlock.getBlockColor(this.mOpCode, this.mType);
    }

    private TextView makeLabel(String str) {
        TextView textView = new TextView(this.mContext);
        textView.setText(str);
        textView.setTextSize(10.0f);
        textView.setPadding(0, 0, 0, 0);
        textView.setGravity(16);
        textView.setTextColor(-1);
        textView.setTypeface(null, BLOCK_TYPE_INPALETTE);
        LayoutParams layoutParams = new LayoutParams(-2, this.labelAndArgHeight);
        layoutParams.setMargins(0, 0, 0, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    public void actionClick(float f, float f2) {
        Iterator it = this.args.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            if ((view instanceof BlockArg) && view.getX() < f && view.getX() + ((float) view.getWidth()) > f && view.getY() < f2 && view.getY() + ((float) view.getHeight()) > f2) {
                ((BlockArg) view).showPopup();
            }
        }
    }

    public String argType(View view) {
        int indexOf = this.labelsAndArgs.indexOf(view);
        return indexOf == -1 ? "" : (String) this.argTypes.get(indexOf);
    }

    public Block bottomBlock() {
        Block block = this;
        while (block.nextBlock != -1) {
            block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
        }
        return block;
    }

    public void fixLayout() {
        this.bringToFront();
        int var1 = this.indentLeft;

        for(int var2 = 0; var2 < this.labelsAndArgs.size(); ++var2) {
            View var8 = (View)this.labelsAndArgs.get(var2);
            var8.bringToFront();
            if(var8 instanceof Block) {
                var8.setX(this.getX() + (float)var1);
            } else {
                var8.setX((float)var1);
            }

            boolean var9 = ((String)this.argTypes.get(var2)).equals("label");
            int var10 = 0;
            if(var9) {
                var10 = this.getLabelWidth((TextView)var8);
            }

            if(var8 instanceof BlockArg) {
                var10 = ((BlockArg)var8).getW();
            }

            if(var8 instanceof Block) {
                var10 = ((Block)var8).getWidthSum();
            }

            var1 += var10 + this.defaultSpace;
            if(var8 instanceof Block) {
                var8.setY(this.getY() + (float)this.indentTop + (float)((-1 + (this.childDepth - ((Block)var8).childDepth)) * this.childInset));
                ((Block)var8).fixLayout();
            } else {
                var8.setY((float)(this.indentTop + this.childDepth * this.childInset));
            }
        }

        if(this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
            var1 = Math.max(var1, this.minReporterWidth);
        }

        if(this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("f")) {
            var1 = Math.max(var1, this.minCommandWidth);
        }

        if(this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
            var1 = Math.max(var1, this.minLoopWidth);
        }

        if(this.mType.equals("h")) {
            var1 = Math.max(var1, this.minHatWidth);
        }

        this.setWidthAndTopHeight((float)(var1 + this.indentRight), (float)(this.indentTop + this.labelAndArgHeight + 2 * this.childDepth * this.childInset + this.indentBottom), true);
        if(this.canHaveSubstack1()) {
            int var4 = this.EmptySubstackH;
            if(this.subStack1 > -1) {
                Block var7 = (Block)this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
                var7.setX(this.getX() + (float)this.SubstackInset);
                var7.setY(this.getY() + (float)this.substack1y());
                var7.bringToFront();
                var7.fixLayout();
                var4 = var7.getHeightSum();
            }

            this.setSubstack1Height(var4);
            int var5 = this.EmptySubstackH;
            if(this.subStack2 > -1) {
                Block var6 = (Block)this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
                var6.setX(this.getX() + (float)this.SubstackInset);
                var6.setY(this.getY() + (float)this.substack2y());
                var6.bringToFront();
                var6.fixLayout();
                var5 = var6.getHeightSum();
                if(var6.bottomBlock().isTerminal) {
                    var5 += this.NotchDepth;
                }
            }

            this.setSubstack2Height(var5);
            this.fixElseLabel();
        }

        if(this.nextBlock > -1) {
            Block var3 = (Block)this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
            var3.setX(this.getX());
            var3.setY(this.getY() + (float)this.nextBlockY());
            var3.bringToFront();
            var3.fixLayout();
        }

    }

    public ArrayList<Block> getAllChildren() {
        ArrayList<Block> arrayList = new ArrayList();
        Block block = this;
        while (true) {
            arrayList.add(block);
            Iterator it = block.labelsAndArgs.iterator();
            while (it.hasNext()) {
                View view = (View) it.next();
                if (view instanceof Block) {
                    arrayList.addAll(((Block) view).getAllChildren());
                }
            }
            if (block.canHaveSubstack1() && block.subStack1 != -1) {
                arrayList.addAll(((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack1))).getAllChildren());
            }
            if (block.canHaveSubstack2() && block.subStack2 != -1) {
                arrayList.addAll(((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack2))).getAllChildren());
            }
            if (block.nextBlock == -1) {
                return arrayList;
            }
            block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
        }
    }

    public BlockBean getBean() {
        BlockBean blockBean = new BlockBean();
        blockBean.id = getTag().toString();
        blockBean.spec = this.mSpec;
        blockBean.type = this.mType;
        blockBean.opCode = this.mOpCode;
        blockBean.color = this.mColor;
        Iterator it = this.args.iterator();
        while (it.hasNext()) {
            View view = (View) it.next();
            if (view instanceof BlockArg) {
                blockBean.parameters.add(((BlockArg) view).getArgValue().toString());
                blockBean.paramTypes.add(((BlockArg) view).mType);
            } else if (view instanceof Block) {
                blockBean.parameters.add("@" + view.getTag().toString());
                blockBean.paramTypes.add(((Block) view).mType);
            }
        }
        blockBean.subStack1 = this.subStack1;
        blockBean.subStack2 = this.subStack2;
        blockBean.nextBlock = this.nextBlock;
        return blockBean;
    }

    public int getBlockType() {
        return this.blockType;
    }

    public int getDepth() {
        Block var1 = this;

        int var2;
        for(var2 = 0; var1.parentBlock != null; ++var2) {
            var1 = var1.parentBlock;
        }

        return var2;
    }

    public int getHeightSum() {
        int i = 0;
        Block block = this;
        while (true) {
            if (i > 0) {
                i -= this.NotchDepth;
            }
            int totalHeight = block.getTotalHeight() + i;
            if (block.nextBlock == -1) {
                return totalHeight;
            }
            block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
            i = totalHeight;
        }
    }

    public int getWidthSum() {
        int i = 0;
        Block block = this;
        while (true) {
            int max = Math.max(i, block.getW());
            if (block.canHaveSubstack1() && block.subStack1 != -1) {
                max = Math.max(max, ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack1))).getWidthSum() + this.SubstackInset);
            }
            if (block.canHaveSubstack2() && block.subStack2 != -1) {
                max = Math.max(max, ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack2))).getWidthSum() + this.SubstackInset);
            }
            if (block.nextBlock == -1) {
                return max;
            }
            block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
            i = max;
        }
    }

    public void insertBlock(Block block) {
        View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
        if (findViewWithTag != null) {
            ((Block) findViewWithTag).parentBlock = null;
        }
        block.parentBlock = this;
        this.nextBlock = ((Integer) block.getTag()).intValue();
        if (findViewWithTag != null) {
            block.appendBlock((Block) findViewWithTag);
        }
    }

    public void insertBlockAbove(Block block) {
        block.setX(getX());
        block.setY((getY() - ((float) block.getHeightSum())) + ((float) this.NotchDepth));
        block.bottomBlock().insertBlock(this);
    }

    public void insertBlockAround(Block block) {
        block.setX(getX() - ((float) this.SubstackInset));
        block.setY(getY() - ((float) substack1y()));
        this.parentBlock = block;
        block.subStack1 = ((Integer) getTag()).intValue();
    }

    public void insertBlockSub1(Block block) {
        View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
        if (findViewWithTag != null) {
            ((Block) findViewWithTag).parentBlock = null;
        }
        block.parentBlock = this;
        this.subStack1 = ((Integer) block.getTag()).intValue();
        if (findViewWithTag != null) {
            block.appendBlock((Block) findViewWithTag);
        }
    }

    public void insertBlockSub2(Block block) {
        View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
        if (findViewWithTag != null) {
            ((Block) findViewWithTag).parentBlock = null;
        }
        block.parentBlock = this;
        this.subStack2 = ((Integer) block.getTag()).intValue();
        if (findViewWithTag != null) {
            block.appendBlock((Block) findViewWithTag);
        }
    }

    public void log() {
    }

    public void recalcWidthToParent() {
        Block var1 = this;

        while(true) {
            var1.recalculateWidth();
            if(var1.parentBlock == null) {
                return;
            }

            var1 = var1.parentBlock;
        }
    }

    public void recalculateWidth() {
        int i = 0;
        int i2 = this.indentLeft;
        while (i < this.labelsAndArgs.size()) {
            View view = (View) this.labelsAndArgs.get(i);
            int labelWidth = ((String) this.argTypes.get(i)).equals("label") ? getLabelWidth((TextView) view) : 0;
            if (view instanceof BlockArg) {
                labelWidth = ((BlockArg) view).getW();
            }
            if (view instanceof Block) {
                labelWidth = ((Block) view).getWidthSum();
            }
            i += BLOCK_TYPE_INPALETTE;
            i2 += this.defaultSpace + labelWidth;
        }
        if (this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
            i2 = Math.max(i2, this.minReporterWidth);
        }
        if (this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("o")) {
            i2 = Math.max(i2, this.minCommandWidth);
        }
        if (this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
            i2 = Math.max(i2, this.minLoopWidth);
        }
        if (this.mType.equals("h")) {
            i2 = Math.max(i2, this.minHatWidth);
        }
        if (this.elseLabel != null) {
            i2 = Math.max(i2, (this.indentLeft + this.elseLabel.getWidth()) + BLOCK_TYPE_HAT);
        }
        setWidthAndTopHeight((float) (this.indentRight + i2), (float) (((this.indentTop + this.labelAndArgHeight) + ((this.childDepth * this.childInset) * BLOCK_TYPE_HAT)) + this.indentBottom), false);
    }

    public void refreshChildDepth() {
        for(Block var1 = this; var1 != null; var1 = var1.parentBlock) {
            int var2 = 0;
            Iterator var3 = var1.args.iterator();

            while(var3.hasNext()) {
                View var4 = (View)var3.next();
                if(var4 instanceof Block) {
                    var2 = Math.max(var2, 1 + ((Block)var4).childDepth);
                }
            }

            var1.childDepth = var2;
            var1.recalculateWidth();
            if(!var1.isReporter) {
                break;
            }
        }

    }

    public void removeBlock(Block block) {
        if (this.nextBlock == ((Integer) block.getTag()).intValue()) {
            this.nextBlock = -1;
        }
        if (this.subStack1 == ((Integer) block.getTag()).intValue()) {
            this.subStack1 = -1;
        }
        if (this.subStack2 == ((Integer) block.getTag()).intValue()) {
            this.subStack2 = -1;
        }
        if (block.isReporter) {
            int indexOf = this.labelsAndArgs.indexOf(block);
            if (indexOf >= 0) {
                View argOrLabelFor = argOrLabelFor((String) this.argTypes.get(indexOf), this.mColor);
                if (argOrLabelFor instanceof BlockBase) {
                    ((BlockBase) argOrLabelFor).parentBlock = this;
                }
                this.labelsAndArgs.set(indexOf, argOrLabelFor);
                addView(argOrLabelFor);
                collectArgs();
                refreshChildDepth();
            } else {
                return;
            }
        }
        topBlock().fixLayout();
    }

    public void replaceArgWithBlock(BlockBase blockBase, Block block) {
        int indexOf = this.labelsAndArgs.indexOf(blockBase);
        if (indexOf >= 0) {
            if (!(blockBase instanceof Block)) {
                removeView(blockBase);
            }
            this.labelsAndArgs.set(indexOf, block);
            block.parentBlock = this;
            collectArgs();
            refreshChildDepth();
            if (blockBase instanceof Block) {
                blockBase.parentBlock = null;
                blockBase.setX(10.0f + (getX() + ((float) getWidthSum())));
                blockBase.setY(5.0f + getY());
                ((Block) blockBase).fixLayout();
            }
        }
    }

    public void setBlockType(int i) {
        this.blockType = i;
        if (this.blockType == BLOCK_TYPE_INPALETTE || this.blockType == BLOCK_TYPE_HAT) {
            for (int i2 = 0; i2 < this.labelsAndArgs.size(); i2 += BLOCK_TYPE_INPALETTE) {
                View view = (View) this.labelsAndArgs.get(i2);
                if (view instanceof BlockArg) {
                    ((BlockArg) view).setEditable(false);
                }
            }
        }
    }

    public void setSpec(String str, Object[] objArr) {
        this.mSpec = str;
        removeAllViews();
        addLabelsAndArgs(this.mSpec, this.mColor);
        Iterator it = this.labelsAndArgs.iterator();
        while (it.hasNext()) {
            addView((View) it.next());
        }
        collectArgs();
        if (this.mType.equals("e")) {
            this.elseLabel = makeLabel(getResources().getString(R.string.block_else));
            addView(this.elseLabel);
        }
        fixLayout();
    }

    public Block topBlock() {
        Block var1;
        for(var1 = this; var1.parentBlock != null; var1 = var1.parentBlock) {
            ;
        }

        return var1;
    }
}
