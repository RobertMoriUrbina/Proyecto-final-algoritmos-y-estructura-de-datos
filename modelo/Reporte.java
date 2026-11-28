package modelo;

import java.time.LocalDateTime;

/**
 * Datos agregados para reportes de prioridad y ventas por turno.
 * Los índices de prioridad y turno son base cero.
 */
public class Reporte {

    public static final int PRIORIDADES_PREDETERMINADAS = 3;
    public static final int TURNOS_PREDETERMINADOS = 3;

    private final int[] conteoPrioridad;
    private final double[][] ventasTurnoPrioridad;
    private final LocalDateTime fechaGeneracion;

    public Reporte() {
        this(PRIORIDADES_PREDETERMINADAS, TURNOS_PREDETERMINADOS);
    }

    public Reporte(int cantidadPrioridades, int cantidadTurnos) {
        if (cantidadPrioridades <= 0 || cantidadTurnos <= 0) {
            throw new IllegalArgumentException("Debe existir al menos una prioridad y un turno.");
        }
        conteoPrioridad = new int[cantidadPrioridades];
        ventasTurnoPrioridad = new double[cantidadTurnos][cantidadPrioridades];
        fechaGeneracion = LocalDateTime.now();
    }

    /** Registra una cantidad de elementos atendidos en una prioridad. */
    public void agregarConteoPrioridad(int prioridad, int cantidad) {
        validarPrioridad(prioridad);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        conteoPrioridad[prioridad] = Math.addExact(conteoPrioridad[prioridad], cantidad);
    }

    /** Registra una venta en el turno y prioridad indicados. */
    public void registrarVenta(int turno, int prioridad, double monto) {
        validarTurno(turno);
        validarPrioridad(prioridad);
        if (!Double.isFinite(monto) || monto < 0) {
            throw new IllegalArgumentException("El monto debe ser finito y no negativo.");
        }
        ventasTurnoPrioridad[turno][prioridad] += monto;
        if (!Double.isFinite(ventasTurnoPrioridad[turno][prioridad])) {
            ventasTurnoPrioridad[turno][prioridad] -= monto;
            throw new ArithmeticException("El acumulado de ventas excede el rango permitido.");
        }
        conteoPrioridad[prioridad] = Math.addExact(conteoPrioridad[prioridad], 1);
    }

    public int[] getConteoPrioridad() {
        return conteoPrioridad.clone();
    }

    public double[][] getVentasTurnoPrioridad() {
        double[][] copia = new double[ventasTurnoPrioridad.length][];
        for (int i = 0; i < ventasTurnoPrioridad.length; i++) {
            copia[i] = ventasTurnoPrioridad[i].clone();
        }
        return copia;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public int getCantidadPrioridades() {
        return conteoPrioridad.length;
    }

    public int getCantidadTurnos() {
        return ventasTurnoPrioridad.length;
    }

    public double getTotalVentas() {
        double total = 0;
        for (double[] ventasTurno : ventasTurnoPrioridad) {
            for (double venta : ventasTurno) {
                total += venta;
            }
        }
        return total;
    }

    private void validarPrioridad(int prioridad) {
        if (prioridad < 0 || prioridad >= conteoPrioridad.length) {
            throw new IndexOutOfBoundsException("Índice de prioridad fuera de rango: " + prioridad);
        }
    }

    private void validarTurno(int turno) {
        if (turno < 0 || turno >= ventasTurnoPrioridad.length) {
            throw new IndexOutOfBoundsException("Índice de turno fuera de rango: " + turno);
        }
    }
}
