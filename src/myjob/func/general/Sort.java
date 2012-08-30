/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjob.func.general;

import java.util.List;
import myjob.func.classutils.ReflectionFunc;

/**
 *
 * @author guillermot
 */
public class Sort {

    public static void quicksort(List vector, String property) {
        quicksort(vector, property, true);
    }

    public static void quicksort(List vector, String property, boolean asc) {
        if (vector.size() == 0) {
            return;
        }

        quicksort(vector, property, 0, vector.size() - 1, asc);

    }

    public static void quicksort(List vector, String property, int primero, int ultimo, boolean asc) {
        int i, j, central;
        Object pivote;
        if (ultimo < primero) {
            return;
        }
        central = (primero + ultimo) / 2;
        pivote = vector.get(central);
        i = primero;
        j = ultimo;

        do {
            if (asc) {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector.get(i), property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) < 0) {
                    i++;
                }
            } else {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector.get(i), property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) > 0) {
                    i++;
                }
            }

            if (asc) {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector.get(j), property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) > 0) {
                    j--;
                }
            } else {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector.get(j), property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) < 0) {
                    j--;
                }
            }

            if (i <= j) {
                Object temp;
                temp = vector.get(i);
                vector.set(i, vector.get(j));
                vector.set(j, temp);

                i++;
                j--;

            }
        } while (i <= j);

        if (primero < j) {
            quicksort(vector, property, primero, j, asc);
        }

        if (i < ultimo) {
            quicksort(vector, property, i, ultimo, asc);
        }
    }

    public static void quicksort(Object[][] vector, int colKey) {
        quicksort(vector, colKey, true);
    }

    public static void quicksort(Object[][] vector, int colKey, boolean asc) {
        if (vector.length == 0) {
            return;
        }

        quicksort(vector, colKey, 0, vector.length - 1, asc);

    }

    public static void quicksort(Object[][] vector, int colKey, int primero, int ultimo, boolean asc) {
        int i, j, central;
        Object[] pivote;
        if (ultimo < primero) {
            return;
        }
        central = (primero + ultimo) / 2;
        pivote = vector[central];
        i = primero;
        j = ultimo;

        do {
            if (asc) {
                while (((Comparable) vector[i][colKey]).compareTo((Comparable) pivote[colKey]) < 0) {
                    i++;
                }
            } else {
                while (((Comparable) vector[i][colKey]).compareTo(pivote[colKey]) > 0) {
                    i++;
                }
            }

            if (asc) {
                while (((Comparable) vector[j][colKey]).compareTo(pivote[colKey]) > 0) {
                    j--;
                }
            } else {
                while (((Comparable) vector[j][colKey]).compareTo(pivote[colKey]) < 0) {
                    j--;
                }
            }

            if (i <= j) {
                Object[] temp;
                temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;

                i++;
                j--;

            }
        } while (i <= j);

        if (primero < j) {
            quicksort(vector, colKey, primero, j, asc);
        }

        if (i < ultimo) {
            quicksort(vector, colKey, i, ultimo, asc);
        }
    }

    public static void quicksort(Object[] vector) {
        quicksort(vector, true);
    }

    public static void quicksort(Object[] vector, boolean asc) {
        if (vector.length == 0) {
            return;
        }

        quicksort(vector, 0, vector.length - 1, asc);

    }

    public static void quicksort(Object[] vector, int primero, int ultimo, boolean asc) {
        int i, j, central;
        Object pivote;
        if (ultimo < primero) {
            return;
        }
        central = (primero + ultimo) / 2;
        pivote = vector[central];
        i = primero;
        j = ultimo;

        do {
            if (asc) {
                while (((Comparable) vector[i]).compareTo((Comparable) pivote) < 0) {
                    i++;
                }
            } else {
                while (((Comparable) vector[i]).compareTo(pivote) > 0) {
                    i++;
                }
            }

            if (asc) {
                while (((Comparable) vector[j]).compareTo(pivote) > 0) {
                    j--;
                }
            } else {
                while (((Comparable) vector[j]).compareTo(pivote) < 0) {
                    j--;
                }
            }

            if (i <= j) {
                Object temp;
                temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;

                i++;
                j--;

            }
        } while (i <= j);

        if (primero < j) {
            quicksort(vector, primero, j, asc);
        }

        if (i < ultimo) {
            quicksort(vector, i, ultimo, asc);
        }
    }

    public static void quicksort(Object[] vector, String property) {
        quicksort(vector, property, true);
    }

    public static void quicksort(Object[] vector, String property, boolean asc) {
        if (vector.length == 0) {
            return;
        }

        quicksort(vector, property, 0, vector.length - 1, asc);

    }

    public static void quicksort(Object[] vector, String property, int primero, int ultimo, boolean asc) {
        int i, j, central;
        Object pivote;
        if (ultimo < primero) {
            return;
        }
        central = (primero + ultimo) / 2;
        pivote = vector[central];
        i = primero;
        j = ultimo;

        do {
            if (asc) {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector[i], property)).compareTo((Comparable) ReflectionFunc.evaluateProperty(pivote, property)) < 0) {
                    i++;
                }
            } else {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector[i], property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) > 0) {
                    i++;
                }
            }

            if (asc) {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector[j], property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) > 0) {
                    j--;
                }
            } else {
                while (((Comparable) ReflectionFunc.evaluateProperty(vector[j], property)).compareTo(ReflectionFunc.evaluateProperty(pivote, property)) < 0) {
                    j--;
                }
            }

            if (i <= j) {
                Object temp;
                temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;

                i++;
                j--;

            }
        } while (i <= j);

        if (primero < j) {
            quicksort(vector, property, primero, j, asc);
        }

        if (i < ultimo) {
            quicksort(vector, property, i, ultimo, asc);
        }
    }
}
