package com.example.saleCampaignManagementSystem.controller;

import com.example.saleCampaignManagementSystem.dto.AddCampaignDto;
import com.example.saleCampaignManagementSystem.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("campaign")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping("addCampaign")
    public void addCampaign(@RequestBody AddCampaignDto addCampaignDto){
        campaignService.addCampaign(addCampaignDto);
    }
}
