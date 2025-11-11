package ru.skoblenkov.devicecontrol.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ru.skoblenkov.devicecontrol.model.DeviceData;
import ru.skoblenkov.devicecontrol.service.DatabaseService;
import ru.skoblenkov.devicecontrol.service.UsbDeviceService;
import ru.skoblenkov.devicecontrol.utils.FxUtils;

import java.sql.SQLException;

public class MainController {
    @FXML
    private Label statusLabel;

    @FXML
    private TextArea dataTextArea;

    private final UsbDeviceService usbService = new UsbDeviceService();
    private final DatabaseService dbService = new DatabaseService();

    private String lastData = null;

    @FXML
    private void handleReadUsb() {
        FxUtils.runAsync(() -> {
            try {
                String data = usbService.readFromDevice();
                lastData = data;
                FxUtils.runOnFxThread(() -> {
                    dataTextArea.setText(data);
                    statusLabel.setText("Status: Data read successfully");
                });
            } catch (Exception e) {
                FxUtils.runOnFxThread(() ->
                        statusLabel.setText("Status: USB Error – " + e.getMessage())
                );
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleSaveToDb() {
        if (lastData == null || lastData.isBlank()) {
            statusLabel.setText("Status: No data to save!");
            return;
        }

        FxUtils.runAsync(() -> {
            try {
                dbService.saveData(new DeviceData(lastData));
                FxUtils.runOnFxThread(() ->
                        statusLabel.setText("Status: Data saved to PostgreSQL!")
                );
            } catch (SQLException e) {
                FxUtils.runOnFxThread(() ->
                        statusLabel.setText("Status: DB Error – " + e.getMessage())
                );
                e.printStackTrace();
            }
        });
    }
}
