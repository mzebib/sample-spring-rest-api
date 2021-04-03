package com.github.mzebib.provisioningapi.controller.unit;

import com.github.mzebib.provisioningapi.controller.OrganizationController;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
import com.github.mzebib.provisioningapi.service.OrganizationService;
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
public class OrgControllerUnitTest {

    private OrganizationController orgController;
    private OrganizationService orgService;

    private static OrgInfo orgInfo;
    private static OrgResponse orgResponse;

    private static final long ORG_ID = 1;

    @Before
    public void init() {
        orgService = mock(OrganizationService.class);
        orgController = new OrganizationController(orgService);

        orgInfo = new OrgInfo();
        orgInfo.setName("Example Corporation");
        orgInfo.setEmail(System.currentTimeMillis() + "@example.com");
        orgInfo.setCity("New York");
        orgInfo.setState("NY");
        orgInfo.setCountry("USA");

        orgResponse = new OrgResponse();
        orgResponse.setId(ORG_ID);
        orgResponse.setName("Example Corporation");
        orgResponse.setEmail(System.currentTimeMillis() + "@example.com");
        orgResponse.setCity("New York");
        orgResponse.setState("NY");
        orgResponse.setCountry("USA");
    }

    @Test
    public void testCreateOrgGivenValidOrgReturnsOrg() {
        when(orgService.createOrg(orgInfo)).thenReturn(orgResponse);

        ResponseEntity<OrgResponse> responseEntity = orgController.createOrg(orgInfo);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        OrgResponse orgResponse = responseEntity.getBody();
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test
    public void testLookupOrgGivenValidIDReturnsOrg() {
        when(orgService.lookupOrgById(ORG_ID)).thenReturn(orgResponse);

        ResponseEntity<OrgResponse> responseEntity = orgController.lookupOrgByID(ORG_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OrgResponse orgResponse = responseEntity.getBody();
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test(expected = NotFoundException.class)
    public void testLookupOrgGivenInvalidIDThrowsException() {
        long orgID = 1000;

        when(orgService.lookupOrgById(orgID)).thenThrow(NotFoundException.class);

        orgController.lookupOrgByID(orgID);
    }

    @Test
    public void testUpdateOrgGivenValidIDReturnsOrg() {
        when(orgService.updateOrg(ORG_ID, orgInfo)).thenReturn(orgResponse);

        ResponseEntity<OrgResponse> responseEntity = orgController.updateOrg(ORG_ID, orgInfo);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OrgResponse orgResponse = responseEntity.getBody();
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateOrgGivenInvalidIDThrowsException() {
        long orgID = 1000;

        when(orgService.updateOrg(orgID, orgInfo)).thenThrow(NotFoundException.class);

        orgController.updateOrg(orgID, orgInfo);
    }

    @Test
    public void testDeleteOrgGivenValidIDReturnsOrg() {
        when(orgService.deleteOrg(ORG_ID)).thenReturn(true);

        ResponseEntity<DefaultResponse> responseEntity = orgController.deleteOrg(ORG_ID);
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteOrgGivenInvalidIDThrowsException() {
        long orgID = 1000;

        when(orgService.deleteOrg(orgID)).thenThrow(NotFoundException.class);

        orgController.deleteOrg(orgID);
    }

}
