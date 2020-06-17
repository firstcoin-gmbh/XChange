package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeLedgerType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeAccountServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeAccountServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeAccountWrapper getBitcoindeAccount() throws IOException {
    try {
      return bitcoinde.getAccount(apiKey, nonceFactory, signatureCreator);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  /**
   * Helper function that converts a FundingRecord.Type to String.
   * @param currency mandatory
   * @param type optional (default: all)
   * @param startTime optional (default: 10 days ago)
   * @param endTime optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeAccountLedgerWrapper getAccountLedger(
          Currency currency, FundingRecord.Type type, Date startTime, Date endTime, Integer page)
          throws IOException {
    return getAccountLedger(currency,
            type == null ? null : type.name().toLowerCase(),
            startTime,
            endTime,
            page);
  }

  /**
   * Helper function that converts a BitcoindeLedgerType to String.
   * @param currency mandatory
   * @param customType optional (default: all)
   * @param startTime optional (default: 10 days ago)
   * @param endTime optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeAccountLedgerWrapper getAccountLedger(
          Currency currency, BitcoindeLedgerType customType, Date startTime, Date endTime, Integer page)
          throws IOException {
    return getAccountLedger(currency,
            customType == null ? null : customType.name().toLowerCase(),
            startTime,
            endTime,
            page);
  }

  /**
   * Calls the API function Bitcoinde.getAccountLedger().
   * @param currency mandatory
   * @param typeString optional (default: all)
   * @param start optional (default: 10 days ago)
   * @param end optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeAccountLedgerWrapper getAccountLedger(
          Currency currency, String typeString, Date start, Date end, Integer page)
          throws IOException {
    String currencyString = currency.toString().toLowerCase();
    String startTime = start == null ? null : BitcoindeUtils.rfc3339Timestamp(start);
    String endTime = end == null ? null : BitcoindeUtils.rfc3339Timestamp(end);
    return bitcoinde.getAccountLedger(apiKey, nonceFactory, signatureCreator,
            currencyString,
            typeString,
            startTime,
            endTime,
            page);
  }

}
