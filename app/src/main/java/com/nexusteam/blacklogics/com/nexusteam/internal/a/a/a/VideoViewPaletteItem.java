package com.nexusteam.internal;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class VideoViewPaletteItem extends il {

	public VideoViewPaletteItem(Context context) {
		super(context);
	}

	@Override
	public void a(Context context) {
		super.a(context);
		try {
			int res = context.getResources().getIdentifier("widget_web_view", "drawable", context.getPackageName());
			if (res == 0) {
				res = R.drawable.icon_bg;
			}
			setWidgetImage(res);
		} catch (Throwable t) {
			try { setWidgetImage(R.drawable.icon_bg); } catch (Throwable ignored) {}
		}
		setWidgetName("VideoView");
	}

	@Override
	public ViewBean getBean() {
		ViewBean viewBean = new ViewBean();
		viewBean.type = 24;
		viewBean.layout.paddingLeft = 8;
		viewBean.layout.paddingTop = 8;
		viewBean.layout.paddingRight = 8;
		viewBean.layout.paddingBottom = 8;
		viewBean.text.text = getName() != null ? getName() : "VideoView";
		return viewBean;
	}
}
