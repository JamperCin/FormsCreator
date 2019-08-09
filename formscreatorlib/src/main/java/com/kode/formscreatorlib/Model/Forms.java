package com.kode.formscreatorlib.Model;

public class Forms {
    private String answer;
    private String question;
    private String questionCode;
    private String pageCode;

    public Forms() {
    }

    public Forms(String answer, String question, String questionCode, String pageCode) {
        this.answer = answer;
        this.question = question;
        this.questionCode = questionCode;
        this.pageCode = pageCode;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public String getPageCode() {
        return pageCode;
    }
}
