package model;

import java.util.List;

public interface PersonalDTO {

    Integer getId();
    
    String getName();

    String getSurname();

    int getSsn();

    String getEmail();
    
    String getRoleId();
    
    void setRoleId(String roleId);
    
    List<Competence> getCompetence();
    
    void setCompetence(List<Competence> competence);
    
    List<Roles> getRole();
    
    void setRole(List<Roles> role);
    
    List<Availability> getAv();
    
    void setAv(List<Availability> av);
}
