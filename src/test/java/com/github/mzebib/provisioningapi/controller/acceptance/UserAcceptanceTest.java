package com.github.mzebib.provisioningapi.controller.acceptance;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
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
import static org.junit.Assert.assertNotNull;

/**
 * @author mzebib
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
public class UserAcceptanceTest extends ControllerTestBase {

    @Test
    public void testCreateUserGivenValidUserReturns201Created() {
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(userUrl, HttpMethod.POST,
                new HttpEntity<>(TestData.createUserInfo()), UserResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final UserResponse userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        assertNotNull(userResponse.getId());
        assertNotNull(userResponse.getFirstName());
        assertNotNull(userResponse.getLastName());
        assertNotNull(userResponse.getEmail());
        assertNotNull(userResponse.getUsername());
    }

}