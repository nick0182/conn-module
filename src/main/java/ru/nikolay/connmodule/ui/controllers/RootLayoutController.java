package ru.nikolay.connmodule.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.msg.*;
import ru.nikolay.connmodule.device.connect.ModBusConnect;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class RootLayoutController {

    private ModBusConnect modBusConnect;
    public final String IP = "192.168.0.201";
    public final String PORT = "502";

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
    private WriteCoilRequest request = new WriteCoilRequest(0, true);

    @PostConstruct
    private void init() {

        fieldIP.setText(IP);
        portField.setText(PORT);

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

        startToggleButton.setOnMouseClicked(event -> {
            if (modBusConnect.isConnected()) {
                try {
                    int count = Integer.valueOf(countAmountLabel.getText());
                    long delay = Long.valueOf(delayAmountLabel.getText());
                    Thread t = new Thread(() -> {
                        for (int i = 0; i < count; i++) {
                            try {
                                WriteCoilResponse response = (WriteCoilResponse) modBusConnect.request(request);
                                System.out.println(response.getCoil());
                                Thread.sleep(delay);
                            } catch (InterruptedException | ModbusException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    @PreDestroy
    private void stop() {
        modBusConnect.disconnect();
    }
}
