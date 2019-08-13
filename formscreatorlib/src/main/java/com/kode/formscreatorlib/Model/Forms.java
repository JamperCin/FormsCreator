package com.kode.formscreatorlib.Model;

import static com.kode.formscreatorlib.Utils.FormsUtils.FORM_CODE;

public class Forms {
    private String answer;
    private String question;
    private String questionCode;
    private String pageCode;
    private String formCode;

    public Forms() {
    }

    public Forms(String answer, String question, String questionCode, String pageCode) {
        this.answer = answer;
        this.question = question;
        this.questionCode = questionCode;
        this.pageCode = pageCode;
        this.formCode = FORM_CODE;
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

    public String getFormCode() {
        return formCode;
    }
}
