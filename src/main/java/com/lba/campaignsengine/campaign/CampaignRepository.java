package com.lba.campaignsengine.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Set;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("select c.id " +
            "from Campaign c " +
            "where c.status = :status " +
            "and c.startTime <= :now " +
            "and (c.endTime is null or c.endTime > :now)")
    Set<Long> findCurrentlyRunningCampaignIds(
            @Param("status") CampaignStatus status,
            @Param("now") Instant now
    );
}
