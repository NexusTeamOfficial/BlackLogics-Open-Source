package com.nexusteam.internal.os.layouteditor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.besome.blacklogics.R;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;
import java.util.ArrayList;

public class WidgetListView extends ListView implements WidgetContract {
	private String mWidgetId;
	private String mWidgetName;
	private boolean isSelected = false;
	private Paint widgetPaint = new Paint();
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mItems;
	
	public WidgetListView(Context context) {
		this(context, null);
	}
	
	public WidgetListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public WidgetListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	//  @Override
	public void init() {
		//super.init();
		setSelector(android.R.color.transparent);
		setChoiceMode(CHOICE_MODE_NONE);
		setDivider(new ColorDrawable(Color.parseColor("#90D7D7")));
		setDividerHeight(1);
		setPadding(8, 8, 8, 8);
		// setEnabled(false);
		
		mItems = new ArrayList<>();
		mItems.add("List item 1");
		mItems.add("List item 2");
		mItems.add("List item 3");
		mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mItems);
		setAdapter(mAdapter);
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
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isSelected) {
			canvas.drawRect(0, 0, getWidth(), getHeight(), widgetPaint);
		}
	}
	
	public void addItem(String item) {
		mItems.add(item);
		mAdapter.notifyDataSetChanged();
	}
	
	public void removeItem(int position) {
		if (position >= 0 && position < mItems.size()) {
			mItems.remove(position);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public void clearItems() {
		mItems.clear();
		mAdapter.notifyDataSetChanged();
	}
	
	public ArrayList<String> getItems() {
		return mItems;
	}
	
	public void setItems(ArrayList<String> items) {
		this.mItems = new ArrayList<>(items);
		mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mItems);
		setAdapter(mAdapter);
	}
	
	@Override
	public String newWidgetId() {
		int i = 1;
		while (WidgetUtil.isWidgetIdExist("listview" + i)) {
			i++;
		}
		return "listview" + i;
	}
}
