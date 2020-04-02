package org.knowm.xchange.bitcoinde;

import java.util.Arrays;
import org.knowm.xchange.dto.Order.IOrderFlags;

public final class OrderRequirements implements IOrderFlags {

  private final TrustLevel minTrustLevel;
  private Boolean onlyFullyIdentified;
  private String[] seatsOfBank;
  private PaymentOption paymentOption;

  public OrderRequirements(TrustLevel minTrustLevel) {
    this.minTrustLevel = minTrustLevel;
  }

  public TrustLevel getMinTrustLevel() {
    return minTrustLevel;
  }

  public Boolean isOnlyFullyIdentified() {
    return onlyFullyIdentified;
  }

  public void setOnlyFullyIdentified(Boolean value) {
    this.onlyFullyIdentified = value;
  }

  public String[] getSeatsOfBank() {
    return seatsOfBank;
  }

  public void setSeatsOfBank(String[] seatsOfBank) {
    this.seatsOfBank = seatsOfBank;
  }

  public PaymentOption getPaymentOption() {
    return paymentOption;
  }

  public void setPaymentOption(PaymentOption paymentOption) {
    this.paymentOption = paymentOption;
  }

  public void setPaymentOption(Integer value) {
    if (value != null && value > 0 && value <= PaymentOption.values().length) {
      setPaymentOption(PaymentOption.values()[value - 1]);
    }
  }

  @Override
  public int hashCode() {
    return OrderRequirements.class.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof OrderRequirements;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("OrderRequirements [minTrustLevel=")
        .append(minTrustLevel)
        .append(", onlyFullyIdentified=")
        .append(onlyFullyIdentified)
        .append(", seatsOfBank=")
        .append(Arrays.toString(seatsOfBank))
        .append(", paymentOption=")
        .append(paymentOption)
        .append("]");
    return builder.toString();
  }
}
