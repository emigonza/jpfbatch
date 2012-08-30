/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author guillermot
 */
public class JDataTable extends DataTable {

    public JDataTable(String string, Connection _dbConn) throws SQLException {
        super(string, _dbConn);
    }

    public JDataTable(ResultSet rs, Connection _dbConn) throws SQLException {
        super(rs, _dbConn);
    }

    @Override
    public int getRowCount()
    {
        return super.getRowCount() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex + 1 >= this.getRowCount()) {
            return null;
        }

        return super.getValueAt(rowIndex, columnIndex);
    }



}
