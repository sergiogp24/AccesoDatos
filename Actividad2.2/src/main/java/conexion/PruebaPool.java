package conexion;

import java.sql.*;

import static conexion.ConexionPool.*;

public class PruebaPool {
    public static void main(String args[]) {

        try ( Connection conexion1 = getConexion();
              Connection conexion2 = getConexion();
              Connection conexion3 = getConexion()){
       String linea = "SELECT * FROM empleados";
            if (conexion1 != null) {
                System.out.printf("Conexion correcta");

                Statement st = conexion1.prepareStatement(linea); // Preparamos la consulta
                ResultSet rs = st.executeQuery(linea); // Ejecutamos la consulta y guardamos el resultado

                System.out.println("\n=== EMPLEADOS ===");
                // Recorremos los resultados fila por fila
                while (rs.next()) {
                    System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                    // Imprimimos los datos de cada empleado
                }
            }
                if (conexion2 != null) {
                    System.out.printf("Conexion correcta");
                    Statement st = conexion2.prepareStatement(linea); // Preparamos la consulta
                    ResultSet rs = st.executeQuery(linea); // Ejecutamos la consulta y guardamos el resultado

                    System.out.println("\n=== EMPLEADOS ===");
                    // Recorremos los resultados fila por fila
                    while (rs.next()) {
                        System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                                rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                        // Imprimimos los datos de cada empleado
                    }
                }
                    if (conexion3 != null) {
                        System.out.printf("Conexion correcta");
                        Statement st = conexion3.prepareStatement(linea); // Preparamos la consulta
                        ResultSet rs = st.executeQuery(linea); // Ejecutamos la consulta y guardamos el resultado

                        System.out.println("\n=== EMPLEADOS ===");
                        // Recorremos los resultados fila por fila
                        while (rs.next()) {
                            System.out.printf("ID: %d | Nombre: %s | Salario: %.2f €%n",
                                    rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
                            // Imprimimos los datos de cada empleado
                        }
                    }

            }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            cerrarPool();
        }
        }
    }