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
public interface IrsExporter {

    public void Escribir(exportEventArgs evt) throws IOException;

    public void escribir(String fileName, DataTable tabla) throws IOException;

    public void escribir(String fileName, DataTable tabla, String[] titulos) throws IOException;

    public void escribir(String fileName, ResultSet tabla) throws IOException;

    public void escribir(String fileName, ResultSet tabla, String[] titulos) throws IOException;
}
