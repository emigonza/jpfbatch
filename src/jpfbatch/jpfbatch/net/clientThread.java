/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import myjob.func.io.PortConfig;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author guillermot
 */
public class clientThread extends Thread {

    DataInputStream is = null;
    PrintStream os = null;
    Socket clientSocket = null;
    String name = "noname";
    int timeOut = 1000;
    PortConfig portConfig;
    protected EventListenerList listenerList = new EventListenerList();
    protected boolean testMode = false;

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
    
    public void addEventListener(JPFBNetEventListener listener) {
        listenerList.add(JPFBNetEventListener.class, listener);
    }

    public void removeEventListener(JPFBNetEventListener listener) {
        listenerList.remove(JPFBNetEventListener.class, listener);
    }

    List<String> onEvent(String batch) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance

        JPFBNetEventArgs loc_tea = new JPFBNetEventArgs(this, batch, this.timeOut, this.portConfig, this.testMode);

        Boolean loc_Actualizar = false;

        List<String> retVal = new ArrayList<String>();

        for (int i = 0; i < listeners.length; i += 2) {
            retVal.addAll(((JPFBNetEventListener) listeners[i + 1]).onEjecutar(loc_tea));
        }

        return retVal;
    }

    public clientThread(Socket clientSocket, String name, int timeOut, PortConfig portConfig) {
        this.clientSocket = clientSocket;
        this.name = name;
        this.timeOut = timeOut;
        this.portConfig = portConfig;
    }

    public void run() {
        String line;
        String batch = "";

        Logger.getLogger(clientThread.class).log(Level.DEBUG, "Iniciando hilo de ejecucion " + this.name + " en el servidor ");

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

            while (true) {
                line = is.readLine();

                Logger.getLogger(clientThread.class).log(Level.DEBUG, "Recibiendo de <" + name + "> " + line);

                if (line == null || line.startsWith("<fin de transmicion>")) {
                    Logger.getLogger(clientThread.class).log(Level.DEBUG, "Recibi <fin de transmicion> así que termino");
                    break;
                } else if(line.equalsIgnoreCase("<TestMode>")) {
                    Logger.getLogger(clientThread.class).log(Level.DEBUG, "Seteo test mode");
                    this.testMode = true;
                } else {

                    //os.println("<" + name + "> " + line);
                    batch += line + "\n";
                }

            }

            Logger.getLogger(clientThread.class).log(Level.DEBUG, "termino de recibir, ahora ejecuto");

            // termino de recibir, ahora ejecuto
            List<String> respuesta = onEvent(batch);

            // termine de ejecutar, ahora envio la respuesta
            for (String s : respuesta) {
                os.println(s);
            }

            // envio fin de ejecucion
            os.println("/end");

            // close the output stream
            // close the input stream
            // close the socket
            is.close();
            os.close();
            clientSocket.close();

            Logger.getLogger(clientThread.class).log(Level.DEBUG, "Finalizando hilo de ejecucion " + this.name + "en el servidor ");

        } catch (IOException ex) {
            Logger.getLogger(clientThread.class).log(Level.FATAL, "Error en thread clientThread " + ex.getMessage(), ex);
        };
    }
}
