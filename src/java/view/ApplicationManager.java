package view;

import controller.CompanyFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.AccountDTO;
import model.Availability;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.PersonalDTO;

/**
 * Changelog: -added roleid in register (Account)
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
 * @author Mikael and Max
 */
@Named("manager")
@SessionScoped
public class ApplicationManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    /* Instance object variables */
    private CompanyFacade compFacade;
    private AccountDTO loginuser;
    private AccountDTO account;

    private CompetenceProfileDTO comptenceProfile;
    private PersonalDTO personinfo;
    private PersonalDTO jobSeeker;

    /* Array lists used in the view */
    private List<PersonalDTO> personalList = new ArrayList<PersonalDTO>();
    private List<AccountDTO> accountList = new ArrayList<AccountDTO>();
    private List<AvailabilityDTO> availabilityList = new ArrayList<AvailabilityDTO>();
    private List<CompetenceDTO> competenceList = new ArrayList<CompetenceDTO>();
    private List<CompetenceProfileDTO> competenceProfileList = new ArrayList<CompetenceProfileDTO>();
    private List<PersonalDTO> jobSeekerList = new ArrayList<PersonalDTO>();
    private Map<Integer, String> jobSeekerAvailability = new HashMap<Integer, String>();
    //private List<ProductDTO> productList = new ArrayList<ProductDTO>();
    // private List<ProductDTO> shoppingList = new ArrayList<ProductDTO>();

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
    //private String personId;
    //  private int id;
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
        jobSeekerList = compFacade.getJobSeekerList();
    }

    public Map<Integer, String> getJobSeekerAvailability() {
        return jobSeekerAvailability;
    }

    public void setJobSeekerAvailability(Map<Integer, String> jobSeekerAvailability) {
        this.jobSeekerAvailability = jobSeekerAvailability;
    }

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public List<PersonalDTO> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<PersonalDTO> personalList) {
        personalList = compFacade.getPersonalList();
    }

    public List<PersonalDTO> getJobSeekerList() {
        return jobSeekerList;
    }

    public void setJobSeekerList(List<PersonalDTO> jobSeekerList) {
        jobSeekerList = compFacade.getJobSeekerList();
    }

    public List<AvailabilityDTO> getAvailabilityList() {
        return availabilityList;
    }

    /**
     * Method to recognize the variable AccountList in the JSF pages.
     *
     * @param accountList accountlist.
     */
    public void setAccountList(List<AccountDTO> accountList) {
        accountList = compFacade.getAccountList();
    }

    /**
     * Method to fetch ProductList.
     *
     * @return List of all the products from the database.
     */
    /*public List<ProductDTO> getProductList() {
     return productList;
     }
     */
    /**
     * Method to recognize the variable ProductList in the JSF page.
     *
     * @param productList Variable used to enable write rights in the web
     * interface.
     */
    /*public void setProductList(List<ProductDTO> productList) {
     productList = compFacade.getProductList();
     }
     */
    /**
     * Method to fetch a customers product List.
     *
     * @return The current shopping list for the current customer.
     */
    /*public List<ProductDTO> getShoppingList() {
     return shoppingList;
     }
     */
    /**
     * Method to recognize the variable AccountList in the JSF pages.
     *
     * @param shoppingList The shopping list for a specific customer.
     */
    /*public void setShoppingList(List<ProductDTO> shoppingList) {
     this.shoppingList = shoppingList;
     }
     */
    /**
     * Method to get the product name field from the JSF pages.
     *
     * @return Returns text from the product name input field in the
     * customer.xhtml page.
     */
    /*public String getProductname() {
     return productname;
     }
     */
    /**
     * Method to recognize productname in the JSF pages.
     *
     * @param productname The Productname that is going to be modified.
     */
    /*public void setProductname(String productname) {
     this.productname = productname;
     }
     */
    /**
     * Method to recognize amount in the JSF pages.
     *
     * @param amount The amount that is going to be modified.
     */
    /*    public void setAmount(String amount) {
     this.amount = amount;
     }
     */
    /**
     * Retrieves the input from the amunt field in either admin.xhtml or
     * customer.xhtml.
     *
     * @return
     */
    /*  public String getAmount() {
     return amount;
     }
     */
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
     *
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

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailabilityFrom() {
        return availabilityFrom;
    }

    public void setAvailabilityFrom(String availabilityFrom) {
        this.availabilityFrom = availabilityFrom;
    }

    public String getAvailabilityTo() {
        return availabilityTo;
    }

    public void setAvailabilityTo(String availabilityTo) {
        this.availabilityTo = availabilityTo;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public PersonalDTO getPersoninfo() {
        return personinfo;
    }

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
     * Method call to purchase the selected products from customers basket.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    /*public String purchaseProducts() {
     try {
     shoppingList.removeAll(shoppingList);
     } catch (Exception e) {
     handleException(e);
     }
     return jsf22Bugfix();
     }
     */
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
            compFacade.updateStatus(getUsername(), true);
        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Method called when you press the "Remove amount" button in the JSF page.
     *
     * The method passes the product name (It's actually the productId). It is
     * sent to the compfacade which changes the record in the DB.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    /*public String update() {
     try {
     startConversation();
     compFacade.updateProduct(getProductname(), Integer.parseInt(getAmount()));
     Product product = new Product(getProductname(), Integer.parseInt(getAmount()));
     shoppingList.add(product);
     } catch (Exception e) {
     handleException(e);
     }
     return jsf22Bugfix();
     }
     */
    /**
     * Updates the display of accounts in Admin.xhtml.
     */
    public void updateAccountList() {
        accountList = compFacade.getAccountList();
    }

    /**
     * Updates the display of products in both Customer.xhtml and Admin.xhtml
     */
    /*public void updateProductList() {
     productList = compFacade.getProductList();
     }
     */
    /**
     * Method used to call the compFacade method updateProduct(), which removes
     * a specific amount of a product (article) from the productlist and from
     * DB.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    /*public String unregisterProductAmount() {
     try {
     startConversation();
     compFacade.updateProduct(getProductname(), Integer.parseInt(getAmount()));
     } catch (Exception e) {
     handleException(e);
     }
     return jsf22Bugfix();
     }
     */
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
     * Method used to call the compfacade method which, erase a product
     * completley from the database.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    /*public String unregisterProduct() {
     try {
     startConversation();
     compFacade.unregisterProduct(getProductname());
     } catch (Exception e) {
     handleException(e);
     }
     return jsf22Bugfix();
     }
     */
    /**
     * Method to register product from Admin.xhtml interface. Method creates a
     * new product object and passes it to the compFacade method
     * registerProduct() to store the new product in the DB.
     *
     * @return Empty string, if transaction succeeds, otherwise the occured
     * error.
     */
    /*public String registerProduct() {
     try {
     startConversation();
     Product product1 = new Product(getProductname(), Integer.parseInt(getAmount()));
     compFacade.registerProduct(product1);
     } catch (Exception e) {
     handleException(e);
     }
     return jsf22Bugfix();
     }
     */
    /**
     * Here's where the magic is happening!
     *
     * ConvManger is makeing a call to the ejb class ConvFacade which is getting
     * the value from the javaDB through JPA (a presistant unit).
     */
    public String login() {
        try {
            startConversation();
            loginuser = compFacade.getAccount(getUsername());
            if (loginuser.getStatus() == true) {
                setBannedStatus(true);

            } else if ((loginuser.getUsername().equals("admin"))
                    && loginuser.getPassword().equals(getPass())) {
                //setLoggedInStatus(true);
                setAdminStatus(true);
                account = loginuser;

            } else if (loginuser.getUsername().equals(getUsername())
                    && loginuser.getPassword().equals(getPass())) {
                setLoggedInStatus(true);
                account = loginuser;
            }

        } catch (Exception e) {
            handleException(e);
        }
        return jsf22Bugfix();
    }

    /**
     * Here's where the magic is happening!
     *
     * ConvManger is makeing a call to the ejb class ConvFacade which is getting
     * the value from the javaDB through JPA (a presistant unit).
     */
    public String register() {

        try {
            startConversation();
            loginuser = compFacade.checkUserExists(getUsername());

            if (loginuser == null) {
                Random random;
                random = new Random();

                int personid = random.nextInt(100);
                int availableid = random.nextInt(100);
                int competenceid = random.nextInt(100);
                int competenceProfileId = random.nextInt(100);
                Availability av = new Availability(availableid, personid, getAvailabilityFrom(), getAvailabilityTo());
                compFacade.register(personid, getUsername(), getPass(), getRole());
                compFacade.registerPersonal(personid, getName(), getSurname(), getSsn(), getEmail(), getRole(),av);
                //compFacade.registerAvailability(availableid, personid, getAvailabilityFrom(), getAvailabilityTo());
                compFacade.registerCompetence(competenceid, getCompetence());
                compFacade.registerCompetenceProfile(competenceProfileId, personid, competenceid, Double.parseDouble(getExperience()));
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
