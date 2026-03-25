package com.nexusteam.blacklogics;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.*;
import androidx.constraintlayout.widget.*;
import androidx.core.*;
import androidx.customview.*;
import androidx.draganddrop.*;
import androidx.dynamicanimation.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.multidex.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.airbnb.viewmodeladapter.*;
import com.bumptech.glide.*;
import com.bumptech.glide.gifdecoder.*;
import com.example.myapp.*;
import com.github.angads25.filepicker.*;
import com.google.android.flexbox.*;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.*;
import com.google.android.material.button.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.*;
import com.googlecode.d2j.*;
import com.larswerkman.holocolorpicker.*;
import com.squareup.leakcanary.*;
import de.hdodenhof.circleimageview.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.textmate.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import b.b.b.Qm;
import b.b.b.Qf;
import com.besome.blacklogics.logic.editor.LogicEditorActivity;
import com.besome.blacklogics.beans.ProjectActivityBean;
import com.besome.blacklogics.project.*;
import com.besome.blacklogics.model.ComponentData;
import com.besome.blacklogics.util.ComponentList;
import com.nexusteam.internal.os.layouteditor.util.TheBlockLogicsUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

public class ComponentFragmentActivity extends Fragment {
	
	private FloatingActionButton _fab;
	public ComponentFragmentActivity.BaseComponentRecyclerAdapter adapter;
	private boolean t = false;
	public ArrayList<HashMap<String, Object>> componentData = new ArrayList();
	private boolean isDeleteMode = false;
	public ComponentFragmentActivity activity;
	public static ComponentFragmentActivity componentFragmentActivity;
	private String componentName = "";
	private String fieldName = "";
	private String activityName = "";
	
	private LinearLayout baseComponentLayout;
	private ScrollView emptyStateView;
	private RecyclerView baseComponentRecycler;
	private LinearLayout linear10;
	private FrameLayout frame_layout11;
	private TextView textview7;
	private TextView textview8;
	private LinearLayout linear13;
	private CardView tipsCard;
	private View view12;
	private ImageView imageview2;
	private MaterialButton addFirstComponentBtn;
	private TextView learnMoreBtn;
	private LinearLayout linear14;
	private TextView textview9;
	private TextView textview10;
	private TextView textview11;
	private TextView textview12;
	
