package com.kode.formscreatorlib.Engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import com.kode.formscreatorlib.R;
import com.kode.formscreatorlib.Utils.DatePickerClass;
import com.kode.formscreatorlib.Utils.FormsUtils;

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
    private Button button;


    public ViewsCreator(Activity context) {
        this.mContext = context;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(16, 16, 16, 16);
        spinnerItem = "233";
        viewList = new ArrayList<>();
        innerViewsList = new ArrayList<>();
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


    public EditText editText(String hint, String type, String tag) {
        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setInputType(getType(type));
        if (type.equalsIgnoreCase("1"))
            editText.setKeyListener(DigitsKeyListener.getInstance("0123456789-."));
        editText.setHint(hint);
        editText.setTag(tag);
        editText.setTextSize(14f);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        viewList.add(editText);
        return editText;
    }


    public EditText textArea(String hint, String tag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
        params.setMargins(16, 16, 16, 16);

        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);

        editText.setHint(hint);
        editText.setTag(tag);
        editText.setTextSize(14f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(mContext.getResources().getDrawable(R.drawable.white_smoke_square_bg));
        }
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));

        viewList.add(editText);
        return editText;
    }


    public EditText editText(String hint, int type, boolean enable) {
        EditText editText = new EditText(mContext);
        editText.setLayoutParams(params);
        editText.setInputType(getType(type));
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setEnabled(enable);
        editText.setTextSize(14f);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        viewList.add(editText);
        return editText;
    }


    public Button button(String text) {
        Button button = new Button(mContext);
        button.setText(text);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setBackgroundColor(mContext.getResources().getColor(R.color.black));
       // button.setFocusBackgroundColor(mContext.getResources().getColor(R.color.blue));
        //button.setRadius(30);
        viewList.add(button);
        return button;
    }

    public Button button(String text, int color) {
        Button button = new Button(mContext);
        button.setText(text);
        button.setLayoutParams(params);
        button.setTextSize(16);
        button.setBackgroundColor(color);
       // button.setFocusBackgroundColor(mContext.getResources().getColor(R.color.blue));
       // button.setRadius(30);
        viewList.add(button);
        return button;
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

    public RadioGroup radioGroup(ArrayList<String> list, String tag) {
        final RadioGroup radioGroup = new RadioGroup(mContext);
        radioGroup.setLayoutParams(params);
        radioGroup.setTag(tag);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);

        if (list != null && list.size() != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            //  params.setMargins(16, 16, 16, 16);

            for (String str : list) {
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(String.format("%s     ", str));
                radioButton.setLayoutParams(params);
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        radioGroup.setTag(radioButton.getText().toString());
                      //  Fso.getTransaction().setGender(radioButton.getText().toString());
                    }
                });
                radioGroup.addView(radioButton);
            }
        }

        viewList.add(radioGroup);
        return radioGroup;
    }

    public LinearLayout datePicker(final FragmentManager fragmentManager, String tag, final CallBack callBack) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setTag(tag);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_HORIZONTAL;
        //  p.setMargins(16, 16, 16, 16);

        //  LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 7f);
        // p1.gravity = Gravity.CENTER_HORIZONTAL;
        // p1.setMargins(16, 16, 16, 16);

        final EditText editText = new EditText(mContext);
        editText.setEnabled(false);
        editText.setHint("dd/MM/yyyy");
        editText.setTextSize(14f);
        editText.setTag(tag);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black_eel));
        editText.setSingleLine(true);
        editText.setSingleLine();
        editText.setLayoutParams(p);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView = (ImageView) inflater.inflate(R.layout.theme_pimageview, null);
        imageView.setPadding(2, 2, 2, 2);

        LinearLayout.LayoutParams pImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        pImage.weight = 1f;
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
                        if (callBack != null)
                            callBack.execute(item);
                    }
                });
            }
        });

       /* Glide.with(mContext)
                .load("")
                .placeholder(R.drawable.calendar_32)
                .into(imageView);*/

       new FormsUtils(mContext).setImageResource(R.drawable.calendar_icon, imageView);

        linearLayout.addView(editText);
        linearLayout.addView(imageView);

        innerViewsList.add(editText);
        viewList.add(linearLayout);
        return linearLayout;
    }


    public LinearLayout phoneNumberPicker(String hint) {

        TableRow.LayoutParams p = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1f);
        p.gravity = Gravity.CENTER_VERTICAL;

        TableRow.LayoutParams param = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT, 4f);
        param.gravity = Gravity.CENTER_HORIZONTAL;


        final LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setTag(hint);

        EditText editText = new EditText(mContext);
        editText.setHint(hint);
        editText.setTag(hint);
        editText.setTextSize((float) 14);
        editText.setHintTextColor(mContext.getResources().getColor(R.color.transparent_black_hex_5));
        editText.setTextColor(mContext.getResources().getColor(R.color.black));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setLayoutParams(p);

        ArrayList<String> list = new ArrayList<>();
        list.add("233");

        SearchingSpinner spinner = new SearchingSpinner(mContext);
        spinner.addEntries(list);
        spinner.setTitle("Search here");
        spinner.setLayoutParams(param);
        spinner.setAcceptLocalEntries(true);
        spinner.setLocalEntriesAddable(true);
        spinner.setItemOnClickDismissDialog(true);

        setListener(editText, linearLayout);
        getSpinnerItem(spinner);

        linearLayout.addView(spinner);
        linearLayout.addView(editText);

        viewList.add(linearLayout);
        innerViewsList.add(editText);
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


   /* public ImageView signature(final String tag) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.setMargins(16, 16, 16, 16);

        final ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(params);
        imageView.getLayoutParams().width = 180;
        imageView.getLayoutParams().height = 180;
        imageView.setTag("TAG#" + tag);
        new FormsUtils(mContext).setImageResource(R.drawable.calendar_icon, imageView);
        new GeneralFunctions(mContext).setImageResource(R.drawable.signature, imageView);

        new ImageHandler(mContext); //Clean any residue, Refresh class
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SignatureDialog(mContext).setSignatureName(tag).setImageView(imageView);
            }
        });

        viewList.add(imageView);
        return imageView;
    }
*/

    private void getSpinnerItem(final SearchingSpinner spinner) {
        spinner.setOnItemSelectedListener(new com.jamper.searchingspinner.Logic.OnItemSelected() {
            @Override
            public void onItemSelected(String s, int i) {
                spinnerItem = s;
            }

            @Override
            public void onNothingSelected(String s) {
                spinnerItem = s;
            }
        });
    }

    private void setListener(final EditText editText, final LinearLayout linearLayout) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String customPhone = getPhoneNumber(editText.getText().toString());
                    if (linearLayout != null)
                        linearLayout.setTag(customPhone);
                    // if (editText != null)
                    //     editText.setTag(customPhone);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String customPhone = getPhoneNumber(editText.getText().toString());
                if (linearLayout != null)
                    linearLayout.setTag(customPhone);
                // if (editText != null)
                //      editText.setTag(customPhone);
            }
        });
    }

    /**
     * Return the Formatted Phone Number beginning with the country code
     **/
    private String getPhoneNumber(String phone) {
        if (phone != null)
            if (!TextUtils.isEmpty(phone)) {
                if (phone.startsWith("0") && phone.length() == 10) {
                    return spinnerItem + phone.substring(phone.length() - 9);
                } else if (!phone.startsWith("0") && phone.length() <= 9) {
                    return spinnerItem + phone;
                } else if (phone.startsWith(spinnerItem) && phone.length() == 12) {
                    return phone;
                }

            }
        return phone;
    }



    public CheckBox checkBox(String text, final CallBack callBack) {
        CheckBox checkBox = new CheckBox(mContext);

        if (!TextUtils.isEmpty(text)) {
            checkBox.setLayoutParams(params);
            checkBox.setText(text);
            checkBox.setTag(text);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (callBack != null)
                            callBack.execute("true");
                    } else {
                        if (callBack != null)
                            callBack.execute("false");
                    }
                }
            });
        }

        viewList.add(checkBox);
        return checkBox;
    }


    public LinearLayout checkBox(ArrayList<String> list, final CallBack callBack) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        if (list == null)
            return linearLayout;

        if (list.size() > 0) {
            CheckBox checkBox;

            for (String str : list) {
                checkBox = new CheckBox(mContext);
                checkBox.setLayoutParams(params);
                checkBox.setText(str);
                checkBox.setTag(str);
                final CheckBox finalCheckBox = checkBox;
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            if (callBack != null)
                                callBack.execute(finalCheckBox.getText().toString() + "#" + b);
                        } else {
                            if (callBack != null)
                                callBack.execute(finalCheckBox.getText().toString() + "#" + b);
                        }
                    }
                });
                innerViewsList.add(checkBox);
                linearLayout.addView(checkBox);
            }
        }

        viewList.add(linearLayout);
        return linearLayout;
    }


    public ArrayList<View> getViewList() {
        return viewList;
    }

    public ArrayList<View> getInnerViewsList() {
        return innerViewsList;
    }

   /* public View cardMenu(String mainHead, String subHead, int resource, String tag) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.cardview_layout, null, false);
        try {
            TextView mainHeading = (TextView) v.findViewById(R.id.mainHead);
            TextView subHeading = (TextView) v.findViewById(R.id.subHeading);
            ImageView image = (ImageView) v.findViewById(R.id.image);

            mainHeading.setText(mainHead);
            subHeading.setText(subHead);

            image.setImageBitmap(CompressImage.decodeSampledBitmapFromResource(mContext.getResources(), resource, 100, 100));

            v.setTag(tag);
            v.setLayoutParams(params);
        } catch (Exception e) {
        }

        viewList.add(v);
        return v;
    }*/


}
