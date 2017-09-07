package org.grantharper.websecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

public class BaseSecurityConfig extends WebSecurityConfigurerAdapter{
  
  @Autowired
  AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("katie").password("").roles("CUSTOMER")
		.and()
		.withUser("liz").password("").roles("CUSTOMER").and()
		.withUser("emp")
				.password("").roles("EMPLOYEE");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests().antMatchers("/", "/console/**").permitAll()
				.antMatchers("/customer", "/customer/**").hasRole("CUSTOMER").antMatchers("/employee", "/employee/**")
				.hasRole("EMPLOYEE").anyRequest().authenticated().and().formLogin().permitAll().and().httpBasic().and()
				.logout().logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
	}
	
}
