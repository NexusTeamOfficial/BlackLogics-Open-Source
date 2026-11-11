package com.nexusteam.internal.editor;

import com.nexusteam.internal.ex;
import com.nexusteam.internal.fh;
import com.nexusteam.internal.fi;
import com.nexusteam.internal.fj;
import com.nexusteam.internal.kd;
import com.nexusteam.internal.ke;
import com.nexusteam.internal.kf;
import com.nexusteam.internal.ki;
import com.nexusteam.internal.kp;
import com.nexusteam.internal.kq;
import com.nexusteam.internal.ma;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.manager.ComponentManager;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.beans.ActivityFileInfo;
import android.animation.Animator;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.dx.io.Opcodes;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.beans.EventBean;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.internal.editor.component.ComponentAddActivity;
import com.nexusteam.internal.lib.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class ComponentListFragment extends BaseFragment implements View.OnClickListener {
	
	ArrayList<ComponentBean> componentList = new ArrayList<ComponentBean>();
	TextView emptyMessageTextView;
	RecyclerView componentRecyclerView;
	private ProjectFileBean projectFile;
	private ComponentAdapter componentAdapter;
	private boolean isDataLoaded = false;
	private final String componentTag = "component";
	private final int deleteMenuItemId = 4;
	private FloatingActionButton fabAddComponent;
	private String screenId;
	private ComponentManager componentManager;
	private String activityName;
	
	private HashSet<String> loadedComponentKeys = new HashSet<String>();
	
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
		ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fr_component_list, viewGroup, false);
		
		componentManager = ComponentManager.getInstance();
		setHasOptionsMenu(true);
		
		if (bundle != null) {
			this.screenId = bundle.getString("sc_id");
		} else {
			this.screenId = getActivity().getIntent().getStringExtra("sc_id");
		}
		
		BlockStorage.setScId(this.screenId);
		
		ActivityFileInfo qm = getSession();
		if (qm != null) {
			String activity = qm.getActivityName();
			activityName = activity;
			if (activityName != null && activityName.endsWith(".java")) {
				activityName = activityName.substring(0, activityName.length() - 5);
			}
		}
		
		initializeViews(viewGroup2);
		
		return viewGroup2;
	}
	
	private void initializeViews(ViewGroup viewGroup) {
		this.emptyMessageTextView = (TextView) viewGroup.findViewById(R.id.empty_message);
		this.componentRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.component_list);
		this.componentRecyclerView.setHasFixedSize(true);
		this.emptyMessageTextView.setVisibility(8);
		this.emptyMessageTextView.setText(kq.a().a(getContext(), (int) R.string.component_message_no_components));
		this.componentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
		this.componentAdapter = new ComponentAdapter(this.componentRecyclerView);
		this.componentRecyclerView.setAdapter(this.componentAdapter);
		this.fabAddComponent = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
		this.fabAddComponent.setOnClickListener(this);
		initializeBean();
		
		loadComponentsFromBlockStorage();
	}
	
	private void loadComponentsFromBlockStorage() {
		try {
			if (activityName == null || activityName.isEmpty()) {
				android.util.Log.e("ComponentList", "Activity name is null!");
				return;
			}
			
			String cleanActivityName = activityName;
			if (cleanActivityName.endsWith(".java")) {
				cleanActivityName = cleanActivityName.substring(0, cleanActivityName.length() - 5);
			}
			
			Object[] allData = BlockStorage.readAll();
			
			if (allData == null || allData.length <= 2) {
				android.util.Log.d("ComponentList", "No data in BlockStorage");
				return;
			}
			
			HashMap<String, ArrayList<ComponentData>> componentsMap = 
			(HashMap<String, ArrayList<ComponentData>>) allData[2];
			
			if (componentsMap == null) {
				android.util.Log.d("ComponentList", "Components map is null");
				return;
			}
			
			ArrayList<ComponentData> storedComponents = componentsMap.get(cleanActivityName);
			if (storedComponents == null || storedComponents.isEmpty()) {
				android.util.Log.d("ComponentList", "No components found for activity: " + cleanActivityName);
				this.componentList = new ArrayList<ComponentBean>();
				this.componentAdapter.notifyDataSetChanged();
				return;
			}
			
			componentManager.refreshCustomComponents();
			List<ComponentBean> customComponents = componentManager.getCustomComponents();
			
			HashMap<Integer, ComponentBean> customComponentMap = new HashMap<Integer, ComponentBean>();
			HashMap<String, ComponentBean> customComponentByNameMap = new HashMap<String, ComponentBean>();
			for (int i = 0; i < customComponents.size(); i++) {
				ComponentBean cc = customComponents.get(i);
				if (cc.type > 24) {
					customComponentMap.put(cc.type, cc);
				}
				if (cc.typeName != null && !cc.typeName.isEmpty()) {
					customComponentByNameMap.put(cc.typeName, cc);
				}
			}
			
			android.util.Log.d("ComponentList", "Loading " + storedComponents.size() + " components from BlockStorage for " + cleanActivityName);
			
			this.componentList = new ArrayList<ComponentBean>();
			loadedComponentKeys = new HashSet<String>();
			
			for (int i = 0; i < storedComponents.size(); i++) {
				ComponentData data = storedComponents.get(i);
				
				if (data == null) continue;
				
				String key = data.type + "_" + data.mainData;
				if (loadedComponentKeys.contains(key)) {
					android.util.Log.d("ComponentList", "Skipping duplicate: " + key);
					continue;
				}
				loadedComponentKeys.add(key);
				
				ComponentBean bean = new ComponentBean();
				bean.type = data.type;
				bean.componentId = data.mainData;
				
				if (data.type > 24) {
					ComponentBean customBean = null;
					
					if (customComponentMap.containsKey(data.type)) {
						customBean = customComponentMap.get(data.type);
					}
					
					if (customBean == null && data.extraData != null && !data.extraData.isEmpty()) {
						customBean = customComponentByNameMap.get(data.extraData);
					}
					
					if (customBean != null) {
						bean.typeName = customBean.typeName;
						bean.name = customBean.name;
						bean.icon = customBean.icon;
						bean.description = customBean.description;
						bean.classInfo = customBean.classInfo;
						bean.events = customBean.events;
						bean.param1 = data.extraData;
					} else {
						bean.typeName = data.extraData != null ? data.extraData : "Custom Component";
						bean.name = bean.typeName;
						bean.param1 = data.extraData;
					}
				} else {
					bean.typeName = ComponentBean.getComponentTypeName(data.type);
					if (data.type == 22) bean.typeName = "FragmentAdapter";
					else if (data.type == 23) bean.typeName = "Handler";
					else if (data.type == 24) bean.typeName = "Clipboard";
					if (bean.typeName != null && bean.typeName.startsWith("CustomComponent_22")) bean.typeName = "FragmentAdapter";
					if (bean.typeName != null && bean.typeName.startsWith("CustomComponent_23")) bean.typeName = "Handler";
					if (bean.typeName != null && bean.typeName.startsWith("CustomComponent_24")) bean.typeName = "Clipboard";
					bean.name = ComponentBean.getComponentName(getContext(), data.type);
					bean.param1 = data.extraData;
				}
				
				bean.param1 = data.extraData;
				
				android.util.Log.d("ComponentList", "Added component: " + bean.componentId + 
				" type: " + bean.type + " typeName: " + bean.typeName);
				
				this.componentList.add(bean);
			}
			
			this.componentAdapter.notifyDataSetChanged();
			android.util.Log.d("ComponentList", "Components loaded successfully! Total: " + this.componentList.size());
			
		} catch (Exception e) {
			android.util.Log.e("ComponentList", "Error loading components: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void setActivityNameAndRefresh(String name) {
		if (name == null) return;
		activityName = name;
		if (activityName.endsWith(".java")) {
			activityName = activityName.substring(0, activityName.length() - 5);
		}
		try {
			loadedComponentKeys.clear();
		} catch (Throwable ignore) {}
		try {
			if (componentAdapter != null) {
				try {
					java.lang.reflect.Method cm = componentAdapter.getClass().getMethod("clear");
					cm.invoke(componentAdapter);
				} catch (Throwable ignore) {}
			}
		} catch (Throwable ignore) {}
		loadComponentsFromBlockStorage();
		refreshComponentList();
		android.util.Log.i("ComponentList", "switched activity -> " + activityName);
	}
	
	public void refreshComponentList() {
		if (this.componentAdapter != null && activityName != null) {
			loadComponentsFromBlockStorage();
		}
	}
	
	public void resetComponentValues() {
		if (this.projectFile != null) {
			for (int i = 0; i < this.componentList.size(); i++) {
				this.componentList.get(i).initValue();
			}
			this.componentAdapter.notifyDataSetChanged();
		}
	}
	
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putString("sc_id", this.screenId);
		super.onSaveInstanceState(bundle);
	}
	
	public void setProjectFile(ProjectFileBean projectFileBean) {
		this.projectFile = projectFileBean;
	}
	
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
		super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
		if (view.getTag().equals("component")) {
			contextMenu.setHeaderTitle(kq.a().a(getContext(), (int) R.string.component_context_menu_title));
			contextMenu.add(0, 4, 0, kq.a().a(getContext(), (int) R.string.component_context_menu_title_delete_component));
		}
	}
	
	public void onClick(View view) {
		if (!ki.a() && view.getId() == R.id.fab) {
			Intent intent = new Intent(getContext(), ComponentAddActivity.class);
			intent.putExtra("sc_id", this.screenId);
			intent.putExtra("project_file", getFileBean());
			intent.putExtra("qm_session", getSession());
			intent.putExtra("activityName", this.activityName);
			startActivityForResult(intent, Opcodes.SHL_INT_LIT8);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 224 && resultCode == -1) {
			refreshComponentList();
		}
	}
	
	public boolean onContextItemSelected(MenuItem menuItem) {
		if (!getUserVisibleHint()) {
			return false;
		}
		if (menuItem.getItemId() != 4) {
			return true;
		}
		showDeleteConfirmationDialog(this.componentAdapter.currentSelectedPosition);
		return true;
	}
	
	private void showDeleteConfirmationDialog(final int position) {
		final kd dialog = new kd(this.m);
		dialog.a(kq.a().a(getContext(), (int) R.string.component_context_menu_title_delete_component));
		dialog.a((int) R.drawable.delete_96);
		dialog.b(kq.a().a(getContext(), (int) R.string.event_dialog_confirm_delete_component));
		dialog.a(kq.a().a(getContext(), (int) R.string.common_word_delete), new View.OnClickListener() {
			public void onClick(View view) {
				if (position >= 0 && position < ComponentListFragment.this.componentList.size()) {
					ComponentBean componentToDelete = ComponentListFragment.this.componentList.get(position);
					
					try {
						ArrayList<ComponentBean> maList = ma.a(screenId).k(activityName);
						if (maList != null) {
							for (int i = maList.size() - 1; i >= 0; i--) {
								ComponentBean cb = maList.get(i);
								if (cb != null && componentToDelete.componentId != null
								&& componentToDelete.componentId.equals(cb.componentId)) {
									maList.remove(i);
									break;
								}
							}
							ma.a(screenId).c();
						}
					} catch (Throwable t) {
						android.util.Log.e("ComponentList", "Failed to remove from ma: " + t.getMessage());
					}
					
					try {
						BlockStorage.removeComponent(activityName, componentToDelete.componentId);
						if (activityName != null && !activityName.endsWith(".java")) {
							BlockStorage.removeComponent(activityName + ".java", componentToDelete.componentId);
						}
					} catch (Throwable ignore) {}
					try {
						String typeName = ComponentBean.getComponentTypeName(componentToDelete.type);
						String field = componentToDelete.componentId;
						ProjectLogicRepository.removeComponentByField(activityName, field);
						ProjectLogicRepository.removeComponentLogic(activityName, typeName, field);
						if (activityName != null && !activityName.endsWith(".java")) {
							ProjectLogicRepository.removeComponentByField(activityName + ".java", field);
							ProjectLogicRepository.removeComponentLogic(activityName + ".java", typeName, field);
						}
					} catch (Throwable t) {
						android.util.Log.e("ComponentList", "removeComponentLogic", t);
					}
					
					ComponentListFragment.this.componentList.remove(position);
					
					ComponentListFragment.this.sendComponentAnalytics("Remove", componentToDelete.type, 0);
					
					ComponentListFragment.this.componentAdapter.notifyDataSetChanged();
					
					ke.a(ComponentListFragment.this.getContext(), 
					(CharSequence) kq.a().a(ComponentListFragment.this.getContext(), 
					(int) R.string.common_message_complete_delete), 0).show();
				}
				dialog.dismiss();
			}
		});
		dialog.b(kq.a().a(getContext(), (int) R.string.common_word_cancel), new View.OnClickListener() {
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	private void sendComponentAnalytics(String action, int componentType, long value) {
		String componentLabel = getComponentLabelForAnalytics(componentType);
		sendAnalytics("Component", action, componentLabel, value);
	}
	
	private String getComponentLabelForAnalytics(int componentType) {
		if (componentType > 21) {
			ComponentBean customComponent = componentManager.getComponentByType(componentType);
			if (customComponent != null && customComponent.typeName != null) {
				return customComponent.typeName.toUpperCase();
			}
			return "CUSTOM_" + componentType;
		}
		
		switch(componentType) {
			case 1: return "INTENT";
			case 2: return "FILE";
			case 3: return "CALENDAR";
			case 4: return "VIBRATOR";
			case 5: return "TIMER";
			case 6: return "FIREBASE";
			case 7: return "DIALOG";
			case 8: return "MEDIAPLAYER";
			case 9: return "SOUNDPOOL";
			case 10: return "OBJECTANIMATOR";
			case 11: return "GYROSCOPE";
			case 12: return "FIREBASEAUTH";
			case 14: return "FIREBASESTORAGE";
			case 15: return "CAMERA";
			case 16: return "FILEPICKER";
			case 17: return "REQUESTNETWORK";
			case 18: return "TEXTTOSPEECH";
			case 19: return "SPEECHTOTEXT";
			case 20: return "BLUETOOTHCONNECT";
			case 21: return "LOCATIONMANAGER";
			default: return "INTENT";
		}
	}
	
	private void sendAnalytics(String category, String action, String label, long value) {
	}
	
	private void openLogicEditor(String componentId, String eventName, String eventText) {
		if (getActivity() == null) return;
		String ph = "";
		try {
			ph = com.nexusteam.blacklogics.utils.EventUtils.getEventPlaceholder(eventName);
		} catch (Throwable ignore) {}
		if (ph == null) ph = "";
		String eventKey = (ph.length() > 0) ? (eventName + " " + ph) : eventName;
		
		Intent intent = new Intent(getActivity(), LogicEditorActivity.class);
		intent.putExtra("sc_id", this.screenId);
		intent.putExtra("id", componentId);
		intent.putExtra("event", eventKey);
		intent.putExtra("project_file", getFileBean());
		intent.putExtra("activityName", activityName);
		intent.putExtra("event_text", eventText != null ? eventText : eventName);
		try {
			intent.putExtra("qm_session", getSession());
		} catch (Throwable ignore) {}
		startActivity(intent);
	}
	
	class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {
		
		int currentSelectedPosition = -1;
		
		public long getItemId(int i) {
			return (long) i;
		}
		
		public int getItemViewType(int i) {
			return i;
		}
		
		public ComponentAdapter(RecyclerView recyclerView) {
			if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
				recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
					public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
						super.onScrolled(recyclerView, dx, dy);
						if (dy > 2) {
							if (ComponentListFragment.this.fabAddComponent.isEnabled()) {
								ComponentListFragment.this.fabAddComponent.hide();
							}
						} else if (dy < -2 && ComponentListFragment.this.fabAddComponent.isEnabled()) {
							ComponentListFragment.this.fabAddComponent.show();
						}
					}
				});
			}
		}
		
		public ComponentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			return new ComponentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fr_logic_list_item_component, viewGroup, false));
		}
		
		public void onBindViewHolder(final ComponentViewHolder holder, final int position) {
			final ComponentBean component = ComponentListFragment.this.componentList.get(position);
			
			String componentName;
			if (component.type > 24) {
				ComponentBean customComponent = componentManager.getComponentByType(component.type);
				if (customComponent != null && customComponent.typeName != null) {
					componentName = customComponent.typeName;
				} else {
					componentName = (component.typeName != null) ? component.typeName : "Custom Component";
				}
			} else {
				componentName = ComponentBean.getComponentName(ComponentListFragment.this.getContext(), component.type);
			}
			holder.componentTypeTextView.setText(componentName);
			
			int iconRes;
			if (component.type > 24) {
				ComponentBean customComponent = componentManager.getComponentByType(component.type);
				if (customComponent != null && customComponent.icon != null) {
					try {
						iconRes = Integer.parseInt(customComponent.icon);
					} catch (NumberFormatException e) {
						iconRes = R.drawable.widget_module;
					}
				} else {
					iconRes = R.drawable.widget_module;
				}
			} else {
				iconRes = ComponentBean.getIconResource(component.type);
			}
			holder.componentIconImageView.setImageResource(iconRes);
			
			if (component.type == 2) {
				holder.componentIdTextView.setText(component.componentId + " : " + component.param1);
			} else if (component.type == 6 || component.type == 14 || component.type == 16) {
				String param = component.param1;
				if (param == null || param.length() <= 0) {
					param = "/";
				}
				holder.componentIdTextView.setText(component.componentId + " : " + param);
			} else if (component.type > 24) {
				ComponentBean customComponent = componentManager.getComponentByType(component.type);
				if (customComponent != null && customComponent.description != null && !customComponent.description.isEmpty()) {
					holder.componentIdTextView.setText(component.componentId + " : " + customComponent.description);
				} else {
					holder.componentIdTextView.setText(component.componentId);
				}
			} else {
				holder.componentIdTextView.setText(component.componentId);
			}
			
			ArrayList<String> existingEventNames = BlockStorage.loadComponentEvents(component.componentId);
			
			final ArrayList<String> availableEvents = new ArrayList<String>();
			
			if (component.type > 24) {
				ComponentBean customComponent = componentManager.getComponentByType(component.type);
				if (customComponent != null && customComponent.events != null) {
					for (int i = 0; i < customComponent.events.size(); i++) {
						availableEvents.add(customComponent.events.get(i));
					}
				} else {
					availableEvents.add("onCreate");
					availableEvents.add("onStart");
					availableEvents.add("onDestroy");
				}
			} else {
				String[] builtInEvents = ex.b(component.getClassInfo());
				if (builtInEvents != null) {
					for (int i = 0; i < builtInEvents.length; i++) {
						availableEvents.add(builtInEvents[i]);
					}
				}
			}
			
			holder.eventsPreviewLayout.removeAllViews();
			holder.componentEventsLayout.removeAllViews();
			holder.eventsPreviewLayout.setAlpha(1.0f);
			holder.eventsPreviewLayout.setTranslationX(0.0f);
			
			if (component.isCollapsed) {
				holder.componentOptionLayout.setVisibility(8);
				holder.expandCollapseIcon.setRotation(0.0f);
			} else {
				holder.componentOptionLayout.setVisibility(0);
				holder.expandCollapseIcon.setRotation(-180.0f);
				if (component.isConfirmation) {
					holder.confirmationView.a();
				} else {
					holder.confirmationView.b();
				}
			}
			
			holder.componentOptionLayout.getLayoutParams().height = -2;
			
			for (int i = 0; i < existingEventNames.size(); i++) {
				final String eventName = existingEventNames.get(i);
				if (availableEvents.contains(eventName)) {
					LinearLayout eventPreview = (LinearLayout) kp.a(ComponentListFragment.this.getContext(), (int) R.layout.fr_logic_list_item_event_preview);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
					layoutParams.setMargins(0, 0, (int) kp.a(ComponentListFragment.this.getContext(), 4.0f), 0);
					eventPreview.setLayoutParams(layoutParams);
					
					int eventIcon = getEventIcon(eventName);
					((ImageView) eventPreview.findViewById(R.id.icon)).setImageResource(eventIcon);
					((LinearLayout) eventPreview.findViewById(R.id.icon_bg)).setBackgroundResource(R.drawable.circle_bg_white_outline_secondary);
					
					fi eventItem = new fi(ComponentListFragment.this.getContext());
					eventItem.e.setText(eventName);
					eventItem.c.setImageResource(eventIcon);
					eventItem.setClickListener(new View.OnClickListener() {
						public void onClick(View view) {
							if (!ki.a()) {
								ComponentListFragment.this.openLogicEditor(component.componentId, eventName, eventName);
							}
						}
					});
					
					holder.eventsPreviewLayout.addView(eventPreview);
					holder.componentEventsLayout.addView(eventItem);
					availableEvents.remove(eventName);
				}
			}
			
			Iterator<String> it2 = availableEvents.iterator();
			while (it2.hasNext()) {
				final String eventName = it2.next();
				
				LinearLayout eventPreview = (LinearLayout) kp.a(ComponentListFragment.this.getContext(), (int) R.layout.fr_logic_list_item_event_preview);
				LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
				layoutParams2.setMargins(0, 0, (int) kp.a(ComponentListFragment.this.getContext(), 4.0f), 0);
				eventPreview.setLayoutParams(layoutParams2);
				ImageView iconView = (ImageView) eventPreview.findViewById(R.id.icon);
				
				int eventIcon = getEventIcon(eventName);
				iconView.setImageResource(eventIcon);
				
				ColorMatrix colorMatrix = new ColorMatrix();
				colorMatrix.setSaturation(0.0f);
				iconView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
				
				holder.eventsPreviewLayout.addView(eventPreview);
				eventPreview.setScaleX(0.8f);
				eventPreview.setScaleY(0.8f);
				
				final fi eventItem = new fi(ComponentListFragment.this.getContext());
				eventItem.e.setText(eventName);
				eventItem.c.setImageResource(eventIcon);
				eventItem.setClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						if (!ki.a()) {
							BlockStorage.saveComponentEvent(component.componentId, eventName);
							
							ke.a(ComponentListFragment.this.getContext(), 
							(CharSequence) kq.a().a(ComponentListFragment.this.getContext(), 
							(int) R.string.event_message_new_event), 0).show();
							holder.confirmationView.b();
							ComponentAdapter.this.notifyItemChanged(ComponentAdapter.this.currentSelectedPosition);
						}
					}
				});
				
				holder.componentEventsLayout.addView(eventItem);
			}
		}
		
		private int getEventIcon(String eventName) {
			return ex.b(eventName);
		}
		
		public int getItemCount() {
			int size = ComponentListFragment.this.componentList.size();
			if (size == 0) {
				ComponentListFragment.this.emptyMessageTextView.setVisibility(0);
			} else {
				ComponentListFragment.this.emptyMessageTextView.setVisibility(8);
			}
			return size;
		}
		
		class ComponentViewHolder extends RecyclerView.ViewHolder {
			
			public ImageView componentIconImageView;
			public TextView componentTypeTextView;
			public TextView componentIdTextView;
			public ImageView expandCollapseIcon;
			public fh confirmationView;
			public LinearLayout eventsPreviewLayout;
			public LinearLayout componentOptionContainer;
			public LinearLayout componentOptionLayout;
			public LinearLayout componentEventsLayout;
			
			public ComponentViewHolder(View itemView) {
				super(itemView);
				this.componentIconImageView = (ImageView) itemView.findViewById(R.id.img_icon);
				this.componentTypeTextView = (TextView) itemView.findViewById(R.id.tv_component_type);
				this.componentIdTextView = (TextView) itemView.findViewById(R.id.tv_component_id);
				this.expandCollapseIcon = (ImageView) itemView.findViewById(R.id.img_menu);
				this.eventsPreviewLayout = (LinearLayout) itemView.findViewById(R.id.events_preview);
				this.componentOptionLayout = (LinearLayout) itemView.findViewById(R.id.component_option_layout);
				this.componentOptionContainer = (LinearLayout) itemView.findViewById(R.id.component_option);
				this.componentEventsLayout = (LinearLayout) itemView.findViewById(R.id.component_events);
				
				this.confirmationView = new fh(ComponentListFragment.this.getContext());
				this.confirmationView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
				this.confirmationView.m.d.setText(kq.a().a(ComponentListFragment.this.getContext(), (int) R.string.component_context_menu_title_delete_component));
				this.componentOptionContainer.addView(this.confirmationView);
				
				this.confirmationView.setButtonOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						currentSelectedPosition = ComponentViewHolder.this.getLayoutPosition();
						if (currentSelectedPosition < 0 || currentSelectedPosition >= ComponentListFragment.this.componentList.size()) {
							return;
						}
						ComponentBean component = ComponentListFragment.this.componentList.get(currentSelectedPosition);
						
						if (view instanceof fj) {
							component.isConfirmation = true;
							notifyItemChanged(currentSelectedPosition);
							return;
						}
						
						int id = view.getId();
						if (id == R.id.confirm_no) {
							component.isConfirmation = false;
							notifyItemChanged(currentSelectedPosition);
						} else if (id == R.id.confirm_yes) {
							try {
								ArrayList<ComponentBean> maList = ma.a(screenId).k(activityName);
								if (maList != null) {
									for (int i = maList.size() - 1; i >= 0; i--) {
										ComponentBean cb = maList.get(i);
										if (cb != null && component.componentId != null
										&& component.componentId.equals(cb.componentId)) {
											maList.remove(i);
											break;
										}
									}
									ma.a(screenId).c();
								}
							} catch (Throwable t) {
								android.util.Log.e("ComponentList", "Failed to remove from ma: " + t.getMessage());
							}
							
							BlockStorage.removeComponent(
							activityName, 
							component.componentId
							);
							
							ComponentListFragment.this.componentList.remove(currentSelectedPosition);
							
							component.isConfirmation = false;
							notifyItemRemoved(currentSelectedPosition);
							notifyItemRangeChanged(currentSelectedPosition, getItemCount());
							ComponentListFragment.this.fabAddComponent.show();
						}
					}
				});
				
				itemView.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						currentSelectedPosition = ComponentViewHolder.this.getLayoutPosition();
						if (currentSelectedPosition < 0 || currentSelectedPosition >= ComponentListFragment.this.componentList.size()) {
							return;
						}
						ComponentBean component = ComponentListFragment.this.componentList.get(currentSelectedPosition);
						
						if (component.isCollapsed) {
							component.isCollapsed = false;
							ComponentViewHolder.this.expandComponent();
						} else {
							component.isCollapsed = true;
							ComponentViewHolder.this.collapseComponent();
						}
					}
				});
				
				this.expandCollapseIcon.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						currentSelectedPosition = ComponentViewHolder.this.getLayoutPosition();
						if (currentSelectedPosition < 0 || currentSelectedPosition >= ComponentListFragment.this.componentList.size()) {
							return;
						}
						ComponentBean component = ComponentListFragment.this.componentList.get(currentSelectedPosition);
						if (component == null) {
							return;
						}
						
						if (component.isCollapsed) {
							component.isCollapsed = false;
							ComponentViewHolder.this.expandComponent();
						} else {
							component.isCollapsed = true;
							ComponentViewHolder.this.collapseComponent();
						}
					}
				});
				
				itemView.setOnLongClickListener(new View.OnLongClickListener() {
					public boolean onLongClick(View view) {
						currentSelectedPosition = ComponentViewHolder.this.getLayoutPosition();
						if (currentSelectedPosition < 0 || currentSelectedPosition >= ComponentListFragment.this.componentList.size()) {
							return false;
						}
						ComponentBean component = ComponentListFragment.this.componentList.get(currentSelectedPosition);
						
						if (component.isCollapsed) {
							component.isCollapsed = false;
							ComponentViewHolder.this.expandComponent();
						} else {
							component.isCollapsed = true;
							ComponentViewHolder.this.collapseComponent();
						}
						return true;
					}
				});
			}
			
			public void expandComponent() {
				this.componentOptionLayout.setVisibility(0);
				kf.a((View) this.expandCollapseIcon, -180.0f, (Animator.AnimatorListener) null);
				kf.a((ViewGroup) this.componentOptionLayout, 200, (Animator.AnimatorListener) null);
				
				this.eventsPreviewLayout.animate().translationX((float) this.eventsPreviewLayout.getWidth()).alpha(0.0f).setDuration(150).start();
				
				this.componentEventsLayout.setTranslationX((float) (-this.componentEventsLayout.getWidth()));
				this.componentEventsLayout.setAlpha(0.0f);
				this.componentEventsLayout.animate().translationX(0.0f).setStartDelay(200).setDuration(120).alpha(1.0f).start();
			}
			
			public void collapseComponent() {
				kf.a((View) this.expandCollapseIcon, 0.0f, (Animator.AnimatorListener) null);
				kf.b(this.componentOptionLayout, 200, new Animator.AnimatorListener() {
					public void onAnimationCancel(Animator animator) {}
					public void onAnimationRepeat(Animator animator) {}
					public void onAnimationStart(Animator animator) {}
					public void onAnimationEnd(Animator animator) {
						ComponentViewHolder.this.componentOptionLayout.setVisibility(8);
					}
				});
				
				this.eventsPreviewLayout.animate().translationX(0.0f).alpha(1.0f).setStartDelay(120).setDuration(150).start();
				
				this.componentEventsLayout.animate().translationX((float) (-this.componentEventsLayout.getWidth())).setDuration(150).alpha(0.0f).start();
			}
		}
	}
	
	private void initializeBean() {
		ProjectFileBean bean = com.nexusteam.blacklogics.bean.AppState.getCurrentFile();
		if (bean != null) {
			setProjectFile(bean);
		}
	}
	
	private ActivityFileInfo getSession() {
		if (getActivity() instanceof com.nexusteam.blacklogics.lib.base.BaseActivity) {
			return ((com.nexusteam.blacklogics.lib.base.BaseActivity) getActivity()).activitySession;
		}
		return null;
	}
	
	private void sendComponentAnalytics(String targetId, String eventName, String eventText) {
	}
	
	private String getJavaName() {
		return activityName + ".java";
	}
	
	private com.nexusteam.internal.beans.ProjectFileBean getFileBean() {
		if (getActivity() instanceof com.nexusteam.blacklogics.lib.base.BaseActivity) {
			return ((com.nexusteam.blacklogics.lib.base.BaseActivity) getActivity()).fileBean;
		}
		return null;
	}
}
