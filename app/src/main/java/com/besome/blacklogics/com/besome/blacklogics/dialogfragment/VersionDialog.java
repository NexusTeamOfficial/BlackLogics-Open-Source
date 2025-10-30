package com.besome.blacklogics.dialogfragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AlertDialog;
import com.besome.blacklogics.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class VersionDialog extends MaterialAlertDialogBuilder {
	
	public interface VersionDialogListener {
		void onVersionSelect(String versionName, String versionCode, String minorNumber);
	}
	
	private VersionDialogListener listener;
	private NumberPicker majorPicker;
	private NumberPicker minorPicker;
	private NumberPicker patchPicker;
	private LayoutInflater inflater;
	
	public VersionDialog(Context context) {
		super(context);
		this.inflater = LayoutInflater.from(context);
		initializeDialog();
	}
	
	private void initializeDialog() {
		setTitle("Version Control");
		
		// Create custom view
		View view = inflater.inflate(R.layout.dialog_version, null);
		setView(view);
		
		majorPicker = view.findViewById(R.id.pickerMajor);
		minorPicker = view.findViewById(R.id.pickerMinor);
		patchPicker = view.findViewById(R.id.pickerPatch);
		
		setupNumberPickers();
		
		setPositiveButton("Save", (dialog, which) -> {
			onSaveClicked();
			dialog.dismiss();
		});
		
		setNegativeButton("Cancel", (dialog, which) -> {
			dialog.dismiss();
		});
	}
	
	private void setupNumberPickers() {
		// Major version (typically starts from 1)
		majorPicker.setMinValue(1);
		majorPicker.setMaxValue(99);
		majorPicker.setValue(1);
		
		// Minor version (0-99)
		minorPicker.setMinValue(0);
		minorPicker.setMaxValue(99);
		minorPicker.setValue(0);
		
		// Patch version (0-99)
		patchPicker.setMinValue(0);
		patchPicker.setMaxValue(99);
		patchPicker.setValue(0);
		
		// Wrap selector wheel for better UX
		majorPicker.setWrapSelectorWheel(true);
		minorPicker.setWrapSelectorWheel(true);
		patchPicker.setWrapSelectorWheel(true);
	}
	
	private void onSaveClicked() {
		int major = majorPicker.getValue(); // Version code
		int minor = minorPicker.getValue(); // Version name part 1
		int patch = patchPicker.getValue(); // Version name part 2
		
		// Version Name = "minor.patch" (like "3.4")
		String versionName = minor + "." + patch;
		
		// Version Code = just major value (like "1")
		String versionCode = String.valueOf(major);
		
		// Minor Number = patch value (like "4")
		String minorNumber = String.valueOf(patch);
		
		if (listener != null) {
			listener.onVersionSelect(versionName, versionCode, minorNumber);
		}
	}
	
	
	public VersionDialog setVersionDialogListener(VersionDialogListener listener) {
		this.listener = listener;
		return this;
	}
	
	public VersionDialog setInitialValues(int major, int minor, int patch) {
		majorPicker.setValue(major);
		minorPicker.setValue(minor);
		patchPicker.setValue(patch);
		return this;
	}
	
	public VersionDialog setInitialValues(String name) {
		return setInitialValues(1, 0, 0);
	}
	
	public VersionDialog setInitialValues() {
		return setInitialValues(1, 0, 0);
	}
	
	@Override
	public AlertDialog create() {
		return super.create();
	}
}
