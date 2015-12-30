package org.vaadin.webinars.springandvaadin.i18n.ui.doneOrReopened;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.webinars.springandvaadin.i18n.ui.util.UIHelper;
import org.vaadin.webinars.springandvaadin.i18n.ui.util.listeners.ChangeStatusClickListener;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Component
@Scope("prototype")
public class TestersDecisionUi extends UI {

    @Autowired
    ProcessEngine engine;
    @Autowired
    UIHelper helper;

    private TextField name = new TextField();
    private TextArea description = new TextArea();
    private TextField expectedTime = new TextField();
    private TextField reopenedStatus  = new TextField();
    private TextField doneStatus  = new TextField();
    private Button doneButton = new Button("Done");
    private Button reopenedButton = new Button("Reopened");


    @Override
    protected void init(VaadinRequest request) {
        setLocale(request.getLocale());
        List<Task> tasks = engine.getTaskService().createTaskQuery().list();
        System.out.println(tasks.size());
        final String id = tasks.get(0).getId();
        Map<String, Object> vars = engine.getTaskService().getVariables(id);
        getPage().setTitle("Task manager");
        helper.setValueAndDisable(name,String.valueOf(vars.get("name")));
        helper.setValueAndDisable(description,String.valueOf(vars.get("description")));
        helper.setValueAndDisable(expectedTime,String.valueOf(vars.get("expectedTime")));
        doneStatus.setValue("done");
        ChangeStatusClickListener doneButtonListener  = new ChangeStatusClickListener(name, id, description, expectedTime, doneStatus, "/task_manager/result", getUI());
        doneButton.addClickListener(doneButtonListener);
        reopenedStatus.setValue("reopened");
        ChangeStatusClickListener reopenedButtonListener = new ChangeStatusClickListener(name, id, description, expectedTime, reopenedStatus, "/task_manager/expectedTime", getUI());
        reopenedButton.addClickListener(reopenedButtonListener);
        HorizontalLayout nameLayout = helper.getHorizontalLayout("Task name:", name);
        HorizontalLayout descriptionLayout = helper.getHorizontalLayout("Description:", description);
        HorizontalLayout expectedTimeLayout = helper.getHorizontalLayout("Expected time:", expectedTime);
        List<? extends AbstractComponent> components = Arrays.asList(nameLayout, descriptionLayout, expectedTimeLayout, doneButton, reopenedButton);

        setContent(helper.getMainLayout(components));
    }
}