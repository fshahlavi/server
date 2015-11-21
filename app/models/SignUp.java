package models;

import play.data.validation.Constraints;

/**
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/7/15
 * Time: 7:05 PM
 */
public class SignUp extends UserForm {
    @Constraints.Required
    @Constraints.MinLength(6)
    public String password;
}
