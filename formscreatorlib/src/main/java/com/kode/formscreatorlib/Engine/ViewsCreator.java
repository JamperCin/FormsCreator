package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.jamper.searchingspinner.UI.SearchingSpinner;
import com.kode.formscreatorlib.Callbacks.CallBack;
import com.kode.formscreatorlib.Callbacks.OnItemSelected;
import com.kode.formscreatorlib.Callbacks.OnSubmitOnClick;
import com.kode.formscreatorlib.Model.DisabledViews;
import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.Forms;
import com.kode.formscreatorlib.Model.OptionsForms;
import com.kode.formscreatorlib.Model.ShowIfForms;
import com.kode.formscreatorlib.Model.ViewError;
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.DatePickerClass;
import com.kode.formscreatorlib.Utils.FormsUtils;
import com.kode.formscreatorlib.Utils.ValidationClass;

import java.util.ArrayList;
import java.util.List;

import static com.kode.formscreatorlib.Utils.FormsUtils.FORM_CODE;
import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;
import static com.kode.formscreatorlib.Utils.FormsUtils.TAG_LIST;
import static com.kode.formscreatorlib.Utils.FormsUtils.convertModel;

/**
 * Created by jamper on 1/16/2018.
 */

public class ViewsCreator {

    Activity mContext;
    LinearLayout.LayoutParams params;
    private String spinnerItem = "";

    private ArrayList<ViewError> viewList;
    private ArrayList<View> innerViewsList, hiddenViews;
    private FormsUtils utils;
    private ViewError viewError;
    private boolean isShowQuestionCodes;
    android.support.v4.app.FragmentManager fragmentManager;


    public ViewsCreator(Activity context, android.support.v4.app.FragmentManager fragmentManager) {
        this.mContext = context;
        this.fragmentManager = fragmentManager;

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(16, 16, 16, 16);
        spinnerItem = "233";
        viewList = new ArrayList<>();
        innerViewsList = new ArrayList<>();
        hiddenViews = new ArrayList<>();
        utils = new FormsUtils(mContext);
    }

    public ViewsCreator(Activity context) {
        this.mContext = context;

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(16, 16, 16, 16);
        spinnerItem = "233";
        viewList = new ArrayList<>();
        innerViewsList = new ArrayList<>();
        hiddenViews = new ArrayList<>();
        utils = new FormsUtils(mContext);
    }


    public ViewsCreator isShowQuestionCode(boolean isShowQuestionCodes) {
        this.isShowQuestionCodes = isShowQuestionCodes;
        return this;
    }


    private int getType(int type) {
        switch (type) {
            case 0:
                return InputType.TYPE_CLASS_TEXT;
            case 1:
                return InputType.TYPE_CLASS_NUMBER;
            case 2:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }

    }


    private int getType(String type) {
        if (type == null)
            type = "3";

        switch (type) {
            case "0":
                return InputType.TYPE_CLASS_TEXT;
            case "1":
                return InputType.TYPE_CLASS_NUMBER;
            case "2":
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }

    }


