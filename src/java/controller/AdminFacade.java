/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
import observer.Notifier;

/**
 *
 * @author Micke
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AdminFacade {

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

    /**
     * Method to register availability.
     *
     * @param id For a specific person.
     * @param personId A persons id.
     * @param availabilityFrom The from date of availability.
     * @param availabilityTo The to date of availability.
     * @return The AvailabilityDTO object.
     */
    public AvailabilityDTO registerAvailability(int id, int personId, String availabilityFrom, String availabilityTo) {
        Availability available = new Availability(id, personId, availabilityFrom, availabilityTo);
        em.persist(available);
        return available;
    }

    /**
     * Method to register an entry in Personal table.
     *
     * @param id Unique identifier for a specific person.
     * @param name The name of a specific person.
     * @param surname The surname of a specific person.
     * @param ssn The social security number for a specific person.
     * @param email The email for a specific person.
     * @return The PersonalDTO object.
     */
    public PersonalDTO registerPersonal(int id, String name, String surname, int ssn,
            String email, String roleId, Roles role,
            Availability av, Competence competence, CompetenceProfile competenceProfile) {
        Personal pers = new Personal(id, name, surname, ssn, email, roleId, role, av, competence, competenceProfile);
        em.persist(pers);
        return pers;
    }

    /**
     * Method used to update role.
     *
     * @param id The unique identifier for the person which role is going to be
     * modified.
     * @param roleId The specific role of a person.
     * @return PersonalDTO object.
     */
    public PersonalDTO updateRole(int id, String roleId) {
        Personal pers = em.find(Personal.class, id);
        Query query = em.createQuery("UPDATE Personal p SET p.roleId = :roleId WHERE p.id = :id");
        query.setParameter("id", id);
        query.setParameter("roleId", roleId);
        query.executeUpdate();
        return pers;
    }

    /**
     * Method used to remove a person from the Account table.
     *
     * @param id Unique identifier.
     */
    public void unregisterAccount(int id) {
        Query query = em.createQuery("DELETE FROM Account a WHERE a.personalId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to unregister availability entry in the database.
     *
     * @param id Unique identifier.
     */
    public void unregisterAvailability(int id) {
        Query query = em.createQuery("DELETE FROM Availability a WHERE a.personal_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to unregister competence entry in the database.
     *
     * @param id Unique identifier.
     */
    public void unregisterCompetence(int id) {
        Query query = em.createQuery("DELETE FROM Competence c WHERE c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to unregister competence profile entry in the database.
     *
     * @param id Unique identifier.
     */
    public void unregisterCompetenceProfile(int id) {
        Query query = em.createQuery("DELETE FROM CompetenceProfile cp WHERE cp.person_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to unregister personal entry in the database.
     *
     * @param id Unique identifier.
     */
    public void unregisterPersonal(int id) {
        Query query = em.createQuery("DELETE FROM Personal p WHERE p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to unregister role entry in the database.
     *
     * @param id Unique identifier.
     */
    public void unregisterRole(int id) {
        Query query = em.createQuery("DELETE FROM Roles r WHERE r.uId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Method to register competence for a specific person.
     *
     * @param id Unique identifier.
     * @param name Name of the potential competence.
     * @return The CompetenceDTO object.
     */
    public CompetenceDTO registerCompetence(int id, String name) {
        Competence comp = new Competence(id, name);
        em.persist(comp);
        return comp;
    }

    /**
     * Method to register competence profile for a specific person.
     *
     * @param competence_profile_id Unique identifier of a specific competence
     * profile.
     * @param person_id Unique identifier of a specific person.
     * @param competence_id Unique identifier of a specific competence.
     * @param experience Years of experience.
     * @return The CompetenceProfileDTO object.
     */
    public CompetenceProfileDTO registerCompetenceProfile(int competence_profile_id,
            int person_id, int competence_id, Double experience) {
        CompetenceProfile compProfile
                = new CompetenceProfile(competence_profile_id, person_id, competence_id, experience);
        em.persist(compProfile);
        return compProfile;
    }

    /**
     * Method to register role for a specific person.
     *
     * @param uid Unique identifier for a specific role.
     * @param id Identifier of a specific role.
     * @param name Name of the role.
     * @return The RolesDTO object.
     */
    public RolesDTO registerRole(int uid, int id, String name) {
        Roles roles = new Roles(uid, id, name);
        em.persist(roles);
        return roles;
    }

    /**
     * Method to retrieve a list of job-seekers.
     *
     * @return A list of PersonalDTO objects.
     */
    public List<PersonalDTO> getJobSeekerList() {
        Query query = em.createQuery("SELECT p FROM Personal p WHERE p.roleId = '3'");
        personalList = query.getResultList();
        return personalList;
    }

    /**
     * Method to retrive a list of competence for a specific person.
     *
     * @param id The unique identifier of a specific person.
     * @return A list of CompetenceDTO objects.
     */
    public List<CompetenceDTO> getCompetenceList(int id) {
        Query query = em.createQuery("SELECT c FROM Competence c WHERE c.id = :id");
        query.setParameter("id", id);
        competenceList = query.getResultList();
        return competenceList;
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
