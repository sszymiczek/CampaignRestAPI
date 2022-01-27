package com.example.task.model;

import com.example.task.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Optional<Campaign> getCampaign(Long campaignId) {
        if (campaignRepository.existsById(campaignId))
            return campaignRepository.findById(campaignId);
        return Optional.empty();
    }

    public Campaign addNewCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
        return campaign;
    }

    public boolean deleteCampaign(Long campaignId) {
        if (!campaignRepository.existsById(campaignId))
            return false;
        campaignRepository.deleteById(campaignId);
        return true;
    }

    @Transactional
    public void updateCampaign(Long campaignId, String name, int bidAmount, int fund, boolean status, Town town, int radiusKm) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalStateException("campaign with id " + campaignId + "does not exists"));

        if (name != null && name.length() > 0)
            campaign.setName(name);
        campaign.setBidAmount(bidAmount);
        campaign.setFund(fund);
        campaign.setStatus(status);

        if (town != null) {
            campaign.setTown(town);
        }

        campaign.setRadiusKm(radiusKm);
    }
}
