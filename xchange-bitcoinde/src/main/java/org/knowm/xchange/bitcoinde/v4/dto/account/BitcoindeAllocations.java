package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAllocations {

  private BitcoindeAllocation btc;
  private BitcoindeAllocation eth;
  private BitcoindeAllocation bch;

  public BitcoindeAllocations(
      @JsonProperty("btc") BitcoindeAllocation btc,
      @JsonProperty("eth") BitcoindeAllocation eth,
      @JsonProperty("bch") BitcoindeAllocation bch) {
    this.btc = btc;
    this.eth = eth;
    this.bch = bch;
  }

  public BitcoindeAllocation getBtc() {
    return btc;
  }

  public BitcoindeAllocation getEth() {
    return eth;
  }

  public BitcoindeAllocation getBch() {
    return bch;
  }
}
