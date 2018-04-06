package ru.nikolay.connmodule.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import ru.nikolay.connmodule.device.connect.ModBusConnect;

import javax.annotation.PostConstruct;

public class RootLayoutController {

    private ModBusConnect modBusConnect;

    @FXML
    private TextField fieldIP;
    @FXML
    private TextField portField;
    @FXML
    private Button connectBtn;
    @FXML
    private Label connectLabel;

    @FXML
    private Label flowMeterLabel;
    @FXML
    private Label flowMeterVersionLabel;

    @FXML
    private Label delayLabel;
    @FXML
    private TextField delayAmountLabel;
    @FXML
    private Label countLabel;
    @FXML
    private TextField countAmountLabel;

    @FXML
    private ToggleButton startToggleButton;

    //Read FlowMeterVersion
    private ReadInputRegistersRequest flowVersion = new ReadInputRegistersRequest(0, 1);

    @PostConstruct
    private void init() {

        modBusConnect = new ModBusConnect();

        connectBtn.setOnMouseClicked(event -> {
            if (modBusConnect.connect(fieldIP.getText(), portField.getText())) {
                connectLabel.setText("connected");
                try {
                    ReadInputRegistersResponse flowVersionResp = (ReadInputRegistersResponse) modBusConnect.request(flowVersion);
                    flowMeterVersionLabel.setText(String.valueOf(flowVersionResp.getRegister(0).getValue()));
                } catch (ModbusException e) {
                    System.err.println("unlucky");
                }

            } else {
                connectLabel.setText("disconnected");
            }
        });
    }

    @FXML
    private void startBtnOnClick() {
        if (!isConnected()) {
            connectLabel.setText("please connect");
            startToggleButton.setSelected(false);
            return;
        }
    }

    private boolean isConnected() {
        return modBusConnect.isConnected();
    }
}
