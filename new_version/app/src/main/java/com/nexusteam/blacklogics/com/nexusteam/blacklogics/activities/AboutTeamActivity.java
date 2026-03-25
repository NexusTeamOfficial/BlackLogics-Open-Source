
package com.nexusteam.blacklogics.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.util.Linkify;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.bean.AboutTeamData;
import com.nexusteam.blacklogics.bean.ChangeLogItem;
import com.nexusteam.blacklogics.bean.TeamMember;
import com.nexusteam.blacklogics.utils.BlackLogicsUtil;
import com.nexusteam.blacklogics.utils.DisplayUtils;
import com.nexusteam.blacklogics.utils.ToastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AboutTeamActivity extends AppCompatActivity {
    
    private ViewPager viewPager;
    private LinearLayout fab;
    private TextView fabLabel;
    private List<TeamMember> teamMembers = new ArrayList<>();
    private List<ChangeLogItem> changeLogItems = new ArrayList<>();
    private TabLayout tabLayout;
    private LinearLayout rootLayout;
    private LinearLayout loadingContainer;
    private LinearLayout trashContainer;
    private LinearLayout teamRecyclerContainer;
    private LinearLayout changeLogRecyclerContainer;
    private RecyclerView teamRecyclerView;
    private RecyclerView changeLogRecyclerView;
    private TextView loadingTitle;
    private TextView loadingDescription;
    private SharedPreferences sharedPreferences;
    private String discordInviteLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initializeViews();
        initializeLogic();
    }
    
    private void initializeViews() {
        fabLabel = findViewById(R.id.fab_label);
        fab = findViewById(R.id.fab);
        loadingContainer = findViewById(R.id.loading_view);
        tabLayout = findViewById(R.id.tab_layout);
        ImageView backButton = findViewById(R.id.img_back);
        rootLayout = findViewById(R.id.rootq);
        trashContainer = findViewById(R.id.trash);
        

        teamRecyclerContainer = findViewById(R.id.layout1);
        changeLogRecyclerContainer = findViewById(R.id.layout2);
        teamRecyclerView = findViewById(R.id.recyclerview1);
        changeLogRecyclerView = findViewById(R.id.recyclerview2);
        loadingTitle = findViewById(R.id.tv_loading);
        loadingDescription = findViewById(R.id.tv_loading_desc);
        
        sharedPreferences = getSharedPreferences("AboutTeam", Activity.MODE_PRIVATE);
        
        applyRippleEffect(backButton, "#FFFFFF", "#1F000000", 90f);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupRecyclerViewScrollListeners();
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiscordInvite();
            }
        });
        
    }
    
    private void setupRecyclerViewScrollListeners() {
        teamRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateFabLabelVisibility(dy);
            }
        });
        
        changeLogRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateFabLabelVisibility(dy);
            }
        });
    }
    
    private void updateFabLabelVisibility(int dy) {
        if (dy > 8) {
            fabLabel.setVisibility(View.GONE);
        } else if (dy < -8) {
            fabLabel.setVisibility(View.VISIBLE);
        }
    }
    
    private void openDiscordInvite() {
        if (discordInviteLink == null || discordInviteLink.isEmpty()) {
            ToastManager.showErrorToast(this, "Discord invite link not available");
            return;
        }
        
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(discordInviteLink));
            startActivity(intent);
        } catch (Exception e) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Discord Invite", discordInviteLink);
            clipboard.setPrimaryClip(clip);
            ToastManager.showInfoToast(this, 
            "Discord invite link copied to clipboard. Please open it in your browser.");
        }
    }
    
    private void initializeLogic() {
        teamRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        changeLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab.setVisibility(View.GONE);
        
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        
        initViewPager();
        applyRippleEffect(fab, "#5865F2", "#FFFFFF", 90f);
        
        String toSelect = getIntent().getStringExtra("select");
        if ("changelog".equals(toSelect)) {
            viewPager.setCurrentItem(1);
        } else if ("majorChanges".equals(toSelect)) {
            viewPager.setCurrentItem(2);
        }
        
        loadData();
    }
    
    private void loadData() {
        String cachedData = sharedPreferences.getString("aboutTeamData", null);
        if (cachedData != null) {
            parseAndDisplayData(cachedData, true);
        }
        
        new FetchTeamDataTask().execute(
        "https://raw.githubusercontent.com/NexusTeamOfficial/BlackLogics-Pre-Alpha/main/aboutus.json"
        );
    }
    
    private void parseAndDisplayData(String jsonData, boolean fromCache) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
            Gson gson = new Gson();
            AboutTeamData data = gson.fromJson(jsonData, AboutTeamData.class);
            
            if (data != null) {
                discordInviteLink = data.getDiscordInviteLink();
                
                teamMembers = new ArrayList<>();
                if (data.getTeamMembers() != null) {
                    for (Object obj : data.getTeamMembers()) {
                        if (obj instanceof TeamMember) {
                            teamMembers.add((TeamMember) obj);
                        }
                    }
                }
                
                changeLogItems = new ArrayList<>();
                if (data.getChangeLogItems() != null) {
                    for (Object obj : data.getChangeLogItems()) {
                        if (obj instanceof ChangeLogItem) {
                            changeLogItems.add((ChangeLogItem) obj);
                        }
                    }
                }
                
                updateRecyclerViews();
                
                if (!fromCache) {
                    saveDataLocally(jsonData);
                    showMainContent();
                } else {
                    showMainContent();
                }
            } else {
                showError("Invalid data format");
            }
        } catch (JsonParseException e) {
            Log.e("AboutTeamActivity", "Error parsing JSON", e);
            showError("Error parsing data: " + e.getMessage());
        }
    }
    
    private void updateRecyclerViews() {
        teamRecyclerView.setAdapter(new TeamMemberAdapter(teamMembers));
        changeLogRecyclerView.setAdapter(new ChangeLogAdapter(changeLogItems));
    }
    
    private void saveDataLocally(String jsonData) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("aboutTeamData", jsonData);
        editor.putLong("lastUpdated", System.currentTimeMillis());
        editor.apply();
    }
    
    private void showMainContent() {
        animateView(loadingContainer, "translationY", -1000, 300);
        animateView(loadingContainer, "alpha", 0, 300);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateView(fab, "translationY", 0, 300);
                animateView(fab, "alpha", 1, 300);
                fab.setVisibility(View.VISIBLE);
            }
        }, 200);
        
    }
    
    private void showError(String message) {
        loadingTitle.setText("Oops! Something went wrong");
        loadingDescription.setText(message);
    }
    
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(0);
        }
    }
    
    private void initViewPager() {
        viewPager = new ViewPager(this);
        viewPager.setLayoutParams(new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        viewPager.setAdapter(new AboutTeamPagerAdapter());
        viewPager.setCurrentItem(0);
        rootLayout.addView(viewPager);
        
        tabLayout.setSelectedTabIndicatorColor(0xff008dcd);
        tabLayout.setupWithViewPager(viewPager);
        
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            
            @Override
            public void onPageSelected(int position) {
                fabLabel.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
    
    private void loadImage(ImageView imageView, String url) {
        Glide.with(getApplicationContext())
        .load(url)
        .placeholder(R.drawable.ic_user) // Using AboutMod's placeholder
        .error(R.drawable.ic_user) // Using AboutMod's error image
        .into(imageView);
    }
    
    private void applyAdvancedCorners(View view, String color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(color));
        gradientDrawable.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
        view.setBackground(gradientDrawable);
    }
    
    private void animateView(View view, String propertyName, float value, long duration) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setTarget(view);
        animator.setPropertyName(propertyName);
        animator.setFloatValues(value);
        animator.setDuration(duration);
        animator.start();
    }
    
    private void animateLayoutChanges(LinearLayout view) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(300);
        TransitionManager.beginDelayedTransition(view, autoTransition);
    }
    
    private void applyRippleEffect(View view, String focusColor, String pressedColor, float cornerRadius) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor(focusColor));
        background.setCornerRadius(cornerRadius);
        
        RippleDrawable rippleDrawable = new RippleDrawable(
        new ColorStateList(
        new int[][]{new int[]{}},
        new int[]{Color.parseColor(pressedColor)}
        ),
        background,
        null
        );
        
        view.setBackground(rippleDrawable);
    }
    
    private class FetchTeamDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingTitle.setText("Loading Team Data");
            loadingDescription.setText("Fetching latest information...");
            loadingContainer.setVisibility(View.VISIBLE);
            fab.setVisibility(View.INVISIBLE);
        }
        
        @Override
        protected String doInBackground(String... urls) {
            if (urls.length == 0 || urls[0] == null) {
                return null;
            }
            
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    reader = new BufferedReader(inputStreamReader);
                    
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    
                    return stringBuilder.toString();
                } else {
                    Log.e("FetchTeamDataTask", "HTTP error: " + connection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e("FetchTeamDataTask", "Error fetching data", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("FetchTeamDataTask", "Error closing reader", e);
                    }
                }
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            if (result != null && !result.isEmpty()) {
                parseAndDisplayData(result, false);
            } else {
                showError("Unable to fetch data. Please check your internet connection.");
                fab.setVisibility(View.VISIBLE);
            }
        }
    }
    
    private class AboutTeamPagerAdapter extends PagerAdapter {
        
        @Override
        public int getCount() {
            return 3;
        }
        
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.about_empty_viewpager, container, false);
            
            LinearLayout viewContainer = view.findViewById(R.id.linearLayout); 
            
            switch (position) {
                case 0:
                if (teamRecyclerContainer.getParent() != null) {
                    ((ViewGroup) teamRecyclerContainer.getParent()).removeView(teamRecyclerContainer);
                }
                viewContainer.addView(teamRecyclerContainer);
                break;
                
                case 1:
                if (changeLogRecyclerContainer.getParent() != null) {
                    ((ViewGroup) changeLogRecyclerContainer.getParent()).removeView(changeLogRecyclerContainer);
                }
                viewContainer.addView(changeLogRecyclerContainer);
                break;
                
                case 2:
                TextView majorChanges = new TextView(AboutTeamActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
                );
                
                int margin = DisplayUtils.dpToPx(AboutTeamActivity.this, 10);
                params.setMargins(margin, margin, margin, margin);
                majorChanges.setLayoutParams(params);
                
                int padding = DisplayUtils.dpToPx(AboutTeamActivity.this, 8);
                majorChanges.setPadding(padding, padding, padding, padding);
                majorChanges.setTextColor(ContextCompat.getColor(
                AboutTeamActivity.this, R.color.primary_text_default_material_light));
                majorChanges.setTextSize(14);
                majorChanges.setText("Major changes to BlackLogics will appear here.");
                viewContainer.addView(majorChanges);
                break;
            }
            
            container.addView(view);
            return view;
        }
        
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            trashContainer.addView((View) object);
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Team Members";
                case 1: return "Changelog";
                case 2: return "Major changes";
                default: return null;
            }
        }
    }
    
    private class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.ViewHolder> {
        
        private final List<TeamMember> teamMembers;
        
        public TeamMemberAdapter(List<TeamMember> teamMembers) {
            this.teamMembers = new ArrayList<>();
            if (teamMembers != null) {
                for (Object obj : teamMembers) {
                    if (obj instanceof TeamMember) {
                        this.teamMembers.add((TeamMember) obj);
                    }
                }
            }
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.about_moddersview, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TeamMember member = teamMembers.get(position);
            
            loadImage(holder.userIcon, member.getImageUrl());
            holder.userName.setText(member.getName());
            holder.description.setText(member.getDescription());
            
            if (member.isTitle()) {
                holder.title.setText(member.getTitle());
                holder.title.setVisibility(View.VISIBLE);
            } else {
                holder.title.setVisibility(View.GONE);
            }
            
            if (member.isMainModder()) {
                applyAdvancedCorners(holder.sideBar, "#008DCD");
            } else {
                applyAdvancedCorners(holder.sideBar, "#00CDAB");
            }
        }
        
        @Override
        public int getItemCount() {
            return teamMembers.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, userName, description;
            LinearLayout sideBar;
            ImageView userIcon;
            
            ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                userName = itemView.findViewById(R.id.tv_user_name);
                description = itemView.findViewById(R.id.tv_description);
                sideBar = itemView.findViewById(R.id.view_leftline);
                userIcon = itemView.findViewById(R.id.img_user_icon);
            }
        }
    }
    
    private class ChangeLogAdapter extends RecyclerView.Adapter<ChangeLogAdapter.ViewHolder> {
        
        private static final String KEY_SHOWING_ADDITIONAL_INFO = "showingAdditionalInfo";
        private final List<ChangeLogItem> changeLogItems;
        
        public ChangeLogAdapter(List<ChangeLogItem> changeLogItems) {
            this.changeLogItems = new ArrayList<>();
            if (changeLogItems != null) {
                for (Object obj : changeLogItems) {
                    if (obj instanceof ChangeLogItem) {
                        this.changeLogItems.add((ChangeLogItem) obj);
                    }
                }
            }
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.about_changelog, parent, false);
            return new ViewHolder(view);
        }
        
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            final ChangeLogItem item = changeLogItems.get(position);
            
            holder.title.setVisibility(item.isTitle() ? View.VISIBLE : View.GONE);
            holder.title.setText(item.getTitle());
            
            holder.variant.setVisibility(item.isBeta() ? View.VISIBLE : View.GONE);
            holder.variant.setText(item.isBeta() ? "Beta" : "Official");
            
            if (item.getReleaseDate() > 0) {
                holder.releasedOn.setVisibility(View.VISIBLE);
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                .format(new Date(item.getReleaseDate()));
                holder.releasedOn.setText("Released on: " + date);
            } else {
                holder.releasedOn.setVisibility(View.GONE);
            }
            
            holder.subtitle.setText(item.getDescription());
            Linkify.addLinks(holder.subtitle, Linkify.WEB_URLS);
            
            final boolean showingAdditionalInfo = item.isShowingAdditionalInfo();
            holder.viewAdditionalInfo.setVisibility(showingAdditionalInfo ? View.VISIBLE : View.GONE);
            holder.arrow.setRotation(showingAdditionalInfo ? 0 : 180);
            
            holder.logBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isVisible = !showingAdditionalInfo;
                    item.setShowingAdditionalInfo(isVisible);
                    holder.viewAdditionalInfo.setVisibility(isVisible ? View.VISIBLE : View.GONE);
                    holder.arrow.setRotation(isVisible ? 0 : 180);
                    notifyItemChanged(position);
                }
            });
            
            
            if (position == 0) {
                applyAdvancedCorners(holder.leftLine, "#008dcd");
            } else {
                holder.leftLine.setBackground(null);
            }
        }
        
        @Override
        public int getItemCount() {
            return changeLogItems.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView title, variant, releasedOn, subtitle;
            final LinearLayout leftLine, logBackground, viewAdditionalInfo;
            final ImageButton arrow;
            
            ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                variant = itemView.findViewWithTag("tv_variant");
                releasedOn = itemView.findViewById(R.id.tv_release_note);
                subtitle = itemView.findViewById(R.id.tv_sub_title);
                leftLine = itemView.findViewById(R.id.view_leftline);
                logBackground = itemView.findViewWithTag("log_background");
                viewAdditionalInfo = itemView.findViewWithTag("view_additional_info");
                arrow = itemView.findViewWithTag("ic_arrow");
            }
        }
    }
}
