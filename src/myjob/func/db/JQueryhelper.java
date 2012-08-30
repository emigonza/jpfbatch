/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermot
 */
public class JQueryhelper {

    public static ResultSet Query(String query, Connection dbConn) {

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(JQueryhelper.class.getName()).log(Level.SEVERE, query, ex);
        }

        return resultSet;
    }

    public static void ExecuteSQL(String query, Connection dbConn, boolean whaitToEnd) {
        

        ThreadExecuteSQL loc_es = new ThreadExecuteSQL();

        loc_es.setDbConn(dbConn);
        loc_es.setQuery(query);

        loc_es.start();

        if(whaitToEnd)
        {
            while(loc_es.isAlive())
            {
                // espera a que termine
            }
        }
    }

    public static void ExecuteSQL(String query, Connection dbConn) {
        ExecuteSQL(query, dbConn, true);
    }
}
