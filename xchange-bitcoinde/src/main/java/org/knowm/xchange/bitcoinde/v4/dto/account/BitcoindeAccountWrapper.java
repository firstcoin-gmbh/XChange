package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BitcoindeAccountWrapper {

  private final BitcoindeData data;
  private final List<Object> errors;
  private final Integer credits;

  public BitcoindeAccountWrapper(
      @JsonProperty("data") BitcoindeData data,
      @JsonProperty("errors") List<Object> errors,
      @JsonProperty("credits") Integer credits) {
    this.data = data;
    this.errors = errors;
    this.credits = credits;
  }

  public BitcoindeData getData() {
    return data;
  }

  @JsonProperty("errors")
  public List<Object> getErrors() {
    return errors;
  }

  @JsonProperty("credits")
  public Integer getCredits() {
    return credits;
  }
}
