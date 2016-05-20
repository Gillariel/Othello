/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;


import models.Game;
import models.Gamer;
import models.Member;

/**
 *
 * @author User
 */
public class DbEntityToObject {
    public static Member ParticipantParser(String[][] entity) {
        //entity : [0] = pseudo, [1] = firstname, [2] = lastname 
        return (entity != null)? new Member(entity[0][0], entity[0][1], entity[0][2]) : null;
    }
    
    public static Member ParticipantParser(String[] entity) {
        //entity : [0] = pseudo, [1] = password, [2] = firstname, [3] = lastname 
        return (entity != null)? new Member(entity[0], entity[1], entity[2]) : null;
    }
    
    public static Gamer GamerParser(String[] entity) {
        //entity : [0] = pseudo, [1] = score 
        return (entity != null)? new Gamer(entity[0], Long.parseLong(entity[1])) : null;
    }
    
    public static Game GameParser(String[] entity) {
        return (entity != null)? new Game(Integer.parseInt(entity[0]), entity[1], entity[2], Integer.parseInt(entity[3])) : null;
    }
}
