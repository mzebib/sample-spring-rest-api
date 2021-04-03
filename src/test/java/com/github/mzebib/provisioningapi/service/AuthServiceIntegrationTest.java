package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.exception.UnauthorizedException;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author mzebib
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@Ignore
public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    private String username;
    private String password;
    private String token;

    @Before
    public void init() {
        CreateUser createUser = TestData.createUserInfo();
        username = createUser.getUsername();
        password = createUser.getPassword();
        userService.createUser(createUser);

        TokenResponse tokenResponse = authService.login(username, password);
        token = tokenResponse.getToken();
    }

    @Test
    public void testLoginGivenValidCredentialsReturnsToken() {
        TokenResponse tokenResponse = authService.login(username, password);
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
    }

    @Test(expected = UnauthorizedException.class)
    public void testLoginGivenInvalidCredentialsThrowsException() {
        authService.login(username, "123");
    }

    @Test(expected = NullPointerException.class)
    public void testLoginGivenNullCredentialsThrowsException() {
        authService.login(null, null);
    }

    @Test
    public void testValidateTokenGivenValidTokenReturnsToken() {
        TokenResponse tokenResponse = authService.validateToken(token);
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertEquals(token, tokenResponse.getToken());
    }

    @Test(expected = UnauthorizedException.class)
    public void testValidateTokenGivenValidTokenThrowsException() {
        authService.validateToken("123");
    }

    @Test(expected = NullPointerException.class)
    public void testValidateTokenGivenNullTokenThrowsException() {
        authService.validateToken(null);
    }

    @Test
    public void testRenewTokenGivenValidTokenReturnsToken() {
        TokenResponse tokenResponse = authService.renewToken(token);
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertNotEquals(token, tokenResponse.getToken());
    }

    @Test(expected = UnauthorizedException.class)
    public void testRenewTokenGivenValidTokenThrowsException() {
        authService.validateToken("123");
    }

    @Test(expected = NullPointerException.class)
    public void testRenewTokenGivenNullTokenThrowsException() {
        authService.validateToken(null);
    }

    @Test
    public void testChangePasswordGivenValidInfoReturnsResponse() {
        DefaultResponse defaultResponse = authService.changePassword(new UserPasswordInfo(username, password, "newPassword"));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse);
    }

    @Test(expected = UnauthorizedException.class)
    public void testChangePasswordGivenInvalidInfoThrowsException() {
        authService.changePassword(new UserPasswordInfo(username, "currentPassword", "newPassword"));
    }

    @Test(expected = NullPointerException.class)
    public void testChangePasswordGivenNullInfoThrowsException() {
        authService.changePassword(null);
    }

}