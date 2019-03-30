package pl.sda.jdbc;

import java.sql.*;
import java.util.Random;

public class Statements {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ksiegarnia?serverTimezone=UTC","root", "Sm0cz0J3st");

//     createTable(connection);
//     insert5newEmployee(connection);
//        insert5newEmployee2(connection);
        deleteEmplyeeWhen(connection);
//     dropTable(connection);

    }

    //Metoda która tworzy nową tabelę

    static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE employee(id smallint not null auto_increment, name varchar(50), salary int, primary key (id))");

    }

    //metoda która zapisuje 5 pracowików

    static void insert5newEmployee(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 0; i < 5; i++){
            String formattedString = String.format("INSERT INTO employee(name,salary) values('name%s',%d)", "Jan"+i, 10*i);
            statement.executeUpdate(formattedString);
        }
    }

    //metoda zapisywania pracowników za pomocą prepare statement

    static void insert5newEmployee2(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee(name, salary) values (?,?)");
        for (int i = 0; i<5 ; i++){
            preparedStatement.setString(1, "imie"+i);
            preparedStatement.setInt(2,new Random().nextInt(2000));
            preparedStatement.executeUpdate(); //zawsze pamietac o execute update!!!!
        }
    }

    //metoda która usuwa wiersze z pracownikami o zarobkach mniejszych niż podana przez użytkownika

    static void deleteEmplyeeWhen(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE salary < ?");
        preparedStatement.setInt(1, 1000);
        preparedStatement.executeUpdate();
    }

    //metoda która usuwa tabelę

    static void dropTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE employee");
    }

}
