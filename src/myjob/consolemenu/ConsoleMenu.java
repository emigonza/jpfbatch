/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.consolemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.event.EventListenerList;

/**
 *
 * @author guillermot
 */
public class ConsoleMenu implements IMenu {

    IMenu parent;
    List<IMenu> childs = new ArrayList<IMenu>();
    String label = "";
    protected EventListenerList MenuListenerList = new EventListenerList();

    public ConsoleMenu() {
    }

    public ConsoleMenu(IMenu parent) {
        this.parent = parent;
    }

    public ConsoleMenu(String label) {
        this.label = label;
    }

    public ConsoleMenu(IMenu parent, String label) {
        this.parent = parent;
        parent.getChilds().add(this);
        this.label = label;
    }

    public ConsoleMenu(IMenu parent, String label, MenuEventListener actionEvent) {
        this.parent = parent;
        parent.getChilds().add(this);
        this.label = label;
        this.addEventListener(actionEvent);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    int printMenu() {

        int i = 0;

        System.out.println(" " + this.label);
        System.out.println(myjob.func.text.TextFunc.repeat(this.label.length() + 2, '-'));
        System.out.println();

        for (i = 0; i < this.childs.size(); i++) {
            System.out.println(i + " - " + this.childs.get(i).getLabel());
        }

        if (this.parent != null) {
            System.out.println(i + " - atras");
        } else {
            System.out.println(i + " - salir");
        }

        return i;
    }

    public void actionPerformed() {

        Object[] listeners = this.MenuListenerList.getListenerList();
        if (listeners.length == 0) {
            // no tiene accion asociada, muestro los hijos

            int optSale;

            String s = "";

            System.out.println("Ingrese su opcion");

            Scanner scanner = new Scanner(System.in);

            do {

                optSale = printMenu();

                s = scanner.nextLine();

                if (myjob.func.general.GeneralFunc.IsInteger(s)) {
                    int opt = Integer.parseInt(s);

                    if (opt == optSale) {
                        break;
                    } else {
                        childs.get(opt).actionPerformed();
                    }
                }

                

            } while (!s.equals(optSale + ""));

        } else {
            // Each listener occupies two elements - the first is the listener class
            // and the second is the listener instance

            MenuEventArgs loc_mea = new MenuEventArgs(this);

            for (int i = 0; i
                    < listeners.length; i += 2) {
                ((MenuEventListener) listeners[i + 1]).onEvent(loc_mea);
            }
        }
    }

    public IMenu getParent() {
        return parent;
    }

    public void setParent(IMenu menu) {
        this.parent = menu;
    }

    public void back() {
        parent.actionPerformed();
    }

    public List<IMenu> getChilds() {
        return childs;
    }

    public void setChilds(List<IMenu> childs) {
        this.childs = childs;
    }

    public void addEventListener(MenuEventListener listener) {
        this.MenuListenerList.add(MenuEventListener.class, listener);
    }

    public void removeEventListener(MenuEventListener listener) {
        this.MenuListenerList.remove(MenuEventListener.class, listener);
    }
}
