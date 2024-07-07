package org.example.parcialfinalpoo;

import javafx.fxml.FXML; // 00174323: Importar anotaciones de JavaFX para inyección de campos
import javafx.fxml.Initializable; // 00174323: Importar interfaz para inicialización del controlador
import javafx.scene.control.*; // 00174323: Importar clases de JavaFX para controles UI
import java.io.BufferedWriter; // 00174323: Importar clase para escritura eficiente en archivos
import java.io.File; // 00174323: Importar clase para manejo de archivos y directorios
import java.io.FileWriter; // 00174323: Importar clase para escribir en archivos
import java.io.IOException; // 00174323: Importar clase para manejo de excepciones de E/S
import java.net.URL; // 00174323: Importar clase para manejo de URL
import java.sql.*; // 00174323: Importar clases para manejo de bases de datos y SQL
import java.time.LocalDateTime;
import java.util.ResourceBundle; // 00174323: Importar clase para manejar recursos
import javafx.scene.control.Alert; // 00174323: Importar clase de JavaFX para mostrar alertas
import javafx.scene.control.Button; // 00174323: Importar clase de JavaFX para botones

public class ReportController implements Initializable { // 00174323: Definir la clase que implementa Initializable

    @FXML // 00174323: Anotación para inyectar el campo desde el archivo FXML
    private ComboBox<String> cmbFacilitadorD; // 00174323: Declaración del ComboBox para facilitadores

    @FXML // 00174323: Anotación para inyectar el campo desde el archivo FXML
    private Button btnGenerarD; // 00174323: Declaración del botón para generar reporte

    @FXML
    private TextField tfIdB;
    @FXML
    private TextField tfMesB;
    @FXML
    private TextField tfAnioB;
    @FXML
    private Button btnGenerarReporteB;

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal"; // 00174323: URL de la base de datos
    private static final String usuario = "sa"; // 00174323: Usuario de la base de datos
    private static final String contraseña = "12345678"; // 00174323: Contraseña de la base de datos

    @Override // 00174323: Método de inicialización del controlador
    public void initialize(URL location, ResourceBundle resources) {
        btnGenerarD.setOnAction(event -> generarReporte()); // 00174323: Configura la acción del botón para generar el reporte
        btnGenerarReporteB.setOnAction(event -> generarReporteB());
        cargarFacilitadores(); // 00174323: Llenar el ComboBox con facilitadores
    }

    // 00174323: Método para cargar facilitadores desde la base de datos al ComboBox
    private void cargarFacilitadores() {
        String consultaSQL = "SELECT nombre FROM Facilitadores"; // 00174323: Consulta SQL para obtener los facilitadores

        try (
                Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña); // 00174323: Establecer conexión a la base de datos
                Statement statement = conexion.createStatement(); // 00174323: Crear una declaración SQL
                ResultSet resultado = statement.executeQuery(consultaSQL) // 00174323: Ejecutar la consulta y obtener el resultado
        ) {
            while (resultado.next()) {
                String nombreFacilitador = resultado.getString("nombre"); // 00174323: Obtener el nombre del facilitador
                cmbFacilitadorD.getItems().add(nombreFacilitador); // 00174323: Añadir el nombre del facilitador al ComboBox
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al cargar facilitadores: " + e.getMessage()); // 00174323: Manejo de excepción en la consulta SQL
        }
    }

