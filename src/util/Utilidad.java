package util;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Utilidad {


    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean esCantidadValida(int cantidad) {
        return cantidad > 0;
    }

    public static String formatearMoneda(double monto) {
        return String.format("S/ %.2f", monto);
    }


    public static double[] clonarArreglo(double[] origen) {
        if (origen == null) {
            return new double[0];
        }
        double[] copia = new double[origen.length];
        System.arraycopy(origen, 0, copia, 0, origen.length);
        return copia;
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            try {

                String entrada = sc.nextLine();


                numero = Integer.parseInt(entrada);
                valido = true;
            } catch (NumberFormatException e) {

                System.out.println("Error: Ingresaste simbolos, letras o lo dejaste vacio. Ingresa un número valido.");

            }
        }
        return numero;
    }


    public static double leerDecimal(Scanner sc, String mensaje) {
        double numero = 0.0;
        boolean valido = false;

        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = sc.nextDouble();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingresaste letras o simbolos. Por favor, usa el punto (.) para los decimales.");
                sc.nextLine();
            }
        }
        return numero;
    }


    public static String leerTexto(Scanner sc, String mensaje) {
        String texto = "";
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            try {
                texto = sc.nextLine();

                if (esTextoValido(texto)) {
                    valido = true;
                } else {
                    System.out.println("Error: El campo no puede estar vacío. Intenta de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado al leer el texto: " + e.getMessage());
                sc.nextLine();
            }
        }
        return texto;
    }
}