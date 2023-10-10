package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialModel {
    private String credentialUrl;
    private String credentialUsername;
    private String password;
    private String credentialId;

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
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

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }
}
