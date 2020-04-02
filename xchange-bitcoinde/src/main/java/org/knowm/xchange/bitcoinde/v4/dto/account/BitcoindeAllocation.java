package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeAllocation {

  private Integer percent;
  private Integer maxEurVolume;
  private Integer eurVolumeOpenOrders;

  public BitcoindeAllocation(
      @JsonProperty("percent") Integer percent,
      @JsonProperty("max_eur_volume") Integer maxEurVolume,
      @JsonProperty("eur_volume_open_orders") Integer eurVolumeOpenOrders) {
    this.percent = percent;
    this.maxEurVolume = maxEurVolume;
    this.eurVolumeOpenOrders = eurVolumeOpenOrders;
  }

  public Integer getPercent() {
    return percent;
  }

  public Integer getMaxEurVolume() {
    return maxEurVolume;
  }

  public Integer getEurVolumeOpenOrders() {
    return eurVolumeOpenOrders;
  }
}
