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
import models.LeafGame;
import models.Tournament;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

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
        addContenders("Gillariel");
        addContenders("HenryLeRi");
        List<String>testParticipant = new ArrayList<>();
        testParticipant.add("Gillariel"); testParticipant.add("HenryLeRi");
        Tournament testTournament = new Tournament(2, testParticipant);
        testTournament.initData();
        testTournament.bindDataToQueue();
        PriorityQueue<Game> q = new PriorityQueue();
        q.add(new LeafGame("Gillariel", "HenryLeRi", 1));
        Assert.assertEquals(q, testTournament.getQueue());
        
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
