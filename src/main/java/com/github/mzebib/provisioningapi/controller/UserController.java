package com.github.mzebib.provisioningapi.controller;

import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.UserInfo;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import com.github.mzebib.provisioningapi.service.UserService;
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
@RequestMapping(value = ProvConst.URI_USER)
@Api(value = "user", tags = "user-endpoint")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController() {
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation("Create user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> createUser(@ApiParam(required = true) @RequestBody CreateUser createUser) {
        UserResponse userResponse = userService.createUser(createUser);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> lookupUserById(@PathVariable("id") long id) {
        UserResponse userResponse = userService.lookupUserById(id);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get user by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> lookupUserByUsername(@ApiParam(required = true) @RequestParam("username") String username) {
        UserResponse userResponse = userService.lookupUserByUsername(username);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Update user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") long id,
                                                   @ApiParam(required = true) @RequestBody UserInfo userInfo) {
        UserResponse userResponse = userService.updateUser(id, userInfo);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = ProvConst.PATH_PARAM_ID)
    @ApiOperation("Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class)
    })
    public ResponseEntity<DefaultResponse> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(new DefaultResponse("User deleted."), HttpStatus.OK);
    }

}
