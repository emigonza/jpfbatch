/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

/**
 *
 * @author guillermot
 */
public enum DBDriver {

    pgSQL("PostgreSQL", "postgresql", 5432),
    MySQL("MySQL", "mysql", 3306),
    FireBird("FireBird", "firebird", 0),
    MSSQL("MSSQL", "mssql", 0),
    Oracle("ORACLE", "oracledb", 0),
    HSQLDB("HSQLDB", "hsqldb", 0),
    Derby("Derby", "derby", 0),
    SQLite("SQLite", "sqlite", 0);
    protected String name;
    protected String driverClass;
    protected int defaultPort;
    protected static DBDriver[] values = {pgSQL, MySQL, FireBird, MSSQL, Oracle, HSQLDB, Derby, SQLite};

    private DBDriver(String name, String driverClass, int defaultPort) {
        this.name = name;
        this.driverClass = driverClass;
        this.defaultPort = defaultPort;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public String getName() {
        return name;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public static DBDriver[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return name;
    }

    public static DBDriver parse(String driver) {

        if (driver == null) {
            return null;
        }

        for (DBDriver db : getValues()) {
            if (db.getName().equalsIgnoreCase(driver) || db.name().equalsIgnoreCase(driver)) {
                return db;
            }
        }

        if (driver.toLowerCase().contains("postgre")) {
            return DBDriver.pgSQL;
        }

        return null;
    }
}
