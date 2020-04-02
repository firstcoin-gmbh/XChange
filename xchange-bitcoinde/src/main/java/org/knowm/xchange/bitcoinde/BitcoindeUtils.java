package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

public class BitcoindeUtils {

  private BitcoindeUtils() {}

  public static String createBitcoindePair(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase()
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  public static String createBitcoindeType(OrderType type) {
    return type.equals(OrderType.ASK) ? "sell" : "buy";
  }

  public static int createBitcoindeBoolean(boolean value) {
    return value ? 1 : 0;
  }
}
