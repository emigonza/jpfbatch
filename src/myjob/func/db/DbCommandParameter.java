/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class DbCommandParameter implements IDbCommandParameter {

    IDbCommand owner;
    String parameterName = "";
    Class parameterClass = null;
    Object value = null;
    Integer index = -1;
    public static String dateFormat = "yyyy/MM/dd";
    public static String parameterMark = ":";

    public DbCommandParameter() {
    }

    public DbCommandParameter(IDbCommand dbCommand) {
        owner = dbCommand;
    }

    public IDbCommand getDbCommand() {
        return owner;
    }

    public void setDbCommand(IDbCommand dbCommand) {
        owner = dbCommand;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Class getParameterType() {
        if (parameterClass != null) {
            return parameterClass;
        }

        if (value != null) {
            this.resolveType();
        }

        return parameterClass;
    }

    public void setParameterClass(Class type) {
        this.parameterClass = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void resolveType() {
        if (value == null) {
            return;
        }

        this.parameterClass = value.getClass();
    }

    public void replaceInCommand() throws SQLException {

        Integer loc_pos = 0;
        Integer loc_Index = 1;
        boolean loc_Esta = false;

        while (loc_pos >= 0) {
            loc_pos = owner.getPreparedCommandText().indexOf("?", loc_pos);

            //System.out.println(loc_Index.toString() + " " + loc_pos.toString() + " " + index.toString());

            if(loc_pos < 0) {
                //no esta
                break;
            }

            if ((int) loc_pos == (int) index) {
                loc_Esta = true;
                break;
            }

            loc_pos++;
            loc_Index++;
        }

        if (!loc_Esta) {
            System.out.println(owner.getPreparedCommandText() + "\n" + this.getParameterName() + "(" + index.toString() + ")->" + this.getDbCommand().getPreparedCommandText());
            throw new SQLException("Posicion incorrecta");
        }

        try {
            Object loc_Val = value;

            /*if (loc_Val == null) {
                owner.getStatement().setNull(loc_Index, myjob.func.db.DBHelpers.getSqlType(parameterClass));
            } else if (loc_Val.getClass() == Calendar.class) {
                owner.getStatement().setDate(loc_Index, (java.sql.Date) ((Calendar) loc_Val).getTime());
            } else if (loc_Val.getClass() == Boolean.class) {
                owner.getStatement().setBoolean(loc_Index, (Boolean) loc_Val);
            } else if (loc_Val.getClass() == Date.class) {
                owner.getStatement().setDate(loc_Index, (java.sql.Date) loc_Val);
            } else if (loc_Val.getClass() == String.class) {
                owner.getStatement().setString(loc_Index, (String) loc_Val);
            } else if (GeneralFunc.typeIsInteger(parameterClass)) {
                owner.getStatement().setInt(loc_Index, (Integer) loc_Val);
            } else if (GeneralFunc.typeIsNumber(parameterClass)) {
                owner.getStatement().setDouble(loc_Index, (Double) loc_Val);
            } else if (loc_Val.getClass() == (new Byte[]{}).getClass()) {
                owner.getStatement().setBytes(loc_Index, (byte[]) loc_Val);
            } else {
                throw new SQLException("tipo no soportado " + parameterClass.toString() + " en parametro " + parameterName);
            }*/

            //System.out.println("Seteando parametro " + this.getParameterName() + " en pos " + loc_Index + " con valor " + loc_Val + " de la intruccion ->" + this.getDbCommand().getPreparedCommandText());

            owner.getStatement().setObject(loc_Index, loc_Val);
        } catch (Exception ex) {
            System.out.println(this.getParameterName() + "->" + this.getDbCommand().getPreparedCommandText());
            throw new SQLException(ex.getMessage());
        }


    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
