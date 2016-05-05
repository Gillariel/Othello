/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import models.Game;

/**
 *
 * @author Sev
 */
public class TournamentManager extends DbConnect{

    public TournamentManager() {super(); }
    
    public int insertGames(List<Game> list){
        int result = 0;
        try (NHDatabaseSession session = getDb()){
            session.openTransaction();
            for(Game g : list){
                result =  session.createStatement("Insert into Game (id,leftContenderScore,rightContenderScore,concreteType)"
                        + "VALUES (@id, 0, 0, 0);")
                    .bindParameter("@id",g.getId())
                    .executeUpdate();
                if(result != 0){
                   session.rollback();
                   return -1;
                }
            }
            return result;
        }catch (Exception e) {
            return -1;
        }
    }
    
    //Renvoie la liste des games du tour actuelle.
    public List<Game> selectAllGames() {
        List<Game> games = new ArrayList<>();
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT g.id, l.leftContenders, l.rightContenders, g._priority "
                    + "FROM Games g "
                    + "JOIN LeafGames l on l.id = g.id "
                    + "WHERE g.winner = (SELECT winner "
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
