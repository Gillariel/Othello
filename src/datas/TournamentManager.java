/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.time.LocalDate;
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
            for(Game a : list){
                 result =  session.createStatement("Insert into Game (id,leftContenderScore,rightContenderScore,concreteType)"
                        + "VALUES (@id,@leftContenderScore,@rightContenderScore,0);")
                    .bindParameter("@id",a.getId())
                    .bindParameter("@leftContenderScore",a.getJ1().getScore())
                    .bindParameter("@rightContenderScore", a.getJ2().getScore())
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
}
