package org.knowm.xchange.bitcoinde;

import java.math.BigDecimal;
import org.knowm.xchange.dto.Order.IOrderFlags;

public class OrderQuantities implements IOrderFlags {

  private final BigDecimal minAmountToTrade;
  private final BigDecimal maxAmountToTrade;
  private final BigDecimal minVolumeToPay;
  private final BigDecimal maxVolumeToPay;

  public OrderQuantities(
      BigDecimal minAmountToTrade,
      BigDecimal maxAmountToTrade,
      BigDecimal minVolumeToPay,
      BigDecimal maxVolumeToPay) {
    this.minAmountToTrade = minAmountToTrade;
    this.maxAmountToTrade = maxAmountToTrade;
    this.minVolumeToPay = minVolumeToPay;
    this.maxVolumeToPay = maxVolumeToPay;
  }

  public BigDecimal getMinAmountToTrade() {
    return minAmountToTrade;
  }

  public BigDecimal getMaxAmountToTrade() {
    return maxAmountToTrade;
  }

  public BigDecimal getMinVolumeToPay() {
    return minVolumeToPay;
  }

  public BigDecimal getMaxVolumeToPay() {
    return maxVolumeToPay;
  }

  @Override
  public int hashCode() {
    return OrderQuantities.class.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof OrderQuantities;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("OrderQuantities [minAmountToTrade=")
        .append(minAmountToTrade)
        .append(", maxAmountToTrade=")
        .append(maxAmountToTrade)
        .append(", minVolumeToPay=")
        .append(minVolumeToPay)
        .append(", maxVolumeToPay=")
        .append(maxVolumeToPay)
        .append("]");
    return builder.toString();
  }
}
