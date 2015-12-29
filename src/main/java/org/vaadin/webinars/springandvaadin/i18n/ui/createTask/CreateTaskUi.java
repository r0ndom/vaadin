/*
 * Copyright 2014 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.webinars.springandvaadin.i18n.ui.createTask;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author petter@vaadin.com
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class CreateTaskUi extends UI {

    @Autowired
    ProcessEngine engine;


    private TextField name;
    private TextField description;
    private Button submit;


    @Override
    protected void init(final VaadinRequest request) {
        ProcessInstance instance = engine.getRuntimeService().startProcessInstanceByKey("process");
        setLocale(request.getLocale());
        List<Task> tasks = engine.getTaskService().createTaskQuery().list();
        final String id = tasks.get(0).getId();
        getPage().setTitle("Task manager");

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        name = new TextField();
        description = new TextField();
        submit = new Button("Click Me");
        submit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                engine.getFormService().submitTaskFormData(id, getInputMap());
                getUI().getPage().setLocation("/task_manager/expectedTime");
            }
        });
        layout.addComponent(name);
        layout.addComponent(description);
        layout.addComponent(submit);

    }

    private Map<String, String> getInputMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name.getValue());
        map.put("description", description.getValue());
        return map;
    }
}
