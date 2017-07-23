package com.eeu.smaartu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.eeu.smaartu.domain.enumeration.UnitType;
import com.eeu.smaartu.domain.enumeration.UnitStatus;

/**
 * A DTO for the EndNodeUnit entity.
 */
public class EndNodeUnitDTO implements Serializable {

    private Long id;

    private String name;

    private UnitType type;

    private UnitStatus status;

    private Long endNodeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public Long getEndNodeId() {
        return endNodeId;
    }

    public void setEndNodeId(Long endNodeId) {
        this.endNodeId = endNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EndNodeUnitDTO endNodeUnitDTO = (EndNodeUnitDTO) o;
        if(endNodeUnitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endNodeUnitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndNodeUnitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
