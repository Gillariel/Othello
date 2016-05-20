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
import models.Member;

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
    
   /* public Game selectGame(String J1) {
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT id, leftContender, rightContender, _priority "
                    + "FROM Games "
                    + "WHERE leftContender = @J1;")
                    .bindParameter("@J1", J1)
                    .executeQuery(); 
            return new Game(Long.parseLong(result[0][0]), result[0][1], result[0][2], Integer.parseInt(result[0][3]));
        }catch (Exception e) {
            return null;
        }
    }*/
     
    public int updateScore(Game id) {
        try(NHDatabaseSession session = getDb()){
            int result = 0;
                result = session.createStatement("UPDATE Games "
                    + "SET leftContenderScore = @scoreJ1,rightContenderScore = @scoreJ2 "
                    + "WHERE id like @id")
                    .bindParameter("@scoreJ1",id.getJ1().getScore())
                    .bindParameter("@scoreJ2",id.getJ2().getScore())
                    .bindParameter("@id",id.getId())
                    .executeUpdate();
           
            return result;
        }catch(Exception e){
            return -1;
        }
    }
    
    public List<Member> selectParticipantsScore() {
        List<Member> list = new ArrayList<>();
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
                list.add(DbEntityToObject.ParticipantParser(participant));
            return list;
        }catch(Exception e) {
            return null;
        }
    }
   
    public int selectCurrentPriority(){
        try (NHDatabaseSession session = getDb()){
            String[][] result = session.createStatement("SELECT _priority "
                    + "FROM Games "
                    + "WHERE leftContender != '?'")
                    .executeQuery();
            return Integer.parseInt(result[0][0]);
        }catch (Exception e) {
            return -1;
        }
    }
    
/*        try (NHDatabaseSession session = getDb()){
            getDb().openTransaction();
            
            int turn = selectCurrentPriority();
            
            List<Game> futureGames = new ArrayList<>();
            String[][] result = session.createStatement("SELECT id, leftContender, rightContender, _priority "
                    + "FROM Games "
                    + "WHERE _priority = @priority")
                    .bindParameter("@priority", turn + 1)
                    .executeQuery();
            for(String[] g : result)
                futureGames.add(new Game(Long.parseLong(g[0]), g[1], g[2], Integer.parseInt(g[3])));
            
            List<Game> lastGames = new ArrayList<>();
            String[][] result2 = session.createStatement("SELECT id, leftContender, rightContender, _priority "
                    + "FROM Games "
                    + "WHERE _priority = @priority")
                    .bindParameter("@priority", turn)
                    .executeQuery();
            for(String[] ga : result)
                lastGames.add(new Game(Long.parseLong(ga[0]), ga[1], ga[2], Integer.parseInt(ga[3])));
            
            String currentLeft = "", currentRight = "";
            long id_futureGame = 0;
            int resultFromUpdate = 0;
            
            for(int i = 0; i < lastGames.size(); i++) {
                if(i%2 == 0){
                    currentLeft = lastGames.get(i).getWinner();
                }else if(i%2 == 1){
                    currentRight = lastGames.get(i).getWinner();
                }else if(currentLeft != "" && currentRight != ""){
                    id_futureGame = futureGames.get(i/2).getId();
                    resultFromUpdate += updateGame(id_futureGame, currentLeft, currentRight);
                }
            }
            
            if(resultFromUpdate > 0){
                getDb().rollback();
            }else{
                getDb().commit();
            }
            return resultFromUpdate;
        }catch (Exception e) {
            return -1;
        }

    }*/
    
    private int updateGame(long id, String currentLeft, String currentRight) {
        try(NHDatabaseSession session = getDb()){
            int result = session.createStatement("UPDATE Games "
                    + "SET leftContender = @J1, rightContender = @J2 "
                    + "WHERE id LIKE @id;")
                    .bindParameter("@J1", currentLeft)
                    .bindParameter("@J2", currentRight)
                    .bindParameter("@id", id)
                    .executeUpdate();
            return result;
       }catch(Exception e){
           return -1;
       }
    }
    
}
