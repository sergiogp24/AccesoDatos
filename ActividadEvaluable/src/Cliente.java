
import java.io.Serializable;



public class Cliente implements Serializable {

    private String nombre;
    private String DNI;

    public Cliente(String nombre, String DNI) {
        this.nombre = nombre;
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDNI() {
        return DNI;
    }

    @Override
    public String toString() {
        return nombre + "(DNI: " + DNI + ")";
    }


}
