package models;

import play.data.validation.Constraints;

/**
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/7/15
 * Time: 7:10 PM
 */
public class Login  {
    @Constraints.Required
    public String password;
    @Constraints.Required
    public String email;
}
