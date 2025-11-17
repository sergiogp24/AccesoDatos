package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static config.DatabaseConfigPool.getConexion;

public class ConsultasDAO {
    Scanner sc = new Scanner(System.in);
    public void listaCliente() {
        try (Connection con = getConexion()) {
            String sql = "Select * from cliente";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            System.out.println("\n=== Clientes ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Nombre: %s  ",
                        rs.getInt("id_cliente"), rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    public void listaPedido() {
        try (Connection con = getConexion()) {
            String sql = "Select * from pedido";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            System.out.println("\n=== Pedidos ===");
            while (rs.next()) {
                System.out.printf("ID: %d | Fecha: %s | ID_Libro:%d| ID_Cliente:%d ",
                        rs.getInt("id_pedido"), rs.getDate("fecha"), rs.getInt("id_libro"), rs.getInt("id_cliente"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    public void listaVentas() {
        try (Connection con = getConexion()) {
            String sql = "Select * from venta";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            System.out.println("\n=== Ventas ===");
            while (rs.next()) {
                System.out.printf("ID_Venta: %d | Precio: %s | ID_Pedido:%d ",
                        rs.getInt("id_venta"), rs.getBigDecimal("precio"), rs.getInt("id_pedido"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage());
        }
    }


}
