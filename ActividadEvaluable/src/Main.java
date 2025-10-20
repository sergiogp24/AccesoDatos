import java.util.Scanner;
import java.io.*;


public class Main {
    private static final String Ruta_Datos = "cuentas.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CuentaBancaria cuentaBancaria = null;



        try{
            cuentaBancaria = cargarCuenta();
            System.out.println("Cuenta cargada exitosamente.");

        } catch (Exception e){
            System.out.println("No se pudo cargar la cuenta. Creando una nueva cuenta.");
            System.out.print("Ingrese el nombre del cliente: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese el DNI del cliente: ");
            String dni = sc.nextLine();
            cuentaBancaria = new CuentaBancaria(new Cliente(nombre, dni));
        }

        int opcion;
        do {
            System.out.println("\n--- MENÚ CUENTA BANCARIA ---");
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Ver movimientos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = Integer.parseInt(sc.nextLine());
            try {
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Cantidad a ingresar: ");
                        double ing = Double.parseDouble(sc.nextLine());
                        cuentaBancaria.ingresarDinero(ing);
                        System.out.println("Ingreso realizado.");
                }
                    case 2 -> {
                        System.out.print("Cantidad a retirar: ");
                        double ret = Double.parseDouble(sc.nextLine());
                        cuentaBancaria.retirar(ret);
                        System.out.println("Retirada realizada.");
                    }

                    case 3 -> System.out.println("Saldo actual: " + cuentaBancaria.getSaldo());
                    case 4 -> {
                        System.out.println("Movimientos:");
                        cuentaBancaria.getMovimientos().forEach(System.out::println);
                        }
                    case 0 -> {
                        System.out.println("Saliendo");
                    }
                    default -> System.out.println("Opción no válida.");

                }

            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }

            } while (opcion != 0);

        try {
            guardarCuenta(cuentaBancaria);
            System.out.println("Cuenta guardada exitosamente en: " + Ruta_Datos);
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta: " + e.getMessage());
        }
        sc.close();
    }

    private static CuentaBancaria cargarCuenta() throws IOException, ClassNotFoundException {
        File archivo = new File(Ruta_Datos);
        if (!archivo.exists()) throw new FileNotFoundException();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (CuentaBancaria) ois.readObject();
        }
    }

    private static void guardarCuenta(CuentaBancaria cuenta) throws IOException {
        File carpeta = new File("datos");
        if (!carpeta.exists()) carpeta.mkdir();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Ruta_Datos))) {
            oos.writeObject(cuenta);
        }
    }
}
