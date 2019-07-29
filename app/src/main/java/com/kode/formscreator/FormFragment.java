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
    String productTypeName,productTypeCode;
    TextView tvPageHeader;

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

       /* *//** Get bundle data **//*
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            productTypeName = bundle.getString("product_name","Product Type");
            productTypeCode = bundle.getString("code","Product Code");
        }
*/
        /** intiialize views **/
       // initViews(view);

        /** set view pager **/
        new EngineBean(getActivity()).Builder("");

    }


    private void initViews(View view) {
        tvPageHeader = (TextView) view.findViewById(R.id.tv_page_header);
        tvPageHeader.setText(productTypeName);

    }


    @Override
    public void onClick(View v) {

    }


}