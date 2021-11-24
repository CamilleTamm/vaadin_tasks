package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import db.RelationDatabase;
import db.TaskDatabase;
import db.UserDatabase;
import model.Relation;
import model.Task;
import model.User;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<Task> grid;
    private ArrayList<Task> tasks;
    private ArrayList<User> users;

    public MainView() throws SQLException, ClassNotFoundException {

        TaskDatabase.init();
        UserDatabase.init();
        RelationDatabase.init();

        //tasks = new ArrayList<Task>();
        tasks = TaskDatabase.getTasks();
        users = UserDatabase.getUsers();

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

        Button plusButton2 = new Button("Ajouter un utilisateur", new Icon(VaadinIcon.PLUS));
        plusButton2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        plusButton2.getElement().setAttribute("aria-label", "Add item");

        plusButton2.addClickListener(clickEvent -> {new UserDialog(this).open();
        });

        grid = new Grid<>(Task.class);
        grid.setItems(tasks);
        grid.setColumns("title", "date", "status", "priority", "progress");

        grid.addComponentColumn(item -> getAvatarGroup(item)).setHeader("assigned users");

        grid.addComponentColumn(item -> new Button(new Icon(VaadinIcon.PLUS), click-> {
            try {
                new RelationDialog(this, item).open();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        })).setHeader("assign user(s)");

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
                new HorizontalLayout(plusButton, plusButton2),
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

    public void updateTask(Task task) throws  SQLException, ClassNotFoundException {
        TaskDatabase.updateTask(task);
        tasks = TaskDatabase.getTasks();
        grid.setItems(tasks);
        grid.getDataProvider().refreshAll();
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        UserDatabase.addUser(user);
        users = UserDatabase.getUsers();
    }

    public List<String> getUsersString() {
        ArrayList<String> users_string = new ArrayList<>();

        for(User u : users) {
            users_string.add(u.getFirstName() + " " + u.getLastName());
        }

        return users_string;
    }

    public boolean relationExists(Task task, User user) throws SQLException, ClassNotFoundException {
        ArrayList<Relation> relations = RelationDatabase.getRelations();

        for(Relation r : relations) {
            if(r.getTaskId() == task.getId() && r.getUserId() == user.getId()) {
                return true;
            }
        }

        return false;
    }

    public List<String> getUsersNamesAssignedToTask(Task task) throws SQLException, ClassNotFoundException {
        ArrayList<String> names = new ArrayList<>();

        for(Relation r : RelationDatabase.getRelations()) {
            if(r.getTaskId() == task.getId()) {
                for(User u : UserDatabase.getUsers()) {
                    if(u.getId() == r.getUserId()) {
                        if(!names.contains(u.getCompleteName())) {
                            names.add(u.getCompleteName());
                        }
                    }
                }
            }
        }

        return names;
    }

    public AvatarGroup getAvatarGroup(Task task) {
        AvatarGroup avatarGroup = new AvatarGroup();

        try {
            for (String person : getUsersNamesAssignedToTask(task)) {
                AvatarGroup.AvatarGroupItem avatar = new AvatarGroup.AvatarGroupItem(person);
                avatarGroup.add(avatar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return avatarGroup;
    }

    public void refreshTheGrid() {
        grid.getDataProvider().refreshAll();
    }
}