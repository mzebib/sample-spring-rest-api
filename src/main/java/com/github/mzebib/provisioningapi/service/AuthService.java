package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.exception.DatabaseException;
import com.github.mzebib.provisioningapi.exception.UnauthorizedException;
import com.github.mzebib.provisioningapi.model.client.AuthInfo;
import com.github.mzebib.provisioningapi.model.client.DefaultResponse;
import com.github.mzebib.provisioningapi.model.client.TokenResponse;
import com.github.mzebib.provisioningapi.model.client.UserPasswordInfo;
import com.github.mzebib.provisioningapi.model.entity.CredentialsEntity;
import com.github.mzebib.provisioningapi.repository.CredentialsRepository;
import com.github.mzebib.provisioningapi.repository.UserRepository;
import com.github.mzebib.provisioningapi.security.TokenGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author mzebib
 */
@Service
public class AuthService {

    @Autowired
    private Logger log;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UserRepository userRepository;

    public AuthService() {
    }

    public TokenResponse login(AuthInfo authInfo) {
        if (authInfo == null) throw new NullPointerException("Missing credentials");

        return login(authInfo.getUsername(), authInfo.getPassword());
    }

    public TokenResponse login(String username, String password) {
        log.info("Login via username and password");

        if (StringUtils.isEmpty(username)) throw new NullPointerException("Missing username");
        if (StringUtils.isEmpty(password)) throw new NullPointerException("Missing password");

        CredentialsEntity credentialsEntity;

        try {
            credentialsEntity = credentialsRepository.lookupCredentialsByUsername(username.toLowerCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (credentialsEntity == null) {
            throw new UnauthorizedException("Invalid credentials");
        }

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(password, credentialsEntity.getPasswordHash());

        if (!authenticated) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token;

        try {
            token = TokenGenerator.generateToken();
            credentialsEntity.setToken(token);

            credentialsRepository.save(credentialsEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return new TokenResponse(token);
    }

    public TokenResponse validateToken(String token) {
        log.info("Validate token");

        if (StringUtils.isEmpty(token)) throw new NullPointerException("Missing token");

        CredentialsEntity credentialsEntity;

        try {
            credentialsEntity = credentialsRepository.lookupCredentialsByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (credentialsEntity == null || StringUtils.isEmpty(credentialsEntity.getToken())) {
            throw new UnauthorizedException("Invalid token");
        }

        return new TokenResponse(credentialsEntity.getToken());
    }

    public TokenResponse renewToken(String token) {
        log.info("Renew token");

        if (StringUtils.isEmpty(token)) throw new NullPointerException("Missing token");

        CredentialsEntity credentialsEntity;

        try {
            credentialsEntity = credentialsRepository.lookupCredentialsByToken(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (credentialsEntity == null || StringUtils.isEmpty(credentialsEntity.getToken())) {
            throw new UnauthorizedException("Invalid token");
        }

        credentialsEntity.setToken(TokenGenerator.generateToken());

        try {
            credentialsEntity = credentialsRepository.save(credentialsEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return new TokenResponse(credentialsEntity.getToken());
    }

    public DefaultResponse changePassword(UserPasswordInfo changePassword) {
        log.info("Change password");

        if (changePassword == null) throw new NullPointerException("Missing info");
        if (StringUtils.isEmpty(changePassword.getUsername())) throw new NullPointerException("Missing username");
        if (StringUtils.isEmpty(changePassword.getCurrentPassword())) throw new NullPointerException("Missing currentPassword");
        if (StringUtils.isEmpty(changePassword.getNewPassword())) throw new NullPointerException("Missing newPassword");

        CredentialsEntity credentialsEntity;

        try {
            credentialsEntity = credentialsRepository.lookupCredentialsByUsername(changePassword.getUsername().toLowerCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (credentialsEntity == null) {
            throw new UnauthorizedException("Invalid credentials");
        }

        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(changePassword.getCurrentPassword(), credentialsEntity.getPasswordHash());

        if (!authenticated) {
            throw new UnauthorizedException("Invalid credentials");
        }

        try {
            credentialsEntity.setPasswordHash(passwordEncoder.encode(changePassword.getNewPassword()));
            credentialsRepository.save(credentialsEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return new DefaultResponse("Password successfully changed.");
    }

}
