
package conexion;

import java.sql.*;
import java.util.Scanner;

public class PruebaCallable {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/maven_empresa";
        String usuario = "root";
        String password = "root";

        try (Connection con = DriverManager.getConnection(url, usuario, password)) {

            // Pedimos al usuario que introduzca el ID del empleado
            System.out.print("Introduce el ID del empleado: ");
            int idEmpleado = sc.nextInt();

            // Pedimos al usuario que introduzca el incremento salarial
            System.out.print("Introduce el incremento salarial: ");
            double incremento = sc.nextDouble();

            // Creamos el CallableStatement para llamar al procedimiento
            CallableStatement cs = con.prepareCall("{CALL incrementar_salario(?, ?, ?)}");

            // Asignamos valores a los parámetros de entrada
            cs.setInt(1, idEmpleado);
            cs.setDouble(2, incremento);

            // Registramos el parámetro de salida
            cs.registerOutParameter(3, Types.DOUBLE);

            // Ejecutamos el procedimiento
            cs.execute();

            // Obtenemos el nuevo salario
            double nuevoSalario = cs.getDouble(3);

            // Mostramos el resultado
            System.out.println("El nuevo salario del empleado es: " + nuevoSalario + " €");

        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        } finally {
            sc.close(); // Cerramos el Scanner
        }
    }
}

