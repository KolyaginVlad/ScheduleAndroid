package ru.same.schedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.same.schedule.api.AddAPI;
import ru.same.schedule.api.PostSchedule;
import ru.same.schedule.api.PostSubject;

public interface Api {
    //Получить предметы
    @GET("/api.php")
    Call<PostSubject> getSubjects(@Query("module") String module);

    //Получить расписание на день
    @GET("/api.php")
    Call<PostSchedule> getSchedules(@Query("module") String module, @Query("date")String date);

    //Добавить новый экзамен
    @GET("/api.php")
    Call<AddAPI> addNew(@Query("module") String module,@Query("subject") String subject,
                        @Query("date")String date,
                        @Query("time") String time);
}
