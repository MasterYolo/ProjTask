package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Changelog: -added role.
 *
 * @author Micke
 */
@Entity
public class Personal implements PersonalDTO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    /* Personal variables */
    private String name;
    private String surname;
    private int ssn;
    private String email;
    private String roleId;

    
    /**
     * Gets the roleId
     * 
     * @return Returns the roleId.
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * Sets the roleId
     * @param roleId Sets the roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<Availability> av = new ArrayList();
    /**
     * Method to recieve the list of availabilities for a specific person.
     * @return The list of availabilities for a specific person
     */
    
    public List<Availability> getAv() {
        return av;
    }

    /**
     * Method to modify a specific list of availabilities.
     * @param av The availability that is going to be modified.
     */
    public void setAv(List<Availability> av) {
        this.av = av;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<Roles> role = new ArrayList();

    /**
     * Method to receive a list of roles for a specific person.
     * @return The list of roles for a specific person.
     */
    public List<Roles> getRole() {
        return role;
    }

    /**
     * Method to modify a specific list of roles.
     * @param role The role that is going to be modified.
     */
    public void setRole(List<Roles> role) {
        this.role = role;
    }
    @OneToMany(cascade = CascadeType.ALL)
    List<Competence> competence = new ArrayList();

    /**
     * Metod to recieve a list of competence for a specific person
     * @return The list of competence for a specific person.
     */
    public List<Competence> getCompetence() {
        return competence;
    }

    /**
     * Method to modify a specific list of competences.
     * @param competence The list of competence that is going to be modified
     */
    public void setCompetence(List<Competence> competence) {
        this.competence = competence;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<CompetenceProfile> competenceProfile = new ArrayList();

    /**
     * Method to recieve a list of cometenceProfiles for a specific person.
     * @return The list of competenceProfiles for a specific person.
     */
    public List<CompetenceProfile> getCompetenceProfile() {
        return competenceProfile;
    }

    /**
     * Method to modify a specific list of competenceProfiles.
     * @param competenceProfile The list of competenceProfile that is going to
     * be modified.
     */
    public void setCompetenceProfile(List<CompetenceProfile> competenceProfile) {
        this.competenceProfile = competenceProfile;
    }

    /**
     * Constructor needed when it is an entity.
     */
    public Personal() {
    }

    /**
     * Constructor that is used to put the info in the entity.
     * @param id The unique id for a specific person (generated in View).
     * @param name The name of a person.
     * @param surname The surname of a person.
     * @param ssn The social security number of a person.
     * @param email The email of a person.
     * @param roleId The roleId of a person.
     * @param role The role (as an object) of a person.
     * @param av The availability object of a person.
     * @param competence The competence object of a person.
     * @param competenceProfile The competenceProfile object of a person.
     */
    public Personal(int id, String name, String surname, int ssn, String email, String roleId, Roles role, Availability av, Competence competence, CompetenceProfile competenceProfile) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
        this.roleId = roleId;
        this.role.add(role);
        this.av.add(av);
        this.competence.add(competence);
        this.competenceProfile.add(competenceProfile);
    }

    /**
     * Method to recieve the id of a specific person.
     * @return Returns the id of a specific person.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to modify the specific id of a person.
     * @param id The id that is going to be modified.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method to recive the name of a specific person.
     * @return Returns the name of a specific person
     */
    public String getName() {
        return name;
    }

    /**
     * Method to modify the name of a specific person.
     * @param name The name that is going to be modified.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to recieve the surname of a specific person.
     * @return Returns the surname of a specific person.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Method to modify the surname of a specific person.
     * @param surname The surname that is going to be modified.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Method to retrive the Social security number of a specific person.s
     * @return Returns the ssn of a specific person.
     */
    public int getSsn() {
        return ssn;
    }

    /**
     * Method to modify the ssn of a specific person.
     * @param ssn The ssn that is going to be modified.
     */
    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    /**
     * Method to recieve the email of a specific person.
     * @return The email of a specific person.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to modify the email of a specific person.
     * @param email The email that is going to be modified.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
