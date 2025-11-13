package conexion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Consultas c = new Consultas();

        int opcion;

        do {
            System.out.println("\n============================================");
            System.out.println("Menu  de Empresa");
            System.out.println("\n============================================");
            System.out.println("1. Insertar un empleado: ");
            System.out.println("2. Insertar un proyecto: ");
            System.out.println("3. Ver los empleados: ");
            System.out.println("4. Ver los proyectos: ");
            System.out.println("5. Asignar un empleado al proyecto: ");
            System.out.println("6. Realiza la transaccion: ");
            System.out.println("7. Salir del programa: ");
            System.out.print("Seleccione una opción: ");

            while (!sc.hasNextInt()) {

                System.out.println("Introduce un numero valido");

                sc.next();
                System.out.println("Seleccione una opcion:");
            }
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    c.InsertarDatosEmpleado();
                    break;
                case 2:
                    c.InsertarDatosProyectos();
                    break;
                case 3:
                    c.listaEmpleados();
                    break;
                case 4:
                    c.listaProyectos();
                    break;
                case 5:
                    c.LlamarProcedimiento();
                    break;
                case 6:
                    c.Transaccion();
                    break;
                case 7:
                    System.out.println("Saliendo del programa........");
                    break;

                default:
                    System.out.println("Opción no válida. Intentelo de nuevo.");

            }
        }while (opcion != 7) ;

            sc.close();
        }
}
