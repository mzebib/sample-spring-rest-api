package com.github.mzebib.provisioningapi.controller.unit;

import com.github.mzebib.provisioningapi.controller.AuthController;
import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.service.AuthService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author mzebib
 */
public class AuthControllerUnitTest {

    private AuthController authController;
    private AuthService authService;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PWD = "testpwd";
    private static final String TEST_TOKEN = "ul956xrDPa6gna4j5kHgDLuq1RrvgPddTRUSLscqKyAnnY2fprPxl";

    @Before
    public void init() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    public void testLoginGivenValidCredentialsReturnsResponse() {
        final AuthInfo authInfo = new AuthInfo(TEST_USERNAME, TEST_PWD);

        when(authService.login(authInfo)).thenReturn(new TokenResponse(TEST_TOKEN));

        ResponseEntity<TokenResponse> responseEntity = authController.login(authInfo);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertEquals(TEST_TOKEN, tokenResponse.getToken());
    }

    @Test
    public void testValidateTokenGivenValidTokenReturnsResponse() {
        when(authService.validateToken(TEST_TOKEN)).thenReturn(new TokenResponse(TEST_TOKEN));

        ResponseEntity<TokenResponse> responseEntity = authController.validateToken(TEST_TOKEN);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertEquals(TEST_TOKEN, tokenResponse.getToken());
    }

    @Test
    public void testRenewTokenGivenValidTokenReturnsResponse() {
        when(authService.renewToken(TEST_TOKEN)).thenReturn(new TokenResponse("" + System.currentTimeMillis()));

        ResponseEntity<TokenResponse> responseEntity = authController.renewToken(TEST_TOKEN);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        TokenResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getToken());
        assertNotEquals(TEST_TOKEN, tokenResponse.getToken());
    }

    @Test
    public void testChangePasswordGivenValidCredentialsReturnsResponse() {
        final UserPasswordInfo userPasswordInfo = new UserPasswordInfo(TEST_USERNAME, TEST_PWD, "newPassword");

        when(authService.changePassword(userPasswordInfo)).thenReturn(new DefaultResponse("Password successfully changed."));

        ResponseEntity<DefaultResponse> responseEntity = authController.changePassword(userPasswordInfo);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        DefaultResponse tokenResponse = responseEntity.getBody();
        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getMessage());
    }
}
