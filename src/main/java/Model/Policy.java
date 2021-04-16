package Model;

import java.io.IOException;
import java.util.ArrayList;

public class Policy {
    public static double premium_price = 60;
    public static double premium_discount = 0.1;//on buying premium,0.1 for 10%off
    public static double live_discount = 0.3;
    public static ArrayList<String> sport_type;// contains types info of courses
    static {
        sport_type = new ArrayList<String>();
        sport_type.add("Yoga");
        sport_type.add("Bike");
        Policy policy;

    }
}
/**
 * client 1
 * trainer 1
 * course 2
 * live 2
 *
 */