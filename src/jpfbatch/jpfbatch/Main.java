package jpfbatch;

import ifepson.doc.IndexedOut;
import ifepson.doc.DataType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import jpfbatch.tests.IFTest;
import myjob.func.general.GeneralFunc;
import myjob.func.general.Sort;
import myjob.func.io.PortConfig;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author guillermot
 */
public class Main {

    public static void main(String[] args) throws IOException {

        pub.init(args);
        
        //jpfbatch.tests.IFTest.doTiquetTest();
        
        
        //System.exit(0);
        
        pub.loadCommands();

        if (pub.hasOption("help") || pub.hasOption("Help")) {

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "\n Ayuda de JPFBatch\n"
                    + "---------------------\n");

            pub.getOptParser().printHelpOnScreen();
        } else if (pub.hasOption("ListOptions")) {
            pub.listOptions();
        } else if (pub.hasOption("v")) {
            System.out.println(pub.getVersion());
        } else if (pub.hasOption("changelog")) {
            System.out.println(pub.getChangeLog());
        } else if (pub.hasOption("HelpCommand")) {
            if (pub.hasOptValue("HelpCommand")) {
                pub.showHelp((String) pub.getOptValue("HelpCommand"));
            } else {
                // toda la ayuda
                List<String> keys = new ArrayList<String>(pub.getComandos().keySet());
                String[] keys1 = keys.toArray(new String[]{});
                Sort.quicksort(keys1);
                for (String s : keys1) {
                    pub.showHelp(s);
                }
            }
        } else if (pub.hasOption("ListCommands")) {
            List<String> keys = new ArrayList<String>(pub.getComandos().keySet());
            String[] keys1 = keys.toArray(new String[]{});
            Sort.quicksort(keys1);
            System.out.println("Listado de comandos");
            for (String s : keys1) {
                pub.showHelp(s, false);
            }
        } else if (pub.hasOption("HelpTipos")) {
            System.out.println("Ayuda de JPFBatch - tipos da datos\n"
                    + "-------------------------------------\n");

            for (DataType tf : DataType.values()) {
                System.out.println(tf.getType() + " = " + tf.getDesc());
            }
        } else if (pub.hasOption("HelpOut")) {

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "\n Ayuda de JPFBatch - salidas indexadas\n"
                    + "----------------------------------------\n");

