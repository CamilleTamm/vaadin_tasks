package model;

public class Task {

    private int id;
    private String title;
    private String date;
    private String status;
    private String priority;
    private double progress;

    public Task(int id, String title, String date, String status,String priority, double progress) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.priority = priority;
        this.progress = progress;
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
}
