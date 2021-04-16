package Model;

import java.util.ArrayList;

public class GeneralCourse {


    protected String course_id;//pk
    protected String name;
    protected String trainer_id;//pk for trainer
    protected String trainer;
    protected String type;
    protected String info;
    protected ArrayList <String> plan;//daily plan,normal String
    protected int rank;



    protected double price;
    public GeneralCourse(String course_id,String name,String trainer_id,String trainer,String type,String info,int rank,double price){
        setCourse_id(course_id);
        setCourse_id(name);
        setTrainer_id(trainer_id);
        setTrainer(trainer);
        setType(type);
        setInfo(info);
        setRank(rank);
        setPrice(price);
        setPlan(new ArrayList<String>());
    }

    public GeneralCourse() {

    }

    public void setPlan(ArrayList<String> plan) {
        this.plan = plan;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setCourse_id(String course_id){
        this.course_id = course_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTrainer_id(String trainer_id){
        this.trainer_id = trainer_id;
    }
    public void setTrainer(String trainer){
        this.trainer = trainer;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getCourse_id() {
        return course_id;
    }

    public String getName() {
        return name;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public ArrayList<String> getPlan() {
        return plan;
    }

    public int getRank() {
        return rank;
    }

    public double getPrice() {
        return price;
    }
    public void setRank(int rank){
        this.rank = rank;
    }

}
