import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Crear un Arraylist de Prodcutos llamado inventario
        ArrayList<Producto> inventario = new ArrayList<>();
        //Añadir 5 productos al inventario
        inventario.add(new Producto("P001", "Portátil", 899.99));
        inventario.add(new Producto("P002", "Ratón", 25.50));
        inventario.add(new Producto("P003", "Teclado", 45.00));
        inventario.add(new Producto("P004", "Monitor", 199.99));
        inventario.add(new Producto("P005", "Webcam", 59.99));
        //Mostrar todos los productos del inventario
        System.out.println("Inventario de productos:");
        for (Producto p : inventario) {
            System.out.println(p);
        }
 // EJERCICIO 2: Búsqueda y consulta
        //  TODO: Buscar el producto con código "P003" y mostrar sus datos
        System.out.println("Datos del producto con código P003:");
        for (Producto p : inventario) {
            if (p.getCodigo().equals("P003")) {
                System.out.println("Producto encontrado (P003): " + p);
            }
        }
        // TODO: Verificar si existe un producto llamado "Ratón"
        boolean existeRaton = false;
        for (Producto p : inventario) {
            if (p.getNombre().equals("Ratón")) {
                existeRaton = true;
                break;
            }
        }
        System.out.println("¿Existe un producto llamado 'Ratón'? " + existeRaton );
        // TODO: Mostrar cuántos productos hay en el inventario
        System.out.println("Número total de productos en el inventario: " + inventario.size());
        // EJERCICIO 3: Modificaciones
        // TODO: Cambiar el precio del Monitor a 179.99€
        for (Producto p : inventario) {
            if (p.getCodigo().equals("P004")) {
                p.setPrecio(179.99);
                System.out.println("Precio del Monitor actualizado: " + p);
            }
        }
        //TODO: Eliminar la Webcam del inventario
        inventario.remove(4); // La Webcam es el quinto producto (índice 4)
        System.out.println("Webcam eliminada del inventario.");
        //TODO: Añadir un nuevo producto: P006 - Altavoces - 35.00€
        inventario.add(new Producto("P006", "Altavoces", 35.00));
        System.out.println("Inventario de productos:");
        for (Producto p : inventario) {
            System.out.println(p);
        }
       // EJERCICIO 4: Operaciones avanzadas
        // TODO: Calcular el precio total del inventario
        double precioTotal = 0;
        for (Producto p : inventario) {
            precioTotal += p.getPrecio();
        }
        System.out.println("Precio total del inventario: " + precioTotal + "€");
        // TODO: Encontrar el producto más caro
        Producto Caro = null;
        double precioMasAlto = 0;
        for (Producto p : inventario) {
            if (p.getPrecio() > precioMasAlto) {
                precioMasAlto = p.getPrecio();
                Caro = p;
            }
        }
        System.out.println("Producto más caro: " + Caro);
        // TODO: Mostrar solo productos con precio superior a 50€
        System.out.println("\n Productos con precio > 50€:");
        for (Producto p : inventario) {
            if (p.getPrecio() > 50) {
                System.out.println(p);
            }
        }

    }
}