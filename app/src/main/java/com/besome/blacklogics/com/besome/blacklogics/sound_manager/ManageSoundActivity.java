package com.besome.blacklogics.sound_manager;

import static mod.SketchwareUtil.dpToPx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.besome.blacklogics.R;
import com.besome.blacklogics.FileUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mod.hey.studios.util.Helper;

public class ManageSoundActivity extends AppCompatActivity {
	public final int REQ_CD_SOUND_PICKER = 103;
	private TabLayout tab_layout;
	private ViewPager view_pager;
	private LinearLayout layout_ads;
	private Intent soundPicker = new Intent(Intent.ACTION_GET_CONTENT);
	private String sc_id = "601";
	private ArrayList<String> selectedSoundPaths = new ArrayList<>();
	private String currentSoundPath;
	private MediaPlayer mediaPlayer;
	private AlertDialog currentSoundDialog; // Add at class level
	private Handler handler = new Handler();
	private Runnable updateSeekBar;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manage_image); // Reuse layout
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1002);
		} else {
			setupUI();
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1002) {
			setupUI();
			initializeLogic();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		handler.removeCallbacksAndMessages(null);
		if (currentSoundDialog != null && currentSoundDialog.isShowing()) {
			currentSoundDialog.dismiss();
			currentSoundDialog = null;
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		tab_layout = findViewById(R.id.tab_layout);
		view_pager = findViewById(R.id.view_pager);
		layout_ads = findViewById(R.id.layout_ads);
		soundPicker.setType("audio/*");
		soundPicker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		sc_id = getIntent().getStringExtra("sc_id");
		
		view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@NonNull
			@Override
			public Fragment getItem(int position) {
				if (position == 0) return new SoundFragment();
				return new SoundCollectionFragment();
			}
			
			@Override
			public int getCount() {
				return 2;
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				return position == 0 ? "This project" : "My collection";
			}
		});
		
		tab_layout.setupWithViewPager(view_pager);
	}
	
	private void initializeLogic() {
		File collectionDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/sound");
		if (!collectionDir.exists()) collectionDir.mkdirs();
		File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/sounds/" + sc_id);
		if (!resourceDir.exists()) resourceDir.mkdirs();
	}
	
	public void _SoundPickerDialog(final boolean _check, final String path, final boolean isEditMode) {
		if (currentSoundDialog != null && currentSoundDialog.isShowing()) {
			currentSoundDialog.dismiss();
		}
		
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
        currentSoundDialog = dialog;
		View inflate = getLayoutInflater().inflate(R.layout.dialog_add_sound, null);
		dialog.setView(inflate);
		
		LinearLayout addSoundArea = inflate.findViewById(R.id.add_sound_area);
		ImageView imageView = inflate.findViewById(R.id.imageview3);
		TextView textView = inflate.findViewById(R.id.textview3);
		TextView audioFileName = inflate.findViewById(R.id.audio_file_name);
		LinearLayout playerLayout = inflate.findViewById(R.id.player_layout);
		ImageView playPauseButton = inflate.findViewById(R.id.play_pause_button);
		SeekBar seekBar = inflate.findViewById(R.id.seekbar);
		TextView startTime = inflate.findViewById(R.id.start_time);
		TextView endTime = inflate.findViewById(R.id.end_time);
		EditText soundNameEditText = inflate.findViewById(R.id.sound_name_edittext);
		CheckBox addToCollection = inflate.findViewById(R.id.add_to_collection_checkbox);
		TextView cancelButton = inflate.findViewById(R.id.cancel_button);
		TextView saveButton = inflate.findViewById(R.id.save_button);
		
		if (isEditMode) {
			soundNameEditText.setEnabled(false);
			addToCollection.setVisibility(View.GONE);
			saveButton.setVisibility(View.GONE);
		} else {
			soundNameEditText.setEnabled(true);
			addToCollection.setVisibility(View.VISIBLE);
			saveButton.setVisibility(View.VISIBLE);
		}
		
		addSoundArea.setOnClickListener(v -> {
			if (!isEditMode) {
				startActivityForResult(soundPicker, REQ_CD_SOUND_PICKER);
			}
		});
		
		MediaPlayer dialogMediaPlayer = new MediaPlayer();
		updateSeekBar = new Runnable() {
			@Override
			public void run() {
				if (dialogMediaPlayer.isPlaying()) {
					int currentPosition = dialogMediaPlayer.getCurrentPosition();
					seekBar.setProgress(currentPosition);
					startTime.setText(formatTime(currentPosition));
					handler.postDelayed(this, 1000);
				}
			}
		};
		
		playPauseButton.setOnClickListener(v -> {
			if (dialogMediaPlayer.isPlaying()) {
				dialogMediaPlayer.pause();
				playPauseButton.setImageResource(R.drawable.ic_play);
			} else {
				try {
					dialogMediaPlayer.start();
					playPauseButton.setImageResource(R.drawable.ic_pause);
					handler.postDelayed(updateSeekBar, 0);
				} catch (IllegalStateException e) {
					showMessage("Error playing sound: " + e.getMessage());
				}
			}
		});
		
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					dialogMediaPlayer.seekTo(progress);
					startTime.setText(formatTime(progress));
				}
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				handler.removeCallbacks(updateSeekBar);
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (dialogMediaPlayer.isPlaying()) {
					handler.postDelayed(updateSeekBar, 0);
				}
			}
		});
		
		saveButton.setOnClickListener(v -> {
			String name = soundNameEditText.getText().toString().trim();
			if (name.isEmpty()) {
				showMessage("Please enter a sound name");
				return;
			}
			
			File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/sounds/" + sc_id);
			File destFile = new File(resourceDir, name + ".mp3");
			if (destFile.exists()) {
				showMessage("Sound name already exists!");
				return;
			}
			
			if (addToCollection.isChecked()) {
				saveToCollection(name, currentSoundPath);
			}
			importSounds(Arrays.asList(currentSoundPath));
			dialogMediaPlayer.release();
			dialog.dismiss();
			currentSoundDialog = null;
		});
		
		cancelButton.setOnClickListener(v -> {
			dialogMediaPlayer.release();
			dialog.dismiss();
		});
		
		if (_check && path != null) {
			File soundFile = new File(path);
			if (soundFile.exists()) {
				try {
					dialogMediaPlayer.setDataSource(path);
					dialogMediaPlayer.prepare();
					audioFileName.setText(soundFile.getName());
					soundNameEditText.setText(soundFile.getName().replaceFirst("[.][^.]+$", ""));
					playerLayout.setVisibility(View.VISIBLE);
					addSoundArea.setVisibility(View.GONE);
					int duration = dialogMediaPlayer.getDuration();
					seekBar.setMax(duration);
					endTime.setText(formatTime(duration));
				} catch (Exception e) {
					showMessage("Error loading sound: " + e.getMessage());
					audioFileName.setText("loading.mp3");
					playerLayout.setVisibility(View.GONE);
					addSoundArea.setVisibility(View.VISIBLE);
				}
			} else {
				showMessage("Sound file not found");
				audioFileName.setText("loading.mp3");
				playerLayout.setVisibility(View.GONE);
				addSoundArea.setVisibility(View.VISIBLE);
			}
		} else {
			audioFileName.setText("loading.mp3");
			playerLayout.setVisibility(View.GONE);
			addSoundArea.setVisibility(View.VISIBLE);
		}
		
		dialog.setCancelable(true);
		dialog.setOnDismissListener(d -> {
			dialogMediaPlayer.release();
			handler.removeCallbacks(updateSeekBar);
			currentSoundDialog = null;
		});
		dialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CD_SOUND_PICKER && resultCode == RESULT_OK && data != null) {
			selectedSoundPaths.clear();
			if (data.getClipData() != null) {
				for (int i = 0; i < data.getClipData().getItemCount(); i++) {
					Uri uri = data.getClipData().getItemAt(i).getUri();
					String path = getRealPathFromURI(uri);
					if (!path.isEmpty()) {
						selectedSoundPaths.add(path);
					}
				}
			} else if (data.getData() != null) {
				String path = getRealPathFromURI(data.getData());
				if (!path.isEmpty()) {
					selectedSoundPaths.add(path);
				}
			}
			if (!selectedSoundPaths.isEmpty()) {
				currentSoundPath = selectedSoundPaths.get(0);
				_SoundPickerDialog(true, currentSoundPath, false);
			} else {
				showMessage("Failed to load selected sound");
			}
		}
	}
	
	private String getRealPathFromURI(Uri uri) {
		String path = "";
		try {
			java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
			File file = new File(getCacheDir(), "temp_sound_" + System.currentTimeMillis() + ".mp3");
			java.io.FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			inputStream.close();
			outputStream.close();
			path = file.getAbsolutePath();
		} catch (Exception e) {
			showMessage("Error getting sound path: " + e.getMessage());
		}
		return path;
	}
	
	private void saveToCollection(String name, String path) {
		File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/sound_collection.json");
		List<HashMap<String, String>> collections = new ArrayList<>();
		Gson gson = new Gson();
		
		try {
			if (collectionFile.exists()) {
				collections = gson.fromJson(new FileReader(collectionFile),
				new TypeToken<List<HashMap<String, String>>>() {}.getType());
				if (collections == null) {
					collections = new ArrayList<>();
				}
			}
			
			HashMap<String, String> entry = new HashMap<>();
			entry.put("name", name);
			entry.put("path", path);
			collections.add(entry);
			
			FileWriter writer = new FileWriter(collectionFile);
			gson.toJson(collections, writer);
			writer.close();
			showMessage("Sound added to collection");
		} catch (Exception e) {
			showMessage("Error saving collection: " + e.getMessage());
		}
	}
	
	public void importSounds(List<String> soundPaths) {
		File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/sounds/" + sc_id);
		for (String path : soundPaths) {
			try {
				File sourceFile = new File(path);
				String name = sourceFile.getName();
				if (currentSoundPath != null && currentSoundPath.equals(path)) {
					String newName = new File(currentSoundPath).getName();
					if (!newName.isEmpty() && !newName.equals(name)) {
						name = newName;
					}
				}
				File destFile = new File(resourceDir, name);
				if (destFile.exists()) {
					showMessage("Sound " + name + " already exists");
					continue;
				}
				FileUtil.copyFile(sourceFile.getAbsolutePath(), destFile.getAbsolutePath());
				showMessage("Sound imported successfully");
			} catch (Exception e) {
				showMessage("Error importing sound: " + e.getMessage());
			}
		}
	}
	
	private String formatTime(int milliseconds) {
		int seconds = milliseconds / 1000;
		int minutes = seconds / 60;
		seconds = seconds % 60;
		return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
	}
	
	private void releaseMediaPlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	private void setupUI() {
		ImageView back = findViewById(R.id.ig_toolbar_back);
		TextView title = findViewById(R.id.tx_toolbar_title);
		ImageView loadFile = findViewById(R.id.ig_toolbar_load_file);
		
		loadFile.setImageResource(R.drawable.ic_delete_btn_white_96dp);
		
		ImageView importSound = new ImageView(this);
		importSound.setLayoutParams(new LinearLayout.LayoutParams(
		dpToPx(ManageSoundActivity.this, 40),
		dpToPx(ManageSoundActivity.this, 40)));
		importSound.setPadding(
		dpToPx(ManageSoundActivity.this, 9),
		dpToPx(ManageSoundActivity.this, 9),
		dpToPx(ManageSoundActivity.this, 9),
		dpToPx(ManageSoundActivity.this, 9)
		);
		importSound.setImageResource(R.drawable.ic_import_white_24dp);
		importSound.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		((ViewGroup) loadFile.getParent()).addView(importSound, ((ViewGroup) loadFile.getParent()).indexOfChild(loadFile));
		Helper.applyRippleToToolbarView(importSound);
		
		Helper.applyRippleToToolbarView(back);
		back.setOnClickListener(Helper.getBackPressedClickListener(this));
		title.setText("Sound manager");
		
		loadFile.setVisibility(View.VISIBLE);
		Helper.applyRippleToToolbarView(loadFile);
	}
	
	public static class SoundFragment extends Fragment {
		private RecyclerView recyclerView;
		private List<File> sounds = new ArrayList<>();
		private FloatingActionButton fabImport;
		private MediaPlayer fragmentMediaPlayer;
		private Handler fragmentHandler = new Handler();
		private Runnable updateProgressBar;
		private int currentPlayingPosition = -1;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_image, container, false); // Reuse layout
			recyclerView = view.findViewById(R.id.recycler_view);
			fabImport = view.findViewById(R.id.fab_import);
			
			recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1)); // Linear for audio
			loadSounds();
			recyclerView.setAdapter(new SoundAdapter());
			
			fabImport.setOnClickListener(v -> {
				((ManageSoundActivity) getActivity())._SoundPickerDialog(false, null, false);
			});
			
			return view;
		}
		
		private void loadSounds() {
			File resourceDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/resources/sounds/" + ((ManageSoundActivity) getActivity()).sc_id);
			if (resourceDir.exists()) {
				sounds = Arrays.asList(resourceDir.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav")));
			}
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();
			if (fragmentMediaPlayer != null) {
				fragmentMediaPlayer.release();
				fragmentMediaPlayer = null;
			}
			fragmentHandler.removeCallbacksAndMessages(null);
		}
		
		private class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
				return new ViewHolder(view);
			}
			
			@Override
			public void onBindViewHolder(ViewHolder holder, int position) {
				File sound = sounds.get(position);
				String name = sound.getName().substring(0, sound.getName().lastIndexOf('.'));
				holder.audioTitle.setText(name);
				
				try {
					MediaPlayer tempPlayer = new MediaPlayer();
					tempPlayer.setDataSource(sound.getAbsolutePath());
					tempPlayer.prepare();
					int duration = tempPlayer.getDuration();
					holder.endTime.setText(formatTime(duration));
					tempPlayer.release();
				} catch (Exception e) {
					holder.endTime.setText("0:00");
				}
				
				holder.playButton.setImageResource(currentPlayingPosition == position && fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play);
				
				holder.playButton.setOnClickListener(v -> {
					if (currentPlayingPosition == position && fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying()) {
						fragmentMediaPlayer.pause();
						holder.playButton.setImageResource(R.drawable.ic_play);
						fragmentHandler.removeCallbacks(updateProgressBar);
					} else {
						if (fragmentMediaPlayer != null) {
							fragmentMediaPlayer.release();
							fragmentHandler.removeCallbacks(updateProgressBar);
						}
						fragmentMediaPlayer = new MediaPlayer();
						try {
							fragmentMediaPlayer.setDataSource(sound.getAbsolutePath());
							fragmentMediaPlayer.prepare();
							fragmentMediaPlayer.start();
							currentPlayingPosition = position;
							holder.playButton.setImageResource(R.drawable.ic_pause);
							holder.audioProgress.setMax(fragmentMediaPlayer.getDuration());
							updateProgressBar = () -> {
								if (fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying()) {
									int currentPosition = fragmentMediaPlayer.getCurrentPosition();
									holder.audioProgress.setProgress(currentPosition);
									holder.startTime.setText(formatTime(currentPosition));
									fragmentHandler.postDelayed(() -> {
										ManageSoundActivity activity = (ManageSoundActivity) getActivity();
										if (activity != null) {
											// do something with activity
										}
									}, 1000);
									
								}
							};
							fragmentHandler.postDelayed(updateProgressBar, 0);
							fragmentMediaPlayer.setOnCompletionListener(mp -> {
								holder.playButton.setImageResource(R.drawable.ic_play);
								holder.audioProgress.setProgress(0);
								holder.startTime.setText("0:00");
								currentPlayingPosition = -1;
								fragmentHandler.removeCallbacks(updateProgressBar);
							});
						} catch (Exception e) {
							((ManageSoundActivity) getActivity()).showMessage("Error playing sound: " + e.getMessage());
						}
					}
					notifyDataSetChanged();
				});
				
				holder.itemView.setOnClickListener(v -> {
					((ManageSoundActivity) getActivity())._SoundPickerDialog(true, sound.getAbsolutePath(), true);
				});
			}
			
			@Override
			public int getItemCount() {
				return sounds.size();
			}
			
			class ViewHolder extends RecyclerView.ViewHolder {
				ImageView audioThumbnail;
				TextView audioTitle;
				TextView startTime;
				ProgressBar audioProgress;
				TextView endTime;
				ImageView playButton;
				
				ViewHolder(View itemView) {
					super(itemView);
					audioThumbnail = itemView.findViewById(R.id.audio_thumbnail);
					audioTitle = itemView.findViewById(R.id.audio_title);
					startTime = itemView.findViewById(R.id.start_time);
					audioProgress = itemView.findViewById(R.id.audio_progress);
					endTime = itemView.findViewById(R.id.end_time);
					playButton = itemView.findViewById(R.id.play_button);
				}
			}
		}
		
		private String formatTime(int milliseconds) {
			int seconds = milliseconds / 1000;
			int minutes = seconds / 60;
			seconds = seconds % 60;
			return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
		}
	}
	
	public static class SoundCollectionFragment extends Fragment {
		private RecyclerView recyclerView;
		private LinearLayout buttonLayout;
		private Button importButton, cancelButton;
		private List<HashMap<String, String>> collections = new ArrayList<>();
		private List<Integer> selectedPositions = new ArrayList<>();
		private MediaPlayer fragmentMediaPlayer;
		private Handler fragmentHandler = new Handler();
		private Runnable updateProgressBar;
		private int currentPlayingPosition = -1;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_collection, container, false);
			recyclerView = view.findViewById(R.id.recycler_view);
			buttonLayout = view.findViewById(R.id.button_layout);
			importButton = view.findViewById(R.id.import_button);
			cancelButton = view.findViewById(R.id.cancel_button);
			
			recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1)); // Linear for audio
			loadCollections();
			recyclerView.setAdapter(new CollectionAdapter());
			
			importButton.setOnClickListener(v -> {
				List<String> pathsToImport = new ArrayList<>();
				for (int pos : selectedPositions) {
					pathsToImport.add(collections.get(pos).get("path"));
				}
				((ManageSoundActivity) getActivity()).importSounds(pathsToImport);
				selectedPositions.clear();
				buttonLayout.setVisibility(View.GONE);
				recyclerView.getAdapter().notifyDataSetChanged();
			});
			
			cancelButton.setOnClickListener(v -> {
				selectedPositions.clear();
				buttonLayout.setVisibility(View.GONE);
				recyclerView.getAdapter().notifyDataSetChanged();
			});
			
			return view;
		}
		
		private void loadCollections() {
			File collectionFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ".blacklogics/collection/sound_collection.json");
			if (collectionFile.exists()) {
				try {
					collections = new Gson().fromJson(new FileReader(collectionFile),
					new TypeToken<List<HashMap<String, String>>>() {}.getType());
					if (collections == null) {
						collections = new ArrayList<>();
					}
				} catch (Exception e) {
					((ManageSoundActivity) getActivity()).showMessage("Error loading collections");
				}
			}
		}
		
		@Override
		public void onDestroyView() {
			super.onDestroyView();
			if (fragmentMediaPlayer != null) {
				fragmentMediaPlayer.release();
				fragmentMediaPlayer = null;
			}
			fragmentHandler.removeCallbacksAndMessages(null);
		}
		
		private class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
			@Override
			public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio, parent, false);
				return new ViewHolder(view);
			}
			
			@Override
			public void onBindViewHolder(ViewHolder holder, int position) {
				HashMap<String, String> item = collections.get(position);
				holder.audioTitle.setText(item.get("name"));
				
				try {
					MediaPlayer tempPlayer = new MediaPlayer();
					tempPlayer.setDataSource(item.get("path"));
					tempPlayer.prepare();
					int duration = tempPlayer.getDuration();
					holder.endTime.setText(formatTime(duration));
					tempPlayer.release();
				} catch (Exception e) {
					holder.endTime.setText("0:00");
				}
				
				holder.playButton.setImageResource(currentPlayingPosition == position && fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play);
				holder.checkBox.setChecked(selectedPositions.contains(position));
				
				holder.playButton.setOnClickListener(v -> {
					if (currentPlayingPosition == position && fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying()) {
						fragmentMediaPlayer.pause();
						holder.playButton.setImageResource(R.drawable.ic_play);
						fragmentHandler.removeCallbacks(updateProgressBar);
					} else {
						if (fragmentMediaPlayer != null) {
							fragmentMediaPlayer.release();
							fragmentHandler.removeCallbacks(updateProgressBar);
						}
						fragmentMediaPlayer = new MediaPlayer();
						try {
							fragmentMediaPlayer.setDataSource(item.get("path"));
							fragmentMediaPlayer.prepare();
							fragmentMediaPlayer.start();
							currentPlayingPosition = position;
							holder.playButton.setImageResource(R.drawable.ic_pause);
							holder.audioProgress.setMax(fragmentMediaPlayer.getDuration());
							updateProgressBar = () -> {
								if (fragmentMediaPlayer != null && fragmentMediaPlayer.isPlaying()) {
									int currentPosition = fragmentMediaPlayer.getCurrentPosition();
									holder.audioProgress.setProgress(currentPosition);
									holder.startTime.setText(formatTime(currentPosition));
									fragmentHandler.postDelayed(() -> {
										ManageSoundActivity activity = (ManageSoundActivity) getActivity();
										if (activity != null) {
											// do something with activity
										}
									}, 1000);
									
								}
							};
							fragmentHandler.postDelayed(updateProgressBar, 0);
							fragmentMediaPlayer.setOnCompletionListener(mp -> {
								holder.playButton.setImageResource(R.drawable.ic_play);
								holder.audioProgress.setProgress(0);
								holder.startTime.setText("0:00");
								currentPlayingPosition = -1;
								fragmentHandler.removeCallbacks(updateProgressBar);
							});
						} catch (Exception e) {
							((ManageSoundActivity) getActivity()).showMessage("Error playing sound: " + e.getMessage());
						}
					}
					notifyDataSetChanged();
				});
				
				holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
					if (isChecked) {
						selectedPositions.add(position);
					} else {
						selectedPositions.remove(Integer.valueOf(position));
					}
					buttonLayout.setVisibility(selectedPositions.isEmpty() ? View.GONE : View.VISIBLE);
				});
				
				holder.itemView.setOnClickListener(v -> {
					((ManageSoundActivity) getActivity())._SoundPickerDialog(true, item.get("path"), true);
				});
			}
			
			@Override
			public int getItemCount() {
				return collections.size();
			}
			
			class ViewHolder extends RecyclerView.ViewHolder {
				ImageView audioThumbnail;
				TextView audioTitle;
				TextView startTime;
				ProgressBar audioProgress;
				TextView endTime;
				ImageView playButton;
				CheckBox checkBox;
				
				ViewHolder(View itemView) {
					super(itemView);
					audioThumbnail = itemView.findViewById(R.id.audio_thumbnail);
					audioTitle = itemView.findViewById(R.id.audio_title);
					startTime = itemView.findViewById(R.id.start_time);
					audioProgress = itemView.findViewById(R.id.audio_progress);
					endTime = itemView.findViewById(R.id.end_time);
					playButton = itemView.findViewById(R.id.play_button);
					checkBox = itemView.findViewById(R.id.check_box); // Assumes check_box exists in item_audio.xml for collection
				}
			}
			
			private String formatTime(int milliseconds) {
				int seconds = milliseconds / 1000;
				int minutes = seconds / 60;
				seconds = seconds % 60;
				return String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
			}
		}
	}
}
