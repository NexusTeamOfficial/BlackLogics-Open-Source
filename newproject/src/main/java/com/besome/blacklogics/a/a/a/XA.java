package a.a.a;

import android.view.View;
import com.besome.blacklogics.lib.ui.EasyDeleteEditText;

public class XA implements View.OnFocusChangeListener {
    private final EasyDeleteEditText a;

    public XA(EasyDeleteEditText easyDeleteEditText) {
        this.a = easyDeleteEditText;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
      //  this.a.updateClearButtonVisibility();
    }
}