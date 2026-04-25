package com.example.saleCampaignManagementSystem.service;


import com.example.saleCampaignManagementSystem.dto.AddCampaignDto;
import com.example.saleCampaignManagementSystem.dto.CampaignProductDto;
import com.example.saleCampaignManagementSystem.dto.ProductDiscountMap;
import com.example.saleCampaignManagementSystem.enums.Status;
import com.example.saleCampaignManagementSystem.model.*;
import com.example.saleCampaignManagementSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    CampaignRepo campaignRepo;

    @Autowired
    CampaignProductRepo campaignProductRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    RetryRepository retryRepository;

    @Autowired
    ProductPriceRepo productPriceRepo;

    public void addCampaign(AddCampaignDto addCampaignDto) {

        Campaign campaign = new Campaign();
        campaign.setTitle(addCampaignDto.getTitle());
        campaign.setStartDate(addCampaignDto.getStartDate());
        campaign.setEbdDate(addCampaignDto.getEndDate());

        campaignRepo.save(campaign);

        List<CampaignProductDto> productList = addCampaignDto.getCampaignProductsList();
        for(CampaignProductDto p : productList){

            Product product = productRepo.findById(p.getProduct()).orElse(null);
            CampaignProducts campaignProducts = new CampaignProducts();
            campaignProducts.setCampaign(campaign);
            campaignProducts.setProduct(product);
            campaignProducts.setDiscount(p.getDiscount());

            campaignProductRepo.save(campaignProducts);
        }

    }


//    @Scheduled(cron = "0 29 13 * * ?",zone = "Asia/Kolkata" )
//    @Transactional
//    void startCampaign(){
//
//
//        List<Campaign> todayCampaign = campaignRepo.getTodayCampaign(LocalDate.now());
//
//        for(Campaign campaign : todayCampaign){
//
//            List<CampaignProducts> todayCampaignProduct = campaignRepo.getCampaignProducts(campaign.getCampaignId());
//
//            for(CampaignProducts campaignProducts : todayCampaignProduct){
//                Product p = campaignProducts.getProduct();
//
//                double totalDis = campaignRepo.getTotalDiscountForProduct(p.getProductId(),LocalDate.now());
//
//                double mrp = p.getMRP();
//                double totalDisVal = (mrp * totalDis) / 100;
//                double newPrice = mrp - totalDisVal;
//
//                p.setCurrentPrice(newPrice);
//                p.setDiscount(totalDis);
//                productRepo.save(p);
//            }
//        }
//
//    }

//    @Scheduled(cron = "0 19 16 * * ?", zone = "Asia/Kolkata")
//    public void startCampaign() {
//
//        LocalDate today = LocalDate.now();
//
//        List<ProductDiscountMap> results =
//                campaignRepo.getTodayStartCampaignProductAndTotalDiscount(LocalDate.now());
//
//        for (ProductDiscountMap dto : results) {
//
//
//            Product p = productRepo.findById(dto.getProductId()).orElse(null);
//            if (p == null) continue;
//
//            if(Long.valueOf(7).equals(p.getProductId())){
//                System.out.println("7");
//                retryRepository.save(new RetryTable(p.getProductId(),dto.getTotalDiscount(),3,today));
//                continue;
//            }
//
//            double mrp = p.getMRP();
//            double discountValue = (mrp * dto.getTotalDiscount()) / 100;
//            double newPrice = mrp - discountValue;
//
//            p.setCurrentPrice(newPrice);
//            p.setDiscount(dto.getTotalDiscount());
//
//            productRepo.save(p);
//
//            ProductPrice productPrice = new ProductPrice();
//            productPrice.setProductId(p.getProductId());
//            productPrice.setPrice(p.getCurrentPrice());
//            productPrice.setUpdatedDate(LocalDate.now());
//            productPriceRepo.save(productPrice);
//
//        }
//
//        List<RetryTable> retryTableList = retryRepository.getRetryList(today);
//
//        while (!retryTableList.isEmpty()) {
//
//            Iterator<RetryTable> iterator = retryTableList.iterator();
//
//            while (iterator.hasNext()) {
//
//                RetryTable product = iterator.next();
//
//                if (product.getCount() == 0) {
//                    iterator.remove();
//                    retryRepository.deleteById(product.getId());
//                    continue;
//                }
//
//                Product p = productRepo.findById(product.getProductId()).orElse(null);
//
//                if (p != null) {
//
//                    if (p.getProductId() == 7) {
//                        product.setCount(product.getCount() - 1);
//                        retryRepository.save(product);
//                        continue;
//                    }
//
//                    double dis = product.getDiscount();
//                    double disVal = (p.getMRP() * dis) / 100;
//
//                    p.setCurrentPrice(p.getMRP() - disVal);
//                    p.setDiscount(dis);
//
//                    productRepo.save(p);
//
//                    iterator.remove();   // ✅ SAFE REMOVE
//                    retryRepository.deleteById(product.getId());
//                }
//            }
//        }
//
//        List<Campaign> startingCampaigns =
//                campaignRepo.getTodayStartingCampaigns(today);
//
//        for (Campaign c : startingCampaigns) {
//            if (c.getStatus() == Status.UPCOMING) {
//                c.setStatus(Status.PRESENT);
//            }
//        }
//    }



//    @Scheduled(cron = "0 39 17 * * ?", zone = "Asia/Kolkata")
//    @Transactional
//    void endCampaign(){
//
//        LocalDate today = LocalDate.now();
//        List<ProductDiscountMap> todayEndList = campaignRepo.getTodayEndCampaignProductAndTotalDiscount(LocalDate.now());
//        for(ProductDiscountMap dto : todayEndList){
//            Product p = productRepo.findById(dto.getProductId()).orElse(null);
//
//            if(p == null){
//                continue;
//            }
//            double mrp = p.getMRP();
//            double currDis = p.getDiscount();
//            double currPrice = p.getCurrentPrice();
//            double newPrice = currPrice + ( (mrp * dto.getTotalDiscount()) / 100 );
//            double newDis = currDis - dto.getTotalDiscount();
//            p.setDiscount(newDis);
//            p.setCurrentPrice(newPrice);
//            productRepo.save(p);
//
//            ProductPrice productPrice = new ProductPrice();
//            productPrice.setProductId(p.getProductId());
//            productPrice.setPrice(p.getCurrentPrice());
//            productPrice.setUpdatedDate(LocalDate.now());
//            productPriceRepo.save(productPrice);
//
//        }
//
//        List<Campaign> endingCampaigns =
//                campaignRepo.getTodayEndingCampaigns(today);
//
//        for (Campaign c : endingCampaigns) {
//            if (c.getStatus() == Status.PRESENT) {
//                c.setStatus(Status.PAST);
//            }
//        }
//
//    }

    @Scheduled(cron = "0 59 10 * * ?",zone = "Asia/Kolkata")
    void startCampaign(){
        int res = campaignRepo.bulkStartCampaign();
        if(res > 0){
            productPriceRepo.insertPriceHistoryForStartedCampaign();
            campaignRepo.activateCampaigns();
        }

    }


    @Scheduled(cron = "0 59 10 * * ?",zone = "Asia/Kolkata")
    void endCampaign(){
        int res = campaignRepo.bulkEndCampaign();
        if(res > 0){
            productPriceRepo.insertPriceHistoryForEndedCampaign();
            campaignRepo.deactivateCampaigns();
        }
    }




}
