package org.example.parcialfinalpoo;

import javafx.collections.FXCollections; // 00174323 - Importa la clase FXCollections para manejar colecciones observables de JavaFX
import javafx.collections.ObservableList; // 00174323 - Importa la clase ObservableList para listas que pueden ser observadas en JavaFX
import javafx.fxml.FXML; // 00174323 - Importa la anotación FXML para identificar elementos de la interfaz definidos en archivos FXML
import javafx.fxml.Initializable; // 00174323 - Importa la interfaz Initializable que permite inicializar el controlador después de que su contenido ha sido completamente procesado
import javafx.scene.control.*; // 00174323 - Importa todas las clases del paquete control de JavaFX
import javafx.scene.control.cell.PropertyValueFactory; // 00174323 - Importa la clase PropertyValueFactory para establecer valores en las celdas de la tabla
import javafx.scene.control.Alert.AlertType; // 00174323 - Importa el tipo de alerta para el control de Alert
import org.example.parcialfinalpoo.Clases.Transaccion; // 00174323 - Importa la clase Transaccion del paquete Clases

import java.net.URL; // 00174323 - Importa la clase URL para manejar URLs
import java.sql.*; // 00174323 - Importa todas las clases de java.sql para manejar la conexión y operaciones con la base de datos
import java.time.LocalDate; // 00174323 - Importa la clase LocalDate para manejar fechas
import java.util.ResourceBundle; // 00174323 - Importa la clase ResourceBundle para manejar recursos internacionalizados

public class TransaccionController implements Initializable { // 00174323 - Define la clase TransaccionController que implementa Initializable

    @FXML // 00174323 - Marca la variable tblTransacciones para que sea inyectada desde el archivo FXML
    private TableView<Transaccion> tblTransacciones;
    @FXML // 00174323 - Marca la variable colId para que sea inyectada desde el archivo FXML
    private TableColumn<Transaccion, Integer> colId;
    @FXML // 00174323 - Marca la variable colTarjetaId para que sea inyectada desde el archivo FXML
    private TableColumn<Transaccion, Integer> colTarjetaId;
    @FXML // 00174323 - Marca la variable colFecha para que sea inyectada desde el archivo FXML
    private TableColumn<Transaccion, String> colFecha;
    @FXML // 00174323 - Marca la variable colMonto para que sea inyectada desde el archivo FXML
    private TableColumn<Transaccion, Double> colMonto;
    @FXML // 00174323 - Marca la variable colDescripcion para que sea inyectada desde el archivo FXML
    private TableColumn<Transaccion, String> colDescripcion;
    @FXML // 00174323 - Marca la variable txtTarjetaId para que sea inyectada desde el archivo FXML
    private TextField txtTarjetaId;
    @FXML // 00174323 - Marca la variable txtFecha para que sea inyectada desde el archivo FXML
    private TextField txtFecha;
    @FXML // 00174323 - Marca la variable txtMonto para que sea inyectada desde el archivo FXML
    private TextField txtMonto;
    @FXML // 00174323 - Marca la variable txtDescripcion para que sea inyectada desde el archivo FXML
    private TextField txtDescripcion;
    @FXML // 00174323 - Marca la variable btnAgregarTransaccion para que sea inyectada desde el archivo FXML
    private Button btnAgregarTransaccion;
    @FXML // 00174323 - Marca la variable btnActualizarTransaccion para que sea inyectada desde el archivo FXML
    private Button btnActualizarTransaccion;
    @FXML // 00174323 - Marca la variable btnEliminarTransaccion para que sea inyectada desde el archivo FXML
    private Button btnEliminarTransaccion;
    @FXML // 00174323 - Marca la variable btnLimpiarTransaccion para que sea inyectada desde el archivo FXML
    private Button btnLimpiarTransaccion;

    private ObservableList<Transaccion> listaTransacciones; // 00174323 - Declara una lista observable para almacenar transacciones

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal"; // 00174323 - URL de conexión a la base de datos MySQL
    private final String usuario = "root"; // 00174323 - Nombre de usuario para la conexión a la base de datos
    private final String contrasena = "root1234"; // 00174323 - Contraseña para la conexión a la base de datos

    @Override // 00174323 - Anotación para indicar que este método sobrescribe uno en la superclase
    public void initialize(URL location, ResourceBundle resources) { // 00174323 - Método inicializador que se llama después de cargar el FXML
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); // 00174323 - Asocia la columna colId con la propiedad id de la clase Transaccion
        colTarjetaId.setCellValueFactory(new PropertyValueFactory<>("tarjetaId")); // 00174323 - Asocia la columna colTarjetaId con la propiedad tarjetaId de la clase Transaccion
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha")); // 00174323 - Asocia la columna colFecha con la propiedad fecha de la clase Transaccion
        colMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal")); // 00174323 - Asocia la columna colMonto con la propiedad montoTotal de la clase Transaccion
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion")); // 00174323 - Asocia la columna colDescripcion con la propiedad descripcion de la clase Transaccion

