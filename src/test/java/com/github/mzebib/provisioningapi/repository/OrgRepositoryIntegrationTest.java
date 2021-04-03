package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.OrgEntity;
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
public class OrgRepositoryIntegrationTest {

    @Autowired
    private OrganizationRepository orgRepository;

    private OrgEntity createdOrg;

    @Before
    public void init() {
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setName("" + System.currentTimeMillis());
        orgEntity.setEmail("admin@example.com");
        orgEntity.setCity("New York");
        orgEntity.setState("NY");
        orgEntity.setCountry("USA");

        createdOrg = orgRepository.save(orgEntity);
    }

    @Test
    public void testDoesUserExist() {
        int count = orgRepository.doesOrgExist(createdOrg.getName());
        assertEquals(1, count);
    }

    @Test
    public void testCreateOrg() {
        assertNotNull(createdOrg);
        assertNotNull(createdOrg.getId());
        assertNotNull(createdOrg.getName());
        assertNotNull(createdOrg.getEmail());
        assertNotNull(createdOrg.getCity());
        assertNotNull(createdOrg.getState());
        assertNotNull(createdOrg.getCountry());
    }

    @Test
    public void testLookupOrgById() {
        OrgEntity orgEntity = orgRepository.findOne(createdOrg.getId());
        assertNotNull(orgEntity);
        assertNotNull(orgEntity.getId());
        assertEquals(createdOrg.getId(), orgEntity.getId());
        assertNotNull(orgEntity.getName());
        assertNotNull(orgEntity.getEmail());
        assertNotNull(orgEntity.getCity());
        assertNotNull(orgEntity.getState());
        assertNotNull(orgEntity.getCountry());
    }

    @Test
    public void testLookupOrgByName() {
        OrgEntity orgEntity = orgRepository.lookupOrgByName(createdOrg.getName().toLowerCase());
        assertNotNull(orgEntity);
        assertNotNull(orgEntity);
        assertNotNull(orgEntity.getId());
        assertNotNull(orgEntity.getName());
        assertNotNull(orgEntity.getEmail());
        assertNotNull(orgEntity.getCity());
        assertNotNull(orgEntity.getState());
        assertNotNull(orgEntity.getCountry());
    }

    @Test
    public void testUpdateOrg() {
        createdOrg.setCity("San Francisco");
        createdOrg.setState("CA");

        OrgEntity updatedOrgEntity = orgRepository.save(createdOrg);
        assertNotNull(updatedOrgEntity);
        assertNotNull(updatedOrgEntity.getId());
        assertEquals(createdOrg.getId(), updatedOrgEntity.getId());
        assertNotNull(updatedOrgEntity.getName());
        assertNotNull(updatedOrgEntity.getEmail());
        assertNotNull(updatedOrgEntity.getCity());
        assertEquals("San Francisco", updatedOrgEntity.getCity());
        assertNotNull(updatedOrgEntity.getState());
        assertEquals("CA", updatedOrgEntity.getState());
        assertNotNull(updatedOrgEntity.getCountry());
    }

    @Test
    public void testDeleteOrg() {
        orgRepository.delete(createdOrg.getId());
    }

}
