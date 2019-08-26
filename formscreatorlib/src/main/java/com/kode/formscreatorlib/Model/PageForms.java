package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class PageForms {
    private boolean isRepeat;
    private String label;
    private String fieldCode;
    private ArrayList<FieldsForms> fields = new ArrayList<>();




    public boolean isRepeat() {
        return isRepeat;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<FieldsForms> getFields() {
        return fields;
    }


    public String getFieldCode() {
        return fieldCode;
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
