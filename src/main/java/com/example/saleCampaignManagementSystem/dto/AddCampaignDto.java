package com.example.saleCampaignManagementSystem.dto;

import com.example.saleCampaignManagementSystem.model.CampaignProducts;
import com.example.saleCampaignManagementSystem.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCampaignDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private List<CampaignProductDto> campaignProductsList;


}
