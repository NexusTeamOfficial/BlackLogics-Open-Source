package com.besome.blacklogics.logic.editor;

import android.content.Context;
import com.google. android.material.textfield.TextInputLayout;
import android.text.Spanned;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.besome.blacklogics.R;

public class VariableNameValidator extends BaseInputValidator {
    private ArrayList<String> alreadyUsed;
    private String exception;
    private String[] keywords;
    Pattern ps = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");
    private String[] usedwords;

    public VariableNameValidator(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.keywords = strArr;
        this.usedwords = strArr2;
        this.alreadyUsed = arrayList;
    }

    public VariableNameValidator(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        this.keywords = strArr;
        this.usedwords = strArr2;
        this.alreadyUsed = arrayList;
        this.exception = str;
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() < 1) {
            this.textInputLayout.setErrorEnabled(true);
            this.textInputLayout.setError(this.context.getString(R.string.err_valid_min_len, new Object[]{Integer.valueOf(1)}));
            this.valid = false;
        } else if (charSequence.toString().trim().length() > 20) {
            this.textInputLayout.setErrorEnabled(true);
            this.textInputLayout.setError(this.context.getString(R.string.err_valid_max_len, new Object[]{Integer.valueOf(20)}));
            this.valid = false;
        } else if (this.exception != null && this.exception.length() > 0 && charSequence.toString().equals(this.exception)) {
            this.valid = true;
        } else if (this.alreadyUsed.indexOf(charSequence.toString()) >= 0) {
            this.textInputLayout.setErrorEnabled(true);
            this.textInputLayout.setError(this.context.getString(R.string.err_valid_already_used));
            this.valid = false;
        } else {
            boolean z;
            for (Object equals : this.usedwords) {
                if (charSequence.toString().equals(equals)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (z) {
                this.textInputLayout.setErrorEnabled(true);
                this.textInputLayout.setError(this.context.getString(R.string.err_valid_already_used));
                this.valid = false;
                return;
            }
            for (Object equals2 : this.keywords) {
                if (charSequence.toString().equals(equals2)) {
                    z = true;
                    break;
                }
            }
            z = false;
            if (z) {
                this.textInputLayout.setErrorEnabled(true);
                this.textInputLayout.setError(this.context.getString(R.string.err_use_rev_name));
                this.valid = false;
            } else if (!Character.isLetter(charSequence.charAt(0))) {
                this.textInputLayout.setErrorEnabled(true);
                this.textInputLayout.setError(this.context.getString(R.string.err_start_number));
                this.valid = false;
            } else if (this.ps.matcher(charSequence.toString()).matches()) {
                this.textInputLayout.setErrorEnabled(false);
                this.valid = true;
            } else {
                this.textInputLayout.setErrorEnabled(true);
                this.textInputLayout.setError(this.context.getString(R.string.err_valid_only_eng_3));
                this.valid = false;
            }
        }
    }

    public void setUsedWords(String[] strArr) {
        this.usedwords = strArr;
    }
}
