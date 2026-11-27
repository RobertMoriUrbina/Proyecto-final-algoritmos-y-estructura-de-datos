package modelo;

public class Administrador extends Empleado {
    private String nivelAcceso;
    private String sucursal;

    public Administrador(String id, String nombre, String correo, String contrasena,
                         String cargo, String turno, double salario,
                         String nivelAcceso, String sucursal) {
        super(id, nombre, correo, contrasena, cargo, turno, salario);
        this.nivelAcceso = nivelAcceso;
        this.sucursal = sucursal;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }
}
