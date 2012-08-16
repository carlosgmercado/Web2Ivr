
package com.cm2labs.web2ivr.restful.support;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.impl.client.BasicCredentialsProvider;


public class CustomCredentialsProvider extends BasicCredentialsProvider {

	public void setCredentials(Credentials credentials) {
		this.setCredentials(AuthScope.ANY, credentials);
	}
	
}
