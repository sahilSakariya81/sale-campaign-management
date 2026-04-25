package com.example.saleCampaignManagementSystem.repository;

import com.example.saleCampaignManagementSystem.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductPriceRepo extends JpaRepository<ProductPrice, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO product_price (updated_date, product_id, price)
            SELECT CURDATE(), p.product_id, p.current_price
            FROM product p
            JOIN (
             SELECT cp.product_id
             FROM campaign_product cp
             JOIN campaign_info c ON cp.campaign_id = c.campaign_id
             WHERE c.start_date = CURDATE()
             AND c.status = 'UPCOMING'
             GROUP BY cp.product_id
            ) x ON p.product_id = x.product_id
            """, nativeQuery = true)
    int insertPriceHistoryForStartedCampaign();


    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO product_price (updated_date, product_id, price)
            SELECT CURDATE(), p.product_id, p.current_price
            FROM product p
            JOIN (
                SELECT cp.product_id
                FROM campaign_product cp
                JOIN campaign_info c ON cp.campaign_id = c.campaign_id
                WHERE c.end_date = CURDATE()
                AND c.status = 'PRESENT'
                GROUP BY cp.product_id
            ) x ON p.product_id = x.product_id
            """, nativeQuery = true)
    int insertPriceHistoryForEndedCampaign();
}
