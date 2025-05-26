package com.shapun.layouteditor.utils;

import android.content.ClipData;
import android.view.View;
import android.os.Build;

public class DragAndDropUtils {
	public static boolean startDragAndDrop( View v, ClipData data,
	View.DragShadowBuilder shadowBuilder, Object localState, int flags) {
		if (Build.VERSION.SDK_INT >= 24) {
			return v.startDragAndDrop(data, shadowBuilder, localState, flags);
		} else {
			return v.startDrag(data, shadowBuilder, localState, flags);
		}
	}
	
	/**
* Cancel the drag and drop operation.
*/
	public static void cancelDragAndDrop(View v) {
		if (Build.VERSION.SDK_INT >= 24) {
			v.cancelDragAndDrop();
		}
	}
	
	/**
* Update the drag shadow while drag and drop is in progress.
*/
	public static void updateDragShadow(View v, View.DragShadowBuilder shadowBuilder) {
		if (Build.VERSION.SDK_INT >= 24) {
			v.updateDragShadow(shadowBuilder);
		}
	}
	
}
