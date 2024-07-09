package org.example.parcialfinalpoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
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

    public void actualizarTarjeta(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateTarjetas.fxml"));
            Parent creditsRoot = loader.load();

            Stage creditsStage = new Stage();
            creditsStage.setTitle("Update");
            creditsStage.setScene(new Scene(creditsRoot));
            creditsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void actualizarTransaccion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateTransaccion.fxml"));
            Parent creditsRoot = loader.load();

            Stage creditsStage = new Stage();
            creditsStage.setTitle("Transaccion");
            creditsStage.setScene(new Scene(creditsRoot));
            creditsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}