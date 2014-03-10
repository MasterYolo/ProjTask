/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ExceptionHandler;

/**
 *
 * @author Micke
 */
public class LoginException extends Throwable {
 
    String message;
    public LoginException(String message,Exception e) {
        this.message = message;
    }
}
