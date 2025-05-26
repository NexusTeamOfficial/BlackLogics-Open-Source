package com.besome.blacklogics.custom;

import android.app.Dialog;
import com.besome.blacklogics.R;
import com.besome.blacklogics.adapter.*;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustomSpinner extends RelativeLayout {
	private TextView tvSelectedItem;
	private ImageView ivDropdownArrow;
	private PopupWindow popupWindow;
	private List<String> items;
	private OnItemSelectedCallback callback;
	private boolean useDefaultClickHandler = true;
	
	public interface OnItemSelectedCallback {
		void onItemSelected(String item);
	}
	
	public CustomSpinner(Context context) {
		super(context);
		init(context);
	}
	
	public CustomSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.custom_spinner_layout, this, true);
		tvSelectedItem = findViewById(R.id.tv_selected_item);
		ivDropdownArrow = findViewById(R.id.iv_dropdown_arrow);
		
		if (useDefaultClickHandler) {
			setOnClickListener(v -> toggleDropdown());
		}
	}
	
	public void setItems(List<String> items) {
		this.items = items;
		if (!items.isEmpty()) {
			tvSelectedItem.setText(items.get(0));
		}
	}
	
	public List<String> getItems() {
		return items;
	}
	
	public void setSelectedItem(String item) {
		if (items != null && items.contains(item)) {
			tvSelectedItem.setText(item);
		}
	}
	
	public String getSelectedItem() {
		return tvSelectedItem != null ? tvSelectedItem.getText().toString() : null;
	}
	
	public void setOnItemSelectedCallback(OnItemSelectedCallback callback) {
		this.callback = callback;
	}
	
	public void setUseDefaultClickHandler(boolean useDefault) {
		this.useDefaultClickHandler = useDefault;
		if (useDefault) {
			setOnClickListener(v -> toggleDropdown());
		} else {
			setOnClickListener(null); // Clear default click listener
		}
	}
	
	private void toggleDropdown() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			return;
		}
		
		View dropdownView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_layout, null);
		RecyclerView recyclerView = dropdownView.findViewById(R.id.rv_dropdown);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		DropdownAdapter adapter = new DropdownAdapter(items, item -> {
			tvSelectedItem.setText(item);
			if (callback != null) {
				callback.onItemSelected(item);
			}
			popupWindow.dismiss();
		});
		recyclerView.setAdapter(adapter);
		
		popupWindow = new PopupWindow(dropdownView, getWidth(), LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_background_new));
		popupWindow.setElevation(8);
		popupWindow.showAsDropDown(this);
	}
	
	public void showCustomSpinnerDialog() {
		/*Dialog dialog = new Dialog(getContext());
		dialog.setContentView(R.layout.dropdown_layout);
		
		RecyclerView recyclerView = dialog.findViewById(R.id.rv_dropdown);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		DropdownAdapter adapter = new DropdownAdapter(items, item -> {
		tvSelectedItem.setText(item);
		if (callback != null) {
		callback.onItemSelected(item);
		}
		dialog.dismiss();
		});
		recyclerView.setAdapter(adapter);
		
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.spinner_background_new);
		dialog.getWindow().setLayout(getWidth(), LayoutParams.WRAP_CONTENT);
		dialog.show();*/
	}
}
