/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import datas.ContendersManager;
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
    //private TournamentManager provider;
    
    public Tournament(int NB_PARTICIPANTS, List<String> participants_id) {
        this.ID = System.currentTimeMillis();
        this.NB_PARTICIPANTS = NB_PARTICIPANTS;
        this.participants_id = participants_id;
        this.queue = new PriorityQueue(new GameComparator());
        //this.provider = new TournamentManager();
    }

    public synchronized void bindDataToQueue(int guardian) throws InterruptedException {
        final int NB_LEVEL = Log.logBase2((double)this.NB_PARTICIPANTS);
        if(guardian == 1) {    
            generateLeafs(1);
            //bindDataToQueue(guardian+1);
        }
        if(guardian < NB_LEVEL){
            for(int i = 0; i < this.NB_PARTICIPANTS / (Math.pow(2, guardian)); i++) {
                Game game = InternalGame.questionMarkGame(1 + guardian);
                queue.add(game);
                //provider.insertGame(game);
                wait(50);
            }
            bindDataToQueue(guardian+1);
        }
        //au cas ou le provider doit être initialisé pour chaque insert...
        //provider.insertgames(queue)
    }
    
    private List<Member> initLeafs() {
        List<Member> leafs = new ArrayList<>();
        ContendersManager provider = new ContendersManager();
        leafs = provider.selectAllContenders();
        Collections.shuffle(leafs);
        return leafs;
    }
    
    private synchronized void generateLeafs(int priority) throws InterruptedException {
        Member currentLeft = null, currentRight = null;
            List<Member> leafList = initLeafs();
            for(int i = 0; i < leafList.size(); i++) {
                wait(50);
                if(i%2 == 1 && i == leafList.size()) {
                    currentLeft = leafList.get(i);
                    currentRight = new Member("?", "?", "?");
                }
                if(i%2 == 0)
                    currentLeft = leafList.get(i);
                else
                    currentRight = leafList.get(i);
            
                if(currentLeft != null && currentRight != null) {
                    Game game = new LeafGame(currentLeft.getPseudo(), currentRight.getPseudo(), priority);
                    queue.add(game);
                    //provider.insertGame(game);
                    currentLeft = null; currentRight = null;
                }
            }
    }
    
    public PriorityQueue<Game> getQueue() { return queue; }
    public void setQueue(PriorityQueue queue) { this.queue = queue; }
}
