package com.eeu.smaartu.service.impl;

import com.eeu.smaartu.service.SerialConnectionService;
import com.eeu.smaartu.domain.SerialConnection;
import com.eeu.smaartu.repository.SerialConnectionRepository;
import com.eeu.smaartu.service.dto.SerialConnectionDTO;
import com.eeu.smaartu.service.mapper.SerialConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing SerialConnection.
 */
@Service
@Transactional
public class SerialConnectionServiceImpl implements SerialConnectionService{

    private final Logger log = LoggerFactory.getLogger(SerialConnectionServiceImpl.class);

    private final SerialConnectionRepository serialConnectionRepository;

    private final SerialConnectionMapper serialConnectionMapper;

    public SerialConnectionServiceImpl(SerialConnectionRepository serialConnectionRepository, SerialConnectionMapper serialConnectionMapper) {
        this.serialConnectionRepository = serialConnectionRepository;
        this.serialConnectionMapper = serialConnectionMapper;
    }

    /**
     * Save a serialConnection.
     *
     * @param serialConnectionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SerialConnectionDTO save(SerialConnectionDTO serialConnectionDTO) {
        log.debug("Request to save SerialConnection : {}", serialConnectionDTO);
        SerialConnection serialConnection = serialConnectionMapper.toEntity(serialConnectionDTO);
        serialConnection = serialConnectionRepository.save(serialConnection);
        return serialConnectionMapper.toDto(serialConnection);
    }

    /**
     *  Get all the serialConnections.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SerialConnectionDTO> findAll() {
        log.debug("Request to get all SerialConnections");
        return serialConnectionRepository.findAll().stream()
            .map(serialConnectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  get all the serialConnections where EndNode is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SerialConnectionDTO> findAllWhereEndNodeIsNull() {
        log.debug("Request to get all serialConnections where EndNode is null");
        return StreamSupport
            .stream(serialConnectionRepository.findAll().spliterator(), false)
            .filter(serialConnection -> serialConnection.getEndNode() == null)
            .map(serialConnectionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one serialConnection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SerialConnectionDTO findOne(Long id) {
        log.debug("Request to get SerialConnection : {}", id);
        SerialConnection serialConnection = serialConnectionRepository.findOne(id);
        return serialConnectionMapper.toDto(serialConnection);
    }

    /**
     *  Delete the  serialConnection by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SerialConnection : {}", id);
        serialConnectionRepository.delete(id);
    }
}
