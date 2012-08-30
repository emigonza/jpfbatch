/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.SQLException;

/**
 *
 * @author guillermot
 */
public interface IDbCommandParameter {

    IDbCommand getDbCommand();

    void setDbCommand(IDbCommand dbCommand);

    String getParameterName();

    void setParameterName(String parameterName);

    Class getParameterType();

    void setParameterClass(Class type);

    Object getValue();

    void setValue(Object value);

    void resolveType();

    void replaceInCommand() throws SQLException;

    Integer getIndex();

    void setIndex(Integer index);
}
