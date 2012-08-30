/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.sql.rowset.spi.SyncProviderException;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 * Datatable puro sin rutina de inserción
 */
public class DataTable extends AbstractTableModel {

    protected String[] keyColumns = null;
    protected Connection dbConn = null;
    protected ResultSet internalResultSet = null;
    protected ResultSetMetaData tableMetaData = null;
    protected DatabaseMetaData dbMetaData = null;
    //CachedRowSetImpl internalRowSet = null;
    protected boolean ReadOnly = false;
    protected String tableName = "not setted";
    protected String inDateFormat = "dd/MM/yyyy";
    protected String outDateFormat = "yyyy/MM/dd";
    protected boolean reloadAfterInsertAndDelete = false;

    public boolean isReloadAfterInsertAndDelete() {
        return reloadAfterInsertAndDelete;
    }

    public void setReloadAfterInsertAndDelete(boolean reloadAfterInsert) {
        this.reloadAfterInsertAndDelete = reloadAfterInsert;
    }

    public String getInDateFormat() {
        return inDateFormat;
    }

    public void setInDateFormat(String inDateFormat) {
        this.inDateFormat = inDateFormat;
    }

    public String getOutDateFormat() {
        return outDateFormat;
    }

    public void setOutDateFormat(String outDateFormat) {
        this.outDateFormat = outDateFormat;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(String[] keyColumns) {
        this.keyColumns = keyColumns;
    }
    public Object[] fixedKey = null;

    public Object[] getFixedKey() {
        return fixedKey;
    }

    public void setFixedKey(Object[] fixedKey) {
        this.fixedKey = fixedKey;
    }

    public DataTable(ResultSet rs, Connection dbConn) throws SQLException {
        super();
        this.setDBConn(dbConn);
        setinternalResultSet(rs);
    }

    public DataTable(String query, Connection dbConn) throws SQLException {
        super();
        this.setDBConn(dbConn);
        setinternalResultSet(Queryhelper.Query(query, this.dbConn));
    }

    public DataTable(ResultSet rs) {
        super();
        setinternalResultSet(rs);
    }

    public Connection getDBConn() {
        return dbConn;
    }

    public void setDBConn(Connection _DBConn) {
        this.dbConn = _DBConn;
        try {
            this.dbMetaData = _DBConn.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al intetar establecer la conección", ex);
        }
    }

    public ResultSetMetaData getMetaData() {
        if (internalResultSet == null) {
            return null;
        }
        if (tableMetaData == null) {
            try {
                tableMetaData = internalResultSet.getMetaData();
            } catch (SQLException ex) {
                Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener los metadatos", ex);
                return null;
            }
        }
        return tableMetaData;
    }

    public ResultSet getinternalResultSet() {
        return internalResultSet;
    }

    public void setinternalResultSet(ResultSet rs) {
        this.internalResultSet = rs;
    }

    @Override
    public int getRowCount() {
        return getWithoutNewRowCount();
    }

    public Object[] getRow(int index) {
        Object[] loc_o = new Object[this.getColumnCount()];

        for (int loc_Column = 0; loc_Column < this.getColumnCount(); loc_Column++) {
            loc_o[loc_Column] = this.getValueAt(index, loc_Column);
        }

        return loc_o;
    }

    public Map<String, Object> getRowWithNames(int index) {

        Map<String, Object> loc_RetVal = new HashMap<String, Object>();

        Object[] loc_o = getRow(index);

        for (int loc_Column = 0; loc_Column < loc_o.length; loc_Column++) {
            loc_RetVal.put(this.getColumnName(loc_Column), loc_o[loc_Column]);
        }


        return loc_RetVal;
    }

    public int getWithoutNewRowCount() {
        if (internalResultSet == null) {
            return 0;
        }

        int size = 0;
        //int currentPos = -1;

        //return internalRowSet.size();

        try {
            internalResultSet.beforeFirst();
            //internalResultSet.moveToCurrentRow();
            if (!internalResultSet.last()) {
                //size = 0;
            } else {
                size = internalResultSet.getRow();
            }
            //internalRowSet.absolute(currentPos);
        } catch (SQLException e) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener la cantidad de registros sin el vacio", e);
            return 0;
        }


        //Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, "size " + ((Integer) size).toString());

        return size;
    }

    @Override
    public int getColumnCount() {
        try {
            if (this.getMetaData() == null) {
                //Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, "metadata nula");
                return 0;
            }
            return this.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener la cantidad de columnas", ex);
            return -1;
        }
    }

    /**
     * Devuelve el nro de columna, o -1 en caso de que no exista
     * @param column
     * @return
     */
    public int getColumnOrder(String column) {
        if (column != null) {
            for (int loc_Conta = 0; loc_Conta < this.getColumnCount(); loc_Conta++) {
                if (this.getColumnName(loc_Conta).toLowerCase().equals(column.toLowerCase())) {
                    return loc_Conta;
                }
            }
        }

        return -1;
    }

