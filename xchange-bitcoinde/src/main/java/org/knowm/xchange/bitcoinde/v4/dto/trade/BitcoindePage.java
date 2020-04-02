package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindePage {

  @JsonProperty("current")
  public Integer current;

  @JsonProperty("last")
  public Integer last;
}
