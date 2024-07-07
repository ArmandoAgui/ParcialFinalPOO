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
    private TextField idRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField mesRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField anioRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField idRepoC; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte C
    @FXML
    private Label facilitadorRepoD; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte D


    //00144723 - Se crea un metodo para mostrar una alerta
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00144723 - se crea una alerta de informacion
        alert.setTitle(titulo); // 00144723 - se le asigna un titulo
        alert.setHeaderText(null); // 00144723 - se le elimina el encabezado a la alerta
        alert.setContentText(mensaje); // 00144723 - se le asigna un mensaje que deberá mostrar
        alert.showAndWait(); //00144723 - muestra la alerta y se cerrará hasta que el usuario lo decida
    }
}