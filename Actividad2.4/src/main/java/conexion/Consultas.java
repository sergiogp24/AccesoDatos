package conexion;
// Este paquete se llama “conexion” y aquí metemos todo lo relacionado con conectarse a la base de datos.

import java.sql.*;
import static conexion.Utiles.*;
// Importamos todo lo necesario para trabajar con bases de datos (JDBC) y los métodos de la clase Utiles.

public class Consultas {
    // Aquí guardamos los datos de conexión que obtenemos del archivo db.properties
    String[] prop = llamarDatos(); // Llamamos a un método que carga los datos de conexión
    String url = prop[0]; // Dirección del servidor o base de datos
    String user = prop[1]; // Usuario de la base de datos
    String password = prop[2]; // Contraseña del usuario

    // =================== MÉTODO 1 ===================
    // Este método lista todos los empleados que hay en la base de datos
    public void listaEmpleados() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            // Nos conectamos a la base de datos usando los datos de conexión
            String linea = "Select * from empleados"; // Creamos la consulta SQL
            Statement st = con.prepareStatement(linea); // Preparamos la consulta (aunque podría usarse createStatement)
            ResultSet rs = st.executeQuery(linea); // Ejecutamos la consulta y guardamos el resultado

            System.out.println("\n=== EMPLEADOS ===");
            // Recorremos los resultados uno a uno (cada fila de la tabla)
            while (rs.next()) {
                // Mostramos los datos por consola de una forma formateada
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }

        } catch (SQLException e) {
            // Si hay algún error al conectar o ejecutar la consulta, lo mostramos
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    // =================== MÉTODO 2 ===================
    // Este método muestra información sobre las columnas de la tabla empleados
    // usando la clase ResultSetMetaData.
    public void ResultadoMetaData() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String linea = "SELECT * FROM empleados "; // Consulta que selecciona todo de empleados
            Statement st = con.createStatement(); // Creamos el statement
            ResultSet rs = st.executeQuery(linea); // Ejecutamos la consulta
            ResultSetMetaData meta = rs.getMetaData(); // Obtenemos los metadatos del resultado
            int numeroColumnas = meta.getColumnCount(); // Contamos cuántas columnas hay

            System.out.println("\n=== METADATOS DE LA TABLA EMPLEADOS ===");
            System.out.println("Número de columnas: " + numeroColumnas);

            // Recorremos las columnas y mostramos su nombre y tipo
            for (int i = 1; i <= numeroColumnas; i++) {
                System.out.println("Columna " + i + ": " +
                        meta.getColumnName(i) +
                        " (" + meta.getColumnTypeName(i) + ")");
            }

        } catch (SQLException ex) {
            // Capturamos cualquier error y lo mostramos
            System.err.println(" Error al conectar a la base de datos: " + ex.getMessage());
        }
    }

    // =================== MÉTODO 3 ===================
    // Este método usa DatabaseMetaData para mostrar información general
    // sobre la base de datos y el driver JDBC que se está usando.
    public void DataBaseMetaData() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            DatabaseMetaData dbmeta = con.getMetaData(); // Obtenemos la información de la base de datos

            System.out.println("\n=== INFORMACIÓN DE LA BASE DE DATOS ===");
            // Mostramos el nombre del producto (por ejemplo MySQL, PostgreSQL, etc.)
            System.out.println("Nombre del producto de base de datos: " + dbmeta.getDatabaseProductName());
            // Mostramos la versión del motor de base de datos
            System.out.println("Versión del motor: " + dbmeta.getDatabaseProductVersion());
            // Mostramos el nombre del driver JDBC que se está usando
            System.out.println("Nombre del driver JDBC: " + dbmeta.getDriverName());

        } catch (SQLException e) {
            // Si algo falla al obtener la información, mostramos el error
            System.err.println("Error al obtener la información de la base de datos: " + e.getMessage());
        }
    }
}



