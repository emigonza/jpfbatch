/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Dictionary;
import myjob.func.db.DataParameterCollection;

/**
 *
 * @author guillermot
 */
public interface IDbCommand {

    PreparedStatement getStatement();

    void setStatement(PreparedStatement statement);

    Connection getConnection();

    void setConnection(Connection connection);

    DataParameterCollection getParameters();
    
    void setParameters(DataParameterCollection parameters);
    
    void setParameter(String name, Object value);
    
    Object getParameter(String name);
    
    ResultSet execute() throws SQLException;
    
    int executeNonQuery() throws SQLException;

    String getCommandText();

    void setCommandText(String commandText);

    String getPreparedCommandText();

    boolean isPrepared();

    void prepare() throws SQLException;

    public String getCommandTextWithParameters();
}
