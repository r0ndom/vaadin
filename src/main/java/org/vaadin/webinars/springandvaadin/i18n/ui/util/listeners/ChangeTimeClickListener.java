package org.vaadin.webinars.springandvaadin.i18n.ui.util.listeners;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;



@Component
public class ChangeTimeClickListener extends AbstractButtonClickListener {

    private TextField name;
    private TextArea description;
    private TextField expectedTime;
    private UI ui;

    public ChangeTimeClickListener() {
    }

    public ChangeTimeClickListener(TextField name, String taskId, TextArea description, TextField expectedTime, UI ui) {
        super(taskId);
        this.name = name;
        this.description = description;
        this.expectedTime = expectedTime;
        this.ui = ui;
    }

    @Override
    public void redirect() {
        ui.getPage().setLocation("/changeStatus");;
    }

    @Override
    public Map<String, String> getInputMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name.getValue());
        map.put("description", description.getValue());
        map.put("expectedTime", expectedTime.getValue());
        return map;
    }

}