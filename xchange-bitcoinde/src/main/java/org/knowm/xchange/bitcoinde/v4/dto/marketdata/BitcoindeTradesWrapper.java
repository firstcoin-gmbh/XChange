package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;

public class BitcoindeTradesWrapper extends BitcoindeResponse {

  private final String tradingPair;
  private final BitcoindeTrade[] trades;

  public BitcoindeTradesWrapper(
      @JsonProperty("trading_pair") String tradingPair,
      @JsonProperty("trades") BitcoindeTrade[] trades,
      @JsonProperty("credits") int credits,
      @JsonProperty("errors") String[] errors) {
    super(credits, errors);
    this.tradingPair = tradingPair;
    this.trades = trades;
  }

  public String getTradingPair() {
    return tradingPair;
  }

  public BitcoindeTrade[] getTrades() {
    return trades;
  }
}
