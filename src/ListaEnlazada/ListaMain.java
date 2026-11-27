
package ListaEnlazada;

public class ListaMain {
    public static void main(String[] args) {
        Lista<String> lista = new Lista<>();

        lista.agregarAlFinal("Cliente 1");
        lista.agregarAlInicio("Cliente 0");
        lista.agregarEntreNodos("Cliente 1.5", 2);

        System.out.println("Cantidad de elementos: " + lista.cantidadNodos());
        System.out.println("Primer elemento: " + lista.mostrarPrimerElemento());
        System.out.println("Ultimo elemento: " + lista.mostrarUltimoElemento());
        System.out.println("\nContenido de la lista:\n" + lista.mostrarLista());

        System.out.println("Buscar 'Cliente 1': " + lista.buscarDato("Cliente 1"));
        lista.eliminar("Cliente 1");
        System.out.println("\nLista luego de eliminar 'Cliente 1':\n" + lista.mostrarLista());
    }
}
