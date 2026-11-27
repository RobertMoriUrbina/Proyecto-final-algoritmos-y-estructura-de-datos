package modelo;

public abstract class Persona {
    protected String id;
    protected String nombre;
    protected String correo;
    protected String contrasena;

    public Persona(String id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean validarCredenciales(String correo, String contrasena) {
        return this.correo != null && this.contrasena != null
                && this.correo.equalsIgnoreCase(correo)
                && this.contrasena.equals(contrasena);
    }

    public abstract String getRol();

    @Override
    public String toString() {
        return "[" + getRol() + "] " + nombre + " (" + correo + ")";
    }
}
