package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeEncryptedInformation {

  private String bicShort;
  private String bicFull;
  private String uid;

  public BitcoindeEncryptedInformation(
      @JsonProperty("bic_short") String bicShort,
      @JsonProperty("bic_full") String bicFull,
      @JsonProperty("uid") String uid) {
    this.bicShort = bicShort;
    this.bicFull = bicFull;
    this.uid = uid;
  }

  public String getBicShort() {
    return bicShort;
  }

  public String getBicFull() {
    return bicFull;
  }

  public String getUid() {
    return uid;
  }
}
