package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.InLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InLocationRepository extends JpaRepository<InLocation,Long> {
    
}
