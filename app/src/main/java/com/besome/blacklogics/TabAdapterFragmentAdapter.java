package com.besome.blacklogics;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapterFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context context;
    private final Fragment[] fragments;
    private int tabCount;

    public TabAdapterFragmentAdapter(Context context, FragmentManager manager, int tabCount) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.tabCount = tabCount;
        fragments = new Fragment[tabCount];
        initFragments();
    }

    // Initialize fragment instances and store references
    private void initFragments() {
        for (int i = 0; i < tabCount; i++) {
            fragments[i] = createFragmentByPosition(i);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "View";
            case 1:
                return "Event";
            case 2:
                return "Component";
            default:
                return "View";
        }
    }

    // Method to create fragments
    private Fragment createFragmentByPosition(int position) {
        switch (position) {
            case 0:
                return new ViewBuilderFragmentActivity();
            case 1:
                return new EventFragmentActivity();
            case 2:
                return new ComponentFragmentActivity();
            default:
                return new ViewBuilderFragmentActivity();
        }
    }

    // Access fragment reference from Activity
    public Fragment getFragment(int position) {
        if (position >= 0 && position < tabCount) {
            return fragments[position];
        }
        return null;
    }
}
