/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.DocumentsExporter;

/**
 *
 * @author guillermot
 */
public interface IDocumentExporter {

    String getExecPath();

    void setExecPath(String path);

    void prepareConn() throws Exception;

    String getDefaultExtension();

    void saveDoc(String fileName) throws Exception;
}
