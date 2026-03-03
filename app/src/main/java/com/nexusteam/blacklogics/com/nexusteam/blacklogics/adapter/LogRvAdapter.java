package com.nexusteam.blacklogics.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.model.Trace;
import com.nexusteam.blacklogics.model.TraceLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class LogRvAdapter extends RecyclerView.Adapter<LogRvAdapter.ViewHolder> implements
        View.OnClickListener {

    private final OnSelectedTraceRangeChangeListener rangeChangeListener;
    private boolean showTag;
    private boolean showTime;
    private int width;

    private List<Trace> traces;
    private int defLogColor;
    private int errorLogColor;

    private int selectedColor;

    private TreeSet<Integer> selectedItems = new TreeSet<>();

    public LogRvAdapter(boolean showTag, boolean showTime,
                        OnSelectedTraceRangeChangeListener rangeChangeListener) {
        traces = new ArrayList<>();
        this.showTag = showTag;
        this.showTime = showTime;
        this.rangeChangeListener = rangeChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(selectedItems.contains(position) ? selectedColor : Color.TRANSPARENT);

        Trace trace = traces.get(position);
        holder.txtTraceLevel.setText(trace.level.getValue());

        int color = trace.level == TraceLevel.ASSERT || trace.level == TraceLevel.ERROR ? errorLogColor : defLogColor;
        holder.txtTraceLevel.setTextColor(color);
        holder.txtMessage.setTextColor(color);

        String log = "";
        if (showTime) log = trace.time + " ";
        if (showTag) {
            log += trace.tag + ": ";
        }
        log += trace.message;

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.width = width;
        holder.txtMessage.requestLayout();
        holder.txtMessage.setText(log);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        defLogColor = ContextCompat.getColor(recyclerView.getContext(), R.color.dark);
        errorLogColor = ContextCompat.getColor(recyclerView.getContext(), R.color.log_error_color);
        selectedColor = ContextCompat.getColor(recyclerView.getContext(), R.color.selected_log_color);

        recyclerView.setItemAnimator(null);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return traces.size();
    }

    public void setNewData(List<Trace> newData) {
        this.traces = newData == null ? new ArrayList<Trace>() : newData;

        notifyDataSetChanged();
    }

    @Nullable
    public Trace getItem(int position) {
        if (position >= 0 && position < traces.size()) {
            return traces.get(position);
        }
        return null;
    }

    public int getPosition(Trace trace) {
        return traces.indexOf(trace);
    }

    public void setShowTag(boolean showTag) {
        this.showTag = showTag;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void clearSelectedItems(boolean notifyCallback) {
        selectedItems.clear();
        if (notifyCallback) rangeChangeListener.onSelectedTracesRangeChanged(selectedItems);
    }

    public void selectAllBetween(boolean notifyCallback) {
        for (int i = selectedItems.first(); i < selectedItems.last(); i++) {
            selectedItems.add(i);
        }
        if (notifyCallback) rangeChangeListener.onSelectedTracesRangeChanged(selectedItems);
    }

    public String getSelectedTracesAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        int lastPosition = selectedItems.last();
        for (Integer selectedItem : selectedItems) {
            Trace trace = getItem(selectedItem);
            if (trace != null) {
                stringBuilder.append(trace.level.getValue())
                        .append(' ')
                        .append(trace.time)
                        .append(' ')
                        .append(trace.tag)
                        .append(": ")
                        .append(trace.message);
                if (lastPosition != selectedItem) stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();

        if (selectedItems.contains(position)) {
            selectedItems.remove(position);
            v.setBackgroundColor(Color.TRANSPARENT);
        } else {
            selectedItems.add(position);
            v.setBackgroundColor(selectedColor);
        }
        rangeChangeListener.onSelectedTracesRangeChanged(selectedItems);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtTraceLevel;
        final TextView txtMessage;

        ViewHolder(View itemView) {
            super(itemView);
            txtTraceLevel = itemView.findViewById(R.id.txt_log_level);
            txtMessage = itemView.findViewById(R.id.txt_log_text);
        }
    }

    public interface OnSelectedTraceRangeChangeListener {
        void onSelectedTracesRangeChanged(TreeSet<Integer> selectedRange);
    }
}
