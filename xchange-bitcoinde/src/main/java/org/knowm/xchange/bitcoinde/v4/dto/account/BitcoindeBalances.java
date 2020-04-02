package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeBalances {

  @JsonProperty("btc")
  public BitcoindeBalance btc;

  @JsonProperty("bch")
  public BitcoindeBalance bch;

  @JsonProperty("eth")
  public BitcoindeBalance eth;

  @JsonProperty("bsv")
  public BitcoindeBalance bsv;

  @JsonProperty("btg")
  public BitcoindeBalance btg;
}
