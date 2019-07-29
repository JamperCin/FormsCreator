package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class PageForms {
    private String repeat;
    private boolean isRepeat;
    private String label;
    private ArrayList<FieldsForms> fields = new ArrayList<>();


    public String getRepeat() {
        return repeat;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<FieldsForms> getFields() {
        return fields;
    }


    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFields(ArrayList<FieldsForms> fields) {
        this.fields = fields;
    }
}
