package com.nexusteam.internal;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Validator for Activity / Custom View names.
 * Rules:
 *  - 3–20 characters
 *  - must start with a lowercase letter
 *  - only a-z, 0-9 and underscore allowed
 *  - cannot be a reserved keyword
 *  - cannot collide with existing names (case + underscore/hyphen insensitive)
 */
public class ActivityNameValidator extends le {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");

    private final String[] reservedKeywords;
    private final ArrayList<String> existingNames;
    private final String originalName;   // used when editing (allow the same name)

    public ActivityNameValidator(Context context, TextInputLayout inputLayout,
              String[] reservedKeywords, ArrayList<String> existingNames) {
        this(context, inputLayout, reservedKeywords, existingNames, null);
    }

    public ActivityNameValidator(Context context, TextInputLayout inputLayout,
              String[] reservedKeywords, ArrayList<String> existingNames, String originalName) {
        super(context, inputLayout);
        this.reservedKeywords = reservedKeywords != null ? reservedKeywords : new String[0];
        this.existingNames = existingNames != null ? existingNames : new ArrayList<String>();
        this.originalName     = originalName;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        return null; // no character filtering
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        String value = text.toString().trim();

        // 1. Minimum length
        if (value.length() < 3) {
            showError(kq.a().a(b, R.string.invalid_value_min_lenth, 3));
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

        // 4. Reserved keywords
        if (containsNormalized(reservedKeywords, value)) {
            showError(kq.a().a(b, R.string.logic_editor_message_reserved_keywords));
            return;
        }

        // 5. Already used name (or "main")
        if ("main".equalsIgnoreCase(value) || containsNormalized(existingNames, value)) {
            showError(kq.a().a(b, R.string.common_message_name_unavailable));
            return;
        }

        // 6. Must start with a letter
        if (!Character.isLetter(value.charAt(0))) {
            showError(kq.a().a(b, R.string.logic_editor_message_variable_name_must_start_letter));
            return;
        }

        // 7. Pattern check
        if (NAME_PATTERN.matcher(value).matches()) {
            clearError();
        } else {
            showError(kq.a().a(b, R.string.invalid_value_rule_4));
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
        name = name.trim().toLowerCase();
        if (name.endsWith(".xml"))  name = name.substring(0, name.length() - 4);
        if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
        return name.replace("_", "").replace("-", "");
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