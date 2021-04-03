package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.CredentialsEntity;
import com.github.mzebib.provisioningapi.model.entity.UserEntity;
import com.github.mzebib.provisioningapi.security.Role;
import com.github.mzebib.provisioningapi.security.TokenGenerator;
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
public class CredentialsRepositoryIntegrationTest {

    @Autowired
    private CredentialsRepository credentialsRepository;

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
    public void testCreateCredentials() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUser(createdUser);
        credentialsEntity.setPasswordHash("passwordHash");
        credentialsEntity.setToken(TokenGenerator.generateToken());

        credentialsEntity = credentialsRepository.save(credentialsEntity);
        assertNotNull(credentialsEntity);
        assertNotNull(credentialsEntity.getId());
        assertNotNull(credentialsEntity.getUser());
        assertNotNull(credentialsEntity.getPasswordHash());
        assertNotNull(credentialsEntity.getToken());
    }

    @Test
    public void testLookupCredentialsById() {
        CredentialsEntity createdCredentialsEntity = new CredentialsEntity();
        createdCredentialsEntity.setUser(createdUser);
        createdCredentialsEntity.setPasswordHash("passwordHash");
        createdCredentialsEntity.setToken(TokenGenerator.generateToken());

        createdCredentialsEntity = credentialsRepository.save(createdCredentialsEntity);

        CredentialsEntity credentialsEntity = credentialsRepository.findOne(createdCredentialsEntity.getId());
        assertNotNull(credentialsEntity);
        assertNotNull(credentialsEntity.getId());
        assertEquals(createdCredentialsEntity.getId(), credentialsEntity.getId());
    }

    @Test
    public void testLookupCredentialsByToken() {
        CredentialsEntity createdCredentialsEntity = new CredentialsEntity();
        createdCredentialsEntity.setUser(createdUser);
        createdCredentialsEntity.setPasswordHash("passwordHash");
        createdCredentialsEntity.setToken(TokenGenerator.generateToken());

        createdCredentialsEntity = credentialsRepository.save(createdCredentialsEntity);

        CredentialsEntity credentialsEntity = credentialsRepository.lookupCredentialsByToken(createdCredentialsEntity.getToken());
        assertNotNull(credentialsEntity);
        assertNotNull(credentialsEntity.getId());
        assertEquals(createdCredentialsEntity.getId(), credentialsEntity.getId());
    }

    @Test
    public void testUpdateCredentials() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUser(createdUser);
        credentialsEntity.setPasswordHash("passwordHash");
        credentialsEntity.setToken(TokenGenerator.generateToken());

        credentialsEntity = credentialsRepository.save(credentialsEntity);
        credentialsEntity.setPasswordHash("passwordHash123");

        credentialsEntity = credentialsRepository.save(credentialsEntity);
        assertNotNull(credentialsEntity);
        assertNotNull(credentialsEntity.getId());
        assertNotNull(credentialsEntity.getUser());
        assertNotNull(credentialsEntity.getPasswordHash());
        assertEquals("passwordHash123", credentialsEntity.getPasswordHash());
        assertNotNull(credentialsEntity.getToken());
    }

    @Test
    public void testDeleteCredentials() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUser(createdUser);
        credentialsEntity.setPasswordHash("passwordHash");
        credentialsEntity.setToken(TokenGenerator.generateToken());

        credentialsEntity = credentialsRepository.save(credentialsEntity);
        credentialsRepository.delete(credentialsEntity.getId());
    }
}
