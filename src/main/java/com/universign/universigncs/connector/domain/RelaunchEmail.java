package com.universign.universigncs.connector.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RelaunchEmail.
 */
@Document(collection = "relaunch_email")
public class RelaunchEmail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("relaunch_subject")
    private String relaunchSubject;

    @Field("relaunch_body")
    private String relaunchBody;

    @Field("relaunch_end_body")
    private String relaunchEndBody;

    @Field("relaunch_signature_link_body")
    private String relaunchSignatureLinkBody;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelaunchSubject() {
        return relaunchSubject;
    }

    public RelaunchEmail relaunchSubject(String relaunchSubject) {
        this.relaunchSubject = relaunchSubject;
        return this;
    }

    public void setRelaunchSubject(String relaunchSubject) {
        this.relaunchSubject = relaunchSubject;
    }

    public String getRelaunchBody() {
        return relaunchBody;
    }

    public RelaunchEmail relaunchBody(String relaunchBody) {
        this.relaunchBody = relaunchBody;
        return this;
    }

    public void setRelaunchBody(String relaunchBody) {
        this.relaunchBody = relaunchBody;
    }

    public String getRelaunchEndBody() {
        return relaunchEndBody;
    }

    public RelaunchEmail relaunchEndBody(String relaunchEndBody) {
        this.relaunchEndBody = relaunchEndBody;
        return this;
    }

    public void setRelaunchEndBody(String relaunchEndBody) {
        this.relaunchEndBody = relaunchEndBody;
    }

    public String getRelaunchSignatureLinkBody() {
        return relaunchSignatureLinkBody;
    }

    public RelaunchEmail relaunchSignatureLinkBody(String relaunchSignatureLinkBody) {
        this.relaunchSignatureLinkBody = relaunchSignatureLinkBody;
        return this;
    }

    public void setRelaunchSignatureLinkBody(String relaunchSignatureLinkBody) {
        this.relaunchSignatureLinkBody = relaunchSignatureLinkBody;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelaunchEmail)) {
            return false;
        }
        return id != null && id.equals(((RelaunchEmail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RelaunchEmail{" +
            "id=" + getId() +
            ", relaunchSubject='" + getRelaunchSubject() + "'" +
            ", relaunchBody='" + getRelaunchBody() + "'" +
            ", relaunchEndBody='" + getRelaunchEndBody() + "'" +
            ", relaunchSignatureLinkBody='" + getRelaunchSignatureLinkBody() + "'" +
            "}";
    }
}
