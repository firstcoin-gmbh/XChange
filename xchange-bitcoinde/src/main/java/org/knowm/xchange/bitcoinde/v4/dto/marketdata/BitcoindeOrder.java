package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderRequirements;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradingPartnerInformation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeOrder {

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
  public BigDecimal maxVolume;

  @JsonProperty("min_volume_currency_to_pay")
  public BigDecimal minVolume;

  @JsonProperty("order_requirements_fullfilled")
  public Boolean requirementsFullfilled;

  @JsonProperty("trading_partner_information")
  public BitcoindeTradingPartnerInformation tradingPartnerInformation;

  @JsonProperty("order_requirements")
  public BitcoindeOrderRequirements orderRequirements;
}
