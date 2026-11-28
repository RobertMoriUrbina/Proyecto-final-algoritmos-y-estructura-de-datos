package controlador;

import java.util.List;
import modelo.Insumo;
import modelo.Inventario;

/** Conecta las acciones de la interfaz con el modelo de inventario. */
public class InventarioController {

    private final Inventario inventario;

    public InventarioController() {
        this(new Inventario());
    }

    public InventarioController(Inventario inventario) {
        if (inventario == null) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        this.inventario = inventario;
    }

    public void registrarInsumo(Insumo insumo) {
        inventario.agregarInsumo(insumo);
    }

    public Insumo buscarInsumo(String codigo) {
        return inventario.buscarInsumo(codigo);
    }

    public Insumo eliminarInsumo(String codigo) {
        return inventario.eliminarInsumo(codigo);
    }

    public void aumentarStock(String codigo, double cantidad) {
        inventario.aumentarStock(codigo, cantidad);
    }

    public void disminuirStock(String codigo, double cantidad) {
        inventario.disminuirStock(codigo, cantidad);
    }

    public List<Insumo> obtenerInsumos() {
        return inventario.listarInsumos();
    }

    public List<Insumo> obtenerInsumosStockCritico() {
        return inventario.listarInsumosStockCritico();
    }

    public int obtenerCantidadInsumos() {
        return inventario.getCantidadInsumos();
    }

    public Inventario getInventario() {
        return inventario;
    }
}
