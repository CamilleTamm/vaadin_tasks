package db;

import model.Task;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class TaskDatabase {

    public static void init() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement drop = con.prepareStatement("DROP TABLE IF EXISTS TASK");
        drop.executeUpdate();

        PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS TASK(" +
                "id int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "title varchar(100)," +
                "date varchar(100)," +
                "status varchar(100)," +
                "priority varchar(100)," +
                "progress double)");
        create.executeUpdate();
    }

    public static ArrayList<Task> getTasks() throws SQLException, ClassNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();

        Connection con = DatabaseConnection.initializeDatabase();

        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM TASK");


        while(rs.next()) {
            final int id = rs.getInt("id");
            final String title = rs.getString("title");
            final String date = rs.getString("date");
            final String status = rs.getString("status");
            final String priority = rs.getString("priority");
            final double progress = rs.getDouble("progress");

            tasks.add(new Task(id, title, date, status, priority, progress));
        }

        return tasks;
    }

    public static void addTask(Task task) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement add = con.prepareStatement("INSERT INTO TASK (title, date, status, priority, progress) VALUES (?,?,?,?,?)");
        add.setString(1, task.getTitle());
        add.setString(2, task.getDate());
        add.setString(3, task.getStatus());
        add.setString(4, task.getPriority());
        add.setDouble(5, task.getProgress());

        add.executeUpdate();
    }

    public static void deleteTask(Task task) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement add = con.prepareStatement("DELETE FROM TASK WHERE id=?");
        add.setInt(1, task.getId());

        add.executeUpdate();
    }

    public static void updateTask(Task task) throws  SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();
    }
}
