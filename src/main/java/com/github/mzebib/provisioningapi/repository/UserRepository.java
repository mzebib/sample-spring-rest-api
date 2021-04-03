package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mzebib
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM user u WHERE LOWER(u.username) = :username AND ROWNUM = 1")
    UserEntity lookupUserByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) FROM user u WHERE LOWER(u.username) = :username")
    int doesUserExist(@Param("username") String username);

}