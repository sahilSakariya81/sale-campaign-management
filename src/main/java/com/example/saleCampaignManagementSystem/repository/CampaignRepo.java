package com.example.saleCampaignManagementSystem.repository;

import com.example.saleCampaignManagementSystem.dto.ProductDiscountMap;
import com.example.saleCampaignManagementSystem.model.Campaign;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CampaignRepo extends JpaRepository<Campaign, Long> {

//    @Query(value = "Select * from campaign_info where start_date = ?1",nativeQuery = true)
//    List<Campaign> getTodayCampaign(LocalDate date);
//
//    @Query(value = "SELECT * FROM campaign_product where campaign_id = ?1",nativeQuery = true)
//    List<CampaignProducts> getCampaignProducts(long cId);
//
//    @Query(value = """
//    SELECT SUM(cp.discount)
//    FROM campaign_product cp
//    JOIN campaign_info c ON c.campaign_id = cp.campaign_id
//    WHERE cp.product_id = ?1
//    AND c.start_date = ?2
//    """, nativeQuery = true)
//    Double getTotalDiscountForProduct(Long productId, LocalDate date);


//    @Query(value = """
//        SELECT *
//        FROM campaign_info
//        WHERE start_date = ?1
//        AND status = 'UPCOMING'
//        """, nativeQuery = true)
//    List<Campaign> getTodayStartingCampaigns(LocalDate date);
//
//    @Query(value = """
//        SELECT *
//        FROM campaign_info
//        WHERE end_date = ?1
//        AND status = 'PRESENT'
//        """, nativeQuery = true)
//    List<Campaign> getTodayEndingCampaigns(LocalDate date);
//
//    @Query(value = """
//    SELECT cp.product_id AS productId,
//    SUM(cp.discount) AS totalDiscount
//    FROM campaign_product cp
//    JOIN campaign_info c
//    ON c.campaign_id = cp.campaign_id
//    WHERE c.start_date = ?1 AND c.status = 'UPCOMING'
//    GROUP BY cp.product_id
//    """, nativeQuery = true)
//    List<ProductDiscountMap> getTodayStartCampaignProductAndTotalDiscount(LocalDate date);
//
//    @Query(value = """
//    SELECT cp.product_id AS productId,
//    SUM(cp.discount) AS totalDiscount
//    FROM campaign_product cp
//    JOIN campaign_info c
//    ON c.campaign_id = cp.campaign_id
//    WHERE c.end_date = ?1 AND c.status = 'PRESENT'
//    GROUP BY cp.product_id
//    """, nativeQuery = true)
//    List<ProductDiscountMap> getTodayEndCampaignProductAndTotalDiscount(LocalDate date);


    @Modifying
    @Transactional
    @Query(value = """
            UPDATE product p
            JOIN (
                SELECT cp.product_id,
                SUM(cp.discount) AS total_discount
                FROM campaign_product cp
                JOIN campaign_info c ON cp.campaign_id = c.campaign_id
                WHERE c.start_date = CURDATE()
                AND c.status = 'UPCOMING'
                 GROUP BY cp.product_id
                ) x ON p.product_id = x.product_id
            SET p.current_price = p.mrp - (p.mrp * x.total_discount / 100),
                p.discount = x.total_discount
            """, nativeQuery = true)
    int bulkStartCampaign();


    @Modifying
    @Transactional
    @Query(value = """
            UPDATE product p
            JOIN (
                SELECT cp.product_id
                FROM campaign_product cp
                JOIN campaign_info c ON cp.campaign_id = c.campaign_id
                WHERE c.end_date = CURDATE()
                AND c.status = 'PRESENT'
                GROUP BY cp.product_id
            ) x ON p.product_id = x.product_id
            SET p.current_price = p.mrp,
            p.discount = 0
            """, nativeQuery = true)
    int bulkEndCampaign();

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE campaign_info
            SET status = 'PRESENT'
            WHERE start_date = CURDATE()
            AND status = 'UPCOMING'
            """, nativeQuery = true)
    int activateCampaigns();


    @Modifying
    @Transactional
    @Query(value = """
            UPDATE campaign_info
            SET status = 'PAST'
            WHERE end_date = CURDATE()
            AND status = 'PRESENT'
            """, nativeQuery = true)
    int deactivateCampaigns();

}
