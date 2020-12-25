package com.sflpro.cafemanager.table.repository;

import com.sflpro.cafemanager.table.domain.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    boolean existsByNumber(int number);

    @Query("from table t where t.user.id=:userId")
    List<Table> findAssignedTables(@Param("userId") long userId);
}
