package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeAccountLedgerTrade;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTrade;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAccountLedger {
    @JsonProperty("date")
    private Date date;

    @JsonProperty("type")
    private BitcoindeType type;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("trade")
    private BitcoindeAccountLedgerTrade trade;

    @JsonProperty("cashflow")
    private BigDecimal cashflow;

    @JsonProperty("balance")
    private BigDecimal balance;
}
