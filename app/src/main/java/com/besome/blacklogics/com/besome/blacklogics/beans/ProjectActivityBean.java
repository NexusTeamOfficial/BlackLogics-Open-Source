package com.besome.blacklogics.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

import android.text.InputType;
import android.view.Gravity;
import android.view.View;

public class ProjectActivityBean implements Parcelable {
	@Expose
	private String activityName = "MainActivity"; // Name of the activity (e.g., MainActivity)
	@Expose
	private String layoutName = "main"; // Associated layout name (e.g., main)
	@Expose
	private String packageName; // Package name of the project
	@Expose
	private boolean isMainActivity = true; // Flag to indicate if this is the main activity
	@Expose
	private String scId; // Project-specific ID
	@Expose
	private String projectName; // Name of the project
	@Expose
	private List<ViewBean> widgets; // List of widgets in the layout
	@Expose
	private String javaCode; // Generated Java code for the activity
	@Expose
	private String xmlCode; // Generated XML code for the layout
	@Expose
	private boolean useAndroidX; // Flag to indicate if AndroidX is used
	
	// Default constructor
	public ProjectActivityBean() {
		this.widgets = new ArrayList<>();
	}
	
	// Constructor with basic fields
	public ProjectActivityBean(String activityName, String layoutName, String packageName, boolean isMainActivity, String scId, String projectName) {
		this.activityName = activityName;
		this.layoutName = layoutName;
		this.packageName = packageName;
		this.isMainActivity = isMainActivity;
		this.scId = scId;
		this.projectName = projectName;
		this.widgets = new ArrayList<>();
		this.useAndroidX = true; // Default to AndroidX
	}
	
	// Getters and Setters
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getLayoutName() {
		return layoutName;
	}
	
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public boolean isMainActivity() {
		return isMainActivity;
	}
	
	public void setMainActivity(boolean mainActivity) {
		isMainActivity = mainActivity;
	}
	
	public String getScId() {
		return scId;
	}
	
