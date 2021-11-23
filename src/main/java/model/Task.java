package model;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private int id;
    private String title;
    private String date;
    private String status;
    private String priority;
    private double progress;
    private List<User> users;

    public Task(int id, String title, String date, String status,String priority, double progress) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
        this.users = new ArrayList<>();
        this.users.add(new User(0, "Camille", "Tamim"));
        this.users.add(new User(0, "Boubou", "Sylla"));
    }

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return this.priority;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public List<String> getUsers() {
        List<String> users_string = new ArrayList<>();

        for(User u : this.users) {
            users_string.add(u.getFirstName() + ' ' + u.getLastName());
        }

        return users_string;
    }
}
