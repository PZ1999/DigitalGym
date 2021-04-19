package Model;

import ViewController.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
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
    }
    public static void addPremiumToClient(String client_id,int premium_month) throws IOException {
        Client client = (Client)IO.read(new Client(),client_id);
        client.prolongPremium(premium_month);
        IO.write(client,client_id);
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
}
