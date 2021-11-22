package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import model.Task;

import java.sql.SQLException;
import java.util.ArrayList;

@Route("")
public class MainView extends VerticalLayout {
    public MainView() throws SQLException, ClassNotFoundException {

        /*Connection con = DatabaseConnection.initializeDatabase();

        Statement st = con.createStatement();

        st.executeQuery("CREATE TABLE TASK IF NOT EXISTS (id PRIMARY KEY AUTO_INCREMENT," +
                "title varchar(100)," +
                "date varchar(100)," +
                "status varchar(100)," +
                "priority varchar(100)," +
                "progress int)");*/

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("Todo", "Aug 31, 2021", "Not Started", "green", 0));
        tasks.add(new Task("Design", "Aug 31, 2021", "In Progress", "blue", 25));
        tasks.add(new Task("Programming", "Aug 31, 2021", "In Review", "red", 90));
        tasks.add(new Task("Writing", "Aug 31, 2021", "Completed", "red", 100));

        Tab tab1 = new Tab("Task");
        Tab tab2 = new Tab("End Date");
        Tab tab3 = new Tab("Status");
        Tab tab4 = new Tab("Priority");
        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4);

        Button plusButton = new Button(new Icon(VaadinIcon.PLUS));
        plusButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        plusButton.getElement().setAttribute("aria-label", "Add item");

        plusButton.addClickListener(clickEvent -> {
            new TaskDIalog(this).open();
        });

        add(
                new H1("Task Board"),
                tabs,
                plusButton
        );

        for(Task task : tasks) {
            add(new TaskHorizontalLayout(task));
        }

        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    public void addTask(Task task) {
        add(new TaskHorizontalLayout(task));
    }
}