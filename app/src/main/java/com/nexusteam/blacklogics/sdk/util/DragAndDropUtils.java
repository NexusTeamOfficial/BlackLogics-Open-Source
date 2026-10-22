package com.nexusteam.blacklogics.sdk.utils;

import android.content.ClipData;
import android.os.Build;
import android.view.View;

public class DragAndDropUtils {
	public static boolean startDragAndDrop(View v, ClipData data,
			View.DragShadowBuilder shadowBuilder, Object localState, int flags) {
		if (Build.VERSION.SDK_INT >= 24) {
			return v.startDragAndDrop(data, shadowBuilder, localState, flags);
		} else {
			return v.startDrag(data, shadowBuilder, localState, flags);
		}
	}

	public static void cancelDragAndDrop(View v) {
		if (Build.VERSION.SDK_INT >= 24) {
			v.cancelDragAndDrop();
		}
	}

	public static void updateDragShadow(View v, View.DragShadowBuilder shadowBuilder) {
		if (Build.VERSION.SDK_INT >= 24) {
			v.updateDragShadow(shadowBuilder);
		}
	}
}
