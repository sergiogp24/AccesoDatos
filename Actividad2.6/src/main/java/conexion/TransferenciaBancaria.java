package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.sql.SQLException;
import java.util.Scanner;

public class TransferenciaBancaria {

    public static void main(String[] args) {
        // Datos para conectarse a la base de datos
        String url = "jdbc:mysql://localhost:3306/maven_empresa";
        String user = "root";
        String password = "root123";

        // Scanner para que el usuario pueda escribir los montos por consola
        Scanner sc = new Scanner(System.in);

        try (Connection conexion = DriverManager.getConnection(url, user, password)) {
            // Desactivamos el autocommit para manejar manualmente la transacción
            conexion.setAutoCommit(false);
            Savepoint afterRetiro = null; // Este savepoint servirá si algo falla después

            try {
                // Paso 1: retirar de la cuenta 1
                System.out.println("Ingrese el monto a retirar de la cuenta 1:");
                double montoRetiro = sc.nextDouble(); // Leemos el monto que el usuario quiere retirar

                // Consulta SQL para restar dinero de la cuenta 1
                String retirarSQL = "UPDATE cuentas SET saldo = saldo - ? WHERE id = 1";
                try (PreparedStatement pstmtRet = conexion.prepareStatement(retirarSQL)) {
                    pstmtRet.setDouble(1, montoRetiro); // Colocamos el monto dentro del SQL
                    int filas = pstmtRet.executeUpdate(); // Ejecutamos la actualización
                    if (filas != 1) {
                        // Si no se actualizó una fila, algo salió mal
                        throw new SQLException("No se pudo retirar: filas afectadas = " + filas);
                    }
                }

                // Registrar log del primer paso (el retiro)
                String insertLogSQL = "INSERT INTO logs (paso, descripcion, fecha) VALUES (?, ?, NOW())";
                try (PreparedStatement pstmtLog1 = conexion.prepareStatement(insertLogSQL)) {
                    pstmtLog1.setString(1, "RETIRO");
                    pstmtLog1.setString(2, "Retiro en cuenta 1 por monto: " + montoRetiro);
                    int filasLog = pstmtLog1.executeUpdate();
                    if (filasLog != 1) {
                        // Si no se pudo guardar el log, lanzamos error
                        throw new SQLException("No se pudo insertar log del retiro");
                    }
                }

                // Creamos un savepoint para poder mantener el retiro si después falla el depósito
                afterRetiro = conexion.setSavepoint("after_retiro");

                // Paso 2: depositar en la cuenta 2
                System.out.println("Ingrese el monto a depositar en la cuenta 2:");
                double montoDeposito = sc.nextDouble(); // Leemos el monto a depositar

                // Consulta SQL para sumar dinero en la cuenta 2
                String depositarSQL = "UPDATE cuentas SET saldo = saldo + ? WHERE id = 2";
                try (PreparedStatement pstmtDep = conexion.prepareStatement(depositarSQL)) {
                    pstmtDep.setDouble(1, montoDeposito);
                    int filasDep = pstmtDep.executeUpdate();
                    if (filasDep != 1) {
                        // Si no se actualizó una fila, algo falló
                        throw new SQLException("No se pudo depositar: filas afectadas = " + filasDep);
                    }
                }

                // Registrar log del segundo paso (el depósito)
                try (PreparedStatement pstmtLog2 = conexion.prepareStatement(insertLogSQL)) {
                    pstmtLog2.setString(1, "DEPOSITO");
                    pstmtLog2.setString(2, "Deposito en cuenta 2 por monto: " + montoDeposito);
                    int filasLog2 = pstmtLog2.executeUpdate();
                    if (filasLog2 != 1) {
                        throw new SQLException("No se pudo insertar log del deposito");
                    }
                }

                // Si todo salió bien hasta aquí, confirmamos la transacción
                conexion.commit();
                System.out.println("Transferencia completada con éxito.");

            } catch (Exception e) {
                // Si ocurre algún error dentro de los pasos anteriores
                try {
                    if (afterRetiro != null) {
                        // Si ya se había hecho el retiro, volvemos al savepoint
                        conexion.rollback(afterRetiro);
                        // Confirmamos (commit) los cambios hasta el savepoint
                        conexion.commit();
                        System.out.println("Error en el segundo paso o en su registro. Se revirtió hasta el savepoint y se mantuvo el primer paso.");
                    } else {
                        // Si no existía savepoint, revertimos todo lo que se haya hecho
                        conexion.rollback();
                        System.out.println("Error en la transferencia. Se ha revertido toda la transacción.");
                    }
                } catch (SQLException ex) {
                    // Si falla el rollback parcial, intentamos hacer un rollback total
                    try {
                        conexion.rollback();
                        System.out.println("Error al intentar rollback parcial; se revertió toda la transacción.");
                    } catch (SQLException ex2) {
                        // Si también falla el rollback total, mostramos el error
                        System.err.println("Fallo al revertir la transacción: ");
                        ex2.printStackTrace();
                    }
                }
                // Mostramos el error principal para saber qué falló
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // Si no se logra conectar a la base de datos o hay otro error general
            e.printStackTrace();
        } finally {
            // Cerramos el scanner al final para liberar recursos
            sc.close();
        }

    }
}
