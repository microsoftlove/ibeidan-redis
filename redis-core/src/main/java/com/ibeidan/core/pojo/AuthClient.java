package com.ibeidan.core.pojo;



import java.io.Serializable;

public class AuthClient implements  Serializable {
	private static final long serialVersionUID = -4373455537236557927L;

	public AuthClient(){
		
	}
	
	private String clientId;

    /**
     * 业务线id
     */
    private Long bizId;

    /**
     * client名称
     */
    private String name;

    /**
     * 客户端所能访问的资源id集合
     */
    private String resourceIds;

    /**
     * 指定客户端申请的权限范围
     */
    private String scope;

    /**
     * 指定客户端在用户中心的权限值，如：CLIENT,USER,RESOURCE
     */
    private String authorities;


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}


	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}


	@Override
	public String toString() {
		return "AuthClient{" +
				"clientId='" + clientId + '\'' +
				", bizId=" + bizId +
				", name='" + name + '\'' +
				", resourceIds='" + resourceIds + '\'' +
				", scope='" + scope + '\'' +
				", authorities='" + authorities + '\'' +
				'}';
	}
}