    public List<String> getColumnNames() {
        List<String> retVal = new ArrayList();
        try {
            for (int i = 1; i <= getMetaData().getColumnCount(); i++) {
                retVal.add(getMetaData().getColumnName(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener los nombres de columna", ex);
        }

        return retVal;
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            return getMetaData().getColumnName(columnIndex + 1);
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener el nombre de la columna " + columnIndex, ex);
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            //Logger.getLogger(DataTable.class).log(Level.DEBUG, "Obteniendo clase para " + getMetaData().getColumnType(columnIndex + 1));
            return DBHelpers.JDBCtoJavaType(getMetaData().getColumnType(columnIndex + 1));
            //return String.class;

        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener la clase de la columna " + columnIndex, ex);
        }


        return String.class;
    }

    public boolean isReadOnly() {
        return ReadOnly;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.ReadOnly = isReadOnly;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !ReadOnly;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex >= this.getRowCount()) {
            return null;
        }

        if (columnIndex < 0 || rowIndex < 0) {
            return null;
        }

        try {
            if (internalResultSet.absolute(rowIndex + 1)) {
                return internalResultSet.getObject(columnIndex + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "rowindex " + rowIndex + " columnIndex " + columnIndex + " rowcount " + getRowCount(), ex);
        }

        return null;
    }

    public Object getValueAt(int rowIndex, String column) {

        int loc_Column = 0;

        for (int loc_Conta = 0; loc_Conta < this.getColumnCount(); loc_Conta++) {
            if (this.getColumnName(loc_Conta).toLowerCase().equals(column.toLowerCase())) {
                loc_Column = loc_Conta;
                break;
            }
        }

        return getValueAt(rowIndex, loc_Column);
    }

    public Object[] getObjectRow(int rowIndex) {
        if (rowIndex >= this.getRowCount()) {
            return null;
        }

        Object[] loc_o = new Object[this.getColumnCount()];


        try {
            internalResultSet.absolute(rowIndex + 1);

            for (int loc_Conta = 0; loc_Conta < this.getColumnCount(); loc_Conta++) {
                loc_o[loc_Conta] = internalResultSet.getObject(loc_Conta + 1);
            }


        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al obtener el array de la fila " + rowIndex, ex);
        }

        return loc_o;

    }

    public void deleteRow(int rowIndex) {
        removeRow(rowIndex);
    }

    public void removeRow(int rowIndex) {
        try {
            if (internalResultSet.absolute(rowIndex + 1)) {
                internalResultSet.deleteRow();
                //internalResultSet.updateRow();
                if (this.reloadAfterInsertAndDelete) {
                    this.ReLoad();
                }
            } else {
                Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "No se puede mover el cursor a la fila " + rowIndex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al borrar la fila " + rowIndex, ex);
        }
        this.fireTableDataChanged();
    }

    /*
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    valuesChanged.setValue(aValue, rowIndex, columnIndex);
    }
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        if (aValue == null) {
            JOptionPane.showMessageDialog(null, "el valor es nulo");
        }

        setValueAt(aValue, rowIndex, columnIndex, false);
    }

    void setValueAt(Object aValue, int rowIndex, int columnIndex, boolean onlyUpdate) {

        boolean insert = false;
        int keyVal = -1;
        int pkCol = -1;

        Logger.getLogger(DataTable.class).log(Level.DEBUG, "ingresando a setValueAt valor:'" + aValue + "' rowIndex:" + rowIndex + " columnIndex " + columnIndex + " onlyUpdate " + onlyUpdate);

        if (!onlyUpdate) {
            try {
                this.internalResultSet.moveToCurrentRow();
                this.internalResultSet.afterLast();

                Logger.getLogger(DataTable.class).log(Level.DEBUG, "cant filas " + this.getRowCount() + " pos actual " + (rowIndex + 1));

                if (rowIndex + 1 == this.getRowCount()) {
                    // inserto una nueva fila

                    if (this.dbMetaData != null) {
                        Logger.getLogger(DataTable.class).log(Level.DEBUG, "Calculando claves primarias de tabla " + tableName);
                        ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, tableName);
                        if (primaryKeys.next()) {
                            // la primera columna es la que tomo automática
                            String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
                            pkCol = this.getColumnOrder(primaryKeyColumn);

                            if (pkCol != columnIndex) {
                                if (this.getValueAt(rowIndex, pkCol) == null || (int) (Integer) this.getValueAt(rowIndex, pkCol) == 0) {

                                    for (int i = 0; i < this.getRowCount() - 1; i++) {
                                        if (keyVal < (int) (Integer) this.getValueAt(i, pkCol)) {
                                            keyVal = (int) (Integer) this.getValueAt(i, pkCol);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    try {
                        Logger.getLogger(DataTable.class).log(Level.DEBUG, "Moviendo cursor a InsertRow");
                        internalResultSet.moveToInsertRow();
                        insert = true;
                    } catch (SQLException ex) {
                        Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "error sql al mover a la columna de insercion", ex);
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, ex.getMessage(), ex);
            }
        }

        try {
            //System.out.println(aValue);
            if (!onlyUpdate && !insert) {
                // si no inserto y no modifico unicamente voy a la fila que modifico
                Logger.getLogger(DataTable.class).log(Level.DEBUG, "Moviendo cursor a " + (rowIndex + 1));
                internalResultSet.absolute(rowIndex + 1);
            }

            Object val = null;

            switch (getMetaData().getColumnType(columnIndex + 1)) {
                case -7:
                    internalResultSet.updateBoolean(columnIndex + 1, Boolean.parseBoolean((String) aValue));
                    break;
                case -6:
                    internalResultSet.updateInt(columnIndex + 1, Integer.parseInt((String) aValue));
                    break;
                case -5:
                    internalResultSet.updateLong(columnIndex + 1, Long.parseLong((String) aValue));
                    break;
                case -4:
                    internalResultSet.updateObject(columnIndex + 1, aValue);
                    break;
                case -3:
                    internalResultSet.updateObject(columnIndex + 1, aValue);
                    break;
                case -2:
                    internalResultSet.updateObject(columnIndex + 1, aValue);
                    break;
                case -1:
                    internalResultSet.updateString(columnIndex + 1, (String) aValue);
                    break;
                case 0:
                    break;
                case 1:
                    internalResultSet.updateInt(columnIndex + 1, Integer.parseInt((String) aValue));
                    break;
                case 2:
                    internalResultSet.updateDouble(columnIndex + 1, (Double) aValue);
                    break;
                case 3:
                    internalResultSet.updateFloat(columnIndex + 1, (Float) aValue);
                    break;
                case 4:
                    if (aValue == null) {
                        aValue = 0;
                    }
                    if (aValue.getClass().equals(Integer.class)) {
                        internalResultSet.updateInt(columnIndex + 1, (Integer) aValue);
                    } else {
                        internalResultSet.updateInt(columnIndex + 1, Integer.parseInt((String) aValue));
                    }
                    break;
                case 5:
                    internalResultSet.updateInt(columnIndex + 1, Integer.parseInt((String) aValue));
                    break;
                case 6:
                    internalResultSet.updateFloat(columnIndex + 1, (Float) aValue);
                    break;
                case 7:
                    internalResultSet.updateFloat(columnIndex + 1, (Float) aValue);
                    break;
                case 8:
                    internalResultSet.updateDouble(columnIndex + 1, (Double) aValue);
                    break;
                case 12:
                    internalResultSet.updateString(columnIndex + 1, (String) aValue);
                    break;
                case 91:
                    if (aValue.getClass().equals(String.class)) {
                        if (((String) aValue).trim().length() > 0) {
                            DateFormat inFormatter = new SimpleDateFormat(inDateFormat);
                            internalResultSet.updateDate(columnIndex + 1, new Date(((java.util.Date) inFormatter.parse((String) aValue)).getTime()));
                        } else {
                            java.util.Date loc_d = new java.util.Date();
                            internalResultSet.updateDate(columnIndex + 1, new Date(loc_d.getTime()));
                        }
                    } else {
                        internalResultSet.updateDate(columnIndex + 1, (java.sql.Date) aValue);
                    }
                    break;
                case 92:

                    if (aValue.getClass() == java.util.Date.class) {
                        val = java.sql.Date.valueOf(GeneralFunc.stringFormat("{0:yyyy-MM-dd HH:mm:ss} ", (java.util.Date) aValue));
                    } else if (aValue.getClass() == java.sql.Date.class) {
                        val = (java.sql.Date) aValue;
                    } else if (aValue.getClass() == String.class) {
                        val = java.sql.Date.valueOf((String) aValue);
                    }

                    internalResultSet.updateDate(columnIndex + 1, (java.sql.Date) val);
                    break;
                case 93:

                    if (aValue.getClass() == java.util.Date.class) {
                        val = java.sql.Date.valueOf(GeneralFunc.stringFormat("{0:yyyy-MM-dd}", (java.util.Date) aValue));
                    } else if (aValue.getClass() == java.sql.Date.class) {
                        val = (java.sql.Date) aValue;
                    } else if (aValue.getClass() == String.class) {
                        val = java.sql.Date.valueOf((String) aValue);
                    }

                    internalResultSet.updateDate(columnIndex + 1, (java.sql.Date) val);
                    break;
                case 1111:
                    internalResultSet.updateObject(columnIndex + 1, aValue);
                    break;
                default:
                    internalResultSet.updateObject(columnIndex + 1, aValue);
                    break;
            }
        } catch (ParseException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al parsear valor row:" + rowIndex + ", col:" + columnIndex + " valor " + aValue, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error sql al establecer valor en :" + rowIndex + ", col:" + columnIndex + " valor " + aValue, ex);
        } catch (ClassCastException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error al castear valor row:" + rowIndex + ", col:" + columnIndex + " valor " + aValue, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "Error en formato numerico row:" + rowIndex + ", col:" + columnIndex + " valor " + aValue, ex);
        }

        try {
            if (!onlyUpdate) {
                if (insert) {
                    Logger.getLogger(DataTable.class.getName()).log(Level.DEBUG, "Insertando fila");
                    //Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, "inserting");
                    for (int loc_Conta = 0; loc_Conta < this.getColumnCount(); loc_Conta++) {
                        if (loc_Conta != columnIndex) {
                            Logger.getLogger(DataTable.class).log(Level.DEBUG, "seteando valor por defecto para " + loc_Conta + " [" + this.getColumnClass(loc_Conta) + "] '" + myjob.func.general.GeneralFunc.getDefaultValue(this.getColumnClass(loc_Conta)) + "'");
                            setValueAt(myjob.func.general.GeneralFunc.getDefaultValue(this.getColumnClass(loc_Conta)), rowIndex, loc_Conta, true);
                            //internalResultSet.updateObject(loc_Conta, myjob.func.general.GeneralFunc.getDefaultValue(this.getColumnClass(loc_Conta)));
                        }
                    }

                    if (pkCol >= 0 && keyVal > 0) {
                        Logger.getLogger(DataTable.class).log(Level.DEBUG, "seteando valor para columna clave " + pkCol + " '" + (keyVal + 1) + "'");
                        setValueAt(keyVal + 1, rowIndex, pkCol, true);
                        //internalResultSet.updateObject(pkCol, keyVal + 1);
                    }

                    if (this.keyColumns != null && this.fixedKey != null) {
                        for (int loc_Conta = 0; loc_Conta < keyColumns.length; loc_Conta++) {
                            if (fixedKey[loc_Conta] != null) {
                                Logger.getLogger(DataTable.class).log(Level.DEBUG, "Seteando valor en columna " + loc_Conta + " '" + fixedKey[loc_Conta] + "'");
                                //internalResultSet.updateObject(loc_Conta, fixedKey[loc_Conta]);
                                setValueAt(fixedKey[loc_Conta], rowIndex, loc_Conta, true);
                            }
                        }
                    }

                    internalResultSet.insertRow();
                    if (this.reloadAfterInsertAndDelete) {
                        this.ReLoad();
                    }

                    Logger.getLogger(DataTable.class.getName()).log(Level.DEBUG, "fila insertada ");

                } else {

                    Logger.getLogger(DataTable.class.getName()).log(Level.DEBUG, "Actualizando fila");

                    internalResultSet.updateRow();
                }


                fireTableCellUpdated(rowIndex, columnIndex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataTable.class.getName()).log(Level.ERROR, "row: " + rowIndex + " col:" + columnIndex + " value:" + aValue, ex);
        }


    }

    public void ReLoad() throws SQLException {
        if (!tableName.equals("not setted")) {
            Logger.getLogger(DataTable.class.getName()).log(Level.DEBUG, "Releyendo ResultSet con SELECT * FROM " + tableName);
            this.internalResultSet.close();
            this.internalResultSet = null;
            this.internalResultSet = myjob.func.db.JQueryhelper.Query("SELECT * FROM " + tableName, dbConn);
            this.tableMetaData = this.internalResultSet.getMetaData();
        }
    }

    public DataTable() {
        super();
    }

    public void acceptChanges() throws SyncProviderException, SQLException {
        if (this.ReadOnly) {
            throw new SQLException("Table is read only");
        }

        if (this.tableName.equals("not setted")) {
            throw new SQLException("TableName is not setted");
        }

        //this.internalRowSet.setTableName(this.tableName);
        //this.internalRowSet.acceptChanges(this._DBConn);
    }

    public void acceptChanges(Connection conn) throws SyncProviderException, SQLException {
        if (this.ReadOnly) {
            throw new SQLException("Table is read only");
        }

        if (this.tableName.equals("not setted")) {
            throw new SQLException("TableName is not setted");
        }

        //JOptionPane.showMessageDialog(null, ((Integer) this.internalRowSet.size()).toString());

        //this.internalRowSet.setTableName(this.tableName);
        //this.internalRowSet.acceptChanges(conn);
        //this.internalRowSet.acceptChanges(conn);
    }
}
