package com.besome.blacklogics.logic.editor;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google. android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.blacklogics.R;
import com.besome.blacklogics.ViewEditorFragmentActivity;
import com.besome.blacklogics.DesignActivity;
import com.besome.blacklogics.development.Complex;

import com.besome.blacklogics.beans.SourceCodeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.content.Context;
import java.util.Arrays;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.DialogInterface;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


public class LogicEditorActivity extends AppCompatActivity implements OnClickListener, OnBlockCategorySelectListener, OnTouchListener {
	private static final String TAG = "LogicEditorActivity";
	
	public static final String LOGIC_NAME_SEPARATOR = "_";
	private static final int PALETTE_SIZE_HORIZONTAL = 320;
	private static final int PALETTE_SIZE_VERTICAL = 240;
	public static String filename = "";
	private String BLOCK_PATH = "/storage/emulated/0/.blacklogics/resources/block/My Block/block.json";
	private Context context;
	
	private Complex complex;
	
	private String activityName;
	
	private ObjectAnimator aniHideIconDelete;
	private ObjectAnimator aniHidePalette;
	private ObjectAnimator aniShowIconDelete;
	private ObjectAnimator aniShowPalette;
	private LinearLayout areaPalette;
	private boolean bActiveIconDelete = false;
	private boolean bInitIconDeleteAnimation = false;
	private boolean bInitPaletteAnimation = false;
	private boolean bShowIconDelete = false;
	private BlockCopyInterface blockCopyInterface;
	private VariableNameValidator booleanValidator;
	private View currentTouchedView = null;
	private ViewDummy dummy;
	private ViewLogicEditor editor;
	private String eventName = "";
	private FloatingActionButton fab;
	private final Handler handler = new Handler();
	private ImageView iconDelete;
	private String id = "";
	private boolean isDragged = false;
	private boolean isPaletteOpened = false;
	private LinearLayout layoutPalette;
	private Runnable longPressed = new Runnable() {
		@Override
		public void run() {
			dragStart();
		}
	};
	private AlertDialog mDlg;
	private Menu menu;
	private int minDist = 0;
	private VariableNameValidator numberValidator;
	private int originalArgIndex;
	private int originalInsertOption;
	private Block originalParent;
	private PaletteBlock paletteBlock;
	private PaletteSelector paletteSelector;
	private BlockPane pane;
	private int[] posDummy = new int[2];
	private float posInitX = 0.0f;
	private float posInitY = 0.0f;
	private int[] posOriginal = new int[2];
	private SharedPreferenceUtil prefBackup;
	private SharedPreferenceUtil prefInstall;
	private VariableNameValidator stringValidator;
	private Toolbar toolbar;
	private boolean useVibrate;
	private Vibrator vibrator;
	
	private List<BlockDefinition> blockDefinitions;
	private Set<String> validPaletteIds;
	private Map<Integer, String> indexToPaletteId;
	
	//new
	public int BLOCK_DRAG_X = 0;
	public int BLOCK_DRAG_Y = -30;
	
	public String widgetId;
	
	private void activeIconDelete(boolean z) {
		if (this.bActiveIconDelete != z) {
			this.bActiveIconDelete = z;
			if (this.bActiveIconDelete) {
				this.iconDelete.setImageResource(R.drawable.icon_delete_active);
			} else {
				this.iconDelete.setImageResource(R.drawable.icon_delete);
			}
		}
	}
	
	private void addBlockToPalette(String str, String str2, String str3, int i, Object... objArr) {
		BlockBase addBlock = this.paletteBlock.addBlock(str, str2, str3, i, objArr);
		addBlock.setClickable(true);
		addBlock.setOnTouchListener(this);
	}
	
	private void addButtonToPalette(String str, String str2) {
		View addButton = this.paletteBlock.addButton(str);
		addButton.setTag(str2);
		addButton.setSoundEffectsEnabled(true);
		addButton.setOnClickListener(this);
	}
	
	private void addFunctions() {
		Iterator it = DesignDataManager.getFunctions(filename).iterator();
		while (it.hasNext()) {
			addBlockToPalette((String) ((Pair) it.next()).second, " ", "definedFunc", -7711273, new Object[0]);
		}
	}
	
	private void addLists() {
		Iterator it = DesignDataManager.getLists(filename).iterator();
		int i = 0;
		int i2 = 0;
		while (it.hasNext()) {
			if (((Integer) ((Pair) it.next()).first).intValue() == 1) {
				i2++;
			} else {
				i++;
			}
		}
		if (i2 > 0) {
			ArrayList arrayList = new ArrayList();
			addBlockToPalette("", " ", "addListInt", -3384542, new Object[0]);
			addBlockToPalette("", " ", "insertListInt", -3384542, new Object[0]);
			addBlockToPalette("", "d", "getAtListInt", -3384542, new Object[0]);
			addBlockToPalette("", "d", "indexListInt", -3384542, new Object[0]);
			addBlockToPalette("", "b", "containListInt", -3384542, new Object[0]);
		}
		if (i > 0) {
			addBlockToPalette("", " ", "addListStr", -3384542, new Object[0]);
			addBlockToPalette("", " ", "insertListStr", -3384542, new Object[0]);
			addBlockToPalette("", "s", "getAtListStr", -3384542, new Object[0]);
			addBlockToPalette("", "d", "indexListStr", -3384542, new Object[0]);
			addBlockToPalette("", "b", "containListStr", -3384542, new Object[0]);
		}
		if (i2 > 0 || i > 0) {
			addBlockToPalette("", " ", "deleteList", -3384542, new Object[0]);
			addBlockToPalette("", "d", "lengthList", -3384542, new Object[0]);
			addBlockToPalette("", " ", "clearList", -3384542, new Object[0]);
		}
	}
	
	private void addVariables() {
		Iterator it = DesignDataManager.getVariables(filename).iterator();
		int i = 0;
		int i2 = 0;
		int i3 = 0;
		while (it.hasNext()) {
			Pair pair = (Pair) it.next();
			if (((Integer) pair.first).intValue() == 0) {
				addBlockToPalette((String) pair.second, "b", "getVar", -1147626, new Object[0]);
				i3++;
			} else if (((Integer) pair.first).intValue() == 1) {
				addBlockToPalette((String) pair.second, "d", "getVar", -1147626, new Object[0]);
				i2++;
			} else {
				addBlockToPalette((String) pair.second, "s", "getVar", -1147626, new Object[0]);
				i++;
			}
		}
		if (i3 > 0) {
			addBlockToPalette("", " ", "setVarBoolean", -1147626, new Object[0]);
		}
		if (i2 > 0) {
			addBlockToPalette("", " ", "setVarInt", -1147626, new Object[0]);
			addBlockToPalette("", " ", "increaseInt", -1147626, new Object[0]);
			addBlockToPalette("", " ", "decreaseInt", -1147626, new Object[0]);
		}
		if (i > 0) {
			addBlockToPalette("", " ", "setVarString", -1147626, new Object[0]);
		}
	}
	
	private void allocateBlockArea(int i) {
		int i2 = -1;
		if (this.isPaletteOpened) {
			int i3 = getResources().getDisplayMetrics().widthPixels;
			int i4 = getResources().getDisplayMetrics().heightPixels;
			if (i3 <= i4) {
				i3 = i4;
			}
			if (2 == i) {
				i3 -= (int) LayoutUtil.getDip(this, 320.0f);
			} else {
				int height = ((i3 - getSupportActionBar().getHeight()) - SysUtil.getStatusBarHeight(this.context)) - ((int) LayoutUtil.getDip(this, 240.0f));
				i3 = -1;
				i2 = height;
			}
			this.blockCopyInterface.setLayoutParams(new LayoutParams(i3, i2));
			this.blockCopyInterface.requestLayout();
			return;
		}
		this.blockCopyInterface.setLayoutParams(new LayoutParams(-1, -1));
		this.blockCopyInterface.requestLayout();
	}
	
	private void allocatePalette(int var1) {
		if(2 == var1) {
			LayoutParams var2 = new LayoutParams((int)LayoutUtil.getDip(this, 320.0F), -1);
			this.areaPalette.setLayoutParams(var2);
			LayoutParams var3 = new LayoutParams(-2, -2);
			var3.gravity = 81;
			int var4 = (int)this.getResources().getDimension(R.dimen.action_button_margin);
			var3.setMargins(var4, var4, var4, var4);
			this.fab.setLayoutParams(var3);
			android.widget.RelativeLayout.LayoutParams var5 = new android.widget.RelativeLayout.LayoutParams(-2, -1);
			var5.addRule(10);
			var5.addRule(11);
			var5.topMargin = this.getSupportActionBar().getHeight();
			this.layoutPalette.setOrientation(0);
			this.layoutPalette.setLayoutParams(var5);
		} else {
			LayoutParams var6 = new LayoutParams(-1, (int)LayoutUtil.getDip(this, 240.0F));
			this.areaPalette.setLayoutParams(var6);
			LayoutParams var7 = new LayoutParams(-2, -2);
			var7.gravity = 21;
			int var8 = (int)this.getResources().getDimension(R.dimen.action_button_margin);
			var7.setMargins(var8, var8, var8, var8);
			this.fab.setLayoutParams(var7);
			android.widget.RelativeLayout.LayoutParams var9 = new android.widget.RelativeLayout.LayoutParams(-1, -2);
			var9.addRule(9);
			var9.addRule(12);
			this.layoutPalette.setOrientation(1);
			this.layoutPalette.setLayoutParams(var9);
		}
		
		this.initPaletteAnimation(var1);
		this.allocateBlockArea(var1);
	}
	
	private void backupCurrentData(Bundle bundle) {
	}
	
	private void cancelIconDeleteAnimation() {
		if (this.aniShowIconDelete.isRunning()) {
			this.aniShowIconDelete.cancel();
		}
		if (this.aniHideIconDelete.isRunning()) {
			this.aniHideIconDelete.cancel();
		}
	}
	
	private void cancelPaletteAnimation() {
		if (this.aniShowPalette.isRunning()) {
			this.aniShowPalette.cancel();
		}
		if (this.aniHidePalette.isRunning()) {
			this.aniHidePalette.cancel();
		}
	}
	
