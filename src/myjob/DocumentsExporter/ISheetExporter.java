/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.DocumentsExporter;

import java.sql.ResultSet;
import java.util.Map;
import javax.swing.table.TableModel;

/**
 *
 * @author guillermot
 */
public interface ISheetExporter extends IDocumentExporter {

    void writeRow(Object[] tabla, ISheetPoint sp) throws Exception;

    void writeRow(Object[] tabla, ISheetPoint sp, String cellFormatName) throws Exception;

    Integer writeTable(ResultSet rs);

    Integer writeTable(ResultSet rs, ISheetPoint sp);

    Integer writeTable(ResultSet rs, ISheetPoint sp, Map<Integer, String> formulas);

    Integer writeTable(ResultSet rs, ISheetPoint sp, boolean withTitles, boolean withTypes, Map<Integer, String> formulas, boolean horizontal);

    Integer writeTable(TableModel tm);

    Integer writeTable(TableModel tm, ISheetPoint sp);

    Integer writeTable(TableModel tm, ISheetPoint sp, Map<Integer, String> formulas);

    Integer writeTable(TableModel tm, ISheetPoint sp, boolean withTitles, boolean withTypes, Map<Integer, String> formulas, boolean horizontal);

    Integer writeTable(Object[][] tabla);

    Integer writeTable(Object[][] tabla, ISheetPoint sp);

    Integer writeTable(Object[][] tabla, ISheetPoint sp, Map<Integer, String> formulas);

    Integer writeTable(Object[][] tabla, ISheetPoint sp, boolean withTitles, boolean withTypes, Map<Integer, String> formulas, boolean horizontal);

    void writeValue(ISheetPoint sp, Object value);

    void writeValue(String hoja, Object value, int row, int col);

    void writeValue(String hoja, Object value, int row, int col, String formatName);

    void writeFormula(ISheetPoint sp, String formula);

    void writeFormula(ISheetPoint sp, String cellName, String formula);

    void addFormulas(ISheetPoint sp, Map<Integer, String> formulas, boolean horizontal, int rowIni, int colIni, int delta);

    ISheetPoint newSheetPoint(String name, int row, int col);

    ISheetPoint newSheetPoint(String name);

    String getFileName();

    void setFileName(String fileName);

    void saveDoc() throws Exception;

    void saveDoc(String fileName) throws Exception;

}
