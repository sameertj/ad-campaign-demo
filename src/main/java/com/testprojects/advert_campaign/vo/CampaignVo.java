package com.testprojects.advert_campaign.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.testprojects.advert_campaign.entity.Campaign;

public class CampaignVo {

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("ad_content")
    private String adContent;

    @JsonProperty("ad_create_date")
    private String createdDateString;

    @JsonProperty("ad_end_date")
    private String adEndDateTimeSting;

    public String getPartnerId() {
        return partnerId;
    }

    public CampaignVo() {
    }

    public CampaignVo(Campaign campaign) {
        this.partnerId = campaign.getUser().getUserId();
        this.duration = (int) campaign.getDuration()/1000;
        this.adContent = campaign.getAdContent();
        this.createdDateString = campaign.getCreatedDateString();
        this.adEndDateTimeSting = campaign.getAdEndDateTimeSting();
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAdContent() {
        return adContent;
    }

    public void setAdContent(String adContent) {
        this.adContent = adContent;
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
