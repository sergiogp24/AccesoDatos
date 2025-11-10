package conexion;

import java.io.InputStream;
import java.util.Properties;

public class Utiles {
    private static String url;
    private static String user;
    private static String password;
    public static String[] llamarDatos() {
        String [] parametros;
        Properties props = new Properties();
        try (InputStream input = Utiles.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("❌ No se encontró el archivo db.properties");
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Obtener datos de conexión
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        parametros = new String[]{url, user, password};
        return parametros;

    }
}
