package model;

import java.util.List;

public interface PersonalDTO {

    /**
     * Method to recieve the id of a specific person.
     * @return Returns the id of a specific person.
     */
    Integer getId();

    /**
     * Method to recieve the name of a specific person.
     * @return Returns the name of a specific person.
     */
    String getName();

    /**
     * Method to recieve the surname of a specific person. 
     * @return Returns the surname of a specific person.
     */
    String getSurname();

    /**
     * Method to recive the social security number of a specific person.
     * @return Returns the ssn of specific person.
     */
    int getSsn();

    /**
     * Method to recive the email of specific person.
     * @return Returns the email of specific person.
     */
    String getEmail();

    /**
     * Method to recive the roleId of specific person.
     * @return Returns the roleId of a specific person.
     */
    String getRoleId();

    /**
     * Method to modify the role id of specific person.
     * @param roleId The roleId that is going to be modified.
     */
    void setRoleId(String roleId);

    /**
     * Method to recieve the list of competences for a specific person.
     * @return Returns a list of competencess for a specific person.
     */
    List<Competence> getCompetence();

    /**
     * Method to modify a list of a list of competences for a specific person.
     * @param competence The competence list
     */
    void setCompetence(List<Competence> competence);

    /**
     * Method to recive the list of roles for a specific person.
     * @return Returns the list of roles for a specific person.
     */
    List<Roles> getRole();

    /**
     * Method to modify a list of roles for a specific person.
     * @param role The role that is going to be modified.
     */
    void setRole(List<Roles> role);

    /**
     * Method to recieve a list of availabilities for a specific person.
     * @return Returns the list of availabilities for a specific person.
     */
    List<Availability> getAv();

    /**
     * Method to modifiy a list of availabilities for a specific person.
     * @param av The availability that is going to be modified.
     */
    void setAv(List<Availability> av);

    /**
     * Method to recieve a list of competenceProfiles for a specific person.
     * @return Return a list of competenceProfiles for a specific person.
     */
    List<CompetenceProfile> getCompetenceProfile();

}
