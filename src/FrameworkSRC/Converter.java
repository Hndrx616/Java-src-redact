/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import org.jdesktop.beansbinding.Converter;

public class AgeConverter extends Converter<Integer, String> {

    public String convertForward(Integer arg) {
        return String.valueOf(arg);
    }

    public Integer convertReverse(String arg) {
        int value;
        try {
            value = (arg == null) ? 0 : Integer.parseInt(arg);
        } catch (NumberFormatException ex) {
            value = 0;
        }
        return value;
    }

}
