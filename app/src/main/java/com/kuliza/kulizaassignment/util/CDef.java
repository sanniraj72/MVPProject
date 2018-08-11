package com.kuliza.kulizaassignment.util;

/**
 * CDef - Define constant
 */
public enum CDef {

    API_KEY("edd4a2ec64574bc0a93112259180504"),
    PLACE("Bangalore"),
    DAYS("7");

    // Value for String constant
    private String value;

    /**
     * Constructor
     *
     * @param value value
     */
    CDef(String value) {
        this.value = value;
    }

    /**
     * Get Enum Value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }
}
