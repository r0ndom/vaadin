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
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.webinars.springandvaadin.i18n.ui.util.listeners.CreateTaskClickListener;
import org.vaadin.webinars.springandvaadin.i18n.ui.util.UIHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @author petter@vaadin.com
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class CreateTaskUi extends UI {

    @Autowired
    ProcessEngine engine;
    @Autowired
    UIHelper helper;

    private TextField name = new TextField();
    private TextArea description = new TextArea();
    private Button submit = new Button("Create");

    @Override
    protected void init(final VaadinRequest request) {
        engine.getRuntimeService().startProcessInstanceByKey("process");
        setLocale(request.getLocale());
        List<Task> tasks = engine.getTaskService().createTaskQuery().list();
        final String id = tasks.get(0).getId();
        getPage().setTitle("Task manager");
        CreateTaskClickListener listener = new CreateTaskClickListener(name, id, description, "/task_manager/expectedTime", getUI());
        submit.addClickListener(listener);
        HorizontalLayout nameLayout = helper.getHorizontalLayout("Task name:", name);
        HorizontalLayout descriptionLayout = helper.getHorizontalLayout("Description:", description);
        List<? extends AbstractComponent> components = Arrays.asList(nameLayout, descriptionLayout, submit);
        setContent(helper.getMainLayout(components));
    }

}
