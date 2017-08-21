package org.grantharper.websecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@Profile("secure")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecureSecurityConfig extends BaseSecurityConfig {

	public static Logger log = LoggerFactory.getLogger(SecureSecurityConfig.class);

//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.authorizeRequests().antMatchers("/", "/console/**").permitAll()
//		.antMatchers("/customer", "/customer/**").hasRole("CUSTOMER").antMatchers("/employee", "/employee/**", "/sensitive")
//		.hasRole("EMPLOYEE").anyRequest().authenticated().and().formLogin().permitAll().and().httpBasic().and()
//		.logout().logoutSuccessUrl("/").permitAll();
//
//	}
}
