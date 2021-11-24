package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import db.TaskDatabase;
import model.Task;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

@Route(value = "edit/:taskId", layout=MainView.class)
public class TaskDialogEdition extends Dialog implements BeforeEnterObserver {

    private int taskId;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        taskId = event.getRouteParameters().getInteger("taskId").get();

        this.addDialogCloseActionListener(item -> UI.getCurrent().navigate("/"));

        Task task = null;

        try {
            task = TaskDatabase.getTaskById(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(task != null) {
            this.setWidth("480px");
            this.setHeight("600px");

            VerticalLayout verticalLayout = new VerticalLayout();

            verticalLayout.add(new Text("Edit task"));

            TextField textField = new TextField();
            textField.setLabel("Titre");
            textField.setRequiredIndicatorVisible(true);
            textField.setErrorMessage("This field is required");
            textField.setValue(task.getTitle());

            DatePicker datePicker = new DatePicker();
            datePicker.setRequiredIndicatorVisible(true);
            datePicker.setLabel("End date");

            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(task.getDate());
                datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Select<String> labelSelect = new Select<>();
            labelSelect.setItems("Not Started", "In Progress", "In Review", "Completed");
            labelSelect.setValue("Not Started");
            labelSelect.setLabel("Status");
            labelSelect.setValue(task.getStatus());

            NumberField numberField = new NumberField("Progression");
            numberField.setRequiredIndicatorVisible(true);
            numberField.setHasControls(true);
            numberField.setStep(1.0d);
            numberField.setValue(task.getProgress());

            Button b_add = new Button("Modifier");

            Task finalTask = task;
            b_add.addClickListener(click -> {
                this.close();
                UI.getCurrent().navigate("/");
            });

            verticalLayout.add(textField, datePicker, labelSelect, numberField, b_add);
            verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            this.add(verticalLayout);

            this.open();
        }
    }
}
