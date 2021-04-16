package Model;

import java.util.ArrayList;

public class Live extends GeneralCourse{
    private ArrayList<LivePlan> live_plan;
    private String client_id;

    public Live(String course_id, String name, String trainer_id, String trainer, String type, String info, int rank, double price, ArrayList<LivePlan> live_plan, String client_id) {
        super(course_id, name, trainer_id, trainer, type, info, rank, price);
        this.live_plan = live_plan;
        this.client_id = client_id;
    }

    public Live() {

    }

    public ArrayList<LivePlan> getLive_plan() {
        return live_plan;
    }

    public void setLive_plan(ArrayList<LivePlan> live_plan) {
        this.live_plan = live_plan;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "use IO.printObject instead.";
    }
}
