package org.knowm.xchange.bitcoinde.v4;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindePaymentOption {
    @JsonProperty("1")
    EXPRESS_ONLY(1),
    @JsonProperty("2")
    SEPA_ONLY(2);

    private int value;

    BitcoindePaymentOption(int value) { this.value = value; }

    @JsonValue
    public int value() { return value; }
}
