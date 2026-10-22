package com.nexusteam.internal;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class ViewPagerPaletteItem extends il {

	public ViewPagerPaletteItem(Context context) {
		super(context);
	}

	@Override
	public void a(Context context) {
		super.a(context);
		try {
			int res = context.getResources().getIdentifier("widget_listview", "drawable", context.getPackageName());
			if (res == 0) {
				res = R.drawable.icon_bg;
			}
			setWidgetImage(res);
		} catch (Throwable t) {
			try { setWidgetImage(R.drawable.icon_bg); } catch (Throwable ignored) {}
		}
		setWidgetName("ViewPager");
	}

	@Override
	public ViewBean getBean() {
		ViewBean viewBean = new ViewBean();
		viewBean.type = 40;
		viewBean.layout.paddingLeft = 8;
		viewBean.layout.paddingTop = 8;
		viewBean.layout.paddingRight = 8;
		viewBean.layout.paddingBottom = 8;
		viewBean.text.text = getName() != null ? getName() : "ViewPager";
		return viewBean;
	}
}
