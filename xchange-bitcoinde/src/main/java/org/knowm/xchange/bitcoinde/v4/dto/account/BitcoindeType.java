package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeType.BitcoindeTypeDeserializer;


@JsonDeserialize(using = BitcoindeTypeDeserializer.class)
public enum
BitcoindeType {
    ALL,
    BUY,
    SELL,
    INPAYMENT,
    PAYOUT,
    AFFILIATE,
    WELCOME_BTC,
    BUY_YUBIKEY,
    BUY_GOLDSHOP,
    BUY_DIAMANTSHOP,
    KICKBACK,
    OUTGOING_FEE_VOLUNTARY;

    private static final Map<String, BitcoindeType> fromString = new HashMap<>();

    static {
        for (BitcoindeType ledgerType : values()) fromString.put(ledgerType.toString(), ledgerType);
    }

    public static BitcoindeType fromString(String ledgerTypeString) {

        BitcoindeType bitcoindeType = fromString.get(ledgerTypeString.toLowerCase());
        if (bitcoindeType == null) {
            throw new RuntimeException("Not supported bitcoinde ledger type: " + ledgerTypeString);
        }
        return bitcoindeType;
    }

    @Override
    public String toString() {

        return super.toString().toLowerCase();
    }

    static class BitcoindeTypeDeserializer extends JsonDeserializer<BitcoindeType> {

        @Override
        public BitcoindeType deserialize(JsonParser jsonParser, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            String ledgerTypeString = node.textValue();
            return fromString(ledgerTypeString);
        }
    }
}

