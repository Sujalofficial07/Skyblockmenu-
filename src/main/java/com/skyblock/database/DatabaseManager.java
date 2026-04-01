package com.skyblock.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseManager {

    private HikariDataSource dataSource;

    public void init() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://u1208_8E6Db6pnxy:gmNrPRZj2mJyii%5EsH7!e4%4035@paid2.skilloraclouds.site:3306/s1208_Skyblock");
        config.setUsername("u1208_8E6Db6pnxy");
        config.setPassword("gmNrPRZj2mJyii^sH7!e4@35");
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_skills(uuid VARCHAR(36), skill VARCHAR(32), xp DOUBLE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_collections(uuid VARCHAR(36), collection VARCHAR(32), amount INT)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS player_stats(uuid VARCHAR(36), stat VARCHAR(32), value DOUBLE)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
