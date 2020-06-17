package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.bitcoinde.v4.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradeState;
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

  public static int createBitcoindePaymentOption(BitcoindePaymentOption option){
    return option.equals(BitcoindePaymentOption.SEPA_ONLY) ?  1 : 2;
  }

  public static int createBitcoindeTradeState(BitcoindeTradeState tradeState){
    switch (tradeState)
    {
      case PENDING:
        return  0;
      case SUCCESSFUL:
        return 1;
      default:
        return -1;
    }
  }
}
