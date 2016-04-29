/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import java.util.ArrayList;
import java.util.List;
import models.Contender;
import models.Member;

/**
 *
 * @author User
 */
public class ContendersManager extends DbConnect {

    public ContendersManager() {
        super();
    }

    public void zklhfzioqzhen() {
        this.getDb().openTransaction();
        
    }
    
    public Contender selectContenders(String pseudo) {
        try (NHDatabaseSession session = getDb()) {
            String[][] result = session.createStatement("SELECT pseudo"
                    + "FROM Test where pseudo LIKE @pseudo;")
                    .bindParameter("@pseudo", pseudo)
                    .executeQuery();
            return DbEntityToObject.ContederParser(result);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Member> selectAllContenders() {
        List<Member> list = new ArrayList<>();
        try (NHDatabaseSession session = getDb()) {
            String[][] result = session.createStatement("SELECT m.pseudo, m.firstname, m.lastname"
                    + "FROM Members m "
                    + "JOIN Contenders c on c.pseudo = m.pseudo "
                    + "WHERE Exist(c.pseudo);")
                    .executeQuery();
            for(String[] One : result)
                list.add(DbEntityToObject.ParticipantParser(One));
            return list;
        } catch (Exception e) {
            return null;
        }
    }
    
    public int insertContenders(String pseudo) {
        try (NHDatabaseSession session = getDb()) {
            int result = session.createStatement("INSERT INTO TEST (pseudo)"
                    + "VALUES (@pseudo);")
                    .bindParameter("@pseudo", pseudo)
                    .executeUpdate();
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
    
    /*
    public int countContender() {
        try (NHDatabaseSession session = getDb()) {
            int result = session.createStatement("SELECT count(*)"
                    + "FROM test;")
                    .executeUpdate();
            return result;
        } catch (Exception e) {
            return -1;
        }
    }*/

    public int deleteContenders(String pseudo) {
        try (NHDatabaseSession session = getDb()) {
            int result = session.createStatement("DELETE FROM Test WHERE pseudo LIKE @pseudo")
                    .bindParameter("@pseudo", pseudo)
                    .executeUpdate();
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
}
