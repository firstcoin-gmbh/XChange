package org.knowm.xchange.bitcoinde.v4.dto.account;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

/** @author kaiserfr */
public class BitcoindeAccountTest {

  @Test
  public void testBitcoindeOrderBook()
      throws JsonParseException, JsonMappingException, IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/account.json");

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

    assertEquals(new BigDecimal("0.009"), btcBalance);
    assertEquals(new BigDecimal("0.008"), bchBalance);
    assertEquals(new BigDecimal("0.007"), btgBalance);
    assertEquals(new BigDecimal("0.006"), bsvBalance);
    assertEquals(new BigDecimal("0.06463044"), ethBalance);
    assertEquals(new BigDecimal("2000"), reservedAmount);
  }
}
