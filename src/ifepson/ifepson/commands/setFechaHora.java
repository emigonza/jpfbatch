/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.Parametro;
import java.util.Calendar;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author guillermot
 */
public class setFechaHora extends ifCommand {

    public void setFecha(String fecha) {
        params.put(Parametro.EFH__FECHA, fecha);
    }

    public String getFecha() {
        return params.get(Parametro.EFH__FECHA);
    }

    public void setHora(String hora) {
        params.put(Parametro.EFH__HORA, hora);
    }

    public String getHora() {
        return params.get(Parametro.EFH__HORA);
    }

    public Calendar getFechaHora() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFecha().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFecha().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFecha().substring(4)));

        c.set(Calendar.HOUR, Integer.parseInt(getHora().substring(0, 1)));
        c.set(Calendar.MINUTE, Integer.parseInt(getHora().substring(2, 3)));
        c.set(Calendar.SECOND, Integer.parseInt(getHora().substring(4)));

        return c;
    }

    public void setFechaHora(Calendar c) {
        params.put(Parametro.EFH__FECHA, GeneralFunc.stringFormat("{0:yyMMdd", c));
        params.put(Parametro.EFH__HORA, GeneralFunc.stringFormat("{0:HHmmss", c));
    }

    public setFechaHora() {
        this.commandId = 0x58;
        this.name = "setFechaHora";
        this.descripcionComando = "Seteo de fecha y hora";

        this.params.put(Parametro.EFH__FECHA, GeneralFunc.stringFormat("{0:yyMMdd}", Calendar.getInstance()));
        this.params.put(Parametro.EFH__HORA, GeneralFunc.stringFormat("{0:HHmmss}", Calendar.getInstance()));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
