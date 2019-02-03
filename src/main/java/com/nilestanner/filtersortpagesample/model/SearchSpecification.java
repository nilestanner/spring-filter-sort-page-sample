package com.nilestanner.filtersortpagesample.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchSpecification implements Specification<MainObj> {

  private SearchCriteria criteria;


  @Override
  public Predicate toPredicate(Root<MainObj> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    Boolean valueIsDateTime = criteria.getKey().equalsIgnoreCase("createdAt") || criteria.getKey().equalsIgnoreCase("modifiedAt");
    if (criteria.getKey().equalsIgnoreCase("id")) {
      criteria.setValue(UUID.fromString(criteria.getValue().toString()));
    } else if (valueIsDateTime) {
      criteria.setValue(ZonedDateTime.parse(criteria.getValue().toString()));
    }
    switch (criteria.getOperation()) {
      case EQUALITY:
        return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
      case NEGATION:
        return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
      case LESS_THAN:
        if (valueIsDateTime) {
          // need to convert tell criteriaBuilder that value is a ZonedDateTime
          return criteriaBuilder.lessThan(root.get(criteria.getKey()), (ZonedDateTime)criteria.getValue());
        } else {
          return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        }
      case GREATER_THAN:
        if (valueIsDateTime) {
          // need to convert tell criteriaBuilder that value is a ZonedDateTime
          return criteriaBuilder.greaterThan(root.get(criteria.getKey()), (ZonedDateTime)criteria.getValue());
        } else {
          return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        }
      default:
        return null;
    }
  }
}
