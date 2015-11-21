package controllers;

import models.BlogPost;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/11/15
 * Time: 6:03 PM
 */
@Security.Authenticated(Secured.class)
public class Profile extends Controller{

    public static Result getUserProfile() {
        User user = getUser();
        if(user == null) {
            return badRequest(Application.buildJsonResponse("error", "No such user"));
        }
        return ok(Json.toJson(BlogPost.findBlogPostsByUser(user)));
    }

    private static User getUser() {
        return User.findByEmail(session().get("username"));
    }
}
