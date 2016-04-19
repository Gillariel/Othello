/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import java.util.ArrayList;
import java.util.List;
import models.Participant;

/**
 *
 * @author User
 */
public class DbEntityToObject {
    
    public static List<Participant> ParticipantParser(String[][] entity) {
        ArrayList<Participant> result = new ArrayList<>();
        if(entity.length > 1) {
            return null;
        }
        return null;
    }
}
