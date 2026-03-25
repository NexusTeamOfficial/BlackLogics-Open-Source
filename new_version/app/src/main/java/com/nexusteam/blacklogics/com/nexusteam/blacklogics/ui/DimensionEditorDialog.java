package com.nexusteam.blacklogics.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.nexusteam.blacklogics.constants.DimensionType;
import com.nexusteam.blacklogics.R;

public class DimensionEditorDialog {

    private Context context;
    private View targetView;
    private int dimensionType;
    private Runnable onApplyCallback;

    public DimensionEditorDialog(
            Context context,
            View targetView,
            int dimensionType,
            Runnable onApplyCallback
    ) {
        this.context = context;
        this.targetView = targetView;
        this.dimensionType = dimensionType;
        this.onApplyCallback = onApplyCallback;
    }

    public void show() {

        AlertDialog.Builder dialog =
                new AlertDialog.Builder(context);

        dialog.setTitle(
                dimensionType == DimensionType.PADDING
                        ? "Padding Settings"
                        : "Margin Settings"
        );

        View dialogView = LayoutInflater
                .from(context)
                .inflate(R.layout.dimension_settings, null);

        dialog.setView(dialogView);

        final EditText etAll    = dialogView.findViewById(R.id.et_all);
        final CheckBox cbAll    = dialogView.findViewById(R.id.cb_all);
        final EditText etLeft  = dialogView.findViewById(R.id.et_left);
        final CheckBox cbLeft  = dialogView.findViewById(R.id.cb_left);
        final EditText etTop   = dialogView.findViewById(R.id.et_top);
        final CheckBox cbTop   = dialogView.findViewById(R.id.cb_top);
        final EditText etRight = dialogView.findViewById(R.id.et_right);
        final CheckBox cbRight = dialogView.findViewById(R.id.cb_right);
        final EditText etBottom= dialogView.findViewById(R.id.et_bottom);
        final CheckBox cbBottom= dialogView.findViewById(R.id.cb_bottom);
        final TextView tvTitle = dialogView.findViewById(R.id.tv_title);

        tvTitle.setText(
                dimensionType == DimensionType.PADDING
                        ? "Padding (in dp)"
                        : "Margin (in dp)"
        );

        loadCurrentValues(
                etLeft, etTop, etRight, etBottom
        );

        setupCheckboxToggle(cbAll, etAll,
                new EditText[]{etLeft, etTop, etRight, etBottom});
        setupCheckboxToggle(cbLeft, etLeft, null);
        setupCheckboxToggle(cbTop, etTop, null);
        setupCheckboxToggle(cbRight, etRight, null);
        setupCheckboxToggle(cbBottom, etBottom, null);

        etAll.addTextChangedListener(
                createAllValueWatcher(
                        cbAll,
                        etLeft, etTop, etRight, etBottom
                )
        );

        dialog.setPositiveButton(
                "Apply",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        applyValues(
                                etLeft, etTop, etRight, etBottom,
                                cbLeft, cbTop, cbRight, cbBottom
                        );

                        if (onApplyCallback != null) {
                            onApplyCallback.run();
                        }
                    }
                }
        );

        dialog.setNegativeButton("Cancel", null);
        dialog.show();
    }



    private void loadCurrentValues(
            EditText l, EditText t, EditText r, EditText b
    ) {

        if (dimensionType == DimensionType.PADDING) {
            l.setText(String.valueOf(pxToDp(targetView.getPaddingLeft())));
            t.setText(String.valueOf(pxToDp(targetView.getPaddingTop())));
            r.setText(String.valueOf(pxToDp(targetView.getPaddingRight())));
            b.setText(String.valueOf(pxToDp(targetView.getPaddingBottom())));
        } else {
            ViewGroup.LayoutParams p = targetView.getLayoutParams();
            if (p instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams m =
                        (ViewGroup.MarginLayoutParams) p;

                l.setText(String.valueOf(pxToDp(m.leftMargin)));
                t.setText(String.valueOf(pxToDp(m.topMargin)));
                r.setText(String.valueOf(pxToDp(m.rightMargin)));
                b.setText(String.valueOf(pxToDp(m.bottomMargin)));
            }
        }
    }

    private void applyValues(
            EditText l, EditText t, EditText r, EditText b,
            CheckBox cl, CheckBox ct, CheckBox cr, CheckBox cb
    ) {

        int left   = parseDp(l);
        int top    = parseDp(t);
        int right  = parseDp(r);
        int bottom = parseDp(b);

        if (dimensionType == DimensionType.PADDING) {
            targetView.setPadding(left, top, right, bottom);
        } else {
            ViewGroup.MarginLayoutParams p =
                    (ViewGroup.MarginLayoutParams)
                            targetView.getLayoutParams();

            p.setMargins(left, top, right, bottom);
            targetView.setLayoutParams(p);
        }
    }

    private TextWatcher createAllValueWatcher(
            final CheckBox cb,
            final EditText l,
            final EditText t,
            final EditText r,
            final EditText b
    ) {
        return new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            public void onTextChanged(CharSequence s, int a, int b, int c) {}
            public void afterTextChanged(Editable s) {
                if (cb.isChecked() && !TextUtils.isEmpty(s)) {
                    l.setText(s.toString());
                    t.setText(s.toString());
                    r.setText(s.toString());
                    b.setText(s.toString());
                }
            }
        };
    }

    private int parseDp(EditText et) {
        try {
            return (int) (
                    Float.parseFloat(et.getText().toString())
                            * context.getResources()
                            .getDisplayMetrics().density
            );
        } catch (Exception e) {
            return 0;
        }
    }

    private int pxToDp(int px) {
        return (int) (
                px / context.getResources()
                        .getDisplayMetrics().density
        );
    }


    private void setupCheckboxToggle(
            CheckBox cb,
            EditText et,
            EditText[] linked
    ) {

    }
}
