package com.nilestanner.filtersortpagesample.service;

import com.nilestanner.filtersortpagesample.model.MainObj;
import com.nilestanner.filtersortpagesample.model.SearchCriteria;
import com.nilestanner.filtersortpagesample.model.SearchSpecification;
import com.nilestanner.filtersortpagesample.processor.SearchCriteriaParser;
import com.nilestanner.filtersortpagesample.repository.MainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nilestanner.filtersortpagesample.model.SearchOperation.SORT_ASC;
import static com.nilestanner.filtersortpagesample.model.SearchOperation.SORT_DESC;

@Slf4j
@Service
public class MainService {

    @Autowired
    SearchCriteriaParser searchCriteriaParser;

    @Autowired
    MainRepository mainRepository;

    /**
     * This function takes in the raw input from the endpoint, and performs searches against the DB.
     *
     * @param searchString unparsed search criteria string
     * @param pageSize     number of records to return per page
     * @return list of WorkOrders
     */
    public List<MainObj> search(String searchString, Integer pageSize, Integer pageIndex) {
        List<SearchCriteria> searchCriteria = searchCriteriaParser.parse(searchString);
        List<SearchSpecification> specList = searchCriteria.stream().map(criterion -> new SearchSpecification(criterion)).collect(Collectors.toList());
        Specification<MainObj> specs = andSpecification(specList).orElseThrow(() -> new IllegalArgumentException("No criteria provided"));
        List<Sort> sortList = generateSortList(searchCriteria);
        Sort sort = andSort(sortList).orElse(Sort.unsorted());
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page page = mainRepository.findAll(specs, pageable);
        return page.getContent();
    }

    private <T, V extends Specification<T>> Optional<Specification<T>> andSpecification(List<V> criteria) {
        Iterator<V> itr = criteria.iterator();
        if (itr.hasNext()) {
            Specification<T> spec = Specification.where(itr.next());
            while (itr.hasNext()) {
                spec = spec.and(itr.next());
            }
            return Optional.of(spec);
        }
        return Optional.empty();
    }

    private <T, V extends Sort> Optional<Sort> andSort(List<V> criteria) {

        Iterator<V> itr = criteria.iterator();
        if (itr.hasNext()) {
            Sort sort = (itr.next());
            while (itr.hasNext()) {
                sort = sort.and(itr.next());
            }
            return Optional.of(sort);
        }
        return Optional.empty();
    }

    private List<Sort> generateSortList(List<SearchCriteria> criteria) {
        return criteria.stream().map((criterion) -> {
            switch (criterion.getOperation()) {
                case SORT_ASC:
                    return Sort.by(Order.asc(criterion.getKey()));
                case SORT_DESC:
                    return Sort.by(Order.desc(criterion.getKey()));
                default:
                    return null;
            }
        }).filter((sort) -> sort != null).collect(Collectors.toList());
    }

    public void createObj(MainObj obj) {
        mainRepository.save(obj);
    }

}


