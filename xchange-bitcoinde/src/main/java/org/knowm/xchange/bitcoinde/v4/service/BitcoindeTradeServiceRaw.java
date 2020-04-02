package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.*;

import java.io.IOException;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOpenOrdersWrapper;
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
}
