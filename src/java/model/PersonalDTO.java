package model;

import java.util.List;

public interface PersonalDTO {

    Integer getId();
    
    String getName();

    String getSurname();

    int getSsn();

    String getEmail();
    
    List<Availability> getAv();
    
    void setAv(List<Availability> av);
}
