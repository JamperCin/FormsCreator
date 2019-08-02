package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.PageForms;

import java.util.List;


public class ControlsCreator {
    private Activity mContext;
    private PageForms pageForms;
    private ScrollView svFormPage;
    private FragmentManager fragmentManager;

    public ControlsCreator(Activity mContext, FragmentManager fragmentManager, ScrollView scrollView) {
        this.mContext = mContext;
        this.svFormPage = scrollView;
        this.fragmentManager = fragmentManager;
    }


    public ScrollView generate(PageForms pageBean) {
        this.pageForms = pageBean;
        float scale = mContext.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (1 * scale + 0.5f);
        svFormPage.setFillViewport(true);
        svFormPage.setPadding(dpAsPixels, 0, dpAsPixels, 0);

        /** Place Linearlayout in scrollview **/
        LinearLayout llFormPage = new LinearLayout(mContext);
        llFormPage.setOrientation(LinearLayout.VERTICAL);
        llFormPage.setBackgroundColor(Color.WHITE);
        dpAsPixels = (int) (8 * scale + 0.5f);
        llFormPage.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        /** set controls on linearlayout **/
        setControls(pageBean.getFields(), llFormPage);


        svFormPage.addView(llFormPage);
        return svFormPage;
    }


    private LinearLayout setControls(List<FieldsForms> fieldsBean, LinearLayout llFormPage) {
        String type;
        ViewsCreator viewsCreator = new ViewsCreator(mContext);

        for (FieldsForms field : fieldsBean) {
            type = field.getType();

            switch (type) {
                case "space":
                    View space = viewsCreator.space();
                    llFormPage.addView(space);
                    break;
                case "text":
                    EditText editText = viewsCreator.editText(field.getLabel(), field.getInputType(), field.getCode());
                    llFormPage.addView(editText);
                    break;
                case "textView":
                    TextView textView = viewsCreator.textView(field.getLabel(),false);
                    llFormPage.addView(textView);
                    break;
                case "textViewBold":
                    TextView textViewBold = viewsCreator.textView(field.getLabel(),true);
                    llFormPage.addView(textViewBold);
                    break;
                case "textarea":
                    EditText textarea = viewsCreator.textArea(field.getLabel(), field.getCode());
                    llFormPage.addView(textarea);
                    break;

                case "radio":
                    LinearLayout radioGroup = viewsCreator.radioGroup(field.getLabel(),field.getCode(), field.getOptions());
                    llFormPage.addView(radioGroup);
                    break;

                case "button":
                    Button button = viewsCreator.button(field.getCode(), field.getLabel());
                    llFormPage.addView(button);
                    break;

                case "date":
                    if (fragmentManager != null) {
                        LinearLayout datePicker = viewsCreator.datePicker(field.getCode(), field.getLabel(), fragmentManager);
                        llFormPage.addView(datePicker);
                    }

                    break;
            }
        }
        return llFormPage;
    }


}
