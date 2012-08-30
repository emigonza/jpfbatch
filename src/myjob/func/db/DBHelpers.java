/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.lang.Object;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author guillermot
 */
public class DBHelpers {

    public static Class<?> JDBCtoJavaType(int JDBCType) {

        //return String.class;

        switch (JDBCType) {
            case -7:
                return Boolean.class;
            case -6:
                return Integer.class;
            case -5:
                return Long.class;
            case -4:
                return String.class;
            case -3:
                return String.class;
            case -2:
                return String.class;
            case -1:
                return String.class;
            case 0:
                return String.class;
            case 1:
                return Character.class;
            case 2:
                return Double.class;
            case 3:
                return Float.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Float.class;
            case 7:
                return Float.class;
            case 8:
                return Double.class;
            case 12:
                return String.class;
            case 91:
                return Date.class;
            case 92:
                return Date.class;
            case 93:
                return Date.class;
            case 1111:
                return String.class;
            default:
                return String.class;
        }

    }

    public static String getCreateDB(String tableName, String[] names, Class[] types, Integer[] lenghts, DBDriver dialect) {
        return getCreateDB(tableName, names, types, lenghts, new String[]{}, dialect);
    }

    public static String getCreateDB(String tableName, String[] names, Class[] types, Integer[] lenghts, String PK[], DBDriver dialect) {
        return getCreateDB(tableName, names, types, lenghts, PK, dialect, false);
    }

    public static String getCreateDB(String tableName, String[] names, Class[] types, int[] lengths, String PK[], DBDriver dialect, boolean auto_id) {
        List<Integer> _lengths = new ArrayList<Integer>();
        for(int i : lengths) {
            _lengths.add(i);
        }

        return getCreateDB(tableName, names, types, _lengths.toArray(new Integer[] {}), PK, dialect, auto_id);
    }

    public static String getCreateDB(String tableName, String[] names, Class[] types, Integer[] lengths, String PK[], DBDriver dialect, boolean auto_id) {
        String loc_RetVal = "CREATE TABLE " + tableName + "(\n";

        if (names.length != types.length) {
            return "";
        }
        if (names.length != types.length) {
            return "";
        }

        if (names.length == 0) {
            return "";
        }

        String loc_Campos = "";

        if (auto_id) {
            loc_Campos = DBHelpers.getAutoPK(dialect);
        }

        for (int loc_Pos = 0; loc_Pos < names.length; loc_Pos++) {

            if (loc_Campos.length() > 0) {
                loc_Campos += ",\n";
            }

            loc_Campos += names[loc_Pos] + " " + getDbType(types[loc_Pos], dialect);

            if (lengths[loc_Pos] > 0) {
                loc_Campos += " (" + ((Integer) lengths[loc_Pos]).toString() + ")";
            }


            loc_Campos += "\n";
        }

        loc_RetVal += loc_Campos;

        if (PK.length > 0) {

            loc_RetVal += ",\nPRIMARY KEY (";

            for (int loc_Conta = 0; loc_Conta < PK.length; loc_Conta++) {
                if (loc_Conta > 0) {
                    loc_RetVal += ", ";
                }
                loc_RetVal += PK[loc_Conta];
            }

            if (auto_id) {
                loc_RetVal += ", AUTO_PK";
            }

            loc_RetVal += ")\n";
        } else if (auto_id) {
            loc_RetVal += ",\nPRIMARY KEY (AUTO_PK)\n";
        }

        loc_RetVal += ");";

        return loc_RetVal;
    }

    public static String getInsertCommand(String tableName, String[] names, Class[] types, DBDriver dialect) {

        String loc_RetVal = "";

        if (dialect == DBDriver.pgSQL) {
            loc_RetVal = "INSERT INTO " + tableName + "\n";

            if (names.length != types.length) {
                return "";
            }
            if (names.length != types.length) {
                return "";
            }

            String loc_Campos = "";

            for (int loc_Pos = 0; loc_Pos < names.length; loc_Pos++) {
                if (loc_Campos.length() > 0) {
                    loc_Campos += ", ";
                }
                loc_Campos += names[loc_Pos];
            }

            loc_RetVal += "(" + loc_Campos + ")\nVALUES\n";

            loc_Campos = "";

            for (int loc_Pos = 0; loc_Pos < names.length; loc_Pos++) {
                if (loc_Campos.length() > 0) {
                    loc_Campos += ", ";
                }
                loc_Campos += "%" + names[loc_Pos] + "%";
            }

            loc_RetVal += "(" + loc_Campos + ");\n";
        }


        return loc_RetVal;
    }

    public static int getSqlType(String dbType) {
        dbType = dbType.toUpperCase();
        if (dbType.startsWith("CHARACTER VARYING") || dbType.startsWith("VARCHAR")) {
            return java.sql.Types.VARCHAR;
        } else if (dbType.startsWith("BOOL")) {
            return java.sql.Types.BOOLEAN;
        } else if (dbType.startsWith("TIMESTAMP")) {
            return java.sql.Types.DATE;
        } else if (dbType.startsWith("DATE")) {
            return java.sql.Types.DATE;
        } else if (dbType.startsWith("DOUBLE")) {
            return java.sql.Types.DOUBLE;
        } else if (dbType.startsWith("FLOAT") || dbType.startsWith("NUMBER")) {
            return java.sql.Types.FLOAT;
        } else if (dbType.startsWith("INTEGER")) {
            return java.sql.Types.INTEGER;
        } else if (dbType.startsWith("SHORT") || dbType.startsWith("SMALLINT")) {
            return java.sql.Types.SMALLINT;
        } else if (dbType.startsWith("LONG") || dbType.startsWith("BIGINT")) {
            return java.sql.Types.BIGINT;
        } else if (dbType.startsWith("BLOB") || dbType.startsWith("BYTEA")) {
            return java.sql.Types.BLOB;
        }

        return 0;
    }

