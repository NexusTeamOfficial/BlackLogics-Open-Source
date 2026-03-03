package com.besome.blacklogics.dialogfragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AlertDialog;
import com.nexusteam.blacklogics.R;
import com.nexusteam.internal.StringResourceManager;
import com.nexusteam.blacklogics.model.DataModel;
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
    private Context context;
    
    public VersionDialog(Context context) {
        super(context);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initializeDialog();
    }
    
    private void initializeDialog() {
        setTitle(StringResourceManager.a().a(context, R.string.ver_control_title));
        

        View view = inflater.inflate(R.layout.dialog_version, null);
        setView(view);
        
        majorPicker = view.findViewById(R.id.pickerMajor);
        minorPicker = view.findViewById(R.id.pickerMinor);
        patchPicker = view.findViewById(R.id.pickerPatch);
        
        setupNumberPickers();
        
        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSaveClicked();
                dialog.dismiss();
            }
        });
        
        setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        
    }
    
    private void setupNumberPickers() {

        majorPicker.setMinValue(DataModel.DEFAULT_VERSION);
        majorPicker.setMaxValue(DataModel.MAX_VERSION);
        majorPicker.setValue(DataModel.DEFAULT_VERSION);
        

        minorPicker.setMinValue(DataModel.ZERO_VERSION);
        minorPicker.setMaxValue(DataModel.MAX_VERSION);
        minorPicker.setValue(DataModel.ZERO_VERSION);
        

        patchPicker.setMinValue(DataModel.ZERO_VERSION);
        patchPicker.setMaxValue(DataModel.MAX_VERSION);
        patchPicker.setValue(DataModel.ZERO_VERSION);
        

        majorPicker.setWrapSelectorWheel(true);
        minorPicker.setWrapSelectorWheel(true);
        patchPicker.setWrapSelectorWheel(true);
    }
    
    private void onSaveClicked() {
        int major = majorPicker.getValue(); // Version code
        int minor = minorPicker.getValue(); // Version name part 1
        int patch = patchPicker.getValue(); // Version name part 2
        

        String versionName = minor + DataModel.ADD_DOT + patch;
        

        String versionCode = String.valueOf(major);
        

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
        return setInitialValues(DataModel.DEFAULT_VERSION, DataModel.ZERO_VERSION, DataModel.ZERO_VERSION);
    }
    
    public VersionDialog setInitialValues() {
        return setInitialValues(DataModel.DEFAULT_VERSION, DataModel.ZERO_VERSION, DataModel.ZERO_VERSION);
    }
    
    @Override
    public AlertDialog create() {
        return super.create();
    }
}