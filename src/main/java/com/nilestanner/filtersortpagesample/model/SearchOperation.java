package com.nilestanner.filtersortpagesample.model;

public enum SearchOperation {
    EQUALITY, NEGATION, GREATER_THAN, LESS_THAN, SORT_ASC, SORT_DESC;

    public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "+", "-"};

    /**
     * Converts strings into SearchOpertaion enum.
     *
     * @param input single char string.
     * @return SearchOperation enum
     */
    public static SearchOperation getSimpleOperation(String input) {
        switch (input) {
            case ":":
                return EQUALITY;
            case "!":
                return NEGATION;
            case ">":
                return GREATER_THAN;
            case "<":
                return LESS_THAN;
            case "+":
            case " ": // + is encoded in query strings as a space
                return SORT_ASC;
            case "-":
                return SORT_DESC;
            default:
                return null;
        }
    }
}
