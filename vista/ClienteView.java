package vista;

import ListaEnlazada.Lista;
import controlador.ClienteController;
import java.util.Scanner;
import modelo.Cliente;

public class ClienteView {
    private ClienteController clienteController;
    private Scanner scanner;

    public ClienteView(ClienteController clienteController) {
        this.clienteController = clienteController;
        this.scanner = new Scanner(System.in);
    }

    public ClienteView(ClienteController clienteController, Scanner scanner) {
        this.clienteController = clienteController;
        this.scanner = scanner != null ? scanner : new Scanner(System.in);
    }

    public void mostrarMenuClientes() {
        if (clienteController == null) {
            System.out.println("Error: ClienteController no inicializado.");
            return;
        }

        while (true) {
            System.out.println("\n=== DOLCE CAPRICCIO - GESTION DE CLIENTES ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Buscar cliente por correo");
            System.out.println("5. Actualizar cliente");
            System.out.println("6. Eliminar cliente");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarCliente();
                    break;
                case "2":
                    listarClientes();
                    break;
                case "3":
                    buscarPorId();
                    break;
                case "4":
                    buscarPorCorreo();
                    break;
                case "5":
                    actualizarCliente();
                    break;
                case "6":
                    eliminarCliente();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    private void registrarCliente() {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();
        System.out.print("Direccion: ");
        String direccion = scanner.nextLine().trim();

        Cliente nuevo = new Cliente(id, nombre, correo, contrasena, telefono, direccion);
        if (clienteController.registrarCliente(nuevo)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar. Verifique que el ID o correo no esten duplicados.");
        }
    }

    private void listarClientes() {
        Lista<Cliente> lista = clienteController.getClientes();
        if (lista == null || lista.estaVacia()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n--- Lista de clientes (" + clienteController.totalClientes() + ") ---");
        int total = lista.cantidadNodos();
        for (int i = 0; i < total; i++) {
            Cliente c = lista.obtenerElemento(i);
            if (c != null) {
                System.out.println((i + 1) + ". ID: " + c.getId()
                        + " | Nombre: " + c.getNombre()
                        + " | Correo: " + c.getCorreo()
                        + " | Tel: " + c.getTelefono()
                        + " | Dir: " + c.getDireccion()
                        + " | Puntos: " + c.getPuntosFidelidad());
            }
        }
    }

    private void buscarPorId() {
        System.out.print("Ingrese ID del cliente: ");
        String id = scanner.nextLine().trim();
        Cliente c = clienteController.buscarPorId(id);
        mostrarDetalleCliente(c);
    }

    private void buscarPorCorreo() {
        System.out.print("Ingrese correo del cliente: ");
        String correo = scanner.nextLine().trim();
        Cliente c = clienteController.buscarPorCorreo(correo);
        mostrarDetalleCliente(c);
    }

    private void actualizarCliente() {
        System.out.print("Ingrese ID del cliente a actualizar: ");
        String id = scanner.nextLine().trim();
        Cliente existente = clienteController.buscarPorId(id);
        if (existente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre (dejar vacio para mantener '" + existente.getNombre() + "'): ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo telefono (dejar vacio para mantener '" + existente.getTelefono() + "'): ");
        String telefono = scanner.nextLine();
        System.out.print("Nueva direccion (dejar vacio para mantener '" + existente.getDireccion() + "'): ");
        String direccion = scanner.nextLine();

        if (clienteController.actualizarCliente(id, nombre, telefono, direccion)) {
            System.out.println("Cliente actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el cliente.");
        }
    }

    private void eliminarCliente() {
        System.out.print("Ingrese ID del cliente a eliminar: ");
        String id = scanner.nextLine().trim();
        if (clienteController.eliminarCliente(id)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se encontro un cliente con ese ID.");
        }
    }

    private void mostrarDetalleCliente(Cliente c) {
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("ID: " + c.getId()
                + " | Nombre: " + c.getNombre()
                + " | Correo: " + c.getCorreo()
                + " | Tel: " + c.getTelefono()
                + " | Dir: " + c.getDireccion()
                + " | Puntos: " + c.getPuntosFidelidad());
    }
}
