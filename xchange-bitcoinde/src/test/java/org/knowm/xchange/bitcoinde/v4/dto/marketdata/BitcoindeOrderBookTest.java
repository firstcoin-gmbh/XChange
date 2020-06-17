package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class BitcoindeOrderBookTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);
    final BitcoindeOrder[] orders = bitcoindeOrderBook.getBitcoindeOrders();

    assertEquals(1, orders.length);
    assertEquals("A1B2D3", orders[0].orderId);
    assertEquals("btceur", orders[0].tradingPair);
    assertEquals("buy", orders[0].type);
    assertEquals(false, orders[0].externalWalletOrder);
    assertEquals(new BigDecimal("0.1"), orders[0].minAmount);
    assertEquals(new BigDecimal("0.5"), orders[0].maxAmount);
    assertEquals(new BigDecimal("230.55"), orders[0].price);
    assertEquals(new BigDecimal("23.06"), orders[0].minVolume);
    assertEquals(new BigDecimal("115.28"), orders[0].maxVolume);
    assertEquals(true, orders[0].requirementsFullfilled);
    assertNotNull(orders[0].tradingPartnerInformation);
    assertEquals("bla", orders[0].tradingPartnerInformation.getUserName());
    assertEquals("gold", orders[0].tradingPartnerInformation.getTrustLevel());
    assertEquals(new Integer(99), orders[0].tradingPartnerInformation.getRating());
    assertEquals(true, orders[0].tradingPartnerInformation.getIsKycFull());
    assertEquals("Sparkasse", orders[0].tradingPartnerInformation.getBankName());
    assertEquals("HASPDEHHXXX", orders[0].tradingPartnerInformation.getBic());
    assertEquals("DE", orders[0].tradingPartnerInformation.getSeatOfBank());
    assertEquals(new Integer(52), orders[0].tradingPartnerInformation.getAmountTrades());
    assertNotNull(orders[0].orderRequirements);
    assertEquals("gold", orders[0].orderRequirements.minTrustLevel);
    assertEquals(true, orders[0].orderRequirements.onlyKycFull);
    assertEquals(new Integer(1), orders[0].orderRequirements.paymentOption);
    assertArrayEquals(new String[] {"DE", "NL"}, orders[0].orderRequirements.seatOfBank);
  }
}
