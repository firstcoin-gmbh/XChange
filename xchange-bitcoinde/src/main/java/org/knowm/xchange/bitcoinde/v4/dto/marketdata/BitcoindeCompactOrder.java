package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitcoindeCompactOrder {

  private final BigDecimal price;
  private final BigDecimal amount;

  public BitcoindeCompactOrder(
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount_currency_to_trade") BigDecimal amount) {
    this.price = price;
    this.amount = amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "BitcoindeOrder{" + "price=" + price + ", amount=" + amount + '}';
  }
}
