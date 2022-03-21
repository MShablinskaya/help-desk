package com.innowise.training.shablinskaya.helpdesk.config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail/emailconfig.properties")
public class EmailConfig implements ApplicationContextAware, EnvironmentAware {
    private static final String NOREPLY_ADRESS = "noreply.email";
    private static final String PASSWORD = "noreply.password";
    private static final String HOST = "mail.host";
    private static final String PORT = "mail.port";
    private static final String PROTOCOL = "mail.transport.protocol";
    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
    private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";

    private ApplicationContext applicationContext;
    private Environment environment;

    public EmailConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }


    @Bean
    public JavaMailSender getJavaMailSender() throws IOException {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(this.environment.getProperty(HOST));
        javaMailSender.setPort(Integer.parseInt(Objects.requireNonNull(this.environment.getProperty(PORT))));
        javaMailSender.setProtocol(this.environment.getProperty(PROTOCOL));
        javaMailSender.setUsername(this.environment.getProperty(NOREPLY_ADRESS));
        javaMailSender.setPassword(this.environment.getProperty(PASSWORD));

        final Properties javaMailProperties = new Properties();
        javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return  javaMailSender;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public SimpleMailMessage templateSimpleMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText("This is the test email template for your email:\\n%s\\n");
        return simpleMailMessage;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mail/MailMessages");
        return messageSource;
    }

    @Bean
    public TemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());

        return templateEngine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}
