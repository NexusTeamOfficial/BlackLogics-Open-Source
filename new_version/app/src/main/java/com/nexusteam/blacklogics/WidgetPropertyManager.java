package com.nexusteam.blacklogics;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView; 

import androidx.cardview.widget.CardView;

import com.nexusteam.internal.os.layouteditor.widget.Widget;
import com.nexusteam.internal.os.layouteditor.widget.*;
import com.nexusteam.internal.os.layouteditor.util.WidgetUtil;

public class WidgetPropertyManager {
    // Static fields for widget properties (extracted from original)
    public static CardView widget_width;
    public static CardView widget_height;
    public static CardView widget_text;
    public static CardView widget_src;
    public static LinearLayout widgetpropertiesLinearLayout1;
    public static CardView translationX;
    public static CardView transY;
    public static CardView colorText;
    public static CardView textSize;
    public static CardView Lines;
    public static CardView textStyle;
    public static int mListView = 1;
    public static CardView padding;
    public static CardView margin;
    public static CardView background;
    public static CardView widget_id;
    public static CardView widget_inject_attributes;
    public static CardView gravityLayout;
    public static CardView layoutGravity;
    public static CardView checkState;
    public static CardView switchCheckState;
    public static CardView progressStyle;
    public static CardView widget_scale;
    public static CardView max_progress;
    public static CardView widget_orientation;
    public static CardView widget_convert;
    public static CardView widget_weight;

    public static View selectedWidget;
    public static androidx.appcompat.widget.LinearLayoutCompat ll_properties;  // Properties panel

    private static ObjectAnimator anim = new ObjectAnimator();  // Shared animator

    /**
     * Get the properties panel LinearLayout.
     */
    public static androidx.appcompat.widget.LinearLayoutCompat getPropertiesPanel() {
        return ll_properties;  // Assuming this is initialized in DesignActivity
    }

    /**
     * Hide the properties panel with animation.
     */
    public static void hideProperties() {
        anim.setTarget(ll_properties);
        anim.setProperty(View.TRANSLATION_Y);
        anim.setFloatValues((float) ll_properties.getHeight());
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    /**
     * Show the properties panel with animation.
     */
    public static void showProperties() {
        ll_properties.setVisibility(View.VISIBLE);
        anim.setTarget(ll_properties);
        anim.setProperty(View.TRANSLATION_Y);
        anim.setFloatValues(0f);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }

    /**
     * Check if properties panel is hidden.
     */
    public static boolean isHiddenProperties() {
        return ll_properties.getTranslationY() == (float) ll_properties.getHeight();
    }

    /**
     * Unselect the currently selected widget.
     */
    public static void unselectSelectedWidget() {
        if (selectedWidget != null) {
            ((Widget) selectedWidget).setBackgroundColor(0);  // Transparent
            selectedWidget = null;
            hideProperties();
        }
    }

    /**
     * Select a widget and show its properties.
     */
    public static void selectWidget(View view) {
        if (selectedWidget != null) {
            unselectSelectedWidget();
        }

        selectedWidget = view;
        ((Widget) view).setBackgroundColor(Color.parseColor("#77BBCCDD"));  // Highlight color

        // Update widget ID in properties (assuming tv_widget_id is in ll_properties)
         ((TextView) ll_properties.findViewById(R.id.tv_widget_id)).setText(WidgetUtil.getWidgetId(view));

        // Show/hide specific property cards based on widget type
        String widgetType = view.getClass().getSimpleName();
        widget_text.setVisibility((view instanceof WidgetButton || view instanceof WidgetTextView) ? View.VISIBLE : View.GONE);
        colorText.setVisibility((view instanceof WidgetButton || view instanceof WidgetTextView) ? View.VISIBLE : View.GONE);
        textSize.setVisibility((view instanceof WidgetButton || view instanceof WidgetTextView) ? View.VISIBLE : View.GONE);
        textStyle.setVisibility((view instanceof WidgetButton || view instanceof WidgetTextView) ? View.VISIBLE : View.GONE);
        Lines.setVisibility((view instanceof WidgetButton || view instanceof WidgetTextView) ? View.VISIBLE : View.GONE);
        widget_src.setVisibility((view instanceof WidgetImageView) ? View.VISIBLE : View.GONE);

        // Add more type-specific logic as needed (e.g., for WebView)

        showProperties();
    }

    // Constructor (if needed for initialization, but since static, maybe not)
    public WidgetPropertyManager() {
        // Initialize any non-static stuff if needed
    }
}