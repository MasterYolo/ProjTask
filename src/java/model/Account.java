package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * Changelog:
 * -added roleid
 * Class used to handle the account, log in details from and to the DB. Class is
 * a entity class which means it can be stored as a whole object in the javaDB.
 *
 * @author Micke, Max.
 */
@Entity
public class Account implements Serializable, AccountDTO {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /* Account specific variables */
    private String username;

    private int personalId;
    private int roleId;
    private String password;
    private boolean banned;
   

    /**
     * Consturctor needed when the class is Serializable.
     */
    public Account() {
    }

    /**
     * Consturctor used when a new account is created.
     *
     * @param username A specific username (input from the JSF page).
     * @param password A specific password (input from the JSF page).
     * @param banned the bannedstatus.
     * @param role_id
     */
    public Account(String username, String password, boolean banned, int personalId, int roleId) {

        this.banned = banned;
        this.username = username;
        this.password = password;
        this.personalId = personalId;
        this.roleId = roleId;
    }

    /**
     * Method to modify the username.
     *
     * @param username The username that is going to be modified.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrives the username.
     *
     * @return A specific username as a string.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to modify the password.
     *
     * @param password The password that is going to be modified.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrives the password.
     *
     * @return A specific password as a string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method to modify the banned status.
     *
     * @param status The banned status that is going to be modified.
     */
    public void setBanned(boolean status) {
        this.banned = status;
    }

    /**
     * Retrives the banned status.
     *
     * @return The banned status of the current user.
     */
    public boolean getStatus() {
        return banned;
    }

    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    

}
