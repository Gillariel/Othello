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
public class Contender {
    private String pseudo;
   

    public Contender() {
        pseudo = "";
       
    }

    public Contender(String pseudo) {
        this.pseudo = pseudo;
        
    }
    
   
    
    public String getPseudo() { return pseudo; }
   

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
   
   
}
