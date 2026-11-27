package vista;

import controlador.LoginController;
import java.util.Scanner;
import modelo.Persona;

public class LoginView {
    private LoginController loginController;
    private Scanner scanner;

    public LoginView(LoginController loginController) {
        this.loginController = loginController;
        this.scanner = new Scanner(System.in);
    }

    public LoginView(LoginController loginController, Scanner scanner) {
        this.loginController = loginController;
        this.scanner = scanner != null ? scanner : new Scanner(System.in);
    }

    public boolean mostrarMenuLogin() {
        if (loginController == null) {
            System.out.println("Error: LoginController no inicializado.");
            return false;
        }

        while (true) {
            System.out.println("\n=== DOLCE CAPRICCIO - INICIO DE SESION ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine().trim();
                    System.out.print("Contrasena: ");
                    String contrasena = scanner.nextLine();

                    if (loginController.iniciarSesion(correo, contrasena)) {
                        Persona actual = loginController.getUsuarioActual();
                        System.out.println("Bienvenido, " + actual.getNombre() + " [" + actual.getRol() + "]");
                        return true;
                    } else {
                        System.out.println("Credenciales incorrectas. Intente nuevamente.");
                    }
                    break;
                case "2":
                    System.out.println("Saliendo del sistema...");
                    return false;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }
}
