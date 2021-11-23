package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import db.DatabaseConnection;
import db.TaskDatabase;
import model.Task;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<Task> grid;
    private ArrayList<Task> tasks;

    public MainView() throws SQLException, ClassNotFoundException {

        TaskDatabase.init();

        //tasks = new ArrayList<Task>();
        tasks = TaskDatabase.getTasks();

        Button plusButton = new Button(" Ajouter une tache", new Icon(VaadinIcon.PLUS));
        //plusButton.setIconAfterText(true);
        plusButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        plusButton.getElement().setAttribute("aria-label", "Add item");

        plusButton.addClickListener(clickEvent -> {
            try {
                new TaskDialog(this, null).open();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        grid= new Grid<>(Task.class);
        grid.setItems(tasks);
        grid.setColumns("title", "date", "status", "priority", "progress");

        grid.addComponentColumn(item -> new Button("Update", click -> {
            try {
                new TaskDialog(this, item).open();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }));

        grid.addComponentColumn(item -> new Button("Delete", click -> {
            try {
                removeTask(item);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));

        //grid.removeAllColumns();
        //grid.addColumn(Task::getTitle);
        //grid.addColumn(item -> item.getTitle() + " " + item.getStatus());

        add(
                new H1("Task Board"),
                plusButton,
                grid
        );

        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    public void addTask(Task task) throws SQLException, ClassNotFoundException {
        TaskDatabase.addTask(task);
        tasks = TaskDatabase.getTasks();
        grid.setItems(tasks);
        grid.getDataProvider().refreshAll();
    }

    public void removeTask(Task task) throws SQLException, ClassNotFoundException {
        TaskDatabase.deleteTask(task);
        tasks = TaskDatabase.getTasks();
        grid.setItems(tasks);
        grid.getDataProvider().refreshAll();
    }

    public void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}