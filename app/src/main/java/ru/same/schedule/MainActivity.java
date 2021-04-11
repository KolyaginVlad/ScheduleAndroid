package ru.same.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ru.same.schedule.api.PostSubject;


public class MainActivity extends AppCompatActivity implements Presenter.ViewMain{
    private CalendarView calendar;
    private Spinner subjects;
    private TextView date;
    private Presenter presenter;
    private ProgressBar progressBar;
    private Button addButton;
    private TextView addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        calendar = findViewById(R.id.calendar);
        subjects = findViewById(R.id.subjects);
        date = findViewById(R.id.date);
        progressBar = findViewById(R.id.proBar);
        addButton = findViewById(R.id.addButton);
        addText = findViewById(R.id.textView);
        setSupportActionBar(toolbar);
        //Получаем разрешение на использование интернета
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                    1);
        } else {
            presenter = new Presenter(this);
            presenter.getSubjects();

        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDatePicker();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year,
                                            int monthOfYear,
                                            int dayOfMonth) {
                String editTextDateParam =
                        dayOfMonth  + "-" + (monthOfYear + 1) + "-" + year;
                    Intent intent = new Intent(MainActivity.this, DayActivity.class);
                    intent.putExtra("day", editTextDateParam);
                    startActivity(intent);
            }
        });
    }

    // Получаем подтверждение до того момента пока пользователь их не выдаст
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter = new Presenter(this);
                    presenter.getSubjects();
                } else {
                    ActivityCompat
                            .requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                                    1);
                }

        }
    }

    private void callTimePicker() {
        // получаем текущее время
        final Calendar cal = Calendar.getInstance();
        int mHour = cal.get(Calendar.HOUR_OF_DAY);
        int mMinute = cal.get(Calendar.MINUTE);

        // инициализируем диалог выбора времени текущими значениями
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String editTextTimeParam = hourOfDay + ":" + minute;
                        date.setText(date.getText()+" "+editTextTimeParam);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam =
                                dayOfMonth  + "." + (monthOfYear + 1) + "." + year;
                        date.setText(editTextDateParam);
                        callTimePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    //Добавить новый экзамен
    public void addNew(View view) throws ParseException {
        if (date.getText().equals("дд.мм.гггг --:--")||date.getText().toString().split(" ").length!=2){
            Toast.makeText(this, "Введите дату и время!", Toast.LENGTH_LONG).show();
        }else {
            String dateRaw = date.getText().toString();
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy H:m");

            Date date0 = oldDateFormat.parse(dateRaw);
            String resultDate = newDateFormat.format(date0);

            String[] dateInMass = resultDate.split(" ");
            presenter.addNew(subjects.getSelectedItem().toString(), dateInMass[0], dateInMass[1]);
        }
    }

    @Override
    public void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void hideViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calendar.setVisibility(View.INVISIBLE);
                subjects.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
                addButton.setVisibility(View.INVISIBLE);
                addText.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void showViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calendar.setVisibility(View.VISIBLE);
                subjects.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);
                addText.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void setSubjects(PostSubject postSubject) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, postSubject.getSubjects());
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjects.setPrompt("Выберите предмет");
                subjects.setAdapter(arrayAdapter);
            }
        });

    }

    @Override
    public void sayAboutError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saySuccess(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}