package oauth2client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {
    // this is a smart object. if client has valid token,
    // even if client calls this endpoint, it won't call authorization server
    // and will return the same token, because the token is valid and
    // there is no reason to ask authorization server to generate a new one
    private final OAuth2AuthorizedClientManager authorizedClientManager; // not clean code - should be in proxy

    @GetMapping("/token")
    public String token() {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId("1")
                .principal("client")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(request); // request to authorization server

        return authorizedClient.getAccessToken().getTokenValue(); // added on the Authorization header on the request "Bearer ..."
    }

}
