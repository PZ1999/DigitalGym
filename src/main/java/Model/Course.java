package Model;


import java.util.ArrayList;

public class Course extends GeneralCourse{


    private ArrayList <String> video_path = new ArrayList<String>();
    public Course(String course_id,String name,String trainer_id,String trainer,String type,String info,int rank,double price,ArrayList<String> video_path){
        super(course_id, name, trainer_id, trainer, type, info, rank, price);
        setVideo_path(video_path);
    }

    public Course() {
        super();
    }

    public ArrayList<String> getVideo_path() {
        return video_path;
    }

    public void setVideo_path(ArrayList<String> video_path) {
        this.video_path = video_path;
    }

    @Override
    public String toString() {
        return "Course{" +
                "video_path=" + video_path +
                ", course_id='" + course_id + '\'' +
                ", name='" + name + '\'' +
                ", trainer_id='" + trainer_id + '\'' +
                ", trainer='" + trainer + '\'' +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", plan=" + plan +
                ", rank=" + rank +
                ", state='" + state + '\'' +
                ", price=" + price +
                '}';
    }
}