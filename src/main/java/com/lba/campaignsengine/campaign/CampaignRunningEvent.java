package com.lba.campaignsengine.campaign;

import java.time.Instant;

public class CampaignRunningEvent {

    private final Long campaignId;
    private final CampaignRunningEventType type;
    private final Instant detectedAt;

    public CampaignRunningEvent(Long campaignId, CampaignRunningEventType type, Instant detectedAt) {
        this.campaignId = campaignId;
        this.type = type;
        this.detectedAt = detectedAt;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public CampaignRunningEventType getType() {
        return type;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }
}
