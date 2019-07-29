package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.PageForms;

import java.util.List;


public class ControlsCreator {
    private Activity mContext;
    private PageForms pageForms;
    private  ScrollView svFormPage;

    public ControlsCreator(Activity mContext, ScrollView scrollView, PageForms pageForms) {
        this.mContext = mContext;
        this.pageForms = pageForms;
        this.svFormPage = scrollView;
    }


    public ScrollView generate(PageForms pageBean) {
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
                case "text":
                    EditText editText = viewsCreator.editText(field.getLabel(), field.getInputType(), field.getCode());
                    llFormPage.addView(editText);
                    break;
                case "textarea":
                    EditText textarea = viewsCreator.textArea(field.getLabel(), field.getCode());
                    llFormPage.addView(textarea);
                    break;

               /* case "date":
                    pNewEditText date = new pNewEditText(context, fieldBean, vpPager,svFormPage);
                    llFormPage.addView(date.dateTextBox());
                    break;

                case "nextbutton":
                    pNextPrevButton nextButton = new pNextPrevButton(mcontext,context, fieldBean,vpPager,llFormPage,svFormPage,currentFragment);
                    llFormPage.addView(nextButton.nextButton());
                    break;
                case "prevbutton":
                    pNextPrevButton prevButton = new pNextPrevButton(mcontext, context,fieldBean,vpPager,llFormPage,svFormPage,currentFragment);
                    llFormPage.addView(prevButton.prevButton());
                    break;
                case "email":
                    pNewEditText email = new pNewEditText(context, fieldBean, vpPager,svFormPage);
                    llFormPage.addView(email.email());

                    break;
                case "telephone":
                    pNewEditText telephone = new pNewEditText(context, fieldBean, vpPager,svFormPage);
                    llFormPage.addView(telephone.telephone());

                    break;
                case "textarea":
                    pNewEditText textarea = new pNewEditText(context, fieldBean, vpPager,svFormPage);
                    llFormPage.addView(textarea.textArea());

                    break;
                case "label":
                    pTextView label = new pTextView(context, fieldBean);
                    llFormPage.addView(label.label(14));
                    break;
                case "dropdown":
                    pNewSpinner dropdown = new pNewSpinner(context, fieldBean,svFormPage);
                    llFormPage.addView(dropdown.dropDown());
                    break;
                case "SearchableSpinner":
                    pSearchableSpinner searchSpinner = new pSearchableSpinner(context, fieldBean,svFormPage);
                    llFormPage.addView(searchSpinner.dropDown());
                    break;
                case "radiogroup":
                    pRadioGroup radioGroup = new pRadioGroup(context, fieldBean);
                    llFormPage.addView(radioGroup.radioGroup());
                    break;
                case "image":
                    pImageSelector imageSelector = new pImageSelector(mcontext, context, fieldBean,svFormPage);
                    llFormPage.addView(imageSelector.createImageView());
                    break;
                case "group":
                    pGroup group = new pGroup(mcontext, context, fieldBean, currentFragment, vpPager,svFormPage);
                    llFormPage.addView(group.group());
                    break;
                case "button":
                    pButton button = new pButton(mcontext,context, fieldBean,llFormPage,vpPager,svFormPage);
                    llFormPage.addView(button.button());
                    break;
                case "calendar":
                    pDateSelector dateSelector = new pDateSelector(mcontext,context, fieldBean, currentFragment,svFormPage);
                    llFormPage.addView(dateSelector.dateSelector());
                    break;*/
            }
        }
        return llFormPage;
    }


}
