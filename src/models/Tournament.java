/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import datas.TournamentManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import utils.Log;

/**
 *
 * @author User
 */
public class Tournament {
    private final long ID;
    private final int NB_PARTICIPANTS;
    private List<String> participants_id;
    private PriorityQueue<Game> queue;
    private TournamentManager provider;
    private List<Member> turnGame;
    
    public Tournament(int NB_PARTICIPANTS, List<String> participants_id, List<Member> turnGame) {
        this.ID = System.currentTimeMillis();
        this.NB_PARTICIPANTS = NB_PARTICIPANTS;
        this.participants_id = participants_id;
        this.queue = new PriorityQueue(new GameComparator());
        this.provider = new TournamentManager();
        this.turnGame = turnGame;
    }

    
    
    public synchronized void bindDataToQueue(int guardian) throws InterruptedException {
        final int NB_LEVEL = Log.logBase2((double)this.NB_PARTICIPANTS);
        if(guardian == 1) {    
            generateTurnGame(1);
        }
        if(guardian < NB_LEVEL){
            for(int i = 0; i < this.NB_PARTICIPANTS / (Math.pow(2, guardian)); i++) {
                Game game = Game.questionMarkGame(1 + guardian);
                queue.add(game);
                wait(50);
            }
            bindDataToQueue(guardian + 1);
        }
    }
    
    private List<Member> initTurnGame() {
        Collections.shuffle(turnGame);
        return turnGame;
    }
    
    private synchronized void generateTurnGame(int priority) throws InterruptedException {
        Member currentLeft = null, currentRight = null;
            List<Member> turnGameList = initTurnGame();
            for(int i = 0; i < turnGameList.size(); i++) {
                wait(50);
                if(i%2 == 1 && i == turnGameList.size() - 1) {
                    currentLeft = turnGameList.get(i);
                    currentRight = new Member("?", "?", "?");
                }
                if(i%2 == 0)
                    currentLeft = turnGameList.get(i);
                else
                    currentRight = turnGameList.get(i);
            
                if(currentLeft != null && currentRight != null) {
                    Game game = new Game(currentLeft.getPseudo(), currentRight.getPseudo(), priority);
                    queue.add(game);
                    currentLeft = null; currentRight = null;
                }
            }
    }
    
    public PriorityQueue<Game> getQueue() { return queue; }
    public void setQueue(PriorityQueue queue) { this.queue = queue; }
}
