package org.example.parcialfinalpoo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import org.example.parcialfinalpoo.Clases.Transaccion;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransaccionController implements Initializable {

    @FXML
    private TableView<Transaccion> tblTransacciones;
    @FXML
    private TableColumn<Transaccion, Integer> colId;
    @FXML
    private TableColumn<Transaccion, Integer> colTarjetaId;
    @FXML
    private TableColumn<Transaccion, String> colFecha;
    @FXML
    private TableColumn<Transaccion, Double> colMonto;
    @FXML
    private TableColumn<Transaccion, String> colDescripcion;
    @FXML
    private TextField txtTarjetaId;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtMonto;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private Button btnAgregarTransaccion;
    @FXML
    private Button btnActualizarTransaccion;
    @FXML
    private Button btnEliminarTransaccion;
    @FXML
    private Button btnLimpiarTransaccion;

    private ObservableList<Transaccion> listaTransacciones;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal";
    private final String usuario = "sa";
    private final String contrasena = "12345678";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTarjetaId.setCellValueFactory(new PropertyValueFactory<>("tarjetaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        listaTransacciones = FXCollections.observableArrayList();
        tblTransacciones.setItems(listaTransacciones);

        cargarTransacciones();

        btnAgregarTransaccion.setOnAction(event -> agregarTransaccion());
        btnActualizarTransaccion.setOnAction(event -> actualizarTransaccion());
        btnEliminarTransaccion.setOnAction(event -> eliminarTransaccion());
        btnLimpiarTransaccion.setOnAction(event -> limpiarCampos());

        tblTransacciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarDatosTransaccion(newValue);
            }
        });
    }

    private void cargarTransacciones() {
        String consultaSQL = "SELECT tr.id, tr.fecha, tr.montoTotal, tr.descripcion, tr.tarjetaId FROM Transacciones tr";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             Statement statement = conn.createStatement();
             ResultSet resultado = statement.executeQuery(consultaSQL)) {

            listaTransacciones.clear();

            while (resultado.next()) {
                Transaccion transaccion = new Transaccion(
                        resultado.getInt("id"),
                        resultado.getString("fecha"),
                        resultado.getDouble("montoTotal"),
                        resultado.getString("descripcion"),
                        resultado.getInt("tarjetaId")
                );
                listaTransacciones.add(transaccion);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al cargar transacciones: " + e.getMessage());
        }
    }

    private void agregarTransaccion() {
        int tarjetaId = Integer.parseInt(txtTarjetaId.getText());
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String descripcion = txtDescripcion.getText();

        String consultaSQL = "INSERT INTO Transacciones (tarjetaId, fecha, montoTotal, descripcion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, tarjetaId);
            statement.setDate(2, Date.valueOf(fecha));
            statement.setDouble(3, monto);
            statement.setString(4, descripcion);
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Transacción agregada exitosamente.");
            cargarTransacciones();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al agregar transacción: " + e.getMessage());
        }
    }

    private void actualizarTransaccion() {
        Transaccion transaccionSeleccionada = tblTransacciones.getSelectionModel().getSelectedItem();
        if (transaccionSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, selecciona una transacción para actualizar.");
            return;
        }

        int id = transaccionSeleccionada.getId();
        int tarjetaId = Integer.parseInt(txtTarjetaId.getText());
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String descripcion = txtDescripcion.getText();

        // Validar formato de fecha
        if (!esFechaValida(fecha)) {
            mostrarAlerta("Error", "La fecha debe tener el formato yyyy-mm-dd.");
            return;
        }

        String consultaSQL = "UPDATE Transacciones SET tarjetaId = ?, fecha = ?, montoTotal = ?, descripcion = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, tarjetaId);
            statement.setDate(2, Date.valueOf(fecha));
            statement.setDouble(3, monto);
            statement.setString(4, descripcion);
            statement.setInt(5, id);
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Transacción actualizada exitosamente.");
            cargarTransacciones();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al actualizar transacción: " + e.getMessage());
        }
    }

    private boolean esFechaValida(String fecha) {
        try {
            Date.valueOf(fecha);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void eliminarTransaccion() {
        Transaccion transaccionSeleccionada = tblTransacciones.getSelectionModel().getSelectedItem();
        if (transaccionSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, selecciona una transacción para eliminar.");
            return;
        }

        int id = transaccionSeleccionada.getId();
        String consultaSQL = "DELETE FROM Transacciones WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) {

            statement.setInt(1, id);
            statement.executeUpdate();

            mostrarAlerta("Éxito", "Transacción eliminada exitosamente.");
            cargarTransacciones();
            limpiarCampos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar transacción: " + e.getMessage());
        }
    }

    private void cargarDatosTransaccion(Transaccion transaccion) {
        txtTarjetaId.setText(String.valueOf(transaccion.getTarjetaId()));
        txtFecha.setText(transaccion.getFecha());
        txtMonto.setText(String.valueOf(transaccion.getMontoTotal()));
        txtDescripcion.setText(transaccion.getDescripcion());

        btnActualizarTransaccion.setDisable(false);
        btnEliminarTransaccion.setDisable(false);
    }

    private void limpiarCampos() {
        txtTarjetaId.clear();
        txtFecha.clear();
        txtMonto.clear();
        txtDescripcion.clear();

        btnActualizarTransaccion.setDisable(true);
        btnEliminarTransaccion.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}