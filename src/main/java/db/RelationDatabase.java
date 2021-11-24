package db;

import model.Relation;
import model.Task;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class RelationDatabase {

    public static void init() throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        //PreparedStatement drop = con.prepareStatement("DROP TABLE IF EXISTS RELATION");
        //drop.executeUpdate();

        PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS RELATION(" +
                "id int PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                "taskId int," +
                "userId int)");
        create.executeUpdate();
    }

    public static ArrayList<Relation> getRelations() throws SQLException, ClassNotFoundException {
        ArrayList<Relation> relations = new ArrayList<>();

        Connection con = DatabaseConnection.initializeDatabase();

        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM RELATION");


        while(rs.next()) {
            final int id = rs.getInt("id");
            final int taskId = rs.getInt("taskId");
            final int userId = rs.getInt("userId");

            relations.add(new Relation(id, taskId, userId));
        }

        return relations;
    }

    public static void addRelation(Relation relation) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement add = con.prepareStatement("INSERT INTO RELATION (taskId, userId) VALUES (?,?)");
        add.setInt(1, relation.getTaskId());
        add.setInt(2, relation.getUserId());

        add.executeUpdate();
    }

    public static void deleteRelation(Task task, User user) throws SQLException, ClassNotFoundException {
        Connection con = DatabaseConnection.initializeDatabase();

        PreparedStatement add = con.prepareStatement("DELETE FROM RELATION WHERE taskId=? AND userId=?");
        add.setInt(1, task.getId());
        add.setInt(2, user.getId());

        add.executeUpdate();
    }
}
