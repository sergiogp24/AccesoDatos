package model;

public class Cliente {
    private int id_cliente;
    private String nombre;

    public Cliente() {
    }

    public Cliente(int id_cliente, String nombre) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nombre='" + nombre + '\'' +
                '}';
    }

}
