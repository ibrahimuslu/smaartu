package com.eeu.smaartu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eeu.smaartu.domain.enumeration.EndNodeType;

/**
 * A EndNode.
 */
@Entity
@Table(name = "end_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EndNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private EndNodeType type;

    @ManyToOne
    private ControlSystem controlSystem;

    @OneToOne
    @JoinColumn(unique = true)
    private InLocation inLocation;

    @OneToOne
    @JoinColumn(unique = true)
    private SerialConnection serialConnection;

    @OneToMany(mappedBy = "endNode")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EndNodeUnit> endNodeUnits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EndNode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public EndNode address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EndNodeType getType() {
        return type;
    }

    public EndNode type(EndNodeType type) {
        this.type = type;
        return this;
    }

    public void setType(EndNodeType type) {
        this.type = type;
    }

    public ControlSystem getControlSystem() {
        return controlSystem;
    }

    public EndNode controlSystem(ControlSystem controlSystem) {
        this.controlSystem = controlSystem;
        return this;
    }

    public void setControlSystem(ControlSystem controlSystem) {
        this.controlSystem = controlSystem;
    }

    public InLocation getInLocation() {
        return inLocation;
    }

    public EndNode inLocation(InLocation inLocation) {
        this.inLocation = inLocation;
        return this;
    }

    public void setInLocation(InLocation inLocation) {
        this.inLocation = inLocation;
    }

    public SerialConnection getSerialConnection() {
        return serialConnection;
    }

    public EndNode serialConnection(SerialConnection serialConnection) {
        this.serialConnection = serialConnection;
        return this;
    }

    public void setSerialConnection(SerialConnection serialConnection) {
        this.serialConnection = serialConnection;
    }

    public Set<EndNodeUnit> getEndNodeUnits() {
        return endNodeUnits;
    }

    public EndNode endNodeUnits(Set<EndNodeUnit> endNodeUnits) {
        this.endNodeUnits = endNodeUnits;
        return this;
    }

    public EndNode addEndNodeUnit(EndNodeUnit endNodeUnit) {
        this.endNodeUnits.add(endNodeUnit);
        endNodeUnit.setEndNode(this);
        return this;
    }

    public EndNode removeEndNodeUnit(EndNodeUnit endNodeUnit) {
        this.endNodeUnits.remove(endNodeUnit);
        endNodeUnit.setEndNode(null);
        return this;
    }

    public void setEndNodeUnits(Set<EndNodeUnit> endNodeUnits) {
        this.endNodeUnits = endNodeUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndNode endNode = (EndNode) o;
        if (endNode.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), endNode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EndNode{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
