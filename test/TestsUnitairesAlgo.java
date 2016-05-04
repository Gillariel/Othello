/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import datas.ContendersManager;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import models.Game;
import models.InternalGame;
import models.LeafGame;
import models.Tournament;
import org.junit.Assert;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import utils.Log;

/**
 *
 * @author Sev
 */
public class TestsUnitairesAlgo {
    
    
    @BeforeClass
    public static void setup() {
        
    }
    
    @Test
    public void testGenerationTournoi2Participants() throws InterruptedException{
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233");
        Tournament testTournament = new Tournament(2, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(2.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @Test
    public void testGenerationTournoi3Participants() throws InterruptedException{
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        addContenders("HenriLeRi");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233"); testParticipant.add("HenriLeRi");
        Tournament testTournament = new Tournament(3, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(3.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        q.add(new LeafGame("HenriLeRi", "?", 1));
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @Test
    public void testGenerationTournoi4Participants() throws InterruptedException{
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        addContenders("HenriLeRi");
        addContenders("Ulrik233");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233"); testParticipant.add("HenriLeRi"); testParticipant.add("Ulrik233");
        Tournament testTournament = new Tournament(4, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(4.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        q.add(new LeafGame("HenriLeRi", "Ulrik233", 1));
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @Test
    public void testGenerationTournoi8Participants() throws InterruptedException{
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        addContenders("HenriLeRi");
        addContenders("Ulrik233");
        addContenders("BatmanDu69");
        addContenders("JohnVivant");
        addContenders("PeaceOfCake");
        addContenders("FChouffe");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233"); testParticipant.add("HenriLeRi"); testParticipant.add("Ulrik233");
        testParticipant.add("BatmanDu69"); testParticipant.add("JohnVivant"); testParticipant.add("PeaceOfCake"); testParticipant.add("FChouffe");
        Tournament testTournament = new Tournament(8, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(8.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        q.add(new LeafGame("HenriLeRi", "Ulrik233", 1));
        q.add(new LeafGame("BatmanDu69", "JohnVivant", 1));
        q.add(new LeafGame("PeaceOfCake", "FChouffe", 1));
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @Test
    public void testGenerationTournoi7Participants() throws InterruptedException{
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        addContenders("HenriLeRi");
        addContenders("Ulrik233");
        addContenders("BatmanDu69");
        addContenders("JohnVivant");
        addContenders("PeaceOfCake");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233"); testParticipant.add("HenriLeRi"); testParticipant.add("Ulrik233");
        testParticipant.add("BatmanDu69"); testParticipant.add("JohnVivant"); testParticipant.add("PeaceOfCake");
        Tournament testTournament = new Tournament(7, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(7.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        q.add(new LeafGame("HenriLeRi", "Ulrik233", 1));
        q.add(new LeafGame("BatmanDu69", "JohnVivant", 1));
        q.add(new LeafGame("PeaceOfCake", "?", 1));
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @Test
    public void InternalGamesAreGeneratedFor4Participants() throws InterruptedException {
        cleanDb();
        addContenders("Gillariel");
        addContenders("Sevmi233");
        addContenders("HenriLeRi");
        addContenders("Ulrik233");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("Sevmi233"); testParticipant.add("HenriLeRi"); testParticipant.add("Ulrik233");
        Tournament testTournament = new Tournament(4, testParticipant);
        testTournament.bindDataToQueue(Log.logBase2(4.0d));
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "Sevmi233", 1));
        q.add(new LeafGame("HenriLeRi", "Ulrik233", 1));
        for(int i = 0; i < 3; i++){
            if(i < 2)
                q.add(InternalGame.questionMarkGame(2));
            else
                q.add(InternalGame.questionMarkGame(3));
        }
        for(Game g : q)
            for(Game ga : testTournament.getQueue()) {
                Assert.assertEquals(g.getJ1().getPseudo(), ga.getJ1().getPseudo());
                Assert.assertEquals(g.getJ2().getPseudo(), ga.getJ2().getPseudo());
            }
    }
    
    @AfterClass
    public static void cleanDb() {
        datas.ContendersManager provider = new ContendersManager();
        provider.deleteAllContenders();
    }
    
    private void addContenders(String pseudo){
        ContendersManager provider = new ContendersManager();
        provider.insertContenders(pseudo);
    }
   
}
