/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Micke
 */
public interface HashInterface {
    String MakeHash() throws NoSuchAlgorithmException;
}
