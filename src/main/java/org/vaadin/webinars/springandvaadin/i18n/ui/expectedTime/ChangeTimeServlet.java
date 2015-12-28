package org.vaadin.webinars.springandvaadin.i18n.ui.expectedTime;

import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.vaadin.webinars.springandvaadin.i18n.ui.createTask.CreateTaskUi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Mednikov on 28.12.2015.
 */
@WebServlet(urlPatterns = "/expectedTime/*")
public class ChangeTimeServlet extends VaadinServlet {


    @Override
    protected void servletInitialized() throws ServletException {
        final ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
                sessionInitEvent.getSession().addUIProvider(new UIProvider() {
                    @Override
                    public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
                        return ChangeTimeUi.class;
                    }

                    @Override
                    public UI createInstance(UICreateEvent event) {
                        return context.getBean(event.getUIClass());
                    }
                });
            }
        });
    }

}