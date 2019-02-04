package com.nilestanner.filtersortpagesample.repository;

import com.nilestanner.filtersortpagesample.model.MainObj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MainRepository extends JpaRepository<MainObj, UUID>, JpaSpecificationExecutor<MainObj> {

}
