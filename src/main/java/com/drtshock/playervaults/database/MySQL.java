package com.drtshock.playervaults.database;

import java.sql.*;
import java.util.Arrays;


import com.drtshock.playervaults.PlayerVaults;
import com.drtshock.playervaults.vaultmanagement.VaultDataContainer;

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

    public void updatePlayerVaultTable(VaultDataContainer vaultDataContainer) {
        String tableCreateSQL = """
                REPLACE INTO  " + tableName + " (
                        serverName,
                        playerVaultKey,
                        playerVaultData
                        } VALUES  (?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.setString(0, vaultDataContainer.serverName);
            preparedStatement.setInt(1, vaultDataContainer.playerVaultKey);
            preparedStatement.setString(2, vaultDataContainer.playerVaultData);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void emptyPlayerVaultTable(String tableName) {
        String tableCreateSQL = "DELETE FROM  " + tableName + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void backupPlayerVaultTable(String tableName) {
        String tableCreateSQL = "INSERT INTO  " + tableName + "_bak  TABLE " + tableName + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropPlayerVaultTable(String tableName) {
        String tableCreateSQL = "DROP TABLE " + tableName + ";";
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePlayerVaultRow(String tableName, int playerVaultKey) {
        String tableCreateSQL = "DELETE FROM " + tableName + " WHERE playerVaultKey = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCreateSQL)) {
            preparedStatement.setInt(0, playerVaultKey);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ResultSet loadPlayerVaultTable(String tableName) {
        String sqlStatement = "SELECT * FROM " + tableName + ";";
        try (PreparedStatement tableTest = connection.prepareStatement(sqlStatement);
             ResultSet resultSet = tableTest.executeQuery()) {
            return resultSet;
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve connection" + Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }

    public boolean tableExists(String tableName) {
        try (PreparedStatement tableTest = connection.prepareStatement("CALL sys.table_exists('" + tableName +"', @exists); SELECT @exists;");
             ResultSet resultSet = tableTest.executeQuery()) {
            return resultSet.isBeforeFirst();
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve connection" + Arrays.toString(ex.getStackTrace()));
        }
        return false;
    }

    public boolean tableRowExists(String tableName, int playerVaultKey) {
        String sqlStatement = "SELECT * FROM " + tableName + " WHERE playerVaultKey = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setInt(0, playerVaultKey);
            ResultSet resultSet = preparedStatement.executeQuery();
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

    public void CloseResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
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

