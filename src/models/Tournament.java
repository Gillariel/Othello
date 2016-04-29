/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import datas.ContendersManager;
import datas.ParticipantsManager;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
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
    private long[] participants_id;
    private PriorityQueue<Game> queue;

    public Tournament(int NB_PARTICIPANTS, long[] participants_id) {
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
    public Map<String,List<Member>> initData() {
        List<Member> leafs = new ArrayList<>();
        List<Member> internals = new ArrayList<>();
        
        // leafs = Tout les participants de la bd
        ContendersManager provider = new ContendersManager();
        leafs = provider.selectAllContenders();
        
        //Ajouter X/nbTour pour chaque tour un participant factice "?" -> X = nb De participants du tour
        
        Map<String,List<Member>> result = new HashMap<String, List<Member>>();
        Collections.shuffle(leafs); Collections.shuffle(internals);
        
        result.put("leafs",leafs); result.put("internals",internals);
        return result;
    }
    
    public void bindDataToQueue() {
        Map<String, List<Member>> data = initData();
        //boucle for simple car itérer sur i+2 et pas i+1 -> On prend 2 participants d'un coup
        //Checker si tout seul, Game ou il gagne d'office
        /*for(Game leaf : data.get("leafs"))
            queue.add(leaf);
        for(Game internal : data.get("internals"))
            queue.add(internal);
        */
    }
    
    public long[] getParticipants_id() { return participants_id; }
    public void setParticipants_id(long[] participants_id) { this.participants_id = participants_id; }
    public PriorityQueue<Game> getQueue() { return queue; }
    public void setQueue(PriorityQueue queue) { this.queue = queue; }
}
