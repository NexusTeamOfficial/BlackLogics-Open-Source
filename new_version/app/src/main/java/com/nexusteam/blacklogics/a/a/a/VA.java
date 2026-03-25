package a.a.a;

import android.view.View;
import com.besome.blacklogics.lib.ui.EasyDeleteEditText;

public class VA implements View.OnClickListener {
    private final EasyDeleteEditText a;

    public VA(EasyDeleteEditText easyDeleteEditText) {
        this.a = easyDeleteEditText;
    }

    @Override
    public void onClick(View view) {
        this.a.getEditText().setText("");
    }
}