package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.kode.formscreatorlib.Callbacks.OnSubmitOnClick;
import com.kode.formscreatorlib.Model.MainForms;
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.CustomViewPager;

import java.io.IOException;
import java.io.InputStream;

import static com.kode.formscreatorlib.Utils.FormsUtils.FORM_CODE;
import static com.kode.formscreatorlib.Utils.FormsUtils.TAG_LIST;

public class EngineBean {
    private Activity mContext;
    private Gson gson;
    private MainForms formFormat;
    private String json;
    private int pageSize;
    private MainPagerAdapter pagerAdapter;
    private CustomViewPager viewPager;
    private FragmentManager fragmentManager;
    private TextView textView;
    private OnSubmitOnClick callBack;
    private boolean isPagingEnabled;


    /**
     * Create constructor which takes the Activity context as its param
     *
     * @param mContext the Activity or calling fragment
     **/
    public EngineBean(Activity mContext) {
        this.mContext = mContext;
        TAG_LIST.clear();
        FORM_CODE = "";
    }


    /**
     * Create constructor which takes the Activity context as its param
     *
     * @param mContext the Activity or calling fragment
     **/
    public EngineBean(Activity mContext, FragmentManager fragmentManager) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
        TAG_LIST.clear();
        FORM_CODE = "";
    }
    
    
    public EngineBean setHeaderView(TextView textView){
        this.textView = textView;
        return this;
    }


    public EngineBean setOnSubmitClickListener(OnSubmitOnClick callBack){
        this.callBack = callBack;
        return this;
    }

    public EngineBean enablePaging(boolean isPagingEnabled){
        this.isPagingEnabled = isPagingEnabled;
        return this;
    }


    /**
     * This is the builder class that gets the json file from the assets and convert it to a Gson file.
     * This is later converted and mapped to the MainForms Object which is later used in creating the views
     *
     * @param jsonFileName This is the json file name saved in the asset
     **/
    public EngineBean Builder(String jsonFileName, CustomViewPager viewPager) {
        this.viewPager = viewPager;

        this.readJsonFile(jsonFileName);

        if (json == null)
            return this;

        setViewPager();

        this.convertJsonToObj();
        this.generateViews();


        return this;
    }


    private void setViewPager() {
        try {
            pagerAdapter = new MainPagerAdapter();
            viewPager.setPagingEnabled(isPagingEnabled); //TODO set this to false to stop swiping pages
            viewPager.setAdapter(pagerAdapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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
        try {
            gson = new GsonBuilder().create();
            formFormat = gson.fromJson(json, MainForms.class);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }
    }


    /**
     * The viewGenerator will create the various pages in scrollView while the controlcCreator will generate the individual
     * controls on each page. its a loop within a loop
     **/
    private void generateViews() {
        //Generate the number
        if (formFormat == null || formFormat.getPages() == null)
            return;

        FORM_CODE = formFormat.getForm(); //Get the form code

        if (textView != null)
            textView.setText(String.format("%s\n%s", formFormat.getTitle(), formFormat.getSection()));

        pageSize = formFormat.getPages().size();

        // Create an initial view to display; must be a subclass of FrameLayout.
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ScrollView svFormPage;

        ControlsCreator controlsCreator;

        for (int i = 0; i < pageSize; i++) {
            svFormPage = (ScrollView) inflater.inflate(R.layout.form_controls_fragment, null);

            /** use the controls creator to create form controls **/
            controlsCreator = new ControlsCreator(mContext,fragmentManager, svFormPage)
                    .setViewPager(viewPager, pagerAdapter)
                    .setOnSubmitClickListener(callBack);

            svFormPage = controlsCreator.generate(formFormat.getPages().get(i));

            pagerAdapter.addView(svFormPage, i);
            pagerAdapter.notifyDataSetChanged();
        }

    }



}
