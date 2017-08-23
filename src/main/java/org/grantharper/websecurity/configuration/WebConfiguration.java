package org.grantharper.websecurity.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class WebConfiguration {

	// This is for the H2 database console if needed
	@Bean
	ServletRegistrationBean h2servletRegistration() {
			ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
			registrationBean.addUrlMappings("/console/*");
			return registrationBean;
	}
	

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource reloadMessage = new ReloadableResourceBundleMessageSource();
		reloadMessage.setBasename("classpath:/messages");
		reloadMessage.setDefaultEncoding("UTF-8");
		reloadMessage.setCacheSeconds(0);
		return reloadMessage;
	}

}
