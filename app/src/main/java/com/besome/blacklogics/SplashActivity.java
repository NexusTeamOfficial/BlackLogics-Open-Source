package com.besome.blacklogics;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
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
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;

public class SplashActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private LinearLayout baseLayout;
	private View baseSpace1;
	private FrameLayout frame_layout2;
	private TextView baseTag;
	private View baseSpace2;
	private TextView baseGodTag;
	private View circleEffect;
	private ImageView baseIcon;
	
	private Intent bI = new Intent();
	private TimerTask bT;
	private ObjectAnimator anim = new ObjectAnimator();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.splash);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		baseLayout = findViewById(R.id.baseLayout);
		baseSpace1 = findViewById(R.id.baseSpace1);
		frame_layout2 = findViewById(R.id.frame_layout2);
		baseTag = findViewById(R.id.baseTag);
		baseSpace2 = findViewById(R.id.baseSpace2);
		baseGodTag = findViewById(R.id.baseGodTag);
		circleEffect = findViewById(R.id.circleEffect);
		baseIcon = findViewById(R.id.baseIcon);
	}
	
	private void initializeLogic() {
		bT = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						bI.setClass(getApplicationContext(), MainActivity.class);
						startActivity(bI);
					}
				});
			}
		};
		_timer.schedule(bT, (int)(5000));
		
		
		// Pulse Animation
		circleEffect.setVisibility(View.VISIBLE);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(circleEffect, "scaleX", 0f, 1.5f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(circleEffect, "scaleY", 0f, 1.5f);
		ObjectAnimator alpha = ObjectAnimator.ofFloat(circleEffect, "alpha", 1f, 0f);
		AnimatorSet pulse = new AnimatorSet();
		pulse.playTogether(scaleX, scaleY, alpha);
		pulse.setDuration(2000);
		pulse.setStartDelay(500);
		pulse.start();
		
		// Bounce Icon
		ScaleAnimation scale = new ScaleAnimation(
		    0.8f, 1f, 0.8f, 1f,
		    Animation.RELATIVE_TO_SELF, 0.5f,
		    Animation.RELATIVE_TO_SELF, 0.5f
		);
		scale.setDuration(700);
		//scale.setRepeatCount(Animation.INFINITE);
		//scale.setRepeatMode(Animation.REVERSE);
		baseIcon.startAnimation(scale);
		
		// Color Filter Animation (icon color change)
		ObjectAnimator colorAnim = ObjectAnimator.ofArgb(
		    baseIcon,
		    "colorFilter",
		    Color.parseColor("#FF4081"),
		    Color.parseColor("#448AFF"),
		    Color.parseColor("#00E676"),
		    Color.parseColor("#FFD600")
		);
		colorAnim.setDuration(4000);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
		colorAnim.start();
		
		// Fade In App Name
		baseTag.setAlpha(0f);
		baseTag.setVisibility(View.VISIBLE);
		baseTag.animate()
		    .alpha(1f)
		    .setDuration(2000)
		    .setStartDelay(1500)
		    .start();
		
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