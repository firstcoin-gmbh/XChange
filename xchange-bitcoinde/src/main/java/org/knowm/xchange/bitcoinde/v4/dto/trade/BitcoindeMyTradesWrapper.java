package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePage;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeLedgerType;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class BitcoindeMyTradesWrapper {
    private final List<BitcoindeMyTrade> trades;
    private final BitcoindePage page;
    private final List<Object> errors;
    private final Integer credits;

    public BitcoindeMyTradesWrapper(
            @JsonProperty("trades") List<BitcoindeMyTrade> trades,
            @JsonProperty("page") BitcoindePage page,
            @JsonProperty("errors") List<Object> errors,
            @JsonProperty("credits") Integer credits) {
        this.trades = trades;
        this.page = page;
        this.errors = errors;
        this.credits = credits;
    }

    public List<BitcoindeMyTrade> getTrades() {
        return trades;
    }

    public BitcoindePage getPage() {
        return page;
    }

    @JsonProperty("errors")
    public List<Object> getErrors() {
        return errors;
    }

    @JsonProperty("credits")
    public Integer getCredits() {
        return credits;
    }

}

