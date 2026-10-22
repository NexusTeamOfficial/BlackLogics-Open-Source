package com.shapun.layouteditor;

import com.besome.blacklogics.beans.ProjectActivityBean;
import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.view.View;
import android.text.InputType;

/**
 * A standalone class to generate Android XML layout code from ProjectActivityBean.ViewBean attributes.
 * This class is independent of ViewEditor and focuses solely on XML generation, mapping attributes
 * similar to ViewEditor's applyAttribute method.
 */
public class XmlLayoutGenerator {

    /**
     * Generates XML layout code from the ViewBeans in a ProjectActivityBean.
     * @param activityBean The ProjectActivityBean containing the ViewBean list.
     * @return A formatted XML string representing the layout.
     */
    public String generate(ProjectActivityBean activityBean) {
        if (activityBean == null) {
            return "";
        }

        List<ProjectActivityBean.ViewBean> viewBeans = activityBean.getWidgets();
        if (viewBeans == null || viewBeans.isEmpty()) {
            return "";
        }

        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");


        String rootWidgetType = getQualifiedWidgetType("LinearLayout", activityBean.isUseAndroidX());
        xmlBuilder.append("<").append(rootWidgetType).append("\n");
        xmlBuilder.append("    xmlns:android=\"http://schemas.android.com/apk/res/android\"\n");
        xmlBuilder.append("    android:layout_width=\"match_parent\"\n");
        xmlBuilder.append("    android:layout_height=\"match_parent\"\n");
        xmlBuilder.append("    android:orientation=\"vertical\">\n");


        List<ProjectActivityBean.ViewBean> rootBeans = new ArrayList<>();
        for (ProjectActivityBean.ViewBean bean : viewBeans) {
            if (bean.getParentId() == null || bean.getParentId().isEmpty()) {
                rootBeans.add(bean);
            }
        }


        for (ProjectActivityBean.ViewBean rootBean : rootBeans) {
            appendViewBeanXml(rootBean, viewBeans, xmlBuilder, 1, activityBean.isUseAndroidX());
        }


        xmlBuilder.append("</").append(rootWidgetType).append(">\n");

        return xmlBuilder.toString();
    }

    /**
     * Recursively generates XML for a ViewBean and its children.
     * @param bean The ViewBean to process.
     * @param allBeans List of all ViewBeans for looking up children.
     * @param xmlBuilder StringBuilder to append XML to.
     * @param indentLevel Current indentation level.
     * @param useAndroidX Whether to use AndroidX namespaces.
     */
    private void appendViewBeanXml(ProjectActivityBean.ViewBean bean, List<ProjectActivityBean.ViewBean> allBeans, StringBuilder xmlBuilder, int indentLevel, boolean useAndroidX) {
        String indent = getIndent(indentLevel);
        String widgetType = getQualifiedWidgetType(bean.getWidgetType(), useAndroidX);
        String widgetId = bean.getWidgetId();


        xmlBuilder.append(indent).append("<").append(widgetType).append("\n");


        appendAttribute(xmlBuilder, indent, "android:id", "@+id/" + widgetId);
        appendLayoutParams(xmlBuilder, indent, bean);
        appendCommonAttributes(xmlBuilder, indent, bean);


        appendTypeSpecificAttributes(xmlBuilder, indent, bean);


        List<ProjectActivityBean.ViewBean> children = bean.getChildren();
        if (children == null) {
            children = new ArrayList<>();
        }

        for (ProjectActivityBean.ViewBean childBean : allBeans) {
            if (widgetId.equals(childBean.getParentId()) && !children.contains(childBean)) {
                children.add(childBean);
            }
        }

        if (children.isEmpty()) {
            xmlBuilder.append(indent).append("/>\n");
        } else {
            xmlBuilder.append(indent).append(">\n");
            for (ProjectActivityBean.ViewBean child : children) {
                appendViewBeanXml(child, allBeans, xmlBuilder, indentLevel + 1, useAndroidX);
            }
            xmlBuilder.append(indent).append("</").append(widgetType).append(">\n");
        }
    }

