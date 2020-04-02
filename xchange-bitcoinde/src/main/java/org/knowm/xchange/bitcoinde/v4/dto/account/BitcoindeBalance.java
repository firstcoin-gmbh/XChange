package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitcoindeBalance {

  private BigDecimal availableAmount;
  private BigDecimal reservedAmount;
  private BigDecimal totalAmount;

  public BitcoindeBalance(
      @JsonProperty("available_amount") BigDecimal availableAmount,
      @JsonProperty("reserved_amount") BigDecimal reservedAmount,
      @JsonProperty("total_amount") BigDecimal totalAmount) {
    this.availableAmount = availableAmount;
    this.reservedAmount = reservedAmount;
    this.totalAmount = totalAmount;
  }

  public BigDecimal getAvailableAmount() {
    return availableAmount;
  }

  public BigDecimal getReservedAmount() {
    return reservedAmount;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }
}
