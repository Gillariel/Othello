/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author User
 */
public class Person {
    private final SimpleStringProperty pseudo;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private final SimpleIntegerProperty wonGames;
    private final SimpleIntegerProperty lostGames;
    
    public Person(String pseudo, String firstname, String lastname, int wongames, int lostgames) {
        this.pseudo = new SimpleStringProperty(pseudo);
        this.firstname = new SimpleStringProperty(firstname);
        this.lastname = new SimpleStringProperty(lastname);
        this.wonGames = new SimpleIntegerProperty(wongames);
        this.lostGames = new SimpleIntegerProperty(lostgames);
    }

    public String getPseudo() { return pseudo.get(); }
    public String getFirstname() { return firstname.get(); }
    public String getLastname() { return lastname.get(); }
    public int getWonGames() { return wonGames.get(); }
    public int getLostGames() { return lostGames.get(); } 
    
    public void setPseudo(String pseudo) { this.pseudo.set(pseudo); }
    public void setFirstname(String firstname) { this.firstname.set(firstname); }
    public void setLastname(String lastname) { this.lastname.set(lastname); }
    public void setWonGames(int wonGames) { this.wonGames.set(wonGames); }
    public void setLostGames(int lostGames) { this.lostGames.set(lostGames); }
}
