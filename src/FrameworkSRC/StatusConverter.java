/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import org.jdesktop.beansbinding.Converter;

public class StatusConverter extends Converter<Integer, String> {

    public String convertForward(Integer arg) {
        String value = null;
        switch (arg) {
            case 0: value="A"; break;
            case 1: value="B"; break;
            case 2: value="C"; break;
            case 3: value="D"; break;
        }
        return value;
    }

    public Integer convertReverse(String arg) {
        int value = 0;
        if ("A".equals(arg)) {
            value = 0;
        } else if ("B".equals(arg)) {
            value = 1;
        } else if ("C".equals(arg)) {
            value = 2;
        } else if ("D".equals(arg)) {
            value = 3;
        }
        return value;
    }

}
