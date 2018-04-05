package ru.nikolay.connmodule.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;

public class RootLayoutController {
    @FXML
    private TextField fieldIP;
    @FXML
    private TextField portField;
    @FXML
    private Button connectBtn;


    @FXML
    public void initialize() {
    }

    @PostConstruct
    private void init() {

        connectBtn.setOnAction(event -> System.err.println("HAY!"));
    }
}
