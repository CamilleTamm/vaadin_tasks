package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import db.RelationDatabase;
import db.UserDatabase;
import model.Relation;
import model.Task;
import model.User;

import javax.sound.midi.SysexMessage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationDialog extends Dialog {

    private CheckboxGroup<String> checkboxGroup;

    public RelationDialog(MainView mainView, Task task) throws SQLException, ClassNotFoundException {
        super();
        this.setWidth("480px");
        this.setHeight("480px");

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new Text("Choose users to add to " + task.getTitle()));

        checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Select user(s)");
        checkboxGroup.setItems(mainView.getUsersString());
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        List<Relation> relations = RelationDatabase.getRelations();
        List<User> users = UserDatabase.getUsers();

        for(Relation r : relations) {
            if(r.getTaskId() == task.getId()) {
                for(User u : users) {
                    if(r.getUserId() == u.getId()) {
                        checkboxGroup.select(u.getCompleteName());
                    }
                }
            }
        }

        add(checkboxGroup);

        Button b_add = new Button("Modifier");

        b_add.addClickListener(event -> {
            try {
                update(task);
                mainView.refreshTheGrid();
                this.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        verticalLayout.add(checkboxGroup, b_add);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.add(verticalLayout);
    }

    private List<String> getNotSelectedValues() throws SQLException, ClassNotFoundException {
        List<String> not_selected = new ArrayList<>();
        List<User> users = UserDatabase.getUsers();

        for(User u : users) {
            boolean selected = false;

            for(String s2 : checkboxGroup.getSelectedItems()) {
                if(u.getCompleteName().equals(s2)) {
                    selected = true;
                }
            }

            if(!selected) {
                not_selected.add(u.getCompleteName());
            }
        }

        return not_selected;
    }

    private void update(Task task) throws SQLException, ClassNotFoundException {
        List<User> users = UserDatabase.getUsers();

        // on ajoute
        for(String s : checkboxGroup.getSelectedItems()) {
            for(User u : users) {
                if(u.getCompleteName().equals(s)) {
                    try {
                        RelationDatabase.addRelation(new Relation(0, task.getId(), u.getId()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // on supprime
        for(String s : getNotSelectedValues()) {
            for(User u : users) {
                if(u.getCompleteName().equals(s)) {
                    try {
                        RelationDatabase.deleteRelation(task, u);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
