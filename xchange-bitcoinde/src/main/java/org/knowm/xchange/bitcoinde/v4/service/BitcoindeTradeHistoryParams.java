package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradeState;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import lombok.Data;

@Data
public  class BitcoindeTradeHistoryParams
        implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging, TradeHistoryParamCurrency {

    private Integer pageLength;
    private Integer pageNumber;
    private CurrencyPair currencyPair;
    private Currency currency;

    private BitcoindeType bitcoindeType;
    private BitcoindePaymentOption paymentOption;
    private BitcoindeTradeState tradeState;
    private Integer bitcoindeState;
    private Boolean onlyTradesWithActionForPaymentOrTransferRequired;
}
