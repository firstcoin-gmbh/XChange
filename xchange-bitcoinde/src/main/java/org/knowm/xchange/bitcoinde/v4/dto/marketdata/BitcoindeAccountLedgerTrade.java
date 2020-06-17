package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAccountLedgerTrade extends BitcoindeTrade {

    @JsonProperty("currency_to_trade")
    private BitcoindeCurrency currencyToTrade;

    @JsonProperty("currency_to_pay")
    private BitcoindeCurrency currencyToPay;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class BitcoindeCurrency{
        @JsonProperty("currency")
        private Currency currency;

        @JsonProperty("before_fee")
        private BigDecimal beforeFee;

        @JsonProperty("after_fee")
        private BigDecimal afterFee;
    }
}
