package com.eeu.smaartu.service.mapper;

import com.eeu.smaartu.domain.*;
import com.eeu.smaartu.service.dto.InLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InLocation and its DTO InLocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InLocationMapper extends EntityMapper <InLocationDTO, InLocation> {
    
    
    default InLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        InLocation inLocation = new InLocation();
        inLocation.setId(id);
        return inLocation;
    }
}
