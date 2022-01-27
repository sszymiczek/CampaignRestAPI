package com.example.task.model;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@RestController
@Validated
@RequestMapping(path = "/campaign")
@RequiredArgsConstructor
public class CampaignController {
    private final CampaignService campaignService;

    @GetMapping(path = "/list")
    public ResponseEntity<Response> getAllCampaigns() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("campaigns", campaignService.getAllCampaigns()))
                        .message("Campaigns retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping(path = "get/{campaignId}")
    public ResponseEntity<Response> getCampaign(@PathVariable Long campaignId) {
        Optional<Campaign> campaign = campaignService.getCampaign(campaignId);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("campaign", campaign))
                        .message(campaign.isPresent() ? "Campaign retrieved" : "No such campaign")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Response> registerNewCampaign(@RequestBody @Valid Campaign campaign) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("campaign", campaignService.addNewCampaign(campaign)))
                        .message("Campaigns created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{campaignId}")
    public ResponseEntity<Response> deleteCampaign(@PathVariable("campaignId") Long campaignId) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", campaignService.deleteCampaign(campaignId)))
                        .message("Campaign deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping(path = "/edit/{campaignId}")
        public void updateCampaign(
            @PathVariable("campaignId") Long campaignId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @Min(2000) int bidAmount,
            @RequestParam(required = false) int fund,
            @RequestParam(required = false) boolean status,
            @RequestParam(required = false) Town town,
            @RequestParam(required = false) int radiusKm) {
        campaignService.updateCampaign(campaignId, name, bidAmount, fund, status, town, radiusKm);
    }

}
