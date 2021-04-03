package com.github.mzebib.provisioningapi.controller.integration;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class UserControllerIntegrationTest extends ControllerTestBase {

    @Test
    public void testCreateUserGivenValidUserReturns201Created() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(USER_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(TestData.createUserInfo()));

        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        UserResponse userEntity = gson.fromJson(content, UserResponse.class);
        assertNotNull(userEntity);
        assertNotNull(userEntity.getId());
        assertNotNull(userEntity.getFirstName());
        assertNotNull(userEntity.getLastName());
        assertNotNull(userEntity.getEmail());
        assertNotNull(userEntity.getUsername());
    }

    @Test
    public void testCreateUserGivenInvalidUserReturns400BadRequest() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(USER_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(new CreateUser()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void testCreateUserGivenExistingUserReturns409Conflict() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(USER_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(TestData.createUserInfo()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is(409))
                .andReturn();
    }

    @Test
    public void testLookupUserGivenValidIDReturns200Ok() throws Exception {
        MvcResult result = mockMvc.perform(get(USER_URI + "/" + createdUser.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        UserResponse userResponse = gson.fromJson(content, UserResponse.class);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertEquals(createdUser.getId(), userResponse.getId());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertNotNull(userResponse.getUsername());
    }

    @Test
    public void testLookupUserGivenInvalidIDReturns404NotFound() throws Exception {
        mockMvc.perform(get(USER_URI + "/" + "1000"))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void testUpdateUserGivenValidIDReturns200Ok() throws Exception {
        createdUser.setEmail(System.currentTimeMillis() + "@test.com");

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(USER_URI + "/" + createdUser.getId());
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(createdUser));

        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        UserResponse updatedUserResponse = gson.fromJson(content, UserResponse.class);
        assertNotNull(updatedUserResponse);
        assertNotNull(updatedUserResponse.getId());
        assertEquals(createdUser.getId(), updatedUserResponse.getId());
        assertNotNull(updatedUserResponse.getFirstName());
        assertNotNull(updatedUserResponse.getLastName());
        assertNotNull(updatedUserResponse.getEmail());
        assertNotNull(updatedUserResponse.getUsername());
        assertEquals(createdUser.getEmail(), updatedUserResponse.getEmail());
    }

    @Test
    public void testDeleteUserGivenValidIDReturns200Ok() throws Exception {
        // Delete User
        mockMvc.perform(delete(USER_URI + "/" + createdUser.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteUserGivenInvalidIDReturns404NotFound() throws Exception {
        mockMvc.perform(delete(USER_URI + "/" + "1000"))
                .andExpect(status().is(404))
                .andReturn();
    }

}
