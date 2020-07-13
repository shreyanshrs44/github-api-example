package com.shreyanshrs44.githubapiexample.controller;

import com.shreyanshrs44.githubapiexample.model.GithubUser;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ApiController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private GithubUser githubUser;


    @GetMapping("/")
    public void initialize(@AuthenticationPrincipal OAuth2User principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());
        String accessToken = client.getAccessToken().getTokenValue();
        githubUser.setAccessToken(accessToken);
    }

    @GetMapping("/getAccessToken")
    public String getAccessToken() throws IOException {
        return githubUser.getAccessToken();
    }

    @GetMapping("/createRepository/{repoName}")
    public String createRepo(@PathVariable(name="repoName") String repoName) throws IOException {
        GitHub github = new GitHubBuilder().withAppInstallationToken(githubUser.getAccessToken()).build();
        GHCreateRepositoryBuilder myRepository = github.createRepository(repoName);
        myRepository.create();
        return repoName;
    }
    
}
