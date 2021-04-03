package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.UserEntity;
import com.github.mzebib.provisioningapi.security.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author mzebib
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity createdUser;

    @Before
    public void init() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Test");
        userEntity.setLastName("User");
        userEntity.setEmail(System.currentTimeMillis() + "@example.com");
        userEntity.setUsername("" + System.currentTimeMillis());
        userEntity.setUserType(Role.USER.name());

        createdUser = userRepository.save(userEntity);
    }

    @Test
    public void testDoesUserExist() {
        int count = userRepository.doesUserExist(createdUser.getUsername());
        assertEquals(1, count);
    }

    @Test
    public void testCreateUser() {
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getFirstName());
        assertNotNull(createdUser.getLastName());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getUsername());
    }

    @Test
    public void testLookupUserById() {
        UserEntity userEntity = userRepository.findOne(createdUser.getId());
        assertNotNull(userEntity);
        assertNotNull(userEntity.getId());
        assertEquals(createdUser.getId(), userEntity.getId());
        assertNotNull(userEntity.getFirstName());
        assertNotNull(userEntity.getLastName());
        assertNotNull(userEntity.getEmail());
        assertNotNull(userEntity.getUsername());
    }

    @Test
    public void testLookupUserByUsername() {
        UserEntity userEntity = userRepository.lookupUserByUsername(createdUser.getUsername());
        assertNotNull(userEntity);
        assertNotNull(userEntity.getId());
        assertEquals(createdUser.getId(), userEntity.getId());
        assertNotNull(userEntity.getFirstName());
        assertNotNull(userEntity.getLastName());
        assertNotNull(userEntity.getEmail());
        assertNotNull(userEntity.getUsername());
    }

    @Test
    public void testUpdateUser() {
        String updatedEmail = System.currentTimeMillis() + "@example.com";
        createdUser.setEmail(updatedEmail);

        UserEntity updatedUserEntity = userRepository.save(createdUser);
        assertNotNull(updatedUserEntity);
        assertNotNull(updatedUserEntity.getId());
        assertEquals(createdUser.getId(), updatedUserEntity.getId());
        assertNotNull(updatedUserEntity.getFirstName());
        assertNotNull(updatedUserEntity.getLastName());
        assertNotNull(updatedUserEntity.getEmail());
        assertEquals(updatedEmail, updatedUserEntity.getEmail());
        assertNotNull(updatedUserEntity.getUsername());
    }

    @Test
    public void testDeleteUser() {
        userRepository.delete(createdUser.getId());
    }

}
