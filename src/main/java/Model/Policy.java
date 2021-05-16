package Model;

import java.io.IOException;
import java.util.ArrayList;

public class Policy {
    public double premium_price;
    public double premium_discount;//on buying premium,0.1 for 10%off
    public double live_discount;
    public ArrayList<String> sport_type;// contains types info of courses

    @Override
    public String toString() {
        return "Policy{" +
                "premium_price=" + premium_price +
                ", premium_discount=" + premium_discount +
                ", live_discount=" + live_discount +
                ", sport_type=" + sport_type +
                '}';
    }
}
/**
 * client 1
 * trainer 1
 * course 2
 * live 2
 *
 */