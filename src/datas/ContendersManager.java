/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datas;

import helmo.nhpack.NHDatabaseSession;
import models.Contender;

/**
 *
 * @author User
 */
public class ContendersManager extends DbConnect {

    public ContendersManager() {
        super();
    }

    public Contender selectContenders(String pseudo) {
        try (NHDatabaseSession session = getDb()) {
            String[][] result = session.createStatement("SELECT pseudo"
                    + "FROM Test where pseudo = @pseudo;")
                    .bindParameter("@pseudo", pseudo)
                    .executeQuery();
            return DbEntityToObject.ContederParser(result);
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

    public int countContender() {
        try (NHDatabaseSession session = getDb()) {
            int result = session.createStatement("SELECT count(*)"
                    + "FROM test;")
                    .executeUpdate();
            return result;
        } catch (Exception e) {
            return -1;
        }

    }

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
