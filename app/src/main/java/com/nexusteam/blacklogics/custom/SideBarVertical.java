package com.nexusteam.blacklogics.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.color.MaterialColors;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.List;

public class SideBarVertical extends LinearLayout {

    private OnItemClickListener listener;
    private int selectedPosition = -1;
    private boolean isDarkTheme = false;
    private boolean darkThemeForced = false;
    private int iconSize;
    private int textColor;
    private int selectedBackgroundColor;
    private boolean hasCustomTextColor = false;
    private boolean hasCustomSelectedColor = false;
    private boolean showDividers;

    private final List<SidebarItem> items = new ArrayList<>();

    private static final int DEFAULT_ICON_SIZE_DP = 24;

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

    private void init(Context context, @Nullable AttributeSet attrs) {
        setOrientation(VERTICAL);
        int padding = dpToPx(8);
        setPadding(padding, padding, padding, padding);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SideBarVertical);
        try {
            darkThemeForced = a.hasValue(R.styleable.SideBarVertical_darkTheme);
            isDarkTheme = a.getBoolean(R.styleable.SideBarVertical_darkTheme, isNightMode(context));

            hasCustomTextColor = a.hasValue(R.styleable.SideBarVertical_textColor);
            hasCustomSelectedColor = a.hasValue(R.styleable.SideBarVertical_selectedBackgroundColor);

            iconSize = a.getDimensionPixelSize(R.styleable.SideBarVertical_iconSize, dpToPx(DEFAULT_ICON_SIZE_DP));
            textColor = a.getColor(R.styleable.SideBarVertical_textColor, defaultTextColor());
            selectedBackgroundColor = a.getColor(R.styleable.SideBarVertical_selectedBackgroundColor, defaultSelectedColor());
            showDividers = a.getBoolean(R.styleable.SideBarVertical_nexusShowDividers, false);
        } finally {
            a.recycle();
        }

        setVerticalScrollBarEnabled(true);
    }


    private boolean isNightMode(Context c) {
        int mode = c.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    @ColorInt
    private int resolveThemeColor(int attr, @ColorRes int lightRes, @ColorRes int darkRes) {
        Context c = getContext();
        int fallback = ContextCompat.getColor(c, isDarkTheme ? darkRes : lightRes);
        if (darkThemeForced) {
            return fallback;
        }
        return MaterialColors.getColor(c, attr, fallback);
    }

    @ColorInt
    private int defaultTextColor() {
        return resolveThemeColor(
                com.google.android.material.R.attr.colorOnSurface,
                R.color.md_theme_light_onSurface,
                R.color.md_theme_dark_onSurface);
    }

    @ColorInt
    private int defaultSelectedColor() {
        return resolveThemeColor(
                com.google.android.material.R.attr.colorSecondaryContainer,
                R.color.md_theme_light_secondaryContainer,
                R.color.md_theme_dark_secondaryContainer);
    }

    @ColorInt
    private int surfaceColor() {
        return resolveThemeColor(
                com.google.android.material.R.attr.colorSurface,
                R.color.md_theme_light_surface,
                R.color.md_theme_dark_surface);
    }

    @ColorInt
    private int outlineColor() {
        return resolveThemeColor(
                com.google.android.material.R.attr.colorOutline,
                R.color.md_theme_light_outline,
                R.color.md_theme_dark_outline);
    }

    private RippleDrawable createItemBackground() {
        int rippleColor = ColorUtils.setAlphaComponent(defaultTextColor(), 31);
        return new RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                null,
                new ColorDrawable(surfaceColor())
        );
    }

    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


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
        ViewCompat.setAccessibilityDelegate(itemView, new AccessibilityDelegateCompat() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    event.getText().add("Selected: " + item.getTitle());
                }
            }
        });

        itemView.setBackground(createItemBackground());

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
            divider.setBackgroundColor(outlineColor());
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

    private void updateSelectedState(int newPosition) {
        if (selectedPosition != -1) {
            View oldView = getItemViewByPosition(selectedPosition);
            if (oldView != null) {
                oldView.setBackground(createItemBackground());
            }
        }

        selectedPosition = newPosition;

        View newView = getItemViewByPosition(selectedPosition);
        if (newView != null) {
            newView.setBackgroundColor(selectedBackgroundColor);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SidebarItem item, int position);
    }


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

    private void animateItemEntry(View view, int position) {
        view.setTranslationX(-view.getWidth());
        view.setAlpha(0f);
        ObjectAnimator slideIn = ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(slideIn, fadeIn);
        animatorSet.setDuration(500);
        animatorSet.setStartDelay(position * 100L);
        animatorSet.start();
    }

    private void animateHover(View view, int action) {
        if (action == MotionEvent.ACTION_HOVER_ENTER) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.05f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.05f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(200);
            animatorSet.start();
        } else if (action == MotionEvent.ACTION_HOVER_EXIT) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY);
            animatorSet.setDuration(200);
            animatorSet.start();
        }
    }


    public void setDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        this.darkThemeForced = true;

        if (!hasCustomTextColor) {
            textColor = defaultTextColor();
        }
        if (!hasCustomSelectedColor) {
            selectedBackgroundColor = defaultSelectedColor();
        }

        setBackgroundColor(surfaceColor());

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                child.setBackground(createItemBackground());
                TextView titleView = child.findViewById(R.id.sidebar_item_title);
                if (titleView != null) {
                    titleView.setTextColor(textColor);
                }
            } else {
                child.setBackgroundColor(outlineColor());
            }
        }

        if (selectedPosition != -1) {
            updateSelectedState(selectedPosition);
        }
        invalidate();
    }

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

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        this.hasCustomTextColor = true;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                TextView titleView = child.findViewById(R.id.sidebar_item_title);
                titleView.setTextColor(textColor);
            }
        }
        invalidate();
    }

    public void setSelectedBackgroundColor(@ColorInt int color) {
        this.selectedBackgroundColor = color;
        this.hasCustomSelectedColor = true;
        if (selectedPosition != -1) {
            updateSelectedState(selectedPosition);
        }
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

    public void setShowDividers(boolean showDividers) {
        this.showDividers = showDividers;

        List<SidebarItem> copy = new ArrayList<>(items);
        items.clear();

        removeAllViews();
        selectedPosition = -1;

        for (SidebarItem item : copy) {
            addItem(item);
        }

        invalidate();
    }
}
