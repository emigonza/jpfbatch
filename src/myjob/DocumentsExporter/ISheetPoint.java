/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.DocumentsExporter;

/**
 *
 * @author guillermot
 */
public interface ISheetPoint {

    Integer getCol();

    void setCol(Integer _col);

    Integer nextCol();

    Integer getRow();

    void setRow(Integer _row);

    Integer nextRow();

    String getAddress();

    void addInterval(Integer Dx, Integer Dy);

    String getSheetName();

    void setSheetName(String _sheet);

    Object getNativeSheet();

    void setNativeSheet(Object nativeSheet);

    Object getNativeDocument();

    void setNativeDocument(Object doc);

    void MoveTo(int row, int col);
}
