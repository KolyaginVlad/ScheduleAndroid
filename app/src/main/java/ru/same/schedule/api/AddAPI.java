
package ru.same.schedule.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddAPI {

    @SerializedName("answer")
    @Expose
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