    /**
     * Appends layout parameters (width, height, margins, padding).
     */
    private void appendLayoutParams(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        appendDimensionAttribute(xmlBuilder, indent, "android:layout_width", bean.getWidth());
        appendDimensionAttribute(xmlBuilder, indent, "android:layout_height", bean.getHeight());
        if (bean.getMarginLeft() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:layout_marginLeft", bean.getMarginLeft());
        }
        if (bean.getMarginTop() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:layout_marginTop", bean.getMarginTop());
        }
        if (bean.getMarginRight() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:layout_marginRight", bean.getMarginRight());
        }
        if (bean.getMarginBottom() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:layout_marginBottom", bean.getMarginBottom());
        }
        if (bean.getPaddingLeft() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:paddingLeft", bean.getPaddingLeft());
        }
        if (bean.getPaddingTop() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:paddingTop", bean.getPaddingTop());
        }
        if (bean.getPaddingRight() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:paddingRight", bean.getPaddingRight());
        }
        if (bean.getPaddingBottom() != 0) {
            appendDimensionAttribute(xmlBuilder, indent, "android:paddingBottom", bean.getPaddingBottom());
        }
    }

    /**
     * Appends common attributes applicable to all View types.
     */
    private void appendCommonAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        if (bean.getBackgroundColor() != 0) {
            appendAttribute(xmlBuilder, indent, "android:background", formatColor(bean.getBackgroundColor()));
        }
        if (bean.getVisibility() != View.VISIBLE) {
            String visibility = getVisibilityString(bean.getVisibility());
            appendAttribute(xmlBuilder, indent, "android:visibility", visibility);
        }
        if (bean.getAlpha() != 1.0f) {
            appendAttribute(xmlBuilder, indent, "android:alpha", String.valueOf(bean.getAlpha()));
        }
        if (bean.getRotation() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:rotation", String.valueOf(bean.getRotation()));
        }
        if (bean.getScaleX() != 1.0f) {
            appendAttribute(xmlBuilder, indent, "android:scaleX", String.valueOf(bean.getScaleX()));
        }
        if (bean.getScaleY() != 1.0f) {
            appendAttribute(xmlBuilder, indent, "android:scaleY", String.valueOf(bean.getScaleY()));
        }
        if (bean.getElevation() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:elevation", bean.getElevation() + "dp");
        }
        if (bean.getTranslationX() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:translationX", bean.getTranslationX() + "dp");
        }
        if (bean.getTranslationY() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:translationY", bean.getTranslationY() + "dp");
        }
    }

    /**
     * Appends type-specific attributes based on the widget type.
     */
    private void appendTypeSpecificAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        String widgetType = bean.getWidgetType();
        if (widgetType.equals("TextView") || widgetType.equals("Button") || widgetType.equals("EditText")) {
            appendTextViewAttributes(xmlBuilder, indent, bean);
        } else if (widgetType.equals("ImageView")) {
            appendImageViewAttributes(xmlBuilder, indent, bean);
        } else if (widgetType.equals("ProgressBar") || widgetType.equals("SeekBar")) {
            appendProgressBarAttributes(xmlBuilder, indent, bean);
        } else if (widgetType.equals("CheckBox") || widgetType.equals("Switch")) {
            appendCompoundButtonAttributes(xmlBuilder, indent, bean);
        }
    }

    /**
     * Appends attributes specific to TextView, Button, and EditText.
     */
    private void appendTextViewAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        if (bean.getText() != null && !bean.getText().isEmpty()) {
            appendAttribute(xmlBuilder, indent, "android:text", bean.getText());
        }
        if (bean.getTextSize() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:textSize", bean.getTextSize() + "sp");
        }
        if (bean.getTextColor() != 0) {
            appendAttribute(xmlBuilder, indent, "android:textColor", formatColor(bean.getTextColor()));
        }
        if (bean.getGravity() != 0) {
            String gravity = formatGravity(bean.getGravity());
            appendAttribute(xmlBuilder, indent, "android:gravity", gravity);
        }
        if (bean.getTextAlignment() != 0) {
            String alignment = getTextAlignmentString(bean.getTextAlignment());
            appendAttribute(xmlBuilder, indent, "android:textAlignment", alignment);
        }
        if (bean.getTextStyle() != null && !bean.getTextStyle().isEmpty()) {
            appendAttribute(xmlBuilder, indent, "android:textStyle", bean.getTextStyle());
        }
        if (bean.getFontFamily() != null && !bean.getFontFamily().isEmpty()) {
            appendAttribute(xmlBuilder, indent, "android:fontFamily", bean.getFontFamily());
        }
        if (bean.getMaxLines() != Integer.MAX_VALUE) {
            appendAttribute(xmlBuilder, indent, "android:maxLines", String.valueOf(bean.getMaxLines()));
        }
        if (bean.getLineSpacingMultiplier() != 1.0f) {
            appendAttribute(xmlBuilder, indent, "android:lineSpacingMultiplier", String.valueOf(bean.getLineSpacingMultiplier()));
        }
        if (bean.getLineSpacingExtra() != 0f) {
            appendAttribute(xmlBuilder, indent, "android:lineSpacingExtra", bean.getLineSpacingExtra() + "sp");
        }
        if (bean.isSingleLine()) {
            appendAttribute(xmlBuilder, indent, "android:singleLine", "true");
        }
        if (bean.getWidgetType().equals("EditText")) {
            if (bean.getHint() != null && !bean.getHint().isEmpty()) {
                appendAttribute(xmlBuilder, indent, "android:hint", bean.getHint());
            }
            if (bean.getInputType() != 0) {
                String inputType = formatInputType(bean.getInputType());
                appendAttribute(xmlBuilder, indent, "android:inputType", inputType);
            }
        }
    }

    /**
     * Appends attributes specific to ImageView.
     */
    private void appendImageViewAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        if (bean.getImagePath() != null && !bean.getImagePath().isEmpty()) {
            appendAttribute(xmlBuilder, indent, "android:src", "@drawable/" + bean.getImagePath());
        }
        if (bean.getScaleType() != null && !bean.getScaleType().isEmpty() && !bean.getScaleType().equals("FIT_CENTER")) {
            appendAttribute(xmlBuilder, indent, "android:scaleType", bean.getScaleType().toLowerCase());
        }
    }

    /**
     * Appends attributes specific to ProgressBar and SeekBar.
     */
    private void appendProgressBarAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        if (bean.getProgress() != 0) {
            appendAttribute(xmlBuilder, indent, "android:progress", String.valueOf(bean.getProgress()));
        }
        if (bean.getMaxProgress() != 0) {
            appendAttribute(xmlBuilder, indent, "android:max", String.valueOf(bean.getMaxProgress()));
        }
        if (bean.getProgressType() != null && !bean.getProgressType().isEmpty() && bean.getWidgetType().equals("ProgressBar")) {
            String style = bean.getProgressType().equals("HORIZONTAL") ? "?android:attr/progressBarStyleHorizontal" : "?android:attr/progressBarStyle";
            appendAttribute(xmlBuilder, indent, "style", style);
        }
    }

    /**
     * Appends attributes specific to CheckBox and Switch.
     */
    private void appendCompoundButtonAttributes(StringBuilder xmlBuilder, String indent, ProjectActivityBean.ViewBean bean) {
        if (bean.isChecked()) {
            appendAttribute(xmlBuilder, indent, "android:checked", "true");
        }
        if (bean.getText() != null && !bean.getText().isEmpty()) {
            appendAttribute(xmlBuilder, indent, "android:text", bean.getText());
        }
    }

    /**
     * Appends a single attribute to the XML, handling indentation.
     */
    private void appendAttribute(StringBuilder xmlBuilder, String indent, String name, String value) {
        if (value != null && !value.isEmpty()) {
            xmlBuilder.append(indent).append("    ").append(name).append("=\"").append(escapeXml(value)).append("\"\n");
        }
    }

    /**
     * Appends a dimension attribute (width, height, margins, padding) in dp or match_parent/wrap_content.
     */
    private void appendDimensionAttribute(StringBuilder xmlBuilder, String indent, String name, int value) {
        String dimension;
        if (value == -1) {
            dimension = "match_parent";
        } else if (value == -2) {
            dimension = "wrap_content";
        } else {
            dimension = value + "dp";
        }
        appendAttribute(xmlBuilder, indent, name, dimension);
    }

    /**
     * Formats a color value as a hex string (e.g., #FF000000).
     */
    private String formatColor(int color) {
        return String.format("#%08X", (0xFFFFFFFF & color));
    }

    /**
     * Formats gravity value as a string (e.g., "center|start").
     */
    private String formatGravity(int gravity) {
        List<String> gravityValues = new ArrayList<>();
        if ((gravity & Gravity.CENTER) == Gravity.CENTER) {
            gravityValues.add("center");
        } else {
            if ((gravity & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL) {
                gravityValues.add("center_horizontal");
            }
            if ((gravity & Gravity.CENTER_VERTICAL) == Gravity.CENTER_VERTICAL) {
                gravityValues.add("center_vertical");
            }
        }
        if ((gravity & Gravity.START) == Gravity.START) {
            gravityValues.add("start");
        }
        if ((gravity & Gravity.END) == Gravity.END) {
            gravityValues.add("end");
        }
        if ((gravity & Gravity.TOP) == Gravity.TOP) {
            gravityValues.add("top");
        }
        if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            gravityValues.add("bottom");
        }
        return gravityValues.isEmpty() ? "start" : String.join("|", gravityValues);
    }

    /**
     * Formats inputType value as a string (e.g., "text|multiLine").
     */
    private String formatInputType(int inputType) {
        List<String> types = new ArrayList<>();
        if ((inputType & InputType.TYPE_CLASS_TEXT) == InputType.TYPE_CLASS_TEXT) {
            types.add("text");
            if ((inputType & InputType.TYPE_TEXT_FLAG_MULTI_LINE) == InputType.TYPE_TEXT_FLAG_MULTI_LINE) {
                types.add("multiLine");
            }
        } else if ((inputType & InputType.TYPE_CLASS_NUMBER) == InputType.TYPE_CLASS_NUMBER) {
            types.add("number");
        } else if ((inputType & InputType.TYPE_CLASS_PHONE) == InputType.TYPE_CLASS_PHONE) {
            types.add("phone");
        } else if ((inputType & InputType.TYPE_CLASS_DATETIME) == InputType.TYPE_CLASS_DATETIME) {
            types.add("datetime");
        }
        return types.isEmpty() ? "text" : String.join("|", types);
    }

    /**
     * Escapes special characters in XML attribute values.
     */
    private String escapeXml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /**
     * Returns the qualified widget type (e.g., androidx.appcompat.widget.AppCompatTextView for AndroidX).
     */
    private String getQualifiedWidgetType(String widgetType, boolean useAndroidX) {
        if (useAndroidX) {
            if (widgetType.equals("TextView")) {
                return "androidx.appcompat.widget.AppCompatTextView";
            } else if (widgetType.equals("Button")) {
                return "androidx.appcompat.widget.AppCompatButton";
            } else if (widgetType.equals("EditText")) {
                return "androidx.appcompat.widget.AppCompatEditText";
            } else if (widgetType.equals("ImageView")) {
                return "androidx.appcompat.widget.AppCompatImageView";
            } else if (widgetType.equals("CheckBox")) {
                return "androidx.appcompat.widget.AppCompatCheckBox";
            } else if (widgetType.equals("RadioButton")) {
                return "androidx.appcompat.widget.AppCompatRadioButton";
            } else if (widgetType.equals("Spinner")) {
                return "androidx.appcompat.widget.AppCompatSpinner";
            } else if (widgetType.equals("LinearLayout")) {
                return "androidx.linearlayout.widget.LinearLayout";
            } else if (widgetType.equals("RelativeLayout")) {
                return "androidx.constraintlayout.widget.ConstraintLayout";
            } else {
                return widgetType;
            }
        } else {
            return "android.widget." + widgetType;
        }
    }

    /**
     * Helper method to get indentation string (Java 8 compatible replacement for String.repeat)
     */
    private String getIndent(int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("    ");
        }
        return indent.toString();
    }

    /**
     * Converts visibility integer to string (Java 8 compatible replacement for switch expression)
     */
    private String getVisibilityString(int visibility) {
        switch (visibility) {
            case View.INVISIBLE:
                return "invisible";
            case View.GONE:
                return "gone";
            default:
                return "visible";
        }
    }

    /**
     * Converts text alignment integer to string (Java 8 compatible replacement for switch expression)
     */
    private String getTextAlignmentString(int textAlignment) {
        switch (textAlignment) {
            case View.TEXT_ALIGNMENT_CENTER:
                return "center";
            case View.TEXT_ALIGNMENT_TEXT_START:
                return "textStart";
            case View.TEXT_ALIGNMENT_TEXT_END:
                return "textEnd";
            default:
                return "gravity";
        }
    }
}