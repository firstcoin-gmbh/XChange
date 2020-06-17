package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.utils.DateUtils;

public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

  public BitcoindeAccountService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    String currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      final TradeHistoryParamCurrency currenciesParam = (TradeHistoryParamCurrency) params;
      if (currenciesParam.getCurrency() != null) {
        currency = currenciesParam.getCurrency().toString();
      }
    }

    String type = null;
    if (params instanceof BitcoindeTradeHistoryParams) {
      final BitcoindeType typeParam = ((BitcoindeTradeHistoryParams) params).getBitcoindeType();
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

    return BitcoindeAdapters.adaptFundingHistory(getBitcoindeFundingHistory(currency, type, startTime, endTime, page),currencyPair);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {return new BitcoindeTradeHistoryParams();}
}
