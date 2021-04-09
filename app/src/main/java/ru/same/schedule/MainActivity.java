package ru.same.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        calendar = findViewById(R.id.calendar);
        setSupportActionBar(toolbar);
    }

    //Добавить новый экзамен
    public void addNew(View view){

    }
}