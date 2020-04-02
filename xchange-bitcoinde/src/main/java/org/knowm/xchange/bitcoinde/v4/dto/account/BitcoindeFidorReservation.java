package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitcoindeFidorReservation {

  private BigDecimal totalAmount;
  private BigDecimal availableAmount;
  private String reservedAt;
  private String validUntil;
  private BitcoindeAllocations allocation;

  public BitcoindeFidorReservation(
      @JsonProperty("total_amount") BigDecimal totalAmount,
      @JsonProperty("available_amount") BigDecimal availableAmount,
      @JsonProperty("reserved_at") String reservedAt,
      @JsonProperty("valid_until") String validUntil,
      @JsonProperty("allocation") BitcoindeAllocations allocation) {
    this.totalAmount = totalAmount;
    this.availableAmount = availableAmount;
    this.reservedAt = reservedAt;
    this.validUntil = validUntil;
    this.allocation = allocation;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public BigDecimal getAvailableAmount() {
    return availableAmount;
  }

  public String getReservedAt() {
    return reservedAt;
  }

  public String getValidUntil() {
    return validUntil;
  }

  public BitcoindeAllocations getAllocation() {
    return allocation;
  }
}
