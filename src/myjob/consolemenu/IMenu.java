/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package myjob.consolemenu;

import java.util.List;

/**
 *
 * @author guillermot
 */
public interface IMenu {
    void actionPerformed();

    IMenu getParent();

    void setParent(IMenu menu);

    void back();

    public List<IMenu> getChilds();

    public void setChilds(List<IMenu> childs);

    String getLabel();

    void setLabel(String label);

    void addEventListener(MenuEventListener listener);

    void removeEventListener(MenuEventListener listener);

}
