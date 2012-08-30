/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class rsFunc {

    public static boolean debug = false;

    public static ResultSet executeQuery(String sqlCmd, Connection dbConn) throws SQLException {
        Statement st = rsFunc.createStatement(dbConn);
        return st.executeQuery(sqlCmd);
    }

    public static int getColumnIndex(ResultSetMetaData rsmd, String columnName) {
        try {
            for (int i = 1; i < rsmd.getColumnCount(); i++) {
                if (rsmd.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return i;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public static Object[] rsRowToArray(ResultSet rs) throws SQLException, Exception {

        return rsRowToArray(rs, rs.getMetaData().getColumnCount(), null);

    }

    public static Object[] rsRowToArray(ResultSet rs, Integer length) throws SQLException, Exception {
        return rsRowToArray(rs, length, null);
    }

    public static Object[] rsRowToArray(ResultSet rs, Integer length, Object nullValue) throws SQLException, Exception {

        if (length < rs.getMetaData().getColumnCount()) {
            throw new Exception("Columnas del rs mayor que cantColumns");
        }

        Object[] retVal = new Object[length];

        for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {
            retVal[loc_Conta] = rs.getObject(loc_Conta + 1);
        }

        for (int loc_Conta = rs.getMetaData().getColumnCount(); loc_Conta < length; loc_Conta++) {
            retVal[loc_Conta] = nullValue;
        }

        return retVal;
    }

    public static Object[][] rsToArray(ResultSet rs) throws SQLException {
        try {
            return rsToArray(rs, rs.getMetaData().getColumnCount(), 0, null);
        } catch (Exception ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static Object[][] rsToArray(ResultSet rs, int cantColumns) throws SQLException, Exception {
        return rsToArray(rs, cantColumns, 0, null);
    }

    public static Object[][] rsToArray(ResultSet rs, int cantColumns, int extraRows) throws SQLException, Exception {
        return rsToArray(rs, cantColumns, extraRows, null);
    }

    public static Object[][] rsToArray(ResultSet rs, int cantColumns, int extraRows, Object nullValue) throws SQLException, Exception {

        if (cantColumns < rs.getMetaData().getColumnCount()) {
            throw new Exception("Columnas del rs mayor que cantColumns");
        }

        Object[][] retVal = new Object[rowCount(rs) + extraRows][cantColumns];

        int loc_Conta = 0;

        if (rs.first()) {

            do {

                retVal[loc_Conta++] = rsFunc.rsRowToArray(rs, cantColumns, nullValue);

            } while (rs.next());
        }

        for (int loc_Row = rowCount(rs); loc_Row < retVal.length; loc_Row++) {
            for (int loc_Col = 0; loc_Col < cantColumns; loc_Col++) {
                retVal[loc_Row][loc_Col] = nullValue;
            }
        }


        return retVal;
    }

    public static void rsToFile(ResultSet rs, String fileName, Integer[] rowsLength, String[] rowsFormat) throws IOException {
        File f = new File(fileName);

        if (f.exists()) {
            f.delete();
        }

        BufferedWriter salida = new BufferedWriter(new FileWriter(f));
        try {
            rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
            salida.write("error al escribir archivo\n");
            salida.flush();
            salida.close();
            return;
        }
        try {
            while (rs.next()) {
                salida.append(rowToString(rs, rowsLength, rowsFormat));
                salida.append("\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
            salida.write("error al escribir archivo\n");
            salida.flush();
            salida.close();
            return;
        }


        salida.flush();
        salida.close();

    }

    public static void rsToFile(ResultSet rs, String fileName, Integer[] rowsLength) throws IOException {
        rsToFile(rs, fileName, rowsLength, null);
    }

    public static void rsToFile(ResultSet rs, String fileName) throws IOException {
        rsToFile(rs, fileName, null, null);
    }

    public static String rowToString(ResultSet rs) throws SQLException {
        return rowToString(rs, null, null);
    }

    public static String rowToString(ResultSet rs, Integer[] rowsLength, String[] rowsFormat) throws SQLException {
        String retVal = "";

        Object val = null;

        if (rowsLength == null || rowsLength.length != rs.getMetaData().getColumnCount()) {
            rowsLength = new Integer[rs.getMetaData().getColumnCount()];

            for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {
                rowsLength[loc_Conta] = rs.getMetaData().getPrecision(loc_Conta + 1);
            }
        }

        if (rowsFormat == null || rowsFormat.length != rs.getMetaData().getColumnCount()) {
            rowsFormat = new String[rs.getMetaData().getColumnCount()];

            for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {
                rowsFormat[loc_Conta] = "";
            }
        }

        for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {
            retVal += GeneralFunc.padObject(GeneralFunc.formatObject(rs.getObject(loc_Conta + 1), rowsFormat[loc_Conta]), rowsLength[loc_Conta], ' ');
        }

        return retVal;
    }

    public static Integer rowCount(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        //rs.moveToCurrentRow();
        if (!rs.last()) {
            return 0;
        }
        return rs.getRow();
        //internalRowSet.absolute(currentPos);
    }

    public static String getColumnsCreateSQLCmd(ResultSet rs, String columnPrefix, Map<Integer, String> dbTypes) throws java.sql.SQLException {
        String retVal = "";

        for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {

            if (loc_Conta > 0) {
                retVal += ",\n";
            }

            retVal += columnPrefix + rs.getMetaData().getColumnName(loc_Conta + 1) + " " + dbTypes.get(rs.getMetaData().getColumnType(loc_Conta + 1));

            if (rs.getMetaData().getColumnType(loc_Conta + 1) == java.sql.Types.VARCHAR
                    || rs.getMetaData().getColumnType(loc_Conta + 1) == java.sql.Types.FLOAT) {
                retVal += "(" + ((Integer) rs.getMetaData().getPrecision(loc_Conta + 1)).toString();


                if (rs.getMetaData().getScale(loc_Conta + 1) > 0) {
                    retVal += "," + ((Integer) rs.getMetaData().getScale(loc_Conta + 1)).toString();
                }

                retVal += ")";
            }
        }

        return retVal;

    }

    public static String getCreateSQLCmd(ResultSet[] ResultSets, String tableName, String driver) throws java.sql.SQLException {
        return getCreateSQLCmd(ResultSets, tableName, getDbTypes(driver), "");
    }

    public static String getCreateSQLCmd(ResultSet[] ResultSets, String tableName, String driver, String autoCol) throws java.sql.SQLException {
        return getCreateSQLCmd(ResultSets, tableName, getDbTypes(driver), autoCol);
    }

    /**
     * devuelve un comando sql, el arreglo dbTypes debe coincidir con los tipos de java.sql.Types
     * @param rs
     * @param types
     * @return
     */
    public static String getCreateSQLCmd(ResultSet[] ResultSets, String tableName, Map<Integer, String> dbTypes, String autoCol) throws java.sql.SQLException {


        String retVal = "CREATE TABLE " + tableName + " (\n";

        if (autoCol.length() > 0) {
            retVal += autoCol + " INTEGER, \n";
        }

        String prefix = "";
        String tables = "";

        for (ResultSet rs : ResultSets) {

            if (ResultSets.length > 1) {
                prefix = rs.getMetaData().getTableName(1) + "__";
            }

            if (rsFunc.debug) {
                if (tables.length() > 0) {
                    tables += ", ";
                }

                tables += rs.getMetaData().getTableName(1);
            }

            retVal += getColumnsCreateSQLCmd(rs, prefix, dbTypes);
        }

        // claves primarias??

        // claves foraneas??

        retVal += ");";

        if (rsFunc.debug) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.ALL, "getCreateSQLCmd(ResultSets[" + tables + "], " + tableName + ", dbTypes," + autoCol + ")\n" + retVal);

        }

        return retVal;
    }

    public static Statement createStatement(Connection dbConn) throws SQLException {

        return dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

    }

    public static Map<Integer, String> getDbTypes(String driver) {
        Map<Integer, String> dbTypes = new HashMap<Integer, String>();

        if (driver.toLowerCase().equals("hsqldb")) {
            dbTypes.put(java.sql.Types.BIGINT, "BIGING");
            dbTypes.put(java.sql.Types.VARCHAR, "VARCHAR");
            dbTypes.put(java.sql.Types.INTEGER, "INTEGER");
            dbTypes.put(java.sql.Types.FLOAT, "FLOAT");
            dbTypes.put(java.sql.Types.DOUBLE, "DOUBLE");
            dbTypes.put(java.sql.Types.BOOLEAN, "BOOLEAN");
            dbTypes.put(java.sql.Types.DATE, "TIMESTAMP");

        } else if (driver.toLowerCase().equals("postgresql")) {

            dbTypes.put(java.sql.Types.BIGINT, "BIGING");
            dbTypes.put(java.sql.Types.VARCHAR, "CHARACTER VARYING");
            dbTypes.put(java.sql.Types.INTEGER, "INTEGER");
            dbTypes.put(java.sql.Types.FLOAT, "FLOAT");
            dbTypes.put(java.sql.Types.DOUBLE, "DOUBLE");
            dbTypes.put(java.sql.Types.BOOLEAN, "BOOLEAN");
            dbTypes.put(java.sql.Types.DATE, "TIMESTAMP WITHOUT TIME ZONE");
        }

        return dbTypes;
    }

    public static Integer getBDType(Class nativeType) {

        if (nativeType == String.class) {
            return java.sql.Types.VARCHAR;
        }

        if (nativeType == Integer.class || nativeType == int.class) {
            return java.sql.Types.INTEGER;
        }

        if (nativeType == long.class || nativeType == Long.class) {
            return java.sql.Types.BIGINT;
        }

        if (nativeType == Short.class || nativeType == short.class) {
            return java.sql.Types.SMALLINT;
        }

        if (nativeType == Date.class) {
            return java.sql.Types.DATE;
        }

        if (nativeType == Calendar.class) {
            return java.sql.Types.TIMESTAMP;
        }

        if (nativeType == Float.class || nativeType == float.class) {
            return java.sql.Types.FLOAT;
        }

        if (nativeType == Double.class || nativeType == double.class) {
            return java.sql.Types.DOUBLE;
        }

        if (nativeType == Boolean.class || nativeType == boolean.class) {
            return java.sql.Types.BOOLEAN;
        }

        return -1;
    }

    public static Class getNativeType(Integer dbType) {
        switch (dbType) {
            case java.sql.Types.ARRAY:
                return Array.class;
            case java.sql.Types.BIGINT:
                return Long.class;
            case java.sql.Types.BINARY:
                return Boolean.class;
            case java.sql.Types.BIT:
                return Boolean.class;
            case java.sql.Types.BLOB:
                return (new byte[]{}).getClass();
            case java.sql.Types.BOOLEAN:
                return Boolean.class;
            case java.sql.Types.CHAR:
                return char.class;
            case java.sql.Types.CLOB:
                return (new char[]{}).getClass();
            case java.sql.Types.DATE:
                return Date.class;
            case java.sql.Types.DECIMAL:
                return Float.class;
            case java.sql.Types.DOUBLE:
                return Double.class;
            case java.sql.Types.FLOAT:
                return Float.class;
            case java.sql.Types.INTEGER:
                return Integer.class;
            case java.sql.Types.JAVA_OBJECT:
                return Object.class;
            case java.sql.Types.LONGNVARCHAR:
                return String.class;
            case java.sql.Types.LONGVARCHAR:
                return String.class;
            case java.sql.Types.NUMERIC:
                return Double.class;
            case java.sql.Types.REAL:
                return Float.class;
            case java.sql.Types.SMALLINT:
                return Short.class;
            case java.sql.Types.TIMESTAMP:
                return Calendar.class;
            case java.sql.Types.TINYINT:
                return Short.class;
            case java.sql.Types.VARCHAR:
                return String.class;
        }

        return null;
    }

    public static String castVal(Object value) {

        if (value == null) {
            return "";
        }

        if (value.getClass() == Boolean.class) {
            if ((Boolean) value) {
                return "true";
            } else {
                return "false";
            }
        }

        if (value.getClass() == String.class) {
            return "'" + (String) value + "'";
        }

        if (value.getClass() == Date.class) {
            return "'" + myjob.func.general.GeneralFunc.dateFormat((Date) value, "yyyy/MM/dd") + "'";
        }

        if (value.getClass() == Calendar.class) {
            return "'" + myjob.func.general.GeneralFunc.dateFormat(((Calendar) value), "yyyy/MM/dd HH:mm:ss") + "'";
        }

        return value.toString();

    }

    public static String replaceParameters(String sqlCmd, Dictionary<String, Object> params) {
        String paramMark = ":";

        if (sqlCmd.contains("@")) {
            paramMark = "@";
        } else if (sqlCmd.contains("?")) {
            paramMark = "?";
        }

        return replaceParameters(sqlCmd, paramMark, params);
    }

    public static String replaceParameters(String sqlCmd, ResultSet[] ResultSets, boolean withTableName) {
        String paramMark = ":";

        if (sqlCmd.contains("@")) {
            paramMark = "@";
        } else if (sqlCmd.contains("?")) {
            paramMark = "?";
        }

        return replaceParameters(sqlCmd, paramMark, ResultSets, withTableName);
    }

    public static String replaceParameters(String sqlCmd, ResultSet[] ResultSets) {
        String paramMark = ":";

        if (sqlCmd.contains("@")) {
            paramMark = "@";
        } else if (sqlCmd.contains("?")) {
            paramMark = "?";
        }

        return replaceParameters(sqlCmd, paramMark, ResultSets);
    }

    public static String replaceParameters(String sqlCmd, String paramMark, ResultSet[] ResultSets) {
        try {
            return replaceParameters(sqlCmd, paramMark, rsFunc.getParams(ResultSets));
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String replaceParameters(String sqlCmd, String paramMark, ResultSet[] ResultSets, boolean withTableName) {
        try {
            return replaceParameters(sqlCmd, paramMark, rsFunc.getParams(ResultSets, withTableName));
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String replaceParameters(String sqlCmd, String paramMark, Dictionary<String, Object> params) {

        String paramName = "";

        if (sqlCmd.contains("%1")) {

            List<Object> pars = Collections.list(params.elements());

            for (Integer loc_Conta = 0; loc_Conta < pars.size(); loc_Conta++) {
                sqlCmd = sqlCmd.replace("%" + loc_Conta.toString(), castVal(pars.get(loc_Conta)));
            }

        } else {

            List<String> keys = Collections.list(params.keys());

            for (String pName : keys) {

                if (pName.startsWith(paramMark)) {
                    paramName = pName;
                } else {
                    paramName = paramMark + pName.trim();

                }

                //System.out.println("aca -->>   '" + paramName + "' " + castVal(params.get(pName)));

                sqlCmd = sqlCmd.replace(paramName, castVal(params.get(pName))).
                        replace(paramName.toUpperCase(), castVal(params.get(pName))).
                        replace(paramName.toLowerCase(), castVal(params.get(pName)));
            }
        }

        return sqlCmd;
    }

    public static String getInsertCommand(Connection dbConn, String tableName) {
        return getInsertCommand(dbConn, tableName, "");
    }

    public static String getInsertCommand(Connection dbConn, String tableName, String autoColumn) {

        ResultSet rs = null;

        try {
            rs = rsFunc.createStatement(dbConn).executeQuery("SELECT * FROM " + tableName + " limit 1");
        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getInsertCommand(rs, tableName, autoColumn);
    }

    public static String getInsertCommand(ResultSet rs, String tableName) {
        return getInsertCommand(rs, tableName, "");
    }

    public static String getInsertCommand(ResultSet rs, String tableName, String autoColumn) {
        String retVal = "";
        String values = " VALUES (";

        try {
            retVal = "INSERT INTO " + tableName + "(";

            for (Integer loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {


                if (loc_Conta > 0) {
                    retVal += ", ";
                    values += ", ";
                } else {
                    if (autoColumn.length() > 0) {
                        retVal += "Id, ";
                        values += ":Id, ";
                    }
                }

                retVal += rs.getMetaData().getColumnName(loc_Conta);

                values += ":" + rs.getMetaData().getColumnName(loc_Conta);

            }

            retVal += ") " + values + ");\n";

        } catch (SQLException ex) {
            Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }

    public static void copyTable(Connection dbConn, ResultSet table, String toTableName, String autoCol, Dictionary<Integer, String> dbTypes) throws SQLException {


        String sqlInsert = getInsertCommand(table, toTableName, autoCol);
        String sqlCmd = "";

        int loc_Conta = 1;

        table.beforeFirst();

        Dictionary<String, Object> params;

        while (table.next()) {

            params = getParams(table, false);

            if (autoCol.length() > 0) {
                params.put(autoCol, loc_Conta);
            }

            sqlCmd = replaceParameters(sqlInsert, params);

            rsFunc.createStatement(dbConn).execute(sqlCmd);

        }

    }

    public static Dictionary<String, Object> getParams(ResultSet[] ResultSets) throws SQLException {

        return getParams(ResultSets, ResultSets.length > 1);
    }

    public static Dictionary<String, Object> getParams(ResultSet[] ResultSets, boolean withTableName) throws SQLException {
        Dictionary<String, Object> params = new Hashtable<String, Object>();

        for (ResultSet rs : ResultSets) {
            try {
                GeneralFunc.addRange(params, rsFunc.getParams(rs, withTableName));
            } catch (SQLException ex) {
                Logger.getLogger(rsFunc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return params;
    }

    public static Dictionary<String, Object> getParams(ResultSet rs, boolean withTableName) throws SQLException {
        Dictionary<String, Object> retVal = new Hashtable<String, Object>();

        String tableName = "";

        if (withTableName) {
            tableName = rs.getMetaData().getTableName(1) + "__";
        }

        for (int loc_Conta = 0; loc_Conta < rs.getMetaData().getColumnCount(); loc_Conta++) {
            retVal.put(tableName + rs.getMetaData().getColumnName(loc_Conta), rs.getObject(loc_Conta));
        }

        return retVal;
    }

    public static Connection getConnection(String driverName, String server, String dataBase, String userName, String password) throws SQLException {
        return getConnection(driverName, server, dataBase, -1, userName, password);

    }

    public static Connection getConnection(String driverName, String server, String dataBase, int port, String userName, String password) throws SQLException {
        return getConnection(getConnectionString(driverName, server, dataBase, port), userName, password);
    }

    public static String getConnectionString(String driverName, String server, String dataBase) {
        return getConnectionString(driverName, server, dataBase, -1);
    }

    public static String getConnectionString(String driverName, String server, String dataBase, String port) {
        if (port.trim().length() == 0) {
            port = "-1";
        }
        return getConnectionString(driverName, server, dataBase, Integer.parseInt(port));
    }

    public static String getConnectionString(String driverName, String server, String dataBase, int port) {
        String conn = "jdbc:" + driverName + "://" + server;

        if (port > 0) {
            conn += ":" + ((Integer) port).toString();
        }

        conn += "/" + dataBase;

        return conn;
    }

    public static Connection getConnection(String connectionString, String userName, String password) throws SQLException {


        return DriverManager.getConnection(connectionString, userName, password);
    }
}
