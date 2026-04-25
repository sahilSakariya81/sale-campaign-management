package com.example.saleCampaignManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "retry_table")
public class RetryTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retry_id")
    private long id;
    private long ProductId;
    private double discount;
    private int count;
    private LocalDate date;

    public RetryTable(long productId, Double totalDiscount, int count,LocalDate date) {
        this.ProductId = productId;
        this.discount = totalDiscount;
        this.count = count;
        this.date = date;
    }
}
