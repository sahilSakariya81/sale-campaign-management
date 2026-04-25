package com.example.saleCampaignManagementSystem.repository;

import com.example.saleCampaignManagementSystem.model.CampaignProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignProductRepo extends JpaRepository<CampaignProducts,Long> {
}
