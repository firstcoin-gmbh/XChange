package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradingPartnerInformation {

  @JsonProperty("username")
  private String userName;

  @JsonProperty("is_kyc_full")
  private Boolean isKycFull;

  @JsonProperty("trust_level")
  private String trustLevel;

  @JsonProperty("depositor")
  private String depositor;

  @JsonProperty("iban")
  private String iban;

  @JsonProperty("bank_name")
  private String bankName;

  @JsonProperty("bic")
  private String bic;

  @JsonProperty("seat_of_bank")
  private String seatOfBank;

  @JsonProperty("rating")
  private Integer rating;

  @JsonProperty("amount_trades")
  private Integer amountTrades;
}
