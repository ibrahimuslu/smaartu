package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.EndNodeUnit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EndNodeUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndNodeUnitRepository extends JpaRepository<EndNodeUnit,Long> {
    
}
