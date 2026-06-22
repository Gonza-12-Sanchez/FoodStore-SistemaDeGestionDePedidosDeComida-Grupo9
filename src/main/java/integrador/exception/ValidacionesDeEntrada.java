package integrador.exception;

import java.util.Scanner;

public class ValidacionesDeEntrada {
    //Dicha clase contiene las validaciones de entrada necesarias para que el codigo sea correcto y eficiente
    //Atributo
    private static Scanner scanner = new Scanner(System.in);

    public static int ingresarEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Ingrese un número entero válido.");
            }
        }
    }

    public static int ingresarEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = ingresarEntero(mensaje);
            if (valor < 0) System.out.println("[ERROR] El valor debe ser mayor o igual a 0.");
        } while (valor < 0);
        return valor;
    }

    public static double ingresarDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Ingrese un número decimal válido.");
            }
        }
    }

    public static double ingresarDoublePositivo(String mensaje) {
        double valor;
        do {
            valor = ingresarDouble(mensaje);
            if (valor < 0) System.out.println("[ERROR] El valor debe ser mayor o igual a 0.");
        } while (valor < 0);
        return valor;
    }

    public static String ingresarOpcionNoVacia(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("[ERROR] No puede estar vacio.");
        }
    }

    public static String ingresarOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
