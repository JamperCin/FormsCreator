package com.kode.formscreator;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kode.formscreatorlib.Engine.EngineBean;


/**
 * Created by andrews on 12/14/16.
 */

public class FormFragment extends Fragment implements View.OnClickListener {

    Context context;
    Activity mContext;
    View parentView;
    TextView tvPageHeader;
    CustomViewPager viewPager;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        context = getContext();
        mContext = getActivity();

        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.frag_form, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        parentView = view;

        viewPager = view.findViewById(R.id.viewPager);
        tvPageHeader = view.findViewById(R.id.tv_page_header);

        viewPager.setPagingEnabled(true);


        /** set view pager **/
        new EngineBean(getActivity(), getFragmentManager()).setHeaderView(tvPageHeader).Builder("forms.json", viewPager);

    }




    @Override
    public void onClick(View v) {

    }


}