package org.knowm.xchange.bitcoinde;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.FundingRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitcoindeUtils {

  private static DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

  private BitcoindeUtils() {}

  public static String createBitcoindePair(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase()
        + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  public static String createBitcoindeType(OrderType type) {
    return type.equals(OrderType.ASK) ? "sell" : "buy";
  }

  public static String rfc3339Timestamp(Date date) {
    return DATE_FORMATTER.format(date);
  }

  public static Date fromRfc3339Timestamp(String timestamp) throws ParseException {
    return DATE_FORMATTER.parse(timestamp);
  }

  public static int createBitcoindeBoolean(boolean value) {
    return value ? 1 : 0;
  }
}
