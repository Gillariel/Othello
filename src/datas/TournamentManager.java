/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import models.Game;

/**
 *
 * @author Sev
 */
public class TournamentManager extends DbConnect{

    public TournamentManager() {super(); }
    
    public int insertGames(List<Game> list){
        int result = 0,result0 = 0;
        
        try (NHDatabaseSession session = getDb()){
                
                for(Game g : list){
                    
                    if(!(g.getJ1().getPseudo().equals("?") && g.getJ2().getPseudo().equals("?"))){
                        result = session.createStatement("INSERT INTO Games (id, leftContenderScore, rightContenderScore, "
                                + "leftContender, rightContender, _priority) "
                                + "VALUES (@id, 0, 0, @pseudoJ1, @pseudoJ2, @priority);")
                                .bindParameter("@id",g.getId())
                                .bindParameter("@pseudoJ1",g.getJ1().getPseudo())
                                .bindParameter("@pseudoJ2",g.getJ2().getPseudo())
                                .bindParameter("@priority",g.getPriority())
                                .executeUpdate();
                    System.out.println(session.getLastError());
                    }
                }
            if(result < 0){
               return -1;
            }
            return result;
        }catch (Exception e) {
            return -1;
        }
    }
    
    /*public List<Pair<String, String>> selectAllParticipant() {
        List<Pair<String,String>> participants = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT id_game, pseudo "
                    + "FROM CONTENDERS")
                    .executeQuery();
            for(String[] contender : result) 
                participants.add(new Pair<String,String>(contender[0], contender[1]));
            return participants;
        }catch (Exception e) {
            return null;
        }
    }*/
    
    //Renvoie la liste des games du tour actuelle.
    public List<Game> selectAllGames() {
        List<Game> games = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT id, leftContender, rightContender, _priority "
                    + "FROM Games "
                    + "WHERE _priority = (SELECT Min(_priority) "
                                      + "FROM Games "
                                      + "WHERE LeftContender != '?');")

                .executeQuery();
            for(String[] game : result) 
                games.add(DbEntityToObject.GameParser(game));
            return games;
        }catch (Exception e) {
            return null;
        }
    }
    
    public List<Pair<String, String>> selectAllVsParticipant() {
        List<Pair<String,String>> games = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT leftContender, rightContender "
                    + "FROM Games "
                    + "WHERE _priority = (SELECT Min(_priority) "
                                      + "FROM Games "
                                      + "WHERE LeftContender != '?');")
                    .executeQuery();
            for(String[] g : result) 
                games.add(new Pair<String,String>(g[0], g[1]));
            return games;
        }catch (Exception e) {
            return null;
        }
    }
}
