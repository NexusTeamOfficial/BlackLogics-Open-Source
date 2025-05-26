package a.a.a;

import android.text.Editable;
import android.text.TextWatcher;
import com.besome.blacklogics.lib.ui.EasyDeleteEditText;

public class WA implements TextWatcher {
    private final EasyDeleteEditText a;

    public WA(EasyDeleteEditText easyDeleteEditText) {
        this.a = easyDeleteEditText;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Empty implementation
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Empty implementation
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       // this.a.updateClearButtonVisibility();
    }
}