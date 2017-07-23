package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.ControlSystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ControlSystem and its DTO ControlSystemDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, })
public interface ControlSystemMapper extends EntityMapper <ControlSystemDTO, ControlSystem> {

    @Mapping(source = "location.id", target = "locationId")
    ControlSystemDTO toDto(ControlSystem controlSystem); 

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "nodes", ignore = true)
    ControlSystem toEntity(ControlSystemDTO controlSystemDTO); 
    default ControlSystem fromId(Long id) {
        if (id == null) {
            return null;
        }
        ControlSystem controlSystem = new ControlSystem();
        controlSystem.setId(id);
        return controlSystem;
    }
}
