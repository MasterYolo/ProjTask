package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Account;
import model.AccountDTO;
import model.Availability;
import model.AvailabilityDTO;
import model.Competence;
import model.CompetenceDTO;
import model.CompetenceProfile;
import model.CompetenceProfileDTO;
import model.Personal;
import model.PersonalDTO;
import model.Roles;
import model.RolesDTO;

/**
 * Changelog: -added roleid to method register() -added registerCompetence()
 * -added registerCompetenceProfile()
 *
 * A controller. All calls to the model that are executed because of an action
 * taken by the cashier pass through here.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CompanyFacade {
    /* Presistant configurations */

    @PersistenceContext(unitName = "ProjTaskPU")
    private EntityManager em;

    /* Array lists that is going to be retrived from the database and passed to
     the manager class in the view layer.
     */
    //private List<ProductDTO> productList = new ArrayList<ProductDTO>();
    private List<AccountDTO> accountList = new ArrayList<AccountDTO>();

    private PersonalDTO personalDTO;

    private List<PersonalDTO> personalList = new ArrayList<PersonalDTO>();
    private List<AvailabilityDTO> availabilityList = new ArrayList<AvailabilityDTO>();
    private List<CompetenceDTO> competenceList = new ArrayList<CompetenceDTO>();
    private List<CompetenceProfileDTO> competenceProfileList = new ArrayList<CompetenceProfileDTO>();

    /**
     * Method to register a new account in the database.
     *
     * The method uses an AccountDTO object to define the newly created account.
     *
     * @param username The specified username from Login.xhtml, passed through
     * Manager.java.
     * @param password The specified password from Login.xhtml, passed through
     * Manager.java.
     * @return AccountDTO object, which is the new account.
     */
    public AccountDTO register(int id, String username, String password, String roleId) {
        Account acc = new Account(username, password, false, id, roleId);
        em.persist(acc);
        return acc;
    }

    public AvailabilityDTO registerAvailability(int id, int personId, String availabilityFrom, String availabilityTo) {
        Availability available = new Availability(id, personId, availabilityFrom, availabilityTo);
        em.persist(available);
        return available;
    }

    /**
     *
     * @param id
     * @param name
     * @param surname
     * @param ssn
     * @param email
     * @return
     */
    public PersonalDTO registerPersonal(int id, String name, String surname, int ssn, String email, String role_id) {
        Personal pers = new Personal(id, name, surname, ssn, email, role_id);
        em.persist(pers);
        return pers;
    }

    /**
     *
     * @param id
     * @param name
     * @return
     */
    public CompetenceDTO registerCompetence(int id, String name) {
        Competence comp = new Competence(id, name);
        em.persist(comp);
        return comp;
    }

    public CompetenceProfileDTO registerCompetenceProfile(int competence_profile_id,
            int person_id, int competence_id, Double experience) {
        CompetenceProfile compProfile
                = new CompetenceProfile(competence_profile_id, person_id, competence_id, experience);
        em.persist(compProfile);
        return compProfile;
    }

    /**
     *
     * @param id
     * @param name
     * @return
     */
    public RolesDTO registerRole(int id, String name) {
        Roles roles = new Roles(id, name);
        em.persist(roles);
        return roles;
    }

    /**
     * Method to check if a user (or admin) is registered in the database.
     *
     * @param username Search value for finding a specific user.
     * @return Null if the username does not exists otherwise it returns the
     * entered username.
     */
    public AccountDTO checkUserExists(String username) {
        Account account = em.find(Account.class, username);
        return account;
    }

    /**
     * Method to retrieve account from database.
     *
     * The if statement is used to throw exception if no account was found.
     *
     * @param username Username is used as a search value in the database, as
     * primary key.
     * @return If the username exists, return it. Otherwise throw exception.
     */
    public AccountDTO getAccount(String username) {

        Account account = em.find(Account.class, username);
        if (account == null) {
            throw new EntityNotFoundException("No account with that name: " + username);
        }
        return account;
    }
    /**
     * Method to retrieve a list of available accounts from the database.
     *
     * @return The list of available accounts from the database.
     */
    public List<AccountDTO> getAccountList() {
        Query query = em.createQuery("SELECT a FROM Account a");
        accountList = query.getResultList();
        return accountList;
    }

    public List<PersonalDTO> getJobSeekerList() {
        Query query = em.createQuery("SELECT p FROM Personal p WHERE p.role_id = '3'");
        personalList = query.getResultList();
        return personalList;
    }

    public List<CompetenceDTO> getCompetenceList(int id) {
        Query query = em.createQuery("SELECT c FROM Competence c WHERE c.id = :id");
        query.setParameter("id", id);
        competenceList = query.getResultList();
        return competenceList;
    }

    public List<PersonalDTO> getPersonalList() {
        Query query = em.createQuery("SELECT p from Personal p");
        personalList = query.getResultList();
        return personalList;
    }

    /**
     * Method to insert a new product amount into a specific product in the
     * database.
     *
     * This method is used in Admin.xhtml as well as in Customer.xhtml during
     * purchase.
     *
     * @param id The specific product ID.
     * @param amount The specific amount.
     * @return Updated product (with a new amount).
     */
    /*    public ProductDTO updateProduct(String id, int amount) {
     Product product = em.find(Product.class, id);
     product.getAmount();
     Query query = em.createQuery("UPDATE Product p SET p.amount = :amount WHERE p.id = :id");
     query.setParameter("amount", product.getAmount() - amount);
     query.setParameter("id", id);
     query.executeUpdate();
     return product;
     }
     */
    /**
     * Method to return the product list from database.
     *
     * @return A list of the available products.
     */
    /*  public List<ProductDTO> getProductList() {
     Query query = em.createQuery("SELECT p FROM Product p");
     productList = query.getResultList();
     return productList;
     }
     */
    /**
     * Registers a new product object and sends to database.
     *
     * @param product The specific product entity to be inserted.
     * @return The newly registered product.
     */
    /*public ProductDTO registerProduct(Product product) {
     em.persist(product);
     return product;
     }
     */
    /**
     * Method to unregister (erase) product from the database.
     *
     * @param id The primary key to find the specific entity.
     */
    public void unregisterProduct(String id) {
        Query query = em.createQuery("DELETE FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to update the banned status of an existing customer (or admin) in
     * the database.
     *
     * @param name The name of the user, which the primary key in the database.
     * @param status The applied status. True for banned, false for not banned.
     * @return The updated account.
     */
    public AccountDTO updateStatus(String name, boolean status) {
        Account acc = em.find(Account.class, name);
        Query query = em.createQuery("UPDATE Account a SET a.banned = :status WHERE a.username = :name");
        query.setParameter("name", name);
        query.setParameter("status", status);
        query.executeUpdate();
        return acc;
    }
}
