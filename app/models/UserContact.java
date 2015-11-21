package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Model representing a Blog user
 * Created with IntelliJ IDEA.
 * User: fshahlavi
 * Date: 11/15/15
 * Time: 9:09 PM
 */

@Entity
public class UserContact extends Model {
    @Id
    Long id;

    public Long userId;

    @Column(length = 255)
    @Constraints.MaxLength(255)

   // @Constraints.Required
    public String address1;

    @Column(length = 255)
    @Constraints.MaxLength(255)
    //@Constraints.Required
    public String address2;

    @Column(length = 255)
    @Constraints.MaxLength(255)
    //@Constraints.Required
    public String state;

    @Column(length = 255)
    @Constraints.MaxLength(255)

   // @Constraints.Required
    public String zip;

    @Column(length = 255)
    @Constraints.MaxLength(255)
    // @Constraints.Required
    public String workphone;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    public static final Finder<Long, UserContact> find = new Finder<Long, UserContact>(
            Long.class, UserContact.class);

    public static UserContact findByUserId(Long userId) {
        return find
                .where()
                .eq("userId", userId)
                .findUnique();
    }

}
