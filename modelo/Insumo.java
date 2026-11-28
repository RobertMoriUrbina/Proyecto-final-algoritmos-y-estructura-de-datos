package modelo;

import java.util.Objects;

/**
 * Insumo administrado por el inventario de Dolce Capriccio.
 * Las cantidades se expresan en la unidad indicada por {@code unidadMedida}.
 */
public class Insumo {

    private final String codigo;
    private String nombre;
    private double cantidad;
    private double stockMinimo;
    private String unidadMedida;

    public Insumo(String codigo, String nombre, double cantidad,
                  double stockMinimo, String unidadMedida) {
        this.codigo = validarTexto(codigo, "El código");
        setNombre(nombre);
        setCantidad(cantidad);
        setStockMinimo(stockMinimo);
        setUnidadMedida(unidadMedida);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre");
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = validarCantidad(cantidad, "La cantidad");
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = validarCantidad(stockMinimo, "El stock mínimo");
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = validarTexto(unidadMedida, "La unidad de medida");
    }

    /** Aumenta el stock; el incremento debe ser finito y mayor que cero. */
    public void agregarCantidad(double incremento) {
        validarIncremento(incremento);
        setCantidad(cantidad + incremento);
    }

    /** Reduce el stock sin permitir que quede en negativo. */
    public void retirarCantidad(double decremento) {
        validarIncremento(decremento);
        if (decremento > cantidad) {
            throw new IllegalStateException("No hay suficiente stock de " + nombre + ".");
        }
        setCantidad(cantidad - decremento);
    }

    /** El stock se considera crítico cuando alcanza o queda por debajo del mínimo. */
    public boolean tieneStockCritico() {
        return cantidad <= stockMinimo;
    }

    private static void validarIncremento(double valor) {
        if (!Double.isFinite(valor) || valor <= 0) {
            throw new IllegalArgumentException("La cantidad de la operación debe ser finita y mayor que cero.");
        }
    }

    private static double validarCantidad(double valor, String campo) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException(campo + " debe ser finita y no negativa.");
        }
        return valor;
    }

    private static String validarTexto(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " no puede ser nulo.");
        String limpio = valor.trim();
        if (limpio.isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
        return limpio;
    }

    @Override
    public String toString() {
        return "Insumo{" + "codigo='" + codigo + '\'' + ", nombre='" + nombre + '\''
                + ", cantidad=" + cantidad + ' ' + unidadMedida
                + ", stockMinimo=" + stockMinimo + ' ' + unidadMedida + '}';
    }
}
