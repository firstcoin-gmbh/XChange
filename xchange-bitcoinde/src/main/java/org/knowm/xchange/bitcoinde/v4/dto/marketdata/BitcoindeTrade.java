package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTrade {

  @JsonProperty("trade_id")
  private String tradeId;

  @JsonProperty("date")
  private Date date;

  @JsonProperty("trading_pair")
  private CurrencyPair currencyPair;

  @JsonProperty("amount_currency_to_trade")
  private BigDecimal amount;

  @JsonProperty("is_external_wallet_trade")
  private Boolean isExternalWalletTrade;

  @JsonProperty("price")
  private BigDecimal price;
}