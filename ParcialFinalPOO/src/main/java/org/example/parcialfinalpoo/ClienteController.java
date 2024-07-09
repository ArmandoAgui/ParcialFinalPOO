package org.example.parcialfinalpoo;

// 00174323: Importa las colecciones observables de JavaFX.
import javafx.collections.FXCollections;
// 00174323: Importa la clase ObservableList de JavaFX.
import javafx.collections.ObservableList;
// 00174323: Importa las anotaciones y clases necesarias de JavaFX.
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
// 00174323: Importa las clases de control de interfaz gráfica de JavaFX.
import javafx.scene.control.*;
// 00174323: Importa la clase PropertyValueFactory para configurar las columnas de la tabla.
import javafx.scene.control.cell.PropertyValueFactory;
// 00174323: Importa la clase Cliente del paquete org.example.parcialfinalpoo.Clases.
import org.example.parcialfinalpoo.Clases.Cliente;
// 00174323: Importa las clases necesarias para la conexión a la base de datos y manejo de recursos.
import java.net.URL;
// Importar clases para manejo de bases de datos y SQL
import java.sql.*;
// 00174323: Importa la clase ResourceBundle para la internacionalización.
import java.util.ResourceBundle;

// 00174323: Define la clase ClienteController que implementa la interfaz Initializable.
public class ClienteController implements Initializable {
    // 00174323: Define los elementos de la interfaz gráfica con sus respectivas anotaciones FXML.
    @FXML
    private TextField txtNombreCompleto;  //00174323 Campo de texto para el nombre completo del cliente.
    @FXML
    private TextField txtDireccion;  //00174323 Campo de texto para la dirección del cliente.
    @FXML
    private TextField txtTelefono;  //00174323 Campo de texto para el teléfono del cliente.
    @FXML
    private TextField txtDui;  //00174323 Campo de texto para buscar con el dui al cliente.
    @FXML
    private TextField txtBuscarDui;  //00174323 Campo de texto para el dui del cliente.
    @FXML
    private TableView<Cliente> tblClientes;  //00174323 Tabla para mostrar la lista de clientes.
    @FXML
    private TableColumn<Cliente, Integer> colId;  //00174323 Columna para el ID del cliente.
    @FXML
    private TableColumn<Cliente, String> colNombreCompleto;  //00174323 Columna para el nombre completo del cliente.
    @FXML
    private TableColumn<Cliente, String> colDireccion;  //00174323 Columna para la dirección del cliente.
    @FXML
    private TableColumn<Cliente, String> colTelefono;  //00174323 Columna para el teléfono del cliente.
    @FXML
    private TableColumn<Cliente, String> colDui;  //00174323 Columna para el dui del cliente.
    @FXML
    private Button btnAgregar;  //00174323 Botón para agregar un cliente.
    @FXML
    private Button btnActualizar;  //00174323 Botón para actualizar un cliente.
    @FXML
    private Button btnEliminar;  //00174323 Botón para eliminar un cliente.
    @FXML
    private Button btnLimpiar;  //00174323 Botón para limpiar los campos de texto.
    @FXML
    private Button btnBuscar;  //00174323 Botón para buscar a los clientes mediante el dui.

    // 00174323: Define las constantes para la conexión a la base de datos.
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal";  // URL de la base de datos.
    private static final String usuario = "root";  // Nombre de usuario de la base de datos.
    private static final String contrasena = "root1234";  // Contraseña de la base de datos.

    // 00174323: Define una lista observable para almacenar los clientes.
    private ObservableList<Cliente> listaClientes;

