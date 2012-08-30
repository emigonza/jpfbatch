/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.DocumentsExporter;

import java.util.EventObject;
import java.sql.ResultSet;
import javax.swing.table.TableModel;

/**
 *
 * @author guillermot
 */
public class toSheetEventArgs extends EventObject {

    ResultSet dataTable = null;
    TableModel tableModel = null;

    public ResultSet getDataTable() {
        return dataTable;
    }

    public void setDataTable(ResultSet dataTable) {
        this.dataTable = dataTable;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public toSheetEventArgs(Object source)
    {
        super(source);
    }

    public toSheetEventArgs(Object source, ResultSet dataTable, String fileName)
    {
        super(source);
        this.fileName = fileName;
        this.dataTable = dataTable;
    }

    public toSheetEventArgs(Object source, TableModel table, String fileName) {
        super(source);
        this.fileName = fileName;
        this.tableModel = table;
    }
}
