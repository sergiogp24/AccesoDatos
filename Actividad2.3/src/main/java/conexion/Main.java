package conexion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Consultas consultas = new Consultas();
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n==============================");
            System.out.println("     MENÚ DE PROYECTOS");
            System.out.println("==============================");
            System.out.println("1. Insertar proyecto");
            System.out.println("2. Actualizar proyecto");
            System.out.println("3. Eliminar proyecto");
            System.out.println("4. Listar proyectos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // Verificamos que el usuario introduzca un número válido
            while (!sc.hasNextInt()) {
                System.out.print("Por favor ingrese un número válido: ");
                sc.next();
            }

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1: consultas.insertarProyectos();
                break;

                case 2:
                    System.out.print("Ingrese el ID del proyecto a actualizar: ");
                    int idActualizar = sc.nextInt();
                    sc.nextLine(); // limpia buffer después del número\
                    consultas.actualizarProyectos(idActualizar);
                    break;

                case 3:
                    consultas.eliminarProyectosPorId();
                    break;

                case 4:  consultas.listaProyectos();
                    break;

                case 0: System.out.println("Saliendo del programa...");
                break;
                default: System.out.println("Opción no válida. Intente de nuevo.");
                break;

            }

        } while (opcion != 0);


        sc.close();
    }
}
