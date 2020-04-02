package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author matthewdowney */
public class BitcoindeCompactOrderBookTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeCompactOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/compact_orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeCompactOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeCompactOrderbookWrapper.class);
    final BitcoindeCompactOrders orders = bitcoindeOrderBook.getBitcoindeOrders();

    // Make sure asks are correct
    assertEquals(new BigDecimal("2461.61"), orders.getAsks()[0].getPrice());
    assertEquals(new BigDecimal("0.0406218"), orders.getAsks()[0].getAmount());

    // Make sure bids are correct
    assertEquals(new BigDecimal("1200"), orders.getBids()[0].getPrice());
    assertEquals(new BigDecimal("8.333"), orders.getBids()[0].getAmount());
  }
}
