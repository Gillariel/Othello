/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import models.Gamer;
import models.Member;
import utils.MyDialog;

/**
 *
 * @author User
 */
public class MembersManager extends DbConnect {

    public MembersManager() { super(); }
    
    
   // try(blabla) = bloc de ressource -> appel automatique de close() à la fin
    public Member selectMember(String pseudo) {
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
    
    public List<Member> selectAllMembers() {
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
    
    public int insertMember(Member p){
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
    
    public int deleteMember(String pseudo) {
        try(NHDatabaseSession session = getDb()){
            int result = session.createStatement("DELETE FROM Members WHERE pseudo LIKE @pseudo")
                    .bindParameter("@pseudo", pseudo)
                    .executeUpdate();
            return result;
        }catch(Exception e){
            return -1;
        }
    }

    public int updateMember(Member p) {
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
    
     public List<Gamer> selectParticipantsScore() {
        List<Gamer> list = new ArrayList<>();
            try(NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("select m.pseudo,sum(g.leftContenderScore) as Total " 
                    + "from Members m " 
                    + "join Games g on g.leftContender = m.pseudo" 
                    + "group by m.pseudo " 
                    + "UNION " 
                    + "select m.pseudo,sum(g.rightContenderScore) " 
                    + "from Members m " 
                    + "join Games g on g.rightContender = m.pseudo " 
                    + "group by m.pseudo " 
                    + "order by sum(leftContenderScore) desc ;")
                    .executeQuery();
            for(String[] participant : result)
                list.add(DbEntityToObject.GamerParser(participant));
            return list;
        }catch(Exception e) {
            return null;
        }
    }
}
