package com.eeu.smaartu.service;

import com.eeu.smaartu.service.dto.EndNodeUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing EndNodeUnit.
 */
public interface EndNodeUnitService {

    /**
     * Save a endNodeUnit.
     *
     * @param endNodeUnitDTO the entity to save
     * @return the persisted entity
     */
    EndNodeUnitDTO save(EndNodeUnitDTO endNodeUnitDTO);

    /**
     *  Get all the endNodeUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EndNodeUnitDTO> findAll(Pageable pageable);
    /**
     *  Get all the EndNodeUnitDTO where Condition is null.
     *
     *  @return the list of entities
     */
    List<EndNodeUnitDTO> findAllWhereConditionIsNull();
    /**
     *  Get all the EndNodeUnitDTO where Action is null.
     *
     *  @return the list of entities
     */
    List<EndNodeUnitDTO> findAllWhereActionIsNull();

    /**
     *  Get the "id" endNodeUnit.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EndNodeUnitDTO findOne(Long id);

    /**
     *  Delete the "id" endNodeUnit.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
