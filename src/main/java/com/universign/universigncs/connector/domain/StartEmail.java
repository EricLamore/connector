package com.universign.universigncs.connector.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StartEmail.
 */
@Document(collection = "start_email")
public class StartEmail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("start_subject")
    private String startSubject;

    @Field("start_body")
    private String startBody;

    @Field("end_body")
    private String endBody;

    @Field("signature_link_body")
    private String signatureLinkBody;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartSubject() {
        return startSubject;
    }

    public StartEmail startSubject(String startSubject) {
        this.startSubject = startSubject;
        return this;
    }

    public void setStartSubject(String startSubject) {
        this.startSubject = startSubject;
    }

    public String getStartBody() {
        return startBody;
    }

    public StartEmail startBody(String startBody) {
        this.startBody = startBody;
        return this;
    }

    public void setStartBody(String startBody) {
        this.startBody = startBody;
    }

    public String getEndBody() {
        return endBody;
    }

    public StartEmail endBody(String endBody) {
        this.endBody = endBody;
        return this;
    }

    public void setEndBody(String endBody) {
        this.endBody = endBody;
    }

    public String getSignatureLinkBody() {
        return signatureLinkBody;
    }

    public StartEmail signatureLinkBody(String signatureLinkBody) {
        this.signatureLinkBody = signatureLinkBody;
        return this;
    }

    public void setSignatureLinkBody(String signatureLinkBody) {
        this.signatureLinkBody = signatureLinkBody;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StartEmail)) {
            return false;
        }
        return id != null && id.equals(((StartEmail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StartEmail{" +
            "id=" + getId() +
            ", startSubject='" + getStartSubject() + "'" +
            ", startBody='" + getStartBody() + "'" +
            ", endBody='" + getEndBody() + "'" +
            ", signatureLinkBody='" + getSignatureLinkBody() + "'" +
            "}";
    }
}
