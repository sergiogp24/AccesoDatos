package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import static config.DatabaseConfigPool.getConexion;

public class LibroDAO {
    Scanner sc = new Scanner(System.in);
// Crear Libro
    public void creaLibro() {
        try (Connection con = getConexion()) {

            // Pido los datos al usuario
            System.out.println("Ingrese el nombre del Libro: ");
            String nombre = sc.nextLine();
            System.out.println("Ingrese el stock del libro: ");
            int stock = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingrese id del genero que quieres relacionarlo(1.Poesia, 2.Novela, 3.Historia, 4.Cuento infantil): ");
            int id_genero = sc.nextInt();
            sc.nextLine();


            // Preparo la consulta SQL para insertar los datos
            String sql = "INSERT INTO libro (nombre, stock, id_genero) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            // Envío los valores a la consulta
            ps.setString(1, nombre);
            ps.setInt(2, stock);
            ps.setInt(3, id_genero);
            // Ejecuto y muestro cuántas filas se insertaron
            int rs = ps.executeUpdate();
            System.out.println("Libro insertado con éxito. Filas afectadas: " + rs);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
    public void listaLibro() {
        try (Connection con = getConexion()) {
            String sql = "Select * from libro";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            System.out.println("\n=== Libros ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Stock:%d | ID_Genero:%d ",
                        rs.getInt("id_libro"), rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getInt("id_genero"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    // Actualiza los datos de un libro
    public void actualizarLibro() {
        try (Connection con = getConexion()) {

            // Pido los nuevos datos
            System.out.println("Introduce el ID del libro que deseas editar:");
            int idEditar = sc.nextInt();
            sc.nextLine();

            System.out.println("Introduce el nuevo nombre del libro:");
            String nombre = sc.nextLine();

            System.out.println("Introduce el stock :");
            int stock = sc.nextInt();
            sc.nextLine();

            System.out.println("Introduce id del genero que quieres relacionarlo(1.Poesia, 2.Novela, 3.Historia, 4.Cuento infantil):");
           int id_genero = sc.nextInt();
            sc.nextLine();

            // Sentencia SQL para actualizar
            String sql = "UPDATE libro SET nombre = ?,stock = ?, id_genero = ? WHERE id_libro = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            // Asigno los datos nuevos
            ps.setString(1, nombre);
            ps.setInt(2, stock);
            ps.setInt(3, id_genero);
            ps.setInt(4, idEditar);

            int rs = ps.executeUpdate();

            // Compruebo si se actualizó realmente
            if (rs > 0) {
                System.out.println("Proyecto actualizado correctamente.");

            } else {
                System.out.println("No se encontró ningún libro con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());

        }
    }
    // Borra un libro por su ID
    public void eliminarLibro(int id) {
        try (Connection con = getConexion()) {

            String sql = "DELETE FROM libro WHERE id_libro = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rs = ps.executeUpdate();

            // Si rs > 0, significa que sí se borró algo
            if (rs > 0) {
                System.out.println("Libro eliminado correctamente.");

            } else {
                System.out.println("No se encontró ningún libro con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}


