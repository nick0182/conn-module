package ru.nikolay.connmodule.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.nikolay.connmodule.device.connect.ModBusConnect;

import javax.annotation.PostConstruct;

public class RootLayoutController {

    private ModBusConnect modBusConnect;
    private String ip;
    private String port;

    @FXML
    private TextField fieldIP;
    @FXML
    private TextField portField;
    @FXML
    private Button connectBtn;
    @FXML
    private Label connectLabel;


    @FXML
    public void initialize() {
    }

    @PostConstruct
    private void init() {

        modBusConnect = new ModBusConnect();

        connectBtn.setOnAction(event -> {

                if(modBusConnect.connect(fieldIP.getText(), portField.getText())){
                    connectLabel.setText("connected");
                } else {
                    connectLabel.setText("disconnected");
                }

        });
    }
}
