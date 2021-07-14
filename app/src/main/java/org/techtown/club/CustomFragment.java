package org.techtown.club;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomFragment extends Fragment {

    EditText name;
    EditText id;
    EditText pw;
    EditText pwcheck;
    Button birthday;
    EditText phonenumber;
    EditText phonenumbercheck;

    Date curDate = new Date();
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    String result = dataFormat.format(curDate);

    public CustomFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_custom, container, false);
        name = view.findViewById(R.id.nameText);
        id = view.findViewById(R.id.idText);
        pw = view.findViewById(R.id.passwordText);
        pwcheck = view.findViewById(R.id.passwordcheckText);
        phonenumber = view.findViewById(R.id.numberText);
        phonenumbercheck = view.findViewById(R.id.numberCheck);

        birthday = view.findViewById(R.id.button);
        birthday.setText(result);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        return view;
    }

    private void showDateDialog(){

        Calendar calendar = Calendar.getInstance();
        try {
            curDate = dataFormat.parse(birthday.getText().toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(curDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),  birthDateSetListener,  curYear, curMonth, curDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, month);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, day);

            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        String selectedDateStr = dataFormat.format(curDate);
        birthday.setText(selectedDateStr);
    }

}