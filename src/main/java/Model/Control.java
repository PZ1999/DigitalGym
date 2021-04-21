package Model;

import ViewController.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import Model.*;

public class Control {
    /**
     * This method is called when login button clicked
     * @author JXY
     * need to modified later--PZ
     * @param name
     * @param password
     * @return "no user"
     */
    static String login(String name,String password) throws IOException {
        Client client = (Client)IO.read(new Client(),name);
        if(client==null) return null;
        else return "Client";
    }

    /**
     * @author PZ
     * This method used to get all courses from IO
     * @return list of course
     */
    public static ArrayList<Course> getAllCourses() throws IOException {
        ArrayList<Course> courses = IO.showAllCourse();
        return courses;
    }
    /**
     *
     * This method used to get all lives from IO
     * @return list of course
     * @author PZ
     */
    public static ArrayList<Live> getAllLives() throws IOException {
        ArrayList<Live> lives = IO.showAllLive();
        return lives;
    }

    /**
     *
     * @author PZ
     * @param client client which requesting his courses
     * @return list of client's courses
     */
    public static ArrayList<Course> getClientCourses(Client client) throws IOException {
        ArrayList <Course> courses = new ArrayList<Course>();
        for(String s:client.getMy_course())
            courses.add((Course)IO.read(new Course(),s));
        return courses;

    }

    /**
     * This method will remove a subscribed course from the client
     * @author PZ
     * @param phone_number
     * @param course_id
     */
    public static void deleteClientCourse(String phone_number, String course_id) throws IOException {
        Client client = (Client)IO.read(new Client(),phone_number);
        for(int i=0;i<client.getMy_course().size();i++){
            if(client.getMy_course().get(i).equals(course_id)){//find
                System.out.println("target course found, deleting.");
                client.getMy_course().remove(i);//delete
            }
        }
        IO.write(client,client.getPhone_number());
    }

    /**
     * This method delete live subscription from client side and trainer side
     * @param client_id
     * @param live
     * @throws IOException
     */
    public static void deleteClientLive(String client_id, Live live) throws IOException {
        System.out.println("deleting live"+live.getCourse_id()+" from client_id");
        Client client = (Client)IO.read(new Client(),client_id);
        Trainer trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
        for(int i=0;i<client.getMy_live().size();i++)
            if(client.getMy_live().get(i).getCourse_id().equals(live.getCourse_id()))
                client.getMy_live().remove(i);//delete from client
        for(int i=0;i<trainer.getMy_live().size();i++)
            if(trainer.getMy_live().get(i).getCourse_id().equals(live.getCourse_id())&&trainer.getMy_live().get(i).getClient_id().equals(client_id))
                trainer.getMy_live().remove(i);//delete from trainer
        IO.write(client,client_id);
        IO.write(trainer,trainer.getPhone_number());
    }

    /**
     * THis method will return avaliable time slots according to trainers' occupation.
     * @author PZ
     * @param trainer_id live's trainer_id
     * @param date1
     * @param date2
     * @param date3
     * @param date4
     * @return
     * @throws IOException
     */
    public static ArrayList<String> findAvaliableBookTimeSlot(String trainer_id,Date date1, Date date2, Date date3, Date date4) throws IOException {
        ArrayList<String> s = new ArrayList<String>();
        Trainer trainer = (Trainer) IO.read(new Trainer(),trainer_id);
        if(!trainer.getOccupation().contains(date1)) s.add("8:00 ~ 10:00");
        if(!trainer.getOccupation().contains(date2)) s.add("10:00 ~ 12:00");
        if(!trainer.getOccupation().contains(date3)) s.add("13:00 ~ 15:00");
        if(!trainer.getOccupation().contains(date4)) s.add("15:00 ~ 17:00");
        return s;
    }

