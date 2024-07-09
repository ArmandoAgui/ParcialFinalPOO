package org.example.parcialfinalpoo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.parcialfinalpoo.Clases.Facilitador;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class FacilitadoresController implements Initializable {
    // 00144723 - Etiqueta para mostrar errores en el nombre
    @FXML
    private Label lblErrorNombre;

    // 00144723 - Columna ID en la tabla
    @FXML
    private TableColumn<Facilitador, Integer> ColID;

    // 00144723 - Botón para actualizar
    @FXML
    private Button btnActualizar;

    // 00144723 - Botón para agregar
    @FXML
    private Button btnAgregar;

    // 00144723 - Botón para mostrar
    @FXML
    private Button btnMostrar;

    // 00144723 -Botón para eliminar
    @FXML
    private Button btnEliminar;

    // 00144723 - Columna Nombre en la tabla
    @FXML
    private TableColumn<Facilitador, String> colNombre;

    // 00144723 - Tabla para mostrar los resultados
    @FXML
    private TableView<Facilitador> tblResultados;

    // 00144723 - Campo de texto para el nombre del facilitador
    @FXML
    private TextField txtNombreFacilitador;

    // 00144723 - Lista observable de facilitadores
    private ObservableList<Facilitador> listaFacilitadores;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal"; // 00144723 URL de conexión a la base de datos MySQL
    private final String usuario = "root"; // 00144723 Nombre de usuario para la conexión a la base de datos
    private final String contrasena = "root1234"; // 00144723 Contraseña para la conexión a la base de datos

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 00144723 - Configuración de la columna ID
        ColID.setCellValueFactory(new PropertyValueFactory<>("id"));

        // 00144723 - Configuración de la columna Nombre
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // 00144723 - Inicialización de la lista observable de facilitadores
        listaFacilitadores = FXCollections.observableArrayList();

        // 00144723 - Establecer la lista observable en la tabla
        tblResultados.setItems(listaFacilitadores);

        // 00144723 - Configuración del evento para el botón agregar
        btnAgregar.setOnAction(event -> agregarFacilitador());

        // 00144723 - Configuración del evento para el botón actualizar
        btnActualizar.setOnAction(event -> actualizarFacilitador());

        // 00144723 - Configuración del evento para el botón eliminar
        btnEliminar.setOnAction(event -> eliminarFacilitador());

        // 00144723 - Configuración del evento para el botón mostrar
        btnMostrar.setOnAction(event -> mostrarFacilitadores());

        // 00144723 - Configuración del listener para la selección de elementos en la tabla
        tblResultados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatos(newValue);
            }
        });
    }

    // 00144723 - Método para cargar datos de un facilitador
    private void cargarDatos(Facilitador newValue) {
        // 00144723 - Método vacío, se puede implementar según necesidades
    }

    // 00144723 - Método para mostrar los facilitadores en la tabla
    private void mostrarFacilitadores() {
        // 00144723 - Consulta SQL para obtener todos los facilitadores
        String query = "SELECT * FROM facilitadores";

        try {
            // 00144723 - Establecimiento de conexión con la base de datos
            Connection conn = DriverManager.getConnection(
                    jdbcUrl,
                    usuario,
                    contrasena
            );
            // 00144723 - Preparación de la consulta SQL
            PreparedStatement ps = conn.prepareStatement(query);
            // 00144723 - Ejecución de la consulta
            ResultSet rs = ps.executeQuery();

            // 00144723 - Limpieza de la lista observable de facilitadores
            listaFacilitadores.clear();

            // 00144723 - Recorrido de los resultados de la consulta
            while (rs.next()) {
                // 00144723 - Creación de un nuevo facilitador con los datos obtenidos
                Facilitador facilitador = new Facilitador(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                // 00144723 - Adición del facilitador a la lista observable
                listaFacilitadores.add(facilitador);
            }

        } catch (SQLException e) {
            // 00144723 - Mostrar alerta en caso de error
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // 00144723 - Método para eliminar un facilitador
    private void eliminarFacilitador() {
        // 00144723 - Obtener el facilitador seleccionado en la tabla
        Facilitador facilitadorSeleccionado = tblResultados.getSelectionModel().getSelectedItem();
        // 00144723 - Verificar si no hay facilitador seleccionado
        if (facilitadorSeleccionado == null) {
            // 00144723 - Mostrar alerta de advertencia si no hay selección
            mostrarAlerta("Advertencia", "Por favor selecciona un facilitador para eliminar.");
            return;
        }
        // 00144723 - Obtener el ID del facilitador seleccionado
        int id = facilitadorSeleccionado.getId();
        // 00144723 - Consulta SQL para eliminar un facilitador por ID
        String query = "DELETE FROM facilitadores WHERE id = ?";

        try {
            // 00144723 - Establecimiento de conexión con la base de datos
            Connection connection = DriverManager.getConnection(
                    jdbcUrl,
                    usuario,
                    contrasena
            );
            // 00144723 - Preparación de la consulta SQL
            PreparedStatement ps = connection.prepareStatement(query);
            // 00144723 - Establecer el ID del facilitador en la consulta
            ps.setInt(1, id);
            // 00144723 - Ejecución de la consulta
            ps.executeUpdate();

            // 00144723 - Mostrar alerta de éxito
            mostrarAlerta("Éxito", "Facilitador eliminado exitosamente.");
            // 00144723 - Actualizar la lista de facilitadores en la tabla
            mostrarFacilitadores();
            // 00144723 - Limpiar el campo de texto del nombre del facilitador
            txtNombreFacilitador.clear();
        } catch (SQLException e) {
            // 00144723 - Mostrar alerta en caso de error
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // 00144723 - Método para agregar un nuevo facilitador
    private void agregarFacilitador() {
        // 00144723 - Iniciar el texto de la etiqueta de error como vacío
        lblErrorNombre.setText("");
        // 00144723 - Verificar si el campo de texto está vacío
        if (txtNombreFacilitador.getText().trim().isEmpty()) {
            // 00144723 - Establecer el estilo de la etiqueta de error a rojo
            lblErrorNombre.setStyle("-fx-text-fill: red");
            // 00144723 - Mostrar mensaje de error en la etiqueta
            lblErrorNombre.setText("Campo Obligatorio.");
            return;
        }
        // 00144723 - Obtener el nombre del facilitador del campo de texto
        String nombre = txtNombreFacilitador.getText();
        // 00144723 - Consulta SQL para insertar un nuevo facilitador
        String query = "INSERT INTO facilitadores(nombre) VALUES (?)";

        try {
            // 00144723 - Establecimiento de conexión con la base de datos
            Connection connection = DriverManager.getConnection(
                    jdbcUrl,
                    usuario,
                    contrasena
            );
            // 00144723 - Preparación de la consulta SQL
            PreparedStatement ps = connection.prepareStatement(query);
            // 00144723 - Establecer el nombre del facilitador en la consulta
            ps.setString(1, nombre);
            // 00144723 - Ejecución de la consulta
            ps.executeUpdate();

            // 00144723 - Mostrar alerta de éxito
            mostrarAlerta("Registro Exitoso!", "Facilitador agregado exitosamente.");
            // 00144723 - Actualizar la lista de facilitadores en la tabla
            mostrarFacilitadores();
            // 00144723 - Limpiar el campo de texto del nombre del facilitador
            txtNombreFacilitador.clear();

        } catch (Exception e) {
            // 00144723 - Mostrar alerta en caso de error
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // 00144723 - Método para actualizar un facilitador existente
    private void actualizarFacilitador() {
        // 00144723 - Obtener el facilitador seleccionado en la tabla
        Facilitador facilitador = tblResultados.getSelectionModel().getSelectedItem();
        // 00144723 - Verificar si no hay facilitador seleccionado
        if (facilitador == null) {
            // 00144723 - Mostrar alerta de advertencia si no hay selección
            mostrarAlerta("Advertencia", "Por favor selecciona un facilitador.");
            return;
        }
        // 00144723 - Verificar si el campo de texto está vacío
        if (txtNombreFacilitador.getText().trim().isEmpty()) {
            // 00144723 - Establecer el estilo de la etiqueta de error a rojo
            lblErrorNombre.setStyle("-fx-text-fill: red");
            // 00144723 - Mostrar mensaje de error en la etiqueta
            lblErrorNombre.setText("Por favor, ingrese el nuevo nombre.");
            return;
        }

        // 00144723 - Limpiar el texto de la etiqueta de error
        lblErrorNombre.setText("");
        // 00144723 - Obtener el nuevo nombre del campo de texto
        String nombre = txtNombreFacilitador.getText();
        // 00144723 - Obtener el ID del facilitador seleccionado
        int id = facilitador.getId();
        // 00144723 - Consulta SQL para actualizar el nombre del facilitador
        String query = "UPDATE facilitadores SET nombre = ? WHERE id = ?";

        try {
            // 00144723 - Establecimiento de conexión con la base de datos
            Connection connection = DriverManager.getConnection(
                    jdbcUrl,
                    usuario,
                    contrasena
            );
            // 00144723 - Preparación de la consulta SQL
            PreparedStatement ps = connection.prepareStatement(query);
            // 00144723 - Establecer el nuevo nombre y el ID del facilitador en la consulta
            ps.setString(1, nombre);
            ps.setInt(2, id);
            // 00144723 - Ejecución de la consulta
            ps.executeUpdate();

            // 00144723 - Mostrar alerta de éxito
            mostrarAlerta("Actualización exitosa!", "Facilitador actualizado exitosamente.");
            // 00144723 - Actualizar la lista de facilitadores en la tabla
            mostrarFacilitadores();
            // 00144723 - Limpiar el campo de texto del nombre del facilitador
            txtNombreFacilitador.clear();
            // 00144723 - Cerrar la conexión con la base de datos
            connection.close();
        } catch (Exception e) {
            // 00144723 - Mostrar alerta en caso de error
            mostrarAlerta("Error", e.getMessage());
        }
    }

    // 00144723 - Método para mostrar una alerta en la interfaz
    private void mostrarAlerta(String titulo, String mensaje) {
        // 00144723 - Crear una nueva alerta de tipo información
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        // 00144723 - Establecer el título de la alerta
        alerta.setTitle(titulo);
        // 00144723 - Establecer el encabezado de la alerta como nulo
        alerta.setHeaderText(null);
        // 00144723 - Establecer el contenido de la alerta con el mensaje proporcionado
        alerta.setContentText(mensaje);
        // 00144723 - Mostrar la alerta y esperar a que el usuario la cierre
        alerta.showAndWait();
    }
}