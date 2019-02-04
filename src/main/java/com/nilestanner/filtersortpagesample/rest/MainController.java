package com.nilestanner.filtersortpagesample.rest;

import com.nilestanner.filtersortpagesample.model.MainObj;
import com.nilestanner.filtersortpagesample.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    MainService mainService;


    /**
     * Search endpoint allows for filtering, sorting and paging.
     *
     * @param search    Format of search string: {key}{operation}{value?},. ex. key1:value1,key2>value2,key3+ .
     * @param pageSize  number of work orders per page
     * @param pageIndex index of the page requested
     * @return List of work orders
     */
    @GetMapping
    public ResponseEntity search(@RequestParam("search") String search, @RequestParam(value = "pagesize", required = false, defaultValue = "50") Integer pageSize,
                                           @RequestParam(value = "pageindex", required = false, defaultValue = "0") Integer pageIndex) {
        try {
            List<MainObj> mainObjList = mainService.search(search, pageSize, pageIndex);
            return new ResponseEntity(mainObjList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody MainObj obj) {
        try {
            mainService.createObj(obj);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
