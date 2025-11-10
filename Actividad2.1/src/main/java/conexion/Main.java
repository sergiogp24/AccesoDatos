package conexion;

public class Main {
    public static void main(String[] args) {
        Consultas c = new Consultas();
        c.listaEmpleados();
        c.buscarEmpleadosId(1);
        c.obtenerEmpleados(2);
    }
}
