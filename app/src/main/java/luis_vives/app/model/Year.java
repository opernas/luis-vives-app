package luis_vives.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Year {

    @SerializedName("year")
    @Expose
    private Year_ year;

    public Year_ getYear() {
        return year;
    }

    public void setYear(Year_ year) {
        this.year = year;
    }

}