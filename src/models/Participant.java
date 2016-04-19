/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author User
 */
public class Participant {
    private String pseudo;
    private String firstname;
    private String lastname;

    public Participant() {
        pseudo = "";
        firstname = "";
        lastname = "";
    }

    public Participant(String pseudo, String firstname, String lastname) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public String getPseudo() { return pseudo; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    
}
