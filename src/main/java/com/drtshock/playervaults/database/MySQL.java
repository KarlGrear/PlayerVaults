package com.drtshock.playervaults.database;

import java.sql.*;
import java.util.Arrays;


import com.drtshock.playervaults.PlayerVaults;

public abstract class MySQL {
    private final PlayerVaults plugin;
    protected Connection connection;
    protected String databaseName = "";

    public MySQL(String databaseName, PlayerVaults plugin) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.plugin = plugin;
        createDatabase(databaseName);
        this.databaseName = databaseName;
        connection = getConnection();
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        //TODO: Get the MySql credentials from config.
        return DriverManager.getConnection("jdbc:mysql://localhost" + (!(databaseName == null) ? "/" + databaseName : "") + "?user=root&password=ImdaGr81");
    }

    public void createDatabase(String databaseName) {
        String databaseCreateSQL = "CREATE SCHEMA IF NOT EXISTS " + databaseName;
        try (Connection connection1 = getConnection(); PreparedStatement preparedStatement = connection1.prepareStatement(databaseCreateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void createTable(String tableName) {
        String tableCreateSQL = """
                CREATE TABLE IF NOT EXISTS " + tableName + " (
                        serverName VARCHAR(32) NOT NULL,
                        playerVaultKey INT NOT NULL,
                        playerVaultData TEXT,
                        PRIMARY KEY (playerVaultKey, serverName)
                );
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    public boolean tableExists(String tableName) {
        try (PreparedStatement tableTest = connection.prepareStatement("CALL sys.table_exists('tempPlayerVaults', 'tempPlayerTable1', @exists); SELECT @exists;");
             ResultSet resultSet = tableTest.executeQuery() ) {
             return resultSet.isBeforeFirst();
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve connection" + Arrays.toString(ex.getStackTrace()));
        }
        return false;
    }


    public void ClosePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void CloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

