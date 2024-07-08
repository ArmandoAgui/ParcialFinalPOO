package org.example.parcialfinalpoo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.parcialfinalpoo.Clases.Tarjeta;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TarjetasController implements Initializable {

    @FXML
    private TableView<Tarjeta> tblTarjetas;
    @FXML
    private TableColumn<Tarjeta, Integer> colId;
    @FXML
    private TableColumn<Tarjeta, String> colCliente;
    @FXML
    private TableColumn<Tarjeta, String> colNoTarjeta;
    @FXML
    private TableColumn<Tarjeta, String> colFacilitador;
    @FXML
    private TableColumn<Tarjeta, String> colFechaCaducidad;
    @FXML
    private TableColumn<Tarjeta, String> colTipo;
    @FXML
    private TextField txtNoCompleto;
    @FXML
    private ComboBox<String> comboBoxTipo;
    @FXML
    private ComboBox<String> comboBoxFacilitador;
    @FXML
    private TextField txtBuscarIDTarjeta;
    @FXML
    private TextField txtFechaCaducidad;
    @FXML
    private TextField txtClienteId;
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

    private ObservableList<Tarjeta> listaTarjetas;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal";
    private final String usuario = "sa";
    private final String contrasena = "12345678";

    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colNoTarjeta.setCellValueFactory(new PropertyValueFactory<>("NoTarjeta"));
        colFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        listaTarjetas = FXCollections.observableArrayList();
        tblTarjetas.setItems(listaTarjetas);

        cargarTarjetas();

        btnAgregar.setOnAction(event -> agregarTarjeta());
        btnActualizar.setOnAction(event -> actualizarTarjeta());
        btnEliminar.setOnAction(event -> eliminarTarjeta());
        btnLimpiar.setOnAction(event -> limpiarCampos());
        btnBuscar.setOnAction(event -> buscarTarjetaPorId());

        tblTarjetas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatosCliente(newValue);
            }
        });
    }

    private void cargarTarjetas() {
        String consultaSQL = "SELECT t.id, t.numeroTarjeta AS NoTarjeta, t.fechaExpiracion AS fechaCaducidad, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, t.clienteId " +
                "FROM TARJETAS t " +
                "INNER JOIN Facilitadores f ON t.facilitadorId = f.id";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             Statement statement = conn.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            listaTarjetas.clear();

            while (resultado.next()) {
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getInt("id"),
                        resultado.getString("clienteId"),
                        resultado.getString("NoTarjeta"),
                        resultado.getString("Facilitador"),
                        resultado.getString("fechaCaducidad"),
                        resultado.getString("TipoTarjeta")
                );
                listaTarjetas.add(tarjeta);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al cargar tarjetas: " + e.getMessage());
        }
    }

    private void agregarTarjeta() {
        String numeroTarjeta = txtNoCompleto.getText();
        String fechaExpiracion = txtFechaCaducidad.getText();
        String tipo = comboBoxTipo.getSelectionModel().getSelectedItem();
        String facilitador = comboBoxFacilitador.getSelectionModel().getSelectedItem();
        int clienteId = Integer.parseInt(txtClienteId.getText());

        String consultaSQL = "INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) " +
                "VALUES (?, ?, ?, (SELECT id FROM Facilitadores WHERE nombre = ?), ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaExpiracion);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setInt(5, clienteId);
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Tarjeta agregada exitosamente.");
            cargarTarjetas();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al agregar tarjeta: " + e.getMessage());
        }
    }

    private void actualizarTarjeta() {
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();
        if (tarjetaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor selecciona una tarjeta para actualizar.");
            return;
        }

        String numeroTarjeta = txtNoCompleto.getText();
        String fechaExpiracion = txtFechaCaducidad.getText();
        String tipo = comboBoxTipo.getSelectionModel().getSelectedItem();
        String facilitador = comboBoxFacilitador.getSelectionModel().getSelectedItem();
        int clienteId = Integer.parseInt(txtClienteId.getText());

        String consultaSQL = "UPDATE Tarjetas SET numeroTarjeta = ?, fechaExpiracion = ?, tipo = ?, facilitadorId = (SELECT id FROM Facilitadores WHERE nombre = ?), clienteId = ? " +
                "WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaExpiracion);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setInt(5, clienteId);
            statement.setInt(6, tarjetaSeleccionada.getId());
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Tarjeta actualizada exitosamente.");
            cargarTarjetas();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al actualizar tarjeta: " + e.getMessage());
        }
    }

    private void eliminarTarjeta() {
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();
        if (tarjetaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor selecciona una tarjeta para eliminar.");
            return;
        }

        String consultaSQL = "DELETE FROM Tarjetas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, tarjetaSeleccionada.getId());
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Tarjeta eliminada exitosamente.");
            cargarTarjetas();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar tarjeta: " + e.getMessage());
        }
    }

    private void buscarTarjetaPorId() {
        int idTarjeta = Integer.parseInt(txtBuscarIDTarjeta.getText());
        String consultaSQL = "SELECT t.id, t.numeroTarjeta AS NumeroTarjeta, t.fechaExpiracion, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, t.clienteId " +
                "FROM TARJETAS t " +
                "INNER JOIN Facilitadores f ON t.facilitadorId = f.id " +
                "WHERE t.id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, idTarjeta);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getInt("id"),
                        resultado.getString("clienteId"),
                        resultado.getString("numeroTarjeta"),
                        resultado.getString("Facilitador"),
                        resultado.getString("fechaCaducidad"),
                        resultado.getString("TipoTarjeta")
                );
                listaTarjetas.clear();
                listaTarjetas.add(tarjeta);
            } else {
                mostrarAlerta("Información", "No se encontró ninguna tarjeta con el ID especificado.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al buscar tarjeta: " + e.getMessage());
        }
    }

    private void cargarDatosCliente(Tarjeta tarjeta) {
        txtNoCompleto.setText(tarjeta.getNoTarjeta());
        txtFechaCaducidad.setText(tarjeta.getFechaCaducidad());
        comboBoxTipo.getSelectionModel().select(tarjeta.getTipo());
        comboBoxFacilitador.getSelectionModel().select(tarjeta.getFacilitador());
        txtClienteId.setText(String.valueOf(tarjeta.getClienteId()));

        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
    }

    private void limpiarCampos() {
        txtNoCompleto.clear();
        txtFechaCaducidad.clear();
        comboBoxTipo.getSelectionModel().clearSelection();
        comboBoxFacilitador.getSelectionModel().clearSelection();
        txtClienteId.clear();
        txtBuscarIDTarjeta.clear();

        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
