package com.sflpro.cafemanager.table.repository;

import com.sflpro.cafemanager.table.domain.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    boolean existsByNumber(int number);
}
