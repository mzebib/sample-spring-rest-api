package com.github.mzebib.provisioningapi.controller;

import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.ErrorResponse;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import com.github.mzebib.provisioningapi.util.ProvConst;
import com.google.gson.Gson;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mzebib
 */
public abstract class ControllerTestBase {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected RestTemplate restTemplate = new RestTemplate();

    protected static final Gson gson = new Gson();

    private static final String HOST = "http://localhost";

    @LocalServerPort
    private int port;

    protected String baseUrl;
    protected String authLoginUrl;
    protected String authTokenUrl;
    protected String authPasswordUrl;
    protected String userUrl;
    protected String orgUrl;

    protected static final String AUTH_LOGIN_URI = ProvConst.URI_AUTH + ProvConst.URI_LOGIN;
    protected static final String AUTH_TOKEN_URI = ProvConst.URI_AUTH + ProvConst.URI_TOKEN;
    protected static final String AUTH_PASSWORD_URI = ProvConst.URI_AUTH + ProvConst.URI_PASSWORD;
    protected static final String USER_URI = ProvConst.URI_USER;
    protected static final String ORG_URI = ProvConst.URI_ORG;

    private TokenResponse tokenResponse;
    protected String username;
    protected String password;
    protected UserResponse createdUser;
    protected OrgResponse createdOrg;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        baseUrl = HOST + ":" + port + ProvConst.URI_API;
        authLoginUrl = baseUrl + AUTH_LOGIN_URI;
        authTokenUrl = baseUrl + AUTH_TOKEN_URI;
        authPasswordUrl = baseUrl + AUTH_PASSWORD_URI;
        userUrl = baseUrl + USER_URI;
        orgUrl = baseUrl + ORG_URI;

        if (createdUser == null) {
            // Create User
            CreateUser createUser = TestData.createUserInfo();
            username = createUser.getUsername();
            password = createUser.getPassword();
            MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(USER_URI);
            mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
            mockHttpServletRequestBuilder.content(gson.toJson(createUser));

            MvcResult resultUser = mockMvc.perform(mockHttpServletRequestBuilder)
                    .andExpect(status().isCreated())
                    .andReturn();

            createdUser = gson.fromJson(resultUser.getResponse().getContentAsString(), UserResponse.class);
        }

        if (createdOrg == null) {
            // Create Org
            OrgInfo createOrg = TestData.createOrgInfo();
            MockHttpServletRequestBuilder mockHttpServletRequestBuilderOrg = post(ORG_URI);
            mockHttpServletRequestBuilderOrg.contentType(MediaType.APPLICATION_JSON_VALUE);
            mockHttpServletRequestBuilderOrg.content(gson.toJson(createOrg));

            MvcResult resultOrg = mockMvc.perform(mockHttpServletRequestBuilderOrg)
                    .andExpect(status().isCreated())
                    .andReturn();

            createdOrg = gson.fromJson(resultOrg.getResponse().getContentAsString(), OrgResponse.class);
        }
    }

    protected String getToken() throws Exception {
        if (tokenResponse == null) {
            tokenResponse = login(username, password);
        }

        return tokenResponse.getToken();
    }

    protected TokenResponse login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post(AUTH_LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(new AuthInfo(username, password))))
                .andReturn();

        return gson.fromJson(result.getResponse().getContentAsString(), TokenResponse.class);
    }

    protected void assertErrorResponse(ResponseEntity<ErrorResponse> responseEntity, HttpStatus httpStatus) {
        assertNotNull(responseEntity);

        ErrorResponse errorResponse = responseEntity.getBody();
        assertNotNull(errorResponse);
        assertEquals(httpStatus.value(), errorResponse.getStatusCode());
    }

}
