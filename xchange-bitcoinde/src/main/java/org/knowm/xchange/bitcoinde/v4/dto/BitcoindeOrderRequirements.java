package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeOrderRequirements {

  @JsonProperty("min_trust_level")
  public String minTrustLevel;

  @JsonProperty("only_kyc_full")
  public Boolean onlyKycFull;

  @JsonProperty("seat_of_bank")
  public String[] seatOfBank;

  @JsonProperty("payment_option")
  public Integer paymentOption;
}
