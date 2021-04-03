package com.github.mzebib.provisioningapi.controller.integration;

import com.github.mzebib.provisioningapi.controller.ControllerTestBase;
import com.github.mzebib.provisioningapi.data.TestData;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
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
public class OrgControllerIntegrationTest extends ControllerTestBase {

    @Test
    public void testCreateOrgGivenValidOrgReturns201Created() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(ORG_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(TestData.createOrgInfo()));

        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        OrgResponse orgResponse = gson.fromJson(content, OrgResponse.class);
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test
    public void testCreateOrgGivenInvalidOrgReturns400BadRequest() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(ORG_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(new OrgInfo()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void testCreateOrgGivenExistingOrgReturns409Conflict() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = post(ORG_URI);
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(TestData.createOrgInfo()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().is(409))
                .andReturn();
    }

    @Test
    public void testLookupOrgGivenValidIDReturns200Ok() throws Exception {
        MvcResult result = mockMvc.perform(get(ORG_URI + "/" + createdOrg.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        OrgResponse orgResponse = gson.fromJson(content, OrgResponse.class);
        assertNotNull(orgResponse);
        assertNotNull(orgResponse.getId());
        assertEquals(createdOrg.getId(), orgResponse.getId());
        assertNotNull(orgResponse.getName());
        assertNotNull(orgResponse.getEmail());
        assertNotNull(orgResponse.getCity());
        assertNotNull(orgResponse.getState());
        assertNotNull(orgResponse.getCountry());
    }

    @Test
    public void testLookupOrgGivenInvalidIDReturns404NotFound() throws Exception {
        mockMvc.perform(get(ORG_URI + "/" + "1000"))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void testUpdateOrgGivenValidIDReturns200Ok() throws Exception {
        createdOrg.setName("XYZ Corporation");

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = put(ORG_URI + "/" + createdOrg.getId());
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE);
        mockHttpServletRequestBuilder.content(gson.toJson(createdOrg));

        MvcResult result = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertNotNull(content);

        OrgResponse updatedOrgResponse = gson.fromJson(content, OrgResponse.class);
        assertNotNull(updatedOrgResponse);
        assertNotNull(updatedOrgResponse.getId());
        assertEquals(createdOrg.getId(), updatedOrgResponse.getId());
        assertNotNull(updatedOrgResponse.getName());
        assertEquals("XYZ Corporation", updatedOrgResponse.getName());
        assertNotNull(updatedOrgResponse.getEmail());
        assertNotNull(updatedOrgResponse.getCity());
        assertNotNull(updatedOrgResponse.getState());
        assertNotNull(updatedOrgResponse.getCountry());
    }

    @Test
    public void testDeleteOrgGivenValidIDReturns200Ok() throws Exception {
        mockMvc.perform(delete(ORG_URI + "/" + createdOrg.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteOrgGivenInvalidIDReturns404NotFound() throws Exception {
        mockMvc.perform(delete(ORG_URI + "/" + "1000"))
                .andExpect(status().is(404))
                .andReturn();
    }

}
