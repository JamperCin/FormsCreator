package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class FieldsForms {

    private String label;
    private String code;
    private String type;
    private String required;
    private String inputType;
    private boolean isGotoSource;
    private ArrayList<OptionsForms> options = new ArrayList<>();
    private ArrayList<GotoIfForms> gotoIf = new ArrayList<>();


    public String getInputType() {
        return inputType;
    }

    public boolean isGotoSource() {
        return isGotoSource;
    }

    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getRequired() {
        return required;
    }

    public ArrayList<OptionsForms> getOptions() {
        return options;
    }

    public ArrayList<GotoIfForms> getGotoIf() {
        return gotoIf;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setOptions(ArrayList<OptionsForms> options) {
        this.options = options;
    }

    public void setGotoIf(ArrayList<GotoIfForms> gotoIf) {
        this.gotoIf = gotoIf;
    }
}
