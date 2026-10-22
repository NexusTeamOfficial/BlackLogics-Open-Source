package com.nexusteam.internal;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Validator for variable / more-block names.
 * Rules:
 *  - 1–20 characters
 *  - must start with a letter (a-z / A-Z)
 *  - only letters, digits and underscore allowed
 *  - cannot be a reserved keyword
 *  - cannot collide with existing names (case + underscore/hyphen insensitive)
 */
public class lr extends le {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    private final String[] reservedKeywords;
    private String[] forbiddenNames;          // can be updated at runtime
    private final ArrayList<String> existingNames;
    private String originalName;              // used when editing

    public lr(Context context, TextInputLayout inputLayout,
              String[] reservedKeywords, String[] forbiddenNames, ArrayList<String> existingNames) {
        super(context, inputLayout);
        this.reservedKeywords = reservedKeywords != null ? reservedKeywords : new String[0];
        this.forbiddenNames   = forbiddenNames   != null ? forbiddenNames   : new String[0];
        this.existingNames = existingNames != null ? existingNames : new ArrayList<String>();
    }

    /** Update the list of already-used variable names (called when user adds a variable). */
    public void a(String[] newForbidden) {
        this.forbiddenNames = newForbidden != null ? newForbidden : new String[0];
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        return null;
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        String value = text.toString().trim();

        // 1. Minimum length
        if (value.length() < 1) {
            showError(kq.a().a(b, R.string.invalid_value_min_lenth, 1));
            return;
        }

        // 2. Maximum length
        if (value.length() > 20) {
            showError(kq.a().a(b, R.string.invalid_value_max_lenth, 20));
            return;
        }

        // 3. Editing the same item → always valid
        if (originalName != null && originalName.length() > 0 && value.equals(originalName)) {
            clearError();
            return;
        }

        // 4. Already used in the current list of variables
        if (containsNormalized(existingNames, value)) {
            showError(kq.a().a(b, R.string.common_message_name_unavailable));
            return;
        }

        // 5. Already used (runtime-updated list)
        if (containsNormalized(forbiddenNames, value)) {
            showError(kq.a().a(b, R.string.common_message_name_unavailable));
            return;
        }

        // 6. Reserved keywords
        if (containsNormalized(reservedKeywords, value)) {
            showError(kq.a().a(b, R.string.logic_editor_message_reserved_keywords));
            return;
        }

        // 7. Must start with a letter
        if (!Character.isLetter(value.charAt(0))) {
            showError(kq.a().a(b, R.string.logic_editor_message_variable_name_must_start_letter));
            return;
        }

        // 8. Pattern check
        if (NAME_PATTERN.matcher(value).matches()) {
            clearError();
        } else {
            showError(kq.a().a(b, R.string.invalid_value_rule_3));
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Helpers
    // ──────────────────────────────────────────────────────────────

    private void showError(String message) {
        c.setErrorEnabled(true);
        c.setError(message);
        e = false;
    }

    private void clearError() {
        c.setErrorEnabled(false);
        e = true;
    }

    /** Canonical form used only for duplicate detection. */
    private static String normalize(String name) {
        if (name == null) return "";
        return name.trim().toLowerCase().replace("_", "").replace("-", "");
    }

    private boolean containsNormalized(String[] list, String name) {
        if (list == null) return false;
        String key = normalize(name);
        for (String item : list) {
            if (normalize(item).equals(key)) return true;
        }
        return false;
    }

    private boolean containsNormalized(ArrayList<String> list, String name) {
        if (list == null) return false;
        String key = normalize(name);
        for (String item : list) {
            if (normalize(item).equals(key)) return true;
        }
        return false;
    }
}