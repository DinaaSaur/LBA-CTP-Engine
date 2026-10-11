package com.lba.campaignsengine.campaign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingCampaignEventSender implements CampaignEventSender {

    private static final Logger log = LoggerFactory.getLogger(LoggingCampaignEventSender.class);

    @Override
    public void send(CampaignRunningEvent event) {
        log.info("Campaign running event: campaignId={}, type={}, detectedAt={}",
                event.getCampaignId(), event.getType(), event.getDetectedAt());
    }
}
