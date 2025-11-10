package conexion;

import java.sql.*;
import static conexion.Utiles.*;

public class Consultas {
    String[] prop = llamarDatos();
    String url = prop[0];
    String user = prop[1];
    String password = prop[2];

    public void listaEmpleados() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String linea = "Select * from empleados";
            Statement st = con.prepareStatement(linea);
            ResultSet rs = st.executeQuery(linea);
            System.out.println("\n=== EMPLEADOS ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void buscarEmpleadosId(int id) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String linea = "SELECT * FROM empleados WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(linea);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n-----------EMPLEADOS------------");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }

    }

    public void obtenerEmpleados(int id) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String linea = "{call obtener_empleado(?)}";
            CallableStatement cs = con.prepareCall(linea);
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            System.out.println("\n-----------EMPLEADOS con CallableStatement------------");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s",
                        rs.getInt("id"), rs.getString("nombre"));
            }

        } catch (Exception e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());


        }
    }
}
