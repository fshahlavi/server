package models;

import java.util.Date;
import play.data.validation.Constraints;

/**
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/7/15
 * Time: 6:53 PM
 */

public class UserForm {
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    @Constraints.Email
    public String emailConfirm;

    @Constraints.Required
    @Constraints.Pattern(value = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
            message = "Please provide a valid phone number")
    public String mobileNumber;

    @Constraints.Required
    @Constraints.Pattern(value = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
            message = "Please provide a valid phone number")
    public String mobileNumberConfirm;

    @Constraints.Required
    public String firstname;

    @Constraints.Required
    public String lastname;

    @Constraints.Required
    public Date birthdate;

    @Constraints.Required
    public int gender;


}
