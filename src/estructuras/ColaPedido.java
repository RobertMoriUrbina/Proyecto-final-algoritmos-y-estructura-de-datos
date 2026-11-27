package estructuras;

import modelo.Comanda;

public class ColaPedido {
    private NodoCola frente;
    private NodoCola fin;

    public ColaPedido() {
        this.frente = null;
        this.fin = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void encolar(Comanda nuevaComanda) {
        if (nuevaComanda == null) {
            System.out.println("⚠️ Aviso: Intento de encolar una comanda nula. Se ignoró.");
            return;
        }

        NodoCola nuevoNodo = new NodoCola(nuevaComanda);

        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        }
        else if (nuevaComanda.getPrioridad() < frente.getComanda().getPrioridad()) {
            nuevoNodo.setSiguiente(frente);
            frente = nuevoNodo;
        }
        else {
            NodoCola actual = frente;
            while (actual.getSiguiente() != null &&
                    actual.getSiguiente().getComanda().getPrioridad() <= nuevaComanda.getPrioridad()) {
                actual = actual.getSiguiente();
            }

            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);

            if (nuevoNodo.getSiguiente() == null) {
                fin = nuevoNodo;
            }
        }
    }

    public Comanda desencolar() {
        if (estaVacia()) {
            return null;
        }

        Comanda comandaAtendida = frente.getComanda();
        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }
        return comandaAtendida;
    }

    public Comanda peek() {
        if (estaVacia()) {
            return null;
        }
        return frente.getComanda();
    }

    public int tamanio() {
        int contador = 0;
        NodoCola actual = frente;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }
}