    // 00174323: Sobrescribe el método initialize para configurar la interfaz al inicializar.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 00174323: Configura las columnas de la tabla con las propiedades de la clase Cliente.
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); // 00174323: Configura la columna colId para que use la propiedad "id" del modelo Cliente.
        colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto")); // 00174323: Configura la columna colNombreCompleto para que use la propiedad "nombreCompleto" del modelo Cliente.
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion")); // 00174323: Configura la columna colDireccion para que use la propiedad "direccion" del modelo Cliente.
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono")); // 00174323: Configura la columna colTelefono para que use la propiedad "telefono" del modelo Cliente.
        colDui.setCellValueFactory(new PropertyValueFactory<>("dui")); // 00174323: Configura la columna colDui para que use la propiedad "dui" del modelo Cliente.
        // 00174323: Inicializa la lista observable
        listaClientes = FXCollections.observableArrayList();
        tblClientes.setItems(listaClientes); // 00174323 Asocia con la tabla la lista.

        // 00174323: Carga los clientes desde la base de datos.
        cargarClientes();

        // 00174323: Configura los manejadores de eventos para los botones.
        // 00174323: Configura el manejador de eventos del botón btnAgregar para llamar al método agregarCliente() al hacer clic.
        btnAgregar.setOnAction(event -> agregarCliente());
        // 00174323: Configura el manejador de eventos del botón btnActualizar para llamar al método actualizarCliente() al hacer clic.
        btnActualizar.setOnAction(event -> actualizarCliente());
        // 00174323: Configura el manejador de eventos del botón btnEliminar para llamar al método eliminarCliente() al hacer clic.
        btnEliminar.setOnAction(event -> eliminarCliente());
        // 00174323: Configura el manejador de eventos del botón btnLimpiar para llamar al método limpiarCampos() al hacer clic.
        btnLimpiar.setOnAction(event -> limpiarCampos());
        // 00174323: Configura el manejador de eventos del botón btnBuscar para llamar al método buscarClientePorDui() al hacer clic.
        btnBuscar.setOnAction(even -> buscarClientePorDui());
        // 00174323: Agrega un listener para cargar los datos del cliente seleccionado.
        tblClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { //00174323 Verifica si aun hay datos
                cargarDatosCliente(newValue); // 00174323 carga otro cliente
            }
        });
    }

    // 00174323: Método para cargar los clientes desde la base de datos.
    private void cargarClientes() {
        // 00174323: Define la consulta SQL para obtener los datos de los clientes.
        String consultaSQL = "SELECT c.id, c.nombreCompleto, c.direccion, c.telefono, c.dui FROM Clientes c";
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             Statement statement = conexion.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            // 00174323: Limpia la lista de clientes antes de cargar los nuevos.
            listaClientes.clear();
            // 00174323: Itera sobre los resultados de la consulta y agrega cada cliente a la lista.
            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getInt("id"),
                        resultado.getString("nombreCompleto"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono"),
                        resultado.getString("dui")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            // 00174323: Muestra una alerta en caso de error al cargar los clientes.
            mostrarAlerta("Error", "Error al cargar clientes: " + e.getMessage());
        }
    }

    // 00174323: Método para cargar los datos del cliente seleccionado en los campos de texto.
    private void cargarDatosCliente(Cliente cliente) {
        // 00174323: Llena los campos de texto con los datos del cliente seleccionado.
        txtNombreCompleto.setText(cliente.getNombreCompleto());
        txtDireccion.setText(cliente.getDireccion());
        txtTelefono.setText(cliente.getTelefono());
        // 00174323: Habilita y deshabilita los botones correspondientes.
        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
        btnAgregar.setDisable(true);
    }

    // 00174323: Método para agregar un nuevo cliente a la base de datos.
    private void agregarCliente() {
        // 00174323: Obtiene los valores de los campos de texto.
        String nombreCompleto = txtNombreCompleto.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String dui = txtDui.getText();

        // 00174323: Define la consulta SQL para insertar un nuevo cliente.
        String consultaSQL = "INSERT INTO Clientes (nombreCompleto, direccion, telefono, dui) VALUES (?, ?, ?, ?)";
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            // 00174323: Asigna los valores a los parámetros de la consulta.
            statement.setString(1, nombreCompleto);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, dui);

            // 00174323: Ejecuta la consulta de inserción y recarga la lista de clientes.
            statement.executeUpdate();
            cargarClientes();
            limpiarCampos();
        } catch (SQLException e) {
            // 00174323: Muestra una alerta en caso de error al agregar el cliente.
            mostrarAlerta("Error", "Error al agregar cliente: " + e.getMessage());
        }
    }

    // 00174323: Método para actualizar un cliente en la base de datos.
    private void actualizarCliente() {
        // 00174323: Obtiene el cliente seleccionado en la tabla.
        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            // 00174323: Muestra una alerta si no hay cliente seleccionado.
            mostrarAlerta("Error", "Debe seleccionar un cliente para actualizar.");
            return;
        }

        // 00174323: Obtiene los valores de los campos de texto.
        String nombreCompleto = txtNombreCompleto.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        String dui = txtDui.getText();
        // 00174323: Define la consulta SQL para actualizar un cliente.
        String consultaSQL = "UPDATE Clientes SET nombreCompleto = ?, direccion = ?, telefono = ?, dui = ? WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            // 00174323: Asigna los valores a los parámetros de la consulta.
            statement.setString(1, nombreCompleto);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, dui);
            statement.setInt(5, clienteSeleccionado.getId());

            // 00174323: Ejecuta la consulta de actualización y recarga la lista de clientes.
            statement.executeUpdate();
            cargarClientes();
            limpiarCampos();
            // 00174323: Habilita y deshabilita los botones correspondientes.
            btnActualizar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(false);
        } catch (SQLException e) {
            // 00174323: Muestra una alerta en caso de error al actualizar el cliente.
            mostrarAlerta("Error", "Error al actualizar cliente: " + e.getMessage());
        }
    }

    // 00174323: Método para eliminar un cliente de la base de datos.
    private void eliminarCliente() {
        // 00174323: Obtiene el cliente seleccionado en la tabla.
        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            // 00174323: Muestra una alerta si no hay cliente seleccionado.
            mostrarAlerta("Error", "Debe seleccionar un cliente para eliminar.");
            return;
        }

        // 00174323: Define la consulta SQL para eliminar un cliente.
        String consultaSQL = "DELETE FROM Clientes WHERE id = ?";
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            // 00174323: Asigna el valor del ID al parámetro de la consulta.
            statement.setInt(1, clienteSeleccionado.getId());
            // 00174323: Ejecuta la consulta de eliminación y recarga la lista de clientes.
            statement.executeUpdate();
            cargarClientes();
            limpiarCampos();
            // 00174323: Habilita y deshabilita los botones correspondientes.
            btnActualizar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(false);
        } catch (SQLException e) {
            // 00174323: Muestra una alerta en caso de error al eliminar el cliente.
            mostrarAlerta("Error", "Error al eliminar cliente: " + e.getMessage());
        }
    }

    // Método para buscar un cliente por Dui
    private void buscarClientePorDui() {
        String duiBuscado = txtBuscarDui.getText();
        if (duiBuscado.isEmpty()) {
            cargarClientes();
            return;
        }

        String consultaSQL = "SELECT c.id, c.nombreCompleto, c.direccion, c.telefono, c.dui FROM Clientes c WHERE c.dui = ?";
        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            statement.setString(1, duiBuscado);
            ResultSet resultado = statement.executeQuery();

            listaClientes.clear();
            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getInt("id"),
                        resultado.getString("nombreCompleto"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono"),
                        resultado.getString("dui")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al buscar cliente: " + e.getMessage());
        }
    }

    // 00174323: Método para limpiar los campos de texto.
    private void limpiarCampos() {
        // 00174323: Limpia los campos de texto.
        txtNombreCompleto.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }

    // 00174323: Método auxiliar para mostrar alertas.
    private void mostrarAlerta(String titulo, String mensaje) {
        // 00174323: Crea y configura una alerta con el título y mensaje proporcionados.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        // 00174323: Muestra la alerta y espera a que el usuario la cierre.
        alert.showAndWait();
    }
}
