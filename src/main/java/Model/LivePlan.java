package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class LivePlan {

    private String live_url;
    private Date live_start_Date;//null before book
    private String personal_plan;
    private String course_id;
    private String client_id;
    private String trainer_id;
    private Boolean finish;//false before live , true after live.

    public LivePlan(String course_id,String client_id,String trainer_id) {
        setCourse_id(course_id);
        setLive_url("initial url");
        setLive_start_Date(null);
        setPersonal_plan("initial text");
        this.client_id = client_id;
        this.trainer_id = trainer_id;
        this.finish = false;
    }
    public LivePlan()
    {

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

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivePlan livePlan = (LivePlan) o;
        return Objects.equals(getLive_url(), livePlan.getLive_url()) && Objects.equals(getLive_start_Date(), livePlan.getLive_start_Date()) && Objects.equals(getPersonal_plan(), livePlan.getPersonal_plan()) && Objects.equals(getCourse_id(), livePlan.getCourse_id()) && Objects.equals(getClient_id(), livePlan.getClient_id()) && Objects.equals(getFinish(), livePlan.getFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLive_url(), getLive_start_Date(), getPersonal_plan(), getCourse_id(), getClient_id(), getFinish());
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    @Override
    public String toString() {
        return "LivePlan{" +
                "live_url='" + live_url + '\'' +"\n"+
                ", live_start_Date=" + live_start_Date +"\n"+
                ", personal_plan='" + personal_plan + '\'' +"\n"+
                ", course_id='" + course_id + '\'' +"\n"+
                ", client_id='" + client_id + '\'' +"\n"+
                ", trainer_id='" + trainer_id + '\'' +"\n"+
                ", finish='" + finish + '\'' +"\n"+
                '}';
    }
}
