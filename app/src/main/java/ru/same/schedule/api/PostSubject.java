
package ru.same.schedule.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostSubject {

    @SerializedName("answer")
    @Expose
    private List<String> answer = null;

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

}
