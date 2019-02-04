package com.nilestanner.filtersortpagesample.processor;

import com.nilestanner.filtersortpagesample.model.SearchCriteria;
import com.nilestanner.filtersortpagesample.model.SearchOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchCriteriaParser {

    private static String wordRegex = "[a-zA-Z]\\w*";
    private static String valueRegex = "\\w+";
    private static String operatorRegex = "(:|<|>|!|\\+|-|\\s)";
    private static String timestampRegex = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0 -9]{2}:[0-9]{2}:[0-9]{2}-[0-9]{2}:[0-9]{2}";
    private static String idRegex = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";
    private static String fullRegex = "(" + wordRegex + ")" + operatorRegex + "(" + timestampRegex + "|" + idRegex + "|" + valueRegex + ")?,";
    private static final Pattern searchPattern = Pattern.compile(fullRegex);

    /**
     * Parses the search string from the endpoint.
     *
     * @param searchString unparsed search string
     * @return list of of search criteria
     */
    public List<SearchCriteria> parse(String searchString) {
        List<SearchCriteria> searchCriterias = new ArrayList<>();
        if (searchString != null) {
            Matcher matcher = searchPattern.matcher(searchString + ",");
            while (matcher.find()) {
                SearchCriteria searchCriteria = new SearchCriteria();
                searchCriteria.setKey(matcher.group(1));
                searchCriteria.setOperation(SearchOperation.getSimpleOperation(matcher.group(2)));
                searchCriteria.setValue(matcher.group(3));
                // easiest way to test for bad UUIDs
                if ((searchCriteria.getOperation() != SearchOperation.SORT_DESC && searchCriteria.getOperation() != SearchOperation.SORT_ASC) || searchCriteria.getValue() == null) {
                    searchCriterias.add(searchCriteria);
                }
            }
        }
        return searchCriterias;
    }
}
