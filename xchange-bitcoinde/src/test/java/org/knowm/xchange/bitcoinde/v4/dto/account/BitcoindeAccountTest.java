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
    final BitcoindeBalances balances =
            bitcoindeTradesWrapper.getData().getBalances();
    final BitcoindeFidorReservation fidorReservation =
            bitcoindeTradesWrapper.getData().getFidorReservation();

    assertEquals(new BigDecimal("0.009"), balances.btc.getAvailableAmount());
    assertEquals(new BigDecimal("0.008"), balances.bch.getAvailableAmount());
    assertEquals(new BigDecimal("0.007"), balances.btg.getAvailableAmount());
    assertEquals(new BigDecimal("0.006"), balances.bsv.getAvailableAmount());
    assertEquals(new BigDecimal("0.06463044"), balances.eth.getAvailableAmount());
    assertEquals(new BigDecimal("2000"), fidorReservation.getAvailableAmount());

    assertEquals(new BigDecimal("0.009"), balances.btc.getTotalAmount());
    assertEquals(new BigDecimal("0.008"), balances.bch.getTotalAmount());
    assertEquals(new BigDecimal("0.007"), balances.btg.getTotalAmount());
    assertEquals(new BigDecimal("0.006"), balances.bsv.getTotalAmount());
    assertEquals(new BigDecimal("0.06463044"), balances.eth.getTotalAmount());
    assertEquals(new BigDecimal("2000"), fidorReservation.getTotalAmount());

    assertEquals(new BigDecimal("0"), balances.btc.getReservedAmount());
    assertEquals(new BigDecimal("0"), balances.bch.getReservedAmount());
    assertEquals(new BigDecimal("0"), balances.btg.getReservedAmount());
    assertEquals(new BigDecimal("0"), balances.bsv.getReservedAmount());
    assertEquals(new BigDecimal("0"), balances.eth.getReservedAmount());

    assertEquals("2018-01-24T10:36:03+01:00", fidorReservation.getReservedAt());
    assertEquals("2018-01-31T10:36:02+01:00", fidorReservation.getValidUntil());

    assertEquals(new Integer("100"), fidorReservation.getAllocation().getBtc().getPercent());
    assertEquals(new Integer("2000"), fidorReservation.getAllocation().getBtc().getMaxEurVolume());
    assertEquals(new Integer("0"), fidorReservation.getAllocation().getBtc().getEurVolumeOpenOrders());

    assertEquals("0yzjPeTw33DssfxIB4io2Mow-w..", bitcoindeTradesWrapper.getData().getEncryptedInformation().getBicFull());
    assertEquals("0yzjPeTwlzkig1B-", bitcoindeTradesWrapper.getData().getEncryptedInformation().getBicShort());
    assertEquals("0ywbQpgJkzkjYVE1", bitcoindeTradesWrapper.getData().getEncryptedInformation().getUid());
  }
}
