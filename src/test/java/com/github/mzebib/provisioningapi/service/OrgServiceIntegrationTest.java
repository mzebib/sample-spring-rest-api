package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.exception.DuplicateEntityException;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
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
public class OrgServiceIntegrationTest {

    @Autowired
    private OrganizationService orgService;

    @Test
    public void testDoesOrgExistGivenValidOrgReturnsTrue() {
        OrgResponse orgResponse = orgService.createOrg(TestData.createOrgInfo());
        assertTrue(orgService.doesOrgExist(orgResponse.getName()));
    }

    @Test(expected = NullPointerException.class)
    public void testDoesOrgExistGivenNullOrgThrowsException() {
        orgService.doesOrgExist(null);
    }

    @Test
    public void testCreateOrgGivenValidOrgReturnsOrg() {
        OrgResponse orgResponse = orgService.createOrg(TestData.createOrgInfo());
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateOrgGivenNullOrgThrowsException() {
        orgService.createOrg(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateOrgMissingEmailThrowsException() {
        orgService.createOrg(new OrgInfo());
    }

    @Test(expected = DuplicateEntityException.class)
    public void testCreateOrgDuplicateOrgThrowsException() {
        OrgInfo orgInfo = TestData.createOrgInfo();
        orgService.createOrg(orgInfo);
        orgService.createOrg(orgInfo);
    }

    @Test
    public void testLookupOrgGivenValidIdReturnsOrg() {
        OrgResponse createdOrg = orgService.createOrg(TestData.createOrgInfo());

        OrgResponse orgResponse = orgService.lookupOrgById(createdOrg.getId());
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test(expected = NotFoundException.class)
    public void testLookupOrgGivenInvalidIdThrowsException() {
        orgService.lookupOrgById((long) 1000);
    }

    @Test
    public void testUpdateOrgGivenValidOrgReturnsOrg() {
        OrgInfo orgInfo = TestData.createOrgInfo();

        OrgResponse createdOrg = orgService.createOrg(orgInfo);
        orgInfo.setName("Example Corporation");

        OrgResponse orgResponse = orgService.updateOrg(createdOrg.getId(), orgInfo);
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertEquals("Example Corporation", orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateOrgGivenInvalidIdThrowsException() {
        orgService.updateOrg((long) 1000, TestData.createOrgInfo());
    }

    @Test
    public void testDeleteOrgGivenValidIdDeletesOrg() {
        orgService.createOrg(TestData.createOrgInfo());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteOrgGivenInvalidIdThrowsException() {
        orgService.deleteOrg((long) 1000);
    }

}
