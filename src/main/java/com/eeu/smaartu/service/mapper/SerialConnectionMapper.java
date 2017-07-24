package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.SerialConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SerialConnection and its DTO SerialConnectionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SerialConnectionMapper extends EntityMapper <SerialConnectionDTO, SerialConnection> {
    
    @Mapping(target = "endNode", ignore = true)
    SerialConnection toEntity(SerialConnectionDTO serialConnectionDTO); 
    default SerialConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        SerialConnection serialConnection = new SerialConnection();
        serialConnection.setId(id);
        return serialConnection;
    }
}
