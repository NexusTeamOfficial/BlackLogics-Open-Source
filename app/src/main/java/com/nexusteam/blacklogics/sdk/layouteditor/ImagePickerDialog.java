package com.nexusteam.blacklogics.sdk.layouteditor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Image / drawable picker: [thumbnail] [name] [radio] per row.
 * Lists BOTH project folders merged (see DrawableResolver).
 */
final class ImagePickerDialog {

	interface Callback { void onPicked(String name); }

	private static final ExecutorService POOL = Executors.newFixedThreadPool(2);
	private static final Handler MAIN = new Handler(Looper.getMainLooper());

	private ImagePickerDialog() {}

	static void show(final Context ctx, String title, String scId, String currentValue,
			final Callback cb) {
		Map<String, File> all = DrawableResolver.listAll(scId);
		final ArrayList<String> names = new ArrayList<String>(all.keySet());
		final ArrayList<File> files = new ArrayList<File>(all.values());
		final String[] selected = {DrawableResolver.nameFromRef(currentValue)};
		final String sc = scId;

		final float d = ctx.getResources().getDisplayMetrics().density;
		MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(ctx);
		b.setTitle(title != null ? title : "Image");

		if (names.isEmpty()) {
			TextView empty = new TextView(ctx);
			empty.setText("No images found in:\n.blacklogics/data/<sc_id>/files/resource/drawable\n"
					+ ".blacklogics/resources/images/<sc_id>");
			empty.setPadding((int) (24 * d), (int) (16 * d), (int) (24 * d), (int) (8 * d));
			b.setView(empty);
			b.setNeutralButton("Close", null);
			b.show();
			return;
		}

		final RecyclerView rv = new RecyclerView(ctx);
		rv.setLayoutManager(new LinearLayoutManager(ctx));
		int h = (int) (Math.min(names.size(), 6) * 68 * d);
		FrameLayout holder = new FrameLayout(ctx);
		holder.addView(rv, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h));

		final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter =
				new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
			@NonNull @Override
			public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int vt) {
				LinearLayout row = new LinearLayout(ctx);
				row.setOrientation(LinearLayout.HORIZONTAL);
				row.setGravity(Gravity.CENTER_VERTICAL);
				row.setPadding((int) (20 * d), (int) (6 * d), (int) (12 * d), (int) (6 * d));
				row.setLayoutParams(new RecyclerView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, (int) (68 * d)));
				TypedValue tv = new TypedValue();
				if (ctx.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, tv, true)) {
					row.setBackgroundResource(tv.resourceId);
				}

				ImageView iv = new ImageView(ctx);
				iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
				iv.setId(View.generateViewId());
				LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams((int) (52 * d), (int) (52 * d));
				row.addView(iv, ilp);

				TextView name = new TextView(ctx);
				name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				name.setSingleLine(true);
				name.setEllipsize(android.text.TextUtils.TruncateAt.END);
				name.setPadding((int) (16 * d), 0, (int) (8 * d), 0);
				TypedValue c = new TypedValue();
				if (ctx.getTheme().resolveAttribute(
						com.google.android.material.R.attr.colorOnSurface, c, true)) {
					name.setTextColor(c.data);
				}
				row.addView(name, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

				MaterialRadioButton rb = new MaterialRadioButton(ctx);
				rb.setClickable(false);
				rb.setFocusable(false);
				row.addView(rb, new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				return new RecyclerView.ViewHolder(row) {};
			}

			@Override
			public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, final int pos) {
				LinearLayout row = (LinearLayout) vh.itemView;
				final ImageView iv = (ImageView) row.getChildAt(0);
				TextView name = (TextView) row.getChildAt(1);
				MaterialRadioButton rb = (MaterialRadioButton) row.getChildAt(2);
				final String n = names.get(pos);
				final File f = files.get(pos);
				name.setText(n);
				rb.setChecked(n.equals(selected[0]));

				iv.setImageDrawable(null);
				iv.setTag(f.getAbsolutePath());
				POOL.execute(new Runnable() {
					@Override public void run() {
						final Drawable th = DrawableResolver.thumbnail(ctx, sc, f, 128);
						MAIN.post(new Runnable() {
							@Override public void run() {
								if (f.getAbsolutePath().equals(iv.getTag()) && th != null) {
									iv.setImageDrawable(th);
								}
							}
						});
					}
				});

				row.setOnClickListener(new View.OnClickListener() {
					@Override public void onClick(View v) {
						selected[0] = n;
						notifyDataSetChanged();
					}
				});
			}

			@Override public int getItemCount() { return names.size(); }
		};
		rv.setAdapter(adapter);

		// scroll to current selection
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equals(selected[0])) { rv.scrollToPosition(i); break; }
		}

		b.setView(holder);
		b.setNeutralButton("Cancel", null);
		b.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override public void onClick(DialogInterface di, int w) {
				if (selected[0] != null && cb != null) cb.onPicked(selected[0]);
			}
		});
		b.show();
	}
}
