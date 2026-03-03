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
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.elfilibustero.quizboard.beans.QuizBean;
import com.elfilibustero.quizboard.tool.QuizCountdownTicker;
import com.elfilibustero.quizboard.tool.QuizStarter;
import com.elfilibustero.quizboard.util.Utilities;




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


    public static void animateQuizBoard(QuizBoard quizBoard) {
        quizBoard.clearAnswerAnimations();
    }

    private void setData(QuizBean quizBean) {

        mQuizBean = quizBean;


        questionTextView.setText(quizBean.prompt);


        switch (quizBean.type) {

        case QuizBean.QUIZ_TYPE_OX:
            this.answerOXLayout.setVisibility(View.VISIBLE);
            answerOImageView.setVisibility(View.VISIBLE);
            answerXImageView.setVisibility(View.VISIBLE);


            Utilities.setImageColorMatrix(answerOImageView, 1);
            Utilities.setImageColorMatrix(answerXImageView, 1);


            answerOImageView.setOnClickListener(this);
            answerXImageView.setOnClickListener(this);


            this.answerABLayout.setVisibility(View.GONE);
            break;

        case QuizBean.QUIZ_TYPE_ANSWER:

            this.answerABLayout.setVisibility(View.VISIBLE);


            answerAView.setOnClickListener(this);
            answerBView.setOnClickListener(this);


            answerATextView.setText(quizBean.optionA);
            answerBTextView.setText(quizBean.optionB);


            this.answerOXLayout.setVisibility(View.GONE);
            break;
        }
    }

    private void setTimeoutProgress(int elapsedTime) {

        int progressIndex = elapsedTime / TIMER_INTERVAL_MILLIS;


        for (int i = progressIndex; i < timeOutBar.getChildCount(); i++) {

            View progressView = timeOutBar.getChildAt(i);

            progressView.setBackgroundColor(Color.LTGRAY);
        }
    }


    public void cancel() {

        QuizCountdownTimer quizCountdownTimer = this.quizTimer;
        if (quizCountdownTimer != null) {

            quizCountdownTimer.cancel();

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


    public void startQuiz() {

        ArrayList<QuizBean> quizList = this.quizList;
        if (quizList == null || quizList.isEmpty()) {

            quizList = QuizList.getQuizList();
            Collections.shuffle(quizList);

            this.quizList = quizList;
        }

        QuizBean quizBean = quizList.remove(0);
        this.setData(quizBean);

        this.startCountdown();
    }


    private void clearAnswerClickListeners() {

        this.answerOImageView.setOnClickListener(null);
        this.answerXImageView.setOnClickListener(null);
        this.answerAView.setOnClickListener(null);
        this.answerBView.setOnClickListener(null);
    }


    private void clearAnswerAnimations() {
        float translateX = Utilities.applyDimens(getContext(), -50.0f);
        float scaleY = 0.9f;
        float scaleX = 0.9f;


        answerOImageView.setTranslationX(translateX);
        answerOImageView.setAlpha(DEFAULT_ONE_FLOAT_VALUE);
        answerOImageView.setScaleX(scaleX);
        answerOImageView.setScaleY(scaleY);


        answerXImageView.setTranslationX(Utilities.applyDimens(getContext(), 50.0f));
        answerXImageView.setAlpha(DEFAULT_ONE_FLOAT_VALUE);
        answerXImageView.setScaleX(scaleX);
        answerXImageView.setScaleY(scaleY);


        setDefaultImageViewProperties(answerAImageView);
        setDefaultImageViewProperties(answerBImageView);
    }

    private void setDefaultImageViewProperties(ImageView imageView) {
        imageView.setScaleX(DEFAULT_FLOAT_VALUE);
        imageView.setScaleY(DEFAULT_FLOAT_VALUE);
        imageView.setAlpha(DEFAULT_FLOAT_VALUE);
    }


    public void startCountdown() {

        QuizCountdownTimer quizCountdownTimer = this.quizTimer;
        if (quizCountdownTimer != null) {
            quizCountdownTimer.cancel();
        }


        this.quizTimer = new QuizCountdownTimer(this, TIMER_DURATION_MILLIS, TIMER_INTERVAL_MILLIS);
        this.quizTimer.start();
    }


    private void initializeAnswer() {
        QuizBean quizBean = this.mQuizBean;
        int type = quizBean.type;
        int answer = quizBean.answer;

        switch (type) {
        case QuizBean.QUIZ_TYPE_OX: // type 1 answer
            if (answer == 1) {
                Utilities.setImageColorMatrix(this.answerOImageView, 0);
                Utilities.setImageColorMatrix(this.answerXImageView, 1);

                this.answerXImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_ONE_FLOAT_VALUE).start();
                this.answerOImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_FLOAT_VALUE).start();
            } else {
                Utilities.setImageColorMatrix(this.answerOImageView, 1);
                Utilities.setImageColorMatrix(this.answerXImageView, 0);

                this.answerOImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_ONE_FLOAT_VALUE).start();
                this.answerXImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .translationX(DEFAULT_FLOAT_VALUE).alpha(DEFAULT_FLOAT_VALUE).start();
            }
            break;
        case QuizBean.QUIZ_TYPE_ANSWER: // type 2 answer
            if (answer == 1) {

                this.answerBImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .alpha(DEFAULT_ONE_FLOAT_VALUE).start();
            } else {

                this.answerAImageView.animate().scaleX(DEFAULT_ONE_FLOAT_VALUE).scaleY(DEFAULT_ONE_FLOAT_VALUE)
                        .alpha(DEFAULT_ONE_FLOAT_VALUE).start();
            }
            break;
        default:
            break;
        }

        this.clearAnswerClickListeners();


        postDelayed(new QuizStarter(this), TIMER_START_DELAY_MILLIS);

    }


    private void initAnimation() {
        this.clearAnswerAnimations();
        this.startQuiz();
    }

    @Override
    public void onClick(View view) {

        if (!Utilities.isElapsedRealtime()) {

            this.cancel();


            switch (view.getId()) {

            case R.id.img_answer_o:
            case R.id.img_answer_x:
            case R.id.view_answer_a:
            case R.id.view_answer_b:
                this.initializeAnswer();
                break;


            default:
                break;
            }


            if (this.mQuizBean.type == QuizBean.QUIZ_TYPE_ANSWER) {
                return;
            }


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
