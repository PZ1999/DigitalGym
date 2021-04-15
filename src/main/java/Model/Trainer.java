package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Trainer extends User{
    HashSet <Date> occupation;
    ArrayList <String> my_course;
    ArrayList <Live> my_live;

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
     * @param course
     */
    public void addCourse(Course course){

    }
    public void deleteCourse(String course_id){

    }

    /**
     * add a live subscribed by client to trainer
     * @param live
     */
    public void addLive(Live live) throws Exception {
        for(Live l : my_live){
            if(live.getCourse_id().equals(live.getCourse_id())){
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

}
