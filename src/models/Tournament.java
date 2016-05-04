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
/* A checker pour le déroulement complet (ensemble des games)
* nombre de niveau = log²(nb_element) + 1
*import java.util.PriorityQueue
*/
/**
 *
 * @author User
 */
public class Tournament {
    private final long ID;
    private final int NB_PARTICIPANTS;
    private List<String> participants_id;
    private PriorityQueue<Game> queue;

    public Tournament(int NB_PARTICIPANTS, List<String> participants_id) {
        this.ID = System.currentTimeMillis();
        this.NB_PARTICIPANTS = NB_PARTICIPANTS;
        this.participants_id = participants_id;
        this.queue = new PriorityQueue(new GameComparator());
    }

    /**
     * 1) préparation collections
     * 2) Parcours du 1er niveau 
     * 3) ajouter 2 participants danas un Objet Game -> itération suivante
     * 4) Jouez chaque partie du 1er
     * 5) Parcourir le 1er niveau 2 par 2
     * 6) Pour chaque Pair<Game, Game>, on initialise internal
     * 7) Sur base du gagnant de chaque enfant, on crée la Game correspondante
     * 8) on itère pour chaque Pair :
     *      SI une Game est seule (pas de Game à associer), on initialise une Game dont le vainqueur est automatiquement le gagnant de la précédente
     * 9) Jusqu'a ce qu'une seule Pair soit présente
     * 10) Le gagnant de cette Game gagne le tournoi.
     */
    /**
    * Modifier Schéma BD :  Contenders devient Member
    * une table Contenders sera crée, comprenant seulement les membres participants au tournoi en cours
    */
   
    /*
    * Ok pour un générer les leafs, aps les interals
    * -> appeler récursivement bindDataToQueue(List<Membre> leafs, int guardian) après génération des internals.
    * guardian : double log = Math.log(16)/Math.log(2) à l'initialisation, - 1 à chaque itération
    */
    public synchronized void bindDataToQueue(int guardian) throws InterruptedException {
        final int NB_LEVEL = Log.logBase2((double)this.NB_PARTICIPANTS);
        if(guardian == 1) {    
            generateLeafs(1);
            bindDataToQueue(guardian+1);
        }
        if(guardian < NB_LEVEL){
            for(int i = 0; i < this.NB_PARTICIPANTS / (Math.pow(2, guardian)); i++) {
                queue.add(InternalGame.questionMarkGame(1 + guardian + Log.logBase2((double)this.NB_PARTICIPANTS)));
                wait(50);
            }
            bindDataToQueue(guardian+1);
        }
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
                    break;
                }
                if(i%2 == 0)
                    currentLeft = leafList.get(i);
                else
                    currentRight = leafList.get(i);
            
                if(currentLeft != null && currentRight != null) {
                    queue.add(new LeafGame(currentLeft.getPseudo(), currentRight.getPseudo(), priority));
                    currentLeft = null; currentRight = null;
                }
            }
    }
    
    public PriorityQueue<Game> getQueue() { return queue; }
    public void setQueue(PriorityQueue queue) { this.queue = queue; }
}
