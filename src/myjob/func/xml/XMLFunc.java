/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author guillermot
 */
public class XMLFunc {

    public static NodeList childNodes(Node node) {

        NodeList nl = node.getChildNodes();

        miNodeList retVal = new miNodeList();

        for(int loc_Conta = 0; loc_Conta < nl.getLength(); loc_Conta++) {

            if(nl.item(loc_Conta).getNodeType() == 1) {
                retVal.add(nl.item(loc_Conta));
            }
        }

        return retVal;

    }

    public static class miNodeList extends ArrayList<Node> implements NodeList {


        public Node item(int index) {
            return get(index);
        }

        public int getLength() {
            return size();
        }

    }

    public static void saveXml(Document doc, String fileName) throws TransformerConfigurationException, TransformerException {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource(doc);
        Result dest = new StreamResult(new File(fileName));

        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception ex) {
            // no pasa nada si no puedo borrar el archivo
        }

        aTransformer.transform(src, dest);
    }
}