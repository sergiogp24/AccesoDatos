package model;

public class Libro {
    private int id_libro;
    private String nombre;
    private int stock;
    private int id_genero;

    public Libro() {
    }

    public Libro(int id_libro, String nombre, int stock, int id_genero) {
        this.id_libro = id_libro;
        this.nombre = nombre;
        this.stock = stock;
        this.id_genero = id_genero;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id_libro=" + id_libro +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", id_genero=" + id_genero +
                '}';
    }
}
