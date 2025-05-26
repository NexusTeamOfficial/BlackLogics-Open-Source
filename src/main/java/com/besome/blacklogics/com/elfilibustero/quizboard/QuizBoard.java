package com.elfilibustero.quizboard;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.blacklogics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.elfilibustero.quizboard.beans.QuizBean;
import com.elfilibustero.quizboard.tool.QuizCountdownTicker;
import com.elfilibustero.quizboard.tool.QuizStarter;
import com.elfilibustero.quizboard.util.Utilities;

//import elfilibustero.quiz.board.*;


/**
 * QuizBoard.java
 * 
 * Developed by NexusTeam & SmartIndiaGaming
 * 
 * This class represents the main view for the Quiz Board game. It handles
 * the rendering of questions, options, navigation between questions, 
 * answer validation, and score tracking.
 * 
 * The class dynamically creates and manages TextViews and Buttons for 
 * displaying quiz content, while keeping track of user selections and 
 * correct answers. It's designed to be easily extendable for different
 * quiz formats and difficulty levels.
 * 
 * Usage:
 * - Call `loadBoard(List<String>, List<String>, List<String>)` with properly formatted
 *   question and answer data to initialize the board.
 * - User interactions are handled internally, and the final score is displayed at the end.
 */

public class QuizBoard extends LinearLayout implements View.OnClickListener {

	private int TIMER_DURATION_MILLIS = 15000;
	private int TIMER_INTERVAL_MILLIS = 250;
	private int TIMER_START_DELAY_MILLIS = 2000;

	final float DEFAULT_FLOAT_VALUE = 0.0f;
	final float DEFAULT_ONE_FLOAT_VALUE = 1.0f;

	public long timeRemaining;
	public TextView remainingTime;
	public TextView questionTextView;

	public RelativeLayout answerOXLayout;
	public ImageView answerOImageView;
	public ImageView answerXImageView;

	public LinearLayout answerABLayout;
	public View answerAView;
	public View answerBView;
	public TextView answerATextView;
	public TextView answerBTextView;
	public ImageView answerAImageView;
	public ImageView answerBImageView;

	public LinearLayout timeOutBar;

	public ArrayList<QuizBean> quizList;
	public QuizBean mQuizBean;

	public QuizCountdownTimer quizTimer;

	public QuizBoard(Context context) {
		super(context);
		this.setupViews(context);
	}

	public void setTimer(int a) {
		this.TIMER_DURATION_MILLIS = a;
	}

	public void setTimerSec(int a) {
		this.TIMER_INTERVAL_MILLIS = a;
	}

	public void setTimerStart(int a) {
		this.TIMER_START_DELAY_MILLIS = a;
	}

	//public static void a(QuizBoard)
	public static void animateQuizBoard(QuizBoard quizBoard) {
		quizBoard.clearAnswerAnimations();
	}

	private void setData(QuizBean quizBean) {
		// Assign the quizBean to mQuizBean
		mQuizBean = quizBean;

		// Set the question text
		questionTextView.setText(quizBean.prompt);

		// Show/hide the answer options based on the quiz type
		switch (quizBean.type) {
		// If quiz type is 1 (OX type)
		case QuizBean.QUIZ_TYPE_OX:
			this.answerOXLayout.setVisibility(View.VISIBLE);
			answerOImageView.setVisibility(View.VISIBLE);
			answerXImageView.setVisibility(View.VISIBLE);

			// Apply color matrix to O and X images
			Utilities.setImageColorMatrix(answerOImageView, 1);
			Utilities.setImageColorMatrix(answerXImageView, 1);

			// Set click listeners for O and X images
			answerOImageView.setOnClickListener(this);
			answerXImageView.setOnClickListener(this);

			// Hide the AB type answer options
			this.answerABLayout.setVisibility(View.GONE);
			break;
		// If quiz type is 2 (AB type)
		case QuizBean.QUIZ_TYPE_ANSWER:
			// Show the AB type answer options
			this.answerABLayout.setVisibility(View.VISIBLE);

			// Set click listeners for answer views A and B
			answerAView.setOnClickListener(this);
			answerBView.setOnClickListener(this);

			// Set text for answer options A and B
			answerATextView.setText(quizBean.optionA);
			answerBTextView.setText(quizBean.optionB);

			// Hide the OX type answer options
			this.answerOXLayout.setVisibility(View.GONE);
			break;
		}
	}

