package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import model.Task;

import java.awt.*;

public class TaskDIalog extends Dialog {

    public TaskDIalog(MainView mainView) {
        super();
        this.setWidth("480px");
        this.setHeight("560px");

        this.add(new Text("Add a new task"));

        TextField textField = new TextField();;
        textField.setLabel("Titre");
        textField.setRequiredIndicatorVisible(true);
        textField.setErrorMessage("This field is required");

        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("End date");

        this.add(textField);
        this.add(datePicker);

        Button b_add = new Button("Ajouter");

        b_add.addClickListener(event -> mainView.addTask(
                new Task(textField.getValue(), datePicker.getValue().toString(), "TEST", "TEST", 0))
        );

        Button b_close = new Button("Close");

        b_close.addClickListener(event -> this.close());

        this.add(b_add, b_close);
    }
}