    private void saveData(View v, final FieldsForms field) {
        if (v instanceof EditText) {
            final EditText editText = (EditText) v;
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

//                    Forms forms = new Forms(editable.toString(), field.getLabel(), field.getCode(), field.getPageCode());
//                    String value = convertModel(forms);
//                    utils.cacheStringData(value, field.getCode());

                    saveData(editable.toString(), field);
                }
            });
        }
    }


    private void saveData(String answer, final FieldsForms field) {
        Forms forms = new Forms(answer, field.getLabel(), field.getCode(), field.getPageCode());
        String value = convertModel(forms);

        utils.cacheStringData(value, field.getCode());

        makeViewActive(answer, field);

        makeViewVisible(answer, field);
    }


    private void addTags(String tag) {
        try {
            int index = TAG_LIST.indexOf(tag);

            if (index == -1)
                TAG_LIST.add(tag);


        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * =========================== VARIOUS VIEWS CREATED OVER HERE ==============================
     **/

    public LinearLayout editText(FieldsForms forms) {
        String type = forms.getInputType();
        String hint = forms.getLabel();
        String tag = forms.getCode();

        LinearLayout linearLayout = createTag(forms);

        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setInputType(getType(type));
        if (type != null && type.equalsIgnoreCase("1"))
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
        editText.setHint(hint);
        if (tag != null)
            editText.setTag(tag);
        editText.setTextSize(14f);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();


        //Add View only and only if it is a required field with its associate error message
        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True")) {
            viewError = new ViewError(editText, forms.getLabel());
            viewList.add(viewError);
        }

        innerViewsList.add(editText); //Add View regardless of it being required

        saveData(editText, forms);

        addTags(forms.getCode());
        editText.setText("");


        linearLayout.addView(editText);

        if (forms.isHidden()) {
            linearLayout.setVisibility(View.GONE);
            hiddenViews.add(linearLayout);
        }

        return linearLayout;
    }


    public LinearLayout textArea(FieldsForms forms) {
        // String type = forms.getInputType();
        String hint = forms.getLabel();
        String tag = forms.getCode();

        LinearLayout linearLayout = createTag(forms);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.setMargins(16, 16, 16, 16);

        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);

        editText.setHint(hint);
        if (tag != null)
            editText.setTag(tag);
        editText.setTextSize(14f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(mContext.getResources().getDrawable(R.drawable.white_smoke_square_bg));
        }
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));

        //Add View only and only if it is a required field with its associate error message
        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True")) {
            viewError = new ViewError(editText, forms.getLabel());
            viewList.add(viewError);
        }

        innerViewsList.add(editText); //Add View regardless of it being required

        saveData(editText, forms);

        addTags(forms.getCode());
        editText.setText("");

        linearLayout.addView(editText);

        if (forms.isHidden()) {
            linearLayout.setVisibility(View.GONE);
            hiddenViews.add(linearLayout);
        }

        return linearLayout;
    }


    public Button submitButton(final FieldsForms forms, final OnSubmitOnClick callBack) {
        String text = forms.getLabel();
        final String tag = forms.getCode();

        final Button button = new Button(mContext);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        button.setTag(tag);
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ValidationClass(mContext).Validator(viewList)) {
                    if (callBack != null)
                        callBack.submit();

                    LOG("success");
                } else
                    LOG("failed");

            }
        });

        viewError = new ViewError(button, forms.getLabel());
        viewList.add(viewError);

        return button;
    }


    public Button button(final FieldsForms forms, final ViewPager viewPager, final MainPagerAdapter adapter, final List<FieldsForms> formsList) {
        String text = forms.getLabel();
        final String tag = forms.getCode();

        final Button button = new Button(mContext);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        button.setTag(tag);
        button.setAllCaps(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ValidationClass(mContext).Validator(viewList)) {
                    new GotoIfEngine(mContext).Builder(formsList, viewPager, adapter);

                  new GotoIfEngine(mContext).handleRepeat(forms,fragmentManager);
                }

                LOG("Size " + viewList.size());
            }
        });
        viewError = new ViewError(button, forms.getLabel());
        viewList.add(viewError);

        return button;
    }


    public Button prevButton(final FieldsForms forms, final ViewPager viewPager, final MainPagerAdapter adapter) {

        String text = "Previous";
        final String tag = forms.getCode();

        final Button button = new Button(mContext);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setAllCaps(false);
        button.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        button.setTag(tag);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GotoIfEngine(mContext).handleGotoSource(forms, viewPager, adapter);
            }
        });

        viewError = new ViewError(button, forms.getLabel());
        viewList.add(viewError);
        return button;
    }


    public LinearLayout previousNextButton(final FieldsForms forms, final ViewPager viewPager, final MainPagerAdapter adapter, List<FieldsForms> fieldsFormsList) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);


        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        p.weight = 1f;
        p.setMargins(16, 16, 16, 16);

        final Button previousBtn = prevButton(forms, viewPager, adapter);
        final Button nextBtn = button(forms, viewPager, adapter, fieldsFormsList);


        previousBtn.setLayoutParams(p);
        nextBtn.setLayoutParams(p);

        linearLayout.addView(previousBtn);
        linearLayout.addView(nextBtn);

        return linearLayout;

    }


    public View spinner(ArrayList<String> list) {
        try {
            if (list != null) {
                if (list.size() > 10)
                    return searchableSpinner(list);
                else return normalSpinner(list);
            }
        } catch (Exception e) {
            LOG("Error with spinner >> " + e.getMessage());
        }
        return normalSpinner(list);
    }

    public Spinner normalSpinner(List<String> list) {
        final Spinner spinner = new Spinner(mContext);
        spinner.setLayoutParams(params);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                spinner.setBackground(mContext.getDrawable(R.drawable.spinner_default_holo_light));
            }
        } catch (Exception e) {
        }


        if (list != null) {
            ArrayAdapter mAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_layout, list);
            mAdapter.setDropDownViewResource(R.layout.simple_dropdown_item);
            spinner.setAdapter(mAdapter);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner.setTag(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setTag(adapterView.getSelectedItem().toString());
            }
        });

