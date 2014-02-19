/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Account;
import model.AccountDTO;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.PersonalDTO;
import observer.Notifier;

/**
 *
 * @author Micke
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class LoginFacade {

    /* Presistant configurations */
    @PersistenceContext(unitName = "ProjTaskPU")
    private EntityManager em;

    /* Array lists that is going to be retrived from the database and passed to
     the manager class in the view layer.
     */
    private List<AccountDTO> accountList = new ArrayList<AccountDTO>();
    private PersonalDTO personalDTO;
    private List<PersonalDTO> personalList = new ArrayList<PersonalDTO>();
    private List<AvailabilityDTO> availabilityList = new ArrayList<AvailabilityDTO>();
    private List<CompetenceDTO> competenceList = new ArrayList<CompetenceDTO>();
    private List<CompetenceProfileDTO> competenceProfileList = new ArrayList<CompetenceProfileDTO>();

    Notifier observer = new Notifier();
    Date date = new Date();

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
    public AccountDTO getAccount(String username) throws EntityNotFoundException {
        

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

}
