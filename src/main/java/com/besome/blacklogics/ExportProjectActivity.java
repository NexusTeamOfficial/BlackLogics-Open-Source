package com.besome.blacklogics;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.*;
import androidx.viewpager.*;
import androidx.viewpager2.*;
import com.besome.sketch.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import mod.hey.studios.util.Helper;
import com.besome.blacklogics.development.Complex;
import java.io.File;

public class ExportProjectActivity extends AppCompatActivity {
	
	public Complex complex;
	private String exportDirPath = "";
	private String sc_id = "";
	private String basePath = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout btn_layout;
	private LinearLayout progress;
	private LinearLayout exportPath;
	private ImageView imageview1;
	private TextView textview1;
	private MaterialButton export_btn;
	private LinearLayout progress_layout;
	private TextView progress_count;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private MaterialButton materialbutton1;
	private ImageView imageview2;
	private TextView textview2;
	private LinearLayout linear6;
	private TextView path;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.export_project);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		complex= new Complex();
		complex.setC(this);
		complex.setId(getIntent().getStringExtra("sc_id"));
		sc_id = getIntent().getStringExtra("sc_id");
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		btn_layout = findViewById(R.id.btn_layout);
		progress = findViewById(R.id.progress);
		exportPath = findViewById(R.id.exportPath);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		export_btn = findViewById(R.id.export_btn);
		progress_layout = findViewById(R.id.progress_layout);
		progress_count = findViewById(R.id.progress_count);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		materialbutton1 = findViewById(R.id.materialbutton1);
		imageview2 = findViewById(R.id.imageview2);
		textview2 = findViewById(R.id.textview2);
		linear6 = findViewById(R.id.linear6);
		path = findViewById(R.id.path);
		
		export_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startExport();
			}
		});
	}
	
	private void initializeLogic() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Export Project");
		loadFile.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
		loadFile.setImageResource(R.drawable.ic_more_vert_black);
		loadFile.setVisibility(View.GONE);
		Helper.applyRippleToToolbarView(loadFile);
		exportDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/blacklogics/export";
		File exportDir = new File(exportDirPath);
		if (!exportDir.exists()) {
				exportDir.mkdirs();
		}
		btn_layout.setVisibility(View.VISIBLE);
		progress.setVisibility(View.GONE);
		exportPath.setVisibility(View.GONE);
		
		basePath = FileUtil.getExternalStorageDir();
		_AVLd("JavaLibrary");
	}
	
	public void _a() {
	}
	private void startExport() {
			// Show progress, hide button layout
			progress.setVisibility(View.VISIBLE);
			btn_layout.setVisibility(View.GONE);
			export_btn.setEnabled(false);
			
			String resFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id + "/files/resource/";
			String javaFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.blacklogics/data/" + sc_id + "/files/java/";
			
			String dataDir = basePath + "/.blacklogics/data/" + sc_id + "/";
			
			File permissionFile = new File(dataDir + "permission");
			if (permissionFile.exists() && permissionFile.length() > 0) {
					try {
							complex.addPermissionsFromJsonFile(permissionFile.getAbsolutePath());
					} catch (Exception e) {
							//System.err.println("Error reading permissions file: " + e.getMessage());
					}
			}
			
			// Handle java/activities file
			File javaFile = new File(dataDir + "java");
			if (javaFile.exists() && javaFile.length() > 0) {
					try {
							complex.addActivitiesFromJsonFile(javaFile.getAbsolutePath());
					} catch (Exception e) {
							//System.err.println("Error reading activities file: " + e.getMessage());
					}
			}
			
			// Handle services file
			File serviceFile = new File(dataDir + "service");
			if (serviceFile.exists() && serviceFile.length() > 0) {
					try {
							complex.addServicesFromJsonFile(serviceFile.getAbsolutePath());
					} catch (Exception e) {
							//System.err.println("Error reading services file: " + e.getMessage());
					}
			}
			
			// Set project ID from intent or configuration
			
			try {
					complex.exportProject(
					exportDirPath,
					resFolderPath,
					javaFolderPath,
					(progress, message) -> runOnUiThread(() -> {
							// Combine progress percentage and message
							progress_count.setText(String.format("%d%% - %s", progress, message));
					}),
					errorMessage -> runOnUiThread(() -> {
							showMessage(errorMessage);
							resetUI();
					}),
					zipFilePath -> runOnUiThread(() -> {
							showMessage("Project exported successfully to: " + zipFilePath);
							// Show exportPath on success
							path.setText(zipFilePath);
							exportPath.setVisibility(View.VISIBLE);
							progress.setVisibility(View.GONE);
							btn_layout.setVisibility(View.GONE);
					})
					);
			} catch (Exception e) {
					showMessage("Export failed: " + e.getMessage());
					resetUI();
			}
	}
	
	private void resetUI() {
			// On failure, show button layout, hide progress and exportPath
			progress.setVisibility(View.GONE);
			btn_layout.setVisibility(View.VISIBLE);
			exportPath.setVisibility(View.GONE);
			export_btn.setEnabled(true);
			progress_count.setText("0%");
	}
	
	{
	}
	
	
	public void _library() {
	}
	
	
	public static class AVLoadingIndicatorView extends View {
		    private static final String TAG="AVLoadingIndicatorView";
		    private static final BallPulseIndicator DEFAULT_INDICATOR=new BallPulseIndicator();
		    private static final int MIN_SHOW_TIME = 500; // ms
		    private static final int MIN_DELAY = 500; // ms
		    private long mStartTime = -1;
		    private boolean mPostedHide = false;
		    private boolean mPostedShow = false;
		    private boolean mDismissed = false;
		    private final Runnable mDelayedHide = new Runnable() {
			        @Override
			        public void run() {
				            mPostedHide = false;
				            mStartTime = -1;
				            setVisibility(View.GONE);
				        }
			    };
		    private final Runnable mDelayedShow = new Runnable() {
			        @Override
			        public void run() {
				            mPostedShow = false;
				            if (!mDismissed) {
					                mStartTime = System.currentTimeMillis();
					                setVisibility(View.VISIBLE);
					            }
				        }
			    };
		    int mMinWidth;
		    int mMaxWidth;
		    int mMinHeight;
		    int mMaxHeight;
		    private Indicator mIndicator;
		    private int mIndicatorColor;
		    private boolean mShouldStartAnimationDrawable;
		    public AVLoadingIndicatorView(Context context) {
			        super(context);
			        init(context, null,0,0);
			    }
		    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
			        super(context, attrs);
			        init(context, attrs,0,0);
			    }
		    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
			        super(context, attrs, defStyleAttr);
			        init(context, attrs,defStyleAttr,0);
			    }
		    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
			        super(context, attrs, defStyleAttr, defStyleRes);
			        init(context,attrs,defStyleAttr,0);
			    }
		    private void init(Context context,AttributeSet attrs,int defStyleAttr, int defStyleRes) {
			        mMinWidth = 24;
			        mMaxWidth = 48;
			        mMinHeight = 24;
			        mMaxHeight = 48;
			        mMinWidth = mMinWidth;
			        mMaxWidth = mMaxWidth;
			        mMinHeight = mMinHeight;
			        mMaxHeight = mMaxHeight; 
			        String indicatorName="";
			        mIndicatorColor=Color.WHITE;
			        setIndicator(indicatorName);
			        if (mIndicator==null){
				            setIndicator(DEFAULT_INDICATOR);
				        }     
			    }
		    public Indicator getIndicator() {
			        return mIndicator;
			    }
		    public void setIndicator(Indicator d) {
			        if (mIndicator != d) {
				            if (mIndicator != null) {
					                mIndicator.setCallback(null);
					                unscheduleDrawable(mIndicator);
					            }
				            mIndicator = d;
				            setIndicatorColor(mIndicatorColor);
				            if (d != null) {
					                d.setCallback(this);
					            }
				            postInvalidate();
				        }
			    }
		    public void setIndicatorColor(int color){
			        this.mIndicatorColor=color;
			        mIndicator.setColor(color);
			    }
		    public void setIndicatorName(String indi) {
			    	if (indi.contains("TriangleSkew")) {
				    		TriangleSkewSpinIndicator indic = new TriangleSkewSpinIndicator();
				    		setIndicator(indic);
				    } else  if (indi.contains("Square")) {
				    		SquareSpinIndicator indic = new SquareSpinIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("SemiCircle")) {
				    		SemiCircleSpinIndicator indic = new SemiCircleSpinIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallPulse")) {
				    		BallPulseSyncIndicator indic = new BallPulseSyncIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallPulseRise")) {
				    		BallPulseRiseIndicator indic = new BallPulseRiseIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallGridPulse")) {
				    		BallGridPulseIndicator indic = new BallGridPulseIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallGridBeat")) {
				    		BallGridBeatIndicator indic = new BallGridBeatIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallClipRotatePulse")) {
				    		BallClipRotatePulseIndicator indic = new BallClipRotatePulseIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallClipRotateMultiple")) {
				    		BallClipRotateMultipleIndicator indic = new BallClipRotateMultipleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallClipRotate")) {
				    		BallClipRotateIndicator indic = new BallClipRotateIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallBeat")) {
				    		BallBeatIndicator indic = new BallBeatIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallRotate")) {
				    		BallRotateIndicator indic = new BallRotateIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallScale")) {
				    		BallScaleIndicator indic = new BallScaleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallScaleMultiple")) {
				    		BallScaleMultipleIndicator indic = new BallScaleMultipleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallScaleRipple")) {
				    		BallScaleRippleIndicator indic = new BallScaleRippleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallScaleRippleMultiple")) {
				    		BallScaleRippleMultipleIndicator indic = new BallScaleRippleMultipleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallSpinFadeLoader")) {
				    		BallSpinFadeLoaderIndicator indic = new BallSpinFadeLoaderIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallTrianglePath")) {
				    		BallTrianglePathIndicator indic = new BallTrianglePathIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallZigZagDeflect")) {
				    		BallZigZagDeflectIndicator indic = new BallZigZagDeflectIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("BallZigZag")) {
				    		BallZigZagIndicator indic = new BallZigZagIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("CubeTransition")) {
				    		CubeTransitionIndicator indic = new CubeTransitionIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("LineScale")) {
				    		LineScaleIndicator indic = new LineScaleIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("LineScaleParty")) {
				    		LineScalePartyIndicator indic = new LineScalePartyIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("LineScalePulseOut")) {
				    		LineScalePulseOutIndicator indic = new LineScalePulseOutIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("LineScalePulseOutRapid")) {
				    		LineScalePulseOutRapidIndicator indic = new LineScalePulseOutRapidIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("LineSpinFadeLoader")) {
				    		LineSpinFadeLoaderIndicator indic = new LineSpinFadeLoaderIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("Pacman")) {
				    		PacmanIndicator indic = new PacmanIndicator();
				    		setIndicator(indic);
				    } else if (indi.contains("JavaLibrary")) {
				    		JavaLibrary indic = new JavaLibrary();
				    		setIndicator(indic);
				    }
			    }
		    public void setIndicator(String indicatorName){
			        if (TextUtils.isEmpty(indicatorName)){
				            return;
				        }
			        StringBuilder drawableClassName=new StringBuilder();
			        if (!indicatorName.contains(".")){
				            String defaultPackageName=getClass().getPackage().getName();
				            drawableClassName.append(defaultPackageName)
				                    .append(".indicators")
				                    .append(".");
				        }
			        drawableClassName.append(indicatorName);
			        try {
				            Class<?> drawableClass = Class.forName(drawableClassName.toString());
				            Indicator indicator = (Indicator) drawableClass.newInstance();
				            setIndicator(indicator);
				        } catch (ClassNotFoundException e) {
				            Log.e(TAG,"Didn't find your class , check the name again !");
				        } catch (InstantiationException e) {
				            e.printStackTrace();
				        } catch (IllegalAccessException e) {
				            e.printStackTrace();
				        }
			    }
		    public void smoothToShow(){
			        startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_in));
			        setVisibility(VISIBLE);
			    }
		    public void smoothToHide(){
			        startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out));
			        setVisibility(GONE);
			    }
		    public void hide() {
			        mDismissed = true;
			        removeCallbacks(mDelayedShow);
			        long diff = System.currentTimeMillis() - mStartTime;
			        if (diff >= MIN_SHOW_TIME || mStartTime == -1) {
				            setVisibility(View.GONE);
				        } else {
				            if (!mPostedHide) {
					                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff);
					                mPostedHide = true;
					            }
				        }
			    }
		    public void show() {
			        mStartTime = -1;
			        mDismissed = false;
			        removeCallbacks(mDelayedHide);
			        if (!mPostedShow) {
				            postDelayed(mDelayedShow, MIN_DELAY);
				            mPostedShow = true;
				        }
			    }
		    @Override
		    protected boolean verifyDrawable(android.graphics.drawable.Drawable who) {
			        return who == mIndicator
			                || super.verifyDrawable(who);
			    }
		    void startAnimation() {
			        if (getVisibility() != VISIBLE) {
				            return;
				        }
			        if (mIndicator instanceof android.graphics.drawable.Animatable) {
				            mShouldStartAnimationDrawable = true;
				        }
			        postInvalidate();
			    }
		    void stopAnimation() {
			        if (mIndicator instanceof android.graphics.drawable.Animatable) {
				            mIndicator.stop();
				            mShouldStartAnimationDrawable = false;
				        }
			        postInvalidate();
			    }
		    @Override
		    public void setVisibility(int v) {
			        if (getVisibility() != v) {
				            super.setVisibility(v);
				            if (v == GONE || v == INVISIBLE) {
					                stopAnimation();
					            } else {
					                startAnimation();
					            }
				        }
			    }
		    @Override
		    protected void onVisibilityChanged(View changedView, int visibility) {
			        super.onVisibilityChanged(changedView, visibility);
			        if (visibility == GONE || visibility == INVISIBLE) {
				            stopAnimation();
				        } else {
				            startAnimation();
				        }
			    }
		    @Override
		    public void invalidateDrawable(android.graphics.drawable.Drawable dr) {
			        if (verifyDrawable(dr)) {
				            final Rect dirty = dr.getBounds();
				            final int scrollX = getScrollX() + getPaddingLeft();
				            final int scrollY = getScrollY() + getPaddingTop();
				            invalidate(dirty.left + scrollX, dirty.top + scrollY,
				                    dirty.right + scrollX, dirty.bottom + scrollY);
				        } else {
				            super.invalidateDrawable(dr);
				        }
			    }
		    @Override
		    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			        updateDrawableBounds(w, h);
			    }
		    private void updateDrawableBounds(int w, int h) {
			        w -= getPaddingRight() + getPaddingLeft();
			        h -= getPaddingTop() + getPaddingBottom();
			        int right = w;
			        int bottom = h;
			        int top = 0;
			        int left = 0;
			        if (mIndicator != null) {
				            final int intrinsicWidth = mIndicator.getIntrinsicWidth();
				            final int intrinsicHeight = mIndicator.getIntrinsicHeight();
				            final float intrinsicAspect = (float) intrinsicWidth / intrinsicHeight;
				            final float boundAspect = (float) w / h;
				            if (intrinsicAspect != boundAspect) {
					                if (boundAspect > intrinsicAspect) {
						                    final int width = (int) (h * intrinsicAspect);
						                    left = (w - width) / 2;
						                    right = left + width;
						                } else {
						                    final int height = (int) (w * (1 / intrinsicAspect));
						                    top = (h - height) / 2;
						                    bottom = top + height;
						                }
					            }
				            mIndicator.setBounds(left, top, right, bottom);
				        }
			    }
		    @Override
		    protected synchronized void onDraw(Canvas canvas) {
			        super.onDraw(canvas);
			        drawTrack(canvas);
			    }
		    void drawTrack(Canvas canvas) {
			        final android.graphics.drawable.Drawable d = mIndicator;
			        if (d != null) {
				            final int saveCount = canvas.save();
				            canvas.translate(getPaddingLeft(), getPaddingTop());
				            d.draw(canvas);
				            canvas.restoreToCount(saveCount);
				            if (mShouldStartAnimationDrawable && d instanceof android.graphics.drawable.Animatable) {
					                ((android.graphics.drawable.Animatable) d).start();
					                mShouldStartAnimationDrawable = false;
					            }
				        }
			    }
		    @Override
		    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			        int dw = 0;
			        int dh = 0;
			        final android.graphics.drawable.Drawable d = mIndicator;
			        if (d != null) {
				            dw = Math.max(mMinWidth, Math.min(mMaxWidth, d.getIntrinsicWidth()));
				            dh = Math.max(mMinHeight, Math.min(mMaxHeight, d.getIntrinsicHeight()));
				        }
			        updateDrawableState();
			        dw += getPaddingLeft() + getPaddingRight();
			        dh += getPaddingTop() + getPaddingBottom();
			        final int measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
			        final int measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
			        setMeasuredDimension(measuredWidth, measuredHeight);
			    }
		    @Override
		    protected void drawableStateChanged() {
			        super.drawableStateChanged();
			        updateDrawableState();
			    }
		    private void updateDrawableState() {
			        final int[] state = getDrawableState();
			        if (mIndicator != null && mIndicator.isStateful()) {
				            mIndicator.setState(state);
				        }
			    }
		    @Override
		    public void drawableHotspotChanged(float x, float y) {
			        super.drawableHotspotChanged(x, y);
			        if (mIndicator != null) {
				            mIndicator.setHotspot(x, y);
				        }
			    }
		    @Override
		    protected void onAttachedToWindow() {
			        super.onAttachedToWindow();
			        startAnimation();
			        removeCallbacks();
			    }
		    @Override
		    protected void onDetachedFromWindow() {
			        stopAnimation();
			        super.onDetachedFromWindow();
			        removeCallbacks();
			    }
		    private void removeCallbacks() {
			        removeCallbacks(mDelayedHide);
			        removeCallbacks(mDelayedShow);
			    }
	}
	
	
	public static class BallPulseIndicator extends Indicator {
		    public static final float SCALE=1.0f;
		    private float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(Math.min(getWidth(),getHeight())-circleSpacing*2)/6;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        float y=getHeight() / 2;
			        for (int i = 0; i < 3; i++) {
				            canvas.save();
				            float translateX=x+(radius*2)*i+circleSpacing*i;
				            canvas.translate(translateX, y);
				            canvas.scale(scaleFloats[i], scaleFloats[i]);
				            canvas.drawCircle(0, 0, radius, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] delays=new int[]{120,240,360};
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
				            scaleAnim.setDuration(750);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static abstract class Indicator extends android.graphics.drawable.Drawable implements android.graphics.drawable.Animatable {
		    private HashMap<ValueAnimator,ValueAnimator.AnimatorUpdateListener> mUpdateListeners=new HashMap<>();
		    private ArrayList<ValueAnimator> mAnimators;
		    private int alpha = 255;
		    private static final Rect ZERO_BOUNDS_RECT = new Rect();
		    protected Rect drawBounds = ZERO_BOUNDS_RECT;
		    private boolean mHasAnimators;
		    private Paint mPaint=new Paint();
		    public Indicator(){
			        mPaint.setColor(Color.WHITE);
			        mPaint.setStyle(Paint.Style.FILL);
			        mPaint.setAntiAlias(true);
			    }
		    public int getColor() {
			        return mPaint.getColor();
			    }
		    public void setColor(int color) {
			        mPaint.setColor(color);
			    }
		    @Override
		    public void setAlpha(int alpha) {
			        this.alpha = alpha;
			    }
		    @Override
		    public int getAlpha() {
			        return alpha;
			    }
		    @Override
		    public int getOpacity() {
			        return PixelFormat.OPAQUE;
			    }
		    @Override
		    public void setColorFilter(ColorFilter colorFilter) {}
		    @Override
		    public void draw(Canvas canvas) {
			        draw(canvas,mPaint);
			    }
		    public abstract void draw(Canvas canvas, Paint paint);
		    public abstract ArrayList<ValueAnimator> onCreateAnimators();
		    @Override
		    public void start() {
			        ensureAnimators();
			        if (mAnimators == null) {
				            return;
				        }
			        if (isStarted()) {
				            return;
				        }
			        startAnimators();
			        invalidateSelf();
			    }
		    private void startAnimators() {
			        for (int i = 0; i < mAnimators.size(); i++) {
				            ValueAnimator animator = mAnimators.get(i);
				            ValueAnimator.AnimatorUpdateListener updateListener=mUpdateListeners.get(animator);
				            if (updateListener!=null){
					                animator.addUpdateListener(updateListener);
					            }
				            animator.start();
				        }
			    }
		    private void stopAnimators() {
			        if (mAnimators!=null){
				            for (ValueAnimator animator : mAnimators) {
					                if (animator != null && animator.isStarted()) {
						                    animator.removeAllUpdateListeners();
						                    animator.end();
						                }
					            }
				        }
			    }
		    private void ensureAnimators() {
			        if (!mHasAnimators) {
				            mAnimators = onCreateAnimators();
				            mHasAnimators = true;
				        }
			    }
		    @Override
		    public void stop() {
			        stopAnimators();
			    }
		    private boolean isStarted() {
			        for (ValueAnimator animator : mAnimators) {
				            return animator.isStarted();
				        }
			        return false;
			    }
		    @Override
		    public boolean isRunning() {
			        for (ValueAnimator animator : mAnimators) {
				            return animator.isRunning();
				        }
			        return false;
			    }
		    public void addUpdateListener(ValueAnimator animator, ValueAnimator.AnimatorUpdateListener updateListener){
			        mUpdateListeners.put(animator,updateListener);
			    }
		    @Override
		    protected void onBoundsChange(Rect bounds) {
			        super.onBoundsChange(bounds);
			        setDrawBounds(bounds);
			    }
		    public void setDrawBounds(Rect drawBounds) {
			        setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom);
			    }
		    public void setDrawBounds(int left, int top, int right, int bottom) {
			        this.drawBounds = new Rect(left, top, right, bottom);
			    }
		    public void postInvalidate(){
			        invalidateSelf();
			    }
		    public Rect getDrawBounds() {
			        return drawBounds;
			    }
		    public int getWidth(){
			        return drawBounds.width();
			    }
		    public int getHeight(){
			        return drawBounds.height();
			    }
		    public int centerX(){
			        return drawBounds.centerX();
			    }
		    public int centerY(){
			        return drawBounds.centerY();
			    }
		    public float exactCenterX(){
			        return drawBounds.exactCenterX();
			    }
		    public float exactCenterY(){
			        return drawBounds.exactCenterY();
			    }
	}
	
	
	public static class LineScalePulseOutRapidIndicator extends LineScaleIndicator {
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] delays=new long[]{400,200,0,200,400};
			        for (int i = 0; i < 5; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.4f,1);
				            scaleAnim.setDuration(1000);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleYFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static class PacmanIndicator extends Indicator {
		    private float translateX;
		    private int alpha;
		    private float degrees1,degrees2;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        drawPacman(canvas,paint);
			        drawCircle(canvas,paint);
			    }
		    private void drawPacman(Canvas canvas,Paint paint){
			        float x=getWidth()/2;
			        float y=getHeight()/2;
			        canvas.save();
			        canvas.translate(x, y);
			        canvas.rotate(degrees1);
			        paint.setAlpha(255);
			        RectF rectF1=new RectF(-x/1.7f,-y/1.7f,x/1.7f,y/1.7f);
			        canvas.drawArc(rectF1, 0, 270, true, paint);
			        canvas.restore();
			        canvas.save();
			        canvas.translate(x, y);
			        canvas.rotate(degrees2);
			        paint.setAlpha(255);
			        RectF rectF2=new RectF(-x/1.7f,-y/1.7f,x/1.7f,y/1.7f);
			        canvas.drawArc(rectF2,90,270,true,paint);
			        canvas.restore();
			    }
		    private void drawCircle(Canvas canvas, Paint paint) {
			        float radius=getWidth()/11;
			        paint.setAlpha(alpha);
			        canvas.drawCircle(translateX, getHeight() / 2, radius, paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float startT=getWidth()/11;
			        ValueAnimator translationAnim=ValueAnimator.ofFloat(getWidth()-startT,getWidth()/2);
			        translationAnim.setDuration(650);
			        translationAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        translationAnim.setRepeatCount(-1);
			        addUpdateListener(translationAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                translateX = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator alphaAnim=ValueAnimator.ofInt(255,122);
			        alphaAnim.setDuration(650);
			        alphaAnim.setRepeatCount(-1);
			        addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                alpha = (int) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim1=ValueAnimator.ofFloat(0, 45, 0);
			        rotateAnim1.setDuration(650);
			        rotateAnim1.setRepeatCount(-1);
			        addUpdateListener(rotateAnim1,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees1 = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim2=ValueAnimator.ofFloat(0,-45,0);
			        rotateAnim2.setDuration(650);
			        rotateAnim2.setRepeatCount(-1);
			        addUpdateListener(rotateAnim2,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees2 = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(translationAnim);
			        animators.add(alphaAnim);
			        animators.add(rotateAnim1);
			        animators.add(rotateAnim2);
			        return animators;
			    }
	}
	
	
	public static class JavaLibrary extends Indicator{
		    public static final float SCALE=1.0f;
		    private float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(Math.min(getWidth(),getHeight())-circleSpacing*2)/12;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        float y=getHeight() / 2;
			        for (int i = 0; i < 4; i++) {
				            canvas.save();
				            float translateX=x+(radius*2)*i+circleSpacing*i;
				            canvas.translate(translateX, y);
				            canvas.scale(scaleFloats[i], scaleFloats[i]);
				            canvas.drawCircle(0, 0, radius, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] delays=new int[]{120,240,360,480};
			        for (int i = 0; i < 4; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
				            scaleAnim.setDuration(750);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static class LineSpinFadeLoaderIndicator extends BallSpinFadeLoaderIndicator {
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float radius=getWidth()/10;
			        for (int i = 0; i < 8; i++) {
				            canvas.save();
				            Point point=circleAt(getWidth(),getHeight(),getWidth()/2.5f-radius,i*(Math.PI/4));
				            canvas.translate(point.x, point.y);
				            canvas.scale(scaleFloats[i], scaleFloats[i]);
				            canvas.rotate(i*45);
				            paint.setAlpha(alphas[i]);
				            RectF rectF=new RectF(-radius,-radius/1.5f,1.5f*radius,radius/1.5f);
				            canvas.drawRoundRect(rectF,5,5,paint);
				            canvas.restore();
				        }
			    }
	}
	
	
	public static class BallZigZagIndicator extends Indicator {
		    float[] translateX=new float[2],translateY=new float[2];
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        for (int i = 0; i < 2; i++) {
				            canvas.save();
				            canvas.translate(translateX[i], translateY[i]);
				            canvas.drawCircle(0, 0, getWidth() / 10, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float startX=getWidth()/6;
			        float startY=getWidth()/6;
			        for (int i = 0; i < 2; i++) {
				            final int index=i;
				            ValueAnimator translateXAnim=ValueAnimator.ofFloat(startX,getWidth()-startX,getWidth()/2,startX);
				            if (i==1){
					                translateXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,getWidth()/2,getWidth()-startX);
					            }
				            ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,startY,getHeight()/2,startY);
				            if (i==1){
					                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,getHeight()/2,getHeight()-startY);
					            }
				            translateXAnim.setDuration(1000);
				            translateXAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translateXAnim.setRepeatCount(-1);
				            addUpdateListener(translateXAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateX[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            translateYAnim.setDuration(1000);
				            translateYAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translateYAnim.setRepeatCount(-1);
				            addUpdateListener(translateYAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateY[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(translateXAnim);
				            animators.add(translateYAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static class LineScalePulseOutIndicator extends LineScaleIndicator {
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] delays=new long[]{500,250,0,250,500};
			        for (int i = 0; i < 5; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
				            scaleAnim.setDuration(900);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleYFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	public static class CubeTransitionIndicator extends Indicator {
		    float[] translateX=new float[2],translateY=new float[2];
		    float degrees,scaleFloat=1.0f;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float rWidth=getWidth()/5;
			        float rHeight=getHeight()/5;
			        for (int i = 0; i < 2; i++) {
				            canvas.save();
				            canvas.translate(translateX[i], translateY[i]);
				            canvas.rotate(degrees);
				            canvas.scale(scaleFloat,scaleFloat);
				            RectF rectF=new RectF(-rWidth/2,-rHeight/2,rWidth/2,rHeight/2);
				            canvas.drawRect(rectF,paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float startX=getWidth()/5;
			        float startY=getHeight()/5;
			        for (int i = 0; i < 2; i++) {
				            final int index=i;
				            translateX[index]=startX;
				            ValueAnimator translationXAnim=ValueAnimator.ofFloat(startX,getWidth()-startX,getWidth()-startX, startX,startX);
				            if (i==1){
					                translationXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,startX, getWidth()-startX,getWidth()-startX);
					            }
				            translationXAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translationXAnim.setDuration(1600);
				            translationXAnim.setRepeatCount(-1);
				            translationXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateX[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            translateY[index]=startY;
				            ValueAnimator translationYAnim=ValueAnimator.ofFloat(startY,startY,getHeight()-startY,getHeight()- startY,startY);
				            if (i==1){
					                translationYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,startY,startY,getHeight()-startY);
					            }
				            translationYAnim.setDuration(1600);
				            translationYAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translationYAnim.setRepeatCount(-1);
				            addUpdateListener(translationYAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateY[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(translationXAnim);
				            animators.add(translationYAnim);
				        }
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.5f,1,0.5f,1);
			        scaleAnim.setDuration(1600);
			        scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0,180,360,1.5f*360,2*360);
			        rotateAnim.setDuration(1600);
			        rotateAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        rotateAnim.setRepeatCount(-1);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(scaleAnim);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	
	//TriangleSkewSpinIndicator
	
	public static class TriangleSkewSpinIndicator extends Indicator {
		    private float rotateX;
		    private float rotateY;
		    private Camera mCamera;
		    private Matrix mMatrix;
		    public TriangleSkewSpinIndicator(){
			        mCamera=new Camera();
			        mMatrix=new Matrix();
			    }
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        mMatrix.reset();
			        mCamera.save();
			        mCamera.rotateX(rotateX);
			        mCamera.rotateY(rotateY);
			        mCamera.getMatrix(mMatrix);
			        mCamera.restore();
			        mMatrix.preTranslate(-centerX(), -centerY());
			        mMatrix.postTranslate(centerX(), centerY());
			        canvas.concat(mMatrix);
			        Path path=new Path();
			        path.moveTo(getWidth()/5,getHeight()*4/5);
			        path.lineTo(getWidth()*4/5, getHeight()*4/5);
			        path.lineTo(getWidth()/2,getHeight()/5);
			        path.close();
			        canvas.drawPath(path, paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator animator=ValueAnimator.ofFloat(0,180,180,0,0);
			        addUpdateListener(animator,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                rotateX= (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animator.setInterpolator(new android.view.animation.LinearInterpolator());
			        animator.setRepeatCount(-1);
			        animator.setDuration(2500);
			        ValueAnimator animator1=ValueAnimator.ofFloat(0,0,180,180,0);
			        addUpdateListener(animator1,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                rotateY= (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animator1.setInterpolator(new android.view.animation.LinearInterpolator());
			        animator1.setRepeatCount(-1);
			        animator1.setDuration(2500);
			        animators.add(animator);
			        animators.add(animator1);
			        return animators;
			    }
	}
	
	//SquareSpinIndicator
	
	public static class SquareSpinIndicator extends Indicator {
		    private float rotateX;
		    private float rotateY;
		    private Camera mCamera;
		    private Matrix mMatrix;
		    public SquareSpinIndicator(){
			        mCamera=new Camera();
			        mMatrix=new Matrix();
			    }
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        mMatrix.reset();
			        mCamera.save();
			        mCamera.rotateX(rotateX);
			        mCamera.rotateY(rotateY);
			        mCamera.getMatrix(mMatrix);
			        mCamera.restore();
			        mMatrix.preTranslate(-centerX(), -centerY());
			        mMatrix.postTranslate(centerX(), centerY());
			        canvas.concat(mMatrix);
			        canvas.drawRect(new RectF(getWidth()/5,getHeight()/5,getWidth()*4/5,getHeight()*4/5),paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator animator=ValueAnimator.ofFloat(0,180,180,0,0);
			        addUpdateListener(animator,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                rotateX= (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animator.setInterpolator(new android.view.animation.LinearInterpolator());
			        animator.setRepeatCount(-1);
			        animator.setDuration(2500);
			        ValueAnimator animator1=ValueAnimator.ofFloat(0,0,180,180,0);
			        addUpdateListener(animator1,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                rotateY= (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animator1.setInterpolator(new android.view.animation.LinearInterpolator());
			        animator1.setRepeatCount(-1);
			        animator1.setDuration(2500);
			        animators.add(animator);
			        animators.add(animator1);
			        return animators;
			    }
	}
	
	
	public static class LineScaleIndicator extends Indicator {
		    public static final float SCALE=1.0f;
		    float[] scaleYFloats=new float[]{SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float translateX=getWidth()/11;
			        float translateY=getHeight()/2;
			        for (int i = 0; i < 5; i++) {
				            canvas.save();
				            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
				            canvas.scale(SCALE, scaleYFloats[i]);
				            RectF rectF=new RectF(-translateX/2,-getHeight()/2.5f,translateX/2,getHeight()/2.5f);
				            canvas.drawRoundRect(rectF, 5, 5, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] delays=new long[]{100,200,300,400,500};
			        for (int i = 0; i < 5; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1, 0.4f, 1);
				            scaleAnim.setDuration(1000);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleYFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	//SemiCircleSpinIndicator
	
	public static class SemiCircleSpinIndicator extends Indicator {
		    private float degress;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        canvas.rotate(degress,centerX(),centerY());
			        RectF rectF=new RectF(0,0,getWidth(),getHeight());
			        canvas.drawArc(rectF,-60,120,false,paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0,180,360);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degress= (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        rotateAnim.setDuration(600);
			        rotateAnim.setRepeatCount(-1);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	
	public static class LineScalePartyIndicator extends Indicator {
		    public static final float SCALE=1.0f;
		    float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float translateX=getWidth()/9;
			        float translateY=getHeight()/2;
			        for (int i = 0; i < 4; i++) {
				            canvas.save();
				            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
				            canvas.scale(scaleFloats[i], scaleFloats[i]);
				            RectF rectF=new RectF(-translateX/2,-getHeight()/2.5f,translateX/2,getHeight()/2.5f);
				            canvas.drawRoundRect(rectF,5,5,paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] durations=new long[]{1260, 430, 1010, 730};
			        long[] delays=new long[]{770, 290, 280, 740};
			        for (int i = 0; i < 4; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.4f,1);
				            scaleAnim.setDuration(durations[i]);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	
	
	//BallPulseSyncIndicator
	public static class BallPulseSyncIndicator extends Indicator {
		    float[] translateYFloats=new float[3];
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(getWidth()-circleSpacing*2)/6;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        for (int i = 0; i < 3; i++) {
				            canvas.save();
				            float translateX=x+(radius*2)*i+circleSpacing*i;
				            canvas.translate(translateX, translateYFloats[i]);
				            canvas.drawCircle(0, 0, radius, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float circleSpacing=4;
			        float radius=(getWidth()-circleSpacing*2)/6;
			        int[] delays=new int[]{70,140,210};
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(getHeight()/2,getHeight()/2-radius*2,getHeight()/2);
				            scaleAnim.setDuration(600);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateYFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				        }
			        return animators;
			    }
	}
	//BallPulseRiseIndicator
	public static class BallPulseRiseIndicator extends Indicator {
		    private Camera mCamera;
		    private Matrix mMatrix;
		    private float degress;
		    public BallPulseRiseIndicator(){
			        mCamera=new Camera();
			        mMatrix=new Matrix();
			    }
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        mMatrix.reset();
			        mCamera.save();
			        mCamera.rotateX(degress);
			        mCamera.getMatrix(mMatrix);
			        mCamera.restore();
			        mMatrix.preTranslate(-centerX(), -centerY());
			        mMatrix.postTranslate(centerX(), centerY());
			        canvas.concat(mMatrix);
			        float radius=getWidth()/10;
			        canvas.drawCircle(getWidth()/4,radius*2,radius,paint);
			        canvas.drawCircle(getWidth()*3/4,radius*2,radius,paint);
			        canvas.drawCircle(radius,getHeight()-2*radius,radius,paint);
			        canvas.drawCircle(getWidth()/2,getHeight()-2*radius,radius,paint);
			        canvas.drawCircle(getWidth()-radius,getHeight()-2*radius,radius,paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator animator=ValueAnimator.ofFloat(0,360);
			        addUpdateListener(animator,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degress = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animator.setInterpolator(new android.view.animation.LinearInterpolator());
			        animator.setRepeatCount(-1);
			        animator.setDuration(1500);
			        animators.add(animator);
			        return animators;
			    }
	}
	
	//BallGridPulseIndicator
	public static class BallGridPulseIndicator extends Indicator {
		    public static final int ALPHA=255;
		    public static final float SCALE=1.0f;
		    int[] alphas=new int[]{ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA};
		    float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(getWidth()-circleSpacing*4)/6;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        float y = getWidth()/ 2-(radius*2+circleSpacing);
			        for (int i = 0; i < 3; i++) {
				            for (int j = 0; j < 3; j++) {
					                canvas.save();
					                float translateX=x+(radius*2)*j+circleSpacing*j;
					                float translateY=y+(radius*2)*i+circleSpacing*i;
					                canvas.translate(translateX, translateY);
					                canvas.scale(scaleFloats[3 * i + j], scaleFloats[3 * i + j]);
					                paint.setAlpha(alphas[3 * i + j]);
					                canvas.drawCircle(0, 0, radius, paint);
					                canvas.restore();
					            }
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] durations={720, 1020, 1280, 1420, 1450, 1180, 870, 1450, 1060};
			        int[] delays= {-60, 250, -170, 480, 310, 30, 460, 780, 450};
			        for (int i = 0; i < 9; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.5f,1);
				            scaleAnim.setDuration(durations[i]);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 210, 122, 255);
				            alphaAnim.setDuration(durations[i]);
				            alphaAnim.setRepeatCount(-1);
				            alphaAnim.setStartDelay(delays[i]);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphas[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
	}
	//BallGridBeatIndicator
	public static class BallGridBeatIndicator extends Indicator {
		    public static final int ALPHA=255;
		    int[] alphas=new int[]{ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(getWidth()-circleSpacing*4)/6;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        float y = getWidth()/ 2-(radius*2+circleSpacing);
			        for (int i = 0; i < 3; i++) {
				            for (int j = 0; j < 3; j++) {
					                canvas.save();
					                float translateX=x+(radius*2)*j+circleSpacing*j;
					                float translateY=y+(radius*2)*i+circleSpacing*i;
					                canvas.translate(translateX, translateY);
					                paint.setAlpha(alphas[3 * i + j]);
					                canvas.drawCircle(0, 0, radius, paint);
					                canvas.restore();
					            }
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] durations={960, 930, 1190, 1130, 1340, 940, 1200, 820, 1190};
			        int[] delays= {360, 400, 680, 410, 710, -150, -120, 10, 320};
			        for (int i = 0; i < 9; i++) {
				            final int index=i;
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 168,255);
				            alphaAnim.setDuration(durations[i]);
				            alphaAnim.setRepeatCount(-1);
				            alphaAnim.setStartDelay(delays[i]);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphas[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
	}
	//BallClipRotatePulseIndicator
	public static class BallClipRotatePulseIndicator extends Indicator {
		    float scaleFloat1,scaleFloat2,degrees;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=12;
			        float x=getWidth()/2;
			        float y=getHeight()/2;
			        canvas.save();
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat1, scaleFloat1);
			        paint.setStyle(Paint.Style.FILL);
			        canvas.drawCircle(0, 0, x / 2.5f, paint);
			        canvas.restore();
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat2, scaleFloat2);
			        canvas.rotate(degrees);
			        paint.setStrokeWidth(3);
			        paint.setStyle(Paint.Style.STROKE);
			        float[] startAngles=new float[]{225,45};
			        for (int i = 0; i < 2; i++) {
				            RectF rectF=new RectF(-x+circleSpacing,-y+circleSpacing,x-circleSpacing,y-circleSpacing);
				            canvas.drawArc(rectF, startAngles[i], 90, false, paint);
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
			        scaleAnim.setDuration(1000);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat1 = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator scaleAnim2=ValueAnimator.ofFloat(1,0.6f,1);
			        scaleAnim2.setDuration(1000);
			        scaleAnim2.setRepeatCount(-1);
			        addUpdateListener(scaleAnim2,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat2 = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180,360);
			        rotateAnim.setDuration(1000);
			        rotateAnim.setRepeatCount(-1);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        animators.add(scaleAnim);
			        animators.add(scaleAnim2);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	//BallClipRotateMultipleIndicator
	public static class BallClipRotateMultipleIndicator extends Indicator {
		    float scaleFloat=1,degrees;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        paint.setStrokeWidth(3);
			        paint.setStyle(Paint.Style.STROKE);
			        float circleSpacing=12;
			        float x=getWidth()/2;
			        float y=getHeight()/2;
			        canvas.save();
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.rotate(degrees);
			        float[] bStartAngles=new float[]{135,-45};
			        for (int i = 0; i < 2; i++) {
				            RectF rectF=new RectF(-x+circleSpacing,-y+circleSpacing,x-circleSpacing,y-circleSpacing);
				            canvas.drawArc(rectF, bStartAngles[i], 90, false, paint);
				        }
			        canvas.restore();
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.rotate(-degrees);
			        float[] sStartAngles=new float[]{225,45};
			        for (int i = 0; i < 2; i++) {
				            RectF rectF=new RectF(-x/1.8f+circleSpacing,-y/1.8f+circleSpacing,x/1.8f-circleSpacing,y/1.8f-circleSpacing);
				            canvas.drawArc(rectF, sStartAngles[i], 90, false, paint);
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.6f,1);
			        scaleAnim.setDuration(1000);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180,360);
			        rotateAnim.setDuration(1000);
			        rotateAnim.setRepeatCount(-1);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(scaleAnim);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	//BallClipRotateIndicator
	public static class BallClipRotateIndicator extends Indicator {
		    float scaleFloat=1,degrees;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        paint.setStyle(Paint.Style.STROKE);
			        paint.setStrokeWidth(3);
			        float circleSpacing=12;
			        float x = (getWidth()) / 2;
			        float y=(getHeight()) / 2;
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.rotate(degrees);
			        RectF rectF=new RectF(-x+circleSpacing,-y+circleSpacing,0+x-circleSpacing,0+y-circleSpacing);
			        canvas.drawArc(rectF, -45, 270, false, paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.6f,0.5f,1);
			        scaleAnim.setDuration(750);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0,180,360);
			        rotateAnim.setDuration(750);
			        rotateAnim.setRepeatCount(-1);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degrees = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(scaleAnim);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	
	// BallBeatIndicator
	
	public static class BallBeatIndicator extends Indicator {
		    public static final float SCALE=1.0f;
		    public static final int ALPHA=255;
		    private float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE};
		    int[] alphas=new int[]{ALPHA,
			            ALPHA,
			            ALPHA,};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        float radius=(getWidth()-circleSpacing*2)/6;
			        float x = getWidth()/ 2-(radius*2+circleSpacing);
			        float y=getHeight() / 2;
			        for (int i = 0; i < 3; i++) {
				            canvas.save();
				            float translateX=x+(radius*2)*i+circleSpacing*i;
				            canvas.translate(translateX, y);
				            canvas.scale(scaleFloats[i], scaleFloats[i]);
				            paint.setAlpha(alphas[i]);
				            canvas.drawCircle(0, 0, radius, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] delays=new int[]{350,0,350};
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.75f,1);
				            scaleAnim.setDuration(700);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(255,51,255);
				            alphaAnim.setDuration(700);
				            alphaAnim.setRepeatCount(-1);
				            alphaAnim.setStartDelay(delays[i]);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphas[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
	}
	
	public static class BallRotateIndicator extends Indicator {
		    float scaleFloat=0.5f;
		    float degress;
		    private Matrix mMatrix;
		    public BallRotateIndicator(){
			        mMatrix=new Matrix();
			    }
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float radius=getWidth()/10;
			        float x = getWidth()/ 2;
			        float y=getHeight()/2;
			        canvas.rotate(degress,centerX(),centerY());
			        canvas.save();
			        canvas.translate(x - radius * 2 - radius, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.drawCircle(0, 0, radius, paint);
			        canvas.restore();
			        canvas.save();
			        canvas.translate(x, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.drawCircle(0, 0, radius, paint);
			        canvas.restore();
			        canvas.save();
			        canvas.translate(x + radius * 2 + radius, y);
			        canvas.scale(scaleFloat, scaleFloat);
			        canvas.drawCircle(0,0,radius, paint);
			        canvas.restore();
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(0.5f,1,0.5f);
			        scaleAnim.setDuration(1000);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scaleFloat = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0,180,360);
			        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                degress = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        rotateAnim.setDuration(1000);
			        rotateAnim.setRepeatCount(-1);
			        animators.add(scaleAnim);
			        animators.add(rotateAnim);
			        return animators;
			    }
	}
	
	
	public static class BallScaleIndicator extends Indicator {
		    float scale=1;
		    int alpha=255;
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        paint.setAlpha(alpha);
			        canvas.scale(scale,scale,getWidth()/2,getHeight()/2);
			        paint.setAlpha(alpha);
			        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-circleSpacing,paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
			        scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        scaleAnim.setDuration(1000);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scale = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 0);
			        alphaAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        alphaAnim.setDuration(1000);
			        alphaAnim.setRepeatCount(-1);
			        addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                alpha = (int) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(scaleAnim);
			        animators.add(alphaAnim);
			        return animators;
			    }
	}
	
	public static class BallScaleMultipleIndicator extends Indicator {
		    float[] scaleFloats=new float[]{1,1,1};
		    int[] alphaInts=new int[]{255,255,255};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float circleSpacing=4;
			        for (int i = 0; i < 3; i++) {
				            paint.setAlpha(alphaInts[i]);
				            canvas.scale(scaleFloats[i],scaleFloats[i],getWidth()/2,getHeight()/2);
				            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-circleSpacing,paint);
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] delays=new long[]{0, 200, 400};
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
				            scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            scaleAnim.setDuration(1000);
				            scaleAnim.setRepeatCount(-1);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            scaleAnim.setStartDelay(delays[i]);
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(255,0);
				            alphaAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            alphaAnim.setDuration(1000);
				            alphaAnim.setRepeatCount(-1);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphaInts[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            scaleAnim.setStartDelay(delays[i]);
				            animators.add(scaleAnim);
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
	}
	
	public static class BallScaleRippleIndicator extends BallScaleIndicator {
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        paint.setStyle(Paint.Style.STROKE);
			        paint.setStrokeWidth(3);
			        super.draw(canvas, paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
			        scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        scaleAnim.setDuration(1000);
			        scaleAnim.setRepeatCount(-1);
			        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                scale = (float) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        ValueAnimator alphaAnim=ValueAnimator.ofInt(0, 255);
			        alphaAnim.setInterpolator(new android.view.animation.LinearInterpolator());
			        alphaAnim.setDuration(1000);
			        alphaAnim.setRepeatCount(-1);
			        addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
				            @Override
				            public void onAnimationUpdate(ValueAnimator animation) {
					                alpha = (int) animation.getAnimatedValue();
					                postInvalidate();
					            }
				        });
			        animators.add(scaleAnim);
			        animators.add(alphaAnim);
			        return animators;
			    }
	}
	
	public static class BallScaleRippleMultipleIndicator extends BallScaleMultipleIndicator {
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        paint.setStyle(Paint.Style.STROKE);
			        paint.setStrokeWidth(3);
			        super.draw(canvas, paint);
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        long[] delays=new long[]{0, 200, 400};
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
				            scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            scaleAnim.setDuration(1000);
				            scaleAnim.setRepeatCount(-1);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            scaleAnim.setStartDelay(delays[i]);
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(0,255);
				            scaleAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            alphaAnim.setDuration(1000);
				            alphaAnim.setRepeatCount(-1);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphaInts[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            scaleAnim.setStartDelay(delays[i]);
				            animators.add(scaleAnim);
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static class BallSpinFadeLoaderIndicator extends Indicator {
		    public static final float SCALE=1.0f;
		    public static final int ALPHA=255;
		    float[] scaleFloats=new float[]{SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE,
			            SCALE};
		    int[] alphas=new int[]{ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA,
			            ALPHA};
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        float radius=getWidth()/10;
			        for (int i = 0; i < 8; i++) {
				            canvas.save();
				            Point point=circleAt(getWidth(),getHeight(),getWidth()/2-radius,i*(Math.PI/4));
				            canvas.translate(point.x,point.y);
				            canvas.scale(scaleFloats[i],scaleFloats[i]);
				            paint.setAlpha(alphas[i]);
				            canvas.drawCircle(0,0,radius,paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        int[] delays= {0, 120, 240, 360, 480, 600, 720, 780, 840};
			        for (int i = 0; i < 8; i++) {
				            final int index=i;
				            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.4f,1);
				            scaleAnim.setDuration(1000);
				            scaleAnim.setRepeatCount(-1);
				            scaleAnim.setStartDelay(delays[i]);
				            addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    scaleFloats[index] = (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 77, 255);
				            alphaAnim.setDuration(1000);
				            alphaAnim.setRepeatCount(-1);
				            alphaAnim.setStartDelay(delays[i]);
				            addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    alphas[index] = (int) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(scaleAnim);
				            animators.add(alphaAnim);
				        }
			        return animators;
			    }
		    Point circleAt(int width,int height,float radius,double angle){
			        float x= (float) (width/2+radius*(Math.cos(angle)));
			        float y= (float) (height/2+radius*(Math.sin(angle)));
			        return new Point(x,y);
			    }
		    final class Point{
			        public float x;
			        public float y;
			        public Point(float x, float y){
				            this.x=x;
				            this.y=y;
				        }
			    }
	}
	
	
	public static class BallTrianglePathIndicator extends Indicator {
		    float[] translateX=new float[3],translateY=new float[3];
		    @Override
		    public void draw(Canvas canvas, Paint paint) {
			        paint.setStrokeWidth(3);
			        paint.setStyle(Paint.Style.STROKE);
			        for (int i = 0; i < 3; i++) {
				            canvas.save();
				            canvas.translate(translateX[i], translateY[i]);
				            canvas.drawCircle(0, 0, getWidth() / 10, paint);
				            canvas.restore();
				        }
			    }
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float startX=getWidth()/5;
			        float startY=getWidth()/5;
			        for (int i = 0; i < 3; i++) {
				            final int index=i;
				            ValueAnimator translateXAnim=ValueAnimator.ofFloat(getWidth()/2,getWidth()-startX,startX,getWidth()/2);
				            if (i==1){
					                translateXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,getWidth()/2,getWidth()-startX);
					            }else if (i==2){
					                translateXAnim=ValueAnimator.ofFloat(startX,getWidth()/2,getWidth()-startX,startX);
					            }
				            ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,getHeight()-startY,getHeight()-startY,startY);
				            if (i==1){
					                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,startY,getHeight()-startY);
					            }else if (i==2){
					                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,startY,getHeight()-startY,getHeight()-startY);
					            }
				            translateXAnim.setDuration(2000);
				            translateXAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				                translateXAnim.setRepeatCount(-1);
				            addUpdateListener(translateXAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateX [index]= (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            translateYAnim.setDuration(2000);
				            translateYAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translateYAnim.setRepeatCount(-1);
				            addUpdateListener(translateYAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateY [index]= (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(translateXAnim);
				            animators.add(translateYAnim);
				        }
			        return animators;
			    }
	}
	
	
	public static class BallZigZagDeflectIndicator extends BallZigZagIndicator {
		    @Override
		    public ArrayList<ValueAnimator> onCreateAnimators() {
			        ArrayList<ValueAnimator> animators=new ArrayList<>();
			        float startX=getWidth()/6;
			        float startY=getWidth()/6;
			        for (int i = 0; i < 2; i++) {
				            final int index=i;
				            ValueAnimator translateXAnim=ValueAnimator.ofFloat(startX,getWidth()-startX,startX,getWidth()-startX,startX);
				            if (i==1){
					                translateXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,getWidth()-startX,startX,getWidth()-startX);
					            }
				            ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,startY,getHeight()-startY,getHeight()-startY,startY);
				            if (i==1){
					                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,startY,startY,getHeight()-startY);
					            }
				            translateXAnim.setDuration(2000);
				            translateXAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translateXAnim.setRepeatCount(-1);
				            addUpdateListener(translateXAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateX [index]= (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            translateYAnim.setDuration(2000);
				            translateYAnim.setInterpolator(new android.view.animation.LinearInterpolator());
				            translateYAnim.setRepeatCount(-1);
				            addUpdateListener(translateYAnim,new ValueAnimator.AnimatorUpdateListener() {
					                @Override
					                public void onAnimationUpdate(ValueAnimator animation) {
						                    translateY [index]= (float) animation.getAnimatedValue();
						                    postInvalidate();
						                }
					            });
				            animators.add(translateXAnim);
				            animators.add(translateYAnim);
				        }
			        return animators;
			    }
	}
	
	{
	}
	
	
	public void _AVLd(final String _key) {
		AVLoadingIndicatorView v = new AVLoadingIndicatorView(this);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(70, 70);
		v.setLayoutParams(lp);
		progress_layout.addView(v);
		
		v.setIndicatorColor(Color.BLUE);
		
		
		v.setIndicatorName(_key);
		
		
		v.show();
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}