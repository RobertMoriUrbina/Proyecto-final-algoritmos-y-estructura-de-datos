package estructuras;

import modelo.Comanda;

public class NodoCola {
    private Comanda comanda;
    private NodoCola siguiente;

    public NodoCola(Comanda comanda) {
        if (comanda == null) {
            throw new IllegalArgumentException("Error: No se puede crear un NodoCola sin una Comanda.");
        }
        this.comanda = comanda;
        this.siguiente = null;
    }

    public Comanda getComanda() { return comanda; }
    public void setComanda(Comanda comanda) { if (comanda != null) this.comanda = comanda; }

    public NodoCola getSiguiente() { return siguiente; }
    public void setSiguiente(NodoCola siguiente) { this.siguiente = siguiente; }
}