package com.lba.campaignsengine.campaign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/campaigns")
public class RunningCampaignController {

    private final CampaignRunningTracker campaignRunningTracker;

    public RunningCampaignController(CampaignRunningTracker campaignRunningTracker) {
        this.campaignRunningTracker = campaignRunningTracker;
    }

    @GetMapping("/running")
    public Set<Long> runningCampaigns() {
        return campaignRunningTracker.getRunningCampaignIds();
    }
}
