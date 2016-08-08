/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */
import org.jdesktop.beansbinding.Validator;

public class RequiredStringValidator extends Validator<String> {

    public Validator.Result validate(String arg) {
        if ((arg == null) || (arg.length() == 0)) {
            return new Validator.Result(null, "Empty value");
        }
        return null;
    }
}
