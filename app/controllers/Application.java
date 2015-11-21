////////
// This sample is published as part of the blog article at www.toptal.com/blog
// Visit www.toptal.com/blog and subscribe to our newsletter to read great posts
////////

package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.ParseException;
import models.BlogPost;
import models.Login;
import models.SignUp;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/*
 * This controller contains Blog app common logic
 */
public class Application extends Controller {

  public static Result signup() throws ParseException {
    Form<SignUp> signUpForm = Form.form(SignUp.class).bindFromRequest();

    if ( signUpForm.hasErrors()) {
      return badRequest(signUpForm.errorsAsJson());
    }
    SignUp newUser =  signUpForm.get();
    User existingUser = User.findByEmail(newUser.email);
    if(existingUser != null) {
      return badRequest(buildJsonResponse("error", "User exists"));
    } else {
      User user = new User();
      if(newUser.mobileNumber.equals(newUser.mobileNumberConfirm)) {
          user.setMobileNumber(newUser.mobileNumber);
      } else {
          return badRequest(buildJsonResponse("error", "Mobile numbers do not match"));
      }

      if(newUser.email.equals(newUser.emailConfirm)) {
          user.setEmail(newUser.email);
      } else {
          return badRequest(buildJsonResponse("error", "Emails do not match"));
      }

      user.setFirstname(newUser.firstname);
      user.setLastname(newUser.lastname);
      user.setGender(newUser.gender);
      user.setBirthday(newUser.birthdate);
      user.setPassword(newUser.password);
      user.save();
      session().clear();
      session("username", newUser.email);


      return ok(buildJsonResponse("success", "User created successfully"));

    }
    //  return ok("Success!");
  }

  public static Result login() {
    Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    if (loginForm.hasErrors()) {
      return badRequest(loginForm.errorsAsJson());
    }
    Login loggingInUser = loginForm.get();
    User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
    if(user == null) {
      return badRequest(buildJsonResponse("error", "Incorrect email or password"));
    } else {
      session().clear();
      session("username", loggingInUser.email);
      session().put("userId", user.id.toString());

      ObjectNode wrapper = Json.newObject();
      ObjectNode msg = Json.newObject();
      msg.put("message", "Logged in successfully");
      msg.put("user", loggingInUser.email);
      wrapper.put("success", msg);
      return ok(wrapper);
    }
  }

  public static Result logout() {
    session().clear();
    return ok(buildJsonResponse("success", "Logged out successfully"));
  }

  public static Result isAuthenticated() {
    if(session().get("username") == null) {
      return unauthorized();
    } else {
      ObjectNode wrapper = Json.newObject();
      ObjectNode msg = Json.newObject();
      msg.put("message", "User is logged in already");
      msg.put("user", session().get("username"));
      wrapper.put("success", msg);
      return ok(wrapper);
    }
  }

  public static Result getPosts() {
    return ok(Json.toJson(BlogPost.find.findList()));
  }

  public static Result getPost(Long id) {
    BlogPost blogPost = BlogPost.findBlogPostById(id);
    if(blogPost == null) {
      return notFound(buildJsonResponse("error", "Post not found"));
    }
    return ok(Json.toJson(blogPost));
  }

  public static ObjectNode buildJsonResponse(String type, String message) {
    ObjectNode wrapper = Json.newObject();
    ObjectNode msg = Json.newObject();
    msg.put("message", message);
    wrapper.put(type, msg);
    return wrapper;
  }

}
