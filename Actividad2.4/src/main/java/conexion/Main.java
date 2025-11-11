package conexion;

public class Main {
    public static void main(String[] args) {
        Consultas consultas = new Consultas();
        consultas.listaEmpleados();
        consultas.ResultadoMetaData();
        consultas.DataBaseMetaData();

    }
}
