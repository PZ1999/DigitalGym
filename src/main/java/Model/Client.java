package Model;

import java.util.ArrayList;
import java.util.Date;

public class Client extends User{

    private int rank;//0 for normal, 1 for premium
    private Date premium_end_date;
    private double height;
    private double weight;
    private double waistline;
    private double BMI;
    private double body_fat_rate;
    private String sex;//Male,Female
    private int age;
    private String generic_plan;
    private ArrayList <String> my_course;
    private ArrayList <Live> my_live;

    public Client(String phone_number,String password,String name,String sex) {
        super(phone_number,password,name);
        premium_end_date = new Date();
        setRank(0);
        setSex(sex);
        my_course = new ArrayList<String>();
        my_live = new ArrayList<Live>();
        setGeneric_plan("initial text");
        setHeight(160.0);
        setWeight(50.0);
        setBMI(0.0);
        setBody_fat_rate(0.0);

    }

    public Client(){

    }
    /**
     * need to be finished by WHY --PZ 4.14 2130
     */
    public void generateGeneric_plan(){

            if(BMI < 18.5) {
                generic_plan = "You are to light. Eat more food and do aerobics.";
            }else if(BMI > 24){
                generic_plan = "Make sure to have enough protein and calorie, and keep your regular exercise.";
            }else{
                generic_plan = "You can eat more protein and try more Anaerobic exercise to make your muscle stronger.";
            }
    }
    /**
     * need to be finished by WHY --PZ 4.14 2130
     */
    public void cauculateBMIandBody_fat_rate(){
        BMI = weight/(height*height*0.0001);

        //Body_fat_rate needs yaoWei(cm)//female - 34.89 male-44.74//infer that yaowei is 64

        body_fat_rate = ((1.2*BMI)+(0.23*(double)age)-16.2)/100;
    }
    public void prolongPremium(int month){
        final long MONTH = 3600L *1000*24*30;
        if(!checkPremium()){//expire
            System.out.println("not premium"+month);

            premium_end_date = new Date(((new Date()).getTime()+MONTH*month));
            //premium_end_date = new Date((new Date()).getTime());
            System.out.println(premium_end_date);
        }
        else premium_end_date = new Date(premium_end_date.getTime()+MONTH*month);
        updateRank();
    }
    public void updateRank(){
        if(!checkPremium()) rank = 0;
        else rank=1;
    }

    /**
     * check if client is premium
     * @return TRUE for yes, False for not
     */
    public Boolean checkPremium(){
        System.out.println(new Date());
        System.out.println(premium_end_date);
        return (new Date()).compareTo(premium_end_date)!=1;//expire
    }
    /**
     * need to be finished by PZ --PZ  4.14 2130
     * finished --PZ 4.15 2000
     * @param course_id pk of course
     */
    public void addCourse(String course_id) throws Exception{
        if(my_course.contains(course_id)){
            Exception e = new Exception("courses already exist");
            throw e;
        }
        else{
            my_course.add(course_id);
            //IO.write(new Client(),phone_number);
        }
    }
    /**
     * need to be finished by PZ --PZ  4.14 2130
     * this method
     * @param live live object from DB and added with client_id
     *
     */
    public void addLive(Live live) throws Exception {
        for(Live l : my_live){
            if(live.getCourse_id().equals(l.getCourse_id())){
                Exception e = new Exception("live already exist");
                throw e;
            }
        }
        my_live.add(live);
    }

    @Override
    public String toString() {

       return "Client{" +
                "phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", generic_plan='" + generic_plan + '\'' +
                ", rank=" + rank +
                ", height=" + height +
                ", weight=" + weight +
                ", premium_end_date=" + premium_end_date +
                ", my_course=" + my_course +
                ", my_live=" + my_live +
                '}';
    }




    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Date getPremium_end_date() {
        return premium_end_date;
    }

    public void setPremium_end_date(Date premium_end_date) {
        this.premium_end_date = premium_end_date;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public double getBody_fat_rate() {
        return body_fat_rate;
    }

    public void setBody_fat_rate(double body_fat_rate) {
        this.body_fat_rate = body_fat_rate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGeneric_plan() {
        return generic_plan;
    }

    public void setGeneric_plan(String generic_plan) {
        this.generic_plan = generic_plan;
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

    public double getWaistline() {
        return waistline;
    }

    public void setWaistline(double waistline) {
        this.waistline = waistline;
    }
}