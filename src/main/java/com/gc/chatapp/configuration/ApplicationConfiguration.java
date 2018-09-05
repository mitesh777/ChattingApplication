package com.gc.chatapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ApplicationConfiguration {

	@Value("${gc.api.create.user}")
	String createUserUrl;
	
	@Value("${gc.api.validate.userexist}")
	String isUserExistUrl;
	

	public String getIsUserExistUrl() {
		return isUserExistUrl;
	}

	public void setIsUserExistUrl(String isUserExistUrl) {
		this.isUserExistUrl = isUserExistUrl;
	}

	public String getCreateUserUrl() {
		return createUserUrl;
	}

	public void setCreateUserUrl(String createUserUrl) {
		this.createUserUrl = createUserUrl;
	}
}
