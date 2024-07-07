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
import java.time.LocalDate;
import java.util.ResourceBundle; // 00174323: Importar clase para manejar recursos
import javafx.scene.control.Alert; // 00174323: Importar clase de JavaFX para mostrar alertas
import javafx.scene.control.Button; // 00174323: Importar clase de JavaFX para botones

public class ReportController implements Initializable { // 00174323: Definir la clase que implementa Initializable

    @FXML // 00174323: Anotación para inyectar el campo desde el archivo FXML
    private ComboBox<String> cmbFacilitadorD; // 00174323: Declaración del ComboBox para facilitadores

    @FXML // 00174323: Anotación para inyectar el campo desde el archivo FXML
    private Button btnGenerarD; // 00174323: Declaración del botón para generar reporte

    @FXML
    private TextField idRepoA; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte A
    @FXML
    private DatePicker dateFechaInicioA;  //00144723 -> En este campo es donde se selecciona la fecha inicial para qenerar el reporte tipo A
    @FXML
    private DatePicker DateFechaFinA;  //00144723 -> En este campo es donde se selecciona la fecha fina para qenerar el reporte tipo A
    @FXML
    private Label lblErrorReporteA;  //00144723 -> Este label es para mostrar un mensaje de que uno o más campos estan vacios al querer generar un reporte.
    @FXML
    private Button btnGenerarReporteA;
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal"; // 00174323: URL de la base de datos
    private static final String usuario = "sa"; // 00174323: Usuario de la base de datos
    private static final String contraseña = "12345678"; // 00174323: Contraseña de la base de datos

