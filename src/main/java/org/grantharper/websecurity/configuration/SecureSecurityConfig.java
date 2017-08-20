package org.grantharper.websecurity.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("secure")
public class SecureSecurityConfig extends BaseSecurityConfig {
	
	public static Logger log = LoggerFactory.getLogger(SecureSecurityConfig.class);


}
