package com.universign.universigncs.connector.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EndEmail.
 */
@Document(collection = "end_email")
public class EndEmail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("end_subject")
    private String endSubject;

    @Field("end_body")
    private String endBody;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndSubject() {
        return endSubject;
    }

    public EndEmail endSubject(String endSubject) {
        this.endSubject = endSubject;
        return this;
    }

    public void setEndSubject(String endSubject) {
        this.endSubject = endSubject;
    }

    public String getEndBody() {
        return endBody;
    }

    public EndEmail endBody(String endBody) {
        this.endBody = endBody;
        return this;
    }

    public void setEndBody(String endBody) {
        this.endBody = endBody;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndEmail)) {
            return false;
        }
        return id != null && id.equals(((EndEmail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EndEmail{" +
            "id=" + getId() +
            ", endSubject='" + getEndSubject() + "'" +
            ", endBody='" + getEndBody() + "'" +
            "}";
    }
}
