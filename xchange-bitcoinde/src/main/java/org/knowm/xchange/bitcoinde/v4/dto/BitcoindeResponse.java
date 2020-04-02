package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeResponse {

  private final int credits;
  private final String[] errors;

  public BitcoindeResponse(
      @JsonProperty("credits") int credits, @JsonProperty("errors") String[] errors) {
    this.credits = credits;
    this.errors = errors;
  }

  public int getCredits() {
    return credits;
  }

  public String[] getErrors() {
    return errors;
  }
}