	private void setTimeoutProgress(int elapsedTime) {
		// Calculate the current progress index based on elapsed time and timer interval
		int progressIndex = elapsedTime / TIMER_INTERVAL_MILLIS;

		// Iterate through the progress views and update their colors based on progress index
		for (int i = progressIndex; i < timeOutBar.getChildCount(); i++) {
			// Get the progress view at the current index
			View progressView = timeOutBar.getChildAt(i);
			// Set the background color of the progress view to light gray
			progressView.setBackgroundColor(Color.LTGRAY);
		}
	}

	//public void a()
	public void cancel() {
		// Check if quizTimer object is not null
		QuizCountdownTimer quizCountdownTimer = this.quizTimer;
		if (quizCountdownTimer != null) {
			// Cancel the quizTimer object
			quizCountdownTimer.cancel();
			// Set quizTimer to null
			this.quizTimer = null;
		}
	}

	private void setupViews(Context context) {
		LayoutInflater.from(context).inflate(R.layout.quiz_board, this, true);
		this.remainingTime = (TextView) this.findViewById(R.id.tv_remaining_time);
		this.questionTextView = (TextView) this.findViewById(R.id.tv_question);
		this.timeOutBar = (LinearLayout) this.findViewById(R.id.timeout_bar);
		this.answerOXLayout = (RelativeLayout) this.findViewById(R.id.layout_answer_ox);
		this.answerOImageView = (ImageView) this.findViewById(R.id.img_answer_o);
		this.answerXImageView = (ImageView) this.findViewById(R.id.img_answer_x);
		this.answerABLayout = (LinearLayout) this.findViewById(R.id.layout_answer_ab);
		this.answerAView = this.findViewById(R.id.view_answer_a);
		this.answerBView = this.findViewById(R.id.view_answer_b);
		this.answerATextView = (TextView) this.findViewById(R.id.tv_answer_a);
		this.answerBTextView = (TextView) this.findViewById(R.id.tv_answer_b);
		this.answerAImageView = (ImageView) this.findViewById(R.id.img_answer_a);
		this.answerBImageView = (ImageView) this.findViewById(R.id.img_answer_b);
		this.initAnimation();
	}

	//public void b()
	public void startQuiz() {
		// Check if quizList is null or empty
		ArrayList<QuizBean> quizList = this.quizList;
		if (quizList == null || quizList.isEmpty()) {
			// If quizList is null or empty, get the quizList from QuizList class and shuffle it
			quizList = QuizList.getQuizList();
			Collections.shuffle(quizList);
			// Set the shuffled quizList to quizList variable
			this.quizList = quizList;
		}
		// Remove the first quizBean from quizList and set it to mQuizBean
		QuizBean quizBean = quizList.remove(0);
		this.setData(quizBean);
		// Start the QuizCountdownTimer
		this.startCountdown();
	}

	//public final void c()
	private void clearAnswerClickListeners() {
		// Set click listeners to null for OX type answer views and AB type answer views
		this.answerOImageView.setOnClickListener(null);
		this.answerXImageView.setOnClickListener(null);
		this.answerAView.setOnClickListener(null);
		this.answerBView.setOnClickListener(null);
	}

	// public final void d()
	private void clearAnswerAnimations() {
		float translateX = Utilities.applyDimens(getContext(), -50.0f);
		float scaleY = 0.9f;
		float scaleX = 0.9f;

		// Initialize answer O image view properties
		answerOImageView.setTranslationX(translateX);
		answerOImageView.setAlpha(DEFAULT_ONE_FLOAT_VALUE);
		answerOImageView.setScaleX(scaleX);
		answerOImageView.setScaleY(scaleY);

		// Initialize answer X image view properties
		answerXImageView.setTranslationX(Utilities.applyDimens(getContext(), 50.0f));
		answerXImageView.setAlpha(DEFAULT_ONE_FLOAT_VALUE);
		answerXImageView.setScaleX(scaleX);
		answerXImageView.setScaleY(scaleY);

		// Initialize answer A and B image view properties
		setDefaultImageViewProperties(answerAImageView);
		setDefaultImageViewProperties(answerBImageView);
	}

	private void setDefaultImageViewProperties(ImageView imageView) {
		imageView.setScaleX(DEFAULT_FLOAT_VALUE);
		imageView.setScaleY(DEFAULT_FLOAT_VALUE);
		imageView.setAlpha(DEFAULT_FLOAT_VALUE);
	}

