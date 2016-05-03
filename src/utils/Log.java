/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author User
 */
public class Log {
    public static int logBase2(Double value) {
        double log = Math.log(value)/Math.log(2);
        return (String.valueOf(log).contains("."))? (int)log+1 : (int)log;
    }
}
