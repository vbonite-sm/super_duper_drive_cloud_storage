package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialid;
    private Integer userid;
    private String credentialUsername;
    private String password;
    private String key;
    private String credentialUrl;

    public Credential(Integer credentialid, String credentialUrl, String credentialUsername, String key, String password, Integer userid) {
        this.credentialid = credentialid;
        this.userid = userid;
        this.credentialUsername = credentialUsername;
        this.password = password;
        this.key = key;
        this.credentialUrl = credentialUrl;
    }

    public Credential(String credentialUrl, String credentialUsername, String password) {
        this.credentialUsername = credentialUsername;
        this.password = password;
        this.credentialUrl = credentialUrl;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }
}
