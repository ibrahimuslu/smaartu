package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.SerialConnection;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SerialConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SerialConnectionRepository extends JpaRepository<SerialConnection,Long> {
    
}
