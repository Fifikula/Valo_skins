package org.example.app_val.services;

import org.example.app_val.model.Skin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = ConfigManager.get("db.url");

    public Database() {
        createTableIfNeeded();
    }

    private void createTableIfNeeded() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS favorites (
                    uuid TEXT PRIMARY KEY,
                    name TEXT,
                    imageUrl TEXT,
                    weaponId TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToFavorites(Skin skin) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT OR IGNORE INTO favorites (uuid, name, imageUrl, weaponId) VALUES (?, ?, ?, ?)"
             )) {
            ps.setString(1, skin.uuid());
            ps.setString(2, skin.name());
            ps.setString(3, skin.imageUrl());
            ps.setString(4, skin.weaponId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Skin> getFavorites() {
        List<Skin> favorites = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM favorites")) {

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String name = rs.getString("name");
                String imageUrl = rs.getString("imageUrl");
                String weaponId = rs.getString("weaponId");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    favorites.add(new Skin(name, imageUrl, uuid, weaponId));
                } else {
                    System.out.println("Nieprawidłowy URL: " + name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favorites;
    }

    public boolean isFavorite(Skin skin) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM favorites WHERE uuid = ?")) {
            ps.setString(1, skin.uuid());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeFromFavorites(Skin skin) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM favorites WHERE uuid = ?")) {
            ps.setString(1, skin.uuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
