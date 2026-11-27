package modelo;

public class Cliente extends Persona {
    private String telefono;
    private String direccion;
    private int puntosFidelidad;

    public Cliente(String id, String nombre, String correo, String contrasena,
                   String telefono, String direccion) {
        super(id, nombre, correo, contrasena);
        this.telefono = telefono;
        this.direccion = direccion;
        this.puntosFidelidad = 0;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }

    public void sumarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntosFidelidad += puntos;
        }
    }

    @Override
    public String getRol() {
        return "CLIENTE";
    }
}