    @Override // 00174323: Método de inicialización del controlador
    public void initialize(URL location, ResourceBundle resources) {
        btnGenerarD.setOnAction(event -> generarReporte()); // 00174323: Configura la acción del botón para generar el reporte
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

    //Reporte A.
    @FXML
    private void generarReporteA() {
        // 00144723 -> Inicializar los label de errores.
        //Esta linea inicializa el label del error para que al ejecutar no de un NullPointerException
        lblErrorReporteA.setText("");

        //00144723 -> Extraer los datos ingresados por el usuario
        String idEmpleado = idRepoA.getText(); // 00144723 -> Guardamos el id del empleado en una variable para poder operar con el.
        LocalDate fechaInicio = dateFechaInicioA.getValue();  //00144723 -> Guardamos la fecha inicial una variable para poder operar con ella.
        LocalDate fechaFin = DateFechaFinA.getValue(); //00144723 -> Guardamos la fecha final una variable para poder operar con ella.

        if (idEmpleado.equals("") || fechaInicio == null || fechaFin == null) //00144723 -> Hacemos un if para verificar si uno o mas campos esta vacio{
        {
            lblErrorReporteA.setStyle("-fx-text-fill: red"); //00144723 - Si algun campo es vacio se configura de color rojo el texto que muestra el label
            lblErrorReporteA.setText("Uno o más campos esta vacio."); //00144723 - Muestra un mensaje de error.
        } else {
            try {
                //00144723 - Aqui establecemos conexion a la BD
                Connection conn = DriverManager.getConnection(
                        jdbcUrl,
                        usuario,
                        contraseña);
                //00144723 Aqui preparamos una sentencia para realizar la consulta a la BD
                PreparedStatement psMostrarCompras = conn.prepareStatement("select c.nombreCompleto as Nombre, c.direccion as Domicilio, c.telefono as Telefono,\n" +
                        "       T.descripcion as Descripcion, T.montoTotal as MontoCompra, T.fecha as FechaCompra,\n" +
                        "       tj.numeroTarjeta as Tarjeta, tj.fechaExpiracion as FechaExpiracion, tj.tipo as Tipo, F.nombre as Facilitador\n" +
                        "from clientes c inner join tarjetas tj on c.id = tj.clienteId\n" +
                        "inner join Facilitadores F on tj.facilitadorId = F.id\n" +
                        "inner join Transacciones T on tj.id = T.tarjetaId where c.id = ? and DATE(T.fecha) between ? and ?");
                //00144723 - Pasamos el primero parametro que es el id pero realizamos un casteo de String a int para que no genere error
                psMostrarCompras.setInt(1, Integer.valueOf(idEmpleado));
                //00144723 - Pasamos el segundo parametro que es la fecha inicial del rango y tambien realizamos un casteo de LocalDate a Date, ya que si guardabamos la fecha
                //en una variable Date al inicio se generá un error ya que se estaria queriendo acceder a un dato que aun no se guarda.
                psMostrarCompras.setDate(2, Date.valueOf(fechaInicio));
                //00144723 - Pasamos el tercer parametro que es la fecha final del rango.
                psMostrarCompras.setDate(3, Date.valueOf(fechaFin));

                //00144723 - Ejecutamos la consulta
                ResultSet rs = psMostrarCompras.executeQuery();

                //00144723 - Se crea un carpeta si no existe, esto con el fin de ir almacenando los reportes
                File reporteA = new File("Reporte");
                if (!reporteA.exists()) {
                    reporteA.mkdirs();//00144723 - Si no existe se crea
                }
                //00144723 - Se guarda el nombre que se desea tener en nuestro caso designamos este formato.
                String nombreArchivo = "Reporte A" + " - " + java.time.LocalDateTime.now().toString().replace(':', '-');

                //00144723 - Se crea un objeto BufferedWriter para poder ir escribiendo dentro de la carpeta que almacenará los txt
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(reporteA, nombreArchivo + ".txt")))) {
                    //00144723 - Aqui se escribe este mensaje como encabezado
                    bufferedWriter.write("Reporte de compras que ha realizado el cliente con ID: " + idEmpleado + "\n\n");
                    //00144723 - Aqui iremos guardando los datos del cliente y de la transaccion realizada para poder mostrarlos posteriormente
                    while (rs.next()) {
                        String nombre = rs.getString("Nombre"); //00144723 - se extrae el nombre de usuario
                        String direccionCliente = rs.getString("Domicilio"); //00144723 - se extrae el domicilio de usuario
                        String telefonoCliente = rs.getString("Telefono");//00144723 - se extrae el telefono de usuario
                        String numTarjeta = rs.getString("Tarjeta");//00144723 - se extrae el numero de tarjeta
                        String Facilitador = rs.getString("Facilitador");//00144723 - se extrae el nombre del facilitador
                        String fechaExpiracion = rs.getString("FechaExpiracion");//00144723 - se extrae la fecha de expiracion                        String tipo = rs.getString("Tipo");
                        String tipo = rs.getString("Tipo");//00144723 - Se extrae el tipo de tarjeta
                        String descripcion = rs.getString("Descripcion"); //00144723 - Se la descripcion de la compra
                        double montoTotal = rs.getDouble("MontoCompra");//00144723 - Se extrae el monto de la compra
                        Date fechaCompra = rs.getDate("FechaCompra");//00144723 - Se extrae la fecha de la compra


                        bufferedWriter.write("\n");  //00144723 - Se escribe un salto de linea
                        bufferedWriter.write("Cliente: " + nombre + "\n");//00144723 - Se escribe el nombre del cliente
                        bufferedWriter.write("Fecha de compra: " + fechaCompra + "\n");//00144723 - Se escribe la fecha de compra
                        bufferedWriter.write("Monto total de la compra: " + montoTotal + "\n");//00144723 - Se escribe el monto de la compra
                        bufferedWriter.write("Numero de tarjeta: " + numTarjeta + "\n");//00144723 - Se escribe el numero de la tarjeta
                        bufferedWriter.write("Fecha de expiracion: " + fechaExpiracion + "\n");//00144723 - Se escribe la fecha de expiracio de la tarjeta
                        bufferedWriter.write("Tipo: " + tipo + "\n");//00144723 - Se escribe el tipo de tarjeta
                        bufferedWriter.write("Facilitador: " + Facilitador + "\n");//00144723 - Se escribe el facilitador
                    }
                } catch (IOException i) {
                    mostrarAlerta("Error", "Error al escribir en el archivo: " + i.getMessage()); //00144723 - Se muestra una alerta si se generá un error
                }
                conn.close(); //00144723 - Como toda buena practica de un estudiante UCA se cierra la conexion


            } catch (Exception e) {
                //00144723 - Aqui mostramos un mensaje de error y la excepcion que se salió pero en forma de mensaje
                mostrarAlerta("ERROR!", e.getMessage());
            }
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
}
