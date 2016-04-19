/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import java.util.List;
import models.Participant;

/**
 *
 * @author User
 */
public class DbEntityToObject {
    public static Participant ParticipantParser(String[][] entity) {
        //entity : [0] = pseudo, [1] = password, [2] = firstname, [3] = lastname 
        return (entity != null)? new Participant(entity[0][0], entity[0][2], entity[0][3]) : null;
    }
}
