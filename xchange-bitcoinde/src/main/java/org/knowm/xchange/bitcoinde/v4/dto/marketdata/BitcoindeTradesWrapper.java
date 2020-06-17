package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradesWrapper extends BitcoindeResponse {

  @JsonProperty("trading_pair")
  private String tradingPair;
  @JsonProperty("trades")
  private BitcoindeTrade[] trades;
}
