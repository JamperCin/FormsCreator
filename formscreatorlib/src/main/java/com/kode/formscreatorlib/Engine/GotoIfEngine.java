package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.Forms;
import com.kode.formscreatorlib.Model.GotoIfForms;
import com.kode.formscreatorlib.Utils.FormsUtils;

import java.util.List;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;
import static com.kode.formscreatorlib.Utils.FormsUtils.convertModel;

public class GotoIfEngine {

    private ViewPager viewPager;
    private MainPagerAdapter mAdapter;
    private List<FieldsForms> fieldsForms;
    private FormsUtils utils;
    private Activity mContext;
    private static final String GO_TO_SOURCE_TAG = "SOURCE_FILE_NAME_";

    private int viewPagerPosition = -1;


    public GotoIfEngine(Activity mContext) {
        this.mContext = mContext;
        utils = new FormsUtils(mContext);
    }

    public GotoIfEngine Builder(final List<FieldsForms> forms, final ViewPager viewPager, final MainPagerAdapter adapter) {
        this.viewPager = viewPager;
        this.mAdapter = adapter;
        this.fieldsForms = forms;

        handleGotoIfLogic();

        return this;
    }


    private void handleGotoIfLogic() {
        try {

            if (fieldsForms != null && fieldsForms.size() > 0) {

                for (FieldsForms forms : fieldsForms) {
                    if (forms.getGotoIf() != null && forms.getGotoIf().size() > 0) {

                        for (GotoIfForms go : forms.getGotoIf()) {
                            viewPagerPosition = mAdapter.getViewPosition(returnPagesCodes(go));
                        }
                    }
                }
            }

            if (viewPagerPosition != -1)
                viewPager.setCurrentItem(viewPagerPosition);
            else {
                // utils.cacheStringData("", GO_TO_SOURCE_TAG);  //Clear the source of the page where we were before moving to the gotoif position
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String returnPagesCodes(GotoIfForms go) {
        if (go == null)
            return "";

        if (go.getAnswer() == null || TextUtils.isEmpty(go.getAnswer()))
            return "";

        //When the answer for the gotoIf is zero, it means whichever answer chosen send him to the next value
        if (go.getAnswer().equalsIgnoreCase("0"))
            return go.getNext();


        Forms f = utils.getSavedAnswer(go.getQuestionCode() == null ? "" : go.getQuestionCode());

        if (f == null)
            return "";

        String savedAnswer = f.getAnswer();
        LOG("ANS is " + savedAnswer + "  >>>  " + go.getAnswer());

        if (savedAnswer != null && savedAnswer.equalsIgnoreCase(go.getAnswer())) {
            utils.cacheStringData(go.getGotoSource(), GO_TO_SOURCE_TAG); //Save the source of the page where we were before moving to the gotoif position
            return go.getNext();
        }


        return "";
    }


    public void handleGotoSource(FieldsForms forms, final ViewPager viewPager, final MainPagerAdapter mAdapter) {
        try {
            if (forms != null && forms.isGotoSource()) {
                String savedAnswer = utils.getAnswer(GO_TO_SOURCE_TAG);

                LOG("forms >> " + savedAnswer);

                if (savedAnswer != null && !TextUtils.isEmpty(savedAnswer))
                    viewPagerPosition = mAdapter.getViewPosition(savedAnswer);
            }

            if (viewPagerPosition != -1) {
                viewPager.setCurrentItem(viewPagerPosition);
                utils.cacheStringData("", GO_TO_SOURCE_TAG);  //Clear the source of the page where we were before moving to the gotoif position

            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
