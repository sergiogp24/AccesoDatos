package model;

import java.time.LocalDate;

public class Pedido {
    private int id_pedido;
    private LocalDate fecha;
    private int id_libro;
    private int id_cliente;

    public Pedido() {
    }

    public Pedido(int id_pedido, LocalDate fecha, int id_libro, int id_cliente) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.id_libro = id_libro;
        this.id_cliente = id_cliente;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id_pedido=" + id_pedido +
                ", fecha=" + fecha +
                ", id_libro=" + id_libro +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
