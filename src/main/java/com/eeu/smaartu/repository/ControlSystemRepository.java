package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.ControlSystem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ControlSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlSystemRepository extends JpaRepository<ControlSystem,Long> {
    
}
