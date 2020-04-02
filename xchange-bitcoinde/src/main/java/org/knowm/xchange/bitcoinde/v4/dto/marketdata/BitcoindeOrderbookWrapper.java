package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;

public class BitcoindeOrderbookWrapper extends BitcoindeResponse {

  private final BitcoindeOrder[] bitcoindeOrders;

  public BitcoindeOrderbookWrapper(
      @JsonProperty("orders") BitcoindeOrder[] bitcoindeOrders,
      @JsonProperty("credits") int credits,
      @JsonProperty("errors") String[] errors) {
    super(credits, errors);
    this.bitcoindeOrders = bitcoindeOrders;
  }

  public BitcoindeOrder[] getBitcoindeOrders() {
    return bitcoindeOrders;
  }
}
