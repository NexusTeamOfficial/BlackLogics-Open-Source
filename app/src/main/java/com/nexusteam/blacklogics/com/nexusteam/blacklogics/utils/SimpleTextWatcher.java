package com.nexusteam.blacklogics.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class SimpleTextWatcher implements TextWatcher {

    public interface OnTextChanged {
        void onTextChanged(String text);
    }

    private final OnTextChanged listener;

    public SimpleTextWatcher(OnTextChanged listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (listener != null) {
            listener.onTextChanged(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
