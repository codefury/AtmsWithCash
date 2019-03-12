package com.codefury.atmswithcash;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by codefury on 11/15/16.
 */
public class ReportATMFragment extends Fragment {

    String title;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String currentDateAndTime;

    public ReportATMFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_atm_fragment, container, false);

        ViewGroup layout = v.findViewById(R.id.main_layout);
        LayoutTransition layoutTransition = layout.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);

        ViewGroup layout2 = v.findViewById(R.id.internal_layout);
        LayoutTransition layoutTransition2 = layout2.getLayoutTransition();
        layoutTransition2.enableTransitionType(LayoutTransition.CHANGING);

        title = getArguments().getString("TITLE");

        TextView titleTV = v.findViewById(R.id.title);

        titleTV.setText(title);

        currentDateAndTime = new SimpleDateFormat("hh:mm a dd-MM-yyyy").format(new Date());

        final TextView time = v.findViewById(R.id.timeStamp);

        time.setText("Reporting time: " + currentDateAndTime);

        v.findViewById(R.id.changeTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                currentDateAndTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " ";
                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {

                                                currentDateAndTime += hourOfDay + ":" + minute;
                                                time.setText("Reporting time: " + currentDateAndTime);

                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        final TextView approxText = v.findViewById(R.id.approx_text);
        approxText.setVisibility(View.GONE);

        final RadioGroup approxGroup = v.findViewById(R.id.queue_group);
        approxGroup.setVisibility(View.GONE);

        RadioGroup workingRGroup = v.findViewById(R.id.working_group);
        RadioButton checkedRadioButton = workingRGroup.findViewById(workingRGroup.getCheckedRadioButtonId());
        workingRGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radio button that has changed in check state is now checked...
                if (isChecked) {
                    switch (checkedRadioButton.getId()) {
                        case R.id.wg1:
                            approxText.setVisibility(View.VISIBLE);
                            approxGroup.setVisibility(View.VISIBLE);
                            break;
                        case R.id.wg2:
                            approxText.setVisibility(View.GONE);
                            approxGroup.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        approxGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radio button that has changed in check state is now checked...
                if (isChecked) {
                    switch (checkedRadioButton.getId()) {
                        case R.id.qg1:

                            break;
                        case R.id.qg2:

                            break;
                        case R.id.qg3:

                            break;
                    }
                }
            }
        });
        v.findViewById(R.id.cancel_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        v.findViewById(R.id.submit_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //API CALL
                Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
