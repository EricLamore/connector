package com.universign.universigncs.connector.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import com.universign.universigncs.connector.domain.enumeration.Language;

/**
 * A SMS.
 */
@Document(collection = "sms")
public class SMS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("sms_auth_text")
    private String smsAuthText;

    @Field("language")
    private Language language;

    @DBRef
    @Field("profile")
    @JsonIgnoreProperties("sMS")
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmsAuthText() {
        return smsAuthText;
    }

    public SMS smsAuthText(String smsAuthText) {
        this.smsAuthText = smsAuthText;
        return this;
    }

    public void setSmsAuthText(String smsAuthText) {
        this.smsAuthText = smsAuthText;
    }

    public Language getLanguage() {
        return language;
    }

    public SMS language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Profile getProfile() {
        return profile;
    }

    public SMS profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SMS)) {
            return false;
        }
        return id != null && id.equals(((SMS) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SMS{" +
            "id=" + getId() +
            ", smsAuthText='" + getSmsAuthText() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
