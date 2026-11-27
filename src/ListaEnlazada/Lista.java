// ==========================================================
// ASIGNADO A: INTEGRANTE 1 (PARTE 1 - Acceso, Clientes y Lista)
// NO MODIFICAR si perteneces a otra parte.
// ==========================================================
package ListaEnlazada;

public class Lista<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int cont;

    public Lista() {
        this.inicio = null;
        this.fin = null;
        this.cont = 0;
    }

    public Nodo<T> getInicio() {
        return inicio;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int cantidadNodos() {
        return cont;
    }

    public void agregarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato, inicio);
        inicio = nuevo;
        if (fin == null) {
            fin = inicio;
        }
        cont++;
    }

    public void agregarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        cont++;
    }

    public void agregarEntreNodos(T dato, int pos) {
        if (pos <= 0 || estaVacia()) {
            agregarAlInicio(dato);
            return;
        }
        if (pos >= cont) {
            agregarAlFinal(dato);
            return;
        }
        Nodo<T> anterior = inicio;
        Nodo<T> temporal = inicio.getSiguiente();
        int item = 1;
        while (item < pos && temporal != null) {
            anterior = anterior.getSiguiente();
            temporal = temporal.getSiguiente();
            item++;
        }
        Nodo<T> nuevo = new Nodo<>(dato, temporal);
        anterior.setSiguiente(nuevo);
        cont++;
    }

    public boolean buscarDato(T dato) {
        if (dato == null) {
            return false;
        }
        Nodo<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato() != null && aux.getDato().equals(dato)) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    public void eliminarInicio() {
        if (estaVacia()) {
            return;
        }
        inicio = inicio.getSiguiente();
        cont--;
        if (inicio == null) {
            fin = null;
        }
    }

    public void eliminarFinal() {
        if (estaVacia()) {
            return;
        }
        if (inicio.getSiguiente() == null) {
            inicio = null;
            fin = null;
            cont = 0;
            return;
        }
        Nodo<T> aux = inicio;
        while (aux.getSiguiente().getSiguiente() != null) {
            aux = aux.getSiguiente();
        }
        aux.setSiguiente(null);
        fin = aux;
        cont--;
    }

    public boolean eliminar(T dato) {
        if (estaVacia() || dato == null) {
            return false;
        }
        if (inicio.getDato() != null && inicio.getDato().equals(dato)) {
            eliminarInicio();
            return true;
        }
        Nodo<T> anterior = inicio;
        Nodo<T> actual = inicio.getSiguiente();
        while (actual != null) {
            if (actual.getDato() != null && actual.getDato().equals(dato)) {
                anterior.setSiguiente(actual.getSiguiente());
                if (actual == fin) {
                    fin = anterior;
                }
                cont--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public T mostrarPrimerElemento() {
        return estaVacia() ? null : inicio.getDato();
    }

    public T mostrarUltimoElemento() {
        return estaVacia() ? null : fin.getDato();
    }

    public String mostrarLista() {
        if (estaVacia()) {
            return "La lista esta vacia.";
        }
        StringBuilder sb = new StringBuilder();
        Nodo<T> aux = inicio;
        while (aux != null) {
            sb.append(aux.getDato()).append("\n");
            aux = aux.getSiguiente();
        }
        return sb.toString();
    }
}
