import java.io.IOException;
import java.util.*;
import Model.*;

public class JarMain {
    public static void main(String[] args) throws IOException {

        //HashSet<Date> set = new HashSet<Date>();
        Date date1;
        Date date2;
        Calendar calendar = new GregorianCalendar(2021,4,17,8,0);//set to 2021-4-17-8-0
        date1 = calendar.getTime();
        date2 = calendar.getTime();
        Trainer trainer = (Trainer) IO.read(new Trainer(),"22222222222");
        trainer.getOccupation().add(date1);
        IO.write(trainer,"22222222222");
        trainer = (Trainer) IO.read(new Trainer(),"22222222222");
        System.out.println(trainer.getOccupation().contains(date2));

        Main.main(args);
    }
}
