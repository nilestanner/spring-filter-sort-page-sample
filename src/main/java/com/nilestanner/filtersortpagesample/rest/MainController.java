package com.nilestanner.filtersortpagesample.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nilestanner.filtersortpagesample.service.MainService;
import com.nilestanner.filtersortpagesample.model.MainObj;

@RestController
@RequestMapping("/workorder")
public class MainController {

  @Autowired
  MainService mainService;


  /**
   * Search endpoint allows for filtering, sorting and paging.
   *
   * @param search Format of search string: {key}{operation}{value?},. ex. key1:value1,key2>value2,key3+ .
   * @param pageSize number of work orders per page
   * @param pageIndex index of the page requested
   * @return List of work orders
   */
  @GetMapping
  public ResponseEntity searchWorkOrders(@RequestParam("search") String search, @RequestParam(value = "pagesize", required = false, defaultValue = "50") Integer pageSize,
      @RequestParam(value = "pageindex", required = false, defaultValue = "0") Integer pageIndex) {
    try {
      List<MainObj> mainObjList = mainService.search(search, pageSize, pageIndex);
      return new ResponseEntity(mainObjList, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }


}