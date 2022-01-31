package com.innowise.training.shablinskaya.helpdesk.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class ApplicationInit extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{com.innowise.training.shablinskaya.helpdesk.config.SpringConfig.class,
                com.innowise.training.shablinskaya.helpdesk.config.PersistenceJPAConfig.class,
        com.innowise.training.shablinskaya.helpdesk.config.SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{com.innowise.training.shablinskaya.helpdesk.config.SpringConfig.class,
        com.innowise.training.shablinskaya.helpdesk.config.PersistenceJPAConfig.class,
                com.innowise.training.shablinskaya.helpdesk.config.SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