    // 00174323: Método para generar el reporte y escribirlo en un archivo .txt
    private void generarReporte() {
        String facilitador = cmbFacilitadorD.getSelectionModel().getSelectedItem(); // 00174323: Obtener el facilitador seleccionado del ComboBox

        String consultaSQL = "SELECT c.id, c.nombreCompleto AS Cliente, COUNT(t.id) AS CantidadCompras, SUM(tr.montoTotal) AS TotalGastado, f.nombre AS Facilitador " +
                "FROM Clientes c " +
                "INNER JOIN Tarjetas t ON c.id = t.clienteId " +
                "INNER JOIN Facilitadores f ON t.facilitadorId = f.id " +
                "INNER JOIN Transacciones tr ON t.id = tr.tarjetaId " +
                "WHERE f.nombre = ? " +
                "GROUP BY c.id, c.nombreCompleto"; // 00174323: Consulta SQL para obtener clientes que han realizado compras con un facilitador específico

        try (
                Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña); // 00174323: Establecer conexión a la base de datos
                PreparedStatement statement = conexion.prepareStatement(consultaSQL); // 00174323: Preparar la declaración SQL
        ) {
            statement.setString(1, facilitador); // 00174323: Asignar el valor del facilitador al parámetro en la consulta preparada
            ResultSet resultado = statement.executeQuery(); // 00174323: Ejecutar la consulta y obtener el resultado

            // 00174323: Crear la carpeta "Reporte" si no existe
            File carpetaReporte = new File("Reporte");
            if (!carpetaReporte.exists()) { // 00174323: Verifica la existencia de la carpeta "Reporte" si no existe
                carpetaReporte.mkdirs(); // 00174323: Crear la carpeta "Reporte" si no existe
            }

            String nombreArchivo = "Reporte D" + " - " + java.time.LocalDateTime.now().toString().replace(':', '-'); // 00174323: Nombre del archivo de reporte con la letra del facilitador y fecha/hora actual

            // 00174323: Crear un BufferedWriter para escribir en el archivo dentro de la carpeta "Reporte"
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(carpetaReporte, nombreArchivo + ".txt")))) {
                writer.write("Reporte de clientes que han realizado compras con facilitador " + facilitador + "\n\n"); // 00174323: Escribir encabezado del reporte

                // 00174323: Iterar sobre los resultados y escribir en el archivo
                while (resultado.next()) {
                    String cliente = resultado.getString("Cliente"); // 00174323: Obtener el nombre del cliente
                    int cantidadCompras = resultado.getInt("CantidadCompras"); // 00174323: Obtener la cantidad de compras
                    double totalGastado = resultado.getDouble("TotalGastado"); // 00174323: Obtener el total gastado

                    // 00174323: Escribir cada línea en el archivo
                    writer.write("Cliente: " + cliente + "\n"); // 00174323: Escribir nombre del cliente
                    writer.write("Cantidad de compras: " + cantidadCompras + "\n"); // 00174323: Escribir cantidad de transacciones
                    writer.write("Total gastado: $" + totalGastado + "\n\n"); // 00174323: Escribir total gastado
                }

                mostrarAlerta("Reporte generado", "El reporte se ha generado correctamente en el archivo " + nombreArchivo + ".txt"); // 00174323: Confirmación de escritura
            } catch (IOException e) { // 00174323: Manejo de excepción al escribir en el archivo
                mostrarAlerta("Error", "Error al escribir en el archivo: " + e.getMessage()); // 00174323: Manejo de excepción al escribir en el archivo
            }
        } catch (SQLException e) { // 00174323: Manejo de excepción en la consulta SQL
            mostrarAlerta("Error", "Error en la consulta SQL: " + e.getMessage()); // 00174323: Manejo de excepción en la consulta SQL
        }
    }

    // 00174323: Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00174323: Crear una nueva alerta de información
        alert.setTitle(titulo); // 00174323: Establecer el título de la alerta
        alert.setHeaderText(null); // 00174323: Eliminar el encabezado de la alerta
        alert.setContentText(mensaje); // 00174323: Establecer el mensaje de contenido de la alerta
        alert.showAndWait(); // 00174323: Mostrar la alerta y esperar a que el usuario la cierre
    }

    private void generarReporteB (){

        String id = tfIdB.getText(); // 00044123: Obtener el facilitador seleccionado del ComboBox
        String mes = tfMesB.getText();
        String anio = tfAnioB.getText();
        String consultaSQL = "SELECT c.id AS ClienteID, c.nombreCompleto AS NombreCliente, SUM(t.montoTotal) AS TotalGastado, MONTHNAME(t.fecha) AS NombreMes " +
                "FROM Clientes c " +
                "INNER JOIN Tarjetas tj ON c.id = tj.clienteId " +
                "INNER JOIN Transacciones t ON tj.id = t.tarjetaId " +
                "WHERE c.id = ? AND YEAR(t.fecha) = ? AND MONTH(t.fecha) = ? " +
                "GROUP BY c.id, c.nombreCompleto, NombreMes";// 00044123: Consulta SQL para calcular la sumatoria del dinero gastado por un cliente en un mes específico.
        try (
                Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña); // 00044123: Establecer conexión a la base de datos
                PreparedStatement statement = conexion.prepareStatement(consultaSQL); // 00044123: Preparar la declaración SQL
        ) {
            statement.setString(1, id); // 00044123: Asignar el valor del ID al parámetro en la consulta preparada
            statement.setString(2, anio); // 00044123: Asignar el valor del mes al parámetro en la consulta preparada
            statement.setString(3, mes); // 00044123: Asignar el valor del año al parámetro en la consulta preparada
            ResultSet resultado = statement.executeQuery(); // 00044123: Ejecutar la consulta y obtener el resultado

            // 00044123: Crear la carpeta "Reporte" si no existe
            File carpetaReporte = new File("Reporte");
            if (!carpetaReporte.exists()) { // 00044123: Verifica la existencia de la carpeta "Reporte" si no existe
                carpetaReporte.mkdirs(); // 00044123: Crear la carpeta "Reporte" si no existe
            }
            String nombreArchivo = "Reporte B" + " - " + java.time.LocalDateTime.now().toString().replace(':', '-'); // 00044123: Nombre del archivo de reporte con la letra del facilitador y fecha/hora actual

            // 00044123: Crear un BufferedWriter para escribir en el archivo dentro de la carpeta "Reporte"
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(carpetaReporte, nombreArchivo + ".txt")))) {
                writer.write("Reporte de el total de dinero gastado por un cliente en un mes específico " + "\n\n"); // 00044123: Escribir encabezado del reporte

                // 00044123: Iterar sobre los resultados y escribir en el archivo
                while (resultado.next()) {
                    String cliente = resultado.getString("ClienteID");
                    String nombre = resultado.getString("NombreCliente");
                    double totalGastado = resultado.getDouble("TotalGastado");
                    String nombreMes = resultado.getString("NombreMes");

                    // 00044123: Escribir cada línea en el archivo
                    writer.write("ID Cliente: " + cliente + "\n");
                    writer.write("Nombre: " + nombre + "\n");
                    writer.write("Total gastado: $" + totalGastado + "\n");
                    writer.write("Mes: " + nombreMes + "\n\n");
                }
                mostrarAlerta("Reporte generado", "El reporte se ha generado correctamente en el archivo " + nombreArchivo + ".txt"); // 00044123: Confirmación de escritura
            } catch (IOException e) { // 00044123: Manejo de excepción al escribir en el archivo
                mostrarAlerta("Error", "Error al escribir en el archivo: " + e.getMessage()); // 00044123: Manejo de excepción al escribir en el archivo
            }
        } catch (SQLException e) { // 00044123: Manejo de excepción en la consulta SQL
            mostrarAlerta("Error", "Error en la consulta SQL: " + e.getMessage()); // 00044123: Manejo de excepción en la consulta SQL
        }
    }
}


