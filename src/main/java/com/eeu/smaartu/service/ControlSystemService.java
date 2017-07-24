package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.ControlSystemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ControlSystem.
 */
public interface ControlSystemService {

    /**
     * Save a controlSystem.
     *
     * @param controlSystemDTO the entity to save
     * @return the persisted entity
     */
    ControlSystemDTO save(ControlSystemDTO controlSystemDTO);

    /**
     *  Get all the controlSystems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ControlSystemDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" controlSystem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ControlSystemDTO findOne(Long id);

    /**
     *  Delete the "id" controlSystem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
