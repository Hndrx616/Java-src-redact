/**
 * author Stephen Hilliard (c) 2015.
 * developer Stephen Hilliard (c) 2015.
 */

import org.jdesktop.beansbinding.Validator;

public class AgeValidator extends Validator<Integer> {

    public Validator.Result validate(Integer arg) {        
        if ((arg < 1) || (arg > 199)) {
            return new Result(null, "Age range is 1-199");
        }
        
        return null;    
    }
}
