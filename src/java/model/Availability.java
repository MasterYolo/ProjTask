package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date availabilityFrom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date availabilityTo;

    public Availability() {
    }

    public Availability(int id, int personal_id, Date availabilityFrom, Date availabilityTo) {
        this.id = id;
        this.personal_id = personal_id;
        this.availabilityFrom = availabilityFrom;
        this.availabilityTo = availabilityTo;
    }

    public Integer getId() {
        return id;
    }

    public Date getAvailabilityFrom() {
        return availabilityFrom;
    }

    public Date getAvailabilityTo() {
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

    public void setAvailabiltyFrom(Date availabityFrom) {
        this.availabilityFrom = availabityFrom;
    }

    public void setAvailabilityTo(Date availabilityTo) {
        this.availabilityTo = availabilityTo;
    }
}
