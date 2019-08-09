package com.kode.formscreatorlib.Utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kode.formscreatorlib.Model.ViewError;

import java.util.ArrayList;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;

public class ValidationClass {
    private Activity mContext;
    private Button button;

    public ValidationClass(Activity mContext) {
        this.mContext = mContext;
    }


    public ValidationClass setParentView(Button button) {
        this.button = button;
        return this;
    }


    public boolean Validator(ArrayList<ViewError> viewArrayList) {
        boolean valid = true;
        try {
            for (ViewError viewError : viewArrayList) {
                View v = viewError.getView();

                if (v instanceof EditText) {
                    EditText edt = (EditText) v;
                    if (TextUtils.isEmpty(edt.getText().toString())) {
                        edt.setError("required");
                        edt.requestFocus();
                        edt.setFocusable(true);
                        valid = false;
                        break;
                    }else  edt.setError(null);
                }


                if (v instanceof Spinner) {
                    Spinner spinner = (Spinner) v;
                    if (spinner.getSelectedItem() == null || TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
                        valid = false;
                        Toast.makeText(mContext, viewError.getErrorMessage()  + " required", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }


                if (v instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) v;
                    if (radioGroup.getCheckedRadioButtonId() == -1) {
                        valid = false;
                        Toast.makeText(mContext, viewError.getErrorMessage()  + " required", Toast.LENGTH_SHORT).show();
                    }

                    break;
                }




                if (v instanceof ImageView) {
                    ImageView linearLayout = (ImageView) v;
                    String tag = linearLayout.getTag().toString();
                    if (splitText(0, tag).equalsIgnoreCase("TAG")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    } else if (splitText(0, tag).equalsIgnoreCase("Passport")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    } else if (splitText(0, tag).equalsIgnoreCase("ID Card")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    } else if (splitText(0, tag).equalsIgnoreCase("signature")) {
                        valid = false;
                        Toast.makeText(mContext, splitText(1, tag) + " required", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            LOG("Error validating >>> " + e.getMessage());
        }

        return valid;
    }


    /**
     * this function accepts a string in the form of date eg)) 22/04/2017 and splits them into an array and uses the index to
     * retrieve the particular value. Eg index 0 retrieves value = 22, index 1 for value = 04, and index 2 for value = 2017
     **/
    private static String splitText(int index, String date) {
        try {
            if (!date.contains("#"))
                return date;

            if (!TextUtils.isEmpty(date)) {
                String[] dateArray = date.split("#");
                return dateArray[index];
            }
        } catch (Exception e) {
            return date;
        }
        return "";
    }


    private static String splitName(int index, String date) {
        try {
            if (!date.contains(" "))
                return date;

            if (!TextUtils.isEmpty(date)) {
                String[] dateArray = date.split(" ");
                return dateArray[index];
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }



}
