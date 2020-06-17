package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum BitcoindeTradeState {
    @JsonProperty("-1")
    CANCELLED(-1),
    @JsonProperty("0")
    PENDING(0),
    @JsonProperty("1")
    SUCCESSFUL(1);
    
    private int value;

    BitcoindeTradeState(int value) { this.value = value; }

    @JsonValue
    public int value() { return value; }
}
