package Model;

import java.util.ArrayList;

public class Policy {
    public static double premium_price;
    public static double premium_discount;//on buying premium,0.1 for 10%off
    public static double live_discount;
    public static ArrayList<String> sport_type;// contains types info of courses
    static {
        sport_type = new ArrayList<String>();
        sport_type.add("Yoga");
        sport_type.add("Bike");
    }
}
/**
 * client 1
 * trainer 1
 * course 2
 * live 2
 *
 */