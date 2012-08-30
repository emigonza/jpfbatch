/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpfbatch.net;

/**
 *
 * @author guillermot
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import jpfbatch.pub;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class TCPClient {

    String batch = "";

    public TCPClient(String batch) {
        this.batch = batch;
    }

    public List<String> communicate(boolean testMode) throws Exception {

        Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Iniciando tcp client");
        
        Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Seteando test mode en " + testMode);

        String recivedLine;
        
        Socket clientSocket = new Socket(pub.getEnv().getProperty("HostName", "localhost"), (Integer) myjob.func.prop.PropTools.getObject(pub.getEnv(), "TCPPort"));

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        if(testMode) {
            batch = "<TestMode>\n" + batch;
        }
        
        batch += "\n<fin de transmicion>\n";
        
        Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Enviando \n" + batch);
        
        outToServer.writeBytes(batch);
        
        outToServer.flush();
        
        Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Envio finalizado, esperando respuesta");

        List<String> respuesta = new ArrayList<String>();
        
        while (!(recivedLine = inFromServer.readLine()).equals("/end")) {
            Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Respuesta obtenida " + recivedLine);
            respuesta.add(recivedLine);
        }

        clientSocket.close();
        
        Logger.getLogger(TCPClient.class).log(Level.DEBUG, "Finalizando tcp client");

        return respuesta;
    }
}
