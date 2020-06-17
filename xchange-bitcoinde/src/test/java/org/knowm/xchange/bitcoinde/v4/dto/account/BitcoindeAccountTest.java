package org.knowm.xchange.bitcoinde.v4.dto.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.FundingRecord;

/** @author kaiserfr */
public class BitcoindeAccountTest {

  /*
  Tolerance for asserting double values.
  Some of them may be parsed as integers, which requires a tolerance of at least 1.0.
   */
  private final static double DOUBLE_TOLERANCE = 1.0;

  @Test
  public void testBitcoindeOrderBook()
          throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
            BitcoindeAccountTest.class.getResourceAsStream(
                    "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountInfo.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeAccountWrapper bitcoindeTradesWrapper =
            mapper.readValue(is, BitcoindeAccountWrapper.class);

    // Make sure trade values are correct
    final BitcoindeBalances balances = bitcoindeTradesWrapper.getData().getBalances();
    final BigDecimal btcBalance = balances.btc.getAvailableAmount();
    final BigDecimal ethBalance = balances.eth.getAvailableAmount();
    final BigDecimal bchBalance = balances.bch.getAvailableAmount();
    final BigDecimal btgBalance = balances.btg.getAvailableAmount();
    final BigDecimal bsvBalance = balances.bsv.getAvailableAmount();
    final BitcoindeFidorReservation fidorReservation =
            bitcoindeTradesWrapper.getData().getFidorReservation();
    final BigDecimal reservedAmount = fidorReservation.getAvailableAmount();
    BitcoindeAllocations allocations = fidorReservation.getAllocation();

    final double btcReservedAmountAvailable = allocations.getBtc().getMaxEurVolume()
            - allocations.getBtc().getEurVolumeOpenOrders();
    final double ethReservedAmountAvailable = allocations.getEth().getMaxEurVolume()
            - allocations.getEth().getEurVolumeOpenOrders();
    final double bchReservedAmountAvailable = allocations.getBch().getMaxEurVolume()
            - allocations.getBch().getEurVolumeOpenOrders();

    assertThat(btcBalance).isEqualTo(new BigDecimal("6.07848416"));
    assertThat(bchBalance).isEqualTo(new BigDecimal("40.27951459"));
    assertThat(btgBalance).isEqualTo(new BigDecimal("0"));
    assertThat(bsvBalance).isEqualTo(new BigDecimal("4.85"));
    assertThat(ethBalance).isEqualTo(new BigDecimal("23.23876897"));
    assertThat(reservedAmount).isEqualTo(new BigDecimal("23828.14"));
    assertThat(btcReservedAmountAvailable).isCloseTo(11914.07 - 0, within(DOUBLE_TOLERANCE));
    assertThat(ethReservedAmountAvailable).isEqualTo(11914.07 - 10118.14, within(DOUBLE_TOLERANCE));
    assertThat(bchReservedAmountAvailable).isEqualTo(0 - 0, within(DOUBLE_TOLERANCE));
  }

