package com.besome.blacklogics.logic.editor;

import android.content.Context;
import com.google. android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class BaseInputValidator implements TextWatcher, InputFilter {
    protected Context context;
    protected EditText editText;
    protected int errTextRes;
    protected TextInputLayout textInputLayout;
    protected boolean valid;

    public BaseInputValidator(Context context, TextInputLayout textInputLayout) {
        this.context = context;
        this.textInputLayout = textInputLayout;
        this.editText = textInputLayout.getEditText();
        this.editText.setFilters(new InputFilter[]{this});
        this.editText.addTextChangedListener(this);
    }

    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            this.textInputLayout.setErrorEnabled(false);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public boolean isValid() {
        if (!this.valid) {
            this.editText.requestFocus();
        }
        return this.valid;
    }

    public void setErrorTextRes(int i) {
        this.errTextRes = i;
    }

    public void setText(String str) {
        this.valid = true;
        this.editText.setText(str);
    }
}
