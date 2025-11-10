package conexion;

import java.sql.*; // Importa clases para la conexión y manejo de bases de datos
import java.util.Scanner; // Importa Scanner para leer datos desde la consola

import static conexion.Utiles.llamarDatos; // Importa método para obtener los datos de conexión

public class Consultas {

    // Obtenemos los datos de conexión a la base de datos
    String[] prop = llamarDatos();
    String url = prop[0];       // URL de la base de datos
    String user = prop[1];      // Usuario de la base de datos
    String password = prop[2];  // Contraseña de la base de datos
    Scanner sc = new Scanner(System.in); // Scanner para leer datos desde la consola

    // Método para insertar un proyecto nuevo en la base de datos
    public void insertarProyectos() {
        try (Connection con = DriverManager.getConnection(url, user, password)) { // Conexión automática cerrada con try-with-resources
            System.out.printf("Ingrese el nombre del proyecto a ingresar: ");
            String nombre = sc.nextLine(); // Leemos el nombre del proyecto
            System.out.println("Ingrese el presupuesto a ingresar: ");
            double presupuesto = sc.nextDouble(); // Leemos el presupuesto
            // Consulta SQL parametrizada para evitar inyección SQL
            String sql = "INSERT INTO proyectos (nombre, presupuesto) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);      // Primer parámetro = nombre
            ps.setDouble(2, presupuesto); // Segundo parámetro = presupuesto
            int rs = ps.executeUpdate();   // Ejecuta la consulta
            if (rs > 0) {                  // Si afecta filas, la inserción fue exitosa
                System.out.println("Proyecto insertado con éxito. Filas afectadas: " + rs);
            } else {
                System.out.println("No se insertó ningún proyecto");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error si algo falla
        }
    }

    // Método para actualizar un proyecto según su ID
    public void actualizarProyectos(int id) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            // Pedimos los nuevos datos del proyecto
            System.out.print("Ingrese el nombre del proyecto a editar: ");
            String nombre = sc.nextLine();

            // Validamos que el presupuesto sea un número válido
            double presupuesto;
            while (true) {
                System.out.print("Ingrese el presupuesto a editar: ");
                if (sc.hasNextDouble()) {
                    presupuesto = sc.nextDouble();
                    sc.nextLine(); // Limpiar buffer
                    break; // Entrada válida, salimos del bucle
                } else {
                    System.out.println("Entrada no válida. Por favor ingrese un número.");
                    sc.nextLine(); // Limpiar buffer para leer de nuevo
                }
            }

            // Consulta SQL de actualización
            String sql = "UPDATE proyectos SET nombre = ?, presupuesto = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);      // Primer parámetro = nombre
            ps.setDouble(2, presupuesto); // Segundo parámetro = presupuesto
            ps.setInt(3, id);             // Tercer parámetro = ID del proyecto

            int rs = ps.executeUpdate();  // Ejecuta la consulta
            if (rs > 0) {
                System.out.println("Proyecto actualizado con éxito. Filas afectadas: " + rs);
            } else {
                System.out.println("No se encontró ningún proyecto con el ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error si algo falla
        }
    }


    // Método para eliminar un proyecto según su ID
    public void eliminarProyectosPorId() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.print("Ingrese el ID del proyecto a eliminar: ");
            int id = sc.nextInt(); // Leemos ID
            sc.nextLine(); // Limpiamos buffer
            // Consulta SQL para eliminar proyecto
            String sql = "DELETE FROM proyectos WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id); // Parámetro = ID
            int rs = ps.executeUpdate(); // Ejecuta la consulta

            if (rs > 0) { // Si afecta filas
                System.out.println("Proyecto eliminado con éxito. Filas afectadas: " + rs);
            } else { // Si no afecta filas
                System.out.println("No se encontró ningún proyecto con el ID: " + id);
            }
        } catch(Exception e) {
            e.printStackTrace(); // Muestra error si falla
        }
    }

    // Método para listar todos los proyectos de la base de datos
    public void listaProyectos() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String linea = "SELECT * FROM proyectos"; // Consulta SQL
            Statement st = con.prepareStatement(linea); // Crear statement
            ResultSet rs = st.executeQuery(linea); // Ejecutar consulta y obtener resultados
            System.out.println("\n=== Proyectos ===");
            while (rs.next()) { // Iteramos todos los resultados
                System.out.printf("ID: %d | Nombre: %s | Presupuesto: %.2f €%n",
                        rs.getInt("id"),       // Columna ID
                        rs.getString("nombre"),// Columna nombre
                        rs.getDouble("presupuesto")); // Columna presupuesto
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
