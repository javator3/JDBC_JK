package pl.sda.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Statements {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ksiegarnia?serverTimezone=UTC&logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true","root", "Sm0cz0J3st");

//     createTable(connection);
//     insert5newEmployee(connection);
        insert5newEmployee2(connection);
//        deleteEmployeeWhen2(connection,1000);
//        deleteEmplyeeWhen(connection, 1000);
//        selectNameAndSalaryFromTable(connection);
//        selectNmeAndSalaryFromTableSortedByName(connection);
//        selectNmeAndSalaryFromTableWereSalaryIsHigherThan1000(connection);
//        selectNameAndSalaryFromTableWereNameEndsWith4(connection);
//     dropTable(connection);


        //Zadanie 17
//        List<Employee> employees = selectEmployee(connection);
//        System.out.println(employees.toString());

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

    static void deleteEmployeeWhen2(Connection connection, int salaryBorder) throws SQLException {
        Statement statement = connection.createStatement();
        String formattedString = String.format("DELETE FROM employee WHERE salary < %d", salaryBorder);

        statement.executeUpdate(formattedString);
    }


    //metoda która usuwa wiersze z pracownikami o zarobkach mniejszych niż podana przez użytkownika (prepared statement)

    static void deleteEmplyeeWhen(Connection connection, int salary) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE salary < ?");
        preparedStatement.setInt(1, salary);
        preparedStatement.executeUpdate();
    }

    //metoda która usuwa tabelę

    static void dropTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE employee");
    }

    //Wypisz wszystkich pracowikow

    static void selectNameAndSalaryFromTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name, salary FROM employee");

        while (resultSet.next()){
            String name = resultSet.getString(1); //można wwpisać nr kolumny
           int salary  = resultSet.getInt("salary"); //lub nazwe kolumny tabeli
            System.out.println(name + " " + salary);
        }

    }

    //Wypisz wszystkich pracownikow posortowanych wg imienia

    static void selectNmeAndSalaryFromTableSortedByName(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name, salary FROM employee ORDER BY name ASC");
        while (resultSet.next()){
            String name = resultSet.getString(1); //można wwpisać nr kolumny
            int salary  = resultSet.getInt("salary"); //lub nazwe kolumny tabeli
            System.out.println(name + " " + salary);
        }

    }

    static void selectNmeAndSalaryFromTableWereSalaryIsHigherThan1000(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name, salary FROM employee WHERE salary > 1000");
        while (resultSet.next()){
            String name = resultSet.getString(1); //można wwpisać nr kolumny
            int salary  = resultSet.getInt("salary"); //lub nazwe kolumny tabeli
            System.out.println(name + " " + salary);
        }

    }

    static void selectNameAndSalaryFromTableWereNameEndsWith4(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name, salary FROM employee WHERE name LIKE '%4'");
        while (resultSet.next()){
            String name = resultSet.getString(1); //można wwpisać nr kolumny
            int salary  = resultSet.getInt("salary"); //lub nazwe kolumny tabeli
            System.out.println(name + " " + salary);
        }

    }

    //metoda który stworzy troche obiektowosci
    //

    static List<Employee> selectEmployee(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, name, salary from employee");
        List<Employee> result = new ArrayList<Employee>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString(2);
            int salary = resultSet.getInt(3);
            Employee employee = new Employee(id, name, salary);
            result.add(employee);
        }

        return result;
    }

}
