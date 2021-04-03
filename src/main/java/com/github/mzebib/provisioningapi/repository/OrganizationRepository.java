package com.github.mzebib.provisioningapi.repository;

import com.github.mzebib.provisioningapi.model.entity.OrgEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author mzebib
 */
@Repository
public interface OrganizationRepository extends CrudRepository<OrgEntity, Long> {

    @Query("SELECT o FROM org o WHERE LOWER(name) = :name AND ROWNUM = 1")
    OrgEntity lookupOrgByName(@Param("name") String name);

    @Query("SELECT COUNT(*) FROM org o WHERE LOWER(o.name) = :name")
    int doesOrgExist(@Param("name") String name);
}
