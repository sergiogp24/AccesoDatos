package conexion;
// Este paquete se llama “conexion”. Aquí vamos a meter todo lo relacionado con la conexión a la base de datos.


import java.io.InputStream;
import java.util.Properties;
//Importamos lo necesario: InputStream para leer archivos y Properties para manejar las propiedades del archivo de configuración.


public class Utiles {
    // Variables donde vamos a guardar los datos de conexión a la base de datos
    private static String url;
    private static String user;
    private static String password;


    // Este método devuelve un array con los datos de conexión (url, usuario y contraseña)
    public static String[] llamarDatos() {
        String [] parametros;
        Properties props = new Properties(); // Creamos un objeto Properties para leer el archivo .properties


        try (InputStream input = Utiles.class.getClassLoader().getResourceAsStream("db.properties")) {
            // Intentamos abrir el archivo db.properties que debería estar en la carpeta de recursos del proyecto
            if (input == null) {
                System.err.println(" No se encontró el archivo db.properties");
                // Si no lo encuentra, mostramos un mensaje de error en consola
            }
            props.load(input); // Cargamos el contenido del archivo en el objeto props
        } catch (Exception e) {
            e.printStackTrace(); // Si algo falla, mostramos el error por consola
        }


        // Aquí ya leemos las propiedades del archivo y las guardamos en variables
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");


        // Metemos las tres en un array para devolverlas todas juntas
        parametros = new String[]{url, user, password};
        return parametros; // Devolvemos el array con los datos de conexión
    }
}
