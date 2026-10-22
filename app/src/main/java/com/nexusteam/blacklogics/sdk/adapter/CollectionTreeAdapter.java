package com.nexusteam.blacklogics.sdk.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.sdk.managers.CollectionFolderRepository;
import com.nexusteam.blacklogics.sdk.managers.WidgetRepository;
import com.nexusteam.blacklogics.sdk.models.CollectionFolder;
import com.nexusteam.blacklogics.sdk.models.WidgetBlueprint;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionTreeAdapter
        extends RecyclerView.Adapter<CollectionTreeAdapter.Holder> {

    public static final int TYPE_FOLDER = 0;
    public static final int TYPE_WIDGET = 1;

    public interface Listener {
        void onFolderClick(CollectionFolder folder, int depth);
        void onFolderLongClick(CollectionFolder folder, View anchor);
        void onFolderToggle(CollectionFolder folder, boolean expanded);
        void onWidgetClick(WidgetBlueprint blueprint, int depth);
        void onWidgetLongClick(WidgetBlueprint blueprint, View anchor);
        void onWidgetFavorite(WidgetBlueprint blueprint, boolean favorite);
    }

    private static final class Row {
        final int type;
        final CollectionFolder folder;
        final WidgetBlueprint blueprint;
        final int depth;

        Row(CollectionFolder folder, int depth) {
            this.type = TYPE_FOLDER;
            this.folder = folder;
            this.blueprint = null;
            this.depth = depth;
        }

        Row(WidgetBlueprint blueprint, int depth) {
            this.type = TYPE_WIDGET;
            this.folder = null;
            this.blueprint = blueprint;
            this.depth = depth;
        }
    }

    private final Context context;
    private final WidgetRepository widgetRepo;
    private final List<Row> rows = new ArrayList<Row>();
    private final Set<String> expandedIds = new HashSet<String>();
    private List<CollectionFolder> roots = new ArrayList<CollectionFolder>();
    private List<WidgetBlueprint> uncategorized = new ArrayList<WidgetBlueprint>();
    private Listener listener;
    private boolean showUncategorized = true;

    public CollectionTreeAdapter(Context context) {
        this.context = context;
        this.widgetRepo = WidgetRepository.getInstance(context);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setShowUncategorized(boolean show) {
        this.showUncategorized = show;
    }

    public void setData(List<CollectionFolder> roots,
            List<WidgetBlueprint> uncategorized) {
        this.roots = roots != null
                ? new ArrayList<CollectionFolder>(roots)
                : new ArrayList<CollectionFolder>();
        this.uncategorized = uncategorized != null
                ? new ArrayList<WidgetBlueprint>(uncategorized)
                : new ArrayList<WidgetBlueprint>();
        rebuild();
        notifyDataSetChanged();
    }

    public void loadFromRepos(Context ctx) {
        CollectionFolderRepository folderRepo =
                CollectionFolderRepository.getInstance(ctx);
        WidgetRepository wr = WidgetRepository.getInstance(ctx);
        setData(folderRepo.getRoots(), folderRepo.getUncategorized(wr));
    }

    public void expandAll() {
        for (CollectionFolder f : CollectionFolder.flattenForest(roots)) {
            if (f != null) expandedIds.add(f.getId());
        }
        rebuild();
        notifyDataSetChanged();
    }

    public void collapseAll() {
        expandedIds.clear();
        rebuild();
        notifyDataSetChanged();
    }

    public void toggleExpand(String folderId) {
        if (folderId == null) return;
        if (expandedIds.contains(folderId)) expandedIds.remove(folderId);
        else expandedIds.add(folderId);
        rebuild();
        notifyDataSetChanged();
    }

    private void rebuild() {
        rows.clear();
        appendFolders(roots, 0);
        if (showUncategorized && uncategorized != null) {
            for (WidgetBlueprint bp : uncategorized) {
                if (bp != null) rows.add(new Row(bp, 0));
            }
        }
    }

    private void appendFolders(List<CollectionFolder> list, int depth) {
        if (list == null) return;
        for (CollectionFolder folder : list) {
            if (folder == null) continue;
            rows.add(new Row(folder, depth));
            if (expandedIds.contains(folder.getId())) {
                for (String bpId : folder.getBlueprintIds()) {
                    WidgetBlueprint bp = findBlueprint(bpId);
                    if (bp != null) rows.add(new Row(bp, depth + 1));
                }
                appendFolders(folder.getChildren(), depth + 1);
            }
        }
    }

    private WidgetBlueprint findBlueprint(String id) {
        if (id == null) return null;
        for (WidgetBlueprint bp : widgetRepo.getAllWidgets()) {
            if (bp != null && id.equals(bp.getBlueprintId())) return bp;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return rows.get(position).type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setGravity(Gravity.CENTER_VERTICAL);
        int pad = dp(10);
        root.setPadding(pad, pad, pad, pad);
        root.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageView ivIcon = new ImageView(context);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(32), dp(32));
        iconLp.rightMargin = dp(10);
        ivIcon.setLayoutParams(iconLp);
        ivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        root.addView(ivIcon);

        LinearLayout texts = new LinearLayout(context);
        texts.setOrientation(LinearLayout.VERTICAL);
        texts.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView tvTitle = new TextView(context);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tvTitle.setTextColor(Color.parseColor("#1A1A1A"));
        tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        texts.addView(tvTitle);

        TextView tvSub = new TextView(context);
        tvSub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tvSub.setTextColor(Color.parseColor("#757575"));
        texts.addView(tvSub);
        root.addView(texts);

        ImageView ivFav = new ImageView(context);
        LinearLayout.LayoutParams favLp = new LinearLayout.LayoutParams(dp(24), dp(24));
        favLp.leftMargin = dp(6);
        ivFav.setLayoutParams(favLp);
        root.addView(ivFav);

        Holder h = new Holder(root);
        h.ivIcon = ivIcon;
        h.tvTitle = tvTitle;
        h.tvSub = tvSub;
        h.ivFavorite = ivFav;
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder h, int position) {
        final Row row = rows.get(position);
        int indent = dp(14) * row.depth;
        h.itemView.setPadding(dp(10) + indent, dp(10), dp(10), dp(10));

        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(dp(10));
        bg.setStroke(1, Color.parseColor("#E8E8E8"));

        if (row.type == TYPE_FOLDER) {
            bindFolder(h, row.folder, bg);
        } else {
            bindWidget(h, row.blueprint, bg);
        }
    }

    private void bindFolder(final Holder h, final CollectionFolder folder,
            GradientDrawable bg) {
        bg.setColor(Color.parseColor("#F5F7FA"));
        h.itemView.setBackground(bg);
        h.itemView.setElevation(1f);

        h.tvTitle.setText(folder.getDisplayName());
        h.tvSub.setText(folder.getSubtitle());

        boolean expanded = expandedIds.contains(folder.getId());
        h.ivIcon.setImageResource(android.R.drawable.ic_menu_more);
        h.ivIcon.setRotation(expanded ? 90f : 0f);
        h.ivIcon.setColorFilter(Color.parseColor("#546E7A"));
        h.ivIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        h.ivFavorite.setVisibility(View.GONE);

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean now = !expandedIds.contains(folder.getId());
                toggleExpand(folder.getId());
                if (listener != null) {
                    listener.onFolderToggle(folder, now);
                    listener.onFolderClick(folder, 0);
                }
            }
        });
        h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onFolderLongClick(folder, v);
                    return true;
                }
                return false;
            }
        });
    }

    private void bindWidget(final Holder h, final WidgetBlueprint bp,
            GradientDrawable bg) {
        bg.setColor(Color.WHITE);
        h.itemView.setBackground(bg);
        h.itemView.setElevation(2f);

        String name = bp.getDisplayName();
        if (name == null || name.isEmpty()) name = bp.getWidgetType();
        h.tvTitle.setText(name);

        String desc = bp.getDescription();
        String type = bp.getWidgetType();
        if (desc != null && !desc.isEmpty()) {
            h.tvSub.setText(type + " · " + desc);
        } else {
            h.tvSub.setText(type != null ? type : "");
        }

        h.ivIcon.setRotation(0f);
        h.ivIcon.setColorFilter(null);
        h.ivIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String thumb = bp.getThumbnailPath();
        if (thumb != null && new File(thumb).exists()) {
            try {
                h.ivIcon.setImageBitmap(BitmapFactory.decodeFile(thumb));
            } catch (Exception e) {
                h.ivIcon.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            h.ivIcon.setImageResource(android.R.drawable.ic_menu_gallery);
            h.ivIcon.setColorFilter(Color.parseColor("#1976D2"));
            h.ivIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        h.ivFavorite.setVisibility(View.VISIBLE);
        h.ivFavorite.setImageResource(bp.isFavorite()
                ? android.R.drawable.btn_star_big_on
                : android.R.drawable.btn_star_big_off);
        h.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean next = !bp.isFavorite();
                widgetRepo.toggleFavorite(bp.getBlueprintId());
                h.ivFavorite.setImageResource(next
                        ? android.R.drawable.btn_star_big_on
                        : android.R.drawable.btn_star_big_off);
                if (listener != null) listener.onWidgetFavorite(bp, next);
            }
        });

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onWidgetClick(bp, 0);
            }
        });
        h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onWidgetLongClick(bp, v);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public Object getItem(int position) {
        if (position < 0 || position >= rows.size()) return null;
        Row r = rows.get(position);
        return r.type == TYPE_FOLDER ? r.folder : r.blueprint;
    }

    private int dp(int v) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, v,
                context.getResources().getDisplayMetrics()));
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvSub;
        ImageView ivFavorite;

        Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
