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
    private TableView<Tarjeta> tblTarjetas; //00116223 -> Declaracion de elemento de FXML, que almacenera todas las tarjertas.
    @FXML
    private TableColumn<Tarjeta, Integer> colId; //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de idTarjetas.
    @FXML
    private TableColumn<Tarjeta, String> colCliente;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de cliente.
    @FXML
    private TableColumn<Tarjeta, String> colNoTarjeta;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de noTarjetas.
    @FXML
    private TableColumn<Tarjeta, String> colFacilitador;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de facilitador.
    @FXML
    private TableColumn<Tarjeta, String> colFechaCaducidad;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de fecha caducidad.
    @FXML
    private TableColumn<Tarjeta, String> colTipo;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de tipo tarjeta..
    @FXML
    private TextField   txtNoCompleto;  //00116223 -> Declaracion de elemento de FXML, que almacenera la columna de numero completo.
    @FXML
    private ComboBox<String> comboBoxTipo;  //00116223 -> Declaracion de elemento de FXML, que almacenera los tipos de tarjetaas.
    @FXML
    private ComboBox<String> comboBoxFacilitador; //00116223 -> Declaracion de elemento de FXML, que almacenera los facilitadores cargados desde bd Facilitadores.
    @FXML
    private TextField txtBuscarIDTarjeta;
    @FXML
    private TextField txtFechaCaducidad;
    @FXML
    private TextField txtClienteId;
    @FXML
    private Button btnAgregar;  //00116223 -> Boton agregar tarjetas
    @FXML
    private Button btnActualizar;  //00116223 -> Boton actualizar tarjeta
    @FXML
    private Button btnEliminar;  //00116223 -> Boton elimitar tarjeta
    @FXML
    private Button btnLimpiar;  //00116223 -> Boton limpair campos
    @FXML
    private Button btnBuscar;  //00116223 -> Boton buscar tarjeta

    private ObservableList<Tarjeta> listaTarjetas;

    private final String jdbcUrl = "jdbc:mysql://localhost:3306/parcialFinal";  //00116223 -> Url conexion a BD
    private final String usuario = "root"; //00116223 -> user conexion a BD
    private final String contrasena = "root1234"; //00116223 -> password conexion a BD

    public void initialize(URL location, ResourceBundle resources) {
        //00116223 -> seteo de tableview con info de tarjetas.
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colNoTarjeta.setCellValueFactory(new PropertyValueFactory<>("NoTarjeta"));
        colFacilitador.setCellValueFactory(new PropertyValueFactory<>("facilitador"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        listaTarjetas = FXCollections.observableArrayList();
        //00116223 -> agregacion de tarjetas
        tblTarjetas.setItems(listaTarjetas);

        cargarFacilitadores(); //00116223 -> carga de facilitadores en checkbox
        cargarTipos(); //00116223 -> carga de tipos de tarjeta en checkbox
        cargarTarjetas(); //00116223 -> carga de base de datos en listView

        btnAgregar.setOnAction(event -> agregarTarjeta()); //00116223 -> Metodo que hace el boton agregar
        btnActualizar.setOnAction(event -> actualizarTarjeta()); //00116223 -> Metodo que hace el boton actualizar
        btnEliminar.setOnAction(event -> eliminarTarjeta()); //00116223 -> Metodo que hace el boton eliminar
        btnLimpiar.setOnAction(event -> limpiarCampos()); //00116223 -> Metodo que hace el boton limpiar
        btnBuscar.setOnAction(event -> buscarTarjetaPorId()); //00116223 -> Metodo que hace el boton buscar

        // 00116223 -> Agrega un listener para cargar los datos de tarjeta seleccionado.
        tblTarjetas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { //00116223 -> Si hay una tarjeta seleccionada
                cargarDatosCliente(newValue); //00116223 -> Carga de datos
            }
        });
    }

    private void cargarFacilitadores() {
        String consultaSQL = "SELECT nombre FROM Facilitadores"; //00116223 -> Consulta SQL

        try (
                //00116223 -> Gestion conexion de base de datos.
                Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
                Statement statement = conexion.createStatement(); //00116223 -> Proceso bd.
                ResultSet resultado = statement.executeQuery(consultaSQL) //00116223 -> Proceso bd.
        ) {
            while (resultado.next()) {
                String nombreFacilitador = resultado.getString("nombre"); //00116223 -> Presenta los faciltiadores en bd.
                //00116223 -> Inserta faciltiadores en el comboBox.
                comboBoxFacilitador.getItems().add(nombreFacilitador);
            }
        } catch (SQLException e) {
            //00116223 -> Gestion de error.
            mostrarAlerta("Error", "Error al cargar facilitadores: " + e.getMessage());
        }
    }

    private void cargarTipos(){
        //00116223 -> Insercion de tipos de tarjeta de credito ddentro de comboBox.
        comboBoxTipo.getItems().add("Credito");
        comboBoxTipo.getItems().add("Debito");
    }

    private void cargarTarjetas() {
        //00116223 -> Consulta SQL
        String consultaSQL = "SELECT t.id, t.numeroTarjeta AS NoTarjeta, t.fechaExpiracion AS fechaCaducidad, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, t.clienteId " +
                "FROM TARJETAS t " +
                "INNER JOIN Facilitadores f ON t.facilitadorId = f.id";

        //00116223 -> Conexion a base de datos.
        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
             Statement statement = conn.createStatement(); //00116223 -> Proceso bd.
             ResultSet resultado = statement.executeQuery(consultaSQL)) { //00116223 -> Proceso bd.

            //00116223 -> Limpia lista de tarjetas.
            listaTarjetas.clear();

            while (resultado.next()) {
                //00116223 -> Creacion de un nuevo objeto tipo Tarjeta, llamado tarjeta que se mostraran los valores desde la base de datos.
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getInt("id"),
                        resultado.getString("clienteId"),
                        resultado.getString("NoTarjeta"),
                        resultado.getString("Facilitador"),
                        resultado.getString("fechaCaducidad"),
                        resultado.getString("TipoTarjeta")
                );
                //00116223 -> Agregacion del objeto dentro de la lista tarjetas.
                listaTarjetas.add(tarjeta);
            }
        } catch (SQLException e) {
            //00116223 -> Gestion de errores.
            mostrarAlerta("Error", "Error al cargar tarjetas: " + e.getMessage());
        }
    }

    private void agregarTarjeta() {
        //00116223 -> Toma de datos desde la interfaz.
        String numeroTarjeta = txtNoCompleto.getText();
        String fechaExpiracion = txtFechaCaducidad.getText();
        String tipo = comboBoxTipo.getSelectionModel().getSelectedItem();
        String facilitador = comboBoxFacilitador.getSelectionModel().getSelectedItem();
        int clienteId = Integer.parseInt(txtClienteId.getText());

        //00116223 -> Consulta de SQL y conexion a bd.
        String consultaSQL = "INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) " +
                "VALUES (?, ?, ?, (SELECT id FROM Facilitadores WHERE nombre = ?), ?)";

        //00116223 -> Conexion a base de datos.
        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { //00116223 -> Proceso bd.

            //00116223 -> Los "parameter index" se setean en el lugar de los  "?" segun por orden.
            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaExpiracion);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setInt(5, clienteId);
            statement.executeUpdate(); //00116223 -> Proceso bd.

            //00116223 -> Salta alerta, si se concreta la insercion de tarjetas.
            mostrarAlerta("Éxito", "Tarjeta agregada exitosamente.");
            cargarTarjetas();         //00116223 -> Llamado de metodo de cargar Tarjeta.
            limpiarCampos();         //00116223 -> Limpia datos que anteriormente se ingresaron.
        } catch (SQLException e) {
            //00116223 -> Gestion de errores.
            mostrarAlerta("Error", "Error al agregar tarjeta: " + e.getMessage());
        }
    }

    private void actualizarTarjeta() {
        //00116223 -> Se lee la opcion que fue seleccionada por el usuario, y la almacena.
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();
        //00116223 -> Si no se ha seleccionado, antes de tocar boton, salta alerta.
        if (tarjetaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor selecciona una tarjeta para actualizar.");
            return;
        }

        //00116223 -> Toma de  todos los nuevos datos desde la interfaz.
        String numeroTarjeta = txtNoCompleto.getText();
        String fechaExpiracion = txtFechaCaducidad.getText();
        String tipo = comboBoxTipo.getSelectionModel().getSelectedItem();
        String facilitador = comboBoxFacilitador.getSelectionModel().getSelectedItem();
        int clienteId = Integer.parseInt(txtClienteId.getText());

        //00116223 -> Consulta de SQL y conexion a bd.
        String consultaSQL = "UPDATE Tarjetas SET numeroTarjeta = ?, fechaExpiracion = ?, tipo = ?, facilitadorId = (SELECT id FROM Facilitadores WHERE nombre = ?), clienteId = ? " +
                "WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { //00116223 -> Proceso bd.

            //00116223 -> Los "parameter index" se setean en el lugar de los  "?" segun por orden.
            statement.setString(1, numeroTarjeta);
            statement.setString(2, fechaExpiracion);
            statement.setString(3, tipo);
            statement.setString(4, facilitador);
            statement.setInt(5, clienteId);
            statement.setInt(6, tarjetaSeleccionada.getId());
            statement.executeUpdate(); //00116223 -> Proceso bd.

            mostrarAlerta("Éxito", "Tarjeta actualizada exitosamente."); // 00116223-> Muestra alerta exitosa.
            cargarTarjetas(); //00116223 -> Llamado de metodo de cargar Tarjeta.
            limpiarCampos(); //00116223 -> Limpia datos que anteriormente se ingresaron.
        } catch (SQLException e) {
            //00116223 -> Gestion de errores.
            mostrarAlerta("Error", "Error al actualizar tarjeta: " + e.getMessage());
        }
    }

    private void eliminarTarjeta() {
        //00116223 -> Se lee la opcion que fue seleccionada por el usuario, y la almacena.
        Tarjeta tarjetaSeleccionada = tblTarjetas.getSelectionModel().getSelectedItem();
        if (tarjetaSeleccionada == null) {
            //00116223 -> Si no se ha seleccionado, antes de tocar boton, salta alerta.
            mostrarAlerta("Advertencia", "Por favor selecciona una tarjeta para eliminar.");
            return;
        }

        //00116223 -> Consulta de SQL y conexion a bd.
        String consultaSQL = "DELETE FROM Tarjetas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { //00116223 -> Proceso bd.

            //00116223 -> Los "parameter index" se setean en el lugar de los  "?" segun por orden.
            statement.setInt(1, tarjetaSeleccionada.getId());
            statement.executeUpdate(); //00116223 -> Proceso bd.

            mostrarAlerta("Éxito", "Tarjeta eliminada exitosamente.");
            cargarTarjetas(); //00116223 -> Llamado de metodo de cargar Tarjeta
            limpiarCampos(); //00116223 -> Limpia datos que anteriormente se ingresaron.
        } catch (SQLException e) {
            //00116223 -> Gestion de errores.
            mostrarAlerta("Error", "Error al eliminar tarjeta: " + e.getMessage());
        }
    }

    private void buscarTarjetaPorId() {
        //00116223 -> Se lee la opcion que fue seleccionada por el usuario, y la almacena.
        int idTarjeta = Integer.parseInt(txtBuscarIDTarjeta.getText());
        //00116223 -> Consulta de SQL y conexion a bd.
        String consultaSQL = "SELECT t.id, t.numeroTarjeta AS NumeroTarjeta, t.fechaExpiracion, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, t.clienteId " +
                "FROM TARJETAS t " +
                "INNER JOIN Facilitadores f ON t.facilitadorId = f.id " +
                "WHERE t.id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contrasena); //00116223 -> Proceso bd.
             PreparedStatement statement = conn.prepareStatement(consultaSQL)) { //00116223 -> Proceso bd.

            statement.setInt(1, idTarjeta); //00116223 -> Los "parameter index" se setean en el lugar de los  "?" segun por orden.
            ResultSet resultado = statement.executeQuery(); //00116223 -> Proceso bd.

            if (resultado.next()) {
                //00116223 -> Creacion de un nuevo objeto tipo Tarjeta con sus atributos que se mostraran los valores desde la base de datos.
                Tarjeta tarjeta = new Tarjeta(
                        resultado.getInt("id"),
                        resultado.getString("clienteId"),
                        resultado.getString("numeroTarjeta"),
                        resultado.getString("Facilitador"),
                        resultado.getString("fechaExpiracion"),
                        resultado.getString("TipoTarjeta")
                );
                listaTarjetas.clear();                 //00116223 -> Limpia los otros datos que almacena la listView.
                listaTarjetas.add(tarjeta);                 //00116223 -> Agregacion del objeto unico objeto que coincida con el idTarjeta dentro de la lista tarjetas.
            } else {
                //00116223 -> Salto de alerta. En caso de no coincidencias
                mostrarAlerta("Información", "No se encontró ninguna tarjeta con el ID especificado.");
            }
        } catch (SQLException e) {
            //00116223 -> Gestion de error.
            mostrarAlerta("Error", "Error al buscar tarjeta: " + e.getMessage());
        }
    }

    private void cargarDatosCliente(Tarjeta tarjeta) {
        //00116223 -> Seteo de TODOS las tarjetas que posee la base de datos dentro del ListView.
        txtNoCompleto.setText(tarjeta.getNoTarjeta());
        txtFechaCaducidad.setText(tarjeta.getFechaCaducidad());
        comboBoxTipo.getSelectionModel().select(tarjeta.getTipo());
        comboBoxFacilitador.getSelectionModel().select(tarjeta.getFacilitador());
        txtClienteId.setText(String.valueOf(tarjeta.getClienteId()));

        //00116223 -> Deshabilita los botones de actualizar y eliminar.
        btnActualizar.setDisable(false);
        btnEliminar.setDisable(false);
    }

    private void limpiarCampos() {
        //00116223 -> Limpia TODOS los campos dentro de la interfaz.
        txtNoCompleto.clear();
        txtFechaCaducidad.clear();
        comboBoxTipo.getSelectionModel().clearSelection();
        comboBoxFacilitador.getSelectionModel().clearSelection();
        txtClienteId.clear();
        txtBuscarIDTarjeta.clear();

        //00116223 -> Habilita los botones de actualizar y eliminar.
        btnActualizar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        //00116223 -> Crea una alerta.
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        //00116223 -> Titulo de la alerta.
        alerta.setTitle(titulo);
        //00116223 -> Setea el texto/header que poseera la alerta.
        alerta.setHeaderText(null);
        //00116223 -> Establece el contenido del mensaje de la alerta.
        alerta.setContentText(mensaje);
        //00116223 -> Muestra la alerta y espera a que el usuario la cierre.
        alerta.showAndWait();
    }
}
