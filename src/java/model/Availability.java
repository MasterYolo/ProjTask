package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Micke
 */
@Entity
public class Availability implements Serializable, AvailabilityDTO {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer personal_id;
    private String availabilityFrom;
    private String availabilityTo;

    public Availability() {
    }

    public Availability(int id, int personal_id, String availabilityFrom, String availabilityTo) {
        this.id = id;
        this.personal_id = personal_id;
        this.availabilityFrom = availabilityFrom;
        this.availabilityTo = availabilityTo;
    }

    public Integer getId() {
        return id;
    }

    public String getAvailabilityFrom() {
        return availabilityFrom;
    }

    public String getAvailabilityTo() {
        return availabilityTo;
    }

    public Integer getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(Integer personal_id) {
        this.personal_id = personal_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAvailabiltyFrom(String availabityFrom) {
        this.availabilityFrom = availabityFrom;
    }

    public void setAvailabilityTo(String availabilityTo) {
        this.availabilityTo = availabilityTo;
    }
}
