package com.eeu.smaartu.repository;

import com.eeu.smaartu.domain.EndNode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EndNode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndNodeRepository extends JpaRepository<EndNode,Long> {
    
}
