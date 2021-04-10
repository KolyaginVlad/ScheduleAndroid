package ru.same.schedule.api;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostSubject {

    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

}