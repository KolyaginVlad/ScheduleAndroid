package ru.same.schedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.same.schedule.api.PostSchedule;
import ru.same.schedule.api.PostSubject;

public class DayPresenter {
    private ViewDay viewDay;
    private String date;

    public DayPresenter(ViewDay viewDay, String date) {
        this.viewDay = viewDay;
        this.date = date;
    }
    //Получаем расписание на день date
    public void getSchedule(){
        viewDay.hideViews();
        viewDay.showProgressBar();
        App.getApi().getSchedules("getSchedule", date).enqueue(new Callback<PostSchedule>() {
            @Override
            public void onResponse(Call<PostSchedule> call, Response<PostSchedule> response) {
                viewDay.showViews();
                viewDay.hideProgressBar();
                if (response.body()!=null&&!response.body().getAnswer().equals("Ошибка"))
                    viewDay.setSchedules(response.body());
                else if(response.body()==null) viewDay.sayAboutError("Ошибка, ничего не " +
                        "вернулось");
                else viewDay.sayAboutError(response.body().getAnswer());

            }

            @Override
            public void onFailure(Call<PostSchedule> call, Throwable t) {
                viewDay.sayAboutError(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public interface ViewDay {
        void showProgressBar();

        void hideProgressBar();

        void hideViews();

        void showViews();

        void setSchedules(PostSchedule postSchedule);

        void sayAboutError(String s);

        void saySuccess(String s);
    }
}
