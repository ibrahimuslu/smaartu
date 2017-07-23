package com.eeu.smaartu.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.eeu.smaartu.domain.enumeration.EndNodeType;

/**
 * A DTO for the EndNode entity.
 */
public class EndNodeDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private EndNodeType type;

    private Long controlSystemId;

    private Long inLocationId;

    private Long serialConnectionId;

    private String serialConnectionPort;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EndNodeType getType() {
        return type;
    }

    public void setType(EndNodeType type) {
        this.type = type;
    }

    public Long getControlSystemId() {
        return controlSystemId;
    }

    public void setControlSystemId(Long controlSystemId) {
        this.controlSystemId = controlSystemId;
    }

    public Long getInLocationId() {
        return inLocationId;
    }

    public void setInLocationId(Long inLocationId) {
        this.inLocationId = inLocationId;
    }

    public Long getSerialConnectionId() {
        return serialConnectionId;
    }

    public void setSerialConnectionId(Long serialConnectionId) {
        this.serialConnectionId = serialConnectionId;
    }

    public String getSerialConnectionPort() {
        return serialConnectionPort;
    }

    public void setSerialConnectionPort(String serialConnectionPort) {
        this.serialConnectionPort = serialConnectionPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EndNodeDTO endNodeDTO = (EndNodeDTO) o;
        if(endNodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endNodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndNodeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
