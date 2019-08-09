package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;


import com.jamper.searchingspinner.UI.SearchingSpinner;
import com.kode.formscreatorlib.Callbacks.CallBack;
import com.kode.formscreatorlib.Callbacks.OnItemSelected;
import com.kode.formscreatorlib.Callbacks.OnSubmitOnClick;
import com.kode.formscreatorlib.Model.FieldsForms;
import com.kode.formscreatorlib.Model.GotoIfForms;
import com.kode.formscreatorlib.Model.OptionsForms;
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.DatePickerClass;
import com.kode.formscreatorlib.Utils.FormsUtils;
import com.kode.formscreatorlib.Utils.ValidationClass;

import java.util.ArrayList;
import java.util.List;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;

/**
 * Created by jamper on 1/16/2018.
 */

public class ViewsCreator {

    Activity mContext;
    LinearLayout.LayoutParams params;
    private String spinnerItem = "";

    private ArrayList<View> viewList;
    private ArrayList<View> innerViewsList;
    private FormsUtils utils;


    public ViewsCreator(Activity context) {
        this.mContext = context;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(16, 16, 16, 16);
        spinnerItem = "233";
        viewList = new ArrayList<>();
        innerViewsList = new ArrayList<>();
        utils = new FormsUtils(mContext);
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


    private void saveData(View v, final String tag) {
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

                    utils.cacheStringData(editable.toString(), tag);
                }
            });
        }
    }


    public EditText editText(FieldsForms forms) {
        String type = forms.getInputType();
        String hint = forms.getLabel();
        String tag = forms.getCode();

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

        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True"))
            viewList.add(editText);

        saveData(editText, tag);

        return editText;
    }


    public EditText textArea(FieldsForms forms) {
        // String type = forms.getInputType();
        String hint = forms.getLabel();
        String tag = forms.getCode();

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

        if (forms.getRequired() != null && forms.getRequired().equalsIgnoreCase("True"))
            viewList.add(editText);

        saveData(editText, tag);

        return editText;
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
                }else
                    LOG("failed");

            }
        });
        viewList.add(button);
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
                if (new ValidationClass(mContext).Validator(viewList))
                    new GotoIfEngine(mContext).Builder(formsList, viewPager, adapter);

                LOG("Size " + viewList.size());
            }
        });
        viewList.add(button);
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
                int currentPage = viewPager.getCurrentItem();

                if (currentPage != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        viewList.add(button);
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

        viewList.add(spinner);
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
        viewList.add(spinner);
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
        viewList.add(textView);
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
        viewList.add(textView);
        return textView;
    }

    public View space() {
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(params);
        textView.setText("");
        textView.setTextSize(14);
        textView.setTextColor(mContext.getResources().getColor(R.color.black));
        viewList.add(textView);
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

        viewList.add(radioGroup);
        return radioGroup;
    }


    public LinearLayout radioGroup(FieldsForms forms) {
        String header = forms.getLabel();
        final String tag = forms.getCode();

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_HORIZONTAL;

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(p);
        linearLayout.setTag(tag);

        linearLayout.addView(textView(header, true));

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
                        utils.cacheStringData(option.getId01(), tag);
                    }
                }));

            if (option.getId02() != null)
                radioGroup.addView(radioButton("02", option.getId02(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId02(), tag);
                    }
                }));

            if (option.getId03() != null)
                radioGroup.addView(radioButton("03", option.getId03(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId03(), tag);
                    }
                }));

            if (option.getId04() != null)
                radioGroup.addView(radioButton("04", option.getId04(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId04(), tag);
                    }
                }));

            if (option.getId05() != null)
                radioGroup.addView(radioButton("05", option.getId05(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId05(), tag);
                    }
                }));

            if (option.getId06() != null)
                radioGroup.addView(radioButton("06", option.getId06(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId06(), tag);
                    }
                }));

            if (option.getId07() != null)
                radioGroup.addView(radioButton("07", option.getId07(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId07(), tag);
                    }
                }));

            if (option.getId08() != null)
                radioGroup.addView(radioButton("08", option.getId08(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId08(), tag);
                    }
                }));

            if (option.getId09() != null)
                radioGroup.addView(radioButton("09", option.getId09(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId09(), tag);
                    }
                }));

            if (option.getId10() != null)
                radioGroup.addView(radioButton("10", option.getId10(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId10(), tag);
                    }
                }));

            if (option.getId11() != null)
                radioGroup.addView(radioButton("11", option.getId11(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId11(), tag);
                    }
                }));

            if (option.getId12() != null)
                radioGroup.addView(radioButton("12", option.getId12(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId12(), tag);
                    }
                }));
            if (option.getId13() != null)
                radioGroup.addView(radioButton("13", option.getId13(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId13(), tag);
                    }
                }));
            if (option.getId14() != null)
                radioGroup.addView(radioButton("14", option.getId14(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId14(), tag);
                    }
                }));
            if (option.getId15() != null)
                radioGroup.addView(radioButton("15", option.getId15(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId15(), tag);
                    }
                }));
            if (option.getId16() != null)
                radioGroup.addView(radioButton("16", option.getId16(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utils.cacheStringData(option.getId16(), tag);
                    }
                }));


        }


        viewList.add(radioGroup);
        linearLayout.addView(radioGroup);
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


    public LinearLayout datePicker(final String tag, String text, final android.support.v4.app.FragmentManager fragmentManager) {
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


        // final ImageView imageView = new ImageView(mContext);
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
                        linearLayout.setTag(item);

                        utils.cacheStringData(item, tag);
                    }
                });
            }
        });

        new FormsUtils(mContext).setImageResource(R.drawable.calendar_32, imageView);

        linearLayout.addView(editText);
        linearLayout.addView(imageView);

        innerViewsList.add(editText);
        viewList.add(linearLayout);
        return linearLayout;
    }


    public ImageView imageView(int resource, final String tag, final CallBack callBack) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(params);
        imageView.getLayoutParams().width = 180;
        imageView.getLayoutParams().height = 180;
        imageView.setTag(tag);
        new FormsUtils(mContext).setImageResource(resource, imageView);

       /* new ImageHandler(mContext); //Clean any residue, Refresh class
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Fso.getImageHandler(mContext)
                            .setImageName(tag)
                            .setImageView(imageView)
                            .setRequest(0)
                            .onImageSaved(new OnImageSelectedSaved() {
                                @Override
                                public void getData(String data) {
                                    imageView.setTag(data);
                                    LOG("Data >> " + data);
                                    if (callBack != null)
                                        callBack.execute(data);
                                }
                            });
                } catch (Exception e) {
                    LOG("Error opening camera");
                }
            }
        });*/

        viewList.add(imageView);
        return imageView;
    }


    public ArrayList<View> getViewList() {
        return viewList;
    }

    public ArrayList<View> getInnerViewsList() {
        return innerViewsList;
    }


}
