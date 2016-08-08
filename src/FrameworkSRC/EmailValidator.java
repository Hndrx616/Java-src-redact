/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */

import org.jdesktop.beansbinding.Validator;

public class EmailValidator extends Validator<String> {

    public Validator.Result validate(String arg) {
        if ((arg.length() < 4) || !arg.contains("@") || !arg.contains(".")) {
            return new Result(null, "Please enter a valid email");
        }
        return null;    
    }
}
