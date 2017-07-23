package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.EndNodeUnitService;
import com.eeu.smaartu.domain.EndNodeUnit;
import com.eeu.smaartu.repository.EndNodeUnitRepository;
import com.eeu.smaartu.service.dto.EndNodeUnitDTO;
import com.eeu.smaartu.service.mapper.EndNodeUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing EndNodeUnit.
 */
@Service
@Transactional
public class EndNodeUnitServiceImpl implements EndNodeUnitService{

    private final Logger log = LoggerFactory.getLogger(EndNodeUnitServiceImpl.class);

    private final EndNodeUnitRepository endNodeUnitRepository;

    private final EndNodeUnitMapper endNodeUnitMapper;

    public EndNodeUnitServiceImpl(EndNodeUnitRepository endNodeUnitRepository, EndNodeUnitMapper endNodeUnitMapper) {
        this.endNodeUnitRepository = endNodeUnitRepository;
        this.endNodeUnitMapper = endNodeUnitMapper;
    }

    /**
     * Save a endNodeUnit.
     *
     * @param endNodeUnitDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EndNodeUnitDTO save(EndNodeUnitDTO endNodeUnitDTO) {
        log.debug("Request to save EndNodeUnit : {}", endNodeUnitDTO);
        EndNodeUnit endNodeUnit = endNodeUnitMapper.toEntity(endNodeUnitDTO);
        endNodeUnit = endNodeUnitRepository.save(endNodeUnit);
        return endNodeUnitMapper.toDto(endNodeUnit);
    }

    /**
     *  Get all the endNodeUnits.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EndNodeUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EndNodeUnits");
        return endNodeUnitRepository.findAll(pageable)
            .map(endNodeUnitMapper::toDto);
    }


    /**
     *  get all the endNodeUnits where Condition is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EndNodeUnitDTO> findAllWhereConditionIsNull() {
        log.debug("Request to get all endNodeUnits where Condition is null");
        return StreamSupport
            .stream(endNodeUnitRepository.findAll().spliterator(), false)
            .filter(endNodeUnit -> endNodeUnit.getCondition() == null)
            .map(endNodeUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the endNodeUnits where Action is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EndNodeUnitDTO> findAllWhereActionIsNull() {
        log.debug("Request to get all endNodeUnits where Action is null");
        return StreamSupport
            .stream(endNodeUnitRepository.findAll().spliterator(), false)
            .filter(endNodeUnit -> endNodeUnit.getAction() == null)
            .map(endNodeUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one endNodeUnit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EndNodeUnitDTO findOne(Long id) {
        log.debug("Request to get EndNodeUnit : {}", id);
        EndNodeUnit endNodeUnit = endNodeUnitRepository.findOne(id);
        return endNodeUnitMapper.toDto(endNodeUnit);
    }

    /**
     *  Delete the  endNodeUnit by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EndNodeUnit : {}", id);
        endNodeUnitRepository.delete(id);
    }
}
