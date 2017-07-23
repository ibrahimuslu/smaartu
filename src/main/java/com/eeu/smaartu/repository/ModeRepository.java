package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.Mode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeRepository extends JpaRepository<Mode,Long> {
    
}
