/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import models.Game;
import utils.MyDialog;

/**
 *
 * @author Sev
 */
public class TournamentManager extends DbConnect{

    public TournamentManager() {super(); }
    
    /*public int insertJ1Incontenders(Game g){
        int result = 0;
        try (NHDatabaseSession session = getDb()){
                session.openTransaction();
                result = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ1().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
        }catch (Exception e) {
            MyDialog.warningDialog("J2", "J2 Not incoded");
            return -1;
        }
        return result;
    }
    
    public int insertJ2Incontenders(Game g){
        int result = 0;
        try (NHDatabaseSession session = getDb()){
                session.openTransaction();
                result = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ2().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
        }catch (Exception e) {
            MyDialog.warningDialog("J2", "J2 Not incoded");
            return -1;
        }
        return result;
    }
    
    public int insertLeaf(Game g) {
        int result = 0;
        try (NHDatabaseSession session = getDb()){
            result = session.createStatement("Insert into LeafGame (id, leftContender, rightContender) "
                        + "VALUES (@id, @J1, @J2);")
                        .bindParameter("@id",g.getId())
                        .bindParameter("@J1",g.getJ1().getPseudo())
                        .bindParameter("@J2",g.getJ2().getPseudo())
                        .executeUpdate();
        }catch(Exception e){
            MyDialog.warningDialog("Leaf", "Leaf Not incoded");
            return -1;
        }
        return result;
    }
    */
    // For each game : Insert Contender with it associated game + Insert the game itself 
    public int insertGames(List<Game> list){
        int result0 = 0,result = 0, result1 = 0, result2 = 0;
        
        try (NHDatabaseSession session = getDb()){
            session.openTransaction();
                for(Game g : list){
              if(!g.getJ1().getPseudo().equals("?") && !g.getJ2().getPseudo().equals(result)){
                result0 = session.createStatement("Insert into Games (id,leftContenderScore, rightContenderScore,concreteType) "
                        + "VALUES (@id,0,0,0);")
                        .bindParameter("@id",g.getId())
                        .executeUpdate();
                
                result1 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ1().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
                
                result2 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ2().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
                    
                result = session.createStatement("Insert into LeafGames (id,leftContender,rightContender) "
                        + "VALUES (@id, @J1, @J2);")
                        .bindParameter("@id",g.getId())
                        .bindParameter("@J1",g.getJ1().getPseudo())
                        .bindParameter("@J2",g.getJ2().getPseudo())
                        .executeUpdate();
                System.out.println(session.getLastError());
                }
               }
             
            if(result0 + result + result1 + result2 < 0){
               session.rollback();
               return -1;
            }else{
                session.commit();
            }
            
            return result;
        }catch (Exception e) {
            return -1;
        }
    }
    
    /*public int insertGame(Game g){
        int result = 0, result1 = 0, result2 = 0;
            try (NHDatabaseSession session = getDb()){
                session.openTransaction();
                result = session.createStatement("Insert into LeafGames (id, leftContender, rightContender) "
                        + "VALUES (@id, @J1, @J2);")
                        .bindParameter("@id",g.getId())
                        .bindParameter("@J1",g.getJ1().getPseudo())
                        .bindParameter("@J2",g.getJ2().getPseudo())
                        .executeUpdate();
                
                result1 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ1().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
                
                result2 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                        .bindParameter("@pseudo", g.getJ2().getPseudo())
                        .bindParameter("@id", g.getId())
                        .executeUpdate();
                
                
                if(result + result1 + result2 < 0){
                   session.rollback();
                   return -1;
                }
                session.commit();
                session.close();
                return result;
            }catch (Exception e) {
                MyDialog.warningDialog("Game", "Game not encoded");
                return -1;
            }
    }*/
    
    public List<Pair<String, String>> selectAllcontenders() {
        List<Pair<String,String>> contenders = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT id_game, pseudo "
                    + "FROM CONTENDERS")
                    .executeQuery();
            for(String[] contender : result) 
                contenders.add(new Pair<String,String>(contender[0], contender[1]));
            return contenders;
        }catch (Exception e) {
            return null;
        }
    }
    
    //Renvoie la liste des games du tour actuelle.
    public List<Game> selectAllGames() {
        List<Game> games = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT g.id, l.leftContenders, l.rightContenders, g._priority "
                    + "FROM Games g "
                    + "JOIN LeafGames l on l.id = g.id "
                    + "WHERE g.winner IN (SELECT winner "
                                      + "FROM Games "
                                      + "WHERE _priority = (SELECT MIN(_priority) "
                                                         + "FROM Games "
                                                         + "WHERE winner IS NOT NULL"
                                                         + "GROUP BY _priority));")

                .executeQuery();
            for(String[] game : result) 
                games.add(DbEntityToObject.GameParser(game));
            return games;
        }catch (Exception e) {
            return null;
        }
        
    }
}
