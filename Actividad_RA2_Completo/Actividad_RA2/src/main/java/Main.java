import dao.ConsultasDAO;
import dao.LibroDAO;
import service.Procedimiento;
import service.Transaccion;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibroDAO libroDAO = new LibroDAO();
      ConsultasDAO consultasDAO = new ConsultasDAO();
        Transaccion transService = new Transaccion();
        Procedimiento proc = new Procedimiento();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestión de Libros");
            System.out.println("2. Gestión de Otras Consultas");
            System.out.println("3. Transaccion");
            System.out.println("4. Procedimiento");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    // Submenú Empleados con bucle
                    boolean volverEmp = false;
                    while (!volverEmp) {
                        System.out.println("\n--- Gestión de Libros ---");
                        System.out.println("1. Crear libro");
                        System.out.println("2. Listar libros");
                        System.out.println("3. Actualizar libros");
                        System.out.println("4. Eliminar libros");
                        System.out.println("0. Volver");
                        System.out.print("Seleccione una opción: ");
                        int opUsuario = sc.nextInt();
                        sc.nextLine();

                        switch (opUsuario) {
                            case 1:
                                libroDAO.creaLibro();
                                break;
                            case 2:
                                libroDAO.listaLibro();
                                break;
                            case 3: {
                                libroDAO.actualizarLibro();
                                break;
                            }
                            case 4: {
                                System.out.print("Ingrese el ID a eliminar: ");
                                int id = sc.nextInt();
                                sc.nextLine();
                                libroDAO.eliminarLibro(id);
                                break;
                            }
                            case 0:
                                volverEmp = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }
                    break;

                case 2:
                    // Submenú Proyectos con bucle
                    boolean volverPro = false;
                    while (!volverPro) {
                        System.out.println("\n--- Gestión de Consultas ---");
                        System.out.println("1.Listar Clientes");
                        System.out.println("2. Listar pedidos");
                        System.out.println("3. Listar ventas" );
                        System.out.println("0. Volver");
                        System.out.print("Seleccione una opción: ");
                        int opPro = sc.nextInt();
                        sc.nextLine();

                        switch (opPro) {
                            case 1:
                                consultasDAO.listaCliente();
                                break;
                            case 2:
                                consultasDAO.listaPedido();
                                break;
                            case 3: consultasDAO.listaVentas();
                                break;
                            case 0:
                                volverPro = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n--- Transferencia de stock ---");
                    System.out.print("ID libro origen: ");
                    int proyectoorigen = sc.nextInt();
                    System.out.print("ID libro destino: ");
                    int nombreDestino = sc.nextInt();
                    System.out.print("Stock a transferir: ");
                    int stock = sc.nextInt();
                    sc.nextLine();
                    transService.transferirStock(proyectoorigen, nombreDestino, stock);
                    break;

                case 4:
                    // Submenú Procedimientos con bucle
                    boolean volverProc = false;
                    while (!volverProc) {
                        System.out.println("\n--- Procedimiento  ---");
                        System.out.println("1. Actualizar stock de los libros");
                        System.out.println("0. Volver");
                        System.out.print("Seleccione una opción: ");
                        int opProc = sc.nextInt();
                        sc.nextLine();

                        switch (opProc) {
                            case 1: {
                                System.out.print("Nombre: ");
                                String dept = sc.nextLine();
                                System.out.print("stock de aumento: ");
                                int porc = sc.nextInt();
                                sc.nextLine();
                                int filas = proc.actualizarStockLibro(dept, porc);
                                System.out.println("Filas afectadas: " + filas);
                                break;
                            }
                            case 0:
                                volverProc = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }
                    break;

                case 5:
                    salir = true;
                    System.out.println("------------Hasta luego-------------");
                    // Cierre de pool
                    config.DatabaseConfigPool.cerrarPool();
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }

        sc.close();
    }
}