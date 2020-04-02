package org.knowm.xchange.bitcoinde.v4;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.knowm.xchange.bitcoinde.OrderQuantities;
import org.knowm.xchange.bitcoinde.OrderRequirements;
import org.knowm.xchange.bitcoinde.TradingPartnerInformation;
import org.knowm.xchange.bitcoinde.TrustLevel;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeBalances;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeFidorReservation;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrder;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrder;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public final class BitcoindeAdapters {

  /** Private constructor. */
  private BitcoindeAdapters() {}

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook
   * object.
   *
   * @param bitcoindeOrderbookWrapper the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptCompactOrderBook(
      BitcoindeCompactOrderbookWrapper bitcoindeOrderbookWrapper, CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createCompactOrders(
            currencyPair,
            Order.OrderType.ASK,
            bitcoindeOrderbookWrapper.getBitcoindeOrders().getAsks());
    final List<LimitOrder> bids =
        createCompactOrders(
            currencyPair,
            Order.OrderType.BID,
            bitcoindeOrderbookWrapper.getBitcoindeOrders().getBids());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  public static OrderBook adaptOrderBook(
      BitcoindeOrderbookWrapper asksWrapper,
      BitcoindeOrderbookWrapper bidsWrapper,
      CurrencyPair currencyPair) {
    final List<LimitOrder> asks =
        createOrders(currencyPair, OrderType.ASK, asksWrapper.getBitcoindeOrders());
    final List<LimitOrder> bids =
        createOrders(currencyPair, OrderType.BID, bidsWrapper.getBitcoindeOrders());

    Collections.sort(asks);
    Collections.sort(bids);

    return new OrderBook(null, asks, bids);
  }

  /** Create a list of orders from a list of asks or bids. */
  public static List<LimitOrder> createCompactOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, BitcoindeCompactOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeCompactOrder order : orders) {
      limitOrders.add(
          new LimitOrder(orderType, order.getAmount(), currencyPair, null, null, order.getPrice()));
    }

    return limitOrders;
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeOrder[] orders) {
    final List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitcoindeOrder order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, order.orderId, null));
    }

    return limitOrders;
  }

  /** Create an individual order. */
  public static LimitOrder createOrder(
      CurrencyPair currencyPair,
      BitcoindeOrder bitcoindeOrder,
      Order.OrderType orderType,
      String orderId,
      Date timeStamp) {
    final LimitOrder limitOrder =
        new LimitOrder(
            orderType,
            bitcoindeOrder.maxAmount,
            currencyPair,
            orderId,
            timeStamp,
            bitcoindeOrder.price);

    limitOrder.addOrderFlag(
        new OrderQuantities(
            bitcoindeOrder.minAmount,
            bitcoindeOrder.maxAmount,
            bitcoindeOrder.minVolume,
            bitcoindeOrder.maxVolume));
    if (bitcoindeOrder.tradingPartnerInformation != null) {
      final TradingPartnerInformation tpi =
          new TradingPartnerInformation(
              bitcoindeOrder.tradingPartnerInformation.userName,
              bitcoindeOrder.tradingPartnerInformation.isKycFull,
              TrustLevel.valueOf(
                  bitcoindeOrder.tradingPartnerInformation.trustLevel.toUpperCase()));
      tpi.setRating(bitcoindeOrder.tradingPartnerInformation.rating);
      tpi.setNumberOfTrades(bitcoindeOrder.tradingPartnerInformation.amountTrades);
      tpi.setBankName(bitcoindeOrder.tradingPartnerInformation.bankName);
      tpi.setBic(bitcoindeOrder.tradingPartnerInformation.bic);
      tpi.setSeatOfBank(bitcoindeOrder.tradingPartnerInformation.seatOfBank);
      limitOrder.addOrderFlag(tpi);
    }
    if (bitcoindeOrder.orderRequirements != null) {
      final OrderRequirements or =
          new OrderRequirements(
              TrustLevel.valueOf(bitcoindeOrder.orderRequirements.minTrustLevel.toUpperCase()));
      or.setOnlyFullyIdentified(bitcoindeOrder.orderRequirements.onlyKycFull);
      or.setPaymentOption(bitcoindeOrder.orderRequirements.paymentOption);
      or.setSeatsOfBank(bitcoindeOrder.orderRequirements.seatOfBank);
      limitOrder.addOrderFlag(or);
    }

    return limitOrder;
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeAccount object to an AccountInfo
   * object.
   *
   * @param bitcoindeAccount
   * @return
   */
  public static AccountInfo adaptAccountInfo(BitcoindeAccountWrapper bitcoindeAccount) {
    // This adapter is not complete yet
    final BitcoindeBalances balances = bitcoindeAccount.getData().getBalances();
    final BitcoindeFidorReservation fidorReservation =
        bitcoindeAccount.getData().getFidorReservation();
    final Balance eurBalance =
        new Balance(
            Currency.EUR,
            fidorReservation != null ? fidorReservation.getAvailableAmount() : BigDecimal.ZERO);
    final Balance btcBalance = new Balance(Currency.BTC, balances.btc.getAvailableAmount());
    final Balance ethBalance = new Balance(Currency.ETH, balances.eth.getAvailableAmount());
    final Balance bchBalance = new Balance(Currency.BCH, balances.bch.getAvailableAmount());
    final Balance bsvBalance =
        new Balance(Currency.getInstance("BSV"), balances.bsv.getAvailableAmount());
    final Balance btgBalance = new Balance(Currency.BTG, balances.btg.getAvailableAmount());

    return new AccountInfo(
        Wallet.Builder.from(
                Arrays.asList(
                    btcBalance, ethBalance, bchBalance, bsvBalance, btgBalance, eurBalance))
            .build());
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   *
   * @param bitcoindeTradesWrapper Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(
      BitcoindeTradesWrapper bitcoindeTradesWrapper, CurrencyPair currencyPair) {
    final List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;

    for (BitcoindeTrade bitcoindeTrade : bitcoindeTradesWrapper.getTrades()) {
      final long tid = bitcoindeTrade.getTid();

      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(
          new Trade.Builder()
              .originalAmount(bitcoindeTrade.getAmount())
              .currencyPair(currencyPair)
              .price(bitcoindeTrade.getPrice())
              .timestamp(DateUtils.fromMillisUtc(bitcoindeTrade.getDate() * 1000L))
              .id(String.valueOf(tid))
              .build());
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * @param bitcoindeOpenOrdersWrapper
   * @return
   */
  public static OpenOrders adaptOpenOrders(
      BitcoindeMyOpenOrdersWrapper bitcoindeOpenOrdersWrapper) {
    final List<LimitOrder> orders = new ArrayList<>();

    for (BitcoindeMyOrder bitcoindeMyOrder : bitcoindeOpenOrdersWrapper.orders) {
      final CurrencyPair tradingPair =
          CurrencyPairDeserializer.getCurrencyPairFromString(bitcoindeMyOrder.tradingPair);
      final Date timestamp = fromRfc3339DateStringQuietly(bitcoindeMyOrder.createdAt);
      final OrderType otype = "buy".equals(bitcoindeMyOrder.type) ? OrderType.BID : OrderType.ASK;
      final LimitOrder limitOrder =
          new LimitOrder(
              otype,
              bitcoindeMyOrder.maxAmount,
              tradingPair,
              bitcoindeMyOrder.orderId,
              timestamp,
              bitcoindeMyOrder.price);

      orders.add(limitOrder);
    }

    return new OpenOrders(orders);
  }

  private static Date fromRfc3339DateStringQuietly(String timestamp) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      return simpleDateFormat.parse(timestamp);
    } catch (ParseException e) {
      return null;
    }
  }
}
