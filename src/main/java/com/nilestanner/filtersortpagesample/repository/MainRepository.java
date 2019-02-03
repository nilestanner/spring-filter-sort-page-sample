package com.nilestanner.filtersortpagesample.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nilestanner.filtersortpagesample.model.MainObj;

@Repository
public interface MainRepository extends JpaRepository<MainObj, UUID>, JpaSpecificationExecutor<MainObj> {

}
