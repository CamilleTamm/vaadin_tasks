package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import model.User;

import java.sql.SQLException;

public class UserDialog extends Dialog {

    public UserDialog(MainView mainView) {
        super();
        this.setWidth("480px");
        this.setHeight("380px");

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new Text("Add a new user"));

        TextField textField = new TextField();;
        textField.setLabel("Prénom");
        textField.setRequiredIndicatorVisible(true);
        textField.setErrorMessage("This field is required");

        TextField textField2 = new TextField();;
        textField2.setLabel("Nom");
        textField2.setRequiredIndicatorVisible(true);
        textField2.setErrorMessage("This field is required");

        Button b_add = new Button("Ajouter");

        b_add.addClickListener(event -> {
            try {
                addUser(mainView, new User(0,textField.getValue(), textField2.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        verticalLayout.add(textField, textField2, b_add);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.add(verticalLayout);
    }

    private void addUser(MainView mainView, User user) throws SQLException, ClassNotFoundException {
        mainView.addUser(user);
        this.close();
    }
}
