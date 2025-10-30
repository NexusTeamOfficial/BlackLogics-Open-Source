package com.elfilibustero.quizboard.beans;

import com.elfilibustero.quizboard.util.QuizMap;

public class QuizBean extends QuizMap {
    
    // Constants for the different types of quiz questions
    public static final int QUIZ_TYPE_INFO = 0;
    public static final int QUIZ_TYPE_OX = 1;
    public static final int QUIZ_TYPE_ANSWER = 2;
    
    // Instance variables for the quiz question
    public int answer;
    public String optionA;
    public String optionB;
    public String prompt;
    public int type;
    
    // Constructor for a multiple choice quiz question
    public QuizBean(String prompt, int answer, String optionA, String optionB) {
        this.type = QUIZ_TYPE_ANSWER;
        this.prompt = prompt;
        this.answer = answer;
        this.optionA = optionA;
        this.optionB = optionB;
    }
    
    // Constructor for a true/false quiz question
    public QuizBean(String prompt, int answer) {
        this.type = QUIZ_TYPE_OX;
        this.prompt = prompt;
        this.answer = answer;
    }
}