package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import model.Task;

public class TaskHorizontalLayout extends HorizontalLayout {

    public TaskHorizontalLayout(Task task) {
        super();
        this.setWidthFull();

        this.add(new Text(task.title));
        this.add(new Text(task.date));

        Button b = new Button(task.status);

        switch(task.status) {
            case "Not Started" : b.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
            break;
            case "In Progress" : default : b.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        }

        this.add(b);
        this.setDefaultVerticalComponentAlignment(Alignment.CENTER);
    }
}
