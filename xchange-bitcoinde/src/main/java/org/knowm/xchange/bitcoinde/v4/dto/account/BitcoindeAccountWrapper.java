package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAccountWrapper extends BitcoindeResponse {

  @JsonProperty("data")
  private BitcoindeData data;
}
