
import controlador.ClienteController;
import controlador.InventarioController;
import controlador.PedidoController;
import controlador.ProductoController;
import controlador.ReporteController;
import vista.MenuPrincipal;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el estilo visual nativo.");
        }

        // 2. Iniciar la aplicación de forma segura
        SwingUtilities.invokeLater(() -> {
            try {


                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);

            } catch (Exception e) {
                System.err.println("Error al iniciar el sistema: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Faltan clases de otros integrantes para iniciar el sistema completo.\n" +
                                "Revisa la consola para más detalles.",
                        "Error de integración", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}