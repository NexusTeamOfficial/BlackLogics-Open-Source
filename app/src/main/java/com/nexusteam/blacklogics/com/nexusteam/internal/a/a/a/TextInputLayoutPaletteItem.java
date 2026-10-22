package com.nexusteam.internal;

import android.content.Context;
import com.nexusteam.internal.beans.ViewBean;
import com.nexusteam.blacklogics.R;

public class TextInputLayoutPaletteItem extends il {

	public TextInputLayoutPaletteItem(Context context) {
		super(context);
	}

	@Override
	public void a(Context context) {
		super.a(context);
		try {
			int res = context.getResources().getIdentifier("widget_edittext", "drawable", context.getPackageName());
			if (res == 0) {
				res = R.drawable.icon_bg;
			}
			setWidgetImage(res);
		} catch (Throwable t) {
			try { setWidgetImage(R.drawable.icon_bg); } catch (Throwable ignored) {}
		}
		setWidgetName("TextInputLayout");
	}

	@Override
	public ViewBean getBean() {
		ViewBean viewBean = new ViewBean();
		viewBean.type = 31;
		viewBean.layout.paddingLeft = 8;
		viewBean.layout.paddingTop = 8;
		viewBean.layout.paddingRight = 8;
		viewBean.layout.paddingBottom = 8;
		viewBean.text.text = getName() != null ? getName() : "TextInputLayout";
		return viewBean;
	}
}
