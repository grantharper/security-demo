package org.grantharper.websecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@Profile("insecure")
public class InsecureSecurityConfig extends BaseSecurityConfig {

	public static Logger log = LoggerFactory.getLogger(InsecureSecurityConfig.class);

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		super.configure(httpSecurity);
		
    // disable these protections so that I can access the H2 console
    httpSecurity.csrf().disable();
    httpSecurity.headers().frameOptions().disable();
		httpSecurity.headers().xssProtection().disable();
	}
}
