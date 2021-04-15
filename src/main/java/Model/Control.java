package Model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;

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
        return new String();
    }




}
