package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.*;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeTradeServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeTradeServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeMyOpenOrdersWrapper getBitcoindeMyOpenOrders() throws IOException {
    try {
      return bitcoinde.getMyOrders(apiKey, nonceFactory, signatureCreator);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeResponse bitcoindeCancelOrders(String orderId, CurrencyPair currencyPair)
      throws IOException {
    try {
      String currPair = BitcoindeUtils.createBitcoindePair(currencyPair);

      return bitcoinde.deleteOrder(apiKey, nonceFactory, signatureCreator, orderId, currPair);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeIdResponse bitcoindePlaceLimitOrder(LimitOrder order) throws IOException {
    try {
      String side = createBitcoindeType(order.getType());
      String bitcoindeCurrencyPair = createBitcoindePair(order.getCurrencyPair());

      return bitcoinde.createOrder(
          apiKey,
          nonceFactory,
          signatureCreator,
          order.getOriginalAmount(),
          order.getLimitPrice(),
          bitcoindeCurrencyPair,
          side);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  /**
   * Helper function that converts a BitcoindeTradeType and BitcoindeTradeState to Strings.
   * @param tradingPair mandatory
   * @param type optional (default: all)
   * @param state optional (default: all)
   * @param only_trades_with_action_for_payment_or_transfer_required optional (default: all)
   * @param startTime optional (default: 10 days ago)
   * @param endTime optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeMyTradesWrapper getMyTrades(
          CurrencyPair tradingPair, BitcoindeMyTrade.Type type, BitcoindeMyTrade.State state,
          Integer only_trades_with_action_for_payment_or_transfer_required, Date startTime, Date endTime, Integer page)
          throws IOException {
    return getMyTrades(tradingPair,
            type == null ? null : type.name().toLowerCase(),
            state == null ? null : state.toInt(),
            only_trades_with_action_for_payment_or_transfer_required,
            startTime,
            endTime,
            page);
  }

  /**
   * Calls the API function Bitcoinde.getMyTrades().
   * @param tradingPair optional (default: all)
   * @param typeString optional (default: all)
   * @param stateInt optional (default: all)
   * @param only_trades_with_action_for_payment_or_transfer_required (default: all)
   * @param start optional (default: 10 days ago)
   * @param end optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeMyTradesWrapper getMyTrades(
          CurrencyPair tradingPair, String typeString, Integer stateInt,
          Integer only_trades_with_action_for_payment_or_transfer_required, Date start, Date end, Integer page)
          throws IOException {
    String tradingPairString = tradingPair == null ? null :
            (tradingPair.base.toString() + tradingPair.counter.toString()).toLowerCase();
    String startTime = start == null ? null : BitcoindeUtils.rfc3339Timestamp(start);
    String endTime = end == null ? null : BitcoindeUtils.rfc3339Timestamp(end);
    if (tradingPairString == null) {
      return bitcoinde.getMyTrades(apiKey, nonceFactory, signatureCreator,
              typeString,
              stateInt,
              only_trades_with_action_for_payment_or_transfer_required,
              startTime,
              endTime,
              page);
    }
    else {
      return bitcoinde.getMyTrades(apiKey, nonceFactory, signatureCreator,
              tradingPairString,
              typeString,
              stateInt,
              only_trades_with_action_for_payment_or_transfer_required,
              startTime,
              endTime,
              page);
    }
  }

}
