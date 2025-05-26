/*
* Copyright (c) 2025 NexusTeam & SmartIndiaGaming
* All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Authors: NexusTeam, SmartIndiaGaming
* Description: LogicEditorActivity provides a visual programming interface for creating
* and editing logic blocks in the BlackLogics application.
*/

package com.besome.blacklogics.editor;

//import com.my.newproject4.MainmenuActivity;
import com.besome.blacklogics.R;
import com.besome.blacklogics.development.Complex;
import com.besome.blacklogics.custom_blocks.CustomBlocksDialog;

import com.besome.blacklogics.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.HorizontalScrollView;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;
import android.Manifest;
import android.content.pm.PackageManager;

public class LogicEditorActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private Complex complex;
	private static String sc_id;
	
	private HashMap<String, Object> list_keys = new HashMap<>();
	private String code_save_path = "";
	private String pl_path = "";
	private double pos = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private String blocks_save_path = "";
	private double r_id = 0;
	private String log = "";
	private double undo_redo_pos = 0;
	private String collections_path = "";
	private String event_name = "";
	private String custom_blocks_path = "";
	private HashMap<String, Object> allBlocksData = new HashMap<>(); // Store all blocks by event name
	private  Object[] myObj = new Object[100];
	
	private  ArrayList<View> VA = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> pll = new ArrayList<>();
	private  ArrayList<View> linears = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> undo_redo_listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> collectionsM = new ArrayList<>();
	
	private LinearLayout ddd;
	private LinearLayout B_andPsL;
	private LinearLayout bottom;
	private ImageView imageview1;
	private ScrollView vscroll4;
	private ImageView undo;
	private ImageView redo;
	private ImageView imageview2;
	private HorizontalScrollView hscroll3;
	private LinearLayout saveLin;
	private HorizontalScrollView blocksV;
	private LinearLayout pl_LM;
	private ScrollView vscroll1;
	private LinearLayout main;
	private LinearLayout shadow;
	private ScrollView vscroll2;
	private ScrollView vscroll3;
	private HorizontalScrollView blocks_pos;
	private LinearLayout blocks_lin;
	private LinearLayout palletes;
	private ImageView delete;
	private LinearLayout linear5;
	private ImageView collection;
	
	private TimerTask timer;
	private AlertDialog.Builder dialog;
	private AlertDialog di;
	private ObjectAnimator objE = new ObjectAnimator();
	
	// Assuming these are existing fields in LogicEditorActivity
	private CustomBlocksDialog customBlocksDialog;
	private ArrayList<HashMap<String, Object>> liK = new ArrayList<>();
	
	public static class EventCodeManager {
		private static final String EVENT_FILE_PATH = new File(
		Environment.getExternalStorageDirectory(), ".blacklogics/data/" + sc_id + "/event_codes.json"
		).getAbsolutePath();
		
		// Load existing event-code map from file
		private static HashMap<String, String> loadEventCodes() {
			if (FileUtil.isExistFile(EVENT_FILE_PATH) && !FileUtil.readFile(EVENT_FILE_PATH).isEmpty()) {
				return new Gson().fromJson(
				FileUtil.readFile(EVENT_FILE_PATH),
				new TypeToken<HashMap<String, String>>(){}.getType()
				);
			}
			return new HashMap<>();
		}
		
		// Save event-code map to file
		private static void saveEventCodes(HashMap<String, String> eventCodes) {
			FileUtil.writeFile(EVENT_FILE_PATH, new Gson().toJson(eventCodes));
		}
		
		// Add or update event code
		public static void addOrUpdateEventCode(String eventName, String code) {
			HashMap<String, String> eventCodes = loadEventCodes();
			// Replace or add new code for the event
			eventCodes.put(eventName, code);
			saveEventCodes(eventCodes);
		}
		
		// Get code for an event
		public static String getEventCode(String eventName) {
			HashMap<String, String> eventCodes = loadEventCodes();
			return eventCodes.getOrDefault(eventName, "");
		}
	}
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.logic_editor);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
			||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
			} else {
				initializeLogic();
			}
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		ddd = findViewById(R.id.ddd);
		B_andPsL = findViewById(R.id.B_andPsL);
		bottom = findViewById(R.id.bottom);
		imageview1 = findViewById(R.id.imageview1);
		vscroll4 = findViewById(R.id.vscroll4);
		undo = findViewById(R.id.undo);
		redo = findViewById(R.id.redo);
		imageview2 = findViewById(R.id.imageview2);
		hscroll3 = findViewById(R.id.hscroll3);
		saveLin = findViewById(R.id.saveLin);
		blocksV = findViewById(R.id.blocksV);
		pl_LM = findViewById(R.id.pl_LM);
		vscroll1 = findViewById(R.id.vscroll1);
		main = findViewById(R.id.main);
		shadow = findViewById(R.id.shadow);
		vscroll2 = findViewById(R.id.vscroll2);
		vscroll3 = findViewById(R.id.vscroll3);
		blocks_pos = findViewById(R.id.blocks_pos);
		blocks_lin = findViewById(R.id.blocks_lin);
		palletes = findViewById(R.id.palletes);
		delete = findViewById(R.id.delete);
		linear5 = findViewById(R.id.linear5);
		collection = findViewById(R.id.collection);
		dialog = new AlertDialog.Builder(this);
		complex = new Complex();
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (log.replace("\n", "").equals("")) {
					imageview1.setVisibility(View.INVISIBLE);
				}
				else {
					dialog.setMessage(log);
					dialog.create().show();
					dialog.setCancelable(false);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
				}
				imageview1.setVisibility(View.INVISIBLE);
			}
		});
		
		undo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				undo_redo_pos--;
				try {
					ArrayList<HashMap<String, Object>> saveM = new ArrayList<>();
					saveM = new Gson().fromJson(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					main.removeAllViews();
					main.setId((int)0);
					_load_from(saveM, main);
					try {
						((LinearLayout) main).getChildAt((int) 0).setOnLongClickListener(new View.OnLongClickListener(){
							@Override
							public boolean onLongClick(View _view){
								
								return false;
							}
						});
					} catch(Exception gb) {
						
					}
					_check_About_Undo_Redo();
					if (log.replace("\n", "").equals("")) {
						imageview1.setVisibility(View.INVISIBLE);
					}
					else {
						imageview1.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					undo_redo_pos++;
				}
			}
		});
		
		redo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				undo_redo_pos++;
				try {
					ArrayList<HashMap<String, Object>> saveM = new ArrayList<>();
					saveM = new Gson().fromJson(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					main.removeAllViews();
					main.setId((int)0);
					_load_from(saveM, main);
					try {
						((LinearLayout) main).getChildAt((int) 0).setOnLongClickListener(new View.OnLongClickListener(){
							@Override
							public boolean onLongClick(View _view){
								
								return false;
							}
						});
					} catch(Exception gb) {
						
					}
					_check_About_Undo_Redo();
					if (log.replace("\n", "").equals("")) {
						imageview1.setVisibility(View.INVISIBLE);
					}
					else {
						imageview1.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					undo_redo_pos--;
				}
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog di = new AlertDialog.Builder(LogicEditorActivity.this,android.R.style.Theme_Material_Light_NoActionBar).create();
				LayoutInflater diLI = getLayoutInflater();
				View diCV = (View) diLI.inflate(R.layout.collections_drawer, null);
				di.setView(diCV);
				final LinearLayout linear = (LinearLayout)
				diCV.findViewById(R.id.linear);
				final LinearLayout show = (LinearLayout)
				diCV.findViewById(R.id.show);
				final ImageView close = (ImageView)
				diCV.findViewById(R.id.close);
				di.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				di.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				di.show();
				close.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						di.dismiss();
					}
				});
				final starlistview cl = new starlistview(LogicEditorActivity.this);
				cl.custom_view = R.layout.collections_csv;
				cl.setInterface(
				new starlistview.OnBlindCustomView() {
					@Override 
					public void OnBlind(int id,final int _position,final starlistview t8888){
						LayoutInflater uLI = getLayoutInflater();
						View uCV = (View) uLI.inflate(t8888.custom_view, null);
						final TextView name = (TextView)
						uCV.findViewById(R.id.name);
						final LinearLayout block = (LinearLayout)
						uCV.findViewById(R.id.block);
						final ImageView delete = (ImageView)
						uCV.findViewById(R.id.delete);
						final ImageView info = (ImageView)
						uCV.findViewById(R.id.info);
						name.setText((t8888.list).get((int)_position).get("title").toString());
						try {
							if ((t8888.list).get((int)_position).get("type").toString().equals("block")) {
								
							}
							else {
								
							}
						} catch (Exception e) {
							
						}
						block.getBackground().setColorFilter(Color.parseColor("#2195f3"), android.graphics.PorterDuff.Mode.MULTIPLY);
						delete.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View _view){
								collectionsM.remove((int)(_position));
								FileUtil.writeFile(collections_path, new Gson().toJson(collectionsM));
								cl.setListMap(collectionsM);
							}
						});
						info.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View _view){
								di.dismiss();
								ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
								lm = new Gson().fromJson((t8888.list).get((int)_position).get("json").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
								saveLin.removeAllViews();
								saveLin.setId((int)0);
								_load_from(lm, saveLin);
								if (!"".equals(log.replace("\n", ""))) {
									dialog.setTitle("eror");
									dialog.setMessage(log);
									dialog.create().show();
									di.show();
								}
							}
						});
						block.setOnLongClickListener(new View.OnLongClickListener(){
							@Override
							public boolean onLongClick(View _view){
								di.dismiss();
								ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
								lm = new Gson().fromJson((t8888.list).get((int)_position).get("json").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
								saveLin.removeAllViews();
								_setRandomIdsIn(main);
								saveLin.setId((int)0);
								_load_from(lm, saveLin);
								timer = new TimerTask() {
									@Override
									public void run() {
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												try {
													ClipData  data = ClipData.newPlainText("","");
													Point offset = new Point(0,0);
													DragShadowBuilder shadow = new CustomDragShadowBuilder(((LinearLayout)saveLin).getChildAt((int) 0),offset);
													if (Build.VERSION.SDK_INT >= 24){
														((LinearLayout)saveLin).getChildAt((int) 0).startDragAndDrop(data, shadow, ((LinearLayout)saveLin).getChildAt((int) 0),1);
													} else {
														((LinearLayout)saveLin).getChildAt((int) 0).startDrag(data,shadow,((LinearLayout)saveLin).getChildAt((int) 0), 1); 
													}
												} catch (Exception e) {
													dialog.setTitle("eror");
													dialog.setMessage(e.toString());
													dialog.create().show();
													di.show();
												}
											}
										});
									}
								};
								_timer.schedule(timer, (int)(100));
								return false;
							}
						});
						t8888.cv = uCV;
					}
				}
				);
				linear.addView(cl);
				if (!FileUtil.readFile(collections_path).equals("")) {
					collectionsM = new Gson().fromJson(FileUtil.readFile(collections_path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					cl.setListMap(collectionsM);
				}
			}
		});
	}
	
	private void initializeLogic() {
		shadow.getBackground().setColorFilter(Color.parseColor("0xFF000000".replace("0x","#")), android.graphics.PorterDuff.Mode.MULTIPLY);
		((ViewGroup)shadow.getParent()).removeView(shadow);
		delete.setOnDragListener(new StarDragNdrop());
		pl_path = getIntent().getStringExtra("pallets_path");
		code_save_path = getIntent().getStringExtra("code_save_path"); // Note: Changed from "code_save_path" to match intent
		blocks_save_path = getIntent().getStringExtra("blocks_save_path"); // Note: Changed to match intent
		collections_path = getIntent().getStringExtra("collections_path");
		event_name = getIntent().getStringExtra("event_name");
		sc_id = getIntent().getStringExtra("sc_id");
		
		custom_blocks_path = new File(
		Environment.getExternalStorageDirectory(), 
		".blacklogics/data/" + sc_id + "/custom_blocks.json"
		).getAbsolutePath();
		
		complex.setId(sc_id);
		customBlocksDialog = CustomBlocksDialog.newInstance(sc_id);
		
		// Load existing blocks data as a HashMap
		if (FileUtil.isExistFile(blocks_save_path) && !FileUtil.readFile(blocks_save_path).equals("")) {
			allBlocksData = new Gson().fromJson(FileUtil.readFile(blocks_save_path), new TypeToken<HashMap<String, Object>>(){}.getType());
		}
		
		// Load blocks for the specific event
		if (allBlocksData.containsKey(event_name)) {
			ArrayList<HashMap<String, Object>> saveM = new Gson().fromJson(allBlocksData.get(event_name).toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			main.removeAllViews();
			main.setId((int)0);
			_load_from(saveM, main);
		} else {
			// Add default block if no event data exists
			((ViewGroup)main).addView(new regular_block(LogicEditorActivity.this));
			_addViewsFrom(event_name, ((LinearLayout)main).getChildAt(((LinearLayout) main).getChildCount() - 1));
		}
		
		regular_block vb = ((regular_block) (((LinearLayout)main).getChildAt((int) ((LinearLayout) main).getChildCount() - 1)));
		vb.ccode = "";
		((LinearLayout)main).getChildAt((int) ((LinearLayout) main).getChildCount() - 1).setOnLongClickListener(new View.OnLongClickListener(){
			@Override
			public boolean onLongClick(View _view){
				return false;
			}
		});
		main.setOnDragListener(new delD());
		
		bottom.setVisibility(View.GONE);
		starlistview listvm = new starlistview(LogicEditorActivity.this);
		listvm.custom_view = R.layout.pellets;
		listvm.setInterface(
		new starlistview.OnBlindCustomView() {
			@Override 
			public void OnBlind(int id, final int _position, final starlistview t8888) {
				LayoutInflater uLI = getLayoutInflater();
				View uCV = (View) uLI.inflate(t8888.custom_view, null);
				final TextView name = (TextView) uCV.findViewById(R.id.name);
				final LinearLayout clr = (LinearLayout) uCV.findViewById(R.id.clr);
				final LinearLayout linear = (LinearLayout) uCV.findViewById(R.id.linear);
				linears.add(linear);
				try {
					name.setText((t8888.list).get((int)_position).get("name").toString());
					linear.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)2, 0xFF263238, 0xFFFFFFFF));
				} catch (Exception e) {
					SketchwareUtil.showMessage(getApplicationContext(), "getting name eror!");
				}
				try {
					clr.setBackgroundColor(Color.parseColor((t8888.list).get((int)_position).get("color").toString()));
				} catch (Exception e) {
					SketchwareUtil.showMessage(getApplicationContext(), "getting color eror!");
				}
				linear.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
				linear.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						blocks_lin.removeAllViews();
						pos = _position;
						for(View lin:linears){
							lin.setBackgroundColor(Color.TRANSPARENT);
						}
						linear.setBackgroundColor(0xFF689F38);
						starlistview temp = new starlistview(LogicEditorActivity.this);
						temp.custom_view = R.layout.blocks;
						temp.setInterface(
						new starlistview.OnBlindCustomView() {
							@Override 
							public void OnBlind(int id, final int _position, final starlistview t8888){
								LayoutInflater uLI = getLayoutInflater();
								View uCV = (View) uLI.inflate(t8888.custom_view, null);
								final LinearLayout linear = (LinearLayout) uCV.findViewById(R.id.linear);
								try {
									_addBlockFrom((t8888.list).get((int)(_position)), linear);
								} catch (Exception e) {
								}
								t8888.cv = uCV;
							}
						}
						);
						blocks_lin.addView(temp);
						ArrayList<HashMap<String, Object>> lk = new ArrayList<>();
						try {
							lk = new Gson().fromJson((t8888.list).get((int)_position).get("blocks").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
							temp.setListMap(lk);
						} catch (Exception e) {
						}
					}
				});
				if (pos == _position) {
					linear.performClick(); 
				}
				t8888.cv = uCV;
			}
		}
		);
		palletes.addView(listvm);
		listv = listvm;
		listv.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
		
		if (!FileUtil.readFile(pl_path).equals("")) {
			pll.clear();
			linears.clear();
			pll = new Gson().fromJson(FileUtil.readFile(pl_path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listv.setListMap(pll);
		}
		
		if (FileUtil.isExistFile(custom_blocks_path) && !FileUtil.readFile(custom_blocks_path).isEmpty()) {
			ArrayList<HashMap<String, Object>> customBlocks = new Gson().fromJson(
			FileUtil.readFile(custom_blocks_path),
			new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
			);
			pll.addAll(customBlocks);
		}
		
		try {
			pl_LM.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) / 2.3d));
			blocksV.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) / 2.3d));
		} catch (Exception e) {
		}
		((ViewGroup)palletes.getParent()).setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)2, 0xFF263238, Color.TRANSPARENT));
		((ViewGroup)((ViewGroup)blocks_lin.getParent()).getParent()).setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)2, 0xFF263238, Color.TRANSPARENT));
		
		try {
			main.setPivotX(0);
			main.setPivotY(0);
			main.setScaleX((float)(0.7d));
			main.setScaleY((float)(0.7d));
			blocks_lin.setPivotX(0);
			blocks_lin.setPivotY(0);
			blocks_lin.setScaleX((float)(0.7d));
			blocks_lin.setScaleY((float)(0.7d));
			saveLin.setPivotX(0);
			saveLin.setPivotY(0);
			saveLin.setScaleX((float)(0.7d));
			saveLin.setScaleY((float)(0.7d));
		} catch (Exception e) {
		}
		
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
						main.setId((int)0);
						r_id = 0;
						for (int i = 0; i < ((ViewGroup) main).getChildCount(); i++) {
							View v = ((ViewGroup)main).getChildAt(i);
							_save_views_from(v, lm);
						}
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("ur", new Gson().toJson(lm));
							undo_redo_listmap.add(_item);
						}
					}
				});
			}
		};
		_timer.schedule(timer, (int)(100));
		redo.setScaleX((float)(-1));
		collection.setOnDragListener(new StarDragNdrop());
		if (getIntent().hasExtra("list_keys")) {
			list_keys = new Gson().fromJson(getIntent().getStringExtra("list_keys"), new TypeToken<HashMap<String, Object>>(){}.getType());
		}
	}
	
	@Override
	public void onBackPressed() {
		// Generate code
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < ((ViewGroup) main).getChildCount(); i++) {
			View v = ((ViewGroup)main).getChildAt(i);
			code.append("\n").append(((ForAll)v).getCode());
		}
		FileUtil.writeFile(code_save_path, code.toString()); // Save code in .txt
		complex.setLogic(event_name, code.toString());
		
		EventCodeManager.addOrUpdateEventCode(event_name, code.toString());
		
		// Save blocks for this event
		ArrayList<HashMap<String, Object>> saveM = new ArrayList<>();
		main.setId((int)0);
		r_id = 0;
		for (int i = 0; i < ((ViewGroup) main).getChildCount(); i++) {
			View v = ((ViewGroup)main).getChildAt(i);
			_save_views_from(v, saveM);
		}
		allBlocksData.put(event_name, new Gson().toJson(saveM));
		FileUtil.writeFile(blocks_save_path, new Gson().toJson(allBlocksData)); // Save all blocks in one JSON
		
		finish();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (!FileUtil.readFile(pl_path).equals("")) {
			pll.clear();
			linears.clear();
			pll = new Gson().fromJson(FileUtil.readFile(pl_path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listv.setListMap(pll);
		}
		collection.setOnDragListener(new coll());
	}
	public void _DragNdrop() {
	}
	public ArrayList<View> star_V8887 = new ArrayList<>();
	View star_d8887;
	public int getPos_By_Star(Object[] Dobj,ArrayList<Object[]> arr){
		int n=0;
		for(Object[] obj:arr){
			if (Arrays.equals(Dobj,obj)) {
				break;
			} else { n++; }
		}
		return n;} 
	public static class star4droidDropClass {
		public static Star4Droid OnDrag;
		public static ArrayList<Object[]> StarArray1 = new ArrayList<>(); 
		public static ArrayList<Star4Droid> StarArray2 = new ArrayList<>();
		public interface Star4Droid {
			public void start(View drop,View dv);
			public void enter(View drop,View dragged);
			public void location(View drop,View dragged);
			public void drop(View drop,View dragged);
			public void exit(View drop,View dragged);
			public void end(View drop,View dragged);
		} 
	}
	protected class StarDragNdrop implements View.OnDragListener {
		public boolean onDrag(final View vm88, DragEvent event) {
			final View v = ((View)vm88);
			final int action = event.getAction();
			final View DV = ((View) event.getLocalState());
			Object[] bl ={v,DV}; 
			int pos = getPos_By_Star(bl,star4droidDropClass.StarArray1);
			try {
				star4droidDropClass.OnDrag = star4droidDropClass.StarArray2.get(pos);
			} catch (Exception e) {
				try {
					Object[] bll ={v.getClass(),DV}; 
					pos = getPos_By_Star(bll,star4droidDropClass.StarArray1);
				} catch(Exception bvb){
					try {
						Object[] bllg ={DV,v.getClass()}; 
						pos = getPos_By_Star(bllg,star4droidDropClass.StarArray1);
						star4droidDropClass.OnDrag = star4droidDropClass.StarArray2.get(pos);
					} catch(Exception ghfj){
						Object[] bllgh ={DV.getClass(),v.getClass()}; 
						pos = getPos_By_Star(bllgh,star4droidDropClass.StarArray1);
					}
				}
			}
			switch(action) {
				case DragEvent.ACTION_DRAG_STARTED:
				star_d8887=null;
				if (pos<star4droidDropClass.StarArray2.size()) {
					star4droidDropClass.StarArray2.get(pos).start(v,DV);
				} else {
					star_V8887.add(v);
					v.setOnDragListener(null);
				}
				return true;
				case DragEvent.ACTION_DRAG_ENTERED:
				if (pos<star4droidDropClass.StarArray2.size()) {
					star4droidDropClass.StarArray2.get(pos).enter(v,DV);
				}
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_LOCATION:
				//drag started
				if ((pos<star4droidDropClass.StarArray2.size())&&(star_d8887==null)) {
					star4droidDropClass.StarArray2.get(pos).location(v,DV);
					star_d8887=v;
				}
				return true;
				case DragEvent.ACTION_DRAG_EXITED:
				if (pos<star4droidDropClass.StarArray2.size()) {
					star4droidDropClass.StarArray2.get(pos).exit(v,DV);
					star_d8887=null;
				}
				v.invalidate();
				return true;
				case DragEvent.ACTION_DROP:
				//drop
				if (pos<star4droidDropClass.StarArray2.size()) {
					star4droidDropClass.StarArray2.get(pos).drop(v,DV);
				}
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_ENDED:
				v.invalidate();
				if (pos<star4droidDropClass.StarArray2.size()) {
					star4droidDropClass.StarArray2.get(pos).end(v,DV);
					star_d8887=null;
				}
				for(View vb889:star_V8887){
					vb889.setOnDragListener(new StarDragNdrop());
				}
				star_V8887.clear();
				return true;
				default:
				break;
			}
			return false;
		}
	};
	{
	}
	public static class starlistview extends LinearLayout {
		public int custom_view;
		public View cv;
		public ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		public LinearLayout childs;
		public OnBlindCustomView ViewInterface;
		public starlistview(Context ctx) {
			super(ctx);
			this.setOrientation(LinearLayout.VERTICAL);
		}
		public interface OnBlindCustomView {
			public void OnBlind(int id,int _position,starlistview t8888);
		}
		public void setInterface(OnBlindCustomView i){
			this.ViewInterface = i;
		}
		public void setListMap(final ArrayList<HashMap<String, Object>> l){
			int x=0;
			this.removeAllViews();
			this.list.clear();
			for(int _repeat11 = 0; _repeat11 < (int)(l.size()); _repeat11++) {
				this.list.add(l.get((int)(x)));
				this.ViewInterface.OnBlind(this.custom_view,x,this);
				this.addView(this.cv);
				x++; 
			}
		}
	}
	class ForAll extends LinearLayout{
		public ForAll(Context ctx)  {
			super(ctx);
			this.setOnDragListener(new StarDragNdrop());
			try {
				Object[] ook = {regular_block.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							DV.setVisibility(View.GONE);
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							try {
								((ViewGroup)shadow.getParent()).removeView(shadow);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) ((LinearLayout)dropV.getParent())).addView(shadow,(int)(((LinearLayout)dropV.getParent()).indexOfChild(dropV) + 1));
							try {
								((ViewGroup)DV.getParent()).removeView(DV);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) ((LinearLayout)dropV.getParent())).addView(DV,(int)(((LinearLayout)dropV.getParent()).indexOfChild(dropV) + 1));
						};
						@Override public void drop(final View dropV,final View DV){
							
						};
						@Override public void exit(final View dropV,final View DV){
							
						};
						@Override public void end(final View dropV,final View DV){
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											DV.setVisibility(View.VISIBLE);
											try {
												((ViewGroup)shadow.getParent()).removeView(shadow);
											} catch (Exception e) {
												
											}
										}
									});
								}
							};
							_timer.schedule(timer, (int)(20));
						};
					} );
				}
			} catch(Exception ex654) {}
			this.setLayoutParams(new LinearLayout.LayoutParams((int) (ViewGroup.LayoutParams.WRAP_CONTENT),(int) (ViewGroup.LayoutParams.WRAP_CONTENT)));
			try {
				Object[] ook = {LinearLayout.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							try {
								try {
									((ViewGroup)shadow.getParent()).removeView(shadow);
								} catch (Exception exp65676) {
									
								}
								((ViewGroup) ((LinearLayout)((View) (dropV.getTag())).getParent())).addView(shadow,(int)(((LinearLayout)((View) (dropV.getTag())).getParent()).indexOfChild(((View) (dropV.getTag()))) + 1));
								try {
									((ViewGroup)DV.getParent()).removeView(DV);
								} catch (Exception exp65676) {
									
								}
								((ViewGroup) ((LinearLayout)((View) (dropV.getTag())).getParent())).addView(DV,(int)(((LinearLayout)((View) (dropV.getTag())).getParent()).indexOfChild(((View) (dropV.getTag()))) + 1));
							} catch (Exception e) {
								try {
									if_block i = ((if_block) (_get_if_parent_from(dropV)));
									try {
										((ViewGroup)shadow.getParent()).removeView(shadow);
									} catch (Exception exp65676) {
										
									}
									((ViewGroup) i.center).addView(shadow,(int)(0));
									try {
										((ViewGroup)DV.getParent()).removeView(DV);
									} catch (Exception exp65676) {
										
									}
									((ViewGroup) i.center).addView(DV,(int)(0));
								} catch(Exception ed) {
									
								}
							}
						};
						@Override public void drop(final View dropV,final View DV){
							
						};
						@Override public void exit(final View dropV,final View DV){
							
						};
						@Override public void end(final View dropV,final View DV){
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											DV.setVisibility(View.VISIBLE);
											try {
												((ViewGroup)shadow.getParent()).removeView(shadow);
											} catch (Exception e) {
												
											}
										}
									});
								}
							};
							_timer.schedule(timer, (int)(20));
						};
					} );
				}
			} catch(Exception ex654) {}
			try {
				Object[] ook = {if_block.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							if_block i = ((if_block) (dropV));
							try {
								((ViewGroup)shadow.getParent()).removeView(shadow);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) i.center).addView(shadow,(int)(0));
							try {
								((ViewGroup)DV.getParent()).removeView(DV);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) i.center).addView(DV,(int)(0));
						};
						@Override public void drop(final View dropV,final View DV){
							
						};
						@Override public void exit(final View dropV,final View DV){
							
						};
						@Override public void end(final View dropV,final View DV){
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											DV.setVisibility(View.VISIBLE);
										}
									});
								}
							};
							_timer.schedule(timer, (int)(20));
						};
					} );
				}
			} catch(Exception ex654) {}
			this.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View _view){
					VA.clear();
					for (int i = 0; i < ((ViewGroup) ((LinearLayout)_view.getParent())).getChildCount(); i++) {
						View v = ((ViewGroup)((LinearLayout)_view.getParent())).getChildAt(i);
						if (((LinearLayout)_view.getParent()).indexOfChild(_view) < i) {
							VA.add(v);
							v.setVisibility(View.GONE);
						}
					}
					_view.setVisibility(View.GONE);
					ClipData  data = ClipData.newPlainText("","");
					//add drag shadow
					DragShadowBuilder shadow = new View.DragShadowBuilder(_view);
					if (Build.VERSION.SDK_INT >= 24){
						_view.startDragAndDrop(data, shadow, _view,1);
					} else {
						_view.startDrag(data,shadow,_view, 1); 
					}
					return false;
				}
			});
			try {
				final Object[] Star_obj ={delete,this};
				star4droidDropClass.StarArray1.add(Star_obj);
				star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
					@Override public void start(final View dropV,final View DV){
						
					};
					@Override public void enter(final View dropV,final View DV){
						
					};
					@Override public void location(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete_cross);
					};
					@Override public void drop(final View dropV,final View DV){
						((ViewGroup)DV.getParent()).removeView(DV);
					};
					@Override public void exit(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
					@Override public void end(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
				} );
				delete.setOnDragListener(new StarDragNdrop());
			} catch(Exception ex654) {}
		}
		public String getCode(){return "";}
		public String ccode="";
	}
	protected class delD implements View.OnDragListener {
		public boolean onDrag(final View v,final DragEvent event) {
			final int action = event.getAction();
			final View DV = ((View) event.getLocalState());
			switch(action) {
				case DragEvent.ACTION_DRAG_STARTED:
				bottom.setVisibility(View.VISIBLE);
				listv.setListMap(pll);
				objE.setTarget(bottom);
				objE.setPropertyName("translationY");
				objE.setFloatValues((float)(delete.getTranslationY()), (float)(-150));
				objE.setDuration((int)(300));
				objE.start();
				double ind = ((LinearLayout)DV.getParent()).indexOfChild(DV);
				myObj[0]=((View)(DV.getParent()));
				myObj[1]=ind;
				return true;
				case DragEvent.ACTION_DRAG_ENTERED:
				
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_LOCATION:
				//drag started
				
				return true;
				case DragEvent.ACTION_DRAG_EXITED:
				
				v.invalidate();
				return true;
				case DragEvent.ACTION_DROP:
				//drop
				/*drop in the screen */
				/*
				if (DV instanceof component_typeN) {
				((component_typeN)DV).dropped = true;
				}
				else {
				if (DV instanceof component_type) {
				((component_type)DV).dropped = true;
				}
				else {
				if (DV instanceof boolean_block) {
				((boolean_block)DV).dropped = true;
				}
				else {
				
				}
				}
				}
				try {
				((ViewGroup)DV.getParent()).removeView(DV);
				} catch (Exception exp65676) {
				
				}
				((ViewGroup) B_andPsL).addView(DV);
				DV.setTranslationX((float)(event.getX()));
				DV.setTranslationY((float)(event.getY()));
				.setX((float) (0));
				.setY((float) (0));
				*/
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_ENDED:
				v.invalidate();
				bottom.setVisibility(View.GONE);
				_setRandomIdsIn(main);
				if (DV instanceof regular_block || DV instanceof if_block) {
					try {
						int in=1;
						for(final View vb:VA){
							final int inx=in;
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											try {
												try {
													((ViewGroup)vb.getParent()).removeView(vb);
												} catch (Exception exp65676) {
													
												}
												((ViewGroup) ((LinearLayout)DV.getParent())).addView(vb,(int)(((LinearLayout)DV.getParent()).indexOfChild(DV) + inx));
												vb.setTranslationY((float)(((((LinearLayout)DV.getParent()).indexOfChild(DV) + inx) * -5) - 5));
												vb.setVisibility(View.VISIBLE);
											} catch (Exception e) {
												
											}
										}
									});
								}
							};
							_timer.schedule(timer, (int)(50 + (10 * in)));
							in++;
						}
					} catch (Exception e) {
						
					}
				}
				else {
					
				}
				listv.setListMap(pll);
				DV.setOnDragListener(new StarDragNdrop());
				objE.setTarget(bottom);
				objE.setPropertyName("translationY");
				objE.setFloatValues((float)(delete.getTranslationY()), (float)(0));
				objE.setDuration((int)(300));
				objE.start();
				ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
				main.setId((int)0);
				r_id = 0;
				for (int io = 0; io < ((LinearLayout) main).getChildCount(); io++) {
					View vio = ((LinearLayout)main).getChildAt(io);
					_save_views_from(vio, lm);
				}
				if (undo_redo_listmap.size() == 0) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("ur", new Gson().toJson(lm));
						undo_redo_listmap.add(_item);
					}
					
				}
				else {
					if (!new Gson().toJson(lm).equals(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString())) {
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("ur", new Gson().toJson(lm));
							undo_redo_listmap.add(_item);
						}
						
						undo_redo_pos++;
						double n=undo_redo_listmap.size()-1;
						for(int _repeat260 = 0; _repeat260 < (int)(undo_redo_listmap.size()); _repeat260++) {
							if (n > undo_redo_pos) {
								undo_redo_listmap.remove((int)(n));
							}
							n--;
						}
					}
				}
				_check_About_Undo_Redo();
				saveLin.removeAllViews();
				_setDragListenee(true, DV);
				try {
					if ((DV instanceof ForAll)) {
						DV.setTranslationY((float)((((LinearLayout)DV.getParent()).indexOfChild(DV) * -5) + -5));
					}
				} catch (Exception e) {
					
				}
				return true;
				default:
				break;
			}
			return false;
		}
	};
	{
	}
	
	
	public void _regular() {
	}
	class regular_block extends ForAll{
		public regular_block(Context ctx)  {
			super(ctx);
			this.setBackgroundResource(R.drawable.block);
			this.getBackground().setColorFilter(Color.parseColor("#2195f3"), android.graphics.PorterDuff.Mode.MULTIPLY);
			this.setPadding((int) 15,(int) 15,(int) 58,(int) 15);
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		}
		public String ccode="";
		public String colorf="#2195f3";
		public String getCode(){
			String code=ccode; 
			double num=1;
			for (int i = 0; i < ((ViewGroup) this).getChildCount(); i++) {
				View v = ((ViewGroup)this).getChildAt(i);
				if ((v instanceof TextViewT) || ((v instanceof TextViewN) || !(v instanceof TextView))) {
					code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), _getCodeFrom(v));
					num++;
				}
			}
			return code;
		}
	}
	starlistview listv; 
	class bbbbbbbbbb {
	}
	public class CustomDragShadowBuilder extends View.DragShadowBuilder {
		
		// ------------------------------------------------------------------------------------------
		// Private attributes :
		private Point _offset;
		// ------------------------------------------------------------------------------------------
		
		
		
		// ------------------------------------------------------------------------------------------
		// Constructor :
		public CustomDragShadowBuilder(View view, Point offset) {
			
			// Stores the View parameter passed to myDragShadowBuilder.
			super(view);
			
			// Save the offset :
			_offset = offset;
		}
		// ------------------------------------------------------------------------------------------
		
		
		
		// ------------------------------------------------------------------------------------------
		// Defines a callback that sends the drag shadow dimensions and touch point back to the system.
		@Override
		public void onProvideShadowMetrics (Point size, Point touch) {
			
			// Set the shadow size :
			size.set(getView().getWidth(), getView().getHeight());
			
			// Sets the touch point's position to be in the middle of the drag shadow
			touch.set(_offset.x, _offset.y);
		}
		// ------------------------------------------------------------------------------------------
	}
	protected class coll implements View.OnDragListener {
		public boolean onDrag(final View v,final DragEvent event) {
			final int action = event.getAction();
			final View DV = ((View) event.getLocalState());
			switch(action) {
				case DragEvent.ACTION_DRAG_STARTED:
				
				return true;
				case DragEvent.ACTION_DRAG_ENTERED:
				
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_LOCATION:
				//drag started
				collection.setImageResource(R.drawable.ic_save_black);
				return true;
				case DragEvent.ACTION_DRAG_EXITED:
				collection.setImageResource(R.drawable.ic_save_sky_36dp);
				v.invalidate();
				return true;
				case DragEvent.ACTION_DROP:
				//drop
				try {
					((ViewGroup)DV.getParent()).removeView(DV);
				} catch (Exception exp65676) {
					
				}
				((ViewGroup) myObj[0]).addView(DV,(int)(((double)(myObj[1]))));
				_save(DV);
				v.invalidate();
				return true;
				case DragEvent.ACTION_DRAG_ENDED:
				v.invalidate();
				collection.setImageResource(R.drawable.ic_save_sky_36dp);
				return true;
				default:
				break;
			}
			return false;
		}
	};
	{
	}
	@Override public void onConfigurationChanged(Configuration _configuration) { 
		super.onConfigurationChanged(_configuration);
		if(_configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			((LinearLayout) B_andPsL).setOrientation(LinearLayout.HORIZONTAL);
			blocksV.getLayoutParams().width = ((int)(SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) * (2 / 3)));
			pl_LM.getLayoutParams().width = ((int)(SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) * (1 / 3)));
			blocksV.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - ddd.getHeight()));
			pl_LM.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - ddd.getHeight()));
		} else {
			((LinearLayout) B_andPsL).setOrientation(LinearLayout.VERTICAL);
			blocksV.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
			blocksV.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
			pl_LM.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
			blocksV.getLayoutParams().height = ((int)((SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - ddd.getHeight()) / 2));
			pl_LM.getLayoutParams().height = ((int)((SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - ddd.getHeight()) / 2));
		}
	}
	
	
	public void _if_type() {
	}
	class if_block extends ForAll {
		public if_block(Context ctx)  {
			super(ctx);
			LayoutInflater thisLL = getLayoutInflater(); 
			View thisVV = thisLL.inflate(R.layout.if_block, null);
			((LinearLayout) this).addView(thisVV);
			final LinearLayout top = (LinearLayout)
			thisVV.findViewById(R.id.top);
			final LinearLayout bottom = (LinearLayout)
			thisVV.findViewById(R.id.bottom);
			final LinearLayout center = (LinearLayout)
			thisVV.findViewById(R.id.center);
			this.setOnDragListener(new StarDragNdrop());
			bottom.setOnDragListener(new StarDragNdrop());
			bottom.setTag(this);
			bottom.getBackground().setColorFilter(Color.parseColor("#2195f3"), android.graphics.PorterDuff.Mode.MULTIPLY);
			top.getBackground().setColorFilter(Color.parseColor("#2195f3"), android.graphics.PorterDuff.Mode.MULTIPLY);
			center.getBackground().setColorFilter(Color.parseColor("#2195f3"), android.graphics.PorterDuff.Mode.MULTIPLY);
			this.center = center; 
			this.top= top;
			this.bottom = bottom;
		}
		public LinearLayout center;
		public LinearLayout top;
		public String colorf="#2195f3";
		public LinearLayout bottom;
		public String ccode;
		public String getCode(){
			String code=ccode; 
			double num=1;
			for (int i = 0; i < ((ViewGroup) this.top).getChildCount(); i++) {
				View v = ((ViewGroup)this.top).getChildAt(i);
				if ((v instanceof TextViewT) || ((v instanceof TextViewN) || !(v instanceof TextView))) {
					code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), _getCodeFrom(v));
					num++;
				}
			}
			String innerC="";
			for (int i = 0; i < ((ViewGroup) this.center).getChildCount(); i++) {
				View v = ((ViewGroup)this.center).getChildAt(i);
				innerC = innerC.concat("\n".concat(_getCodeFrom(v)));
			}
			code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), innerC);
			return code;
		}
	}
	
	
	public void _boolean_types() {
	}
	class boolean_field extends LinearLayout{
		public boolean_field(Context ctx)  {
			super(ctx);
			this.setBackgroundResource(R.drawable.boolean_block);
			this.setOnDragListener(new StarDragNdrop());
			this.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);
			this.setLayoutParams(new LinearLayout.LayoutParams((int) (ViewGroup.LayoutParams.WRAP_CONTENT),(int) (ViewGroup.LayoutParams.WRAP_CONTENT)));
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			this.setPadding((int) 25,(int) 8,(int) 25,(int) 8);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
		}
		boolean dropped=false;
		public String colorf="#4CAF50";
	}
	class boolean_block extends boolean_field{
		public boolean_block(Context ctx)  {
			super(ctx);
			this.getBackground().setColorFilter(0xFF4DD0E1, PorterDuff.Mode.MULTIPLY);
			try {
				Object[] ook = {boolean_block.class,boolean_field.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							dropV.getBackground().setColorFilter(0xFF263238, PorterDuff.Mode.MULTIPLY);
							for (int i = 0; i < ((ViewGroup) dropV).getChildCount(); i++) {
								View v = ((ViewGroup)dropV).getChildAt(i);
								v.setVisibility(View.INVISIBLE);
							}
						};
						@Override public void drop(final View dropV,final View DV){
							try {
								((ViewGroup)DV.getParent()).removeView(DV);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) ((LinearLayout)dropV.getParent())).addView(DV,(int)(((LinearLayout)dropV.getParent()).indexOfChild(dropV)));
							((boolean_block)DV).dropped = true;
							((ViewGroup)dropV.getParent()).removeView(dropV);
						};
						@Override public void exit(final View dropV,final View DV){
							for (int i = 0; i < ((ViewGroup) dropV).getChildCount(); i++) {
								View v = ((ViewGroup)dropV).getChildAt(i);
								v.setVisibility(View.VISIBLE);
							}
							if (dropV instanceof boolean_block) {
								dropV.getBackground().setColorFilter(Color.parseColor(((boolean_block)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(Color.parseColor(((boolean_field)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
						};
						@Override public void end(final View dropV,final View DV){
							for (int i = 0; i < ((ViewGroup) dropV).getChildCount(); i++) {
								View v = ((ViewGroup)dropV).getChildAt(i);
								v.setVisibility(View.VISIBLE);
							}
							DV.setVisibility(View.VISIBLE);
							if (((boolean_block)DV).dropped) {
								
							}
							else {
								try {
									((ViewGroup)((View) (DV.getTag())).getParent()).removeView(((View) (DV.getTag())));
								} catch (Exception e) {
									
								}
							}
							if (dropV instanceof boolean_block) {
								dropV.getBackground().setColorFilter(Color.parseColor(((boolean_block)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(Color.parseColor(((boolean_field)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
						};
					} );
				}
			} catch(Exception ex654) {}
			this.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View _view){
					boolean_field bf = new boolean_field(LogicEditorActivity.this);
					((boolean_block)_view).dropped = false;
					try {
						((ViewGroup)bf.getParent()).removeView(bf);
					} catch (Exception exp65676) {
						
					}
					((ViewGroup) ((LinearLayout)_view.getParent())).addView(bf,(int)(((LinearLayout)_view.getParent()).indexOfChild(_view)));
					_view.setVisibility(View.GONE);
					_view.setTag(bf);
					ClipData  data = ClipData.newPlainText("","");
					//add drag shadow
					DragShadowBuilder shadow = new View.DragShadowBuilder(_view);
					if (Build.VERSION.SDK_INT >= 24){
						_view.startDragAndDrop(data, shadow, _view,1);
					} else {
						_view.startDrag(data,shadow,_view, 1); 
					}
					return false;
				}
			});
			try {
				final Object[] Star_obj ={delete,this};
				star4droidDropClass.StarArray1.add(Star_obj);
				star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
					@Override public void start(final View dropV,final View DV){
						
					};
					@Override public void enter(final View dropV,final View DV){
						
					};
					@Override public void location(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete_cross);
					};
					@Override public void drop(final View dropV,final View DV){
						((boolean_block)DV).dropped = true;
						((ViewGroup)DV.getParent()).removeView(DV);
					};
					@Override public void exit(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
					@Override public void end(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
				} );
				delete.setOnDragListener(new StarDragNdrop());
			} catch(Exception ex654) {}
		}
		public String colorf="#4DD0E1";
		public String ccode="";
		
		public String getCode(){
			String code=ccode; 
			double num=1;
			for (int i = 0; i < ((ViewGroup) this).getChildCount(); i++) {
				View v = ((ViewGroup)this).getChildAt(i);
				if ((v instanceof TextViewT) || ((v instanceof TextViewN) || !(v instanceof TextView))) {
					code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), _getCodeFrom(v));
					num++;
				}
			}
			return code;
		}
	}
	
	
	public void _addViewsFrom(final String _str, final View _view) {
		for(String i:_str.split(" ")){
			if (i.equals("%m.e")) {
				((LinearLayout)_view).addView(new TextViewT(LogicEditorActivity.this));
			}
			else {
				if (i.equals("%m.n")) {
					((LinearLayout)_view).addView(new TextViewN(LogicEditorActivity.this));
				}
				else {
					if (i.equals("%m.b")) {
						((LinearLayout)_view).addView(new boolean_field(LogicEditorActivity.this));
					}
					else {
						if (i.equals("%m.io")) {
							((LinearLayout)_view).addView(new TextViewT(LogicEditorActivity.this));
							View vv = ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1);
							((TextViewT)vv).type="io";
						}
						else {
							if (i.startsWith("%m.")) {
								((LinearLayout)_view).addView(new list_type(LogicEditorActivity.this));
								list_type t = ((list_type) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
								t.Ttype.setText(i.replace("%m.", ""));
							}
							else {
								if (i.startsWith("%image.")) {
									try {
										((LinearLayout)_view).addView(new ImageView(LogicEditorActivity.this));
										((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1).setLayoutParams(new LinearLayout.LayoutParams((int) (50),(int) (50)));
										((ImageView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setScaleType(ImageView.ScaleType.FIT_CENTER);
										((ImageView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setImageResource(getResources().getIdentifier(i.replace("%image.", ""), "drawable", getPackageName()));
										((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1).setTag(i.replace("%image.", ""));
									} catch (Exception e) {
										
									}
								}
								else {
									((LinearLayout)_view).addView(new TextView(LogicEditorActivity.this));
									((TextView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setText("  "+i+"  ");
									((TextView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setTextColor(0xFFFFFFFF);
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _component_types() {
	}
	class TextViewT extends TextView{
		public TextViewT(Context ctx)  {
			super(ctx);
			this.setBackgroundResource(R.drawable.empty);
			this.setTextColor(0xFF000000);
			this.getBackground().setColorFilter(0xFFE0F7FA, PorterDuff.Mode.MULTIPLY);
			this.setMinimumWidth((int)SketchwareUtil.getDip(LogicEditorActivity.this,40));
			
			
			this.setPadding((int) 10,(int) 8,(int) 10,(int) 8);
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			this.setOnDragListener(new StarDragNdrop());
			this.setTextSize((int)12);
			this.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					_showEditDialogFor((TextView)_view);
				}
			});
			this.setSingleLine(true);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
		}
		boolean dropped=false;
		public String colorf="#E0F7FA";
		public String type="";
	}
	class component_type extends LinearLayout{
		public component_type(Context ctx)  {
			super(ctx);
			this.setBackgroundResource(R.drawable.empty);
			this.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)0, (int)2, 0xFF2196F3, 0xFF0D47A1));
			this.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View _view){
					if (((component_type)_view).type.equals("")) {
						TextViewT bf = new TextViewT(LogicEditorActivity.this);
						((component_type)_view).dropped = false;
						try {
							((ViewGroup)bf.getParent()).removeView(bf);
						} catch (Exception exp65676) {
							
						}
						((ViewGroup) ((LinearLayout)_view.getParent())).addView(bf,(int)(((LinearLayout)_view.getParent()).indexOfChild(_view)));
						_view.setVisibility(View.GONE);
						_view.setTag(bf);
					}
					else {
						list_type bf = new list_type(LogicEditorActivity.this);
						String type = ((component_type)_view).type;
						bf.Ttype.setText(type);
						((component_type)_view).dropped = false;
						try {
							((ViewGroup)bf.getParent()).removeView(bf);
						} catch (Exception exp65676) {
							
						}
						((ViewGroup) ((LinearLayout)_view.getParent())).addView(bf,(int)(((LinearLayout)_view.getParent()).indexOfChild(_view)));
						_view.setVisibility(View.GONE);
						_view.setTag(bf);
					}
					ClipData  data = ClipData.newPlainText("","");
					//add drag shadow
					DragShadowBuilder shadow = new View.DragShadowBuilder(_view);
					if (Build.VERSION.SDK_INT >= 24){
						_view.startDragAndDrop(data, shadow, _view,1);
					} else {
						_view.startDrag(data,shadow,_view, 1); 
					}
					return false;
				}
			});
			this.setMinimumWidth((int)SketchwareUtil.getDip(LogicEditorActivity.this,40));
			
			
			this.setPadding((int) 10,(int) 8,(int) 10,(int) 8);
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
			try {
				Object[] ook = {list_type.class,TextViewT.class,component_type.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							boolean b=false;
							if (dropV instanceof TextViewT || dropV instanceof component_type) {
								try {
									if (dropV instanceof component_type) {
										b = ((component_type)DV).type.equals(((component_type)dropV).type);
									}
									else {
										b = ((component_type)DV).type.equals("");
									}
								} catch (Exception e) {
									
								}
							}
							else {
								if (dropV instanceof list_type) {
									b = ((component_type)DV).type.equals(((list_type)dropV).type);
								}
								else {
									
								}
							}
							if (b) {
								dropV.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
								try {
									for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
										View vn = ((LinearLayout)dropV).getChildAt(n);
										vn.setVisibility(View.INVISIBLE);
									}
								} catch (Exception e) {
									
								}
							}
						};
						@Override public void drop(final View dropV,final View DV){
							boolean b=false;
							if (dropV instanceof TextViewT || dropV instanceof component_type) {
								try {
									if (dropV instanceof component_type) {
										b = ((component_type)DV).type.equals(((component_type)dropV).type);
									}
									else {
										b = ((component_type)DV).type.equals("");
									}
								} catch (Exception e) {
									
								}
							}
							else {
								if (dropV instanceof list_type) {
									b = ((component_type)DV).type.equals(((list_type)dropV).type);
								}
								else {
									
								}
							}
							if (b) {
								try {
									((ViewGroup)DV.getParent()).removeView(DV);
								} catch (Exception exp65676) {
									
								}
								((ViewGroup) ((LinearLayout)dropV.getParent())).addView(DV,(int)(((LinearLayout)dropV.getParent()).indexOfChild(dropV)));
								((component_type)DV).dropped = true;
								((ViewGroup)dropV.getParent()).removeView(dropV);
							}
							else {
								
							}
						};
						@Override public void exit(final View dropV,final View DV){
							try {
								for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
									View vn = ((LinearLayout)dropV).getChildAt(n);
									vn.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								
							}
							if (dropV instanceof component_type) {
								dropV.getBackground().setColorFilter(Color.parseColor(((component_type)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
							}
						};
						@Override public void end(final View dropV,final View DV){
							try {
								for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
									View vn = ((LinearLayout)dropV).getChildAt(n);
									vn.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								
							}
							if (dropV instanceof component_type) {
								dropV.getBackground().setColorFilter(Color.parseColor(((component_type)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
							}
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											try {
												DV.setVisibility(View.VISIBLE);
											} catch (Exception e) {
												
											}
											if (((component_type)DV).dropped) {
												
											}
											else {
												try {
													((ViewGroup)((View) (DV.getTag())).getParent()).removeView(((View) (DV.getTag())));
												} catch (Exception e) {
													
												}
											}
										}
									});
								}
							};
							_timer.schedule(timer, (int)(50));
						};
					} );
				}
			} catch(Exception ex654) {}
			this.setOnDragListener(new StarDragNdrop());
			try {
				final Object[] Star_obj ={delete,this};
				star4droidDropClass.StarArray1.add(Star_obj);
				star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
					@Override public void start(final View dropV,final View DV){
						
					};
					@Override public void enter(final View dropV,final View DV){
						
					};
					@Override public void location(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete_cross);
					};
					@Override public void drop(final View dropV,final View DV){
						((component_type)DV).dropped = true;
						((ViewGroup)DV.getParent()).removeView(DV);
					};
					@Override public void exit(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
					@Override public void end(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
				} );
				delete.setOnDragListener(new StarDragNdrop());
			} catch(Exception ex654) {}
		}
		boolean dropped=false;
		public String colorf="#0D47A1";
		public String type="";
		public String ccode;
		public String getCode(){
			String code=ccode; 
			double num=1;
			for (int i = 0; i < ((ViewGroup) this).getChildCount(); i++) {
				View v = ((ViewGroup)this).getChildAt(i);
				if ((v instanceof TextViewT) || ((v instanceof TextViewN) || !(v instanceof TextView))) {
					code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), _getCodeFrom(v));
					num++;
				}
			}
			return code;
		}
	}
	class list_type extends LinearLayout {
		public list_type(Context ctx)  {
			super(ctx);
			LayoutInflater thisLL = getLayoutInflater(); 
			View thisVV = thisLL.inflate(R.layout.list_type, null);
			((LinearLayout) this).addView(thisVV);
			final TextView name = (TextView)
			thisVV.findViewById(R.id.name);
			final TextView type = (TextView)
			thisVV.findViewById(R.id.type);
			this.Ttype=type;
			final list_type tt=this;
			this.value=name;
			type.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
					final String typenn = _param1.toString();
					
					tt.type=type.getText().toString(); 
				}
				
				@Override
				public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
					
				}
				
				@Override
				public void afterTextChanged(Editable _param1) {
					
				}
			});
			this.setOnDragListener(new StarDragNdrop());
			this.setBackgroundResource(R.drawable.empty);
			this.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					_showEditDialogFor(name);
				}
			});
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
		}
		public String colorf="#0D47A1";
		public String type="";
		public TextView Ttype;
		public TextView value;
		public String getCode(){
			return this.value.getText().toString();
		} 
	}
	
	
	public void _component_types2() {
	}
	class TextViewN extends TextView{
		public TextViewN(Context ctx)  {
			super(ctx);
			this.setBackgroundResource(R.drawable.empty);
			this.setMinimumWidth((int)SketchwareUtil.getDip(LogicEditorActivity.this,40));
			
			
			this.setPadding((int) 10,(int) 8,(int) 10,(int) 8);
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			this.setOnDragListener(new StarDragNdrop());
			this.setLayoutParams(new LinearLayout.LayoutParams((int) (ViewGroup.LayoutParams.WRAP_CONTENT),(int) (ViewGroup.LayoutParams.WRAP_CONTENT)));
			this.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)90, 0xFFE3F2FD));
			this.setTextSize((int)12);
			this.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					_showEditDialogFor((TextView)_view);
				}
			});
			this.setSingleLine(true);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
		}
		boolean dropped=false;
		public String type="";
	}
	class component_typeN extends LinearLayout{
		public component_typeN(Context ctx)  {
			super(ctx);
			try {
				Object[] ook = {TextViewN.class,component_typeN.class};
				for (Object ook1:ook) {
					final Object[] Star_obj ={ook1,this};
					star4droidDropClass.StarArray1.add(Star_obj);
					star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
						@Override public void start(final View dropV,final View DV){
							
						};
						@Override public void enter(final View dropV,final View DV){
							
						};
						@Override public void location(final View dropV,final View DV){
							try {
								for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
									View vn = ((LinearLayout)dropV).getChildAt(n);
									vn.setVisibility(View.INVISIBLE);
								}
							} catch (Exception e) {
								
							}
							dropV.getBackground().setColorFilter(0xFF000000, PorterDuff.Mode.MULTIPLY);
						};
						@Override public void drop(final View dropV,final View DV){
							try {
								((ViewGroup)DV.getParent()).removeView(DV);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) ((LinearLayout)dropV.getParent())).addView(DV,(int)(((LinearLayout)dropV.getParent()).indexOfChild(dropV)));
							((component_typeN)DV).dropped = true;
							((ViewGroup)dropV.getParent()).removeView(dropV);
						};
						@Override public void exit(final View dropV,final View DV){
							try {
								for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
									View vn = ((LinearLayout)dropV).getChildAt(n);
									vn.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								
							}
							if (dropV instanceof component_typeN) {
								dropV.getBackground().setColorFilter(Color.parseColor(((component_typeN)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
							}
						};
						@Override public void end(final View dropV,final View DV){
							try {
								for (int n = 0; n < ((LinearLayout) dropV).getChildCount(); n++) {
									View vn = ((LinearLayout)dropV).getChildAt(n);
									vn.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								
							}
							if (dropV instanceof component_typeN) {
								dropV.getBackground().setColorFilter(Color.parseColor(((component_typeN)dropV).colorf), PorterDuff.Mode.MULTIPLY);
							}
							else {
								dropV.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
							}
							timer = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											try {
												DV.setVisibility(View.VISIBLE);
											} catch (Exception e) {
												
											}
											if (((component_typeN)DV).dropped) {
												
											}
											else {
												try {
													((ViewGroup)((View) (DV.getTag())).getParent()).removeView(((View) (DV.getTag())));
												} catch (Exception e) {
													
												}
											}
										}
									});
								}
							};
							_timer.schedule(timer, (int)(50));
						};
					} );
				}
			} catch(Exception ex654) {}
			this.setBackgroundResource(R.drawable.empty);
			this.setMinimumWidth((int)SketchwareUtil.getDip(LogicEditorActivity.this,40));
			
			
			this.setPadding((int) 10,(int) 8,(int) 10,(int) 8);
			this.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			this.setOnDragListener(new StarDragNdrop());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT); 
			int top = (int) 0;
			int bottom = (int) 0;
			int right = (int) 0;
			int left = (int) 8;
			params.setMargins(left, top, right, bottom);
			this.setLayoutParams(params);
			this.setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View _view){
					TextViewN bf = new TextViewN(LogicEditorActivity.this);
					((component_typeN)_view).dropped = false;
					try {
						((ViewGroup)bf.getParent()).removeView(bf);
					} catch (Exception exp65676) {
						
					}
					((ViewGroup) ((LinearLayout)_view.getParent())).addView(bf,(int)(((LinearLayout)_view.getParent()).indexOfChild(_view)));
					_view.setVisibility(View.GONE);
					_view.setTag(bf);
					ClipData  data = ClipData.newPlainText("","");
					//add drag shadow
					DragShadowBuilder shadow = new View.DragShadowBuilder(_view);
					if (Build.VERSION.SDK_INT >= 24){
						_view.startDragAndDrop(data, shadow, _view,1);
					} else {
						_view.startDrag(data,shadow,_view, 1); 
					}
					return false;
				}
			});
			this.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)90, (int)2, 0xFF00BCD4, 0xFF0D47A1));
			try {
				final Object[] Star_obj ={delete,this};
				star4droidDropClass.StarArray1.add(Star_obj);
				star4droidDropClass.StarArray2.add(new star4droidDropClass.Star4Droid(){
					@Override public void start(final View dropV,final View DV){
						
					};
					@Override public void enter(final View dropV,final View DV){
						
					};
					@Override public void location(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete_cross);
					};
					@Override public void drop(final View dropV,final View DV){
						((component_typeN)DV).dropped = true;
						((ViewGroup)DV.getParent()).removeView(DV);
					};
					@Override public void exit(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
					@Override public void end(final View dropV,final View DV){
						delete.setImageResource(R.drawable.ic_delete);
					};
				} );
				delete.setOnDragListener(new StarDragNdrop());
			} catch(Exception ex654) {}
		}
		public boolean dropped = false; 
		public String colorf="#0D47A1";
		public String ccode;
		public String getCode(){
			String code=ccode; 
			double num=1;
			for (int i = 0; i < ((ViewGroup) this).getChildCount(); i++) {
				View v = ((ViewGroup)this).getChildAt(i);
				if ((v instanceof TextViewT) || ((v instanceof TextViewN) || !(v instanceof TextView))) {
					code = code.replace("%".concat(String.valueOf((long)(num))).concat("$s"), _getCodeFrom(v));
					num++;
				}
			}
			return code;
		}
	}
	
	
	public String _getCodeFrom(final View _view) {
		if (_view instanceof ForAll) {
			return ((ForAll)_view).getCode();
		}
		else {
			if (_view instanceof boolean_block) {
				return ((boolean_block)_view).getCode();
			}
			else {
				if (_view instanceof component_type) {
					return ((component_type)_view).getCode();
				}
				else {
					if (_view instanceof component_typeN) {
						return ((component_typeN)_view).getCode();
					}
					else {
						if (_view instanceof list_type) {
							return ((list_type)_view).getCode();
						}
						else {
							if (_view instanceof TextViewT || _view instanceof TextViewN) {
								return ((TextView)_view).getText().toString();
							}
							else {
								return "";
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _showEditDialogFor(final TextView _textview) {
		if (!(_textview instanceof TextViewN)) {
			final AlertDialog di = new AlertDialog.Builder(LogicEditorActivity.this).create();
			LayoutInflater diLI = getLayoutInflater();
			View diCV = (View) diLI.inflate(R.layout.edittext_string, null);
			di.setView(diCV);
			final EditText value = (EditText)
			diCV.findViewById(R.id.value);
			final TextView save = (TextView)
			diCV.findViewById(R.id.save);
			final TextView cancel = (TextView)
			diCV.findViewById(R.id.cancel);
			final TextView code_editor = (TextView)
			diCV.findViewById(R.id.code_editor);
			final LinearLayout choices = (LinearLayout)
			diCV.findViewById(R.id.choices);
			di.show();
			value.setText(_textview.getText().toString());
			save.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					try {
						if (((TextViewT)_textview).type.equals("io")) {
							_textview.setText(value.getText().toString());
						}
						else {
							_textview.setText(value.getText().toString().replace("\n", "\n"));
						}
					} catch (Exception e) {
						_textview.setText(value.getText().toString());
					}
					di.dismiss();
					SketchwareUtil.hideKeyboard(getApplicationContext());
					ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
					main.setId((int)0);
					r_id = 0;
					for (int io = 0; io < ((LinearLayout) main).getChildCount(); io++) {
						View vio = ((LinearLayout)main).getChildAt(io);
						_save_views_from(vio, lm);
					}
					if (undo_redo_listmap.size() == 0) {
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("ur", new Gson().toJson(lm));
							undo_redo_listmap.add(_item);
						}
						
					}
					else {
						if (!new Gson().toJson(undo_redo_listmap).equals(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString())) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("ur", new Gson().toJson(lm));
								undo_redo_listmap.add(_item);
							}
							
							undo_redo_pos++;
						}
					}
					_check_About_Undo_Redo();
				}
			});
			cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					di.dismiss();
					SketchwareUtil.hideKeyboard(getApplicationContext());
				}
			});
			code_editor.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					String cc = value.getText().toString();
					di.dismiss();
					final AlertDialog di = new AlertDialog.Builder(LogicEditorActivity.this,android.R.style.Theme_Material_Light_NoActionBar).create();
					LayoutInflater diLI = getLayoutInflater();
					View diCV = (View) diLI.inflate(R.layout.edittext_string, null);
					di.setView(diCV);
					final EditText value = (EditText)
					diCV.findViewById(R.id.value);
					final TextView save = (TextView)
					diCV.findViewById(R.id.save);
					final TextView cancel = (TextView)
					diCV.findViewById(R.id.cancel);
					final TextView code_editor = (TextView)
					diCV.findViewById(R.id.code_editor);
					final ScrollView con = (ScrollView)
					diCV.findViewById(R.id.con);
					final LinearLayout choices = (LinearLayout)
					diCV.findViewById(R.id.choices);
					di.show();
					code_editor.setVisibility(View.GONE);
					((View)choices.getParent()).setVisibility(View.GONE);
					value.setText(cc);
					con.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - 80));
					value.getLayoutParams().height = ((int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext()) - 200));
					save.setOnClickListener(new View.OnClickListener(){
						@Override
						public void onClick(View _view){
							try {
								if (((TextViewT)_textview).type.equals("io")) {
									_textview.setText(value.getText().toString());
								}
								else {
									_textview.setText(value.getText().toString().replace("\n", "\n"));
								}
							} catch (Exception e) {
								_textview.setText(value.getText().toString());
							}
							di.dismiss();
							SketchwareUtil.hideKeyboard(getApplicationContext());
							ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
							main.setId((int)0);
							r_id = 0;
							for (int io = 0; io < ((LinearLayout) main).getChildCount(); io++) {
								View vio = ((LinearLayout)main).getChildAt(io);
								_save_views_from(vio, lm);
							}
							if (undo_redo_listmap.size() == 0) {
								{
									HashMap<String, Object> _item = new HashMap<>();
									_item.put("ur", new Gson().toJson(lm));
									undo_redo_listmap.add(_item);
								}
								
							}
							else {
								if (!new Gson().toJson(undo_redo_listmap).equals(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString())) {
									{
										HashMap<String, Object> _item = new HashMap<>();
										_item.put("ur", new Gson().toJson(lm));
										undo_redo_listmap.add(_item);
									}
									
									undo_redo_pos++;
								}
							}
							_check_About_Undo_Redo();
						}
					});
					cancel.setOnClickListener(new View.OnClickListener(){
						@Override
						public void onClick(View _view){
							di.dismiss();
							SketchwareUtil.hideKeyboard(getApplicationContext());
						}
					});
				}
			});
			if ((_textview instanceof TextViewT)) {
				((View)choices.getParent()).setVisibility(View.GONE);
			}
			else {
				View cb = ((TextView)((LinearLayout)_textview.getParent()).getChildAt((int) 1));
				if (list_keys.containsKey(((TextView)cb).getText().toString())) {
					ArrayList<String> lm = new ArrayList<>();
					lm = new Gson().fromJson(list_keys.get(((TextView)cb).getText().toString()).toString(), new TypeToken<ArrayList<String>>(){}.getType());
					for(final String h:lm){
						RadioButton check = new RadioButton(LogicEditorActivity.this);
						choices.addView(check);
						check.setText(h);
						if (h.equals(_textview.getText().toString())) {
							((RadioButton) check).setChecked(true);
						}
						check.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View _view){
								value.setText(((RadioButton) _view).getText().toString());
								/**/
								/*
								for (int i = 0; i < ((ViewGroup) ((LinearLayout)_view.getParent())).getChildCount(); i++) {
								View v = ((ViewGroup)((LinearLayout)_view.getParent())).getChildAt(i);
								((CheckBox) v).setChecked(false);
								}
								((CheckBox) _view).setChecked(true);
								*/
							}
						});
					}
					value.setVisibility(View.GONE);
				}
				else {
					((View)choices.getParent()).setVisibility(View.GONE);
				}
			}
		}
		else {
			final AlertDialog di = new AlertDialog.Builder(LogicEditorActivity.this).create();
			LayoutInflater diLI = getLayoutInflater();
			View diCV = (View) diLI.inflate(R.layout.edittext_n, null);
			di.setView(diCV);
			final EditText value = (EditText)
			diCV.findViewById(R.id.value);
			final TextView save = (TextView)
			diCV.findViewById(R.id.save);
			final TextView cancel = (TextView)
			diCV.findViewById(R.id.cancel);
			di.show();
			save.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					_textview.setText(value.getText().toString());
					di.dismiss();
					SketchwareUtil.hideKeyboard(getApplicationContext());
					ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
					main.setId((int)0);
					r_id = 0;
					for (int io = 0; io < ((LinearLayout) main).getChildCount(); io++) {
						View vio = ((LinearLayout)main).getChildAt(io);
						_save_views_from(vio, lm);
					}
					if (undo_redo_listmap.size() == 0) {
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("ur", new Gson().toJson(lm));
							undo_redo_listmap.add(_item);
						}
						
					}
					else {
						if (!new Gson().toJson(undo_redo_listmap).equals(undo_redo_listmap.get((int)undo_redo_pos).get("ur").toString())) {
							{
								HashMap<String, Object> _item = new HashMap<>();
								_item.put("ur", new Gson().toJson(lm));
								undo_redo_listmap.add(_item);
							}
							
							undo_redo_pos++;
						}
					}
					_check_About_Undo_Redo();
				}
			});
			cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View _view){
					di.dismiss();
					SketchwareUtil.hideKeyboard(getApplicationContext());
				}
			});
			value.setText(_textview.getText().toString());
		}
	}
	
	
	public void _addBlockFrom(final HashMap<String, Object> _map, final View _view) {
		String code = _map.get("code").toString();
		String views = _map.get("views").toString();
		String type = _map.get("type").toString();
		String color = _map.get("color").toString();
		if (_map.get("type").toString().equals("regular")) {
			((ViewGroup)_view).addView(new regular_block(LogicEditorActivity.this));
			_addViewsFrom(views, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
			regular_block cv = ((regular_block) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
			cv.ccode = code;
			cv.colorf = color;
			cv.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
		}
		else {
			if (_map.get("type").toString().equals("if")) {
				((ViewGroup)_view).addView(new if_block(LogicEditorActivity.this));
				if_block bb = ((if_block) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
				_addViewsFrom(views, bb.top);
				bb.ccode = code;
				bb.colorf = color;
				bb.top.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
				bb.center.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
				bb.bottom.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
			}
			else {
				if (_map.get("type").toString().equals("p")) {
					((ViewGroup)_view).addView(new component_type(LogicEditorActivity.this));
					_addViewsFrom(views, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
					component_type cv = ((component_type) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
					cv.ccode = code;
					cv.colorf = color;
					cv.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
				}
				else {
					if (_map.get("type").toString().equals("d")) {
						((ViewGroup)_view).addView(new component_typeN(LogicEditorActivity.this));
						_addViewsFrom(views, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
						component_typeN cv = ((component_typeN) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
						cv.ccode = code;
						cv.colorf= color;
						cv.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
					}
					else {
						if (_map.get("type").toString().equals("b")) {
							((ViewGroup)_view).addView(new boolean_block(LogicEditorActivity.this));
							_addViewsFrom(views, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
							boolean_block cv = ((boolean_block) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
							cv.ccode = code;
							cv.colorf = color;
							cv.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
						}
						else {
							if (_map.get("type").toString().equals("header")) {
								((ViewGroup)_view).addView(new TextView(LogicEditorActivity.this));
								((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1).setBackgroundColor(0xFF263238);
								((TextView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setText(views);
								((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1).getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
								((TextView) ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)).setTextColor(0xFFFFFFFF);
							}
							else {
								((ViewGroup)_view).addView(new component_type(LogicEditorActivity.this));
								_addViewsFrom(views, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
								component_type cv = ((component_type) (((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1)));
								cv.ccode = code;
								cv.type=type;
								cv.colorf = color;
								cv.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
							}
						}
					}
				}
			}
		}
		_setDragListenee(false, ((LinearLayout)_view).getChildAt((int) ((LinearLayout) _view).getChildCount() - 1));
	}
	
	
	
	public void _save_views_from(final View _view, final ArrayList<HashMap<String, Object>> _listmap) {
		List<CustomBlocksDialog.Block> customBlocks = new ArrayList<>();
		liK.clear();
		r_id++;
		map = new HashMap<>();
		map.put("id", String.valueOf((long)(r_id)));
		_view.setId((int)r_id);
		map.put("pid", String.valueOf((long)(((LinearLayout)_view.getParent()).getId())));
		
		if (_view instanceof ForAll) {
			if (_view instanceof regular_block) {
				map.put("type", "regular_block");
				map.put("views", ((regular_block)_view).ccode);
				map.put("color", ((regular_block)_view).colorf);
				/*if (((regular_block)_view).tty != null) {
					map.put("tty", ((regular_block)_view).tty); // Save tty for stop block
				}*/
			} else {
				map.put("type", "if_block");
				map.put("views", ((if_block)_view).ccode);
				map.put("color", ((if_block)_view).colorf);
			}
		} else {
			// Other types (component_type, component_typeN, etc.) remain unchanged
			if (_view instanceof component_type) {
				map.put("views", ((component_type)_view).ccode);
				map.put("type", "component_type");
				map.put("tty", ((component_type)_view).type);
				map.put("color", ((component_type)_view).colorf);
			} else if (_view instanceof component_typeN) {
				map.put("views", ((component_typeN)_view).ccode);
				map.put("type", "component_typeN");
				map.put("color", ((component_typeN)_view).colorf);
			} else if (_view instanceof TextViewN) {
				map.put("views", ((TextView)_view).getText().toString());
				map.put("type", "TextViewN");
			} else if (_view instanceof TextViewT) {
				map.put("views", ((TextView)_view).getText().toString());
				map.put("type", "TextViewT");
				map.put("tty", ((TextViewT)_view).type);
			} else if (_view instanceof boolean_field) {
				map.put("views", "");
				map.put("type", "boolean_field");
			} else if (_view instanceof boolean_block) {
				map.put("views", ((boolean_block)_view).ccode);
				map.put("type", "boolean_block");
				map.put("color", ((boolean_block)_view).colorf);
			} else if (_view instanceof list_type) {
				map.put("type", "%list.".concat(((list_type)_view).type));
				map.put("views", ((list_type)_view).getCode());
			} else if (_view instanceof TextView) {
				map.put("type", "TextView");
				map.put("views", ((TextView)_view).getText().toString());
			} else if (_view instanceof ImageView) {
				map.put("type", "ImageView");
				map.put("views", _view.getTag().toString());
			}
		}
		
		map.put("c1", String.valueOf((long)(r_id + 1)));
		map.put("c2", String.valueOf((long)(r_id + 2)));
		_listmap.add(map);
		
		// Save custom blocks to custom_blocks_path
        
        /*
		if (!customBlocksList.isEmpty()) {
			// Load existing custom blocks
			ArrayList<HashMap<String, Object>> existingBlocks = new ArrayList<>();
			if (FileUtil.isExistFile(custom_blocks_path) && !FileUtil.readFile(custom_blocks_path).equals("")) {
				existingBlocks = new Gson().fromJson(
				FileUtil.readFile(custom_blocks_path),
				new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
				);
			}
			// Append new blocks
			existingBlocks.addAll(customBlocksList);
			// Save to file
			FileUtil.writeFile(custom_blocks_path, new Gson().toJson(existingBlocks));
		}*/
		
		if ((_view instanceof if_block)) {
			r_id++;
			((if_block)_view).top.setId((int) r_id);
			r_id++;
			((if_block)_view).center.setId((int) r_id);
			for (int i = 0; i < ((ViewGroup) ((if_block)_view).top).getChildCount(); i++) {
				View v = ((ViewGroup)((if_block)_view).top).getChildAt(i);
				_save_views_from(v, liK);
			}
			for (int i = 0; i < ((ViewGroup) ((if_block)_view).center).getChildCount(); i++) {
				View v = ((ViewGroup)((if_block)_view).center).getChildAt(i);
				_save_views_from(v, _listmap);
			}
		} else if (!(_view instanceof list_type) && (_view instanceof LinearLayout)) {
			for (int i = 0; i < ((ViewGroup) _view).getChildCount(); i++) {
				View v = ((ViewGroup)_view).getChildAt(i);
				_save_views_from(v, _listmap);
			}
		}
		if (_view instanceof LinearLayout) {
			for (int i = 0; i < ((LinearLayout)_view).getChildCount(); i++) {
				View v = ((LinearLayout)_view).getChildAt(i);
				HashMap<String, Object> map = new HashMap<>();
				
				if (v instanceof ForAll) {
					if (v instanceof regular_block) {
						regular_block block = (regular_block) v;
						map.put("type", "regular_block");
						map.put("views", block.ccode);
						map.put("color", block.colorf);
						map.put("id", String.valueOf(v.getId()));
						map.put("pid", String.valueOf(((ViewGroup)v.getParent()).getId()));
						
						// Add to custom blocks
						CustomBlocksDialog.Block customBlock = new CustomBlocksDialog.Block();
						customBlock.id = "blk_" + v.getId();
						customBlock.type = "regular_block";
						customBlock.views = block.ccode;
						customBlock.code = block.ccode;
						customBlock.color = block.colorf;
						customBlock.name = block.ccode.isEmpty() ? "RegularBlock_" + v.getId() : block.ccode.replace("\n", "").substring(0, Math.min(block.ccode.length(), 20));
						customBlocks.add(customBlock);
					} else if (v instanceof if_block) {
						if_block block = (if_block) v;
						map.put("type", "if_block");
						map.put("views", block.ccode);
						map.put("color", block.colorf);
						map.put("id", String.valueOf(v.getId()));
						map.put("pid", String.valueOf(((ViewGroup)v.getParent()).getId()));
						map.put("c1", String.valueOf(block.top.getId()));
						map.put("c2", String.valueOf(block.center.getId()));
						
						// Add to custom blocks
						CustomBlocksDialog.Block customBlock = new CustomBlocksDialog.Block();
						customBlock.id = "blk_" + v.getId();
						customBlock.type = "if_block";
						customBlock.views = block.ccode;
						customBlock.code = block.ccode;
						customBlock.color = block.colorf;
						customBlock.name = block.ccode.isEmpty() ? "IfBlock_" + v.getId() : block.ccode.replace("\n", "").substring(0, Math.min(block.ccode.length(), 20));
						customBlocks.add(customBlock);
					}
					liK.add(map);
				}
			}
		}
		
		// Save custom blocks using setCustomBlocks
		if (!customBlocks.isEmpty()) {
			customBlocksDialog.setCustomBlocks(customBlocks);
		}
	}
	
	
	public void _load_from(final ArrayList<HashMap<String, Object>> _listmap, final View _view) {
		_view.setId((int)0);
		double n = 0;
		log = "";
		for(int _repeat14 = 0; _repeat14 < (int)(_listmap.size()); _repeat14++) {
			try {
				String code = _listmap.get((int)n).get("views").toString();
				double id = Double.parseDouble(_listmap.get((int)n).get("id").toString());
				double c1 = Double.parseDouble(_listmap.get((int)n).get("c1").toString());
				double c2 = Double.parseDouble(_listmap.get((int)n).get("c2").toString());
				if ("if_block".equals(_listmap.get((int)n).get("type").toString())) {
					((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new if_block(LogicEditorActivity.this));
					if_block i = ((if_block) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
					i.top.setId((int) c1);
					i.setId((int) id);
					i.center.setId((int) c2);
					i.ccode = code;
					try {
						String color = _listmap.get((int)n).get("color").toString();
						i.colorf = color;
						i.top.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
						i.center.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
						i.bottom.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
					} catch (Exception e) {
						
					}
					i.setTranslationY((float)((((LinearLayout)i.getParent()).indexOfChild(i) * -6) - 6));
				}
				else {
					if ("regular_block".equals(_listmap.get((int)n).get("type").toString())) {
						((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new regular_block(LogicEditorActivity.this));
						regular_block i = ((regular_block) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
						i.ccode = code;
						i.setId((int) id);
						try {
							String color = _listmap.get((int)n).get("color").toString();
							i.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
							i.colorf = color;
						} catch (Exception e) {
							
						}
						i.setTranslationY((float)(((LinearLayout)i.getParent()).indexOfChild(i) * -6));
					}
					else {
						if ("component_type".equals(_listmap.get((int)n).get("type").toString())) {
							((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new component_type(LogicEditorActivity.this));
							component_type i = ((component_type) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
							String tty = _listmap.get((int)n).get("tty").toString();
							i.ccode = code;
							i.type = tty;
							i.setId((int) id);
							try {
								String color = _listmap.get((int)n).get("color").toString();
								i.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
								i.colorf = color;
							} catch (Exception e) {
								
							}
						}
						else {
							if ("component_typeN".equals(_listmap.get((int)n).get("type").toString())) {
								((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new component_typeN(LogicEditorActivity.this));
								component_typeN i = ((component_typeN) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
								i.ccode = code;
								i.setId((int) id);
								try {
									String color = _listmap.get((int)n).get("color").toString();
									i.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
									i.colorf = color;
								} catch (Exception e) {
									
								}
							}
							else {
								if ("TextViewN".equals(_listmap.get((int)n).get("type").toString())) {
									((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new TextViewN(LogicEditorActivity.this));
									TextViewN i = ((TextViewN) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
									i.setText(code);
								}
								else {
									if ("TextViewT".equals(_listmap.get((int)n).get("type").toString())) {
										((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new TextViewT(LogicEditorActivity.this));
										TextViewT i = ((TextViewT) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
										String tty = _listmap.get((int)n).get("tty").toString();
										i.setText(code);
										i.type =tty;
									}
									else {
										if ("boolean_field".equals(_listmap.get((int)n).get("type").toString())) {
											((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new boolean_field(LogicEditorActivity.this));
										}
										else {
											if ("boolean_block".equals(_listmap.get((int)n).get("type").toString())) {
												((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new boolean_block(LogicEditorActivity.this));
												boolean_block i = ((boolean_block) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
												i.setId((int) id);
												i.ccode = code;
												try {
													String color = _listmap.get((int)n).get("color").toString();
													i.getBackground().setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
													i.colorf = color;
												} catch (Exception e) {
													
												}
											}
											else {
												if ("TextView".equals(_listmap.get((int)n).get("type").toString())) {
													((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new TextView(LogicEditorActivity.this));
													TextView i = ((TextView) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
													i.setText(code);
													i.setTextColor(0xFFFFFFFF);
												}
												else {
													if ("ImageView".equals(_listmap.get((int)n).get("type").toString())) {
														((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new ImageView(LogicEditorActivity.this));
														ImageView i = ((ImageView) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
														i.setTag(code);
														i.setLayoutParams(new LinearLayout.LayoutParams((int) (50),(int) (50)));
														((ImageView) i).setScaleType(ImageView.ScaleType.FIT_CENTER);
														try {
															i.setImageResource(getResources().getIdentifier(code, "drawable", getPackageName()));
														} catch (Exception e) {
															
														}
													}
													else {
														String type = _listmap.get((int)n).get("type").toString().replace("%list.", "");
														((ViewGroup)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).addView(new list_type(LogicEditorActivity.this));
														list_type i = ((list_type) (((LinearLayout)(findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildAt((int) ((LinearLayout) (findViewById((int) Double.parseDouble(_listmap.get((int)n).get("pid").toString())))).getChildCount() - 1)));
														i.Ttype.setText(type);
														i.value.setText(code);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} catch(Exception ui) {
				try {
					String og = "\ntype: ".concat(_listmap.get((int)n).get("type").toString());
					if ("component_type".equals(_listmap.get((int)n).get("type").toString())) {
						try {
							String ogh = "\ntty: ".concat(_listmap.get((int)n).get("tty").toString());
						} catch(Exception io) {
							log = log.concat("\ntty: ".concat(io.toString()+" "+n));
						}
					}
					else {
						
					}
				} catch (Exception e) {
					String og = log.concat("\ntype: ".concat(e.toString()+" "+n));
				}
				try {
					String og = "\nviews: ".concat(_listmap.get((int)n).get("views").toString());
				} catch (Exception e) {
					log = log.concat("\nviews: ".concat(e.toString()+" "+n));
				}
				try {
					String og = "\nid: ".concat(_listmap.get((int)n).get("id").toString());
				} catch (Exception e) {
					log = log.concat("\nid: ".concat(e.toString()+" "+n));
				}
				try {
					String og = "\nparent: ".concat(_listmap.get((int)n).get("pid").toString());
				} catch (Exception e) {
					log = log.concat("\npid: ".concat(e.toString()+" "+n));
				}
				log = log.concat("\njson: ".concat(new Gson().toJson(_listmap.get((int)(n)))));
			}
			n++;
		}
		_setRandomIdsIn(_view);
	}
	
	
	public void _check_About_Undo_Redo() {
		try {
			String s = undo_redo_listmap.get((int)undo_redo_pos + 1).get("ur").toString();
			redo.setImageResource(R.drawable.ic_redo_black);
		} catch (Exception e) {
			redo.setImageResource(R.drawable.ic_undo_grey);
		}
		try {
			String s = undo_redo_listmap.get((int)undo_redo_pos - 1).get("ur").toString();
			undo.setImageResource(R.drawable.ic_redo_black);
		} catch (Exception e) {
			undo.setImageResource(R.drawable.ic_undo_grey);
		}
	}
	
	
	public void _setRandomIdsIn(final View _view) {
		r_id = Math.abs(new Random().nextInt(1000000)); // Start with a positive random ID
		_view.setId((int) r_id);
		if (_view instanceof LinearLayout) {
			for (int i = 0; i < ((ViewGroup) _view).getChildCount(); i++) {
				View v = ((ViewGroup)_view).getChildAt(i);
				r_id++;
				v.setId((int) r_id);
				if (v instanceof LinearLayout) {
					_setRandomIdsIn(v); // Recursive call for nested LinearLayouts
				}
			}
		}
	}
	
	public void _save_v(final View _v, final ArrayList<HashMap<String, Object>> _listmap) {
		_v.setId((int)0);
		r_id = 0;
		for (int io = 0; io < ((LinearLayout) _v).getChildCount(); io++) {
			View vio = ((LinearLayout)_v).getChildAt(io);
			_save_views_from(vio, _listmap);
		}
	}
	
	
	public void _setDragListenee(final boolean _on, final View _view) {
		if (_on) {
			_view.setOnDragListener(new StarDragNdrop());
		}
		else {
			_view.setOnDragListener(null);
		}
		if (_view instanceof LinearLayout) {
			for (int i = 0; i < ((ViewGroup) _view).getChildCount(); i++) {
				View v = ((ViewGroup)_view).getChildAt(i);
				_setDragListenee(_on, v);
			}
		}
		_view.setVisibility(View.VISIBLE);
		_view.setTranslationY((float)(0));
		_view.setTranslationX((float)(0));
	}
	
	
	public void _save(final View _view) {
		final AlertDialog di = new AlertDialog.Builder(LogicEditorActivity.this).create();
		LayoutInflater diLI = getLayoutInflater();
		View diCV = (View) diLI.inflate(R.layout.save_collection, null);
		di.setView(diCV);
		final EditText name = (EditText)
		diCV.findViewById(R.id.name);
		final Button save = (Button)
		diCV.findViewById(R.id.save);
		di.show();
		final View cx=_view;
		//i take about to hours to find that i named the variable of the function as _view and the onclick Listener returns _view 🤣
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View _view){
				ArrayList<HashMap<String, Object>> listh = new ArrayList<>();
				HashMap<String, Object> map2 = new HashMap<>();
				if (!"".equals(name.getText().toString())) {
					_setRandomIdsIn(main);
					map2.put("title", name.getText().toString());
					if ((cx instanceof ForAll)) {
						map2.put("type", "block");
						((LinearLayout)cx.getParent()).setId((int)0);
						r_id = 0;
						for (int io = 0; io < ((LinearLayout) ((LinearLayout)cx.getParent())).getChildCount(); io++) {
							View vio = ((LinearLayout)((LinearLayout)cx.getParent())).getChildAt(io);
							if (((LinearLayout)cx.getParent()).indexOfChild(cx) > (io - 1)) {
								_save_views_from(vio, listh);
							}
						}
					}
					else {
						try {
							double n = ((LinearLayout)cx.getParent()).indexOfChild(cx);
							View pv = ((LinearLayout)cx.getParent());
							try {
								((ViewGroup)cx.getParent()).removeView(cx);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) saveLin).addView(cx);
							saveLin.setId((int)0);
							r_id = 0;
							for (int io = 0; io < ((LinearLayout) saveLin).getChildCount(); io++) {
								View vio = ((LinearLayout)saveLin).getChildAt(io);
								_save_views_from(vio, listh);
							}
							try {
								((ViewGroup)cx.getParent()).removeView(cx);
							} catch (Exception exp65676) {
								
							}
							((ViewGroup) pv).addView(cx,(int)(n));
							if ((cx instanceof boolean_block)) {
								map2.put("type", "boolean_block");
							}
							else {
								if ((cx instanceof component_type)) {
									map2.put("type", "string");
								}
								else {
									if ((cx instanceof component_typeN)) {
										map2.put("type", "number");
									}
									else {
										map2.put("type", (_view).getClass().getName());
									}
								}
							}
						} catch (Exception e) {
							
						}
					}
					map2.put("json", new Gson().toJson(listh));
					collectionsM.add(map2);
					FileUtil.writeFile(collections_path, new Gson().toJson(collectionsM));
					di.dismiss();
				}
			}
		});
		if (!FileUtil.readFile(collections_path).equals("")) {
			collectionsM = new Gson().fromJson(FileUtil.readFile(collections_path), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
	}
	
	
	public View _get_if_parent_from(final View _view) {
		if ((((View)_view.getParent()) instanceof if_block)) {
			return ((View)_view.getParent());
		}
		else {
			return _get_if_parent_from(((View)_view.getParent()));
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
