/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 *
 * @author guillermot
 */
public class DbCommand implements IDbCommand {

    boolean prepared = false;
    boolean compiled = false;
    String commandText = "";
    String preparedCommandText = "";
    PreparedStatement preparedStatement = null;
    Connection dbConn;
    DataParameterCollection parameters = new DataParameterCollection();

    public DbCommand() {
        super();
    }

    public DbCommand(Connection dbConn) {
        this.setConnection(dbConn);
    }

    public DbCommand(Connection dbConn, String sqlCmd) {
        this.setConnection(dbConn);
        this.setCommandText(sqlCmd);
    }

    public PreparedStatement getStatement() {
        return preparedStatement;
    }

    public void setStatement(PreparedStatement statement) {
        this.preparedStatement = statement;
    }

    public DataParameterCollection getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        for(int i = 0; i < parameters.length / 2; i++) {
            this.setParameter((String) parameters[i], parameters[i + 1]);
        }
    }

    public void setParameters(List<Object> parameters) {
        setParameters(parameters.toArray(new Object[] {}));
    }

    public void setParameters(Map<String, Object> parameters) {
        for(String s : parameters.keySet()) {
            this.setParameter(s, parameters.get(s));
        }
    }

    public void setParameters(DataParameterCollection parameters) {

        for (IDbCommandParameter p : parameters.values()) {
            p.setDbCommand(this);
        }

        this.parameters = parameters;
    }

    public void setParameter(String name, Object value) {

        IDbCommandParameter p = (IDbCommandParameter) new DbCommandParameter(this);

        p.setParameterName(name);

        p.setValue(value);

        this.parameters.put(name, p);

        this.prepared = false;
    }

    public Object getParameter(String name) {
        return this.parameters.get(name).getValue();
    }

    public ResultSet execute() throws SQLException {

        //System.out.println("preparando sqlcmd " + this.getCommandText());

        if (!prepared) {
            prepare();
        }

        //System.out.println("ejecutando sqlcmd " + this.getPreparedCommandText());

        ResultSet rs = null;
        try {
            rs = this.preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println(this.commandText);
            throw ex;
        }


        return rs;

    }

    public int executeNonQuery() throws SQLException {
        if (!prepared) {
            prepare();
        }

        return this.preparedStatement.executeUpdate();
    }

    public void prepare() throws SQLException {
        if (!compiled) {
            compile();
        }

        if (this.preparedCommandText == null) {
            throw new SQLException("El comando es nulo");
        }

        if (dbConn == null) {
            throw new SQLException("La conexión es nula");
        }

        this.preparedStatement = dbConn.prepareStatement(this.preparedCommandText, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        this.preparedStatement.clearParameters();

        for (IDbCommandParameter p : parameters.values()) {
            p.replaceInCommand();
        }

        prepared = true;

    }

    public boolean isPrepared() {
        return prepared;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        preparedCommandText = "";
        this.commandText = commandText;
        prepared = false;
        compiled = false;
    }

    public Connection getConnection() {
        return dbConn;
    }

    public void setConnection(Connection connection) {
        dbConn = connection;
    }

    public String getPreparedCommandText() {
        if (this.preparedCommandText.length() == 0) {
            this.preparedCommandText = this.commandText;
        }

        return this.preparedCommandText;
    }

    public PreparedStatement getPreparedStatement() throws SQLException {
        if (!prepared) {
            this.prepare();
        }
        return this.preparedStatement;
    }

    void compile() {

        Integer loc_i = -1;
        String loc_CommandText = this.commandText;
        String loc_Name = "";

        for (IDbCommandParameter p : parameters.values()) {
            p.setDbCommand(this);

            loc_Name = p.getParameterName();
            if (!loc_Name.startsWith(DbCommandParameter.parameterMark)) {
                loc_Name = ":" + loc_Name;
            }

            loc_i = loc_CommandText.indexOf(loc_Name);

            for (IDbCommandParameter p1 : parameters.values()) {

                if (p1.getIndex() > loc_i) {
                    p1.setIndex(1 + p1.getIndex() - loc_Name.length());
                }
            }

            p.setIndex(loc_i);

            loc_CommandText = loc_CommandText.replace(loc_Name, "?");
        }

        //javax.swing.JOptionPane.showMessageDialog(null, loc_CommandText);

        this.preparedCommandText = loc_CommandText;

        compiled = true;
    }

    IDbCommandParameter getByIndex(Integer index) {

        for (IDbCommandParameter p : parameters.values()) {

            if (p.getIndex() == index) {
                return p;
            }
        }

        return null;
    }

    public String getCommandTextWithParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
