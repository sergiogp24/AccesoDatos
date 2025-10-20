
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Movimiento implements Serializable {
    private LocalDateTime fecha;
    private double cantidad;
    private String tipo; // "ingreso" o "retiro"

    public Movimiento(double cantidad, String tipo) {
        this.fecha = LocalDateTime.now();
        this.cantidad = cantidad;
        this.tipo = tipo;

    }
    public double getCantidad() {
        return cantidad;
    }
    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Movimiento{" +
                "fecha=" + fecha.format(formatter) +
                ", cantidad=" + cantidad +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
