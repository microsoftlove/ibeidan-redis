package com.ibeidan.utils.pojo;

import java.io.Serializable;


public class OAuth2Authentication implements Serializable {
	private static final long serialVersionUID = -2759973121772214516L;
	
	
	private AuthClient authClient;
	private AuthUser authUser;
	private String scope ;
	private String grantType;

    public OAuth2Authentication() {
	}
	
	
	public OAuth2Authentication(AuthClient authClient, AuthUser authUser) {
		this.authClient = authClient;
		this.authUser = authUser;
		
		if(authClient != null){
			this.scope = authClient.getScope();
		}
	}
	public AuthClient getAuthClient() {
		return authClient;
	}
	public void setAuthClient(AuthClient authClient) {
		this.authClient = authClient;
	}
	public AuthUser getAuthUser() {
		return authUser;
	}
	public void setAuthUser(AuthUser authUser) {
		this.authUser = authUser;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	public boolean hasClientOnly() {
		return authClient !=null && authUser == null;
	}
}
