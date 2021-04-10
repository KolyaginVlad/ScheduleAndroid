package ru.same.schedule;

import android.graphics.Bitmap;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.same.schedule.api.GetAdd;
import ru.same.schedule.api.PostSubject;

public class Presenter {
    private ViewMain viewMain;

    public Presenter(ViewMain viewMain) {
        this.viewMain = viewMain;
    }

    //Получить список предметов
    public void getSubjects(){
        viewMain.hideViews();
        viewMain.showProgressBar();
        App.getApi().getSubjects("getSubjects").enqueue(new Callback<PostSubject>() {
            @Override
            public void onResponse(Call<PostSubject> call, Response<PostSubject> response) {
                viewMain.hideProgressBar();
                viewMain.showViews();
                if (response.body()!=null&&!response.body().getAnswer().equals("Ошибка"))
                viewMain.setSubjects(response.body());
                else if(response.body()==null) viewMain.sayAboutError("Ошибка, ничего не " +
                        "вернулось");
                else viewMain.sayAboutError(response.body().getAnswer());
            }

            @Override
            public void onFailure(Call<PostSubject> call, Throwable t) {
                viewMain.sayAboutError(t.getMessage());
                t.printStackTrace();

            }
        });
    }

    //Добавить новый экзамен
    public void addNew(String subject, String date, String time){
        App.getApi().addNew("add", subject, date, time).enqueue(new Callback<GetAdd>() {
            @Override
            public void onResponse(Call<GetAdd> call, Response<GetAdd> response) {
                if (response.body()!=null&&!response.body().getAnswer().equals("Ошибка"))
                    viewMain.saySuccess(response.body().getAnswer());
                else if(response.body()==null) viewMain.sayAboutError("Ошибка, ничего не " +
                        "вернулось");
                else viewMain.sayAboutError(response.body().getAnswer());

            }

            @Override
            public void onFailure(Call<GetAdd> call, Throwable t) {
                viewMain.sayAboutError(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public interface ViewMain {
        void showProgressBar();

        void hideProgressBar();

        void hideViews();

        void showViews();

        void setSubjects(PostSubject postSubject);

        void sayAboutError(String s);

        void saySuccess(String s);
    }
}
