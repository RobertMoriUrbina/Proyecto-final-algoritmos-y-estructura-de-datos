package controlador;

import ListaEnlazada.Lista;
import modelo.Persona;

public class LoginController {
    private Lista<Persona> usuarios;
    private Persona usuarioActual;

    public LoginController(Lista<Persona> usuarios) {
        this.usuarios = usuarios;
        this.usuarioActual = null;
    }

    public boolean registrarUsuario(Persona persona) {
        if (persona == null || usuarios == null) {
            return false;
        }
        if (buscarPorCorreo(persona.getCorreo()) != null) {
            return false;
        }
        usuarios.agregarAlFinal(persona);
        return true;
    }

    public Persona buscarPorCorreo(String correo) {
        if (correo == null || usuarios == null || usuarios.estaVacia()) {
            return null;
        }
        int total = usuarios.cantidadNodos();
        for (int i = 0; i < total; i++) {
            Persona actual = usuarios.obtenerElemento(i);
            if (actual != null && actual.getCorreo() != null
                    && actual.getCorreo().equalsIgnoreCase(correo)) {
                return actual;
            }
        }
        return null;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Persona encontrada = buscarPorCorreo(correo);
        if (encontrada != null && encontrada.validarCredenciales(correo, contrasena)) {
            this.usuarioActual = encontrada;
            return true;
        }
        this.usuarioActual = null;
        return false;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Persona getUsuarioActual() {
        return usuarioActual;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public Lista<Persona> getUsuarios() {
        return usuarios;
    }
}
