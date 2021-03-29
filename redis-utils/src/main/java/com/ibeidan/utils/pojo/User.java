package com.ibeidan.utils.pojo;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -41454103779699149L;
	public User(){
		
	}

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
     * 密码
     */
    private String password;

    private String salt;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Byte gender;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 姓名
     */
    private String idName;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 创建client
     */
    private String createClientId;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 更新时间
     */
    private Long mtime;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 修改者
     */
    private Long modifier;

    /**
     * 获取全局唯一id
     *
     * @return global_id - 全局唯一id
     */
    public Long getGlobalId() {
        return globalId;
    }

    /**
     * 设置全局唯一id
     *
     * @param globalId 全局唯一id
     */
    public void setGlobalId(Long globalId) {
        this.globalId = globalId;
    }

    /**
     * 获取账号
     *
     * @return username - 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号
     *
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * 获取身份证号
     *
     * @return id_number - 身份证号
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置身份证号
     *
     * @param idNumber 身份证号
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * 获取姓名
     *
     * @return id_name - 姓名
     */
    public String getIdName() {
        return idName;
    }

    /**
     * 设置姓名
     *
     * @param idName 姓名
     */
    public void setIdName(String idName) {
        this.idName = idName;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取创建client
     *
     * @return create_client_id - 创建client
     */
    public String getCreateClientId() {
        return createClientId;
    }

    /**
     * 设置创建client
     *
     * @param createClientId 创建client
     */
    public void setCreateClientId(String createClientId) {
        this.createClientId = createClientId;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * 获取修改者
     *
     * @return modifier - 修改者
     */
    public Long getModifier() {
        return modifier;
    }

    public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public Long getMtime() {
		return mtime;
	}

	public void setMtime(Long mtime) {
		this.mtime = mtime;
	}

	/**
     * 设置修改者
     *
     * @param modifier 修改者
     */
    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    @Override
    public String toString() {
        return "User{" +
                "globalId=" + globalId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status=" + status +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", idNumber='" + idNumber + '\'' +
                ", idName='" + idName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", createClientId='" + createClientId + '\'' +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                ", creator=" + creator +
                ", modifier=" + modifier +
                '}';
    }
}