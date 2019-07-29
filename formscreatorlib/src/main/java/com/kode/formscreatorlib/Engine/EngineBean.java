package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kode.formscreatorlib.Model.MainForms;
import com.kode.formscreatorlib.R;

import java.io.IOException;
import java.io.InputStream;

public class EngineBean {
    private Activity mContext;
    private Gson gson;
    private MainForms formFormat;
    private String json;
    private int pageSize;
    private MainPagerAdapter pagerAdapter;


    /**
     * Create constructor which takes the Activity context as its param
     *
     * @param mContext the Activity or calling fragment
     **/
    public EngineBean(Activity mContext) {
        this.mContext = mContext;

    }


    /**
     * This is the builder class that gets the json file from the assets and convert it to a Gson file.
     * This is later converted and mapped to the MainForms Object which is later used in creating the views
     *
     * @param jsonFileName This is the json file name saved in the asset
     **/
    public EngineBean Builder(String jsonFileName) {
        this.readJsonFile(jsonFileName);
        this.convertJsonToObj();
        this.generateViews();


        return this;
    }


    /**
     * Read the json file file from the assets when interfacing with this library
     *
     * @param fileName the file name of the json file saved in the assets
     **/
    public EngineBean readJsonFile(String fileName) {

        if (mContext == null || fileName == null || TextUtils.isEmpty(fileName))
            return this;

        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(fileName);

            if (is != null) {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                json = new String(buffer, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return this;
    }


    private String readJsonFile() {
        this.readJsonFile("forms.json");
        return json;
    }


    private void convertJsonToObj() {
        gson = new GsonBuilder().create();
        formFormat = gson.fromJson(json, MainForms.class);
    }


    /**
     * The viewGenerator will create the various pages in scrollView while the controlcCreator will generate the individual
     * controls on each page. its a loop within a loop
     **/
    private void generateViews() {
        //Generate the number
        pageSize = formFormat.getPages().size();

        // Create an initial view to display; must be a subclass of FrameLayout.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ScrollView svFormPage;

        ControlsCreator controlsCreator;

        for (int i = 0; i < pageSize; i++) {
            svFormPage = (ScrollView) inflater.inflate(R.layout.form_controls_fragment, null);

            /** use the controls creator to create form controls **/
            controlsCreator = new ControlsCreator(mContext,svFormPage,formFormat.getPages().get(i));
            svFormPage = controlsCreator.generate(formFormat.getPages().get(i));

            pagerAdapter.addView(svFormPage, i);
            pagerAdapter.notifyDataSetChanged();
        }

       /* // Set page indicator
        pagerSlider = (PodSlider) view.findViewById(R.id.pager_slider);
        pagerSlider.usePageTitles(false);
        pagerSlider.setUpWithViewPager(vpPager);

        pagerSlider.setVisibility(View.GONE);

        // Build the components in the fragment

        *//**Disable the swiping of the viewpager by hand horizontally**//*
        vpPager.setPagingEnabled(false);*/

    }


}
