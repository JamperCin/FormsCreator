package com.kode.formscreatorlib.Model;

import android.view.View;

public class ViewError {
    private View view;
    private String errorMessage;


    public ViewError(View view, String errorMessage) {
        this.view = view;
        this.errorMessage = errorMessage;
    }

    public View getView() {
        return view;
    }



    public String getErrorMessage() {
        return errorMessage;
    }

}
