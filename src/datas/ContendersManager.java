/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import models.Participant;

/**
 *
 * @author User
 */
public class ContendersManager extends DbConnect{
    public ContendersManager() { super(); }
    
    public List<Participant> selectContenders() {
        List<Participant> list = new ArrayList<>();
        try(NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT pseudo, firstname, lastname "
                    + "FROM CONTENDERS;")
                    .executeQuery();
        
            for(String[] oneParticipant : result)
                list.add(DbEntityToObject.ParticipantParser(oneParticipant));
            
            return list;
        }catch(Exception e) {
            return null; 
        }
    }
    
    public int insertAllContenders(List<Participant> contenders) {
        int results = 0;
        try(NHDatabaseSession session = getDb()){
            for(Participant p : contenders){
                int result = session.createStatement("INSERT INTO CONTENDERS (pseudo,firstname,lastname,totalScore) " 
                        +"VALUES (@pseudo,@firstname,@lastname,0);")
                        .bindParameter("@pseudo",p.getPseudo())
                        .bindParameter("@firstname",p.getFirstname())
                        .bindParameter("@lastname",p.getLastname())
                        .executeUpdate();
                results += result; 
            }
            return results;      
        }catch(Exception e){
            return -1;
        }
    }
}
