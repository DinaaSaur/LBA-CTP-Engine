package com.lba.campaignsengine.campaign;

public interface CampaignEventSender {

    void send(CampaignRunningEvent event);
}
