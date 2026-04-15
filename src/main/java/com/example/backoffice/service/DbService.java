package com.example.backoffice.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class DbService {

    private final DataSource dataSource;

    public DbService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Connexion OK à : " + conn.getMetaData().getURL();
        } catch (Exception e) {
            return "Erreur connexion : " + e.getMessage();
        }
    }
}