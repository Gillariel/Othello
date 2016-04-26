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
public class ParticipantsManager extends DbConnect {

    public ParticipantsManager() { super(); }
    
    
   // try(blabla) = bloc de ressource -> appel automatique de close() à la fin
    public Participant selectParticipant(String pseudo) {
        try(NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT pseudo, firstname, lastname "
                    + "FROM CONTENDERS "
                    + "WHERE LOWER(pseudo) LIKE @pseudo;")
                    .bindParameter("@pseudo", pseudo.toLowerCase())
                    .executeQuery();
            return DbEntityToObject.ParticipantParser(result);
        }catch(Exception e) {
            return null;
        }
    }
    
    public List<Participant> selectAllParticipants() {
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
    
    public int insertParticipant(Participant p){
        try(NHDatabaseSession session = getDb()){
 
            int result = session.createStatement("INSERT INTO CONTENDERS (pseudo,firstname,lastname,wonGames,lostGames,_password) " 
                    +"VALUES (@pseudo,@firstname,@lastname,0,0,@password);")
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
        }catch(Exception e){
            return -1;
        }
    }
    
    public int deleteParticipant(String pseudo) {
        try(NHDatabaseSession session = getDb()){
            int result = session.createStatement("DELETE FROM CONTENDERS WHERE pseudo LIKE @pseudo")
                    .bindParameter("@pseudo", pseudo)
                    .executeUpdate();
            return result;
        }catch(Exception e){
            return -1;
        }
    }
    
    public int updateParticipant(Participant p) {
        try(NHDatabaseSession session = getDb()){
            int result = session.createStatement("UPDATE CONTENDERS "
                    + "SET pseudo = @pseudo, _password = @password "
                    + "WHERE firstname LIKE @firstname AND lastname LIKE @lastname")
                    .bindParameter("@pseudo", p.getPseudo())
                    .bindParameter("@password", p.getPassword())
                    .bindParameter("@firstname", p.getFirstname())
                    .bindParameter("@lastname", p.getLastname())
                    .executeUpdate();
            return result;
        }catch(Exception e){
            return -1;
        }
    }
    
    public boolean isConnected(){
        return this.getDb().isConnected();  
    }
}
