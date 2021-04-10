package ru.same.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.same.schedule.api.PostSubject;


public class MainActivity extends AppCompatActivity implements Presenter.ViewMain{
    private Toolbar toolbar;
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
        toolbar = findViewById(R.id.toolbar);
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


    //Добавить новый экзамен
    public void addNew(View view){
        String[] dateInMass = date.getText().toString().split(" ");
        String onlyDate = dateInMass[0];
        String onlyTime = dateInMass[1];
        presenter.addNew(subjects.getSelectedItem().toString(), onlyDate, onlyTime );
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
    public void saySuccess() {
        Toast.makeText(this, "Успешно", Toast.LENGTH_LONG).show();
    }
}