package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public Credential geCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public Credential[] getCredentialList(Integer userId) {
        return credentialMapper.getCredentialListings(userId);
    }

    public void addCredential(String credentialUrl, String username, String credentialUsername, String key, String password) {
        Integer userId = userMapper.getUser(username).getUserId();
        Credential credential = new Credential(0, credentialUrl, credentialUsername, key, password, userId);
        credentialMapper.insert(credential);
    }

    public void updateCredential(Integer credentialId, String credentialUsername, String credentialUrl, String key, String password) {
        credentialMapper.updateCredential(credentialId, credentialUsername, credentialUrl, key, password);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

}
