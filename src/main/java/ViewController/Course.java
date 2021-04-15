package ViewController;

import java.util.ArrayList;

/**
 * This is a class for virtual classes
 */
public class Course {
    public String name;
    public String trainer;
    public int day;
    public ArrayList <String> plan;
    public Course(String name,String trainer,int day,ArrayList <String> plan){
        this.name = name;
        this.trainer = trainer;
        this.day = day;
        this.plan = plan;
    }
    String getIntro(){
        return name+"\n"+trainer+"\n"+plan.size()+"days\n";
    }

    /**
     * return plan of xth day
     * @param x
     * @return plan of xth day
     */
    String getplan(int x){
        return plan.get(x);
    }
}
