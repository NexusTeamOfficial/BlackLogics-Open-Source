package com.shapun.layouteditor;

import android.widget.LinearLayout;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import java.util.ArrayList;

public class EditorLayout extends LinearLayout {
	
	View mFocusedView;
	Paint mFocusedViewPaint;
	ArrayList<Rect> strokedRects ;
	Rect focusedViewRect ;
	//Rect border;
	Paint strokePaint;
	
	public EditorLayout(Context context) {
		this(context, null);
	}
	
	public EditorLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public EditorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}
	
	public EditorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		mFocusedViewPaint = new Paint();
		mFocusedViewPaint.setColor(0x88008DCD);
		strokedRects = new ArrayList<>();
		strokePaint = new Paint();
		strokePaint.setColor(0xFF000000);
		strokePaint.setStyle(Style.STROKE);
		strokePaint.setStrokeWidth(2);
	}
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(getFocusedView() != null)focusedViewRect = getRect(getFocusedView());
		strokedRects = getRects(this); 
		//border = new Rect(0, 0, getWidth(), getHeight()) ;
		
		
	}
	
	
	
	public void setFocusedView(View view){
		mFocusedView = view;
	}
	public View getFocusedView(){
		return mFocusedView;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		//canvas.drawRect(border,strokePaint);
		/*
		for(Rect rect :strokedRects){
			canvas.drawRect(rect,strokePaint);
		}
		*/
		
		if(getFocusedView() != null){
			canvas.drawRect(getRect(getFocusedView()),mFocusedViewPaint);
		}
		
	}
	
	
	
	private int getRelativeLeft(View view) {
		if (view.getParent() == this)
		return view.getLeft();
		else
		return view.getLeft() + getRelativeLeft((View) view.getParent());
	}
	
	private int getRelativeTop(View view) {
		if (view.getParent() == this)
		return view.getTop();
		else
		return view.getTop() + getRelativeTop((View) view.getParent());
	}
	
	
	
	private Rect getRect(View view){
		int left = getRelativeLeft(view);
		int top = getRelativeTop(view);
		return new Rect(left,top,left+view.getWidth(),top+view.getHeight());
	}
	
	private ArrayList<Rect> getRects(ViewGroup parent){
		ArrayList<Rect> list = new ArrayList<>();
		for(int i = 0 ;i<parent.getChildCount();i++){
			View child = parent.getChildAt(i);
			
			if(child instanceof ViewGroup ){
				list.add(getRect(child));
				list.addAll(getRects((ViewGroup)child));
			}
			
		}
		return list;
	}
	
	
	@Override
	public void addView(View child) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("RootView can host only one direct child");
		}
		
		super.addView(child);
	}
	
	
	
	@Override
	public void addView(View child, int index) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("RootView can host only one direct child");
		}
		
		super.addView(child, index);
	}
	
	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("RootView can host only one direct child");
		}
		
		super.addView(child, params);
	}
	
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (getChildCount() > 0) {
			throw new IllegalStateException("RootView can host only one direct child");
		}
		
		super.addView(child, index, params);
	}
	
}
