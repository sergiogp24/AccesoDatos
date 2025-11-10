package conexion;

import java.io.InputStream; // Para leer archivos desde el classpath
import java.util.Properties; // Para manejar archivos de propiedades (.properties)

public class Utiles {
    // Variables estáticas (no se usan realmente en tu método actual)
    private static String url;
    private static String user;
    private static String password;

    // Método que devuelve un arreglo con los datos de conexión
    public static String[] llamarDatos() {
        String[] parametros; // Arreglo que se retornará
        Properties props = new Properties(); // Creamos un objeto Properties para leer el archivo

        try (InputStream input = Utiles.class.getClassLoader().getResourceAsStream("db.properties")) {
            // Buscamos el archivo db.properties en el classpath
            if (input == null) {
                System.err.println("No se encontró el archivo db.properties");
            }

            props.load(input); // Cargamos las propiedades desde el archivo
        } catch (Exception e) {
            e.printStackTrace(); // Mostramos el error si ocurre
        }

        // Obtenemos los valores de las propiedades
        String url = props.getProperty("db.url");       // URL de la base de datos
        String user = props.getProperty("db.user");     // Usuario de la base de datos
        String password = props.getProperty("db.password"); // Contraseña de la base de datos

        parametros = new String[]{url, user, password}; // Guardamos los valores en un arreglo
        return parametros; // Retornamos el arreglo
    }
}