    /**
     * This method will add a live booking information to trainer and client
     * @author PZ
     * @param live target live from client object
     * @param session day of the plan
     * @param date date of book
     * @param timeSlot time of book
     * @throws IOException
     */
    public static void bookLiveSession(Live live, int session, LocalDate date, String timeSlot) throws Exception{


        Trainer trainer = (Trainer) IO.read(new Trainer(),live.getTrainer_id());
        Client client = (Client) IO.read(new Client(),live.getClient_id());

        int index1 = -1,index2 = -1;//locate live in trainer & client
        for(int i=0;i<client.getMy_live().size();i++){//find target live in client
            if(live.getCourse_id().equals(client.getMy_live().get(i).getCourse_id()))
                index1 = i;
        }



        for(int i=0;i<trainer.getMy_live().size();i++) {//find target live in trainer
            if (live.getCourse_id().equals(trainer.getMy_live().get(i).getCourse_id()))
                index2 = i;
        }

        Calendar calendar = null;//set live time slot
        if(timeSlot.equals("8:00 ~ 10:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),8,0);
        else if(timeSlot.equals("10:00 ~ 12:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),10,0);
        else if(timeSlot.equals("13:00 ~ 15:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),13,0);
        else if(timeSlot.equals("15:00 ~ 17:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),15,0);
        Date bookDate = calendar.getTime();

        live.getLive_plan().get(session-1).setLive_start_Date(bookDate);//update live info into client and trainer
        live.getLive_plan().get(session-1).setLive_url("ZoomLiveSession/Client:"+live.getClient_id()+"/Trainer: "+live.getTrainer_id()+"/time: "+bookDate);
        trainer.getOccupation().add(bookDate);
        client.getMy_live().set(index1,live);
        trainer.getMy_live().set(index2,live);
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());

    }

    /**
     *
     * @param client client which requesting his courses
     * @return list of client's lives
     */
    public ArrayList<Live> getClientLives(Client client) throws IOException {
        ArrayList <Live> lives = new ArrayList<Live>();
        for(Live live:client.getMy_live())
            lives.add(live);
        return lives;
    }

    /**
     * read client and a course to client's subscription
     * @param client_id
     * @param course_id
     */
    public static void addCourseToClient(String client_id,String course_id) throws Exception{
        Client client = (Client)IO.read(new Client(),client_id);
        client.addCourse(course_id);
        IO.write(client,client_id);

    }
    public static void addLiveToClient(String client_id,String live_id) throws Exception {
        Live live = (Live)IO.read(new Live(),live_id);
        live.setClient_id(client_id);
        Client client = (Client)IO.read(new Client(),client_id);
        client.addLive(live);
        Trainer trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
        trainer.addLive(live);
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());
    }
    public static void addPremiumToClient(String client_id,int premium_month) throws IOException {
        Client client = (Client)IO.read(new Client(),client_id);
        client.prolongPremium(premium_month);
        IO.write(client,client_id);
    }

    public static void register(String Username, String client_id, String password, String sex) throws Exception{
        Client client = new Client(client_id, password, Username, sex);
        boolean res = IO.create(client, client_id);
        int res2 = IO.write(client,client_id);
        //System.out.println(res);
        //System.out.println(res2);

    }
    public static void updateMyAccountPage(String client_id, String clientAge, String clientWeight, String clientHeight) throws IOException {
        Integer age = Integer.parseInt(clientAge);
        Double weight = Double.parseDouble(clientWeight);
        Double height = Double.parseDouble(clientHeight);


        Client client = (Client)IO.read(new Client(),client_id);
        client.setAge(age);
        client.setWeight(weight);
        client.setHeight(height);
        //
        client.cauculateBMIandBody_fat_rate();
        client.generateGeneric_plan();
        IO.write(client,client_id);

    }

    public static void createNewLive(String trainer_id, String Type, String info, ArrayList plan, ArrayList URL) throws IOException {
        Live live = new Live();
        String live_id = "??????";
        live.setCourse_id("?????");
        live.setInfo(info);
        live.setPlan(plan);
        live.setLive_plan(URL);
        live.setTrainer(trainer_id);
        live.setType(Type);

        IO.create(new Live(), live_id);


    }

}
