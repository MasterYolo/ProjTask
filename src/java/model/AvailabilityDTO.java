/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author Micke
 */
public interface AvailabilityDTO {

    Integer getId();
    
    Date getAvailabilityFrom();
    
    Date getAvailabilityTo();
    
}
