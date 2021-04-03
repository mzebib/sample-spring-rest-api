package com.github.mzebib.provisioningapi.controller.unit;

import com.github.mzebib.provisioningapi.controller.UserController;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import com.github.mzebib.provisioningapi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author mzebib
 */
public class UserControllerUnitTest {

    private UserController userController;
    private UserService userService;

    private static CreateUser createUser;
    private static UserResponse userResponse;

    private static final long USER_ID = 1;

    @Before
    public void init() {
        userService = mock(UserService.class);
        userController = new UserController(userService);

        createUser = new CreateUser();
        createUser.setFirstName("Test");
        createUser.setLastName("User");
        createUser.setEmail("testuser@gmail.com");
        createUser.setUsername("testuser");
        createUser.setPassword("testpwd");

        userResponse = new UserResponse();
        userResponse.setId(USER_ID);
        userResponse.setFirstName("Test");
        userResponse.setLastName("User");
        userResponse.setEmail("testuser@gmail.com");
        userResponse.setUsername("testuser");
    }

    @Test
    public void testCreateUserGivenValidUserReturnsUser() {
        when(userService.createUser(createUser)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.createUser(createUser);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        UserResponse userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertNotNull(userResponse.getUsername());
    }

    @Test
    public void testLookupUserGivenValidIDReturnsUser() {
        when(userService.lookupUserById(USER_ID)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.lookupUserById(USER_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserResponse userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertNotNull(userResponse.getUsername());
    }

    @Test(expected = NotFoundException.class)
    public void testLookupUserGivenInvalidIDThrowsException() {
        long userID = 1000;

        when(userService.lookupUserById(userID)).thenThrow(NotFoundException.class);

        userController.lookupUserById(userID);
    }

    @Test
    public void testUpdateUserGivenValidIDReturnsUser() {
        when(userService.updateUser(USER_ID, createUser)).thenReturn(userResponse);

        ResponseEntity<UserResponse> responseEntity = userController.updateUser(USER_ID, createUser);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        UserResponse userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertNotNull(userResponse.getUsername());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateUserGivenInvalidIDThrowsException() {
        long userID = 1000;

        when(userService.updateUser(userID, createUser)).thenThrow(NotFoundException.class);

        userController.updateUser(userID, createUser);
    }

    @Test
    public void testDeleteUserGivenValidIDReturnsResponse() {
        when(userService.deleteUser(USER_ID)).thenReturn(true);

        ResponseEntity<DefaultResponse> responseEntity = userController.deleteUser(USER_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteUserGivenInvalidIDThrowsException() {
        long userID = 1000;

        when(userService.deleteUser(userID)).thenThrow(NotFoundException.class);

        userController.deleteUser(userID);
    }

}
