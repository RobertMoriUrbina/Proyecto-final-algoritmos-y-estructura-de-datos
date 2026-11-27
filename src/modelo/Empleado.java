package modelo;

public class Empleado extends Persona {
    private String cargo;
    private String turno;
    private double salario;

    public Empleado(String id, String nombre, String correo, String contrasena,
                    String cargo, String turno, double salario) {
        super(id, nombre, correo, contrasena);
        this.cargo = cargo;
        this.turno = turno;
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String getRol() {
        return "EMPLEADO";
    }
}
