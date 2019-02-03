package com.nilestanner.filtersortpagesample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

  private String key;
  private SearchOperation operation;
  private Object value;
}
