/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import utils.SHA256;

/**
 *
 * @author User
 */
public class LoginManager extends DbConnect{

    public LoginManager() { super(); }
    
    public boolean Authenticate(String pseudo, String password) {
        String hashPassword = SHA256.encode(password);
        String[][] result = getDb().createStatement("SELECT pseudo, firstname, lastname"
                + "FROM PARTICIPANT"
                + "WHERE pseudo LIKE @pseudo AND password LIKE @password")
                .bindParameter("@pseudo", pseudo)
                .bindParameter("@password", hashPassword)
                .executeQuery();
        return (result.length == 1)? true : false;
    }
    
    
}
