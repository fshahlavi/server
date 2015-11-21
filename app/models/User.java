package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.validation.Constraints;
import play.db.ebean.Model;


/**
 * Model representing a Blog user
 */
@Entity
public class User extends Model {

    @Id
    public Long id;

    @Column(length = 255, unique = true, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String firstname;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String lastname;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String mobileNumber;

    @Column(nullable = false)
    @Constraints.Required
    public Date birthday;

    @Column(nullable = false)
    @Constraints.Required
    public int gender;

    @Column(length = 64, nullable = false)
    private byte[] shaPassword;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    public List<models.BlogPost> posts;

    public void setPassword(String password) {
        this.shaPassword = getSha512(password);
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

   // public String getEmail() { return this.email; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setMobileNumber(String number) {
        this.mobileNumber = number;
    }

   // public String getMobileNumber() {return this.mobileNumber; }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    //public Date getBirthday() { return this.birthday.}

    public void setGender(int gender) {
        this.gender = gender;
    }

    public static final Finder<Long, User> find = new Finder<Long, User>(
            Long.class, User.class);

    public static User findByEmailAndPassword(String email, String password) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .eq("shaPassword", getSha512(password))
                .findUnique();
    }

    public static User findByEmail(String email) {
        return find
                .where()
                .eq("email", email.toLowerCase())
                .findUnique();
    }

    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}