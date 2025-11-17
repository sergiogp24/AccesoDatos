package service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import static config.DatabaseConfigPool.getConexion;

public class Procedimiento {
    Scanner sc = new Scanner(System.in);
    public int actualizarStockLibro(String nombre, int stock) {
        try (Connection con = getConexion(); // Conecta con la DB
             CallableStatement cstmt = con.prepareCall("{call actualizar_stock(?, ?, ?)}")) { // Prepara el SP

            cstmt.setString(1, nombre);
            cstmt.setInt(2, stock);
            cstmt.registerOutParameter(3, Types.INTEGER);

            cstmt.execute(); // Ejecuta el SP

            return cstmt.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace(); // Si algo falla, lo imprime
            return -1; // Y devuelve -1 para indicar que hubo un error
        }
    }


}
