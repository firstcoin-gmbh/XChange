package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeIdResponse {

  private final String id;

  public BitcoindeIdResponse(@JsonProperty("order_id") String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("BitcoindeIdResponse [id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }
}
