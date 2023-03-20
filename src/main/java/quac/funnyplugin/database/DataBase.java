package quac.funnyplugin.database;

import quac.funnyplugin.Entity.PlayerBase;

import java.math.BigDecimal;
import java.sql.*;

public class DataBase {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void init() {

        String url = "jdbc:mysql://localhost:3306/kaiblock?characterEncoding=utf8";
        String username = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, username, password);

            System.out.println("Connected to the database!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PlayerData getPlayerData(PlayerBase playerBase) throws SQLException {
        PlayerData playerData = getPlayerDataFromDB(playerBase.uuid.toString());
        if(playerData != null) return playerData;

        System.out.println("PlayerData == null, creating new in database");

        setPlayerDataInDB(new PlayerData(playerBase.uuid.toString(), playerBase.money));

        return getPlayerData(playerBase);
    }

    public static void setPlayerDataInDB(PlayerData data) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into player_data values (?, ?)");

        preparedStatement.setString(1, data.uuid);
        preparedStatement.setBigDecimal(2, BigDecimal.valueOf(data.money==null? 0 : data.money.doubleValue()));
        preparedStatement.executeUpdate();

        preparedStatement = connection
                .prepareStatement("SELECT uuid, money from player_data");
        resultSet = preparedStatement.executeQuery();

        System.out.println("Created new player in DB now: ");
        while (resultSet.next()) {
            String id = resultSet.getString("uuid");
            int money = resultSet.getInt("money");

            System.out.println("ID: " + id + " | Money: " + money);
        }
    }

    private static PlayerData getPlayerDataFromDB(String uuid) throws SQLException {
        System.out.println("Looping through entries till right one found");

        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from player_data");

        while (resultSet.next()) {
            String id = resultSet.getString("uuid");
            BigDecimal money = resultSet.getBigDecimal("money");

            System.out.println("ID: " + id + " | Money: " + money);
            if(id.equals(uuid)) {
                return new PlayerData(id, money);
            }
        }

        statement.close();

        return null;
    }
}
