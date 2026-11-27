package modelo;

import java.time.LocalDateTime;

public class Comanda {
    private int id;
    private LocalDateTime horaIngreso;
    private String estado;
    private int prioridad;


    public Comanda(int id, LocalDateTime horaIngreso, String estado, int prioridad) {
        this.id = id;
        this.horaIngreso = (horaIngreso != null) ? horaIngreso : LocalDateTime.now();
        this.estado = estado;
        this.prioridad = prioridad;
    }

    public int getId() { return id; }
    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public String getEstado() { return estado; }
    public int getPrioridad() { return prioridad; }

    public void iniciarPreparacion() {
        this.estado = "EN PREPARACION";
    }

    public void marcarLista() {
        this.estado = "LISTA";
    }

    @Override
    public String toString() {
        return "Comanda [ID=" + id + ", Hora=" + horaIngreso + ", Estado=" + estado + ", Prioridad=" + prioridad + "]";
    }
}