//        viewError = new ViewError(spinner, forms.getLabel());
//        viewList.add(viewError);
        return spinner;
    }

    public SearchingSpinner searchableSpinner(ArrayList<String> list) {
        final SearchingSpinner spinner = new SearchingSpinner(mContext);
        spinner.addEntries(list);
        spinner.setTitle("Search here");
        spinner.setAcceptLocalEntries(true);
        spinner.setLocalEntriesAddable(true);
        spinner.setItemOnClickDismissDialog(true);
        spinner.setOnItemSelectedListener(new com.jamper.searchingspinner.Logic.OnItemSelected() {
            @Override
            public void onItemSelected(String s, int i) {
                spinner.setTag(s);
            }

            @Override
            public void onNothingSelected(String s) {
                spinner.setTag(s);
            }
        });
        spinner.setTag(spinner.getSelectedItem());

        // viewList.add(spinner);
        return spinner;
    }


    public TextView textView(String text, boolean bolded) {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        if (bolded)
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        // viewList.add(textView);
        return textView;
    }

    public TextView textView(String text, boolean bolded, boolean center) {
        TextView textView = new TextView(mContext);
        if (center)
            params.gravity = Gravity.CENTER_HORIZONTAL;

        textView.setLayoutParams(params);
        textView.setText(text);
        textView.setTextSize(14);
        if (bolded)
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        //  viewList.add(textView);
        return textView;
    }

    public View space() {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setText("");
        textView.setTextSize(14);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        // viewList.add(textView);
        return textView;
    }

    public RadioGroup radioGroup(ArrayList<String> list, final CallBack callBack) {
        final RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setLayoutParams(params);
        radioGroup.setTag("TAG");
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        if (list != null && list.size() != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.setMargins(16, 16, 16, 16);


            for (String str : list) {
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(String.format("%s", str));
                radioButton.setLayoutParams(params);
                radioButton.setPadding(16, 0, 50, 0);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        radioGroup.setTag(radioButton.getText().toString());
                        if (callBack != null)
                            callBack.execute(radioButton.getText().toString());
                    }
                });
                radioGroup.addView(radioButton);
            }
        }

        // viewList.add(radioGroup);
        return radioGroup;
    }


    public LinearLayout radioGroup(final FieldsForms forms) {
        String header = forms.getLabel();
        final String tag = forms.getCode();

        String question = header;
        if (this.isShowQuestionCodes)
            question = tag + " : " + header;

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_HORIZONTAL;

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(p);
        linearLayout.setTag(tag);

        linearLayout.addView(textView(question, true)); //Add the Question Tag to each question
        final RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setLayoutParams(p);
        radioGroup.setTag(tag);
        radioGroup.setOrientation(LinearLayout.VERTICAL);

        if (forms.getOptions() != null && forms.getOptions().size() != 0) {

            final OptionsForms option = forms.getOptions().get(0);

            if (option.getId01() != null)
                radioGroup.addView(radioButton("01", option.getId01(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId01(), forms);

                    }
                }));

            if (option.getId02() != null)
                radioGroup.addView(radioButton("02", option.getId02(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId02(), forms);
                    }
                }));

            if (option.getId03() != null)
                radioGroup.addView(radioButton("03", option.getId03(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId03(), forms);
                    }
                }));

            if (option.getId04() != null)
                radioGroup.addView(radioButton("04", option.getId04(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId04(), forms);
                    }
                }));

            if (option.getId05() != null)
                radioGroup.addView(radioButton("05", option.getId05(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId05(), forms);
                    }
                }));

            if (option.getId06() != null)
                radioGroup.addView(radioButton("06", option.getId06(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId06(), forms);
                    }
                }));

            if (option.getId07() != null)
                radioGroup.addView(radioButton("07", option.getId07(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId07(), forms);
                    }
                }));

            if (option.getId08() != null)
                radioGroup.addView(radioButton("08", option.getId08(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId08(), forms);
                    }
                }));

            if (option.getId09() != null)
                radioGroup.addView(radioButton("09", option.getId09(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId09(), forms);
                    }
                }));

            if (option.getId10() != null)
                radioGroup.addView(radioButton("10", option.getId10(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId10(), forms);
                    }
                }));

            if (option.getId11() != null)
                radioGroup.addView(radioButton("11", option.getId11(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId11(), forms);
                    }
                }));

            if (option.getId12() != null)
                radioGroup.addView(radioButton("12", option.getId12(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId12(), forms);
                    }
                }));
            if (option.getId13() != null)
                radioGroup.addView(radioButton("13", option.getId13(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId13(), forms);
                    }
                }));
            if (option.getId14() != null)
                radioGroup.addView(radioButton("14", option.getId14(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId14(), forms);
                    }
                }));
            if (option.getId15() != null)
                radioGroup.addView(radioButton("15", option.getId15(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId15(), forms);
                    }
                }));
            if (option.getId16() != null)
                radioGroup.addView(radioButton("16", option.getId16(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveData(option.getId16(), forms);
                    }
                }));
        }


        //Add View only and only if it is a required field with its associate error message
        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True")) {
            viewError = new ViewError(radioGroup, forms.getLabel());
            viewList.add(viewError);
        }

        innerViewsList.add(radioGroup); //Add View regardless of it being required

        saveData("", forms); //Save empty data first
        addTags(forms.getCode());

        linearLayout.addView(radioGroup);

        if (forms.isHidden()) {
            linearLayout.setVisibility(View.GONE);
            hiddenViews.add(linearLayout);
        }

        return linearLayout;
    }


    private RadioButton radioButton(String tag, String text, View.OnClickListener onclick) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        final RadioButton radioButton = new RadioButton(mContext);
        radioButton.setText(String.format("%s     ", text));
        radioButton.setTag(tag);
        radioButton.setLayoutParams(params);
        radioButton.setOnClickListener(onclick);
        radioButton.setLayoutParams(params);

        return radioButton;
    }


    public LinearLayout datePicker(FieldsForms forms) {
        String text = forms.getLabel();
        final String tag = forms.getCode();

        LinearLayout lin = createTag(forms);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setTag(tag);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_HORIZONTAL;

        final EditText editText = new EditText(mContext);
        editText.setEnabled(false);
        editText.setHint("dd/MM/yyyy (" + text + ")");
        editText.setTextSize(14f);
        editText.setTag(tag);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        editText.setLayoutParams(p);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.theme_pimageview, null);
        // imageView.setPadding(2, 2, 2, 2);

        LinearLayout.LayoutParams pImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        pImage.weight = .3f;
        pImage.gravity = Gravity.END;


        imageView.setTag(tag);
        imageView.setLayoutParams(pImage);
        //  imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.calender));
        imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.black));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerClass dp = new DatePickerClass(fragmentManager).openCalendarDatePicker();
                dp.onDateSelected(new OnItemSelected() {
                    @Override
                    public void onItemSelected(String item) {
                        editText.setText(item);

                    }
                });
            }
        });

        new FormsUtils(mContext).setImageResource(R.drawable.calendar_32, imageView);

        saveData(editText, forms);
        addTags(forms.getCode());
        editText.setText("");

        linearLayout.addView(editText);
        linearLayout.addView(imageView);


        //Add View only and only if it is a required field with its associate error message
        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True")) {
            viewError = new ViewError(editText, forms.getLabel());
            viewList.add(viewError);
        }

        innerViewsList.add(linearLayout); //Add View regardless of it being required
        innerViewsList.add(imageView); //Add ImageView to restrict the onclick

        lin.addView(linearLayout);

        if (forms.isHidden()) {
            lin.setVisibility(View.GONE);
            hiddenViews.add(lin);
        }

        return lin;
    }


    /**
     * This function creates the Title to each question with a bolded Textview and
     * shows or hide the Question code based on th boolean value passed
     *
     * @param forms This is the instance of the field to be created
     **/
    private LinearLayout createTag(FieldsForms forms) {
        String header = forms.getLabel();
        final String tag = forms.getCode();

        String question = header;
        if (this.isShowQuestionCodes)
            question = "\n" + tag + " : " + header;

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_HORIZONTAL;

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(p);
        linearLayout.setTag(tag);

        linearLayout.addView(textView(question, true)); //Add the Question Tag to each question

        return linearLayout;
    }


    /**
     * =========================== VARIOUS VIEWS CREATED OVER HERE ==============================
     **/


    private void makeViewActive(String answer, FieldsForms forms) {
        if (forms.getDisabledCodes() != null && forms.getDisabledCodes().size() > 0 && answer != null && !answer.isEmpty()) {

            for (View v : innerViewsList) {
                v.setEnabled(true);
                v.setClickable(true);
                v.setBackgroundColor(Color.TRANSPARENT);

                if (v instanceof EditText)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        v.setBackground(mContext.getDrawable(R.drawable.edit_text_holo_light));
                    }

                if (v instanceof RadioGroup) {
                    RadioGroup radioGroup = (RadioGroup) v;
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(true);
                    }
                }

            }


            //Get the answer chosen by the user and use that to query for the disabled views by their TAG names
            for (DisabledViews d : forms.getDisabledCodes()) {
                if (d != null && d.getAnswer() != null && d.getAnswer().equalsIgnoreCase(answer)) {

                    for (String code : d.getCodes()) {
                        if (code != null && !code.isEmpty()) {
                            for (View v : innerViewsList) {
                                String tag = "";
                                if (v.getTag() != null)
                                    tag = v.getTag().toString();

                                // LOG("TAGw >> " + tag);

                                if (tag.equalsIgnoreCase(code)) {
                                    v.setEnabled(false);
                                    v.setClickable(false);


                                    if (v instanceof RadioGroup) {
                                        RadioGroup radioGroup = (RadioGroup) v;
                                        radioGroup.clearCheck();
                                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                            radioGroup.getChildAt(i).setEnabled(false);
                                        }
                                    }

                                    if (v instanceof ImageView)
                                        LOG("images clicked");
                                    else
                                        v.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_black_hex_1));


                                }
                            }
                        }

                        saveEmptyForDisabledView(code); //Clear the previously saved answer for a disabled view
                    }

                    break;
                }
            }
        }
    }

    private void makeViewVisible(String answer, FieldsForms forms) {
        if (answer != null && !answer.isEmpty() && forms.getShowIf() != null && forms.getShowIf().size() > 0) {

            for (ShowIfForms d : forms.getShowIf()) {

                //Get the answer chosen by the user and use that to query for the hidden views by their TAG names and make them visible
                if (d != null && d.getVisibleCodes() != null && d.getVisibleCodes().size() > 0) {
                    if (d.getAnswer() != null && d.getAnswer().equalsIgnoreCase(answer)) {
                        for (String code : d.getVisibleCodes()) {
                            if (code != null && !code.isEmpty()) {
                                for (View v : hiddenViews) {
                                    String tag = "";
                                    if (v.getTag() != null)
                                        tag = v.getTag().toString();

                                    LOG("TAGw >> " + tag + " >> " + code);

                                    if (tag.equalsIgnoreCase(code)) {
                                        v.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            //saveEmptyForDisabledView(code); //Clear the previously saved answer for a hidden view
                        }
                        break;
                    }
                }

                //Get all visible views and set them to gone
                if (d != null && d.getHiddenCodes() != null && d.getHiddenCodes().size() > 0) {
                    if (d.getAnswer() != null && d.getAnswer().equalsIgnoreCase(answer)) {
                        for (String code : d.getHiddenCodes()) {
                            if (code != null && !code.isEmpty()) {
                                for (View v : hiddenViews) {
                                    String tag = "";
                                    if (v.getTag() != null)
                                        tag = v.getTag().toString();

                                    LOG("TAGw >> " + tag + " >> " + code);

                                    if (tag.equalsIgnoreCase(code)) {
                                        v.setVisibility(View.GONE);
                                    }
                                }
                            }

                        }
                        break;
                    }
                }

            }
        }
    }


    private void saveEmptyForDisabledView(final String tag) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Forms f = new FormsUtils(mContext).getSavedAnswer(tag);
                String questionCode = f.getQuestionCode();
                String question = f.getQuestion();
                String answer = f.getAnswer();
                String pageCode = f.getPageCode();
                String formCode = f.getFormCode();

                FORM_CODE = formCode;
                Forms forms = new Forms("", question, questionCode, pageCode);
                String value = convertModel(forms);

                utils.cacheStringData(value, tag);
            }
        });

    }


}
