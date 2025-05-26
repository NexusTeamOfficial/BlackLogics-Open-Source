package com.besome.blacklogics.logic.editor;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.besome.blacklogics.R;
/*import com.besome.sketch.define.BlockBean;
import com.besome.sketch.lib.utils.LayoutUtil;*/
import java.util.ArrayList;
import java.util.Iterator;

public class BlockPane extends RelativeLayout {
    public static final int INSERT_ABOVE = 1;
    public static final int INSERT_NORMAL = 0;
    public static final int INSERT_PARAM = 5;
    public static final int INSERT_SUB1 = 2;
    public static final int INSERT_SUB2 = 3;
    public static final int INSERT_WRAP = 4;
    public int blockId;
    private BlockBase feedbackShape;
    private Block hitTarget;
    private Context mContext;
    private int maxDepth;
    private Object[] nearestTarget;
    private int[] posArea;
    private ArrayList<Object[]> possibleTargets;
    private Block root;

    public BlockPane(Context context) {
        super(context);
        this.posArea = new int[INSERT_SUB1];
        this.possibleTargets = new ArrayList();
        this.nearestTarget = null;
        this.blockId = 10;
        this.hitTarget = null;
        this.maxDepth = -1;
        init(context);
    }

    public BlockPane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.posArea = new int[INSERT_SUB1];
        this.possibleTargets = new ArrayList();
        this.nearestTarget = null;
        this.blockId = 10;
        this.hitTarget = null;
        this.maxDepth = -1;
        init(context);
    }

    private void addFeedbackShape() {
        if (this.feedbackShape == null) {
            this.feedbackShape = new BlockBase(this.mContext, " ", true);
        }
        this.feedbackShape.setWidthAndTopHeight(10.0f, 10.0f, false);
        addView(this.feedbackShape);
        hideFeedbackShape();
    }

    private void addPossibleTarget(int[] iArr, View view, int i) {
        Object[] obj = new Object[INSERT_SUB2];
        obj[INSERT_NORMAL] = iArr;
        obj[INSERT_ABOVE] = view;
        obj[INSERT_SUB1] = Integer.valueOf(i);
        this.possibleTargets.add(obj);
    }

    private boolean dropCompatible(Block block, View view) {
        if (block.isReporter) {
            String str = block.mType;
            if ((view instanceof BlockBase) && !((BlockBase) view).mType.equals(str)) {
                return false;
            }
        }
        return true;
    }

    private void findCommandTargetsIn(Block block, Block block2, boolean z) {
        while (block2.getVisibility() != 8) {
            if (!block2.isTerminal && (!z || -1 == block2.nextBlock)) {
                int[] iArr = new int[INSERT_SUB1];
                block2.getLocationOnScreen(iArr);
                iArr[INSERT_ABOVE] = iArr[INSERT_ABOVE] + block2.nextBlockY();
                addPossibleTarget(iArr, block2, INSERT_NORMAL);
            }
            if (block2.canHaveSubstack1() && (!z || block2.subStack1 == -1)) {
                int[] iArr = new int[INSERT_SUB1];
                block2.getLocationOnScreen(iArr);
                iArr[INSERT_NORMAL] = iArr[INSERT_NORMAL] + block2.SubstackInset;
                iArr[INSERT_ABOVE] = iArr[INSERT_ABOVE] + block2.substack1y();
                addPossibleTarget(iArr, block2, INSERT_SUB1);
            }
            if (block2.canHaveSubstack2() && (!z || block2.subStack2 == -1)) {
                int[] iArr = new int[INSERT_SUB1];
                block2.getLocationOnScreen(iArr);
                iArr[INSERT_NORMAL] = iArr[INSERT_NORMAL] + block2.SubstackInset;
                iArr[INSERT_ABOVE] = iArr[INSERT_ABOVE] + block2.substack2y();
                addPossibleTarget(iArr, block2, INSERT_SUB2);
            }
            if (block2.subStack1 != -1) {
                findCommandTargetsIn(block, (Block) findViewWithTag(Integer.valueOf(block2.subStack1)), z);
            }
            if (block2.subStack2 != -1) {
                findCommandTargetsIn(block, (Block) findViewWithTag(Integer.valueOf(block2.subStack2)), z);
            }
            if (block2.nextBlock != -1) {
                block2 = (Block) findViewWithTag(Integer.valueOf(block2.nextBlock));
            } else {
                return;
            }
        }
    }

    private void findReporterTargetsIn(Block block, Block block2) {
        while (block != null) {
            for (int i = INSERT_NORMAL; i < block.args.size(); i += INSERT_ABOVE) {
                View view = (View) block.args.get(i);
                if (((view instanceof Block) || (view instanceof BlockArg)) && view != block2) {
                    int[] iArr = new int[INSERT_SUB1];
                    view.getLocationOnScreen(iArr);
                    addPossibleTarget(iArr, view, INSERT_NORMAL);
                    if (view instanceof Block) {
                        findReporterTargetsIn((Block) view, block2);
                    }
                }
            }
            if (block.subStack1 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(block.subStack1)), block2);
            }
            if (block.subStack2 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(block.subStack2)), block2);
            }
            if (block.nextBlock != -1) {
                block = (Block) findViewWithTag(Integer.valueOf(block.nextBlock));
            } else {
                return;
            }
        }
    }

    private void init(Context context) {
        this.mContext = context;
        addFeedbackShape();
    }

    public Block addBlock(Block block, int i, int i2) {
        Block block2;
        getLocationOnScreen(this.posArea);
        if (block.getBlockType() == INSERT_ABOVE) {
            Context context = getContext();
            int i3 = this.blockId;
            this.blockId = i3 + INSERT_ABOVE;
            String str = block.mSpec;
            String str2 = block.mType;
            String str3 = block.mOpCode;
            Object[] objArr = new Object[INSERT_SUB1];
            objArr[INSERT_NORMAL] = Integer.valueOf(block.mColor);
            objArr[INSERT_ABOVE] = block.defaultArgValues;
            block2 = new Block(context, i3, str, str2, str3, objArr);
        } else {
            block2 = block;
        }
        block2.pane = this;
        addView(block2);
        block2.setX((float) ((i - this.posArea[INSERT_NORMAL]) - getPaddingLeft()));
        block2.setY((float) ((i2 - this.posArea[INSERT_ABOVE]) - getPaddingTop()));
        return block2;
    }

    public void addRoot(String str, String str2) {
        Context context = getContext();
        Object[] objArr = new Object[INSERT_ABOVE];
        objArr[INSERT_NORMAL] = Integer.valueOf(-3636432);
        this.root = new Block(context, INSERT_NORMAL, str, "h", str2, objArr);
        this.root.pane = this;
        addView(this.root);
        float dip = LayoutUtil.getDip(getContext(), 1.0f);
        this.root.setX(8.0f * dip);
        this.root.setY(dip * 8.0f);
    }
    
    
    
   public Block blockDropped(Block var1, int var2, int var3, boolean var4) {
      Block var5 = var1;
      if(!var4) {
         var5 = this.addBlock(var1, var2, var3);
      } else {
         var1.setX((float)(var2 - this.posArea[0] - this.getPaddingLeft()));
         var1.setY((float)(var3 - this.posArea[1] - this.getPaddingTop()));
      }

      if(this.nearestTarget == null) {
         var5.topBlock().fixLayout();
         this.calculateWidthHeight();
         return var5;
      } else {
         if(var1.isReporter) {
            ((BlockBase)this.nearestTarget[1]).parentBlock.replaceArgWithBlock((BlockBase)this.nearestTarget[1], var5);
         } else {
            Block var6 = (Block)this.nearestTarget[1];
            switch(((Integer)this.nearestTarget[2]).intValue()) {
            case 0:
               var6.insertBlock(var5);
               break;
            case 1:
               var6.insertBlockAbove(var5);
               break;
            case 2:
               var6.insertBlockSub1(var5);
               break;
            case 3:
               var6.insertBlockSub2(var5);
               break;
            case 4:
               var6.insertBlockAround(var5);
            }
         }

         var5.topBlock().fixLayout();
         this.calculateWidthHeight();
         return var5;
      }
   }

   public void calculateWidthHeight() {
      int var1 = this.getChildCount();
      int var2 = this.getLayoutParams().width;
      int var3 = this.getLayoutParams().width;

      for(int var4 = 0; var4 < var1; ++var4) {
         View var5 = this.getChildAt(var4);
         if(var5 instanceof Block) {
            var2 = Math.max(150 + (int)(var5.getX() + (float)((Block)var5).getWidthSum()), var2);
            var3 = Math.max(150 + (int)(var5.getY() + (float)((Block)var5).getHeightSum()), var3);
         }
      }

      this.getLayoutParams().width = var2;
      this.getLayoutParams().height = var3;
   }
    
    
    
    
    public void draggingDone() {
        hideFeedbackShape();
        this.possibleTargets = new ArrayList();
        this.nearestTarget = null;
    }

    public void findTargetsFor(Block block) {
        this.possibleTargets = new ArrayList();
        boolean z = block.bottomBlock().isTerminal;
        int i = (block.canHaveSubstack1() && -1 == block.subStack1) ? INSERT_ABOVE : INSERT_NORMAL;
        for (int i2 = INSERT_NORMAL; i2 < getChildCount(); i2 += INSERT_ABOVE) {
            View childAt = getChildAt(i2);
            if (childAt instanceof Block) {
                Block block2 = (Block) childAt;
                if (block2.getVisibility() != 8 && block2.parentBlock == null) {
                    if (block.isReporter) {
                        findReporterTargetsIn(block2, block);
                    } else if (!block2.isReporter) {
                        int[] iArr;
                        if (!(z || block2.isHat)) {
                            iArr = new int[INSERT_SUB1];
                            block2.getLocationOnScreen(iArr);
                            iArr[INSERT_ABOVE] = iArr[INSERT_ABOVE] - (block.getHeight() - block.NotchDepth);
                            addPossibleTarget(iArr, block2, INSERT_ABOVE);
                        }
                        if (!(i == 0 || block2.isHat)) {
                            iArr = new int[INSERT_SUB1];
                            block2.getLocationOnScreen(iArr);
                            iArr[INSERT_NORMAL] = iArr[INSERT_NORMAL] - block2.SubstackInset;
                            iArr[INSERT_ABOVE] = iArr[INSERT_ABOVE] - (block.substack1y() - block.NotchDepth);
                            addPossibleTarget(iArr, block2, INSERT_WRAP);
                        }
                        if (!block.isHat) {
                            boolean z2 = z && i == 0;
                            findCommandTargetsIn(block, block2, z2);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<BlockBean> getBlocks() {
        ArrayList<BlockBean> arrayList = new ArrayList();
        Block block = (Block) findViewWithTag(Integer.valueOf(this.root.nextBlock));
        if (block != null) {
            Iterator it = block.getAllChildren().iterator();
            while (it.hasNext()) {
                arrayList.add(((Block) it.next()).getBean());
            }
        }
        return arrayList;
    }

    public Block getHitBlock(float f, float f2) {
        this.hitTarget = null;
        this.maxDepth = -1;
        for (int i = INSERT_NORMAL; i < getChildCount(); i += INSERT_ABOVE) {
            View childAt = getChildAt(i);
            if ((childAt instanceof Block) && childAt != this.root) {
                Block block = (Block) childAt;
                int[] iArr = new int[INSERT_SUB1];
                block.getLocationOnScreen(iArr);
                if (f > ((float) iArr[INSERT_NORMAL]) && f < ((float) (iArr[INSERT_NORMAL] + block.getWidth())) && f2 > ((float) iArr[INSERT_ABOVE]) && f2 < ((float) (iArr[INSERT_ABOVE] + block.getHeight()))) {
                    int depth = block.getDepth();
                    if (depth > this.maxDepth) {
                        this.maxDepth = depth;
                        this.hitTarget = block;
                    }
                }
            }
        }
        return this.hitTarget;
    }

    public Block getRoot() {
        return this.root;
    }

    public void hideFeedbackShape() {
        this.feedbackShape.setVisibility(8);
    }

    public boolean hitTest(float f, float f2) {
        getLocationOnScreen(this.posArea);
        return f > ((float) this.posArea[INSERT_NORMAL]) && f < ((float) (this.posArea[INSERT_NORMAL] + getWidth())) && f2 > ((float) this.posArea[INSERT_ABOVE]) && f2 < ((float) (this.posArea[INSERT_ABOVE] + getHeight()));
    }

    public boolean isExistListBlock(String str) {
        int childCount = getChildCount();
        for (int i = INSERT_NORMAL; i < childCount; i += INSERT_ABOVE) {
            View childAt = getChildAt(i);
            if (childAt instanceof Block) {
                BlockBean bean = ((Block) childAt).getBean();
                String str2 = bean.opCode;
                int i2 = -1;
                switch (str2.hashCode()) {
                    case -1384861688:
                        if (str2.equals("getAtListInt")) {
                            i2 = 6;
                            break;
                        }
                        break;
                    case -1384851894:
                        if (str2.equals("getAtListStr")) {
                            i2 = 7;
                            break;
                        }
                        break;
                    case -1271141237:
                        if (str2.equals("clearList")) {
                            i2 = INSERT_SUB2;
                            break;
                        }
                        break;
                    case -329562760:
                        if (str2.equals("insertListInt")) {
                            i2 = 11;
                            break;
                        }
                        break;
                    case -329552966:
                        if (str2.equals("insertListStr")) {
                            i2 = 12;
                            break;
                        }
                        break;
                    case -96313603:
                        if (str2.equals("containListInt")) {
                            i2 = INSERT_ABOVE;
                            break;
                        }
                        break;
                    case -96303809:
                        if (str2.equals("containListStr")) {
                            i2 = INSERT_SUB1;
                            break;
                        }
                        break;
                    case 762282303:
                        if (str2.equals("indexListInt")) {
                            i2 = 8;
                            break;
                        }
                        break;
                    case 762292097:
                        if (str2.equals("indexListStr")) {
                            i2 = 9;
                            break;
                        }
                        break;
                    case 1160674468:
                        if (str2.equals("lengthList")) {
                            i2 = INSERT_NORMAL;
                            break;
                        }
                        break;
                    case 1764351209:
                        if (str2.equals("deleteList")) {
                            i2 = 10;
                            break;
                        }
                        break;
                    case 2090179216:
                        if (str2.equals("addListInt")) {
                            i2 = INSERT_WRAP;
                            break;
                        }
                        break;
                    case 2090189010:
                        if (str2.equals("addListStr")) {
                            i2 = INSERT_PARAM;
                            break;
                        }
                        break;
                }
                switch (i2) {
                    case INSERT_NORMAL /*0*/:
                    case INSERT_ABOVE /*1*/:
                    case INSERT_SUB1 /*2*/:
                    case INSERT_SUB2 /*3*/:
                        if (!((String) bean.parameters.get(INSERT_NORMAL)).equals(str)) {
                            break;
                        }
                        return true;
                    case INSERT_WRAP /*4*/:
                    case INSERT_PARAM /*5*/:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        if (!((String) bean.parameters.get(INSERT_ABOVE)).equals(str)) {
                            break;
                        }
                        return true;
                    case 11:
                    case 12:
                        if (!((String) bean.parameters.get(INSERT_SUB1)).equals(str)) {
                            break;
                        }
                        return true;
                    default:
                        continue;
                }
            }
        }
        return false;
    }

    public boolean isExistVariableBlock(String str) {
        int childCount = getChildCount();
        for (int i = INSERT_NORMAL; i < childCount; i += INSERT_ABOVE) {
            View childAt = getChildAt(i);
            if (childAt instanceof Block) {
                BlockBean bean = ((Block) childAt).getBean();
                String str2 = bean.opCode;
                int i2 = -1;
                switch (str2.hashCode()) {
                    case -1920517885:
                        if (str2.equals("setVarBoolean")) {
                            i2 = INSERT_ABOVE;
                            break;
                        }
                        break;
                    case -1377080719:
                        if (str2.equals("decreaseInt")) {
                            i2 = INSERT_PARAM;
                            break;
                        }
                        break;
                    case -1249347599:
                        if (str2.equals("getVar")) {
                            i2 = INSERT_NORMAL;
                            break;
                        }
                        break;
                    case 657721930:
                        if (str2.equals("setVarInt")) {
                            i2 = INSERT_SUB1;
                            break;
                        }
                        break;
                    case 754442829:
                        if (str2.equals("increaseInt")) {
                            i2 = INSERT_WRAP;
                            break;
                        }
                        break;
                    case 845089750:
                        if (str2.equals("setVarString")) {
                            i2 = INSERT_SUB2;
                            break;
                        }
                        break;
                }
                switch (i2) {
                    case INSERT_NORMAL /*0*/:
                        if (!bean.spec.equals(str)) {
                            break;
                        }
                        return true;
                    case INSERT_ABOVE /*1*/:
                    case INSERT_SUB1 /*2*/:
                    case INSERT_SUB2 /*3*/:
                    case INSERT_WRAP /*4*/:
                    case INSERT_PARAM /*5*/:
                        if (!((String) bean.parameters.get(INSERT_NORMAL)).equals(str)) {
                            break;
                        }
                        return true;
                    default:
                        continue;
                }
            }
        }
        return false;
    }

    public void logAll() {
        for (int i = INSERT_NORMAL; i < getChildCount(); i += INSERT_ABOVE) {
            View childAt = getChildAt(i);
            if (childAt instanceof Block) {
                ((Block) childAt).log();
            }
        }
    }

    public Object[] nearestTargetForBlockIn(Block block, int i, int i2) {
        int i3 = block.isReporter ? 40 : 60;
        int i4 = 100000;
        Object[] objArr = null;
        Point point = new Point(i, i2);
        int i5 = INSERT_NORMAL;
        while (i5 < this.possibleTargets.size()) {
            int i6;
            Object[] objArr2 = (Object[]) this.possibleTargets.get(i5);
            int[] iArr = (int[]) objArr2[INSERT_NORMAL];
            Point point2 = new Point(point.x - iArr[INSERT_NORMAL], point.y - iArr[INSERT_ABOVE]);
            int abs = Math.abs(point2.y) + Math.abs(point2.x / INSERT_SUB1);
            if (abs >= i4 || abs >= i3 || !dropCompatible(block, (View) objArr2[INSERT_ABOVE])) {
                objArr2 = objArr;
                i6 = i4;
            } else {
                i6 = abs;
            }
            i5 += INSERT_ABOVE;
            i4 = i6;
            objArr = objArr2;
        }
        return objArr;
    }

    public void prepareToDrag(Block block) {
        findTargetsFor(block);
        this.nearestTarget = null;
    }

    public void removeBlock(Block block) {
        removeRelation(block);
        Iterator it = block.getAllChildren().iterator();
        while (it.hasNext()) {
            removeView((Block) it.next());
        }
    }

    public void removeRelation(Block block) {
        if (block.parentBlock != null) {
            Block block2 = block.parentBlock;
            if (block2 != null) {
                block2.removeBlock(block);
                block.parentBlock = null;
            }
        }
    }

    public void setVisibleBlock(Block block, int i) {
        while (block != null) {
            block.setVisibility(i);
            Iterator it = block.args.iterator();
            while (it.hasNext()) {
                View view = (View) it.next();
                if (view instanceof Block) {
                    setVisibleBlock((Block) view, i);
                }
            }
            if (block.canHaveSubstack1() && block.subStack1 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(block.subStack1)), i);
            }
            if (block.canHaveSubstack2() && block.subStack2 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(block.subStack2)), i);
            }
            if (block.nextBlock != -1) {
                block = (Block) findViewWithTag(Integer.valueOf(block.nextBlock));
            } else {
                return;
            }
        }
    }

    public void updateFeedbackFor(Block block, int i, int i2) {
        getLocationOnScreen(this.posArea);
        this.nearestTarget = nearestTargetForBlockIn(block, i, i2);
        if (block.canHaveSubstack1() && -1 == block.subStack1 && this.nearestTarget != null) {
            Block block2 = (Block) this.nearestTarget[INSERT_ABOVE];
            switch (((Integer) this.nearestTarget[INSERT_SUB1]).intValue()) {
                case INSERT_NORMAL /*0*/:
                    block2 = (Block) findViewWithTag(Integer.valueOf(block2.nextBlock));
                    break;
                case INSERT_SUB1 /*2*/:
                    block2 = (Block) findViewWithTag(Integer.valueOf(block2.subStack1));
                    break;
                case INSERT_SUB2 /*3*/:
                    block2 = (Block) findViewWithTag(Integer.valueOf(block2.subStack2));
                    break;
            }
        }
        if (this.nearestTarget != null) {
            int[] iArr = (int[]) this.nearestTarget[INSERT_NORMAL];
            View view = (View) this.nearestTarget[INSERT_ABOVE];
            this.feedbackShape.setX((float) (iArr[INSERT_NORMAL] - this.posArea[INSERT_NORMAL]));
            this.feedbackShape.setY((float) (iArr[INSERT_ABOVE] - this.posArea[INSERT_ABOVE]));
            this.feedbackShape.bringToFront();
            this.feedbackShape.setVisibility(INSERT_NORMAL);
            if (block.isReporter) {
                if (view instanceof Block) {
                    this.feedbackShape.copyFeedbackShapeFrom((Block) view, true, false, INSERT_NORMAL);
                }
                if (view instanceof BlockArg) {
                    this.feedbackShape.copyFeedbackShapeFrom((BlockArg) view, true, false, INSERT_NORMAL);
                    return;
                }
                return;
            }
            int intValue = ((Integer) this.nearestTarget[INSERT_SUB1]).intValue();
            int heightSum = intValue == INSERT_WRAP ? ((Block) view).getHeightSum() : INSERT_NORMAL;
            boolean z = (intValue == INSERT_ABOVE || intValue == INSERT_WRAP) ? false : true;
            this.feedbackShape.copyFeedbackShapeFrom(block, false, z, heightSum);
            return;
        }
        hideFeedbackShape();
    }
}
