package vista;

import controlador.ClienteController;
import controlador.InventarioController;
import controlador.PedidoController;
import controlador.ProductoController;
import controlador.ReporteController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPrincipal extends JFrame {

    // Controladores de todo el equipo (Lógica de la guía)
    private PedidoController pedidoCtrl;
    private InventarioController invCtrl;
    private ProductoController prodCtrl;
    private ClienteController cliCtrl;
    private ReporteController repCtrl;

    // Paleta de colores (Diseño de tu captura)
    private final Color colorAzulOscuro = new Color(16, 43, 83);
    private final Color colorMarron = new Color(139, 69, 19);
    private final Color colorGrisBarra = new Color(240, 240, 240);
    private final Font fuenteMenu = new Font("Segoe UI", Font.BOLD, 14);

    // 1. Constructor oficial para cuando integren todo el proyecto
    public MenuPrincipal(PedidoController pedidoCtrl, InventarioController invCtrl,
                         ProductoController prodCtrl, ClienteController cliCtrl,
                         ReporteController repCtrl) {
        this.pedidoCtrl = pedidoCtrl;
        this.invCtrl = invCtrl;
        this.prodCtrl = prodCtrl;
        this.cliCtrl = cliCtrl;
        this.repCtrl = repCtrl;
        inicializarComponentes();
    }

    // 2. Constructor vacío extra para que puedas probar TU ventana ahora mismo sin errores
    public MenuPrincipal() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Dolce Capriccio - Menú principal");
        setSize(860, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ==========================================
        // BARRA SUPERIOR (Título oscuro)
        // ==========================================
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panelTitulo.setBackground(colorAzulOscuro);
        JLabel lblTitulo = new JLabel("Dolce Capriccio - Menú principal");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);

        // ==========================================
        // BARRA DE MENÚS (Pedidos, Cocina...)
        // ==========================================
        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelMenu.setBackground(colorGrisBarra);
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        String[] opciones = {"Pedidos", "Cocina", "Inventario", "Productos", "Clientes", "Reportes"};

        for (String op : opciones) {
            JLabel lblMenu = new JLabel(op);
            lblMenu.setFont(fuenteMenu);
            lblMenu.setForeground(Color.BLACK);
            lblMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Efecto hover y clic
            lblMenu.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblMenu.setForeground(colorMarron);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    lblMenu.setForeground(Color.BLACK);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirVista(op);
                }
            });
            panelMenu.add(lblMenu);
        }

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelTitulo, BorderLayout.NORTH);
        panelNorte.add(panelMenu, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // ==========================================
        // PANEL CENTRAL (Mensaje de Bienvenida)
        // ==========================================
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(new EmptyBorder(100, 20, 20, 20));

        JLabel lblBienvenido = new JLabel("Bienvenido al Sistema de Gestión");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBienvenido.setForeground(colorMarron);
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Inteligente de Pedidos e Inventarios");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(colorAzulOscuro);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAyuda = new JLabel("Seleccione una opción de la barra de menú");
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAyuda.setForeground(Color.GRAY);
        lblAyuda.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalGlue());
        panelCentro.add(lblBienvenido);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentro.add(lblSubtitulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 40)));
        panelCentro.add(lblAyuda);
        panelCentro.add(Box.createVerticalGlue());

        add(panelCentro, BorderLayout.CENTER);
    }

    // ==========================================
    // LÓGICA DE NAVEGACIÓN (Basada en tu guía)
    // ==========================================
    public void abrirVista(String opcion) {
        try {
            switch (opcion) {
                case "Pedidos":
                    new PedidoView(pedidoCtrl).mostrar();
                    break;
                case "Cocina":
                    new CocinaView(pedidoCtrl).mostrar();
                    break;
                case "Inventario":
                    new InventarioView(invCtrl).mostrar();
                    break;
                case "Productos":
                    new ProductoView(prodCtrl).mostrar();
                    break;
                case "Clientes":
                    new ClienteView(cliCtrl).mostrar();
                    break;
                case "Reportes":
                    new ReporteView(repCtrl).mostrar();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Opción no reconocida: " + opcion);
            }
        } catch (Exception e) {
            // Blindaje: Si el controlador o la vista no existen aún, avisa sin cerrar el programa
            JOptionPane.showMessageDialog(this,
                    "El módulo de " + opcion + " aún no está disponible.\n" +
                            "(Falta el código de otro integrante o el controlador no está inicializado).",
                    "Módulo en construcción", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mostrar() {
        setVisible(true);
    }

    // Main de prueba (Para ver el diseño sin depender de nadie)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Usa el constructor vacío para que no falle por las clases que faltan
            new MenuPrincipal().setVisible(true);
        });
    }
}