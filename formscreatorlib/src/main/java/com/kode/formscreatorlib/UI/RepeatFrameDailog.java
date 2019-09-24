package com.kode.formscreatorlib.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kode.formscreatorlib.Callbacks.OnSubmitOnClick;
import com.kode.formscreatorlib.Engine.ControlsCreator;
import com.kode.formscreatorlib.Engine.MainPagerAdapter;
import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.PageForms;
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.CustomViewPager;

import java.util.ArrayList;

import static android.view.Gravity.BOTTOM;
import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;

public class RepeatFrameDailog extends Dialog implements View.OnClickListener {

    CustomViewPager viewPager;
    TextView textView, cancelBtn_x;
    private int repeatCount = 0;
    private PageForms page;
    private MainPagerAdapter pagerAdapter;
    private boolean isPagingEnabled;
    private boolean isShowQuestionCodes;
    private String headerTitle = "";
    private int closeCounter = 0;

    private FragmentManager fragmentManager;
    private Activity mContext;

    private ArrayList<PageForms> editedList = new ArrayList<>();


    public RepeatFrameDailog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeat_frame_layout);

        setViewPager();

    }


    public RepeatFrameDailog setPage(PageForms page) {
        this.page = page;
        return this;
    }

    public RepeatFrameDailog setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
        return this;
    }

    public RepeatFrameDailog setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public RepeatFrameDailog isPagingEnabled(boolean isPagingEnabled) {
        this.isPagingEnabled = isPagingEnabled;
        return this;
    }

    public RepeatFrameDailog isShowQuestionCode(boolean isShowQuestionCodes) {
        this.isShowQuestionCodes = isShowQuestionCodes;
        return this;
    }

    public RepeatFrameDailog setContexts(Activity mContext, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mContext = mContext;
        return this;
    }


    private void setViewPager() {
        viewPager = findViewById(R.id.viewPager_x);
        textView = findViewById(R.id.headerTitle_x);
        cancelBtn_x = findViewById(R.id.cancelBtn_x);
        generateViews();

        textView.setText(headerTitle);

        cancelBtn_x.setOnClickListener(this);
        closeCounter = 0;
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

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            for (int k = 0; k < mForms.getFields().size(); k++) {
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
                mField.setCode(field.getCode() + "_" + (i + 1) + "_" + (k + 1)); //Introduce new codes for the individual fields


                if ((i == repeatCount - 1) && (k == forms.getFields().size() - 1)) {
                    mField.setType("submitButton");
                    mField.setLabel("Save");
                }

                fieldsList.add(mField);

                LOG("Field Code >> " + mField.getCode());
            }

            mForms.setFields(fieldsList);

            svFormPage = (ScrollView) inflater.inflate(R.layout.form_controls_fragment, null);

            /** use the controls creator to create form controls **/
            controlsCreator = new ControlsCreator(mContext, fragmentManager, svFormPage)
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


    @Override
    public void onClick(View view) {
        if (closeCounter < 1)
            Toast.makeText(getContext(), "Click again to exit", Toast.LENGTH_SHORT).show();
        else
            dismiss();

        closeCounter = closeCounter + 1;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (this.getWindow() != null) {
                final View view = this.getWindow().getDecorView();
                WindowManager.LayoutParams params = this.getWindow().getAttributes();
                params.gravity = BOTTOM;

                this.getWindow().setAttributes(params);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(getContext().getResources().getDrawable(R.drawable.dialog_inset_bg));
                }

                getWindow().getWindowManager().updateViewLayout(view, params);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
