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
 * @author Administrador
 */
public class repoMemFiscFecha extends ifCommand {

    public void setFechaIni(String fecha) {
        params.put(Parametro.RF__FECHA_INICIO, fecha);
    }

    public String getFechaIni() {
        return params.get(Parametro.RF__FECHA_INICIO);
    }

    public Calendar getCalendarIni() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFechaIni().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFechaIni().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFechaIni().substring(4)));
        return c;
    }

    public void setCalendarIni(Calendar c) {
        params.put(Parametro.RF__FECHA_INICIO, GeneralFunc.stringFormat("{0:yyMMdd", c));
    }

    public void setFechaFin(String fecha) {
        params.put(Parametro.RF__FECHA_FIN, fecha);
    }

    public String getFechaFin() {
        return params.get(Parametro.RF__FECHA_FIN);
    }

    public Calendar getCalendarFin() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFechaFin().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFechaFin().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFechaFin().substring(4)));
        return c;
    }

    public void setCalendarFin(Calendar c) {
        params.put(Parametro.RF__FECHA_FIN, GeneralFunc.stringFormat("{0:yyMMdd", c));
    }

    public TipoRepoMem getTipo() {
        return TipoRepoMem.parseLetra(params.get(Parametro.RF__TIPO_REPORTE));
    }

    public void setTipo(TipoRepoMem tipo) {
        params.put(Parametro.RF__TIPO_REPORTE, tipo.getLetra());
    }

    public repoMemFiscFecha() {
        this.commandId = 58; //0x3A;
        this.name = "repoMemFiscFecha";
        this.descripcionComando = "Este comando imprime un reporte de Cierres Diarios en forma selectiva por un rango de fechas. Este comando usa tiempo extendido para su finalización. Además brinda la opción de producir sólo totales, o totales y detalles de Cierres Diarios.";

        //this.params.put(Parametro.RF__FECHA_INICIO, getStrFechaInicio());
        setCalendarIni(Calendar.getInstance());
        //this.params.put(Parametro.RF__FECHA_FIN, getStrFechaFin());
        setCalendarFin(Calendar.getInstance());
        this.params.put(Parametro.RF__TIPO_REPORTE, TipoRepoMem.InfoAuditDetalle.getLetra());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    public static enum TipoRepoMem {

        TotalGeneral("T", "Se envía ´T´ 0x54 para un Total General, sin detalle diario como Documento No Fiscal con centavos (“Reporte de Contador” resumido)."),
        ReporteContador("D", "Se envía ´D´ 0x44 para un reporte detallado como Documento No Fiscal con centavos (“Reporte de Contador” con detalles)."),
        InfoAuditResum("t", "Se envía ´t´ 0x74 para un Total General, sin detalle diario como Documento Fiscal con redondeo al peso (“Informe de Auditoría” resumido)."),
        InfoAuditDetalle("d", "Se envía ´d´ 0x64 para un reporte detallado como Documento Fiscal con redondeo al peso (“Informe de Auditoría” con detalles).");
        String letra = "";
        String descripcion = "";

        public String getLetra() {
            return letra;
        }

        public String getDescripcion() {
            return descripcion;
        }

        TipoRepoMem(String letra, String descripcion) {
            this.letra = letra;
            this.descripcion = descripcion;
        }

        public static TipoRepoMem parseLetra(String letra) {

            switch(letra.charAt(0)) {
                case 'T': return TipoRepoMem.TotalGeneral;
                case 'D': return TipoRepoMem.ReporteContador;
                case 't': return TipoRepoMem.InfoAuditResum;
                case 'd': return TipoRepoMem.InfoAuditDetalle;
            }

            return null;
        }
    }
}
