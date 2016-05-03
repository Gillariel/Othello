/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import datas.ContendersManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
    public List<Member> initData() {
        List<Member> result = new ArrayList<>();
        //List<Member> internals = new ArrayList<>();
        
        // leafs = Tout les participants de la bd
        ContendersManager provider = new ContendersManager();
        result = provider.selectAllContenders();
         
        //Map<String,List<Member>> result = new HashMap<String, List<Member>>();
        //Collections.shuffle(leafs); Collections.shuffle(internals);
        
        return result;
    }
    
    /*
    * Ok pour un générer les leafs, aps les interals
    * -> appeler récursivement bindDataToQueue(List<Membre> leafs, int guardian) après génération des internals.
    * guardian : double log = Math.log(16)/Math.log(2) à l'initialisation, - 1 à chaque itération
    */
    public synchronized void bindDataToQueue(List<Member> leafs, int guardian) throws InterruptedException {
        List<Member> leafList = initData();
        Member currentLeft = null, currentRight = null;
        //boucle for simple car itérer sur i+2 et pas i+1 -> On prend 2 participants d'un coup
        //Checker si tout seul, Game ou il gagne d'office
        for(int i = 0; i < leafList.size(); i++) {
            if(i%2 == 1 && i == leafList.size()) {
                currentLeft = leafList.get(i);
                currentRight = new Member("?", "?", "?");
            }
            if(i%2 == 0)
                currentLeft = leafList.get(i);
            else
                currentRight = leafList.get(i);
            
            if(currentLeft == null && currentRight == null) {
                queue.add(new LeafGame(currentLeft.getPseudo(), currentRight.getPseudo(), 1));
                currentLeft = null; currentRight = null;
            }
            
            wait(50);
        }
        for(int i = 0; i < data.get("internals").size(); i+=2) {
            queue.add(InternalGame.questionMarkGame(2));
            wait(50);
        }
    }
    
    public PriorityQueue<Game> getQueue() { return queue; }
    public void setQueue(PriorityQueue queue) { this.queue = queue; }
}
