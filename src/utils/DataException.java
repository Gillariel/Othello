/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import helmo.nhpack.NHPack;

/**
 *
 * @author User
 */
public class DataException extends Exception{
    private final Message exception;
    
    public DataException(Message exception){
        super(exception.getMessage());
        this.exception = exception;
    }
    public DataException(String exception){
        super(exception);
        this.exception = null;
    }
    public void show(){
        if(this.exception != null) 
            NHPack.getInstance().showError("ERROR", "" + this.exception);
        else 
            NHPack.getInstance().showError("ERROR", "" + this.getMessage());
    }
}
