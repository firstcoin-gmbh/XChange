package org.knowm.xchange.bitcoinde.v4.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitcoindeAccountLedgerTrade {

    private final String trade_id;
    private final String trading_pair;
    private final BigDecimal price;
    private final boolean is_external_wallet_trade;
    private final BitcoindeAccountLedgerTradingAmount currency_to_trade;
    private final BitcoindeAccountLedgerTradingAmount currency_to_pay;

    /**
     * Constructor
     *
     * @param trade_id
     * @param trading_pair
     * @param price
     * @param is_external_wallet_trade
     * @param currency_to_trade
     * @param currency_to_pay
     */
    public BitcoindeAccountLedgerTrade(
            @JsonProperty("trade_id") String trade_id,
            @JsonProperty("trading_pair") String trading_pair,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("is_external_wallet_trade") boolean is_external_wallet_trade,
            @JsonProperty("currency_to_trade") BitcoindeAccountLedgerTradingAmount currency_to_trade,
            @JsonProperty("currency_to_pay") BitcoindeAccountLedgerTradingAmount currency_to_pay) {
        this.trade_id = trade_id;
        this.trading_pair = trading_pair;
        this.price = price;
        this.is_external_wallet_trade = is_external_wallet_trade;
        this.currency_to_trade = currency_to_trade;
        this.currency_to_pay = currency_to_pay;
    }

    public String getTradeId() {
        return trade_id;
    }

    public String getTradingPair() {
        return trading_pair;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isExternalWalletTrade() { return is_external_wallet_trade; }

    public BitcoindeAccountLedgerTradingAmount getCurrencyToTrade() {
        return currency_to_trade;
    }

    public BitcoindeAccountLedgerTradingAmount getCurrencyToPay() {
        return currency_to_pay;
    }

    @Override
    public String toString() {

        return "BitcoindeTrade{"
                + "trading_pair="
                + trading_pair
                + ", price="
                + price
                + ", is_external_wallet_trade="
                + is_external_wallet_trade
                + ", currency_to_trade='"
                + currency_to_trade
                + ", currency_to_pay='"
                + currency_to_pay
                + "', tid="
                + trade_id
                + '}';
    }

}
