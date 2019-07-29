package com.kode.formscreatorlib.Model;

import java.util.ArrayList;

public class MainForms {
    private String 	form ;
    private String  title;
    private String section;
    private ArrayList<PageForms>  pages = new ArrayList<>();


    public String getForm() {
        return form;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public ArrayList<PageForms> getPages() {
        return pages;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setPages(ArrayList<PageForms> pages) {
        this.pages = pages;
    }
}
