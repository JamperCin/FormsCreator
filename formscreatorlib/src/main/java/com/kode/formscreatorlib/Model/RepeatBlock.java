package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class RepeatBlock {
    private String repeatField;
    private ArrayList<String> repeatValueFrom = new ArrayList<>();
    private String repeatHeader;

    public String getRepeatField() {
        return repeatField;
    }

    public ArrayList<String> getRepeatValueFrom() {
        return repeatValueFrom;
    }

    public String getRepeatHeader() {
        return repeatHeader;
    }
}
