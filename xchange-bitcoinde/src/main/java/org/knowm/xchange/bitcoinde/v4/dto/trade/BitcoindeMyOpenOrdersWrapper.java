package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindePage;

import java.util.List;

public class BitcoindeMyOpenOrdersWrapper {

  @JsonProperty("orders")
  public List<BitcoindeMyOrder> orders;

  @JsonProperty("page")
  public BitcoindePage page;

  @JsonProperty("errors")
  public List<Object> errors;

  @JsonProperty("credits")
  public Integer credits;
}
