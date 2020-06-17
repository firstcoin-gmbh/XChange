package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

public class BitcoindeTradesTest {

  private static final long FIRST_TRADE_DATE = 1500718454L;
  private static final long FIRST_TRADE_TID = 2844384L;

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeTradesTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);

    // Make sure trade values are correct
    final BitcoindeTrade[] trades = bitcoindeTradesWrapper.getTrades();

    final Date date = new Date();
    date.setTime(FIRST_TRADE_DATE * 1000);

    assertEquals(new BigDecimal("2391.48"), trades[0].getPrice());
    assertEquals(new BigDecimal("0.90000000"), trades[0].getAmount());
    assertEquals(Long.toString(FIRST_TRADE_TID), trades[0].getTid());
    assertEquals(date, trades[0].getDate());
  }
}
