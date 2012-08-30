/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.classutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermot
 */
public class ReflectionFunc {

    public static Method getMethodByName(Class<?> clazz, String property) {

        Method method = null;

        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(property)) {
                method = m;
                break;
            }
        }

        return method;
    }

    public static Method getSetterMethod(Class<?> clazz, String property) {
        String prop = "";

        if (!property.startsWith("set")) {
            prop = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        }

        Method method = null;

        try {
            //Logger.getLogger(GeneralFunc.class.getName()).log(Level.INFO, "evaluateProperty " + property);
            method = ReflectionFunc.getMethodByName(clazz, prop);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return method;
    }

    public static Method getGetterMethod(Class<?> clazz, String property) {

        String prop = "";

        if (!property.startsWith("get") && !property.startsWith("is") && property.length() > 0) {
            prop = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        }

        Method method = null;

        try {
            //Logger.getLogger(GeneralFunc.class.getName()).log(Level.INFO, "evaluateProperty " + property);
            method = ReflectionFunc.getMethodByName(clazz, prop);

            if (method == null) {
                if (!property.startsWith("is")) {
                    prop = "is" + property.substring(0, 1).toUpperCase() + property.substring(1);
                    method = ReflectionFunc.getMethodByName(clazz, prop);
                }
            }

        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        return method;
    }

    public static void setProperty(Object instance, String property, Object value) {
        Method method = getSetterMethod(instance.getClass(), property);

        if (property == null) {

            System.out.println("error al setear metodo property es nulo");
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.INFO, "error al setear metodo property es nulo");
            return;
        }

        if (property.trim().length() == 0) {
            System.out.println("error al setear metodo property es vacio");
            return;
        }

        if (instance == null) {
            System.out.println("error al setear metodo " + property + " instance es nulo");
            return;
        }

        if (value == null) {
            System.out.println("error al setear metodo " + property + " el valor es nulo");
            return;
        }

        if (method == null) {
            System.out.println("error al setear metodo obteniendo metodo " + property);
            return;
        }

        try {
            method.invoke(instance, value);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error al setiar valor" + ex.getMessage());
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, "error al setear metodo " + property + " con " + value, ex);
        }
    }

    public static Object evaluateProperty(Object o, String property) {

        Method method = getGetterMethod(o.getClass(), property);

        try {
            return method.invoke(o);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void doMethod(Object o, String methodName) {

        Class c = o.getClass();
        //Method[] methods = c.getDeclaredMethods();


        Method method = null;
        try {
            //Logger.getLogger(GeneralFunc.class.getName()).log(Level.INFO, "evaluateProperty " + property);
            method = c.getMethod(methodName, new Class[0]);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            method.invoke(o);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ReflectionFunc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Returns the public static methods of a class or interface,
     *   including those declared in super classes and interfaces.
     */
    public static List<Method> getStaticMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                methods.add(method);
            }
        }
        return Collections.unmodifiableList(methods);
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        
        if (method.getReturnType().equals(void.class))  {
            return false;
        }
        
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }
}
