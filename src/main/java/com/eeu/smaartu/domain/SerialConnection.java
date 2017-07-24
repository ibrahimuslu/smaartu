package com.eeu.smaartu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.eeu.smaartu.domain.enumeration.DataBits;

import com.eeu.smaartu.domain.enumeration.Parity;

import com.eeu.smaartu.domain.enumeration.StopBits;

import com.eeu.smaartu.domain.enumeration.FlowControl;

/**
 * A SerialConnection.
 */
@Entity
@Table(name = "serial_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SerialConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "port")
    private String port;

    @Column(name = "baud_rate")
    private Long baudRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_bits")
    private DataBits dataBits;

    @Enumerated(EnumType.STRING)
    @Column(name = "parity")
    private Parity parity;

    @Enumerated(EnumType.STRING)
    @Column(name = "stop_bits")
    private StopBits stopBits;

    @Enumerated(EnumType.STRING)
    @Column(name = "flow_control")
    private FlowControl flowControl;

    @OneToOne(mappedBy = "serialConnection")
    @JsonIgnore
    private EndNode endNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public SerialConnection port(String port) {
        this.port = port;
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getBaudRate() {
        return baudRate;
    }

    public SerialConnection baudRate(Long baudRate) {
        this.baudRate = baudRate;
        return this;
    }

    public void setBaudRate(Long baudRate) {
        this.baudRate = baudRate;
    }

    public DataBits getDataBits() {
        return dataBits;
    }

    public SerialConnection dataBits(DataBits dataBits) {
        this.dataBits = dataBits;
        return this;
    }

    public void setDataBits(DataBits dataBits) {
        this.dataBits = dataBits;
    }

    public Parity getParity() {
        return parity;
    }

    public SerialConnection parity(Parity parity) {
        this.parity = parity;
        return this;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public StopBits getStopBits() {
        return stopBits;
    }

    public SerialConnection stopBits(StopBits stopBits) {
        this.stopBits = stopBits;
        return this;
    }

    public void setStopBits(StopBits stopBits) {
        this.stopBits = stopBits;
    }

    public FlowControl getFlowControl() {
        return flowControl;
    }

    public SerialConnection flowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
        return this;
    }

    public void setFlowControl(FlowControl flowControl) {
        this.flowControl = flowControl;
    }

    public EndNode getEndNode() {
        return endNode;
    }

    public SerialConnection endNode(EndNode endNode) {
        this.endNode = endNode;
        return this;
    }

    public void setEndNode(EndNode endNode) {
        this.endNode = endNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SerialConnection serialConnection = (SerialConnection) o;
        if (serialConnection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serialConnection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SerialConnection{" +
            "id=" + getId() +
            ", port='" + getPort() + "'" +
            ", baudRate='" + getBaudRate() + "'" +
            ", dataBits='" + getDataBits() + "'" +
            ", parity='" + getParity() + "'" +
            ", stopBits='" + getStopBits() + "'" +
            ", flowControl='" + getFlowControl() + "'" +
            "}";
    }
}
