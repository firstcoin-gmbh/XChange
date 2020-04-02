package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderRequirements;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeMyOrder {

  @JsonProperty("order_id")
  public String orderId;

  @JsonProperty("trading_pair")
  public String tradingPair;

  @JsonProperty("is_external_wallet_order")
  public Boolean externalWalletOrder;

  @JsonProperty("type")
  public String type;

  @JsonProperty("max_amount_currency_to_trade")
  public BigDecimal maxAmount;

  @JsonProperty("min_amount_currency_to_trade")
  public BigDecimal minAmount;

  @JsonProperty("price")
  public BigDecimal price;

  @JsonProperty("max_volume_currency_to_pay")
  public Integer maxVolume;

  @JsonProperty("min_volume_currency_to_pay")
  public Integer minVolume;

  @JsonProperty("order_requirements")
  public BitcoindeOrderRequirements orderRequirements;

  @JsonProperty("new_order_for_remaining_amount")
  public Boolean newOrderForRemainingAmount;

  @JsonProperty("state")
  public Integer state;

  @JsonProperty("end_datetime")
  public String endDatetime;

  @JsonProperty("created_at")
  public String createdAt;
}
