package com.nexusteam.blacklogics.design;

import android.app.Dialog;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.development.Complex;
import com.nexusteam.blacklogics.development.Complex.ViewItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nexusteam.blacklogics.DesignActivity;
import com.nexusteam.blacklogics.bean.AppState;
import com.nexusteam.internal.beans.ProjectFileBean;
import com.nexusteam.blacklogics.R;

public class CustomSpinnerDialog {
	
	private final DesignActivity activity;
	private final Complex complex;
	private final String sc_id;
	
	public CustomSpinnerDialog(DesignActivity activity, String sc_id, Complex complex) {
		this.activity = activity;
		this.sc_id = sc_id;
		this.complex = complex;
	}
	
	public void show() {
		
		final Dialog dialog =
		new Dialog(activity, R.style.TransparentDialogTheme);
		dialog.setContentView(R.layout.dialog_custom_view);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		
		final RecyclerView recyclerView =
		dialog.findViewById(R.id.recyclerView);
		final FloatingActionButton fab =
		dialog.findViewById(R.id.fabAddView);
		final MaterialButtonToggleGroup toggleGroup =
		dialog.findViewById(R.id.toggleGroup);
		final MaterialButton tabView =
		dialog.findViewById(R.id.tabView);
		final MaterialButton tabCustom =
		dialog.findViewById(R.id.tabCustomView);
		
		tabView.setChecked(true);
		
		activity.setOnActivityListChangedListener(
		new DesignActivity.OnActivityListChangedListener() {
			@Override
			public void onActivityListChanged() {
				complex.refreshViewAdapter(recyclerView);
			}
		}
		);
		
		dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(android.content.DialogInterface d) {
				activity.setOnActivityListChangedListener(null);
			}
		});
		
		final Complex.OnViewItemClickListener onItemSelected =
		new Complex.OnViewItemClickListener() {
			@Override
			public void onItemClick(ViewItem item) {
				ProjectFileBean bean = new ProjectFileBean(
				ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY,
				item.xmlName
				);
				
				AppState.setCurrentFile(bean);
				dialog.dismiss();
				activity.handleItemSelection(item);
			}
		};
		
		toggleGroup.addOnButtonCheckedListener(
		new MaterialButtonToggleGroup.OnButtonCheckedListener() {
			@Override
			public void onButtonChecked(
			MaterialButtonToggleGroup group,
			int checkedId,
			boolean isChecked) {
				
				if (!isChecked) return;
				
				if (checkedId == R.id.tabView) {
					
					complex.setupViewAdapter(
					recyclerView,
					onItemSelected
					);
					
				} else if (checkedId == R.id.tabCustomView) {
					
					complex.setupCustomViewRecycler(
					recyclerView,
					new Complex.CustomViewAdapter.OnItemClickListener() {
						@Override
						public void onItemClick(String viewName) {
							
							ViewItem customItem =
							new ViewItem(
							viewName,
							viewName.toLowerCase() + ".xml",
							viewName,
							viewName + ".java"
							);
							
							onItemSelected.onItemClick(customItem);
						}
					}
					);
				}
			}
		}
		);
		
		complex.setupViewAdapter(
		recyclerView,
		onItemSelected
		);
		
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tabView.isChecked()) {
					activity.yq(false, null);
				} else {
					new CreateCustomViewDialog(activity, complex)
					.show();
				}
			}
		});
		
		dialog.show();
	}
}
