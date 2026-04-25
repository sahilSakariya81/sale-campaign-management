package com.example.saleCampaignManagementSystem.service;

import com.example.saleCampaignManagementSystem.dto.GetProductDto;
import com.example.saleCampaignManagementSystem.model.Product;
import com.example.saleCampaignManagementSystem.model.ProductPrice;
import com.example.saleCampaignManagementSystem.repository.ProductPriceRepo;
import com.example.saleCampaignManagementSystem.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductPriceRepo productPriceRepo;

    public void addProduct(Product product) {
        productRepo.save(product);
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(product.getProductId());
        productPrice.setPrice(product.getCurrentPrice());
        productPrice.setUpdatedDate(LocalDate.now());
        productPriceRepo.save(productPrice);
    }

    public void addProducts(List<Product> productList) {
        productRepo.saveAll(productList);
        for(Product p : productList){
            ProductPrice productPrice = new ProductPrice();
            productPrice.setProductId(p.getProductId());
            productPrice.setPrice(p.getCurrentPrice());
            productPrice.setUpdatedDate(LocalDate.now());
            productPriceRepo.save(productPrice);
        }
    }

    public GetProductDto getProducts(Pageable pageable) {
        Page<Product> products = productRepo.findAll((org.springframework.data.domain.Pageable) pageable);

        if(products.isEmpty()){
            return  new GetProductDto(List.of(), products.getNumber(), products.getSize(),products.getTotalPages(),HttpStatus.OK,"No Products Avaolable" );

        }
        return  new GetProductDto(products.getContent(), products.getNumber(), products.getSize(),products.getTotalPages(),HttpStatus.OK,"Products Fetched Successfully" );


    }
}
