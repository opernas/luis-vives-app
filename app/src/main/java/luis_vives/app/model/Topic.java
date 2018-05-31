package luis_vives.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topic {

    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("years")
    @Expose
    private List<Year> years = null;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }

}
