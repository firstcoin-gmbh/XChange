package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;

public class BitcoindeCompactOrderbookWrapper extends BitcoindeResponse {

  private final String tradingPair;
  private final BitcoindeCompactOrders bitcoindeOrders;

  public BitcoindeCompactOrderbookWrapper(
      @JsonProperty("trading_pair") String tradingPair,
      @JsonProperty("orders") BitcoindeCompactOrders bitcoindeOrders,
      @JsonProperty("credits") int credits,
      @JsonProperty("errors") String[] errors) {
    super(credits, errors);
    this.tradingPair = tradingPair;
    this.bitcoindeOrders = bitcoindeOrders;
  }

  public String getTradingPair() {
    return tradingPair;
  }

  public BitcoindeCompactOrders getBitcoindeOrders() {
    return bitcoindeOrders;
  }
}
