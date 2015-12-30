package org.vaadin.webinars.springandvaadin.i18n.ui.util;

import com.vaadin.ui.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mednikov on 29.12.2015.
 */
@Component
public class UIHelper {
    public HorizontalLayout getHorizontalLayout(String name, AbstractComponent component) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(new Label(name));
        layout.addComponent(component);
        return layout;
    }

    public VerticalLayout getMainLayout(List<? extends AbstractComponent> components) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        for (AbstractComponent component : components) {
            layout.addComponent(component);
        }
        return layout;
    }

    public void setValueAndDisable(AbstractTextField field, String value) {
        field.setValue(value);
        field.setEnabled(false);
    }
}
