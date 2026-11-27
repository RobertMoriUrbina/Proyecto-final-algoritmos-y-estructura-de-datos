package controlador;

import ListaEnlazada.Lista;
import modelo.Cliente;

public class ClienteController {
    private Lista<Cliente> clientes;

    public ClienteController(Lista<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || clientes == null) {
            return false;
        }
        if (buscarPorId(cliente.getId()) != null || buscarPorCorreo(cliente.getCorreo()) != null) {
            return false;
        }
        clientes.agregarAlFinal(cliente);
        return true;
    }

    public Cliente buscarPorId(String id) {
        if (id == null || clientes == null || clientes.estaVacia()) {
            return null;
        }
        int total = clientes.cantidadNodos();
        for (int i = 0; i < total; i++) {
            Cliente actual = clientes.obtenerElemento(i);
            if (actual != null && actual.getId() != null
                    && actual.getId().equalsIgnoreCase(id)) {
                return actual;
            }
        }
        return null;
    }

    public Cliente buscarPorCorreo(String correo) {
        if (correo == null || clientes == null || clientes.estaVacia()) {
            return null;
        }
        int total = clientes.cantidadNodos();
        for (int i = 0; i < total; i++) {
            Cliente actual = clientes.obtenerElemento(i);
            if (actual != null && actual.getCorreo() != null
                    && actual.getCorreo().equalsIgnoreCase(correo)) {
                return actual;
            }
        }
        return null;
    }

    public boolean actualizarCliente(String id, String nuevoNombre, String nuevoTelefono, String nuevaDireccion) {
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            return false;
        }
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            cliente.setNombre(nuevoNombre.trim());
        }
        if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
            cliente.setTelefono(nuevoTelefono.trim());
        }
        if (nuevaDireccion != null && !nuevaDireccion.trim().isEmpty()) {
            cliente.setDireccion(nuevaDireccion.trim());
        }
        return true;
    }

    public boolean eliminarCliente(String id) {
        Cliente cliente = buscarPorId(id);
        if (cliente == null || clientes == null) {
            return false;
        }
        return clientes.eliminar(cliente);
    }

    public int totalClientes() {
        return clientes != null ? clientes.cantidadNodos() : 0;
    }

    public Lista<Cliente> getClientes() {
        return clientes;
    }
}
