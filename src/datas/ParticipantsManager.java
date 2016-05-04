/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import models.Member;
import utils.MyDialog;

/**
 *
 * @author User
 */
public class ParticipantsManager extends DbConnect {

    public ParticipantsManager() { super(); }
    
    
   // try(blabla) = bloc de ressource -> appel automatique de close() à la fin
    public Member selectParticipant(String pseudo) {
        try(NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT pseudo, firstname, lastname "
                    + "FROM Members "
                    + "WHERE LOWER(pseudo) LIKE @pseudo;")
                    .bindParameter("@pseudo", pseudo.toLowerCase())
                    .executeQuery();
            return DbEntityToObject.ParticipantParser(result);
        }catch(Exception e) {
            return null;
        }
    }
    
    public List<Member> selectAllParticipants() {
        List<Member> list = new ArrayList<>();
        try(NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT pseudo, firstname, lastname "
                    + "FROM Members;")
                    .executeQuery();
            for(String[] oneParticipant : result)
                list.add(DbEntityToObject.ParticipantParser(oneParticipant));
            
            return list;
        }catch(Exception e) {
            MyDialog.warningDialog("Connection Problem", "Please check your internet connection and try again");
            return null;
        }
    } 
    
    public int insertParticipant(Member p){
        try(NHDatabaseSession session = getDb()){
            
            int result = session.createStatement("INSERT INTO Members (pseudo,firstname,lastname,wonGames,lostGames,_password) " 
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
            int result = session.createStatement("DELETE FROM Members WHERE pseudo LIKE @pseudo")
                    .bindParameter("@pseudo", pseudo)
                    .executeUpdate();
            return result;
        }catch(Exception e){
            return -1;
        }
    }

    public int updateParticipant(Member p) {
        try(NHDatabaseSession session = getDb()){
            int result = session.createStatement("UPDATE Members "
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
