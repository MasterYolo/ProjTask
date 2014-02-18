package view;

import controller.AdminFacade;
import controller.LoginFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.AccountDTO;
import model.Availability;
import model.Competence;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.PersonalDTO;
import model.Roles;
import crypto.Hash;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.CompetenceProfile;
import model.RolesDTO;

/**
 * Changelog: -UpdateApplication on login, updateRole and rejectApplication.
 * -Added hash sha1 password. The input from the user is encrypted and compared
 * with the one in the db. -Added updated role -Added rejectApplication. - added
 * unique id to Role - changed from hardcoded admin username to use the roleid
 * instead - added hashed sha1 password to the model. (Added crypto layer).
 * -added roleid in register (Account)
 *
 * -submitApplication() removed. -register() now registers inputs from the user.
 * -register() now registers personal,account,availablity,competence and
 * comptenceprofile.
 *
 * -added personallist object with get/set methods. -getting a role id now works
 * as intended -Role ID is now a String and not an Integer
 *
 * Class to handle the calls from and to the web interface through JSF. It
 * passes method-calls to the controller to handle store and get objects in the
 * database.
 *
 *
 *
 *
 * @author Mikael, Max och Kalle
 */
@Named("manager")
@SessionScoped
public class ApplicationManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private AdminFacade adminFacade;
    @EJB
    private LoginFacade loginFacade;
    
    private AccountDTO loginuser;
    private AccountDTO account;

    private CompetenceProfileDTO comptenceProfile;
    private PersonalDTO personinfo;
    private PersonalDTO jobSeeker;

    /* Array lists used in the view */
    private List<PersonalDTO> personalList = new ArrayList<PersonalDTO>();
    private List<AccountDTO> accountList = new ArrayList<AccountDTO>();
    private List<PersonalDTO> jobSeekerList = new ArrayList<PersonalDTO>();

    /* Account variables */
    private String username;
    private String pass;
    private boolean rss; //RegistrationSuccessfulStatus
    private boolean uaes; //UserAlreadyExsitsStatus
    private boolean adminStatus;
    private boolean loggedIn;
    private boolean status;

    /* Application variables */
    private String availabilityFrom;
    private String availabilityTo;
    private String competence;
    private String experience;
    private String role;

    /*Person variables */
    private String name;
    private String surname;
    private int ssn;
    private String email;

    /* Conversation variables */
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    /**
     * Starts a new conversation.
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    /**
     * Stops a ongoing conversation.
     */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    /**
     * Handles exception if any.
     *
     * @param e The exception object.
     */
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }

    /**
     * @return True for success, false when transaction failed.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the lastest exception.
     *
     * @return The latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }

    /**
     * Retrieves a list of the Accounts from the database.
     *
     * @return List of accounts from the database
     */
    public void updateApplications() {
        jobSeekerList = adminFacade.getJobSeekerList();
    }

    /**
     * AND goo!
     * @return 
     */
    
    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    /**
     * 
     * @return 
     */
    
    public List<PersonalDTO> getPersonalList() {
        return personalList;
    }

    /**
     * Method to retrieve a list of job seekers.
     * 
     * @return A list of PersonalDTO objects.
     */
    
    public List<PersonalDTO> getJobSeekerList() {
        return jobSeekerList;
    }

    /**
     * Method to modifiy a job seeker list.
     * 
     * @param jobSeekerList The job seeker list to be modified.
     */
    
    public void setJobSeekerList(List<PersonalDTO> jobSeekerList) {
        jobSeekerList = adminFacade.getJobSeekerList();
    }

    /**
     * Method to recognize the variable AccountList in the JSF pages.
     *
     * @param accountList accountlist.
     */
    public void setAccountList(List<AccountDTO> accountList) {
        accountList = loginFacade.getAccountList();
    }

    /**
     * Returns already exists status.
     *
     * @return Returns true if the user already exists False if the user does
     * not exists.
     */
    public boolean getUserAlreadyExistsStatus() {
        return uaes;
    }

    /**
     * Method to recognize UserAlreadyExsistsStatus in the JSF pages.
     *
     * @param uaes The userAlreadyExistsStatus that is going to be modified.
     */
    public void setUserAlreadyExistsStatus(boolean uaes) {
        this.uaes = uaes;
    }

    /**
     * Method to recognize setLoggedInStatus in the JSF pages.
     *
     * @param loggedin The loggedin status that is going to be modified.
     */
    public void setLoggedInStatus(boolean loggedin) {
        this.loggedIn = loggedin;
    }

    /**
     * Method to recognize setRegistrationSuccessfulStatus in the JSF pages.
     *
     * @param rss Returns true if the Registration was successful.
     */
    public void setRegistrationSuccessfulStatus(boolean rss) {
        this.rss = rss;
    }

    /**
     * Returns the registrationSuccessfulStatus
     *
     * @return True if the registration was successful otherwise false.
     */
    public boolean getRegistrationSuccessfulStatus() {
        return rss;
    }

    /**
     *
     * Return the loggedinstatus.
     *
     * @return True if a customer is logged in otherwise false.
     */
    public boolean getLoggedInStatus() {
        return loggedIn;
    }

    /**
     * Method to set the adminstatus to a user in the web interface.
     *
     * @param adminStatus True if admin is logged in otherwise false.
     */
    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    /**
     * Retrieves adminStatus value.
     *
     * @return if admin is logged in or not.
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }

    /**
     * Retrieves the roleID.
     * 
     * @return roleID.
     */
    
    public String getRole() {
        return role;
    }

    /**
     * Retrieves the name from the XHTML page.
     * 
     * @return name.
     */
    
    public String getName() {
        return name;
    }

    /**
     * Retrieves the surname from the XHTML page.
     * 
     * @return surname.
     */
    
    public String getSurname() {
        return surname;
    }

    /**
     * Retrieves the social security number (ssn) from XHTML page.
     * 
     * @return ssn.
     */
    
    public int getSsn() {
        return ssn;
    }

    /**
     * Returns the Email address of the user
     * @return Email address
     */
    
    public String getEmail() {
        return email;
    }

    /**
     * Set the role of the user
     * @param role represents the kind of role the user is getting
     */
    
    public void setRole(String role) {
        this.role = role;
    }

    /**
     *  Set the user's name
     * @param name name of the user 
     */
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the user's surname   
     * @param surname surname of the user
     */
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * set the social security number
     * @param ssn represents a person's social security number
     */
    
    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    /**
     * Set the email of the user
     * @param email string that represents the desired email address
     */
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * returns a person's "from" availability
     * @return the from availability
     */

    public String getAvailabilityFrom() {
        return availabilityFrom;
    }

    /**
     * set a job seekers availability
     * @param availabilityFrom the availability entered by the user
     */
    
    public void setAvailabilityFrom(String availabilityFrom) {
        this.availabilityFrom = availabilityFrom;
    }

    /**
     * returns the "to" availability of a user
     * @return the "to" abailability
     */
    
    public String getAvailabilityTo() {
        return availabilityTo;
    }

    /**
     * Set the "To" availability of a person    
     * @param availabilityTo  
     */
    
    public void setAvailabilityTo(String availabilityTo) {
        this.availabilityTo = availabilityTo;
    }

    /**
     * Retrives the competence of a person.
     * @return The competence of a person
     */
    
    public String getCompetence() {
        return competence;
    }

    /**
     * Set the competence of a person
     * @param competence 
     */
    
    public void setCompetence(String competence) {
        this.competence = competence;
    }

    /**
     * Returns the experience of a person
     * @return the experience
     */
    
    public String getExperience() {
        return experience;
    }

    /**
     * set the experience of a person 
     * @param experience the experience entered by the user
     */
    
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * Gets the entire PersonalDTO representing a person
     * @return the PersonalDTO object
     */
    
    public PersonalDTO getPersoninfo() {
        return personinfo;
    }

    /**
     * Set the Personal information 
     * @param personinfo the PersonalDTO containing all the information
     */
    
    public void setPersoninfo(PersonalDTO personinfo) {
        this.personinfo = personinfo;
    }

    /**
     * Returns the current account
     *
     * @return Returns the current account to the web interfaced.
     */
    public AccountDTO getAccount() {
        return account;
    }

    /**
     * Method to recognize account object in the JSF pages.
     *
     * @param account The current account object.
     */
    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    /**
     * Method to recognize username variable in the JSF pages.
     *
     * @param username The username that is going to be modified.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * Returns the username from the xhtml Username input field
     *
     * @return The username from the JSF pages.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to recognize pass object in the JSF pages.
     *
     * @param pass The password field that is going to be modified. (If ever).
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Retrieves the password from the Password input field in JSF pages.
     *
     * @return The specific password.
     */
    public String getPass() {
        return pass;
    }

    /**
     * This is called when an admin banhammers a customer from login page.
     *
     * @param status True if the account is banned, otherwise false.
     */
    public void setBannedStatus(boolean status) {
        this.status = status;
    }

    /**
     * Retrieves bannedstatus on the current customer account.
     *
     * @return The banned status boolean value.
     */
    public boolean getBannedStatus() {
        return status;
    }
    /**
     * Bans a customer from Admin interface
     *
     * Makes a call to the compFacade to update the account banned status of a
     * specific account in the database.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    public String banHammer() {
        try {
            adminFacade.updateStatus(getUsername(), true);
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
    /**
     * Updates the display of accounts in Admin.xhtml.
     */
    public void updateAccountList() {
        accountList = loginFacade.getAccountList();
    }

    /**
     * Method to change the loggedIn status, both set methods are called in
     * order to ensure that both have been logged out.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    public String logout() {
        try {
            setLoggedInStatus(false);
            setAdminStatus(false);
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Method that updates a Job seekers role (done by the admin)
     * @param personId the ID of the person who's role you want to change
     * @return an empty string when the update is successful
     */
    public String updateRole(int personId, String roleId) {
        try {
            startConversation();
            for (PersonalDTO jobseeker : getJobSeekerList()) {
                if (jobseeker.getId().equals(personId)) {
                    adminFacade.updateRole(personId, roleId);
                }
            }
            updateApplications();
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Method used when an admin does not want to accept an application. it removes the person along with everything 
     * connected to this person from the system
     * @param personId Id of the person that is to be removed
     * @return empty string on success,
     */
    public String rejectApplication(int personId) {
        try {
            startConversation();
            for (PersonalDTO jobseeker : getJobSeekerList()) {
                if (jobseeker.getId().equals(personId)) {

                    adminFacade.unregisterPersonal(personId);

                    adminFacade.unregisterAccount(personId);

                    adminFacade.unregisterAvailability(personId);

                    for (CompetenceDTO competence : jobseeker.getCompetence()) {
                        adminFacade.unregisterCompetence(competence.getId());
                    }

                    adminFacade.unregisterCompetenceProfile(personId);

                    for (RolesDTO role : jobseeker.getRole()) {
                        adminFacade.unregisterRole(role.getuId());
                    }
                }
            }

            updateApplications();
        } catch (Exception e) {
            handleException(e);
        }

        return jsf22Bugfix();
    }

    /**
     * Method used when logging into the sustem. it starts a conversation aswell as validates the
     * entered password against the hashed password for the person in the database. it also checks the
     * banned status.
     *
     * ConvManger is makeing a call to the ejb class ConvFacade which is getting
     * the value from the javaDB through JPA (a presistant unit).
     * @return empty string on success
     */
    public String login() {
        try {
            startConversation();
            Hash hash;
            loginuser = loginFacade.getAccount(getUsername());
            hash = new Hash(getPass());
            if (loginuser.getStatus() == true) {
                setBannedStatus(true);

            } else if ((loginuser.getRoleId().equals("1"))
                    && loginuser.getPassword().equals(hash.MakeHash())) {
                setAdminStatus(true);
                account = loginuser;
                updateApplications();

            } else if (loginuser.getUsername().equals(getUsername())
                    && loginuser.getPassword().equals(hash.MakeHash())) {
                setLoggedInStatus(true);
                account = loginuser;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("loginform:Username", 
                    new FacesMessage("Login failed: Wrong username or password"));
        }
        return jsf22Bugfix();
    }

    /**
     * Method that is called when a user registers from the user interface. it sets all the persnoal information 
     * about the new user and persists it in the database
     * @return empty string on success
     */
    public String register() {

        try {
            startConversation();
            loginuser = loginFacade.checkUserExists(getUsername());

            if (loginuser == null) {
                Random random;
                random = new Random();

                int personid = random.nextInt(100);
                int availableid = random.nextInt(100);
                int competenceid = random.nextInt(100);
                int competenceProfileId = random.nextInt(100);
                int roleId = random.nextInt(100);
                Roles adminRole = new Roles(roleId, 1, "Admin");
                Roles recruitRole = new Roles(roleId, 2, "Recruit");
                Roles jobSeekerRole = new Roles(roleId, 3, "Job seeker");
                Competence competence;
                Hash hash;
                if (getRole().equals("1")) {

                    Availability av = new Availability(availableid, personid, getAvailabilityFrom(), getAvailabilityTo());
                    CompetenceProfile cp = new CompetenceProfile(competenceProfileId, personid, competenceid, Double.parseDouble(getExperience()));
                    hash = new Hash(getPass());
                    competence = new Competence(competenceid, getCompetence());
                    adminFacade.register(personid, getUsername(), hash.MakeHash(), getRole());
                    adminFacade.registerPersonal(personid, getName(), getSurname(), getSsn(), getEmail(), getRole(), adminRole, av, competence, cp);

                } else if (getRole().equals("2")) {
                    Availability av = new Availability(availableid, personid, getAvailabilityFrom(), getAvailabilityTo());
                    CompetenceProfile cp = new CompetenceProfile(competenceProfileId, personid, competenceid, Double.parseDouble(getExperience()));
                    hash = new Hash(getPass());
                    competence = new Competence(competenceid, getCompetence());
                    adminFacade.register(personid, getUsername(), hash.MakeHash(), getRole());
                    adminFacade.registerPersonal(personid, getName(), getSurname(), getSsn(), getEmail(), getRole(), recruitRole, av, competence, cp);
                } else if (getRole().equals("3")) {
                    Availability av = new Availability(availableid, personid, getAvailabilityFrom(), getAvailabilityTo());
                    CompetenceProfile cp = new CompetenceProfile(competenceProfileId, personid, competenceid, Double.parseDouble(getExperience()));
                    hash = new Hash(getPass());
                    competence = new Competence(competenceid, getCompetence());
                    adminFacade.register(personid, getUsername(), hash.MakeHash(), getRole());
                    adminFacade.registerPersonal(personid, getName(), getSurname(), getSsn(), getEmail(), getRole(), jobSeekerRole, av, competence, cp);
                }
                setRegistrationSuccessfulStatus(true);
            } else {
                setUserAlreadyExistsStatus(true);
                setRegistrationSuccessfulStatus(false);
            }
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }
}