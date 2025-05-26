package com.besome.blacklogics.view_manager;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.besome.blacklogics.R;
import com.besome.blacklogics.development.Complex;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mod.hey.studios.util.Helper;

public class ManageViewActivity extends AppCompatActivity {
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private LinearLayout layout_ads;
    private String sc_id = "601";
    private Complex complex;
    private ImageView deleteButton;
    private Set<Integer> selectedViewPositions = new HashSet<>();
    private Set<Integer> selectedCustomViewPositions = new HashSet<>();
    private boolean isDeleteMode = false;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.manage_image); // Reuse layout
        initialize(_savedInstanceState);
        setupUI();
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        layout_ads = findViewById(R.id.layout_ads);
        sc_id = getIntent().getStringExtra("sc_id");
        complex = new Complex();
        complex.setId(sc_id);

        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) return new ViewFragment();
                return new CustomViewFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? "View" : "Custom views";
            }
        });

        tab_layout.setupWithViewPager(view_pager);
    }

    private void initializeLogic() {
        File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/views/" + sc_id);
        if (!resourceDir.exists()) resourceDir.mkdirs();
    }

    private void setupUI() {
        ImageView back = findViewById(R.id.ig_toolbar_back);
        TextView title = findViewById(R.id.tx_toolbar_title);
        ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);

        // Configure delete button
        loadFile.setImageResource(R.drawable.ic_delete_btn_white_96dp);
        loadFile.setVisibility(View.GONE); // Initially hidden
        deleteButton = loadFile;

        Helper.applyRippleToToolbarView(back);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        title.setText("View manager");

        deleteButton.setOnClickListener(v -> {
            if (view_pager.getCurrentItem() == 0) {
                deleteSelectedViews();
            } else {
                deleteSelectedCustomViews();
            }
        });
    }

    private void deleteSelectedViews() {
        ViewFragment fragment = (ViewFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.view_pager + ":0");
        if (fragment != null) {
            fragment.deleteSelectedItems(selectedViewPositions);
            selectedViewPositions.clear();
            isDeleteMode = false;
            deleteButton.setVisibility(View.GONE);
            fragment.notifyAdapter();
        }
    }

    private void deleteSelectedCustomViews() {
        CustomViewFragment fragment = (CustomViewFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.view_pager + ":1");
        if (fragment != null) {
            fragment.deleteSelectedItems(selectedCustomViewPositions);
            selectedCustomViewPositions.clear();
            isDeleteMode = false;
            deleteButton.setVisibility(View.GONE);
            fragment.notifyAdapter();
        }
    }

    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    public static class ViewFragment extends Fragment {
        private RecyclerView recyclerView;
        private FloatingActionButton fabImport;
        private Complex.ViewAdapter adapter;
        private List<Complex.ViewItem> items = new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false); // Reuse layout
            recyclerView = view.findViewById(R.id.recycler_view);
            fabImport = view.findViewById(R.id.fab_import);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            setupAdapter();

            fabImport.setOnClickListener(v -> {
                showMessage("Adding new view not implemented"); // Placeholder
            });

            return view;
        }

        private void setupAdapter() {
            ManageViewActivity activity = (ManageViewActivity) getActivity();
            activity.complex.setupViewAdapter(recyclerView, new Complex.OnViewItemClickListener() {
                @Override
                public void onItemClick(Complex.ViewItem item) {
                    if (activity.isDeleteMode) {
                        int position = items.indexOf(item);
                        if (activity.selectedViewPositions.contains(position)) {
                            activity.selectedViewPositions.remove(position);
                        } else {
                            // Prevent selecting MainActivity
                            if (item.getJavaName().equals("MainActivity") || item.getXmlName().equals("activity_main")) {
                                showMessage("Cannot select MainActivity for deletion");
                                return;
                            }
                            activity.selectedViewPositions.add(position);
                        }
                        notifyAdapter();
                        activity.deleteButton.setVisibility(activity.selectedViewPositions.isEmpty() ? View.GONE : View.VISIBLE);
                    } else {
                        showMessage("Selected: " + item.getXmlName()); // Placeholder
                    }
                }
            });

            adapter = (Complex.ViewAdapter) recyclerView.getAdapter();
            items = adapter.items;

            // Set long click listener
            adapter.setOnViewItemLongClickListener((item, position) -> {
                // Prevent entering delete mode for MainActivity
                if (item.getJavaName().equals("MainActivity") || item.getXmlName().equals("activity_main")) {
                    showMessage("Cannot delete MainActivity");
                    return;
                }
                activity.isDeleteMode = true;
                activity.selectedViewPositions.add(position);
                activity.deleteButton.setVisibility(View.VISIBLE);
                notifyAdapter();
            });
        }

        public void deleteSelectedItems(Set<Integer> positions) {
            if (positions.isEmpty()) {
                showMessage("No views selected for deletion");
                return;
            }

            ManageViewActivity activity = (ManageViewActivity) getActivity();
            List<Complex.ViewItem> itemsToRemove = new ArrayList<>();

            // Collect items to remove
            for (int pos : positions) {
                if (pos >= 0 && pos < items.size()) {
                    Complex.ViewItem item = items.get(pos);
                    // Skip MainActivity
                    if (item.getJavaName().equals("MainActivity") || item.getXmlName().equals("activity_main")) {
                        showMessage("Cannot delete MainActivity");
                        continue;
                    }
                    itemsToRemove.add(item);
                }
            }

            if (itemsToRemove.isEmpty()) {
                showMessage("No valid views selected for deletion");
                return;
            }

            // Remove items from Complex storage
            for (Complex.ViewItem item : itemsToRemove) {
                String xmlName = item.getXmlFileName();
                String javaName = item.getJavaFileName().replace(".java", "");
                activity.complex.removeView(xmlName, javaName);
                activity.complex.removeXmlName(javaName);
                activity.complex.removeJavaName(xmlName);
                items.remove(item);
            }

            showMessage("Deleted " + itemsToRemove.size() + " views");
            notifyAdapter();
        }

        public void notifyAdapter() {
            adapter.notifyDataSetChanged();
        }

        private void showMessage(String _s) {
            Toast.makeText(getContext(), _s, Toast.LENGTH_SHORT).show();
        }
    }

    public static class CustomViewFragment extends Fragment {
        private RecyclerView recyclerView;
        private FloatingActionButton fabImport;
        private Complex.CustomViewAdapter adapter;
        private List<String> customViews = new ArrayList<>();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false); // Reuse layout
            recyclerView = view.findViewById(R.id.recycler_view);
            fabImport = view.findViewById(R.id.fab_import);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            setupAdapter();

            fabImport.setOnClickListener(v -> {
                showMessage("Adding new custom view not implemented"); // Placeholder
            });

            return view;
        }

        private void setupAdapter() {
            ManageViewActivity activity = (ManageViewActivity) getActivity();
            activity.complex.setupCustomViewRecycler(recyclerView, viewName -> {
                if (activity.isDeleteMode) {
                    int position = customViews.indexOf(viewName);
                    if (activity.selectedCustomViewPositions.contains(position)) {
                        activity.selectedCustomViewPositions.remove(position);
                    } else {
                        activity.selectedCustomViewPositions.add(position);
                    }
                    notifyAdapter();
                    activity.deleteButton.setVisibility(activity.selectedCustomViewPositions.isEmpty() ? View.GONE : View.VISIBLE);
                } else {
                    showMessage("Selected: " + viewName); // Placeholder
                }
            });

            adapter = (Complex.CustomViewAdapter) recyclerView.getAdapter();
            customViews = adapter.customViewNames;

            // Set long click listener
            adapter.setOnCustomViewLongClickListener((viewName, position) -> {
                activity.isDeleteMode = true;
                activity.selectedCustomViewPositions.add(position);
                activity.deleteButton.setVisibility(View.VISIBLE);
                notifyAdapter();
            });
        }

        public void deleteSelectedItems(Set<Integer> positions) {
            if (positions.isEmpty()) {
                ((ManageViewActivity) getActivity()).complex.removeAllCustomViews();
                showMessage("All custom views deleted");
            } else {
                List<String> viewsToRemove = new ArrayList<>();
                for (int pos : positions) {
                    viewsToRemove.add(customViews.get(pos));
                }
                for (String viewName : viewsToRemove) {
                    ((ManageViewActivity) getActivity()).complex.removeCustomView(viewName);
                    ((ManageViewActivity) getActivity()).complex.removeXmlName(viewName);
                }
                customViews.removeAll(viewsToRemove);
                showMessage("Deleted " + viewsToRemove.size() + " custom views");
            }
            notifyAdapter();
        }

        public void notifyAdapter() {
            adapter.notifyDataSetChanged();
        }

        private void showMessage(String _s) {
            Toast.makeText(getContext(), _s, Toast.LENGTH_SHORT).show();
        }
    }
}