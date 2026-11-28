package vista;

import controlador.InventarioController;
import controlador.ReporteController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.Inventario;

/** Ventana Swing para generar y visualizar los reportes de la Parte 5. */
public class ReporteView extends JFrame {

    private static final String REPORTE_PRIORIDADES = "Conteo por prioridad (arreglo 1D)";
    private static final String REPORTE_VENTAS = "Ventas por turno y prioridad (matriz 2D)";
    private static final String REPORTE_INVENTARIO = "Inventario";
    private static final String REPORTE_CRITICO = "Stock crítico";

    private final ReporteController reporteController;
    private final InventarioController inventarioController;
    private final JComboBox<String> tipoReporte;
    private final JTextArea areaTexto;
    private final JTextField campoTurno;
    private final JTextField campoPrioridad;
    private final JTextField campoMonto;

    public ReporteView() {
        this(new ReporteController(), new InventarioController());
    }

    public ReporteView(ReporteController reporteController, InventarioController inventarioController) {
        super("Dolce Capriccio - Reportes");
        if (reporteController == null || inventarioController == null) {
            throw new IllegalArgumentException("Los controladores no pueden ser nulos.");
        }
        this.reporteController = reporteController;
        this.inventarioController = inventarioController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 540);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        tipoReporte = new JComboBox<>(new String[]{
            REPORTE_PRIORIDADES, REPORTE_VENTAS, REPORTE_INVENTARIO, REPORTE_CRITICO
        });
        JButton generar = new JButton("Generar reporte");
        generar.addActionListener(event -> generarReporteSeleccionado());
        JPanel selector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selector.add(new JLabel("Tipo de reporte:"));
        selector.add(tipoReporte);
        selector.add(generar);
        add(selector, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
        areaTexto.setText("Seleccione un tipo de reporte y pulse «Generar reporte».\n"
                + "El reporte de ventas muestra la matriz turno × prioridad.");
        JScrollPane desplazamiento = new JScrollPane(areaTexto);
        desplazamiento.setBorder(BorderFactory.createTitledBorder("Resultado"));
        add(desplazamiento, BorderLayout.CENTER);

        campoTurno = new JTextField(4);
        campoPrioridad = new JTextField(4);
        campoMonto = new JTextField(8);
        JPanel registro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        registro.setBorder(BorderFactory.createTitledBorder("Registrar venta para la matriz (índices desde 1)"));
        registro.add(new JLabel("Turno:"));
        registro.add(campoTurno);
        registro.add(new JLabel("Prioridad:"));
        registro.add(campoPrioridad);
        registro.add(new JLabel("Monto:"));
        registro.add(campoMonto);
        JButton registrarVenta = new JButton("Registrar venta");
        registrarVenta.addActionListener(event -> registrarVenta());
        registro.add(registrarVenta);
        JButton reiniciar = new JButton("Reiniciar datos del reporte");
        reiniciar.addActionListener(event -> reiniciarReporte());
        registro.add(reiniciar);
        JPanel inferior = new JPanel(new GridLayout(1, 1));
        inferior.add(registro);
        add(inferior, BorderLayout.SOUTH);
    }

    private void generarReporteSeleccionado() {
        try {
            String seleccion = (String) tipoReporte.getSelectedItem();
            String resultado;
            if (REPORTE_PRIORIDADES.equals(seleccion)) {
                resultado = reporteController.generarReportePrioridades();
            } else if (REPORTE_VENTAS.equals(seleccion)) {
                resultado = reporteController.generarReporteVentasTurnoPrioridad();
            } else if (REPORTE_INVENTARIO.equals(seleccion)) {
                resultado = reporteController.generarReporteInventario(obtenerInventario());
            } else {
                resultado = reporteController.generarReporteStockCritico(obtenerInventario());
            }
            areaTexto.setText(resultado);
            areaTexto.setCaretPosition(0);
        } catch (RuntimeException ex) {
            mostrarError(ex);
        }
    }

    private Inventario obtenerInventario() {
        return inventarioController.getInventario();
    }

    private void registrarVenta() {
        try {
            int turno = Integer.parseInt(campoTurno.getText().trim()) - 1;
            int prioridad = Integer.parseInt(campoPrioridad.getText().trim()) - 1;
            double monto = Double.parseDouble(campoMonto.getText().trim());
            reporteController.registrarVenta(turno, prioridad, monto);
            campoMonto.setText("");
            tipoReporte.setSelectedItem(REPORTE_VENTAS);
            generarReporteSeleccionado();
            JOptionPane.showMessageDialog(this, "Venta registrada en la matriz 2D.",
                    "Reporte", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            mostrarError(new IllegalArgumentException("Turno, prioridad y monto deben ser valores numéricos.", ex));
        } catch (RuntimeException ex) {
            mostrarError(ex);
        }
    }

    private void reiniciarReporte() {
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea borrar los datos acumulados del reporte?",
                "Confirmar reinicio", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            reporteController.reiniciarReporte();
            areaTexto.setText("Datos reiniciados. Seleccione un reporte para visualizarlo.");
        }
    }

    private void mostrarError(RuntimeException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrar(ReporteController reporteController,
                               InventarioController inventarioController) {
        javax.swing.SwingUtilities.invokeLater(() ->
                new ReporteView(reporteController, inventarioController).setVisible(true));
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new ReporteView().setVisible(true));
    }
}
