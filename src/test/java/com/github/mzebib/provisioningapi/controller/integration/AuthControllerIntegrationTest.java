package com.github.mzebib.provisioningapi.controller.integration;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import com.github.mzebib.provisioningapi.util.ProvConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mzebib
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class AuthControllerIntegrationTest extends ControllerTestBase {

    @Test
    public void testLoginGivenValidCredentialsReturns200Ok() throws Exception {
        MvcResult result = mockMvc.perform(post(AUTH_LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new AuthInfo(username, password))))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        TokenResponse tokenResponse = gson.fromJson(content, TokenResponse.class);
        assertNotNull(tokenResponse);
        assertNotNull(getToken());
    }

    @Test
    public void testLoginGivenInvalidCredentialsReturns401Unauthorized() throws Exception {
        mockMvc.perform(post(AUTH_LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new AuthInfo(username, "123"))))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testLoginGivenMissingCredentialsReturns400BadRequest() throws Exception {
        mockMvc.perform(post(AUTH_LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new AuthInfo(null, null))))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testValidateTokenGivenValidTokenReturns200Ok() throws Exception {
        mockMvc.perform(get(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, getToken()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testValidateTokenGivenInvalidTokenReturns401Unauthorized() throws Exception {
        mockMvc.perform(get(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, "" + System.currentTimeMillis()))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testValidateTokenGivenMissingTokenReturns400BadRequest() throws Exception {
        mockMvc.perform(get(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, ""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testRenewTokenGivenValidTokenReturns200Ok() throws Exception {
        mockMvc.perform(put(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, getToken()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testRenewTokenGivenInvalidTokenReturns401Unauthorized() throws Exception {
        mockMvc.perform(put(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, "" + System.currentTimeMillis()))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testRenewTokenGivenMissingTokenReturns400BadRequest() throws Exception {
        mockMvc.perform(put(AUTH_TOKEN_URI)
                .param(ProvConst.PARAM_TOKEN, ""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void testChangePasswordGivenValidCredentialsReturns200Ok() throws Exception {
        String newPassword = "newPassword";

        mockMvc.perform(put(AUTH_PASSWORD_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new UserPasswordInfo(username, password, newPassword))))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post(AUTH_LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new AuthInfo(username, newPassword))))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testChangePasswordGivenInvalidCredentialsReturns401Unauthorized() throws Exception {
        mockMvc.perform(put(AUTH_PASSWORD_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new UserPasswordInfo(username, "currentPassword", "newPassword"))))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testChangePasswordGivenMissingCredentialsReturns400BadRequest() throws Exception {
        mockMvc.perform(put(AUTH_PASSWORD_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new UserPasswordInfo())))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
