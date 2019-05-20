package com.universign.universigncs.connector.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.universign.universigncs.connector.domain.enumeration.Environment;

/**
 * A Profile.
 */
@Document(collection = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("environment")
    private Environment environment;

    @Field("life_transaction")
    private Integer lifeTransaction;

    @Field("callback_url")
    private String callbackUrl;

    @Field("redirection_url")
    private String redirectionUrl;

    @Field("google_analytics_id")
    private String googleAnalyticsId;

    @Field("logo")
    private byte[] logo;

    @Field("logo_content_type")
    private String logoContentType;

    @Field("displayed_name")
    private String displayedName;

    @Field("click_url")
    private String clickUrl;

    @Field("sms_otp_lifetime")
    private Integer smsOtpLifetime;

    @Field("logo_align")
    private String logoAlign;

    @Field("color_intro")
    private String colorIntro;

    @Field("size_textintro")
    private String sizeTextintro;

    @Field("color_body")
    private String colorBody;

    @Field("size_text_body")
    private String sizeTextBody;

    @Field("color_buton")
    private String colorButon;

    @Field("size_text_button")
    private String sizeTextButton;

    @Field("color_text_button")
    private String colorTextButton;

    @Field("text_align")
    private String textAlign;

    @Field("storage")
    private Boolean storage;

    @Field("storage_service")
    private String storageService;

    @Field("multi_stamp")
    private Boolean multiStamp;

    @Field("merge")
    private Boolean merge;

    @DBRef
    @Field("emails")
    private Emails emails;

    @DBRef
    @Field("sms")
    private Set<SMS> sms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Profile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Profile environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Integer getLifeTransaction() {
        return lifeTransaction;
    }

    public Profile lifeTransaction(Integer lifeTransaction) {
        this.lifeTransaction = lifeTransaction;
        return this;
    }

    public void setLifeTransaction(Integer lifeTransaction) {
        this.lifeTransaction = lifeTransaction;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public Profile callbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public Profile redirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
        return this;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public String getGoogleAnalyticsId() {
        return googleAnalyticsId;
    }

    public Profile googleAnalyticsId(String googleAnalyticsId) {
        this.googleAnalyticsId = googleAnalyticsId;
        return this;
    }

    public void setGoogleAnalyticsId(String googleAnalyticsId) {
        this.googleAnalyticsId = googleAnalyticsId;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Profile logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Profile logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public Profile displayedName(String displayedName) {
        this.displayedName = displayedName;
        return this;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public Profile clickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
        return this;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public Integer getSmsOtpLifetime() {
        return smsOtpLifetime;
    }

    public Profile smsOtpLifetime(Integer smsOtpLifetime) {
        this.smsOtpLifetime = smsOtpLifetime;
        return this;
    }

    public void setSmsOtpLifetime(Integer smsOtpLifetime) {
        this.smsOtpLifetime = smsOtpLifetime;
    }

    public String getLogoAlign() {
        return logoAlign;
    }

    public Profile logoAlign(String logoAlign) {
        this.logoAlign = logoAlign;
        return this;
    }

    public void setLogoAlign(String logoAlign) {
        this.logoAlign = logoAlign;
    }

    public String getColorIntro() {
        return colorIntro;
    }

    public Profile colorIntro(String colorIntro) {
        this.colorIntro = colorIntro;
        return this;
    }

    public void setColorIntro(String colorIntro) {
        this.colorIntro = colorIntro;
    }

    public String getSizeTextintro() {
        return sizeTextintro;
    }

    public Profile sizeTextintro(String sizeTextintro) {
        this.sizeTextintro = sizeTextintro;
        return this;
    }

    public void setSizeTextintro(String sizeTextintro) {
        this.sizeTextintro = sizeTextintro;
    }

    public String getColorBody() {
        return colorBody;
    }

    public Profile colorBody(String colorBody) {
        this.colorBody = colorBody;
        return this;
    }

    public void setColorBody(String colorBody) {
        this.colorBody = colorBody;
    }

    public String getSizeTextBody() {
        return sizeTextBody;
    }

    public Profile sizeTextBody(String sizeTextBody) {
        this.sizeTextBody = sizeTextBody;
        return this;
    }

    public void setSizeTextBody(String sizeTextBody) {
        this.sizeTextBody = sizeTextBody;
    }

    public String getColorButon() {
        return colorButon;
    }

    public Profile colorButon(String colorButon) {
        this.colorButon = colorButon;
        return this;
    }

    public void setColorButon(String colorButon) {
        this.colorButon = colorButon;
    }

    public String getSizeTextButton() {
        return sizeTextButton;
    }

    public Profile sizeTextButton(String sizeTextButton) {
        this.sizeTextButton = sizeTextButton;
        return this;
    }

    public void setSizeTextButton(String sizeTextButton) {
        this.sizeTextButton = sizeTextButton;
    }

    public String getColorTextButton() {
        return colorTextButton;
    }

    public Profile colorTextButton(String colorTextButton) {
        this.colorTextButton = colorTextButton;
        return this;
    }

    public void setColorTextButton(String colorTextButton) {
        this.colorTextButton = colorTextButton;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public Profile textAlign(String textAlign) {
        this.textAlign = textAlign;
        return this;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public Boolean isStorage() {
        return storage;
    }

    public Profile storage(Boolean storage) {
        this.storage = storage;
        return this;
    }

    public void setStorage(Boolean storage) {
        this.storage = storage;
    }

    public String getStorageService() {
        return storageService;
    }

    public Profile storageService(String storageService) {
        this.storageService = storageService;
        return this;
    }

    public void setStorageService(String storageService) {
        this.storageService = storageService;
    }

    public Boolean isMultiStamp() {
        return multiStamp;
    }

    public Profile multiStamp(Boolean multiStamp) {
        this.multiStamp = multiStamp;
        return this;
    }

    public void setMultiStamp(Boolean multiStamp) {
        this.multiStamp = multiStamp;
    }

    public Boolean isMerge() {
        return merge;
    }

    public Profile merge(Boolean merge) {
        this.merge = merge;
        return this;
    }

    public void setMerge(Boolean merge) {
        this.merge = merge;
    }

    public Emails getEmails() {
        return emails;
    }

    public Profile emails(Emails emails) {
        this.emails = emails;
        return this;
    }

    public void setEmails(Emails emails) {
        this.emails = emails;
    }

    public Set<SMS> getSms() {
        return sms;
    }

    public Profile sms(Set<SMS> sMS) {
        this.sms = sMS;
        return this;
    }

    public Profile addSms(SMS sMS) {
        this.sms.add(sMS);
        sMS.setProfile(this);
        return this;
    }

    public Profile removeSms(SMS sMS) {
        this.sms.remove(sMS);
        sMS.setProfile(null);
        return this;
    }

    public void setSms(Set<SMS> sMS) {
        this.sms = sMS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", environment='" + getEnvironment() + "'" +
            ", lifeTransaction=" + getLifeTransaction() +
            ", callbackUrl='" + getCallbackUrl() + "'" +
            ", redirectionUrl='" + getRedirectionUrl() + "'" +
            ", googleAnalyticsId='" + getGoogleAnalyticsId() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", displayedName='" + getDisplayedName() + "'" +
            ", clickUrl='" + getClickUrl() + "'" +
            ", smsOtpLifetime=" + getSmsOtpLifetime() +
            ", logoAlign='" + getLogoAlign() + "'" +
            ", colorIntro='" + getColorIntro() + "'" +
            ", sizeTextintro='" + getSizeTextintro() + "'" +
            ", colorBody='" + getColorBody() + "'" +
            ", sizeTextBody='" + getSizeTextBody() + "'" +
            ", colorButon='" + getColorButon() + "'" +
            ", sizeTextButton='" + getSizeTextButton() + "'" +
            ", colorTextButton='" + getColorTextButton() + "'" +
            ", textAlign='" + getTextAlign() + "'" +
            ", storage='" + isStorage() + "'" +
            ", storageService='" + getStorageService() + "'" +
            ", multiStamp='" + isMultiStamp() + "'" +
            ", merge='" + isMerge() + "'" +
            "}";
    }
}
