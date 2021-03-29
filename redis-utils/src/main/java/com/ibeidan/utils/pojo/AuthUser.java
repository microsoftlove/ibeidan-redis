package com.ibeidan.utils.pojo;




import java.io.Serializable;


public class AuthUser implements Serializable{
	
	private static final long serialVersionUID = -8851872412928814174L;
	public AuthUser(){
		
	}

	/**
     * 业务线信息
     */
    private Long bizId;
    private Long bizUserId;
	
	/**
     * 全局唯一id
     */
    private Long globalId;

    /**
     * 账号
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 设备号
     */
    private String deviceId;


    public Long getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Long globalId) {
		this.globalId = globalId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getBizUserId() {
        return bizUserId;
    }

    public void setBizUserId(Long bizUserId) {
        this.bizUserId = bizUserId;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "bizId=" + bizId +
                ", bizUserId=" + bizUserId +
                ", globalId=" + globalId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
