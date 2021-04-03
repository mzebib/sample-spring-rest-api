package com.github.mzebib.provisioningapi.service;

import com.github.mzebib.provisioningapi.exception.DatabaseException;
import com.github.mzebib.provisioningapi.exception.DuplicateEntityException;
import com.github.mzebib.provisioningapi.exception.NotFoundException;
import com.github.mzebib.provisioningapi.model.client.CreateUser;
import com.github.mzebib.provisioningapi.model.client.UserInfo;
import com.github.mzebib.provisioningapi.model.client.UserResponse;
import com.github.mzebib.provisioningapi.model.entity.CredentialsEntity;
import com.github.mzebib.provisioningapi.model.entity.UserEntity;
import com.github.mzebib.provisioningapi.repository.CredentialsRepository;
import com.github.mzebib.provisioningapi.repository.UserRepository;
import com.github.mzebib.provisioningapi.security.Role;
import com.github.mzebib.provisioningapi.validator.EmailValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author mzebib
 */
@Service
public class UserService {

    @Autowired
    private Logger log;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    public UserService() {
    }

    public boolean doesUserExist(String username) {
        if (StringUtils.isEmpty(username)) throw new NullPointerException("Missing username");

        try {
            int count = userRepository.doesUserExist(username);

            if (count == 0) {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return true;
    }

    public UserResponse createUser(CreateUser createUser) {
        log.info("Create user");

        if (createUser == null) throw new NullPointerException("Missing user information");
        if (StringUtils.isEmpty(createUser.getFirstName())) throw new NullPointerException("Missing firstName");
        if (StringUtils.isEmpty(createUser.getLastName())) throw new NullPointerException("Missing Missing lastName");
        if (StringUtils.isEmpty(createUser.getEmail())) throw new NullPointerException("Missing email");
        if (StringUtils.isEmpty(createUser.getUsername())) throw new NullPointerException("Missing username");
        if (StringUtils.isEmpty(createUser.getPassword())) throw new NullPointerException("Missing password");

        final String firstName = createUser.getFirstName().trim();
        final String lastName = createUser.getLastName().trim();
        final String email = EmailValidator.getInstance().validate(createUser.getEmail()).toLowerCase();
        final String username = createUser.getUsername().trim().toLowerCase();

        if (doesUserExist(username)) throw new DuplicateEntityException("User already exists");

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setUsername(username);
        userEntity.setUserType(Role.USER.name());

        try {
            userEntity = userRepository.save(userEntity);

            final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            credentialsRepository.save(
                    new CredentialsEntity(userEntity, passwordEncoder.encode(createUser.getPassword())));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("User already exists");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        final UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());

        return userResponse;
    }

    public UserResponse lookupUserById(Long id) {
        if (id == null) throw new NullPointerException("Missing ID");

        log.info("Lookup user by ID");

        UserEntity userEntity = lookupById(id);

        final UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());

        return userResponse;
    }

    private UserEntity lookupById(Long id) {
        UserEntity userEntity;

        try {
            userEntity = userRepository.findOne(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (userEntity == null) throw new NotFoundException("User not found");

        return userEntity;
    }

    public UserResponse lookupUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) throw new NullPointerException("Missing username");

        log.info("Lookup user by username");

        UserEntity userEntity;

        try {
            userEntity = userRepository.lookupUserByUsername(username.toLowerCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        if (userEntity == null) throw new NotFoundException("User not found");

        final UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());

        return userResponse;
    }

    public UserResponse updateUser(Long id, UserInfo userInfo) {
        log.info("Update user");

        if (id == null) throw new NullPointerException("Missing ID");
        if (userInfo == null) throw new NullPointerException("Missing user information");
        if (StringUtils.isEmpty(userInfo.getFirstName())) throw new NullPointerException("Missing firstName");
        if (StringUtils.isEmpty(userInfo.getLastName())) throw new NullPointerException("Missing Missing lastName");
        if (StringUtils.isEmpty(userInfo.getEmail())) throw new NullPointerException("Missing email");
        if (StringUtils.isEmpty(userInfo.getUsername())) throw new NullPointerException("Missing username");

        String firstName = userInfo.getFirstName().trim();
        String lastName = userInfo.getLastName().trim();
        String email = EmailValidator.getInstance().validate(userInfo.getEmail()).toLowerCase();
        String username = userInfo.getUsername().trim().toLowerCase();

        UserEntity userEntity = lookupById(id);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setUsername(username);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("User already exists");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        final UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());

        return userResponse;
    }

    public boolean deleteUser(Long id) {
        if (id == null) throw new NullPointerException("Missing ID");

        log.info("Delete user");

        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("User not found");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DatabaseException("Database error");
        }

        return true;
    }

}
