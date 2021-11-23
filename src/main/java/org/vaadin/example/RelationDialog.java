package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import model.Task;

public class RelationDialog extends Dialog {

    public RelationDialog(MainView mainView, Task task) {
        super();
        this.setWidth("480px");
        this.setHeight("480px");

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(new Text("Choose users to add to " + task.getTitle()));

        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Select user(s)");
        checkboxGroup.setItems(mainView.getUsers());
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        add(checkboxGroup);

        Button b_add = new Button("Ajouter");

        b_add.addClickListener(event -> {

        });

        verticalLayout.add(checkboxGroup, b_add);
        verticalLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.add(verticalLayout);
    }
}
