package com.kode.formscreatorlib.UI;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kode.formscreatorlib.Callbacks.OnSubmitOnClick;
import com.kode.formscreatorlib.Engine.ControlsCreator;
import com.kode.formscreatorlib.Engine.MainPagerAdapter;
import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.PageForms;
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.CustomViewPager;

import java.util.ArrayList;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;


public class RepeatFrame extends BottomSheetDialogFragment {

    CustomViewPager viewPager;
    TextView textView;
    private int repeatCount = 0;
    private PageForms page;
    private MainPagerAdapter pagerAdapter;
    private boolean isPagingEnabled;
    private boolean isShowQuestionCodes;
    private String headerTitle = "";


    private ArrayList<PageForms> editedList = new ArrayList<>();

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.repeat_frame_layout, null);

        dialog.setContentView(contentView);

        setViewPager(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }


    /**
     * =========================================================== NEEDED FUNCTIONS/METHODS BELOW==================================================
     ***/



    public RepeatFrame setPage(PageForms page){
        this.page = page;
        return this;
    }

    public RepeatFrame setHeaderTitle(String headerTitle){
        this.headerTitle = headerTitle;
        return this;
    }

    public RepeatFrame setRepeatCount(int repeatCount){
        this.repeatCount = repeatCount;
        return this;
    }

    public RepeatFrame isPagingEnabled(boolean isPagingEnabled){
        this.isPagingEnabled = isPagingEnabled;
        return this;
    }

    public RepeatFrame isShowQuestionCode(boolean isShowQuestionCodes){
        this.isShowQuestionCodes = isShowQuestionCodes;
        return this;
    }


    private void setViewPager(View view) {
        viewPager = view.findViewById(R.id.viewPager_x);
        textView = view.findViewById(R.id.headerTitle_x);

        generateViews();

        textView.setText(headerTitle);
    }


    /**
     * The viewGenerator will create the various pages in scrollView while the controlcCreator will generate the individual
     * controls on each page. its a loop within a loop
     **/
    private void generateViews() {
        if (page == null)
            return;

        pagerAdapter = new MainPagerAdapter();
        viewPager.setPagingEnabled(isPagingEnabled); //TODO set this to false to stop swiping pages
        viewPager.setAdapter(pagerAdapter);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ScrollView svFormPage;

        ControlsCreator controlsCreator;


        for (int i = 0; i < repeatCount; i++) {
            editedList.add(page);
        }

        for (int i = 0; i < editedList.size(); i++) {

            PageForms forms = editedList.get(i);

            PageForms mForms = new PageForms();

            mForms.setRepeat(forms.isRepeat());
            mForms.setLabel(forms.getLabel());
            mForms.setFields(forms.getFields());
            mForms.setFieldCode(forms.getFieldCode() + "_" + (i + 1)); //Introduce new codes for the pages

            LOG("Code >> " + mForms.getFieldCode() + " Count " + repeatCount + " MM >> " + editedList.size());

            ArrayList<FieldsForms> fieldsList = new ArrayList<>();

            for (int k = 0; k < mForms.getFields().size(); k++){
                FieldsForms field = mForms.getFields().get(k);

                FieldsForms mField = new FieldsForms();
                mField.setLabel(field.getLabel());
                mField.setType(field.getType());
                mField.setPageCode(field.getPageCode());
                mField.setDisabledCodes(field.getDisabledCodes());
                mField.setGotoIf(field.getGotoIf());
                mField.setGotoSource(field.isGotoSource());
                mField.setHidden(field.isHidden());
                mField.setInputType(field.getInputType());
                mField.setOptions(field.getOptions());
                mField.setRepeatBlock(field.getRepeatBlock());
                mField.setRequired(field.getRequired());
                mField.setShowIf(field.getShowIf());
                mField.setCode(field.getCode() +  "_" + (i + 1) + "_" + (k + 1)); //Introduce new codes for the individual fields


                if((i == repeatCount -1) &&  (k == forms.getFields().size() - 1)) {
                    mField.setType("submitButton");
                    mField.setLabel("Save");
                }

                fieldsList.add(mField);

                LOG("Field Code >> " + mField.getCode());
            }

            mForms.setFields(fieldsList);

            svFormPage = (ScrollView) inflater.inflate(R.layout.form_controls_fragment, null);

            /** use the controls creator to create form controls **/
            controlsCreator = new ControlsCreator(getActivity(), getFragmentManager(), svFormPage)
                    .setViewPager(viewPager, pagerAdapter)
                    .isShowQuestionCode(isShowQuestionCodes)
                    .setOnSubmitClickListener(new OnSubmitOnClick() {
                        @Override
                        public void submit() {

                            dismiss();
                        }
                    });


            svFormPage = controlsCreator.generate(mForms);
            pagerAdapter.addView(svFormPage, i);

            pagerAdapter.notifyDataSetChanged();
        }
    }




}
