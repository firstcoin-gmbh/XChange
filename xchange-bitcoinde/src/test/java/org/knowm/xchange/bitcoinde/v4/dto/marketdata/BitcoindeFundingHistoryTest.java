package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.BitcoindeUtils;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapterTest;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeFundingHistoryWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BitcoindeFundingHistoryTest {

    @Test
    public void testBitcoindeFundingHistory() throws IOException {

        final InputStream is =
                BitcoindeAdapterTest.class.getResourceAsStream(
                        "/org/knowm/xchange/bitcoinde/v4/dto/accountLedger.json");

        // Use Jackson to parse it
        final ObjectMapper mapper = new ObjectMapper();
        final BitcoindeFundingHistoryWrapper bitcoindeFundingHistoryWrapper =
                mapper.readValue(is, BitcoindeFundingHistoryWrapper.class);

        List<BitcoindeAccountLedger> accountLedgers = bitcoindeFundingHistoryWrapper.getAccountLedgers();

        assertEquals(BitcoindeType.SELL, accountLedgers.get(0).getType());
    }
}
