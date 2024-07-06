package org.example.parcialfinalpoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parcialfinalpoo.Clases.Cliente;
import org.example.parcialfinalpoo.Clases.Tarjeta;
import org.example.parcialfinalpoo.Clases.Transaccion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private ListView<String> tarjetasListView; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Se encarga de presentar las tarjetas, con su informacion al momento de modificarla su informacion.
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
                        "sa",
                        "123456");
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
                File reporteA = new File("ReporteA");
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
    //00144723 - Se crea un metodo para mostrar una alerta
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00144723 - se crea una alerta de informacion
        alert.setTitle(titulo); // 00144723 - se le asigna un titulo
        alert.setHeaderText(null); // 00144723 - se le elimina el encabezado a la alerta
        alert.setContentText(mensaje); // 00144723 - se le asigna un mensaje que deberá mostrar
        alert.showAndWait(); //00144723 - muestra la alerta y se cerrará hasta que el usuario lo decida
    }
}