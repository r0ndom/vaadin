package org.vaadin.webinars.springandvaadin.i18n.ui.expectedTime;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Component
@Scope("prototype")
public class ChangeTimeUi extends UI {

    @Autowired
    ProcessEngine engine;


    private TextField name;
    private TextField description;
    private TextField expectedTime;
    private Button submit;


    @Override
    protected void init(VaadinRequest request) {
        setLocale(request.getLocale());
        List<Task> tasks = engine.getTaskService().createTaskQuery().list();
        System.out.println(tasks.size());
        final String id = tasks.get(0).getId();
        //Map<String, Object> vars = engine.getFormService().getTaskFormData(id);
        Map<String, Object> vars = engine.getTaskService().getVariables(id);

        getPage().setTitle("Task manager");

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        name = new TextField();
        description = new TextField();
        name.setValue(String.valueOf(vars.get("name")));
        name.setEnabled(false);
        description.setValue(String.valueOf(vars.get("description")));
        description.setEnabled(false);
        expectedTime = new TextField();
        submit = new Button("Click Me");
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                engine.getFormService().submitTaskFormData(id, getInputMap());
                getUI().getPage().setLocation("/changeStatus");
            }
        });
        layout.addComponent(name);
        layout.addComponent(description);
        layout.addComponent(expectedTime);
        layout.addComponent(submit);

    }

    private Map<String, String> getInputMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name.getValue());
        map.put("description", description.getValue());
        map.put("expectedTime", expectedTime.getValue());
        return map;
    }
}