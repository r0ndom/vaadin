package org.vaadin.webinars.springandvaadin.i18n.ui.lastPage;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.webinars.springandvaadin.i18n.ui.util.UIHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Berdnik S.O.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class LastTaskPageUi extends UI {
    @Autowired
    ProcessEngine engine;
    @Autowired
    UIHelper helper;

    private TextField name = new TextField();
    private TextArea description = new TextArea();
    private TextField expectedTime = new TextField();
    private Button closeButton = new Button("Close");

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(request.getLocale());
        getPage().setTitle("Task manager");

        List<Task> tasks = engine.getTaskService().createTaskQuery().list();
        final String id = tasks.get(0).getId();
        Map<String, Object> vars = engine.getTaskService().getVariables(id);
        helper.setValueAndDisable(name,String.valueOf(vars.get("name")));
        helper.setValueAndDisable(description,String.valueOf(vars.get("description")));
        helper.setValueAndDisable(expectedTime,String.valueOf(vars.get("expectedTime")));

        closeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                engine.getTaskService().complete(id);
                getUI().getPage().setLocation("/task_manager");
            }
        });

        HorizontalLayout nameLayout = helper.getHorizontalLayout("Task name:", name);
        HorizontalLayout descriptionLayout = helper.getHorizontalLayout("Description:", description);
        HorizontalLayout expectedTimeLayout = helper.getHorizontalLayout("Expected time:", expectedTime);
        List<? extends AbstractComponent> components = Arrays.asList(nameLayout, descriptionLayout, expectedTimeLayout, closeButton);
        setContent(helper.getMainLayout(components));
    }
}
