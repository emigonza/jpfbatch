/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import ifepson.ifCommand;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import java.io.File;
import java.io.FileInputStream;
import myjob.func.io.PortConfig;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import myjob.func.general.GeneralFunc;
import myjob.func.general.Sort;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author guillermot
 */
public class pub {

    //protected static EnvVars env = null;
    //protected static SerialPort serialPort = null;
    protected static Properties env = null;
    protected static OptionSet options = null;
    protected static OptionParser optParser = null;
    protected static Map<String, Class> comandos = new HashMap<String, Class>();
    public static String path = "";
    public static PortConfig config;

    public static Properties getEnv() {
        return env;
    }

    public static void setEnv(Properties env) {
        pub.env = env;
    }

    static {
        File archivo = null;

        File props4j = null;

        try {
            archivo = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(pub.class.getName()).log(Level.ERROR, null, ex);
        }

        String loc_Path = archivo.getParent();

        if (loc_Path.contains("build")) {
            props4j = new File(loc_Path.replace("build", "src") + "/log4j.properties");
            loc_Path = loc_Path.replace("build", "dist");
        } else {
            props4j = new File(loc_Path + "/log4j.properties");
        }

        pub.path = loc_Path;

        optParser = new OptionParser();
        addOptions();

        if (props4j.exists()) {
            try {
                Properties p = new Properties();
                p.load(new FileInputStream(props4j.getAbsolutePath()));
                PropertyConfigurator.configure(p);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(pub.class.getName()).log(java.util.logging.Level.SEVERE, "Error al intentar abrir log4j.properties", ex);
            }

        }

        pub.env = new java.util.Properties();

        try {
            File fileConf = new File(loc_Path + "/jpfbatch.properties");

            if (fileConf.exists()) {
                pub.env.load(new FileInputStream(loc_Path + "/jpfbatch.properties"));
            } else {
                pub.env.load(pub.class.getResourceAsStream("/jpfbatch.properties"));
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(pub.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Logger.getLogger(pub.class.getName()).log(Level.DEBUG, "pub iniciado");

    }

    public static Map<String, Class> getComandos() {
        return comandos;
    }

    public static void setComandos(Map<String, Class> comandos) {
        pub.comandos = comandos;
    }

    public static void showHelp(String commandName) {
        showHelp(commandName, true);
    }

    public static void showHelp(String commandName, boolean completo) {
        Class c = comandos.get(commandName);

        if (c != null) {

            Object obj = null;
            try {
                obj = myjob.func.classutils.ClassFunc.getInstanceFromClassName(c.getName());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
            }

            if (obj != null) {

                ifCommand com = (ifCommand) obj;

                if (completo) {
                    System.out.println("\n Ayuda de comando fiscal " + com.getNombreA());
                    System.out.println("-------------------------");
                    System.out.println(GeneralFunc.padLeft("", com.getNombreA().length(), '-'));
                    System.out.println(com.getDescripcionComando());

                    Sort.quicksort(com.getPosiblesParams(), "indice");
                    for (Parametro par : com.getPosiblesParams()) {
                        System.out.println();
                        System.out.println("\tparametro " + par.getIndice().toString() + ", tipo = " + par.getType().getDesc() + ", largo=" + par.getLargo().toString() + " \"" + par.getDescripcion() + "\"");
                    }
                    for (IndexedOut out : com.getPosiblesSalidas()) {
                        System.out.println();
                        System.out.println("\tsalida " + out.getCod().toString() + ", tipo = " + out.getType().getDesc() + ", largo=" + out.getLargo().toString() + " \"" + out.getDescripcion() + "\"");
                    }

                    System.out.println();
                } else {
                    System.out.println(com.getNombreA() + " - " + com.getDescripcionComando());
                }


            }
        }



    }

    static void resolvePort() throws IOException {


        Logger.getLogger(pub.class).log(Level.DEBUG, "El sistema operativo detectado es:" + GeneralFunc.getOS() + (GeneralFunc.is64OS() ? " 64bit" : " 32bit"));

        String pathToAdd = "";

        if (new File(path + "/dist").exists()) {
            path = path + "/dist";
        }


        if (GeneralFunc.getOS().toLowerCase().contains("win")) {
            if (GeneralFunc.is64OS()) {
                pathToAdd = (String) pub.getOptValue("rxtxWin64");
            } else {
                pathToAdd = (String) pub.getOptValue("rxtxWin32");
            }
        } else {
            if (GeneralFunc.is64OS()) {
                pathToAdd = (String) pub.getOptValue("rxtxLnx64");
            } else {
                pathToAdd = (String) pub.getOptValue("rxtxLnx32");
            }
        }

        if (pathToAdd.startsWith(".") || !pathToAdd.startsWith("/")) {
            if (pathToAdd.startsWith("./")) {
                pathToAdd = path + pathToAdd.substring(1);
            } else if (pathToAdd.startsWith(".")) {
                pathToAdd = path + "/" + pathToAdd.substring(1);
            } else {
                pathToAdd = path + "/" + pathToAdd;
            }
        }

        IFBatch.InitRxTx(pathToAdd);

        pub.config = new PortConfig((String) pub.getOptValue("ComPort"),
                (Integer) pub.getOptValue("BaudRate"),
                (Integer) pub.getOptValue("DataBits"),
                (Integer) pub.getOptValue("StopBits"),
                (Integer) pub.getOptValue("Parity"));

    }

    public static void loadCommands() {

        Class[] commands = myjob.func.classutils.ClassFunc.getClasseInPackage(path + "/lib/IFEpson.jar", "ifepson.commands");

        Object obj = null;
        ifCommand com = null;

        for (Class c : commands) {
            //System.out.println(c.getName());
            if (!c.getName().contains("$") && !c.getName().equals("ifepson.commands.ifCommand")) {
                try {
                    obj = myjob.func.classutils.ClassFunc.getInstanceFromClassName(c.getName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(pub.class.getName()).log(Level.FATAL, null, ex);
                }
                if (obj != null) {
                    com = (ifCommand) obj;
                    pub.getComandos().put(com.getNombreA(), c);

                }
            }
        }
    }

    public static OptionSet getOptions() {
        return options;
    }

    public static void setOptions(OptionSet options) {
        pub.options = options;
    }

    public static OptionParser getOptParser() {
        return optParser;
    }

    public static void setOptParser(OptionParser optParser) {
        pub.optParser = optParser;
    }

    public static void addOptions() {


        optParser.acceptsAll(GeneralFunc.asList("WithNroRef", "withnroref"), "compatibilidad con PFBatch para aceptar el 1er parametro nro de referencia de tiquet. Si no se coloca este modificador no se admitirá este nro");
        optParser.acceptsAll(GeneralFunc.asList("OutExt", "outext"), "extension que se le agregará al archivo de salida. El valor por defecto es \".out\"").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("OutFileName", "outfilename", "O", "o"), "nombre del archivo de salida sin la extension. El valor por defecto es el mismo que el del archivo de entrada").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("InFileName", "infilename", "i", "I"), "nombre del archivo de entrada.").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("Help", "help"), "muestra esta ayuda");
        optParser.acceptsAll(GeneralFunc.asList("ListOptions", "listoptions"), "lista las opciones del archivo de configuracion");
        optParser.acceptsAll(GeneralFunc.asList("ChangeLog", "changelog"), "muestra changelog");
        optParser.acceptsAll(GeneralFunc.asList("Debug", "debug"), "muestra esta ayuda");
        optParser.acceptsAll(GeneralFunc.asList("AsServer", "asserver"), "Ejecuta el programa en modo servidor escuchando en el puerto seteado con el parametro TCPPort");
        optParser.acceptsAll(GeneralFunc.asList("AsClient", "asclient"), "Ejecuta el programa en modo cliente hablando en el puerto seteado con el parametro TCPPort");
        optParser.acceptsAll(GeneralFunc.asList("HostName", "hostname"), "Especifica el nombre o la ip del servidor").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("TCPPort", "tcpport"), "Especifica puerto tcp en que va a hablar o escuchar").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("Debug", "debug"), "muestra esta ayuda");
        optParser.acceptsAll(GeneralFunc.asList("Version", "version", "v"), "muestra la version");
        optParser.acceptsAll(GeneralFunc.asList("TestMode", "testmode"), "modo de testeo, envia la información a la pantalla y devuelve un texto de prueba");
        optParser.acceptsAll(GeneralFunc.asList("NoClosePortBetweenCommands", "nocloseportbetweencommands"), "NO cierra el puerto entre comandos en vez de ejecutar todos los comandos con el puerto abierto");
        optParser.acceptsAll(GeneralFunc.asList("HelpTipos", "helptipos"), "muestra ayuda sobre los tipos de datos");
        optParser.acceptsAll(GeneralFunc.asList("HelpOut", "helpout"), "muestra ayuda sobre la salida de los comandos");
        optParser.acceptsAll(GeneralFunc.asList("BaudRate", "baudrate"), "baudios de conexión, por defecto 9600").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("CopyInFile", "copyinfile", "cif"), "Copia el archivo de entrada").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("TimeOut", "timeout"), "Tiempo de espera en milisegundos").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("Retardo", "retardo"), "Tiempo de retardo entre ejecuciones en milisegundos").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("ComPort", "comport"), "puerto de conexión, por defecto /dev/ttyS0").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("DataBits", "databits"), "bit de datos con que se configura al puerto serie").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("StopBits", "stopbits"), "bit de parada con que se configura al puerto serie, pueden ser 1, 2 o 3 si es 1.5").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("Parity", "parity"), "tipo de paridad con que se configura al puerto serie, puede ser Even = 2, Mark = 3, None = 0, Odd = 1, Space = 4, por defecto es 0 (None)").withRequiredArg().ofType(Integer.class);
        optParser.acceptsAll(GeneralFunc.asList("rxtxDir", "rxtxdir"), "directorio donde se encuentra el driver nativo (.so o .dll)").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("InitSerial", "initserial"), "serial del comando con el que debe comenzar").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("Sep", "sep"), "caracter separador").withRequiredArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("HelpCommand", "helpcommand"), "muestra la ayuda de comando, si se le agrega el nombre del comando solamente mostrará la ayuda de este").withOptionalArg().ofType(String.class);
        optParser.acceptsAll(GeneralFunc.asList("ListCommands", "listcommands"), "Lista los comandos").withOptionalArg().ofType(String.class);
    }

    public static void listOptions() {
        for (Object o : env.keySet()) {
            System.err.println(o.toString() + " = " + env.getProperty(o.toString()));
        }
    }

    public static void init(String... args) {

        optParser = new OptionParser();
        addOptions();
        options = optParser.parse(args);
    }

    public static boolean hasOption(String optName) {
        return pub.getOptions().has(optName) || (env.getProperty(optName, null) != null);
    }

    public static boolean hasOptValue(String optName) {

        return env.contains(optName) || options.hasArgument(optName);
    }

    public static Object getOptValue(String optName) {

        Object retVal = null;

        if (options.has(optName)) {
            if (env.contains(optName)) {
                if (options.hasArgument(optName)) {
                    env.setProperty(optName, options.valueOf(optName).toString());
                } else {
                    env.setProperty(optName, "1");
                }
            }
            if (options.hasArgument(optName)) {
                retVal = options.valueOf(optName);
            } else {
                retVal = true;
            }
        } else {
            retVal = myjob.func.prop.PropTools.getObject(env, optName);
        }

        return retVal;
    }

    public static String getVersion() {
        return pub.class.getPackage().getImplementationVersion();
    }

    public static String getChangeLog() {
        return ""
                + "- Versiones anteriores a la 1.51 no tienen changelog\n"
                + "- Agrego Semilla aleatoria en tiempo Calendar.GetInstance().getTimeInMillis() y realizo varios sorteos para conseguir el serial\n"
                + "ver 1.52\n"
                + "- Reduccion de reintentos de envio de 4 a 3\n"
                + "- Fin de thread principal forzado\n"
                + "- Control de ejecución en paralelo\n"
                + "ver 1.53\n"
                + "- Mejora de control de apertura y cierre del puerto serie\n"
                + "- Limpieza de parametros de entrada en desuso\n"
                + "- Listado de comandos\n"
                + "- Cambio de archivo de configuración jpfbatch.cfg a jpfbatch.properties con retiro de libreria JEnv\n"
                + "- Funcionamiento como Cliente o como Servidor en una red"
                + "ver 1.53.1\n"
                + "- Mejora del logeo\n"
                + "- Carga menos librerias en modo cliente\n"
                + "ver 1.53.2\n"
                + "- Reintenta con errores timeout\n"
                + "ver 1.53.27\n"
                + "- Envia siempre al final un comando con serial 127 para sincronizar y no sortear mas el serial";

    }
}