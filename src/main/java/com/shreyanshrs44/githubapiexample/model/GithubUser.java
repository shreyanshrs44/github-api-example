package com.shreyanshrs44.githubapiexample.model;

import org.springframework.stereotype.Component;

@Component
public class GithubUser {
    private String username;
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
