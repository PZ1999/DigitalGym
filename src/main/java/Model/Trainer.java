package Model;

import java.util.*;

public class Trainer extends User{
    private HashSet <Date> occupation;
    private ArrayList <String> my_course;
    private ArrayList <Live> my_live;

    public Trainer(String phone_number, String password, String name) {
        super(phone_number, password, name);
        occupation = new HashSet<Date>();
        my_course = new ArrayList<String>();
        my_live = new ArrayList<Live>();

    }

    public Trainer() {

    }

    public HashSet<Date> getOccupation() {
        return occupation;
    }

    public void setOccupation(HashSet<Date> occupation) {
        this.occupation = occupation;
    }

    public ArrayList<String> getMy_course() {
        return my_course;
    }

    public void setMy_course(ArrayList<String> my_course) {
        this.my_course = my_course;
    }

    public ArrayList<Live> getMy_live() {
        return my_live;
    }

    public void setMy_live(ArrayList<Live> my_live) {
        this.my_live = my_live;
    }

    /**
     * later --PZ
     * done --PZ
     * @param course
     */
    public void addCourse(Course course){
        this.my_course.add(course.getCourse_id());
    }
    public void deleteCourse(String course_id){

    }

    /**
     * add a live subscribed by client to trainer
     * @param live
     */
    public void addLive(Live live) throws Exception {
        for(Live l : my_live){
            if(l.getCourse_id().equals(live.getCourse_id())){
                if(l.getClient_id().equals(live.getClient_id())){
                    Exception e = new Exception("live already subscribed by this client");
                    throw e;
                }
            }
        }
        my_live.add(live);
    }
    public void deleteLive(){

    }

    @Override
    public String toString() {
        return "Trainer{" +
                "occupation=" + occupation +
                ", my_course=" + my_course +
                ", my_live=" + my_live +
                ", phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
    /**
     * used to mark a live session as finished
     * @param live_plan
     */
    public void finishLiveSession(LivePlan live_plan) {
        for(int i=0;i<my_live.size();i++){
            if(my_live.get(i).getCourse_id().equals(live_plan.getCourse_id())){
                int index = my_live.get(i).getLive_plan().indexOf(live_plan);
                //live_plan.setFinish(true);
                my_live.get(i).getLive_plan().get(index).setFinish(true);
            }
        }
    }
}
