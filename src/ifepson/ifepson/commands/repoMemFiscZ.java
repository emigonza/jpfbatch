/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ifepson.commands;

import ifepson.ifCommand;
import ifepson.doc.Parametro;
import myjob.func.general.GeneralFunc;

/**
 *
 * @author Administrador
 */
public class repoMemFiscZ extends ifCommand {

    public TipoRepoMem getTipo() {
        return TipoRepoMem.parseLetra(params.get(Parametro.RZ__TIPO_REPORTE));
    }

    public void setTipo(TipoRepoMem tipo) {
        params.put(Parametro.RZ__TIPO_REPORTE, tipo.getLetra());
    }

    public Integer getzFin() {
        return Integer.parseInt(params.get(Parametro.RZ__Z_FIN));
    }

    public void setzFin(Integer zFin) {
        params.put(Parametro.RZ__Z_FIN, GeneralFunc.stringFormat("{0:0000}", zFin));
    }

    public Integer getzIni() {
        return Integer.parseInt(params.get(Parametro.RZ__Z_INICIO));
    }

    public void setzIni(Integer zIni) {
        params.put(Parametro.RZ__Z_INICIO, GeneralFunc.stringFormat("{0:0000}", zIni));
    }

    public repoMemFiscZ() {
        this.commandId = 0x3B;
        this.name = "reporteMemoriaFiscalPorZ";
        this.descripcionComando = "Este comando imprime un reporte de cierres diarios en forma selectiva por un rango de números de cierre. Este comando usa tiempo extendido para la finalización. Provee la opción de producir sólo totales, o totales y detalles de Cierres Diarios.";

        this.params.put(Parametro.RZ__Z_INICIO, GeneralFunc.padLeft("0", 4, '0'));
        this.params.put(Parametro.RZ__Z_FIN, GeneralFunc.padLeft("0", 4, '0'));
        this.params.put(Parametro.RZ__TIPO_REPORTE, TipoRepoMem.InfoAuditDetalle.getLetra());
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

            switch (letra.charAt(0)) {
                case 'T':
                    return TipoRepoMem.TotalGeneral;
                case 'D':
                    return TipoRepoMem.ReporteContador;
                case 't':
                    return TipoRepoMem.InfoAuditResum;
                case 'd':
                    return TipoRepoMem.InfoAuditDetalle;
            }

            return null;
        }
    }
}
