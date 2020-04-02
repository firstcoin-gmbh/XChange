package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author matthewdowney */
public class BitcoindeTrade {

  private final long date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long tid;

  /**
   * Constructor
   *
   * @param tid
   * @param price
   * @param amount
   * @param date
   */
  public BitcoindeTrade(
      @JsonProperty("tid") long tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount_currency_to_trade") BigDecimal amount,
      @JsonProperty("date") long date) {
    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.date = date;
  }

  public long getTid() {
    return tid;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public long getDate() {
    return date;
  }

  @Override
  public String toString() {

    return "BitcoindeTrade{"
        + "date="
        + date
        + ", price="
        + price
        + ", amount='"
        + amount
        + "', tid="
        + tid
        + '}';
  }
}
