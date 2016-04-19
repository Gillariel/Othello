/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import models.Participant;

/**
 *
 * @author User
 */
public class ParticipantsManager extends DbConnect {

    public ParticipantsManager() { super();}
    
    public String[][] selectParticipant(Participant p) {
        String[][] result = this.getDb().createStatement("SELECT pseudo, firstname, lastname"
                + "FROM PARTICIPANT"
                + "WHERE pseudo LIKE @pseudo")
                .bindParameter("@pseudo", p.getPseudo())
                .executeQuery();
        return (result.length > 0)? result : null;
    }
    
    
}
