package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradeState;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.utils.DateUtils;

public class BitcoindeTradeService extends BitcoindeTradeServiceRaw implements TradeService {

  public BitcoindeTradeService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BitcoindeAdapters.adaptOpenOrders(getBitcoindeMyOpenOrders());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    BitcoindeIdResponse response = bitcoindePlaceLimitOrder(limitOrder);
    return response.getId();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdAndCurrencyPair) {
      CancelOrderByIdAndCurrencyPair cob = (CancelOrderByIdAndCurrencyPair) orderParams;
      bitcoindeCancelOrders(cob.getId(), cob.getCurrencyPair());
    }

    return true;
  }


  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    String type = null;
    BitcoindePaymentOption paymentOptionParam = null;
    BitcoindeTradeState tradeState = null;
    boolean onlyTradesWithActionForPaymentOrTransferRequired = false;

    if (params instanceof BitcoindeTradeHistoryParams) {
      final BitcoindeType typeParam = ((BitcoindeTradeHistoryParams) params).getBitcoindeType();
      paymentOptionParam = ((BitcoindeTradeHistoryParams) params).getPaymentOption();
      onlyTradesWithActionForPaymentOrTransferRequired = ((BitcoindeTradeHistoryParams) params).getOnlyTradesWithActionForPaymentOrTransferRequired();
      tradeState = ((BitcoindeTradeHistoryParams) params).getTradeState();
      type = typeParam.toString();
    }

    final Date startTime;
    final Date endTime;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = timeSpan.getStartTime();
      endTime = timeSpan.getEndTime();
    } else {
      startTime = null;
      endTime = null;
    }

    Integer page = null;
    if (params instanceof TradeHistoryParamPaging) {
      page = ((TradeHistoryParamPaging) params).getPageNumber();
    }

    CurrencyPair currencyPair = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }

    return BitcoindeAdapters.adaptTradeHistory(getBitcoindeTradesHistory(currencyPair,
            type,
            BitcoindeUtils.createBitcoindeTradeState(tradeState),
            BitcoindeUtils.createBitcoindeBoolean(onlyTradesWithActionForPaymentOrTransferRequired),
            BitcoindeUtils.createBitcoindePaymentOption(paymentOptionParam),
            startTime,
            endTime,
            page),
            currencyPair);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {return new BitcoindeTradeHistoryParams();}
}
