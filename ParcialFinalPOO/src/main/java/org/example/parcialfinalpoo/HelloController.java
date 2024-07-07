package org.example.parcialfinalpoo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
    /*Faltan rangos de fechas

     */
    @FXML
    private TextField idRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField mesRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField anioRepoB; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte B
    @FXML
    private TextField idRepoC; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte C
    @FXML
    private TextField facilitadorRepoD; //00116223 -> Se le asigna el nombre del elemento de JavaFX dentro del controller. Campo que solicitara en generador de Reporte D

    @FXML
    private void actualizarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCliente.fxml"));
            Parent creditsRoot = loader.load();

            Stage creditsStage = new Stage();
            creditsStage.setTitle("Update");
            creditsStage.setScene(new Scene(creditsRoot));
            creditsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}