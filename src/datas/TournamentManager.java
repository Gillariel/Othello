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
    
    // For each game : Insert Contender with it associated game + Insert the game itself 
    public int insertGames(List<Game> list){
        int result0 = 0,result = 0, result1 = 0, result2 = 0;
        
        try (NHDatabaseSession session = getDb()){
            session.openTransaction();
            
                result1 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, 0);")
                            .bindParameter("@pseudo", "?")
                            .executeUpdate();
                
                for(Game g : list){
                    if(!(g.getJ1().getPseudo().equals("?") && g.getJ2().getPseudo().equals("?"))){
                        result0 = session.createStatement("INSERT INTO Games (id,leftContenderScore, rightContenderScore,concreteType) "
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
                    
                        result = session.createStatement("INSERT INTO LeafGames (id,leftContender,rightContender) "
                            + "VALUES (@id, @J1, @J2);")
                            .bindParameter("@id",g.getId())
                            .bindParameter("@J1",g.getJ1().getPseudo())
                            .bindParameter("@J2",g.getJ2().getPseudo())
                            .executeUpdate();
                        
                    System.out.println(session.getLastError());
                    }else if(g.getJ2().getPseudo().equals("?")){
                        result2 = session.createStatement("INSERT INTO CONTENDERS (pseudo, id_game) VALUES (@pseudo, @id);")
                            .bindParameter("@pseudo", g.getJ1().getPseudo())
                            .bindParameter("@id", g.getId())
                            .executeUpdate();
                        
                        result = session.createStatement("INSERT INTO LeafGames (id,leftContender,rightContender) "
                            + "VALUES (@id, '?', '?');")
                            .bindParameter("@id",g.getId())
                            .executeUpdate();
                        
                    }else{
                        result = session.createStatement("INSERT INTO LeafGames (id,leftContender,rightContender) "
                            + "VALUES (@id, '?', '?');")
                            .bindParameter("@id",g.getId())
                            .executeUpdate();
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
    
    public List<Pair<String, String>> selectAllContenders() {
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
            String[][] result = session.createStatement("SELECT id, leftContenders, rightContenders, _priority "
                    + "FROM Games  "
                    + "WHERE _priority = (SELECT _priority "
                                      + "FROM Games "
                                      + "WHERE LeftContenders IS NOT NULL;")
                .executeQuery();
            for(String[] game : result) 
                games.add(DbEntityToObject.GameParser(game));
            return games;
        }catch (Exception e) {
            return null;
        }
    }
    
    public List<Pair<String, String>> selectAllVsContenders() {
        List<Pair<String,String>> games = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT leftContenders, rightContenders"
                    + "FROM Games "
                    + "WHERE _priority = (SELECT _priority "
                                      + "FROM Games "
                                      + "WHERE LeftContenders IS NOT NULL;")
                    .executeQuery();
            for(String[] g : result) 
                games.add(new Pair<String,String>(g[0], g[1]));
            return games;
        }catch (Exception e) {
            return null;
        }
    }
}
