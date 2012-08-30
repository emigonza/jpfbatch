/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import ifepson.IFException;
import ifepson.IFReturnValue;
import ifepson.ifCommand;
import ifepson.commands.pagoCancelDescRecaFNC;
import ifepson.commands.pagoCancelDescRecaTique.Calificador;
import ifepson.commands.solEstado;
import ifepson.doc.DataType;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import myjob.func.io.PortConfig;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class IFBatch {

    protected int timeOut;
    protected PortConfig portConfig;
    protected List<ifCommand> comandos = new ArrayList<ifCommand>();
    protected Map<IndexedOut, String> respuesta = new EnumMap<IndexedOut, String>(IndexedOut.class);
    protected byte secuencia = 0x20;
    protected String batchOriginal = "";
    protected boolean noClosePortBetweenCommands = false;

    public String getBatchOriginal() {
        return batchOriginal;
    }

    public void setBatchOriginal(String batchOriginal) {
        this.batchOriginal = batchOriginal;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public PortConfig getPortConfig() {
        return portConfig;
    }

    public void setPortConfig(PortConfig config) {
        this.portConfig = config;
    }

    public boolean isNoClosePortBetweenCommands() {
        return noClosePortBetweenCommands;
    }

    public void setNoClosePortBetweenCommands(boolean noClosePortBetweenCommands) {
        this.noClosePortBetweenCommands = noClosePortBetweenCommands;
    }

    public IFBatch() {
        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Secuencia seteada en " + secuencia);
    }
    public static boolean isRxTxInitiated = false;

    public static void InitRxTx(String nativeLibPath) throws IOException {
        if (isRxTxInitiated) {
            return;
        }

        Logger.getLogger(pub.class).log(Level.DEBUG, "utilizando driver ubicado en " + nativeLibPath);
        myjob.func.classutils.ClassFunc.addLibPathDir(nativeLibPath);

        isRxTxInitiated = true;

    }

    /**
     * Separa el String batch en lineas y llama IFBatch.FromLines
     * @param batch
     * @param timeOut
     * @param config
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static IFBatch FromBatchString(String batch, int timeOut, PortConfig config, String separador, int largoDesc, Map<String, Class> comandos) throws FileNotFoundException, IOException {
        String lines[] = batch.split("\n");

        return IFBatch.FromLines(lines, timeOut, config, separador, largoDesc, comandos);
    }

    /**
     * Lee el archivo fileName, lo separa en lineas y llama IFBatch.FromLines
     * @param fileName
     * @param timeOut
     * @param config
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static IFBatch FromFile(String fileName, int timeOut, PortConfig config, String separador, int largoDesc, Map<String, Class> comandos) throws FileNotFoundException, IOException {


        BufferedReader entrada = new BufferedReader(new FileReader(new File(fileName)));

        List<String> lineas = new ArrayList<String>();
        String linea = "";

        while ((linea = entrada.readLine()) != null) {
            lineas.add(linea);
        }

        return IFBatch.FromLines(lineas.toArray(new String[]{}), timeOut, config, separador, largoDesc, comandos);
    }

    /**
     * Parsea cada line en un comando individual para generar el Batch<br/>
     * Además carga cada linea en un texto batchOriginal para usos en log
     * @param lines
     * @param timeOut
     * @param config
     * @return 
     */
    public static IFBatch FromLines(String[] lines, int timeOut, PortConfig config, String separador, int largoDesc, Map<String, Class> comandos) {
        IFBatch retVal = new IFBatch();
        retVal.setTimeOut(timeOut);
        retVal.setPortConfig(config);

        ifCommand comm;

        for (String linea : lines) {
            if (linea.trim().length() > 0) {
                retVal.batchOriginal += linea + "\n";
                comm = IFBatch.parseCommand(linea.trim(), separador, largoDesc, comandos);
                if (comm == null) {
                    Logger.getLogger(IFBatch.class).log(Level.ERROR, "No se pudo generar gomando de " + linea);
                } else {
                    comm.setTimeOut(retVal.getTimeOut());
                    Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Setting timeout en " + comm.getNombreA() + " = " + retVal.getTimeOut());
                    if (comm == null) {
                        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "error al generar comando " + linea);
                        return null;
                    }

                    retVal.addCommand(comm);
                }
            }
        }

        return retVal;
    }

    /**
     * Devuelve un mapa con las respuestas indexadas y su valor
     * @return 
     */
    public Map<IndexedOut, String> getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta
     * @param respuesta 
     */
    public void setRespuesta(Map<IndexedOut, String> respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Devuelve una lista con los comandos del batch
     * @return 
     */
    public List<ifCommand> getComandos() {
        return comandos;
    }

    /**
     * Establece la lista de comandos
     * @param comandos 
     */
    public void setComandos(List<ifCommand> comandos) {
        this.comandos = comandos;
    }

    /**
     * Agrega un comando a la lista de comandos
     * @param comando 
     */
    public void addCommand(ifCommand comando) {
        comandos.add(comando);
    }

    /**
     * Envía los comandos en orden al controlador fiscal
     */
    public void run() {

        IFReturnValue retComm = IFReturnValue.OK;

        SerialPort port = null;

        if (this.noClosePortBetweenCommands) {
            try {
                Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Abriendo el puerto " + this.getPortConfig().getPortName());

                port = (SerialPort) CommPortIdentifier.getPortIdentifier(this.getPortConfig().getPortName()).open("IFBatch", 2000);

                port.setSerialPortParams(
                        this.getPortConfig().getBaudRate(),
                        this.getPortConfig().getDataBits(),
                        this.getPortConfig().getStopBits(),
                        this.getPortConfig().getParity());

                port.setDTR(true);
            } catch (UnsupportedCommOperationException ex) {
                java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Operacion del puerto no soportada", ex);
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Operacion del puerto no soportada");
                return;
            } catch (PortInUseException ex) {
                java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Puerto en uso", ex);
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Puerto en uso");
                return;
            } catch (NoSuchPortException ex) {
                java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "El puerto no existe", ex);
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "El puerto no existe");
                return;
            }

        }

        for (ifCommand comm : comandos) {
            try {

                retComm = IFReturnValue.OK;

                Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Enviando comando " + comm.getNombreA() + "  nro de serie:  " + secuencia);

                if (port == null) {

                    retComm = comm.ejecutar(this.getPortConfig(), getSerial());

                } else {
                    retComm = comm.ejecutar(port, getSerial());
                }

            } catch (IFException ex) {
                retComm = IFReturnValue.UNKNOW_ERROR;
                this.respuesta.put(IndexedOut.OTROS_ERRORES, ex.getMessage());
                java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            } catch (NoSuchPortException ex) {
                retComm = IFReturnValue.UNKNOW_SERIAL_PORT_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "No existe el puerto serie");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "No existe el puerto serie", ex);
            } catch (PortInUseException ex) {
                retComm = IFReturnValue.SERIAL_PORT_IN_USE_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Puerto serie en uso");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Puerto serie en uso", ex);
            } catch (UnsupportedCommOperationException ex) {
                retComm = IFReturnValue.UNKNOW_SERIAL_PORT_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Operacion de puerto serie no soportada");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Operacion del puerto serie no soportada", ex);
            }

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Combinando las respuestas de los comandos individuales");

            CombinarRespuesta(comm.getRespuesta());

            if (retComm != IFReturnValue.OK) {

                String tmpStr = "Error ejecutando " + comm.getNombreA();

                tmpStr += " --" + retComm.getDescription() + "--";

                tmpStr += " con los siguientes parametros\n";

                for (Parametro p : comm.getParams().keySet()) {
                    tmpStr += p.getCodigo().toString() + "(" + p.toString() + ") = " + comm.getParams().get(p) + "\n";
                }

                tmpStr += " las respuestas combinadas son\n";

                for (IndexedOut io : respuesta.keySet()) {
                    tmpStr += io.name() + " " + io.getDescripcion() + " = " + respuesta.get(io) + "\n";
                }

                Logger.getLogger(IFBatch.class).log(Level.ERROR, tmpStr + "\n" + this.toString());

                break;
            }
        }

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Fin de comandos IFBatch con retComm " + retComm);

        if (retComm != IFReturnValue.OK && this.comandos.size() > 0 && this.respuesta.containsKey(IndexedOut.EF__DOCUM_FISC_ABIERTO)) {

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "rutina de salvado de errores en IFBatch");

            retComm = IFReturnValue.OK;

            ifCommand comm = null;
            try {
                // un error en la ejecucion
                // trato de cancelar el tique en caso que se pueda
                if (comandos.get(0).getNombreA().equals("TIQUEABRE")) {

                    Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Intentando cancelar comprobante tiquet");

                    comm = new ifepson.commands.pagoCancelDescRecaTique();
                    ((ifepson.commands.pagoCancelDescRecaTique) comm).setCalificador(Calificador.CANCELAR_COMPROBANTE);
                    if (port == null) {
                        retComm = comm.ejecutar(this.getPortConfig(), getSerial());
                    } else {
                        retComm = comm.ejecutar(port, getSerial());
                    }
                }

                if (comandos.get(0).getNombreA().equals("FACTABRE")) {

                    Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Intentando cancelar comprobante factura / nota credito");

                    comm = new ifepson.commands.pagoCancelDescRecaFNC();
                    ((ifepson.commands.pagoCancelDescRecaFNC) comm).setCalificador(pagoCancelDescRecaFNC.Calificador.CANCELAR_COMPROBANTE);
                    if (port == null) {
                        retComm = comm.ejecutar(portConfig, getSerial());
                    } else {
                        retComm = comm.ejecutar(port, getSerial());
                    }
                }
            } catch (IFException ex) {
                retComm = IFReturnValue.SERIAL_PORT_UNSOPORTED_OP_ERROR;
                this.respuesta.put(IndexedOut.OTROS_ERRORES, ex.getMessage());
                java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            } catch (NoSuchPortException ex) {
                retComm = IFReturnValue.UNKNOW_SERIAL_PORT_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "No existe el puerto serie");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "No existe el puerto serie", ex);
            } catch (PortInUseException ex) {
                retComm = IFReturnValue.SERIAL_PORT_IN_USE_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Puerto serie en uso");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Puerto serie en uso", ex);
            } catch (UnsupportedCommOperationException ex) {
                retComm = IFReturnValue.SERIAL_PORT_UNSOPORTED_OP_ERROR;
                this.respuesta.put(IndexedOut.PUERTO_SERIE, "Operacion de puerto serie no soportada");
                Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Operacion del puerto serie no soportada", ex);
            }

            CombinarRespuesta(comm.getRespuesta());

            if (retComm != IFReturnValue.OK) {
                Logger.getLogger(IFBatch.class).log(Level.ERROR, "NO SE PUDO SALVAR EL ERROR ");
            }

        }

        ifCommand fin = new solEstado();
        if (this.secuencia != 127) {
            fin.setSecuencia((byte) 127);
        } else {
            fin.setSecuencia((byte) 100);
        }
        
        try {
            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Enviando comando con serie " + fin.getSecuencia() + " para que sincronice");
            if (port == null) {
                retComm = fin.ejecutar(this.getPortConfig(), getSerial());
            } else {
                retComm = fin.ejecutar(port, getSerial());
            }
            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "fin volvio con " + retComm);
        } catch (NoSuchPortException ex) {
            java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Error al ejecutar comando final, no existe el puerto serie", ex);
        } catch (PortInUseException ex) {
            java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Error al ejecutar comando final, puerto serie en uso", ex);
        } catch (UnsupportedCommOperationException ex) {
            java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Error al ejecutar comando final, comando no soportado el puerto serie", ex);
        } catch (IFException ex) {
            java.util.logging.Logger.getLogger(IFBatch.class.getName()).log(java.util.logging.Level.SEVERE, "Error al ejecutar comando final, IFException", ex);
        }

        if (port != null) {
            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "\nCerrando Puerto\n");
            port.close();
        }

    }

    /**
     * Combina la respuesta resp con la que tiene
     * @param resp 
     */
    private void CombinarRespuesta(Map<IndexedOut, String> resp) {
        for (IndexedOut key : resp.keySet()) {
            respuesta.put(key, resp.get(key));
        }
    }

    /**
     * devuelve el serial incrementado
     * @return 
     */
    public byte getSerial() {
        secuencia++;

        if (secuencia < 0x20) {
            secuencia = 0x20;
        }
        if (secuencia >= 0x7F) {
            secuencia = 0x20;
        }
        return secuencia;
    }

    /**
     * Transforma una linea de texto en un comando fiscal
     * @param linea
     * @return 
     */
    public static ifCommand parseCommand(String linea, String separador, int largoDesc, Map<String, Class> comandos) {

        String[] parser = linea.split(separador);

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Separando comando " + linea + " con separador " + separador + " y queda como comando:" + parser[0]);

        if (largoDesc == 0) {
            largoDesc = 16;
        }

        Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Seteando largo maximo de descripcion para Tique y TiqueFact en " + largoDesc);

        ifepson.doc.Parametro.IIT__DESCRIPCION_PROD.setLargo(largoDesc);
        ifepson.doc.Parametro.IIFNC__DESCRIPCION_PROD.setLargo(largoDesc);

        ifCommand comm = null;

        for (String commName : comandos.keySet()) {

            if (("@" + commName).toUpperCase().equals(parser[0].toUpperCase())) {
                try {
                    comm = (ifCommand) myjob.func.classutils.ClassFunc.getInstanceFromClassName(comandos.get(commName).getName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Clase no encontrada", ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "No se pudo crear instancia de clase", ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(IFBatch.class.getName()).log(Level.FATAL, "Acceso ilegal", ex);
                }
                break;
            }
        }

        if (comm != null) {

            Logger.getLogger(IFBatch.class).log(Level.DEBUG, "Agregando comando " + comm.getNombreA());

            for (int pos = 1; pos <= comm.getPosiblesParams().size() && pos < parser.length; pos++) {
                if (parser[pos].length() > 0) {
                    String paramVal = parser[pos];

                    if (paramVal.length() > comm.getIndexedParam(pos - 1).getLargo()) {
                        if (comm.getIndexedParam(pos - 1).getType() == DataType.Integer
                                || comm.getIndexedParam(pos - 1).getType() == DataType.Num2Dec
                                || comm.getIndexedParam(pos - 1).getType() == DataType.Num3Dec
                                || comm.getIndexedParam(pos - 1).getType() == DataType.Num4Dec
                                || comm.getIndexedParam(pos - 1).getType() == DataType.Num8Dec) {
                            paramVal = paramVal.substring(paramVal.length() - comm.getIndexedParam(pos - 1).getLargo());
                        } else {
                            paramVal = paramVal.substring(0, paramVal.length());
                        }
                    }

                    comm.setParam(pos - 1, paramVal);
                }
            }
        }


        return comm;
    }

    @Override
    public String toString() {
        String retVal = "IFBatch{" + "timeOut=" + timeOut + ", portConfig=" + portConfig + ", serial=" + secuencia + "\n";

        retVal += "\tORIGINAL:\n" + this.batchOriginal + "\tFIN ORIGINAL\n";

        for (int i = 0; i < comandos.size(); i++) {
            retVal += "\t[" + i + "]" + comandos.get(i).toString().replace("\n", "\n\t") + "\n";
        }

        return retVal + "}";
    }
}
