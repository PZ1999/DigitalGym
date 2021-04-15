package Model;

import java.util.ArrayList;
import java.util.Date;

public class LivePlan {

    private String live_url;
    private Date live_start_Date;//null before book
    private String personal_plan;

    public LivePlan() {
        setLive_url("initial url");
        setLive_start_Date(null);
        setPersonal_plan("initial text");

    }
    public String toString(){
        return "use IO.printObject instead.";
    }

    public String getLive_url() {
        return live_url;
    }

    public void setLive_url(String live_url) {
        this.live_url = live_url;
    }

    public Date getLive_start_Date() {
        return live_start_Date;
    }

    public void setLive_start_Date(Date live_start_Date) {
        this.live_start_Date = live_start_Date;
    }

    public String getPersonal_plan() {
        return personal_plan;
    }

    public void setPersonal_plan(String personal_plan) {
        this.personal_plan = personal_plan;
    }
}
