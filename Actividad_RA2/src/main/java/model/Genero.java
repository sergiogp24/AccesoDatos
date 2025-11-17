package model;

public class Genero {
    private int id_genero;
    private String tipo;

    public Genero() {
    }

    public Genero(int id_genero, String tipo) {
        this.id_genero = id_genero;
        this.tipo = tipo;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Genero{" +
                "id_genero=" + id_genero +
                ", tipo='" + tipo + '\'' +
                '}';
    }

}
