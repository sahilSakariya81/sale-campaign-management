package com.example.saleCampaignManagementSystem.repository;

import com.example.saleCampaignManagementSystem.model.RetryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RetryRepository extends JpaRepository<RetryTable,Long> {

    @Query(value = "SELECT * FROM retry_table where date = ?1",nativeQuery = true)
    List<RetryTable> getRetryList(LocalDate date);

}
