package ru.skoblenkov.devicecontrol.service;

import ru.skoblenkov.devicecontrol.model.DeviceData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/device_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345";

    public void saveData(DeviceData data) throws SQLException {
        String sql = "INSERT INTO device_data (payload, created_at) VALUES (?, NOW())";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, data.getPayload());
            ps.executeUpdate();
        }
    }
}
