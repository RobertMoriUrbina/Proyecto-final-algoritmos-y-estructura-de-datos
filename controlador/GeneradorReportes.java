package controlador;

import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Insumo;
import modelo.Inventario;
import modelo.Reporte;

/** Construye reportes legibles a partir del inventario y los datos agregados. */
public class GeneradorReportes {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Reporte crearReporte() {
        return new Reporte();
    }

    public Reporte crearReporte(int cantidadPrioridades, int cantidadTurnos) {
        return new Reporte(cantidadPrioridades, cantidadTurnos);
    }

    public String generarReporteInventario(Inventario inventario) {
        if (inventario == null) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        StringBuilder reporte = new StringBuilder("REPORTE DE INVENTARIO\n");
        reporte.append("Insumos registrados: ").append(inventario.getCantidadInsumos()).append('\n');
        reporte.append("Código | Insumo | Cantidad | Stock mínimo | Estado\n");
        reporte.append("--------------------------------------------------\n");
        for (Insumo insumo : inventario.listarInsumos()) {
            reporte.append(insumo.getCodigo()).append(" | ")
                    .append(insumo.getNombre()).append(" | ")
                    .append(insumo.getCantidad()).append(' ').append(insumo.getUnidadMedida()).append(" | ")
                    .append(insumo.getStockMinimo()).append(' ').append(insumo.getUnidadMedida()).append(" | ")
                    .append(insumo.tieneStockCritico() ? "CRÍTICO" : "Normal").append('\n');
        }
        return reporte.toString();
    }

    public String generarReporteStockCritico(Inventario inventario) {
        if (inventario == null) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        List<Insumo> insumos = inventario.listarInsumosStockCritico();
        StringBuilder reporte = new StringBuilder("REPORTE DE STOCK CRÍTICO\n");
        reporte.append("Insumos que requieren reposición: ").append(insumos.size()).append('\n');
        reporte.append("Código | Insumo | Disponible | Mínimo\n");
        reporte.append("--------------------------------------\n");
        for (Insumo insumo : insumos) {
            reporte.append(insumo.getCodigo()).append(" | ")
                    .append(insumo.getNombre()).append(" | ")
                    .append(insumo.getCantidad()).append(' ').append(insumo.getUnidadMedida()).append(" | ")
                    .append(insumo.getStockMinimo()).append(' ').append(insumo.getUnidadMedida()).append('\n');
        }
        return reporte.toString();
    }

    public String generarReportePrioridades(Reporte datos) {
        validarReporte(datos);
        int[] conteo = datos.getConteoPrioridad();
        StringBuilder salida = new StringBuilder("REPORTE POR PRIORIDAD\n");
        salida.append("Generado: ").append(datos.getFechaGeneracion().format(FORMATO_FECHA)).append('\n');
        for (int prioridad = 0; prioridad < conteo.length; prioridad++) {
            salida.append("Prioridad ").append(prioridad + 1).append(": ")
                    .append(conteo[prioridad]).append(" registro(s)\n");
        }
        salida.append("Total: ").append(sumar(conteo)).append(" registro(s)\n");
        return salida.toString();
    }

    public String generarReporteVentasTurnoPrioridad(Reporte datos) {
        validarReporte(datos);
        double[][] ventas = datos.getVentasTurnoPrioridad();
        StringBuilder salida = new StringBuilder("VENTAS POR TURNO Y PRIORIDAD\n");
        salida.append("Generado: ").append(datos.getFechaGeneracion().format(FORMATO_FECHA)).append('\n');
        salida.append("Turno/ Prioridad");
        for (int prioridad = 0; prioridad < datos.getCantidadPrioridades(); prioridad++) {
            salida.append(" | P").append(prioridad + 1);
        }
        salida.append(" | Total\n");
        for (int turno = 0; turno < ventas.length; turno++) {
            double totalTurno = 0;
            salida.append("Turno ").append(turno + 1);
            for (double venta : ventas[turno]) {
                salida.append(" | ").append(String.format("%.2f", venta));
                totalTurno += venta;
            }
            salida.append(" | ").append(String.format("%.2f", totalTurno)).append('\n');
        }
        salida.append("Total general: ").append(String.format("%.2f", datos.getTotalVentas())).append('\n');
        return salida.toString();
    }

    private static int sumar(int[] valores) {
        int total = 0;
        for (int valor : valores) {
            total = Math.addExact(total, valor);
        }
        return total;
    }

    private static void validarReporte(Reporte datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del reporte no pueden ser nulos.");
        }
    }
}
