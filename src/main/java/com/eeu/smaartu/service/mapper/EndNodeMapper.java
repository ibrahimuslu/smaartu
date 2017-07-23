package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.EndNodeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EndNode and its DTO EndNodeDTO.
 */
@Mapper(componentModel = "spring", uses = {ControlSystemMapper.class, InLocationMapper.class, SerialConnectionMapper.class, })
public interface EndNodeMapper extends EntityMapper <EndNodeDTO, EndNode> {

    @Mapping(source = "controlSystem.id", target = "controlSystemId")

    @Mapping(source = "inLocation.id", target = "inLocationId")

    @Mapping(source = "serialConnection.id", target = "serialConnectionId")
    @Mapping(source = "serialConnection.port", target = "serialConnectionPort")
    EndNodeDTO toDto(EndNode endNode); 

    @Mapping(source = "controlSystemId", target = "controlSystem")

    @Mapping(source = "inLocationId", target = "inLocation")

    @Mapping(source = "serialConnectionId", target = "serialConnection")
    @Mapping(target = "endNodeUnits", ignore = true)
    EndNode toEntity(EndNodeDTO endNodeDTO); 
    default EndNode fromId(Long id) {
        if (id == null) {
            return null;
        }
        EndNode endNode = new EndNode();
        endNode.setId(id);
        return endNode;
    }
}
