/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch.net;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import jpfbatch.Main;
import jpfbatch.pub;
import myjob.func.io.PortConfig;

/**
 *
 * @author guillermot
 */
public class TCPServer {

    List<clientThread> clientes = new ArrayList<clientThread>();
    int timeOut = 1000;
    PortConfig portConfig;

    public TCPServer(int timeOut, PortConfig portConfig) {
        this.timeOut = timeOut;
        this.portConfig = portConfig;
    }

    List<String> ejecutar(JPFBNetEventArgs evt) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        synchronized (TCPServer.class) {
            return Main.EjecutarBatchFromString(evt.getBatchString(), evt.getTimeOut(), evt.getPortConfig(), evt.isTestMode());
        }
    }

    public void listen() throws Exception {
        ServerSocket serverSocket = new ServerSocket((Integer) pub.getOptValue("TCPPort"));
        Socket clientSocket;

        while (true) {
            
            // un recolector de basura rudimentario...
            
            for(int i = clientes.size() - 1; i >= 0; i--) {
                if(!clientes.get(i).isAlive()) {
                    clientes.remove(i);
                }
            }
            
            clientSocket = serverSocket.accept();

            clientThread ct = new clientThread(clientSocket, "cliente" + clientes.size(), this.timeOut, this.portConfig);

            clientes.add(ct);

            ct.addEventListener(new JPFBNetEventListener() {

                public List<String> onEjecutar(JPFBNetEventArgs evt) {
                    List<String> retVal = null;
                    try {
                        retVal = ejecutar(evt);
                    } catch (IOException ex) {
                        Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchPortException ex) {
                        Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PortInUseException ex) {
                        Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedCommOperationException ex) {
                        Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return retVal;
                }
            });

            ct.start();

        }
    }
}