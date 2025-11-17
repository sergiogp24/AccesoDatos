package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static config.DatabaseConfigPool.getConexion;

public class Transaccion {


    public boolean transferirStock(int proyectoorigen, int nombreDestino, int stock) {
        Connection con = null;
        try {
            con = getConexion(); // conectamos con la DB
            con.setAutoCommit(false); // apagamos commits automáticos, que vamos a controlar la movida

            // restamos el dinero del proyecto origen
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE libro SET stock = stock - ? WHERE id_libro = ?")) {
                ps.setInt(1, stock);
                ps.setInt(2, proyectoorigen);
                ps.executeUpdate();
            }

            // sumamos el dinero al proyecto destino
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE libro SET stock = stock + ? WHERE id_libro = ?")) {
                ps.setInt(1, stock);
                ps.setInt(2, nombreDestino);
                ps.executeUpdate();
            }

            con.commit(); // confirmamos todo
            System.out.println("Transferencia de stock completada correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error en transferencia: " + e.getMessage());
            try {
                if (con != null) con.rollback(); // si algo falla, devolvemos todo atrás
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        }
    }
}

