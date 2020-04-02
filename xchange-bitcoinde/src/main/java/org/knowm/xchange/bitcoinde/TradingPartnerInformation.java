package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.dto.Order.IOrderFlags;

public final class TradingPartnerInformation implements IOrderFlags {

  private final String userName;
  private final boolean fullyIdentified;
  private final TrustLevel trustLevel;
  private String bankName;
  private String bic;
  private String seatOfBank;
  private Integer rating;
  private Integer numberOfTrades;

  public TradingPartnerInformation(
      String userName, boolean fullyIdentified, TrustLevel trustLevel) {
    this.userName = userName;
    this.fullyIdentified = fullyIdentified;
    this.trustLevel = trustLevel;
  }

  public String getUserName() {
    return userName;
  }

  public boolean isFullyIdentified() {
    return fullyIdentified;
  }

  public TrustLevel getTrustLevel() {
    return trustLevel;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBic() {
    return bic;
  }

  public void setBic(String bic) {
    this.bic = bic;
  }

  public String getSeatOfBank() {
    return seatOfBank;
  }

  public void setSeatOfBank(String seatOfBank) {
    this.seatOfBank = seatOfBank;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Integer getNumberOfTrades() {
    return numberOfTrades;
  }

  public void setNumberOfTrades(Integer numberOfTrades) {
    this.numberOfTrades = numberOfTrades;
  }

  @Override
  public int hashCode() {
    return TradingPartnerInformation.class.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof TradingPartnerInformation;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder
        .append("TradingPartnerInformation [userName=")
        .append(userName)
        .append(", fullyIdentified=")
        .append(fullyIdentified)
        .append(", trustLevel=")
        .append(trustLevel)
        .append(", bankName=")
        .append(bankName)
        .append(", bic=")
        .append(bic)
        .append(", seatOfBank=")
        .append(seatOfBank)
        .append(", rating=")
        .append(rating)
        .append(", numberOfTrades=")
        .append(numberOfTrades)
        .append("]");
    return builder.toString();
  }
}
