package org.example.parcialfinalpoo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.parcialfinalpoo.Clases.Cliente;
import org.example.parcialfinalpoo.Clases.Tarjeta;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TarjetasController {
    //Campos ingreso de datos.
    @FXML
    private TextField txtNoCompleto;
    @FXML
    private TextField txtCaducidad;
    @FXML
    private ComboBox comboBoxTipo;
    @FXML
    private ComboBox comboBoxFacilitador;
    @FXML
    private TextField idClienteTarjetas;

    //Mostrar base de datos tarjetas
    @FXML
    private TableView<Tarjeta> tblTarjetas;
    @FXML
    private TableColumn<Tarjeta, Integer> colId;

    /**
     * Checkout
     **/
    @FXML
    private TableColumn<Cliente, String> colCliente;
    @FXML
    private TableColumn<Tarjeta, String> colNoTarjeta;
    @FXML
    private TableColumn<Tarjeta, String> colFacilitador;
    @FXML
    private TableColumn<Tarjeta, String> colFechaCaducidad;
    @FXML
    private TableColumn<Tarjeta, String> colTipo;

    /**
     * Checkout
     **/
    /*@FXML
    private TableColumn<Tarjeta, String> colDui;*/

    //Botones
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnBuscar;

    @FXML
    private TextField txtBuscarIDTarjeta;

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal";
    private static final String usuario = "root";
    private static final String contrasena = "root1234";

    private ObservableList<Tarjeta> listaTarjetas;

    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteID"));
        colNoTarjeta.setCellValueFactory(new PropertyValueFactory<>("numeroTarjeta"));
        colFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitadorID"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaExpiracion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        listaTarjetas = FXCollections.observableArrayList();
        tblTarjetas.setItems(listaTarjetas);

        cargarTarjetas();

        btnAgregar.setOnAction(event -> agregarTarjeta());
        btnActualizar.setOnAction(event -> actualizarTarjeta());
        btnEliminar.setOnAction(event -> eliminarTarjeta());
        btnLimpiar.setOnAction(event -> limpiarCampos());
        btnBuscar.setOnAction(even -> buscarTarjetaPorId());
        tblTarjetas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatosCliente(newValue);
            }
        });
    }

    private void agregarTarjeta() {
        String numeroTarjeta = txtNoCompleto.getText();
        String fechaCaducidad = txtCaducidad.getText();
        String tipo = comboBoxTipo.getValue().toString();
        String facilitador = comboBoxFacilitador.getValue().toString();
        String idCliente = idClienteTarjetas.getText();

        String consultaSQL = "INSERT INTO TARJETAS (numeroTarjeta, fechaExpiracion, tipo, facilitadorId,clienteId) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaCaducidad);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setString(5, idCliente);

            statement.executeUpdate();
            cargarTarjetas();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al agregar cliente: " + e.getMessage());
        }
    }

    private void actualizarTarjeta() {
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();

        if (tarjetaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar un cliente para actualizar.");
            return;
        }

        String numeroTarjeta = txtNoCompleto.getText();
        String fechaCaducidad = txtCaducidad.getText();
        String tipo = comboBoxTipo.getValue().toString();
        String facilitador = comboBoxFacilitador.getValue().toString();
        String idCliente = idClienteTarjetas.getText();

        String consultaSQL = "UPDATE TARJETAS SET numeroTarjeta = ?, fechaExpiracion = ?, tipo = ?, facilitadorID = ?, clienteId = ? WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaCaducidad);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setString(5, idCliente);
            statement.setInt(6, tarjetaSeleccionada.getIdCliente());

            statement.executeUpdate();
            cargarTarjetas();
            limpiarCampos();

            btnActualizar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(false);
        } catch (SQLException e) {

            mostrarAlerta("Error", "Error al actualizar cliente: " + e.getMessage());
        }
    }

    private void eliminarTarjeta() {
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();

        if (tarjetaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una tarjeta para eliminar.");
            return;
        }

        String consultaSQL = "DELETE FROM Tarjeta WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, tarjetaSeleccionada.getIdCliente());
            statement.executeUpdate();
            cargarTarjetas();
            limpiarCampos();
            btnActualizar.setDisable(true);
            btnEliminar.setDisable(true);
            btnAgregar.setDisable(false);
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar cliente: " + e.getMessage());
        }
    }

    private void cargarTarjetas() {
        String consultaSQL = "SELECT * FROM TARJETAS c";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             Statement statement = conn.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            listaTarjetas.clear();

            while (resultado.next()) {
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getString("numeroTarjeta"),
                        resultado.getString("fechaExpiracion"),
                        resultado.getString("tipo"),
                        resultado.getString("facilitadorId"),
                        resultado.getInt("clienteId")
                );
                listaTarjetas.add(tarjeta);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al cargar tarjetas: " + e.getMessage());
        }
    }

    private void buscarTarjetaPorId() {
        String idBuscado = txtBuscarIDTarjeta.getText();
        if (idBuscado.isEmpty()) {
            cargarTarjetas();
            return;
        }

        //String consultaSQL = "SELECT c.id, c.nombreCompleto, c.direccion, c.telefono, c.dui FROM TARJETAS T WHERE T.ID = ?";
        String consultaSQL = "SELECT t.id, t.numeroTarjeta, t.fechaExpiracion, t.tipo, f.nombre, c.nombreCompleto FROM TARJETAS t\n" +
                "INNER JOIN FACILITADORES f on t.facilitadorId = f.id\n" +
                "INNER JOIN CLIENTES c on t.facilitadorId = c.id WHERE t.id = ?";

        try (Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conexion.prepareStatement(consultaSQL)) {

            statement.setString(1, idBuscado);
            ResultSet resultado = statement.executeQuery();

            listaTarjetas.clear();
            while (resultado.next()) {
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getString("numeroTarjeta"),
                        resultado.getString("fechaExpiracion"),
                        resultado.getString("tipo"),
                        resultado.getString("facilitador"),
                        resultado.getInt("idCliente")
                );
                listaTarjetas.add(tarjeta);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al buscar cliente: " + e.getMessage());
        }
    }

    private void cargarDatosCliente(Tarjeta tarjeta) {
        txtNoCompleto.setText(tarjeta.getNumeroTarjeta());
        //txtDireccion.setText(cliente.getDireccion());
        //txtTelefono.setText(cliente.getTelefono());

        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
        btnAgregar.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNoCompleto.clear();
        //txtDireccion.clear();
        // txtTelefono.clear();
    }
}
