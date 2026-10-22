package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexusteam.blacklogics.FileUtil;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.project.ProjectDataHelper;
import com.nexusteam.blacklogics.sdk.AttributeSet;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;
import com.nexusteam.blacklogics.sdk.utils.DimensionUtils;
import com.nexusteam.blacklogics.sdk.utils.ReflectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AttributeApplier {

	public interface MessageSink {
		void showMessage(String msg);
	}

	public interface WidgetConversionHandler {
		void handleWidgetConversion(View view, String value);
		String getStringResourceValue(String scId, String resourceName);
		Object createObject(Class<?> cls);
		ViewGroup.LayoutParams createCompatibleLayoutParams(View view, int w, int h);
		int parseDimension(String value);
	}

	private final Context context;
	private final AttributeStore attributeStore;
	private final MessageSink messageSink;
	private final WidgetConversionHandler conversionHandler;
	private final ExtraAttributeApplier extra;

	public AttributeApplier(Context context, AttributeStore attributeStore,
	MessageSink messageSink, WidgetConversionHandler conversionHandler) {
		this.context = context;
		this.attributeStore = attributeStore;
		this.messageSink = messageSink;
		this.conversionHandler = conversionHandler;
		this.extra = new ExtraAttributeApplier(context, this);
	}

	public void applyAttribute(View view, Attribute attribute) {
		if (view == null || attribute == null) return;

		String n = attribute.getName();
		if ("app:customView".equals(n) || "customView".equals(n)) {
			storeAttributeOnly(view, attribute);
			return;
		}

		AttributeSet currentAttrSet = attributeStore.get(view);

		// ConstraintLayout children: app:layout_constraint* -> ConstraintLayout.LayoutParams
		if (ConstraintLayoutHelper.applyOne(view, n, attribute.getValue())) {
			if (currentAttrSet == null) {
				currentAttrSet = new AttributeSet();
				attributeStore.put(view, currentAttrSet);
			}
			currentAttrSet.add(attribute);
			return;
		}

		// @drawable / @mipmap for background, foreground, src, drawableX, icon ...
		// (project files from BOTH image folders; png/jpg/webp/gif + xml shape/vector/layer-list)
		String drawableVal = attribute.getValue();
		if (extra.applyDrawableRef(view, n, drawableVal)) {
			if (currentAttrSet == null) {
				currentAttrSet = new AttributeSet();
				attributeStore.put(view, currentAttrSet);
			}
			currentAttrSet.add(attribute);
			return;
		}
		// Missing drawable must NOT be parsed as a colour (that painted views black).
		if (drawableVal != null
				&& (drawableVal.startsWith("@drawable/") || drawableVal.startsWith("@mipmap/"))
				&& ("android:background".equals(n) || "app:background".equals(n)
						|| "android:foreground".equals(n))) {
			if (currentAttrSet == null) {
				currentAttrSet = new AttributeSet();
				attributeStore.put(view, currentAttrSet);
			}
			currentAttrSet.add(attribute);
			return;
		}

		ArrayList<HashMap<String, Object>> listMap =
		attributeStore.findAttributesListFor(view.getClass());

		String attrName = attribute.getName();
		boolean found = false;
		for (int i = 0; i < listMap.size(); i++) {
			HashMap<String, Object> map = listMap.get(i);
			if (attrName.equals(map.get("attribute_name"))) {
				applyAttribute(view, attribute, map);
				found = true;
				break;
			}
		}

		if (!found) {
			applyCommonAttribute(view, attribute);
			// attributes with no BuiltInAttributes entry (cardCornerRadius, fontFamily, tint ...)
			extra.apply(view, attrName, attribute.getValue());
		}

		if (currentAttrSet == null) {
			currentAttrSet = new AttributeSet();
			attributeStore.put(view, currentAttrSet);
		}
		currentAttrSet.add(attribute);
	}

	public void applyAttributeInternal(View view, Attribute attribute,
	HashMap<String, Object> map) {
		applyAttribute(view, attribute, map);
	}

	@SuppressWarnings("unchecked")
	public void applyAttribute(View view, Attribute attribute, HashMap<String, Object> map) {
		if (view == null || attribute == null || map == null) {
			applyCommonAttribute(view, attribute);
			return;
		}
		final String attributeName = attribute.getName();
		String value = attribute.getValue();

		Object rawArgType = map.get("argument_type");
		String argumentType = rawArgType != null ? rawArgType.toString() : "";
		if ("customView".equals(argumentType)
				|| "app:customView".equals(attributeName)
				|| "customView".equals(attributeName)) {
			storeAttributeOnly(view, attribute);
			return;
		}

		Object argumentValue = null;
		Object rawMember = map.get("member_name");
		if (rawMember == null) {
			storeAttributeOnly(view, attribute);
			return;
		}
		String memberName = rawMember.toString();

		if ("app:convertedWidget".equals(attributeName)) {
			conversionHandler.handleWidgetConversion(view, value);
			return;
		}
		if (attributeName.startsWith("android:layout_margin")) {
			applyMarginAttribute(view, attributeName, value);
			return;
		}
		if (attributeName.startsWith("android:padding")) {
			applyPaddingAttribute(view, attributeName, value);
			return;
		}
		if ("android:layout_width".equals(attributeName)
		|| "android:layout_height".equals(attributeName)) {
			applyWidthHeightAttribute(view, attributeName, value);
		}
		if ("android:layout_weight".equals(attributeName)) {
			applyWeightAttribute(view, value);
			return;
		}

		try {
			Object targetObj = view;
			if (map.containsKey("layout_params")
			&& Boolean.TRUE.equals(map.get("layout_params"))) {
				targetObj = view.getLayoutParams();
			}

			if ("boolean".equals(argumentType)) {
				argumentValue = "true".equals(value);

			} else if ("int".equals(argumentType)) {
				argumentValue = map.containsKey("dimension")
				? (int) DimensionUtils.getValueInPx(context, value)
				: Integer.parseInt(value);

			} else if ("float".equals(argumentType)) {
				if ("android:textSize".equals(attributeName)
				&& value != null && !value.isEmpty()) {
					applyTextSize(view, value);
					return;
				}
				try {
					if (map.containsKey("dimension")) {
						String trimmed = value.trim();
						if (trimmed.isEmpty() || "0".equals(trimmed)) {
							argumentValue = 0f;
						} else {
							if (trimmed.matches("-?\\d+(\\.\\d+)?"))
							trimmed = trimmed + "dp";
							argumentValue = (float) DimensionUtils.getValueInPx(
							context, trimmed);
						}
					} else {
						argumentValue = Float.parseFloat(value);
					}
				} catch (Exception e) {
					messageSink.showMessage("Invalid float value: " + value);
					return;
				}

			} else if ("String".equals(argumentType)) {
				if ("android:text".equals(attributeName)
				&& value.startsWith("@string/")) {
					String resName = value.substring("@string/".length());
					String resolved = conversionHandler.getStringResourceValue(
					DesignActivity.getScId(), resName);
					argumentValue = (resolved != null) ? resolved : value;
				} else {
					argumentValue = value;
				}

			} else if ("Size".equals(argumentType)) {
				if ("match_parent".equals(value))
				argumentValue = ViewGroup.LayoutParams.MATCH_PARENT;
				else if ("wrap_content".equals(value))
				argumentValue = ViewGroup.LayoutParams.WRAP_CONTENT;
				else
				argumentValue = (int) DimensionUtils.getValueInPx(context, value);

			} else if ("Color".equals(argumentType) || "color".equals(argumentType)) {
				int colorInt = parseColorValue(value);
				if (memberName != null && (
						memberName.contains("TintList")
						|| "setRippleColor".equals(memberName)
						|| "setBackgroundTintList".equals(memberName)
						|| "setImageTintList".equals(memberName))) {
					argumentValue = android.content.res.ColorStateList.valueOf(colorInt);
				} else {
					argumentValue = colorInt;
				}

			} else if ("dimension".equals(argumentType)) {
				String trimmed = value != null ? value.trim() : "0";
				if (trimmed.isEmpty()) trimmed = "0";
				if (trimmed.matches("-?\\d+(\\.\\d+)?")) trimmed = trimmed + "dp";
				argumentValue = (int) DimensionUtils.getValueInPx(context, trimmed);

			} else if ("image".equals(argumentType)) {
				boolean applied = false;
				if (value != null && value.startsWith("@android:drawable/")) {
					String resName = value.substring("@android:drawable/".length()).trim();
					int resId = context.getResources().getIdentifier(resName, "drawable", "android");
					if (resId != 0) {
						ReflectionUtils.invokeMethod(view, "setImageResource", Integer.valueOf(resId));
						applied = true;
					}
				} else if (value != null && value.startsWith("@drawable/")) {
					String resName = value.substring("@drawable/".length()).trim();
					int resId = context.getResources().getIdentifier(
							resName, "drawable", context.getPackageName());
					if (resId != 0) {
						ReflectionUtils.invokeMethod(view, "setImageResource", Integer.valueOf(resId));
						applied = true;
					}
				}
				if (!applied) {
					String scId = null;
					try { scId = ProjectDataHelper.getScId(context); } catch (Throwable ignore) {}
					if (scId == null || scId.isEmpty()) {
						try { scId = DesignActivity.getScId(); } catch (Throwable ignore) {}
					}
					String name = AttributeUtils.getImageName(value);
					String imgPath = "/storage/emulated/0/.blacklogics/data/"
							+ scId + "/files/resource/drawable/" + name + ".png";
					File f = new File(imgPath);
					if (!f.exists()) {
						imgPath = FileUtil.getExternalStorageDir()
								+ "/.blacklogics/resources/images/" + scId + "/" + name + ".png";
						f = new File(imgPath);
					}
					if (f.exists()) {
						Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
						if (bmp != null) {
							android.graphics.drawable.Drawable d =
									new android.graphics.drawable.BitmapDrawable(context.getResources(), bmp);
							ReflectionUtils.invokeMethod(view, "setImageDrawable", d);
							applied = true;
						}
					}
				}
				storeAttributeOnly(view, attribute);
				return;

			} else if ("Drawable".equals(argumentType)) {
				// Prefer android / app drawable ids; never leave null for setImageDrawable
				boolean drawableApplied = false;
				if (value != null && value.startsWith("@android:drawable/")) {
					String resName = value.substring("@android:drawable/".length()).trim();
					int resId = context.getResources().getIdentifier(resName, "drawable", "android");
					if (resId != 0) {
						try {
							ReflectionUtils.invokeMethod(view, "setImageResource", Integer.valueOf(resId));
							drawableApplied = true;
						} catch (Throwable t1) {
							try {
								android.graphics.drawable.Drawable d =
										context.getResources().getDrawable(resId);
								ReflectionUtils.invokeMethod(view, "setImageDrawable", d);
								drawableApplied = true;
							} catch (Throwable ignore) {}
						}
					}
				} else if (value != null && (value.startsWith("@drawable/") || value.startsWith("@mipmap/"))) {
					boolean mip = value.startsWith("@mipmap/");
					String resName = value.substring(mip ? "@mipmap/".length() : "@drawable/".length()).trim();
					int resId = context.getResources().getIdentifier(
							resName, mip ? "mipmap" : "drawable", context.getPackageName());
					if (resId != 0) {
						try {
							ReflectionUtils.invokeMethod(view, "setImageResource", Integer.valueOf(resId));
							drawableApplied = true;
						} catch (Throwable ignore) {}
					}
				}
				if (!drawableApplied) {
					String scId = null;
					try { scId = ProjectDataHelper.getScId(context); } catch (Throwable ignore) {}
					if (scId == null || scId.isEmpty()) {
						try { scId = DesignActivity.getScId(); } catch (Throwable ignore) {}
					}
					String name = null;
					try { name = AttributeUtils.getImageName(value); } catch (Throwable ignore) {}
					if (name == null || name.trim().isEmpty()) name = value;
					if (name != null && scId != null) {
						String imgPath = "/storage/emulated/0/.blacklogics/data/"
								+ scId + "/files/resource/drawable/" + name + ".png";
						File f = new File(imgPath);
						if (!f.exists()) {
							imgPath = "/storage/emulated/0/.blacklogics/data/"
									+ scId + "/files/resource/drawable/" + name;
							f = new File(imgPath);
						}
						if (!f.exists()) {
							imgPath = FileUtil.getExternalStorageDir()
									+ "/.blacklogics/resources/images/"
									+ scId + "/" + name + ".png";
							f = new File(imgPath);
						}
						if (f.exists()) {
							Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
							if (bmp != null) {
								argumentValue = new android.graphics.drawable.BitmapDrawable(
										context.getResources(), bmp);
								drawableApplied = true;
							}
						}
					}
				}
				if (drawableApplied && argumentValue == null) {
					// already applied via setImageResource — skip reflection invoke
					storeAttributeOnly(view, attribute);
					return;
				}
				if (!drawableApplied) {
					Log.w("AttributeApplier", "Drawable not found for: " + value);
					storeAttributeOnly(view, attribute);
					return;
				}

			} else if ("enum".equals(argumentType)) {
				ArrayList<String> xmlArgs = (ArrayList<String>) map.get("xml_arguments");
				ArrayList<String> javaArgs = (ArrayList<String>) map.get("java_arguments");
				if (xmlArgs == null) xmlArgs = new ArrayList<String>();
				if (javaArgs == null) javaArgs = new ArrayList<String>();
				int idx = xmlArgs.indexOf(value);
				if (idx == -1) {
					idx = javaArgs.indexOf(value);
				}
				if (idx == -1) {
					Log.w("AttributeApplier", "Unknown enum value \"" + value
					+ "\" for " + attributeName + ", falling back to default");
					idx = 0;
				}
				if (xmlArgs.isEmpty() || javaArgs.isEmpty()) {
					messageSink.showMessage("No options defined for \"" + attributeName + "\"");
					return;
				}
				if (idx >= javaArgs.size()) idx = 0;
				String path = javaArgs.get(idx);
				if (path != null && path.matches("-?\\d+")) {
					argumentValue = Integer.valueOf(path);
				} else {
					try {
						argumentValue = conversionHandler.createObject(Class.forName(path));
					} catch (ClassNotFoundException e) {
						argumentValue = ReflectionUtils.getStaticField(path).get(null);
					}
				}

			} else if ("flag".equals(argumentType)) {
				List<String> selected = Arrays.asList(value.split("\\|"));
				ArrayList<String> xmlA = (ArrayList<String>) map.get("xml_arguments");
				ArrayList<String> javaA = (ArrayList<String>) map.get("java_arguments");
				int flagValue = 0;
				for (int i = 0; i < selected.size(); i++) {
					String s = selected.get(i);
					int fi = xmlA.indexOf(s);
					if (fi == -1) {
						Log.w("AttributeApplier", "Unknown flag value \"" + s
						+ "\" for " + attributeName + ", skipping");
						continue;
					}
					try {
						String fieldPath = javaA.get(fi);
						flagValue |= (int) ReflectionUtils.getStaticFieldValue(fieldPath);
					} catch (Exception ignored) {}
				}
				argumentValue = flagValue;

			} else if ("customView".equals(argumentType)) {
				storeAttributeOnly(view, attribute);
				return;

			} else {
				messageSink.showMessage("Unknown attribute type: " + argumentType);
				return;
			}

			if (map.containsKey("member_type")) {
				String memberType = map.get("member_type").toString();
				if ("method".equals(memberType)) {
					if (argumentValue == null
							&& memberName != null
							&& (memberName.contains("Drawable")
								|| memberName.contains("Image")
								|| "setImageResource".equals(memberName))) {
						// skip null drawable/image invoke — already handled or missing resource
						storeAttributeOnly(view, attribute);
						return;
					}
					if (map.containsKey("constant")) {
						String constantPath = map.get("constant").toString();
						Object constant;
						try {
							constant = conversionHandler.createObject(Class.forName(constantPath));
						} catch (ClassNotFoundException e) {
							constant = ReflectionUtils.getStaticFieldValue(constantPath);
						}
						ReflectionUtils.invokeMethod(targetObj, memberName,
						constant, argumentValue);
					} else {
						ReflectionUtils.invokeMethod(targetObj, memberName, argumentValue);
					}
				} else {
					ReflectionUtils.setField(targetObj, memberName, argumentValue);
					view.requestLayout();
				}
			}

		} catch (Exception e) {
			messageSink.showMessage("Attribute apply error \"" + attributeName + "\": " + e.getMessage());
			Log.e("AttributeApplier", "applyAttribute error for " + attributeName, e);
		}
	}

	public void applyMarginAttribute(View view, String attrName, String value) {
		try {
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (!(params instanceof ViewGroup.MarginLayoutParams)) return;
			ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) params;
			int px = (int) DimensionUtils.getValueInPx(context, value);

			if ("android:layout_margin".equals(attrName)) {
				mp.leftMargin = mp.topMargin = mp.rightMargin = mp.bottomMargin = px;
			} else if ("android:layout_marginLeft".equals(attrName)) { mp.leftMargin = px; }
			else if ("android:layout_marginTop".equals(attrName)) { mp.topMargin = px; }
			else if ("android:layout_marginRight".equals(attrName)) { mp.rightMargin = px; }
			else if ("android:layout_marginBottom".equals(attrName)) { mp.bottomMargin = px; }
			else if ("android:layout_marginStart".equals(attrName)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
				mp.setMarginStart(px);
			} else if ("android:layout_marginEnd".equals(attrName)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
				mp.setMarginEnd(px);
			} else if ("android:layout_marginHorizontal".equals(attrName)) {
				mp.leftMargin = mp.rightMargin = px;
			} else if ("android:layout_marginVertical".equals(attrName)) {
				mp.topMargin = mp.bottomMargin = px;
			}

			view.setLayoutParams(mp);
			view.requestLayout();
		} catch (Exception e) {
			Log.e("AttributeApplier", "applyMarginAttribute error: " + attrName, e);
		}
	}

	public void applyPaddingAttribute(View view, String attrName, String value) {
		try {
			int l = view.getPaddingLeft(), t = view.getPaddingTop(),
			r = view.getPaddingRight(), b = view.getPaddingBottom();
			int px = (int) DimensionUtils.getValueInPx(context, value);

			if ("android:padding".equals(attrName)) { l = t = r = b = px; }
			else if ("android:paddingLeft".equals(attrName)) { l = px; }
			else if ("android:paddingTop".equals(attrName)) { t = px; }
			else if ("android:paddingRight".equals(attrName)) { r = px; }
			else if ("android:paddingBottom".equals(attrName)) { b = px; }
			else if ("android:paddingStart".equals(attrName)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) l = px;
			} else if ("android:paddingEnd".equals(attrName)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) r = px;
			} else if ("android:paddingHorizontal".equals(attrName)) { l = r = px; }
			else if ("android:paddingVertical".equals(attrName)) { t = b = px; }

			view.setPadding(l, t, r, b);
			view.requestLayout();
		} catch (Exception e) {
			Log.e("AttributeApplier", "applyPaddingAttribute error: " + attrName, e);
		}
	}

	public void applyWidthHeightAttribute(View view, String attrName, String value) {
		if (view == null) return;
		ViewGroup.LayoutParams params = view.getLayoutParams();
		int curW = params != null ? params.width : ViewGroup.LayoutParams.WRAP_CONTENT;
		int curH = params != null ? params.height : ViewGroup.LayoutParams.WRAP_CONTENT;
		int px = conversionHandler.parseDimension(value);
		if ("android:layout_width".equals(attrName)) curW = px;
		else curH = px;

		ViewGroup.LayoutParams compatible =
				conversionHandler.createCompatibleLayoutParams(view, curW, curH);
		if (compatible instanceof ViewGroup.MarginLayoutParams
				&& params instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams from = (ViewGroup.MarginLayoutParams) params;
			ViewGroup.MarginLayoutParams to = (ViewGroup.MarginLayoutParams) compatible;
			to.leftMargin = from.leftMargin;
			to.topMargin = from.topMargin;
			to.rightMargin = from.rightMargin;
			to.bottomMargin = from.bottomMargin;
		}
		if (compatible instanceof LinearLayout.LayoutParams
				&& params instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams from = (LinearLayout.LayoutParams) params;
			LinearLayout.LayoutParams to = (LinearLayout.LayoutParams) compatible;
			to.weight = from.weight;
			to.gravity = from.gravity;
		}
		view.setLayoutParams(compatible);
		view.requestLayout();
		if (view.getParent() instanceof View) {
			((View) view.getParent()).requestLayout();
		}
	}

	public void applyWeightAttribute(View view, String value) {
		ViewGroup.LayoutParams params = view.getLayoutParams();
		if (!(params instanceof LinearLayout.LayoutParams)) return;
		try {
			float weight = Float.parseFloat(value.trim());
			((LinearLayout.LayoutParams) params).weight = weight;
			view.setLayoutParams(params);
			view.requestLayout();
		} catch (NumberFormatException e) {
			Log.e("AttributeApplier", "Invalid layout_weight: " + value, e);
		}
	}

	public void applyTextSize(View view, String value) {
		try {
			if (!(view instanceof TextView)) return;
			TextView tv = (TextView) view;
			if (value.endsWith("sp")) {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,
				Float.parseFloat(value.replace("sp","").trim()));
			} else if (value.endsWith("dp")) {
				tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,
				Float.parseFloat(value.replace("dp","").trim()));
			} else {
				tv.setTextSize(Float.parseFloat(value.trim()));
			}
		} catch (Exception e) {
			messageSink.showMessage("Invalid textSize: " + value);
		}
	}

	private void storeAttributeOnly(View view, Attribute attribute) {
		if (view == null || attribute == null) return;
		AttributeSet set = attributeStore.get(view);
		if (set == null) {
			set = new AttributeSet();
			attributeStore.put(view, set);
		}
		String name = attribute.getName();
		String val = attribute.getValue();
		if (val == null) val = "";
		if ("none".equalsIgnoreCase(val.trim())) val = "";
		if (val.trim().isEmpty()) {
			try { set.remove(name); } catch (Throwable ignore) {}
			try { set.remove("app:customView"); } catch (Throwable ignore) {}
			try { set.remove("customView"); } catch (Throwable ignore) {}
			return;
		}
		set.add(new Attribute(name, val.trim()));
	}

	public void applyCommonAttribute(View view, Attribute attribute) {
		if (attribute == null) return;
		String name = attribute.getName();
		String value = attribute.getValue();
		if (value == null || value.isEmpty()) return;
		if (name.startsWith("android:layout_margin")) {
			applyMarginAttribute(view, name, value);
		} else if (name.startsWith("android:padding")) {
			applyPaddingAttribute(view, name, value);
		} else if ("android:layout_width".equals(name)
		|| "android:layout_height".equals(name)) {
			applyWidthHeightAttribute(view, name, value);
		} else if ("android:layout_weight".equals(name)) {
			applyWeightAttribute(view, value);
		}
	}

	public int parseColorValue(String value) {
		if (value == null || value.trim().isEmpty()) {
			return Color.BLACK;
		}
		String trimmed = value.trim();

		if (trimmed.startsWith("@color/")) {
			String resName = trimmed.substring("@color/".length()).trim();
			String scId = null;
			try {
				scId = ProjectDataHelper.getScId(context);
			} catch (Throwable ignore) {}
			if (scId == null || scId.isEmpty()) {
				try {
					scId = DesignActivity.getScId();
				} catch (Throwable ignore) {}
			}
			if (scId != null && !scId.isEmpty()) {
				return ColorResourceReader.read(scId, resName);
			}
			Log.w("AttributeApplier", "No scId available to resolve " + trimmed);
			return Color.BLACK;
		}

		try {
			if (trimmed.matches("-?\\d+")) {
				return (int) Long.parseLong(trimmed);
			}
			return Color.parseColor(trimmed);
		} catch (Exception e) {
			Log.w("AttributeApplier", "Unrecognized color value \"" + value
			+ "\", falling back to black");
			return Color.BLACK;
		}
	}
}

