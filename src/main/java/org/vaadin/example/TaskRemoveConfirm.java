package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import db.TaskDatabase;
import model.Task;

import java.sql.SQLException;

@Route(value = "remove/:taskId", layout=MainView.class)
public class TaskRemoveConfirm extends ConfirmDialog implements BeforeEnterObserver {

    private int taskId;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        taskId = event.getRouteParameters().getInteger("taskId").get();

        this.addCancelListener(item -> UI.getCurrent().navigate("/"));

        Task task = null;

        try {
            task = TaskDatabase.getTaskById(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(task != null) {
            this.setHeader("Voulez vous vraiment supprimer " + task.getTitle() + " ?");

            this.setCancelable(true);

            this.setRejectable(true);
            this.setRejectText("Annuler");
            this.addRejectListener(item -> UI.getCurrent().navigate("/"));

            this.setConfirmText("Supprimer");
            this.addConfirmListener(item -> UI.getCurrent().navigate("/"));

            this.open();
        }
    }
}
