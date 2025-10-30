package com.shapun.layouteditor.utils;

import android.view.View;
import android.view.ViewGroup;
import android.animation.LayoutTransition;

public class AnimationUtils {
	public static void animate(ViewGroup view){
		view.setLayoutTransition(new LayoutTransition());
	}
}
