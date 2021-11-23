package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import model.Task;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class TaskDialog extends Dialog {

    public TaskDialog(MainView mainView, Task task) throws ParseException {
        super();
        this.setWidth("480px");
        this.setHeight("600px");

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new Text("Add a new task"));

        TextField textField = new TextField();;
        textField.setLabel("Titre");
        textField.setRequiredIndicatorVisible(true);
        textField.setErrorMessage("This field is required");

        if(task != null) {
            textField.setValue(task.getTitle());
        }

        DatePicker datePicker = new DatePicker();
        datePicker.setRequiredIndicatorVisible(true);
        datePicker.setLabel("End date");

        if(task != null) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(task.getDate());

            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        Select<String> labelSelect = new Select<>();
        labelSelect.setItems("Not Started", "In Progress", "In Review", "Completed");
        labelSelect.setValue("Not Started");
        labelSelect.setLabel("Status");

        if(task != null) {
            labelSelect.setValue(task.getStatus());
        }

        NumberField numberField = new NumberField("Progression");
        numberField.setRequiredIndicatorVisible(true);
        numberField.setHasControls(true);
        numberField.setStep(1.0d);

        if(task != null ){
            numberField.setValue(task.getProgress());
        }

        Button b_add;

        if(task != null) {
            b_add = new Button("Modifier");

            b_add.addClickListener(event ->
                    this.editTask(mainView, task, textField.getValue(), datePicker.getValue().toString(), labelSelect.getValue(), numberField.getValue())
            );

        } else {
            b_add = new Button("Ajouter");

            b_add.addClickListener(event -> {
                        try {
                            addTask(mainView,
                            new Task(0,textField.getValue(), datePicker.getValue().toString(), labelSelect.getValue(), "red", numberField.getValue()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

        verticalLayout.add(textField, datePicker, labelSelect, numberField, b_add);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.add(verticalLayout);
    }

    private void addTask(MainView mainView, Task task) throws SQLException, ClassNotFoundException {
        mainView.addTask(task);
        this.close();
    }

    private void editTask(MainView mainView, Task task, String newTitle, String newDate, String newStatus, double newProgress) {
        task.setTitle(newTitle);
        task.setDate(newDate);
        task.setStatus(newStatus);
        task.setProgress(newProgress);
        mainView.refreshGrid();
        this.close();
    }
}
