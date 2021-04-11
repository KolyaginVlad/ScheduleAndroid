package ru.same.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ru.same.schedule.api.PostSchedule;

public class DayActivity extends AppCompatActivity implements DayPresenter.ViewDay {
    private TableLayout mTableLayout;
    private ProgressBar progressBar;
    private TextView day;
    private DayPresenter dayPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        Toolbar toolbar = findViewById(R.id.toolbarDay);
        mTableLayout = findViewById(R.id.table);
        progressBar = findViewById(R.id.progressBar);
        day = findViewById(R.id.day);
        setSupportActionBar(toolbar);
        Intent intentDay = getIntent();
        day.setText("Расписание экзаменов на "+ intentDay.getStringExtra("day"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dayPresenter = new DayPresenter(this, intentDay.getStringExtra("day"));
        dayPresenter.getSchedule();
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
                mTableLayout.setVisibility(View.INVISIBLE);
                day.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void showViews() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTableLayout.setVisibility(View.VISIBLE);
                day.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void setSchedules(PostSchedule postSchedule) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <postSchedule.getTime().size() ; i++) {
                    TableRow tableRow = new TableRow(getApplicationContext());
                    TextView time = new TextView(getApplicationContext());
                    TextView subject = new TextView(getApplicationContext());
                    TextView speciality = new TextView(getApplicationContext());
                    time.setText(postSchedule.getTime().get(i));
                    subject.setText(postSchedule.getSubjects().get(i));
                    speciality.setText(postSchedule.getSpecialities().get(i));
                    tableRow.addView(time);
                    tableRow.addView(subject);
                    tableRow.addView(speciality);
                    mTableLayout.addView(tableRow);
                }
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