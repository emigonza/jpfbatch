/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.func.db;

import java.sql.ResultSet;
import java.util.EventObject;

/**
 *
 * @author guillermot
 */
public class exportEventArgs extends EventObject {

    DataTable dataTable;

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public ResultSet getResultSet() {
        return dataTable.getinternalResultSet();
    }

    String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public exportEventArgs(Object source)
    {
        super(source);
    }

    public exportEventArgs(Object source, DataTable dataTable, String fileName)
    {
        super(source);
        this.fileName = fileName;
        this.dataTable = dataTable;
    }
}
