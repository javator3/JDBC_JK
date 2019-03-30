package pl.sda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Statements {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ksiegarnia?serverTimezone=UTC","root", "Sm0cz0J3st");

//     createTable(connection);
     insert5newEmployee(connection);

    }

    //Metoda która tworzy nową tabelę

    static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE employee(id smallint not null auto_increment, name varchar(50), salary int, primary key (id))");

    }

    //metoda która

    static void insert5newEmployee(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        for (int i = 0; i < 5; i++){
            String formattedString = String.format("INSERT INTO employee(name,salary) values('name%s',%d)", "Jan"+i, 10*i);
            statement.executeUpdate(formattedString);
        }
    }

}
