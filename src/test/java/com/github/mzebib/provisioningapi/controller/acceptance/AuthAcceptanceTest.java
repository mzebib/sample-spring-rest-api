package com.github.mzebib.provisioningapi.controller.acceptance;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author mzebib
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class AuthAcceptanceTest extends ControllerTestBase {

    @Test
    public void testLoginGivenValidCredentialsReturns200Ok() {
        HttpEntity<AuthInfo> httpEntity
                = new HttpEntity<>(new AuthInfo(username, password));

        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(authLoginUrl, HttpMethod.POST,
                httpEntity, TokenResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
    }

    @Test
    public void testValidateTokenGivenValidTokenReturns200Ok() throws Exception {
        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(authTokenUrl + "?token=" + getToken(), HttpMethod.GET,
                HttpEntity.EMPTY, TokenResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertEquals(tokenResponse.getToken(), getToken());
    }

    @Test
    public void testRenewTokenGivenValidTokenReturns200Ok() throws Exception {
        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(authTokenUrl + "?token=" + getToken(), HttpMethod.PUT,
                HttpEntity.EMPTY, TokenResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertNotEquals(tokenResponse.getToken(), getToken());
    }

    @Test
    public void testChangePasswordGivenValidCredentialsReturns200Ok() throws Exception {
        String newPassword = "newPassword";

        HttpEntity<UserPasswordInfo> httpEntity
                = new HttpEntity<>(new UserPasswordInfo(username, password, newPassword));

        ResponseEntity<DefaultResponse> responseEntity = restTemplate.exchange(authPasswordUrl, HttpMethod.PUT,
                httpEntity, DefaultResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final TokenResponse tokenResponse = login(username, newPassword);
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
    }

}