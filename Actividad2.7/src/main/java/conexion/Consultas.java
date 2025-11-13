package conexion;

import java.sql.*;
import java.util.Scanner;

import static conexion.ConexionPool.*; // Importamos la conexión desde otra clase

public class Consultas {
    Scanner sc = new Scanner(System.in); // Escáner para leer datos desde consola

    // Método para insertar un nuevo empleado en la base de datos
    public void InsertarDatosEmpleado(){
        try (Connection con = getConexion()) { // Obtenemos la conexión desde el pool
            System.out.println("Ingrese el nombre del empleado: ");
            String nombre = sc.nextLine();
            System.out.println("Ingresa el salario del empleado: ");
            double salario = sc.nextDouble();
            sc.nextLine(); // Consumimos el salto de línea

            // Sentencia SQL con parámetros
            String sql  = "INSERT INTO empleados (nombre,salario) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setDouble(2,salario);

            // Ejecutamos y mostramos cuántas filas se afectaron
            int rs = ps.executeUpdate();
            System.out.println("Empleado insertado con éxito. Filas afectadas: " + rs);

        } catch (SQLException e) {
            System.err.println("  Error al conectar a la base de datos:" + e.getMessage());
        }
    }

    // Método para insertar un proyecto nuevo
    public void InsertarDatosProyectos(){
        try (Connection con = getConexion()) {
            System.out.println("Ingrese el nombre del Proyecto: ");
            String nombre = sc.nextLine();
            System.out.println("Ingresa el presupuesto del Proyecto: ");
            double presupuesto = sc.nextDouble();
            sc.nextLine();

            // Insertamos el proyecto en la tabla correspondiente
            String sql  = "INSERT INTO proyectos (nombre,presupuesto) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,nombre);
            ps.setDouble(2,presupuesto);

            int rs = ps.executeUpdate();
            System.out.println("Empleado insertado con éxito. Filas afectadas: " + rs); // (Aquí el mensaje debería decir “Proyecto insertado”, pero no lo cambiamos)

        } catch (SQLException e) {
            System.err.println("  Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Muestra todos los empleados que hay en la tabla
    public void listaEmpleados() {
        try (Connection con = getConexion()) {
            String sql = "Select * from empleados";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n=== EMPLEADOS ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Muestra todos los proyectos existentes
    public void listaProyectos() {
        try (Connection con = getConexion()) {
            String sql = "Select * from proyectos";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n=== Proyectos ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Presupuesto: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("presupuesto"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // Llama a un procedimiento almacenado que asigna un empleado a un proyecto
    public void LlamarProcedimiento(){
        try(Connection con = getConexion()){

            System.out.println("\nMete el id del empleado: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Mete el id del proyecto: ");
            int idproyecto = sc.nextInt();
            sc.nextLine();

            // Llamada al procedimiento almacenado con parámetros
            CallableStatement cs = con.prepareCall("{call asignar_empleado_proyecto(? ,?)}");
            cs.setInt(1,id);
            cs.setInt(2,idproyecto);
            cs.execute();
            System.out.println("Empleado asignado al proyecto ");

            // Mostramos todas las asignaciones para comprobar el resultado
            String sql = "SELECT * from asignaciones";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("\n===Asignaciones ===");
            while (rs.next()) {
                System.out.printf("ID asignaciones: %d | ID Empleado: %d | ID PROYECTO : %d| Fecha : %s%n",
                        rs.getInt("id"), rs.getInt("id_empleado"), rs.getInt("id_proyecto"), rs.getString("fecha_asignacion"));
            }
        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }

    }

    // Método que realiza una transacción: aumenta salario de un empleado y reduce presupuesto de un proyecto
    public void Transaccion(){
        try(Connection con = getConexion()){
            System.out.println("Ingrese el id del usuario que quieres aumentar su salario:");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese cuanta cantidad deseas aumentar (Esta cantidad se descontara del proyecto en el que este asignado): ");
            double cantidad = sc.nextDouble();
            sc.nextLine();
            System.out.println("Ingrese el id del proyecto donde disminuira su cantidad: ");
            int id2 = sc.nextInt();
            sc.nextLine();

            // Desactivamos el autocommit para manejar la transacción manualmente
            con.setAutoCommit(false);
            try(PreparedStatement ps1 = con.prepareStatement("Update empleados SET salario = salario + ? WHERE id = ?");
                PreparedStatement ps2 = con.prepareStatement("UPDATE proyectos SET presupuesto = presupuesto - ? WHERE id = ?");) {

                // Aumentamos el salario del empleado
                ps1.setDouble(1, cantidad);
                ps1.setInt(2, id);
                ps1.executeUpdate();

                // Reducimos el presupuesto del proyecto
                ps2.setDouble(1, cantidad);
                ps2.setInt(2, id2);
                ps2.executeUpdate();

                // Si todo va bien, confirmamos la transacción
                con.commit();
                System.out.println("Transferencia realizada con éxito");
            } catch (Exception e) {
                // Si algo falla, revertimos los cambios
                con.rollback();
                System.out.println("Error en la transacción" + e.getMessage());
            }finally {
                // Volvemos a activar el autocommit
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }

    }
}
