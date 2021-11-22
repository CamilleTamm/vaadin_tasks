package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import model.Task;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<Task> grid;
    private ArrayList<Task> tasks;

    public MainView() throws SQLException, ClassNotFoundException {

        //Connection con = DatabaseConnection.initializeDatabase();

        /*Statement st = con.createStatement();

        st.executeQuery("CREATE TABLE TASK IF NOT EXISTS (id PRIMARY KEY AUTO_INCREMENT," +
                "title varchar(100)," +
                "date varchar(100)," +
                "status varchar(100)," +
                "priority varchar(100)," +
                "progress int)");*/

        tasks = new ArrayList<Task>();

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
            removeTask(item);
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
    public void addTask(Task task) {
        tasks.add(task);
        grid.setItems(tasks);
        grid.getDataProvider().refreshAll();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        grid.getDataProvider().refreshAll();
    }

    public void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}