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

    private String name;
    private String surname;
    private int ssn;
    private String email;
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<Availability> av = new ArrayList();

    public List<Availability> getAv() {
        return av;
    }

    public void setAv(List<Availability> av) {
        this.av = av;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<Roles> role = new ArrayList();

    public List<Roles> getRole() {
        return role;
    }

    public void setRole(List<Roles> role) {
        this.role = role;
    }
    @OneToMany(cascade = CascadeType.ALL)
    List<Competence> competence = new ArrayList();

    public List<Competence> getCompetence() {
        return competence;
    }

    public void setCompetence(List<Competence> competence) {
        this.competence = competence;
    }

    @OneToMany(cascade = CascadeType.ALL)
    List<CompetenceProfile> competenceProfile = new ArrayList();

    public List<CompetenceProfile> getCompetenceProfile() {
        return competenceProfile;
    }

    public void setCompetenceProfile(List<CompetenceProfile> competenceProfile) {
        this.competenceProfile = competenceProfile;
    }

    public Personal() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
