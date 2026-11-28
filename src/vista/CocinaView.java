package vista;

import controlador.PedidoController;
import estructuras.ListaEnlazada;
import estructuras.NodoLista;
import modelo.Comanda;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class CocinaView extends JFrame {
    private JLabel lblPoliticaAtencion;
    private JLabel lblSiguientePedido;
    private JTable tablaColaPedidos;
    private JTable tablaComandas;
    private DefaultTableModel modeloCola;
    private DefaultTableModel modeloComandas;
    private JButton btnIniciarPreparacion;
    private JButton btnMarcarListo;
    private PedidoController controller;

    private final Color colorAzulOscuro = new Color(16, 43, 83);
    private final Color colorMarron = new Color(139, 69, 19);
    private final Color colorFondoClaro = new Color(245, 245, 245);
    private final Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fuenteTexto = new Font("Segoe UI", Font.PLAIN, 13);

    public CocinaView(PedidoController controller) {
        this.controller = controller;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Cocina - Comandas");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelMenu.setBackground(colorAzulOscuro);

        String[] opciones = {"Pedidos", "Cocina", "Inventario", "Productos", "Clientes", "Reportes"};
        for (String op : opciones) {
            JLabel lbl = new JLabel(op);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(fuenteTexto);
            if (op.equals("Cocina")) {
                lbl.setOpaque(true);
                lbl.setBackground(colorMarron);
                lbl.setBorder(new EmptyBorder(5, 10, 5, 10));
            }
            panelMenu.add(lbl);
        }

        lblPoliticaAtencion = new JLabel(
                "Política de atención: Prioridad (ALTA > MEDIA > BAJA)  |  Desempate: FIFO por orden de llegada."
        );
        lblPoliticaAtencion.setFont(fuenteTexto);
        lblPoliticaAtencion.setBorder(new EmptyBorder(10, 15, 10, 15));
        lblPoliticaAtencion.setOpaque(true);
        lblPoliticaAtencion.setBackground(colorFondoClaro);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelMenu, BorderLayout.NORTH);
        panelNorte.add(lblPoliticaAtencion, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelFrente = new JPanel(new BorderLayout(10, 10));
        panelFrente.setBackground(Color.WHITE);
        panelFrente.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 15, 10, 15),
                new LineBorder(new Color(200, 200, 200), 1)
        ));

        lblSiguientePedido = new JLabel("Sin pedidos en cola.");
        lblSiguientePedido.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBackground(Color.WHITE);

        btnIniciarPreparacion = new JButton("Iniciar preparación");
        btnIniciarPreparacion.setBackground(colorAzulOscuro);
        btnIniciarPreparacion.setForeground(Color.WHITE);
        btnIniciarPreparacion.setFont(fuenteTitulo);
        btnIniciarPreparacion.setFocusPainted(false);

        btnMarcarListo = new JButton("Marcar listo");
        btnMarcarListo.setBackground(new Color(34, 139, 34));
        btnMarcarListo.setForeground(Color.WHITE);
        btnMarcarListo.setFont(fuenteTitulo);
        btnMarcarListo.setFocusPainted(false);

        panelBotones.add(btnIniciarPreparacion);
        panelBotones.add(btnMarcarListo);

        panelFrente.add(lblSiguientePedido, BorderLayout.CENTER);
        panelFrente.add(panelBotones, BorderLayout.SOUTH);

        modeloCola = new DefaultTableModel(new String[]{"Cola de pedidos", "Prioridad"}, 0);
        tablaColaPedidos = new JTable(modeloCola);
        estilizarTabla(tablaColaPedidos);

        modeloComandas = new DefaultTableModel(new String[]{"Comanda", "Hora ingreso", "Estado"}, 0);
        tablaComandas = new JTable(modeloComandas);
        estilizarTabla(tablaComandas);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblEstadoTitulo = new JLabel("Estado de comandas");
        lblEstadoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstadoTitulo.setForeground(colorAzulOscuro);
        lblEstadoTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));

        panelInferior.add(lblEstadoTitulo, BorderLayout.NORTH);
        panelInferior.add(new JScrollPane(tablaComandas), BorderLayout.CENTER);

        JSplitPane splitSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelFrente, new JScrollPane(tablaColaPedidos));
        splitSuperior.setDividerLocation(450);
        splitSuperior.setResizeWeight(0.5);
        splitSuperior.setBorder(null);

        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitSuperior, panelInferior);
        splitVertical.setDividerLocation(250);
        splitVertical.setBorder(null);

        add(splitVertical, BorderLayout.CENTER);

        btnIniciarPreparacion.addActionListener(e -> {
            Pedido atendido = controller.atenderSiguientePedido();
            if (atendido != null) {
                JOptionPane.showMessageDialog(this, "Pedido #" + atendido.getId() + " en preparación.");
                refrescarVista();
            } else {
                JOptionPane.showMessageDialog(this, "No hay pedidos en cola.");
            }
        });

        btnMarcarListo.addActionListener(e -> {
            int fila = tablaComandas.getSelectedRow();
            if (fila >= 0) {
                JOptionPane.showMessageDialog(this, "Comanda marcada como LISTA.");
                refrescarVista();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una comanda de la tabla de abajo.");
            }
        });
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setGridColor(new Color(220, 220, 220));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(colorAzulOscuro);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBorder(new LineBorder(colorAzulOscuro));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void refrescarVista() {
        mostrarPedido(controller.verSiguientePedido());
        actualizarCola(controller.listarColaPedidos());
        actualizarComandas(controller.listarComandas());
    }

    public void mostrar() {
        refrescarVista();
        setVisible(true);
    }

    public void mostrarPedido(Pedido pedido) {
        if (pedido == null) {
            lblSiguientePedido.setText("<html><h2>Sin pedidos en cola.</h2></html>");
        } else {
            lblSiguientePedido.setText(
                    "<html>" +
                            "<div style='margin-bottom: 10px;'><font color='#8B4513' size='5'><b>Siguiente pedido</b></font></div>" +
                            "<div style='margin-bottom: 5px;'><font size='4'>Pedido <b>#" + pedido.getId() + "</b> - Prioridad: <b>" + pedido.getPrioridad() + "</b></font></div>" +
                            "<div><font size='3'>Ingreso: " + pedido.getFecha().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "</font></div>" +
                            "</html>"
            );
        }
    }

    public void actualizarCola(ListaEnlazada<Pedido> cola) {
        modeloCola.setRowCount(0);
        NodoLista<Pedido> aux = cola.getInicio();
        while (aux != null) {
            Pedido p = aux.getDato();
            modeloCola.addRow(new Object[]{"#" + p.getId(), p.getPrioridad()});
            aux = aux.getSiguiente();
        }
    }

    public void actualizarComandas(ListaEnlazada<Comanda> comandas) {
        modeloComandas.setRowCount(0);
        NodoLista<Comanda> aux = comandas.getInicio();
        while (aux != null) {
            Comanda c = aux.getDato();
            modeloComandas.addRow(new Object[]{
                    "#" + c.getId(),
                    c.getHoraIngreso().format(DateTimeFormatter.ofPattern("HH:mm")),
                    c.getEstado()
            });
            aux = aux.getSiguiente();
        }
    }
}