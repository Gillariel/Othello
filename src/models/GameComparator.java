/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Comparator;

/**
 *
 * @author User
 */
public class GameComparator implements Comparator, Comparable<Game>{
    @Override
    public int compareTo(Game o) {
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return ((Game)o1).getPriority() - ((Game)o2).getPriority();
    }
    
}
