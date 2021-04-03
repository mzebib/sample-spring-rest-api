package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.exception.DuplicateEntityException;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author mzebib
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void testDoesUserExistGivenValidUserReturnsTrue() {
        UserResponse userResponse = userService.createUser(TestData.createUserInfo());
        assertTrue(userService.doesUserExist(userResponse.getUsername()));
    }

    @Test(expected = NullPointerException.class)
    public void testDoesUserExistGivenNullUserThrowsException() {
        userService.doesUserExist(null);
    }

    @Test
    public void testCreateUserGivenValidUserReturnsUser() {
        UserResponse userResponse = userService.createUser(TestData.createUserInfo());
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getUsername());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserGivenNullUserThrowsException() {
        userService.createUser(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserMissingEmailThrowsException() {
        userService.createUser(new CreateUser());
    }

    @Test(expected = DuplicateEntityException.class)
    public void testCreateUserDuplicateUserThrowsException() {
        CreateUser createUser = TestData.createUserInfo();
        userService.createUser(createUser);
        userService.createUser(createUser);
    }

    @Test
    public void testLookupUserGivenValidIdReturnsUser() {
        UserResponse createdUser = userService.createUser(TestData.createUserInfo());

        UserResponse userResponse = userService.lookupUserById(createdUser.getId());
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getUsername());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
    }

    @Test
    public void testLookupUserGivenValidUsernameReturnsUser() {
        UserResponse createdUser = userService.createUser(TestData.createUserInfo());

        UserResponse userResponse = userService.lookupUserByUsername(createdUser.getUsername());
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getUsername());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void testLookupUserGivenInvalidIdThrowsException() {
        userService.lookupUserById((long) 1000);
    }

    @Test
    public void testUpdateUserGivenValidUserReturnsUser() {
        CreateUser createUser = TestData.createUserInfo();

        UserResponse createdUser = userService.createUser(createUser);
        createUser.setEmail("jsmith5@example.com");

        UserResponse userResponse = userService.updateUser(createdUser.getId(), createUser);
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getUsername());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertEquals("jsmith5@example.com", userResponse.getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateUserGivenInvalidIdThrowsException() {
        userService.updateUser((long) 1000, TestData.createUserInfo());
    }

    @Test
    public void testDeleteUserGivenValidIdReturnsTrue() {
        UserResponse userResponse = userService.createUser(TestData.createUserInfo());

        assertTrue(userService.deleteUser(userResponse.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteUserGivenInvalidIdThrowsException() {
        userService.deleteUser((long) 1000);
    }

}
