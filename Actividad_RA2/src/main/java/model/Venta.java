package model;

import java.math.BigDecimal;

public class Venta {
    private int id_venta;
    private BigDecimal precio;
    private int id_pedido;

    public Venta() {
    }

    public Venta(int id_venta, BigDecimal precio, int id_pedido) {
        this.id_venta = id_venta;
        this.precio = precio;
        this.id_pedido = id_pedido;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id_venta=" + id_venta +
                ", precio=" + precio +
                ", id_pedido=" + id_pedido +
                '}';
    }
}
