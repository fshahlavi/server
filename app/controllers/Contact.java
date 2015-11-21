package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import models.UserContact;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/20/15
 * Time: 7:45 PM
 */
public class Contact extends Controller {

    public static Result addContact() throws ParseException {
        Form<UserContact> contactForm = Form.form(UserContact.class).bindFromRequest();

        if ( contactForm.hasErrors()) {
            return badRequest(contactForm.errorsAsJson());
        }
        UserContact newContact =  contactForm.get();
        UserContact existingContact = UserContact.findByUserId(newContact.userId);
        if(existingContact != null) {
            existingContact.setAddress1(newContact.address1);
            existingContact.setAddress2(newContact.address2);
            existingContact.setState(newContact.state);
            existingContact.setWorkphone(newContact.workphone);
            existingContact.setZip(newContact.zip);
        } else {
            UserContact contact = new UserContact();
            contact.setAddress1(newContact.address1);
            contact.setAddress2(newContact.address2);
            contact.setState(newContact.state);
            contact.setWorkphone(newContact.workphone);
            contact.setZip(newContact.zip);
            contact.save();
        }

        return ok(buildJsonResponse("success", "User contact created successfully"));
    }

    public static ObjectNode buildJsonResponse(String type, String message) {
        ObjectNode wrapper = Json.newObject();
        ObjectNode msg = Json.newObject();
        msg.put("message", message);
        wrapper.put(type, msg);
        return wrapper;
    }
}
