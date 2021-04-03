package com.github.mzebib.provisioningapi.controller;

import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.OrgInfo;
import com.github.mzebib.provisioningapi.model.client.OrgResponse;
import com.github.mzebib.provisioningapi.service.OrganizationService;
import com.github.mzebib.provisioningapi.util.ProvConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mzebib
 */
@RestController
@RequestMapping(value = ProvConst.URI_ORG)
@Api(value = "org", tags = "org-endpoint")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    public OrganizationController() {
    }

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    @ApiOperation("Create organization")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = OrgResponse.class)
    })
    public ResponseEntity<OrgResponse> createOrg(@ApiParam(required = true) @RequestBody OrgInfo orgInfo) {
        OrgResponse orgResponse = organizationService.createOrg(orgInfo);

        return new ResponseEntity<>(orgResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Get organization by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OrgResponse.class)
    })
    public ResponseEntity<OrgResponse> lookupOrgByID(@PathVariable("id") long id) {
        OrgResponse orgResponse = organizationService.lookupOrgById(id);

        return new ResponseEntity<>(orgResponse, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get organization by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OrgResponse.class)
    })
    public ResponseEntity<OrgResponse> lookupOrgByName(@ApiParam(required = true) @RequestParam("name") String name) {
        OrgResponse orgResponse = organizationService.lookupOrgByName(name);

        return new ResponseEntity<>(orgResponse, HttpStatus.OK);
    }

    @PutMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Update organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OrgResponse.class)
    })
    public ResponseEntity<OrgResponse> updateOrg(@PathVariable("id") long id,
                                                 @ApiParam(required = true) @RequestBody OrgInfo orgInfo) {
        OrgResponse orgResponse = organizationService.updateOrg(id, orgInfo);

        return new ResponseEntity<>(orgResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Delete organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OrgResponse.class)
    })
    public ResponseEntity<DefaultResponse> deleteOrg(@PathVariable("id") long id) {
        organizationService.deleteOrg(id);

        return new ResponseEntity<>(new DefaultResponse("Organization deleted."), HttpStatus.OK);
    }

}