        listaTransacciones = FXCollections.observableArrayList(); // 00174323 - Inicializa la lista observable de transacciones
        tblTransacciones.setItems(listaTransacciones); // 00174323 - Establece la lista observable como los ítems de la tabla tblTransacciones

        cargarTransacciones(); // 00174323 - Llama al método cargarTransacciones para llenar la tabla con datos

        btnAgregarTransaccion.setOnAction(event -> agregarTransaccion()); // 00174323 - Establece la acción del botón btnAgregarTransaccion para llamar al método agregarTransaccion
        btnActualizarTransaccion.setOnAction(event -> actualizarTransaccion()); // 00174323 - Establece la acción del botón btnActualizarTransaccion para llamar al método actualizarTransaccion
        btnEliminarTransaccion.setOnAction(event -> eliminarTransaccion()); // 00174323 - Establece la acción del botón btnEliminarTransaccion para llamar al método eliminarTransaccion
        btnLimpiarTransaccion.setOnAction(event -> limpiarCampos()); // 00174323 - Establece la acción del botón btnLimpiarTransaccion para llamar al método limpiarCampos

        tblTransacciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { // 00174323 - Añade un listener a la selección de la tabla para cargar datos de la transacción seleccionada
            if (newValue != null) { // 00174323 - Comprueba que la nueva selección no sea nula
                cargarDatosTransaccion(newValue); // 00174323 - Llama al método cargarDatosTransaccion con la transacción seleccionada
            }
        });
    }

    private void cargarTransacciones() { // 00174323 - Método para cargar transacciones desde la base de datos
        String consultaSQL = "SELECT tr.id, tr.fecha, tr.montoTotal, tr.descripcion, tr.tarjetaId FROM Transacciones tr"; // 00174323 - Consulta SQL para obtener todas las transacciones

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); // 00174323 - Establece una conexión con la base de datos
             Statement statement = conn.createStatement(); // 00174323 - Crea un objeto Statement para ejecutar la consulta
             ResultSet resultado = statement.executeQuery(consultaSQL)) { // 00174323 - Ejecuta la consulta y almacena el resultado en un ResultSet

            listaTransacciones.clear(); // 00174323 - Limpia la lista observable de transacciones

            while (resultado.next()) { // 00174323 - Itera sobre los resultados de la consulta
                Transaccion transaccion = new Transaccion( // 00174323 - Crea una nueva instancia de Transaccion con los datos obtenidos
                        resultado.getInt("id"), // 00174323 - Obtiene el valor de la columna id
                        resultado.getString("fecha"), // 00174323 - Obtiene el valor de la columna fecha
                        resultado.getDouble("montoTotal"), // 00174323 - Obtiene el valor de la columna montoTotal
                        resultado.getString("descripcion"), // 00174323 - Obtiene el valor de la columna descripcion
                        resultado.getInt("tarjetaId") // 00174323 - Obtiene el valor de la columna tarjetaId
                );
                listaTransacciones.add(transaccion); // 00174323 - Añade la transaccion a la lista observable
            }
        } catch (SQLException e) { // 00174323 - Captura cualquier excepción SQL que ocurra
            mostrarAlerta("Error", "Error al cargar transacciones: " + e.getMessage()); // 00174323 - Muestra una alerta con el mensaje de error
        }
    }

    private void agregarTransaccion() { // 00174323 - Método para agregar una nueva transacción a la base de datos
        int tarjetaId = Integer.parseInt(txtTarjetaId.getText()); // 00174323 - Obtiene y convierte el texto del campo txtTarjetaId a entero
        String fecha = txtFecha.getText(); // 00174323 - Obtiene el texto del campo txtFecha
        // Validar formato de fecha
        if (!esFechaValida(fecha)) { // 00174323 - Llama al método esFechaValida para comprobar si la fecha tiene un formato válido
            mostrarAlerta("Error", "La fecha debe tener el formato yyyy-mm-dd."); // 00174323 - Muestra una alerta de error si la fecha no es válida
            return; // 00174323 - Sale del método si la fecha no es válida
        }
        double monto = Double.parseDouble(txtMonto.getText()); // 00174323 - Obtiene y convierte el texto del campo txtMonto a doble
        String descripcion = txtDescripcion.getText(); // 00174323 - Obtiene el texto del campo txtDescripcion

        String consultaSQL = "INSERT INTO Transacciones (tarjetaId, fecha, montoTotal, descripcion) VALUES (?, ?, ?, ?)"; // 00174323 - Consulta SQL para insertar una nueva transacción

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); // 00174323 - Establece una conexión con la base de datos
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { // 00174323 - Crea un objeto PreparedStatement para ejecutar la consulta

            statement.setInt(1, tarjetaId); // 00174323 - Establece el valor del primer parámetro en la consulta
            statement.setDate(2, Date.valueOf(fecha)); // 00174323 - Establece el valor del segundo parámetro en la consulta
            statement.setDouble(3, monto); // 00174323 - Establece el valor del tercer parámetro en la consulta
            statement.setString(4, descripcion); // 00174323 - Establece el valor del cuarto parámetro en la consulta
            statement.executeUpdate(); // 00174323 - Ejecuta la consulta de inserción

            mostrarAlerta("Éxito", "Transacción agregada exitosamente."); // 00174323 - Muestra una alerta de éxito
            cargarTransacciones(); // 00174323 - Recarga las transacciones para reflejar la nueva inserción
            limpiarCampos(); // 00174323 - Limpia los campos del formulario
        } catch (SQLException e) { // 00174323 - Captura cualquier excepción SQL que ocurra
            mostrarAlerta("Error", "Error al agregar transacción: " + e.getMessage()); // 00174323 - Muestra una alerta con el mensaje de error
        }
    }

    private void actualizarTransaccion() { // 00174323 - Método para actualizar una transacción existente en la base de datos
        Transaccion transaccionSeleccionada = tblTransacciones.getSelectionModel().getSelectedItem(); // 00174323 - Obtiene la transacción seleccionada en la tabla
        if (transaccionSeleccionada == null) { // 00174323 - Comprueba si no hay una transacción seleccionada
            mostrarAlerta("Advertencia", "Por favor, selecciona una transacción para actualizar."); // 00174323 - Muestra una alerta de advertencia
            return; // 00174323 - Sale del método si no hay una transacción seleccionada
        }

        int id = transaccionSeleccionada.getId(); // 00174323 - Obtiene el ID de la transacción seleccionada
        int tarjetaId = Integer.parseInt(txtTarjetaId.getText()); // 00174323 - Obtiene y convierte el texto del campo txtTarjetaId a entero
        String fecha = txtFecha.getText(); // 00174323 - Obtiene el texto del campo txtFecha
        double monto = Double.parseDouble(txtMonto.getText()); // 00174323 - Obtiene y convierte el texto del campo txtMonto a doble
        String descripcion = txtDescripcion.getText(); // 00174323 - Obtiene el texto del campo txtDescripcion

        // Validar formato de fecha
        if (!esFechaValida(fecha)) { // 00174323 - Llama al método esFechaValida para comprobar si la fecha tiene un formato válido
            mostrarAlerta("Error", "La fecha debe tener el formato yyyy-mm-dd."); // 00174323 - Muestra una alerta de error si la fecha no es válida
            return; // 00174323 - Sale del método si la fecha no es válida
        }

        String consultaSQL = "UPDATE Transacciones SET tarjetaId = ?, fecha = ?, montoTotal = ?, descripcion = ? WHERE id = ?"; // 00174323 - Consulta SQL para actualizar una transacción

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); // 00174323 - Establece una conexión con la base de datos
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { // 00174323 - Crea un objeto PreparedStatement para ejecutar la consulta

            statement.setInt(1, tarjetaId); // 00174323 - Establece el valor del primer parámetro en la consulta
            statement.setDate(2, Date.valueOf(fecha)); // 00174323 - Establece el valor del segundo parámetro en la consulta
            statement.setDouble(3, monto); // 00174323 - Establece el valor del tercer parámetro en la consulta
            statement.setString(4, descripcion); // 00174323 - Establece el valor del cuarto parámetro en la consulta
            statement.setInt(5, id); // 00174323 - Establece el valor del quinto parámetro en la consulta
            statement.executeUpdate(); // 00174323 - Ejecuta la consulta de actualización

            mostrarAlerta("Éxito", "Transacción actualizada exitosamente."); // 00174323 - Muestra una alerta de éxito
            cargarTransacciones(); // 00174323 - Recarga las transacciones para reflejar la actualización
            limpiarCampos(); // 00174323 - Limpia los campos del formulario
        } catch (SQLException e) { // 00174323 - Captura cualquier excepción SQL que ocurra
            mostrarAlerta("Error", "Error al actualizar transacción: " + e.getMessage()); // 00174323 - Muestra una alerta con el mensaje de error
        }
    }

    private boolean esFechaValida(String fecha) { // 00174323 - Método para validar si una fecha tiene un formato válido
        try {
            Date.valueOf(fecha); // 00174323 - Intenta convertir la fecha a un objeto Date
            return true; // 00174323 - Devuelve true si la conversión es exitosa
        } catch (IllegalArgumentException e) { // 00174323 - Captura la excepción si la fecha no es válida
            return false; // 00174323 - Devuelve false si la fecha no es válida
        }
    }

    private void eliminarTransaccion() { // 00174323 - Método para eliminar una transacción de la base de datos
        Transaccion transaccionSeleccionada = tblTransacciones.getSelectionModel().getSelectedItem(); // 00174323 - Obtiene la transacción seleccionada en la tabla
        if (transaccionSeleccionada == null) { // 00174323 - Comprueba si no hay una transacción seleccionada
            mostrarAlerta("Advertencia", "Por favor, selecciona una transacción para eliminar."); // 00174323 - Muestra una alerta de advertencia
            return; // 00174323 - Sale del método si no hay una transacción seleccionada
        }

        int id = transaccionSeleccionada.getId(); // 00174323 - Obtiene el ID de la transacción seleccionada
        String consultaSQL = "DELETE FROM Transacciones WHERE id = ?"; // 00174323 - Consulta SQL para eliminar una transacción

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); // 00174323 - Establece una conexión con la base de datos
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { // 00174323 - Crea un objeto PreparedStatement para ejecutar la consulta

            statement.setInt(1, id); // 00174323 - Establece el valor del primer parámetro en la consulta
            statement.executeUpdate(); // 00174323 - Ejecuta la consulta de eliminación

            mostrarAlerta("Éxito", "Transacción eliminada exitosamente."); // 00174323 - Muestra una alerta de éxito
            cargarTransacciones(); // 00174323 - Recarga las transacciones para reflejar la eliminación
            limpiarCampos(); // 00174323 - Limpia los campos del formulario
        } catch (SQLException e) { // 00174323 - Captura cualquier excepción SQL que ocurra
            mostrarAlerta("Error", "Error al eliminar transacción: " + e.getMessage()); // 00174323 - Muestra una alerta con el mensaje de error
        }
    }

    private void cargarDatosTransaccion(Transaccion transaccion) { // 00174323 - Método para cargar los datos de una transacción en los campos del formulario
        txtTarjetaId.setText(String.valueOf(transaccion.getTarjetaId())); // 00174323 - Establece el texto del campo txtTarjetaId con el valor de tarjetaId de la transacción
        txtFecha.setText(transaccion.getFecha()); // 00174323 - Establece el texto del campo txtFecha con el valor de fecha de la transacción
        txtMonto.setText(String.valueOf(transaccion.getMontoTotal())); // 00174323 - Establece el texto del campo txtMonto con el valor de montoTotal de la transacción
        txtDescripcion.setText(transaccion.getDescripcion()); // 00174323 - Establece el texto del campo txtDescripcion con el valor de descripcion de la transacción

        btnActualizarTransaccion.setDisable(false); // 00174323 - Habilita el botón de actualizar transacción
        btnEliminarTransaccion.setDisable(false); // 00174323 - Habilita el botón de eliminar transacción
    }

    private void limpiarCampos() { // 00174323 - Método para limpiar los campos del formulario
        txtTarjetaId.clear(); // 00174323 - Limpia el texto del campo txtTarjetaId
        txtFecha.clear(); // 00174323 - Limpia el texto del campo txtFecha
        txtMonto.clear(); // 00174323 - Limpia el texto del campo txtMonto
        txtDescripcion.clear(); // 00174323 - Limpia el texto del campo txtDescripcion

        btnActualizarTransaccion.setDisable(true); // 00174323 - Deshabilita el botón de actualizar transacción
        btnEliminarTransaccion.setDisable(true); // 00174323 - Deshabilita el botón de eliminar transacción
    }

    private void mostrarAlerta(String titulo, String mensaje) { // 00174323 - Método para mostrar una alerta con un título y un mensaje específicos
        Alert alerta = new Alert(AlertType.INFORMATION); // 00174323 - Crea un nuevo objeto Alert de tipo INFORMATION
        alerta.setTitle(titulo); // 00174323 - Establece el título de la alerta
        alerta.setHeaderText(null); // 00174323 - Establece el encabezado de la alerta a null
        alerta.setContentText(mensaje); // 00174323 - Establece el contenido de texto de la alerta con el mensaje proporcionado
        alerta.showAndWait(); // 00174323 - Muestra la alerta y espera a que el usuario la cierre
    }
}