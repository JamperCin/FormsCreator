package com.kode.formscreatorlib.Utils;

import android.text.TextUtils;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.kode.formscreatorlib.Callbacks.OnDateSet;
import com.kode.formscreatorlib.Callbacks.OnItemSelected;
import com.kode.formscreatorlib.R;

import java.util.Calendar;

import static com.kode.formscreatorlib.Utils.FormsUtils.LOG;


/**
 * Created by jamper on 12/15/2017.
 */

public class DatePickerClass implements CalendarDatePickerDialogFragment.OnDateSetListener, OnDateSet {
    android.support.v4.app.FragmentManager fragmentManager;
    private Calendar cal;
    private  int day,selectDay;
    private  int month,selectMonth;
    private  int year,selectYear;
    OnItemSelected onItemSelected;
    private String selectedDate = "";


    public DatePickerClass(android.support.v4.app.FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        setUpCalendar();
    }


    private void setUpCalendar() {
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        month++;
    }


    /**OnClicking the calender button, call this function**/
    public DatePickerClass openCalendarDatePicker() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(year, month, day)
                .setOnDateSetListener(this)
                .setDoneText("OK")
                .setCancelText("CANCEL");
        cdp.show(fragmentManager, "datePicker");
        return this;
    }


    public DatePickerClass pickDate(){
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(fragmentManager)
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setYearOptional(true);
        dpb.show();
        return this;
    }


    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int selectedYear, int selectedMonth, int selectedDay) {
        formatDate(selectedYear,selectedMonth,selectedDay);
    }


    private void formatDate(int selectedYear, int selectedMonth, int selectedDay){
        selectYear = selectedYear;
        selectMonth = selectedMonth + 1;
        selectDay = selectedDay;


        String newDay = selectedDay + "";
        String newMonth = selectMonth + "";

        if (newDay.length() == 1) {
          StringBuilder str =  new StringBuilder().append("0" + selectDay).append("/").append(selectMonth).append("/")
                  .append(selectYear);
          selectedDate = String.valueOf(str);
        }

        if (newMonth.length() == 1) {
            StringBuilder str =  new StringBuilder().append(selectDay).append("/").append("0" + (selectMonth)).append("/")
                    .append(selectYear);
            selectedDate = String.valueOf(str);
        }

        if (newDay.length() == 1 && newMonth.length() == 1) {
            StringBuilder str =  new StringBuilder().append("0" + selectDay).append("/").append("0" + (selectMonth)).append("/")
                    .append(selectYear);
            selectedDate = String.valueOf(str);
        }

        if (newDay.length() > 1 && newMonth.length() > 1) {
            StringBuilder str =  new StringBuilder().append(selectDay)
                    .append("/").append(selectMonth).append("/").append(selectYear);
            selectedDate = String.valueOf(str);
        }

        onDateSelected(onItemSelected);
    }


    @Override
    public void onDateSelected(OnItemSelected onItemSelected) {
        DatePickerClass.this.onItemSelected = onItemSelected;
        if (onItemSelected != null && !TextUtils.isEmpty(selectedDate))
            onItemSelected.onItemSelected(selectedDate);
        else LOG("Callback i null");

    }

}
