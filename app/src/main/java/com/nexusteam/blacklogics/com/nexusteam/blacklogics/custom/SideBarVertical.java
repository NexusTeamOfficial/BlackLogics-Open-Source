package com.besome.blacklogics.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.nexusteam.blacklogics.R;

/**
* A custom vertical sidebar widget with Material Design styling, theming, state management,
* advanced animations, accessibility support, and more.
*/
public class SideBarVertical extends LinearLayout {
    
    private OnItemClickListener listener;
    private int selectedPosition = -1; // Track the selected item
    private boolean isDarkTheme = false; // Theme state
    private int iconSize; // Customizable icon size
    private int textColor; // Customizable text color
    private int selectedBackgroundColor; // Background color for selected item
    private boolean showDividers; // Whether to show dividers between items
    
    private final java.util.List<SidebarItem> items = new java.util.ArrayList<>();
    

    private static final int DEFAULT_ICON_SIZE_DP = 24;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_SELECTED_COLOR = Color.LTGRAY;
    
    public SideBarVertical(Context context) {
        super(context);
        init(context, null);
    }
    
    public SideBarVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public SideBarVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    /**
* Initialize the sidebar with default settings and custom attributes.
*
* @param context The context.
* @param attrs   The attribute set from XML.
*/    
    private void init(Context context, @Nullable AttributeSet attrs) {
        setOrientation(VERTICAL);
        setBackgroundColor(isDarkTheme ? Color.DKGRAY : ContextCompat.getColor(context, android.R.color.white));
        int padding = dpToPx(8);
        setPadding(padding, padding, padding, padding);
        
        

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SideBarVertical);
        iconSize = a.getDimensionPixelSize(R.styleable.SideBarVertical_iconSize, dpToPx(DEFAULT_ICON_SIZE_DP));
        textColor = a.getColor(R.styleable.SideBarVertical_textColor, DEFAULT_TEXT_COLOR);
        selectedBackgroundColor = a.getColor(R.styleable.SideBarVertical_selectedBackgroundColor, DEFAULT_SELECTED_COLOR);
        showDividers = a.getBoolean(R.styleable.SideBarVertical_nexusShowDividers, false);
        isDarkTheme = a.getBoolean(R.styleable.SideBarVertical_darkTheme, false);
        a.recycle();
        

