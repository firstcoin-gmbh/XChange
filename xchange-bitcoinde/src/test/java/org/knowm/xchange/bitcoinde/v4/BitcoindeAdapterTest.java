package org.knowm.xchange.bitcoinde.v4;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.OrderQuantities;
import org.knowm.xchange.bitcoinde.OrderRequirements;
import org.knowm.xchange.bitcoinde.TradingPartnerInformation;
import org.knowm.xchange.bitcoinde.TrustLevel;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeCompactOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

/** @author matthewdowney */
public class BitcoindeAdapterTest {

  @Test
  public void testCompactOrderBookAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/compact_orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeCompactOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeCompactOrderbookWrapper.class);

    // Create a generic OrderBook object from a Bitcoinde specific OrderBook
    final OrderBook orderBook =
        BitcoindeAdapters.adaptCompactOrderBook(bitcoindeOrderBook, CurrencyPair.BTC_EUR);
    final LimitOrder firstBid = orderBook.getBids().get(0);

    // verify all fields are filled correctly
    assertThat(firstBid.getLimitPrice()).isEqualTo(new BigDecimal("2406.11"));
    assertThat(firstBid.getType()).isEqualTo(OrderType.BID);
    assertThat(firstBid.getOriginalAmount()).isEqualTo(new BigDecimal("1.745"));
    assertThat(firstBid.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);
  }

  @Test
  public void testOrderBookAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);

    // Create a generic OrderBook object from a Bitcoinde specific OrderBook
    final OrderBook orderBook =
        BitcoindeAdapters.adaptOrderBook(
            bitcoindeOrderBook, bitcoindeOrderBook, CurrencyPair.BCH_EUR);
    final LimitOrder firstBid = orderBook.getBids().get(0);
    final TradingPartnerInformation tpi =
        new TradingPartnerInformation("bla", true, TrustLevel.GOLD);
    final OrderRequirements or = new OrderRequirements(TrustLevel.GOLD);
    final OrderQuantities oqty =
        new OrderQuantities(
            new BigDecimal("0.1"),
            new BigDecimal("0.5"),
            new BigDecimal("23.06"),
            new BigDecimal("115.28"));

    // verify all fields are filled correctly
    assertThat(firstBid.getLimitPrice()).isEqualTo(new BigDecimal("230.55"));
    assertThat(firstBid.getType()).isEqualTo(OrderType.BID);
    assertThat(firstBid.getOriginalAmount()).isEqualTo(new BigDecimal("0.5"));
    assertThat(firstBid.getCurrencyPair()).isEqualTo(CurrencyPair.BCH_EUR);
    assertThat(firstBid.hasFlag(tpi)).isEqualTo(true);
    assertThat(firstBid.hasFlag(or)).isEqualTo(true);
    assertThat(firstBid.hasFlag(oqty)).isEqualTo(true);
    testTradingPartnerInformation(firstBid, tpi);
    testOrderRequirements(firstBid, or);
    testOrderQuantities(firstBid, oqty);
  }

  private void testTradingPartnerInformation(LimitOrder order, TradingPartnerInformation tpi) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof TradingPartnerInformation)
        .forEach(
            flag -> {
              assertEquals(tpi.getUserName(), ((TradingPartnerInformation) flag).getUserName());
              assertEquals(
                  tpi.isFullyIdentified(), ((TradingPartnerInformation) flag).isFullyIdentified());
              assertEquals(tpi.getTrustLevel(), ((TradingPartnerInformation) flag).getTrustLevel());
            });
  }

  private void testOrderRequirements(LimitOrder order, OrderRequirements or) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof OrderRequirements)
        .forEach(
            flag -> {
              assertEquals(or.getMinTrustLevel(), ((OrderRequirements) flag).getMinTrustLevel());
            });
  }

  private void testOrderQuantities(LimitOrder order, OrderQuantities qty) {
    order.getOrderFlags().stream()
        .filter(flag -> flag instanceof OrderQuantities)
        .forEach(
            flag -> {
              assertEquals(
                  qty.getMinAmountToTrade(), ((OrderQuantities) flag).getMinAmountToTrade());
              assertEquals(
                  qty.getMaxAmountToTrade(), ((OrderQuantities) flag).getMaxAmountToTrade());
              assertEquals(qty.getMinVolumeToPay(), ((OrderQuantities) flag).getMinVolumeToPay());
              assertEquals(qty.getMaxVolumeToPay(), ((OrderQuantities) flag).getMaxVolumeToPay());
            });
  }

  @Test
  public void testTradesAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAdapterTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);

    // Use our adapter to get a generic Trades object from a
    // BitcoindeTrade[] object
    final Trades trades =
        BitcoindeAdapters.adaptTrades(bitcoindeTradesWrapper, CurrencyPair.BTC_EUR);
    final Trade firstTrade = trades.getTrades().get(0);

    // Make sure the adapter got all the data
    assertThat(trades.getTrades().size()).isEqualTo(92);
    assertThat(trades.getlastID()).isEqualTo(2844384);

    // Verify that all fields are filled
    assertThat(firstTrade.getId()).isEqualTo("2844111");
    assertThat(firstTrade.getPrice()).isEqualTo(new BigDecimal("2395"));
    assertThat(firstTrade.getOriginalAmount()).isEqualTo(new BigDecimal("0.08064516"));
    assertThat(firstTrade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_EUR);

    // Verify that the date is correct
    final Date date = new Date();
    date.setTime(1500717160L * 1000); // Create the expected date for trade
    // 0
    assertThat(firstTrade.getTimestamp()).isEqualTo(date); // Make
    // sure
    // the
    // dates
    // match
  }
}
