package com.eeu.smaartu.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InLocation entity.
 */
public class InLocationDTO implements Serializable {

    private Long id;

    private String name;

    private String detail;

    private String address;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InLocationDTO inLocationDTO = (InLocationDTO) o;
        if(inLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InLocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
