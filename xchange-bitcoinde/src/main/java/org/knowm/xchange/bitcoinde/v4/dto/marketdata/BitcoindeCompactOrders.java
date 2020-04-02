package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class BitcoindeCompactOrders {

  private final BitcoindeCompactOrder[] bids;
  private final BitcoindeCompactOrder[] asks;

  public BitcoindeCompactOrders(
      @JsonProperty("bids") BitcoindeCompactOrder[] bids,
      @JsonProperty("asks") BitcoindeCompactOrder[] asks) {
    this.bids = bids;
    this.asks = asks;
  }

  public BitcoindeCompactOrder[] getBids() {
    return bids;
  }

  public BitcoindeCompactOrder[] getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "BitcoindeOrders{"
        + "bids="
        + Arrays.toString(bids)
        + ", asks="
        + Arrays.toString(asks)
        + '}';
  }
}
