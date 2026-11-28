package controlador;

import modelo.Inventario;
import modelo.Reporte;

/** Coordina la preparación de datos y la generación de reportes. */
public class ReporteController {

    private final GeneradorReportes generador;
    private Reporte reporteActual;

    public ReporteController() {
        this(new GeneradorReportes());
    }

    public ReporteController(GeneradorReportes generador) {
        if (generador == null) {
            throw new IllegalArgumentException("El generador de reportes no puede ser nulo.");
        }
        this.generador = generador;
        this.reporteActual = generador.crearReporte();
    }

    public Reporte getReporteActual() {
        return reporteActual;
    }

    public void registrarVenta(int turno, int prioridad, double monto) {
        reporteActual.registrarVenta(turno, prioridad, monto);
    }

    public void agregarConteoPrioridad(int prioridad, int cantidad) {
        reporteActual.agregarConteoPrioridad(prioridad, cantidad);
    }

    public String generarReporteInventario(Inventario inventario) {
        return generador.generarReporteInventario(inventario);
    }

    public String generarReporteStockCritico(Inventario inventario) {
        return generador.generarReporteStockCritico(inventario);
    }

    public String generarReportePrioridades() {
        return generador.generarReportePrioridades(reporteActual);
    }

    public String generarReporteVentasTurnoPrioridad() {
        return generador.generarReporteVentasTurnoPrioridad(reporteActual);
    }

    public void reiniciarReporte() {
        reporteActual = generador.crearReporte(
                reporteActual.getCantidadPrioridades(), reporteActual.getCantidadTurnos());
    }
}