  @Test
  public void testBitcoindeAccountLedger()
          throws JsonParseException, JsonMappingException, IOException {
    // Read in each AccountLedger JSON from the example resources
    InputStream is =
            BitcoindeAccountTest.class.getResourceAsStream(
                    "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountLedger-BTC-Inpayment.json");

    ObjectMapper mapper = new ObjectMapper();
    final BitcoindeAccountLedger bitcoindeAccountLedgerInpayment =
            mapper.readValue(is, BitcoindeAccountLedger.class);

    assertThat(bitcoindeAccountLedgerInpayment.getDate()).isEqualTo("2020-04-21T14:44:05+02:00");
    assertThat(bitcoindeAccountLedgerInpayment.getType()).isEqualTo("inpayment");
    assertThat(bitcoindeAccountLedgerInpayment.getReference())
            .isEqualTo("f1b7df81f5aa4c4ea6f4661596cb8718d263c8395cd99586739df5e3805235fa");
    assertThat(bitcoindeAccountLedgerInpayment.getCashflow().doubleValue())
            .isCloseTo(2.29550000, within(DOUBLE_TOLERANCE));
    assertThat(bitcoindeAccountLedgerInpayment.getBalance().doubleValue())
            .isCloseTo(13.71210682, within(DOUBLE_TOLERANCE));

    is = BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountLedger-BTC-Kickback.json");

    mapper = new ObjectMapper();
    final BitcoindeAccountLedger bitcoindeAccountLedgerKickback =
            mapper.readValue(is, BitcoindeAccountLedger.class);

    assertThat(bitcoindeAccountLedgerKickback.getDate()).isEqualTo("2020-02-14T03:30:02+01:00");
    assertThat(bitcoindeAccountLedgerKickback.getType()).isEqualTo("kickback");
    assertThat(bitcoindeAccountLedgerKickback.getReference())
            .isEqualTo("Kickback");
    assertThat(bitcoindeAccountLedgerKickback.getCashflow().doubleValue())
            .isCloseTo(0.04138717, within(DOUBLE_TOLERANCE));
    assertThat(bitcoindeAccountLedgerKickback.getBalance().doubleValue())
            .isCloseTo(136.21036662, within(DOUBLE_TOLERANCE));

    is = BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountLedger-BTC-Outgoing-Fee-Voluntary.json");

    mapper = new ObjectMapper();
    final BitcoindeAccountLedger bitcoindeAccountLedgerOutgoingFeeVoluntary =
            mapper.readValue(is, BitcoindeAccountLedger.class);

    assertThat(bitcoindeAccountLedgerOutgoingFeeVoluntary.getDate()).isEqualTo("2020-01-30T17:46:51+01:00");
    assertThat(bitcoindeAccountLedgerOutgoingFeeVoluntary.getType()).isEqualTo("outgoing_fee_voluntary");
    assertThat(bitcoindeAccountLedgerOutgoingFeeVoluntary.getReference())
            .isEqualTo("c1d29cf751ca1d0f1ba8a570b2a96f2d82566e4c06ff5062575a7abf3617a673");
    assertThat(bitcoindeAccountLedgerOutgoingFeeVoluntary.getCashflow().doubleValue())
            .isCloseTo(-0.00002300, within(DOUBLE_TOLERANCE));
    assertThat(bitcoindeAccountLedgerOutgoingFeeVoluntary.getBalance().doubleValue())
            .isCloseTo(0.81382406, within(DOUBLE_TOLERANCE));

    is = BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountLedger-BTC-Payout.json");

    mapper = new ObjectMapper();
    final BitcoindeAccountLedger bitcoindeAccountLedgerPayout =
            mapper.readValue(is, BitcoindeAccountLedger.class);

    assertThat(bitcoindeAccountLedgerPayout.getDate()).isEqualTo("2020-01-30T17:46:51+01:00");
    assertThat(bitcoindeAccountLedgerPayout.getType()).isEqualTo("payout");
    assertThat(bitcoindeAccountLedgerPayout.getReference())
            .isEqualTo("c1d29cf751ca1d0f1ba8a570b2a96f2d82566e4c06ff5062575a7abf3617a673");
    assertThat(bitcoindeAccountLedgerPayout.getCashflow().doubleValue())
            .isCloseTo(-0.30000000, within(DOUBLE_TOLERANCE));
    assertThat(bitcoindeAccountLedgerPayout.getBalance().doubleValue())
            .isCloseTo(0.81389306, within(DOUBLE_TOLERANCE));

    is = BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/AccountLedger-BTC-Trade.json");

    mapper = new ObjectMapper();
    final BitcoindeAccountLedger bitcoindeAccountLedgerTrade =
            mapper.readValue(is, BitcoindeAccountLedger.class);

    assertThat(bitcoindeAccountLedgerTrade.getDate()).isEqualTo("2020-04-21T08:51:06+02:00");
    assertThat(bitcoindeAccountLedgerTrade.getType()).isEqualTo("sell");
    assertThat(bitcoindeAccountLedgerTrade.getReference())
            .isEqualTo("2C44PC");
    assertThat(bitcoindeAccountLedgerTrade.getCashflow().doubleValue())
            .isCloseTo(-0.01200000, within(DOUBLE_TOLERANCE));
    assertThat(bitcoindeAccountLedgerTrade.getBalance().doubleValue())
            .isCloseTo(17.68650682, within(DOUBLE_TOLERANCE));
    BitcoindeAccountLedgerTrade trade = bitcoindeAccountLedgerTrade.getTrade();
    assertThat(trade.getTradeId()).isEqualTo("2C44PC");
    assertThat(trade.getPrice().doubleValue()).isCloseTo(6422.07, within(DOUBLE_TOLERANCE));
    assertThat(trade.isExternalWalletTrade()).isEqualTo(false);
    assertThat(trade.getTradingPair()).isEqualTo("btceur");
    BitcoindeAccountLedgerTradingAmount currencyToTrade = trade.getCurrencyToTrade();
    assertThat(currencyToTrade.getCurrency()).isEqualTo(Currency.BTC);
    assertThat(currencyToTrade.getBefore_fee().doubleValue())
            .isCloseTo(0.01200000, within(DOUBLE_TOLERANCE));
    assertThat(currencyToTrade.getAfter_fee().doubleValue())
            .isCloseTo(0.01188000, within(DOUBLE_TOLERANCE));
    BitcoindeAccountLedgerTradingAmount currencyToPay = trade.getCurrencyToPay();
    assertThat(currencyToPay.getCurrency()).isEqualTo(Currency.EUR);
    assertThat(currencyToPay.getBefore_fee().doubleValue())
            .isCloseTo(77.06, within(DOUBLE_TOLERANCE));
    assertThat(currencyToPay.getAfter_fee().doubleValue())
            .isCloseTo(76.67, within(DOUBLE_TOLERANCE));

    // Try creating a wrapper around the account ledger entries
    final BitcoindeAccountLedgerWrapper bitcoindeAccountLedgerWrapper = new BitcoindeAccountLedgerWrapper(
            Arrays.asList(bitcoindeAccountLedgerInpayment, bitcoindeAccountLedgerKickback,
                    bitcoindeAccountLedgerOutgoingFeeVoluntary, bitcoindeAccountLedgerPayout,
                    bitcoindeAccountLedgerTrade), null, null, null);
  }

