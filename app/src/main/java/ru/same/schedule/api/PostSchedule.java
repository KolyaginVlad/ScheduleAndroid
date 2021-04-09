
package ru.same.schedule.api;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostSchedule {

    @SerializedName("time")
    @Expose
    private List<String> time = null;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;
    @SerializedName("specialities")
    @Expose
    private List<String> specialities = null;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

}
