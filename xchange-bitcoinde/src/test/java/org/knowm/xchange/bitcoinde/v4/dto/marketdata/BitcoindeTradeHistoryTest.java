package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapterTest;
import org.knowm.xchange.bitcoinde.v4.BitcoindePaymentOption;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTradeHistoryWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeTradeState;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BitcoindeTradeHistoryTest {

    private String createdAtString = "2015-01-10T15:00:00+02:00";
    private String finishedAtString = "2015-01-10T15:00:00+02:00";

    @Test
    public void testBitcoindeTradeHistory() throws IOException, ParseException {

        final InputStream is =
                BitcoindeAdapterTest.class.getResourceAsStream(
                        "/org/knowm/xchange/bitcoinde/v4/dto/tradeHistory.json");

        // Use Jackson to parse it
        final ObjectMapper mapper = new ObjectMapper();
        final BitcoindeTradeHistoryWrapper bitcoindeTradeHistoryWrapper =
                mapper.readValue(is, BitcoindeTradeHistoryWrapper.class);

        List<BitcoindeTradeHistoryTrade> trades = bitcoindeTradeHistoryWrapper.getTrades();

        BitcoindeTradeHistoryTrade firstTrade = trades.get(0);

        assertEquals("2EDYNS", firstTrade.getTid());

        assertEquals( false, firstTrade.getIsExternalWalletTrade());
        assertEquals(BitcoindeType.SELL, firstTrade.getType());
        assertEquals(new BigDecimal("0.5"), firstTrade.getAmount());
        assertEquals(new BigDecimal("250.55"), firstTrade.getPrice());
        assertEquals(new BigDecimal("125.28"), firstTrade.getVolume());
        assertEquals(new BigDecimal("124.68"), firstTrade.getVolumeAfterFee());
        assertEquals(new BigDecimal("0.4975"), firstTrade.getAmountAfterFee());
        assertEquals(new BigDecimal("0.6"), firstTrade.getFeeToPay());
        assertEquals(new BigDecimal("0.0025"), firstTrade.getFeeToTrade());
        assertEquals("C4Y8HD", firstTrade.getNewTradeIdForRemainingAmount());
        assertEquals("positive", firstTrade.getRating());

        assertNotNull(firstTrade.getTradingPartnerInformation());

        assertEquals(BitcoindePaymentOption.SEPA_ONLY, firstTrade.getPaymentMethod());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date createdAtDate = simpleDateFormat.parse(createdAtString);
        Date finishedAtDate = simpleDateFormat.parse(finishedAtString);
        assertNotNull(createdAtDate);
        assertNotNull(finishedAtDate);
        assertEquals(createdAtDate, firstTrade.getCreatedAt());
        assertEquals(finishedAtDate, firstTrade.getFinishedAt());

        assertEquals(new Integer(2), bitcoindeTradeHistoryWrapper.getPage().current);
        assertEquals(new Integer(4), bitcoindeTradeHistoryWrapper.getPage().last);

        //assertEquals(BitcoindeTradeState.SUCCESSFUL, firstTrade.getState());
        //assertEquals("btceur", firstTrade.getCurrencyPair());
        //assertEquals("btceur", BitcoindeUtils.createBitcoindePair(firstTrade.getCurrencyPair()));
    }
}
