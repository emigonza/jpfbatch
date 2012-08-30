/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.pub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author guillermot
 */
public class pubTools {

    /**
     * Devuelve un array<br/>
     * posicion       valor<br/>
     * 0              path del directorio actual<br/>
     * 1              path del directorio donde se encuentra el jar
     * @param clazz
     * @return 
     */
    public static String[] getPaths(Class clazz) {

        File archivo = null;

        String[] retVal = new String[2];

        try {
            archivo = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(clazz.getName()).log(Level.ERROR, null, ex);
        }

        retVal[0] = new File(".").getAbsolutePath();
        retVal[1] = archivo.getParent();

        return retVal;
    }

    /**
     * lee el archivo de configuracion log4j.properties.<br/>
     * en el caso que log4j.properties no exista lo levanta del jar al que pertenece clazz
     * @param clazz 
     */
    public static void loadLog4jConfig(Class clazz) {

        String jarPath = pubTools.getPaths(clazz)[1];

        File props4j = null;

        File jarPathFile = new File(jarPath);

        if (jarPathFile.getName().equalsIgnoreCase("build")) {
            // supongo que estoy ejecutando en directorio de compilación y no desde un jar
            props4j = new File(jarPath.replace("build", "src") + "/log4j.properties");
        } else {
            props4j = new File(jarPath + "/log4j.properties");
        }

        if (props4j.exists()) {
            try {
                Logger.getLogger(clazz.getName()).log(Level.DEBUG, "Configurando log4j desde " + props4j.getAbsolutePath());
                Properties p = new Properties();
                p.load(new FileInputStream(props4j.getAbsolutePath()));
                PropertyConfigurator.configure(p);
            } catch (IOException ex) {
                Logger.getLogger(pubTools.class).log(Level.FATAL, "Error al intentar abrir log4j.properties", ex);
            }
        } else {
            // Trato de leerlo directamente desde el jar (solo si se ejecuta desde el jar)
            if (!jarPathFile.getName().equalsIgnoreCase("build")) {
                Logger.getLogger(clazz.getName()).log(Level.DEBUG, "Configurando log4j desde jar");
                InputStream resourceAsStream = clazz.getResourceAsStream("log4j.properties");
                Properties p = new Properties();
                try {
                    p.load(resourceAsStream);
                } catch (IOException ex) {
                    Logger.getLogger(pubTools.class).log(Level.FATAL, null, ex);
                }
            }
        }
    }

    /**
     * lee las propiedades utilizando como nombre de archivo el nombre del paquete de la clase clazz
     * @param clazz
     * @return 
     */
    public static Properties loadProperties(Class clazz) {
        String jarPath = pubTools.getPaths(clazz)[1];

        File fileProperties = null;

        File jarPathFile = new File(jarPath);

        String packageName = clazz.getPackage().getName();

        if (jarPathFile.getName().equalsIgnoreCase("build")) {
            // supongo que estoy ejecutando en directorio de compilación y no desde un jar
            fileProperties = new File(jarPath.replace("build", "src") + "/" + packageName + ".properties");
        } else {
            fileProperties = new File(jarPath + "/" + packageName + ".properties");
        }

        if (fileProperties.exists()) {
            Properties p = new Properties();
            try {
                Logger.getLogger(clazz.getName()).log(Level.DEBUG, "leyendo configuracion de " + fileProperties.getAbsolutePath());
                p.load(new FileInputStream(fileProperties.getAbsolutePath()));
                return p;
            } catch (IOException ex) {
                Logger.getLogger(pubTools.class).log(Level.FATAL, "Error al intentar abrir log4j.properties", ex);
            }
        } else {
            // Trato de leerlo directamente desde el jar (solo si se ejecuta desde el jar)
            if (!jarPathFile.getName().equalsIgnoreCase("build")) {
                Logger.getLogger(clazz.getName()).log(Level.DEBUG, "leyendo configuracion del jar");
                InputStream resourceAsStream = clazz.getResourceAsStream(packageName + ".properties");
                Properties p = new Properties();
                try {
                    p.load(resourceAsStream);
                    return p;
                } catch (IOException ex) {
                    Logger.getLogger(pubTools.class).log(Level.FATAL, null, ex);
                }
            }
        }
    
        return null;
    }

    /**
     * Las propiedades que tienen que tener env son:<br/>
     * DBDriver driver de la base de datos (por ejemplo org.postgresql.Driver)<br/>
     * DBServer servidor de db<br/>
     * DBName servidor nombre de la db<br/>
     * DBUID nombre de usuario<br/>
     * PSW contraseña<br/>
     * env Properties con los datos para la conexión<br/>
     * @return
     * @throws SQLException 
     */
    public static Connection getConnection(Properties env) throws SQLException {
        return getConnection(env.getProperty("DBDriver"), env.getProperty("DBServer"), env.getProperty("DBName"), env.getProperty("DBUID"), env.getProperty("DBPSW"));
    }

    /**
     * Crea una nueva conexión pasandole los siguientes parametros
     * @param DBDriver driver de la base de datos (por ejemplo org.postgresql.Driver)
     * @param DBServer servidor de db
     * @param DBName servidor nombre de la db
     * @param DBUID nombre de usuario
     * @param PSW contraseña
     * @return nueva conex a la db
     * @throws SQLException 
     */
    public static Connection getConnection(String DBDriver, String DBServer, String DBName, String DBUID, String DBPSW) throws SQLException {
        Connection dbConn = null;
        
        dbConn = DriverManager.getConnection("jdbc:" + DBDriver + "://" + DBServer + "/" + DBName, DBUID, DBPSW);

        return dbConn;
    }
}