        setVerticalScrollBarEnabled(true);
    }
    

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
    
    /**
* Class to hold sidebar item data (icon, title, and optional description).
*/    
    public static class SidebarItem {
        private final int iconRes;
        private final String title;
        private final String description;
        
        public SidebarItem(int iconRes, String title) {
            this(iconRes, title, null);
        }
        
        public SidebarItem(int iconRes, String title, @Nullable String description) {
            this.iconRes = iconRes;
            this.title = title;
            this.description = description;
        }
        
        public int getIconRes() {
            return iconRes;
        }
        
        public String getTitle() {
            return title;
        }
        
        @Nullable
        public String getDescription() {
            return description;
        }
    }
    
    /**
* Add a new item to the sidebar with an icon, title, and optional description.
*
* @param item The SidebarItem to add.
*/    
    public void addItem(final SidebarItem item) {
        final ViewGroup itemView = (ViewGroup) LayoutInflater.from(getContext()).inflate(
        R.layout.sidebar_item_layout, this, false);
        itemView.setTag("ITEM");
        
        ImageView iconView = itemView.findViewById(R.id.sidebar_item_icon);
        TextView titleView = itemView.findViewById(R.id.sidebar_item_title);
        

        iconView.setImageResource(item.getIconRes());
        iconView.getLayoutParams().width = iconSize;
        iconView.getLayoutParams().height = iconSize;
        titleView.setText(item.getTitle());
        titleView.setTextColor(textColor);
        

        String description = item.getDescription() != null ? item.getDescription() : item.getTitle();
        iconView.setContentDescription(description);
        ViewCompat.setAccessibilityDelegate(itemView, new androidx.core.view.AccessibilityDelegateCompat() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    event.getText().add("Selected: " + item.getTitle());
                }
            }
        });
        

        int rippleColor = isDarkTheme ? Color.argb(50, 255, 255, 255) : Color.argb(50, 0, 0, 0);
        RippleDrawable rippleDrawable = new RippleDrawable(
        ColorStateList.valueOf(rippleColor),
        null,
        new ColorDrawable(isDarkTheme ? Color.DKGRAY : Color.WHITE)
        );
        itemView.setBackground(rippleDrawable);
        
        items.add(item);
        

        itemView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                animateHover(v, event.getAction());
                return false;
            }
        });
        

        final int position = getItemCount();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateItemClick(itemView);
                updateSelectedState(position);
                if (listener != null) {
                    listener.onItemClick(item, position);
                }
            }
        });
        
        

        animateItemEntry(itemView, position);
        

        if (showDividers && getChildCount() > 0) {
            View divider = new View(getContext());
            divider.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(1)));
            divider.setBackgroundColor(isDarkTheme ? Color.LTGRAY : Color.GRAY);
            addView(divider);
        }
        
        addView(itemView);
    }
    
    private int getItemCount() {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if ("ITEM".equals(v.getTag())) {
                count++;
            }
        }
        return count;
    }
    
    /**
* Update the selected state of items, highlighting the selected item.
*
* @param newPosition The position of the newly selected item.
*/    
    private void updateSelectedState(int newPosition) {

        if (selectedPosition != -1) {
            View oldView = getItemViewByPosition(selectedPosition);
            if (oldView != null) {
                oldView.setBackground(null);
            }
        }
        
        selectedPosition = newPosition;
        
        View newView = getItemViewByPosition(selectedPosition);
        if (newView != null) {
            newView.setBackgroundColor(selectedBackgroundColor);
        }
    }
    
    
    /**
* Set a click listener for sidebar items.
*
* @param listener The listener to handle item clicks.
*/    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    
    /**
* Interface for handling item click events.
*/    
    public interface OnItemClickListener {
        void onItemClick(SidebarItem item, int position);
    }
    
    /**
* Animate the item on click with scale, fade, and translation effects.
*
* @param view The view to animate.
*/    
    private void animateItemClick(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.9f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.6f, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 0f, -10f, 0f);
        
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha, translationY);
        animatorSet.setDuration(300);
        animatorSet.start();
    }
    
    /**
* Animate the item when it is added to the sidebar (slide in from left).
*
* @param view    The view to animate.
* @param position The position of the item.
*/    
    private void animateItemEntry(View view, int position) {
        view.setTranslationX(-view.getWidth());
        view.setAlpha(0f);
        ObjectAnimator slideIn = ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(slideIn, fadeIn);
        animatorSet.setDuration(500);
        animatorSet.setStartDelay(position * 100L); // Staggered entry
        animatorSet.start();
    }
    
    /**
* Animate the item on hover with a slight scale effect.
*
* @param view  The view to animate.
* @param action The hover action (e.g., MotionEvent.ACTION_HOVER_ENTER).
*/    
    private void animateHover(View view, int action) {
        if (action == android.view.MotionEvent.ACTION_HOVER_ENTER) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.05f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.05f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(200);
            animatorSet.start();
        } else if (action == android.view.MotionEvent.ACTION_HOVER_EXIT) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(200);
            animatorSet.start();
        }
    }
    
    /**
* Toggle between light and dark themes.
*
* @param isDarkTheme True to enable dark theme, false for light theme.
*/    
    public void setDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        setBackgroundColor(isDarkTheme ? Color.DKGRAY : ContextCompat.getColor(getContext(), android.R.color.white));
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                int rippleColor = isDarkTheme ? Color.argb(50, 255, 255, 255) : Color.argb(50, 0, 0, 0);
                RippleDrawable rippleDrawable = new RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                null,
                new ColorDrawable(isDarkTheme ? Color.DKGRAY : Color.WHITE)
                );
                child.setBackground(rippleDrawable);
            }
        }
        if (selectedPosition != -1) {
            updateSelectedState(selectedPosition);
        }
        invalidate();
    }
    
    /**
* Set the icon size for all items.
*
* @param iconSize The icon size in pixels.
*/    
    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                ImageView iconView = child.findViewById(R.id.sidebar_item_icon);
                iconView.getLayoutParams().width = iconSize;
                iconView.getLayoutParams().height = iconSize;
            }
        }
        requestLayout();
    }
    
    /**
* Set the text color for all items.
*
* @param textColor The text color.
*/    
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                TextView titleView = child.findViewById(R.id.sidebar_item_title);
                titleView.setTextColor(textColor);
            }
        }
        invalidate();
    }
    
    
    private View getItemViewByPosition(int position) {
        int index = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if ("ITEM".equals(v.getTag())) {
                if (index == position) return v;
                index++;
            }
        }
        return null;
    }
    
    
    /**
* Show or hide dividers between items.
*
* @param showDividers True to show dividers, false to hide.
*/    
    public void setShowDividers(boolean showDividers) {
        this.showDividers = showDividers;
        
        removeAllViews();
        selectedPosition = -1;
        

        for (SidebarItem item : items) {
            addItem(item);
        }
        
        invalidate();
    }
    
}
