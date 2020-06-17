package org.knowm.xchange.bitcoinde.v4;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.knowm.xchange.bitcoinde.*;
import org.knowm.xchange.bitcoinde.v4.dto.account.*;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrder;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrder;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.*;
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
              bitcoindeOrder.tradingPartnerInformation.getUsername(),
              bitcoindeOrder.tradingPartnerInformation.isKycFull(),
              TrustLevel.valueOf(
                  bitcoindeOrder.tradingPartnerInformation.getTrustLevel().toString().toUpperCase()));
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
   *
   * @param bitcoindeAccount
   * @return AccountInfo
   */
  public static AccountInfo adaptAccountInfo(BitcoindeAccountWrapper bitcoindeAccount) {
    final BitcoindeData data = bitcoindeAccount.getData();
    final BitcoindeBalances balances = data.getBalances();
    final Balance btcBalance = new Balance(Currency.BTC, balances.btc.getAvailableAmount());
    final Balance ethBalance = new Balance(Currency.ETH, balances.eth.getAvailableAmount());
    final Balance bchBalance = new Balance(Currency.BCH, balances.bch.getAvailableAmount());
    final Balance bsvBalance =
        new Balance(Currency.getInstance("BSV"), balances.bsv.getAvailableAmount());
    final Balance btgBalance = new Balance(Currency.BTG, balances.btg.getAvailableAmount());

    final BitcoindeFidorReservation fidorReservation =
            bitcoindeAccount.getData().getFidorReservation();
    final Balance btcReservation;
    final Balance ethReservation;
    final Balance bchReservation;
    if (fidorReservation == null) {
      btcReservation = new Balance(Currency.EUR, BigDecimal.ZERO);
      ethReservation = new Balance(Currency.EUR, BigDecimal.ZERO);
      bchReservation = new Balance(Currency.EUR, BigDecimal.ZERO);
    }
    else {
      final int btcAvailableReservationAmount = fidorReservation.getAllocation().getBtc().getMaxEurVolume()
              - fidorReservation.getAllocation().getBtc().getEurVolumeOpenOrders();
      final int ethAvailableReservationAmount = fidorReservation.getAllocation().getEth().getMaxEurVolume()
              - fidorReservation.getAllocation().getBtc().getEurVolumeOpenOrders();
      final int bchAvailableReservationAmount = fidorReservation.getAllocation().getBch().getMaxEurVolume()
              - fidorReservation.getAllocation().getBtc().getEurVolumeOpenOrders();

      btcReservation = new Balance(
              Currency.EUR, new BigDecimal(btcAvailableReservationAmount));
      ethReservation = new Balance(
              Currency.EUR, new BigDecimal(ethAvailableReservationAmount));
      bchReservation = new Balance(
              Currency.EUR, new BigDecimal(bchAvailableReservationAmount));
    }

    return new AccountInfo(
        Wallet.Builder.from(
                Arrays.asList(
                    btcBalance, btcReservation)).id("BTC")
            .build(),
        Wallet.Builder.from(
                Arrays.asList(
                    ethBalance, ethReservation)).id("ETH")
                .build(),
        Wallet.Builder.from(
                Arrays.asList(
                    bchBalance, bchReservation)).id("BCH")
            .build(),
        // For BSV and BTG, reservation details are not available:
        Wallet.Builder.from(
                Arrays.asList(
                    bsvBalance, new Balance(Currency.EUR, BigDecimal.ZERO))).id("BSV")
            .build(),
        Wallet.Builder.from(
                Arrays.asList(
                    btgBalance, new Balance(Currency.EUR, BigDecimal.ZERO))).id("BTG")
            .build()
        );
  }

  /**
   * Helper function splitting a BitcoindeAccountLedgerWrapper into fees and other ledger entries and then
   * calling the main adaptFundingHistory method.
   * @param bitcoindeAccountLedgerWrapper
   * @return funding history as a list of funding records
   */
  public static List<FundingRecord> adaptFundingHistory(BitcoindeAccountLedgerWrapper bitcoindeAccountLedgerWrapper) {
    return adaptFundingHistory(bitcoindeAccountLedgerWrapper.splitAccountLedger());
  }

  /**
   * Adapt a BitcoindeSplitAccountLedgerWrapper into a list of funding records.
   * @param bitcoindeAccountLedgerWrapper created through BitcoindeAccountLedgerWrapper.splitAccountLedger()
   * @return funding history as a list of funding records
   */
  public static List<FundingRecord> adaptFundingHistory(
          BitcoindeAccountLedgerWrapper.BitcoindeSplitAccountLedgerWrapper bitcoindeAccountLedgerWrapper) {
    LinkedList<BitcoindeAccountLedger> accountLedgerEntries = bitcoindeAccountLedgerWrapper.getAccountLedgerEntries();
    LinkedList<BigDecimal> accountLedgerFees = bitcoindeAccountLedgerWrapper.getAccountLedgerFees();

    List<FundingRecord> result = new ArrayList<>((int)0.5*accountLedgerEntries.size());
    while (!accountLedgerEntries.isEmpty()) {
      BitcoindeAccountLedger accountLedgerEntry = accountLedgerEntries.pop();
      String reference = accountLedgerEntry.getReference();
      if (accountLedgerEntry.getTrade() != null) {
        // The entry is a trade and, thus, does not belong to the funding history
        continue;
      }

      Date date;
      BigDecimal fee = null;
      final Currency currency = Currency.BTC;
      FundingRecord.Type fundingRecordType;
      BigDecimal balance = null;
      BigDecimal cashFlow;
      String internalId = null;
      String blockchainTransactionHash = null;

      BitcoindeLedgerType type = BitcoindeLedgerType.fromString(accountLedgerEntry.getType());
      if (type == BitcoindeLedgerType.PAYOUT) {
        if (accountLedgerFees.isEmpty()) {
          // This block is only needed if the API returns payout entries prior to their respective fee entries
          /*
          // Need to re-query for fees
          accountLedgerEntries.addFirst(accountLedgerEntry);
          break;
           */
        }
        else {
          fee = accountLedgerFees.pop();
        }
      }

      fundingRecordType = type.toFundingRecordType();
      cashFlow = accountLedgerEntry.getCashflow();
      try {
        date = BitcoindeUtils.fromRfc3339Timestamp(accountLedgerEntry.getDate());
      } catch (ParseException e) {
        e.printStackTrace();
        date = null;
      }

      if (fundingRecordType == FundingRecord.Type.INTERNAL_DEPOSIT ||
              fundingRecordType == FundingRecord.Type.INTERNAL_WITHDRAWAL) {
        internalId = reference;
      }
      else if (fundingRecordType == FundingRecord.Type.DEPOSIT ||
              fundingRecordType == FundingRecord.Type.WITHDRAWAL) {
        blockchainTransactionHash = reference;
      }

      if (balance == null) {
        balance = accountLedgerEntry.getBalance();
      }

      final FundingRecord fundingRecord = new FundingRecord(
              null,
              date,
              currency,
              cashFlow,
              internalId,
              blockchainTransactionHash,
              fundingRecordType,
              (FundingRecord.Status) null,
              balance,
              fee,
              null);
      result.add(fundingRecord);
    }

    return result;
  }

  /**
   * Helper function for adapting a BitcoindeMyTradesWrapper into a list of trades
   * sorting them with respect to their timestamps.
   * @param bitcoindeMyTradesWrapper
   * @return the list of trades parsed from the API without any modifications
   */
  public static UserTrades adaptTradeHistory(BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper) {
    return adaptTradeHistory(bitcoindeMyTradesWrapper, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapt a BitcoindeMyTradesWrapper into a list of trades.
   * @param bitcoindeMyTradesWrapper
   * @param sortType Sort the trades with respect to their IDs or timestamps.
   * @return UserTrades
   */
  public static UserTrades adaptTradeHistory(BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper,
                                             TradeSortType sortType) {
    List<BitcoindeMyTrade> trades = bitcoindeMyTradesWrapper.getTrades();

    List<UserTrade> result = new ArrayList<>(trades.size());
    for (BitcoindeMyTrade trade: trades) {
      OrderType type;
      BigDecimal fee;
      Currency feeCurrency;
      if (trade.getType() == BitcoindeMyTrade.Type.BUY) {
        type = OrderType.BID;
        fee = trade.getFeeCurrencyToTrade();
        feeCurrency = trade.getTradingPair().base;
      }
      else if (trade.getType() == BitcoindeMyTrade.Type.SELL) {
        type = OrderType.ASK;
        fee = trade.getFeeCurrencyToPay();
        feeCurrency = trade.getTradingPair().counter;
      }
      else {
        throw new TypeNotPresentException(trade.getType().toString(), null);
      }

      Date timestamp;
      try {
        timestamp = BitcoindeUtils.fromRfc3339Timestamp(trade.getSuccessfullyFinishedAt());
      }
      catch (ParseException e) {
        timestamp = null;
      }

      UserTrade newTrade = new UserTrade(type, trade.getAmountCurrencyToTrade(), trade.getTradingPair(),
              trade.getPrice(), timestamp, trade.getTradeId(), null, fee, feeCurrency, null);
      result.add(newTrade);
    }

    return new UserTrades(result, sortType);
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
