package com.testprojects.advert_campaign.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "campaigns")
public class Campaign {

    @JsonIgnore
    @ManyToOne
    private User user;

    @GeneratedValue
    @Id
    private long id;

    private long duration;

    private String adContent;

    private Date createDateTime;

    private Timestamp createDateTimestamp;

    private String createdDateString;

    private Date addEndDateTime;

    private Timestamp adEndTimestamp;

    private String adEndDateTimeSting;

    public Campaign() {
    }

    public Campaign(User user, long duration, String adContent) {
        this.user = user;
        this.duration = duration * 1000;
        this.adContent = adContent;
        createDateTime = new Date();
        addEndDateTime = new Date(createDateTime.getTime() + this.duration);
        createdDateString = createDateTime.toString();
        adEndDateTimeSting = addEndDateTime.toString();
        createDateTimestamp = new Timestamp(createDateTime.getTime());
        adEndTimestamp = new Timestamp(addEndDateTime.getTime());
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * @return the adContent
     */
    public String getAdContent() {
        return adContent;
    }

    /**
     * @param adContent the adContent to set
     */
    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getAddEndDateTime() {
        return addEndDateTime;
    }

    public void setAddEndDateTime(Date addEndDateTime) {
        this.addEndDateTime = addEndDateTime;
    }

    public String getCreatedDateString() {
        return createdDateString;
    }

    public void setCreatedDateString(String createdDateString) {
        this.createdDateString = createdDateString;
    }

    public String getAdEndDateTimeSting() {
        return adEndDateTimeSting;
    }

    public void setAdEndDateTimeSting(String adEndDateTimeSting) {
        this.adEndDateTimeSting = adEndDateTimeSting;
    }
}