package org.vaadin.webinars.springandvaadin.i18n.ui.util.listeners;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Mednikov on 29.12.2015.
 */
@Component
public abstract class AbstractButtonClickListener implements Button.ClickListener {

    private String taskId;

    public AbstractButtonClickListener() {
    }

    public AbstractButtonClickListener(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        onClick(taskId);
    }

    public abstract void redirect();

    private void onClick(String id) {
        ProcessEngines.getDefaultProcessEngine()
                .getFormService().submitTaskFormData(id, getInputMap());
        redirect();
    }

    public abstract Map<String,String> getInputMap();

}
