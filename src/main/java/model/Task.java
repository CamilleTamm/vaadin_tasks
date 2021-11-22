package model;

public class Task {

    public String title;
    public String date;
    public String status;
    public String priority;
    public int progress;

    public Task(String title, String date, String status,String priority, int progress) {
        this.title = title;
        this.date = date;
        this.status = status;
        this.progress = progress;
    }
}
