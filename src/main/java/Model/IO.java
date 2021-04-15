package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class IO{
    public static Object read(Object o, String primary_key) throws IOException {
        File file = new File("src\\target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
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

    //read(Object o,String pk)
    //write(Object o,String pk)
    //delete(Object o,String pk)
    //create

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
            File file = new File("src\\target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
            FileUtils.writeStringToFile(file,content);
        }catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public static boolean create(Object o, String primary_key) throws IOException {
        File file = new File("src\\target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        return file.createNewFile();
    }
    public static boolean Delete_Info(Object o, String primary_key) throws IOException {
        File file = new File("src\\target\\classes\\Data\\"+o.getClass()+"\\"+primary_key+".json");
        return file.delete();
    }
    public static void main(String[] args) throws IOException {
        Client p = (Client) read(new Client(),"1");
        System.out.println(p.toString());

    }
}
