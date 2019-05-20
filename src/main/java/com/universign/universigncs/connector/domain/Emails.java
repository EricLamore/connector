package com.universign.universigncs.connector.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import com.universign.universigncs.connector.domain.enumeration.Language;

/**
 * A Emails.
 */
@Document(collection = "emails")
public class Emails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("logo")
    private byte[] logo;

    @Field("logo_content_type")
    private String logoContentType;

    @Field("language")
    private Language language;

    @DBRef
    @Field("startEmail")
    private StartEmail startEmail;

    @DBRef
    @Field("endEmail")
    private EndEmail endEmail;

    @DBRef
    @Field("relaunchEmail")
    private RelaunchEmail relaunchEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Emails logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Emails logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Language getLanguage() {
        return language;
    }

    public Emails language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public StartEmail getStartEmail() {
        return startEmail;
    }

    public Emails startEmail(StartEmail startEmail) {
        this.startEmail = startEmail;
        return this;
    }

    public void setStartEmail(StartEmail startEmail) {
        this.startEmail = startEmail;
    }

    public EndEmail getEndEmail() {
        return endEmail;
    }

    public Emails endEmail(EndEmail endEmail) {
        this.endEmail = endEmail;
        return this;
    }

    public void setEndEmail(EndEmail endEmail) {
        this.endEmail = endEmail;
    }

    public RelaunchEmail getRelaunchEmail() {
        return relaunchEmail;
    }

    public Emails relaunchEmail(RelaunchEmail relaunchEmail) {
        this.relaunchEmail = relaunchEmail;
        return this;
    }

    public void setRelaunchEmail(RelaunchEmail relaunchEmail) {
        this.relaunchEmail = relaunchEmail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emails)) {
            return false;
        }
        return id != null && id.equals(((Emails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Emails{" +
            "id=" + getId() +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
