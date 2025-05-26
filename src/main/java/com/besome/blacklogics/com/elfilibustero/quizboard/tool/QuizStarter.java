package com.elfilibustero.quizboard.tool;

import com.elfilibustero.quizboard.QuizBoard;

public class QuizStarter implements Runnable {
	public final QuizBoard quizBoard;
	public QuizStarter(QuizBoard quizBoard) {
		this.quizBoard = quizBoard;
	}
	
	public void run() {
		QuizBoard.animateQuizBoard(this.quizBoard);
		this.quizBoard.startQuiz();
	}
	
}
