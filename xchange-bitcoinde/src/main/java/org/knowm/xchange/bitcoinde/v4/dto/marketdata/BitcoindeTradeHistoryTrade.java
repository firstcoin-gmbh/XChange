package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTradingPartnerInformation;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradeState;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradeHistoryTrade extends BitcoindeTrade{

    @JsonProperty("type")
    private BitcoindeType type;

    @JsonProperty("amount_currency_to_trade_after_fee")
    private BigDecimal amountAfterFee;

    @JsonProperty("volume_currency_to_pay")
    private BigDecimal volume;

    @JsonProperty("volume_currency_to_pay_after_fee")
    private BigDecimal volumeAfterFee;

    @JsonProperty("fee_currency_to_pay")
    private BigDecimal feeCurrencyToPay;

    @JsonProperty("fee_currency_to_trade")
    private BigDecimal feeCurrencyToTrade;

    @JsonProperty("new_trade_id_for_remaining_amount")
    private String newTradeIdForRemainingAmount;

    //enum?
    @JsonProperty("my_rating_for_trading_partner")
    private String rating;

    @JsonProperty("trading_partner_information")
    private BitcoindeTradingPartnerInformation tradingPartnerInformation;

    @JsonProperty("is_trade_marked_as_paid")
    private Boolean markedAsPaid;

    @JsonProperty("trade_marked_as_paid_at")
    private Date markedAsPaidAt;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("successfully_finished_at")
    private  Date finishedAt;

    @JsonProperty("cancelled_at")
    private Date cancelledAt;

    //Using old options?
    @JsonProperty("payment_method")
    private BitcoindePaymentOption paymentMethod;

    //enum?
    @JsonProperty("state")
    private BitcoindeTradeState state;
}
