package model;

/**
 * Interface containing the predefinitions of the account methods.
 * -added roleid
 * @author Micke, Max.
 */
public interface AccountDTO {

    /**
     * Predefination of the method to set the banned status variable.
     *
     * @param status The status of a specific account.
     */
    void setBanned(boolean status);

    /**
     * Retrives the banned status of a specific account.
     *
     * @return
     */
    boolean getStatus();

    /**
     * Predefination of the method to retrives the username of a specific
     * account.
     *
     * @return The username as a String variable.
     */
    String getUsername();

    /**
     * Predefination of the method to retrives the password of a specific
     * account.
     *
     * @return The password as a String variable.
     */
    String getPassword();

    
    int getPersonalId();
    
    

    /**
     * Predefination of the method to modify the username.
     *
     * @param username The username that is going to be modified.
     */
    void setUsername(String username);

    /**
     * Predefination of the method to modify the password.
     *
     * @param password The password that is going to be modified.
     */
    void setPassword(String password);

    
    void setPersonalId(int personalId);
    
    String getRoleId();
    
    
    void setRoleId(String roleId);
}
