package com.lba.campaignsengine.campaign;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CampaignRunningTracker {

    private final CampaignRepository campaignRepository;
    private final CampaignEventSender eventSender;
    private final Clock clock;
    private final Set<Long> runningCampaignIds = ConcurrentHashMap.newKeySet();

    public CampaignRunningTracker(
            CampaignRepository campaignRepository,
            CampaignEventSender eventSender,
            Clock clock
    ) {
        this.campaignRepository = campaignRepository;
        this.eventSender = eventSender;
        this.clock = clock;
    }

    @Scheduled(fixedDelayString = "${campaigns.scan.fixed-delay:30000}")
    @Transactional(readOnly = true)
    public void refreshRunningCampaigns() {
        Instant now = clock.instant();
        Set<Long> currentRunningIds = campaignRepository.findCurrentlyRunningCampaignIds(CampaignStatus.RUNNING, now);

        Set<Long> startedIds = new HashSet<>(currentRunningIds);
        startedIds.removeAll(runningCampaignIds);

        Set<Long> endedIds = new HashSet<>(runningCampaignIds);
        endedIds.removeAll(currentRunningIds);

        startedIds.forEach(id -> eventSender.send(new CampaignRunningEvent(id, CampaignRunningEventType.STARTED, now)));
        endedIds.forEach(id -> eventSender.send(new CampaignRunningEvent(id, CampaignRunningEventType.ENDED, now)));

        runningCampaignIds.clear();
        runningCampaignIds.addAll(currentRunningIds);
    }

    public Set<Long> getRunningCampaignIds() {
        return Collections.unmodifiableSet(new HashSet<>(runningCampaignIds));
    }
}
