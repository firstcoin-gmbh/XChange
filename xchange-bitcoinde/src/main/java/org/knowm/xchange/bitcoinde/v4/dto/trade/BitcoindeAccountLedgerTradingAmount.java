package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class BitcoindeAccountLedgerTradingAmount {

    private Currency currency;
    private BigDecimal before_fee;
    private BigDecimal after_fee;

    /**
     * Constructor
     *
     * @param currency
     * @param before_fee
     * @param after_fee
     */
    public BitcoindeAccountLedgerTradingAmount(
            @JsonProperty("currency") Currency currency,
            @JsonProperty("before_fee") BigDecimal before_fee,
            @JsonProperty("after_fee") BigDecimal after_fee) {
        this.currency = currency;
        this.before_fee = before_fee;
        this.after_fee = after_fee;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getBefore_fee() {
        return before_fee;
    }

    public BigDecimal getAfter_fee() {
        return after_fee;
    }

    @Override
    public String toString() {
        return "BitcoindeAccountLedgerTradingAmount{" +
                "currency=" + currency +
                ", before_fee=" + before_fee +
                ", after_fee=" + after_fee +
                '}';
    }

}
