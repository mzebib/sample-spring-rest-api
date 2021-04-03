package com.github.mzebib.provisioningapi.controller;

import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import com.github.mzebib.provisioningapi.service.AuthService;
import com.github.mzebib.provisioningapi.util.ProvConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = ProvConst.URI_AUTH)
@Api(value = "auth", tags = "auth-endpoint")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController() {
    }

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = ProvConst.URI_LOGIN)
    @ApiOperation("Login via username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TokenResponse.class)
    })
    public ResponseEntity<TokenResponse> login(@ApiParam(required = true) @RequestBody AuthInfo authInfo) {
        TokenResponse response = authService.login(authInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = ProvConst.URI_TOKEN)
    @ApiOperation("Validate token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TokenResponse.class)
    })
    public ResponseEntity<TokenResponse> validateToken(@ApiParam(required = true) @RequestParam String token) {
        TokenResponse response = authService.validateToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = ProvConst.URI_TOKEN)
    @ApiOperation("Renew token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TokenResponse.class)
    })
    public ResponseEntity<TokenResponse> renewToken(@ApiParam(required = true) @RequestParam String token) {
        TokenResponse response = authService.renewToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = ProvConst.URI_PASSWORD)
    @ApiOperation("Change password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DefaultResponse.class)
    })
    public ResponseEntity<DefaultResponse> changePassword(@ApiParam(required = true) @RequestBody UserPasswordInfo userPasswordInfo) {
        DefaultResponse response = authService.changePassword(userPasswordInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
