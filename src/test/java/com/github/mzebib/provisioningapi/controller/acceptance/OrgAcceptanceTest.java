package com.github.mzebib.provisioningapi.controller.acceptance;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
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
public class OrgAcceptanceTest extends ControllerTestBase {

    @Test
    public void testCreateOrgGivenValidOrgReturns201Created() {
        ResponseEntity<OrgResponse> responseEntity = restTemplate.exchange(orgUrl, HttpMethod.POST,
                new HttpEntity<>(TestData.createOrgInfo()), OrgResponse.class);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        final OrgResponse orgResponse = responseEntity.getBody();
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getAddress());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getZipCode());
    }

}