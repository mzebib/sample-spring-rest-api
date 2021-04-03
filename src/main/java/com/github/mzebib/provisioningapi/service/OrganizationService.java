package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.exception.DatabaseException;
import com.github.mzebib.provisioningapi.exception.DuplicateEntityException;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
import com.github.mzebib.provisioningapi.model.entity.OrgEntity;
import com.github.mzebib.provisioningapi.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author mzebib
 */
@Service
public class OrganizationService {

    @Autowired
    private Logger log;

    @Autowired
    private OrganizationRepository orgRepository;

    public OrganizationService() {
    }

    public boolean doesOrgExist(String name) {
        if (StringUtils.isEmpty(name)) throw new NullPointerException("Missing name");

        try {
            int count = orgRepository.doesOrgExist(name);

            if (count == 0) {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return true;
    }

    public OrgResponse createOrg(OrgInfo orgInfo) {
        if (orgInfo == null) throw new NullPointerException("Missing organization information");
        if (StringUtils.isEmpty(orgInfo.getName())) throw new NullPointerException("Missing organization name");

        log.info("Create organization");

        if (doesOrgExist(orgInfo.getName().toLowerCase())) throw new DuplicateEntityException("Organization already exists");

        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setName(orgInfo.getName());
        orgEntity.setDescription(orgInfo.getDescription());
        orgEntity.setEmail(orgInfo.getEmail());
        orgEntity.setPhone(orgInfo.getPhone());
        orgEntity.setAddress(orgInfo.getAddress());
        orgEntity.setCity(orgInfo.getCity());
        orgEntity.setState(orgInfo.getState());
        orgEntity.setZipCode(orgInfo.getZipCode());
        orgEntity.setCountry(orgInfo.getCountry());

        try {
            orgEntity = orgRepository.save(orgEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("Organization already exists");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        final OrgResponse orgResponse = new OrgResponse();
        orgResponse.setId(orgEntity.getId());
        orgResponse.setName(orgEntity.getName());
        orgResponse.setDescription(orgEntity.getDescription());
        orgResponse.setEmail(orgEntity.getEmail());
        orgResponse.setPhone(orgEntity.getPhone());
        orgResponse.setAddress(orgEntity.getAddress());
        orgResponse.setCity(orgEntity.getCity());
        orgResponse.setState(orgEntity.getState());
        orgResponse.setZipCode(orgEntity.getZipCode());
        orgResponse.setCountry(orgEntity.getCountry());

        return orgResponse;
    }

    public OrgResponse lookupOrgById(Long id) {
        if (id == null) throw new NullPointerException("Missing ID");

        log.info("Lookup organization by ID");

        OrgEntity orgEntity = lookupById(id);

        final OrgResponse orgResponse = new OrgResponse();
        orgResponse.setId(orgEntity.getId());
        orgResponse.setName(orgEntity.getName());
        orgResponse.setDescription(orgEntity.getDescription());
        orgResponse.setEmail(orgEntity.getEmail());
        orgResponse.setPhone(orgEntity.getPhone());
        orgResponse.setAddress(orgEntity.getAddress());
        orgResponse.setCity(orgEntity.getCity());
        orgResponse.setState(orgEntity.getState());
        orgResponse.setZipCode(orgEntity.getZipCode());
        orgResponse.setCountry(orgEntity.getCountry());

        return orgResponse;
    }

    private OrgEntity lookupById(Long id) {
        OrgEntity orgEntity;

        try {
            orgEntity = orgRepository.findOne(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (orgEntity == null) throw new NotFoundException("Organization not found");

        return orgEntity;
    }

    public OrgResponse lookupOrgByName(String name) {
        if (StringUtils.isEmpty(name)) throw new NullPointerException("Missing name");

        log.info("Lookup organization by name");

        OrgEntity orgEntity;

        try {
            orgEntity = orgRepository.lookupOrgByName(name.toLowerCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error: " + e.getMessage());
        }

        if (orgEntity == null) throw new NotFoundException("Organization not found");

        final OrgResponse orgResponse = new OrgResponse();
        orgResponse.setId(orgEntity.getId());
        orgResponse.setName(orgEntity.getName());
        orgResponse.setDescription(orgEntity.getDescription());
        orgResponse.setEmail(orgEntity.getEmail());
        orgResponse.setPhone(orgEntity.getPhone());
        orgResponse.setAddress(orgEntity.getAddress());
        orgResponse.setCity(orgEntity.getCity());
        orgResponse.setState(orgEntity.getState());
        orgResponse.setZipCode(orgEntity.getZipCode());
        orgResponse.setCountry(orgEntity.getCountry());

        return orgResponse;
    }

    public OrgResponse updateOrg(Long id, OrgInfo orgInfo) {
        if (id == null) throw new NullPointerException("Missing ID");
        if (orgInfo == null) throw new NullPointerException("Missing orgEntity information");
        if (StringUtils.isEmpty(orgInfo.getName())) throw new NullPointerException("Missing organization name");

        log.info("Update organization");

        OrgEntity orgEntity = lookupById(id);

        if (!StringUtils.isEmpty(orgEntity.getName())
                && !orgEntity.getName().equalsIgnoreCase(orgInfo.getName())) {
            if (doesOrgExist(orgInfo.getName().toLowerCase())) {
                throw new DuplicateEntityException("Organization already exists");
            }
        }

        orgEntity.setName(orgInfo.getName());
        orgEntity.setDescription(orgInfo.getDescription());
        orgEntity.setEmail(orgInfo.getEmail());
        orgEntity.setPhone(orgInfo.getPhone());
        orgEntity.setAddress(orgInfo.getAddress());
        orgEntity.setCity(orgInfo.getCity());
        orgEntity.setState(orgInfo.getState());
        orgEntity.setZipCode(orgInfo.getZipCode());
        orgEntity.setCountry(orgInfo.getCountry());

        try {
            orgEntity = orgRepository.save(orgEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("Organization already exists");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        final OrgResponse orgResponse = new OrgResponse();
        orgResponse.setId(orgEntity.getId());
        orgResponse.setName(orgEntity.getName());
        orgResponse.setDescription(orgEntity.getDescription());
        orgResponse.setEmail(orgEntity.getEmail());
        orgResponse.setPhone(orgEntity.getPhone());
        orgResponse.setAddress(orgEntity.getAddress());
        orgResponse.setCity(orgEntity.getCity());
        orgResponse.setState(orgEntity.getState());
        orgResponse.setZipCode(orgEntity.getZipCode());
        orgResponse.setCountry(orgEntity.getCountry());

        return orgResponse;
    }

    public boolean deleteOrg(Long id) {
        if (id == null) throw new NullPointerException("Missing ID");

        log.info("Delete organization");

        try {
            orgRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Organization not found");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return true;
    }

}
