/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author guillermot
 */
public class ThreadExecuteSQL extends java.lang.Thread {

    Connection _dbConn;
    String query = "";

    public Connection getDbConn() {
        return _dbConn;
    }

    public void setDbConn(Connection _dbConn) {
        this._dbConn = _dbConn;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void run() {
        Statement statement = null;

        boolean autocommit = true;

        try {
            autocommit = _dbConn.getAutoCommit();
            statement = _dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.execute(query);
            if (!autocommit) {
                _dbConn.commit();
            }
        } catch (SQLException ex) {
            System.out.println("error al ejecutar\n" + ex.getMessage() + "\n" + query);
            if (!autocommit) {
                System.out.println("intentando deshacer");
                try {
                    _dbConn.rollback();
                    System.out.println("deshacer completado");
                } catch (SQLException ex1) {
                    System.out.println("error al deshacer");
                    Logger.getLogger(ThreadExecuteSQL.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

            Logger.getLogger(myjob.func.db.ThreadExecuteSQL.class.getName()).log(Level.SEVERE, "error al ejecutar\n" + query, ex);
        }
    }
}
