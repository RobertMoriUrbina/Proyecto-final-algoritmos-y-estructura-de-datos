package modelo;

import estructuras.ArbolInventario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Coordina las operaciones y consultas del inventario de insumos. */
public class Inventario {

    private final ArbolInventario arbol;

    public Inventario() {
        this.arbol = new ArbolInventario();
    }

    /** Agrega un insumo; el código debe ser único. */
    public void agregarInsumo(Insumo insumo) {
        arbol.insertar(insumo);
    }

    public Insumo buscarInsumo(String codigo) {
        return arbol.buscar(codigo);
    }

    /** Elimina y devuelve el insumo, o null si el código no está registrado. */
    public Insumo eliminarInsumo(String codigo) {
        return arbol.eliminar(codigo);
    }

    public void aumentarStock(String codigo, double cantidad) {
        obtenerInsumo(codigo).agregarCantidad(cantidad);
    }

    public void disminuirStock(String codigo, double cantidad) {
        obtenerInsumo(codigo).retirarCantidad(cantidad);
    }

    /** Devuelve una instantánea inmodificable, ordenada por código. */
    public List<Insumo> listarInsumos() {
        return arbol.listarEnOrden();
    }

    /** Devuelve los insumos cuyo stock es igual o inferior al mínimo configurado. */
    public List<Insumo> listarInsumosStockCritico() {
        List<Insumo> criticos = new ArrayList<>();
        for (Insumo insumo : arbol.listarEnOrden()) {
            if (insumo.tieneStockCritico()) {
                criticos.add(insumo);
            }
        }
        return Collections.unmodifiableList(criticos);
    }

    public int getCantidadInsumos() {
        return arbol.getTamanio();
    }

    public boolean estaVacio() {
        return arbol.estaVacio();
    }

    private Insumo obtenerInsumo(String codigo) {
        Insumo insumo = arbol.buscar(codigo);
        if (insumo == null) {
            throw new IllegalArgumentException("No existe un insumo con código " + codigo + ".");
        }
        return insumo;
    }
}
