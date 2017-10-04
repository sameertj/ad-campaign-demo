package com.testprojects.advert_campaign.repository;

import com.testprojects.advert_campaign.entity.Campaign;
import com.testprojects.advert_campaign.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {

    Collection<Campaign> findCampaignsByUser(User user);

    @Query("select c from Campaign c where c.user = ?1 and CURRENT_TIMESTAMP >= c.createDateTimestamp and " +
            "CURRENT_TIMESTAMP <= c.adEndTimestamp order by c.createDateTimestamp desc")
    Collection<Campaign> findCurrentCampaignsForUser(User user);

    @Query("select c from Campaign c where CURRENT_TIMESTAMP >= c.createDateTimestamp and " +
            "CURRENT_TIMESTAMP <= c.adEndTimestamp order by c.createDateTimestamp desc")
    Collection<Campaign> findCurrentCampaigns();

    Collection<Campaign> findCampaignsByUserId(String userId);
}
