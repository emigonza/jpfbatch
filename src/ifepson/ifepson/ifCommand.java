/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import ifepson.doc.IndexedOut;
import ifepson.doc.Parametro;
import gnu.io.SerialPort;
import ifepson.doc.DataType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import myjob.func.general.GeneralFunc;
import myjob.func.io.PortConfig;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public abstract class ifCommand {

    static final byte STX = 0x02;
    static final byte SEPARATOR = 0x1c;
    static final byte ETX = 0x03;
    static final byte NAK = 0x15;
    public static int CANT_NAK = 40;
    static final int BCC_LENGTH = 4;
    public static int reintentos = 3;
    protected byte secuencia = 0x0;
    protected byte commandId = 0x3a;
    protected Map<Parametro, String> params = new EnumMap<Parametro, String>(Parametro.class);
    protected List<Parametro> posiblesParams = null;
    protected List<IndexedOut> posiblesSalidas = null;
    protected Map<IndexedOut, String> respuesta = null;
    protected byte[] respuestabin = null;
    protected List<RespuestaCheck> respuestaChecks = new ArrayList<RespuestaCheck>();
    protected Map<EstadoFiscal, String> estadoFiscal = null;
    protected Map<EstadoImpresora, String> estadoImpresora = null;
    protected String name = "CommandName";
    protected String descripcionComando = "";
    protected int timeOut = 1000;
    protected String nombreA = "";
    protected IFAction errorAction = IFAction.ABORT;

    public int getLength() {

        // inicio, secuencia y comando
        int retVal = 3;

        for (Parametro p : params.keySet()) {
            // 1 del separador mas el largo del campo

            retVal += 1;

            if (!params.get(p).equals("[--. N U L O .--]")) {
                if (params.get(p) != null) {
                    retVal += params.get(p).length();
                }
            }
        }

        // fin de texto + el bcc
        retVal += BCC_LENGTH + 1;

        return retVal;
    }

    public List<Parametro> getPosiblesParams() {
        if (posiblesParams == null) {
            posiblesParams = new ArrayList<Parametro>();

            for (Parametro param : Parametro.values()) {
                if (param.getComando() != null && param.getComando().isInstance(this)) {
                    posiblesParams.add(param);
                }
            }

        }
        return posiblesParams;
    }

    public List<IndexedOut> getPosiblesSalidas() {
        if (posiblesSalidas == null) {
            posiblesSalidas = new ArrayList<IndexedOut>();

            for (IndexedOut out : IndexedOut.values()) {
                if (out.getComando().isInstance(this)) {
                    posiblesSalidas.add(out);
                }
            }

        }
        return posiblesSalidas;
    }

    public Parametro getIndexedParam(int index) {
        for (Parametro p : getPosiblesParams()) {
            if (p.getIndice() == index) {
                return p;
            }
        }

        return null;
    }

    public Parametro setParam(int index, String value) {

        for (Parametro p : getPosiblesParams()) {
            if (p.getIndice() == index) {
                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "seteando parametro " + p.name() + " = " + value + "  -( largo:" + p.getLargo() + ")-");
                if (p.getType() == DataType.Alfa) {
                    String loc_Val = "";
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) < 32 || value.charAt(i) > 127) {
                            loc_Val += " ";
                        } else {
                            loc_Val += value.charAt(i);
                        }
                    }
                    params.put(p, (loc_Val + myjob.func.text.TextFunc.repeat(p.getLargo(), ' ')).substring(0, p.getLargo()));
                } else {
                    params.put(p, value);
                }
                return p;
            }
        }

        return null;

    }

    public IFAction getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(IFAction errorAction) {
        this.errorAction = errorAction;
    }

    public byte[] getComandoBin() {
        byte[] retVal = new byte[getLength()];

        int pos = 0;

        retVal[pos++] = STX;
        retVal[pos++] = secuencia;
        retVal[pos++] = commandId;

        List<Parametro> dictParam = new ArrayList<Parametro>(params.keySet()); //List<Parametro>) params.keySet();

        myjob.func.general.Sort.quicksort(dictParam, "indice");


        for (Parametro p : dictParam) {

            retVal[pos++] = SEPARATOR;

            if (!params.get(p).equals("[--. N U L O .--]")) {

                retVal = GeneralFunc.writeInArray(retVal, params.get(p), pos);
                pos += params.get(p).length();
            }
        }

        retVal[pos++] = ETX;

        retVal = setBcc(retVal);

        return retVal;
    }

    public byte getCommandId() {
        return commandId;
    }

    public void setCommandId(byte commandId) {
        this.commandId = commandId;
    }

    public String getParam(int pos) {
        return params.get(pos);
    }

    public Map<Parametro, String> getParams() {
        return params;
    }

    public Map<IndexedOut, String> getRespuesta() {
        return respuesta;
    }

    public byte getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(byte secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcionComando() {
        return descripcionComando;
    }

    public void setDescripcionComando(String descripcionComando) {
        this.descripcionComando = descripcionComando;
    }

    public String getNombreA() {
        if (nombreA.length() == 0) {
            return name.toUpperCase();
        }
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public static int getReintentos() {
        return reintentos;
    }

    public static void setReintentos(int reintentos) {
        ifCommand.reintentos = reintentos;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public abstract boolean interpretaRespuesta(byte[] resp);
    InputStream is = null;
    OutputStream os = null;

    /**
     * Devuelve:
     * 0 si esta todo bien
     * 1 si el tiempo de espera esta agotado en enviar comando
     * 2 si el tiempo de espera esta agotado en recibir respuesta
     * 3 si la respuesta es vacia
     * 4 error desconocido
     * 5 error fiscal
     * 6 error al interpretar respuesta
     * 15 no existe el puerto
     * 16 puerto en uso
     * 10 si NAK
     * 11 error de impresion
     * 20 secuencia fuera de rango
     * @param pc
     * @param secuencia
     * @return
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     */
    public IFReturnValue ejecutar(PortConfig pc, int secuencia) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {
        this.secuencia = (byte) secuencia;
        return ejecutar(pc);
    }

    /**
     * Devuelve:
     * 0 si esta todo bien
     * 1 si el tiempo de espera esta agotado en enviar comando
     * 2 si el tiempo de espera esta agotado en recibir respuesta
     * 3 si la respuesta es vacia
     * 4 error desconocido
     * 5 error fiscal
     * 6 error al interpretar respuesta
     * 15 no existe el puerto
     * 16 puerto en uso
     * 10 si NAK
     * 11 error de impresion
     * 20 secuencia fuera de rango
     * @param pc
     * @return
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     */
    public IFReturnValue ejecutar(PortConfig pc) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {
        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Iniciendo ejecucion de " + this.getNombreA() + " con el puerto cerrado");

        int reintento = 4;

        IFReturnValue retVal = IFReturnValue.OK;

        do {

            SerialPort port = null;

            reintento--;

            try {
                port = (SerialPort) CommPortIdentifier.getPortIdentifier(pc.getPortName()).open(this.name, pc.getBaudRate());
            } catch (NoSuchPortException noSuchPortException) {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "El puerto no existe", noSuchPortException);
                respuesta.put(IndexedOut.PUERTO_SERIE, "El puerto no existe");
                return IFReturnValue.UNKNOW_SERIAL_PORT_ERROR;
            } catch (PortInUseException portInUseException) {
                respuesta.put(IndexedOut.PUERTO_SERIE, "El puerto ya está siendo usado");
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "Puerto en uso", portInUseException);
                return IFReturnValue.SERIAL_PORT_IN_USE_ERROR;
            }

            Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Abriendo el puerto " + pc.getPortName());

            port.setSerialPortParams(
                    pc.getBaudRate(),
                    pc.getDataBits(),
                    pc.getStopBits(),
                    pc.getParity());

            port.setDTR(true);

            retVal = ejecutarInterno(port, true);

            if (retVal == IFReturnValue.NEED_RETRY && reintento > 0) {
                // voy a reintentar
                try {
                    Thread.sleep(this.timeOut);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error al dormir thread", ex);
                }
                Random aleatorio = new Random();
                this.secuencia = (byte) (aleatorio.nextInt(127 - 32) + 32);
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "sorteo secuencia porque voy a reintentar " + this.getName() + " sec:" + secuencia);
            }

        } while (retVal == IFReturnValue.NEED_RETRY && reintento > 0);

        return retVal;
    }

    /**
     * Devuelve:
     * 0 si esta todo bien
     * 1 si el tiempo de espera esta agotado en enviar comando
     * 2 si el tiempo de espera esta agotado en recibir respuesta
     * 3 si la respuesta es vacia
     * 4 error desconocido
     * 5 error fiscal
     * 6 error al interpretar respuesta
     * 15 no existe el puerto
     * 16 puerto en uso
     * 10 si NAK
     * 11 error de impresion
     * 20 secuencia fuera de rango
     * @param pc
     * @param secuencia
     * @return
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     */
    public IFReturnValue ejecutar(SerialPort port, int secuencia) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {
        this.secuencia = (byte) secuencia;
        return ejecutar(port);
    }

    /**
     * Devuelve:
     * 0 si esta todo bien
     * 1 si el tiempo de espera esta agotado en enviar comando
     * 2 si el tiempo de espera esta agotado en recibir respuesta
     * 3 si la respuesta es vacia
     * 4 error desconocido
     * 5 error fiscal
     * 6 error al interpretar respuesta
     * 15 no existe el puerto
     * 16 puerto en uso
     * 10 si NAK
     * 11 error de impresion
     * 20 secuencia fuera de rango
     * @param pc
     * @param secuencia
     * @return
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     */
    public IFReturnValue ejecutar(SerialPort port) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {

        int reintento = 4;

        IFReturnValue retVal = IFReturnValue.OK;

        do {
            reintento--;
            retVal = ejecutarInterno(port, false);

            if (retVal == IFReturnValue.NEED_RETRY && reintento > 0) {
                // voy a reintentar

                try {
                    Thread.sleep(this.timeOut);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error al dormir thread", ex);
                }
                Random aleatorio = new Random();

                this.secuencia = (byte) (aleatorio.nextInt(127 - 32) + 32);
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "sorteo secuencia porque voy a reintentar " + this.getName() + " sec:" + secuencia);
            }

        } while (retVal == IFReturnValue.NEED_RETRY && reintento > 0);

        return retVal;
    }

    protected IFReturnValue ejecutarInterno(SerialPort port, boolean needClosePort) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IFException {

        Calendar endTime;

        respuesta = new EnumMap<IndexedOut, String>(IndexedOut.class);

        try {

            if (secuencia < 0x20 || secuencia > 0x7f) {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "secuencia fuera de rango en comando " + this.getName() + " sec:" + secuencia);
                Random aleatorio = new Random();
                this.secuencia = (byte) (aleatorio.nextInt(127 - 32) + 32);
            }

            //byte[] defResp = null;

            is = port.getInputStream();
            os = port.getOutputStream();
            // mando el comando mientras me mande un NAK (0x15)


            boolean error = false;

            int reintento;
            byte[] comandoBin = this.getComandoBin();

            synchronized (ifCommand.class) {


                // vacio el buffer antes de mandar nada

                int vaciaBuffer = 300;

                while (is.available() > 0 && vaciaBuffer > 0) {
                    // lee hasta que termina pa vaciar el stream
                    int tmp = is.read();
                    vaciaBuffer--;
                }

                if (is.available() > 0) {
                    throw new IFException("No se pudo vaciar el buffer del puerto serie");
                }

                // intento 3 veces

                List<Byte> loc_respuesta;

                reintento = 3;
                // reintento como maximo 3 veces para mandar el comando
                // lo mando de nuevo en caso que el controlador me responda con un NAK
                // pero lo limito porque si mando mucho se tilda
                do {

                    loc_respuesta = new ArrayList<Byte>();

                    reintento--;
                    error = false;

                    Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Enviando comando " + this.getNombreA() + " al controlador");
                    Logger.getLogger(ifCommand.class).log(Level.DEBUG, "\n                                1         2         3         4         5         6         7         8         9         0         1         2         3\n"
                            + "                      01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890\n"
                            + "enviando comando      " + GeneralFunc.bytesToString(this.getComandoBin()) + "\n" + GeneralFunc.bytesToStringDebug(this.getComandoBin()));

                    os.write(comandoBin);
                    os.flush();

                    endTime = Calendar.getInstance();

                    endTime.add(Calendar.MILLISECOND, this.timeOut);

                    while (is.available() == 0) {
                        if (Calendar.getInstance().getTimeInMillis() > endTime.getTimeInMillis()) {
                            break;
                        }
                        // espero que llegue algo
                    }

                    if (is.available() == 0) {
                        Logger.getLogger(ifCommand.class).log(Level.ERROR, "Se agotó el tiempo para la espera de respuesta del controlador con comando " + this.getNombreA() + " reintento " + reintento + " serial " + this.secuencia);

                        /* no tiro error, reintento.
                        
                         * NOTA: 
                        
                         * ANTES: throw new IFException("Se agotó el tiempo para la espera de respuesta del controlador con comando " + this.getNombreA());
                        
                         */

                        // AHORA:
                        // Bandera de error en true
                        error = true;

                        if (reintento > 0) {
                            // si voy a reintentar duermo el thread un ratito y resorteo el nro serie del comando
                            try {
                                Thread.sleep(this.timeOut);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(ifCommand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }

                            // resorteo la secuencia (por las dudas)
                            Random aleatorio = new Random(Calendar.getInstance().getTimeInMillis());
                            this.secuencia = (byte) (aleatorio.nextInt(127 - 32) + 32);
                            Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Resorteo secuencia de comando " + this.getNombreA() + " a " + secuencia);
                        }
                        //FIN AHORA
                    } else {
                        // recibi algo, lo cargo en tmp

                        byte tmp = (byte) is.read();

                        if (tmp != NAK) {
                            if (tmp == 0x12 || tmp == 0x14) {
                                // me pide un tiempito mas pa responder pero se lo voy a dar mas adelante
                                // descarto to tmp
                                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Recibi " + tmp + " que me dice que espere un ratito");
                            } else {
                                // cargo tmp a respuesta
                                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Agrego a la respuesta " + tmp + " como lo primero que recibe");
                                loc_respuesta.add(tmp);
                            }
                            reintento = 0;
                        } else {
                            Logger.getLogger(ifCommand.class).log(Level.ERROR, "Recibi un NAK, reintento! --quedan " + reintento + "-- (OJO EL PIOJO QUE SI MANDO MAS DE 4 SE BLOQUEA EL CONTROLADOR)");
                            // antes de reenviar lo duermo un ratito
                            try {
                                Thread.sleep(this.timeOut);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(ifCommand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }

                            error = true;
                        }
                    }
                } while (reintento > 0);

                if (!error && reintento < 2) {
                    Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error salvado enviando comando " + this.getNombreA() + " serial " + this.secuencia);
                }

                if (error && reintento == 0) {
                    Logger.getLogger(ifCommand.class).log(Level.ERROR, "Enviar comando ha fallado en " + this.getNombreA() + " serial " + this.secuencia);
                } else {
                    // si no hay error continuo

                    reintento = 3;

                    while (reintento >= 0) {

                        reintento--;

                        // uso un buffer de 1024, creo que alcanza
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        int posBCC = 0;
                        boolean interrumpir = false;

                        endTime = Calendar.getInstance();
                        endTime.add(Calendar.MILLISECOND, this.timeOut);
                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Iniciando recepcion con tiempos curtime: " + Calendar.getInstance().getTimeInMillis() + ", endtime: " + endTime.getTimeInMillis());

                        try {

                            while (!interrumpir) {

                                if (Calendar.getInstance().getTimeInMillis() > endTime.getTimeInMillis()) {
                                    Logger.getLogger(ifCommand.class).log(Level.ERROR, "Tiempo de espera agotado al esperar respuesta en el controlador [curTime:" + Calendar.getInstance().getTimeInMillis() + " > endTime:" + endTime.getTimeInMillis() + "]");
                                    error = true;
                                    interrumpir = true;
                                    continue;
                                }

                                if (is.available() == 0) {
                                    // espero que llegue algo
                                    continue;
                                }

                                len = is.read(buffer);

                                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Leyendo " + len + " byte(s) desde el controlador");

                                for (int loc_Conta = 0; loc_Conta < len; loc_Conta++) {

                                    if ((buffer[loc_Conta] == 0x12 || buffer[loc_Conta] == 0x14) && posBCC == 0) {
                                        Calendar c = Calendar.getInstance();
                                        c.add(Calendar.MILLISECOND, this.timeOut);
                                        endTime = c;
                                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "reiniciando endtime por recibir un " + buffer[loc_Conta] + " tiempo actual = " + Calendar.getInstance().getTimeInMillis() + " endTime actual = " + endTime.getTimeInMillis());
                                        continue;
                                    } else if (buffer[loc_Conta] != 0x02 && loc_respuesta.isEmpty()) {
                                        Calendar c = Calendar.getInstance();
                                        c.add(Calendar.MILLISECOND, this.timeOut);
                                        endTime = c;
                                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Buscando inicio de recepcion 0x02, quitando " + ((Byte) buffer[loc_Conta]).toString() + " y reiniciando endtime a " + endTime.getTimeInMillis());
                                        continue;
                                    }

                                    loc_respuesta.add(buffer[loc_Conta]);

                                    if (buffer[loc_Conta] == 0x03 || posBCC > 0) {
                                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "recibiendo BCC[" + posBCC + "]");
                                        posBCC++;
                                    }

                                    if (posBCC > 4) {
                                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "recepcion finalizada");
                                        interrumpir = true;
                                        break;
                                    }
                                }

                                // si interrumpido esta en true se va
                            } // end while(!interrumpir)

                            Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Copiando respuesta a respuestabin");
                            this.respuestabin = new byte[loc_respuesta.size()];
                            for (int n = 0; n < loc_respuesta.size(); n++) {
                                this.respuestabin[n] = loc_respuesta.get(n);
                            }

                            if (is.available() > 0) {

                                len = is.read(buffer);

                                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Se quitaron " + len + " byte(s) del buffer para vaciarlo");

                            }

                        } catch (IOException e) {
                            Logger.getLogger(ifCommand.class).log(Level.ERROR, e.getMessage(), e);
                            error = true;
                        }

                        boolean sale = false;

                        try {
                            sale = checkBcc(respuestabin);
                        } catch (Exception e) {
                        }

                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "\n"
                                + "              1         2         3         4         5         6         7         8         9         0         1         2         3\n"
                                + "    01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890\n"
                                + "Controlador respondió " + GeneralFunc.bytesToStringDebug(respuestabin));

                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Chequeando BCC " + sale);

                        if (!sale) {
                            Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Espero " + this.timeOut + " para mandar un NAK y recibo nuevamente la respuesta");
                            loc_respuesta = new ArrayList<Byte>();
                            os.write(NAK);

                            // duermo timeOut segundos antes de enviar un NAK para pedir reenvio de datos
                            try {
                                Thread.sleep(this.timeOut);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(ifCommand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                            }
                        } else {
                            break;
                        }

                        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "quedan " + reintento + " intentos");

                    }
                }
            }

            if (needClosePort) {
                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Cerrando puerto " + port.getName());
                port.close();
            }


            // fin de comunicación con el puerto

            IFReturnValue retVal = IFReturnValue.OK;

            if (error || reintento < 0) {
                String tmp = "Error al intentar obtener respuesta\n";
                if (respuestabin != null) {
                    tmp += "llegó hasta el momento del error: " + GeneralFunc.bytesToString(this.respuestabin);
                } else {
                    tmp += "respuesta vacia";
                }

                Logger.getLogger(ifCommand.class).log(Level.ERROR, tmp);

                return IFReturnValue.EMPTY_RESPONSE_ERROR;
            }

            if (this.respuestabin == null || this.respuestabin.length == 0) {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "La respuesta del impresor es " + (this.respuestabin == null ? "nula" : "vacia"));
                return IFReturnValue.EMPTY_RESPONSE_ERROR;
            }

            String respuestaToString = GeneralFunc.bytesToString(respuestabin);


            if (!(this.respuestabin[1] == comandoBin[1] && this.respuestabin[2] == comandoBin[2])) {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "Comando con secuencia repetida con la anterior, sorteo otra secuencia, y devuelvo que se necesita reintentar");

                Logger.getLogger(ifCommand.class).log(Level.ERROR, "secuencia fuera de rango en comando " + this.getName() + " sec:" + secuencia);
                Random aleatorio = new Random();
                byte sorteo = 0;

                do {
                    sorteo = (byte) (aleatorio.nextInt(127 - 32) + 32);
                } while (sorteo == this.secuencia);

                this.secuencia = sorteo;

                return IFReturnValue.NEED_RETRY;
            }

            if (respuestaToString.toLowerCase().contains("error")) {
                this.respuesta.put(IndexedOut.SALIDA_IMPRESOR, respuestaToString.substring(respuestaToString.toLowerCase().indexOf("error"), respuestaToString.length() - 5));
            } else if (!interpretaRespuesta(this.respuestabin)) {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error al interpretar respuesta " + GeneralFunc.bytesToString(respuestabin));
                return IFReturnValue.UNKNOW_RESPONSE_ERROR;
            }

            Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Analizando respuesta (estado fiscal y estado impresora)");
            // analizo la respuesta para sacar los estados fiscales y de impresora
            this.estadoImpresora = EstadoImpresora.parseEstados(respuestabin, 4);
            this.estadoFiscal = EstadoFiscal.parseEstados(respuestabin, 9);

            String estados = "";

            if (this.estadoImpresora != null) {
                for (EstadoImpresora ei : this.estadoImpresora.keySet()) {
                    if (estados.length() > 0) {
                        estados += "\n";
                    }
                    estados += ei.getIndexedOut().getCod().toString() + ": Estado Impresora: " + ei.toString();
                    this.respuesta.put(ei.getIndexedOut(), "1");
                }
            } else {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error al interpretar estado impresora ");
                this.respuesta.put(IndexedOut.OTROS_ERRORES, "Error al interpretar estado impresora");
            }

            if (this.estadoFiscal != null) {
                for (EstadoFiscal ei : this.estadoFiscal.keySet()) {
                    if (estados.length() > 0) {
                        estados += "\n";
                    }
                    estados += ei.getIndexedOut().getCod().toString() + ": Estado Fiscal: " + ei.toString();
                    this.respuesta.put(ei.getIndexedOut(), "1");
                }
            } else {
                Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error al interpretar estado impresora ");
                this.respuesta.put(IndexedOut.OTROS_ERRORES, "Error al interpretar estado fiscal");
            }

            Logger.getLogger(ifCommand.class).log(Level.DEBUG, estados);

            if (this.estadoImpresora != null) {
                for (EstadoImpresora ei : this.estadoImpresora.keySet()) {

                    if (ei.toString().toLowerCase().contains("error")) {
                        Logger.getLogger(ifCommand.class).log(Level.ERROR, ei.getIndexedOut().getCod().toString() + ": Error de impresora");
                        return IFReturnValue.PRINTER_ERROR;
                    }
                }
            }

            if (this.estadoFiscal != null) {
                for (EstadoFiscal ei : this.estadoFiscal.keySet()) {
                    if (ei.toString().toLowerCase().contains("error")) {
                        Logger.getLogger(ifCommand.class).log(Level.ERROR, ei.getIndexedOut().getCod().toString() + ": Error fiscal");
                        return IFReturnValue.FISCAL_ERROR;
                    }
                }
            }

        } catch (IOException iOException) {
            Logger.getLogger(ifCommand.class).log(Level.ERROR, "Error de entrada salida " + iOException.getMessage(), iOException);
            this.respuesta.put(IndexedOut.OTROS_ERRORES, iOException.getMessage());
            return IFReturnValue.UNKNOW_ERROR;
        }


        IFAction action = IFAction.NOTHING;

        // Si llega acá es porque en teoría está todo bien
        // igual falta checkear la respuesta

        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Chequeo " + this.respuestaChecks.size() + " estados");

        for (RespuestaCheck rc : this.respuestaChecks) {
            action = rc.check(respuesta);

            if (action == IFAction.RETRY) {
                Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Fin IFCommand " + this.getNombreA() + " NEED_RETRY\n\n");
                return IFReturnValue.NEED_RETRY;
            }
        }

        Logger.getLogger(ifCommand.class).log(Level.DEBUG, "Fin IFCommand " + this.getNombreA() + " OK\n\n");

        return IFReturnValue.OK;

    }

    public Map<EstadoFiscal, String> getEstadoFiscal() {
        return estadoFiscal;
    }

    public void setEstadoFiscal(Map<EstadoFiscal, String> estadoFiscal) {
        this.estadoFiscal = estadoFiscal;
    }

    public Map<EstadoImpresora, String> getEstadoImpresora() {
        return estadoImpresora;
    }

    public void setEstadoImpresora(Map<EstadoImpresora, String> estadoImpresora) {
        this.estadoImpresora = estadoImpresora;
    }

    static int getBcc(byte[] array) {
        int bcc = 0;

        for (int loc_Conta = 0; loc_Conta < array.length - BCC_LENGTH; loc_Conta++) {
            bcc += (int) array[loc_Conta];
        }

        return bcc;
    }

    static byte[] setBcc(byte[] array) {
        int bcc = getBcc(array);

        array = GeneralFunc.writeInArray(array, GeneralFunc.padLeft(Integer.toHexString(bcc).toUpperCase(), BCC_LENGTH, '0'), array.length - BCC_LENGTH);

        return array;
    }

    public static boolean checkBcc(byte[] array) {

        int cur_bcc = 0;
        StringBuilder sb = new StringBuilder();

        int pos = array.length - 1;

        do {
            sb.insert(0, (char) array[pos]);
            pos--;
        } while (array[pos] != 0x03);

        cur_bcc = Integer.parseInt(sb.toString(), 16);

        if (cur_bcc == getBcc(array)) {
            return true;
        }

        return false;
    }

    public boolean getErrorEstadoFiscal() {
        if (estadoFiscal == null) {
            return false;
        }

        if (estadoFiscal.containsKey(EstadoFiscal.Error)) {
            return true;
        }

        return false;
    }

    public boolean getErrorEstadoImpresora() {
        if (estadoImpresora == null) {
            return false;
        }

        if (estadoImpresora.containsKey(EstadoImpresora.Error)) {
            return true;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String retVal = "ifCommand{nombreA=" + nombreA + ", secuencia=" + secuencia + ", commandId=" + commandId + ", timeOut=" + timeOut + ", name=" + name + ", descripcionComando=" + descripcionComando + ",\n"
                + "\tComandoBin=" + myjob.func.general.GeneralFunc.bytesToString(this.getComandoBin()) + ",\n"
                + "\tRespuestaBin=" + myjob.func.general.GeneralFunc.bytesToString(this.respuestabin) + ",\n"
                + "\testadoFiscal=" + estadoFiscal + ",\n"
                + "\testadoImpresora=" + estadoImpresora + "\n";

        for (Parametro p : this.params.keySet()) {
            retVal += "\tparametro " + p.name() + " = " + params.get(p) + "\n";
        }

        for (IndexedOut io : this.respuesta.keySet()) {
            retVal += "\trespuesta " + io.name() + " = " + respuesta.get(io) + "\n";
        }

        return retVal + "}";
    }

    public enum EstadoFiscal {

        ErrCompMemFisc(new int[]{15 - 0, -15 + 7}, IndexedOut.EF__ERR_COMPROB_MEM_FISC),
        MemFiscFull(new int[]{15 - 0, 15 - 7}, IndexedOut.EF__MEM_FISC_LLENA),
        ErrCompMemTrab(new int[]{1}, IndexedOut.EF__ERR_COMPROB_MEM_TRAB),
        LowBat(new int[]{15 - 2}, IndexedOut.EF__POCA_BATERIA),
        ComNoRecon(new int[]{15 - 3}, IndexedOut.EF__ERR_COMAND_NO_RECON),
        CampDatInv(new int[]{15 - 4}, IndexedOut.EF__ERR_CAMPO_DATOS_INVALID),
        CampInvPEstFisc(new int[]{15 - 5}, IndexedOut.EF__ERR_COMANDO_INV_P_EST_FISC),
        DesbordTotales(new int[]{15 - 6, -15 + 11}, IndexedOut.EF__ERR_DESBORD_TOTALES),
        MemFiscCasiFull(new int[]{15 - 8}, IndexedOut.EF__MEM_FISC_CASI_LLENA),
        ImpFiscalizado(new int[]{15 - 9, 15 - 10}, IndexedOut.EF__IMPRES_FISCAL_FISCALIZADO),
        ImpCertific(new int[]{15 - 9, -15 + 10}, IndexedOut.EF__IMPRES_FISCAL_CERTIFICADO),
        ImpDesfisc(new int[]{-15 + 9, 15 - 10}, IndexedOut.EF__IMPRES_FISCAL_DESFISCALIZADO),
        NececCierreZoTicket(new int[]{-15 + 6, 15 - 11}, IndexedOut.EF__NECESITA_Z),
        NececTranspHoja(new int[]{15 - 6, 15 - 11}, IndexedOut.EF__ERR_NECESITA_TRANSPORTE_HOJA),
        DocFiscalAbierto(new int[]{15 - 12}, IndexedOut.EF__DOCUM_FISC_ABIERTO),
        DocFiscalAbiertoRollo(new int[]{15 - 12, 15 - 13}, IndexedOut.EF__DOCUM_FISCAL_ABI_ROLLO_PAPEL),
        DocNoFiscalAbiertoRollo(new int[]{-15 + 12, 15 - 13}, IndexedOut.EF__DNF_ABIERTO_ROLLO_PAPEL),
        DocAbiertoHojaSuelta(new int[]{15 - 14}, IndexedOut.EF__DOCUM_ABIERTO_HOJA_SUELTA),
        Error(new int[]{}, IndexedOut.EF__ERROR_ESTADO_FISCAL);
        int[] bitsIndex = null;
        IndexedOut detalle = null;

        EstadoFiscal(int[] bitsIndex, IndexedOut detalle) {
            this.bitsIndex = bitsIndex;
            this.detalle = detalle;
        }

        public int[] getBitsIndex() {
            return bitsIndex;
        }

        public void setBitsIndex(int[] bitsIndex) {
            this.bitsIndex = bitsIndex;
        }

        public IndexedOut getIndexedOut() {
            return detalle;
        }

        public static Map<EstadoFiscal, String> parseEstados(byte[] hexa) {
            StringBuilder sb = new StringBuilder();

            for (byte b : hexa) {
                sb.append((char) b);
            }

            return parseEstados(sb.toString());
        }

        public static Map<EstadoFiscal, String> parseEstados(byte[] array, int from) {
            StringBuilder sb = new StringBuilder();

            for (int loc_Conta = 0; loc_Conta < 4; loc_Conta++) {
                sb.append((char) array[from + loc_Conta]);
            }

            return parseEstados(sb.toString());
        }

        public static Map<EstadoFiscal, String> parseEstados(String hexa) {

            Map<EstadoFiscal, String> retVal = null;

            retVal = new EnumMap<EstadoFiscal, String>(EstadoFiscal.class);

            int n = Integer.parseInt(hexa, 16);

            String binStr = GeneralFunc.padLeft(Integer.toBinaryString(Integer.parseInt(hexa, 16)), 16, '0');

            boolean add = false;

            for (EstadoFiscal curEst : EstadoFiscal.values()) {

                if (curEst.getBitsIndex().length > 0) {
                    add = true;

                    for (int idx : curEst.getBitsIndex()) {

                        if (idx > 0) {
                            // tiene que estar prendido el bit

                            add = add && binStr.charAt(idx) == '1';

                        } else {
                            // tiene que estar apagado el bit

                            add = add && binStr.charAt(Math.abs(idx)) == '0';
                        }
                    }

                    if (add) {
                        retVal.put(curEst, "1");
                    }
                }
            }

            for (int idx = 15; idx > 7; idx--) {
                if (binStr.charAt(idx) == '1') {
                    retVal.put(EstadoFiscal.Error, binStr);
                    break;
                }
            }


            if (!retVal.containsKey(EstadoFiscal.Error) && binStr.charAt(15 - 4) == '1') {
                retVal.put(EstadoFiscal.Error, binStr);
            }

            return retVal;
        }
    }

    public enum EstadoImpresora {

        ErrorDeImp(new int[]{15 - 2}, IndexedOut.EI__ERROR_EN_LA_IMPRESORA),
        FueraLinea(new int[]{15 - 3}, IndexedOut.EI__IMPRES_FUERA_LINEA),
        BufferLleno(new int[]{15 - 6}, IndexedOut.EI__BUFFER_LLENO),
        BufferVacio(new int[]{15 - 7}, IndexedOut.EI__BUFFER_VACIO),
        InHojaSuelPrep(new int[]{15 - 8}, IndexedOut.EI__ENTRADA_HOJ_SUEL_FRONT_PREP),
        HojaSuelPrep(new int[]{15 - 9}, IndexedOut.EI__HOJ_SUEL_FROT_PREPAR),
        SinPapel(new int[]{15 - 14}, IndexedOut.EI__SIN_PAPEL),
        PocoPapel(new int[]{}, IndexedOut.EI__POCO_PAPEL),
        Error(new int[]{}, IndexedOut.EI__ERROR_ESTADO_IMPRESORA);
        int[] bitsIndex = null;
        IndexedOut detalle = null;

        EstadoImpresora(int[] bitsIndex, IndexedOut detalle) {
            this.bitsIndex = bitsIndex;
            this.detalle = detalle;
        }

        public int[] getBitsIndex() {
            return bitsIndex;
        }

        public void setBitsIndex(int[] bitsIndex) {
            this.bitsIndex = bitsIndex;
        }

        public IndexedOut getIndexedOut() {
            return detalle;
        }

        public static Map<EstadoImpresora, String> parseEstados(byte[] hexa) {
            StringBuilder sb = new StringBuilder();

            for (byte b : hexa) {
                sb.append((char) b);
            }

            return parseEstados(sb.toString());
        }

        public static Map<EstadoImpresora, String> parseEstados(byte[] array, int from) {
            StringBuilder sb = new StringBuilder();

            for (int loc_Conta = 0; loc_Conta < 4; loc_Conta++) {
                sb.append((char) array[from + loc_Conta]);
            }

            return parseEstados(sb.toString());
        }

        public static Map<EstadoImpresora, String> parseEstados(String hexa) {

            Map<EstadoImpresora, String> retVal = new EnumMap<EstadoImpresora, String>(EstadoImpresora.class);

            int n = 0;


            n = Integer.parseInt(hexa, 16);



            String binStr = GeneralFunc.padLeft(Integer.toBinaryString(Integer.parseInt(hexa, 16)), 16, '0');

            boolean add = false;

            for (EstadoImpresora curEst : EstadoImpresora.values()) {

                if (curEst.getBitsIndex().length > 0) {
                    add = true;

                    for (int idx : curEst.getBitsIndex()) {

                        if (idx > 0) {
                            // tiene que estar prendido el bit

                            add = add && binStr.charAt(idx) == '1';

                        } else {
                            // tiene que estar apagado el bit

                            add = add && binStr.charAt(Math.abs(idx)) == '0';
                        }
                    }

                    if (add) {
                        retVal.put(curEst, "1");
                    }
                }
            }

            for (int idx = 15; idx > 9; idx--) {
                if (binStr.charAt(idx) == '1') {
                    retVal.put(EstadoImpresora.Error, binStr);
                    break;
                }
            }

            if (!retVal.containsKey(EstadoImpresora.Error) && binStr.charAt(2) == '1') {
                retVal.put(EstadoImpresora.Error, binStr);
            }

            if (retVal.containsKey(EstadoImpresora.Error) && !retVal.containsKey(EstadoImpresora.ErrorDeImp) && !retVal.containsKey(EstadoImpresora.FueraLinea) && !retVal.containsKey(EstadoImpresora.BufferLleno) && !retVal.containsKey(EstadoImpresora.SinPapel)) {
                retVal.remove(EstadoImpresora.Error);
                retVal.put(EstadoImpresora.PocoPapel, "1");
            }

            return retVal;
        }
    }
}
