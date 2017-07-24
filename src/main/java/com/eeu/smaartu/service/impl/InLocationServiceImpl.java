package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.InLocationService;
import com.eeu.smaartu.domain.InLocation;
import com.eeu.smaartu.repository.InLocationRepository;
import com.eeu.smaartu.service.dto.InLocationDTO;
import com.eeu.smaartu.service.mapper.InLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InLocation.
 */
@Service
@Transactional
public class InLocationServiceImpl implements InLocationService{

    private final Logger log = LoggerFactory.getLogger(InLocationServiceImpl.class);

    private final InLocationRepository inLocationRepository;

    private final InLocationMapper inLocationMapper;

    public InLocationServiceImpl(InLocationRepository inLocationRepository, InLocationMapper inLocationMapper) {
        this.inLocationRepository = inLocationRepository;
        this.inLocationMapper = inLocationMapper;
    }

    /**
     * Save a inLocation.
     *
     * @param inLocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InLocationDTO save(InLocationDTO inLocationDTO) {
        log.debug("Request to save InLocation : {}", inLocationDTO);
        InLocation inLocation = inLocationMapper.toEntity(inLocationDTO);
        inLocation = inLocationRepository.save(inLocation);
        return inLocationMapper.toDto(inLocation);
    }

    /**
     *  Get all the inLocations.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InLocationDTO> findAll() {
        log.debug("Request to get all InLocations");
        return inLocationRepository.findAll().stream()
            .map(inLocationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one inLocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InLocationDTO findOne(Long id) {
        log.debug("Request to get InLocation : {}", id);
        InLocation inLocation = inLocationRepository.findOne(id);
        return inLocationMapper.toDto(inLocation);
    }

    /**
     *  Delete the  inLocation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InLocation : {}", id);
        inLocationRepository.delete(id);
    }
}
