package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class IO{
    /**
     *  test:clientID 11111111111 trainerID:22222222222 password same to ID
     *       CourseID: C001 C002  LiveID: L001 L002
     *       LivePlan : no use now
     *       PolicyID : policy
     *
     */
    /**
     *
     * @param o to show the class of object
     * @param primary_key
     * @return the object to search
     * @throws IOException
     */

    public static Object read(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        //BufferedReader buffered_reader = new BufferedReader(new FileReader("src\\"+primary_key+".json"));
        String content= FileUtils.readFileToString(file,"UTF-8");
        Gson gson;
        try {
            GsonBuilder builder = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting();
            gson= builder.create();
            o = gson.fromJson(content, o.getClass());

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return o;
    }
    public static boolean create(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        return file.createNewFile();
    }
    public static boolean Delete_Info(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        return file.delete();
    }
    public static ArrayList<Live> showAllLive() throws IOException {
        File file = new File("target\\classes\\Data\\"+Live.class);
        File[] fileName = file.listFiles();
        ArrayList<Live> ans = new ArrayList<>();
        for(int i=0;i<fileName.length;i++)
        {
            String s = fileName[i].getName();
            s = s.substring(0,s.lastIndexOf("."));
            ans.add((Live) read(new Live(), s));
        }
        return  ans;
    }
    public static ArrayList<Course> showAllCourse() throws IOException {
        File file = new File("target\\classes\\Data\\"+Course.class);
        File[] fileName = file.listFiles();
        ArrayList<Course> ans = new ArrayList<>();
        for(int i=0;i<fileName.length;i++)
        {
            String s = fileName[i].getName();
            s = s.substring(0,s.lastIndexOf("."));
            ans.add((Course) read(new Course(), s));
        }
        return  ans;
    }
    /**
     * used in classes' toString --PZ
     * @param o
     * @return
     */
    public static String printObject(Object o){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(o);
    }


    public static int  write(Object o, String primary_key)
    {
        try{
            Gson gson;
            GsonBuilder builder = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting();
            gson = builder.create();
            String content = gson.toJson(o,o.getClass());
            File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
            FileUtils.writeStringToFile(file,content);
        }catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    static {

    }

}