	private Intent intent = new Intent();
	private SharedPreferences prefs;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.component_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		componentFragmentActivity = this;
		_fab = _view.findViewById(R.id._fab);
		baseComponentLayout = _view.findViewById(R.id.baseComponentLayout);
		emptyStateView = _view.findViewById(R.id.emptyStateView);
		baseComponentRecycler = _view.findViewById(R.id.baseComponentRecycler);
		linear10 = _view.findViewById(R.id.linear10);
		frame_layout11 = _view.findViewById(R.id.frame_layout11);
		textview7 = _view.findViewById(R.id.textview7);
		textview8 = _view.findViewById(R.id.textview8);
		linear13 = _view.findViewById(R.id.linear13);
		tipsCard = _view.findViewById(R.id.tipsCard);
		view12 = _view.findViewById(R.id.view12);
		imageview2 = _view.findViewById(R.id.imageview2);
		addFirstComponentBtn = _view.findViewById(R.id.addFirstComponentBtn);
		learnMoreBtn = _view.findViewById(R.id.learnMoreBtn);
		linear14 = _view.findViewById(R.id.linear14);
		textview9 = _view.findViewById(R.id.textview9);
		textview10 = _view.findViewById(R.id.textview10);
		textview11 = _view.findViewById(R.id.textview11);
		textview12 = _view.findViewById(R.id.textview12);
		prefs = getContext().getSharedPreferences("AppSettings", Activity.MODE_PRIVATE);
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				showComponentDialog();
			}
		});
	}
	
	private void initializeLogic() {
		Qm qm = getSession();
		String activity = "";
		String layout = "";
		if (qm != null) {
			activity = qm.getActivityName();
			layout = qm.getLayoutName();
			activityName = activity;
		}
		this.c();
		_updateEmptyState();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if (activityName != null && !activityName.isEmpty()) {
			c();
		}
	}
	public void _a() {
	}
	public void c() {
		if (getActivity() == null) {
			return;
		}
		List<HashMap<String, String>> components = Qf.loadComponentLogic(activityName);
		componentData.clear();
		for (HashMap<String, String> component : components) {
			HashMap<String, Object> data = new HashMap<>();
			data.put("componentName", component.get("componentName"));
			data.put("fieldName", component.get("fieldName"));
			componentData.add(data);
		}
		
		baseComponentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new BaseComponentRecyclerAdapter(componentData);
		baseComponentRecycler.setAdapter(adapter);
		
		_updateEmptyState();
	}
	
	private void showComponentDialog() {
		AddComponentDialog dialog = new AddComponentDialog(getActivity(), 
		new AddComponentDialog.OnComponentSelectedListener() {
			@Override
			public void onComponentSelected(ComponentData component) {
				showComponentDetailsDialog(component);
			}
		});
		dialog.show();
	}
	
	private void showComponentDetailsDialog(final ComponentData component) {
		android.view.LayoutInflater inflater = getActivity().getLayoutInflater();
		android.view.View dialogView = inflater.inflate(R.layout.component_details_dialog, null);
		
		final android.widget.TextView name = dialogView.findViewById(R.id.dialog_title);
		final android.widget.TextView description = dialogView.findViewById(R.id.dialog_description);
		final android.widget.EditText fieldNameInput = dialogView.findViewById(R.id.field_name_input);
		final android.widget.Button saveButton = dialogView.findViewById(R.id.add_button);
		final android.widget.Button docButton = dialogView.findViewById(R.id.doc_button);
		
		fieldNameInput.setFocusable(true);
		fieldNameInput.setFocusableInTouchMode(true);
		fieldNameInput.setClickable(true);
		
		name.setText(component.getName());
		description.setText(component.getDescription());
		
		com.google.android.material.dialog.MaterialAlertDialogBuilder builder =
		new com.google.android.material.dialog.MaterialAlertDialogBuilder(getActivity());
		builder.setView(dialogView);
		final androidx.appcompat.app.AlertDialog dialog = builder.create();
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fieldName = fieldNameInput.getText().toString().trim();
				if (fieldName.isEmpty()) {
					TheBlockLogicsUtil.showToast(getActivity(), "Please enter a field name");
				} else if (!fieldName.matches("^[a-z][a-zA-Z0-9]*$")) {
					TheBlockLogicsUtil.showToast(getActivity(), "Field name must start with a lowercase letter and contain only letters and numbers");
				} else {
					Qf.saveComponentLogic(
					activityName,
					component.getName(),
					fieldName
					);
					java.util.HashMap<String, Object> data = new java.util.HashMap<>();
					data.put("componentName", component.getName());
					data.put("fieldName", fieldName);
					componentData.add(data);
					adapter.notifyDataSetChanged();
					_updateEmptyState();
					dialog.dismiss();
					TheBlockLogicsUtil.showToast(getActivity(), "Component added successfully");
				}
			}
		});
		
		docButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				android.content.Intent intent = new android.content.Intent(
				android.content.Intent.ACTION_VIEW,
				android.net.Uri.parse(component.getDocumentationUrl()));
				startActivity(intent);
			}
		});
		
		dialog.show();
	}
	
	private List<String> getComponentEvents(String componentName) {
		List<String> events = new ArrayList<>();
		switch (componentName) {
			case "Dialog":
			events.add("show");
			events.add("onDismiss");
			events.add("onCancel");
			break;
			case "Intent":
			events.add("onActivityResult");
			break;
			case "ObjectAnimator":
			events.add("onAnimationStart");
			events.add("onAnimationEnd");
			events.add("onAnimationCancel");
			events.add("onAnimationRepeat");
			break;
			case "Timer":
			events.add("onTick");
			events.add("onFinish");
			break;
			case "Notification":
			events.add("onNotificationPosted");
			events.add("onNotificationRemoved");
			break;
			case "AsyncTask":
			events.add("onPreExecute");
			events.add("onProgressUpdate");
			events.add("onPostExecute");
			events.add("onCancelled");
			break;
			case "Handler":
			events.add("handleMessage");
			break;
			case "Service":
			events.add("onCreate");
			events.add("onStartCommand");
			events.add("onBind");
			events.add("onDestroy");
			break;
			case "BroadcastReceiver":
			events.add("onReceive");
			break;
			case "ViewModel":
			events.add("onCleared");
			break;
			case "LiveData":
			events.add("onActive");
			events.add("onInactive");
			break;
			case "WorkManager":
			events.add("onWorkStateChanged");
			break;
			case "MediaPlayer":
			events.add("onPrepared");
			events.add("onCompletion");
			events.add("onError");
			break;
			case "Camera":
			case "Camera2 API":
			events.add("onPictureTaken");
			events.add("onVideoRecorded");
			break;
			case "FilePicker":
			case "Storage Access Framework":
			events.add("onFileSelected");
			break;
			case "LocationManager":
			events.add("onLocationChanged");
			events.add("onProviderEnabled");
			events.add("onProviderDisabled");
			break;
			case "SensorManager":
			events.add("onSensorChanged");
			events.add("onAccuracyChanged");
			break;
			case "BluetoothAdapter":
			events.add("onBluetoothStateChanged");
			events.add("onDeviceFound");
			break;
			case "AlarmManager":
			events.add("onAlarmTriggered");
			break;
			case "JobScheduler":
			events.add("onJobFinished");
			break;
			default:
			//	events.add("onClick");
			break;
		}
		return events;
	}
	
	private void handleEventSelection(String componentName, String fieldName, String eventName) {
		if (getActivity() instanceof DesignActivity) {
			DesignActivity designActivity = (DesignActivity) getActivity();
			Intent intent = new Intent(getContext().getApplicationContext(), LogicEditorActivity.class);
			
			intent.putExtra("id", eventName);
			intent.putExtra("event_text", fieldName);
			intent.putExtra("filename", fieldName + "_" + eventName);
			intent.putExtra("sc_id", designActivity.getScId());
			intent.putExtra("activityName", ProjectDataHelper.getActivityName(getContext().getApplicationContext()));
			intent.putExtra("widgetid", "");
			intent.putExtra("type", fieldName);
			
			// Custom event extras based on event name
			String eventExtra = "";
			
			switch (eventName) {
				case "onActivityResult":
				eventExtra = "%d.requestcode %d.resultcode %m.intent";
				break;
				case "onClick":
				eventExtra = "%m.view";
				break;
				case "onFileSelected":
				eventExtra = "%s.path";
				break;
				case "onLocationChanged":
				eventExtra = "%d.latitude %d.longitude %d.altitude";
				break;
				case "onSensorChanged":
				eventExtra = "%s.sensorname %d.x %d.y %d.z";
				break;
				case "onReceive":
				eventExtra = "%m.intent";
				break;
				case "onNotificationPosted":
				eventExtra = "%s.package %s.title %s.text";
				break;
				case "onTick":
				eventExtra = "%d.remainingtime";
				break;
				case "onPrepared":
				case "onCompletion":
				case "onError":
				eventExtra = "%m.mediaplayer";
				break;
				case "onPictureTaken":
				eventExtra = "%s.imagepath";
				break;
				case "onVideoRecorded":
				eventExtra = "%s.videopath";
				break;
				case "onJobFinished":
				eventExtra = "%d.jobid %b.needsreschedule";
				break;
				case "onStartCommand":
				eventExtra = "%m.intent %d.flags %d.startid";
				break;
				default:
				eventExtra = ""; // No extra
				break;
			}
			
			intent.putExtra("event", eventExtra);
			startActivity(intent);
		}
	}
	
	private Qm getSession() {
		if (getActivity() instanceof com.besome.blacklogics.lib.base.BaseActivity) {
			return ((com.besome.blacklogics.lib.base.BaseActivity) getActivity()).activitySession;
		}
		return null;
	}
	public void refreshUi() {
		Qm qm = getSession();
		String activity = "";
		String layout = "";
		if (qm != null) {
			activity = qm.getActivityName();
			layout = qm.getLayoutName();
			activityName = activity;
		}
	}
	
	{
	}
	
	
	public void _updateEmptyState() {
		if (!isAdded() || getView() == null) {
			return;
		}
		
		View view = getView();
		View emptyStateView = view.findViewById(R.id.emptyStateView);
		RecyclerView recyclerView = view.findViewById(R.id.baseComponentRecycler);
		
		// Safety check - if views aren't found, return
		if (emptyStateView == null || recyclerView == null) {
			return;
		}
		
		if (componentData == null || componentData.isEmpty()) {
			emptyStateView.setVisibility(View.VISIBLE);
			recyclerView.setVisibility(View.GONE);
			
			// Setup empty state click listeners
			View addFirstBtn = view.findViewById(R.id.addFirstComponentBtn);
			View learnMoreBtn = view.findViewById(R.id.learnMoreBtn);
			final View tipsCard = view.findViewById(R.id.tipsCard);
			
			if (addFirstBtn != null) {
				addFirstBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showComponentDialog();
					}
				});
			}
			
			if (learnMoreBtn != null && tipsCard != null) {
				learnMoreBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						tipsCard.setVisibility(tipsCard.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
					}
				});
			}
			
			// Add animation
			_animateEmptyState();
			
		} else {
			emptyStateView.setVisibility(View.GONE);
			recyclerView.setVisibility(View.VISIBLE);
		}
		
	}
	
	
	public void _animateEmptyState() {
		if (!isAdded() || getView() == null) {
			return;
		}
		
		View emptyState = getView().findViewById(R.id.emptyStateView);
		if (emptyState == null) {
			return;
		}
		
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(emptyState, "scaleX", 0.9f, 1.0f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(emptyState, "scaleY", 0.9f, 1.0f);
		ObjectAnimator alpha = ObjectAnimator.ofFloat(emptyState, "alpha", 0f, 1f);
		
		AnimatorSet set = new AnimatorSet();
		set.playTogether(scaleX, scaleY, alpha);
		set.setDuration(300);
		set.setInterpolator(new AccelerateDecelerateInterpolator());
		set.start();
		
	}
	
	public class BaseComponentRecyclerAdapter extends RecyclerView.Adapter<BaseComponentRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public BaseComponentRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.component_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView base = _view.findViewById(R.id.base);
			final HorizontalScrollView hscroll2 = _view.findViewById(R.id.hscroll2);
			final com.google.android.material.card.MaterialCardView baseComponentDeleteUi = _view.findViewById(R.id.baseComponentDeleteUi);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final com.google.android.material.imageview.ShapeableImageView baseComponentIcon = _view.findViewById(R.id.baseComponentIcon);
			final LinearLayout baseLayout = _view.findViewById(R.id.baseLayout);
			final com.google.android.material.button.MaterialButton baseComponentMoreOptions = _view.findViewById(R.id.baseComponentMoreOptions);
			final TextView baseComponentName = _view.findViewById(R.id.baseComponentName);
			final TextView baseComponentSubName = _view.findViewById(R.id.baseComponentSubName);
			final LinearLayout baseOptions = _view.findViewById(R.id.baseOptions);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final ImageView baseDeleteIcon = _view.findViewById(R.id.baseDeleteIcon);
			final TextView baseDeleteText = _view.findViewById(R.id.baseDeleteText);
			
			final ArrayList<HashMap<String, Object>> data = _data;
			final int position= _position;
			final HashMap<String, Object> component = data.get(position);
			
			final String componentName =
			String.valueOf(component.get("componentName"));
			final String fieldName =
			String.valueOf(component.get("fieldName"));
			
			final boolean isDeleteMode =
			Boolean.TRUE.equals(component.get("deleteMode"));
			
			baseComponentName.setText(componentName);
			baseComponentSubName.setText(fieldName);
			
			baseComponentDeleteUi.setVisibility(
			isDeleteMode ? View.VISIBLE : View.GONE
			);
			
			baseComponentIcon.setImageResource(
			isDeleteMode
			? R.drawable.icon_arrow_drop_down_sharp
			: R.drawable.icon_arrow_drop_up_sharp
			);
			
			/* ---------- DELETE CLICK ---------- */
			baseComponentDeleteUi.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final Context ctx = v.getContext();
					final int pos = _position;
					
					if (pos == RecyclerView.NO_POSITION) {
						return;
					}
					
					new AlertDialog.Builder(ctx)
					.setTitle("Remove Component")
					.setMessage(
					"Are you sure you want to remove "
					+ componentName + " (" + fieldName + ")?"
					)
					.setPositiveButton(
					"Remove",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
						DialogInterface _dialog,
						int _which) {
							
							DesignActivity.removeComponentLogic(
							activityName,
							componentName,
							fieldName
							);
							
							data.remove(pos);
							notifyItemRemoved(pos);
							
							TheBlockLogicsUtil.showToast(
							ctx,
							"Component removed successfully"
							);
						}
					}
					)
					.setNegativeButton("Cancel", null)
					.show();
				}
			}
			);
			
			/* ---------- MORE OPTIONS ---------- */
			baseComponentMoreOptions.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					int pos = _position;
					if (pos == RecyclerView.NO_POSITION) return;
					
					boolean newMode = !Boolean.TRUE.equals(
					component.get("deleteMode")
					);
					
					component.put("deleteMode", newMode);
					notifyItemChanged(pos);
				}
			});
			
			//baseOptions.removeViewsInLayout(2, baseOptions.getChildCount() - 2); // Assuming first two children are existing UI elements
			baseOptions.removeAllViews(); 
			// Get the list of events for the component
			List<String> events = getComponentEvents(componentName);
			
			// Programmatically add a button for each event
			for (final String event : events) {
				// Create professional MaterialButton
				com.google.android.material.button.MaterialButton eventButton = new com.google.android.material.button.MaterialButton(getActivity(), null, com.google.android.material.R.attr.materialButtonStyle);
				eventButton.setText(event);
				
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
				params.setMargins(8, 4, 8, 4);
				eventButton.setLayoutParams(params);
				
				// Material styling
				eventButton.setCornerRadius(32); // Rounded corners
				eventButton.setRippleColorResource(R.color.rippleColor); // Define in colors.xml
				eventButton.setBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.colorPrimaryVariant)));
				eventButton.setTextColor(getActivity().getResources().getColor(R.color.white));
				
				eventButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
				eventButton.setPadding(24, 12, 24, 12);
				eventButton.setAllCaps(false);
				eventButton.setTag("eventButton_" + event);
				
				// Click listener
				eventButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						handleEventSelection(componentName, fieldName, event);
					}
				});
				
				
				baseOptions.addView(eventButton);
			}
			
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}