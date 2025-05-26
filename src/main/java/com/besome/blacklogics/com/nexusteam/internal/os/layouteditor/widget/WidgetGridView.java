package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.util.ArrayList;

public class WidgetGridView extends GridView implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mItems;
	
	public WidgetGridView(Context context) {
		this(context, null);
	}
	
	public WidgetGridView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	// @Override
	public void init() {
		//super.init();
		setNumColumns(2);
		mItems = new ArrayList<>();
		mItems.add("Item 1");
		mItems.add("Item 2");
		mItems.add("Item 3");
		mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mItems);
		setAdapter(mAdapter);
		// setEnabled(false);
	}
	
	@Override
	public boolean performClick() {
		// Do nothing, just return true to consume the click
		return true;
	}
	
	
	@Override
	public void setWidgetId(String id) {
		this.mWidgetId = id;
	}
	
	@Override
	public String getWidgetId() {
		return mWidgetId;
	}
	
	@Override
	public void setWidgetName(String name) {
		this.mWidgetName = name;
	}
	
	@Override
	public String getWidgetName() {
		return mWidgetName;
	}
	
	@Override
	public Paint getWidgetPaint() {
		return widgetPaint;
	}
	
	@Override
	public void select() {
		isSelected = true;
		widgetPaint.setColor(getResources().getColor(R.color.widget_selection_color));
		invalidate();
	}
	
	@Override
	public void unselect() {
		isSelected = false;
		widgetPaint.setColor(0);
		invalidate();
	}
	
	@Override
	protected void onDraw(android.graphics.Canvas canvas) {
		super.onDraw(canvas);
		if (isSelected) {
			canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
		}
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("gridview" + i)) {
			i++;
		}
		return "gridview" + i;
	}
}