	//public final void e()
	public void startCountdown() {
		// Cancel any existing quiz timer
		QuizCountdownTimer quizCountdownTimer = this.quizTimer;
		if (quizCountdownTimer != null) {
			quizCountdownTimer.cancel();
		}

		// Create a new quiz timer and start it
		this.quizTimer = new QuizCountdownTimer(this, TIMER_DURATION_MILLIS, TIMER_INTERVAL_MILLIS);
		this.quizTimer.start();
	}

	//public void f()
	private void initializeAnswer() {
		QuizBean quizBean = this.mQuizBean;
		int type = quizBean.type;
		int answer = quizBean.answer;

		switch (type) {
		case QuizBean.QUIZ_TYPE_OX: // type 1 answer
			if (answer == 1) {
				Utilities.setImageColorMatrix(this.answerOImageView, 0);
				Utilities.setImageColorMatrix(this.answerXImageView, 1);
				// animate the X image to appear and the O image to disappear
				this.answerXImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_ONE_FLOAT_VALUE).start();
				this.answerOImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_FLOAT_VALUE).start();
			} else {
				Utilities.setImageColorMatrix(this.answerOImageView, 1);
				Utilities.setImageColorMatrix(this.answerXImageView, 0);
				// animate the O image to appear and the X image to disappear
				this.answerOImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_ONE_FLOAT_VALUE).start();
				this.answerXImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_FLOAT_VALUE).start();
			}
			break;
		case QuizBean.QUIZ_TYPE_ANSWER: // type 2 answer
			if (answer == 1) {
				// animate the B image to appear
				this.answerBImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.alpha(DEFAULT_ONE_FLOAT_VALUE).start();
			} else {
				// animate the A image to appear
				this.answerAImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
						.alpha(DEFAULT_ONE_FLOAT_VALUE).start();
			}
			break;
		default:
			break;
		}
		// call the defaultClick method to set the default click listeners for the images
		this.clearAnswerClickListeners();
		// create a new Handler to post a delayed action that starts the quiz after TIMER_START_DELAY_MILLIS milliseconds
		//new Handler().
		postDelayed(new QuizStarter(this), TIMER_START_DELAY_MILLIS);

	}

	//public void g()
	private void initAnimation() {
		this.clearAnswerAnimations();
		this.startQuiz();
	}

	@Override
	public void onClick(View view) {
		// Check if enough time has elapsed since the last click
		if (!Utilities.isElapsedRealtime()) {
			// Cancel the quiz
			this.cancel();

			// Handle click based on the view ID
			switch (view.getId()) {
			// If the answer was O, X, A, or B, initialize the next question
			case R.id.img_answer_o:
			case R.id.img_answer_x:
			case R.id.view_answer_a:
			case R.id.view_answer_b:
				this.initializeAnswer();
				break;

			// If the click was not on an answer, do nothing
			default:
				break;
			}

			// If the quiz type is not AB type, return
			if (this.mQuizBean.type == QuizBean.QUIZ_TYPE_ANSWER) {
				return;
			}

			// If enough time has not elapsed, do nothing
			view.getId();
		}
	}

	/**
	
	A class representing a countdown timer for a quiz board game.
	*/
	public class QuizCountdownTimer extends CountDownTimer {

		/**
		
		The QuizBoard object associated with this countdown timer.
		*/
		public final QuizBoard quizBoard;

		/**
		* The executor to use for executing countdown ticker tasks.
		*/
		private final Executor executor;

		/**
		
		Constructor for QuizCountdownTimer class.
		@param quizBoard The QuizBoard object associated with this countdown timer.
		@param millisInFuture The number of milliseconds in the future to count down from.
		@param countDownInterval The interval to use when updating the countdown.
		*/

		public QuizCountdownTimer(QuizBoard quizBoard, long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			this.quizBoard = quizBoard;
			this.executor = Executors.newSingleThreadExecutor();
		}

		/**
		
		Called when the countdown timer finishes.
		Initializes the answer for the associated QuizBoard object.
		*/

		public void onFinish() {
			this.quizBoard.initializeAnswer();
		}

		/**
		
		Called on every tick of the countdown timer.
		Posts a new QuizCountdownTicker object to update the countdown display.
		@param millisUntilFinished The number of milliseconds until the countdown finishes.
		*/

		public void onTick(long millisUntilFinished) {
			executor.execute((Runnable) new QuizCountdownTicker(this, millisUntilFinished));
		}
	}

}
