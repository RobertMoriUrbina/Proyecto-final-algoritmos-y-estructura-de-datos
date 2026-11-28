package vista;

import controlador.GeneradorReportes;
import controlador.InventarioController;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Insumo;

/** Ventana Swing para administrar insumos y su stock. */
public class InventarioView extends JFrame {

    private final InventarioController controlador;
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;
    private final JTextField codigoBuscar;
    private final JTextField codigo;
    private final JTextField nombre;
    private final JTextField cantidad;
    private final JTextField stockMinimo;
    private final JTextField unidad;

    public InventarioView() {
        this(new InventarioController());
    }

    public InventarioView(InventarioController controlador) {
        super("Dolce Capriccio - Inventario");
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador de inventario no puede ser nulo.");
        }
        this.controlador = controlador;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        modeloTabla = new DefaultTableModel(
                new Object[]{"Código", "Insumo", "Cantidad", "Unidad", "Mínimo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        codigoBuscar = new JTextField(12);
        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.setBorder(BorderFactory.createTitledBorder("Buscar insumo"));
        busqueda.add(new JLabel("Código:"));
        busqueda.add(codigoBuscar);
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(event -> buscarInsumo());
        busqueda.add(buscar);
        JButton listar = new JButton("Listar todos");
        listar.addActionListener(event -> cargarInsumos(controlador.obtenerInsumos()));
        busqueda.add(listar);
        JButton criticos = new JButton("Stock crítico");
        criticos.addActionListener(event -> cargarInsumos(controlador.obtenerInsumosStockCritico()));
        busqueda.add(criticos);
        add(busqueda, BorderLayout.NORTH);

        codigo = new JTextField(9);
        nombre = new JTextField(12);
        cantidad = new JTextField(7);
        stockMinimo = new JTextField(7);
        unidad = new JTextField(8);
        JPanel formulario = new JPanel(new GridLayout(2, 5, 6, 4));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar insumo"));
        agregarCampo(formulario, "Código", codigo);
        agregarCampo(formulario, "Nombre", nombre);
        agregarCampo(formulario, "Cantidad", cantidad);
        agregarCampo(formulario, "Stock mínimo", stockMinimo);
        agregarCampo(formulario, "Unidad", unidad);
        JButton registrar = new JButton("Registrar");
        registrar.addActionListener(event -> registrarInsumo());
        formulario.add(registrar);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.setBorder(BorderFactory.createTitledBorder("Reposición o salida de stock"));
        JTextField codigoMovimiento = new JTextField(10);
        JTextField cantidadMovimiento = new JTextField(7);
        acciones.add(new JLabel("Código:"));
        acciones.add(codigoMovimiento);
        acciones.add(new JLabel("Cantidad:"));
        acciones.add(cantidadMovimiento);
        JButton reponer = new JButton("Reponer stock");
        reponer.addActionListener(event -> ejecutarMovimiento(codigoMovimiento, cantidadMovimiento, true));
        acciones.add(reponer);
        JButton retirar = new JButton("Retirar stock");
        retirar.addActionListener(event -> ejecutarMovimiento(codigoMovimiento, cantidadMovimiento, false));
        acciones.add(retirar);

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.add(formulario, BorderLayout.NORTH);
        inferior.add(acciones, BorderLayout.SOUTH);
        add(inferior, BorderLayout.SOUTH);
        cargarInsumos(controlador.obtenerInsumos());
    }

    private static void agregarCampo(JPanel panel, String etiqueta, JTextField campo) {
        JPanel contenedor = new JPanel(new BorderLayout(3, 2));
        contenedor.add(new JLabel(etiqueta), BorderLayout.NORTH);
        contenedor.add(campo, BorderLayout.CENTER);
        panel.add(contenedor);
    }

    private void buscarInsumo() {
        try {
            Insumo insumo = controlador.buscarInsumo(codigoBuscar.getText().trim());
            if (insumo == null) {
                cargarInsumos(List.of());
                mostrarMensaje("No se encontró un insumo con ese código.");
            } else {
                cargarInsumos(List.of(insumo));
            }
        } catch (RuntimeException ex) {
            mostrarError(ex);
        }
    }

    private void registrarInsumo() {
        try {
            Insumo nuevo = new Insumo(codigo.getText(), nombre.getText(),
                    parsearCantidad(cantidad.getText(), "cantidad"),
                    parsearCantidad(stockMinimo.getText(), "stock mínimo"), unidad.getText());
            controlador.registrarInsumo(nuevo);
            cargarInsumos(controlador.obtenerInsumos());
            codigo.setText("");
            nombre.setText("");
            cantidad.setText("");
            stockMinimo.setText("");
            unidad.setText("");
            mostrarMensaje("Insumo registrado correctamente.");
        } catch (RuntimeException ex) {
            mostrarError(ex);
        }
    }

    private void ejecutarMovimiento(JTextField campoCodigo, JTextField campoCantidad, boolean reponer) {
        try {
            double valor = parsearCantidad(campoCantidad.getText(), "cantidad");
            if (reponer) {
                controlador.aumentarStock(campoCodigo.getText().trim(), valor);
            } else {
                controlador.disminuirStock(campoCodigo.getText().trim(), valor);
            }
            cargarInsumos(controlador.obtenerInsumos());
            campoCantidad.setText("");
            mostrarMensaje(reponer ? "Stock repuesto correctamente." : "Stock retirado correctamente.");
        } catch (RuntimeException ex) {
            mostrarError(ex);
        }
    }

    private void cargarInsumos(List<Insumo> insumos) {
        modeloTabla.setRowCount(0);
        for (Insumo insumo : insumos) {
            modeloTabla.addRow(new Object[]{insumo.getCodigo(), insumo.getNombre(),
                insumo.getCantidad(), insumo.getUnidadMedida(), insumo.getStockMinimo(),
                insumo.tieneStockCritico() ? "CRÍTICO" : "Normal"});
        }
    }

    private static double parsearCantidad(String texto, String campo) {
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ingrese un valor numérico válido para " + campo + ".", ex);
        }
    }

    private void mostrarError(RuntimeException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Abre la ventana en el hilo de eventos de Swing. */
    public static void mostrar(InventarioController controlador) {
        javax.swing.SwingUtilities.invokeLater(() -> new InventarioView(controlador).setVisible(true));
    }

    /** Constructor de conveniencia para ejecuciones independientes de esta vista. */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            InventarioView vista = new InventarioView();
            vista.setVisible(true);
        });
    }
}