	private RadioButton createSingleItem(String str) {
		RadioButton radioButton = new RadioButton(this);
		radioButton.setText(str);
		ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, (int) (40.0f * LayoutUtil.getDip(this.context, 1.0f)));
		radioButton.setGravity(19);
		radioButton.setLayoutParams(layoutParams);
		return radioButton;
	}
	
	private void dragStart() {
		if (this.currentTouchedView != null) {
			this.paletteBlock.setDragEnabled(false);
			this.editor.setScrollEnabled(false);
			if (this.useVibrate) {
				this.vibrator.vibrate(100);
			}
			this.isDragged = true;
			if (((Block) this.currentTouchedView).getBlockType() == 0) {
				getOriginalState((Block) this.currentTouchedView);
				showIconDelete(true);
				this.dummy.makeDummyWithBlock((Block) this.currentTouchedView);
				this.pane.setVisibleBlock((Block) this.currentTouchedView, 8);
				this.pane.removeRelation((Block) this.currentTouchedView);
			} else {
				this.dummy.makeDummyWithBlock((Block) this.currentTouchedView);
			}
			this.pane.prepareToDrag((Block) this.currentTouchedView);
			this.dummy.moveDummy(this.currentTouchedView, this.posInitX, this.posInitY, this.posInitX, this.posInitY, (float)BLOCK_DRAG_X, (float)BLOCK_DRAG_Y);
			this.dummy.getDummyPosition(this.posDummy);
			if (this.editor.hitTest((float) this.posDummy[0], (float) this.posDummy[1])) {
				this.dummy.setAllow(true);
				this.pane.updateFeedbackFor((Block) this.currentTouchedView, this.posDummy[0], this.posDummy[1]);
				return;
			}
			this.dummy.setAllow(false);
			this.pane.hideFeedbackShape();
		}
	}
	
	private int getLabelWidth(TextView textView) {
		Rect rect = new Rect();
		textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
		return rect.width();
	}
	
	private void getOriginalState(Block block) {
		this.originalParent = null;
		this.originalArgIndex = -1;
		this.originalInsertOption = 0;
		this.posOriginal = new int[2];
		block.getLocationOnScreen(this.posOriginal);
		if (block.parentBlock != null) {
			this.originalParent = block.parentBlock;
		}
		if (this.originalParent != null) {
			if (this.originalParent.nextBlock == ((Integer) block.getTag()).intValue()) {
				this.originalInsertOption = 0;
			} else if (this.originalParent.subStack1 == ((Integer) block.getTag()).intValue()) {
				this.originalInsertOption = 2;
			} else if (this.originalParent.subStack2 == ((Integer) block.getTag()).intValue()) {
				this.originalInsertOption = 3;
			} else if (this.originalParent.args.contains(block)) {
				this.originalInsertOption = 5;
				this.originalArgIndex = this.originalParent.args.indexOf(block);
			}
		}
	}
	
	private boolean hitTestIconDelete(float f, float f2) {
		int[] iArr = new int[2];
		this.iconDelete.getLocationOnScreen(iArr);
		return f > ((float) iArr[0]) && f < ((float) (iArr[0] + this.iconDelete.getWidth())) && f2 > ((float) iArr[1]) && f2 < ((float) (iArr[1] + this.iconDelete.getHeight()));
	}
	
	private void initIconDeleteAnimation() {
		this.aniShowIconDelete = ObjectAnimator.ofFloat(this.iconDelete, "TranslationY", new float[]{0.0f});
		this.aniShowIconDelete.setDuration(500);
		this.aniShowIconDelete.setInterpolator(new DecelerateInterpolator());
		this.aniHideIconDelete = ObjectAnimator.ofFloat(this.iconDelete, "TranslationY", new float[]{(float) this.iconDelete.getHeight()});
		this.aniHideIconDelete.setDuration(300);
		this.aniHideIconDelete.setInterpolator(new DecelerateInterpolator());
		this.bInitIconDeleteAnimation = true;
	}
	
	private void initPaletteAnimation(int i) {
		if (2 == i) {
			if (this.isPaletteOpened) {
				this.layoutPalette.setTranslationX(0.0f);
				this.layoutPalette.setTranslationY(0.0f);
			} else {
				this.layoutPalette.setTranslationX((float) ((int) LayoutUtil.getDip(this, 320.0f)));
				this.layoutPalette.setTranslationY(0.0f);
			}
		} else if (this.isPaletteOpened) {
			this.layoutPalette.setTranslationX(0.0f);
			this.layoutPalette.setTranslationY(0.0f);
		} else {
			this.layoutPalette.setTranslationX(0.0f);
			this.layoutPalette.setTranslationY((float) ((int) LayoutUtil.getDip(this, 240.0f)));
		}
		if (2 == i) {
			this.aniShowPalette = ObjectAnimator.ofFloat(this.layoutPalette, "TranslationX", new float[]{0.0f});
			this.aniHidePalette = ObjectAnimator.ofFloat(this.layoutPalette, "TranslationX", new float[]{(float) ((int) LayoutUtil.getDip(this, 320.0f))});
		} else {
			this.aniShowPalette = ObjectAnimator.ofFloat(this.layoutPalette, "TranslationY", new float[]{0.0f});
			this.aniHidePalette = ObjectAnimator.ofFloat(this.layoutPalette, "TranslationY", new float[]{(float) ((int) LayoutUtil.getDip(this, 240.0f))});
		}
		this.aniShowPalette.removeAllListeners();
		this.aniHidePalette.removeAllListeners();
		this.aniShowPalette.addListener(new Animator.AnimatorListener() {
			public void onAnimationCancel(Animator var1) {
			}
			
			public void onAnimationEnd(Animator var1) {
				updateIconDeletePosition();
			}
			
			public void onAnimationRepeat(Animator var1) {
			}
			
			public void onAnimationStart(Animator var1) {
			}
		});
		this.aniHidePalette.addListener(new Animator.AnimatorListener() {
			public void onAnimationCancel(Animator var1) {
			}
			
			public void onAnimationEnd(Animator var1) {
			}
			
			public void onAnimationRepeat(Animator var1) {
			}
			
			public void onAnimationStart(Animator var1) {
				updateIconDeletePosition();
			}
		});
		this.aniShowPalette.setDuration(500);
		this.aniShowPalette.setInterpolator(new DecelerateInterpolator());
		this.aniHidePalette.setDuration(300);
		this.aniHidePalette.setInterpolator(new DecelerateInterpolator());
		this.bInitPaletteAnimation = true;
	}
	
	private void loadLogic() {
		String key = filename + "_" + id + LOGIC_NAME_SEPARATOR + eventName;
		ArrayList<BlockBean> blocks = DesignDataManager.loadBlocksFromFile(getApplicationContext(), "data", key, DesignActivity.getScId());
		if (blocks != null && !blocks.isEmpty()) {
			Map<Integer, Block> blockMap = new HashMap<>();
			// Step 1: Create all blocks and map them by ID
			Iterator<BlockBean> it = blocks.iterator();
			int isFirstBlock = 1;
			while (it.hasNext()) {
				BlockBean blockBean = it.next();
				Block block = makeBlockFromBean(blockBean);
				blockMap.put(Integer.valueOf(blockBean.id), block);
				pane.blockId = Math.max(pane.blockId, Integer.parseInt(blockBean.id) + 1);
				pane.addBlock(block, 0, 0);
				block.setOnTouchListener(this);
				if (isFirstBlock != 0) {
					pane.getRoot().insertBlock(block);
					isFirstBlock = 0;
				}
			}
			
			// Step 2: Reconstruct relationships (subStack1, subStack2, nextBlock, parameters)
			Iterator<BlockBean> it2 = blocks.iterator();
			while (it2.hasNext()) {
				BlockBean blockBean = it2.next();
				Block block = blockMap.get(Integer.valueOf(blockBean.id));
				if (block != null) {
					// Handle subStack1
					if (blockBean.subStack1 >= 0) {
						Block subBlock = blockMap.get(blockBean.subStack1);
						if (subBlock != null) {
							block.insertBlockSub1(subBlock);
						}
					}
					// Handle subStack2
					if (blockBean.subStack2 >= 0) {
						Block subBlock = blockMap.get(blockBean.subStack2);
						if (subBlock != null) {
							block.insertBlockSub2(subBlock);
						}
					}
					// Handle nextBlock
					if (blockBean.nextBlock >= 0) {
						Block nextBlock = blockMap.get(blockBean.nextBlock);
						if (nextBlock != null) {
							block.insertBlock(nextBlock);
						}
					}
					// Handle parameters
					int size = blockBean.parameters.size();
					for (int i = 0; i < size; i++) {
						String param = blockBean.parameters.get(i);
						if (param != null && !param.isEmpty()) {
							if (param.charAt(0) == '@') {
								// Parameter is a block reference
								Block paramBlock = blockMap.get(Integer.valueOf(param.substring(1)));
								if (paramBlock != null) {
									block.replaceArgWithBlock((BlockBase) block.args.get(i), paramBlock);
								}
							} else {
								// Parameter is a value
								((BlockArg) block.args.get(i)).setArgValue(param);
								block.recalcWidthToParent();
							}
						}
					}
				}
			}
			
			// Step 3: Fix layout and calculate dimensions
			pane.getRoot().fixLayout();
			pane.calculateWidthHeight();
		}
	}
	
	private void makeBlockWithSpec(ViewGroup viewGroup, ViewGroup viewGroup2, Block block, String str, ArrayList<Pair<String, String>> arrayList) {
		int i;
		int i2;
		viewGroup.removeAllViews();
		viewGroup.addView(block);
		Iterator it = arrayList.iterator();
		String str2 = str;
		while (it.hasNext()) {
			Pair pair = (Pair) it.next();
			str2 = ((String) pair.first).equals("b") ? str2 + " %b." + ((String) pair.second) : ((String) pair.first).equals("d") ? str2 + " %d." + ((String) pair.second) : ((String) pair.first).equals("s") ? str2 + " %s." + ((String) pair.second) : str2 + " " + ((String) pair.second);
		}
		block.setSpec(str2, null);
		int size = arrayList.size();
		int i3 = 0;
		for (i = 0; i < size; i++) {
			Pair pair = (Pair) arrayList.get(i);
			Block block2;
			int i4;
			if (((String) pair.first).equals("b")) {
				block2 = new Block(getApplicationContext(), arrayList.indexOf(pair) + 1, (String) pair.second, "b", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
				viewGroup.addView(block2);
				i4 = i3 + 1;
				block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
				i2 = i4;
			} else if (((String) pair.first).equals("d")) {
				block2 = new Block(getApplicationContext(), arrayList.indexOf(pair) + 1, (String) pair.second, "d", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
				viewGroup.addView(block2);
				i4 = i3 + 1;
				block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
				i2 = i4;
			} else if (((String) pair.first).equals("s")) {
				block2 = new Block(getApplicationContext(), arrayList.indexOf(pair) + 1, (String) pair.second, "s", "getParam", new Object[]{Integer.valueOf(-7711273), ""});
				viewGroup.addView(block2);
				i4 = i3 + 1;
				block.replaceArgWithBlock((BlockBase) block.args.get(i3), block2);
				i2 = i4;
			} else {
				i2 = i3;
			}
			i3 = i2;
		}
		block.fixLayout();
		viewGroup2.removeAllViews();
		i = block.labelsAndArgs.size();
		for (i3 = 0; i3 < i; i3++) {
			View view = (View) block.labelsAndArgs.get(i3);
			int i5 = 0;
			if (((String) block.argTypes.get(i3)).equals("label")) {
				i5 = getLabelWidth((TextView) view);
			}
			if (view instanceof Block) {
				i5 = ((Block) view).getWidthSum();
			}
			i2 = (int) (((float) i5) + LayoutUtil.getDip(getApplicationContext(), 4.0f));
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.ic_remove_grey600_24dp);
			imageView.setScaleType(ScaleType.CENTER_INSIDE);
			imageView.setPadding(0, (int) LayoutUtil.getDip(getApplicationContext(), 4.0f), 0, (int) LayoutUtil.getDip(getApplicationContext(), 4.0f));
			imageView.setLayoutParams(new LayoutParams(i2, -1));
			viewGroup2.addView(imageView);
			if (i3 == 0) {
				imageView.setVisibility(4);
				imageView.setEnabled(false);
			} else {
				imageView.setOnClickListener(new LogicEditorActivity$18(this, arrayList, viewGroup2, viewGroup, block, str));
			}
		}
	}
	
	
	
	class LogicEditorActivity$18 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$b;
		// $FF: synthetic field
		final ViewGroup val$removeArea;
		// $FF: synthetic field
		final String val$spec;
		// $FF: synthetic field
		final ViewGroup val$v;
		
		LogicEditorActivity$18(LogicEditorActivity var1, ArrayList var2, ViewGroup var3, ViewGroup var4, Block var5, String var6) {
			this.this$0 = var1;
			this.val$args = var2;
			this.val$removeArea = var3;
			this.val$v = var4;
			this.val$b = var5;
			this.val$spec = var6;
		}
		
		public void onClick(View var1) {
			this.val$args.remove(-1 + this.val$removeArea.indexOfChild(var1));
			ArrayList var3 = new ArrayList(Arrays.asList(new String[0]/*DefineSource.getUsedWord(DesignActivity.getScId())*/));
			Iterator var4 = this.val$args.iterator();
			
			while(var4.hasNext()) {
				Pair var5 = (Pair)var4.next();
				if(!((String)var5.first).equals("t")) {
					var3.add(var5.second);
				}
			}
			
			booleanValidator.setUsedWords((String[])var3.toArray(new String[var3.size()]));
			numberValidator.setUsedWords((String[])var3.toArray(new String[var3.size()]));
			stringValidator.setUsedWords((String[])var3.toArray(new String[var3.size()]));
			makeBlockWithSpec(this.val$v, this.val$removeArea, this.val$b, this.val$spec, this.val$args);
		}
	}
	
	
	
	
	private void openPalette(boolean z) {
		if (!this.bInitPaletteAnimation) {
			initPaletteAnimation(getResources().getConfiguration().orientation);
		}
		if (this.isPaletteOpened != z) {
			this.isPaletteOpened = z;
			cancelPaletteAnimation();
			if (z) {
				this.aniShowPalette.start();
			} else {
				this.aniHidePalette.start();
			}
			allocateBlockArea(getResources().getConfiguration().orientation);
		}
	}
	
	private void pasteCopiedBlocks() {
		if (DesignDataManager.isExistClipboard(filename)) {
			int i;
			BlockBean blockBean;
			int i2;
			int i3;
			Block makeBlockFromBean;
			Map hashMap = new HashMap();
			Map hashMap2 = new HashMap();
			ArrayList clipboard = DesignDataManager.getClipboard(filename);
			Iterator it = clipboard.iterator();
			while (it.hasNext()) {
				Integer valueOf = Integer.valueOf(((BlockBean) it.next()).id);
				BlockPane blockPane = this.pane;
				i = blockPane.blockId;
				blockPane.blockId = i + 1;
				hashMap2.put(valueOf, Integer.valueOf(i));
			}
			Iterator it2 = clipboard.iterator();
			while (it2.hasNext()) {
				blockBean = (BlockBean) it2.next();
				if (blockBean.opCode.equals("getArg")) {
					i = 0;
					i2 = 0;
					while (i < this.pane.getRoot().args.size()) {
						View view = (View) this.pane.getRoot().args.get(i);
						i3 = ((view instanceof Block) && blockBean.type.equals(((Block) view).mType) && blockBean.spec.equals(((Block) view).mSpec)) ? 1 : i2;
						i++;
						i2 = i3;
					}
					if (i2 == 0) {
						hashMap2.put(Integer.valueOf(blockBean.id), Integer.valueOf(0));
					}
				}
			}
			Iterator it3 = clipboard.iterator();
			while (it3.hasNext()) {
				blockBean = (BlockBean) it3.next();
				blockBean.id = String.valueOf(hashMap2.get(Integer.valueOf(blockBean.id)));
				i2 = blockBean.parameters.size();
				for (i3 = 0; i3 < i2; i3++) {
					String str = (String) blockBean.parameters.get(i3);
					if (str != null && str.length() > 0 && str.charAt(0) == '@') {
						Integer num = (Integer) hashMap2.get(Integer.valueOf(Integer.valueOf(str.substring(1)).intValue()));
						if (num == null) {
							blockBean.parameters.set(i3, "");
						} else {
							blockBean.parameters.set(i3, '@' + String.valueOf(num));
						}
					}
				}
				if (blockBean.subStack1 >= 0) {
					blockBean.subStack1 = ((Integer) hashMap2.get(Integer.valueOf(blockBean.subStack1))).intValue();
				}
				if (blockBean.subStack2 >= 0) {
					blockBean.subStack2 = ((Integer) hashMap2.get(Integer.valueOf(blockBean.subStack2))).intValue();
				}
				if (blockBean.nextBlock >= 0) {
					blockBean.nextBlock = ((Integer) hashMap2.get(Integer.valueOf(blockBean.nextBlock))).intValue();
				}
			}
			int[] iArr = new int[2];
			this.editor.getLocationOnScreen(iArr);
			int width = iArr[0] + (this.editor.getWidth() / 2);
			i3 = ((int) LayoutUtil.getDip(getApplicationContext(), 4.0f)) + iArr[1];
			it3 = clipboard.iterator();
			Block block = null;
			while (it3.hasNext()) {
				blockBean = (BlockBean) it3.next();
				if (!blockBean.id.equals("0")) {
					makeBlockFromBean = makeBlockFromBean(blockBean);
					hashMap.put(Integer.valueOf(makeBlockFromBean.getTag().toString()), makeBlockFromBean);
					this.pane.addBlock(makeBlockFromBean, width, i3);
					makeBlockFromBean.setOnTouchListener(this);
					block = makeBlockFromBean;
				}
			}
			Iterator it4 = clipboard.iterator();
			while (it4.hasNext()) {
				blockBean = (BlockBean) it4.next();
				if (!blockBean.id.equals("0")) {
					Block block2 = (Block) hashMap.get(Integer.valueOf(blockBean.id));
					if (block2 != null) {
						Block block3;
						int size = blockBean.parameters.size();
						for (int i4 = 0; i4 < size; i4++) {
							String str2 = (String) blockBean.parameters.get(i4);
							if (str2 != null && str2.length() > 0) {
								if (str2.charAt(0) == '@') {
									block3 = (Block) hashMap.get(Integer.valueOf(Integer.valueOf(str2.substring(1)).intValue()));
									if (block3 != null) {
										block2.replaceArgWithBlock((BlockBase) block2.args.get(i4), block3);
									}
								} else {
									((BlockArg) block2.args.get(i4)).setArgValue(str2);
									block2.recalcWidthToParent();
								}
							}
						}
						if (blockBean.subStack1 >= 0) {
							block3 = (Block) hashMap.get(Integer.valueOf(blockBean.subStack1));
							if (block3 != null) {
								block2.insertBlockSub1(block3);
							}
						}
						if (blockBean.subStack2 >= 0) {
							block3 = (Block) hashMap.get(Integer.valueOf(blockBean.subStack2));
							if (block3 != null) {
								block2.insertBlockSub2(block3);
							}
						}
						if (blockBean.nextBlock >= 0) {
							makeBlockFromBean = (Block) hashMap.get(Integer.valueOf(blockBean.nextBlock));
							if (makeBlockFromBean != null) {
								block2.insertBlock(makeBlockFromBean);
							}
						}
					}
				}
			}
			block.topBlock().fixLayout();
			this.pane.calculateWidthHeight();
			return;
		}
		Toast.makeText(this, "No block for copying (for debug)", 0).show();
	}
	
	private void saveLogic() {
		ArrayList<BlockBean> blocks = pane.getBlocks();
		String key = filename + "_" + id + LOGIC_NAME_SEPARATOR + eventName;
		DesignDataManager.saveBlocksToFile(getApplicationContext(), "data", key, blocks, DesignActivity.getScId());
	}
	
	private void showAddBlockPopup() {
		View inflate = LayoutUtil.inflate(this, R.layout.logic_popup_add_block);
		Builder builder = new Builder(this);
		builder.setView(inflate);
		builder.setTitle(getString(R.string.logic_popup_title_make_block));
		ArrayList arrayList = new ArrayList();
		RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.block_area);
		LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.remove_area);
		Block block = new Block(getApplicationContext(), 0, "", " ", "definedFunc", new Object[]{Integer.valueOf(-7711273)});
		relativeLayout.addView(block);
		TextInputLayout textInputLayout = (TextInputLayout) inflate.findViewById(R.id.ti_boolean);
		TextInputLayout textInputLayout2 = (TextInputLayout) inflate.findViewById(R.id.ti_number);
		TextInputLayout textInputLayout3 = (TextInputLayout) inflate.findViewById(R.id.ti_string);
		VariableNameValidator variableNameValidator = new VariableNameValidator(this.context, (TextInputLayout) inflate.findViewById(R.id.ti_name), DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), DesignDataManager.getAllNamesForValid(filename));
		this.booleanValidator = new VariableNameValidator(this.context, textInputLayout, DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), new ArrayList());
		this.numberValidator = new VariableNameValidator(this.context, textInputLayout2, DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), new ArrayList());
		this.stringValidator = new VariableNameValidator(this.context, textInputLayout3, DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), new ArrayList());
		EditText editText = (EditText) inflate.findViewById(R.id.ed_name);
		EditText editText2 = (EditText) inflate.findViewById(R.id.ed_boolean);
		EditText editText3 = (EditText) inflate.findViewById(R.id.ed_number);
		EditText editText4 = (EditText) inflate.findViewById(R.id.ed_string);
		EditText editText5 = (EditText) inflate.findViewById(R.id.ed_label);
		editText.setPrivateImeOptions("defaultInputmode=english;");
		editText2.setPrivateImeOptions("defaultInputmode=english;");
		editText3.setPrivateImeOptions("defaultInputmode=english;");
		editText4.setPrivateImeOptions("defaultInputmode=english;");
		editText5.setPrivateImeOptions("defaultInputmode=english;");
		editText.addTextChangedListener(new LogicEditorActivity$12(this, relativeLayout, linearLayout, block, arrayList));
		((Button) inflate.findViewById(R.id.add_boolean)).setOnClickListener(new LogicEditorActivity$13(this, arrayList, editText2, relativeLayout, linearLayout, block, editText, editText3, editText4));
		((Button) inflate.findViewById(R.id.add_number)).setOnClickListener(new LogicEditorActivity$14(this, arrayList, editText3, relativeLayout, linearLayout, block, editText, editText2, editText4));
		((Button) inflate.findViewById(R.id.add_string)).setOnClickListener(new LogicEditorActivity$15(this, arrayList, editText4, relativeLayout, linearLayout, block, editText, editText2, editText3));
		((Button) inflate.findViewById(R.id.add_label)).setOnClickListener(new LogicEditorActivity$16(this, arrayList, editText5, relativeLayout, linearLayout, block, editText));
		builder.setNegativeButton(R.string.btn_cancel, null);
		builder.setPositiveButton(R.string.btn_accept, null);
		this.mDlg = builder.create();
		this.mDlg.setOnShowListener(new LogicEditorActivity$17(this, variableNameValidator, editText, block));
		this.mDlg.show();
	}
	
	
	class LogicEditorActivity$17 implements DialogInterface.OnShowListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final EditText val$edName;
		// $FF: synthetic field
		final VariableNameValidator val$varNameValidator;
		
		LogicEditorActivity$17(LogicEditorActivity var1, VariableNameValidator var2, EditText var3, Block var4) {
			this.this$0 = var1;
			this.val$varNameValidator = var2;
			this.val$edName = var3;
			this.val$block = var4;
		}
		
		public void onShow(DialogInterface var1) {
			LogicEditorActivity.access$800(this.this$0).getButton(-1).setOnClickListener(new LogicEditorActivity$17$1(this));
		}
	}
	
	class LogicEditorActivity$17$1 implements View.OnClickListener {
		// Synthetic reference to outer class
		final LogicEditorActivity$17 this$1;
		
		LogicEditorActivity$17$1(LogicEditorActivity$17 var1) {
			this.this$1 = var1;
		}
		
		@Override
		public void onClick(View v) {
			if (this.this$1.val$varNameValidator.isValid()) {
				String type = "";
				List<HashMap<String, String>> parameters = new ArrayList<>();
				
				String mSpec = this.this$1.val$block.mSpec;
				String[] parts = mSpec.split(" ");
				
				// Parse parameter types
				for (int i = 1; i < parts.length; i++) {
					String part = parts[i];
					HashMap<String, String> param = new HashMap<>();
					
					if (part.startsWith("%b.")) {
						param.put("type", "boolean");
						param.put("name", part.substring(3)); // remove %b.
					} else if (part.startsWith("%d.")) {
						param.put("type", "double");
						param.put("name", part.substring(3)); // remove %d.
					} else if (part.startsWith("%s.")) {
						param.put("type", "String");
						param.put("name", part.substring(3)); // remove %s.
					} else {
						continue; // skip labels or untyped parts
					}
					
					parameters.add(param);
				}
				
				// Add function!!! 
				DesignActivity.addFunction(DesignActivity.currentActivityBean.getActivityName(), this.this$1.val$edName.getText().toString(), "void", parameters);
				// Save function 
				DesignDataManager.addFunction(
				LogicEditorActivity.filename,
				this.this$1.val$edName.getText().toString(),
				mSpec
				);
				
				this.this$1.this$0.onBlockCategorySelect(5, -7711273);
				LogicEditorActivity.access$800(this.this$1.this$0).dismiss();
			}
		}
	}
	
	
	
	class LogicEditorActivity$16 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final RelativeLayout val$blockArea;
		// $FF: synthetic field
		final EditText val$edLabel;
		// $FF: synthetic field
		final EditText val$edName;
		// $FF: synthetic field
		final LinearLayout val$removeArea;
		
		LogicEditorActivity$16(LogicEditorActivity var1, ArrayList var2, EditText var3, RelativeLayout var4, LinearLayout var5, Block var6, EditText var7) {
			this.this$0 = var1;
			this.val$args = var2;
			this.val$edLabel = var3;
			this.val$blockArea = var4;
			this.val$removeArea = var5;
			this.val$block = var6;
			this.val$edName = var7;
		}
		
		public void onClick(View var1) {
			this.val$args.add(new Pair("t", this.val$edLabel.getText().toString()));
			LogicEditorActivity.access$1200(this.this$0, this.val$blockArea, this.val$removeArea, this.val$block, this.val$edName.getText().toString(), this.val$args);
		}
	}
	
	
	
	
	class LogicEditorActivity$15 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final RelativeLayout val$blockArea;
		// $FF: synthetic field
		final EditText val$edBoolean;
		// $FF: synthetic field
		final EditText val$edName;
		// $FF: synthetic field
		final EditText val$edNumber;
		// $FF: synthetic field
		final EditText val$edString;
		// $FF: synthetic field
		final LinearLayout val$removeArea;
		
		LogicEditorActivity$15(LogicEditorActivity var1, ArrayList var2, EditText var3, RelativeLayout var4, LinearLayout var5, Block var6, EditText var7, EditText var8, EditText var9) {
			this.this$0 = var1;
			this.val$args = var2;
			this.val$edString = var3;
			this.val$blockArea = var4;
			this.val$removeArea = var5;
			this.val$block = var6;
			this.val$edName = var7;
			this.val$edBoolean = var8;
			this.val$edNumber = var9;
		}
		
		public void onClick(View var1) {
			if(LogicEditorActivity.access$1500(this.this$0).isValid()) {
				this.val$args.add(new Pair("s", this.val$edString.getText().toString()));
				LogicEditorActivity.access$1200(this.this$0, this.val$blockArea, this.val$removeArea, this.val$block, this.val$edName.getText().toString(), this.val$args);
				ArrayList var3 = new ArrayList(Arrays.asList(DefineSource.getUsedWord(DesignActivity.getScId())));
				Iterator var4 = this.val$args.iterator();
				
				while(var4.hasNext()) {
					Pair var5 = (Pair)var4.next();
					if(!((String)var5.first).equals("t")) {
						var3.add(var5.second);
					}
				}
				
				LogicEditorActivity.access$1300(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1300(this.this$0).setText(this.val$edBoolean.getText().toString());
				LogicEditorActivity.access$1400(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1400(this.this$0).setText(this.val$edNumber.getText().toString());
				LogicEditorActivity.access$1500(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				this.val$edString.setText("");
			}
		}
	}
	
	
	
	
	class LogicEditorActivity$14 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final RelativeLayout val$blockArea;
		// $FF: synthetic field
		final EditText val$edBoolean;
		// $FF: synthetic field
		final EditText val$edName;
		// $FF: synthetic field
		final EditText val$edNumber;
		// $FF: synthetic field
		final EditText val$edString;
		// $FF: synthetic field
		final LinearLayout val$removeArea;
		
		LogicEditorActivity$14(LogicEditorActivity var1, ArrayList var2, EditText var3, RelativeLayout var4, LinearLayout var5, Block var6, EditText var7, EditText var8, EditText var9) {
			this.this$0 = var1;
			this.val$args = var2;
			this.val$edNumber = var3;
			this.val$blockArea = var4;
			this.val$removeArea = var5;
			this.val$block = var6;
			this.val$edName = var7;
			this.val$edBoolean = var8;
			this.val$edString = var9;
		}
		
		public void onClick(View var1) {
			if(LogicEditorActivity.access$1400(this.this$0).isValid()) {
				this.val$args.add(new Pair("d", this.val$edNumber.getText().toString()));
				LogicEditorActivity.access$1200(this.this$0, this.val$blockArea, this.val$removeArea, this.val$block, this.val$edName.getText().toString(), this.val$args);
				ArrayList var3 = new ArrayList(Arrays.asList(DefineSource.getUsedWord(DesignActivity.getScId())));
				Iterator var4 = this.val$args.iterator();
				
				while(var4.hasNext()) {
					Pair var5 = (Pair)var4.next();
					if(!((String)var5.first).equals("t")) {
						var3.add(var5.second);
					}
				}
				
				LogicEditorActivity.access$1300(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1300(this.this$0).setText(this.val$edBoolean.getText().toString());
				LogicEditorActivity.access$1400(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1500(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1500(this.this$0).setText(this.val$edString.getText().toString());
				this.val$edNumber.setText("");
			}
		}
	}
	
	
	
	class LogicEditorActivity$12 implements TextWatcher {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final RelativeLayout val$blockArea;
		// $FF: synthetic field
		final LinearLayout val$removeArea;
		
		LogicEditorActivity$12(LogicEditorActivity var1, RelativeLayout var2, LinearLayout var3, Block var4, ArrayList var5) {
			this.this$0 = var1;
			this.val$blockArea = var2;
			this.val$removeArea = var3;
			this.val$block = var4;
			this.val$args = var5;
		}
		
		public void afterTextChanged(Editable var1) {
			makeBlockWithSpec(this.val$blockArea, this.val$removeArea, this.val$block, var1.toString(), this.val$args);
		}
		
		public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
		}
		
		public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
		}
	}
	
	
	class LogicEditorActivity$13 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ArrayList val$args;
		// $FF: synthetic field
		final Block val$block;
		// $FF: synthetic field
		final RelativeLayout val$blockArea;
		// $FF: synthetic field
		final EditText val$edBoolean;
		// $FF: synthetic field
		final EditText val$edName;
		// $FF: synthetic field
		final EditText val$edNumber;
		// $FF: synthetic field
		final EditText val$edString;
		// $FF: synthetic field
		final LinearLayout val$removeArea;
		
		LogicEditorActivity$13(LogicEditorActivity var1, ArrayList var2, EditText var3, RelativeLayout var4, LinearLayout var5, Block var6, EditText var7, EditText var8, EditText var9) {
			this.this$0 = var1;
			this.val$args = var2;
			this.val$edBoolean = var3;
			this.val$blockArea = var4;
			this.val$removeArea = var5;
			this.val$block = var6;
			this.val$edName = var7;
			this.val$edNumber = var8;
			this.val$edString = var9;
		}
		
		public void onClick(View var1) {
			if(LogicEditorActivity.access$1300(this.this$0).isValid()) {
				this.val$args.add(new Pair("b", this.val$edBoolean.getText().toString()));
				LogicEditorActivity.access$1200(this.this$0, this.val$blockArea, this.val$removeArea, this.val$block, this.val$edName.getText().toString(), this.val$args);
				ArrayList var3 = new ArrayList(Arrays.asList(DefineSource.getUsedWord(DesignActivity.getScId())));
				Iterator var4 = this.val$args.iterator();
				
				while(var4.hasNext()) {
					Pair var5 = (Pair)var4.next();
					if(!((String)var5.first).equals("t")) {
						var3.add(var5.second);
					}
				}
				
				LogicEditorActivity.access$1300(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1400(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1400(this.this$0).setText(this.val$edNumber.getText().toString());
				LogicEditorActivity.access$1500(this.this$0).setUsedWords((String[])var3.toArray(new String[var3.size()]));
				LogicEditorActivity.access$1500(this.this$0).setText(this.val$edString.getText().toString());
				this.val$edBoolean.setText("");
			}
		}
	}
	
	// $FF: synthetic method
	static boolean access$000(LogicEditorActivity var0) {
		return var0.isPaletteOpened;
	}
	
	// $FF: synthetic method
	static void access$100(LogicEditorActivity var0, boolean var1) {
		var0.openPalette(var1);
	}
	
	// $FF: synthetic method
	static String access$1000(LogicEditorActivity var0) {
		return var0.id;
	}
	
	// $FF: synthetic method
	static String access$1100(LogicEditorActivity var0) {
		return var0.eventName;
	}
	
	// $FF: synthetic method
	static void access$1200(LogicEditorActivity var0, ViewGroup var1, ViewGroup var2, Block var3, String var4, ArrayList var5) {
		var0.makeBlockWithSpec(var1, var2, var3, var4, var5);
	}
	
	// $FF: synthetic method
	static VariableNameValidator access$1300(LogicEditorActivity var0) {
		return var0.booleanValidator;
	}
	
	// $FF: synthetic method
	static VariableNameValidator access$1400(LogicEditorActivity var0) {
		return var0.numberValidator;
	}
	
	// $FF: synthetic method
	static VariableNameValidator access$1500(LogicEditorActivity var0) {
		return var0.stringValidator;
	}
	
	// $FF: synthetic method
	static void access$200(LogicEditorActivity var0) {
		var0.updateIconDeletePosition();
	}
	
	// $FF: synthetic method
	static void access$300(LogicEditorActivity var0) {
		var0.dragStart();
	}
	
	// $FF: synthetic method
	static void access$400(LogicEditorActivity var0) {
		var0.saveLogic();
	}
	
	/*   // $FF: synthetic method
	static void access$500(LogicEditorActivity var0) {
	var0.dismissProgress();
	}
	
	// $FF: synthetic method
	static void access$600(LogicEditorActivity var0) {
	var0.dismissProgress();
	}
	*/
	// $FF: synthetic method
	static Context access$700(LogicEditorActivity var0) {
		return var0.context;
	}
	
	// $FF: synthetic method
	static AlertDialog access$800(LogicEditorActivity var0) {
		return var0.mDlg;
	}
	
	// $FF: synthetic method
	static BlockPane access$900(LogicEditorActivity var0) {
		return var0.pane;
	}
	
	
	
	
	
	private void showAddListPopup() {
		View inflate = LayoutUtil.inflate(this, R.layout.logic_popup_add_list);
		Builder builder = new Builder(this);
		builder.setView(inflate);
		builder.setTitle(getString(R.string.logic_popup_title_add_list));
		RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_type);
		EditText editText = (EditText) inflate.findViewById(R.id.ed_input);
		VariableNameValidator variableNameValidator = new VariableNameValidator(this.context, (TextInputLayout) inflate.findViewById(R.id.ti_input), DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), DesignDataManager.getAllNamesForValid(filename));
		editText.setPrivateImeOptions("defaultInputmode=english;");
		builder.setNegativeButton(R.string.btn_cancel, new LogicEditorActivity$9(this));
		builder.setPositiveButton(R.string.btn_accept, null);
		this.mDlg = builder.create();
		this.mDlg.setOnShowListener(new LogicEditorActivity$10(this, variableNameValidator, radioGroup, editText));
		this.mDlg.show();
	}
	
	
	
	
	class LogicEditorActivity$10 implements DialogInterface.OnShowListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final EditText val$edInput;
		// $FF: synthetic field
		final RadioGroup val$rgType;
		// $FF: synthetic field
		final VariableNameValidator val$varNameValidator;
		
		LogicEditorActivity$10(LogicEditorActivity var1, VariableNameValidator var2, RadioGroup var3, EditText var4) {
			this.this$0 = var1;
			this.val$varNameValidator = var2;
			this.val$rgType = var3;
			this.val$edInput = var4;
		}
		
		public void onShow(DialogInterface var1) {
			LogicEditorActivity.access$800(this.this$0).getButton(-1).setOnClickListener(new LogicEditorActivity$10$1(this));
		}
	}
	
	
	
	class LogicEditorActivity$10$1 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity$10 this$1;
		
		LogicEditorActivity$10$1(LogicEditorActivity$10 var1) {
			this.this$1 = var1;
		}
		
		public void onClick(View var1) {
			if(this.this$1.val$varNameValidator.isValid()) {
				byte var2 = 1;
				String type = "ArrayList<Double>";
				if(this.this$1.val$rgType.getCheckedRadioButtonId() == R.id.rb_int) {
					var2 = 1;
					type = "ArrayList<Double>";
				} else if(this.this$1.val$rgType.getCheckedRadioButtonId() == R.id.rb_string) {
					var2 = 2;
					type = "ArrayList<String>";
				}
				
				String var3 = this.this$1.val$edInput.getText().toString();
				DesignActivity.saveVariable(DesignActivity.currentActivityBean.getActivityName(), type, var3);
				DesignDataManager.addList(LogicEditorActivity.filename, var2, var3);
				this.this$1.this$0.onBlockCategorySelect(1, -3384542);
				LogicEditorActivity.access$800(this.this$1.this$0).dismiss();
			}
		}
	}
	
	
	
	
	
	class LogicEditorActivity$9 implements DialogInterface.OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		
		LogicEditorActivity$9(LogicEditorActivity var1) {
			this.this$0 = var1;
		}
		
		public void onClick(DialogInterface var1, int var2) {
			LogicEditorActivity.access$800(this.this$0).dismiss();
		}
	}
	
	
	
	
	
	private void showAddVarPopup() {
		View inflate = LayoutUtil.inflate(this, R.layout.logic_popup_add_variable);
		Builder builder = new Builder(this);
		builder.setView(inflate);
		builder.setTitle(getString(R.string.logic_popup_title_add_variable));
		RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.rg_type);
		EditText editText = (EditText) inflate.findViewById(R.id.ed_input);
		editText.setPrivateImeOptions("defaultInputmode=english;");
		VariableNameValidator variableNameValidator = new VariableNameValidator(this.context, (TextInputLayout) inflate.findViewById(R.id.ti_input), DefineSource.RESERVED_WORD, DefineSource.getUsedWord(DesignActivity.getScId()), DesignDataManager.getAllNamesForValid(filename));
		builder.setNegativeButton(R.string.btn_cancel, null);
		builder.setPositiveButton(R.string.btn_accept, null);
		this.mDlg = builder.create();
		this.mDlg.setOnShowListener(new LogicEditorActivity$7(this, radioGroup, editText, variableNameValidator));
		this.mDlg.show();
	}
	
	
	
	
	class LogicEditorActivity$7 implements DialogInterface.OnShowListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final EditText val$edInput;
		// $FF: synthetic field
		final RadioGroup val$rgType;
		// $FF: synthetic field
		final VariableNameValidator val$varNameValidator;
		
		LogicEditorActivity$7(LogicEditorActivity var1, RadioGroup var2, EditText var3, VariableNameValidator var4) {
			this.this$0 = var1;
			this.val$rgType = var2;
			this.val$edInput = var3;
			this.val$varNameValidator = var4;
		}
		
		public void onShow(DialogInterface var1) {
			LogicEditorActivity.access$800(this.this$0).getButton(-1).setOnClickListener(new LogicEditorActivity$7$1(this));
		}
	}
	
	
	class LogicEditorActivity$7$1 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity$7 this$1;
		
		LogicEditorActivity$7$1(LogicEditorActivity$7 var1) {
			this.this$1 = var1;
		}
		
		public void onClick(View var1) {
			byte var2 = 1;
			String type = "double";
			if(this.this$1.val$rgType.getCheckedRadioButtonId() == R.id.rb_boolean) {
				var2 = 0;
				type = "boolean";
			} else if(this.this$1.val$rgType.getCheckedRadioButtonId() == R.id.rb_int) {
				var2 = 1;
				type = "double";
			} else if(this.this$1.val$rgType.getCheckedRadioButtonId() == R.id.rb_string) {
				var2 = 2;
				type = "String";
			}
			
			String var3 = this.this$1.val$edInput.getText().toString();
			if(this.this$1.val$varNameValidator.isValid()) {
				DesignActivity.saveVariable(DesignActivity.currentActivityBean.getActivityName(), type, var3);
				DesignDataManager.addVariable(LogicEditorActivity.filename, var2, var3);
				this.this$1.this$0.onBlockCategorySelect(0, -1147626);
				LogicEditorActivity.access$800(this.this$1.this$0).dismiss();
			}
		}
	}
	
	
	
	
	
	private void showIconDelete(boolean z) {
		if (!this.bInitIconDeleteAnimation) {
			initIconDeleteAnimation();
		}
		if (this.bShowIconDelete != z) {
			this.bShowIconDelete = z;
			cancelIconDeleteAnimation();
			if (z) {
				this.aniShowIconDelete.start();
			} else {
				this.aniHideIconDelete.start();
			}
		}
	}
	
	private void showRemoveListPopup() {
		View inflate = LayoutUtil.inflate(this, R.layout.property_popup_selector_single);
		Builder builder = new Builder(this);
		builder.setView(inflate);
		builder.setTitle(getString(R.string.logic_popup_title_remove_list));
		ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.rg_content);
		ArrayList arrayList = new ArrayList();
		Iterator it = DesignDataManager.getLists(filename).iterator();
		while (it.hasNext()) {
			viewGroup.addView(createSingleItem((String) ((Pair) it.next()).second));
		}
		builder.setNegativeButton(R.string.btn_cancel, null);
		builder.setPositiveButton(R.string.btn_accept, null);
		this.mDlg = builder.create();
		this.mDlg.setOnShowListener(new LogicEditorActivity$11(this, viewGroup));
		this.mDlg.show();
	}
	
	
	class LogicEditorActivity$11 implements DialogInterface.OnShowListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ViewGroup val$content;
		
		LogicEditorActivity$11(LogicEditorActivity var1, ViewGroup var2) {
			this.this$0 = var1;
			this.val$content = var2;
		}
		
		public void onShow(DialogInterface var1) {
			LogicEditorActivity.access$800(this.this$0).getButton(-1).setOnClickListener(new LogicEditorActivity$11$1(this));
		}
	}
	
	
	
	
	class LogicEditorActivity$11$1 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity$11 this$1;
		
		LogicEditorActivity$11$1(LogicEditorActivity$11 var1) {
			this.this$1 = var1;
		}
		
		public void onClick(View var1) {
			int var2 = this.this$1.val$content.getChildCount();
			int var3 = 0;
			
			while(true) {
				if(var3 < var2) {
					RadioButton var4 = (RadioButton)this.this$1.val$content.getChildAt(var3);
					if(!var4.isChecked()) {
						++var3;
						continue;
					}
					
					if(LogicEditorActivity.access$900(this.this$1.this$0).isExistListBlock(var4.getText().toString()) || DesignDataManager.isExistListBlock(LogicEditorActivity.filename, var4.getText().toString(), LogicEditorActivity.access$1000(this.this$1.this$0) + "_" + LogicEditorActivity.access$1100(this.this$1.this$0))) {
						Toast.makeText(this.this$1.this$0.getApplicationContext(), this.this$1.this$0.getString(R.string.err_currently_used_list), 0).show();
						return;
					}
					
					DesignDataManager.removeList(LogicEditorActivity.filename, var4.getText().toString());
					this.this$1.this$0.onBlockCategorySelect(1, -3384542);
				}
				
				LogicEditorActivity.access$800(this.this$1.this$0).dismiss();
				return;
			}
		}
	}
	
	
	
	
	
	private void showRemoveVarPopup() {
		View inflate = LayoutUtil.inflate(this, R.layout.property_popup_selector_single);
		Builder builder = new Builder(this);
		builder.setView(inflate);
		builder.setTitle(getString(R.string.logic_popup_title_remove_variable));
		ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.rg_content);
		ArrayList arrayList = new ArrayList();
		Iterator it = DesignDataManager.getVariables(filename).iterator();
		while (it.hasNext()) {
			viewGroup.addView(createSingleItem((String) ((Pair) it.next()).second));
		}
		builder.setNegativeButton(R.string.btn_cancel, null);
		builder.setPositiveButton(R.string.btn_accept, null);
		this.mDlg = builder.create();
		this.mDlg.setOnShowListener(new LogicEditorActivity$8(this, viewGroup));
		this.mDlg.show();
	}
	
	
	
	class LogicEditorActivity$8 implements DialogInterface.OnShowListener {
		// $FF: synthetic field
		final LogicEditorActivity this$0;
		// $FF: synthetic field
		final ViewGroup val$content;
		
		LogicEditorActivity$8(LogicEditorActivity var1, ViewGroup var2) {
			this.this$0 = var1;
			this.val$content = var2;
		}
		
		public void onShow(DialogInterface var1) {
			LogicEditorActivity.access$800(this.this$0).getButton(-1).setOnClickListener(new LogicEditorActivity$8$1(this));
		}
	}
	
	
	class LogicEditorActivity$8$1 implements OnClickListener {
		// $FF: synthetic field
		final LogicEditorActivity$8 this$1;
		
		LogicEditorActivity$8$1(LogicEditorActivity$8 var1) {
			this.this$1 = var1;
		}
		
		public void onClick(View var1) {
			int var2 = this.this$1.val$content.getChildCount();
			int var3 = 0;
			
			while(true) {
				if(var3 < var2) {
					RadioButton var4 = (RadioButton)this.this$1.val$content.getChildAt(var3);
					if(!var4.isChecked()) {
						++var3;
						continue;
					}
					
					if(LogicEditorActivity.access$900(this.this$1.this$0).isExistVariableBlock(var4.getText().toString()) || DesignDataManager.isExistVariableBlock(LogicEditorActivity.filename, var4.getText().toString(), LogicEditorActivity.access$1000(this.this$1.this$0) + "_" + LogicEditorActivity.access$1100(this.this$1.this$0))) {
						Toast.makeText(this.this$1.this$0.getApplicationContext(), this.this$1.this$0.getString(R.string.err_currently_used_variable), 0).show();
						return;
					}
					
					DesignDataManager.removeVariable(LogicEditorActivity.filename, var4.getText().toString());
					this.this$1.this$0.onBlockCategorySelect(0, -1147626);
				}
				
				LogicEditorActivity.access$800(this.this$1.this$0).dismiss();
				return;
			}
		}
	}
	
	
	
	
	
	
	
	private void startBlockCopyInterface() {
		this.blockCopyInterface.setCopyMode(!this.blockCopyInterface.getCopyMode());
	}
	
	/*   private void startLogicTutorialActivity() {
	Intent intent = new Intent(this.context, LogicTutorialActivity.class);
	intent.setFlags(536870912);
	intent.putExtra("sc_id", DesignActivity.getScId());
	startActivity(intent);
	}
	
	private void startManageImageActivity() {
	Intent intent = new Intent(getApplicationContext(), ManageImageActivity.class);
	intent.setFlags(536870912);
	intent.putExtra("sc_id", DesignActivity.getScId());
	startActivityForResult(intent, 209);
	}*/
	
	private void updateIconDeletePosition() {
		if (this.isPaletteOpened && 1 == getResources().getConfiguration().orientation) {
			((RelativeLayout.LayoutParams) this.iconDelete.getLayoutParams()).bottomMargin = (int) LayoutUtil.getDip(this, 240.0f);
			this.iconDelete.requestLayout();
			return;
		}
		((RelativeLayout.LayoutParams) this.iconDelete.getLayoutParams()).bottomMargin = 0;
		this.iconDelete.requestLayout();
	}
	
	public boolean checkValidForever() {
		int childCount = this.pane.getChildCount();
		int i = 0;
		while (i < childCount) {
			View childAt = this.pane.getChildAt(i);
			i = (!(childAt instanceof Block) || ((Block) childAt).mOpCode.equals("Forever")) ? i + 1 : i + 1;
		}
		return true;
	}
	
	public boolean checkValidZero() {
		return true;
	}
	
	public Block makeBlockFromBean(BlockBean blockBean) {
		return new Block(this, Integer.valueOf(blockBean.id).intValue(), blockBean.spec, blockBean.type, blockBean.opCode, new Object[]{Integer.valueOf(blockBean.color)});
	}
	
	@Override
	public void onBackPressed() {
		saveLogic();
		//	complex.setLogic(getSourceCode(), activityName);
		DesignActivity.saveBlockLogic(activityName, getSourceCode());
		if (widgetId != null) {
			DesignActivity.saveBlockLogicForWidget(activityName, widgetId, getSourceCode());
		}
		try {
			SourceCodeBean sourceCodeBean = new SourceCodeBean(activityName, eventName, getSourceCode(), DesignActivity.getScId());
			sourceCodeBean.save();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, "Failed to save source code", Toast.LENGTH_SHORT).show();
		}
		if (this.isPaletteOpened) {
			openPalette(!this.isPaletteOpened);
			return;
		}
		if (checkValidForever() && checkValidZero()) {
			try {
				super.onBackPressed();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loadCustomBlocks(int paletteIndex, int defaultColor) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(BLOCK_PATH));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bufferedReader.close();
			
			JSONArray jsonArray = new JSONArray(stringBuilder.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String code = jsonObject.getString("code");
				String name = jsonObject.getString("name");
				String type = jsonObject.getString("type");
				String spec = jsonObject.getString("spec");
				String palette = jsonObject.getString("palette");
				int blockColor;
				
				// Handle the color field
				try {
					if (jsonObject.has("color") && !jsonObject.isNull("color")) {
						String colorStr = jsonObject.getString("color");
						if (colorStr.matches("-?\\d+")) { // Check if it's a numeric value
							blockColor = Integer.parseInt(colorStr); // Use as integer directly
						} else {
							blockColor = Color.parseColor(colorStr); // Parse as hex string
						}
					} else {
						blockColor = defaultColor; // Fallback to the passed default color
					}
				} catch (IllegalArgumentException e) {
					Log.e(TAG, "Invalid color format for block: " + name + ", using default color " + defaultColor, e);
					blockColor = defaultColor; // Fallback on invalid format
				}
				
				if (Integer.parseInt(palette) == paletteIndex) {
					Log.d(TAG, "Adding custom block: " + name + ", color: " + blockColor);
					addBlockToPalette(spec, type, code, blockColor, new Object[]{spec});
				}
			}
		} catch (IOException | JSONException e) {
			Log.e(TAG, "Error loading custom blocks from " + BLOCK_PATH, e);
			Toast.makeText(this, "Error loading custom blocks: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	//@Override
	public void onBlockCategorySelect(int id, int color) {
		Log.d(TAG, "onBlockCategorySelect: id=" + id + ", color=" + color);
		String specABC = "add source directly %s.inputOnly";
		this.paletteBlock.removeAllBlocks();
		if (id >= 6) {
			loadCustomBlocks(id, color);
			return;
		}
		// if (id < 6) {
		switch (id) {
			case 0:
			addButtonToPalette(getString(R.string.logic_btn_add_variable), "variableAdd");
			addButtonToPalette(getString(R.string.logic_btn_remove_variable), "variableRemove");
			addVariables();
			break;
			case 1:
			addButtonToPalette(getString(R.string.logic_btn_add_list), "listAdd");
			addButtonToPalette(getString(R.string.logic_btn_remove_list), "listRemove");
			addLists();
			break;
			case 2:
			addBlockToPalette("", "c", "repeat", color, Integer.valueOf(10));
			addBlockToPalette("", "c", "forever", color, new Object[0]);
			addBlockToPalette("", "f", "break", color, new Object[0]);
			addBlockToPalette("", "c", "if", color, new Object[0]);
			addBlockToPalette("", "e", "ifElse", color, new Object[0]);
			break;
			case 3: 
			addBlockToPalette("", "b", "true", color, new Object[0]);
			addBlockToPalette("", "b", "false", color, new Object[0]);
			addBlockToPalette("", "b", "<", color, new Object[0]);
			addBlockToPalette("", "b", "=", color, new Object[0]);
			addBlockToPalette("", "b", ">", color, new Object[0]);
			addBlockToPalette("", "b", "&&", color, new Object[0]);
			addBlockToPalette("", "b", "||", color, new Object[0]);
			addBlockToPalette("", "b", "not", color, new Object[0]);
			addBlockToPalette("", "d", "+", color, new Object[0]);
			addBlockToPalette("", "d", "-", color, new Object[0]);
			addBlockToPalette("", "d", "*", color, new Object[0]);
			addBlockToPalette("", "d", "/", color, new Object[0]);
			addBlockToPalette("", "d", "%", color, new Object[0]);
			addBlockToPalette("", "d", "random", color, Integer.valueOf(1), Integer.valueOf(10));
			addBlockToPalette("", "d", "stringLength", color, new Object[0]);
			addBlockToPalette("", "s", "stringJoin", color, new Object[0]);
			addBlockToPalette("", "d", "stringIndex", color, new Object[0]);
			addBlockToPalette("", "s", "stringSub", color, new Object[0]);
			addBlockToPalette("", "b", "stringEquals", color, new Object[0]);
			addBlockToPalette("", "d", "toNumber", color, new Object[0]);
			addBlockToPalette("", "s", "toString", color, new Object[0]);
			addBlockToPalette("", "s", "trim", color, new Object[0]);
			addBlockToPalette("", "s", "toUpperCase", color, new Object[0]);
			addBlockToPalette("", "s", "toLowerCase", color, new Object[0]);
			addBlockToPalette("add source directly %s.inputOnly", " ", "%s", color, new Object[]{specABC});
			/*	addBlockToPalette("", " ", "GsonStringToListString", color, new Object[0]);
			addBlockToPalette("", " ", "addSourceDirectly", color, new Object[0]);
			addBlockToPalette("%s", " ", "asd %s", color, new Object[0]);*/
			break;
			case 4:
			addBlockToPalette("", " ", "setEnable", color, new Object[0]);
			addBlockToPalette("", "b", "getEnable", color, new Object[0]);
			addBlockToPalette("", " ", "setVisible", color, new Object[0]);
			addBlockToPalette("", " ", "setText", color, new Object[0]);
			addBlockToPalette("", "s", "getText", color, new Object[0]);
			addBlockToPalette("", " ", "setBgColor", color, new Object[0]);
			addBlockToPalette("", " ", "setTextColor", color, new Object[0]);
			if (DesignActivity.getScId().equals("107") || DesignActivity.getScId().equals("108") || DesignActivity.getScId().equals("110") || DesignActivity.getScId().equals("113") || DesignActivity.getScId().equals("102") || ScDefine.isCustomEditMode(DesignActivity.getScId())) {
				addBlockToPalette("", " ", "setImage", color, new Object[0]);
				addBlockToPalette("", " ", "setRotate", color, new Object[0]);
				addBlockToPalette("", "d", "getRotate", color, new Object[0]);
			}
			if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
				addBlockToPalette("", " ", "setChecked", color, new Object[0]);
				addBlockToPalette("", "b", "getChecked", color, new Object[0]);
				addBlockToPalette("", " ", "listSetData", color, new Object[0]);
				addBlockToPalette("", " ", "listRefresh", color, new Object[0]);
				addBlockToPalette("", " ", "listSetItemChecked", color, new Object[0]);
				addBlockToPalette("", "d", "listGetCheckedPosition", color, new Object[0]);
				addBlockToPalette("", " ", "listGetCheckedPositions", color, new Object[0]);
				addBlockToPalette("", "d", "listGetCheckedCount", color, new Object[0]);
				addBlockToPalette("", " ", "spnSetData", color, new Object[0]);
				addBlockToPalette("", " ", "spnRefresh", color, new Object[0]);
				addBlockToPalette("", " ", "spnSetSelection", color, new Object[0]);
				addBlockToPalette("", "d", "spnGetSelection", color, new Object[0]);
			}
			addBlockToPalette("", " ", "doToast", -13851166, new Object[0]);
			addBlockToPalette("", " ", "intentSetScreen", color, new Object[0]);
			addBlockToPalette("", " ", "intentSetAction", color, new Object[0]);
			addBlockToPalette("", " ", "intentSetData", color, new Object[0]);
			addBlockToPalette("", " ", "startActivity", color, new Object[0]);
			addBlockToPalette("", " ", "intentPutExtra", color, new Object[0]);
			if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
				addBlockToPalette("", "s", "intentGetString", color, new Object[0]);
				addBlockToPalette("", "f", "finishActivity", color, new Object[0]);
			}
			addBlockToPalette("", " ", "calendarGetNow", color, new Object[0]);
			addBlockToPalette("", " ", "calendarAdd", color, new Object[0]);
			addBlockToPalette("", " ", "calendarSet", color, new Object[0]);
			addBlockToPalette("", "s", "calendarFormat", color, new Object[0]);
			addBlockToPalette("", "d", "calendarDiff", color, new Object[0]);
			addBlockToPalette("", " ", "vibratorAction", color, new Object[0]);
			break;
			case 5:
			addButtonToPalette(getString(R.string.logic_btn_make_block), "blockAdd");
			addFunctions();
			break;
		}
		Log.d(TAG, "Loaded predefined category: id=" + id);
		//} else {
		// }
	}
	
	
	
	public void onClick(View view) {
		if (view.getTag() != null) {
			if (view.getTag().equals("variableAdd")) {
				showAddVarPopup();
			} else if (view.getTag().equals("variableRemove")) {
				showRemoveVarPopup();
			} else if (view.getTag().equals("listAdd")) {
				showAddListPopup();
			} else if (view.getTag().equals("listRemove")) {
				showRemoveListPopup();
			} else if (view.getTag().equals("blockAdd")) {
				showAddBlockPopup();
			}
		}
		/*     switch (view.getId()) {
		case R.id.btn_cancel:
		setResult(0);
		finish();
		return;
		case R.id.btn_accept:
		setResult(-1, new Intent());
		finish();
		return;
		default:
		return;
		}*/
	}
	
	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		allocatePalette(configuration.orientation);
		updateIconDeletePosition();
	}
	
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.logic_editer);
		
		this.context = this.getApplicationContext();
		
		this.activityName = getIntent().getStringExtra("activityName");
		this.id = getIntent().getStringExtra("id");
		this.eventName = getIntent().getStringExtra("event");
		filename = getIntent().getStringExtra("filename");
		if (DesignDataManager.isInitialized) {
			this.prefInstall = new SharedPreferenceUtil(this.context, "P1");
			this.complex = new Complex();
			if (getIntent().hasExtra("widgetId")) {
				this.widgetId = getIntent().getStringExtra("widgetId");
			}
			
			
			this.complex.setId(getIntent().getStringExtra("sc_id"));
			DesignDataManager.setSc(getIntent().getStringExtra("sc_id"));
			
			/* if (this.prefInstall.getBoolean("P1I5" + DesignActivity.getScId(), true) && !ScDefine.isCustomEditMode(DesignActivity.getScId())) {
			startLogicTutorialActivity();
			}*/
			this.toolbar = (Toolbar) findViewById(R.id.toolbar);
			setSupportActionBar(this.toolbar);
			findViewById(R.id.layout_main_logo).setVisibility(8);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
			this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onBackPressed();
				}
			});
			
			BLOCK_DRAG_Y = (int)LayoutUtil.getDip(this, BLOCK_DRAG_Y);
			
			//    this.toolbar.setPopupTheme(R.style.ThemeOverlay.ToolbarMenu);
			this.useVibrate = new SharedPreferenceUtil(this.context, "P12").getBoolean("P12I0", true);
			this.minDist = ViewConfiguration.get(this.context).getScaledTouchSlop();
			this.vibrator = (Vibrator) getSystemService("vibrator");
			String stringExtra = getIntent().getStringExtra("event_text");
			if (this.id.equals("onCreate")) {
				getSupportActionBar().setTitle(stringExtra);
			} else {
				getSupportActionBar().setTitle(this.id + " : " + stringExtra);
			}
			this.paletteSelector = (PaletteSelector) findViewById(R.id.palette_selector);
			this.paletteSelector.setOnBlockCategorySelectListener(this);
			this.paletteBlock = (PaletteBlock) findViewById(R.id.palette_block);
			this.dummy = (ViewDummy) findViewById(R.id.dummy);
			this.iconDelete = (ImageView) findViewById(R.id.icon_delete);
			this.editor = (ViewLogicEditor) findViewById(R.id.editor);
			this.pane = this.editor.getBlockPane();
			onBlockCategorySelect(0, -1147626);
			this.blockCopyInterface = (BlockCopyInterface) findViewById(R.id.block_copy_interface);
			this.blockCopyInterface.activity = this;
			this.layoutPalette = (LinearLayout) findViewById(R.id.layout_palette);
			this.areaPalette = (LinearLayout) findViewById(R.id.area_palette);
			this.fab = (FloatingActionButton) findViewById(R.id.fab_toggle_palette);
			this.fab.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					openPalette(!isPaletteOpened);
				}
			});
			
			return;
		}
		
		if (bundle != null) {
			backupCurrentData(bundle);
		}
		finish();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.logic_menu, menu);
		this.menu = menu;
		refreshPasteIcon();
		/* if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
		menu.removeItem(R.id.menu_logic_tutorial);
		}*/
		return true;
	}
	
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		/*   if (menuItem.getItemId() == R.id.menu_block_helper) {
		Intent intent = new Intent(this, BlockHelperActivity.class);
		intent.setFlags(536870912);
		startActivity(intent);
		return true;
		}
		if (menuItem.getItemId() == R.id.menu_logic_tutorial) {
		startLogicTutorialActivity();
		}*/
		if (menuItem.getItemId() == R.id.menu_block_copy) {
			startBlockCopyInterface();
		}
		if (menuItem.getItemId() == R.id.menu_block_paste) {
			pasteCopiedBlocks();
		}
		if (menuItem.getItemId() == R.id.menu_show_source) {
			showSourceCode();
		}
		/* if (menuItem.getItemId() == R.id.menu_mng_image) {
		startManageImageActivity();
		}*/
		return super.onOptionsItemSelected(menuItem);
	}
	
	
	private void showSourceCode() {
		JavaSourceMaker jsm = new JavaSourceMaker(this, "601");
		
		String result = jsm.getSource(0, pane.getBlocks());
		
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Source code");
		b.setMessage(result);
		b.setPositiveButton("OK", null);
		b.create().show();
	}
	
	
	protected void onPostCreate(@Nullable Bundle var1) {
		super.onPostCreate(var1);
		String var2;
		if(this.eventName.equals("initializeLogic")) {
			var2 = this.getString(R.string.root_spec_initialize);
		} else if(this.eventName.equals("moreBlock")) {
			String var20 = DesignDataManager.getFunctionSpec(filename, this.id);
			var2 = this.getString(R.string.root_spec_define) + " " + var20;
		} else if(this.eventName.equals("onClick")) {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + this.getString(R.string.root_spec_onclicked);
		} else if(this.eventName.equals("onCheckedChange")) {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + this.getString(R.string.root_spec_oncheckchanged);
		} else if(this.eventName.equals("onItemSelected")) {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + this.getString(R.string.root_spec_onitemselected);
		} else if(this.eventName.equals("onItemClicked")) {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + this.getString(R.string.root_spec_onitemclicked);
		} else if(this.eventName.equals("onTextChanged")) {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + " " + this.getString(R.string.root_spec_ontextchanged);
		} else {
			var2 = this.getString(R.string.root_spec_when) + " " + this.id + " " + this.eventName;
		}
		
		this.pane.addRoot(var2, this.eventName);
		ArrayList var3 = StringUtil.tokenize(var2);
		int var4 = 0;
		
		for(int var5 = 0; var5 < var3.size(); ++var5) {
			String var6 = (String)var3.get(var5);
			if(var6.charAt(0) == 37) {
				Block var11;
				if(var6.charAt(1) == 98) {
					Context var16 = this.getApplicationContext();
					int var17 = var4 + 1;
					String var18 = var6.substring(3);
					Object[] var19 = new Object[]{Integer.valueOf(-7711273)};
					var11 = new Block(var16, var17, var18, "b", "getArg", var19);
				} else if(var6.charAt(1) == 100) {
					Context var12 = this.getApplicationContext();
					int var13 = var4 + 1;
					String var14 = var6.substring(3);
					Object[] var15 = new Object[]{Integer.valueOf(-7711273)};
					var11 = new Block(var12, var13, var14, "d", "getArg", var15);
				} else {
					if(var6.charAt(1) != 115) {
						continue;
					}
					
					Context var7 = this.getApplicationContext();
					int var8 = var4 + 1;
					String var9 = var6.substring(3);
					Object[] var10 = new Object[]{Integer.valueOf(-7711273)};
					var11 = new Block(var7, var8, var9, "s", "getArg", var10);
				}
				
				var11.setBlockType(1);
				this.pane.addView(var11);
				this.pane.getRoot().replaceArgWithBlock((BlockBase)this.pane.getRoot().args.get(var4), var11);
				var11.setOnTouchListener(this);
				++var4;
			}
		}
		
		this.pane.getRoot().fixLayout();
		this.loadLogic();
		this.allocatePalette(this.getResources().getConfiguration().orientation);
	}
	
	protected void onResume() {
		super.onResume();
		//     this.mTracker.setScreenName(getClass().getSimpleName().toString());
		//      this.mTracker.send(new ScreenViewBuilder().build());
	}
	
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
	}
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
		int action = motionEvent.getAction();
		if (action == 0) {
			this.isDragged = false;
			this.handler.postDelayed(this.longPressed, (long) (ViewConfiguration.getLongPressTimeout() / 2));
			this.posInitX = motionEvent.getX();
			this.posInitY = motionEvent.getY();
			this.currentTouchedView = view;
			return true;
		} else if (action == 2) {
			if (this.isDragged) {
				this.handler.removeCallbacks(this.longPressed);
				this.dummy.moveDummy(view, motionEvent.getX(), motionEvent.getY(), this.posInitX, this.posInitY, (float)BLOCK_DRAG_X, (float)BLOCK_DRAG_Y);
				if (hitTestIconDelete(motionEvent.getRawX(), motionEvent.getRawY())) {
					this.dummy.setAllow(true);
					activeIconDelete(true);
					return true;
				}
				activeIconDelete(false);
				this.dummy.getDummyPosition(this.posDummy);
				if (this.editor.hitTest((float) this.posDummy[0], (float) this.posDummy[1])) {
					this.dummy.setAllow(true);
					this.pane.updateFeedbackFor((Block) view, this.posDummy[0], this.posDummy[1]);
				} else {
					this.dummy.setAllow(false);
					this.pane.hideFeedbackShape();
				}
				return true;
			} else if (Math.abs(this.posInitX - motionEvent.getX()) < ((float) this.minDist) && Math.abs(this.posInitY - motionEvent.getY()) < ((float) this.minDist)) {
				return false;
			} else {
				this.currentTouchedView = null;
				this.handler.removeCallbacks(this.longPressed);
				return false;
			}
		} else if (action == 1) {
			this.currentTouchedView = null;
			this.handler.removeCallbacks(this.longPressed);
			if (this.isDragged) {
				this.paletteBlock.setDragEnabled(true);
				this.editor.setScrollEnabled(true);
				this.dummy.setDummyVisibility(8);
				if (this.dummy.getAllow()) {
					if (this.bActiveIconDelete) {
						activeIconDelete(false);
						this.pane.removeBlock((Block) view);
					} else if (view instanceof Block) {
						this.dummy.getDummyPosition(this.posDummy);
						if (((Block) view).getBlockType() == 1) {
							this.pane.blockDropped((Block) view, this.posDummy[0], this.posDummy[1], false).setOnTouchListener(this);
						} else {
							this.pane.setVisibleBlock((Block) view, 0);
							this.pane.blockDropped((Block) view, this.posDummy[0], this.posDummy[1], true);
						}
						this.pane.draggingDone();
					}
				} else if (((Block) view).getBlockType() == 0) {
					this.pane.setVisibleBlock((Block) view, 0);
					if (this.originalParent != null) {
						if (this.originalInsertOption == 0) {
							this.originalParent.nextBlock = ((Integer) view.getTag()).intValue();
						}
						if (this.originalInsertOption == 2) {
							this.originalParent.subStack1 = ((Integer) view.getTag()).intValue();
						}
						if (this.originalInsertOption == 3) {
							this.originalParent.subStack2 = ((Integer) view.getTag()).intValue();
						}
						if (this.originalInsertOption == 5) {
							this.originalParent.replaceArgWithBlock((BlockBase) this.originalParent.args.get(this.originalArgIndex), (Block) view);
						}
						((Block) view).parentBlock = this.originalParent;
						this.originalParent.topBlock().fixLayout();
					} else {
						((Block) view).topBlock().fixLayout();
					}
				}
				this.dummy.setAllow(false);
				showIconDelete(false);
				this.isDragged = false;
				return true;
			}
			if ((view instanceof Block) && ((Block) view).getBlockType() == 0) {
				((Block) view).actionClick(motionEvent.getX(), motionEvent.getY());
			}
			return false;
		} else if (action == 3) {
			this.handler.removeCallbacks(this.longPressed);
			this.isDragged = false;
			return false;
		} else if (action != 8) {
			return true;
		} else {
			this.handler.removeCallbacks(this.longPressed);
			this.isDragged = false;
			return false;
		}
	}
	
	public void refreshPasteIcon() {
		if (DesignDataManager.isExistClipboard(filename)) {
			this.menu.getItem(1).setIcon(R.drawable.ic_content_paste_white_24dp);
			this.menu.getItem(1).setEnabled(true);
			return;
		}
		this.menu.getItem(1).setIcon(R.drawable.ic_content_paste_grey600_24dp);
		this.menu.getItem(1).setEnabled(false);
	}
	
	
	private void loadBlockDefinitions() {
		String path = "/storage/emulated/0/.sketchware/resources/block/My Block/block.json";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bufferedReader.close();
			
			JSONArray jsonArray = new JSONArray(stringBuilder.toString());
			blockDefinitions = new ArrayList<>();
			validPaletteIds = new HashSet<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				BlockDefinition blockDef = new BlockDefinition();
				blockDef.code = jsonObject.getString("code");
				blockDef.name = jsonObject.getString("name");
				blockDef.palette = jsonObject.getString("palette");
				blockDef.type = jsonObject.getString("type");
				blockDef.spec = jsonObject.getString("spec");
				if (jsonObject.has("color")) {
					blockDef.color = jsonObject.getString("color");
				}
				blockDefinitions.add(blockDef);
				validPaletteIds.add(blockDef.palette);
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			Toast.makeText(this, "Failed to load block.json", Toast.LENGTH_SHORT).show();
		}
	}
	
	private static class BlockDefinition {
		String code;
		String name;
		String palette;
		String type;
		String spec;
		String color; // Optional
	}
	
	public String getSourceCode() { 
		JavaSourceMaker jsm = new JavaSourceMaker(this, "601");
		
		return jsm.getSource(0, pane.getBlocks());
	}
}
