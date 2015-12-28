package org.vaadin.webinars.springandvaadin.i18n.ui.inProgress;

import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Mednikov on 28.12.2015.
 */
@WebServlet(urlPatterns = "/changeStatus/*")
public class ChangeStatusServlet extends VaadinServlet {


    @Override
    protected void servletInitialized() throws ServletException {
        final ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
                sessionInitEvent.getSession().addUIProvider(new UIProvider() {
                    @Override
                    public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
                        return ChangeStatusUi.class;
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