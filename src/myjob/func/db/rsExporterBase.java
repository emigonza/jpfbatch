/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.db;

import java.io.IOException;
import java.sql.ResultSet;

/**
 *
 * @author Administrador
 */
public abstract class rsExporterBase implements IrsExporter {

    public void Escribir(exportEventArgs evt) throws IOException {
        escribir(evt.getFileName(), evt.getDataTable().getinternalResultSet());
    }

    public void escribir(String fileName, DataTable tabla) throws IOException {
        escribir(fileName, tabla.getinternalResultSet());
    }

    public void escribir(String fileName, DataTable tabla, String[] titulos) throws IOException {
        escribir(fileName, tabla.getinternalResultSet(), titulos);
    }

    public abstract void escribir(String fileName, ResultSet tabla) throws IOException;

    public void escribir(String fileName, ResultSet tabla, String[] titulos) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
