package com.apk.builder.logger;

import android.text.style.ForegroundColorSpan;
import android.text.Spannable;
import android.text.SpannableString;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.app.Activity;
import com.google.gson.Gson;

public class Logger {
	
	private LogAdapter adapter;
	private LinearLayoutManager layoutManager;
	private List<Log> data = new ArrayList<>();
	private List<Log> error_log = new ArrayList<>();
    
	private RecyclerView mRecyclerView;
	private boolean mAttached;
	private String tag_tag;
	private String message_message;
	
	public void attach(RecyclerView view) {
		mRecyclerView = view;
		init();
	}
	
	private void init() {
		adapter = new LogAdapter(data);
	    layoutManager = new LinearLayoutManager(mRecyclerView.getContext());
		layoutManager.setStackFromEnd(true);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setAdapter(adapter);
	}
	
	public void d(String tag, String message) {
		mRecyclerView.post(() -> {
		    message_message = message;
		    tag_tag = tag;
		    data.add(new Log(tag, message));
	    	adapter.notifyItemInserted(data.size());
			scroll();
		});
	}
	
	public void e(String tag,  String message) {
		mRecyclerView.post(() -> {
		    message_message = message;
		    tag_tag = tag;
		    Spannable messageSpan = new SpannableString(message);
		    messageSpan.setSpan(new ForegroundColorSpan(0xffff0000), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			data.add(new Log(tag, messageSpan));
			adapter.notifyItemInserted(data.size());
			scroll();
		});
	}
	
	public void w(String tag,  String message) {
		mRecyclerView.post(() -> {
		    message_message = message;
		    tag_tag = tag;
		    Spannable messageSpan = new SpannableString(message);
		    messageSpan.setSpan(new ForegroundColorSpan(0xffff7043), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			data.add(new Log(tag, messageSpan));
			error_log.add(new Log(tag, messageSpan));
			adapter.notifyItemInserted(data.size());
			scroll();
		});
	}
	
	private void scroll() {
		mRecyclerView.smoothScrollToPosition(data.size() - 1);
	}
	
	public String getCurrentTag() {
		return tag_tag;
	}

	public String getCurrentMessage() {
		return message_message;
	}

	public String getLogData() {
		return (new Gson().toJson(data));
	}

	public String getErrorLogData() {
		return (new Gson().toJson(error_log));
	}

	public void clearLog() {
		if (!new Gson().toJson(data).equals("")) {
			data.clear();
		}
	}

	public void clearErrorLog() {
		if (!new Gson().toJson(error_log).equals("")) {
			error_log.clear();
		}
	}
}