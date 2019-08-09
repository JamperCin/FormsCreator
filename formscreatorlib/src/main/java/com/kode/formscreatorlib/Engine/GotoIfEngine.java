package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.kode.formscreatorlib.Model.FieldsForms;
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

    private int viewPagerPosition = -1;


    public GotoIfEngine(Activity mContext) {
        this.mContext = mContext;
        utils = new FormsUtils(mContext);
    }

    public GotoIfEngine Builder(final List<FieldsForms> forms, final ViewPager viewPager, final MainPagerAdapter adapter) {
        this.viewPager = viewPager;
        this.mAdapter = adapter;
        this.fieldsForms = forms;

        LOG("CONV >> " + convertModel(fieldsForms));

        handleGotoIfLogic();

        return this;
    }


    private void handleGotoIfLogic() {
        try {

            if (fieldsForms != null && fieldsForms.size() > 0) {

                for (FieldsForms forms : fieldsForms) {
                    if (forms.getGotoIf() != null && forms.getGotoIf().size() > 0) {
                        LOG("Gotoif size" + forms.getGotoIf().size());
                        for (GotoIfForms go : forms.getGotoIf()) {
                            viewPagerPosition = mAdapter.getViewPosition(returnPagesCodes(go));
                        }
                    }
                }
            }


            if (viewPagerPosition != -1)
                viewPager.setCurrentItem(viewPagerPosition);
            else
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String returnPagesCodes(GotoIfForms go) {

        if (go.getAnswer() != null) {
            String savedAnswer = utils.getAnswer(go.getQuestionCode() == null ? "" : go.getQuestionCode());

            LOG("ANS " + savedAnswer + "  >>>  " + go.getAnswer());
            if (savedAnswer.equalsIgnoreCase(go.getAnswer()))
                return go.getNext();
        }

        return "";
    }


}
