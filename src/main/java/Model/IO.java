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
     */
    /**
     *
     * @param  o to show the class of object
     * @param  primary_key
     * @return the object to search
     * @throws IOException
     */

    public static Object read(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        //BufferedReader buffered_reader = new BufferedReader(new FileReader("src\\"+primary_key+".json"));
        System.out.println(file.getPath());
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
            return  null;
        }
        return o;
    }

    /**
     *
     * @param o
     * @param primary_key
     * @return  whether create file successfully
     * @throws IOException
     */
    public static boolean create(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        System.out.println(file);
        return file.createNewFile();
    }

    /**
     *
     * @param o
     * @param primary_key
     * @return whether delete file successfully
     * @throws IOException
     */
    public static boolean delete(Object o, String primary_key) throws IOException {
        File file = new File("target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        return file.delete();
    }

    /**
     *
     * @return a arraylist contains all lives
     * @throws IOException
     */
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

    /**
     *
     * @return a arraylist contains all courses
     * @throws IOException
     */
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

    /**
     *
     * @param o
     * @param primary_key
     * @return write object to string
     */
    public static boolean  write(Object o, String primary_key)
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
            return false;
        }
        return true;
    }

    /**
     *
     * @param o
     * @param old_key
     * @param new_key
     * @return whether the filename has been successfully changed
     */
    public static boolean changeFileName(Object o, String old_key, String new_key)
    {

            File file1 = new File("target\\classes\\Data\\"+o.getClass()+"\\"+old_key+".json");
            File file2 = new File("target\\classes\\Data\\"+o.getClass()+"\\"+new_key+".json");
            return file1.renameTo(file2);
    }


}