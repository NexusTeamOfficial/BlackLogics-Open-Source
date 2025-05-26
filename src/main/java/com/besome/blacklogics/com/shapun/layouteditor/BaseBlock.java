package com.shapun.layouteditor;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
//import com.shapun.blocks.BaseBlock;

public class BaseBlock extends LinearLayout{
	private Paint paint;
	private int mBlockColor = 0xff5CB722;
	private int heightSpacing ;
	private int widthSpacing ;
	
	public BaseBlock(Context context) {
		this(context, null);
	}
	
	public BaseBlock(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public BaseBlock(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}
	
	public BaseBlock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		
		paint = new Paint();
		paint.setStrokeWidth(5);            
		paint.setStyle(Paint.Style.STROKE);         
		heightSpacing = dpToPx(8);
		widthSpacing = dpToPx(25);
		
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = width;
		
		paint.setColor(getBlockColor());    
	
		
		
		Path path = new Path();
		/*
		//RectF rect = new RectF(0,0, width,height);
		
		path.moveTo(width,height/2);
		path.arcTo(rect,0,90);
		
		path.lineTo(width/2,height);
		path.lineTo(width,height);
		
		
		path.close();
		*/
		
		path.moveTo(0,0);
		path.lineTo(widthSpacing,0);
		path.lineTo(widthSpacing,heightSpacing);
		path.lineTo(2*widthSpacing,heightSpacing);
		path.lineTo(2*widthSpacing,0);
		path.lineTo(getWidth(),0);
		path.lineTo(getWidth(),getHeight()- heightSpacing);
		path.lineTo(2*widthSpacing, getHeight()-heightSpacing);
		path.lineTo(2*widthSpacing,getHeight());
		path.lineTo(widthSpacing, getHeight());
		path.lineTo(widthSpacing, getHeight()-heightSpacing);		 
		path.lineTo(0,getHeight()- heightSpacing);
		path.lineTo(0,heightSpacing);
		
		path.close();
		canvas.drawPath(path, paint);
		
	}
	
	public int getBlockColor(){
		return mBlockColor;
	}
	
	public void setBlockColor(int color){
		mBlockColor = color;
		invalidate();
	}
	
	public  int dpToPx(float dp) {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp , this.getContext().getResources().getDisplayMetrics());
	}
	
}
