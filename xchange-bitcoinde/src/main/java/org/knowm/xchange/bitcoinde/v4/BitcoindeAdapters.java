package org.knowm.xchange.bitcoinde.v4;

import java.awt.font.FontRenderContext;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.knowm.xchange.bitcoinde.*;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeFundingHistoryWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTradeHistoryWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.*;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.*;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
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
              bitcoindeOrder.tradingPartnerInformation.getUserName(),
              bitcoindeOrder.tradingPartnerInformation.getIsKycFull(),
              TrustLevel.valueOf(
                  bitcoindeOrder.tradingPartnerInformation.getTrustLevel().toUpperCase()));
      tpi.setRating(bitcoindeOrder.tradingPartnerInformation.getRating());
      tpi.setNumberOfTrades(bitcoindeOrder.tradingPartnerInformation.getAmountTrades());
      tpi.setBankName(bitcoindeOrder.tradingPartnerInformation.getBankName());
      tpi.setBic(bitcoindeOrder.tradingPartnerInformation.getBic());
      tpi.setSeatOfBank(bitcoindeOrder.tradingPartnerInformation.getSeatOfBank());
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
   * @param bitcoindeAccount
   * @return
   */
  public static AccountInfo adaptAccountInfo(BitcoindeAccountWrapper bitcoindeAccount) {
    final BitcoindeBalances balances = bitcoindeAccount.getData().getBalances();

    final Balance btcBalance = new Balance(Currency.BTC,
            balances.btc.getTotalAmount(),
            balances.btc.getAvailableAmount(),
            balances.btc.getReservedAmount());

    final Balance bchBalance = new Balance(Currency.BCH,
            balances.bch.getTotalAmount(),
            balances.bch.getAvailableAmount(),
            balances.bch.getReservedAmount());

    final Balance btgBalance = new Balance(Currency.BTG,
            balances.btg.getTotalAmount(),
            balances.btg.getAvailableAmount(),
            balances.btg.getReservedAmount());

    final Balance bsvBalance = new Balance(Currency.getInstance("BSV"),
            balances.bsv.getTotalAmount(),
            balances.bsv.getAvailableAmount(),
            balances.bsv.getReservedAmount());

    final Balance ethBalance = new Balance(Currency.ETH,
            balances.eth.getTotalAmount(),
            balances.eth.getAvailableAmount(),
            balances.eth.getReservedAmount());

    final BitcoindeFidorReservation fidorReservation =
            bitcoindeAccount.getData().getFidorReservation();

    final Balance eurBalance = new Balance(Currency.EUR,
            fidorReservation.getTotalAmount(),
            fidorReservation.getAvailableAmount());

    return new AccountInfo(
            Wallet.Builder.from(
                    Arrays.asList(
                            btcBalance, ethBalance, bchBalance, bsvBalance, btgBalance, eurBalance))
                    .build());
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeFundingHistoryWrapper object to a List<FundingRecord>
   * object.
   * @return
   */
  public static List<FundingRecord> adaptFundingHistory(BitcoindeFundingHistoryWrapper bitcoindeFundingHistoryWrapper,
                                                        CurrencyPair currencyPair)
  {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    BigDecimal fee = null;

    Map<List<BitcoindeAccountLedger>, BigDecimal> map =
            bitcoindeFundingHistoryWrapper.mapLedger();

    for(Map.Entry<List<BitcoindeAccountLedger>, BigDecimal> mapEntry : map.entrySet()){
      for (BitcoindeAccountLedger ledger : mapEntry.getKey()){
        if (ledger.getType() == BitcoindeType.PAYOUT){
          fee = mapEntry.getValue();
        }

        fundingRecords.add(new FundingRecord.Builder()
                //.setAddress(ledger.)
                .setDate(ledger.getDate())
                .setCurrency(currencyPair.base)
                .setAmount(ledger.getCashflow())
                .setBalance(ledger.getBalance())
                .setType(adaptFundingType(ledger.getType()))
                .setBlockchainTransactionHash(ledger.getReference())
                .setInternalId(ledger.getReference())
                //.setStatus(led.get)
                .setFee(fee)
                .build());
      }
    }

    return fundingRecords;
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradeHistoryWrapper object to a List<UserTrade>
   * object.
   * @return
   */
  public static UserTrades adaptTradeHistory(BitcoindeTradeHistoryWrapper bitcoindeTradeHistoryWrapper,
                                             CurrencyPair currencyPair,
                                              TradeSortType tradeSortType) {
    List<UserTrade> trades = new ArrayList<>();

    for (BitcoindeTradeHistoryTrade trade : bitcoindeTradeHistoryWrapper.getTrades()) {

      BigDecimal fee;

      switch (trade.getType()){
        case BUY:
          fee = trade.getFeeCurrencyToTrade();
          break;
        case SELL:
          fee = trade.getFeeCurrencyToPay();
          break;
        default:
          throw new TypeNotPresentException(trade.getType().toString(), null);
      }

      trades.add(new UserTrade.Builder()
              .type(adaptOrderType(trade.getType()))
              .originalAmount(trade.getAmount())
              .currencyPair(currencyPair)
              .price(trade.getPrice())
              .timestamp(trade.getCreatedAt())
              .id(trade.getTradeId())
              //.orderId()
              //.orderUserReference(trade.getNewTradeIdForRemainingAmount())
              .feeAmount(fee)
              .feeCurrency(currencyPair.base)
              .build());
    }

    return new UserTrades(trades, tradeSortType);
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradeHistoryWrapper object to a OrderType
   * object.
   * @return
   */
  public static OrderType adaptOrderType(BitcoindeType bitcoindeType) {
    return bitcoindeType.equals(BitcoindeType.BUY) ? OrderType.BID : OrderType.ASK;
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeType object to a FundingRecord.Type
   * object.
   * @return
   */
    public static FundingRecord.Type adaptFundingType(BitcoindeType bitcoindeType) {
      return bitcoindeType.equals(BitcoindeType.BUY) ? FundingRecord.Type.WITHDRAWAL : FundingRecord.Type.DEPOSIT;
    }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   *
   * @param bitcoindeTradesWrapper Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(
      BitcoindeTradesWrapper bitcoindeTradesWrapper,
      CurrencyPair currencyPair,
      TradeSortType sortType) {
    final List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;

    for (BitcoindeTrade bitcoindeTrade : bitcoindeTradesWrapper.getTrades()) {

      final long tid = Long.valueOf(bitcoindeTrade.getTradeId());

      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(
          new Trade.Builder()
              .originalAmount(bitcoindeTrade.getAmount())
              .currencyPair(currencyPair)
              .price(bitcoindeTrade.getPrice())
              .timestamp(bitcoindeTrade.getDate())
              .id(bitcoindeTrade.getTradeId())
              .build());
    }

    return new Trades(trades, lastTradeId, sortType);
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
