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
     * add filter function --PZ 4.19
     * @return list of course
     */
    public static ArrayList<Course> getAllCourses(String filter,String client_id) throws IOException {
        ArrayList<Course> courses = IO.showAllCourse();
        ArrayList<Course> targets = new ArrayList<Course>();

        if(filter.equals("All")){
            for(Course course:courses)//all
                    targets.add(course);
        }
        else if(filter.equals("Discount")){//get discount courses to client
            Client client = (Client)IO.read(new Client(),client_id);
            for(Course course:courses){//no discount
                if(course.getRank()<=client.getRank())
                    targets.add(course);
            }
        }
        else if(filter.equals("Wait For Approval")){
            for(Course course:courses){
                if(course.getState().equals("waiting")){
                    targets.add(course);
                }
            }
        }
        else if(filter.equals("Banned")){
            for(Course course:courses){
                if(course.getState().equals("ban")){
                    targets.add(course);
                }
            }
        }
        else{//filter by type
            for(Course course:courses){//no discount
                if(course.getType().equals(filter))
                    targets.add(course);
            }
        }
        return targets;
    }
    /**
     *
     * This method used to get all lives from IO
     * add filter function --PZ 4.19
     * @return list of course
     * @author PZ
     */
    public static ArrayList<Live> getAllLives(String filter,String client_id) throws IOException {
        ArrayList<Live> lives = IO.showAllLive();
        ArrayList<Live> targets = new ArrayList<Live>();

        if(filter.equals("All")){
            for(Live live:lives)//all
                targets.add(live);
        }
        else if(filter.equals("Discount")){//get discount courses to client
            Client client = (Client)IO.read(new Client(),client_id);
            for(Live live:lives){//no discount
                if(live.getRank()<=client.getRank())
                    targets.add(live);
            }
        }
        else if(filter.equals("Wait For Approval")){
            for(Live live:lives){
                if(live.getState().equals("waiting")){
                    targets.add(live);
                }
            }
        }
        else if(filter.equals("Banned")){
            for(Live live:lives){
                if(live.getState().equals("ban")){
                    targets.add(live);
                }
            }
        }
        else{//filter by type
            for(Live live:lives){//no discount
                if(live.getType().equals(filter))
                    targets.add(live);
            }
        }
        return targets;
    }

    /**
     * get client subscription courses
     * add filter function --PZ
     * @author PZ
     * @param client client which requesting his courses
     * @param filter indicate filter info
     * @return list of client's courses
     */
    public static ArrayList<Course> getClientCourses(Client client, String filter) throws IOException {
        ArrayList <Course> courses = new ArrayList<Course>();
        for(String s:client.getMy_course())
            courses.add((Course)IO.read(new Course(),s));
        ArrayList<Course> targets = new ArrayList<Course>();
        if(filter.equals("All")){
            for(Course course:courses)//all
                targets.add(course);
        }
        else{//filter by type
            for(Course course:courses){//no discount
                if(course.getType().equals(filter))
                    targets.add(course);
            }
        }
        return targets;
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
     * delete a live session for both client and trainer
     * @author PZ
     * @param live_plan live session object need to be canceled
     */
    public static void cancelLiveSession(LivePlan live_plan) throws IOException {
        if(live_plan.getLive_start_Date()==null) return;
        Client client = (Client)IO.read(new Client(),live_plan.getClient_id());

        Live live = null;//new Live object after cancellation
        for(Live l:client.getMy_live()){//find target live
            if(l.getCourse_id().equals(live_plan.getCourse_id()))
                live = l;
        }
       // IO.printObject(live);
        Trainer trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
        for(int i=0;i<live.getLive_plan().size();i++){//cancel live session
            if(live.getLive_plan().get(i).getLive_start_Date()!=null){

                if(live.getLive_plan().get(i).getLive_start_Date().equals(live_plan.getLive_start_Date())){
                   // System.out.println(live.getLive_plan().get(i).getLive_start_Date());
                    //System.out.println(trainer.getOccupation().remove(live.getLive_plan().get(i).getLive_start_Date()));

                    live.getLive_plan().get(i).setLive_start_Date(null);
                    live.getLive_plan().get(i).setLive_url(null);
                }

            }

        }
        for(int i=0;i<client.getMy_live().size();i++){//update client
            if(client.getMy_live().get(i).getCourse_id().equals(live_plan.getCourse_id()))
                client.getMy_live().set(i,live);
        }
        for(int i=0;i<trainer.getMy_live().size();i++){//update trainer
            if(trainer.getMy_live().get(i).getCourse_id().equals(live_plan.getCourse_id()))
                trainer.getMy_live().set(i,live);
        }
        System.out.println(trainer.getOccupation().remove(live_plan.getLive_start_Date()));
        //write back to DB
        System.out.println(trainer.toString());
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());
    }

    /**
     * get a live plan finished, may called by trainer or client
     * @param live_plan
     * @throws IOException
     */
    public static void finishLiveSession(LivePlan live_plan) throws IOException {
        Live live = (Live)IO.read(new Live(),live_plan.getCourse_id());
        Trainer trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
        Client client = (Client)IO.read(new Client(),live_plan.getClient_id());
        client.finishLiveSession(live_plan);
        trainer.finishLiveSession(live_plan);
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());
    }

    /**
     * this method is called when a manager approve a course and set its rank and price
     * @param course
     * @param rank rank set by manager, "Normal"/"Premium"
     * @param price
     */
    public static void managerApproveCourse(Course course,String rank, double price) {
        course.setState("alive");
        course.setRank(rank.equals("Normal")?0:1);
        course.setPrice(price);
        IO.write(course,course.getCourse_id());
    }

    public static void managerApproveLive(Live live, String rank, double price) {
        live.setState("alive");
        live.setRank(rank.equals("Normal")?0:1);
        live.setPrice(price);
        IO.write(live,live.getCourse_id());
    }

    public static boolean changePolicy(Double premium_price, Double premium_discount, Double premium_live_discount) throws IOException {
        if(premium_price<0||premium_discount>1.0||premium_discount<0.0||premium_live_discount>1.0||premium_live_discount<0.0)
            return false;
        Policy policy = (Policy)IO.read(new Policy(),"Policy");
        System.out.println(policy.toString());
        policy.premium_price = premium_price;
        policy.premium_discount = premium_discount;
        policy.live_discount = premium_live_discount;
        IO.write(policy,"Policy");
        return true;
    }


    /** return live subscription by client
     * add filter function --PZ
     * @author PZ
     * @param client client which requesting his courses
     * @param filter indicate filter
     * @return list of client's lives
     */
    public ArrayList<Live> getClientLives(Client client, String filter) throws IOException {
        ArrayList <Live> lives = new ArrayList<Live>();
        lives.addAll(client.getMy_live());
        ArrayList<Live> targets = new ArrayList<Live>();
        if(filter.equals("All")){
            //all
            targets.addAll(lives);
        }
        else{//filter by type
            for(Live live:lives){//no discount
                if(live.getType().equals(filter))
                    targets.add(live);
            }
        }
        return targets;
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
            if(client.getMy_live().get(i).getCourse_id().equals(live.getCourse_id())){
                for(LivePlan lp:client.getMy_live().get(i).getLive_plan()){
                    if(lp.getLive_start_Date()!=null){
                        cancelLiveSession(lp);
                    }
                }
                trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
                client.getMy_live().remove(i);//delete from client
            }

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

        if(client.getMy_live().get(index1).getLive_plan().get(session-1).getFinish()){//live session has been booked and already finished
            throw new Exception("live session has been finished, cannot book again");
        }


        Calendar calendar = null;//set live time slot
        if(timeSlot.equals("8:00 ~ 10:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),8,0);
        else if(timeSlot.equals("10:00 ~ 12:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),10,0);
        else if(timeSlot.equals("13:00 ~ 15:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),13,0);
        else if(timeSlot.equals("15:00 ~ 17:00"))
            calendar = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),15,0);
        Date bookDate = calendar.getTime();
        Date oldDate = live.getLive_plan().get(session-1).getLive_start_Date();
        if(oldDate!=null)//dateChanged
            trainer.getOccupation().remove(oldDate);
        live.getLive_plan().get(session-1).setLive_start_Date(bookDate);//update live info into client and trainer
        live.getLive_plan().get(session-1).setLive_url("ZoomLiveSession/Client:"+live.getClient_id()+"/Trainer: "+live.getTrainer_id()+"/time: "+bookDate);
        trainer.getOccupation().add(bookDate);
        client.getMy_live().set(index1,live);
        trainer.getMy_live().set(index2,live);
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());

    }


    /**
     * @author JoyceJ
     * @return java.lang.String
     **/
    public static String checkLoginInfo(String phoneNumber, String password) throws IOException {
        try{
            //System.out.println("test");
            Client client = (Client)IO.read(new Client(),phoneNumber);
            if(client!=null) {

                if(client.password.equals(password))    return "Client";
                else    return "fail";
            }

            /*
            Manager manager = (Manager) IO.read(new Manager(), phoneNumber);
            System.out.println(manager);
            if(manager!=null){
                if(manager.password.equals(password))   return "Manager";
                else    return "PasswdFail";
            }else{
                System.out.println("manager null");
            }

             */
        }catch (IOException e) {
            try{
                Trainer trainer = (Trainer) IO.read(new Trainer(), phoneNumber);
                if(trainer!=null){
                    if(trainer.password.equals(password))   return "Trainer";
                    else    return "fail";
                }

            }catch (IOException o){
                if(phoneNumber.equals("00000000000")&&password.equals("00000000000"))   return "Manager";
                    //else if(phoneNumber.equals("00000000000")||!password.equals("00000000000")) System.out.println("ManagerPassword Wrong");
                else    return "fail";
            }

        }
        return "fail";

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
     * @author PZ
     * @param client_id
     * @param course_id
     */
    public static void addCourseToClient(String client_id,String course_id) throws Exception{
        Client client = (Client)IO.read(new Client(),client_id);
        client.addCourse(course_id);
        IO.write(client,client_id);

    }

    /**
     * add live subscription to client
     * @author PZ
     * @param client_id
     * @param live_id
     * @throws Exception
     */
    public static void addLiveToClient(String client_id,String live_id) throws Exception {
        Live live = (Live)IO.read(new Live(),live_id);
        live.setClient_id(client_id);
        for(LivePlan lp : live.getLive_plan())
            lp.setClient_id(client_id);
        Client client = (Client)IO.read(new Client(),client_id);
        client.addLive(live);
        Trainer trainer = (Trainer)IO.read(new Trainer(),live.getTrainer_id());
        trainer.addLive(live);
        IO.write(client,client.getPhone_number());
        IO.write(trainer,trainer.getPhone_number());
    }

    /**
     * add premium length to client, detail in client.prolongPremium
     * @author PZ
     * @param client_id
     * @param premium_month
     * @throws IOException
     */
    public static void addPremiumToClient(String client_id,int premium_month) throws IOException {
        Client client = (Client)IO.read(new Client(),client_id);
        client.prolongPremium(premium_month);
        IO.write(client,client_id);
    }

    /**
     * called when new client register, generate a new account
     * @param Username
     * @param client_id
     * @param password
     * @param sex
     * @throws Exception
     */
    public static void register(String Username, String client_id, String password, String sex) throws Exception{
        Client client = new Client(client_id, password, Username, sex);
        boolean res = IO.create(client, client_id);
        boolean res2 = IO.write(client,client_id);

    }

    /**
     * @author JoyceJ and yy
     * Used in forgetPasswordScene and the changePasswordScene in client's main scene
     **/
    public static void changePassword(String client_id, String newPassword) throws IOException {

        Client client = (Client)IO.read(new Client(),client_id);
        client.setPassword(newPassword);
        IO.write(client,client_id);

    }

    /**
     * called when client save new body index
     * @param client_id
     * @param clientAge
     * @param clientWeight
     * @param clientHeight
     * @throws IOException
     */
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

    /**
     * get trainer occupations on a certain date
     * @param trainer_id
     * @param date "2021-1-1 0:0:0"
     * @return an arrayList with 4 entries for 4 time slots in a certain date. set null if trainer is free for one slot
     */public static ArrayList<LivePlan> getTrainerLiveSession(String trainer_id, Date date) throws IOException {
        ArrayList<LivePlan> sessions = new ArrayList<LivePlan>();
        Trainer t = (Trainer) IO.read(new Trainer(),trainer_id);
        ArrayList<Live> lives= t.getMy_live();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY,6);

        for(int i=1;i<=4;i++)
        {

            calendar.add(Calendar.HOUR_OF_DAY,2);
            if(i==3)             calendar.add(Calendar.HOUR_OF_DAY,1);

            Date newdate = calendar.getTime();
            int flag=0;
            for(Live l :lives)
            {
                ArrayList<LivePlan> livePlans = l.getLive_plan();
                for(LivePlan liveplan : livePlans)
                {

                    if(liveplan.getLive_start_Date()!=null&&liveplan.getLive_start_Date().equals(newdate))
                    {
                        flag=1;
                        sessions.add(liveplan);
                        break;
                    }
                }

            }
            if(flag==0)
            {
                //LivePlan nullPlan = new LivePlan();
                //nullPlan.setTrainer_id(trainer_id);
                sessions.add(null);
            }
        }

        return sessions;
    }
    /**
     * @param l a live object contain client_id and trainer_id. used to update personal plan.
     */
    public static boolean updatePeronalLive(Live l, int day) throws IOException {
        Client c = (Client) IO.read(new Client(), l.getClient_id());
        Trainer t = (Trainer) IO.read(new Trainer(), l.getTrainer_id());
        for( Live i : c.getMy_live())
        {
            if(i.getCourse_id().equals(l.getCourse_id()))
            {
                i.getLive_plan().get(day).setPersonal_plan(l.getLive_plan().get(day).getPersonal_plan());
            }
        }
        for( Live i : t.getMy_live())
        {
            if(i.getCourse_id().equals(l.getCourse_id()))
            {
                i.getLive_plan().get(day).setPersonal_plan(l.getLive_plan().get(day).getPersonal_plan());
            }
        }
        return IO.write(c,c.getPhone_number()) && IO.write(t,t.getPhone_number());
    }

    /**
     * used to cancel a live session. remember to cancel both in client and trainer.
     * @param live which contains client_id and trainer_id.
     * @param day target live session index
     */
    public static boolean cancelPlan(Live live,int day) throws IOException {
        Client c = (Client) IO.read(new Client(),live.getClient_id());
        for(Live l : c.getMy_live())
        {
            if(l.getCourse_id().equals(live.getCourse_id()))
            {
                l.getALivePlan(day).setLive_start_Date(null);
                return true;
            }
        }
        return false;
    }

    /**
     * @author zz
     * @param client_id pk
     * @return boolean whether write file
     */
    public static boolean deleteClient(String client_id) throws IOException {
        /*
        user.setState("Inactive");
        return IO.write(user,user.getPhone_number());
        */
         return IO.delete(new Client(),client_id);
    }

    public static boolean changeLiveInfo(Trainer t, Live l) throws IOException {
        for( Live i : t.getMy_live())
        {
            if(i.getCourse_id().equals(l.getCourse_id()))
            {
                Client c = (Client) IO.read(new Client(),i.getClient_id());
                for( Live j : c.getMy_live())
                {
                    if(j.getCourse_id().equals(l.getCourse_id()))
                    {
                        j=l;
                        j.setClient_id(c.getPhone_number());
                    }
                }
                IO.write(c,c.getPhone_number());
            }
        }
        return true;
    }
    /**
     * used when pk of client changed
     * @param client_id who wants to change his password
     * @param newPhoneNumber is the new phone number which replaces original phone number
     */
    public static void changePhoneNumber(String client_id, String newPhoneNumber) throws Exception {
        Client client = (Client) IO.read(new Client(), client_id);
        String oldNumber = client.getPhone_number();
        client.setPhone_number(newPhoneNumber);
        IO.write(client, client_id);
        IO.changeFileName(client,oldNumber,client.getPhone_number());
    }
    /**
     * @author zz
     * @param c client
     * @param t train
     * @param l new live contains new personal plan
     * @param day the date of the new personal plan
     * @return true is success
     */
    public  static boolean publishPlan(Client c, Trainer t, Live l, int day)
    {
        for( Live i : c.getMy_live())
        {
            if(i.getCourse_id().equals(l.getCourse_id()))
            {
                i.getLive_plan().get(day).setPersonal_plan(l.getLive_plan().get(day).getPersonal_plan());
            }
        }
        for( Live i : t.getMy_live())
        {
            if(i.getCourse_id().equals(l.getCourse_id()))
            {
                i.getLive_plan().get(day).setPersonal_plan(l.getLive_plan().get(day).getPersonal_plan());
            }
        }
        return IO.write(c,c.getPhone_number()) && IO.write(t,t.getPhone_number());
    }

    /**
     * called to check phoneNumber format
     * need more function later --PZ 4.22
     * @param phoneNumber input for check
     * @return true if valid, otherwise false
     */
    public static Boolean checkPhoneNumberFormat(String phoneNumber){

        if(phoneNumber.length()!=11) return false;
        return true;
    }

    /**
     * called to check password format
     * need more function later --PZ 4.22
     * @param password input for check
     * @return true if valid, otherwise false
     */
    public static Boolean checkPasswordFormat(String password){
        return true;
    }

    /**
     * called whe a new course added by a trainer.
     * Also called when courses info is updated.
     * @author PZ
     * @param course new course passed from GUI
     * @throws IOException IO exception
     */
    public static void addCourse(Course course) throws IOException {

        Trainer trainer = (Trainer) IO.read(new Trainer(),course.getTrainer_id());
        if(trainer.getMy_course().contains(course.getCourse_id())){//called for update course
            System.out.println("course updated");
            IO.write(course,course.getCourse_id());
            return ;
        }
        trainer.addCourse(course);
        IO.write(trainer,trainer.getPhone_number());
        course.setCourse_id("C"+IO.showAllCourse().size());
        IO.create(course,course.getCourse_id());
        IO.write(course,course.getCourse_id());
    }
    /**
     * called whe a new live added by a trainer
     * @param live new live passed from GUI
     * @author PZ
     * @throws IOException
     */
    public static void addLive(Live live) throws IOException {
        live.setCourse_id("L"+IO.showAllLive().size());
        IO.create(live,live.getCourse_id());
        IO.write(live,live.getCourse_id());
    }
    public static ArrayList<Course> getTrainerCourses(Trainer trainer, String filter) throws IOException {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (String s : trainer.getMy_course())
            courses.add((Course) IO.read(new Course(), s));
        ArrayList<Course> targets = new ArrayList<Course>();
        if (filter.equals("All")) {
            for (Course course : courses)//all
                targets.add(course);
        }

        else if (filter.equals("Wait For Approval")) {
            for (Course course : courses) {
                if (course.getState().equals("waiting")) {
                    targets.add(course);
                }
            }
        }
        else if (filter.equals("Banned")) {
            for (Course course : courses) {
                if (course.getState().equals("ban")) {
                    targets.add(course);
                }
            }
        }
        else {//filter by type
            for (Course course : courses) {//no discount
                if (course.getType().equals(filter))
                    targets.add(course);
            }
            return targets;
        }
        return targets;
    }
}

