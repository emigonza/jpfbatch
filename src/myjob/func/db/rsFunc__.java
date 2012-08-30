/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermot
 */
public class rsFunc__ {

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
            Logger.getLogger(rsFunc__.class.getName()).log(Level.SEVERE, null, ex);
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

        rs.first();

        do {

            retVal[loc_Conta++] = rsFunc__.rsRowToArray(rs, cantColumns, nullValue);


        } while (rs.next());

        for (int loc_Row = rowCount(rs); loc_Row < retVal.length; loc_Row++) {
            for (int loc_Col = 0; loc_Col < cantColumns; loc_Col++) {
                retVal[loc_Row][loc_Col] = nullValue;
            }
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
}
