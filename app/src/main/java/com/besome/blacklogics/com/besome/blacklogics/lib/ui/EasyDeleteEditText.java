package com.besome.blacklogics.lib.ui;

import a.a.a.VA;
import a.a.a.WA;
import a.a.a.XA;
import a.a.a.wB;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.besome.blacklogics.R;

public class EasyDeleteEditText extends RelativeLayout {
   public Context context;
   public ImageView imageDelete; // R.id.image_delete
   public EditText editText;     // R.id.edittext
   public TextInputLayout textInputLayout; // R.id.textinputlayout

   public EasyDeleteEditText(Context ctx) {
      super(ctx);
      init(ctx);
   }

   public EasyDeleteEditText(Context ctx, AttributeSet attrs) {
      super(ctx, attrs);
      init(ctx);
   }

   public EasyDeleteEditText(Context ctx, AttributeSet attrs, int defStyleAttr) {
      super(ctx, attrs, defStyleAttr);
      init(ctx);
   }

   public static EditText getEditTextInstance(EasyDeleteEditText view) {
      return view.editText;
   }

   public static void triggerDelete(EasyDeleteEditText view) {
      view.updateDeleteVisibility();
   }

   public final void updateDeleteVisibility() {
      if (editText.isEnabled() && editText.hasFocus() && editText.length() > 0) {
         imageDelete.setVisibility(VISIBLE);
      } else {
         imageDelete.setVisibility(GONE);
      }
   }

   public final void init(Context ctx) {
      this.context = ctx;
      wB.a(ctx, this, R.layout.easy_delete_edittext);
      this.imageDelete = findViewById(R.id.img_delete);
      this.editText = findViewById(R.id.easy_ed_input);
      this.textInputLayout = findViewById(R.id.easy_ti_input);

      imageDelete.setVisibility(GONE);
      imageDelete.setOnClickListener(new VA(this));
      editText.addTextChangedListener(new WA(this));
      editText.setOnFocusChangeListener(new XA(this));
      updateDeleteVisibility();
   }

   public EditText getEditText() {
      return editText;
   }

   public TextInputLayout getTextInputLayout() {
      return textInputLayout;
   }

   public void setHint(String hint) {
      textInputLayout.setHint(hint);
   }
}