  @Test
  public void testBitcoindeMyTrades()
          throws JsonParseException, JsonMappingException, IOException {
    // Read in Trade (Sell) JSON from the example resources
    InputStream is =
            BitcoindeAccountTest.class.getResourceAsStream(
                    "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/MyTrades-BTC-Sell.json");

    ObjectMapper mapper = new ObjectMapper();
    final BitcoindeMyTrade bitcoindeTradeSell =
            mapper.readValue(is, BitcoindeMyTrade.class);

    assertThat(bitcoindeTradeSell.getTradeId()).isEqualTo("X7GEKA");
    assertThat(bitcoindeTradeSell.getTradingPair()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(bitcoindeTradeSell.isExternalWalletTrade()).isEqualTo(false);
    assertThat(bitcoindeTradeSell.getType()).isEqualTo(BitcoindeMyTrade.Type.SELL);
    assertThat(bitcoindeTradeSell.getAmountCurrencyToTrade())
            .isEqualTo(BigDecimal.valueOf(0.2));
    assertThat(bitcoindeTradeSell.getPrice())
            .isEqualTo(BigDecimal.valueOf(6429.99));
    assertThat(bitcoindeTradeSell.getVolumeCurrencyToPay())
            .isEqualTo(BigDecimal.valueOf(1286));
    assertThat(bitcoindeTradeSell.getVolumeCurrencyToPayAfterFee())
            .isEqualTo(BigDecimal.valueOf(1279.56));
    assertThat(bitcoindeTradeSell.getAmountCurrencyToTradeAfterFee())
            .isEqualTo(BigDecimal.valueOf(0.198));
    assertThat(bitcoindeTradeSell.getFeeCurrencyToPay())
            .isEqualTo(BigDecimal.valueOf(6.43));
    assertThat(bitcoindeTradeSell.getFeeCurrencyToTrade())
            .isEqualTo(new BigDecimal("0.00200000"));
    assertThat(bitcoindeTradeSell.getCreatedAt()).isEqualTo("2020-04-21T11:08:54+02:00");
    assertThat(bitcoindeTradeSell.getSuccessfullyFinishedAt()).isEqualTo("2020-04-22T08:55:27+02:00");
    assertThat(bitcoindeTradeSell.getState()).isEqualTo(BitcoindeMyTrade.State.SUCCESSFUL);
    assertThat(bitcoindeTradeSell.isTradeMarkedAsPaid()).isEqualTo(true);
    assertThat(bitcoindeTradeSell.getTradeMarkedAsPaidAt()).isEqualTo("2020-04-21T11:46:49+02:00");
    assertThat(bitcoindeTradeSell.getPaymentMethod()).isEqualTo(BitcoindeMyTrade.PaymentMethod.SEPA);
    assertThat(bitcoindeTradeSell.getMyRatingForTradingPartner()).isEqualTo("positive");

    BitcoindeTradingPartnerInformation tradingPartnerInformationSell
            = bitcoindeTradeSell.getTradingPartnerInformation();
    assertThat(tradingPartnerInformationSell.getUsername()).isEqualTo("lofty045");
    assertThat(tradingPartnerInformationSell.isKycFull()).isEqualTo(true);
    assertThat(tradingPartnerInformationSell.getTrustLevel())
            .isEqualTo(BitcoindeTradingPartnerInformation.TrustLevel.BRONZE);
    assertThat(tradingPartnerInformationSell.getAmountTrades()).isEqualTo(0);
    assertThat(tradingPartnerInformationSell.getRating()).isEqualTo(0);
    assertThat(tradingPartnerInformationSell.getBankName()).isEqualTo("DEUTSCHE POSTBANK AG");
    assertThat(tradingPartnerInformationSell.getBic()).isEqualTo("PBNKDEFFXXX");
    assertThat(tradingPartnerInformationSell.getSeatOfBank()).isEqualTo("DE");


    // Read in Trade (Buy) JSON from the example resources
    is = BitcoindeAccountTest.class.getResourceAsStream(
                    "/org/knowm/xchange/bitcoinde/v4/dto/fcAssignment/MyTrades-ETH-Buy.json");

    mapper = new ObjectMapper();
    final BitcoindeMyTrade bitcoindeTradeBuy =
            mapper.readValue(is, BitcoindeMyTrade.class);

    assertThat(bitcoindeTradeBuy.getTradeId()).isEqualTo("8P5Z3Q");
    assertThat(bitcoindeTradeBuy.getTradingPair()).isEqualTo(CurrencyPair.ETH_EUR);
    assertThat(bitcoindeTradeBuy.isExternalWalletTrade()).isEqualTo(false);
    assertThat(bitcoindeTradeBuy.getType()).isEqualTo(BitcoindeMyTrade.Type.BUY);
    assertThat(bitcoindeTradeBuy.getAmountCurrencyToTrade())
            .isEqualTo(BigDecimal.valueOf(0.9516129));
    assertThat(bitcoindeTradeBuy.getPrice())
            .isEqualTo(BigDecimal.valueOf(155.73));
    assertThat(bitcoindeTradeBuy.getVolumeCurrencyToPay())
            .isEqualTo(BigDecimal.valueOf(148.19));
    assertThat(bitcoindeTradeBuy.getVolumeCurrencyToPayAfterFee())
            .isEqualTo(BigDecimal.valueOf(147.6));
    assertThat(bitcoindeTradeBuy.getAmountCurrencyToTradeAfterFee())
            .isEqualTo(BigDecimal.valueOf(0.944));
    assertThat(bitcoindeTradeBuy.getFeeCurrencyToPay())
            .isEqualTo(BigDecimal.valueOf(0.59));
    assertThat(bitcoindeTradeBuy.getFeeCurrencyToTrade())
            .isEqualTo(new BigDecimal("00.00761290"));
    assertThat(bitcoindeTradeBuy.getCreatedAt()).isEqualTo("2020-04-21T02:52:15+02:00");
    assertThat(bitcoindeTradeBuy.getSuccessfullyFinishedAt()).isEqualTo("2020-04-21T02:52:19+02:00");
    assertThat(bitcoindeTradeBuy.getState()).isEqualTo(BitcoindeMyTrade.State.SUCCESSFUL);
    assertThat(bitcoindeTradeBuy.isTradeMarkedAsPaid()).isEqualTo(false);
    assertThat(bitcoindeTradeBuy.getTradeMarkedAsPaidAt()).isEqualTo(null);
    assertThat(bitcoindeTradeBuy.getPaymentMethod()).isEqualTo(BitcoindeMyTrade.PaymentMethod.EXPRESS);
    assertThat(bitcoindeTradeBuy.getMyRatingForTradingPartner()).isEqualTo("positive");

    BitcoindeTradingPartnerInformation tradingPartnerInformationBuy
            = bitcoindeTradeBuy.getTradingPartnerInformation();
    assertThat(tradingPartnerInformationBuy.getUsername()).isEqualTo("Shroom4you");
    assertThat(tradingPartnerInformationBuy.isKycFull()).isEqualTo(false);
    assertThat(tradingPartnerInformationBuy.getTrustLevel())
            .isEqualTo(BitcoindeTradingPartnerInformation.TrustLevel.BRONZE);
    assertThat(tradingPartnerInformationBuy.getAmountTrades()).isEqualTo(13);
    assertThat(tradingPartnerInformationBuy.getRating()).isEqualTo(100);
    assertThat(tradingPartnerInformationBuy.getBankName()).isEqualTo("COMMERZBANK AG");
    assertThat(tradingPartnerInformationBuy.getBic()).isEqualTo("COBADEFFXXX");
    assertThat(tradingPartnerInformationBuy.getSeatOfBank()).isEqualTo("DE");

    // Try creating a wrapper around the trade entries
    final BitcoindeMyTradesWrapper bitcoindeMyTradesWrapper = new BitcoindeMyTradesWrapper(
            Arrays.asList(bitcoindeTradeSell, bitcoindeTradeBuy),
            null, null, null);
  }

}