            for (IndexedOut io : IndexedOut.values()) {
                System.out.println(io.getCod().toString() + " = " + io.getDescripcion());
            }
        } else if (pub.hasOption("AsServer")) {

            Logger.getLogger(Main.class).log(Level.DEBUG, "Corriendo como servidor");
            pub.resolvePort();

            jpfbatch.net.TCPServer server = new jpfbatch.net.TCPServer((Integer) pub.getOptValue("TimeOut"), pub.config);

            try {
                server.listen();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        } else if (pub.hasOption("AsClient")) {

            Logger.getLogger(Main.class).log(Level.DEBUG, "Corriendo como cliente");

            if (pub.hasOption("InFileName") && !((String) pub.getOptValue("InFileName")).isEmpty()) {

                jpfbatch.net.TCPClient client = new jpfbatch.net.TCPClient(loadFile((String) pub.getOptValue("InFileName")));

                try {
                    List<String> listaSalida = client.communicate(pub.hasOption("TestMode") || pub.hasOption("testmode"));

                    String outFileName = new File((String) pub.getOptValue("InFileName")).getName();

                    writeOut(outFileName, listaSalida);

                } catch (Exception ex) {
                    Logger.getLogger(Main.class).log(Level.FATAL, null, ex);
                }
            }
        } else if (pub.hasOption("InFileName") && !((String) pub.getOptValue("InFileName")).isEmpty()) {

            pub.resolvePort();

            List<String> listaSalida = EjecutarBatchFromFile((String) pub.getOptValue("InFileName"), (Integer) pub.getOptValue("TimeOut"), pub.config);

            String outFileName = new File((String) pub.getOptValue("InFileName")).getName();

            writeOut(outFileName, listaSalida);

        }


        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Todo terminado");

        System.exit(0);

    }

    public static String loadFile(String fileName) throws IOException {
        BufferedReader entrada = new BufferedReader(new FileReader(new File(fileName)));

        String retVal = "";
        String linea = "";

        while ((linea = entrada.readLine()) != null) {
            retVal += linea + "\n";
        }

        return retVal;
    }

    public static List<String> EjecutarBatchFromFile(String inFileName, int timeOut, PortConfig portConfig) throws FileNotFoundException, IOException {
        return EjecutarBatchFromFile(inFileName, timeOut, portConfig, false);
    }

    public static List<String> EjecutarBatchFromFile(String inFileName, int timeOut, PortConfig portConfig, boolean testMode) throws FileNotFoundException, IOException {

        if (pub.hasOption("CopyInFile") && !((String) pub.getOptValue("CopyInFile")).isEmpty()) {
            String copyFileName = (String) pub.getOptValue("CopyInFile");

            copyFileName = copyFileName.replace("%t%", ((Long) Calendar.getInstance().getTimeInMillis()).toString());

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Copiando archivo de entrada " + inFileName + " a " + copyFileName);
            myjob.func.io.FileFunc.copyFile(inFileName, copyFileName);
        }

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Generando batch de " + inFileName);

        Object o = pub.getOptValue("LargoDescItem");

        int largoItem = 16;

        if (o != null) {
            try {
                largoItem = (Integer) pub.getOptValue("LargoDescItem");
            } catch (Exception ex) {
                Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Generando batch de " + inFileName);
                largoItem = 16;
            }
        }

        IFBatch batch = IFBatch.FromFile(inFileName, timeOut, portConfig, (String) pub.getOptValue("Sep"), largoItem, pub.comandos);

        if (pub.hasOption("NoClosePortBetweenCommands")) {
            batch.setNoClosePortBetweenCommands((Boolean) pub.getOptValue("NoClosePortBetweenCommands"));
        }

        return EjecutarBatch(batch, testMode);
    }

    public static List<String> EjecutarBatchFromString(String strBatch, int timeOut, PortConfig portConfig) throws FileNotFoundException, IOException {
        return EjecutarBatchFromString(strBatch, timeOut, portConfig, false);
    }

    public static List<String> EjecutarBatchFromString(String strBatch, int timeOut, PortConfig portConfig, boolean testMode) throws FileNotFoundException, IOException {

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Generando batch de String\n" + strBatch);

        int largoItem = 16;
        Object o = pub.getOptValue("LargoDescItem");
        if (o != null) {
            try {
                largoItem = (Integer) pub.getOptValue("LargoDescItem");
            } catch (Exception ex) {
                largoItem = 16;
            }
        }

        IFBatch batch = IFBatch.FromBatchString(strBatch, timeOut, portConfig, (String) pub.getOptValue("Sep"), largoItem, pub.comandos);

        if (pub.hasOption("NoClosePortBetweenCommands")) {
            batch.setNoClosePortBetweenCommands((Boolean) pub.getOptValue("NoClosePortBetweenCommands"));
        }

        return EjecutarBatch(batch, testMode);
    }

    public static List<String> EjecutarBatch(IFBatch batch) {
        return EjecutarBatch(batch, pub.hasOptValue("TestMode"));
    }

    public static List<String> EjecutarBatch(IFBatch batch, boolean testMode) {

        List<String> retVal = null;

        //batch = new jpfbatch.tests.cierreZ();
        if (batch == null) {
            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "error al generar el batch");
        } else if (testMode) {
            // modo de prueba
            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Modo de prueba");

            retVal = new ArrayList<String>();

            System.out.println(batch.toString());

            retVal.add("Se envio la prueba");

        } else {

            retVal = new ArrayList<String>();

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Ejecutando batch");

            batch.run();

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Fin ejecucion batch, preparando respuesta");

            List<IndexedOut> keys = new ArrayList<IndexedOut>(batch.getRespuesta().keySet());

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Escribiendo en archivo de respuesta");

            if (keys.size() > 0) {
                Sort.quicksort(keys, "cod");

                Integer largo = 0;

                for (IndexedOut io : keys) {

                    largo = io.getLargo();

                    if (largo < 0) {
                        largo = batch.getRespuesta().get(io).toString().length();
                    }

                    Logger.getLogger(Main.class).log(Level.DEBUG, "Agregando a salida: " + GeneralFunc.padLeft(io.getCod().toString(), 4, '0') + io.getType().getType() + GeneralFunc.padLeft(largo.toString(), 2, '0') + batch.getRespuesta().get(io).toString() + " " + io.getDescripcion());

                    retVal.add(GeneralFunc.padLeft(io.getCod().toString(), 4, '0') + io.getType().getType() + GeneralFunc.padLeft(largo.toString(), 2, '0') + batch.getRespuesta().get(io).toString() + " " + io.getDescripcion());
                }
            }
        }

        return retVal;

    }

    public static void writeOut(String outFileName, List<String> listaSalida) throws IOException {

        Logger.getLogger(Main.class).log(Level.DEBUG, "Entrando a escribiendo salida");

        if (listaSalida.size() > 0) {

            if (outFileName.contains(".")) {
                outFileName = outFileName.substring(0, outFileName.lastIndexOf("."));
            }

            if (pub.hasOption("outfilename")) {
                outFileName = (String) pub.getOptValue("outfilename");
            }

            outFileName += pub.getOptValue("OutExt");

            Logger.getLogger(Main.class).log(Level.DEBUG, "Escribiendo salida " + outFileName);

            BufferedWriter salida = new BufferedWriter(new FileWriter(new File(outFileName)));

            Integer largo = 0;
            String logVal = "";

            for (String s : listaSalida) {
                salida.write(s + "\n");
            }

            salida.close();
        }

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Saliendo de escribiendo salida");

    }
}