	public void setScId(String scId) {
		this.scId = scId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public List<ViewBean> getWidgets() {
		return widgets;
	}
	
	public void setWidgets(List<ViewBean> widgets) {
		this.widgets = widgets;
	}
	
	public String getJavaCode() {
		return javaCode;
	}
	
	public void setJavaCode(String javaCode) {
		this.javaCode = javaCode;
	}
	
	public String getXmlCode() {
		return xmlCode;
	}
	
	public void setXmlCode(String xmlCode) {
		this.xmlCode = xmlCode;
	}
	
	public boolean isUseAndroidX() {
		return useAndroidX;
	}
	
	public void setUseAndroidX(boolean useAndroidX) {
		this.useAndroidX = useAndroidX;
	}
	
	// Parcelable implementation
	protected ProjectActivityBean(Parcel in) {
		activityName = in.readString();
		layoutName = in.readString();
		packageName = in.readString();
		isMainActivity = in.readByte() != 0;
		scId = in.readString();
		projectName = in.readString();
		widgets = in.createTypedArrayList(ViewBean.CREATOR);
		javaCode = in.readString();
		xmlCode = in.readString();
		useAndroidX = in.readByte() != 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(activityName);
		dest.writeString(layoutName);
		dest.writeString(packageName);
		dest.writeByte((byte) (isMainActivity ? 1 : 0));
		dest.writeString(scId);
		dest.writeString(projectName);
		dest.writeTypedList(widgets);
		dest.writeString(javaCode);
		dest.writeString(xmlCode);
		dest.writeByte((byte) (useAndroidX ? 1 : 0));
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Creator<ProjectActivityBean> CREATOR = new Creator<ProjectActivityBean>() {
		@Override
		public ProjectActivityBean createFromParcel(Parcel in) {
			return new ProjectActivityBean(in);
		}
		
		@Override
		public ProjectActivityBean[] newArray(int size) {
			return new ProjectActivityBean[size];
		}
	};
	
	// Helper class for widget data
	public static class ViewBean implements Parcelable {
		@Expose
		private String widgetType; // Type of widget (e.g., Button, TextView)
		@Expose
		private String widgetId; // ID of the widget
		@Expose
		private int width; // Width of the widget
		@Expose
		private int height; // Height of the widget
		@Expose
		private float translationX; // Translation X
		@Expose
		private float translationY; // Translation Y
		@Expose
		private int backgroundColor; // Background color
		@Expose
		private String text; // Text for TextView/Button
		@Expose
		private float textSize; // Text size
		@Expose
		private int textColor; // Text color
		@Expose
		private String imagePath; // Image path for ImageView
		@Expose
		private int marginLeft; // Margin left
		@Expose
		private int marginTop; // Margin top
		@Expose
		private int marginRight; // Margin right
		@Expose
		private int marginBottom; // Margin bottom
		@Expose
		private int paddingLeft; // Padding left
		@Expose
		private int paddingTop; // Padding top
		@Expose
		private int paddingRight; // Padding right
		@Expose
		private int paddingBottom; // Padding bottom
		
		@Expose
		public int visibility = View.VISIBLE;
		@Expose
		public float alpha = 1.0f;
		@Expose
		public float rotation = 0f;
		@Expose
		public float scaleX = 1.0f;
		@Expose
		public float scaleY = 1.0f;
		@Expose
		public String textStyle = "";
		@Expose
		public String fontFamily = "";
		@Expose
		public int gravity = Gravity.START;
		@Expose
		public int textAlignment = View.TEXT_ALIGNMENT_GRAVITY;
		@Expose
		public float lineSpacingMultiplier = 1.0f;
		@Expose
		public float lineSpacingExtra = 0f;
		@Expose
		public int maxLines = Integer.MAX_VALUE;
		@Expose
		public boolean singleLine = false;
		@Expose
		public String hint = "";
		@Expose
		public int inputType = InputType.TYPE_CLASS_TEXT;
		@Expose
		private boolean checked; // For CheckBox and Switch
		@Expose
		private String scaleType; // For ImageView
		@Expose
		private int lines; // For TextView/EditText
		@Expose
		private float elevation; // For widget elevation (shadow effect)
		@Expose
		private int progress; // For SeekBar and ProgressBar
		@Expose
		private int maxProgress; // For SeekBar and ProgressBar
		@Expose
		private String progressType; // For ProgressBar (HORIZONTAL or CIRCULAR)
		@Expose
		private String parentId; // Add parent ID
		@Expose
		private List<ViewBean> children; // Add children list
		
		public ViewBean() {
			this.children = new ArrayList<>();
		}
		
		public ViewBean(String widgetType, String widgetId) {
			this.widgetType = widgetType;
			this.widgetId = widgetId;
		}
		
		// Getters and Setters
		public String getWidgetType() {
			return widgetType;
		}
		
		public void setWidgetType(String widgetType) {
			this.widgetType = widgetType;
		}
		
		public String getWidgetId() {
			return widgetId;
		}
		
		public String getId() {
			return widgetId;
		}
		
		public void setWidgetId(String widgetId) {
			this.widgetId = widgetId;
		}
		
		public int getWidth() {
			return width;
		}
		
		public void setWidth(int width) {
			this.width = width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public float getTranslationX() {
			return translationX;
		}
		
		public void setTranslationX(float translationX) {
			this.translationX = translationX;
		}
		
		public float getTranslationY() {
			return translationY;
		}
		
		public void setTranslationY(float translationY) {
			this.translationY = translationY;
		}
		
		public int getBackgroundColor() {
			return backgroundColor;
		}
		
		public void setBackgroundColor(int backgroundColor) {
			this.backgroundColor = backgroundColor;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		public float getTextSize() {
			return textSize;
		}
		
		public void setTextSize(float textSize) {
			this.textSize = textSize;
		}
		
		public int getTextColor() {
			return textColor;
		}
		
		public void setTextColor(int textColor) {
			this.textColor = textColor;
		}
		
		public String getImagePath() {
			return imagePath;
		}
		
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
		
		public int getMarginLeft() {
			return marginLeft;
		}
		
		public void setMarginLeft(int marginLeft) {
			this.marginLeft = marginLeft;
		}
		
		public int getMarginTop() {
			return marginTop;
		}
		
		public void setMarginTop(int marginTop) {
			this.marginTop = marginTop;
		}
		
		public int getMarginRight() {
			return marginRight;
		}
		
		public void setMarginRight(int marginRight) {
			this.marginRight = marginRight;
		}
		
		public int getMarginBottom() {
			return marginBottom;
		}
		
		public void setMarginBottom(int marginBottom) {
			this.marginBottom = marginBottom;
		}
		
		public int getPaddingLeft() {
			return paddingLeft;
		}
		
		public void setPaddingLeft(int paddingLeft) {
			this.paddingLeft = paddingLeft;
		}
		
		public int getPaddingTop() {
			return paddingTop;
		}
		
		public void setPaddingTop(int paddingTop) {
			this.paddingTop = paddingTop;
		}
		
		public int getPaddingRight() {
			return paddingRight;
		}
		
		public void setPaddingRight(int paddingRight) {
			this.paddingRight = paddingRight;
		}
		
		public int getPaddingBottom() {
			return paddingBottom;
		}
		
		public void setPaddingBottom(int paddingBottom) {
			this.paddingBottom = paddingBottom;
		}
		
		public int getVisibility() {
			return visibility;
		}
		
		public float getAlpha() {
			return alpha;
		}
		
		public float getRotation() {
			return rotation;
		}
		
		public float getScaleX() {
			return scaleX;
		}
		
		public float getScaleY() {
			return scaleY;
		}
		
		public String getTextStyle() {
			return textStyle;
		}
		
		public void setTextStyle(String style) {
			this.textStyle = style;
		}
		
		public String getFontFamily() {
			return fontFamily;
		}
		
		public int getGravity() {
			return gravity;
		}
		
		public int getTextAlignment() {
			return textAlignment;
		}
		
		public float getLineSpacingMultiplier() {
			return lineSpacingMultiplier;
		}
		
		public float getLineSpacingExtra() {
			return lineSpacingExtra;
		}
		
		public int getMaxLines() {
			return maxLines;
		}
		
		public boolean isSingleLine() {
			return singleLine;
		}
		
		public String getHint() {
			return hint;
		}
		
		public int getInputType() {
			return inputType;
		}
		
		public void setGravity(int gravity) {
			this.gravity = gravity;
		}
		
		public boolean isChecked() {
			return checked;
		}
		
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		
		public void setLines(int lines) {
			this.lines = lines;
		}
		
		public float getElevation() {
			return elevation;
		}
		
		public void setElevation(float elevation) {
			this.elevation = elevation;
		}
		
		public String getScaleType() {
			return scaleType != null ? scaleType : "FIT_CENTER"; // Default scale type
		}
		
		public void setScaleType(String scaleType) {
			this.scaleType = scaleType;
		}
		
		public void setVisibility(int visibility) {
			this.visibility = visibility;
		}
		
		public void setAlpha(float alpha) {
			this.alpha = alpha;
		}
		
		public void setHint(String hint) {
			this.hint = hint;
		}
		
		public int getProgress() {
			return progress;
		}
		
		public void setProgress(int progress) {
			this.progress = progress;
		}
		
		public int getMaxProgress() {
			return maxProgress;
		}
		
		public void setMaxProgress(int maxProgress) {
			this.maxProgress = maxProgress;
		}
		
		public String getProgressType() {
			return progressType;
		}
		
		public void setProgressType(String progressType) {
			this.progressType = progressType;
		}
		
		public void setRotation(float rotation) {
			this.rotation = rotation;
		}
		
		public void setScaleX(float scaleX) {
			this.scaleX = scaleX;
		}
		
		public void setScaleY(float scaleY) {
			this.scaleY = scaleY;
		}
		
		public void setMaxLines(int maxLines) {
			this.maxLines = maxLines;
		}
		
		public void setInputType(int inputType) {
			this.inputType = inputType;
		}
		
		public String getParentId() {
			return parentId;
		}
		
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		
		public List<ViewBean> getChildren() {
			return children;
		}
		
		public void setChildren(List<ViewBean> children) {
			this.children = children;
		}
		
		// Parcelable implementation
		protected ViewBean(Parcel in) {
			widgetType = in.readString();
			widgetId = in.readString();
			width = in.readInt();
			height = in.readInt();
			translationX = in.readFloat();
			translationY = in.readFloat();
			backgroundColor = in.readInt();
			text = in.readString();
			textSize = in.readFloat();
			textColor = in.readInt();
			imagePath = in.readString();
			marginLeft = in.readInt();
			marginTop = in.readInt();
			marginRight = in.readInt();
			marginBottom = in.readInt();
			paddingLeft = in.readInt();
			paddingTop = in.readInt();
			paddingRight = in.readInt();
			paddingBottom = in.readInt();
			visibility = in.readInt();
			alpha = in.readFloat();
			rotation = in.readFloat();
			scaleX = in.readFloat();
			scaleY = in.readFloat();
			textStyle = in.readString();
			fontFamily = in.readString();
			gravity = in.readInt();
			textAlignment = in.readInt();
			lineSpacingMultiplier = in.readFloat();
			lineSpacingExtra = in.readFloat();
			maxLines = in.readInt();
			singleLine = in.readByte() != 0;
			hint = in.readString();
			inputType = in.readInt();
			checked = in.readByte() != 0;
			scaleType = in.readString();
			lines = in.readInt();
			elevation = in.readFloat();
			hint = in.readString();
			progress = in.readInt();
			maxProgress = in.readInt();
			progressType = in.readString();
			parentId = in.readString();
			children = in.createTypedArrayList(ViewBean.CREATOR);
		}
		
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(widgetType);
			dest.writeString(widgetId);
			dest.writeInt(width);
			dest.writeInt(height);
			dest.writeFloat(translationX);
			dest.writeFloat(translationY);
			dest.writeInt(backgroundColor);
			dest.writeString(text);
			dest.writeFloat(textSize);
			dest.writeInt(textColor);
			dest.writeString(imagePath);
			dest.writeInt(marginLeft);
			dest.writeInt(marginTop);
			dest.writeInt(marginRight);
			dest.writeInt(marginBottom);
			dest.writeInt(paddingLeft);
			dest.writeInt(paddingTop);
			dest.writeInt(paddingRight);
			dest.writeInt(paddingBottom);
			dest.writeInt(visibility);
			dest.writeFloat(alpha);
			dest.writeFloat(rotation);
			dest.writeFloat(scaleX);
			dest.writeFloat(scaleY);
			dest.writeString(textStyle);
			dest.writeString(fontFamily);
			dest.writeInt(gravity);
			dest.writeInt(textAlignment);
			dest.writeFloat(lineSpacingMultiplier);
			dest.writeFloat(lineSpacingExtra);
			dest.writeInt(maxLines);
			dest.writeByte((byte) (singleLine ? 1 : 0));
			dest.writeString(hint);
			dest.writeInt(inputType);
			dest.writeByte((byte) (checked ? 1 : 0));
			dest.writeString(scaleType);
			dest.writeInt(lines);
			dest.writeFloat(elevation);
			dest.writeString(hint);
			dest.writeInt(progress);
			dest.writeInt(maxProgress);
			dest.writeString(progressType);
			dest.writeString(parentId);
			dest.writeTypedList(children);
		}
		
		@Override
		public int describeContents() {
			return 0;
		}
		
		public static final Creator<ViewBean> CREATOR = new Creator<ViewBean>() {
			@Override
			public ViewBean createFromParcel(Parcel in) {
				return new ViewBean(in);
			}
			
			@Override
			public ViewBean[] newArray(int size) {
				return new ViewBean[size];
			}
		};
	}
}
