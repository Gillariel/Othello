/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import java.util.List;
import models.Participant;

/**
 *
 * @author User
 */
public class ParticipantsManager extends DbConnect {

    public ParticipantsManager() { super();}
    
    public Participant selectParticipant(String pseudo) {
        String[][] result = getDb().createStatement("SELECT pseudo, firstname, lastname"
                + "FROM PARTICIPANT"
                + "WHERE pseudo LIKE @pseudo")
                .bindParameter("@pseudo", pseudo)
                .executeQuery();
        return DbEntityToObject.ParticipantParser(result);
    }
    
    public int insertParticipant(Participant p){
        int result = this.getDb().createStatement("INSERT INTO PARTICIPANTS (pseudo,password,firstname,lastName)" 
               +" VALUES (@pseudo,@password,@firstname,@lastname)")
                .bindParameter("@pseudo",p.getPseudo())
                .bindParameter("@password",p.getPassword())
                .bindParameter("@firstname",p.getFirstname())
                .bindParameter("@lastname",p.getLastname())
                /**
                 * textuellement: exécuter la mise à jour de la bd donc valable pour les insert et les update sur les
                 * tables
                 * */
                .executeUpdate();
          return result;      
    }
    
}
