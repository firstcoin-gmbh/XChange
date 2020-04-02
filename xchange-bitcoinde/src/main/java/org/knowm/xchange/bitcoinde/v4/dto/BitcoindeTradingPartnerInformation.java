package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradingPartnerInformation {

  @JsonProperty("username")
  public String userName;

  @JsonProperty("is_kyc_full")
  public Boolean isKycFull;

  @JsonProperty("trust_level")
  public String trustLevel;

  @JsonProperty("bank_name")
  public String bankName;

  @JsonProperty("bic")
  public String bic;

  @JsonProperty("seat_of_bank")
  public String seatOfBank;

  @JsonProperty("rating")
  public Integer rating;

  @JsonProperty("amount_trades")
  public Integer amountTrades;
}
