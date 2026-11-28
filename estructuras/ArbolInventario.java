package estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Insumo;

/** Árbol binario de búsqueda de insumos, ordenado por código. */
public class ArbolInventario {

    private Nodo raiz;
    private int tamanio;

    /** Inserta un insumo. No se permiten códigos duplicados. */
    public void insertar(Insumo insumo) {
        if (insumo == null) {
            throw new IllegalArgumentException("El insumo no puede ser nulo.");
        }
        if (buscar(insumo.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un insumo con código " + insumo.getCodigo() + ".");
        }
        raiz = insertar(raiz, insumo);
        tamanio++;
    }

    /** Busca por código; devuelve null si no existe. */
    public Insumo buscar(String codigo) {
        validarCodigo(codigo);
        Nodo actual = raiz;
        while (actual != null) {
            int comparacion = codigo.compareToIgnoreCase(actual.insumo.getCodigo());
            if (comparacion == 0) {
                return actual.insumo;
            }
            actual = comparacion < 0 ? actual.izquierdo : actual.derecho;
        }
        return null;
    }

    /** Elimina y devuelve el insumo encontrado; devuelve null si no existía. */
    public Insumo eliminar(String codigo) {
        validarCodigo(codigo);
        Insumo encontrado = buscar(codigo);
        if (encontrado != null) {
            raiz = eliminar(raiz, codigo);
            tamanio--;
        }
        return encontrado;
    }

    /** Devuelve los insumos ordenados ascendentemente por código. */
    public List<Insumo> listarEnOrden() {
        List<Insumo> resultado = new ArrayList<>(tamanio);
        listarEnOrden(raiz, resultado);
        return Collections.unmodifiableList(resultado);
    }

    public int getTamanio() {
        return tamanio;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void vaciar() {
        raiz = null;
        tamanio = 0;
    }

    private Nodo insertar(Nodo actual, Insumo insumo) {
        if (actual == null) {
            return new Nodo(insumo);
        }
        int comparacion = insumo.getCodigo().compareToIgnoreCase(actual.insumo.getCodigo());
        if (comparacion < 0) {
            actual.izquierdo = insertar(actual.izquierdo, insumo);
        } else {
            actual.derecho = insertar(actual.derecho, insumo);
        }
        return actual;
    }

    private Nodo eliminar(Nodo actual, String codigo) {
        if (actual == null) {
            return null;
        }
        int comparacion = codigo.compareToIgnoreCase(actual.insumo.getCodigo());
        if (comparacion < 0) {
            actual.izquierdo = eliminar(actual.izquierdo, codigo);
        } else if (comparacion > 0) {
            actual.derecho = eliminar(actual.derecho, codigo);
        } else if (actual.izquierdo == null) {
            return actual.derecho;
        } else if (actual.derecho == null) {
            return actual.izquierdo;
        } else {
            Nodo sucesor = minimo(actual.derecho);
            actual.insumo = sucesor.insumo;
            actual.derecho = eliminar(actual.derecho, sucesor.insumo.getCodigo());
        }
        return actual;
    }

    private Nodo minimo(Nodo actual) {
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    private void listarEnOrden(Nodo actual, List<Insumo> resultado) {
        if (actual != null) {
            listarEnOrden(actual.izquierdo, resultado);
            resultado.add(actual.insumo);
            listarEnOrden(actual.derecho, resultado);
        }
    }

    private static void validarCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo ni vacío.");
        }
    }

    /** Nodo interno para mantener esta implementación aislada de otras partes. */
    private static final class Nodo {
        private Insumo insumo;
        private Nodo izquierdo;
        private Nodo derecho;

        private Nodo(Insumo insumo) {
            this.insumo = insumo;
        }
    }
}
