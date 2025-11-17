# Instrucciones de ejecución

## 1. Requisitos
- JDK 17+ (o la versión que estés usando).
- MySQL en ejecución.
- Driver MySQL (mysql-connector-j) incluido en el classpath (si usas Maven/Gradle ya vendrá como dependencia).
- Librería HikariCP (dependencia añadida).
- Archivo de propiedades: config/db.properties

## 2. Archivo config/db.properties (ejemplo)
db.url=jdbc:mysql://localhost:3306/empresa?useSSL=false&serverTimezone=UTC
db.user=tu_usuario
db.password=tu_password

## 3. Esquema mínimo (campos según tu código)
CREATE TABLE empleados (
  id_empleado INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100),
  departamento VARCHAR(60),
  salario DECIMAL(10,2),
  fecha_contratacion DATE,
  activo BOOLEAN
);

CREATE TABLE proyectos (
  id_proyecto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120),
  fecha_inicio DATE,
  fecha_fin DATE,
  presupuesto DECIMAL(12,2)
);

-- Si usas asignaciones (para savepoints):
CREATE TABLE asignaciones (
  id_empleado INT,
  id_proyecto INT,
  PRIMARY KEY (id_empleado, id_proyecto),
  FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
  FOREIGN KEY (id_proyecto) REFERENCES proyectos(id_proyecto)
);

## 4. Compilación y ejecución
- Con Maven: mvn clean package
- Ejecutar: java -cp target/tu-jar-con-dependencias.jar Main
(O si no empaquetas dependencias: usar mvn exec:java -Dexec.mainClass=Main)

## 5. Uso del programa
Al iniciar se muestra el menú principal:
1. Gestión de Empleados
2. Gestión de Proyectos
3. Transferencia de Presupuesto (transacción)
4. Procedimientos Almacenados
5. Salir

Cada submenú tiene opciones para crear, listar, buscar por ID, actualizar y eliminar.

## 6. Transacciones
- Transferencia de presupuesto: usa una transacción y NO cierra el pool.
- Asignación con savepoints: inserta varios registros y hace rollback parcial si falla uno.

## 7. Buenas prácticas aplicadas
- Pool Hikari inicializado una sola vez (DatabaseConfigPool).
- El pool solo se cierra al salir (opción 5).
- DAO: try-with-resources para Connection/PreparedStatement/ResultSet.
- Se eliminó el cierre del pool dentro de servicios.

## 8. Errores comunes
- Formato de fecha: usar yyyy-MM-dd (ej. 2023-01-08).
- Columnas mal escritas (departemento vs departamento) provocan SQLException.
- “HikariDataSource has been closed”: ocurre si cierras el pool antes de terminar el programa.

## 9. Ajustes opcionales
- Validar que fecha_fin >= fecha_inicio en proyectos.
- Confirmar antes de eliminar un registro.
- Capturar DateTimeParseException al leer fechas.

## 10. Salida
Al salir (opción 5) se imprime el cierre del pool. Reinicia la aplicación para volver a usarla.

Breve: configura db.properties, crea las tablas, compila y ejecuta Main. Usa el menú para gestionar empleados y proyectos y probar transacciones.