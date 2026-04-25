package com.example.saleCampaignManagementSystem.dto;

import com.example.saleCampaignManagementSystem.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignProductDto {

    private long product;
    private double discount;
}
