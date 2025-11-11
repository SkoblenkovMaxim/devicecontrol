package ru.skoblenkov.devicecontrol.service;

import com.fazecast.jSerialComm.SerialPort;

public class UsbDeviceService {
    public String readFromDevice() throws Exception {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            throw new Exception("No serial ports available. Check USB connection.");
        }

        // В реальном приложении — выбор по имени, VID/PID и т.д.
        SerialPort port = ports[0];
        if (!port.openPort()) {
            throw new Exception("Failed to open port: " + port.getSystemPortName());
        }

        try {
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 2000, 0);
            byte[] buffer = new byte[1024];
            int numRead = port.readBytes(buffer, buffer.length);
            if (numRead <= 0) {
                throw new Exception("No data received from device");
            }
            return new String(buffer, 0, numRead).trim();
        } finally {
            port.closePort();
        }
    }
}
