package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mzebib
 */
@Repository
public interface CredentialsRepository extends CrudRepository<CredentialsEntity, Long> {

    @Query("SELECT c FROM credentials c, user u WHERE LOWER(u.username) = :username AND c.user = u.id")
    CredentialsEntity lookupCredentialsByUsername(@Param("username") String username);

    @Query("SELECT c FROM credentials c WHERE c.token = :token")
    CredentialsEntity lookupCredentialsByToken(@Param("token") String token);

}
