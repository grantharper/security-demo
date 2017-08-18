package org.grantharper.websecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("insecure")
public class InsecureSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static Logger log = LoggerFactory.getLogger(InsecureSecurityConfig.class);
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
			httpSecurity.authorizeRequests().antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
			.logout().permitAll();
		
	}
}
