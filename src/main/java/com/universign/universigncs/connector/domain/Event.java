package com.universign.universigncs.connector.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.universign.universigncs.connector.domain.enumeration.EventType;

import com.universign.universigncs.connector.domain.enumeration.ConnectorType;

/**
 * A Event.
 */
@Document(collection = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("parent_id")
    private String parentId;

    @Field("master_event")
    private Boolean masterEvent;

    @Field("type")
    private EventType type;

    @Field("from")
    private ConnectorType from;

    @Field("to")
    private ConnectorType to;

    @Field("exception")
    private String exception;

    @Field("message")
    private String message;

    @Field("creation_date")
    private LocalDate creationDate;

    @Field("child_size")
    private Integer childSize;

    @Field("parameter")
    private String parameter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public Event parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean isMasterEvent() {
        return masterEvent;
    }

    public Event masterEvent(Boolean masterEvent) {
        this.masterEvent = masterEvent;
        return this;
    }

    public void setMasterEvent(Boolean masterEvent) {
        this.masterEvent = masterEvent;
    }

    public EventType getType() {
        return type;
    }

    public Event type(EventType type) {
        this.type = type;
        return this;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public ConnectorType getFrom() {
        return from;
    }

    public Event from(ConnectorType from) {
        this.from = from;
        return this;
    }

    public void setFrom(ConnectorType from) {
        this.from = from;
    }

    public ConnectorType getTo() {
        return to;
    }

    public Event to(ConnectorType to) {
        this.to = to;
        return this;
    }

    public void setTo(ConnectorType to) {
        this.to = to;
    }

    public String getException() {
        return exception;
    }

    public Event exception(String exception) {
        this.exception = exception;
        return this;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public Event message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Event creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getChildSize() {
        return childSize;
    }

    public Event childSize(Integer childSize) {
        this.childSize = childSize;
        return this;
    }

    public void setChildSize(Integer childSize) {
        this.childSize = childSize;
    }

    public String getParameter() {
        return parameter;
    }

    public Event parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", parentId='" + getParentId() + "'" +
            ", masterEvent='" + isMasterEvent() + "'" +
            ", type='" + getType() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", exception='" + getException() + "'" +
            ", message='" + getMessage() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", childSize=" + getChildSize() +
            ", parameter='" + getParameter() + "'" +
            "}";
    }
}
