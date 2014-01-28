/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class CompetenceProfile implements Serializable, CompetenceProfileDTO {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer competence_profile_id;
    private Integer person_id;
    private Integer competence_id;
    private Double experience;

    public CompetenceProfile() {}
    
    public CompetenceProfile(int competence_profile_id,int person_id, int competence_id, Double experience) {
        this.competence_profile_id = competence_profile_id;
        this.person_id = person_id;
        this.competence_id = competence_id;
        this.experience = experience;
    }
    
    public Integer getCompetence_profile_id() {
        return competence_profile_id;
    }

    public void setCompetence_profile_id(Integer competence_profile_id) {
        this.competence_profile_id = competence_profile_id;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer competence_id) {
        this.competence_id = competence_id;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }
    
    
}
