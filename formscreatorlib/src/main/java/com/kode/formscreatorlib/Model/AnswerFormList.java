package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class AnswerFormList {
    private String pageCode;
    private ArrayList<Forms> formsList = new ArrayList<>();


    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public ArrayList<Forms> getFormsList() {
        return formsList;
    }

    public void setFormsList(ArrayList<Forms> formsList) {
        this.formsList = formsList;
    }
}
