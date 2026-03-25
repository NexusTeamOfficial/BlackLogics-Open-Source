package com.nexusteam.blacklogics.logcat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nexusteam.blacklogics.R;
import com.nexusteam.blacklogics.adapter.LogRvAdapter;
import com.nexusteam.blacklogics.model.Logcat;
import com.nexusteam.blacklogics.model.Trace;
import com.nexusteam.blacklogics.model.TraceBuffer;
import com.nexusteam.blacklogics.model.TraceLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class LogcatActivity extends AppCompatActivity implements
        Logcat.Listener,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        PopupMenu.OnMenuItemClickListener,
        LogRvAdapter.OnSelectedTraceRangeChangeListener {

    public static final String BUFFER_SIZE_EXTRA = "BUFFER_SIZE_EXTRA";
    private final long animDuration = 200;

    private View progressView;
    private RecyclerView rvLog;
    private LinearLayoutManager linearLayoutManager;
    private LogRvAdapter logRvAdapter;

    private View btnDown;
    private TextView txtTraceLevel;
    private EditText etxtFilter;
    private View btnClearFilter;
    private CheckBox cbRegex;
    private ViewGroup shareContainer;
    private View btnShare;
    private View btnCopy;
    private View btnSelectAllBetween;
    private View btnCloseSelect;

    private final Logcat log = new Logcat();

    private final TraceBuffer traceBuffer = new TraceBuffer(2500);
    private boolean isInitialized;
    private TraceLevel traceLevel = TraceLevel.VERBOSE;

    private boolean hasSelectedTrace;
    private List<Trace> waitingTraces = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logcat);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int bufferSize = bundle.getInt(BUFFER_SIZE_EXTRA, 2500);
            if (bufferSize < 0) bufferSize = 2500;
            traceBuffer.setBufferSize(bufferSize);
        }
        progressView = findViewById(R.id.pw_log_cat);
        rvLog = findViewById(R.id.rv_log_cat);
        linearLayoutManager = (LinearLayoutManager) rvLog.getLayoutManager();
        logRvAdapter = new LogRvAdapter(true, true, this);
        rvLog.setAdapter(logRvAdapter);

        CheckBox cbTime = findViewById(R.id.cb_log_cat_time);
        cbTime.setOnCheckedChangeListener(this);
        CheckBox cbTag = findViewById(R.id.cb_log_cat_tag);
        cbTag.setOnCheckedChangeListener(this);
        CheckBox cbWrapLog = findViewById(R.id.cb_log_cat_wrap_log);
        cbWrapLog.setOnCheckedChangeListener(this);

        btnDown = findViewById(R.id.fab_log_cat);
        btnDown.setOnClickListener(this);
        showFab(false, false);
        setListWidth(false, false);

        rvLog.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && rvLog.canScrollVertically(1)) {
                    showFab(true, true);
                } else showFab(false, true);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!rvLog.canScrollVertically(1)) showFab(false, true);
            }
        });

        txtTraceLevel = findViewById(R.id.txt_log_cat_trace_level);
        txtTraceLevel.setOnClickListener(this);
        txtTraceLevel.setText(traceLevel.getName());

        etxtFilter = findViewById(R.id.etxt_log_cat_filter);
        btnClearFilter = findViewById(R.id.btn_log_cat_clear_filter_field);
        btnClearFilter.setClickable(false);
        btnClearFilter.setAlpha(0);
        cbRegex = findViewById(R.id.cb_log_cat_regex);

        etxtFilter.addTextChangedListener(new TextWatcher() {
            String prevText;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null) prevText = null;
                else prevText = charSequence.toString();

                if (TextUtils.isEmpty(prevText)) prevText = null;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = String.valueOf(editable);
                filter();
                if (TextUtils.isEmpty(searchText) && prevText != null) {
                    btnClearFilter.setClickable(false);
                    btnClearFilter.animate().alpha(0).setDuration(animDuration);
                } else if (!TextUtils.isEmpty(searchText) && prevText == null) {
                    btnClearFilter.setClickable(true);
                    btnClearFilter.animate().alpha(1).setDuration(animDuration);
                }
            }
        });
        btnClearFilter.setOnClickListener(this);
        cbRegex.setOnCheckedChangeListener(this);

        shareContainer = findViewById(R.id.log_cat_share_container);
        shareContainer.setEnabled(false);

        btnShare = findViewById(R.id.btn_log_cat_share);
        btnCopy = findViewById(R.id.btn_log_cat_copy);
        btnSelectAllBetween = findViewById(R.id.btn_log_cat_select_all_between);
        btnCloseSelect = findViewById(R.id.btn_log_cat_close_select);
        btnShare.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
        btnSelectAllBetween.setOnClickListener(this);
        btnCloseSelect.setOnClickListener(this);
    }

    private void showFab(boolean show, boolean animate) {
        if (animate && show == btnDown.isEnabled()) return;

        btnDown.setEnabled(show);
        if (animate) {
            btnDown.animate()
                    .scaleX(show ? 1 : 0)
                    .scaleY(show ? 1 : 0)
                    .setInterpolator(new AccelerateInterpolator())
                    .setDuration(animDuration);
        } else {
            btnDown.clearAnimation();
            btnDown.setScaleX(show ? 1 : 0);
            btnDown.setScaleY(show ? 1 : 0);
        }
    }

    private void showShareContainer(boolean show) {
        etxtFilter.setEnabled(!show);
        txtTraceLevel.setEnabled(!show);
        cbRegex.setEnabled(!show);

        btnShare.setEnabled(show);
        btnCopy.setEnabled(show);
        btnSelectAllBetween.setEnabled(show);
        btnCloseSelect.setEnabled(show);

        if (show != shareContainer.isEnabled()) {
            shareContainer.setEnabled(show);
            shareContainer.animate()
                    .translationY(show ? 0 : shareContainer.getHeight())
                    .setDuration(animDuration);
        }

        if (!show) {
            hideKeyboard();
        }
    }

    private void showSelectAllBetweenButton(boolean show) {
        if (show != btnSelectAllBetween.isClickable()) {
            btnSelectAllBetween.setClickable(show);
            btnSelectAllBetween.animate()
                    .alpha(show ? 1 : 0)
                    .setDuration(animDuration);
        }
    }

    private void hideKeyboard() {
        final View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInitialized) {
            isInitialized = true;
            log.setListener(this);
            log.startReading();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isInitialized) {
            isInitialized = false;
            log.stopReading();
            log.setListener(null);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab_log_cat) {
            rvLog.scrollToPosition(logRvAdapter.getItemCount() - 1);
        } else if (i == R.id.txt_log_cat_trace_level) {
            PopupMenu popup = new PopupMenu(this, txtTraceLevel);
            popup.getMenuInflater().inflate(R.menu.popup_trace_level, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        } else if (i == R.id.btn_log_cat_clear_filter_field) {
            etxtFilter.setText("");
        } else if (i == R.id.btn_log_cat_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, logRvAdapter.getSelectedTracesAsString());
            startActivity(Intent.createChooser(intent, ""));

            logRvAdapter.clearSelectedItems(true);
        } else if (i == R.id.btn_log_cat_copy) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                ClipData clip = ClipData.newPlainText("Log", logRvAdapter.getSelectedTracesAsString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(this, R.string.title_log_copied, Toast.LENGTH_SHORT).show();
            }

            logRvAdapter.clearSelectedItems(true);
        } else if (i == R.id.btn_log_cat_select_all_between) {
            logRvAdapter.selectAllBetween(true);
            notifyWithSaveUpScroll();
        } else if (i == R.id.btn_log_cat_close_select) {
            logRvAdapter.clearSelectedItems(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (i == R.id.cb_log_cat_time) {
            logRvAdapter.setShowTime(isChecked);
            notifyWithSaveUpScroll();
        } else if (i == R.id.cb_log_cat_tag) {
            logRvAdapter.setShowTag(isChecked);
            notifyWithSaveUpScroll();
        } else if (i == R.id.cb_log_cat_wrap_log) {
            setListWidth(isChecked, true);
        } else if (i == R.id.cb_log_cat_regex) {
            if (!TextUtils.isEmpty(etxtFilter.getText())) {
                filter();
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TraceLevel traceLevel = TraceLevel.getTraceLevel(String.valueOf(item.getTitle()));
        if (this.traceLevel != traceLevel) {
            this.traceLevel = traceLevel;
            txtTraceLevel.setText(traceLevel.getName());

            filter();
        }
        return false;
    }

    @Override
    public void onNewTraces(List<Trace> traces) {
        progressView.setVisibility(View.GONE);

        if (hasSelectedTrace) {
            waitingTraces.addAll(traces);
            return;
        }
        boolean canScrollDown = rvLog.canScrollVertically(1);
        boolean canScrollUp = rvLog.canScrollVertically(-1);

        int position = linearLayoutManager.findFirstVisibleItemPosition();
        Trace firstTrace = logRvAdapter.getItem(position);

        View startView = rvLog.getChildAt(0);
        int offset = (startView == null) ? 0 : (startView.getTop());

        traceBuffer.add(traces);

        filter(etxtFilter.getText().toString(), cbRegex.isChecked(), firstTrace, offset, canScrollDown, canScrollUp);
    }

    @Override
    public void onSelectedTracesRangeChanged(TreeSet<Integer> selectedRange) {
        hasSelectedTrace = !selectedRange.isEmpty();

        if (!hasSelectedTrace) {
            showSelectAllBetweenButton(false);
            if (!waitingTraces.isEmpty()) {
                onNewTraces(waitingTraces);
                waitingTraces.clear();
            } else notifyWithSaveUpScroll();

            showShareContainer(false);
        } else {
            showShareContainer(true);

            showSelectAllBetweenButton(selectedRange.last() - selectedRange.first() >= selectedRange.size());
        }
    }

    private void notifyWithSaveUpScroll() {
        boolean canScrollDown = rvLog.canScrollVertically(1);
        boolean canScrollUp = rvLog.canScrollVertically(-1);

        int position = linearLayoutManager.findFirstVisibleItemPosition();
        Trace firstTrace = logRvAdapter.getItem(position);

        View startView = rvLog.getChildAt(0);
        int offset = (startView == null) ? 0 : (startView.getTop());
        logRvAdapter.notifyDataSetChanged();

        updateScrollPosition(canScrollDown, canScrollUp, offset, firstTrace);
    }

    private void updateScrollPosition(boolean canScrollDown, boolean canScrollUp, int offset, Trace firstTrace) {
        int scrollPosition;
        if (canScrollDown) {
            scrollPosition = !canScrollUp ? 0 : logRvAdapter.getPosition(firstTrace);
        } else {
            scrollPosition = logRvAdapter.getItemCount() - 1;
            rvLog.scrollToPosition(scrollPosition);
            return;
        }

        if (scrollPosition == -1) scrollPosition = 0;

        linearLayoutManager.scrollToPositionWithOffset(scrollPosition, scrollPosition == 0 ? 0 : offset);
    }

    private void setListWidth(boolean wrapContent, boolean notify) {
        int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        if (!wrapContent) {
            final int orientation = Resources.getSystem().getConfiguration().orientation;
            displayWidth = orientation == Configuration.ORIENTATION_LANDSCAPE ? displayWidth * 3 : displayWidth * 6;
        }
        logRvAdapter.setWidth(displayWidth);
        if (notify) {
            notifyWithSaveUpScroll();
        }
    }

    private void filter() {
        boolean canScrollDown = rvLog.canScrollVertically(1);
        boolean canScrollUp = rvLog.canScrollVertically(-1);

        int position = linearLayoutManager.findFirstVisibleItemPosition();
        Trace firstTrace = logRvAdapter.getItem(position);

        View startView = rvLog.getChildAt(0);
        int offset = (startView == null) ? 0 : (startView.getTop());

        filter(etxtFilter.getText().toString(), cbRegex.isChecked(), firstTrace, offset, canScrollDown, canScrollUp);
    }

    private void filter(String searchText, boolean regex,
                        @Nullable Trace firstTrace, int offset, boolean canScrollBottom, boolean canScrollTop
    ) {
        if (searchText != null) searchText = searchText.toLowerCase();

        if (traceLevel == TraceLevel.VERBOSE && TextUtils.isEmpty(searchText)) {
            logRvAdapter.setNewData(traceBuffer.getTraces());

            updateScrollPosition(canScrollBottom, canScrollTop, offset, firstTrace);
            return;
        }

        List<Trace> traces = new ArrayList<>();
        Pattern pattern = null;
        if (regex && !TextUtils.isEmpty(searchText)) try {
            pattern = Pattern.compile(searchText);
        } catch (PatternSyntaxException patternSyntaxException) {
            regex = false;
        }

        for (Trace trace : traceBuffer.getTraces()) {
            String tag = trace.tag.toLowerCase();
            String message = trace.message.toLowerCase();
            boolean add = traceLevel == TraceLevel.VERBOSE || trace.level.ordinal() >= traceLevel.ordinal();

            if (add && !TextUtils.isEmpty(searchText)) {
                if (regex) {

                    Matcher m = pattern.matcher(tag);

                    add = m.find();
                    if (!add) {
                        m = pattern.matcher(message);
                        add = m.find();
                    }
                } else {
                    add = tag.contains(searchText) || message.contains(searchText);
                }
            }
            if (add) traces.add(trace);
        }
        logRvAdapter.setNewData(traces);

        updateScrollPosition(canScrollBottom, canScrollTop, offset, firstTrace);
    }
}
