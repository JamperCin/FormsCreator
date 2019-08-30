package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class FieldsForms {

    private String pageCode;
    private String label;
    private String code;
    private String type;
    private String required;
    private String inputType;
    private boolean isGotoSource;
    private boolean isHidden;
    private ArrayList<OptionsForms> options = new ArrayList<>();
    private ArrayList<GotoIfForms> gotoIf = new ArrayList<>();
    private ArrayList<DisabledViews> disabledCodes = new ArrayList<>();
    private ArrayList<ShowIfForms> showIf = new ArrayList<>();
    private RepeatBlock repeatBlock;


    public void setRequired(String required) {
        this.required = required;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setGotoSource(boolean gotoSource) {
        isGotoSource = gotoSource;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void setOptions(ArrayList<OptionsForms> options) {
        this.options = options;
    }

    public void setGotoIf(ArrayList<GotoIfForms> gotoIf) {
        this.gotoIf = gotoIf;
    }

    public void setDisabledCodes(ArrayList<DisabledViews> disabledCodes) {
        this.disabledCodes = disabledCodes;
    }

    public void setShowIf(ArrayList<ShowIfForms> showIf) {
        this.showIf = showIf;
    }

    public void setRepeatBlock(RepeatBlock repeatBlock) {
        this.repeatBlock = repeatBlock;
    }

    public RepeatBlock getRepeatBlock() {
        return repeatBlock;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInputType() {
        return inputType;
    }

    public boolean isGotoSource() {
        return isGotoSource;
    }


    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
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

    public ArrayList<DisabledViews> getDisabledCodes() {
        return disabledCodes;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public ArrayList<ShowIfForms> getShowIf() {
        return showIf;
    }
}
