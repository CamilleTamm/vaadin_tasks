package db;

import model.Task;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDatabase {

    public static void init() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        //PreparedStatement drop = con.prepareStatement("DROP TABLE IF EXISTS USER");
        //drop.executeUpdate();

        PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS USER(" +
                "id int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "firstName varchar(100)," +
                "lastName varchar(100))");
        create.executeUpdate();
    }

    public static ArrayList<User> getUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();

        Connection con = DatabaseConnection.initializeDatabase();

        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM USER");


        while(rs.next()) {
            final int id = rs.getInt("id");
            final String firstName = rs.getString("firstName");
            final String lastName = rs.getString("lastName");

            users.add(new User(id, firstName, lastName));
        }

        return users;
    }

    public static void addUser(User user) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement add = con.prepareStatement("INSERT INTO USER (firstName, lastName) VALUES (?,?)");
        add.setString(1, user.getFirstName());
        add.setString(2, user.getLastName());

        add.executeUpdate();
    }
}
