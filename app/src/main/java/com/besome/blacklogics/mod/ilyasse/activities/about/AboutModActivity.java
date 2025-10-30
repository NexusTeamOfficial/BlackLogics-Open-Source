package mod.ilyasse.activities.about;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import android.os.AsyncTask;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import com.besome.blacklogics.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import a.a.a.kk;
import mod.RequestNetwork;
import mod.RequestNetworkController;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class AboutModActivity extends AppCompatActivity {
	
	private ViewPager viewPager;
	private LinearLayout fab;
	private TextView fabLabel;
	private ArrayList<HashMap<String, Object>> moddersList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> changelogList = new ArrayList<>();
	private TabLayout tablayout;
	private LinearLayout root;
	//    private LinearLayout loading;
	private LinearLayout trash;
	private LinearLayout moddersRecyclerContainer;
	private LinearLayout changelogRecyclerContainer;
	private RecyclerView moddersRecycler;
	private RecyclerView changelogRecycler;
	private TextView loadingTitle;
	private TextView loadingDescription;
	private RequestNetwork requestData;
	private RequestNetwork.RequestListener requestDataListener;
	private SharedPreferences sharedPref;
	private String discordInviteLink;
	private boolean showingAdditionalInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		fabLabel = findViewById(R.id.fab_label);
		fab = findViewById(R.id.fab);
		LinearLayout loading = findViewById(R.id.loading_view);
		tablayout = findViewById(R.id.tab_layout);
		ImageView back = findViewById(R.id.img_back);
		root = findViewById(R.id.rootq);
		trash = findViewById(R.id.trash);
		//TODO: Rename layout1, layout2, etc. resource IDs to more descriptive names
		moddersRecyclerContainer = findViewById(R.id.layout1);
		changelogRecyclerContainer = findViewById(R.id.layout2);
		moddersRecycler = findViewById(R.id.recyclerview1);
		changelogRecycler = findViewById(R.id.recyclerview2);
		loadingTitle = findViewById(R.id.tv_loading);
		loadingDescription = findViewById(R.id.tv_loading_desc);
		requestData = new RequestNetwork(this);
		sharedPref = getSharedPreferences("AboutMod", Activity.MODE_PRIVATE);
		
		rippleRound(back, "#ffffff", "#1F000000", 90);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		
		// RecyclerView$OnScrollListener got obfuscated to RecyclerView$m
		RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if (dy > 8) {
					fabLabel.setVisibility(View.GONE);
				} else if (dy < -8) {
					fabLabel.setVisibility(View.VISIBLE);
				}
			}
		};
		
		
		// RecyclerView.addOnScrollListener(RecyclerView$OnScrollListener) got obfuscated
		// to RecyclerView.a(RecyclerView$m)
		moddersRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				// Your scroll logic here
			}
		});
		
		// For changelogRecycler:
		changelogRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				// Your scroll logic here
			}
		});
		
		
		fab.setOnClickListener(v -> {
			try {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(discordInviteLink)));
			} catch (Exception e) {
				((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", discordInviteLink));
				Toast.makeText(this, "Your device doesn't have a browser app installed. Invite link has been copied to your clipboard.", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	private class FetchDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loadingTitle.setText("Loading...");
			loadingDescription.setText("Fetching data...");
			//  loading.setVisibility(View.VISIBLE); // Show loading UI
			fab.setVisibility(View.INVISIBLE); // Hide floating action button
		}
		
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			HttpURLConnection urlConnection = null;
			BufferedReader reader = null;
			
			try {
				URL url = new URL(urls[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setConnectTimeout(5000);  // Set connection timeout
				urlConnection.setReadTimeout(5000);     // Set read timeout
				urlConnection.connect();
				
				// Read the response
				InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
				reader = new BufferedReader(inputStreamReader);
				
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}
				
				response = stringBuilder.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return "Error: " + e.getMessage();  // Return error message in case of failure
			} finally {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			LinearLayout loading = findViewById(R.id.loading_view);
			
			if (result != null && !result.isEmpty()) {
				try {
					// Deserialize JSON response to AboutUsData object
					GsonBuilder builder = new GsonBuilder();
					builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
					AboutUsData data = builder.create().fromJson(result, AboutUsData.class);
					
					// Update UI with fetched data
					if (data != null) {
						if (data.discordInviteLink != null) {
							discordInviteLink = data.discordInviteLink;
						}
						
						// Update modders list in RecyclerView
						moddersList = data.moddersteam;
						moddersRecycler.setAdapter(new ModdersRecyclerAdapter(moddersList));
						
						// Update changelog list in RecyclerView
						changelogList = data.changelog;
						changelogRecycler.setAdapter(new ChangelogRecyclerAdapter(changelogList));
						
						// Save data locally
						SharedPreferences.Editor savedData = sharedPref.edit();
						savedData.putString("discordInviteLinkBackup", discordInviteLink);
						savedData.putString("moddersBackup", new Gson().toJson(moddersList));
						savedData.putString("changelogBackup", new Gson().toJson(changelogList));
						savedData.apply();
						
						// Hide loading animation and show UI
						shadAnim(loading, "translationY", -1000, 300);
						shadAnim(loading, "alpha", 0, 300);
						new Handler().postDelayed(() -> {
							shadAnim(fab, "translationY", 0, 300);
							shadAnim(fab, "alpha", 1, 300);
						}, 200);
						
					} else {
						// Handle empty or invalid data
						loadingTitle.setText("No Data Available");
						loadingDescription.setText("No valid data was fetched.");
					}
				} catch (JsonParseException e) {
					// Handle JSON parsing errors
					loadingTitle.setText("Something went wrong");
					loadingDescription.setText("Error: " + e.getMessage());
					Log.e("FetchDataTask", "Error parsing JSON", e);
				}
			} else {
				// Handle error (e.g., no internet connection, invalid response)
				loadingTitle.setText("Your device is offline!");
				loadingDescription.setText("Check your internet connection and try again.");
			}
			
			// Make sure FAB is visible after data fetch attempt
			new Handler().postDelayed(() -> {
				fab.setVisibility(View.VISIBLE);
			}, 200);
		}
	}
	
	// Initialize request parameters and start the request
	
	
	private void initializeLogic() {
		moddersRecycler.setLayoutManager(new LinearLayoutManager(this));
		changelogRecycler.setLayoutManager(new LinearLayoutManager(this));
		fab.setVisibility(View.GONE);
		getWindow().setStatusBarColor(Color.WHITE);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		initViewPager();
		
		// Call FetchDataTask and pass the URL
		new FetchDataTask().execute("https://raw.githubusercontent.com/NexusTeamOfficial/BlackLogics-Pre-Alpha/main/aboutus.json");
		
		rippleRound(fab, "#5865F2", "#FFFFFF", 90);
		
		String toSelect = getIntent().getStringExtra("select");
		if ("changelog".equals(toSelect)) {
			viewPager.setCurrentItem(1);
		} else if ("majorChanges".equals(toSelect)) {
			viewPager.setCurrentItem(2);
		}
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
		ViewGroup.LayoutParams.WRAP_CONTENT));
		viewPager.setAdapter(new PagerAdapterq());
		viewPager.setCurrentItem(0);
		root.addView(viewPager);
		
		tablayout.setSelectedTabIndicatorColor(0xff008dcd);
		tablayout.setupWithViewPager(viewPager);
		
		// ViewPager.addOnPageChangeListener(ViewPager$OnPageChangeListener) got
		// obfuscated to ViewPager.a(ViewPager$e)
		// ViewPager$OnPageChangeListener got obfuscated to ViewPager$e
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// Your logic here (if needed)
			}
			
			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					fabLabel.setVisibility(View.VISIBLE);
				} else {
					fabLabel.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// Your logic here (if needed)
			}
		});
		
	}
	
	private void circularImage(final ImageView image, final String url) {
		Glide.with(this)
		.load(url)
		.placeholder(R.drawable.ic_user)
		.into(image);
	}
	
	private void advancedCorners(final View view, final String color) {
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(Color.parseColor(color));
		gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
		view.setBackground(gd);
	}
	
	private void shadAnim(final View view, final String propertyName, final double value, final double duration) {
		ObjectAnimator anim = new ObjectAnimator();
		anim.setTarget(view);
		anim.setPropertyName(propertyName);
		anim.setFloatValues((float) value);
		anim.setDuration((long) duration);
		anim.start();
	}
	
	private void animateLayoutChanges(final LinearLayout view) {
		//i used this instead of the xml attribute because this one looks better and smoother.
		AutoTransition autoTransition = new AutoTransition();
		autoTransition.setDuration((short) 300);
		TransitionManager.beginDelayedTransition(view, autoTransition);
	}
	
	private void rippleRound(final View view, final String focus, final String pressed, final double round) {
		GradientDrawable GG = new GradientDrawable();
		GG.setColor(Color.parseColor(focus));
		GG.setCornerRadius((float) round);
		RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(pressed)}), GG, null);
		view.setBackground(RE);
	}
	
	private static class AboutUsData {
		String discordInviteLink;
		ArrayList<HashMap<String, Object>> moddersteam;
		ArrayList<HashMap<String, Object>> changelog;
	}
	
	// PagerAdapter got obfuscated to kk
	private class PagerAdapterq extends PagerAdapter {
		
		@Override
		public int getCount() {
			return 3; // Number of pages
		}
		
		@Override
		public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
			return view == object;
		}
		
		@NonNull
		@Override
		public Object instantiateItem(@NonNull ViewGroup container, int position) {
			LayoutInflater inflater = getLayoutInflater();
			View v = inflater.inflate(R.layout.about_empty_viewpager, container, false);
			
			LinearLayout viewContainer = v.findViewById(R.id.linearLayout);
			
			if (position == 0) {
				// Add the Modders Recycler container
				if (moddersRecyclerContainer.getParent() != null) {
					((ViewGroup) moddersRecyclerContainer.getParent()).removeView(moddersRecyclerContainer);
				}
				viewContainer.addView(moddersRecyclerContainer);
				
			} else if (position == 1) {
				// Add the Changelog Recycler container
				if (changelogRecyclerContainer.getParent() != null) {
					((ViewGroup) changelogRecyclerContainer.getParent()).removeView(changelogRecyclerContainer);
				}
				viewContainer.addView(changelogRecyclerContainer);
				
			} else if (position == 2) {
				// Add a TextView for "Major changes"
				TextView majorChanges = new TextView(AboutModActivity.this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
				);
				int tenDp = SketchwareUtil.dpToPx(AboutModActivity.this, 10);
				params.setMargins(tenDp, tenDp, tenDp, tenDp);
				majorChanges.setLayoutParams(params);
				
				int eightDp = SketchwareUtil.dpToPx(AboutModActivity.this, 8);
				majorChanges.setPadding(eightDp, eightDp, eightDp, eightDp);
				majorChanges.setTextColor(ContextCompat.getColor(AboutModActivity.this,
				R.color.primary_text_default_material_light));
				majorChanges.setTextSize(14);
				majorChanges.setText("Major changes to BlackLogics will appear here.");
				viewContainer.addView(majorChanges);
			}
			
			container.addView(v);
			return v;
		}
		
		@Override
		public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
			container.removeView((View) object);
			trash.addView((View) object); // Move destroyed views to the trash container
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
				return "Modder Team";
				case 1:
				return "Changelog";
				case 2:
				return "Major changes";
				default:
				return null;
			}
		}
	}
	
	
	// RecyclerView$Adapter<T extends RecyclerView.ViewHolder> got obfuscated to
	// RecyclerView$a<VH extends RecyclerView.v>
	// VH stands for ViewHolder?
	private class ModdersRecyclerAdapter extends RecyclerView.Adapter<ModdersRecyclerAdapter.ViewHolder> {
		
		private final ArrayList<HashMap<String, Object>> modders;
		
		public ModdersRecyclerAdapter(ArrayList<HashMap<String, Object>> data) {
			modders = data;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// Inflate the item layout
			View v = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.about_moddersview, parent, false);
			return new ViewHolder(v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			// Bind the data for each item
			HashMap<String, Object> modder = modders.get(position);
			
			// Assign data to the views
			Object modder_img = modder.get("modder_img");
			if (modder_img instanceof String) {
				circularImage(holder.userIcon, (String) modder_img);
			}
			
			Object modder_username = modder.get("modder_username");
			if (modder_username instanceof String) {
				holder.userName.setText((String) modder_username);
			}
			
			Object modder_description = modder.get("modder_description");
			if (modder_description instanceof String) {
				holder.description.setText((String) modder_description);
			}
			
			Object isTitled = modder.get("isTitled");
			boolean isTitle = (isTitled instanceof Boolean) ? (boolean) isTitled :
			(isTitled instanceof String && Boolean.parseBoolean((String) isTitled));
			
			Object titleText = modder.get("title");
			if (isTitle && titleText instanceof String) {
				holder.title.setText((String) titleText);
				holder.title.setVisibility(View.VISIBLE);
			} else {
				holder.title.setVisibility(View.GONE);
			}
			
			Object isMainModder = modder.get("isMainModder");
			boolean isMainModderBool = (isMainModder instanceof Boolean) ? (boolean) isMainModder :
			(isMainModder instanceof String && Boolean.parseBoolean((String) isMainModder));
			
			if (isMainModderBool) {
				advancedCorners(holder.sidebar, "#008DCD");
			} else {
				advancedCorners(holder.sidebar, "#00CDAB");
			}
		}
		
		@Override
		public int getItemCount() {
			return modders.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public TextView title, userName, description;
			public LinearLayout sidebar;
			public ImageView userIcon;
			
			public ViewHolder(View itemView) {
				super(itemView);
				// Initialize the views from the item layout
				title = itemView.findViewById(R.id.tv_title);
				userName = itemView.findViewById(R.id.tv_user_name);
				description = itemView.findViewById(R.id.tv_description);
				sidebar = itemView.findViewById(R.id.view_leftline);
				userIcon = itemView.findViewById(R.id.img_user_icon);
			}
		}
	}
	
	// RecyclerView$Adapter<T extends RecyclerView.ViewHolder> got obfuscated to
	// RecyclerView$a<VH extends RecyclerView.v>
	// VH stands for ViewHolder?
	private class ChangelogRecyclerAdapter extends RecyclerView.Adapter<ChangelogRecyclerAdapter.ViewHolder> {
		
		private static final String CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO = "showingAdditionalInfo";
		private final ArrayList<HashMap<String, Object>> changelog;
		
		public ChangelogRecyclerAdapter(ArrayList<HashMap<String, Object>> data) {
			changelog = data;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View aboutChangelog = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.about_changelog, parent, false);
			return new ViewHolder(aboutChangelog);
		}
		
		@SuppressLint("SetTextI18n")
		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			HashMap<String, Object> release = changelog.get(position);
			
			// Title
			boolean isTitle = Boolean.TRUE.equals(release.get("isTitled"));
			holder.title.setVisibility(isTitle ? View.VISIBLE : View.GONE);
			holder.title.setText((String) release.getOrDefault("title", "No Title"));
			
			// Variant
			boolean isBetaVersion = Boolean.TRUE.equals(release.get("isBeta"));
			holder.variant.setVisibility(isBetaVersion ? View.VISIBLE : View.GONE);
			holder.variant.setText(isBetaVersion ? "Beta" : "Official");
			
			// Release Date
			Object releaseDate = release.get("releaseDate");
			if (releaseDate instanceof Double) {
				holder.releasedOn.setVisibility(View.VISIBLE);
				holder.releasedOn.setText("Released on: " + new SimpleDateFormat("yyyy-MM-dd", Locale.US)
				.format(new Date(((Double) releaseDate).longValue())));
			} else {
				holder.releasedOn.setVisibility(View.GONE);
			}
			
			// Description
			String description = (String) release.getOrDefault("description", "No description available.");
			holder.subtitle.setText(description);
			Linkify.addLinks(holder.subtitle, Linkify.WEB_URLS);
			
			// Additional Info
			boolean showingAdditionalInfo = Boolean.TRUE.equals(release.get(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO));
			holder.viewAdditionalInfo.setVisibility(showingAdditionalInfo ? View.VISIBLE : View.GONE);
			holder.arrow.setRotation(showingAdditionalInfo ? 0 : 180);
			
			holder.logBackground.setOnClickListener(v -> {
				boolean isVisible = !showingAdditionalInfo;
				holder.viewAdditionalInfo.setVisibility(isVisible ? View.VISIBLE : View.GONE);
				holder.arrow.setRotation(isVisible ? 0 : 180);
				release.put(CHANGELOG_KEY_SHOWING_ADDITIONAL_INFO, isVisible);
				notifyItemChanged(position);
			});
			
			
			if (position == 0) {
				advancedCorners(holder.leftLine, "#008dcd");
			} else {
				holder.leftLine.setBackground(null);
			}
		}
		
		@Override
		public int getItemCount() {
			return changelog.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public TextView title, variant, releasedOn, subtitle;
			public LinearLayout leftLine, logBackground, viewAdditionalInfo;
			public ImageButton arrow;
			
			public ViewHolder(View itemView) {
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


/**
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// Replace `YourActivity` with your actual activity name
public class AboutModActivity extends YourActivity {
private TextView loadingTitle, loadingDescription;
private View fab, loading;
private SharedPreferences sharedPref;
private RecyclerView moddersRecycler, changelogRecycler;
private String discordInviteLink;
private List<Modder> moddersList;
private List<Changelog> changelogList;

private void shadAnim(View view, String property, float value, long duration) {
view.animate().setDuration(duration).translationY(value).alpha(value == 0 ? 0 : 1).start();
}

private class FetchDataTask extends AsyncTask<String, Void, String> {
@Override
protected void onPreExecute() {
super.onPreExecute();
loadingTitle.setText("Loading...");
loadingDescription.setText("Fetching data...");
}

@Override
protected String doInBackground(String... urls) {
String response = "";
HttpURLConnection urlConnection = null;
BufferedReader reader = null;

try {
URL url = new URL(urls[0]);
urlConnection = (HttpURLConnection) url.openConnection();
urlConnection.setRequestMethod("GET");
urlConnection.connect();

// Read the response
InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
reader = new BufferedReader(new InputStreamReader(inputStream));

StringBuilder stringBuilder = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
stringBuilder.append(line).append("\n");
}

response = stringBuilder.toString();
} catch (IOException e) {
e.printStackTrace();
} finally {
if (urlConnection != null) {
urlConnection.disconnect();
}
try {
if (reader != null) {
reader.close();
}
} catch (IOException e) {
e.printStackTrace();
}
}
return response;
}

@Override
protected void onPostExecute(String result) {
super.onPostExecute(result);
if (result != null && !result.isEmpty()) {
try {
// Deserialize JSON response to AboutUsData object
GsonBuilder builder = new GsonBuilder();
builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
AboutUsData data = builder.create().fromJson(result, AboutUsData.class);

// Update UI with fetched data
if (data.discordInviteLink != null) {
discordInviteLink = data.discordInviteLink;
}

moddersList = data.moddersteam;
moddersRecycler.setAdapter(new ModdersRecyclerAdapter(moddersList));

changelogList = data.changelog;
changelogRecycler.setAdapter(new ChangelogRecyclerAdapter(changelogList));

// Save data locally
SharedPreferences.Editor savedData = sharedPref.edit();
savedData.putString("discordInviteLinkBackup", discordInviteLink);
savedData.putString("moddersBackup", new Gson().toJson(moddersList));
savedData.putString("changelogBackup", new Gson().toJson(changelogList));
savedData.apply();

// Hide loading animation and show UI
shadAnim(loading, "translationY", -1000, 300);
shadAnim(loading, "alpha", 0, 300);
new Handler().postDelayed(() -> {
shadAnim(fab, "translationY", 0, 300);
shadAnim(fab, "alpha", 1, 300);
}, 200);
} catch (JsonParseException e) {
// Handle JSON parsing errors
loadingTitle.setText("Something went wrong");
loadingDescription.setText("Error: " + e.getMessage());
}
} else {
// Handle error (e.g., no internet connection, invalid response)
loadingTitle.setText("Your device is offline!");
loadingDescription.setText("Check your internet connection and try again.");
}

fab.setVisibility(View.VISIBLE);
}
}
}


**/
