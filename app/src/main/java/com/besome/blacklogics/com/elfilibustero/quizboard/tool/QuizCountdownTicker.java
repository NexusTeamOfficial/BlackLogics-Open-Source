package com.elfilibustero.quizboard.tool;

import android.widget.TextView;
import com.elfilibustero.quizboard.QuizBoard.QuizCountdownTimer;

public class QuizCountdownTicker implements Runnable {
	public final long count;
	public final QuizCountdownTimer timer;
	public QuizCountdownTicker(QuizCountdownTimer timer, long count) {
		this.timer = timer;
		this.count = count;
	}
	
	public void run() {
		TextView remainingTimeTextView = this.timer.quizBoard.remainingTime;
		if (remainingTimeTextView != null) {
			remainingTimeTextView.setText(String.valueOf(1 + (this.count / 1000)));
		}
	}
	
}
