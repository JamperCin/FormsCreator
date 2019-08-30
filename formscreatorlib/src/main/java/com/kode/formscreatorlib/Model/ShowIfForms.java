package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class ShowIfForms {
    private String answer;
    private ArrayList<String> visibleCodes = new ArrayList<>();
    private ArrayList<String> hiddenCodes = new ArrayList<>();



    public String getAnswer() {
        return answer;
    }

    public ArrayList<String> getVisibleCodes() {
        return visibleCodes;
    }

    public ArrayList<String> getHiddenCodes() {
        return hiddenCodes;
    }
}
