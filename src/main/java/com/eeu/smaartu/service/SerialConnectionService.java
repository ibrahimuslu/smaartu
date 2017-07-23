package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.SerialConnectionDTO;
import java.util.List;

/**
 * Service Interface for managing SerialConnection.
 */
public interface SerialConnectionService {

    /**
     * Save a serialConnection.
     *
     * @param serialConnectionDTO the entity to save
     * @return the persisted entity
     */
    SerialConnectionDTO save(SerialConnectionDTO serialConnectionDTO);

    /**
     *  Get all the serialConnections.
     *
     *  @return the list of entities
     */
    List<SerialConnectionDTO> findAll();
    /**
     *  Get all the SerialConnectionDTO where EndNode is null.
     *
     *  @return the list of entities
     */
    List<SerialConnectionDTO> findAllWhereEndNodeIsNull();

    /**
     *  Get the "id" serialConnection.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SerialConnectionDTO findOne(Long id);

    /**
     *  Delete the "id" serialConnection.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
