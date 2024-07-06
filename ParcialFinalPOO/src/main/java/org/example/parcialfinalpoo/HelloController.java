package org.example.parcialfinalpoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parcialfinalpoo.Clases.Cliente;
import org.example.parcialfinalpoo.Clases.Tarjeta;
import org.example.parcialfinalpoo.Clases.Transaccion;

import java.sql.*;
import java.time.LocalDate;

public class HelloController {
    @FXML
    private TextField nameField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.
    @FXML
    private TextField adressField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.
    @FXML
    private TextField phoneField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.
    @FXML
    private TextField numberCardField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.
    @FXML
    private TextField dateCardField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.
    @FXML
    private ComboBox<String> typeCardField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller. Con parametros "String" que almacenara el tipo de tarjeta.
    @FXML
    private ComboBox<String> companyCardField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller. Con parametros "String" que almacenara el facilitador.
    @FXML
    private TextField clientCardField; //00116223 -> Se le designa el nombre de los elementos de JavaFX dentro del controller.'
    @FXML
    private ListView<Transaccion> tarjetasListView; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Se encarga de presentar las tarjetas, con su informacion al momento de modificarla su informacion.
    @FXML
    private TextField deletedNameTextField; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Elemento utilizado dentro de la eliminacion de tarjetas
    @FXML
    private TextField deletedNoCardText; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Elemento utilizado en la eliminacion del numero de tarjeta
    @FXML
    private TextField idRepoA; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte A
    @FXML
    private DatePicker dateFechaInicioA;  //00144723 -> En este campo es donde se selecciona la fecha inicial para qenerar el reporte tipo A
    @FXML
    private DatePicker DateFechaFinA;  //00144723 -> En este campo es donde se selecciona la fecha fina para qenerar el reporte tipo A
    @FXML
    private Label lblErrorReporteA;  //00144723 -> Este label es para mostrar un mensaje de que uno o más campos estan vacios al querer generar un reporte.
    @FXML
    private TextField idRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField mesRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField anioRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField idRepoC; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte C
    @FXML
    private Label facilitadorRepoD; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte D


    //Reporte A.
    @FXML
    void generarReporteA(ActionEvent event) {
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
                        "jdbc:mysql://localhost:3306/parcialfinal",
                        "root",
                        "jlmilan");
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

                // 00144723 - Limpiamos el listview por si aun tiene datos almacenados ya que nosotros queremos mostrar nuestro reporte
                // y asi evitar errores.
                tarjetasListView.getItems().clear();

                //00144723 - Aqui iremos guardando los datos del cliente y de la transaccion realizada para poder mostrarlos posteriormente
                while (rs.next()) {
                    String nombre = rs.getString("Nombre");
                    String direccionCliente = rs.getString("Domicilio");
                    String telefonoCliente = rs.getString("Telefono");

                    Cliente cliente = new Cliente(nombre, direccionCliente, telefonoCliente);


                    String numTarjeta = rs.getString("Tarjeta");
                    String Facilitador = rs.getString("Facilitador");
                    String fechaExpiracion = rs.getString("FechaExpiracion");
                    String tipo = rs.getString("Tipo");

                    Tarjeta tarjeta = new Tarjeta(numTarjeta, fechaExpiracion, tipo, Facilitador, cliente);

                    String descripcion = rs.getString("Descripcion");
                    double montoTotal = rs.getDouble("MontoCompra");
                    Date fechaCompra = rs.getDate("FechaCompra");

                    Transaccion transaccion = new Transaccion(fechaCompra, montoTotal, descripcion, tarjeta);
                    tarjetasListView.getItems().add(transaccion);


                }

                conn.close();


            } catch (Exception e) {
                //00144723 - Aqui mostramos un mensaje de error y la excepcion que se salió pero en forma de mensaje
                System.out.println("Error al conectar la base de datos.");
                System.out.println(e.getMessage());
            }
        }

    }
}