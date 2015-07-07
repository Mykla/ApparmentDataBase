package com.myprog;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

/**
 * Спроектировать базу «Квартиры». Каждая запись в базе содержит данные о квартире (район,
 * адрес, площадь, кол. комнат, цена). Сделать возможность выборки квартир из списка по
 * параметрам.
 *
 * Created by user on 07.07.2015.
 */
public class Main {

    private static final String URL = "jdbc:mysql://localhost/apartmentsdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String CREATE_TABLE_SQL = "CREATE TABLE apartments (" +
            "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            "district VARCHAR(45) NULL," +
            "street VARCHAR(45) NULL," +
            "house_number INT(4) NULL," +
            "count_rooms INT(2) NULL," +
            "price DOUBLE NULL)";

    public static void main(String[] args) {

        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера!");
        }

        try (Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
             Statement statement = connection.createStatement()){
            // Создание таблицы
            statement.execute(CREATE_TABLE_SQL);

            // Добавление записей
            statement.execute("INSERT INTO apartments(district, street, house_number, count_rooms, price)" +
                    "VALUES ('Печерский','Левандовская',14,3,230645.60)");
            statement.execute("INSERT INTO apartments(district, street, house_number, count_rooms, price)" +
                    "VALUES ('Подольский','Порика',16,3,150675.60)");
            statement.execute("INSERT INTO apartments(district, street, house_number, count_rooms, price)" +
                    "VALUES ('Шевченковский','Ярославов Вал',10,2,175115.70)");
            statement.execute("INSERT INTO apartments(district, street, house_number, count_rooms, price)" +
                    "VALUES ('Шевченковский','Прорезная',5,1,156115.70)");

            // Выполнение запроса
            ResultSet resultSet = statement.executeQuery("SELECT * FROM apartments WHERE district='Шевченковский'");

            if (resultSet.next()){
                while (resultSet.next()){
                    String district = resultSet.getString("district");
                    String street = resultSet.getString("street");
                    int house_number = resultSet.getInt("house_number");
                    int count_rooms = resultSet.getInt("count_rooms");
                    double price = resultSet.getDouble("price");

                    System.out.println(district + "\t" + street + "\t" + house_number
                            + "\t" + count_rooms + "\t" + price);
                }
            }else {
                System.err.println("Данных удовлетворяющих запросу не найдено!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
