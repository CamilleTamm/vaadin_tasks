package model;

public class Relation {

    private int id;
    private int taskId;
    private int userId;

    public Relation(int id, int taskId, int userId) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
    }

    public int getId() {
        return this.id;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public int getUserId() {
        return this.userId;
    }
}