    public static int getSqlType(Class nativeType) {
        if (nativeType == Boolean.class) {
            return java.sql.Types.BOOLEAN;
        }

        if (nativeType == String.class) {
            return java.sql.Types.VARCHAR;
        }

        if (nativeType == Date.class) {
            return java.sql.Types.TIMESTAMP;
        }

        if (nativeType == Double.class) {
            return java.sql.Types.DOUBLE;
        }

        if (nativeType == Float.class) {
            return java.sql.Types.FLOAT;
        }

        if (nativeType == Integer.class) {
            return java.sql.Types.INTEGER;
        }

        if (nativeType == Short.class) {
            return java.sql.Types.SMALLINT;
        }

        if (nativeType == Long.class) {
            return java.sql.Types.BIGINT;
        }

        if (nativeType == (new Byte[]{}).getClass()) {
            return java.sql.Types.BLOB;
        }

        return java.sql.Types.VARCHAR;
    }

    public static String getDbType(Class nativeType, DBDriver dialect) {
        String loc_RetVal = "";

        if (dialect == DBDriver.pgSQL) {
            if (nativeType == Boolean.class) {
                loc_RetVal = "BOOLEAN";
            }
            if (nativeType == String.class) {
                loc_RetVal = "CHARACTER VARYING";
            }
            if (nativeType == Date.class) {
                loc_RetVal = "TIMESTAMP WITHOUT TIME ZONE";
            }
            if (nativeType == Double.class) {
                loc_RetVal = "DOUBLE PRECISION";
            }
            if (nativeType == Integer.class) {
                loc_RetVal = "INTEGER";
            }
        }

        return loc_RetVal;
    }

    public static Class getNativeType(int sqlType) {

        switch (sqlType) {
                case java.sql.Types.BIGINT:
                    return Long.class;
                case java.sql.Types.BLOB:
                    (new byte[] {}).getClass();
                case java.sql.Types.BOOLEAN:
                    return boolean.class;
                case java.sql.Types.CHAR:
                    return char.class;
                case java.sql.Types.SMALLINT:
                    return short.class;
                case java.sql.Types.DATE:
                    return Date.class;
                case java.sql.Types.TIMESTAMP:
                    return Calendar.class;
                case java.sql.Types.DECIMAL:
                    return float.class;
                case java.sql.Types.INTEGER:
                    return int.class;
                case java.sql.Types.FLOAT:
                    return float.class;
                case java.sql.Types.DOUBLE:
                    return double.class;
                case java.sql.Types.NUMERIC:
                    return float.class;
                case java.sql.Types.REAL:
                    return float.class;
                case java.sql.Types.VARCHAR:
                    return String.class;

            }

        return null;
    }

    public static String getDbType(int sqlType, DBDriver dialect) {

        if (dialect == DBDriver.pgSQL) {
            switch (sqlType) {
                case java.sql.Types.BIGINT:
                    return "BIGINT";
                case java.sql.Types.BLOB:
                    return "BYTEA";
                case java.sql.Types.BOOLEAN:
                    return "BOOLEAN";
                case java.sql.Types.CHAR:
                    return "CHAR";
                case java.sql.Types.SMALLINT:
                    return "SMALLINT";
                case java.sql.Types.DATE:
                    return "TIMESTAMP WITHOUT TIME ZONE";
                case java.sql.Types.TIMESTAMP:
                    return "TIMESTAMP WITHOUT TIME ZONE";
                case java.sql.Types.DECIMAL:
                    return "REAL";
                case java.sql.Types.INTEGER:
                    return "INTEGER";
                case java.sql.Types.FLOAT:
                    return "REAL";
                case java.sql.Types.NUMERIC:
                    return "NUMERIC";
                case java.sql.Types.REAL:
                    return "REAL";
                case java.sql.Types.DOUBLE:
                    return "DOUBLE PRECISION";
                case java.sql.Types.VARCHAR:
                    return "CHARACTER VARYING";

            }
        }

        return "";
    }

    static String getAutoPK(DBDriver dialect) {
        String loc_RetVal = "";

        if (dialect == DBDriver.pgSQL) {
            loc_RetVal = "AUTO_PK SERIAL, \n";
        }

        return loc_RetVal;
    }

    public static boolean TableExists(Connection dbConn, String tableName) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = dbConn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + tableName + " LIMIT 1");
        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public static String getFormattedValue(Object value, Class type, DBDriver dialect) {
        String loc_RetVal = "";

        if (dialect == DBDriver.pgSQL) {
            if (type == String.class) {
                return "'" + value.toString() + "'";
            } else if (type == Date.class) {
                DateFormat outFormatter = new SimpleDateFormat("yyyy/MM/dd");
                return "'" + outFormatter.format((Date) value) + "'";
            } else {
                return value.toString();
            }



        }


        return loc_RetVal;
    }
}
