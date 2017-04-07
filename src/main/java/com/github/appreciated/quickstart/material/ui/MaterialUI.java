package com.github.appreciated.quickstart.material.ui;

import com.github.appreciated.quickstart.base.navigation.WebApplicationUI;
import com.vaadin.server.VaadinServlet;
import org.vaadin.leif.splashscreen.SplashScreen;
import org.vaadin.leif.splashscreen.SplashScreenHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Created by Johannes on 07.04.2017.
 */
@SplashScreen(value = "com/github/appreciated/quickstart/material/ui/loader.html", width = 100, height = 100)
public abstract class MaterialUI extends WebApplicationUI {

    public static class MaterialServlet extends VaadinServlet {
        @Override
        public void init(ServletConfig servletConfig) throws ServletException {
            super.init(servletConfig);
            SplashScreenHandler.init(getService());
        }
    }
}
