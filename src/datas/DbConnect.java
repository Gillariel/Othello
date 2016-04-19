/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;
import utils.DataException;
import helmo.nhpack.NHDatabaseSession;
import helmo.nhpack.db.ConnectionConfig;
import helmo.nhpack.db.SqlServerConnectionConfig;
import helmo.nhpack.exceptions.NHPackException;

/**
 *
 * @author User
 */
public abstract class DbConnect {
    final private ConnectionConfig db;
    private NHDatabaseSession token;

    public DbConnect() {
        this.db = new SqlServerConnectionConfig()
                .withUserName("")
                .withPassword("")
                .withHost("192.168.128.18")
                .withDatabase("");
        this.token = null;
    }
    
    protected NHDatabaseSession getDb() { return (this.token == null)? this.token = this.open() : this.token; }
    
    private NHDatabaseSession open() {
        try{
            return new NHDatabaseSession(db);
        }catch(NHPackException e){
            e.printStackTrace();
            return null;
        }
    }
    
    protected void closeTransaction(DataException ex){
        this.getDb().rollback();
        this.closeSession();
        this.solveError(ex);
    }
    
    protected void closeTransaction() {
        this.getDb().commit();
        this.closeSession();
    }
    
    protected void closeSession() {
        this.token.close();
        this.token = null;
    }
    
    protected void solveError(DataException ex) {
        ex.show();
        this.closeSession();
        System.exit(1);
    }
}
