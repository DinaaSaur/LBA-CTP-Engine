package com.lba.campaignsengine.campaign;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CampaignRunningTrackerTest {

    private final CampaignRepository campaignRepository = mock(CampaignRepository.class);
    private final List<CampaignRunningEvent> sentEvents = new ArrayList<>();
    private final Clock clock = Clock.fixed(Instant.parse("2026-10-10T12:00:00Z"), ZoneOffset.UTC);
    private final CampaignRunningTracker tracker = new CampaignRunningTracker(
            campaignRepository,
            sentEvents::add,
            clock
    );

    @Test
    void sendsStartedEventsForNewRunningCampaigns() {
        when(campaignRepository.findCurrentlyRunningCampaignIds(CampaignStatus.RUNNING, clock.instant()))
                .thenReturn(setOf(10L, 20L));

        tracker.refreshRunningCampaigns();

        assertThat(tracker.getRunningCampaignIds()).containsExactlyInAnyOrder(10L, 20L);
        assertThat(sentEvents)
                .extracting(CampaignRunningEvent::getCampaignId, CampaignRunningEvent::getType)
                .containsExactlyInAnyOrder(
                        tuple(10L, CampaignRunningEventType.STARTED),
                        tuple(20L, CampaignRunningEventType.STARTED)
                );
    }

    @Test
    void sendsEndedEventsWhenPreviouslyRunningCampaignDisappears() {
        when(campaignRepository.findCurrentlyRunningCampaignIds(CampaignStatus.RUNNING, clock.instant()))
                .thenReturn(setOf(10L, 20L))
                .thenReturn(setOf(20L, 30L));

        tracker.refreshRunningCampaigns();
        sentEvents.clear();

        tracker.refreshRunningCampaigns();

        assertThat(tracker.getRunningCampaignIds()).containsExactlyInAnyOrder(20L, 30L);
        assertThat(sentEvents)
                .extracting(CampaignRunningEvent::getCampaignId, CampaignRunningEvent::getType)
                .containsExactlyInAnyOrder(
                        tuple(10L, CampaignRunningEventType.ENDED),
                        tuple(30L, CampaignRunningEventType.STARTED)
                );
    }

    @SafeVarargs
    private static <T> Set<T> setOf(T... values) {
        return new HashSet<>(Arrays.asList(values));
    }
}
